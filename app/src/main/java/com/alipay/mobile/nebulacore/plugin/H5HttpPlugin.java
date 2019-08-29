package com.alipay.mobile.nebulacore.plugin;

import android.content.SharedPreferences;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.webkit.WebResourceResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.transport.http.RequestMethodConstants;
import com.alipay.mobile.common.transportext.biz.spdy.apache.OkApacheClient;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.process.H5HttpRequestResult;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5TinyDebugProvider;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5CookieUtil;
import com.alipay.mobile.nebula.util.H5DomainUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5PatternHelper;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.autonavi.minimap.ajx3.util.Constants;
import com.taobao.tao.remotebusiness.js.MtopJSBridge.MtopJSParam;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.util.EntityUtils;

public class H5HttpPlugin extends H5SimplePlugin {
    public static final int DEFAULT_TIMEOUT = 30000;
    public static final String HTTP_REQUEST = "httpRequest";
    public static final String TAG = "H5HttpPlugin";
    private AndroidHttpClient a;
    private boolean b = false;
    /* access modifiers changed from: private */
    public H5Page c;
    /* access modifiers changed from: private */
    public String d;
    /* access modifiers changed from: private */
    public String e;
    /* access modifiers changed from: private */
    public String f;
    /* access modifiers changed from: private */
    public String g;
    /* access modifiers changed from: private */
    public String h;
    /* access modifiers changed from: private */
    public String i;
    private H5EventHandlerService j;
    private String k = "";

    private static void a(JSONObject result, H5BridgeContext bridgeContext) {
        if (bridgeContext != null) {
            bridgeContext.sendBridgeResult(result);
        }
    }

    private static void a(int resultCode, H5BridgeContext bridgeContext) {
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) Integer.valueOf(resultCode));
        a(result, bridgeContext);
    }

    static void a(String errorMsg, H5BridgeContext bridgeContext) {
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) Integer.valueOf(12));
        result.put((String) "errorMsg", (Object) errorMsg);
        a(result, bridgeContext);
    }

    public void onRelease() {
        this.b = true;
        this.c = null;
        try {
            if (this.a != null) {
                H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
                    public void run() {
                        H5HttpPlugin.this.a();
                    }
                });
            }
        } catch (Exception e2) {
            H5Log.e(TAG, "exception detail", e2);
        }
    }

    /* access modifiers changed from: private */
    public void a() {
        if (this.a != null) {
            this.a.close();
            this.a = null;
        }
    }

    public boolean handleEvent(final H5Event event, final H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if (event.getTarget() instanceof H5Page) {
            this.c = (H5Page) event.getTarget();
        }
        if ("httpRequest".equals(action)) {
            H5Utils.getExecutor("RPC").execute(new Runnable() {
                public void run() {
                    try {
                        Bundle startParams = H5HttpPlugin.this.c.getParams();
                        H5HttpPlugin.this.d = H5Utils.getString(startParams, (String) "appId");
                        H5HttpPlugin.this.e = H5Utils.getString(startParams, (String) H5Param.PUBLIC_ID);
                        H5HttpPlugin.this.f = H5Utils.getString(startParams, (String) "release_type");
                        H5HttpPlugin.this.g = H5Utils.getString(startParams, (String) "appVersion");
                        H5HttpPlugin.this.h = H5Utils.getString(startParams, (String) H5AppUtil.package_nick);
                        H5HttpPlugin.this.i = H5Utils.getString(startParams, (String) H5Param.LONG_BIZ_SCENARIO);
                        boolean hasPermissionFile = false;
                        if (Nebula.getH5TinyAppService() != null) {
                            hasPermissionFile = Nebula.getH5TinyAppService().hasPermissionFile(H5HttpPlugin.this.d, H5HttpPlugin.this.c);
                        }
                        boolean enableProxy = false;
                        if (hasPermissionFile) {
                            enableProxy = Nebula.getH5TinyAppService().hasPermission(H5HttpPlugin.this.d, null, H5ApiManager.Enable_Proxy, H5HttpPlugin.this.c);
                        }
                        if (H5HttpPlugin.b(H5HttpPlugin.this.d) && H5HttpPlugin.this.b() != null) {
                            H5HttpPlugin.this.a(event, bridgeContext, false);
                        } else if (!enableProxy || H5HttpPlugin.this.b() == null) {
                            H5HttpPlugin.this.a(event, bridgeContext);
                        } else {
                            H5HttpPlugin.this.a(event, bridgeContext, true);
                        }
                    } catch (Throwable throwable) {
                        H5Log.e((String) H5HttpPlugin.TAG, throwable);
                        H5HttpPlugin.a(throwable.getMessage(), bridgeContext);
                    }
                }
            });
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static boolean b(String appId) {
        JSONArray jsonArray = H5Utils.parseArray(H5Environment.getConfigWithProcessCache("h5_httpRequest_useAlipayNetAppList"));
        if (jsonArray == null || !jsonArray.contains(appId)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public H5EventHandlerService b() {
        if (this.j == null) {
            this.j = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
        }
        return this.j;
    }

    private boolean a(String url, String responseType, String responseCharset, H5BridgeContext bridgeContext) {
        if (this.c == null || this.c.getParams() == null) {
            return false;
        }
        String host = H5Utils.getString(this.c.getParams(), (String) H5Param.ONLINE_HOST);
        if (url.startsWith("http") && !url.contains(host)) {
            return false;
        }
        String realPath = a(url, this.c.getParams());
        H5Log.d(TAG, "getFromPkg realPath " + realPath);
        H5Session h5Session = this.c.getSession();
        if (h5Session != null) {
            H5ContentProvider h5ContentProvider = h5Session.getWebProvider();
            if (h5ContentProvider != null) {
                WebResourceResponse webResourceResponse = h5ContentProvider.getContent(realPath, true);
                if (webResourceResponse != null) {
                    try {
                        InputStream inputStream = webResourceResponse.getData();
                        String content = null;
                        if (inputStream != null) {
                            byte[] bytes = H5Utils.readBytes(inputStream);
                            if ("base64".equals(responseType)) {
                                content = Base64.encodeToString(bytes, 2);
                            } else if (!TextUtils.isEmpty(responseCharset)) {
                                content = new String(bytes, responseCharset);
                            } else {
                                content = new String(bytes);
                            }
                            inputStream.close();
                        }
                        JSONObject result = new JSONObject();
                        result.put((String) "data", (Object) content);
                        bridgeContext.sendBridgeResult(result);
                        return true;
                    } catch (Throwable throwable) {
                        H5Log.e((String) TAG, throwable);
                    }
                }
            }
        }
        return false;
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction("httpRequest");
    }

    private static String a(String oriUrl, Bundle startParams) {
        String entryUrl = H5Utils.getString(startParams, (String) "url");
        if (!TextUtils.isEmpty(entryUrl)) {
            return H5Utils.getAbsoluteUrlV2(entryUrl, oriUrl, null);
        }
        return oriUrl;
    }

    private boolean c(String reqUrl) {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (this.c == null || h5ConfigProvider == null || !"yes".equalsIgnoreCase(h5ConfigProvider.getConfig("h5_shouldCheckSPPermission"))) {
            return true;
        }
        String currentUrl = this.c.getUrl();
        if (h5ConfigProvider.isAliDomains(currentUrl) || h5ConfigProvider.isAlipayDomains(currentUrl)) {
            return true;
        }
        if (!h5ConfigProvider.isAliDomains(reqUrl) && !h5ConfigProvider.isAlipayDomains(reqUrl)) {
            return true;
        }
        H5LogUtil.logNebulaTech(H5LogData.seedId("h5_al_jsapi_permission_cors").param1().add("reqUrl", reqUrl).param2().add("currentUrl", currentUrl).param3().add(this.d, null));
        return false;
    }

    /* access modifiers changed from: private */
    public void a(H5Event event, H5BridgeContext bridgeContext) {
        String str;
        Header[] allHeaders;
        if (event == null || event.getParam() == null) {
            a(2, bridgeContext);
            return;
        }
        JSONObject params = event.getParam();
        final String url = H5Utils.getString(params, (String) "url");
        if (TextUtils.isEmpty(url) || !url.startsWith("http")) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
        } else if (!c(url)) {
            bridgeContext.sendNoRigHtToInvoke();
        } else {
            final String method = H5Utils.getString(params, (String) "method", (String) "GET");
            JSONArray headersArray = H5Utils.getJSONArray(params, "headers", null);
            JSONObject headerJsonOb = H5Utils.getJSONObject(params, "headers", null);
            final String data = H5Utils.getString(params, (String) "data");
            int timeout = H5Utils.getInt(params, (String) "timeout", -1);
            String responseType = H5Utils.getString(params, (String) "responseType");
            String responseCharset = H5Utils.getString(params, (String) "responseCharset");
            HttpGet httpGet = null;
            if (TextUtils.isEmpty(method) || "GET".equalsIgnoreCase(method)) {
                httpGet = new HttpGet(url);
                if (a(url, responseType, responseCharset, bridgeContext)) {
                    return;
                }
            } else if (RequestMethodConstants.DELETE_METHOD.equalsIgnoreCase(method)) {
                httpGet = new HttpDelete(url);
            } else if ("HEADER".equalsIgnoreCase(method)) {
                httpGet = new HttpHead(url);
            } else if (RequestMethodConstants.PUT_METHOD.equalsIgnoreCase(method)) {
                httpGet = new HttpPut(url);
            } else if ("POST".equalsIgnoreCase(method)) {
                HttpGet httpPost = new HttpPost(url);
                if (data != null) {
                    HttpEntity entity = null;
                    try {
                        HttpEntity byteArrayEntity = new ByteArrayEntity(data.getBytes("UTF-8"));
                        entity = byteArrayEntity;
                    } catch (UnsupportedEncodingException e2) {
                        H5Log.e(TAG, "exception detail", e2);
                    }
                    httpPost.setEntity(entity);
                }
                httpGet = httpPost;
                if (!params.containsKey("headers")) {
                    httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
                }
            }
            if (headersArray != null) {
                try {
                    if (!headersArray.isEmpty()) {
                        Iterator<Object> it = headersArray.iterator();
                        while (it.hasNext()) {
                            for (Entry entry : ((JSONObject) it.next()).entrySet()) {
                                try {
                                    httpGet.addHeader((String) entry.getKey(), (String) entry.getValue());
                                } catch (ClassCastException e3) {
                                    H5Log.e(TAG, "exception detail", e3);
                                }
                            }
                        }
                    }
                } catch (Exception e4) {
                    H5Log.e((String) TAG, (Throwable) e4);
                }
            }
            if (headerJsonOb != null) {
                if (!headerJsonOb.isEmpty()) {
                    for (String key : headerJsonOb.keySet()) {
                        try {
                            httpGet.addHeader(key, headerJsonOb.get(key).toString());
                        } catch (Exception e5) {
                            H5Log.e((String) TAG, (Throwable) e5);
                        }
                    }
                }
            }
            if (e(url)) {
                if (!TextUtils.isEmpty(H5Utils.ldcLevel)) {
                    httpGet.addHeader("x-ldcid-level", H5Utils.ldcLevel);
                }
                if (!TextUtils.isEmpty(this.k)) {
                    httpGet.addHeader("x-user-group", this.k);
                }
            }
            if (TextUtils.isEmpty(responseCharset)) {
                str = "UTF-8";
            } else {
                str = responseCharset;
            }
            httpGet.addHeader("Accept-Charset", str);
            if (TextUtils.equals(method, "POST") && !httpGet.containsHeader("Content-Type")) {
                httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
            }
            if (H5Utils.getBoolean(this.c.getParams(), (String) "isTinyApp", false) && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_getReferWithAppId")) && this.c != null) {
                httpGet.addHeader("referer", d(this.c.getUrl()));
            } else if (!httpGet.containsHeader("referer") && this.c != null) {
                httpGet.addHeader("referer", this.c.getUrl());
            }
            String cookieStr = H5CookieUtil.getCookie(this.c.getParams(), url);
            if (!TextUtils.isEmpty(cookieStr)) {
                httpGet.addHeader("Cookie", cookieStr);
            }
            if (!(this.a != null || this.c == null || this.c.getWebView() == null || this.c.getWebView().getSettings() == null)) {
                this.a = AndroidHttpClient.newInstance(this.c.getWebView().getSettings().getUserAgentString());
            }
            if (timeout < 0) {
                timeout = 30000;
            }
            if (!(this.a == null || this.a.getParams() == null)) {
                this.a.getParams().setParameter("http.connection.timeout", Integer.valueOf(timeout));
            }
            final String uuid = UUID.randomUUID().toString();
            final HttpGet httpGet2 = httpGet;
            final H5TinyDebugProvider h5TinyDebugProvider = (H5TinyDebugProvider) H5Utils.getProvider(H5TinyDebugProvider.class.getName());
            if (h5TinyDebugProvider != null && (h5TinyDebugProvider.isRemoteDebugConnected(this.d) || h5TinyDebugProvider.isVConsolePanelOpened())) {
                final String targetAppId = this.d;
                H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                    public void run() {
                        Header[] allHeaders;
                        JSONObject requestHeaders = new JSONObject();
                        for (Header header : httpGet2.getAllHeaders()) {
                            requestHeaders.put(header.getName(), (Object) header.getValue());
                        }
                        JSONObject requestInfo = new JSONObject();
                        requestInfo.put((String) "headers", (Object) requestHeaders);
                        requestInfo.put((String) "method", (Object) method);
                        requestInfo.put((String) "url", (Object) url);
                        requestInfo.put((String) OkApacheClient.REQUESTID, (Object) uuid);
                        if (data != null && data.getBytes().length > 0) {
                            try {
                                requestInfo.put((String) "postBody", (Object) new String(data.getBytes(), "UTF-8"));
                            } catch (UnsupportedEncodingException e2) {
                                H5Log.w(H5HttpPlugin.TAG, "utf-8 not support");
                            }
                        }
                        if (h5TinyDebugProvider != null) {
                            h5TinyDebugProvider.sendMsgToRemoteWorkerOrVConsole(targetAppId, "tinyAppRemoteDebug_network_request", requestInfo.toJSONString());
                        }
                    }
                });
            }
            try {
                H5Log.d(TAG, "check point 1, ready to execute");
                if (this.a != null) {
                    HttpResponse res = this.a.execute(httpGet2);
                    if (res == null) {
                        a(H5Environment.getResources().getString(R.string.h5_server_error), bridgeContext);
                        a(url, (String) "12", H5Environment.getResources().getString(R.string.h5_server_error), uuid);
                        return;
                    }
                    H5Log.d(TAG, "check point 3, execute done");
                    if (this.b) {
                        return;
                    }
                    if (res.getStatusLine() != null) {
                        final int statusCode = res.getStatusLine().getStatusCode();
                        String reasonPhrase = res.getStatusLine().getReasonPhrase();
                        JSONObject result = new JSONObject();
                        result.put((String) "status", (Object) Integer.valueOf(statusCode));
                        HttpEntity entity2 = res.getEntity();
                        String content = null;
                        if (entity2 != null) {
                            if ("base64".equals(responseType)) {
                                content = Base64.encodeToString(H5Utils.readBytes(entity2.getContent()), 2);
                            } else if (!TextUtils.isEmpty(responseCharset)) {
                                content = EntityUtils.toString(entity2, responseCharset);
                            } else {
                                content = EntityUtils.toString(entity2, "UTF-8");
                            }
                        }
                        result.put((String) "data", (Object) content);
                        final JSONArray headers = new JSONArray();
                        if (res.getAllHeaders() != null && res.getAllHeaders().length > 0) {
                            for (Header header : res.getAllHeaders()) {
                                String headerName = header.getName();
                                if (headerName != null) {
                                    String headerValue = header.getValue();
                                    JSONObject element = new JSONObject();
                                    element.put(headerName, (Object) headerValue);
                                    headers.add(element);
                                    if (headerName.equalsIgnoreCase("set-cookie")) {
                                        H5CookieUtil.setCookie(this.c.getParams(), url, headerValue);
                                    }
                                }
                            }
                            result.put((String) "headers", (Object) headers);
                        }
                        result.put((String) "error", (Object) Integer.valueOf(0));
                        if (statusCode == 502) {
                            result.put((String) "error", (Object) Integer.valueOf(13));
                            result.put((String) "errorMsg", (Object) reasonPhrase);
                            a(url, (String) "13", reasonPhrase, uuid);
                        } else if (statusCode == 403) {
                            result.put((String) "error", (Object) Integer.valueOf(11));
                            result.put((String) "errorMsg", (Object) reasonPhrase);
                            a(url, (String) "11", reasonPhrase, uuid);
                        }
                        if (bridgeContext != null) {
                            bridgeContext.sendBridgeResult(result);
                        }
                        if (h5TinyDebugProvider == null) {
                            return;
                        }
                        if (h5TinyDebugProvider.isRemoteDebugConnected(this.d) || h5TinyDebugProvider.isVConsolePanelOpened()) {
                            final String statusText = res.getStatusLine().toString();
                            final String resContent = content;
                            final String str2 = url;
                            final String str3 = uuid;
                            final H5TinyDebugProvider h5TinyDebugProvider2 = h5TinyDebugProvider;
                            final String str4 = this.d;
                            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                                public void run() {
                                    int bodyLength = 1048576;
                                    JSONObject responseInfo = new JSONObject();
                                    responseInfo.put((String) "headers", (Object) headers);
                                    responseInfo.put((String) "url", (Object) str2);
                                    responseInfo.put((String) "status", (Object) Integer.valueOf(statusCode));
                                    responseInfo.put((String) "statusText", (Object) statusText);
                                    responseInfo.put((String) OkApacheClient.REQUESTID, (Object) str3);
                                    if (resContent.length() <= 1048576) {
                                        bodyLength = resContent.length();
                                    }
                                    responseInfo.put((String) Constants.BODY, (Object) resContent.substring(0, bodyLength));
                                    if (h5TinyDebugProvider2 != null) {
                                        h5TinyDebugProvider2.sendMsgToRemoteWorkerOrVConsole(str4, "tinyAppRemoteDebug_network_response", responseInfo.toJSONString());
                                    }
                                }
                            });
                            return;
                        }
                        return;
                    }
                    a(3, bridgeContext);
                    a(url, (String) "3", (String) "", uuid);
                }
            } catch (Exception e6) {
                H5Log.e(TAG, "exception detail", e6);
                a(e6.getMessage(), bridgeContext);
                a(url, (String) "12", e6.getMessage(), uuid);
                H5LogUtil.logNebulaTech(H5LogData.seedId(TAG).param4().add("executeException", e6));
            }
        }
    }

    private void a(String url, String code, String msg, String uuid) {
        if (this.c != null && this.c.getPageData() != null) {
            final String appId = this.c.getPageData().getAppId();
            final H5TinyDebugProvider provider = (H5TinyDebugProvider) H5Utils.getProvider(H5TinyDebugProvider.class.getName());
            if (provider == null) {
                return;
            }
            if (provider.isRemoteDebugConnected(appId) || provider.isVConsolePanelOpened()) {
                final String str = url;
                final String str2 = code;
                final String str3 = msg;
                final String str4 = uuid;
                H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                    public void run() {
                        JSONObject errorInfo = new JSONObject();
                        errorInfo.put((String) "url", (Object) str);
                        errorInfo.put((String) "errorCode", (Object) str2);
                        errorInfo.put((String) "errorMsg", (Object) str3);
                        errorInfo.put((String) OkApacheClient.REQUESTID, (Object) str4);
                        if (provider != null) {
                            provider.sendMsgToRemoteWorkerOrVConsole(appId, "tinyAppRemoteDebug_network_error", errorInfo.toJSONString());
                        }
                    }
                });
            }
        }
    }

    private String d(String url) {
        Uri uri = H5UrlHelper.parseUrl(url);
        if (uri == null || TextUtils.isEmpty(this.d) || TextUtils.isEmpty(this.g)) {
            return url;
        }
        String path = uri.getPath();
        if (TextUtils.isEmpty(path)) {
            return url;
        }
        try {
            int pathIndex = url.indexOf(path);
            if (pathIndex == -1) {
                return url;
            }
            return url.substring(0, pathIndex) + "/" + this.d + "/" + this.g + url.substring(pathIndex, url.length());
        } catch (Throwable thr) {
            H5Log.e(TAG, "getReferWithAppId subString", thr);
            return url;
        }
    }

    private boolean e(String url) {
        JSONObject jsonObjLdcLevel = H5Environment.getConfigJSONObject("h5_ldcLevelConfig");
        Uri uri = H5UrlHelper.parseUrl(url);
        if (uri == null || jsonObjLdcLevel == null || !H5Utils.getBoolean(jsonObjLdcLevel, (String) "enableHttpRequest", false)) {
            return false;
        }
        boolean a2 = a(uri.getHost(), H5Utils.getJSONArray(jsonObjLdcLevel, "domainList", null));
        this.k = H5Utils.getString(jsonObjLdcLevel, (String) "group", (String) "");
        return a2;
    }

    private static boolean a(String url, JSONArray whiteList) {
        if (whiteList == null || whiteList.isEmpty()) {
            return false;
        }
        for (int i2 = 0; i2 < whiteList.size(); i2++) {
            String whiteItem = whiteList.getString(i2);
            if (!TextUtils.isEmpty(whiteItem) && H5PatternHelper.matchRegex(whiteItem, url)) {
                return true;
            }
        }
        return false;
    }

    private boolean f(String url) {
        if (H5Utils.isDebug() && c()) {
            return false;
        }
        H5ApiManager tinyappPermission = (H5ApiManager) Nebula.getProviderManager().getProvider(H5ApiManager.class.getName());
        if (tinyappPermission != null && !tinyappPermission.httpRequestShouldUseSpdy(this.d, this.c, url)) {
            return false;
        }
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) Nebula.getProviderManager().getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            String appIdList = h5ConfigProvider.getConfigWithProcessCache("h5HttpRequestUseSpdyOnAppId");
            if (!TextUtils.isEmpty(appIdList)) {
                JSONArray jsonArray = H5Utils.parseArray(appIdList);
                if (jsonArray != null && jsonArray.contains(this.d)) {
                    return false;
                }
            }
            String value = h5ConfigProvider.getConfigWithProcessCache("h5HttpRequestUseSpdyOnUrl");
            if (!TextUtils.isEmpty(value)) {
                if (!H5DomainUtil.isSomeDomainInternal(url, value)) {
                    return true;
                }
                return false;
            }
        }
        return true;
    }

    private static boolean c() {
        SharedPreferences preferences = H5Utils.getContext().getSharedPreferences("h5_switchcontrol", 0);
        boolean disableSpdy = false;
        if (preferences != null) {
            disableSpdy = !preferences.getBoolean("enableSPDY", true);
        } else {
            H5Log.d(TAG, "disableSpdyByScanQRCode preferences == null");
        }
        H5Log.d(TAG, "disableSpdyByScanQRCode disableSpdy : " + disableSpdy);
        return disableSpdy;
    }

    /* access modifiers changed from: private */
    public void a(H5Event event, H5BridgeContext bridgeContext, boolean forceSpdyLink) {
        int timeout;
        String str;
        H5HttpRequestResult h5HttpRequestResult;
        if (event == null || event.getParam() == null) {
            a(2, bridgeContext);
            return;
        }
        JSONObject params = event.getParam();
        String url = H5Utils.getString(params, (String) "url");
        if (TextUtils.isEmpty(url) || !url.startsWith("http")) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        String method = H5Utils.getString(params, (String) "method", (String) "GET");
        JSONArray headersArray = H5Utils.getJSONArray(params, "headers", null);
        JSONObject headerJsonOb = H5Utils.getJSONObject(params, "headers", null);
        String data = H5Utils.getString(params, (String) "data");
        int tmpTimeout = H5Utils.getInt(params, (String) "timeout", -1);
        if (tmpTimeout < 0) {
            timeout = 30000;
        } else {
            timeout = tmpTimeout;
        }
        String responseType = H5Utils.getString(params, (String) "responseType");
        String responseCharset = H5Utils.getString(params, (String) "responseCharset");
        Map headers = new HashMap();
        byte[] tmpRequestData = null;
        if (!"GET".equalsIgnoreCase(method) || !a(url, responseType, responseCharset, bridgeContext)) {
            if ("POST".equalsIgnoreCase(method)) {
                if (data != null) {
                    try {
                        tmpRequestData = data.getBytes("UTF-8");
                    } catch (Exception e2) {
                        H5Log.e((String) TAG, (Throwable) e2);
                    }
                }
                if (!params.containsKey("headers")) {
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                }
            }
            byte[] requestData = tmpRequestData;
            boolean hasHeadersContentType = false;
            if (headersArray != null) {
                try {
                    if (!headersArray.isEmpty()) {
                        Iterator<Object> it = headersArray.iterator();
                        while (it.hasNext()) {
                            for (Entry entry : ((JSONObject) it.next()).entrySet()) {
                                try {
                                    if ("content-type".equalsIgnoreCase((String) entry.getKey())) {
                                        hasHeadersContentType = true;
                                    }
                                    headers.put(entry.getKey(), (String) entry.getValue());
                                } catch (ClassCastException e3) {
                                    H5Log.e(TAG, "exception detail", e3);
                                }
                            }
                        }
                    }
                } catch (Exception e4) {
                    H5Log.e((String) TAG, (Throwable) e4);
                }
            }
            if (headerJsonOb != null) {
                if (!headerJsonOb.isEmpty()) {
                    for (String key : headerJsonOb.keySet()) {
                        try {
                            String value = headerJsonOb.get(key).toString();
                            if ("content-type".equalsIgnoreCase(key)) {
                                hasHeadersContentType = true;
                            }
                            headers.put(key, value);
                        } catch (Exception e5) {
                            H5Log.e((String) TAG, (Throwable) e5);
                        }
                    }
                }
            }
            if (TextUtils.isEmpty(responseCharset)) {
                str = "UTF-8";
            } else {
                str = responseCharset;
            }
            headers.put("Accept-Charset", str);
            if (e(url)) {
                if (!TextUtils.isEmpty(H5Utils.ldcLevel)) {
                    headers.put("x-ldcid-level", H5Utils.ldcLevel);
                }
                if (!TextUtils.isEmpty(this.k)) {
                    headers.put("x-user-group", this.k);
                }
            }
            if (d()) {
                if ("POST".equalsIgnoreCase(method) && !headers.containsKey("Content-Type")) {
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                }
            } else if ("POST".equalsIgnoreCase(method) && !hasHeadersContentType) {
                headers.put("Content-Type", "application/x-www-form-urlencoded");
            }
            headers.put("su584appid", this.d);
            if (!TextUtils.isEmpty(this.e)) {
                headers.put("su584publicid", this.e);
            }
            if (!TextUtils.isEmpty(this.f)) {
                headers.put("x-release-type", this.f);
            }
            if (!TextUtils.isEmpty(this.g)) {
                headers.put("su584appversion", this.g);
            }
            if (!TextUtils.isEmpty(this.h)) {
                headers.put("su584tinyappversion", this.h);
            }
            if (!TextUtils.isEmpty(this.i)) {
                headers.put("su584bizscenario", this.i);
            }
            headers.put("su584appletpage", this.c.getUrl());
            if (H5Utils.getBoolean(this.c.getParams(), (String) "isTinyApp", false) && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_getReferWithAppId")) && this.c != null) {
                headers.put("referer", d(this.c.getUrl()));
            } else if (!headers.containsKey("referer") && this.c != null) {
                headers.put("referer", this.c.getUrl());
            }
            long time = System.currentTimeMillis();
            String cookieStr = H5CookieUtil.getCookie(this.c.getParams(), url);
            H5Log.d(TAG, "getCookie cost " + (System.currentTimeMillis() - time));
            if (!TextUtils.isEmpty(cookieStr)) {
                headers.put("Cookie", cookieStr);
            }
            if (!(headers.containsKey(H5AppHttpRequest.HEADER_UA) || headers.containsKey(MtopJSParam.USER_AGENT) || this.c == null || this.c.getWebView() == null || this.c.getWebView().getSettings() == null)) {
                headers.put(H5AppHttpRequest.HEADER_UA, this.c.getWebView().getSettings().getUserAgentString());
            }
            try {
                H5Log.d(TAG, "check point 1, ready to execute");
                H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                if (h5EventHandlerService != null) {
                    if (forceSpdyLink) {
                        h5HttpRequestResult = h5EventHandlerService.httpRequest(url, method, headers, requestData, (long) timeout, responseType, responseCharset, f(url), this.c);
                    } else {
                        h5HttpRequestResult = h5EventHandlerService.httpRequest(url, method, headers, requestData, (long) timeout, responseType, responseCharset, false, this.c);
                    }
                    JSONObject result = new JSONObject();
                    result.put((String) "status", (Object) Integer.valueOf(h5HttpRequestResult.responseStatues));
                    result.put((String) "data", (Object) h5HttpRequestResult.responseContext);
                    result.put((String) "headers", (Object) h5HttpRequestResult.responseHeader);
                    result.put((String) "error", (Object) Integer.valueOf(h5HttpRequestResult.error));
                    if (bridgeContext != null) {
                        bridgeContext.sendBridgeResult(result);
                    }
                }
            } catch (Exception e6) {
                a(e6.getMessage(), bridgeContext);
                H5Log.e(TAG, "exception detail", e6);
                H5LogUtil.logNebulaTech(H5LogData.seedId(TAG).param4().add("executeException", e6));
            }
        }
    }

    private static boolean d() {
        if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_httpRequestHeader_caseInsensitive"))) {
            return true;
        }
        return false;
    }
}
