package com.alipay.mobile.security.bio.service.impl;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.security.bio.exception.BioIllegalArgumentException;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.BioUploadCallBack;
import com.alipay.mobile.security.bio.service.BioUploadItem;
import com.alipay.mobile.security.bio.service.BioUploadResult;
import com.alipay.mobile.security.bio.service.ZimRecordService;
import com.alipay.mobile.security.bio.thread.WatchThread;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.workspace.Env;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class BioUploadWatchThread extends WatchThread {
    private BlockingQueue<BioUploadItem> a = new LinkedBlockingQueue(5);
    private BioUploadGW b;
    private List<BioUploadCallBack> c = new ArrayList();
    private AtomicBoolean d;
    private String e = "";
    public Handler mMainHandle;

    public void setZimId(String str) {
        this.e = str;
    }

    public synchronized void addBioUploadCallBack(BioUploadCallBack bioUploadCallBack) {
        BioLog.d("BioUploadWatchThread.addBioUploadCallBack(): callBack=" + bioUploadCallBack);
        this.c.add(bioUploadCallBack);
    }

    public BioUploadWatchThread(String str, BioServiceManager bioServiceManager) {
        String str2;
        super(str);
        if (bioServiceManager == null) {
            throw new BioIllegalArgumentException("BioServiceManager can't be null.");
        }
        switch (Env.getProtocolFormat(bioServiceManager.getBioApplicationContext())) {
            case 2:
                str2 = "com.alipay.mobile.security.bio.service.impl.BioUploadPBGWImpl";
                break;
            default:
                str2 = "com.alipay.mobile.security.bio.service.impl.BioUploadJsonGWImpl";
                break;
        }
        try {
            Constructor<?> constructor = Class.forName(str2).getConstructor(new Class[]{BioServiceManager.class});
            constructor.setAccessible(true);
            this.b = (BioUploadGW) constructor.newInstance(new Object[]{bioServiceManager});
        } catch (Throwable th) {
            BioLog.e(th);
        }
        this.mMainHandle = new Handler(Looper.getMainLooper());
    }

    private static boolean a(String str) {
        return TextUtils.isEmpty(str) || str.contains("_bis");
    }

    /* access modifiers changed from: protected */
    public final void task() {
        try {
            BioUploadItem poll = this.a.poll(50, TimeUnit.SECONDS);
            BioLog.e("BioUploadWatchThread.task(1), mClearUpFlag=" + this.d + ", request=" + poll);
            if (poll != null) {
                ZimRecordService zimRecordService = null;
                if (!a(this.e) && poll.isNeedSendResponse) {
                    zimRecordService = (ZimRecordService) BioServiceManager.getCurrentInstance().getBioService(ZimRecordService.class);
                    if (zimRecordService != null) {
                        zimRecordService.write(new MetaRecord((String) "UC-RZHY-170807-06", (String) "event", (String) "20001117", (String) "validateRequest", 1));
                    }
                }
                this.d.set(false);
                BioUploadResult upload = this.b.upload(this.e, poll);
                if (!a(this.e) && poll.isNeedSendResponse && zimRecordService != null) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("result", (1000 == upload.validationRetCode));
                    hashMap.put("message", upload.validationRetCode);
                    hashMap.put("retCode", upload.validationRetCode);
                    hashMap.put("subCode", upload.subCode);
                    hashMap.put("subMsg", upload.subMsg);
                    hashMap.put("productRetCode", upload.productRetCode);
                    hashMap.put("validationRetCode", upload.validationRetCode);
                    hashMap.put("hasNext", upload.hasNext);
                    if (upload.extParams != null && !upload.extParams.isEmpty()) {
                        hashMap.putAll(upload.extParams);
                    }
                    zimRecordService.write(new MetaRecord((String) "UC-RZHY-170807-07", (String) "event", (String) "20001117", (String) "validateResponse", 1), hashMap);
                }
                BioLog.e("BioUploadWatchThread.task(2), mClearUpFlag=" + this.d);
                if (this.d.getAndSet(false)) {
                    BioLog.e("BioUploadWatchThread.task(2.5), mClearUpFlag=" + this.d + ", return.");
                } else if (!this.c.isEmpty() && upload != null && this.mMainHandle != null && poll.isNeedSendResponse) {
                    BioLog.e((String) "BioUploadWatchThread.task(3)");
                    this.mMainHandle.post(new c(this, upload));
                }
            }
        } catch (Exception e2) {
            BioLog.e((Throwable) e2);
        }
    }

    public boolean isFulled() {
        return this.a.size() >= 5;
    }

    public void addBioUploadItem(BioUploadItem bioUploadItem) {
        try {
            boolean add = this.a.add(bioUploadItem);
            BioLog.e("BioUploadWatchThread.addBioUploadItem(), isAddSuc=" + add + ", item=" + bioUploadItem);
            if (!add) {
                this.mMainHandle.post(new d(this));
            }
        } catch (IllegalStateException e2) {
            BioLog.e((Throwable) e2);
        }
    }

    /* access modifiers changed from: private */
    public synchronized void a(BioUploadResult bioUploadResult) {
        boolean z = false;
        Iterator<BioUploadCallBack> it = this.c.iterator();
        while (it.hasNext() && !z) {
            z = it.next().onResponse(bioUploadResult);
        }
    }

    public void release() {
        this.c.clear();
        BioLog.d("BioUploadWatchThread.release() => clearBioUploadCallBacks()");
        this.a.clear();
        this.b = null;
        kill();
    }

    public boolean isEmpty() {
        return this.a == null || this.a.isEmpty();
    }

    public synchronized void clearBioUploadCallBacks() {
        if (this.d == null) {
            this.d = new AtomicBoolean(false);
        } else {
            this.d.set(true);
        }
        BioLog.w("BioUploadWatchThread.clearBioUploadCallBacks(), mClearUpFlag=" + this.d);
        this.c.clear();
    }

    public void clearUploadItems() {
        BioLog.w((String) "BioUploadWatchThread.clearUploadItems()");
        this.a.clear();
    }
}
