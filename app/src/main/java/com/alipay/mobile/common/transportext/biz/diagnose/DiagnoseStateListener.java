package com.alipay.mobile.common.transportext.biz.diagnose;

import android.text.TextUtils;
import com.alipay.mobile.common.amnet.api.AmnetNetworkDiagnoseListener;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transportext.amnet.NetTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

class DiagnoseStateListener implements AmnetNetworkDiagnoseListener, NetTest {
    long executTime = -1;
    boolean firstReport = true;
    List<String> logStrList = new ArrayList(5);
    NetworkDiagnoseServiceImpl networkDiagnoseServiceImpl;
    boolean oneceSuccess = false;

    DiagnoseStateListener(NetworkDiagnoseServiceImpl networkDiagnoseServiceImpl2) {
        this.networkDiagnoseServiceImpl = networkDiagnoseServiceImpl2;
    }

    public void report(boolean fin, boolean ok, boolean done, String summary) {
        int delay;
        if (this.networkDiagnoseServiceImpl.isStateRunning()) {
            synchronized (this) {
                if (this.firstReport) {
                    this.firstReport = false;
                    int i1 = new Random().nextInt(20);
                    if (i1 < 6) {
                        i1 += 6;
                    }
                    delay = i1 * 1000;
                    this.executTime = System.currentTimeMillis() + ((long) delay) + 2000;
                } else {
                    long l = this.executTime - System.currentTimeMillis();
                    if (l < 1) {
                        delay = 0;
                    } else {
                        delay = (int) l;
                        this.executTime = this.executTime + ((long) delay) + 2000;
                    }
                }
                LogCatUtil.info("NetTest", " delay=" + delay);
            }
            final boolean z = done;
            final boolean z2 = ok;
            final String str = summary;
            final boolean z3 = fin;
            NetworkAsyncTaskExecutor.schedule((Runnable) new Runnable() {
                public void run() {
                    DiagnoseStateListener.this.doCalcProgress(z, z2, str, z3);
                }
            }, (long) delay, TimeUnit.MILLISECONDS);
        }
    }

    /* access modifiers changed from: private */
    public void doCalcProgress(boolean done, boolean ok, String summary, boolean fin) {
        try {
            LogCatUtil.info("NetTest", "fin=[" + fin + "], ok=[" + ok + "], done=[" + done + "], summary=[" + summary + "]");
            if (done) {
                if (!TextUtils.isEmpty(summary)) {
                    this.logStrList.add(summary);
                }
                if ((this.oneceSuccess || ok) && !this.logStrList.isEmpty()) {
                    this.oneceSuccess = true;
                    if (this.networkDiagnoseServiceImpl.setProgressToStartLog()) {
                        this.networkDiagnoseServiceImpl.notifyUpdateProgress();
                    }
                } else if (this.networkDiagnoseServiceImpl.setProgressToFail()) {
                    this.networkDiagnoseServiceImpl.notifyUpdateProgress();
                }
            } else if (fin) {
                if (fin) {
                    if (this.networkDiagnoseServiceImpl.setProgressToStartTraceRouting()) {
                        this.networkDiagnoseServiceImpl.notifyUpdateProgress();
                    }
                    if (ok) {
                        this.oneceSuccess = true;
                    }
                }
                if (!TextUtils.isEmpty(summary)) {
                    this.logStrList.add(summary);
                }
            } else if (ok) {
                this.oneceSuccess = true;
            }
        } catch (Exception e) {
            LogCatUtil.error((String) "NetTest", (Throwable) e);
        }
    }

    /* access modifiers changed from: 0000 */
    public List<String> getLogStrList() {
        return this.logStrList;
    }

    public boolean getOneceSuccess() {
        return this.oneceSuccess;
    }
}
