package com.alipay.mobile.tinyappcustom.h5plugin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.ali.auth.third.login.LoginConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.account.AccountInfoModel;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.service.InsideOperationService;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.process.H5EventHandler;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import com.alipay.mobile.tinyappcustom.h5plugin.openauth.H5OpenAuthHelper;
import com.alipay.mobile.tinyappcustom.h5plugin.openauth.request.MapStringString;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class H5OpenAuthPlugin extends H5SimplePlugin {
    /* access modifiers changed from: private */
    public H5Page a;
    /* access modifiers changed from: private */
    public Activity b;
    /* access modifiers changed from: private */
    public String c;

    public void onInitialize(H5CoreNode coreNode) {
        this.a = (H5Page) coreNode;
        this.c = H5Utils.getString(this.a.getParams(), (String) "sessionId");
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(H5EventHandler.getAuthCode);
        filter.addAction(H5EventHandler.getAuthUserInfo);
        filter.addAction("getComponentAuth");
        filter.addAction("getBusinessAuth");
        filter.addAction("getAuthorize");
    }

    public void onRelease() {
        super.onRelease();
        this.a = null;
        this.b = null;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (!(event.getTarget() instanceof H5Page)) {
            return false;
        }
        this.a = (H5Page) event.getTarget();
        this.b = event.getActivity();
        String action = event.getAction();
        if (("getComponentAuth".equals(action) || "getBusinessAuth".equals(action) || "getAuthorize".equals(action)) && this.a != null && this.a.getParams() != null && !H5Utils.getBoolean(this.a.getParams(), (String) "isTinyApp", false)) {
            context.sendNoRigHtToInvoke();
            return true;
        } else if (H5EventHandler.getAuthCode.equals(action)) {
            a(event, context, null);
            return true;
        } else if ("getComponentAuth".equals(action)) {
            Map appExt = new HashMap();
            appExt.put("callMethod", "getComponentAuth");
            a(event, context, appExt);
            return true;
        } else if ("getBusinessAuth".equals(action)) {
            Map appExt2 = new HashMap();
            appExt2.put("callMethod", "getBusinessAuth");
            a(event, context, appExt2);
            return true;
        } else if ("getAuthorize".equals(action)) {
            Map appExt3 = new HashMap();
            appExt3.put("callMethod", "getAuthorize");
            a(event, context, appExt3);
            return true;
        } else if (!H5EventHandler.getAuthUserInfo.equals(action)) {
            return false;
        } else {
            if (!H5Flag.getOpenAuthGrantFlag(this.c)) {
                H5Log.d("H5OpenAuthPlugin", "getAuthUserInfo not granted, sessionId is " + this.c);
                context.sendNoRigHtToInvoke();
                return true;
            }
            try {
                OperationResult result = InsideOperationService.getInstance().startAction((Context) event.getActivity(), (BaseModel<T>) new AccountInfoModel<T>());
                if (result != null) {
                    JSONObject dataObject = JSONObject.parseObject(result.getResult());
                    if (dataObject != null) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put((String) "nick", (Object) dataObject.getString("nickName"));
                        jsonObject.put((String) "userAvatar", (Object) dataObject.getString("userAvatar"));
                        context.sendBridgeResult(jsonObject);
                        return true;
                    }
                }
            } catch (Throwable e) {
                H5Log.e((String) "H5OpenAuthPlugin", "getAuthUserInfo...e=" + e);
            }
            context.sendError(event, Error.UNKNOWN_ERROR);
            return true;
        }
    }

    private void a(final H5Event event, final H5BridgeContext context, final Map<String, Object> appExt) {
        H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
            public void run() {
                String clientAppId;
                if (H5OpenAuthPlugin.this.a != null && H5OpenAuthPlugin.this.a.getParams() != null) {
                    Bundle startParams = H5OpenAuthPlugin.this.a.getParams();
                    String url = H5OpenAuthPlugin.this.a.getUrl();
                    boolean isTinyApp = H5Utils.getBoolean(startParams, (String) "isTinyApp", false);
                    String currentPageAppId = H5Utils.getString(startParams, (String) "appId");
                    String currentAppId = currentPageAppId;
                    boolean isMiniService = TinyAppMiniServicePlugin.appIsMiniService(H5OpenAuthPlugin.this.a);
                    if (isMiniService) {
                        currentPageAppId = H5Utils.getString(startParams, (String) "parentAppId");
                    }
                    String appVersion = H5Utils.getString(startParams, (String) "appVersion");
                    String thirdPlatformAppId = null;
                    H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
                    if (h5AppProvider != null) {
                        String thirdPlatform = h5AppProvider.getThirdPlatform(currentPageAppId, appVersion);
                        if (!TextUtils.isEmpty(thirdPlatform)) {
                            H5Log.d("H5OpenAuthPlugin", "thirdPlatform is " + thirdPlatform);
                            try {
                                JSONObject thirdPlatformObj = JSON.parseObject(thirdPlatform);
                                if (thirdPlatformObj != null && !thirdPlatformObj.isEmpty()) {
                                    JSONObject clientName = H5Utils.getJSONObject(thirdPlatformObj, BehavorReporter.PROVIDE_BY_ALIPAY, null);
                                    if (clientName != null && !clientName.isEmpty()) {
                                        thirdPlatformAppId = H5Utils.getString(clientName, (String) "id", (String) "");
                                    }
                                }
                            } catch (Exception e) {
                                H5Log.e("H5OpenAuthPlugin", "catch exception ", e);
                            }
                        }
                    }
                    H5Log.d("H5OpenAuthPlugin", "appId is " + currentPageAppId + ", appVersion is " + appVersion + ", thirdPlatform is " + thirdPlatformAppId);
                    if (TextUtils.isEmpty(thirdPlatformAppId)) {
                        thirdPlatformAppId = currentPageAppId;
                    }
                    List scopeNicks = new ArrayList();
                    JSONArray scopeNicksArray = H5Utils.getJSONArray(event.getParam(), "scopeNicks", null);
                    if (scopeNicksArray != null && !scopeNicksArray.isEmpty()) {
                        H5Log.d("H5OpenAuthPlugin", "scopeNicksArray is " + scopeNicksArray.toJSONString());
                        for (int i = 0; i < scopeNicksArray.size(); i++) {
                            scopeNicks.add(scopeNicksArray.getString(i));
                        }
                    }
                    boolean showErrorTip = H5Utils.getBoolean(event.getParam(), (String) "showErrorTip", true);
                    if (!isTinyApp) {
                        String parsedAppId = H5Utils.getString(event.getParam(), (String) "appId");
                        if (!TextUtils.isEmpty(parsedAppId)) {
                            thirdPlatformAppId = parsedAppId;
                        } else if ("20000778".equals(currentPageAppId)) {
                            url = "https://2015042700050887.hybrid.alipay-eco.com";
                        } else if ("60000146".equals(currentPageAppId)) {
                            url = "https://2017021305648824.hybrid.alipay-eco.com";
                        } else if ("60000033".equals(currentPageAppId)) {
                            url = "https://2015121700992100.hybrid.alipay-eco.com";
                        }
                        if (TextUtils.isEmpty(thirdPlatformAppId)) {
                            context.sendError(event, Error.INVALID_PARAM);
                            return;
                        }
                    }
                    String isvAppId = H5Utils.getString(event.getParam(), (String) "isvAppId");
                    H5Log.d("H5OpenAuthPlugin", "isvAppId is " + isvAppId);
                    MapStringString extInfo = null;
                    JSONObject extInfoObj = H5Utils.getJSONObject(event.getParam(), "extInfo", null);
                    HashMap hashMap = new HashMap();
                    if (H5Utils.contains(event.getParam(), (String) "extInfo")) {
                        if (extInfoObj == null || extInfoObj.isEmpty()) {
                            context.sendError(event, Error.INVALID_PARAM);
                            return;
                        }
                        for (Entry entry : extInfoObj.entrySet()) {
                            hashMap.put(entry.getKey(), entry.getValue());
                        }
                        H5Log.d("H5OpenAuthPlugin", "extInfo is " + hashMap.toString());
                        extInfo = H5OpenAuthHelper.mapToMapStringString(hashMap);
                    }
                    Map appExtInfoMap = new HashMap();
                    appExtInfoMap.put("channel", isTinyApp ? "tinyapp" : LoginConstants.H5_LOGIN);
                    if (isTinyApp) {
                        clientAppId = currentPageAppId;
                    } else {
                        clientAppId = thirdPlatformAppId;
                    }
                    appExtInfoMap.put("clientAppId", clientAppId);
                    if (appExt != null) {
                        appExtInfoMap.putAll(appExt);
                    }
                    H5Log.d("H5OpenAuthPlugin", "appExtInfo is " + appExtInfoMap.toString());
                    MapStringString appExtInfo = H5OpenAuthHelper.mapToMapStringString(appExtInfoMap);
                    if (isMiniService) {
                        url = null;
                        isvAppId = currentAppId;
                    }
                    if (isTinyApp && TextUtils.isEmpty(url)) {
                        H5Log.d("H5OpenAuthPlugin", "url is empty use default url");
                        url = new StringBuilder(AjxHttpLoader.DOMAIN_HTTPS).append(currentPageAppId).append(".hybrid.alipay-eco.com").toString();
                    }
                    new H5OpenAuthHelper(H5OpenAuthPlugin.this.b, context, H5OpenAuthPlugin.this.c).getAuthContentOrAutoAuth(thirdPlatformAppId, url, scopeNicks, isvAppId, extInfo, showErrorTip, appExtInfo);
                }
            }
        });
    }
}
