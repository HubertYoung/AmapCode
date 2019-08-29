package com.alipay.mobile.common.transportext.biz.diagnose.network;

import android.text.TextUtils;
import com.alipay.mobile.common.amnet.api.AmnetNetworkDiagnoseListener;
import com.alipay.mobile.common.amnet.api.AmnetStorageListener;
import com.alipay.mobile.common.transport.ext.diagnose.eastereggs.DiagnoseResult;
import com.alipay.mobile.common.transport.monitor.DeviceTrafficStateInfo;
import com.alipay.mobile.common.transport.monitor.networkqos.AlipayQosService;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transportext.amnet.Storage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DiagnoseByUserCall implements AmnetNetworkDiagnoseListener {
    private static final String TAG = "DIAGNOSE-USR";
    private static final long delay = 200;
    private static DiagnoseByUserCall diagnoseByUserCall = null;
    private DiagnoseResult callback;
    private DeviceTrafficStateInfo deviceTrafficStateInfo = null;
    private boolean flag = false;
    private List<String> logStrList = new ArrayList(5);
    private Future<?> networkDiagnoseFuture;
    private ScheduledFuture<?> scheduledFuture;

    private DiagnoseByUserCall() {
    }

    public static void launch(DiagnoseResult result) {
        synchronized (DiagnoseByUserCall.class) {
            if (diagnoseByUserCall != null) {
                diagnoseByUserCall.cancel();
            }
            diagnoseByUserCall = new DiagnoseByUserCall();
        }
        diagnoseByUserCall.register(result);
        diagnoseByUserCall.startDiagnose();
    }

    public void report(boolean fin, boolean ok, boolean done, String summary) {
        String str;
        StringBuilder append = new StringBuilder().append(fin).append(";").append(ok).append(";").append(done).append(";");
        if (summary == null) {
            str = "";
        } else {
            str = summary;
        }
        LogCatUtil.info(TAG, append.append(str).toString());
        if (!TextUtils.isEmpty(summary)) {
            this.logStrList.add(summary);
            showResult(parseResult(summary, ok));
        }
        if (done) {
            cancelScheduled();
            writeLog();
        }
    }

    /* access modifiers changed from: 0000 */
    public String getResult() {
        String log = "";
        if (this.logStrList == null || this.logStrList.isEmpty()) {
            return "There is no diagnose log.";
        }
        for (String tmp : this.logStrList) {
            StringBuilder append = new StringBuilder().append(log);
            if (tmp == null) {
                tmp = "";
            }
            log = append.append(tmp).append("\n").toString();
        }
        return log;
    }

    private String parseResult(String logContent, boolean ok) {
        String result;
        if (TextUtils.isEmpty(logContent)) {
            r3 = "";
            return "";
        }
        int index = logContent.indexOf("traceroute:");
        if (index != -1) {
            result = "Traceroute result is " + ok + ".\n" + logContent.substring("traceroute:".length() + index).replace('|', 10);
        } else {
            int index2 = logContent.indexOf("out_diago:");
            if (index2 != -1) {
                result = "Tcp result is " + ok + ".\n" + logContent.substring("out_diago:".length() + index2);
            } else {
                int index3 = logContent.indexOf("traffic:");
                if (index3 != -1) {
                    result = "Traffic result:\n" + logContent.substring("traffic:".length() + index3);
                } else {
                    result = "The result is " + ok + ".\n" + logContent;
                }
            }
        }
        return result;
    }

    private void showResult(String result) {
        if (this.callback != null) {
            this.callback.report(result);
        }
    }

    private void cancel() {
        LogCatUtil.info(TAG, "New diagnose task by user, cannel old task.");
        cancelScheduled();
        try {
            if (this.networkDiagnoseFuture != null) {
                this.networkDiagnoseFuture.cancel(true);
                this.networkDiagnoseFuture = null;
            }
        } catch (Throwable e) {
            LogCatUtil.error(TAG, "networkDiagnoseFuture cancel", e);
        }
    }

    private void cancelScheduled() {
        try {
            if (this.scheduledFuture != null) {
                this.scheduledFuture.cancel(true);
                this.scheduledFuture = null;
            }
        } catch (Throwable e) {
            LogCatUtil.error(TAG, "scheduledFuture cancel", e);
        }
    }

    private void register(DiagnoseResult result) {
        this.callback = result;
    }

    private void startDiagnose() {
        this.logStrList.clear();
        this.flag = false;
        final NetworkDiagnose networkDiagnose = new NetworkDiagnose();
        networkDiagnose.register((Storage) AmnetStorageListener.getInstance());
        networkDiagnose.register((AmnetNetworkDiagnoseListener) this);
        networkDiagnose.register(System.nanoTime(), 1);
        this.scheduledFuture = NetworkAsyncTaskExecutor.schedule((Runnable) new Runnable() {
            public void run() {
                LogCatUtil.info(DiagnoseByUserCall.TAG, "200 seconds timeout, set currentState idle.");
                DiagnoseByUserCall.this.writeLog();
            }
        }, 200, TimeUnit.SECONDS);
        this.deviceTrafficStateInfo = AlipayQosService.getInstance().startTrafficMonitor();
        LogCatUtil.info(TAG, "user networkDiagnose launch");
        this.networkDiagnoseFuture = NetworkAsyncTaskExecutor.submitLazy(new Runnable() {
            public void run() {
                LogCatUtil.info(DiagnoseByUserCall.TAG, "user start networkDiagnose launch");
                try {
                    networkDiagnose.launch();
                } catch (Throwable e) {
                    LogCatUtil.warn((String) DiagnoseByUserCall.TAG, "user diagnoseNotify throwable. " + e.toString());
                } finally {
                    r3 = "user networkDiagnose launch finish";
                    LogCatUtil.info(DiagnoseByUserCall.TAG, r3);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0013, code lost:
        r5.logStrList.add(r1);
        showResult(parseResult(r1, true));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0020, code lost:
        com.alipay.mobile.common.transportext.biz.diagnose.network.UploadManager.writeLog(r5.logStrList, "0.2", 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0029, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002a, code lost:
        com.alipay.mobile.common.transport.utils.LogCatUtil.warn((java.lang.String) TAG, "writeLog error. " + r0.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:?, code lost:
        r1 = com.alipay.mobile.common.transportext.biz.diagnose.network.TrafficLogHelper.getTrafficLog(r5.deviceTrafficStateInfo);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0011, code lost:
        if (r1 == null) goto L_0x0020;
     */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeLog() {
        /*
            r5 = this;
            monitor-enter(r5)
            boolean r2 = r5.flag     // Catch:{ all -> 0x0043 }
            if (r2 == 0) goto L_0x0007
            monitor-exit(r5)     // Catch:{ all -> 0x0043 }
        L_0x0006:
            return
        L_0x0007:
            r2 = 1
            r5.flag = r2     // Catch:{ all -> 0x0043 }
            monitor-exit(r5)     // Catch:{ all -> 0x0043 }
            com.alipay.mobile.common.transport.monitor.DeviceTrafficStateInfo r2 = r5.deviceTrafficStateInfo     // Catch:{ Throwable -> 0x0029 }
            java.lang.String r1 = com.alipay.mobile.common.transportext.biz.diagnose.network.TrafficLogHelper.getTrafficLog(r2)     // Catch:{ Throwable -> 0x0029 }
            if (r1 == 0) goto L_0x0020
            java.util.List<java.lang.String> r2 = r5.logStrList     // Catch:{ Throwable -> 0x0029 }
            r2.add(r1)     // Catch:{ Throwable -> 0x0029 }
            r2 = 1
            java.lang.String r2 = r5.parseResult(r1, r2)     // Catch:{ Throwable -> 0x0029 }
            r5.showResult(r2)     // Catch:{ Throwable -> 0x0029 }
        L_0x0020:
            java.util.List<java.lang.String> r2 = r5.logStrList     // Catch:{ Throwable -> 0x0029 }
            java.lang.String r3 = "0.2"
            r4 = 1
            com.alipay.mobile.common.transportext.biz.diagnose.network.UploadManager.writeLog(r2, r3, r4)     // Catch:{ Throwable -> 0x0029 }
            goto L_0x0006
        L_0x0029:
            r0 = move-exception
            java.lang.String r2 = "DIAGNOSE-USR"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "writeLog error. "
            r3.<init>(r4)
            java.lang.String r4 = r0.toString()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r2, r3)
            goto L_0x0006
        L_0x0043:
            r2 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0043 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.diagnose.network.DiagnoseByUserCall.writeLog():void");
    }
}
