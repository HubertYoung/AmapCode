package com.alipay.mobile.logging;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import org.json.JSONObject;

public class TinyLoggingConfigManager {
    private static final String REQUEST_URL_HOST = "https://mdap.alipay.com/loggw/tinyapp/queryConfig.do";
    private static final String REQUEST_URL_UPLOAD = "https://mdap.alipay.com/loggw/tinyapp/testLogUpload.do";
    private static final String TAG = "TinyLoggingConfigManager";
    private static TinyLoggingConfigManager sInstance;
    private String TINY_APP_PREFIX = "TinyAppBiz";
    private String request_url_host = REQUEST_URL_HOST;
    private String request_url_upload = REQUEST_URL_UPLOAD;

    public String getRequest_url_host() {
        return this.request_url_host;
    }

    public String getRequest_url_upload() {
        return this.request_url_upload;
    }

    public void setRequest_url_host(String request_url_host2) {
        this.request_url_host = request_url_host2;
    }

    public void setRequest_url_upload(String request_url_upload2) {
        this.request_url_upload = request_url_upload2;
    }

    public static TinyLoggingConfigManager getInstance() {
        if (sInstance == null) {
            synchronized (TinyLoggingConfigManager.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new TinyLoggingConfigManager();
                    }
                }
            }
        }
        return sInstance;
    }

    private TinyLoggingConfigManager() {
    }

    private SharedPreferences getControlConfigSp() {
        return LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext().getSharedPreferences("ControlConfigSP", 4);
    }

    private SharedPreferences getEventConfigSP() {
        return LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext().getSharedPreferences("EventConfigSP", 4);
    }

    public void updateLogConfig(String tinyAppId, String tinyAppVersion) {
        syncRequestLogConfig(tinyAppId, tinyAppVersion);
    }

    public String queryTrackerConfig(String appId) {
        if (!TextUtils.isEmpty(appId)) {
            return new String(Base64.decode(getEventConfigSP().getString(appId, ""), 0));
        }
        LoggerFactory.getTraceLogger().warn((String) TAG, (String) "queryTrackerConfig appId is null");
        return null;
    }

    public boolean shouldTrack(String appId) {
        if (!TextUtils.isEmpty(appId)) {
            return getControlConfigSp().getBoolean(appId, true);
        }
        LoggerFactory.getTraceLogger().warn((String) TAG, (String) "shouldTrack appId is null");
        return false;
    }

    public void triggerUpload(final String appId, final String env) {
        if ("main".equalsIgnoreCase(Thread.currentThread().getName())) {
            H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                public void run() {
                    TinyLoggingConfigManager.this.uploadByAppId(appId, env);
                }
            });
        } else {
            uploadByAppId(appId, env);
        }
    }

    /* access modifiers changed from: private */
    public void uploadByAppId(String appId, String env) {
        if (TextUtils.isEmpty(appId)) {
            LoggerFactory.getTraceLogger().warn((String) TAG, (String) "triggerUpload appId is null");
            return;
        }
        String bizType = this.TINY_APP_PREFIX + "-" + appId;
        if (!TextUtils.isEmpty(env)) {
            LoggerFactory.getLogContext().flush(bizType, true);
            if ("test".equals(env)) {
                LoggerFactory.getLogContext().uploadAfterSync(bizType, this.request_url_upload);
            } else {
                LoggerFactory.getLogContext().uploadAfterSync(bizType);
            }
        } else {
            String configStr = queryTrackerConfig(appId);
            if (TextUtils.isEmpty(configStr)) {
                LoggerFactory.getTraceLogger().info(TAG, "configStr is null upload bizType" + bizType);
                LoggerFactory.getLogContext().flush(bizType, true);
                LoggerFactory.getLogContext().uploadAfterSync(bizType);
                return;
            }
            try {
                String envStr = new JSONObject(configStr).getString("env");
                LoggerFactory.getTraceLogger().info(TAG, "upload by config envStr = " + envStr);
                LoggerFactory.getLogContext().flush(bizType, true);
                if ("test".equals(envStr)) {
                    LoggerFactory.getLogContext().uploadAfterSync(bizType, this.request_url_upload);
                } else {
                    LoggerFactory.getLogContext().uploadAfterSync(bizType);
                }
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error((String) TAG, e);
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void syncRequestLogConfig(java.lang.String r24, java.lang.String r25) {
        /*
            r23 = this;
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r4 = "TinyLoggingConfigManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "syncRequestLogConfig tinyAppId: "
            r5.<init>(r6)
            r0 = r24
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            r3.warn(r4, r5)
            boolean r3 = android.text.TextUtils.isEmpty(r24)     // Catch:{ Throwable -> 0x01ba }
            if (r3 == 0) goto L_0x002c
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r4 = "TinyLoggingConfigManager"
            java.lang.String r5 = "tinyAppId is null!"
            r3.warn(r4, r5)     // Catch:{ Throwable -> 0x01ba }
        L_0x002b:
            return
        L_0x002c:
            java.util.HashMap r18 = new java.util.HashMap     // Catch:{ Throwable -> 0x01ba }
            r18.<init>()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r3 = "productId"
            com.alipay.mobile.common.logging.api.LogContext r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r4 = r4.getProductId()     // Catch:{ Throwable -> 0x01ba }
            r0 = r18
            r0.put(r3, r4)     // Catch:{ Throwable -> 0x01ba }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r4 = "TinyLoggingConfigManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r6 = "productId = "
            r5.<init>(r6)     // Catch:{ Throwable -> 0x01ba }
            com.alipay.mobile.common.logging.api.LogContext r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r6 = r6.getProductId()     // Catch:{ Throwable -> 0x01ba }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x01ba }
            r3.info(r4, r5)     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r3 = "productVersion"
            com.alipay.mobile.common.logging.api.LogContext r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r4 = r4.getProductVersion()     // Catch:{ Throwable -> 0x01ba }
            r0 = r18
            r0.put(r3, r4)     // Catch:{ Throwable -> 0x01ba }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r4 = "TinyLoggingConfigManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r6 = "productVersion = "
            r5.<init>(r6)     // Catch:{ Throwable -> 0x01ba }
            com.alipay.mobile.common.logging.api.LogContext r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r6 = r6.getProductVersion()     // Catch:{ Throwable -> 0x01ba }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x01ba }
            r3.info(r4, r5)     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r3 = "deviceId"
            com.alipay.mobile.common.logging.api.LogContext r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r4 = r4.getDeviceId()     // Catch:{ Throwable -> 0x01ba }
            r0 = r18
            r0.put(r3, r4)     // Catch:{ Throwable -> 0x01ba }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r4 = "TinyLoggingConfigManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r6 = "deviceId = "
            r5.<init>(r6)     // Catch:{ Throwable -> 0x01ba }
            com.alipay.mobile.common.logging.api.LogContext r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r6 = r6.getDeviceId()     // Catch:{ Throwable -> 0x01ba }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x01ba }
            r3.info(r4, r5)     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r3 = "tinyAppId"
            r0 = r18
            r1 = r24
            r0.put(r3, r1)     // Catch:{ Throwable -> 0x01ba }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r4 = "TinyLoggingConfigManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r6 = "tinyAppId = "
            r5.<init>(r6)     // Catch:{ Throwable -> 0x01ba }
            r0 = r24
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x01ba }
            r3.info(r4, r5)     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r3 = "tinyAppVersion"
            r0 = r18
            r1 = r25
            r0.put(r3, r1)     // Catch:{ Throwable -> 0x01ba }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r4 = "TinyLoggingConfigManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r6 = "tinyAppVersion = "
            r5.<init>(r6)     // Catch:{ Throwable -> 0x01ba }
            r0 = r25
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x01ba }
            r3.info(r4, r5)     // Catch:{ Throwable -> 0x01ba }
            java.lang.Class<com.alipay.mobile.nebula.provider.H5LoginProvider> r3 = com.alipay.mobile.nebula.provider.H5LoginProvider.class
            java.lang.String r3 = r3.getName()     // Catch:{ Throwable -> 0x01ba }
            java.lang.Object r14 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r3)     // Catch:{ Throwable -> 0x01ba }
            com.alipay.mobile.nebula.provider.H5LoginProvider r14 = (com.alipay.mobile.nebula.provider.H5LoginProvider) r14     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r22 = ""
            if (r14 == 0) goto L_0x0118
            java.lang.String r22 = r14.getUserId()     // Catch:{ Throwable -> 0x01ba }
        L_0x0118:
            java.lang.String r3 = "userId"
            r0 = r18
            r1 = r22
            r0.put(r3, r1)     // Catch:{ Throwable -> 0x01ba }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r4 = "TinyLoggingConfigManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r6 = "userId = "
            r5.<init>(r6)     // Catch:{ Throwable -> 0x01ba }
            r0 = r22
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x01ba }
            r3.info(r4, r5)     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r3 = "configVersion"
            java.lang.String r4 = r23.getConfigVersion(r24)     // Catch:{ Throwable -> 0x01ba }
            r0 = r18
            r0.put(r3, r4)     // Catch:{ Throwable -> 0x01ba }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r4 = "TinyLoggingConfigManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r6 = "configVersion = "
            r5.<init>(r6)     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r6 = r23.getConfigVersion(r24)     // Catch:{ Throwable -> 0x01ba }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x01ba }
            r3.info(r4, r5)     // Catch:{ Throwable -> 0x01ba }
            r0 = r23
            java.lang.String r0 = r0.request_url_host     // Catch:{ Throwable -> 0x01ba }
            r21 = r0
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r4 = "TinyLoggingConfigManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r6 = "url = "
            r5.<init>(r6)     // Catch:{ Throwable -> 0x01ba }
            r0 = r21
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x01ba }
            r3.info(r4, r5)     // Catch:{ Throwable -> 0x01ba }
            com.alipay.mobile.common.logging.http.HttpClient r15 = new com.alipay.mobile.common.logging.http.HttpClient     // Catch:{ Throwable -> 0x01ba }
            com.alipay.mobile.common.logging.api.LogContext r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()     // Catch:{ Throwable -> 0x01ba }
            android.content.Context r3 = r3.getApplicationContext()     // Catch:{ Throwable -> 0x01ba }
            r0 = r21
            r15.<init>(r0, r3)     // Catch:{ Throwable -> 0x01ba }
            r16 = 0
            java.lang.String r12 = ""
            r0 = r18
            org.apache.http.HttpResponse r16 = r15.synchronousRequestByGET(r0)     // Catch:{ Throwable -> 0x01c8 }
        L_0x019b:
            if (r16 != 0) goto L_0x01ce
            r15.closeStreamForNextExecute()     // Catch:{ Throwable -> 0x01ba }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r4 = "TinyLoggingConfigManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r6 = "syncRequestLogConfig: response is NULL, network error: "
            r5.<init>(r6)     // Catch:{ Throwable -> 0x01ba }
            java.lang.StringBuilder r5 = r5.append(r12)     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x01ba }
            r3.error(r4, r5)     // Catch:{ Throwable -> 0x01ba }
            goto L_0x002b
        L_0x01ba:
            r11 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r4 = "TinyLoggingConfigManager"
            java.lang.String r5 = "syncRequestLogConfig"
            r3.error(r4, r5, r11)
            goto L_0x002b
        L_0x01c8:
            r3 = move-exception
            java.lang.String r12 = r3.toString()     // Catch:{ Throwable -> 0x01ba }
            goto L_0x019b
        L_0x01ce:
            int r19 = r15.getResponseCode()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r20 = r15.getResponseContent()     // Catch:{ Throwable -> 0x01ba }
            r15.closeStreamForNextExecute()     // Catch:{ Throwable -> 0x01ba }
            r3 = 200(0xc8, float:2.8E-43)
            r0 = r19
            if (r0 != r3) goto L_0x01e5
            boolean r3 = android.text.TextUtils.isEmpty(r20)     // Catch:{ Throwable -> 0x01ba }
            if (r3 == 0) goto L_0x0215
        L_0x01e5:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r4 = "TinyLoggingConfigManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r6 = "syncRequestLogConfig: response is none, or responseCode is "
            r5.<init>(r6)     // Catch:{ Throwable -> 0x01ba }
            r0 = r19
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x01ba }
            r3.error(r4, r5)     // Catch:{ Throwable -> 0x01ba }
            com.alipay.mobile.common.logging.api.monitor.MonitorLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getMonitorLogger()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r4 = "LogStrategy"
            java.lang.String r5 = "TinyLoggingConfig"
            java.lang.String r6 = "ResponseCode"
            java.lang.String r7 = java.lang.String.valueOf(r19)     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r8 = "or response is none"
            r9 = 0
            r3.footprint(r4, r5, r6, r7, r8, r9)     // Catch:{ Throwable -> 0x01ba }
            goto L_0x002b
        L_0x0215:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r4 = "TinyLoggingConfigManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r6 = "responseContent = "
            r5.<init>(r6)     // Catch:{ Throwable -> 0x01ba }
            r0 = r20
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x01ba }
            r3.info(r4, r5)     // Catch:{ Throwable -> 0x01ba }
            org.json.JSONObject r10 = new org.json.JSONObject     // Catch:{ JSONException -> 0x024b }
            r0 = r20
            r10.<init>(r0)     // Catch:{ JSONException -> 0x024b }
            java.lang.String r3 = "success"
            boolean r3 = r10.getBoolean(r3)     // Catch:{ JSONException -> 0x024b }
            if (r3 != 0) goto L_0x0257
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ JSONException -> 0x024b }
            java.lang.String r4 = "TinyLoggingConfigManager"
            java.lang.String r5 = "get TinyLoggingConfig success is false"
            r3.warn(r4, r5)     // Catch:{ JSONException -> 0x024b }
            goto L_0x002b
        L_0x024b:
            r11 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x01ba }
            java.lang.String r4 = "TinyLoggingConfigManager"
            r3.error(r4, r11)     // Catch:{ Throwable -> 0x01ba }
            goto L_0x002b
        L_0x0257:
            java.lang.String r3 = "mdapControlConfig"
            boolean r17 = r10.getBoolean(r3)     // Catch:{ JSONException -> 0x024b }
            r0 = r23
            r1 = r24
            r2 = r17
            r0.saveMdapControlConfig(r1, r2)     // Catch:{ JSONException -> 0x024b }
            r13 = 0
            java.lang.String r3 = "eventConfig"
            java.lang.String r13 = r10.getString(r3)     // Catch:{ JSONException -> 0x0286 }
            r0 = r23
            r1 = r24
            r0.saveEventConfig(r1, r13)     // Catch:{ JSONException -> 0x0286 }
        L_0x0274:
            r0 = r23
            r1 = r24
            r2 = r17
            r0.notifyAppEnableChange(r1, r2)     // Catch:{ JSONException -> 0x024b }
            r0 = r23
            r1 = r24
            r0.notifyLogConfigChange(r1, r13)     // Catch:{ JSONException -> 0x024b }
            goto L_0x002b
        L_0x0286:
            r3 = move-exception
            goto L_0x0274
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.logging.TinyLoggingConfigManager.syncRequestLogConfig(java.lang.String, java.lang.String):void");
    }

    private void notifyAppEnableChange(String appId, boolean isEnable) {
        if (TextUtils.isEmpty(appId)) {
            LoggerFactory.getTraceLogger().info(TAG, "notifyAppEnableChange appId is null");
            return;
        }
        com.alibaba.fastjson.JSONObject param = new com.alibaba.fastjson.JSONObject();
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        jsonObject.put((String) "appId", (Object) appId);
        jsonObject.put((String) "enable", (Object) Boolean.valueOf(isEnable));
        param.put((String) "data", (Object) jsonObject);
        sendToWeb(appId, "onTrackerEnableChange", param);
    }

    public void notifyLogConfigChange(String appId, String content) {
        if (TextUtils.isEmpty(content)) {
            LoggerFactory.getTraceLogger().info(TAG, "notifyLogConfigChange content is null");
            return;
        }
        com.alibaba.fastjson.JSONObject param = new com.alibaba.fastjson.JSONObject();
        param.put((String) "data", (Object) com.alibaba.fastjson.JSONObject.parseObject(content));
        sendToWeb(appId, "onTrackerConfigChange", param);
    }

    private void sendToWeb(String appId, String action, com.alibaba.fastjson.JSONObject param) {
        H5Service h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5Service.class.getName());
        if (h5Service == null) {
            LoggerFactory.getTraceLogger().info(TAG, "sendToWeb h5Service is null");
        } else if (H5Utils.isInTinyProcess()) {
            H5Page h5Page = h5Service.getTopH5PageForTiny();
            if (h5Page != null) {
                h5Page.getBridge().sendToWeb(action, param, null);
            } else {
                LoggerFactory.getTraceLogger().info(TAG, "notifyLogConfigChange h5Page is null");
            }
        } else {
            h5Service.sendToWebFromMainProcess(appId, action, param);
        }
    }

    private void saveMdapControlConfig(String appId, boolean isWrite) {
        getControlConfigSp().edit().putBoolean(appId, isWrite).apply();
    }

    public void saveEventConfig(String appId, String eventConfig) {
        if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(eventConfig)) {
            LoggerFactory.getTraceLogger().warn((String) TAG, (String) "saveEventConfig eventConfig is null");
            return;
        }
        try {
            if (!new JSONObject(eventConfig).getBoolean("newFlag")) {
                LoggerFactory.getTraceLogger().warn((String) TAG, (String) "isNew is false");
                return;
            }
            getEventConfigSP().edit().putString(appId, Base64.encodeToString(eventConfig.getBytes(), 0)).apply();
            LoggerFactory.getTraceLogger().warn((String) TAG, (String) "isNew is true, update config");
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) TAG, e);
        }
    }

    public String getConfigVersion(String tinyAppId) {
        String configStr = queryTrackerConfig(tinyAppId);
        if (TextUtils.isEmpty(configStr)) {
            return "-";
        }
        try {
            return new JSONObject(configStr).getString("configVersion");
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) TAG, e);
            return "-";
        }
    }
}
