package com.alipay.mobile.nebula.tinypermission;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.loading.LoadingView;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PermissionCallBack;
import com.alipay.mobile.nebula.appcenter.apphandler.H5StartAppInfo;
import java.util.Set;

public interface H5ApiManager {
    public static final String EVENT_List = "EVENT_List";
    public static final String Enable_Proxy = "Enable_Proxy";
    public static final String HttpLink_SubResMimeList = "HttpLink_SubResMimeList";
    public static final String JSAPI_List = "JSAPI_List";
    public static final String JSAPI_SP_Config = "JSAPI_SP_Config";
    public static final String Valid_SubResMimeList = "Valid_SubResMimeList";
    public static final String Webview_Config = "Webview_Config";
    public static final String allowedDomain = "allowedDomain";
    public static final String api_permission = "api_permission";
    public static final String httpRequest = "httpRequest";
    public static final String validDomain = "validDomain";

    void clear(String str);

    void doPreloadJob(String str);

    int getAllowCreatedWorkerMaxCount();

    String getAppxSDKVersion(String str);

    String getDebugAppInfoRpcName();

    String getDebugAuthRpcName();

    LoadingView getLoadingViewFromTiny(H5StartAppInfo h5StartAppInfo);

    Set<String> getTransferToTinySet();

    String getWebViewTag();

    boolean hasPermission(String str, String str2, String str3, H5Page h5Page);

    boolean hasPermissionFile(String str, H5Page h5Page);

    boolean hasPermissionOnIframe(String str, String str2, String str3, H5Page h5Page);

    boolean hasPermissionOnScheme(String str, String str2, H5Page h5Page);

    void hasWebARCameraPermission(String str, H5Page h5Page, H5PermissionCallBack h5PermissionCallBack);

    boolean hasWebARPermission(String str, String str2, H5Page h5Page);

    boolean httpRequestShouldUseSpdy(String str, H5Page h5Page, String str2);

    boolean isDomainInBackList(H5Page h5Page, String str);

    boolean isSetAppxMinVersionValid(String str);

    boolean isUCFailFallbackAppSupported(String str);

    boolean isUseTinyAppManagerProcess();

    boolean isWebWorkerSupported();

    void put(String str, byte[] bArr);

    void putJson(String str, JSONObject jSONObject);

    void setIfNeedUpDownAnimWithoutAppinfo(H5StartAppInfo h5StartAppInfo);

    boolean setPermission(H5Event h5Event, String str, H5BridgeContext h5BridgeContext, boolean z, H5Page h5Page);

    int shouldInterceptJSApiCall(H5Event h5Event, String str, H5BridgeContext h5BridgeContext, H5Page h5Page);
}
