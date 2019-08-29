package com.alipay.mobile.tinyappcommon.h5plugin;

import android.net.ParseException;
import android.net.http.AndroidHttpClient;
import android.text.TextUtils;
import android.util.Base64;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.transport.h5.H5HttpUrlRequest;
import com.alipay.mobile.common.transport.http.RequestMethodConstants;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.common.transportext.biz.spdy.apache.OkApacheClient;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5CookieUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppUtils;
import com.alipay.mobile.tinyappcommon.utils.TinyAppParamUtils;
import com.autonavi.minimap.ajx3.util.Constants;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.util.EntityUtils;

public class TinyAppRequestPlugin extends H5SimplePlugin {
    public static final String ACTION_OPERATE_REQUEST = "operateRequestTask";
    public static final String ACTION_REQUEST = "request";
    public static final int DEFAULT_TIMEOUT = 30000;
    private static final String HEADERS = "headers";
    public static final String TAG = "TinyAppRequestPlugin";
    /* access modifiers changed from: private */
    public static String TINYAPP_REMOTE_DEBUG_NETWORK_REQUEST = "tinyAppRemoteDebug_network_request";
    /* access modifiers changed from: private */
    public static String TINYAPP_REMOTE_DEBUG_NETWORK_RESPONSE = "tinyAppRemoteDebug_network_response";
    private AndroidHttpClient client;
    private boolean hasReleased = false;
    /* access modifiers changed from: private */
    public ConcurrentHashMap<Integer, RequestTask> requestTaskMap = new ConcurrentHashMap<>();

    static class RequestTask {
        final AtomicBoolean abort = new AtomicBoolean(false);
        final AtomicBoolean canAbort = new AtomicBoolean(true);
        final int id;
        volatile HttpUriRequest request;
        volatile H5HttpUrlRequest requestProxy;

        public RequestTask(int id2) {
            this.id = id2;
        }
    }

    static void sendResult(JSONObject result, H5BridgeContext bridgeContext) {
        if (result != null && bridgeContext != null) {
            bridgeContext.sendBridgeResult(result);
        }
    }

    static void sendFailed(int resultCode, H5BridgeContext bridgeContext) {
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) Integer.valueOf(resultCode));
        sendResult(result, bridgeContext);
    }

    static void sendFailed(int resultCode, String errorMsg, H5BridgeContext bridgeContext) {
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) Integer.valueOf(resultCode));
        result.put((String) "errorMessage", (Object) errorMsg);
        sendResult(result, bridgeContext);
    }

    public void onRelease() {
        this.hasReleased = true;
        try {
            if (this.client != null) {
                H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
                    public void run() {
                        TinyAppRequestPlugin.this.closeHttpClient();
                    }
                });
            }
        } catch (Exception e) {
            H5Log.e(TAG, "exception detail", e);
        }
    }

    /* access modifiers changed from: private */
    public void closeHttpClient() {
        if (this.client != null) {
            this.client.close();
            this.client = null;
        }
    }

    public boolean handleEvent(final H5Event event, final H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if ("request".equals(action)) {
            final String url = H5Utils.getString(event.getParam(), (String) "url");
            if (TextUtils.isEmpty(url)) {
                bridgeContext.sendError(event, Error.INVALID_PARAM);
                return true;
            }
            int requestTaskId = H5Utils.getInt(event.getParam(), (String) "requestTaskId", -1);
            if (requestTaskId == -1) {
                bridgeContext.sendError(event, Error.INVALID_PARAM);
                return true;
            } else if (this.requestTaskMap.contains(Integer.valueOf(requestTaskId))) {
                bridgeContext.sendError(event, Error.INVALID_PARAM);
                return true;
            } else {
                final RequestTask task = new RequestTask(requestTaskId);
                this.requestTaskMap.put(Integer.valueOf(requestTaskId), task);
                final H5Event h5Event = event;
                final H5BridgeContext h5BridgeContext = bridgeContext;
                H5Utils.getExecutor("RPC").execute(new Runnable() {
                    public void run() {
                        try {
                            H5Page h5Page = h5Event.getH5page();
                            H5ApiManager h5ApiManager = (H5ApiManager) H5Utils.getProvider(H5ApiManager.class.getName());
                            if (h5Page == null || h5ApiManager == null || !h5ApiManager.hasPermission(TinyAppParamUtils.getHostAppId(h5Page), null, H5ApiManager.Enable_Proxy, h5Page)) {
                                TinyAppRequestPlugin.this.httpRequest(h5Event, h5BridgeContext, task, url);
                            } else {
                                TinyAppRequestPlugin.this.httpRequestWithAliPayNet(h5Event, h5BridgeContext, task, url);
                            }
                        } catch (Throwable e) {
                            H5Log.e((String) TinyAppRequestPlugin.TAG, e);
                            TinyAppRequestPlugin.sendFailed(12, e.getMessage(), h5BridgeContext);
                        } finally {
                            TinyAppRequestPlugin.this.requestTaskMap.remove(Integer.valueOf(task.id));
                        }
                    }
                });
                return true;
            }
        } else if (!ACTION_OPERATE_REQUEST.equals(action)) {
            return false;
        } else {
            int requestTaskId2 = H5Utils.getInt(event.getParam(), (String) "requestTaskId", -1);
            if (requestTaskId2 == -1) {
                bridgeContext.sendError(event, Error.INVALID_PARAM);
                return true;
            }
            final RequestTask task2 = this.requestTaskMap.get(Integer.valueOf(requestTaskId2));
            if (task2 == null) {
                bridgeContext.sendError(event, Error.INVALID_PARAM);
                return true;
            }
            H5Utils.getExecutor("RPC").execute(new Runnable() {
                public void run() {
                    try {
                        TinyAppRequestPlugin.this.httpRequestDispose(event, bridgeContext, task2);
                    } catch (Throwable e) {
                        H5Log.e((String) TinyAppRequestPlugin.TAG, e);
                        TinyAppRequestPlugin.sendFailed(12, e.getMessage(), bridgeContext);
                    }
                }
            });
            return true;
        }
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction("request");
        filter.addAction(ACTION_OPERATE_REQUEST);
    }

    private boolean hasPermission(H5Event event, String reqUrl) {
        H5Page h5Page = event.getH5page();
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5Page == null || h5ConfigProvider == null || !"yes".equalsIgnoreCase(h5ConfigProvider.getConfig("h5_shouldCheckSPPermission"))) {
            return true;
        }
        String currentUrl = h5Page.getUrl();
        if (h5ConfigProvider.isAliDomains(currentUrl) || h5ConfigProvider.isAlipayDomains(currentUrl)) {
            return true;
        }
        if (!h5ConfigProvider.isAliDomains(reqUrl) && !h5ConfigProvider.isAlipayDomains(reqUrl)) {
            return true;
        }
        H5LogUtil.logNebulaTech(H5LogData.seedId("h5_al_jsapi_permission_cors").param1().add("reqUrl", reqUrl).param2().add("currentUrl", currentUrl).param3().add(TinyAppParamUtils.getAppId(h5Page), null));
        return false;
    }

    /* access modifiers changed from: private */
    public void httpRequest(H5Event event, H5BridgeContext bridgeContext, RequestTask task, String url) {
        HttpUriRequest req;
        String str;
        Header[] allHeaders;
        if (event == null || event.getParam() == null) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        JSONObject params = event.getParam();
        if (!hasPermission(event, url)) {
            bridgeContext.sendNoRigHtToInvoke();
            return;
        }
        final String method = H5Utils.getString(params, (String) "method", (String) "GET");
        JSONArray headersArray = H5Utils.getJSONArray(params, HEADERS, null);
        JSONObject headerJsonOb = H5Utils.getJSONObject(params, HEADERS, null);
        String data = H5Utils.getString(params, (String) "data");
        int timeout = H5Utils.getInt(params, (String) "timeout", -1);
        String responseType = H5Utils.getString(params, (String) "responseType");
        String responseCharset = H5Utils.getString(params, (String) "responseCharset");
        String postData = "";
        if (TextUtils.isEmpty(method) || "GET".equalsIgnoreCase(method)) {
            req = new HttpGet(url);
        } else if (RequestMethodConstants.DELETE_METHOD.equalsIgnoreCase(method)) {
            req = new HttpDelete(url);
        } else if ("HEADER".equalsIgnoreCase(method)) {
            req = new HttpHead(url);
        } else if (RequestMethodConstants.PUT_METHOD.equalsIgnoreCase(method)) {
            req = new HttpPut(url);
        } else if ("POST".equalsIgnoreCase(method)) {
            HttpUriRequest httpPost = new HttpPost(url);
            if (data != null) {
                HttpEntity entity = null;
                try {
                    HttpEntity byteArrayEntity = new ByteArrayEntity(data.getBytes("UTF-8"));
                    entity = byteArrayEntity;
                } catch (UnsupportedEncodingException e) {
                    H5Log.e(TAG, "exception detail", e);
                }
                httpPost.setEntity(entity);
                postData = data;
            }
            req = httpPost;
            if (!params.containsKey(HEADERS)) {
                req.addHeader("Content-Type", "application/x-www-form-urlencoded");
            }
        } else {
            req = new HttpGet(url);
        }
        if (!url.startsWith("http")) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        if (headersArray != null) {
            try {
                if (!headersArray.isEmpty()) {
                    Iterator<Object> it = headersArray.iterator();
                    while (it.hasNext()) {
                        for (Entry entry : ((JSONObject) it.next()).entrySet()) {
                            try {
                                req.addHeader((String) entry.getKey(), (String) entry.getValue());
                            } catch (ClassCastException e2) {
                                H5Log.e(TAG, "exception detail", e2);
                            }
                        }
                    }
                }
            } catch (Exception e3) {
                H5Log.e((String) TAG, (Throwable) e3);
            }
        }
        if (headerJsonOb != null) {
            if (!headerJsonOb.isEmpty()) {
                for (String key : headerJsonOb.keySet()) {
                    try {
                        req.addHeader(key, headerJsonOb.get(key).toString());
                    } catch (Exception e4) {
                        H5Log.e((String) TAG, (Throwable) e4);
                    }
                }
            }
        }
        if (TextUtils.isEmpty(responseCharset)) {
            str = "UTF-8";
        } else {
            str = responseCharset;
        }
        req.addHeader("Accept-Charset", str);
        if (TextUtils.equals(method, "POST") && !req.containsHeader("Content-Type")) {
            req.addHeader("Content-Type", "application/x-www-form-urlencoded");
        }
        H5Page h5Page = event.getH5page();
        if (!req.containsHeader("referer") && h5Page != null) {
            req.addHeader("referer", h5Page.getUrl());
        }
        String cookieStr = H5CookieUtil.getCookie(h5Page.getParams(), url);
        if (!TextUtils.isEmpty(cookieStr)) {
            req.addHeader("Cookie", cookieStr);
        }
        if (!(this.client != null || h5Page == null || h5Page.getWebView() == null || h5Page.getWebView().getSettings() == null)) {
            this.client = AndroidHttpClient.newInstance(h5Page.getWebView().getSettings().getUserAgentString());
        }
        if (timeout < 0) {
            timeout = 30000;
        }
        if (!(this.client == null || this.client.getParams() == null)) {
            this.client.getParams().setParameter("http.connection.timeout", Integer.valueOf(timeout));
        }
        if (!task.abort.get()) {
            String appId = TinyAppParamUtils.getAppId(h5Page.getParams());
            task.request = req;
            final String uuid = UUID.randomUUID().toString();
            final HttpUriRequest finalReq = req;
            final String postBody = postData;
            if (H5TinyAppUtils.isRemoteDebugConnected(appId) || H5TinyAppUtils.isVConsolePanelOpened()) {
                final String targetAppId = appId;
                final String str2 = url;
                H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                    public void run() {
                        Header[] allHeaders;
                        JSONObject requestHeaders = new JSONObject();
                        for (Header header : finalReq.getAllHeaders()) {
                            requestHeaders.put(header.getName(), (Object) header.getValue());
                        }
                        JSONObject requestInfo = new JSONObject();
                        requestInfo.put((String) TinyAppRequestPlugin.HEADERS, (Object) requestHeaders);
                        requestInfo.put((String) "method", (Object) method);
                        requestInfo.put((String) "url", (Object) str2);
                        requestInfo.put((String) OkApacheClient.REQUESTID, (Object) uuid);
                        requestInfo.put((String) "postBody", (Object) postBody);
                        H5TinyAppUtils.sendMsgToRemoteWorkerOrVConsole(targetAppId, TinyAppRequestPlugin.TINYAPP_REMOTE_DEBUG_NETWORK_REQUEST, requestInfo.toJSONString());
                    }
                });
            }
            try {
                H5Log.d(TAG, "check point 1, ready to execute");
                if (this.client != null) {
                    HttpResponse res = this.client.execute(finalReq);
                    if (res == null) {
                        task.canAbort.set(false);
                        sendFailed(12, H5Environment.getResources().getString(R.string.h5_server_error), bridgeContext);
                        return;
                    }
                    H5Log.d(TAG, "check point 3, execute done");
                    if (this.hasReleased) {
                        return;
                    }
                    if (res.getStatusLine() != null) {
                        final int statusCode = res.getStatusLine().getStatusCode();
                        res.getStatusLine().getReasonPhrase();
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
                                        H5CookieUtil.setCookie(h5Page.getParams(), url, headerValue);
                                    }
                                }
                            }
                            result.put((String) HEADERS, (Object) headers);
                        }
                        if (H5TinyAppUtils.isRemoteDebugConnected(appId) || H5TinyAppUtils.isVConsolePanelOpened()) {
                            final String statusText = res.getStatusLine().toString();
                            final String resContent = content;
                            final String str3 = url;
                            final String str4 = uuid;
                            final String str5 = appId;
                            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                                public void run() {
                                    int bodyLength = 1048576;
                                    JSONObject responseInfo = new JSONObject();
                                    responseInfo.put((String) TinyAppRequestPlugin.HEADERS, (Object) headers);
                                    responseInfo.put((String) "url", (Object) str3);
                                    responseInfo.put((String) "status", (Object) Integer.valueOf(statusCode));
                                    responseInfo.put((String) "statusText", (Object) statusText);
                                    responseInfo.put((String) OkApacheClient.REQUESTID, (Object) str4);
                                    if (resContent.length() <= 1048576) {
                                        bodyLength = resContent.length();
                                    }
                                    responseInfo.put((String) Constants.BODY, (Object) resContent.substring(0, bodyLength));
                                    H5TinyAppUtils.sendMsgToRemoteWorkerOrVConsole(str5, TinyAppRequestPlugin.TINYAPP_REMOTE_DEBUG_NETWORK_RESPONSE, responseInfo.toJSONString());
                                }
                            });
                        }
                        task.canAbort.set(false);
                        result.put((String) "error", (Object) Integer.valueOf(0));
                        if (bridgeContext != null) {
                            bridgeContext.sendBridgeResult(result);
                            return;
                        }
                        return;
                    }
                    bridgeContext.sendError(event, Error.UNKNOWN_ERROR);
                }
            } catch (Exception e5) {
                H5Log.e(TAG, "exception detail", e5);
                if (e5 instanceof ParseException) {
                    sendFailed(14, e5.getMessage(), bridgeContext);
                } else if (!(e5 instanceof IOException) || !TextUtils.equals(e5.getMessage(), "Request already aborted")) {
                    sendFailed(12, e5.getMessage(), bridgeContext);
                } else {
                    sendFailed(20, e5.getMessage(), bridgeContext);
                }
                H5LogUtil.logNebulaTech(H5LogData.seedId(TAG).param4().add("executeException", e5));
            }
        }
    }

    /* access modifiers changed from: private */
    public void httpRequestDispose(H5Event event, H5BridgeContext bridgeContext, RequestTask task) {
        if (event == null || event.getParam() == null) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        String operationType = H5Utils.getString(event.getParam(), (String) TransportConstants.KEY_OPERATION_TYPE);
        if (TextUtils.isEmpty(operationType)) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
        } else if (TextUtils.equals(operationType, "abort")) {
            try {
                if (task.abort.get() || !task.canAbort.get()) {
                    bridgeContext.sendError(event, Error.UNKNOWN_ERROR);
                    return;
                }
                if (task.request != null) {
                    task.request.abort();
                }
                if (task.requestProxy != null) {
                    task.requestProxy.cancel("abort");
                }
                task.abort.set(true);
                bridgeContext.sendSuccess();
            } catch (Exception e) {
                H5Log.e(TAG, "exception detail", e);
                sendFailed(12, e.getMessage(), bridgeContext);
                H5LogUtil.logNebulaTech(H5LogData.seedId(TAG).param4().add("executeException", e));
            }
        }
    }

    /* JADX WARNING: type inference failed for: r43v0, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r35v1 */
    /* JADX WARNING: type inference failed for: r50v0 */
    /* JADX WARNING: type inference failed for: r50v1, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r50v2 */
    /* JADX WARNING: type inference failed for: r1v8, types: [java.io.InputStream] */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void httpRequestWithAliPayNet(com.alipay.mobile.h5container.api.H5Event r69, com.alipay.mobile.h5container.api.H5BridgeContext r70, com.alipay.mobile.tinyappcommon.h5plugin.TinyAppRequestPlugin.RequestTask r71, java.lang.String r72) {
        /*
            r68 = this;
            if (r69 == 0) goto L_0x0008
            com.alibaba.fastjson.JSONObject r4 = r69.getParam()
            if (r4 != 0) goto L_0x0012
        L_0x0008:
            com.alipay.mobile.h5container.api.H5Event$Error r4 = com.alipay.mobile.h5container.api.H5Event.Error.INVALID_PARAM
            r0 = r70
            r1 = r69
            r0.sendError(r1, r4)
        L_0x0011:
            return
        L_0x0012:
            com.alibaba.fastjson.JSONObject r48 = r69.getParam()
            java.lang.String r4 = "method"
            java.lang.String r5 = "GET"
            r0 = r48
            java.lang.String r7 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r4, r5)
            java.lang.String r4 = "headers"
            r5 = 0
            r0 = r48
            com.alibaba.fastjson.JSONArray r41 = com.alipay.mobile.nebula.util.H5Utils.getJSONArray(r0, r4, r5)
            java.lang.String r4 = "headers"
            r5 = 0
            r0 = r48
            com.alibaba.fastjson.JSONObject r39 = com.alipay.mobile.nebula.util.H5Utils.getJSONObject(r0, r4, r5)
            java.lang.String r4 = "data"
            r0 = r48
            java.lang.String r30 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r4)
            java.lang.String r4 = "timeout"
            r5 = -1
            r0 = r48
            int r62 = com.alipay.mobile.nebula.util.H5Utils.getInt(r0, r4, r5)
            if (r62 >= 0) goto L_0x00a7
            r60 = 30000(0x7530, float:4.2039E-41)
        L_0x0047:
            java.lang.String r4 = "responseType"
            r0 = r48
            java.lang.String r55 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r4)
            java.lang.String r4 = "responseCharset"
            r0 = r48
            java.lang.String r53 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r4)
            java.util.HashMap r40 = new java.util.HashMap
            r40.<init>()
            r61 = 0
            java.lang.String r4 = "GET"
            boolean r4 = r4.equalsIgnoreCase(r7)
            if (r4 == 0) goto L_0x006d
            java.lang.String r4 = "TinyAppRequestPlugin"
            java.lang.String r5 = "GET"
            com.alipay.mobile.nebula.util.H5Log.d(r4, r5)
        L_0x006d:
            java.lang.String r4 = "POST"
            boolean r4 = r4.equalsIgnoreCase(r7)
            if (r4 == 0) goto L_0x0092
            if (r30 == 0) goto L_0x007f
            java.lang.String r4 = "UTF-8"
            r0 = r30
            byte[] r61 = r0.getBytes(r4)     // Catch:{ Exception -> 0x00aa }
        L_0x007f:
            java.lang.String r4 = "headers"
            r0 = r48
            boolean r4 = r0.containsKey(r4)
            if (r4 != 0) goto L_0x0092
            java.lang.String r4 = "Content-Type"
            java.lang.String r5 = "application/x-www-form-urlencoded"
            r0 = r40
            r0.put(r4, r5)
        L_0x0092:
            java.lang.String r4 = "http"
            r0 = r72
            boolean r4 = r0.startsWith(r4)
            if (r4 != 0) goto L_0x00b3
            com.alipay.mobile.h5container.api.H5Event$Error r4 = com.alipay.mobile.h5container.api.H5Event.Error.INVALID_PARAM
            r0 = r70
            r1 = r69
            r0.sendError(r1, r4)
            goto L_0x0011
        L_0x00a7:
            r60 = r62
            goto L_0x0047
        L_0x00aa:
            r31 = move-exception
            java.lang.String r4 = "TinyAppRequestPlugin"
            r0 = r31
            com.alipay.mobile.nebula.util.H5Log.e(r4, r0)
            goto L_0x007f
        L_0x00b3:
            r52 = r61
            r38 = 0
            if (r41 == 0) goto L_0x0303
            boolean r4 = r41.isEmpty()     // Catch:{ Exception -> 0x010e }
            if (r4 != 0) goto L_0x0303
            java.util.Iterator r5 = r41.iterator()     // Catch:{ Exception -> 0x010e }
        L_0x00c3:
            boolean r4 = r5.hasNext()     // Catch:{ Exception -> 0x010e }
            if (r4 == 0) goto L_0x0303
            java.lang.Object r4 = r5.next()     // Catch:{ Exception -> 0x010e }
            com.alibaba.fastjson.JSONObject r4 = (com.alibaba.fastjson.JSONObject) r4     // Catch:{ Exception -> 0x010e }
            java.util.Set r4 = r4.entrySet()     // Catch:{ Exception -> 0x010e }
            java.util.Iterator r8 = r4.iterator()     // Catch:{ Exception -> 0x010e }
        L_0x00d7:
            boolean r4 = r8.hasNext()     // Catch:{ Exception -> 0x010e }
            if (r4 == 0) goto L_0x00c3
            java.lang.Object r32 = r8.next()     // Catch:{ Exception -> 0x010e }
            java.util.Map$Entry r32 = (java.util.Map.Entry) r32     // Catch:{ Exception -> 0x010e }
            java.lang.String r11 = "content-type"
            java.lang.Object r4 = r32.getKey()     // Catch:{ ClassCastException -> 0x0103 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ ClassCastException -> 0x0103 }
            boolean r4 = r11.equalsIgnoreCase(r4)     // Catch:{ ClassCastException -> 0x0103 }
            if (r4 == 0) goto L_0x00f3
            r38 = 1
        L_0x00f3:
            java.lang.Object r11 = r32.getKey()     // Catch:{ ClassCastException -> 0x0103 }
            java.lang.Object r4 = r32.getValue()     // Catch:{ ClassCastException -> 0x0103 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ ClassCastException -> 0x0103 }
            r0 = r40
            r0.put(r11, r4)     // Catch:{ ClassCastException -> 0x0103 }
            goto L_0x00d7
        L_0x0103:
            r31 = move-exception
            java.lang.String r4 = "TinyAppRequestPlugin"
            java.lang.String r11 = "exception detail"
            r0 = r31
            com.alipay.mobile.nebula.util.H5Log.e(r4, r11, r0)     // Catch:{ Exception -> 0x010e }
            goto L_0x00d7
        L_0x010e:
            r31 = move-exception
            java.lang.String r4 = "TinyAppRequestPlugin"
            r0 = r31
            com.alipay.mobile.nebula.util.H5Log.e(r4, r0)
        L_0x0116:
            java.lang.String r5 = "Accept-Charset"
            boolean r4 = android.text.TextUtils.isEmpty(r53)
            if (r4 == 0) goto L_0x034a
            java.lang.String r4 = "UTF-8"
        L_0x0120:
            r0 = r40
            r0.put(r5, r4)
            boolean r4 = r68.hasConfigCaseInsensitiveForContentTypeHeader()
            if (r4 == 0) goto L_0x034e
            java.lang.String r4 = "POST"
            boolean r4 = r4.equalsIgnoreCase(r7)
            if (r4 == 0) goto L_0x0146
            java.lang.String r4 = "Content-Type"
            r0 = r40
            boolean r4 = r0.containsKey(r4)
            if (r4 != 0) goto L_0x0146
            java.lang.String r4 = "Content-Type"
            java.lang.String r5 = "application/x-www-form-urlencoded"
            r0 = r40
            r0.put(r4, r5)
        L_0x0146:
            com.alipay.mobile.h5container.api.H5Page r37 = r69.getH5page()
            if (r37 == 0) goto L_0x0363
            android.os.Bundle r57 = r37.getParams()
        L_0x0150:
            java.lang.String r20 = com.alipay.mobile.tinyappcommon.utils.TinyAppParamUtils.getAppId(r57)
            java.lang.String r4 = "su584appid"
            r0 = r40
            r1 = r20
            r0.put(r4, r1)
            java.lang.String r4 = "publicId"
            r0 = r57
            java.lang.String r49 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r4)
            boolean r4 = android.text.TextUtils.isEmpty(r49)
            if (r4 != 0) goto L_0x0174
            java.lang.String r4 = "su584publicid"
            r0 = r40
            r1 = r49
            r0.put(r4, r1)
        L_0x0174:
            java.lang.String r4 = "release_type"
            r0 = r57
            java.lang.String r51 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r4)
            boolean r4 = android.text.TextUtils.isEmpty(r51)
            if (r4 != 0) goto L_0x018b
            java.lang.String r4 = "x-release-type"
            r0 = r40
            r1 = r51
            r0.put(r4, r1)
        L_0x018b:
            java.lang.String r4 = "appVersion"
            r0 = r57
            java.lang.String r21 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r4)
            boolean r4 = android.text.TextUtils.isEmpty(r21)
            if (r4 != 0) goto L_0x01a2
            java.lang.String r4 = "su584appversion"
            r0 = r40
            r1 = r21
            r0.put(r4, r1)
        L_0x01a2:
            java.lang.String r4 = "package_nick"
            r0 = r57
            java.lang.String r47 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r4)
            boolean r4 = android.text.TextUtils.isEmpty(r47)
            if (r4 != 0) goto L_0x01b9
            java.lang.String r4 = "su584tinyappversion"
            r0 = r40
            r1 = r47
            r0.put(r4, r1)
        L_0x01b9:
            java.lang.String r4 = "bizScenario"
            r0 = r57
            java.lang.String r22 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r4)
            boolean r4 = android.text.TextUtils.isEmpty(r22)
            if (r4 != 0) goto L_0x01d0
            java.lang.String r4 = "su584bizscenario"
            r0 = r40
            r1 = r22
            r0.put(r4, r1)
        L_0x01d0:
            java.lang.String r5 = "su584appletpage"
            if (r37 == 0) goto L_0x0367
            java.lang.String r4 = r37.getUrl()
        L_0x01d8:
            r0 = r40
            r0.put(r5, r4)
            java.lang.String r4 = "referer"
            r0 = r40
            boolean r4 = r0.containsKey(r4)
            if (r4 != 0) goto L_0x01f4
            if (r37 == 0) goto L_0x01f4
            java.lang.String r4 = "referer"
            java.lang.String r5 = r37.getUrl()
            r0 = r40
            r0.put(r4, r5)
        L_0x01f4:
            long r58 = java.lang.System.currentTimeMillis()
            android.os.Bundle r4 = r37.getParams()
            r0 = r72
            java.lang.String r25 = com.alipay.mobile.nebula.util.H5CookieUtil.getCookie(r4, r0)
            java.lang.String r4 = "TinyAppRequestPlugin"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r8 = "getCookie cost "
            r5.<init>(r8)
            long r66 = java.lang.System.currentTimeMillis()
            long r66 = r66 - r58
            r0 = r66
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r4, r5)
            boolean r4 = android.text.TextUtils.isEmpty(r25)
            if (r4 != 0) goto L_0x022d
            java.lang.String r4 = "Cookie"
            r0 = r40
            r1 = r25
            r0.put(r4, r1)
        L_0x022d:
            java.lang.String r4 = "User-Agent"
            r0 = r40
            boolean r4 = r0.containsKey(r4)
            if (r4 != 0) goto L_0x0266
            java.lang.String r4 = "user-agent"
            r0 = r40
            boolean r4 = r0.containsKey(r4)
            if (r4 != 0) goto L_0x0266
            if (r37 == 0) goto L_0x0266
            com.alipay.mobile.nebula.webview.APWebView r4 = r37.getWebView()
            if (r4 == 0) goto L_0x0266
            com.alipay.mobile.nebula.webview.APWebView r4 = r37.getWebView()
            com.alipay.mobile.nebula.webview.APWebSettings r4 = r4.getSettings()
            if (r4 == 0) goto L_0x0266
            java.lang.String r4 = "User-Agent"
            com.alipay.mobile.nebula.webview.APWebView r5 = r37.getWebView()
            com.alipay.mobile.nebula.webview.APWebSettings r5 = r5.getSettings()
            java.lang.String r5 = r5.getUserAgentString()
            r0 = r40
            r0.put(r4, r5)
        L_0x0266:
            r0 = r71
            java.util.concurrent.atomic.AtomicBoolean r4 = r0.abort
            boolean r4 = r4.get()
            if (r4 != 0) goto L_0x0011
            java.lang.String r4 = "TinyAppRequestPlugin"
            java.lang.String r5 = "check point 1, ready to execute"
            com.alipay.mobile.nebula.util.H5Log.d(r4, r5)     // Catch:{ Exception -> 0x02d1 }
            com.alipay.mobile.common.transport.h5.H5HttpUrlRequest r6 = new com.alipay.mobile.common.transport.h5.H5HttpUrlRequest     // Catch:{ Exception -> 0x02d1 }
            r0 = r72
            r6.<init>(r0)     // Catch:{ Exception -> 0x02d1 }
            r0 = r71
            r0.requestProxy = r6     // Catch:{ Exception -> 0x02d1 }
            r6.setRequestMethod(r7)     // Catch:{ Exception -> 0x02d1 }
            java.util.Set r4 = r40.keySet()     // Catch:{ Exception -> 0x02d1 }
            java.util.Iterator r5 = r4.iterator()     // Catch:{ Exception -> 0x02d1 }
        L_0x028d:
            boolean r4 = r5.hasNext()     // Catch:{ Exception -> 0x02d1 }
            if (r4 == 0) goto L_0x036a
            java.lang.Object r46 = r5.next()     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r46 = (java.lang.String) r46     // Catch:{ Exception -> 0x02d1 }
            r0 = r40
            r1 = r46
            java.lang.Object r4 = r0.get(r1)     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r64 = com.alipay.mobile.common.transport.utils.ZURLEncodedUtil.urlEncode(r4)     // Catch:{ Exception -> 0x02d1 }
            r0 = r46
            r1 = r64
            r6.addHeader(r0, r1)     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r4 = "TinyAppRequestPlugin"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r11 = "request headers "
            r8.<init>(r11)     // Catch:{ Exception -> 0x02d1 }
            r0 = r46
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r11 = " "
            java.lang.StringBuilder r8 = r8.append(r11)     // Catch:{ Exception -> 0x02d1 }
            r0 = r64
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x02d1 }
            com.alipay.mobile.nebula.util.H5Log.d(r4, r8)     // Catch:{ Exception -> 0x02d1 }
            goto L_0x028d
        L_0x02d1:
            r31 = move-exception
            r0 = r31
            boolean r4 = r0 instanceof android.net.ParseException
            if (r4 == 0) goto L_0x0620
            r4 = 14
            java.lang.String r5 = r31.getMessage()
            r0 = r70
            sendFailed(r4, r5, r0)
        L_0x02e3:
            java.lang.String r4 = "TinyAppRequestPlugin"
            java.lang.String r5 = "exception detail"
            r0 = r31
            com.alipay.mobile.nebula.util.H5Log.e(r4, r5, r0)
            java.lang.String r4 = "TinyAppRequestPlugin"
            com.alipay.mobile.nebula.log.H5LogData r4 = com.alipay.mobile.nebula.log.H5LogData.seedId(r4)
            com.alipay.mobile.nebula.log.H5LogData r4 = r4.param4()
            java.lang.String r5 = "executeException"
            r0 = r31
            com.alipay.mobile.nebula.log.H5LogData r4 = r4.add(r5, r0)
            com.alipay.mobile.nebula.log.H5LogUtil.logNebulaTech(r4)
            goto L_0x0011
        L_0x0303:
            if (r39 == 0) goto L_0x0116
            boolean r4 = r39.isEmpty()     // Catch:{ Exception -> 0x010e }
            if (r4 != 0) goto L_0x0116
            java.util.Set r4 = r39.keySet()     // Catch:{ Exception -> 0x010e }
            java.util.Iterator r44 = r4.iterator()     // Catch:{ Exception -> 0x010e }
        L_0x0313:
            boolean r4 = r44.hasNext()     // Catch:{ Exception -> 0x010e }
            if (r4 == 0) goto L_0x0116
            java.lang.Object r46 = r44.next()     // Catch:{ Exception -> 0x0341 }
            java.lang.String r46 = (java.lang.String) r46     // Catch:{ Exception -> 0x0341 }
            r0 = r39
            r1 = r46
            java.lang.Object r4 = r0.get(r1)     // Catch:{ Exception -> 0x0341 }
            java.lang.String r64 = r4.toString()     // Catch:{ Exception -> 0x0341 }
            java.lang.String r4 = "content-type"
            r0 = r46
            boolean r4 = r4.equalsIgnoreCase(r0)     // Catch:{ Exception -> 0x0341 }
            if (r4 == 0) goto L_0x0337
            r38 = 1
        L_0x0337:
            r0 = r40
            r1 = r46
            r2 = r64
            r0.put(r1, r2)     // Catch:{ Exception -> 0x0341 }
            goto L_0x0313
        L_0x0341:
            r31 = move-exception
            java.lang.String r4 = "TinyAppRequestPlugin"
            r0 = r31
            com.alipay.mobile.nebula.util.H5Log.e(r4, r0)     // Catch:{ Exception -> 0x010e }
            goto L_0x0313
        L_0x034a:
            r4 = r53
            goto L_0x0120
        L_0x034e:
            java.lang.String r4 = "POST"
            boolean r4 = r4.equalsIgnoreCase(r7)
            if (r4 == 0) goto L_0x0146
            if (r38 != 0) goto L_0x0146
            java.lang.String r4 = "Content-Type"
            java.lang.String r5 = "application/x-www-form-urlencoded"
            r0 = r40
            r0.put(r4, r5)
            goto L_0x0146
        L_0x0363:
            r57 = 0
            goto L_0x0150
        L_0x0367:
            r4 = 0
            goto L_0x01d8
        L_0x036a:
            java.lang.String r63 = ""
            java.lang.Class<com.alipay.mobile.nebula.provider.H5LoginProvider> r4 = com.alipay.mobile.nebula.provider.H5LoginProvider.class
            java.lang.String r4 = r4.getName()     // Catch:{ Exception -> 0x02d1 }
            java.lang.Object r36 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r4)     // Catch:{ Exception -> 0x02d1 }
            com.alipay.mobile.nebula.provider.H5LoginProvider r36 = (com.alipay.mobile.nebula.provider.H5LoginProvider) r36     // Catch:{ Exception -> 0x02d1 }
            if (r36 == 0) goto L_0x037e
            java.lang.String r63 = r36.getUserId()     // Catch:{ Exception -> 0x02d1 }
        L_0x037e:
            boolean r4 = android.text.TextUtils.isEmpty(r63)     // Catch:{ Exception -> 0x02d1 }
            if (r4 != 0) goto L_0x038b
            java.lang.String r4 = "su584userid"
            r0 = r63
            r6.addHeader(r4, r0)     // Catch:{ Exception -> 0x02d1 }
        L_0x038b:
            java.lang.String r4 = "su584channelapplet"
            java.lang.String r5 = "Y"
            r6.addHeader(r4, r5)     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r4 = "h5_app_type"
            java.lang.String r5 = "mini_app"
            r6.addTags(r4, r5)     // Catch:{ Exception -> 0x02d1 }
            r0 = r52
            r6.setReqData(r0)     // Catch:{ Exception -> 0x02d1 }
            r4 = 1
            r6.setAsyncMonitorLog(r4)     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r4 = "TinyAppRequestPlugin"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r8 = "linkType SPDY_LINK: "
            r5.<init>(r8)     // Catch:{ Exception -> 0x02d1 }
            r0 = r72
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x02d1 }
            com.alipay.mobile.nebula.util.H5Log.d(r4, r5)     // Catch:{ Exception -> 0x02d1 }
            r4 = 1
            r6.linkType = r4     // Catch:{ Exception -> 0x02d1 }
            java.util.UUID r4 = java.util.UUID.randomUUID()     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r9 = r4.toString()     // Catch:{ Exception -> 0x02d1 }
            boolean r4 = com.alipay.mobile.tinyappcommon.utils.H5TinyAppUtils.isRemoteDebugConnected(r20)     // Catch:{ Exception -> 0x02d1 }
            if (r4 != 0) goto L_0x03cf
            boolean r4 = com.alipay.mobile.tinyappcommon.utils.H5TinyAppUtils.isVConsolePanelOpened()     // Catch:{ Exception -> 0x02d1 }
            if (r4 == 0) goto L_0x03e3
        L_0x03cf:
            r10 = r20
            java.lang.String r4 = "IO"
            java.util.concurrent.ThreadPoolExecutor r11 = com.alipay.mobile.nebula.util.H5Utils.getExecutor(r4)     // Catch:{ Exception -> 0x02d1 }
            com.alipay.mobile.tinyappcommon.h5plugin.TinyAppRequestPlugin$6 r4 = new com.alipay.mobile.tinyappcommon.h5plugin.TinyAppRequestPlugin$6     // Catch:{ Exception -> 0x02d1 }
            r5 = r68
            r8 = r72
            r4.<init>(r6, r7, r8, r9, r10)     // Catch:{ Exception -> 0x02d1 }
            r11.execute(r4)     // Catch:{ Exception -> 0x02d1 }
        L_0x03e3:
            long r58 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x02d1 }
            com.alipay.mobile.common.transport.h5.H5NetworkManager r4 = new com.alipay.mobile.common.transport.h5.H5NetworkManager     // Catch:{ Exception -> 0x02d1 }
            android.content.Context r5 = com.alipay.mobile.nebula.util.H5Utils.getContext()     // Catch:{ Exception -> 0x02d1 }
            r4.<init>(r5)     // Catch:{ Exception -> 0x02d1 }
            java.util.concurrent.Future r33 = r4.enqueue(r6)     // Catch:{ Exception -> 0x02d1 }
            r42 = 0
            if (r60 <= 0) goto L_0x055e
            r4 = 30000(0x7530, float:4.2039E-41)
            r0 = r60
            if (r0 == r4) goto L_0x055e
            java.lang.String r4 = "TinyAppRequestPlugin"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0556 }
            java.lang.String r8 = "timeout "
            r5.<init>(r8)     // Catch:{ Throwable -> 0x0556 }
            r0 = r60
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Throwable -> 0x0556 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0556 }
            com.alipay.mobile.nebula.util.H5Log.d(r4, r5)     // Catch:{ Throwable -> 0x0556 }
            r0 = r60
            long r4 = (long) r0     // Catch:{ Throwable -> 0x0556 }
            java.util.concurrent.TimeUnit r8 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ Throwable -> 0x0556 }
            r0 = r33
            java.lang.Object r4 = r0.get(r4, r8)     // Catch:{ Throwable -> 0x0556 }
            r0 = r4
            com.alipay.mobile.common.transport.h5.H5HttpUrlResponse r0 = (com.alipay.mobile.common.transport.h5.H5HttpUrlResponse) r0     // Catch:{ Throwable -> 0x0556 }
            r42 = r0
        L_0x0424:
            java.lang.String r4 = "TinyAppRequestPlugin"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r8 = "httpRequest timeCost h5HttpUrlRequest "
            r5.<init>(r8)     // Catch:{ Exception -> 0x02d1 }
            long r66 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x02d1 }
            long r66 = r66 - r58
            r0 = r66
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r8 = " "
            java.lang.StringBuilder r5 = r5.append(r8)     // Catch:{ Exception -> 0x02d1 }
            r0 = r72
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x02d1 }
            com.alipay.mobile.nebula.util.H5Log.d(r4, r5)     // Catch:{ Exception -> 0x02d1 }
            com.alibaba.fastjson.JSONArray r13 = new com.alibaba.fastjson.JSONArray     // Catch:{ Exception -> 0x02d1 }
            r13.<init>()     // Catch:{ Exception -> 0x02d1 }
            r34 = 0
            if (r42 == 0) goto L_0x0566
            com.alipay.mobile.common.transport.http.HttpUrlHeader r4 = r42.getHeader()     // Catch:{ Exception -> 0x02d1 }
            if (r4 == 0) goto L_0x0566
            com.alipay.mobile.common.transport.http.HttpUrlHeader r4 = r42.getHeader()     // Catch:{ Exception -> 0x02d1 }
            java.util.Map r54 = r4.toMultimap()     // Catch:{ Exception -> 0x02d1 }
            java.util.Set r4 = r54.keySet()     // Catch:{ Exception -> 0x02d1 }
            java.util.Iterator r5 = r4.iterator()     // Catch:{ Exception -> 0x02d1 }
        L_0x046b:
            boolean r4 = r5.hasNext()     // Catch:{ Exception -> 0x02d1 }
            if (r4 == 0) goto L_0x0566
            java.lang.Object r46 = r5.next()     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r46 = (java.lang.String) r46     // Catch:{ Exception -> 0x02d1 }
            r0 = r54
            r1 = r46
            java.lang.Object r65 = r0.get(r1)     // Catch:{ Exception -> 0x02d1 }
            java.util.List r65 = (java.util.List) r65     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r4 = "Content-Encoding"
            r0 = r46
            boolean r24 = r4.equalsIgnoreCase(r0)     // Catch:{ Exception -> 0x02d1 }
            java.util.Iterator r8 = r65.iterator()     // Catch:{ Exception -> 0x02d1 }
        L_0x048d:
            boolean r4 = r8.hasNext()     // Catch:{ Exception -> 0x02d1 }
            if (r4 == 0) goto L_0x046b
            java.lang.Object r64 = r8.next()     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r64 = (java.lang.String) r64     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r4 = "TinyAppRequestPlugin"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r12 = "handleResponse headers "
            r11.<init>(r12)     // Catch:{ Exception -> 0x02d1 }
            r0 = r46
            java.lang.StringBuilder r11 = r11.append(r0)     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r12 = " "
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x02d1 }
            r0 = r64
            java.lang.StringBuilder r11 = r11.append(r0)     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x02d1 }
            com.alipay.mobile.nebula.util.H5Log.d(r4, r11)     // Catch:{ Exception -> 0x02d1 }
            if (r24 == 0) goto L_0x04c9
            java.lang.String r4 = "gzip"
            r0 = r64
            boolean r4 = r4.equalsIgnoreCase(r0)     // Catch:{ Exception -> 0x02d1 }
            if (r4 == 0) goto L_0x04c9
            r34 = 1
        L_0x04c9:
            com.alibaba.fastjson.JSONObject r45 = new com.alibaba.fastjson.JSONObject     // Catch:{ Exception -> 0x02d1 }
            r4 = 1
            r0 = r45
            r0.<init>(r4)     // Catch:{ Exception -> 0x02d1 }
            r0 = r45
            r1 = r46
            r2 = r64
            r0.put(r1, r2)     // Catch:{ Exception -> 0x02d1 }
            r0 = r45
            r13.add(r0)     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r4 = "set-cookie"
            r0 = r46
            boolean r4 = r0.equalsIgnoreCase(r4)     // Catch:{ Throwable -> 0x054a }
            if (r4 == 0) goto L_0x048d
            long r26 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x054a }
            android.os.Bundle r4 = r37.getParams()     // Catch:{ Throwable -> 0x054a }
            r0 = r72
            r1 = r64
            com.alipay.mobile.nebula.util.H5CookieUtil.setCookie(r4, r0, r1)     // Catch:{ Throwable -> 0x054a }
            long r66 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x054a }
            long r28 = r66 - r26
            java.lang.String r4 = "TinyAppRequestPlugin"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x054a }
            java.lang.String r12 = "httpRequest timeCost setCookie "
            r11.<init>(r12)     // Catch:{ Throwable -> 0x054a }
            r0 = r28
            java.lang.StringBuilder r11 = r11.append(r0)     // Catch:{ Throwable -> 0x054a }
            java.lang.String r12 = " "
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Throwable -> 0x054a }
            r0 = r72
            java.lang.StringBuilder r11 = r11.append(r0)     // Catch:{ Throwable -> 0x054a }
            java.lang.String r11 = r11.toString()     // Catch:{ Throwable -> 0x054a }
            com.alipay.mobile.nebula.util.H5Log.d(r4, r11)     // Catch:{ Throwable -> 0x054a }
            java.lang.Class<com.alipay.mobile.nebula.provider.H5LogProvider> r4 = com.alipay.mobile.nebula.provider.H5LogProvider.class
            java.lang.String r4 = r4.getName()     // Catch:{ Throwable -> 0x054a }
            java.lang.Object r4 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r4)     // Catch:{ Throwable -> 0x054a }
            com.alipay.mobile.nebula.provider.H5LogProvider r4 = (com.alipay.mobile.nebula.provider.H5LogProvider) r4     // Catch:{ Throwable -> 0x054a }
            if (r4 == 0) goto L_0x048d
            if (r37 == 0) goto L_0x048d
            com.alipay.mobile.h5container.api.H5PageData r4 = r37.getPageData()     // Catch:{ Throwable -> 0x054a }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x054a }
            java.lang.String r12 = "^setCookie="
            r11.<init>(r12)     // Catch:{ Throwable -> 0x054a }
            r0 = r28
            java.lang.StringBuilder r11 = r11.append(r0)     // Catch:{ Throwable -> 0x054a }
            java.lang.String r11 = r11.toString()     // Catch:{ Throwable -> 0x054a }
            r4.appendJsApiPerExtra(r11)     // Catch:{ Throwable -> 0x054a }
            goto L_0x048d
        L_0x054a:
            r31 = move-exception
            java.lang.String r4 = "TinyAppRequestPlugin"
            java.lang.String r11 = "exception detail"
            r0 = r31
            com.alipay.mobile.nebula.util.H5Log.e(r4, r11, r0)     // Catch:{ Exception -> 0x02d1 }
            goto L_0x048d
        L_0x0556:
            r4 = move-exception
            java.lang.String r4 = "TimeoutException"
            r6.cancel(r4)     // Catch:{ Exception -> 0x02d1 }
            goto L_0x0424
        L_0x055e:
            java.lang.Object r42 = r33.get()     // Catch:{ Exception -> 0x02d1 }
            com.alipay.mobile.common.transport.h5.H5HttpUrlResponse r42 = (com.alipay.mobile.common.transport.h5.H5HttpUrlResponse) r42     // Catch:{ Exception -> 0x02d1 }
            goto L_0x0424
        L_0x0566:
            r35 = 0
            java.io.InputStream r43 = r42.getInputStream()     // Catch:{ Exception -> 0x02d1 }
            if (r34 == 0) goto L_0x0577
            java.util.zip.GZIPInputStream r35 = new java.util.zip.GZIPInputStream     // Catch:{ Exception -> 0x02d1 }
            r0 = r35
            r1 = r43
            r0.<init>(r1)     // Catch:{ Exception -> 0x02d1 }
        L_0x0577:
            if (r35 == 0) goto L_0x05ff
            r50 = r35
        L_0x057b:
            int r15 = r42.getCode()     // Catch:{ Exception -> 0x02d1 }
            byte[] r18 = com.alipay.mobile.nebula.util.H5Utils.readBytes(r50)     // Catch:{ Exception -> 0x02d1 }
            r0 = r71
            java.util.concurrent.atomic.AtomicBoolean r4 = r0.canAbort     // Catch:{ Exception -> 0x02d1 }
            r5 = 0
            r4.set(r5)     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r4 = "base64"
            r0 = r55
            boolean r4 = r4.equals(r0)     // Catch:{ Exception -> 0x02d1 }
            if (r4 == 0) goto L_0x0603
            r4 = 2
            r0 = r18
            java.lang.String r23 = android.util.Base64.encodeToString(r0, r4)     // Catch:{ Exception -> 0x02d1 }
        L_0x059c:
            boolean r4 = com.alipay.mobile.tinyappcommon.utils.H5TinyAppUtils.isRemoteDebugConnected(r20)     // Catch:{ Exception -> 0x02d1 }
            if (r4 != 0) goto L_0x05a8
            boolean r4 = com.alipay.mobile.tinyappcommon.utils.H5TinyAppUtils.isVConsolePanelOpened()     // Catch:{ Exception -> 0x02d1 }
            if (r4 == 0) goto L_0x05c8
        L_0x05a8:
            org.apache.http.StatusLine r4 = r42.getStatusLine()     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r16 = r4.toString()     // Catch:{ Exception -> 0x02d1 }
            r10 = r20
            java.lang.String r4 = "IO"
            java.util.concurrent.ThreadPoolExecutor r4 = com.alipay.mobile.nebula.util.H5Utils.getExecutor(r4)     // Catch:{ Exception -> 0x02d1 }
            com.alipay.mobile.tinyappcommon.h5plugin.TinyAppRequestPlugin$7 r11 = new com.alipay.mobile.tinyappcommon.h5plugin.TinyAppRequestPlugin$7     // Catch:{ Exception -> 0x02d1 }
            r12 = r68
            r14 = r72
            r17 = r9
            r19 = r10
            r11.<init>(r13, r14, r15, r16, r17, r18, r19)     // Catch:{ Exception -> 0x02d1 }
            r4.execute(r11)     // Catch:{ Exception -> 0x02d1 }
        L_0x05c8:
            com.alibaba.fastjson.JSONObject r56 = new com.alibaba.fastjson.JSONObject     // Catch:{ Exception -> 0x02d1 }
            r56.<init>()     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r4 = "status"
            java.lang.Integer r5 = java.lang.Integer.valueOf(r15)     // Catch:{ Exception -> 0x02d1 }
            r0 = r56
            r0.put(r4, r5)     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r4 = "data"
            r0 = r56
            r1 = r23
            r0.put(r4, r1)     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r4 = "headers"
            r0 = r56
            r0.put(r4, r13)     // Catch:{ Exception -> 0x02d1 }
            java.lang.String r4 = "error"
            r5 = 0
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ Exception -> 0x02d1 }
            r0 = r56
            r0.put(r4, r5)     // Catch:{ Exception -> 0x02d1 }
            if (r70 == 0) goto L_0x0011
            r0 = r70
            r1 = r56
            r0.sendBridgeResult(r1)     // Catch:{ Exception -> 0x02d1 }
            goto L_0x0011
        L_0x05ff:
            r50 = r43
            goto L_0x057b
        L_0x0603:
            boolean r4 = android.text.TextUtils.isEmpty(r53)     // Catch:{ Exception -> 0x02d1 }
            if (r4 != 0) goto L_0x0615
            java.lang.String r23 = new java.lang.String     // Catch:{ Exception -> 0x02d1 }
            r0 = r23
            r1 = r18
            r2 = r53
            r0.<init>(r1, r2)     // Catch:{ Exception -> 0x02d1 }
            goto L_0x059c
        L_0x0615:
            java.lang.String r23 = new java.lang.String     // Catch:{ Exception -> 0x02d1 }
            r0 = r23
            r1 = r18
            r0.<init>(r1)     // Catch:{ Exception -> 0x02d1 }
            goto L_0x059c
        L_0x0620:
            java.lang.Throwable r4 = r31.getCause()
            boolean r4 = r4 instanceof com.alipay.mobile.common.transport.http.HttpException
            if (r4 == 0) goto L_0x0643
            java.lang.Throwable r4 = r31.getCause()
            com.alipay.mobile.common.transport.http.HttpException r4 = (com.alipay.mobile.common.transport.http.HttpException) r4
            int r4 = r4.getCode()
            r5 = 13
            if (r4 != r5) goto L_0x0643
            r4 = 20
            java.lang.String r5 = r31.getMessage()
            r0 = r70
            sendFailed(r4, r5, r0)
            goto L_0x02e3
        L_0x0643:
            r4 = 12
            java.lang.String r5 = r31.getMessage()
            r0 = r70
            sendFailed(r4, r5, r0)
            goto L_0x02e3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.tinyappcommon.h5plugin.TinyAppRequestPlugin.httpRequestWithAliPayNet(com.alipay.mobile.h5container.api.H5Event, com.alipay.mobile.h5container.api.H5BridgeContext, com.alipay.mobile.tinyappcommon.h5plugin.TinyAppRequestPlugin$RequestTask, java.lang.String):void");
    }

    private boolean hasConfigCaseInsensitiveForContentTypeHeader() {
        if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_httpRequestHeader_caseInsensitive"))) {
            return true;
        }
        return false;
    }
}
