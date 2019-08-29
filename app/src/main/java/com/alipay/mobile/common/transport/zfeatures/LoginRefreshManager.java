package com.alipay.mobile.common.transport.zfeatures;

import android.content.Context;
import com.alipay.mobile.common.netsdkextdependapi.deviceinfo.DeviceInfoUtil;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.http.HttpWorker;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class LoginRefreshManager {
    private static LoginRefreshManager a = null;
    private byte b = -1;
    private int c = -1;
    private int d = 0;
    /* access modifiers changed from: private */
    public Map<HttpWorker, SeqModel> e = new HashMap();
    private ReentrantReadWriteLock f = new ReentrantReadWriteLock();
    private ReadLock g = this.f.readLock();
    private WriteLock h = this.f.writeLock();
    /* access modifiers changed from: private */
    public AtomicBoolean i = new AtomicBoolean(false);

    class SafetyInspector implements Runnable {
        private LoginRefreshManager a;

        public SafetyInspector(LoginRefreshManager loginRefreshManager) {
            this.a = loginRefreshManager;
        }

        public void run() {
            try {
                if (this.a.e.isEmpty()) {
                    this.a.i.set(false);
                    return;
                }
                this.a.f();
                if (this.a.e.isEmpty()) {
                    this.a.i.set(false);
                    this.a.b();
                    return;
                }
                this.a.d();
            } catch (Throwable e) {
                LogCatUtil.warn("LoginRefreshManager", "SafetyInspector. execute error. ", e);
            }
        }
    }

    class SeqModel {
        final long createTime = System.currentTimeMillis();
        final int seqNum;

        public SeqModel(int seqNum2) {
            this.seqNum = seqNum2;
        }

        public long getCreateTime() {
            return this.createTime;
        }
    }

    private LoginRefreshManager() {
    }

    public static final LoginRefreshManager getInstance() {
        LoginRefreshManager loginRefreshManager;
        if (a != null) {
            return a;
        }
        synchronized (LoginRefreshManager.class) {
            try {
                if (a == null) {
                    a = new LoginRefreshManager();
                }
                loginRefreshManager = a;
            }
        }
        return loginRefreshManager;
    }

    public void enableRefresh() {
        if (this.b != 1) {
            this.b = 1;
            LogCatUtil.info("LoginRefreshManager", "enableRefresh. mEnabledLoginRefresh is 1");
        }
    }

    public boolean isEnabledLoginRefresh(Context context) {
        int i2;
        if (!MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.LOGIN_REFRESH_SWITCH))) {
            return false;
        }
        if (this.b != -1) {
            return this.b == 1;
        }
        boolean result = MiscUtils.getBooleanFromMetaData(context, "login_refresh_feature");
        LogCatUtil.info("LoginRefreshManager", "isEnabledLoginRefresh. meta-data value : " + result);
        if (result) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        try {
            this.b = (byte) i2;
            LogCatUtil.info("LoginRefreshManager", "isEnabledLoginRefresh. mEnabledLoginRefresh : " + this.b);
            return result;
        } catch (Throwable e2) {
            LogCatUtil.error("LoginRefreshManager", "isEnabledLoginRefresh error", e2);
            if (this.b == 1) {
                return true;
            }
            return false;
        }
    }

    public boolean checkIn(HttpWorker httpWorker) {
        boolean z = true;
        if (c()) {
            this.g.lock();
            try {
                z = a(httpWorker);
            } catch (Throwable e2) {
                LogCatUtil.error("LoginRefreshManager", "checkIn error. ", e2);
            } finally {
                this.g.unlock();
            }
        }
        return z;
    }

    public void recordRpc(HttpWorker httpWorker) {
        if (c()) {
            this.h.lock();
            try {
                b(httpWorker);
            } finally {
                this.h.unlock();
            }
        }
    }

    public void removeRecord(HttpWorker httpWorker) {
        if (c()) {
            this.h.lock();
            try {
                c(httpWorker);
            } finally {
                this.h.unlock();
            }
        }
    }

    private void a() {
        if (this.e.isEmpty()) {
            if (this.c != -1 || this.d != 0) {
                this.c = -1;
                this.d = 0;
                LogCatUtil.info("LoginRefreshManager", "reseted");
            }
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        this.h.lock();
        try {
            a();
        } finally {
            this.h.unlock();
        }
    }

    private boolean c() {
        if (this.b == -1) {
            return false;
        }
        if (this.b == 1) {
            return true;
        }
        return false;
    }

    private boolean a(HttpWorker httpWorker) {
        if (httpWorker.getOriginRequest().isSwitchLoginRpc()) {
            this.c = e();
            LogCatUtil.info("LoginRefreshManager", "loginRespSeq is " + String.valueOf(this.c));
            return true;
        } else if (this.c == -1) {
            return true;
        } else {
            SeqModel workSeqModel = this.e.get(httpWorker);
            if (workSeqModel == null || workSeqModel.seqNum > this.c) {
                return true;
            }
            LogCatUtil.warn((String) "LoginRefreshManager", " checkIn it's false. API is " + httpWorker.getOperationType() + ", loginRespSeq=" + this.c + ", rpcReqSeq=" + workSeqModel.seqNum);
            return false;
        }
    }

    private void b(HttpWorker httpWorker) {
        if (!httpWorker.getOriginRequest().isSwitchLoginRpc()) {
            this.e.put(httpWorker, new SeqModel(e()));
            d();
        }
    }

    /* access modifiers changed from: private */
    public void d() {
        if (!this.i.get() && !this.i.get()) {
            this.i.set(true);
            NetworkAsyncTaskExecutor.schedule((Runnable) new SafetyInspector(this), 60, TimeUnit.SECONDS);
        }
    }

    private void c(HttpWorker httpWorker) {
        this.e.remove(httpWorker);
        a();
    }

    private int e() {
        int i2 = this.d + 1;
        this.d = i2;
        return i2;
    }

    /* access modifiers changed from: private */
    public void f() {
        this.h.lock();
        try {
            if (!this.e.isEmpty()) {
                List<HttpWorker> gclist = new ArrayList<>(3);
                for (Entry entry : this.e.entrySet()) {
                    if (System.currentTimeMillis() - ((SeqModel) entry.getValue()).getCreateTime() > 300000) {
                        gclist.add(entry.getKey());
                    }
                }
                if (gclist.isEmpty()) {
                    this.h.unlock();
                    return;
                }
                StringBuilder logBuilder = new StringBuilder("gcReqSeqMap.  The GC RPC are: ");
                for (HttpWorker httpWorker : gclist) {
                    logBuilder.append(httpWorker.getOperationType()).append(",");
                    this.e.remove(httpWorker);
                }
                this.h.unlock();
                LogCatUtil.warn((String) "LoginRefreshManager", logBuilder.toString());
            }
        } finally {
            this.h.unlock();
        }
    }
}
