package com.alipay.mobile.nebulacore.plugin;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Builder;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.callback.H5SimpleRpcListener;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5NewJSApiPermissionProvider;
import com.alipay.mobile.nebula.provider.H5SimpleRpcProvider;
import com.alipay.mobile.nebula.util.H5DomainUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5NetworkUtil;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.scansdk.constant.Constants;
import com.alipay.mobile.security.bio.workspace.Env;

public class H5NewJSAPIPermissionPlugin extends H5SimplePlugin {
    private H5SimpleRpcProvider a;
    /* access modifiers changed from: private */
    public H5NewJSApiPermissionProvider b;
    private H5ConfigProvider c;
    private Handler d = new Handler(Looper.getMainLooper());
    private int e = -1;
    private boolean f = false;

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.H5_PAGE_SHOULD_LOAD_URL);
        filter.addAction("forceLoadUrl");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        if (TextUtils.equals(action, CommonEvents.H5_PAGE_SHOULD_LOAD_URL)) {
            H5Page h5Page = event.getH5page();
            if (h5Page == null) {
                H5Log.w("H5NewJSAPIPermissionPlugin", "FATAL ERROR h5Page == null");
                return false;
            } else if (this.f) {
                this.f = false;
                H5Log.d("H5NewJSAPIPermissionPlugin", "isForceLoadUrl return");
                return false;
            } else {
                JSONObject params = event.getParam();
                boolean isTinyApp = H5Utils.getBoolean(h5Page.getParams(), (String) "isTinyApp", false);
                String miniWebViewTag = H5Utils.getString(h5Page.getParams(), (String) "MINI-PROGRAM-WEB-VIEW-TAG", (String) "");
                if (!isTinyApp || !TextUtils.isEmpty(miniWebViewTag)) {
                    if (H5Utils.getBoolean(h5Page.getParams(), (String) Nebula.HAS_H5_PKG, false)) {
                        if ("yes".equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_noRpcInOfflinePkg"))) {
                            H5Log.d("H5NewJSAPIPermissionPlugin", "hasPackage skip intercept page");
                            return false;
                        }
                        String pageUrl = H5Utils.getString(params, (String) "url");
                        String h5PageHost = "";
                        if (!TextUtils.isEmpty(pageUrl)) {
                            h5PageHost = H5UrlHelper.getHost(pageUrl);
                        }
                        String vHost = H5Utils.getString(h5Page.getParams(), (String) H5Param.ONLINE_HOST);
                        String hostOfVhost = "";
                        if (!TextUtils.isEmpty(vHost)) {
                            hostOfVhost = H5UrlHelper.getHost(vHost);
                        }
                        if (!TextUtils.isEmpty(h5PageHost) && !TextUtils.isEmpty(hostOfVhost) && h5PageHost.equalsIgnoreCase(hostOfVhost)) {
                            H5Log.d("H5NewJSAPIPermissionPlugin", "hasPackage skip intercept page host: " + h5PageHost + "vhost: " + hostOfVhost);
                            return false;
                        }
                    }
                    if (this.c == null) {
                        this.c = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                    }
                    boolean canRpc = false;
                    int syncTimeout = 0;
                    JSONObject h5_newJsapiPermissionConfigObj = H5Utils.parseObject(this.c.getConfigWithProcessCache("h5_newJsapiPermissionConfig"));
                    if (h5_newJsapiPermissionConfigObj != null && !h5_newJsapiPermissionConfigObj.isEmpty()) {
                        canRpc = h5_newJsapiPermissionConfigObj.getBoolean("canRpc").booleanValue();
                        syncTimeout = h5_newJsapiPermissionConfigObj.getInteger("syncrpctimeout").intValue();
                        this.e = H5Utils.getInt(h5_newJsapiPermissionConfigObj, (String) "levelNoneCacheControl");
                        H5Log.debug("H5NewJSAPIPermissionPlugin", "syncTimeout " + syncTimeout);
                    }
                    if (!canRpc) {
                        H5Log.d("H5NewJSAPIPermissionPlugin", "switch off skip intercept");
                        return false;
                    }
                    String url = H5Utils.getString(params, (String) "url");
                    boolean isWhiteListDomains = a(url);
                    if (this.a == null) {
                        this.a = (H5SimpleRpcProvider) H5Utils.getProvider(H5SimpleRpcProvider.class.getName());
                    }
                    if (this.b == null) {
                        this.b = (H5NewJSApiPermissionProvider) H5Utils.getProvider(H5NewJSApiPermissionProvider.class.getName());
                    }
                    if (this.a == null || this.b == null) {
                        H5Log.w("H5NewJSAPIPermissionPlugin", "FATAL ERROR null == h5SimpleRpcProvider || null == h5NewJSApiPermissionProvider");
                    } else if (!this.b.ifExpiredByUrl(url)) {
                        String redirectUrl = this.b.getDynamicRouteByUrl(url);
                        if (!TextUtils.isEmpty(redirectUrl)) {
                            event.getParam().put((String) "url", (Object) redirectUrl);
                            a(event);
                            return true;
                        }
                        H5Log.d("H5NewJSAPIPermissionPlugin", "not expired");
                        return false;
                    } else {
                        String requestData = a(url, h5Page.getParams());
                        H5Log.debug("H5NewJSAPIPermissionPlugin", "requestData " + requestData);
                        final boolean[] alreadyLoadUrl = {false};
                        final H5Event h5Event = event;
                        AnonymousClass1 r0 = new Runnable() {
                            public void run() {
                                H5Log.d("H5NewJSAPIPermissionPlugin", "rpc synctimeout loadUrl");
                                H5NewJSAPIPermissionPlugin.this.a(h5Event, alreadyLoadUrl, "timer");
                            }
                        };
                        if (this.e >= 0) {
                            this.b.setLevelNoneCacheTime(this.e);
                        }
                        final boolean z = isWhiteListDomains;
                        final H5Event h5Event2 = event;
                        this.a.sendSimpleRpc("alipay.mappconfig.appContainerCheck", requestData, "", true, new JSONObject(), null, false, null, new H5SimpleRpcListener() {
                            public void onSuccess(String response) {
                                H5Log.d("H5NewJSAPIPermissionPlugin", "onSuccess response " + response);
                                String redirectUrl = H5NewJSAPIPermissionPlugin.this.b.handleDynamicRouteByUrl(response);
                                if (!z) {
                                    H5Log.d("H5NewJSAPIPermissionPlugin", "rpc not timeout loadUrl redirectUrl " + redirectUrl);
                                    if (!TextUtils.isEmpty(redirectUrl)) {
                                        h5Event2.getParam().put((String) "url", (Object) redirectUrl);
                                    }
                                    H5NewJSAPIPermissionPlugin.this.a(h5Event2, alreadyLoadUrl, "rpcresult");
                                }
                            }

                            public void onFailed(int errorCode, String errorMessage) {
                                H5Log.d("H5NewJSAPIPermissionPlugin", "onFailed errorCode " + errorCode + ", errorMessage " + errorMessage);
                                H5LogUtil.logNebulaTech(H5LogData.seedId("H5_JsapiPermission_Rpc_Exception").param3().add("errorcode", String.valueOf(errorCode)).add("errormeessage", errorMessage).add("type", "rpcerror"));
                                if (!z) {
                                    H5Log.d("H5NewJSAPIPermissionPlugin", "rpc not timeout loadUrl");
                                    H5NewJSAPIPermissionPlugin.this.a(h5Event2, alreadyLoadUrl, "rpcresult");
                                }
                            }
                        });
                        if (!isWhiteListDomains) {
                            this.d.postDelayed(r0, (long) syncTimeout);
                        }
                        return !isWhiteListDomains;
                    }
                } else {
                    H5Log.d("H5NewJSAPIPermissionPlugin", "isTinyApp skip intercept");
                    return false;
                }
            }
        } else if (TextUtils.equals(action, "forceLoadUrl")) {
            H5Log.d("H5NewJSAPIPermissionPlugin", "force load url");
            JSONObject param = event.getParam();
            param.put((String) "url", (Object) "javascript:location.replace('" + H5Utils.getString(param, (String) "url") + "');");
            this.f = true;
            a(event);
            context.sendSuccess();
            return true;
        }
        return super.handleEvent(event, context);
    }

    /* access modifiers changed from: private */
    public synchronized void a(H5Event event, boolean[] alreadyLoadUrl, String source) {
        if (!alreadyLoadUrl[0]) {
            H5Log.d("H5NewJSAPIPermissionPlugin", "do loadUrl from " + source);
            a(event);
        } else {
            H5Log.d("H5NewJSAPIPermissionPlugin", "discard loadUrl from " + source);
        }
        alreadyLoadUrl[0] = true;
    }

    private static void a(H5Event event) {
        Builder builder = new Builder();
        JSONObject param = event.getParam();
        param.put((String) "force", (Object) Boolean.valueOf(true));
        builder.action(CommonEvents.H5_PAGE_DO_LOAD_URL).param(param).target(event.getTarget());
        Nebula.getDispatcher().dispatch(builder.build(), null);
    }

    private boolean a(String url) {
        if (this.c == null) {
            this.c = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        }
        if (this.c != null) {
            return H5DomainUtil.isSomeDomainInternal(H5DomainUtil.getNewDomainSuffix(url), this.c.getConfigWithProcessCache("h5_newJsapiDomainWhiteList"));
        }
        return false;
    }

    private String a(String url, Bundle startParams) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put((String) "url", (Object) url);
        jsonObject.put((String) "httpMethod", (Object) "GET");
        boolean isTinyApp = H5Utils.getBoolean(startParams, (String) "isTinyApp", false);
        String webviewTag = H5Utils.getString(startParams, (String) "MINI-PROGRAM-WEB-VIEW-TAG");
        if (!isTinyApp || TextUtils.isEmpty(webviewTag)) {
            jsonObject.put((String) H5AppUtil.scene, (Object) Integer.valueOf(0));
            jsonObject.put((String) "appId", (Object) H5Utils.getString(startParams, (String) "appId"));
        } else {
            jsonObject.put((String) H5AppUtil.scene, (Object) Integer.valueOf(1));
            jsonObject.put((String) "appId", (Object) webviewTag);
        }
        jsonObject.put((String) H5Param.PUBLIC_ID, (Object) H5Utils.getString(startParams, (String) H5Param.PUBLIC_ID));
        jsonObject.put((String) H5Param.LONG_BIZ_SCENARIO, (Object) H5Utils.getString(startParams, (String) H5Param.LONG_BIZ_SCENARIO));
        jsonObject.put((String) Constants.SERVICE_SOURCE_ID, (Object) H5Utils.getString(startParams, (String) Constants.SERVICE_SOURCE_ID));
        jsonObject.put((String) "insideClient", (Object) Boolean.valueOf(TextUtils.equals("inner", H5Utils.getString(startParams, (String) "app_startup_type"))));
        if (this.b.ifLevelNoneExpired()) {
            jsonObject.put((String) "levelNoneFetch", (Object) Boolean.valueOf(true));
        } else {
            jsonObject.put((String) "levelNoneFetch", (Object) Boolean.valueOf(false));
        }
        if (!H5Utils.isDebug() || !H5NetworkUtil.PRE_GW.equalsIgnoreCase(H5NetworkUtil.getGWFURL(LauncherApplicationAgent.getInstance().getBaseContext()))) {
            jsonObject.put((String) Env.NAME_PRE, (Object) Boolean.valueOf(false));
        } else {
            jsonObject.put((String) Env.NAME_PRE, (Object) Boolean.valueOf(true));
        }
        jsonArray.add(jsonObject);
        return jsonArray.toJSONString();
    }

    public void onRelease() {
        super.onRelease();
    }
}
