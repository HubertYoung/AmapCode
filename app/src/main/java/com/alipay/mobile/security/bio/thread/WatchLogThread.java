package com.alipay.mobile.security.bio.thread;

import android.content.Context;
import com.alipay.mobile.security.bio.exception.BioIllegalArgumentException;
import com.alipay.mobile.security.bio.service.BioSysBehavior;
import com.alipay.mobile.security.bio.service.BioSysBehaviorType;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.utils.DateUtil;
import com.alipay.mobile.security.bio.utils.FileUtil;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class WatchLogThread extends WatchThread {
    private BlockingQueue<BioSysBehavior> a = new LinkedBlockingQueue(30);
    private File b = null;
    private File c = null;
    private File d = null;
    private File e = null;
    private File f = null;
    private File g = null;
    private Context h = null;

    public WatchLogThread(Context context, String str) {
        super(str);
        if (context == null) {
            throw new BioIllegalArgumentException();
        }
        this.h = context;
        this.b = this.h.getDir("BIO_LOGS", 0);
        String dateFormat = DateUtil.getDateFormat(System.currentTimeMillis());
        this.c = new File(this.b, "BIO_API_" + dateFormat + ".txt");
        this.d = new File(this.b, "BIO_METHOD_" + dateFormat + ".txt");
        this.e = new File(this.b, "BIO_CLICK_" + dateFormat + ".txt");
        this.f = new File(this.b, "BIO_NET_" + dateFormat + ".txt");
        this.g = new File(this.b, "BIO_EVENT_" + dateFormat + ".txt");
    }

    public WatchLogThread(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public final void task() {
        try {
            BioSysBehavior poll = this.a.poll(50, TimeUnit.SECONDS);
            if (poll != null) {
                HashMap<String, String> ext = poll.getExt();
                StringBuilder sb = new StringBuilder();
                if (!ext.isEmpty()) {
                    for (String next : ext.keySet()) {
                        sb.append("[key:" + next + ", value:" + ext.get(next) + "]");
                    }
                }
                BioSysBehaviorType type = poll.getType();
                String str = poll.getMsg() + sb.toString();
                switch (a.a[type.ordinal()]) {
                    case 1:
                        FileUtil.saveContent(this.c, str, true);
                        return;
                    case 2:
                        FileUtil.saveContent(this.e, str, true);
                        return;
                    case 3:
                        FileUtil.saveContent(this.f, str, true);
                        return;
                    case 4:
                        FileUtil.saveContent(this.g, str, true);
                        return;
                    case 5:
                        FileUtil.saveContent(this.d, str, true);
                        return;
                    default:
                        return;
                }
            }
        } catch (InterruptedException e2) {
            BioLog.w((Throwable) e2);
        }
    }

    public void addThreadItem(BioSysBehavior bioSysBehavior) {
        try {
            this.a.add(bioSysBehavior);
        } catch (Throwable th) {
            BioLog.e(th.toString());
        }
    }
}
