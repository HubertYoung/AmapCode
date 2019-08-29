package com.alipay.mobile.tinyappcommon.tinypermission;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.beehive.plugin.H5PhotoPlugin;
import com.alipay.mobile.framework.loading.LoadingView;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5PermissionCallBack;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5PluginManager;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.h5plugin.H5LocationPlugin;
import com.alipay.mobile.h5plugin.TinyAppStoragePlugin;
import com.alipay.mobile.nebula.appcenter.apphandler.H5StartAppInfo;
import com.alipay.mobile.nebula.baseprovider.H5BaseAppBizRpcProvider;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.process.H5IpcServer;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.provider.H5NewJSApiPermissionProvider;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ParamParser;
import com.alipay.mobile.nebula.util.H5PatternHelper;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.plugin.H5AlertPlugin;
import com.alipay.mobile.nebulacore.plugin.H5SessionPlugin;
import com.alipay.mobile.nebulacore.tabbar.H5SessionTabBar;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobile.tinyappcommon.api.TinyAppMixActionService;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import com.alipay.mobile.tinyappcommon.config.TinyAppConfig;
import com.alipay.mobile.tinyappcommon.embedview.H5WebViewMessagePlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.ApiDynamicPermissionPlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.H5ShowShareParamPlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.H5TinyWebViewSharePlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin;
import com.alipay.mobile.tinyappcommon.mode.TinyAppEnvMode;
import com.alipay.mobile.tinyappcommon.storage.TinyAppStorage;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import com.alipay.mobile.tinyappcommon.utils.WalletTinyappUtils;
import com.alipay.mobile.tinyappcustom.process.H5EventHandlerServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class H5ApiManagerImpl implements H5ApiManager {
    private static final String DEBUG_APP_INFO_RPC_NAME = "alipay.openservice.pkgcore.developpackage.download";
    private static final String DEBUG_AUTH_RPC_NAME = "alipay.openservice.pkgcore.packagepermission.check";
    private static final String HPMWEB_DEBUG_AUTH_RPC_NAME = "com.alipay.hpmweb.validMember";
    private static final String STARTPARAMS_IGNORE_HTTP_REQUEST_PERMISSION = "ignoreHttpReqPermission";
    /* access modifiers changed from: private */
    public static String TAG = "H5TinyApiManagerImpl";
    private static final String[] WEBVIEW_API_INIT_LIST = {H5WebViewMessagePlugin.POST_WEBVIEW_MESSAGE, H5WebViewMessagePlugin.GET_EMBED_WEBVIEW_ENV, "chooseImage", H5PhotoPlugin.IMAGE_VIEWER, CommonEvents.GET_NETWORK_TYPE, H5LocationPlugin.GET_CURRENT_LOCATION, H5LocationPlugin.GET_LOCATION, H5LocationPlugin.OPEN_LOCATION, CommonEvents.HIDE_LOADING, "showLoading", "alert", H5EventHandlerServiceImpl.tradePay, TinyAppStoragePlugin.SET_TINY_LOCAL_STORAGE, TinyAppStoragePlugin.GET_TINY_LOCAL_STORAGE, TinyAppStoragePlugin.REMOVE_TINY_LOCAL_STORAGE, TinyAppStoragePlugin.CLEAR_TINY_LOCAL_STORAGE, TinyAppStoragePlugin.GET_TINY_LOCAL_STORAGE_INFO};
    private static final String[] WEBVIEW_NAVI_API_LIST = {"navigateTo", "navigateBack", H5SessionTabBar.SWITCH_TAB, "reLaunch", "redirectTo", H5LocationPlugin.GET_CURRENT_LOCATION, H5LocationPlugin.GET_LOCATION, H5TinyWebViewSharePlugin.ACTION_SHARE};
    private static List<String> WHITE_JSAPI_LIST = null;
    private static final String config = "h5_api_permission_config";
    private static Map<String, Boolean> hasSetCache = new ConcurrentHashMap();
    private Map<String, b> apiInfoMap;
    private String configValue = "";
    private a mApiBizPermissionManager;

    static {
        ArrayList arrayList = new ArrayList();
        WHITE_JSAPI_LIST = arrayList;
        arrayList.add(H5SessionPlugin.SHOW_NETWORK_CHECK_ACTIVITY);
        WHITE_JSAPI_LIST.add(H5AlertPlugin.showUCFailDialog);
        WHITE_JSAPI_LIST.add("setKeyboardType");
        WHITE_JSAPI_LIST.add(H5Param.MONITOR_PERFORMANCE);
        WHITE_JSAPI_LIST.add("getStartupParams");
        WHITE_JSAPI_LIST.add("inputBlurEvent");
        WHITE_JSAPI_LIST.add("hideCustomKeyBoard");
        WHITE_JSAPI_LIST.add("hideCustomInputMethod4NativeInput");
        WHITE_JSAPI_LIST.add("updateNativeKeyBoardInput");
        WHITE_JSAPI_LIST.add(H5TinyAppLogUtil.TINY_APP_STANDARD_LOG);
        WHITE_JSAPI_LIST.add("tinyDebugConsole");
    }

    public H5ApiManagerImpl() {
        c.a();
        this.apiInfoMap = new ConcurrentHashMap();
        this.mApiBizPermissionManager = new a();
    }

    private boolean openPermission(String appId) {
        if (!H5Utils.isDebuggable(H5Utils.getContext())) {
            return false;
        }
        if (H5Utils.isMainProcess()) {
            return H5DevConfig.getBooleanConfig(H5DevConfig.h5_not_use_tiny_permission, false);
        }
        if (hasSetCache.get(appId) != null) {
            return hasSetCache.get(appId).booleanValue();
        }
        H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
        if (h5EventHandlerService == null) {
            return false;
        }
        boolean result = false;
        try {
            H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
            if (h5IpcServer != null && h5IpcServer.getBooleanConfig(H5DevConfig.h5_not_use_tiny_permission, false)) {
                result = true;
            }
        } catch (Throwable throwable) {
            H5Log.e(TAG, throwable);
        }
        hasSetCache.put(appId, Boolean.valueOf(result));
        return result;
    }

    public void putJson(String appId, JSONObject jsonObject) {
        this.apiInfoMap.put(appId, c.a(appId, jsonObject));
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            this.configValue = h5ConfigProvider.getConfigWithProcessCache(config);
        }
        H5Log.d(TAG, "put " + appId);
    }

    public void put(String appId, byte[] bytes) {
        if (c.a(appId, bytes) != null) {
            this.apiInfoMap.put(appId, c.a(appId, bytes));
            H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (h5ConfigProvider != null) {
                this.configValue = h5ConfigProvider.getConfigWithProcessCache(config);
            }
            H5Log.d(TAG, "put " + appId);
        }
    }

    public void clear(String appId) {
        if (this.apiInfoMap.get(appId) != null) {
            this.apiInfoMap.get(appId).b();
            this.apiInfoMap.remove(appId);
            if (H5Utils.isInTinyProcess()) {
                hasSetCache.remove(appId);
            }
        }
    }

    public boolean hasPermission(String appId, String content, String item, H5Page h5Page) {
        H5Log.d(TAG, "hasPermission " + appId + Token.SEPARATOR + content + Token.SEPARATOR + item);
        String tag = callFromWebView(h5Page);
        if (!TextUtils.isEmpty(tag)) {
            appId = tag;
        }
        if (H5Utils.isDebuggable(H5Utils.getContext()) && openPermission(appId)) {
            return true;
        }
        try {
            if (!TextUtils.isEmpty(tag)) {
                if (!TinyAppConfig.getInstance().isSupportedWebview()) {
                    H5Log.d(TAG, "hasPermission...webview is not supported!");
                    return false;
                } else if (item.startsWith(H5ApiManager.validDomain)) {
                    item = "Webview_Config_allowedDomain";
                }
            }
            if (!TextUtils.isEmpty(this.configValue)) {
                JSONObject jsonObject = H5Utils.parseObject(this.configValue);
                JSONObject appIdLevel = H5Utils.getJSONObject(jsonObject, appId, null);
                if (appIdLevel == null || appIdLevel.isEmpty()) {
                    if ("yes".equalsIgnoreCase(H5Utils.getString(jsonObject, item))) {
                        return true;
                    }
                } else if ("yes".equalsIgnoreCase(H5Utils.getString(appIdLevel, item))) {
                    return true;
                }
            }
            if (TextUtils.equals(item, H5ApiManager.Enable_Proxy)) {
                return TextUtils.equals("YES", this.apiInfoMap.get(appId).b(item));
            }
            if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(content) || TextUtils.isEmpty(item) || this.apiInfoMap == null || this.apiInfoMap.get(appId) == null || this.apiInfoMap.get(appId).a(item) == null || this.apiInfoMap.get(appId).a(item).isEmpty()) {
                if (item.startsWith(H5ApiManager.JSAPI_SP_Config)) {
                    return true;
                }
                return false;
            } else if (!TextUtils.equals(item, H5ApiManager.Valid_SubResMimeList) && !item.startsWith(H5ApiManager.JSAPI_SP_Config) && !TextUtils.equals(item, H5ApiManager.EVENT_List) && !TextUtils.equals(item, H5ApiManager.HttpLink_SubResMimeList) && !item.startsWith(H5ApiManager.Webview_Config)) {
                return this.apiInfoMap.get(appId).a(item).contains(content);
            } else {
                List<String> ruleList = this.apiInfoMap.get(appId).a(item);
                if (ruleList != null && !ruleList.isEmpty()) {
                    for (String compile : ruleList) {
                        Matcher matcher = Pattern.compile(compile).matcher(content);
                        if (matcher != null && matcher.matches()) {
                            return true;
                        }
                        if (item.startsWith(H5ApiManager.JSAPI_SP_Config) && matcher != null && matcher.find()) {
                            return true;
                        }
                    }
                }
                return false;
            }
        } catch (PatternSyntaxException e) {
            H5Log.e(TAG, (Throwable) e);
            if (TextUtils.equals(item, "Webview_Config_allowedDomain")) {
                return false;
            }
            throw e;
        } catch (Exception e2) {
            H5Log.e(TAG, (Throwable) e2);
            return true;
        }
    }

    public boolean hasPermissionFile(String appId, H5Page h5Page) {
        if (TextUtils.isEmpty(appId)) {
            String tag = callFromWebView(h5Page);
            if (TextUtils.isEmpty(tag)) {
                return false;
            }
            appId = tag;
        }
        return this.apiInfoMap.get(appId) != null && this.apiInfoMap.get(appId).a();
    }

    private boolean hasJsApiPermissionCallFromRender(String appId, String action, H5Event h5Event) {
        if (H5Event.FROM_WORK.equals(h5Event.getEventSource())) {
            return false;
        }
        H5Page h5Page = h5Event.getH5page();
        if (h5Page == null) {
            return false;
        }
        String url = h5Page.getUrl();
        String onlineHost = H5Utils.getString(h5Page.getParams(), (String) H5Param.ONLINE_HOST);
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(onlineHost) || !url.startsWith(onlineHost)) {
            H5Log.w(TAG, "hasJsApiPermissionCallFromRender...url is not match.");
            return false;
        }
        Set apiBlackList = TinyAppConfig.getInstance().getRenderJsApiBlacklist();
        if (apiBlackList == null || !apiBlackList.contains(action)) {
            return true;
        }
        return false;
    }

    public boolean setPermission(H5Event h5Event, String appId, H5BridgeContext h5BridgeContext, boolean event, H5Page h5Page) {
        boolean hasPermission;
        if (h5Event == null || h5Page == null) {
            return false;
        }
        String action = h5Event.getAction();
        String webviewAppId = null;
        String tag = callFromWebView(h5Page);
        if (!TextUtils.isEmpty(tag)) {
            appId = tag;
            webviewAppId = H5Utils.getString(h5Page.getParams(), (String) "appId");
        }
        if (event) {
            hasPermission = hasPermission(appId, action, H5ApiManager.EVENT_List, h5Event.getH5page());
        } else if (!TextUtils.isEmpty(tag)) {
            if (!(!shouldInterceptWebViewJsapi(h5Event, action, appId, webviewAppId))) {
                if (h5BridgeContext != null) {
                    h5BridgeContext.sendNoRigHtToInvoke();
                }
                return false;
            }
            if (TinyAppMiniServicePlugin.appIsMiniService(h5Page)) {
                appId = H5Utils.getString(h5Page.getParams(), (String) "parentAppId");
            }
            return !this.mApiBizPermissionManager.a((Context) h5Event.getActivity(), appId, h5Event, h5BridgeContext);
        } else if (hasJsApiPermissionCallFromRender(appId, action, h5Event)) {
            H5Log.d(TAG, "setPermission...api from render, permission ok.");
            return true;
        } else {
            hasPermission = hasPermission(appId, action, H5ApiManager.JSAPI_List, h5Event.getH5page());
        }
        if (H5ShowShareParamPlugin.SET_SHOW_SHARE_MENU.equals(action) || ApiDynamicPermissionPlugin.INTERNAL_API.equals(action)) {
            return true;
        }
        H5Log.d(TAG, "action:" + action + " has api permission:" + hasPermission + " appId:" + appId);
        if (!hasPermission) {
            if (h5BridgeContext != null) {
                if (checkJSApiExist(h5Event.getTarget(), action)) {
                    h5BridgeContext.sendNoRigHtToInvoke();
                } else {
                    h5BridgeContext.sendError(h5Event, Error.NOT_FOUND);
                }
            }
            H5Log.e(TAG, action + " not in EVENT_List or JSAPI_List");
            return false;
        }
        JSONObject callParam = h5Event.getParam();
        if (callParam == null) {
            return true;
        }
        if (!a.a(action, appId, callParam)) {
            H5Log.d(TAG, action + " is not supported by param!");
            if (h5BridgeContext != null) {
                h5BridgeContext.sendError(Error.INVALID_PARAM.ordinal(), (String) "invalid parameter!");
            }
            return false;
        }
        try {
            String item = "JSAPI_SP_Config_" + action;
            List apiRuleList = this.apiInfoMap.get(appId).a(item);
            if (TextUtils.equals(action, "uploadFile") || TextUtils.equals(action, "request") || (apiRuleList != null && !apiRuleList.isEmpty())) {
                String content = null;
                boolean allowed = true;
                if (TextUtils.equals(action, "httpRequest") || TextUtils.equals(action, "uploadFile") || TextUtils.equals(action, "request")) {
                    String reqUrl = H5Utils.getString(callParam, (String) "url");
                    Uri reqUri = H5UrlHelper.parseUrl(reqUrl);
                    if (reqUri != null) {
                        content = reqUri.getHost();
                        allowed = hasPermission(appId, content, "JSAPI_SP_Config_httpRequest_allowedDomain", h5Event.getH5page());
                        if (TextUtils.equals(action, "httpRequest") || TextUtils.equals(action, "request")) {
                            if (allowed) {
                                if (isDomainInBackList(h5Page, content)) {
                                    allowed = false;
                                }
                            } else if (H5Utils.getBoolean(h5Page.getParams(), (String) STARTPARAMS_IGNORE_HTTP_REQUEST_PERMISSION, false)) {
                                H5ConfigProvider configProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                                if (configProvider != null && !configProvider.isAlipayDomains(reqUrl) && !configProvider.isSeriousAliDomains(reqUrl) && !configProvider.isAliDomains(reqUrl)) {
                                    allowed = true;
                                }
                            }
                        }
                    }
                } else {
                    for (String apiRule : apiRuleList) {
                        String paramItem = H5Utils.getString(callParam, apiRule, (String) null);
                        if (!TextUtils.isEmpty(paramItem)) {
                            content = paramItem;
                            allowed = hasPermission(appId, content, item + "_" + apiRule, h5Event.getH5page());
                            H5Log.d(TAG, "paramKey: " + apiRule + " check content:" + content + " allowed:" + allowed);
                            if (!allowed) {
                                if (h5BridgeContext != null) {
                                    h5BridgeContext.sendNoRigHtToInvoke();
                                }
                                H5Log.e(TAG, action + " paramKey: " + apiRule + " content: " + content + " is not allowed");
                                return false;
                            }
                        }
                    }
                    if (TextUtils.equals(action, H5PageData.FROM_TYPE_START_APP)) {
                        Uri paramUri = H5UrlHelper.parseUrl(H5Utils.getString(H5ParamParser.parse(H5Utils.toBundle(null, H5Utils.getJSONObject(callParam, "param", null)), false), (String) "url"));
                        if (paramUri != null) {
                            content = paramUri.getHost();
                            allowed = hasPermission(appId, content, item + "_url", h5Event.getH5page());
                        }
                    }
                }
                H5Log.d(TAG, "check content:" + content + " allowed:" + allowed);
                if (!allowed) {
                    if (h5BridgeContext != null) {
                        h5BridgeContext.sendNoRigHtToInvoke();
                    }
                    H5Log.e(TAG, action + " param: " + content + " is not allowed");
                    return false;
                }
                return true;
            }
            if (TinyAppMiniServicePlugin.appIsMiniService(h5Page)) {
                appId = H5Utils.getString(h5Page.getParams(), (String) "parentAppId");
            }
            return !this.mApiBizPermissionManager.a((Context) h5Event.getActivity(), appId, h5Event, h5BridgeContext);
        } catch (Throwable e) {
            H5Log.e(TAG, "setPermission action: " + action + "..." + e);
        }
    }

    public boolean hasPermissionOnIframe(String appId, String host, String level, H5Page h5Page) {
        H5Log.d(TAG, "hasPermissionOnIframe...url=" + host);
        return hasPermission(appId, host, level, h5Page);
    }

    public boolean hasPermissionOnScheme(String appId, String scheme, H5Page h5Page) {
        String appIdTag = callFromWebView(h5Page);
        if (TextUtils.isEmpty(appIdTag)) {
            return true;
        }
        if (TextUtils.isEmpty(scheme)) {
            return true;
        }
        try {
            if (!TinyAppConfig.getInstance().isCloseWebviewSchema() && scheme.startsWith("alipays://platformapi/startapp")) {
                Uri uri = Uri.parse(scheme);
                String targetAppId = uri.getQueryParameter("appId");
                if (this.apiInfoMap == null) {
                    return false;
                }
                if (!hasPermission(appIdTag, H5PageData.FROM_TYPE_START_APP, H5ApiManager.JSAPI_List, h5Page)) {
                    return false;
                }
                boolean allowed = hasPermission(appIdTag, targetAppId, "JSAPI_SP_Config_startApp_appId", h5Page);
                if (!allowed) {
                    return false;
                }
                String openUrl = uri.getQueryParameter("url");
                if (TextUtils.isEmpty(openUrl)) {
                    return allowed;
                }
                Uri openUri = H5UrlHelper.parseUrl(openUrl);
                if (openUri != null) {
                    return hasPermission(appIdTag, openUri.getHost(), "JSAPI_SP_Config_startApp_url", h5Page);
                }
                return allowed;
            }
        } catch (Throwable e) {
            H5Log.e(TAG, e);
        }
        if (scheme.startsWith("http")) {
            return true;
        }
        H5Log.d(TAG, "hasPermissionOnScheme...non-http url, no permission");
        return false;
    }

    public String getWebViewTag() {
        return "MINI-PROGRAM-WEB-VIEW-TAG";
    }

    public boolean httpRequestShouldUseSpdy(String appId, H5Page h5Page, String url) {
        if (!hasPermissionFile(appId, h5Page)) {
            return true;
        }
        if (TextUtils.isEmpty(url)) {
            return true;
        }
        if (TinyAppConfig.getInstance().shouldHttpsUseSpdy()) {
            H5Log.d(TAG, "httpRequestShouldUseSpdy...switch is open");
            return true;
        }
        List<String> blacklist = TinyAppConfig.getInstance().getHttpsUseSpdyBlacklist();
        if (blacklist == null || blacklist.isEmpty()) {
            return !url.startsWith("https");
        }
        for (String matchRegex : blacklist) {
            if (H5PatternHelper.matchRegex(matchRegex, url)) {
                return true;
            }
        }
        return !url.startsWith("https");
    }

    public int shouldInterceptJSApiCall(H5Event h5Event, String appId, H5BridgeContext h5BridgeContext, H5Page h5Page) {
        if (h5Event == null) {
            return 0;
        }
        String action = h5Event.getAction();
        if (!TextUtils.equals(action, "request")) {
            TextUtils.equals(action, "httpRequest");
        }
        if (hasPermissionFile(appId, h5Page)) {
            JSONArray whiteJsApiJsonArray = TinyAppConfig.getInstance().getWhiteJsApiJsonArray();
            if (whiteJsApiJsonArray != null && whiteJsApiJsonArray.contains(action)) {
                H5Log.d(TAG, "whiteJsApiJsonArray contain this " + action);
            } else if (!WHITE_JSAPI_LIST.contains(action) && !setPermission(h5Event, appId, h5BridgeContext, false, h5Page)) {
                return 1;
            }
        } else if (!H5Utils.getBoolean(h5Page.getParams(), (String) "isTinyApp", false) || !TinyAppConfig.getInstance().enableTinyIgnorePermission()) {
            return 0;
        } else {
            H5Log.d(TAG, " is TinyApp but not has permissionFile");
            H5LogProvider h5LogProvider = (H5LogProvider) Nebula.getProviderManager().getProvider(H5LogProvider.class.getName());
            if (h5LogProvider != null) {
                h5LogProvider.log("h5enableTinyIgnorePermission", null, null, appId, action);
            }
            String tag = callFromWebView(h5Page);
            if (!TextUtils.isEmpty(tag)) {
                appId = tag;
            }
            if (this.mApiBizPermissionManager.a((Context) h5Event.getActivity(), appId, h5Event, h5BridgeContext)) {
                return 1;
            }
        }
        return 2;
    }

    public LoadingView getLoadingViewFromTiny(H5StartAppInfo h5StartAppInfo) {
        return null;
    }

    public void setIfNeedUpDownAnimWithoutAppinfo(H5StartAppInfo h5StartAppInfo) {
    }

    public boolean hasWebARPermission(String jsApiName, String hostURL, H5Page h5Page) {
        H5Log.d(TAG, "hasWebARPermission..." + jsApiName + ", " + hostURL);
        if (!hasPermissionFile(null, h5Page)) {
            return true;
        }
        return hasPermission(null, jsApiName, H5ApiManager.JSAPI_List, h5Page);
    }

    public void hasWebARCameraPermission(String hostURL, H5Page h5Page, H5PermissionCallBack h5PermissionCallBack) {
        if (h5PermissionCallBack != null) {
            String tag = callFromWebView(h5Page);
            if (TextUtils.isEmpty(tag)) {
                h5PermissionCallBack.allow();
                return;
            }
            Context context = null;
            if (h5Page.getContext() != null) {
                context = h5Page.getContext().getContext();
            }
            this.mApiBizPermissionManager.a(tag, context, h5PermissionCallBack);
        }
    }

    public String getDebugAuthRpcName() {
        if (TinyAppConfig.getInstance().isUseNewDebugServer()) {
            return DEBUG_AUTH_RPC_NAME;
        }
        return HPMWEB_DEBUG_AUTH_RPC_NAME;
    }

    public String getDebugAppInfoRpcName() {
        if (TinyAppConfig.getInstance().isUseNewDebugServer()) {
            return DEBUG_APP_INFO_RPC_NAME;
        }
        return H5BaseAppBizRpcProvider.bugMeRpcName;
    }

    public boolean isUseTinyAppManagerProcess() {
        return TinyAppConfig.getInstance().isUseTinyAppManagerProcess();
    }

    public boolean isSetAppxMinVersionValid(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return false;
        }
        Set blacklist = TinyAppConfig.getInstance().getSetMinAppxBlacklist();
        if (blacklist == null || blacklist.isEmpty()) {
            return false;
        }
        if (blacklist.contains("all")) {
            H5Log.d(TAG, "isSetAppxMinVersionValid...blacklist all");
            return false;
        } else if (!blacklist.contains(appId)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isWebWorkerSupported() {
        return TinyAppConfig.getInstance().isUseSysWebView();
    }

    public boolean isUCFailFallbackAppSupported(String appId) {
        Set fallbackAppBlacklist = TinyAppConfig.getInstance().getUcFailFallbackAppBlacklist();
        if (fallbackAppBlacklist == null || fallbackAppBlacklist.isEmpty()) {
            return true;
        }
        if (fallbackAppBlacklist.contains("all") || fallbackAppBlacklist.contains(appId)) {
            return false;
        }
        return true;
    }

    public int getAllowCreatedWorkerMaxCount() {
        return TinyAppConfig.getInstance().getMaxWorkerCount();
    }

    public void doPreloadJob(String appId) {
        if (!TextUtils.isEmpty(appId)) {
            H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                public final void run() {
                    try {
                        if (!TinyAppConfig.getInstance().canStartPreload()) {
                            H5Log.d(H5ApiManagerImpl.TAG, "doPreloadJob...closed");
                        } else {
                            WalletTinyappUtils.getMultiProcessService();
                        }
                    } catch (Throwable e) {
                        H5Log.e(H5ApiManagerImpl.TAG, "doPreloadJob...e=" + e);
                    }
                }
            });
        }
    }

    public String getAppxSDKVersion(String appId) {
        return TinyAppStorage.getInstance().getAppxVersion(appId);
    }

    public Set<String> getTransferToTinySet() {
        return TinyAppConfig.getInstance().getH5TransferTinyArray();
    }

    private boolean checkJSApiExist(H5CoreNode target, String apiName) {
        if (target == null || TextUtils.isEmpty(apiName)) {
            return false;
        }
        boolean exist = false;
        while (!TextUtils.isEmpty(apiName) && !exist && target != null) {
            H5PluginManager pluginManager = target.getPluginManager();
            target = target.getParent();
            exist = pluginManager.canHandle(apiName);
        }
        return exist;
    }

    private String callFromWebView(H5Page h5Page) {
        if (h5Page == null) {
            return null;
        }
        return H5Utils.getString(h5Page.getParams(), (String) "MINI-PROGRAM-WEB-VIEW-TAG");
    }

    private boolean shouldInterceptWebViewJsapi(H5Event h5Event, String action, String appId, String webviewAppId) {
        if (TextUtils.isEmpty(action)) {
            return true;
        }
        if (TinyAppConfig.getInstance().isEmbedWebViewServerInterceptOpen() && isServerInterceptWebViewJsapi()) {
            H5NewJSApiPermissionProvider h5NewJSApiPermissionProvider = (H5NewJSApiPermissionProvider) H5Utils.getProvider(H5NewJSApiPermissionProvider.class.getName());
            if (h5NewJSApiPermissionProvider != null) {
                H5Page page = h5Event.getH5page();
                if (page != null && h5NewJSApiPermissionProvider.hasPermissionByUrl(page.getUrl(), action, h5Event.getParam()) == 1) {
                    return false;
                }
            }
        }
        String[] strArr = WEBVIEW_API_INIT_LIST;
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            if (action.equals(strArr[i])) {
                return false;
            }
        }
        List<String> appIdWhitelist = TinyAppConfig.getInstance().getWebviewJsapiWhitelist();
        if (appIdWhitelist != null) {
            for (String equals : appIdWhitelist) {
                if (TextUtils.equals(equals, appId)) {
                    H5Log.d(TAG, "shouldInterceptWebViewJsapi...appId in white list:" + action);
                    return false;
                }
            }
        }
        List webviewApiList = TinyAppConfig.getInstance().getSupportedWebviewApiList();
        if (webviewApiList == null || webviewApiList.isEmpty()) {
            H5Log.d(TAG, "shouldInterceptWebViewJsapi...webview api list is null, not allowed:" + action);
            return true;
        } else if (webviewApiList.contains(action)) {
            return false;
        } else {
            H5Page h5Page = h5Event.getH5page();
            String url = h5Page != null ? h5Page.getUrl() : null;
            if (!TextUtils.isEmpty(url)) {
                try {
                    String urlDomain = H5UrlHelper.getHost(url);
                    JSONArray domainList = TinyAppConfig.getInstance().getWebViewJSAPIDomainWhiteList();
                    if (domainList != null && !domainList.isEmpty()) {
                        int i2 = 0;
                        int size = domainList.size();
                        while (i2 < size) {
                            String domain = domainList.getString(i2);
                            if (TextUtils.isEmpty(domain) || !Pattern.compile(domain).matcher(urlDomain).matches()) {
                                i2++;
                            } else {
                                H5Log.d(TAG, "shouldInterceptWebViewJsapi, match domain: " + urlDomain);
                                return false;
                            }
                        }
                    }
                } catch (Exception e) {
                    H5Log.e(TAG, (Throwable) e);
                }
            }
            TinyAppMixActionService mixActionService = TinyAppService.get().getMixActionService();
            if (mixActionService != null) {
                return mixActionService.shouldInterceptWebviewOpenAppId(appId, webviewAppId);
            }
            H5Log.d(TAG, "shouldInterceptWebViewJsapi...not allowed: " + action);
            return true;
        }
    }

    private boolean isServerInterceptWebViewJsapi() {
        String h5_newJsapiPermissionConfigStr = null;
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            h5_newJsapiPermissionConfigStr = h5ConfigProvider.getConfigWithProcessCache("h5_newJsapiPermissionConfig");
        }
        JSONObject h5_newJsapiPermissionConfigObj = H5Utils.parseObject(h5_newJsapiPermissionConfigStr);
        if (h5_newJsapiPermissionConfigObj == null || h5_newJsapiPermissionConfigObj.isEmpty()) {
            return false;
        }
        return h5_newJsapiPermissionConfigObj.getBoolean("canIntercept").booleanValue();
    }

    public boolean shouldInterceptWebViewNaviJsApi(String appId, String action) {
        if (TextUtils.isEmpty(action)) {
            return false;
        }
        for (String naviApi : WEBVIEW_NAVI_API_LIST) {
            if (action.equals(naviApi)) {
                return false;
            }
        }
        List<String> appIdWhitelist = TinyAppConfig.getInstance().getWebviewJsapiWhitelist();
        if (appIdWhitelist != null) {
            for (String equals : appIdWhitelist) {
                if (TextUtils.equals(equals, appId)) {
                    H5Log.d(TAG, "shouldInterceptWebViewNaviJsApi...appId in white list:" + action);
                    return false;
                }
            }
        }
        H5Log.d(TAG, "shouldInterceptWebViewNaviJsApi...not allowed: " + action);
        return true;
    }

    public Map<String, Boolean> getAllPermissions(String uid, String appid) {
        return a.a(uid, appid);
    }

    public void changePermissionByKey(String uid, String appid, String key, boolean opened) {
        this.mApiBizPermissionManager.a(uid, appid, key, opened);
    }

    public void removeAllPermissionInfo(String appId) {
        a.a(appId);
    }

    public boolean isDomainInBackList(H5Page h5Page, String content) {
        if (h5Page == null || TextUtils.isEmpty(content)) {
            return false;
        }
        if (TinyAppEnvMode.valueOf(h5Page) == TinyAppEnvMode.DEVELOP) {
            return false;
        }
        List<String> blackList = TinyAppConfig.getInstance().getHttpDomainBlacklist();
        if (blackList == null || blackList.isEmpty()) {
            return false;
        }
        for (String compile : blackList) {
            Matcher matcher = Pattern.compile(compile).matcher(content);
            if (matcher != null && matcher.matches()) {
                return true;
            }
        }
        return false;
    }
}
