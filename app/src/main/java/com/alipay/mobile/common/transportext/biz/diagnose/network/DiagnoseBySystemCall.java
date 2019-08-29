package com.alipay.mobile.common.transportext.biz.diagnose.network;

import android.text.TextUtils;
import com.alipay.mobile.common.amnet.api.AmnetNetworkDiagnoseListener;
import com.alipay.mobile.common.amnet.api.AmnetStorageListener;
import com.alipay.mobile.common.transport.monitor.DeviceTrafficStateInfo;
import com.alipay.mobile.common.transport.monitor.networkqos.AlipayQosService;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transportext.amnet.Storage;
import com.alipay.mobile.monitor.api.MonitorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DiagnoseBySystemCall implements AmnetNetworkDiagnoseListener {
    private static final int STATE_IDLE = 1;
    private static final int STATE_RUNNING = 2;
    private static final String TAG = "DIAGNOSE-SYS";
    private static int currentState = 1;
    private static final long delay = 200;
    private static long lastDiagnoseTime = 0;
    private static long timeSpan = 300000;
    private DeviceTrafficStateInfo deviceTrafficStateInfo = null;
    private boolean flag = false;
    private boolean isContainJavaDiag = false;
    private List<String> logStrList = new ArrayList(5);
    private String outDiagDescribe = "";
    private boolean outDiagOk = false;
    private DiagnoseResultState result = null;
    private ScheduledFuture<?> scheduledFuture;

    public interface DiagnoseResultState {
        void stateNotify(boolean z);
    }

    private DiagnoseBySystemCall() {
    }

    public static void diagnoseNotify() {
        diagnoseNotify(null);
    }

    public static void diagnoseNotify(DiagnoseResultState result2) {
        long curTime = System.currentTimeMillis();
        if (curTime - lastDiagnoseTime < timeSpan) {
            LogCatUtil.info(TAG, "curTime: " + curTime + ",lastDiagnoseTime: " + lastDiagnoseTime + ",ignore this diagnose");
            return;
        }
        synchronized (DiagnoseBySystemCall.class) {
            if (currentState != 1) {
                if (result2 != null) {
                    result2.stateNotify(false);
                }
                LogCatUtil.debug(TAG, "diagnoseNotify,currentState is busy");
                return;
            }
            currentState = 2;
            lastDiagnoseTime = curTime;
            DiagnoseBySystemCall diagnoseBySystemCall = new DiagnoseBySystemCall();
            diagnoseBySystemCall.register(result2);
            diagnoseBySystemCall.startDiagnose();
        }
    }

    public void report(boolean fin, boolean ok, boolean done, String summary) {
        String str;
        StringBuilder append = new StringBuilder().append(fin).append(";").append(ok).append(";").append(done).append(";");
        if (summary == null) {
            str = "";
        } else {
            str = summary;
        }
        String result2 = append.append(str).toString();
        if (1 == currentState) {
            LogCatUtil.warn((String) TAG, "the diagnose already stop. this result ignore. " + result2);
            return;
        }
        LogCatUtil.info(TAG, result2);
        if (!TextUtils.isEmpty(summary)) {
            this.logStrList.add(summary);
            if (summary.contains(Configuration.LOG_TYPE_OUT_DIAGO)) {
                outDiag(ok, summary);
            }
        }
        if (done) {
            this.scheduledFuture.cancel(true);
            writeLog();
        }
    }

    private void startDiagnose() {
        final NetworkDiagnose networkDiagnose = new NetworkDiagnose();
        networkDiagnose.register((Storage) AmnetStorageListener.getInstance());
        networkDiagnose.register((AmnetNetworkDiagnoseListener) this);
        networkDiagnose.register(System.nanoTime(), 2);
        this.scheduledFuture = NetworkAsyncTaskExecutor.schedule((Runnable) new Runnable() {
            public void run() {
                LogCatUtil.info(DiagnoseBySystemCall.TAG, "200 seconds timeout, set currentState idle.");
                DiagnoseBySystemCall.this.writeLog();
            }
        }, 200, TimeUnit.SECONDS);
        this.deviceTrafficStateInfo = AlipayQosService.getInstance().startTrafficMonitor();
        LogCatUtil.info(TAG, "system networkDiagnose launch");
        NetworkAsyncTaskExecutor.executeLazy(new Runnable() {
            public void run() {
                LogCatUtil.info(DiagnoseBySystemCall.TAG, "system start networkDiagnose launch");
                try {
                    networkDiagnose.launch();
                } catch (Throwable e) {
                    LogCatUtil.warn((String) DiagnoseBySystemCall.TAG, "system diagnoseNotify throwable. " + e.toString());
                } finally {
                    r3 = "system networkDiagnose launch finish";
                    LogCatUtil.info(DiagnoseBySystemCall.TAG, r3);
                }
            }
        });
    }

    private void register(DiagnoseResultState result2) {
        this.result = result2;
    }

    private void unRegister() {
        this.result = null;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        r1 = com.alipay.mobile.common.transportext.biz.diagnose.network.TrafficLogHelper.getTrafficLog(r6.deviceTrafficStateInfo);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0015, code lost:
        if (r1 == null) goto L_0x001c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0017, code lost:
        r6.logStrList.add(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001c, code lost:
        com.alipay.mobile.common.transportext.biz.diagnose.network.UploadManager.writeLog(r6.logStrList, "0.3", 2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0026, code lost:
        if (r6.result == null) goto L_0x0030;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0028, code lost:
        r6.result.stateNotify(true);
        unRegister();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0030, code lost:
        currentState = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0036, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        com.alipay.mobile.common.transport.utils.LogCatUtil.warn((java.lang.String) TAG, "writeLog error. " + r0.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0051, code lost:
        if (r6.result != null) goto L_0x0053;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0053, code lost:
        r6.result.stateNotify(true);
        unRegister();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005b, code lost:
        currentState = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x005e, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0061, code lost:
        if (r6.result != null) goto L_0x0063;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0063, code lost:
        r6.result.stateNotify(true);
        unRegister();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x006b, code lost:
        currentState = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x006d, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000c, code lost:
        notifyOutDiagResult();
     */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeLog() {
        /*
            r6 = this;
            r5 = 1
            monitor-enter(r6)
            boolean r2 = r6.flag     // Catch:{ all -> 0x0033 }
            if (r2 == 0) goto L_0x0008
            monitor-exit(r6)     // Catch:{ all -> 0x0033 }
        L_0x0007:
            return
        L_0x0008:
            r2 = 1
            r6.flag = r2     // Catch:{ all -> 0x0033 }
            monitor-exit(r6)     // Catch:{ all -> 0x0033 }
            r6.notifyOutDiagResult()
            com.alipay.mobile.common.transport.monitor.DeviceTrafficStateInfo r2 = r6.deviceTrafficStateInfo     // Catch:{ Throwable -> 0x0036 }
            java.lang.String r1 = com.alipay.mobile.common.transportext.biz.diagnose.network.TrafficLogHelper.getTrafficLog(r2)     // Catch:{ Throwable -> 0x0036 }
            if (r1 == 0) goto L_0x001c
            java.util.List<java.lang.String> r2 = r6.logStrList     // Catch:{ Throwable -> 0x0036 }
            r2.add(r1)     // Catch:{ Throwable -> 0x0036 }
        L_0x001c:
            java.util.List<java.lang.String> r2 = r6.logStrList     // Catch:{ Throwable -> 0x0036 }
            java.lang.String r3 = "0.3"
            r4 = 2
            com.alipay.mobile.common.transportext.biz.diagnose.network.UploadManager.writeLog(r2, r3, r4)     // Catch:{ Throwable -> 0x0036 }
            com.alipay.mobile.common.transportext.biz.diagnose.network.DiagnoseBySystemCall$DiagnoseResultState r2 = r6.result
            if (r2 == 0) goto L_0x0030
            com.alipay.mobile.common.transportext.biz.diagnose.network.DiagnoseBySystemCall$DiagnoseResultState r2 = r6.result
            r2.stateNotify(r5)
            r6.unRegister()
        L_0x0030:
            currentState = r5
            goto L_0x0007
        L_0x0033:
            r2 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0033 }
            throw r2
        L_0x0036:
            r0 = move-exception
            java.lang.String r2 = "DIAGNOSE-SYS"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x005e }
            java.lang.String r4 = "writeLog error. "
            r3.<init>(r4)     // Catch:{ all -> 0x005e }
            java.lang.String r4 = r0.toString()     // Catch:{ all -> 0x005e }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x005e }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x005e }
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r2, r3)     // Catch:{ all -> 0x005e }
            com.alipay.mobile.common.transportext.biz.diagnose.network.DiagnoseBySystemCall$DiagnoseResultState r2 = r6.result
            if (r2 == 0) goto L_0x005b
            com.alipay.mobile.common.transportext.biz.diagnose.network.DiagnoseBySystemCall$DiagnoseResultState r2 = r6.result
            r2.stateNotify(r5)
            r6.unRegister()
        L_0x005b:
            currentState = r5
            goto L_0x0007
        L_0x005e:
            r2 = move-exception
            com.alipay.mobile.common.transportext.biz.diagnose.network.DiagnoseBySystemCall$DiagnoseResultState r3 = r6.result
            if (r3 == 0) goto L_0x006b
            com.alipay.mobile.common.transportext.biz.diagnose.network.DiagnoseBySystemCall$DiagnoseResultState r3 = r6.result
            r3.stateNotify(r5)
            r6.unRegister()
        L_0x006b:
            currentState = r5
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.diagnose.network.DiagnoseBySystemCall.writeLog():void");
    }

    private void outDiag(boolean ok, String result2) {
        if (!TextUtils.isEmpty(result2)) {
            this.outDiagOk |= ok;
            this.isContainJavaDiag = true;
            try {
                int index = result2.indexOf("out_diago:");
                if (index >= 0) {
                    String result3 = result2.substring("out_diago:".length() + index);
                    if (!TextUtils.isEmpty(result3) && !ok) {
                        List list = SpeedTestManager.convertLinkData(result3);
                        if (list != null && !list.isEmpty()) {
                            SpeedTestLinkData speedTestLinkData = list.get(0);
                            if (speedTestLinkData != null) {
                                this.outDiagDescribe += speedTestLinkData.describe + ";" + speedTestLinkData.errCode + ";";
                            }
                        }
                    }
                }
            } catch (Throwable e) {
                LogCatUtil.warn((String) TAG, "outDiag " + e.toString());
            }
        }
    }

    private void notifyOutDiagResult() {
        if (this.isContainJavaDiag && !this.outDiagOk) {
            LogCatUtil.warn((String) TAG, "notifyOutDiagResult, the describe is " + this.outDiagDescribe);
            try {
                MonitorFactory.getMonitorContext().kickOnNetworkDiagnose(true, this.outDiagDescribe);
            } catch (Throwable e) {
                LogCatUtil.error((String) TAG, e);
            }
        }
    }
}
