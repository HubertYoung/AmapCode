package com.alipay.mobile.common.transportext.biz.diagnose;

import android.content.Context;
import com.alipay.mobile.common.amnet.api.AmnetNetworkDiagnoseListener;
import com.alipay.mobile.common.amnet.api.AmnetStorageListener;
import com.alipay.mobile.common.transport.ext.diagnose.NetworkDiagnoseListener;
import com.alipay.mobile.common.transport.ext.diagnose.NetworkDiagnoseService;
import com.alipay.mobile.common.transport.monitor.DeviceTrafficStateInfo;
import com.alipay.mobile.common.transport.monitor.networkqos.AlipayQosService;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transportext.amnet.Storage;
import com.alipay.mobile.common.transportext.biz.diagnose.network.NetworkDiagnose;
import com.alipay.mobile.common.transportext.biz.diagnose.network.TrafficLogHelper;
import com.alipay.mobile.common.transportext.biz.diagnose.network.UploadManager;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportEnv;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class NetworkDiagnoseServiceImpl implements NetworkDiagnoseService {
    private static final int PROGRESS_CONNECTING = 40;
    private static final int PROGRESS_FINISH = 100;
    private static final int PROGRESS_TRACE_ROUTING = 95;
    private static final int STATE_IDLE = 1;
    private static final int STATE_RUNNING = 2;
    private static final String TAG = "NetDiag";
    private static final String TEXT_CONNECTING = "开始连接服务器";
    private static final String TEXT_FAIL = "失败";
    private static final String TEXT_SUCCESS = "成功";
    private static final String TRACE_ROUTING = "正在检查路由信息";
    private static final String UPLOADING = "正在上传报告";
    /* access modifiers changed from: private */
    public volatile int currentProgress = 0;
    private int currentStat = 1;
    private DeviceTrafficStateInfo deviceTrafficStateInfo = null;
    private DiagnoseStateListener diagnoseStateListener;
    private List<NetworkDiagnoseListener> listeners = new ArrayList(1);
    private Future<?> networkDiagnoseFuture;
    private ScheduledFuture<?> scheduledFuture;
    /* access modifiers changed from: private */
    public int state = 1;
    private String text = TEXT_CONNECTING;
    private Runnable timerTask;

    class ZTimerTask implements Runnable {
        ZTimerTask() {
        }

        public void run() {
            if (!NetworkUtils.isNetworkAvailable(ExtTransportEnv.getAppContext())) {
                NetworkDiagnoseServiceImpl.this.state = 5;
            }
            if (NetworkDiagnoseServiceImpl.this.state == 1 && NetworkDiagnoseServiceImpl.this.currentProgress < 40) {
                NetworkDiagnoseServiceImpl.this.currentProgress = NetworkDiagnoseServiceImpl.this.currentProgress + 1;
                NetworkDiagnoseServiceImpl.this.notifyUpdateProgress();
                if (NetworkDiagnoseServiceImpl.this.currentProgress == 40) {
                    NetworkDiagnoseServiceImpl.this.setProgressToStartTraceRouting();
                }
            } else if (NetworkDiagnoseServiceImpl.this.state == 2 && NetworkDiagnoseServiceImpl.this.currentProgress < 95) {
                NetworkDiagnoseServiceImpl.this.currentProgress = NetworkDiagnoseServiceImpl.this.currentProgress + 1;
                NetworkDiagnoseServiceImpl.this.notifyUpdateProgress();
                if (NetworkDiagnoseServiceImpl.this.currentProgress == 95) {
                    NetworkDiagnoseServiceImpl.this.setProgressToFail();
                }
            } else if (NetworkDiagnoseServiceImpl.this.state == 5) {
                NetworkDiagnoseServiceImpl.this.currentProgress = 100;
                NetworkDiagnoseServiceImpl.this.notifyUpdateProgress();
                NetworkDiagnoseServiceImpl.this.cancel();
            } else if (NetworkDiagnoseServiceImpl.this.state == 3) {
                NetworkDiagnoseServiceImpl.this.currentProgress = NetworkDiagnoseServiceImpl.this.currentProgress + 1;
                NetworkDiagnoseServiceImpl.this.notifyUpdateProgress();
            }
        }
    }

    public void addDiagnoseListener(NetworkDiagnoseListener listener) {
        this.listeners.add(listener);
    }

    public void removeDiagnoseListener(NetworkDiagnoseListener listener) {
        this.listeners.remove(listener);
    }

    public void startDiagnose() {
        synchronized (this) {
            if (this.currentStat == 1) {
                this.currentStat = 2;
                startDiagnoseTask();
                this.timerTask = new ZTimerTask();
                this.scheduledFuture = NetworkAsyncTaskExecutor.scheduleAtFixedRate(this.timerTask, 0, 1200, TimeUnit.MILLISECONDS);
            }
        }
    }

    private void startDiagnoseTask() {
        this.diagnoseStateListener = new DiagnoseStateListener(this);
        final NetworkDiagnose networkDiagnose = new NetworkDiagnose();
        networkDiagnose.register((Storage) AmnetStorageListener.getInstance());
        networkDiagnose.register((AmnetNetworkDiagnoseListener) this.diagnoseStateListener);
        networkDiagnose.register(System.nanoTime(), 1);
        this.deviceTrafficStateInfo = AlipayQosService.getInstance().startTrafficMonitor();
        LogCatUtil.info(TAG, "submit networkDiagnose launch");
        this.networkDiagnoseFuture = NetworkAsyncTaskExecutor.submitLazy(new Runnable() {
            public void run() {
                LogCatUtil.info(NetworkDiagnoseServiceImpl.TAG, "start networkDiagnose launch");
                try {
                    networkDiagnose.launch();
                } finally {
                    r2 = "networkDiagnose launch finish";
                    LogCatUtil.info(NetworkDiagnoseServiceImpl.TAG, r2);
                }
            }
        });
    }

    public void cancel() {
        cancelTimer();
        recycle();
    }

    private void recycle() {
        this.diagnoseStateListener = null;
        this.text = TEXT_CONNECTING;
        this.currentProgress = 0;
        this.state = 1;
        this.currentStat = 1;
    }

    private void cancelTimer() {
        if (this.timerTask != null) {
            this.timerTask = null;
        }
        try {
            if (this.scheduledFuture != null) {
                this.scheduledFuture.cancel(true);
                this.scheduledFuture = null;
            }
        } catch (Exception e) {
            LogCatUtil.error(TAG, "scheduledFuture cancel", e);
        }
        try {
            if (this.networkDiagnoseFuture != null) {
                this.networkDiagnoseFuture.cancel(true);
                this.networkDiagnoseFuture = null;
            }
        } catch (Exception e2) {
            LogCatUtil.error(TAG, "networkDiagnoseFuture cancel", e2);
        }
    }

    public void notifyUpdateProgress() {
        if (this.currentStat != 1) {
            for (NetworkDiagnoseListener updateProgress : this.listeners) {
                updateProgress.updateProgress(this.text, this.currentProgress, this.state);
            }
            if (this.state == 5 || this.state == 4) {
                if (this.state == 5) {
                    writeLog();
                }
                cancel();
            } else if (this.state == 3) {
                writeLog();
            }
            LogCatUtil.info(TAG, "notifyUpdateProgress  state=" + this.state);
        }
    }

    public boolean isCanDiagnose() {
        Context appContext = ExtTransportEnv.getAppContext();
        if (appContext != null && NetworkUtils.isNetworkAvailable(appContext)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean isStateRunning() {
        return this.currentStat == 2;
    }

    /* access modifiers changed from: 0000 */
    public synchronized boolean setProgressToStartTraceRouting() {
        boolean z = true;
        synchronized (this) {
            try {
                if (this.state != 1 || this.currentProgress > 40) {
                    z = false;
                } else {
                    this.state = 2;
                    this.text = TRACE_ROUTING;
                    this.currentProgress = 41;
                }
            }
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public synchronized boolean setProgressToStartLog() {
        boolean z;
        try {
            if (this.state != 2 || this.currentProgress > 95) {
                z = false;
            } else {
                this.state = 3;
                this.text = UPLOADING;
                this.currentProgress = 96;
                z = true;
            }
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public synchronized boolean setProgressToFail() {
        boolean z;
        try {
            if (this.state == 4 || this.currentProgress >= 100) {
                z = false;
            } else {
                this.state = 5;
                this.text = TEXT_FAIL;
                this.currentProgress = 100;
                z = true;
            }
        }
        return z;
    }

    private synchronized boolean setProgressToSuccess() {
        this.state = 4;
        this.text = TEXT_SUCCESS;
        this.currentProgress = 100;
        return true;
    }

    public void writeLog() {
        List logStrList = this.diagnoseStateListener.getLogStrList();
        String trafficLog = TrafficLogHelper.getTrafficLog(this.deviceTrafficStateInfo);
        if (trafficLog != null) {
            logStrList.add(trafficLog);
        }
        UploadManager.writeLog(logStrList, "0.2", 1);
        if (this.state == 3 && setProgressToSuccess()) {
            notifyUpdateProgress();
        }
    }
}
