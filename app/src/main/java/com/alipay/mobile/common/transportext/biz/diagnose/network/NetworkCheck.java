package com.alipay.mobile.common.transportext.biz.diagnose.network;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration.DetectInf;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportEnv;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.util.ArrayList;
import java.util.List;

public class NetworkCheck {
    private static final String ADDRESS = "www.taobao.com";
    static final int STATE_IDLE = 1;
    static final int STATE_RUNNING = 2;
    private static final String TAG = "DIAGNOSE-NETWORKCHECK";
    static int currentState = 1;
    private static boolean fakeWifi = false;
    private static boolean firstTime = true;
    private static int netType = 0;
    private static boolean networkAvailable = true;
    private int errCode = 10;
    private boolean isErrRsp = false;
    private boolean isRedirect = false;
    private boolean isRpcNotify = false;

    public static boolean isFakeWifi() {
        if (firstTime) {
            LogCatUtil.info(TAG, "first time to call isFakeWifi");
            networkStateNotify(false);
        }
        return fakeWifi;
    }

    public static boolean isNetworkAvailable() {
        if (firstTime) {
            LogCatUtil.info(TAG, "first time to call isNetworkAvailable");
            networkStateNotify(false);
        }
        return networkAvailable;
    }

    public static int getNetType() {
        Context context = ExtTransportEnv.getAppContext();
        if (context == null) {
            return 0;
        }
        int networkType = NetworkUtils.getNetworkType(context);
        netType = networkType;
        return networkType;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void networkStateNotify(boolean r6) {
        /*
            r5 = 1
            java.lang.Class<com.alipay.mobile.common.transportext.biz.diagnose.network.NetworkCheck> r2 = com.alipay.mobile.common.transportext.biz.diagnose.network.NetworkCheck.class
            monitor-enter(r2)
            java.lang.String r1 = "DIAGNOSE-NETWORKCHECK"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0065 }
            java.lang.String r4 = "networkStateNotify. begin to check network by Link. old:"
            r3.<init>(r4)     // Catch:{ all -> 0x0065 }
            boolean r4 = networkAvailable     // Catch:{ all -> 0x0065 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0065 }
            java.lang.String r4 = ". new:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0065 }
            java.lang.StringBuilder r3 = r3.append(r6)     // Catch:{ all -> 0x0065 }
            java.lang.String r4 = ". first:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0065 }
            boolean r4 = firstTime     // Catch:{ all -> 0x0065 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0065 }
            java.lang.String r4 = ". netType:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0065 }
            int r4 = netType     // Catch:{ all -> 0x0065 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0065 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0065 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r1, r3)     // Catch:{ all -> 0x0065 }
            boolean r1 = firstTime     // Catch:{ all -> 0x0065 }
            if (r1 != 0) goto L_0x004a
            boolean r1 = networkAvailable     // Catch:{ all -> 0x0065 }
            if (r1 == r6) goto L_0x0048
            int r1 = currentState     // Catch:{ all -> 0x0065 }
            if (r1 == r5) goto L_0x004a
        L_0x0048:
            monitor-exit(r2)     // Catch:{ all -> 0x0065 }
        L_0x0049:
            return
        L_0x004a:
            r1 = 0
            firstTime = r1     // Catch:{ all -> 0x0065 }
            if (r6 == 0) goto L_0x0051
            networkAvailable = r6     // Catch:{ all -> 0x0065 }
        L_0x0051:
            r1 = 2
            currentState = r1     // Catch:{ all -> 0x0065 }
            monitor-exit(r2)     // Catch:{ all -> 0x0065 }
            com.alipay.mobile.common.transportext.biz.diagnose.network.NetworkCheck r0 = new com.alipay.mobile.common.transportext.biz.diagnose.network.NetworkCheck
            r0.<init>()
            r0.isRpcNotify = r5
            com.alipay.mobile.common.transportext.biz.diagnose.network.NetworkCheck$1 r1 = new com.alipay.mobile.common.transportext.biz.diagnose.network.NetworkCheck$1
            r1.<init>()
            com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor.executeLazy(r1)
            goto L_0x0049
        L_0x0065:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0065 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.diagnose.network.NetworkCheck.networkStateNotify(boolean):void");
    }

    public static void initNetworkCheck(Context context) {
        LogCatUtil.info(TAG, "[initNetworkCheck] begin.");
        if (!NetworkConnectListener.hasInstance()) {
            try {
                NetworkConnectListener.instance(context).register();
                netType = getNetType();
            } catch (Throwable e) {
                LogCatUtil.warn((String) TAG, "[initNetworkCheck] " + e);
            }
        }
    }

    public void checkNetwork() {
        SpeedTestManager.firstTime = false;
        firstTime = false;
        try {
            int netType2 = getNetType();
            netType = netType2;
            if (netType2 == 0) {
                SpeedTestManager.netErrCode = -2;
                networkAvailable = false;
                fakeWifi = false;
                LogCatUtil.warn((String) TAG, (String) "It is no net now.");
                return;
            }
            DetectInf detectInf = new DetectInf();
            detectInf.domain = ADDRESS;
            detectInf.protocol = 0;
            detectInf.request = "HEAD / HTTP/1.1\r\nHost: www.taobao.com\r\nContent-Length: 0\r\n\r\n";
            detectInf.response = "HTTP/1.1 200 ";
            detectInf.waiting = 30;
            detectInf.trying = 1;
            SpeedTestManager speedTestManager = new SpeedTestManager();
            DftNetTest callback = new DftNetTest();
            speedTestManager.register((DiagnoseStateManager) callback);
            speedTestManager.diagnose(detectInf);
            String result = callback.getReport();
            if (result == null) {
                SpeedTestManager.netErrCode = 10;
                networkAvailable = false;
                fakeWifi = false;
                LogCatUtil.warn((String) TAG, (String) "network change. network is unavailable. the diagnose result is null.");
                return;
            }
            List list = SpeedTestManager.convertLinkData(result);
            if (list == null || list.size() == 0) {
                SpeedTestManager.netErrCode = 10;
                networkAvailable = false;
                fakeWifi = false;
                LogCatUtil.warn((String) TAG, (String) "network change. network is unavailable. the return list is null.");
                return;
            }
            boolean ok = false;
            for (SpeedTestLinkData speedTestLinkData : list) {
                if (speedTestLinkData.result == null || !speedTestLinkData.result.equals(DictionaryKeys.CTRLXY_Y)) {
                    this.errCode = speedTestLinkData.errCode;
                    if (speedTestLinkData.describe != null && speedTestLinkData.describe.contains("302 redirect")) {
                        this.isRedirect = true;
                    } else if (2 == speedTestLinkData.errCode || 3 == speedTestLinkData.errCode) {
                        this.isErrRsp = true;
                    }
                } else {
                    ok = true;
                }
            }
            if (ok) {
                SpeedTestManager.netErrCode = 0;
                networkAvailable = true;
                fakeWifi = false;
            } else {
                if (3 != netType || (!this.isRedirect && !this.isErrRsp)) {
                    SpeedTestManager.netErrCode = this.errCode;
                    fakeWifi = false;
                } else {
                    SpeedTestManager.netErrCode = -1;
                    fakeWifi = true;
                }
                networkAvailable = false;
            }
            LogCatUtil.info(TAG, "network change and the errCode is " + SpeedTestManager.netErrCode);
            upLoadResult(result);
        } catch (Throwable e) {
            LogCatUtil.warn((String) TAG, "[checkNetwork] " + e);
        }
    }

    private void upLoadResult(String result) {
        if (this.isRpcNotify && !TextUtils.isEmpty(result)) {
            List logStrList = new ArrayList(1);
            logStrList.add("out_diago:" + result);
            UploadManager.writeLog(logStrList, "0.4", 2);
        }
    }
}
