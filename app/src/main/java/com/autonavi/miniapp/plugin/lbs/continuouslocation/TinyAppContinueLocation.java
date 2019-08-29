package com.autonavi.miniapp.plugin.lbs.continuouslocation;

import android.location.Location;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5plugin.H5LocationPlugin;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import com.alipay.mobile.tinyappcommon.storage.H5SharedPreferenceStorage;
import com.autonavi.miniapp.plugin.lbs.LBSCommonUtil;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.Timer;
import java.util.TimerTask;

public class TinyAppContinueLocation {
    private static final String CONTINUOUS_LOCATION = "continuousLocation";
    private static final long DEFAULT_INTERVAL = 2000;
    private static final long MIN_INTERVAL = 1000;
    public static final String TAG = "TinyAppContinueLocation";
    private String appId;
    /* access modifiers changed from: private */
    public H5BridgeContext bridgeContext;
    private ContinuousLocationCallBack mCallback;
    private ContinuousTimerTask mTask;
    private Timer mTimer = new Timer();

    public interface ContinuousLocationCallBack {
        void unRegisterReceiver();
    }

    class ContinuousTimerTask extends TimerTask {
        ContinuousTimerTask() {
        }

        public void run() {
            Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put((String) "latitude", (Object) Double.valueOf(LBSCommonUtil.formatDouble(latestLocation.getLatitude(), 7)));
            jSONObject.put((String) "longitude", (Object) Double.valueOf(LBSCommonUtil.formatDouble(latestLocation.getLongitude(), 7)));
            jSONObject.put((String) "accurary", (Object) Integer.valueOf(Math.round(latestLocation.getAccuracy())));
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put((String) "data", (Object) jSONObject);
            jSONObject2.put((String) "success", (Object) Boolean.TRUE);
            TinyAppContinueLocation.this.bridgeContext.sendToWeb(TinyAppContinueLocation.CONTINUOUS_LOCATION, jSONObject2, null);
            StringBuilder sb = new StringBuilder("location result: ");
            sb.append(jSONObject2.toJSONString());
            H5Log.d(TinyAppContinueLocation.TAG, sb.toString());
            if (!LBSCommonUtil.hasLocationPermission() || !TinyAppContinueLocation.this.checkMiniAppPermission()) {
                H5Log.w(TinyAppContinueLocation.TAG, "location permission is closed, cancel ContinuousTimerTask.");
                TinyAppContinueLocation.this.stopContinuousLocation();
                TinyAppContinueLocation.this.unregisterReceiver();
            }
        }
    }

    public void setCallBack(ContinuousLocationCallBack continuousLocationCallBack) {
        this.mCallback = continuousLocationCallBack;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Can't wrap try/catch for region: R(2:32|33) */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0048, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger().info(TAG, "parse interval error, interval=".concat(java.lang.String.valueOf(r11)));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00e0, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:32:0x00a0 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void startContinuousLocation(com.alipay.mobile.h5container.api.H5Event r11, com.alipay.mobile.h5container.api.H5BridgeContext r12) {
        /*
            r10 = this;
            monitor-enter(r10)
            java.lang.String r0 = "TinyAppContinueLocation"
            java.lang.String r1 = "startContinuousLocation"
            com.alipay.mobile.nebula.util.H5Log.d(r0, r1)     // Catch:{ all -> 0x00e1 }
            if (r11 != 0) goto L_0x0018
            com.alipay.mobile.common.logging.api.trace.TraceLogger r11 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x00e1 }
            java.lang.String r12 = "TinyAppContinueLocation"
            java.lang.String r0 = "event is null"
            r11.info(r12, r0)     // Catch:{ all -> 0x00e1 }
            monitor-exit(r10)
            return
        L_0x0018:
            r10.bridgeContext = r12     // Catch:{ all -> 0x00e1 }
            boolean r0 = com.autonavi.miniapp.plugin.lbs.LBSCommonUtil.hasLocationPermission()     // Catch:{ all -> 0x00e1 }
            if (r0 != 0) goto L_0x0049
            java.lang.String r11 = "TinyAppContinueLocation"
            java.lang.String r0 = "is background running or have no locate permission."
            com.alipay.mobile.nebula.util.H5Log.w(r11, r0)     // Catch:{ all -> 0x00e1 }
            com.alibaba.fastjson.JSONObject r11 = new com.alibaba.fastjson.JSONObject     // Catch:{ all -> 0x00e1 }
            r11.<init>()     // Catch:{ all -> 0x00e1 }
            java.lang.String r0 = "error"
            r1 = 11
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ all -> 0x00e1 }
            r11.put(r0, r1)     // Catch:{ all -> 0x00e1 }
            java.lang.String r0 = "errorMessage"
            java.lang.String r1 = "请确认定位相关权限已开启"
            r11.put(r0, r1)     // Catch:{ all -> 0x00e1 }
            if (r12 == 0) goto L_0x0047
            r12.sendBridgeResult(r11)     // Catch:{ all -> 0x00e1 }
            r10.unregisterReceiver()     // Catch:{ all -> 0x00e1 }
        L_0x0047:
            monitor-exit(r10)
            return
        L_0x0049:
            com.alibaba.fastjson.JSONObject r0 = r11.getParam()     // Catch:{ all -> 0x00e1 }
            java.lang.String r1 = "bizType"
            r2 = 0
            java.lang.String r0 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r1, r2)     // Catch:{ all -> 0x00e1 }
            r10.appId = r0     // Catch:{ all -> 0x00e1 }
            java.lang.String r0 = r10.appId     // Catch:{ all -> 0x00e1 }
            boolean r0 = r10.checkBlackList(r0)     // Catch:{ all -> 0x00e1 }
            if (r0 == 0) goto L_0x0060
            monitor-exit(r10)
            return
        L_0x0060:
            com.alibaba.fastjson.JSONObject r11 = r11.getParam()     // Catch:{ all -> 0x00e1 }
            java.lang.String r0 = "callbackInterval"
            java.lang.String r11 = r11.getString(r0)     // Catch:{ all -> 0x00e1 }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r0 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x00e1 }
            java.lang.String r1 = "TinyAppContinueLocation"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00e1 }
            java.lang.String r3 = "interval="
            r2.<init>(r3)     // Catch:{ all -> 0x00e1 }
            r2.append(r11)     // Catch:{ all -> 0x00e1 }
            java.lang.String r3 = ",bizType="
            r2.append(r3)     // Catch:{ all -> 0x00e1 }
            java.lang.String r3 = r10.appId     // Catch:{ all -> 0x00e1 }
            r2.append(r3)     // Catch:{ all -> 0x00e1 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00e1 }
            r0.info(r1, r2)     // Catch:{ all -> 0x00e1 }
            r0 = 2000(0x7d0, double:9.88E-321)
            boolean r2 = android.text.TextUtils.isEmpty(r11)     // Catch:{ Throwable -> 0x00a0 }
            if (r2 != 0) goto L_0x009e
            long r2 = java.lang.Long.parseLong(r11)     // Catch:{ Throwable -> 0x00a0 }
            r0 = 1000(0x3e8, double:4.94E-321)
            int r11 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r11 < 0) goto L_0x009e
            r0 = r2
        L_0x009e:
            r8 = r0
            goto L_0x00b4
        L_0x00a0:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x00e1 }
            java.lang.String r3 = "TinyAppContinueLocation"
            java.lang.String r4 = "parse interval error, interval="
            java.lang.String r11 = java.lang.String.valueOf(r11)     // Catch:{ all -> 0x00e1 }
            java.lang.String r11 = r4.concat(r11)     // Catch:{ all -> 0x00e1 }
            r2.info(r3, r11)     // Catch:{ all -> 0x00e1 }
            goto L_0x009e
        L_0x00b4:
            com.autonavi.miniapp.plugin.lbs.continuouslocation.TinyAppContinueLocation$ContinuousTimerTask r11 = r10.mTask     // Catch:{ all -> 0x00e1 }
            if (r11 == 0) goto L_0x00bd
            com.autonavi.miniapp.plugin.lbs.continuouslocation.TinyAppContinueLocation$ContinuousTimerTask r11 = r10.mTask     // Catch:{ all -> 0x00e1 }
            r11.cancel()     // Catch:{ all -> 0x00e1 }
        L_0x00bd:
            com.autonavi.miniapp.plugin.lbs.continuouslocation.TinyAppContinueLocation$ContinuousTimerTask r11 = new com.autonavi.miniapp.plugin.lbs.continuouslocation.TinyAppContinueLocation$ContinuousTimerTask     // Catch:{ all -> 0x00e1 }
            r11.<init>()     // Catch:{ all -> 0x00e1 }
            r10.mTask = r11     // Catch:{ all -> 0x00e1 }
            java.util.Timer r4 = r10.mTimer     // Catch:{ all -> 0x00e1 }
            com.autonavi.miniapp.plugin.lbs.continuouslocation.TinyAppContinueLocation$ContinuousTimerTask r5 = r10.mTask     // Catch:{ all -> 0x00e1 }
            r6 = 0
            r4.schedule(r5, r6, r8)     // Catch:{ all -> 0x00e1 }
            com.alibaba.fastjson.JSONObject r11 = new com.alibaba.fastjson.JSONObject     // Catch:{ all -> 0x00e1 }
            r11.<init>()     // Catch:{ all -> 0x00e1 }
            java.lang.String r0 = "success"
            java.lang.Boolean r1 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x00e1 }
            r11.put(r0, r1)     // Catch:{ all -> 0x00e1 }
            if (r12 == 0) goto L_0x00df
            r12.sendBridgeResult(r11)     // Catch:{ all -> 0x00e1 }
        L_0x00df:
            monitor-exit(r10)
            return
        L_0x00e1:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.plugin.lbs.continuouslocation.TinyAppContinueLocation.startContinuousLocation(com.alipay.mobile.h5container.api.H5Event, com.alipay.mobile.h5container.api.H5BridgeContext):void");
    }

    public boolean checkMiniAppPermission() {
        return "1".equals(H5SharedPreferenceStorage.getInstance().getString(buildPermissionKey(this.appId)));
    }

    private String buildPermissionKey(String str) {
        String userId = TinyAppService.get().getUserId();
        StringBuilder sb = new StringBuilder();
        sb.append(userId);
        sb.append("_");
        sb.append(str);
        sb.append("_location");
        return sb.toString();
    }

    private boolean checkBlackList(String str) {
        if (TextUtils.isEmpty(str)) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put((String) "error", (Object) Integer.valueOf(2));
            jSONObject.put((String) "errorMessage", (Object) "缺少业务场景参数");
            if (this.bridgeContext != null) {
                this.bridgeContext.sendBridgeResult(jSONObject);
            }
            unregisterReceiver();
            return true;
        }
        boolean z = false;
        try {
            ConfigService configService = (ConfigService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(ConfigService.class.getName());
            if (configService != null) {
                String config = configService.getConfig("tinyAppContinuousBlackList");
                LoggerFactory.getTraceLogger().error((String) TAG, "tinyAppContinuousBlackList=".concat(String.valueOf(config)));
                if (!TextUtils.isEmpty(config)) {
                    String[] split = config.split(MergeUtil.SEPARATOR_KV);
                    int length = split.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        } else if (str.equals(split[i])) {
                            z = true;
                            break;
                        } else {
                            i++;
                        }
                    }
                }
            }
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error((String) TAG, th);
        }
        if (z) {
            TraceLogger traceLogger = LoggerFactory.getTraceLogger();
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" in blackList!");
            traceLogger.error((String) TAG, sb.toString());
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put((String) "error", (Object) Integer.valueOf(20));
            jSONObject2.put((String) "errorMessage", (Object) "定位失败");
            if (this.bridgeContext != null) {
                this.bridgeContext.sendBridgeResult(jSONObject2);
            }
            unregisterReceiver();
        }
        return z;
    }

    public synchronized void stopContinuousLocation() {
        H5Log.d(TAG, H5LocationPlugin.STOP_CONTINUOUS_LOCATION);
        try {
            if (this.mTask != null) {
                H5Log.d(TAG, "cancle task.");
                this.mTask.cancel();
            }
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error((String) TAG, th);
        }
    }

    /* access modifiers changed from: private */
    public void unregisterReceiver() {
        if (this.mCallback != null) {
            this.mCallback.unRegisterReceiver();
        }
    }
}
