package com.ali.auth.third.login;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.ali.auth.third.core.config.AuthOption;
import com.ali.auth.third.core.config.ConfigManager;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.cookies.LoginCookieUtils;
import com.ali.auth.third.core.model.HistoryAccount;
import com.ali.auth.third.core.model.LoginReturnData;
import com.ali.auth.third.core.model.RpcRequest;
import com.ali.auth.third.core.model.RpcResponse;
import com.ali.auth.third.core.rpc.protocol.RpcException;
import com.ali.auth.third.core.service.RpcService;
import com.ali.auth.third.core.service.StorageService;
import com.ali.auth.third.core.service.UserTrackerService;
import com.ali.auth.third.core.service.impl.CredentialManager;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.core.util.CommonUtils;
import com.ali.auth.third.core.util.JSONUtils;
import com.ali.auth.third.core.util.RSAKey;
import com.ali.auth.third.core.util.ResourceUtils;
import com.ali.auth.third.core.util.Rsa;
import com.ali.auth.third.core.util.SystemUtils;
import com.ali.auth.third.login.a.a;
import com.ali.auth.third.ui.LoginWebViewActivity;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.alipay.sdk.cons.c;
import com.taobao.accs.common.Constants;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener;

public class LoginComponent {
    public static final LoginComponent INSTANCE = new LoginComponent();

    private LoginComponent() {
    }

    private String a(TreeMap<String, String> treeMap) {
        StringBuilder sb = new StringBuilder();
        for (Entry next : treeMap.entrySet()) {
            String str = (String) next.getKey();
            String str2 = (String) next.getValue();
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                sb.append(str);
                sb.append(str2);
            }
        }
        return sb.toString();
    }

    public static void addKey(Map<String, String> map, String str, String str2) {
        map.put(str, str2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x011f, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r1.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0156, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0157, code lost:
        r1.printStackTrace();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0156 A[ExcHandler: JSONException (r1v5 'e' org.json.JSONException A[CUSTOM_DECLARE]), Splitter:B:1:0x000d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.ali.auth.third.core.model.RpcResponse<com.ali.auth.third.core.model.LoginReturnData> loginByRefreshToken() {
        /*
            com.ali.auth.third.core.model.RpcRequest r0 = new com.ali.auth.third.core.model.RpcRequest
            r0.<init>()
            java.lang.String r1 = "com.taobao.mtop.mLoginUnitService.autoLogin"
            r0.target = r1
            java.lang.String r1 = "1.0"
            r0.version = r1
            com.ali.auth.third.core.service.impl.CredentialManager r1 = com.ali.auth.third.core.service.impl.CredentialManager.INSTANCE     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            com.ali.auth.third.core.model.InternalSession r1 = r1.getInternalSession()     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            com.ali.auth.third.core.model.User r1 = r1.user     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r1 = r1.userId     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r2 = "userId"
            long r3 = java.lang.Long.parseLong(r1)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            r0.addParam(r2, r3)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            r2.<init>()     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r3 = "appName"
            java.lang.String r4 = com.ali.auth.third.core.context.KernelContext.getAppKey()     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            r2.put(r3, r4)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r3 = "token"
            com.ali.auth.third.core.service.impl.CredentialManager r4 = com.ali.auth.third.core.service.impl.CredentialManager.INSTANCE     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            com.ali.auth.third.core.model.InternalSession r4 = r4.getInternalSession()     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r4 = r4.autoLoginToken     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            r2.put(r3, r4)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r3 = "sdkVersion"
            java.lang.String r4 = com.ali.auth.third.core.context.KernelContext.sdkVersion     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            r2.put(r3, r4)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r5 = "t"
            java.lang.String r6 = java.lang.String.valueOf(r3)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            r2.put(r5, r6)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r5 = "clientIp"
            java.lang.String r6 = com.ali.auth.third.core.util.CommonUtils.getLocalIPAddress()     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            r2.put(r5, r6)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            boolean r5 = com.ali.auth.third.core.context.KernelContext.isMini     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            if (r5 == 0) goto L_0x008b
            java.lang.String r5 = "app_id"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            r6.<init>()     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            android.content.Context r7 = com.ali.auth.third.core.context.KernelContext.getApplicationContext()     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r7 = r7.getPackageName()     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            r6.append(r7)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r7 = "|"
            r6.append(r7)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r7 = com.ali.auth.third.core.util.SystemUtils.getApkPublicKeyDigest()     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            r6.append(r7)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
        L_0x0087:
            r2.put(r5, r6)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            goto L_0x009b
        L_0x008b:
            java.lang.String r5 = "utdid"
            java.lang.Class<com.ali.auth.third.core.service.RpcService> r6 = com.ali.auth.third.core.service.RpcService.class
            java.lang.Object r6 = com.ali.auth.third.core.context.KernelContext.getService(r6)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            com.ali.auth.third.core.service.RpcService r6 = (com.ali.auth.third.core.service.RpcService) r6     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r6 = r6.getDeviceId()     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            goto L_0x0087
        L_0x009b:
            boolean r5 = android.text.TextUtils.isEmpty(r1)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            if (r5 != 0) goto L_0x0108
            java.lang.Class<com.ali.auth.third.core.service.StorageService> r5 = com.ali.auth.third.core.service.StorageService.class
            java.lang.Object r5 = com.ali.auth.third.core.context.KernelContext.getService(r5)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            com.ali.auth.third.core.service.StorageService r5 = (com.ali.auth.third.core.service.StorageService) r5     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            com.ali.auth.third.core.model.HistoryAccount r1 = r5.findHistoryAccount(r1)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            if (r1 == 0) goto L_0x0108
            java.lang.String r5 = r1.tokenKey     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            if (r6 != 0) goto L_0x0108
            java.util.TreeMap r6 = new java.util.TreeMap     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            r6.<init>()     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r7 = com.ali.auth.third.login.LoginConstants.KEY_APPKEY     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r8 = com.ali.auth.third.core.context.KernelContext.getAppKey()     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            addKey(r6, r7, r8)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r7 = com.ali.auth.third.login.LoginConstants.KEY_HAVANAID     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r8 = r1.userId     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            addKey(r6, r7, r8)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r7 = com.ali.auth.third.login.LoginConstants.KEY_TIMESTAMP     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            addKey(r6, r7, r3)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r3 = com.ali.auth.third.login.LoginConstants.KEY_APPVERSION     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r4 = com.ali.auth.third.core.util.CommonUtils.getAndroidAppVersion()     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            addKey(r6, r3, r4)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r3 = com.ali.auth.third.login.LoginConstants.KEY_SDKVERSION     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r4 = com.ali.auth.third.core.context.KernelContext.sdkVersion     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            addKey(r6, r3, r4)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.Class<com.ali.auth.third.core.service.StorageService> r3 = com.ali.auth.third.core.service.StorageService.class
            java.lang.Object r3 = com.ali.auth.third.core.context.KernelContext.getService(r3)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            com.ali.auth.third.core.service.StorageService r3 = (com.ali.auth.third.core.service.StorageService) r3     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r3 = r3.signMap(r5, r6)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            if (r4 != 0) goto L_0x0108
            java.lang.String r4 = "deviceTokenSign"
            r2.put(r4, r3)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r3 = "deviceTokenKey"
            r2.put(r3, r5)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r3 = "hid"
            java.lang.String r1 = r1.userId     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            r2.put(r3, r1)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
        L_0x0108:
            java.lang.String r1 = "alimm_"
            org.json.JSONObject r1 = com.ali.auth.third.core.cookies.LoginCookieUtils.getKeyValues(r1)     // Catch:{ Exception -> 0x011f, JSONException -> 0x0156 }
            java.lang.String r3 = "miid"
            java.lang.String r4 = "miid"
            java.lang.String r4 = com.ali.auth.third.core.cookies.LoginCookieUtils.getValue(r4)     // Catch:{ Exception -> 0x011f, JSONException -> 0x0156 }
            r1.put(r3, r4)     // Catch:{ Exception -> 0x011f, JSONException -> 0x0156 }
            java.lang.String r3 = "ext"
            r2.put(r3, r1)     // Catch:{ Exception -> 0x011f, JSONException -> 0x0156 }
            goto L_0x0123
        L_0x011f:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
        L_0x0123:
            java.lang.String r1 = "tokenInfo"
            r0.addParam(r1, r2)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            r1.<init>()     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r2 = "umidToken"
            java.lang.Class<com.ali.auth.third.core.service.StorageService> r3 = com.ali.auth.third.core.service.StorageService.class
            java.lang.Object r3 = com.ali.auth.third.core.context.KernelContext.getService(r3)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            com.ali.auth.third.core.service.StorageService r3 = (com.ali.auth.third.core.service.StorageService) r3     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r3 = r3.getUmid()     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            r1.put(r2, r3)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r2 = "riskControlInfo"
            r0.addParam(r2, r1)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            java.lang.String r1 = "ext"
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            r2.<init>()     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            r0.addParam(r1, r2)     // Catch:{ JSONException -> 0x0156, Exception -> 0x0151 }
            goto L_0x015a
        L_0x0151:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x015a
        L_0x0156:
            r1 = move-exception
            r1.printStackTrace()
        L_0x015a:
            java.lang.Class<com.ali.auth.third.core.service.RpcService> r1 = com.ali.auth.third.core.service.RpcService.class
            java.lang.Object r1 = com.ali.auth.third.core.context.KernelContext.getService(r1)
            com.ali.auth.third.core.service.RpcService r1 = (com.ali.auth.third.core.service.RpcService) r1
            java.lang.Class<com.ali.auth.third.core.model.LoginReturnData> r2 = com.ali.auth.third.core.model.LoginReturnData.class
            com.ali.auth.third.core.model.RpcResponse r0 = r1.invoke(r0, r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.auth.third.login.LoginComponent.loginByRefreshToken():com.ali.auth.third.core.model.RpcResponse");
    }

    public static RpcResponse<String> logout() {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.target = "mtop.taobao.havana.mlogin.logout";
        rpcRequest.version = "1.0";
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("appKey", KernelContext.getAppKey());
            jSONObject.put(Constants.KEY_SID, CredentialManager.INSTANCE.getInternalSession().sid);
            jSONObject.put(OnNativeInvokeListener.ARG_IP, CommonUtils.getLocalIPAddress());
            rpcRequest.addParam("userId", Long.valueOf(Long.parseLong(CredentialManager.INSTANCE.getInternalSession().user.userId)));
            rpcRequest.addParam("request", jSONObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((RpcService) KernelContext.getService(RpcService.class)).invoke(rpcRequest, String.class);
    }

    public String generateTopAppLinkToken(String str) {
        ((UserTrackerService) KernelContext.getService(UserTrackerService.class)).send(UTConstants.E_GENERATE_TAOBAO_SIGN, null);
        TreeMap treeMap = new TreeMap();
        treeMap.put("appKey", KernelContext.getAppKey());
        treeMap.put("apkSign", str);
        treeMap.put(c.n, "taobao.oauth.code.create");
        String a = a(treeMap);
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.target = "com.alibaba.havana.login.applink.generateTopAppLinkToken";
        rpcRequest.version = "1.0";
        String appKey = KernelContext.getAppKey();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("appName", appKey);
            StringBuilder sb = new StringBuilder();
            sb.append(System.currentTimeMillis());
            jSONObject.put(LogItem.MM_C15_K4_TIME, sb.toString());
            jSONObject.put("clientIp", CommonUtils.getLocalIPAddress());
            if (KernelContext.isMini) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(KernelContext.getApplicationContext().getPackageName());
                sb2.append(MergeUtil.SEPARATOR_KV);
                sb2.append(SystemUtils.getApkPublicKeyDigest());
                jSONObject.put("app_id", sb2.toString());
            }
            jSONObject.put(Constants.KEY_SDK_VERSION, KernelContext.sdkVersion);
            rpcRequest.addParam("baseInfo", jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        rpcRequest.addParam("content", a);
        try {
            RpcResponse<String> invoke = ((RpcService) KernelContext.getService(RpcService.class)).invoke(rpcRequest, String.class);
            if (invoke != null) {
                return (String) invoke.returnValue;
            }
            return null;
        } catch (RpcException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public RpcResponse<LoginReturnData> loginByCode(String str) {
        String str2;
        String deviceId;
        try {
            ((UserTrackerService) KernelContext.getService(UserTrackerService.class)).send(UTConstants.E_TOP_TOKEN_LOGIN, null);
            RpcRequest rpcRequest = new RpcRequest();
            rpcRequest.target = "com.taobao.mtop.mloginService.topTokenLogin";
            rpcRequest.version = "1.0";
            try {
                JSONObject jSONObject = new JSONObject();
                if (KernelContext.isMini) {
                    str2 = "app_id";
                    StringBuilder sb = new StringBuilder();
                    sb.append(KernelContext.getApplicationContext().getPackageName());
                    sb.append(MergeUtil.SEPARATOR_KV);
                    sb.append(SystemUtils.getApkPublicKeyDigest());
                    deviceId = sb.toString();
                } else {
                    str2 = "utdid";
                    deviceId = ((RpcService) KernelContext.getService(RpcService.class)).getDeviceId();
                }
                jSONObject.put(str2, deviceId);
                jSONObject.put("appName", KernelContext.getAppKey());
                jSONObject.put("token", str);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(System.currentTimeMillis());
                jSONObject.put(LogItem.MM_C15_K4_TIME, sb2.toString());
                jSONObject.put(Constants.KEY_SDK_VERSION, KernelContext.sdkVersion);
                jSONObject.put("clientIp", CommonUtils.getLocalIPAddress());
                try {
                    JSONObject keyValues = LoginCookieUtils.getKeyValues("alimm_");
                    keyValues.put(com.xiaomi.mipush.sdk.Constants.EXTRA_KEY_MIID, LoginCookieUtils.getValue(com.xiaomi.mipush.sdk.Constants.EXTRA_KEY_MIID));
                    jSONObject.put(ProcessInfo.ALIAS_EXT, keyValues);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                rpcRequest.addParam("tokenInfo", jSONObject);
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put(DictionaryKeys.V2_UMID, ((StorageService) KernelContext.getService(StorageService.class)).getUmid());
                rpcRequest.addParam("riskControlInfo", jSONObject2);
                rpcRequest.addParam(ProcessInfo.ALIAS_EXT, new JSONObject());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            return ((RpcService) KernelContext.getService(RpcService.class)).invoke(rpcRequest, LoginReturnData.class);
        } catch (Exception e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public RpcResponse<LoginReturnData> loginByIVToken(String str, String str2, String str3) {
        String str4;
        String deviceId;
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.target = "com.taobao.mtop.mloginService.mloginTokenLogin";
        rpcRequest.version = "1.0";
        try {
            JSONObject jSONObject = new JSONObject();
            if (KernelContext.isMini) {
                str4 = "app_id";
                StringBuilder sb = new StringBuilder();
                sb.append(KernelContext.getApplicationContext().getPackageName());
                sb.append(MergeUtil.SEPARATOR_KV);
                sb.append(SystemUtils.getApkPublicKeyDigest());
                deviceId = sb.toString();
            } else {
                str4 = "utdid";
                deviceId = ((RpcService) KernelContext.getService(RpcService.class)).getDeviceId();
            }
            jSONObject.put(str4, deviceId);
            jSONObject.put("appName", KernelContext.getAppKey());
            jSONObject.put("token", str);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(System.currentTimeMillis());
            jSONObject.put(LogItem.MM_C15_K4_TIME, sb2.toString());
            jSONObject.put(H5AppUtil.scene, str2);
            jSONObject.put(Constants.KEY_SDK_VERSION, KernelContext.sdkVersion);
            jSONObject.put("clientIp", CommonUtils.getLocalIPAddress());
            try {
                JSONObject keyValues = LoginCookieUtils.getKeyValues("alimm_");
                keyValues.put(com.xiaomi.mipush.sdk.Constants.EXTRA_KEY_MIID, LoginCookieUtils.getValue(com.xiaomi.mipush.sdk.Constants.EXTRA_KEY_MIID));
                keyValues.put("aliusersdk_h5querystring", str3);
                jSONObject.put(ProcessInfo.ALIAS_EXT, keyValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
            rpcRequest.addParam("tokenInfo", jSONObject);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(DictionaryKeys.V2_UMID, ((StorageService) KernelContext.getService(StorageService.class)).getUmid());
            rpcRequest.addParam("riskControlInfo", jSONObject2);
            rpcRequest.addParam(ProcessInfo.ALIAS_EXT, JSONUtils.toJsonObject(new HashMap()));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return a.d.invoke(rpcRequest, LoginReturnData.class);
    }

    public RpcResponse<LoginReturnData> loginByUserName(String str) {
        String str2;
        String deviceId;
        RpcRequest rpcRequest = new RpcRequest();
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = JSONUtils.optString(jSONObject, "loginid");
            if (TextUtils.isEmpty(optString)) {
                optString = JSONUtils.optString(jSONObject, "loginId");
            }
            rpcRequest.target = "com.taobao.mtop.mloginService.login";
            rpcRequest.version = "1.0";
            JSONObject jSONObject2 = new JSONObject();
            if (KernelContext.isMini) {
                str2 = "app_id";
                StringBuilder sb = new StringBuilder();
                sb.append(KernelContext.getApplicationContext().getPackageName());
                sb.append(MergeUtil.SEPARATOR_KV);
                sb.append(SystemUtils.getApkPublicKeyDigest());
                deviceId = sb.toString();
            } else {
                str2 = "utdid";
                deviceId = ((RpcService) KernelContext.getService(RpcService.class)).getDeviceId();
            }
            jSONObject2.put(str2, deviceId);
            jSONObject2.put("appName", KernelContext.getAppKey());
            jSONObject2.put("loginId", optString);
            jSONObject2.put("clientIp", CommonUtils.getLocalIPAddress());
            long currentTimeMillis = System.currentTimeMillis();
            if (!TextUtils.isEmpty(optString)) {
                HistoryAccount matchHistoryAccount = ((StorageService) KernelContext.getService(StorageService.class)).matchHistoryAccount(optString);
                if (matchHistoryAccount != null) {
                    String str3 = matchHistoryAccount.tokenKey;
                    if (!TextUtils.isEmpty(str3)) {
                        TreeMap treeMap = new TreeMap();
                        addKey(treeMap, LoginConstants.KEY_APPKEY, KernelContext.getAppKey());
                        addKey(treeMap, LoginConstants.KEY_HAVANAID, matchHistoryAccount.userId);
                        addKey(treeMap, LoginConstants.KEY_TIMESTAMP, String.valueOf(currentTimeMillis));
                        addKey(treeMap, LoginConstants.KEY_APPVERSION, CommonUtils.getAndroidAppVersion());
                        addKey(treeMap, LoginConstants.KEY_SDKVERSION, KernelContext.sdkVersion);
                        String signMap = ((StorageService) KernelContext.getService(StorageService.class)).signMap(str3, treeMap);
                        if (!TextUtils.isEmpty(signMap)) {
                            jSONObject2.put("deviceTokenSign", signMap);
                            jSONObject2.put("deviceTokenKey", str3);
                            jSONObject2.put("hid", matchHistoryAccount.userId);
                        }
                    }
                }
            }
            jSONObject2.put("password", Rsa.encrypt(JSONUtils.optString(jSONObject, "password"), RSAKey.getRsaPubkey()));
            jSONObject2.put("pwdEncrypted", true);
            jSONObject2.put("appVersion", CommonUtils.getAndroidAppVersion());
            jSONObject2.put(Constants.KEY_SDK_VERSION, KernelContext.sdkVersion);
            jSONObject2.put(LogItem.MM_C15_K4_TIME, String.valueOf(currentTimeMillis));
            jSONObject2.put("ccId", JSONUtils.optString(jSONObject, "checkCodeId"));
            jSONObject2.put("checkCode", JSONUtils.optString(jSONObject, "checkCode"));
            try {
                JSONObject keyValues = LoginCookieUtils.getKeyValues("alimm_");
                keyValues.put(com.xiaomi.mipush.sdk.Constants.EXTRA_KEY_MIID, LoginCookieUtils.getValue(com.xiaomi.mipush.sdk.Constants.EXTRA_KEY_MIID));
                jSONObject2.put(ProcessInfo.ALIAS_EXT, keyValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
            rpcRequest.addParam("loginInfo", jSONObject2);
            JSONObject jSONObject3 = new JSONObject();
            if (KernelContext.isMini) {
                String optString2 = JSONUtils.optString(jSONObject, com.ali.auth.third.core.model.Constants.UMID);
                jSONObject3.put(DictionaryKeys.V2_UMID, optString2);
                ((StorageService) KernelContext.getService(StorageService.class)).setUmid(optString2);
            } else {
                jSONObject3.put(DictionaryKeys.V2_UMID, ((StorageService) KernelContext.getService(StorageService.class)).getUmid());
            }
            jSONObject3.put(com.ali.auth.third.core.model.Constants.UA, JSONUtils.optString(jSONObject, com.ali.auth.third.core.model.Constants.UA));
            rpcRequest.addParam("riskControlInfo", jSONObject3);
            rpcRequest.addParam(ProcessInfo.ALIAS_EXT, new JSONObject());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return ((RpcService) KernelContext.getService(RpcService.class)).invoke(rpcRequest, LoginReturnData.class);
    }

    public void showH5Login(Activity activity) {
        SDKLogger.d("login", "open H5 login");
        Intent intent = new Intent(activity, LoginWebViewActivity.class);
        intent.putExtra("url", ConfigManager.LOGIN_HOST);
        intent.putExtra("title", ResourceUtils.getString(activity.getApplicationContext(), "com_taobao_tae_sdk_authorize_title"));
        activity.startActivityForResult(intent, RequestCode.OPEN_H5_LOGIN);
    }

    public void showLogin(Activity activity) {
        SDKLogger.d("login", "showLogin");
        if (KernelContext.authOption == AuthOption.H5ONLY) {
            showH5Login(activity);
        } else {
            new a(this, SystemUtils.getApkSignNumber(), activity).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
        }
    }
}
