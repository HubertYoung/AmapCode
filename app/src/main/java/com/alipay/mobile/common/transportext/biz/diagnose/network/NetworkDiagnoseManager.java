package com.alipay.mobile.common.transportext.biz.diagnose.network;

import android.text.TextUtils;
import com.alipay.mobile.common.amnet.api.AmnetNetworkDiagnoseListener;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration.DetectInf;
import com.alipay.mobile.common.transportext.biz.diagnose.network.Traceroute.PingInf;
import java.util.HashMap;
import java.util.Map;

public class NetworkDiagnoseManager {
    private static final String TAG = "DIAGNOSE-MANAGER";
    /* access modifiers changed from: private */
    public static Map<String, String> map = new HashMap();
    /* access modifiers changed from: private */
    public AmnetNetworkDiagnoseListener callback = null;
    /* access modifiers changed from: private */
    public DetectInf[] detectInfs = null;
    private DiagnoseStateManager diagnoseStateManager = null;
    /* access modifiers changed from: private */
    public long diagnoseTime = 0;
    /* access modifiers changed from: private */
    public int diagnoseType = 0;
    private PingInf[] pingInfs = null;
    /* access modifiers changed from: private */
    public ResultCount resultCount = null;

    class DiagnoseStateManagerImpl implements DiagnoseStateManager {
        private DiagnoseStateManagerImpl() {
        }

        public void report(boolean fin, boolean ok, boolean done, String summary) {
            if (1 == NetworkDiagnoseManager.this.diagnoseType) {
                String time = (String) NetworkDiagnoseManager.map.get(String.valueOf(NetworkDiagnoseManager.this.diagnoseType));
                if (TextUtils.isEmpty(time) || !time.equals(String.valueOf(NetworkDiagnoseManager.this.diagnoseTime))) {
                    LogCatUtil.warn((String) NetworkDiagnoseManager.TAG, (String) "not the same diagnose, ignose result.");
                    return;
                }
            }
            if (NetworkDiagnoseManager.this.callback != null) {
                NetworkDiagnoseManager.this.callback.report(fin, ok, done, summary);
            }
            if (NetworkDiagnoseManager.this.resultCount != null) {
                NetworkDiagnoseManager.this.resultCount.addCount();
            }
        }

        public void notify(String info) {
        }
    }

    public interface ResultCount {
        void addCount();

        void addTotal();
    }

    class ResultCountImpl implements ResultCount {
        private int linkNum;
        private int linkTotal;

        private ResultCountImpl() {
            this.linkTotal = 0;
            this.linkNum = 0;
        }

        public void addTotal() {
            synchronized (this) {
                this.linkTotal++;
            }
        }

        public void addCount() {
            synchronized (this) {
                this.linkNum++;
                if (this.linkNum >= this.linkTotal && NetworkDiagnoseManager.this.callback != null) {
                    NetworkDiagnoseManager.this.callback.report(true, false, true, "");
                }
            }
        }
    }

    public NetworkDiagnoseManager(DetectInf[] detectInfs2, PingInf[] pingInfs2) {
        this.detectInfs = detectInfs2;
        this.pingInfs = pingInfs2;
    }

    public void register(AmnetNetworkDiagnoseListener callback2) {
        this.callback = callback2;
    }

    public void register(int diagnoseType2, long diagnoseTime2) {
        this.diagnoseType = diagnoseType2;
        this.diagnoseTime = diagnoseTime2;
        map.put(String.valueOf(diagnoseType2), String.valueOf(diagnoseTime2));
    }

    public void start() {
        this.resultCount = new ResultCountImpl();
        this.diagnoseStateManager = new DiagnoseStateManagerImpl();
        if ((this.detectInfs == null || this.detectInfs.length <= 0) && (this.pingInfs == null || this.pingInfs.length <= 0)) {
            if (this.callback != null) {
                this.callback.report(true, false, true, "");
            }
            LogCatUtil.warn((String) TAG, (String) "all input is null");
            return;
        }
        diagnoseByLink();
        diagnoseByPing();
    }

    private void diagnoseByLink() {
        if (this.detectInfs == null || this.detectInfs.length <= 0) {
            LogCatUtil.warn((String) TAG, (String) "detectInfs is null.");
            return;
        }
        int linkNum = this.detectInfs.length;
        for (int i = 0; i < linkNum; i++) {
            if (this.detectInfs[i] != null) {
                this.resultCount.addTotal();
                final SpeedTestManager speedTestManager = new SpeedTestManager();
                speedTestManager.register(this.resultCount);
                speedTestManager.register(this.diagnoseStateManager);
                final int num = i;
                NetworkAsyncTaskExecutor.executeLazy(new Runnable() {
                    public void run() {
                        speedTestManager.diagnose(NetworkDiagnoseManager.this.detectInfs[num]);
                    }
                });
            }
        }
    }

    private void diagnoseByPing() {
        if (this.pingInfs == null || this.pingInfs.length <= 0) {
            LogCatUtil.warn((String) TAG, (String) "pingInfs is null.");
            return;
        }
        int pingNum = this.pingInfs.length;
        for (int i = 0; i < pingNum; i++) {
            if (this.pingInfs[i] != null) {
                this.resultCount.addTotal();
                final Traceroute traceroute = new Traceroute(this.pingInfs[i]);
                traceroute.register(this.diagnoseStateManager);
                NetworkAsyncTaskExecutor.executeLazy(new Runnable() {
                    public void run() {
                        try {
                            traceroute.start();
                        } catch (Throwable e) {
                            LogCatUtil.warn((String) NetworkDiagnoseManager.TAG, "traceroute start error. " + e.toString());
                        }
                    }
                });
            }
        }
    }
}
