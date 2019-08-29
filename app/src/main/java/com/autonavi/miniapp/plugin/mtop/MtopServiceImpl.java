package com.autonavi.miniapp.plugin.mtop;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Pair;
import anet.channel.entity.ENV;
import anet.channel.strategy.ConnProtocol;
import anetwork.channel.http.NetworkSdkSetting;
import com.alibaba.fastjson.JSON;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.staticdatastore.IStaticDataStoreComponent;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.aliautologin.AliAutoLoginModel;
import com.alipay.android.phone.inside.api.model.aliautologin.SourceTypeEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.aliautologin.AliAutoLoginCode;
import com.alipay.android.phone.inside.service.InsideOperationService;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import com.alipay.mobile.securitycommon.aliauth.AliAuthResult;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.miniapp.plugin.mtop.MtopResponseWrapper.Status;
import com.taobao.tao.remotebusiness.IRemoteBaseListener;
import com.taobao.tao.remotebusiness.js.MtopJSBridge;
import com.taobao.tao.remotebusiness.login.RemoteLogin;
import java.util.Map;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.mtop.domain.BaseOutDo;
import mtopsdk.mtop.domain.EnvModeEnum;
import mtopsdk.mtop.domain.MethodEnum;
import mtopsdk.mtop.domain.MtopRequest;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.domain.ProtocolEnum;
import mtopsdk.mtop.intf.Mtop;
import org.json.JSONObject;

public class MtopServiceImpl extends MtopService {
    public static final String APP_ID = "2013081300000797";
    private static final String CONFIG_KEY_FALLBACK_TO_CACHE_SESSION = "mtop_fallback_to_cache_session";
    private static final String CONFIG_KEY_POST_MAIN_LOOPER = "mtop_post_main_looper";
    private static final String DAILY_INDEX = "mtop_appkey_daily_index";
    private static final String ONLINE_INDEX = "mtop_appkey_online_index";
    private static final String TAG = "MtopServiceImpl";
    private boolean fallbackToSessionCache = false;
    private boolean hasSession = false;
    private boolean isDebug = false;
    /* access modifiers changed from: private */
    public ENV mEnvMode;
    private Handler mMainHandler;
    private SsoLoginInfo mSsoLoginInfo;
    /* access modifiers changed from: private */
    public boolean useAlipaySession = true;

    final class MtopFinishListenerWrapper<OutputDO extends BaseOutDo> implements b {
        ffg builder;
        MtopAsyncRequestCallback<OutputDO> delegateCallback;
        Class<OutputDO> outputDoClass;

        public MtopFinishListenerWrapper(MtopAsyncRequestCallback<OutputDO> mtopAsyncRequestCallback, Class<OutputDO> cls, ffg ffg) {
            this.delegateCallback = mtopAsyncRequestCallback;
            this.outputDoClass = cls;
            this.builder = ffg;
        }

        public final void onFinished(feu feu, Object obj) {
            MtopResponse mtopResponse = feu.a;
            try {
                if (mtopResponse.isSessionInvalid() && MtopServiceImpl.this.useAlipaySession) {
                    MtopServiceImpl.this.ssoLoginToRegisterSessionInfo(null, true);
                    this.builder.asyncRequest();
                    return;
                }
            } catch (Exception e) {
                LoggerFactory.getTraceLogger().warn((String) MtopServiceImpl.TAG, (Throwable) e);
            }
            this.delegateCallback.onFinished(MtopServiceImpl.handleMtopResponse(mtopResponse, this.outputDoClass));
        }
    }

    class SsoLoginTask implements Runnable {
        final RegisterSessionListener listener;

        SsoLoginTask(RegisterSessionListener registerSessionListener) {
            this.listener = registerSessionListener;
        }

        public void run() {
            MtopServiceImpl.this.ssoLoginToRegisterSessionInfo(this.listener);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }

    public MtopServiceImpl() {
        int i;
        int i2 = 0;
        int i3 = 2;
        try {
            Application applicationContext = LauncherApplicationAgent.getInstance().getApplicationContext();
            ApplicationInfo applicationInfo = applicationContext.getPackageManager().getApplicationInfo(applicationContext.getPackageName(), 128);
            if (!(applicationInfo == null || applicationInfo.metaData == null)) {
                if (applicationInfo.metaData.containsKey(ONLINE_INDEX)) {
                    i = applicationInfo.metaData.getInt(ONLINE_INDEX);
                    LoggerFactory.getTraceLogger().info(TAG, "MtopInit: mtop_appkey_online_index = ".concat(String.valueOf(i)));
                } else {
                    i = 0;
                }
                if (applicationInfo.metaData.containsKey(DAILY_INDEX)) {
                    int i4 = applicationInfo.metaData.getInt(DAILY_INDEX);
                    LoggerFactory.getTraceLogger().info(TAG, "MtopInit: mtop_appkey_daily_index = ".concat(String.valueOf(i4)));
                    i3 = i4;
                }
                i2 = i;
            }
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error((String) TAG, th);
        }
        TraceLogger traceLogger = LoggerFactory.getTraceLogger();
        StringBuilder sb = new StringBuilder("MtopInit: onlineIndex = ");
        sb.append(i2);
        sb.append(", dailyIndex = ");
        sb.append(i3);
        traceLogger.info(TAG, sb.toString());
        MtopInit(getApplicationContext(), i2, i3);
    }

    private boolean isDebug(Context context) {
        try {
            if ((context.getPackageManager().getApplicationInfo(context.getPackageName(), 16384).flags & 2) != 0) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().warn((String) TAG, th);
            return false;
        }
    }

    public void MtopInit(Context context, int i, int i2) {
        this.isDebug = isDebug(context);
        if (this.isDebug) {
            TBSdkLog.a();
            TBSdkLog.a(true);
            cl.a();
        }
        Mtop.a(i, i2);
        LogContext logContext = LoggerFactory.getLogContext();
        StringBuilder sb = new StringBuilder();
        sb.append(NetworkParam.getDic());
        sb.append("@amap_android_");
        sb.append(logContext.getProductVersion());
        String sb2 = sb.toString();
        NetworkSdkSetting.setTtid(sb2);
        Mtop a = Mtop.a(context, sb2);
        RemoteLogin.setLoginImpl(a, new RemoteLoginImpl());
        a.a(sb2);
        TBSdkLog.a(this.isDebug);
        a.a(EnvModeEnum.ONLINE);
        this.mEnvMode = ENV.ONLINE;
        ConfigService configService = (ConfigService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(ConfigService.class.getName());
        if (configService != null) {
            if ("true".equals(configService.getConfig(CONFIG_KEY_POST_MAIN_LOOPER))) {
                this.mMainHandler = new Handler(Looper.getMainLooper());
            }
            if ("true".equals(configService.getConfig(CONFIG_KEY_FALLBACK_TO_CACHE_SESSION))) {
                this.fallbackToSessionCache = true;
            }
            TraceLogger traceLogger = LoggerFactory.getTraceLogger();
            StringBuilder sb3 = new StringBuilder("fallback to session cache : ");
            sb3.append(this.fallbackToSessionCache);
            traceLogger.debug(TAG, sb3.toString());
        }
    }

    public void switchEnvMode(EnvModeEnum envModeEnum) {
        Mtop.a((Context) getApplicationContext()).a(envModeEnum);
    }

    public synchronized JSONObject syncRequest(String str, String str2, boolean z, boolean z2, String str3, String str4, String str5) {
        try {
        } catch (Throwable th) {
            throw th;
        }
        return syncRequest(str, str2, z, z2, true, true, str3, str4, str5, null, MethodEnum.GET);
    }

    public synchronized JSONObject syncRequest(String str, String str2, boolean z, boolean z2, String str3, String str4, String str5, MethodEnum methodEnum) {
        return syncRequest(str, str2, z, z2, true, true, str3, str4, str5, null, methodEnum);
    }

    public synchronized JSONObject syncRequest(String str, String str2, boolean z, boolean z2, String str3, String str4, String str5, String str6, MethodEnum methodEnum) {
        return syncRequest(str, str2, z, z2, true, true, str3, str4, str5, str6, methodEnum);
    }

    public synchronized JSONObject syncRequest(String str, String str2, boolean z, boolean z2, boolean z3, boolean z4, String str3, String str4, String str5, String str6, MethodEnum methodEnum) {
        return syncRequest(str, str2, z, z2, true, true, str3, str4, null, str5, str6, methodEnum);
    }

    public JSONObject syncRequest(String str, String str2, boolean z, boolean z2, boolean z3, boolean z4, String str3, String str4, com.alibaba.fastjson.JSONObject jSONObject, String str5, String str6, MethodEnum methodEnum) {
        MtopResponse innerSyncRequest = innerSyncRequest(str, str2, z, z2, z3, z4, str3, str4, jSONObject, str5, str6, null, null, methodEnum);
        if (innerSyncRequest != null) {
            try {
                String str7 = new String(innerSyncRequest.getBytedata());
                LoggerFactory.getTraceLogger().warn((String) TAG, "syncRequest.response=".concat(String.valueOf(str7)));
                return new JSONObject(str7);
            } catch (Throwable th) {
                LoggerFactory.getTraceLogger().warn((String) TAG, th);
            }
        }
        return null;
    }

    public Pair<Handler, com.alibaba.fastjson.JSONObject> syncRequestFastJson(String str, String str2, boolean z, boolean z2, boolean z3, boolean z4, String str3, String str4, com.alibaba.fastjson.JSONObject jSONObject, String str5, String str6, String str7, String str8, MethodEnum methodEnum) {
        com.alibaba.fastjson.JSONObject jSONObject2;
        MtopResponse innerSyncRequest = innerSyncRequest(str, str2, z, z2, z3, z4, str3, str4, jSONObject, str5, str6, str7, str8, methodEnum);
        if (innerSyncRequest == null) {
            return null;
        }
        String str9 = new String(innerSyncRequest.getBytedata());
        LoggerFactory.getTraceLogger().warn((String) TAG, "syncRequest.response=".concat(String.valueOf(str9)));
        if (TextUtils.isEmpty(str9)) {
            com.alibaba.fastjson.JSONObject jSONObject3 = new com.alibaba.fastjson.JSONObject();
            jSONObject3.put((String) "error", (Object) innerSyncRequest.getRetCode());
            jSONObject3.put((String) "retCode", (Object) innerSyncRequest.getRetCode());
            jSONObject3.put((String) "retMsg", (Object) innerSyncRequest.getRetMsg());
            if (!(innerSyncRequest.getHeaderFields() == null || innerSyncRequest.getHeaderFields().get("eagleeye-traceid") == null || innerSyncRequest.getHeaderFields().get("eagleeye-traceid").size() <= 0)) {
                String str10 = (String) innerSyncRequest.getHeaderFields().get("eagleeye-traceid").get(0);
                jSONObject3.put((String) "__eagleeye_traceid__", (Object) str10);
                LoggerFactory.getTraceLogger().info(TAG, "add __eagleeye_traceid__ = ".concat(String.valueOf(str10)));
            }
            return new Pair<>(this.mMainHandler, jSONObject3);
        }
        try {
            jSONObject2 = JSON.parseObject(str9);
            try {
                if (!(innerSyncRequest.getHeaderFields() == null || innerSyncRequest.getHeaderFields().get("eagleeye-traceid") == null || innerSyncRequest.getHeaderFields().get("eagleeye-traceid").size() <= 0)) {
                    String str11 = (String) innerSyncRequest.getHeaderFields().get("eagleeye-traceid").get(0);
                    jSONObject2.put((String) "__eagleeye_traceid__", (Object) str11);
                    LoggerFactory.getTraceLogger().info(TAG, "add __eagleeye_traceid__ = ".concat(String.valueOf(str11)));
                }
            } catch (Throwable th) {
                th = th;
                LoggerFactory.getTraceLogger().error(TAG, "syncRequestFastJson parse fastJson error", th);
                return new Pair<>(this.mMainHandler, jSONObject2);
            }
        } catch (Throwable th2) {
            th = th2;
            jSONObject2 = null;
            LoggerFactory.getTraceLogger().error(TAG, "syncRequestFastJson parse fastJson error", th);
            return new Pair<>(this.mMainHandler, jSONObject2);
        }
        return new Pair<>(this.mMainHandler, jSONObject2);
    }

    public void sendMtopJsapiRequest(Map<String, Object> map, IRemoteBaseListener iRemoteBaseListener) {
        MtopJSBridge.sendMtopRequest(map, iRemoteBaseListener);
    }

    /* JADX WARNING: Removed duplicated region for block: B:50:0x00f9  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00fe  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0107  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0115  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0123  */
    /* JADX WARNING: Removed duplicated region for block: B:68:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private mtopsdk.mtop.domain.MtopResponse innerSyncRequest(java.lang.String r12, java.lang.String r13, boolean r14, boolean r15, boolean r16, boolean r17, java.lang.String r18, java.lang.String r19, com.alibaba.fastjson.JSONObject r20, java.lang.String r21, java.lang.String r22, java.lang.String r23, java.lang.String r24, mtopsdk.mtop.domain.MethodEnum r25) {
        /*
            r11 = this;
            r1 = r11
            r2 = r20
            r3 = r25
            android.app.Application r4 = getApplicationContext()
            mtopsdk.mtop.intf.Mtop r4 = mtopsdk.mtop.intf.Mtop.a(r4)
            mtopsdk.mtop.domain.MtopRequest r5 = new mtopsdk.mtop.domain.MtopRequest
            r5.<init>()
            r6 = 1
            r5.setNeedSession(r6)
            r7 = r14
            r5.setNeedEcode(r7)
            r7 = r13
            r5.setVersion(r7)
            r7 = r12
            r5.setApiName(r7)
            boolean r7 = android.text.TextUtils.isEmpty(r18)
            if (r7 != 0) goto L_0x002d
            r7 = r18
            r5.setData(r7)
        L_0x002d:
            com.alipay.mobile.common.logging.api.LogContext r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()
            java.lang.String r7 = r7.getUserId()
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            r8 = 0
            if (r7 != 0) goto L_0x004b
            boolean r7 = r1.fallbackToSessionCache
            if (r7 == 0) goto L_0x0048
            boolean r7 = r1.hasSession
            if (r7 != 0) goto L_0x004b
            boolean r7 = r1.useAlipaySession
            if (r7 == 0) goto L_0x004b
        L_0x0048:
            r1.ssoLoginToRegisterSessionInfo(r8)
        L_0x004b:
            boolean r7 = r1.isDebug
            if (r7 == 0) goto L_0x0083
            if (r16 != 0) goto L_0x0054
            defpackage.ds.b()
        L_0x0054:
            if (r17 != 0) goto L_0x0083
            defpackage.fff.a()
            fda r7 = defpackage.fff.a
            r9 = 0
            r7.c = r9
            mtopsdk.common.util.TBSdkLog$LogEnable r7 = mtopsdk.common.util.TBSdkLog.LogEnable.InfoEnable
            boolean r7 = mtopsdk.common.util.TBSdkLog.a(r7)
            if (r7 == 0) goto L_0x006d
            java.lang.String r7 = "mtopsdk.SwitchConfig"
            java.lang.String r10 = "[setGlobalSpdySwitchOpen]set local spdySwitchOpen=false"
            mtopsdk.common.util.TBSdkLog.b(r7, r10)
        L_0x006d:
            defpackage.fff.a()
            fda r7 = defpackage.fff.a
            r7.e = r9
            mtopsdk.common.util.TBSdkLog$LogEnable r7 = mtopsdk.common.util.TBSdkLog.LogEnable.InfoEnable
            boolean r7 = mtopsdk.common.util.TBSdkLog.a(r7)
            if (r7 == 0) goto L_0x0083
            java.lang.String r7 = "mtopsdk.SwitchConfig"
            java.lang.String r9 = "[setGlobalSpdySslSwitchOpen]set local spdySslSwitchOpen=false"
            mtopsdk.common.util.TBSdkLog.b(r7, r9)
        L_0x0083:
            r7 = r19
            ffg r4 = r4.a(r5, r7)
            if (r2 == 0) goto L_0x00d8
            com.alipay.mobile.common.logging.api.trace.TraceLogger r5 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x00c8 }
            java.lang.String r7 = "MtopServiceImpl"
            java.lang.String r9 = "headers = "
            java.lang.String r10 = java.lang.String.valueOf(r20)     // Catch:{ Throwable -> 0x00c8 }
            java.lang.String r9 = r9.concat(r10)     // Catch:{ Throwable -> 0x00c8 }
            r5.debug(r7, r9)     // Catch:{ Throwable -> 0x00c8 }
            int r5 = r20.size()     // Catch:{ Throwable -> 0x00c8 }
            if (r5 <= 0) goto L_0x00d8
            java.util.HashMap r7 = new java.util.HashMap     // Catch:{ Throwable -> 0x00c8 }
            r7.<init>(r5)     // Catch:{ Throwable -> 0x00c8 }
            java.util.Set r5 = r20.keySet()     // Catch:{ Throwable -> 0x00c5 }
            java.util.Iterator r5 = r5.iterator()     // Catch:{ Throwable -> 0x00c5 }
        L_0x00b1:
            boolean r9 = r5.hasNext()     // Catch:{ Throwable -> 0x00c5 }
            if (r9 == 0) goto L_0x00d9
            java.lang.Object r9 = r5.next()     // Catch:{ Throwable -> 0x00c5 }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ Throwable -> 0x00c5 }
            java.lang.String r10 = r2.getString(r9)     // Catch:{ Throwable -> 0x00c5 }
            r7.put(r9, r10)     // Catch:{ Throwable -> 0x00c5 }
            goto L_0x00b1
        L_0x00c5:
            r0 = move-exception
            r2 = r0
            goto L_0x00cb
        L_0x00c8:
            r0 = move-exception
            r2 = r0
            r7 = r8
        L_0x00cb:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r5 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r9 = "MtopServiceImpl"
            java.lang.String r10 = "syncRequest parse headers error"
            r5.error(r9, r10, r2)
            goto L_0x00d9
        L_0x00d8:
            r7 = r8
        L_0x00d9:
            if (r7 == 0) goto L_0x00e4
            int r2 = r7.size()
            if (r2 <= 0) goto L_0x00e4
            r4.headers(r7)
        L_0x00e4:
            boolean r2 = android.text.TextUtils.isEmpty(r23)
            if (r2 != 0) goto L_0x00f7
            boolean r2 = android.text.TextUtils.isEmpty(r24)
            if (r2 != 0) goto L_0x00f7
            r2 = r23
            r5 = r24
            r4.addOpenApiParams(r2, r5)
        L_0x00f7:
            if (r3 == 0) goto L_0x00fc
            r4.reqMethod(r3)
        L_0x00fc:
            if (r15 == 0) goto L_0x0101
            r4.useWua()
        L_0x0101:
            boolean r2 = android.text.TextUtils.isEmpty(r21)
            if (r2 != 0) goto L_0x010f
            java.lang.String r2 = "type"
            r3 = r21
            r4.addHttpQueryParameter(r2, r3)
        L_0x010f:
            boolean r2 = android.text.TextUtils.isEmpty(r22)
            if (r2 != 0) goto L_0x011d
            java.lang.String r2 = "tb_eagleeyex_scm_project"
            r3 = r22
            r4.addHttpQueryParameter(r2, r3)
        L_0x011d:
            mtopsdk.mtop.domain.MtopResponse r2 = r4.syncRequest()
            if (r2 == 0) goto L_0x0134
            boolean r3 = r2.isSessionInvalid()
            if (r3 == 0) goto L_0x0134
            boolean r3 = r1.useAlipaySession
            if (r3 == 0) goto L_0x0134
            r1.ssoLoginToRegisterSessionInfo(r8, r6)
            mtopsdk.mtop.domain.MtopResponse r2 = r4.syncRequest()
        L_0x0134:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.plugin.mtop.MtopServiceImpl.innerSyncRequest(java.lang.String, java.lang.String, boolean, boolean, boolean, boolean, java.lang.String, java.lang.String, com.alibaba.fastjson.JSONObject, java.lang.String, java.lang.String, java.lang.String, java.lang.String, mtopsdk.mtop.domain.MethodEnum):mtopsdk.mtop.domain.MtopResponse");
    }

    public synchronized <InputDO extends ffb, OutputDO extends BaseOutDo> MtopResponseWrapper<OutputDO> syncRequest(InputDO inputdo, Class<OutputDO> cls) {
        return syncRequest(inputdo, cls, (String) null, (ProtocolEnum) null, false, (MethodEnum) null, -1, (Map<String, String>) null);
    }

    public synchronized <InputDO extends ffb, OutputDO extends BaseOutDo> MtopResponseWrapper<OutputDO> syncRequest(InputDO inputdo, Class<OutputDO> cls, String str, ProtocolEnum protocolEnum, boolean z, MethodEnum methodEnum, int i, Map<String, String> map) {
        MtopResponse syncRequest;
        if (inputdo == null) {
            try {
                throw new IllegalArgumentException("inputDo can't be null");
            } catch (Throwable th) {
                throw th;
            }
        } else {
            ffg a = Mtop.a((Context) getApplicationContext()).a((ffb) inputdo, str);
            if (protocolEnum != null) {
                a.protocol(protocolEnum);
            }
            if (z) {
                a.useCache();
            }
            if (methodEnum != null) {
                a.reqMethod(methodEnum);
            } else {
                a.reqMethod(MethodEnum.GET);
            }
            if (i > 0) {
                a.retryTime(i);
            }
            if (map != null) {
                a.headers(map);
            }
            if (!TextUtils.isEmpty(LoggerFactory.getLogContext().getUserId()) && (!this.fallbackToSessionCache || (!this.hasSession && this.useAlipaySession))) {
                ssoLoginToRegisterSessionInfo(null);
            }
            syncRequest = a.syncRequest();
            if (syncRequest.isSessionInvalid() && this.useAlipaySession) {
                ssoLoginToRegisterSessionInfo(null, true);
                syncRequest = a.syncRequest();
            }
        }
        return handleMtopResponse(syncRequest, cls);
    }

    private static <OutputDO> MtopResponseWrapper<OutputDO> buildResponseWrapper(Status status, OutputDO outputdo) {
        MtopResponseWrapper<OutputDO> mtopResponseWrapper = new MtopResponseWrapper<>();
        mtopResponseWrapper.setResponseStatus(status);
        mtopResponseWrapper.setOutputDO(outputdo);
        return mtopResponseWrapper;
    }

    public synchronized <InputDO extends ffb, OutputDO extends BaseOutDo> boolean asyncRequest(InputDO inputdo, Class<OutputDO> cls, MtopAsyncRequestCallback<OutputDO> mtopAsyncRequestCallback) {
        return asyncRequest(inputdo, cls, mtopAsyncRequestCallback, null, null, false, null, -1, null);
    }

    public synchronized <InputDO extends ffb, OutputDO extends BaseOutDo> boolean asyncRequest(InputDO inputdo, final Class<OutputDO> cls, final MtopAsyncRequestCallback<OutputDO> mtopAsyncRequestCallback, String str, ProtocolEnum protocolEnum, boolean z, MethodEnum methodEnum, int i, Map<String, String> map) {
        if (inputdo == null) {
            try {
                throw new IllegalArgumentException("inputDo can't be null");
            } catch (Exception e) {
                LoggerFactory.getTraceLogger().warn((String) TAG, (Throwable) e);
                return false;
            } catch (Throwable th) {
                throw th;
            }
        } else {
            final ffg a = Mtop.a((Context) getApplicationContext()).a((ffb) inputdo, str);
            if (protocolEnum != null) {
                a.protocol(protocolEnum);
            }
            if (z) {
                a.useCache();
            }
            if (methodEnum != null) {
                a.reqMethod(methodEnum);
            }
            if (i > 0) {
                a.retryTime(i);
            }
            if (map != null) {
                a.headers(map);
            }
            if (this.hasSession || !this.useAlipaySession) {
                a.addListener(new MtopFinishListenerWrapper(mtopAsyncRequestCallback, cls, a)).asyncRequest();
            } else {
                ((TaskScheduleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName())).parallelExecute(new Runnable() {
                    public void run() {
                        MtopServiceImpl.this.ssoLoginToRegisterSessionInfo(null);
                        a.addListener(new MtopFinishListenerWrapper(mtopAsyncRequestCallback, cls, a)).asyncRequest();
                    }
                }, "mtop_async_request");
            }
        }
        return true;
    }

    static <OutputDO extends BaseOutDo> MtopResponseWrapper<OutputDO> handleMtopResponse(MtopResponse mtopResponse, Class<OutputDO> cls) {
        MtopResponseWrapper<OutputDO> mtopResponseWrapper;
        if (mtopResponse == null) {
            return null;
        }
        if (mtopResponse.isApiSuccess()) {
            mtopResponseWrapper = buildResponseWrapper(Status.SUCCESS, ffx.a(mtopResponse.getBytedata(), cls));
        } else if (mtopResponse.isSessionInvalid()) {
            mtopResponseWrapper = buildResponseWrapper(Status.SESSIONINVALID, null);
        } else if (mtopResponse.isSystemError() || mtopResponse.isNetworkError() || mtopResponse.isExpiredRequest() || mtopResponse.is41XResult() || mtopResponse.isApiLockedResult() || mtopResponse.isMtopSdkError()) {
            mtopResponseWrapper = buildResponseWrapper(Status.SYSTEMERROR, null);
        } else {
            mtopResponseWrapper = buildResponseWrapper(Status.BUSSINESSERROR, null);
        }
        return mtopResponseWrapper;
    }

    public void registerSessionInfo() {
        registerSessionInfo(null);
    }

    public void registerSessionInfo(RegisterSessionListener registerSessionListener) {
        ((TaskScheduleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName())).parallelExecute(new SsoLoginTask(registerSessionListener), "mtop_rpc_ssologin");
    }

    /* access modifiers changed from: private */
    public void ssoLoginToRegisterSessionInfo(RegisterSessionListener registerSessionListener) {
        ssoLoginToRegisterSessionInfo(registerSessionListener, false);
    }

    /* access modifiers changed from: private */
    public void ssoLoginToRegisterSessionInfo(RegisterSessionListener registerSessionListener, boolean z) {
        AliAutoLoginModel aliAutoLoginModel = new AliAutoLoginModel();
        aliAutoLoginModel.setShowUi(false);
        aliAutoLoginModel.setForceAuth(z);
        aliAutoLoginModel.setBizScene("tbAutoLogin");
        aliAutoLoginModel.setSourceType(SourceTypeEnum.NATIVE);
        aliAutoLoginModel.setSource("tbAutoLogin");
        aliAutoLoginModel.setSaveAliLoginCookie("NO");
        try {
            OperationResult startAction = InsideOperationService.getInstance().startAction((Context) getApplicationContext(), (BaseModel<T>) aliAutoLoginModel);
            if (startAction == null) {
                LoggerFactory.getTraceLogger().debug(TAG, "autoLogin result null");
                invokeFailed(registerSessionListener);
                return;
            }
            TraceLogger traceLogger = LoggerFactory.getTraceLogger();
            StringBuilder sb = new StringBuilder("autoLogin result: ");
            sb.append(startAction.toJsonString());
            traceLogger.debug(TAG, sb.toString());
            if (startAction.getCode() == AliAutoLoginCode.SUCCESS && !TextUtils.isEmpty(startAction.getResult())) {
                AliAuthResult aliAuthResult = (AliAuthResult) JSON.parseObject(startAction.getResult(), AliAuthResult.class);
                if (aliAuthResult != null) {
                    this.mSsoLoginInfo = new SsoLoginInfo(aliAuthResult);
                    if (aliAuthResult.success && !TextUtils.isEmpty(aliAuthResult.sid) && !TextUtils.isEmpty(aliAuthResult.ecode)) {
                        Mtop.a((Context) getApplicationContext()).a((String) null, aliAuthResult.sid, String.valueOf(aliAuthResult.tbUserId));
                        invokeSuccess(registerSessionListener);
                        return;
                    }
                }
            }
            invokeFailed(registerSessionListener);
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error(TAG, "checkAutoLogin error", th);
            invokeFailed(registerSessionListener);
        }
    }

    private void invokeSuccess(RegisterSessionListener registerSessionListener) {
        this.hasSession = true;
        if (registerSessionListener != null) {
            registerSessionListener.onRegisterSessionDone();
        }
    }

    private void invokeFailed(RegisterSessionListener registerSessionListener) {
        this.hasSession = false;
        if (registerSessionListener != null) {
            registerSessionListener.onRegisterSessionFail();
        }
    }

    public void registerSession(String str, String str2, String str3) {
        Mtop.a((Context) getApplicationContext()).a((String) null, str, str3);
    }

    /* access modifiers changed from: private */
    public static Application getApplicationContext() {
        return LauncherApplicationAgent.getInstance().getApplicationContext();
    }

    public void setHasSession(boolean z) {
        this.hasSession = z;
    }

    public boolean isHasSession() {
        return this.hasSession;
    }

    public void setUseAlipaySession(boolean z) {
        this.useAlipaySession = z;
    }

    public void logout() {
        Mtop.a((Context) getApplicationContext()).c();
    }

    public SsoLoginInfo getSsoLoginInfo() {
        return this.mSsoLoginInfo;
    }

    public void preConnect() {
        ((TaskScheduleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName())).acquireExecutor(ScheduleType.NORMAL).execute(new Runnable() {
            public void run() {
                try {
                    IStaticDataStoreComponent staticDataStoreComp = SecurityGuardManager.getInstance(MtopServiceImpl.getApplicationContext()).getStaticDataStoreComp();
                    if (staticDataStoreComp != null) {
                        int i = -1;
                        if (MtopServiceImpl.this.mEnvMode == ENV.ONLINE) {
                            i = 0;
                        } else if (MtopServiceImpl.this.mEnvMode == ENV.PREPARE) {
                            i = 1;
                        } else if (MtopServiceImpl.this.mEnvMode == ENV.TEST) {
                            i = 2;
                        }
                        LoggerFactory.getTraceLogger().debug(MtopServiceImpl.TAG, "appKeyIndex = ".concat(String.valueOf(i)));
                        String appKeyByIndex = staticDataStoreComp.getAppKeyByIndex(i, "");
                        a.a.a("acs.m.taobao.com", ConnProtocol.valueOf("http2", "0rtt", "acs", false));
                        t a = t.a("acs.m.taobao.com", true, false, null, null);
                        a aVar = new a();
                        aVar.b = appKeyByIndex;
                        aVar.c = MtopServiceImpl.this.mEnvMode;
                        r.a(aVar.a()).a(a);
                        MtopServiceImpl.this.ssoLoginToRegisterSessionInfo(null);
                        new MtopRequest();
                    }
                } catch (Throwable th) {
                    LoggerFactory.getTraceLogger().error(MtopServiceImpl.TAG, "preConnect error ", th);
                }
            }
        });
    }
}
