package com.alipay.mobile.tinyappcustom.h5plugin;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.SecurityCacheService;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.appcenter.apphandler.H5PreferAppList;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ParamParser;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import com.alipay.mobile.tinyappcommon.mode.TinyAppEnvMode;
import com.alipay.mobile.tinyappcommon.utils.TinyAppParamUtils;
import com.alipay.mobile.tinyappcustom.biz.auth.TinyAppAuthBridge;
import com.alipay.mobile.tinyappcustom.h5plugin.navigation.MiniAppConfig;
import com.alipay.mobile.tinyappcustom.h5plugin.navigation.MiniAppRelationCheckRequestPB;
import com.alipay.mobile.tinyappcustom.h5plugin.navigation.MiniAppRelationCheckResultPB;
import com.alipay.mobile.tinyappcustom.h5plugin.navigation.MiniAppRelationCheckRpcService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.json.JSONException;

public class H5MiniProgramNavigationPlugin extends H5SimplePlugin {
    public static final String NAVIGATE_BACK_MINI_PROGRAM = "navigateBackMiniProgram";
    public static final String NAVIGATE_TO_MINI_PROGRAM = "navigateToMiniProgram";
    public static final String PARAM_CHANNEL = "chInfo";
    /* access modifiers changed from: private */
    public static final String a = H5MiniProgramNavigationPlugin.class.getSimpleName();
    private H5Event b;

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(NAVIGATE_TO_MINI_PROGRAM);
        filter.addAction(NAVIGATE_BACK_MINI_PROGRAM);
    }

    public void onRelease() {
        this.b = null;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        this.b = event;
        if (NAVIGATE_TO_MINI_PROGRAM.equals(event.getAction())) {
            a(event, context);
        } else if (NAVIGATE_BACK_MINI_PROGRAM.equals(event.getAction())) {
            b(event, context);
        }
        return true;
    }

    private void a(final H5Event event, final H5BridgeContext context) {
        H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
            public void run() {
                if (event != null) {
                    H5Page h5Page = event.getH5page();
                    if (h5Page == null) {
                        context.sendError(event, Error.INVALID_PARAM);
                        return;
                    }
                    String currentAppId = TinyAppParamUtils.getHostAppId(h5Page.getParams());
                    String appId = H5Utils.getString(event.getParam(), (String) "appId");
                    if (TextUtils.isEmpty(appId)) {
                        context.sendError(event, Error.INVALID_PARAM);
                        return;
                    }
                    TinyAppEnvMode currentAppEnv = TinyAppEnvMode.valueOf(h5Page);
                    TinyAppEnvMode targetAppEnv = TinyAppEnvMode.RELEASE;
                    boolean acStartModeOnline = H5Utils.contains(event.getParam(), (String) TinyAppEnvMode.PARAM_ENV_TINY_APP);
                    if (currentAppEnv != TinyAppEnvMode.RELEASE) {
                        targetAppEnv = TinyAppEnvMode.valueOf(event);
                    }
                    H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                    if (h5ConfigProvider != null) {
                        JSONObject whitelistObject = h5ConfigProvider.getConfigJSONObject("start_app_whitelist");
                        if (whitelistObject != null && whitelistObject.containsKey(appId)) {
                            JSONArray whitelist = H5Utils.getJSONArray(whitelistObject, appId, null);
                            if (whitelist != null && !whitelist.isEmpty()) {
                                for (int i = 0; i < whitelist.size(); i++) {
                                    if (TextUtils.equals(whitelist.getString(i), currentAppId)) {
                                        H5Log.d(H5MiniProgramNavigationPlugin.a, "navigateToMiniProgram..allowed, in start_app_whitelist");
                                        H5MiniProgramNavigationPlugin.this.a(currentAppId, appId, targetAppEnv, acStartModeOnline);
                                        return;
                                    }
                                }
                            }
                            H5Log.d(H5MiniProgramNavigationPlugin.a, "navigateToMiniProgram..not allowed, not in start_app_whitelist");
                            H5MiniProgramNavigationPlugin.b(context);
                            return;
                        }
                    }
                    if (MiniAppConfig.getInstance().allowedNaviToNonSubjectMiniProgram()) {
                        H5Log.d(H5MiniProgramNavigationPlugin.a, "navigateToMiniProgram..allowed non-same-obj");
                        H5MiniProgramNavigationPlugin.this.a(currentAppId, appId, targetAppEnv, acStartModeOnline);
                        return;
                    }
                    List<String> naviToMiniProgramWhitelist = MiniAppConfig.getInstance().getNaviToMiniProgramWhitelist();
                    if (naviToMiniProgramWhitelist == null || !naviToMiniProgramWhitelist.contains(currentAppId)) {
                        String userId = TinyAppService.get().getUserId();
                        String miniProgInfo = ((SecurityCacheService) H5Utils.findServiceByInterface(SecurityCacheService.class.getName())).getString(null, H5MiniProgramNavigationPlugin.e(userId, currentAppId));
                        if (TextUtils.isEmpty(miniProgInfo)) {
                            H5MiniProgramNavigationPlugin.this.a(userId, currentAppId, appId, context, targetAppEnv, acStartModeOnline);
                            return;
                        }
                        try {
                            org.json.JSONObject jsonObject = new org.json.JSONObject(miniProgInfo);
                            if (System.currentTimeMillis() > jsonObject.optLong("m_p_n_d_k")) {
                                H5Log.d(H5MiniProgramNavigationPlugin.a, "navigateToMiniProgram.. expired, DO rpc");
                                H5MiniProgramNavigationPlugin.this.a(userId, currentAppId, appId, context, targetAppEnv, acStartModeOnline);
                            } else if (H5MiniProgramNavigationPlugin.c(appId, jsonObject.optString("a_m_p_n_k"))) {
                                H5Log.d(H5MiniProgramNavigationPlugin.a, "navigateToMiniProgram..allowed target: " + appId);
                                H5MiniProgramNavigationPlugin.this.a(currentAppId, appId, targetAppEnv, acStartModeOnline);
                            } else if (H5MiniProgramNavigationPlugin.c(appId, jsonObject.optString("n_a_m_p_n_k"))) {
                                H5Log.e(H5MiniProgramNavigationPlugin.a, "navigateToMiniProgram...not allowed target: " + appId);
                                H5MiniProgramNavigationPlugin.b(context);
                            } else {
                                H5Log.d(H5MiniProgramNavigationPlugin.a, "navigateToMiniProgram.. DO rpc, new target appId: " + appId);
                                H5MiniProgramNavigationPlugin.this.a(userId, currentAppId, appId, context, targetAppEnv, acStartModeOnline);
                            }
                        } catch (JSONException e) {
                            H5Log.e(H5MiniProgramNavigationPlugin.a, "navigateToMiniProgram...e=" + e);
                            H5MiniProgramNavigationPlugin.a(context, 31, "跳转失败");
                        }
                    } else {
                        H5Log.d(H5MiniProgramNavigationPlugin.a, "navigateToMiniProgram..allowed .. in whitelist");
                        H5MiniProgramNavigationPlugin.this.a(currentAppId, appId, targetAppEnv, acStartModeOnline);
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static boolean c(String appId, String navigateInfo) {
        if (TextUtils.isEmpty(navigateInfo)) {
            return false;
        }
        for (String equals : navigateInfo.split(",")) {
            if (equals.equals(appId)) {
                return true;
            }
        }
        return false;
    }

    private static void b(H5Event event, H5BridgeContext context) {
        if (event == null) {
            a(context, 32, "回跳失败");
            return;
        }
        JSONObject param = event.getParam();
        if (param != null && !param.containsKey("chInfo")) {
            param.put((String) "chInfo", (Object) "ch_backfromtinyapp");
        }
        event.setAction(H5PageData.FROM_TYPE_START_APP);
        event.setParam(param);
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service != null) {
            h5Service.sendEvent(event, context);
        }
    }

    /* access modifiers changed from: private */
    public void a(String userId, String currentAppId, String appId, H5BridgeContext context, TinyAppEnvMode targetAppEnv, boolean acStartModeOnline) {
        org.json.JSONObject jsonObject;
        try {
            String uuid = UUID.randomUUID().toString();
            MiniAppRelationCheckRequestPB request = new MiniAppRelationCheckRequestPB();
            request.appId = currentAppId;
            request.userId = userId;
            request.targetId = appId;
            Bundle requestParams = new Bundle();
            requestParams.putString("appId", request.appId);
            requestParams.putString("userId", request.userId);
            requestParams.putString("targetId", request.targetId);
            TinyAppAuthBridge.get().executeSkipIdentifyAuth(uuid, requestParams);
            Map extParams = new HashMap();
            extParams.put("OpenAuthLogin", "YES");
            extParams.put("needOpenAuth", "YES");
            extParams.put("bizSource", "tinyapp");
            extParams.put("cAuthUUID", uuid);
            MiniAppRelationCheckResultPB resultPB = ((MiniAppRelationCheckRpcService) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(MiniAppRelationCheckRpcService.class, extParams)).checkRelation(request);
            if (resultPB == null || !resultPB.success.booleanValue()) {
                H5Log.d(a, "checkSameSubjectMiniAppInfoFromRPC...rpc failed: " + resultPB);
                a(context, 31, "跳转失败");
                return;
            }
            SecurityCacheService scs = (SecurityCacheService) H5Utils.findServiceByInterface(SecurityCacheService.class.getName());
            String miniProgInfo = scs.getString(null, e(userId, currentAppId));
            if (TextUtils.isEmpty(miniProgInfo)) {
                jsonObject = new org.json.JSONObject();
            } else {
                jsonObject = new org.json.JSONObject(miniProgInfo);
            }
            jsonObject.put("m_p_n_d_k", System.currentTimeMillis() + 86400000);
            if (resultPB.jumpable == null || !resultPB.jumpable.booleanValue()) {
                H5Log.d(a, "checkSameSubjectMiniAppInfoFromRPC...not allowed: " + appId);
                jsonObject.put("n_a_m_p_n_k", d(appId, jsonObject.optString("n_a_m_p_n_k")));
                H5Log.d(a, "checkSameSubjectMiniAppInfoFromRPC..not allowed: " + jsonObject.toString());
                scs.set(null, e(userId, currentAppId), jsonObject.toString());
                b(context);
                return;
            }
            H5Log.d(a, "checkSameSubjectMiniAppInfoFromRPC...allowed: " + appId);
            jsonObject.put("a_m_p_n_k", d(appId, jsonObject.optString("a_m_p_n_k")));
            H5Log.d(a, "checkSameSubjectMiniAppInfoFromRPC..allowed: " + jsonObject.toString());
            scs.set(null, e(userId, currentAppId), jsonObject.toString());
            a(currentAppId, appId, targetAppEnv, acStartModeOnline);
        } catch (Throwable e) {
            H5Log.e(a, "checkSameSubjectMiniAppInfoFromRPC...e=" + e);
            a(context, 31, "跳转失败");
        }
    }

    private static String d(String targetId, String navigateInfo) {
        return TextUtils.isEmpty(navigateInfo) ? targetId : navigateInfo + "," + targetId;
    }

    /* access modifiers changed from: private */
    public static void b(H5BridgeContext context) {
        a(context, 30, "不允许跳转到目标小程序");
    }

    /* access modifiers changed from: private */
    public static void a(H5BridgeContext context, int errorCode, String errorMsg) {
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) Integer.valueOf(errorCode));
        result.put((String) "errorMessage", (Object) errorMsg);
        context.sendBridgeResult(result);
    }

    /* access modifiers changed from: private */
    public void a(String currentAppId, String appId, TinyAppEnvMode targetAppEnv, boolean acStartModeOnline) {
        JSONObject callParam = null;
        if (this.b != null) {
            callParam = H5Utils.getJSONObject(this.b.getParam(), "param", null);
            H5Log.d(a, "startAppInternal..appId=" + appId + ",param=" + callParam.toString());
        }
        Bundle startParams = H5ParamParser.parse(H5Utils.toBundle(null, callParam), false);
        startParams.putString("FROM_TINY_APP_ID", currentAppId);
        if (!startParams.containsKey("chInfo")) {
            startParams.putString("chInfo", "ch_othertinyapp");
        }
        if (MiniAppConfig.getInstance().isNavigateAppDebug()) {
            targetAppEnv.putToBundle(startParams);
            if (targetAppEnv != TinyAppEnvMode.RELEASE) {
                String nbsv = H5Utils.getString(this.b.getParam(), (String) "version");
                if (TextUtils.isEmpty(nbsv)) {
                    nbsv = "*";
                }
                startParams.putString(H5PreferAppList.nbsv, nbsv);
            } else {
                startParams.remove(H5PreferAppList.nbsv);
            }
            if (acStartModeOnline && targetAppEnv == TinyAppEnvMode.RELEASE) {
                startParams.putString("ac_start_mode", "online");
            }
        }
        LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(null, appId, startParams);
    }

    /* access modifiers changed from: private */
    public static String e(String userId, String appId) {
        return userId + "_" + appId + "_miniprog";
    }
}
