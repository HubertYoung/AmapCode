package com.alipay.mobile.nebulauc.impl.network;

import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.TransportCallbackAdapter;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.h5.H5HttpUrlRequest;
import com.alipay.mobile.common.transport.h5.H5HttpUrlResponse;
import com.alipay.mobile.common.transport.h5.H5NetworkManager;
import com.alipay.mobile.common.transport.http.CookieAccessHelper;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.http.PerformanceDataCallback;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.common.transport.utils.ZURLEncodedUtil;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportStrategy;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5GetAllResponse;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5ResInputListen;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.callback.H5478Listener;
import com.alipay.mobile.nebula.callback.H5RequestListener;
import com.alipay.mobile.nebula.data.H5Trace;
import com.alipay.mobile.nebula.dev.H5BugmeIdGenerator;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.log.H5Logger;
import com.alipay.mobile.nebula.networksupervisor.H5NetworkSuRequest;
import com.alipay.mobile.nebula.networksupervisor.H5NetworkSuScheduler;
import com.alipay.mobile.nebula.provider.H5478Provider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5DevDebugProvider;
import com.alipay.mobile.nebula.provider.H5ImageProvider;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5IOUtils;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5PatternHelper;
import com.alipay.mobile.nebula.util.H5ResourceCORSUtil;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.APWebViewClient;
import com.alipay.mobile.nebula.webview.H5ResContentList;
import com.alipay.mobile.nebulauc.util.CommonUtil;
import com.alipay.mobile.nebulauc.util.H5ConfigUtil;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.ajx3.util.AjxDebugConfig;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.tao.remotebusiness.js.MtopJSBridge.MtopJSParam;
import com.uc.webview.export.internal.interfaces.EventHandler;
import com.uc.webview.export.internal.interfaces.IRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlipayRequest implements IRequest {
    private static int REQUEST_INDEX = 0;
    /* access modifiers changed from: private */
    public String TAG;
    private boolean alreadyRequest;
    private APWebViewClient apWebViewClient;
    private String appId;
    private String appVersion = "";
    private String bizScenario = "";
    private int bugmeReqId = H5BugmeIdGenerator.nextRequestId();
    private long calculateDataLength;
    private boolean capture;
    private boolean checkCrossOrigin;
    private long costTime;
    private String currentMainDocUrl = "";
    private H5DevDebugProvider devDebugProvider;
    /* access modifiers changed from: private */
    public EventHandler eventHandler;
    private Future<Response> futureResponse;
    private H5NetworkManager h5NetworkManager;
    private H5Page h5Page;
    private H5Page h5Page4Landing;
    /* access modifiers changed from: private */
    public boolean handleResponseGetError = false;
    /* access modifiers changed from: private */
    public Map<String, String> headers;
    private boolean isTinyApp;
    private boolean isUcProxy;
    private JSONArray jsonArrayEncodeHeader;
    private int loadType;
    private String mFallbackOriginUrl = "";
    /* access modifiers changed from: private */
    public boolean mHighPriorityRequest = false;
    private boolean mIsFallbackRequest = false;
    private String mLdcUserGroup = "";
    /* access modifiers changed from: private */
    public boolean mNeedPendingResponse = true;
    /* access modifiers changed from: private */
    public Map<String, String> mNetPerformanceData;
    /* access modifiers changed from: private */
    public long mRequestDuration;
    private boolean mUrlConnectSwitch = false;
    private String method;
    private String packageNick = "";
    /* access modifiers changed from: private */
    public H5PageData pageData;
    private int pageId;
    private String pageUrl;
    /* access modifiers changed from: private */
    public PendingResponse pendingResponse;
    /* access modifiers changed from: private */
    public String protocol = "h1";
    private String publicId;
    private String releaseType;
    /* access modifiers changed from: private */
    public int requestType;
    /* access modifiers changed from: private */
    public int statusCode = 200;
    private final Object syncLock = new Object();
    private Map<String, String> ucHeaders;
    private Map<String, byte[]> uploadDataMap;
    private long uploadFileLength;
    private Map<String, String> uploadFileMap;
    /* access modifiers changed from: private */
    public String url;
    /* access modifiers changed from: private */
    public boolean useNew478Cookie = true;
    /* access modifiers changed from: private */
    public boolean useSpdy;
    /* access modifiers changed from: private */
    public String viewId;

    private class H5RequestAdapter extends TransportCallbackAdapter {
        private H5RequestAdapter() {
        }

        public void onCancelled(Request request) {
            super.onCancelled(request);
        }

        public void onPreExecute(Request request) {
            if (H5Trace.isTraceEnable()) {
                H5Trace.sessionBegin("AlipayRequest", AlipayRequest.this.viewId, "url", AlipayRequest.this.url);
            }
            super.onPreExecute(request);
        }

        public void onPostExecute(Request request, Response response) {
            H5HttpUrlResponse httpUrlResponse = (H5HttpUrlResponse) response;
            if (AlipayRequest.this.eventHandler != null) {
                AlipayRequest.this.handleResponse(httpUrlResponse);
            } else if (AlipayRequest.this.mNeedPendingResponse) {
                AlipayRequest.this.pendingResponse = new PendingResponse(httpUrlResponse);
            } else if (httpUrlResponse != null) {
                try {
                    H5IOUtils.closeQuietly(httpUrlResponse.getInputStream());
                } catch (Throwable e) {
                    H5Log.e((String) "onPostExecute close httpUrlResponse exception ", e);
                }
            }
            if (H5Trace.isTraceEnable()) {
                H5Trace.sessionEnd("AlipayRequest", AlipayRequest.this.viewId, "url", AlipayRequest.this.url);
            }
        }

        public void onFailed(Request request, int code, String msg) {
            AlipayRequest.this.handleResponseGetError = true;
            String requestMethod = ((HttpUrlRequest) request).getRequestMethod();
            H5Log.d(AlipayRequest.this.TAG, "asyncRequest onFailed code " + code + " msg " + msg + " url " + AlipayRequest.this.url);
            if (code == 1) {
                code = -7;
            }
            if (ExtTransportStrategy.EXT_PROTO_SPDY.equals(((HttpUrlRequest) request).getTag("linkType"))) {
                H5Log.d(AlipayRequest.this.TAG, "spdy failed, try http");
                AlipayRequest.this.monitorLogger(AlipayRequest.this.url, requestMethod, code, msg, "YES", "NO");
                AlipayRequest.this.request(false, "YES", false);
                if (CommonUtil.isMainDoc(AlipayRequest.this.requestType) && AlipayRequest.this.pageData != null) {
                    AlipayRequest.this.pageData.setErrorSpdyCode(code);
                    AlipayRequest.this.pageData.setErrorSpdyMsg(msg);
                    return;
                }
                return;
            }
            if (CommonUtil.isMainDoc(AlipayRequest.this.requestType) && AlipayRequest.this.pageData != null) {
                AlipayRequest.this.pageData.setStatusCode(code);
                AlipayRequest.this.pageData.setErrorMsg(msg);
            }
            if (AlipayRequest.this.eventHandler == null) {
                AlipayRequest.this.pendingResponse = new PendingResponse(code, msg);
            } else {
                AlipayRequest.this.eventHandler.error(code, msg);
            }
            AlipayRequest.this.monitorLogger(AlipayRequest.this.url, requestMethod, code, msg, "NO", ((HttpUrlRequest) request).getTag("spdyRetry"));
        }
    }

    private class MultiInputStream extends InputStream {
        private ArrayList<InputStream> inputStreams = new ArrayList<>();

        public MultiInputStream() {
        }

        public void addInputStream(InputStream inputStream) {
            this.inputStreams.add(inputStream);
        }

        public int available() {
            int totalLen = 0;
            Iterator<InputStream> it = this.inputStreams.iterator();
            while (it.hasNext()) {
                InputStream item = it.next();
                if (item.available() > 0) {
                    totalLen += item.available();
                }
            }
            return totalLen;
        }

        public int read() {
            for (int i = 0; i < this.inputStreams.size(); i++) {
                int result = this.inputStreams.get(i).read();
                if (result != -1) {
                    return result;
                }
            }
            return -1;
        }

        public int read(byte[] b) {
            for (int i = 0; i < this.inputStreams.size(); i++) {
                int result = this.inputStreams.get(i).read(b);
                if (result != -1) {
                    return result;
                }
            }
            return -1;
        }

        public int read(byte[] b, int off, int len) {
            for (int i = 0; i < this.inputStreams.size(); i++) {
                int result = this.inputStreams.get(i).read(b, off, len);
                if (result != -1) {
                    return result;
                }
            }
            return -1;
        }

        public void close() {
            for (int i = 0; i < this.inputStreams.size(); i++) {
                try {
                    this.inputStreams.get(i).close();
                } catch (Exception e) {
                    H5Log.e(AlipayRequest.this.TAG, "MultiInputStream close ", e);
                }
            }
        }
    }

    private class NetPerformanceDataCallbackImpl extends PerformanceDataCallback {
        private NetPerformanceDataCallbackImpl() {
        }

        public void callback(Map<String, String> performanceMap) {
            AlipayRequest.this.mNetPerformanceData = performanceMap;
            if (!AlipayRequest.this.handleResponseGetError && AlipayRequest.this.pageData != null && AlipayNetwork.sEnableUploadNetInfoWhenSuccess && AlipayNetwork.canUploadNetInfo(AlipayRequest.this.url)) {
                AlipayRequest.this.pageData.addNetRequestInfo(H5Utils.getCleanUrl(AlipayRequest.this.url), NetInfoGenerator.generateNetInfo(AlipayRequest.this.TAG, AlipayRequest.this.mNetPerformanceData, AlipayRequest.this.protocol, AlipayRequest.this.mHighPriorityRequest, AlipayRequest.this.statusCode, AlipayRequest.this.mRequestDuration));
            }
        }
    }

    private class PendingResponse {
        int errorCode;
        String errorMsg;
        H5HttpUrlResponse response = null;

        PendingResponse(H5HttpUrlResponse response2) {
            this.response = response2;
        }

        PendingResponse(int errorCode2, String errorMsg2) {
            this.errorCode = errorCode2;
            this.errorMsg = errorMsg2;
        }

        public void setEventHandler(EventHandler handler) {
            if (handler == null) {
                return;
            }
            if (this.response != null) {
                H5Log.d(AlipayRequest.this.TAG, "response: " + this.response.getStatusLine());
                AlipayRequest.this.handleResponse(this.response);
                return;
            }
            H5Log.d(AlipayRequest.this.TAG, "errorCode: " + this.errorCode + " errorMsg:" + this.errorMsg);
            handler.error(this.errorCode, this.errorMsg);
        }
    }

    public AlipayRequest(String url2, String method2, boolean isUCProxyReq, Map<String, String> headers2, Map<String, String> ucHeaders2, Map<String, String> uploadFileMap2, Map<String, byte[]> uploadDataMap2, long uploadFileTotalLen, int requestType2, int loadType2) {
        boolean z = true;
        this.url = url2;
        this.method = method2;
        this.isUcProxy = isUCProxyReq;
        this.headers = headers2;
        if (this.headers == null) {
            this.headers = new HashMap();
        }
        this.ucHeaders = ucHeaders2;
        this.uploadFileMap = uploadFileMap2;
        this.uploadDataMap = uploadDataMap2;
        this.uploadFileLength = uploadFileTotalLen;
        this.requestType = requestType2;
        this.loadType = loadType2;
        this.capture = false;
        this.useSpdy = false;
        this.jsonArrayEncodeHeader = H5ConfigUtil.getConfigJSONArray("h5_androidEncodeHeader");
        this.useNew478Cookie = TextUtils.equals("NO", H5ConfigUtil.getConfig("h5_useNew478Cookie")) ? false : z;
        this.checkCrossOrigin = false;
        this.mUrlConnectSwitch = MiscUtils.grayscaleUtdidForABTest(TransportConfigureItem.GO_URLCONNECTION_SWITCH);
    }

    public void applyStartParams(Bundle startParam) {
        this.appId = H5Utils.getString(startParam, (String) "appId");
        this.publicId = H5Utils.getString(startParam, (String) H5Param.PUBLIC_ID);
        this.pageUrl = H5Utils.getString(startParam, (String) "url");
        this.isTinyApp = H5AppUtil.isAppChannel4(startParam);
        this.releaseType = H5Utils.getString(startParam, (String) "release_type");
        this.appVersion = H5Utils.getString(startParam, (String) "appVersion");
        this.packageNick = H5Utils.getString(startParam, (String) H5AppUtil.package_nick);
        this.bizScenario = H5Utils.getString(startParam, (String) H5Param.LONG_BIZ_SCENARIO);
        this.devDebugProvider = (H5DevDebugProvider) H5Utils.getProvider(H5DevDebugProvider.class.getName());
        if (this.eventHandler != null) {
            this.TAG = "AlipayRequest";
        } else {
            this.TAG = "AlipayRequest#Pre#";
        }
        StringBuilder append = new StringBuilder().append(this.TAG);
        int i = REQUEST_INDEX;
        REQUEST_INDEX = i + 1;
        this.TAG = append.append(i).append(Token.SEPARATOR).append(this.appId).toString();
        modifyEmbedWebViewParam(startParam);
    }

    private void modifyEmbedWebViewParam(Bundle startParam) {
        if (!TextUtils.isEmpty(H5Utils.getString(startParam, (String) "MINI-PROGRAM-WEB-VIEW-TAG")) && enableModifyEmbedWebViewParam()) {
            H5Log.d(this.TAG, "modifyEmbedWebViewParam");
            this.isTinyApp = true;
            this.appId = H5Utils.getString(startParam, (String) "parentAppId");
            this.appVersion = H5Utils.getString(startParam, (String) "parentVersion");
            this.packageNick = H5Utils.getString(startParam, (String) "parentPackageNick");
        }
    }

    public void attachPage(H5Page h5Page2) {
        this.viewId = H5BugmeIdGenerator.getBugmeViewId(h5Page2);
        this.pageData = h5Page2.getPageData();
        this.currentMainDocUrl = h5Page2.getUrl();
        this.apWebViewClient = h5Page2.getAPWebViewClient();
        this.h5Page = h5Page2;
    }

    public void cancel() {
        H5Log.d(this.TAG, "cancel " + this.url);
        complete();
        cancelFuture();
    }

    private void cancelFuture() {
        if (this.futureResponse != null) {
            try {
                this.futureResponse.cancel(true);
            } catch (Exception e) {
                H5Log.e(this.TAG, "cancelFuture exception ", e);
            }
        }
    }

    public EventHandler getEventHandler() {
        return this.eventHandler;
    }

    public void setEventHandler(EventHandler h) {
        if (h != null) {
            this.eventHandler = h;
            this.eventHandler.setRequest(this);
            this.eventHandler.setResourceType(this.requestType);
            if (this.pendingResponse != null) {
                H5Log.d(this.TAG, "pendingResponse != null, put response to EventHandler!");
                this.pendingResponse.setEventHandler(h);
            }
        }
    }

    public int getRequestType() {
        return this.requestType;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public Map<String, String> getUCHeaders() {
        return this.ucHeaders;
    }

    public void handleSslErrorResponse(boolean proceed) {
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void waitUntilComplete(int r7) {
        /*
            r6 = this;
            java.lang.String r1 = r6.TAG
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "waitUntilComplete timeout "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r7)
            java.lang.String r2 = r2.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r1, r2)
            boolean r1 = r6.isSynchronous()
            if (r1 == 0) goto L_0x0028
            java.lang.Object r2 = r6.syncLock
            monitor-enter(r2)
            java.lang.Object r1 = r6.syncLock     // Catch:{ Throwable -> 0x0029 }
            long r4 = (long) r7     // Catch:{ Throwable -> 0x0029 }
            r1.wait(r4)     // Catch:{ Throwable -> 0x0029 }
        L_0x0027:
            monitor-exit(r2)     // Catch:{ all -> 0x0032 }
        L_0x0028:
            return
        L_0x0029:
            r0 = move-exception
            java.lang.String r1 = r6.TAG     // Catch:{ all -> 0x0032 }
            java.lang.String r3 = "waitUntilComplete exception."
            com.alipay.mobile.nebula.util.H5Log.e(r1, r3, r0)     // Catch:{ all -> 0x0032 }
            goto L_0x0027
        L_0x0032:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0032 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulauc.impl.network.AlipayRequest.waitUntilComplete(int):void");
    }

    /* access modifiers changed from: 0000 */
    public void complete() {
        if (isSynchronous()) {
            synchronized (this.syncLock) {
                H5Log.d(this.TAG, "complete will notify");
                this.syncLock.notifyAll();
            }
        }
    }

    public String getUrl() {
        return this.url;
    }

    public String getMethod() {
        return this.method;
    }

    public boolean getIsUCProxy() {
        return this.isUcProxy;
    }

    public Map<String, String> getUploadFileMap() {
        return this.uploadFileMap;
    }

    public Map<String, byte[]> getUploadDataMap() {
        return this.uploadDataMap;
    }

    public long getUploadFileTotalLen() {
        return this.uploadFileLength;
    }

    public int getLoadtype() {
        return this.loadType;
    }

    public String getAppId() {
        return this.appId;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x005f A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.alipay.mobile.nebulauc.impl.network.AlipayRequest.MultiInputStream initRequestInputStream() {
        /*
            r12 = this;
            long r8 = r12.uploadFileLength
            r10 = 0
            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r8 <= 0) goto L_0x0010
            java.util.Map<java.lang.String, byte[]> r8 = r12.uploadDataMap
            if (r8 == 0) goto L_0x0010
            java.util.Map<java.lang.String, java.lang.String> r8 = r12.uploadFileMap
            if (r8 != 0) goto L_0x0012
        L_0x0010:
            r5 = 0
        L_0x0011:
            return r5
        L_0x0012:
            java.util.Map<java.lang.String, byte[]> r8 = r12.uploadDataMap
            int r8 = r8.size()
            java.util.Map<java.lang.String, java.lang.String> r9 = r12.uploadFileMap
            int r9 = r9.size()
            int r6 = r8 + r9
            com.alipay.mobile.nebulauc.impl.network.AlipayRequest$MultiInputStream r5 = new com.alipay.mobile.nebulauc.impl.network.AlipayRequest$MultiInputStream
            r5.<init>()
            r2 = 0
        L_0x0026:
            if (r2 >= r6) goto L_0x0011
            r3 = 0
            java.util.Map<java.lang.String, java.lang.String> r8 = r12.uploadFileMap
            java.lang.String r9 = java.lang.String.valueOf(r2)
            java.lang.Object r1 = r8.get(r9)
            java.lang.String r1 = (java.lang.String) r1
            boolean r8 = android.text.TextUtils.isEmpty(r1)
            if (r8 != 0) goto L_0x0070
            java.io.InputStream r3 = r12.file2InputStream(r1)
            r4 = r3
        L_0x0040:
            if (r4 != 0) goto L_0x006e
            java.util.Map<java.lang.String, byte[]> r8 = r12.uploadDataMap     // Catch:{ Throwable -> 0x0062 }
            java.lang.String r9 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x0062 }
            java.lang.Object r0 = r8.get(r9)     // Catch:{ Throwable -> 0x0062 }
            byte[] r0 = (byte[]) r0     // Catch:{ Throwable -> 0x0062 }
            java.io.ByteArrayInputStream r3 = new java.io.ByteArrayInputStream     // Catch:{ Throwable -> 0x0062 }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x0062 }
            long r8 = r12.calculateDataLength     // Catch:{ Throwable -> 0x006c }
            int r10 = r0.length     // Catch:{ Throwable -> 0x006c }
            long r10 = (long) r10     // Catch:{ Throwable -> 0x006c }
            long r8 = r8 + r10
            r12.calculateDataLength = r8     // Catch:{ Throwable -> 0x006c }
        L_0x005a:
            if (r3 == 0) goto L_0x005f
            r5.addInputStream(r3)
        L_0x005f:
            int r2 = r2 + 1
            goto L_0x0026
        L_0x0062:
            r7 = move-exception
            r3 = r4
        L_0x0064:
            java.lang.String r8 = r12.TAG
            java.lang.String r9 = "catch exception "
            com.alipay.mobile.nebula.util.H5Log.e(r8, r9, r7)
            goto L_0x005a
        L_0x006c:
            r7 = move-exception
            goto L_0x0064
        L_0x006e:
            r3 = r4
            goto L_0x005a
        L_0x0070:
            r4 = r3
            goto L_0x0040
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulauc.impl.network.AlipayRequest.initRequestInputStream():com.alipay.mobile.nebulauc.impl.network.AlipayRequest$MultiInputStream");
    }

    private InputStream file2InputStream(String fileName) {
        InputStream inputStream = null;
        try {
            if (fileName.toLowerCase().startsWith("content://")) {
                InputStream inputStream2 = H5Utils.getContext().getContentResolver().openInputStream(Uri.parse(fileName));
                this.calculateDataLength += (long) inputStream2.available();
                return inputStream2;
            }
            InputStream inputStream3 = new FileInputStream(fileName);
            try {
                this.calculateDataLength += new File(fileName).length();
                return inputStream3;
            } catch (Throwable th) {
                t = th;
                inputStream = inputStream3;
                H5Log.e(this.TAG, "file2InputStream exception.", t);
                return inputStream;
            }
        } catch (Throwable th2) {
            t = th2;
            H5Log.e(this.TAG, "file2InputStream exception.", t);
            return inputStream;
        }
    }

    private byte[] initDatas() {
        if (this.uploadFileLength <= 0 || this.uploadDataMap == null || this.uploadFileMap == null) {
            return null;
        }
        int size = this.uploadDataMap.size() + this.uploadFileMap.size();
        H5Log.d(this.TAG, "initDatas uploadDataMap size " + this.uploadDataMap.size());
        List uploadDataList = new ArrayList();
        for (int index = 0; index < size; index++) {
            if (TextUtils.isEmpty(this.uploadFileMap.get(String.valueOf(index)))) {
                uploadDataList.add(this.uploadDataMap.get(String.valueOf(index)));
            }
        }
        return sysCopy(uploadDataList);
    }

    private byte[] sysCopy(List<byte[]> srcArrays) {
        int len = 0;
        for (byte[] srcArray : srcArrays) {
            len += srcArray.length;
        }
        byte[] destArray = new byte[len];
        int destLen = 0;
        for (byte[] srcArray2 : srcArrays) {
            System.arraycopy(srcArray2, 0, destArray, destLen, srcArray2.length);
            destLen += srcArray2.length;
        }
        return destArray;
    }

    private void setRequestMethod(H5HttpUrlRequest request) {
        request.setRequestMethod(this.method);
        H5Log.d(this.TAG, "setRequestMethod " + this.method);
    }

    /* access modifiers changed from: 0000 */
    public void setNeedPendingResponse(boolean needPendingResponse) {
        this.mNeedPendingResponse = needPendingResponse;
    }

    /* access modifiers changed from: 0000 */
    public boolean sendRequest(boolean useSpdy2, String spdyRetry, boolean add478Header) {
        if (this.alreadyRequest) {
            H5Log.d(this.TAG, "request already sent! " + this.url);
            return true;
        }
        this.alreadyRequest = true;
        if (handleSubResForSec() || handleResourceContent()) {
            return true;
        }
        handleFallbackRequest();
        if (this.isTinyApp && !CommonUtil.isMainDoc(this.requestType)) {
            useSpdy2 = !subResUseHttpLinkInTinyApp();
            if (useSpdy2 && !CommonUtil.tinyProcessUseSpdy(this.appId)) {
                useSpdy2 = false;
            }
        }
        return request(useSpdy2, spdyRetry, add478Header);
    }

    private void handleFallbackRequest() {
        H5ContentProvider h5ContentProvider;
        String fallbackUrl = null;
        if (this.h5Page != null) {
            H5Session session = this.h5Page.getSession();
            if (session != null) {
                h5ContentProvider = session.getWebProvider();
            } else {
                h5ContentProvider = null;
            }
            if (h5ContentProvider != null) {
                fallbackUrl = h5ContentProvider.getFallbackUrl(this.url);
            }
            if (!TextUtils.isEmpty(fallbackUrl)) {
                H5Log.d(this.TAG, "handleFallbackRequest originUrl : " + this.url + ", fallbackUrl : " + fallbackUrl);
                this.mIsFallbackRequest = true;
                this.mFallbackOriginUrl = this.url;
                this.url = fallbackUrl;
            }
        }
    }

    private boolean handleSubResForSec() {
        try {
            if (CommonUtil.isMainDoc(this.requestType) || !AlipayNetwork.shouldInterceptSubResRequestForSec(this.url)) {
                return false;
            }
            byte[] responseDate = "该资源请求已被禁止".getBytes("utf-8");
            this.eventHandler.status(0, 0, 579, "");
            Map headers2 = new HashMap();
            List contentLengthList = new ArrayList();
            contentLengthList.add(responseDate.length + "");
            headers2.put("Content-Length", contentLengthList);
            List typeList = new ArrayList();
            typeList.add(H5FileUtil.getMimeType(H5UrlHelper.getPath(this.url)));
            headers2.put("Content-Type", typeList);
            this.eventHandler.headers(headers2);
            this.eventHandler.data(responseDate, responseDate.length);
            this.eventHandler.endData();
            H5Log.d(this.TAG, "handleSubResForSec url : " + this.url);
            return true;
        } catch (Throwable throwable) {
            H5Log.e(this.TAG, "handleSubResForSec exception :", throwable);
            return false;
        }
    }

    private boolean handleResourceContent() {
        try {
            if (!H5ResContentList.enableResHttpCache() || this.eventHandler == null || !H5ResContentList.getInstance().contains(this.url)) {
                return false;
            }
            byte[] urlData = H5ResContentList.getInstance().get(this.url);
            if (urlData == null || urlData.length <= 0) {
                return false;
            }
            this.eventHandler.status(0, 0, 200, "");
            Map map = new HashMap();
            List list = new ArrayList();
            list.add("max-age=" + H5ResContentList.getCacheTime());
            map.put("Cache-Control", list);
            List contentLengthList = new ArrayList();
            contentLengthList.add(urlData.length + "");
            map.put("Content-Length", contentLengthList);
            List typeList = new ArrayList();
            typeList.add(H5FileUtil.getMimeType(H5UrlHelper.getPath(this.url)));
            map.put("Content-Type", typeList);
            if (H5Utils.enableCheckCrossOrigin()) {
                String cosUrl = H5ResourceCORSUtil.getCORSUrl(this.pageUrl);
                if (this.checkCrossOrigin && !TextUtils.isEmpty(cosUrl)) {
                    List originValueList = new ArrayList();
                    originValueList.add(cosUrl);
                    map.put("Access-Control-Allow-Origin", originValueList);
                }
            }
            this.eventHandler.headers(map);
            this.eventHandler.data(urlData, urlData.length);
            this.eventHandler.endData();
            H5Log.d(this.TAG, "load response from H5ResContentList : " + this.url);
            if (this.pageData != null) {
                this.pageData.setAliRequestResNum(this.pageData.getAliRequestResNum() + 1);
            }
            return true;
        } catch (Throwable throwable) {
            H5Log.e(this.TAG, throwable);
            return false;
        }
    }

    private boolean isSynchronous() {
        return this.eventHandler != null && this.eventHandler.isSynchronous();
    }

    public boolean request(boolean useSpdy2, String spdyRetry, boolean add478Header) {
        this.costTime = System.currentTimeMillis();
        if (useSpdy2) {
            H5Log.d(this.TAG, "linkType SPDY_LINK " + this.url);
        } else {
            H5Log.d(this.TAG, "linkType HTTP_LINK " + this.url);
        }
        String type = CommonUtil.matchMultimediaImageType(this.appId, this.url);
        if (!TextUtils.isEmpty(type)) {
            H5ImageProvider h5ImageProvider = (H5ImageProvider) H5Utils.getProvider(H5ImageProvider.class.getName());
            if (h5ImageProvider != null) {
                final String str = type;
                final long currentTimeMillis = System.currentTimeMillis();
                h5ImageProvider.getImageWithByte(this.url, new H5ResInputListen() {
                    public void onGetInput(InputStream inputStream) {
                        try {
                            long onGetInputTime = System.currentTimeMillis();
                            Map map = new HashMap();
                            List list = new ArrayList();
                            list.add(str);
                            map.put("content-type", list);
                            AlipayRequest.this.eventHandler.headers(map);
                            if (inputStream == null) {
                                H5Log.d(AlipayRequest.this.TAG, "onGetInput inputStream null return");
                                AlipayRequest.this.eventHandler.endData();
                                AlipayRequest.this.complete();
                                H5IOUtils.closeQuietly(inputStream);
                                return;
                            }
                            while (true) {
                                int length = -1;
                                byte[] buffer = new byte[4096];
                                length = inputStream.read(buffer);
                                if (length == -1) {
                                    AlipayRequest.this.eventHandler.endData();
                                    long currentTime = System.currentTimeMillis();
                                    H5Log.d(AlipayRequest.this.TAG, "load response form multiMedia :" + AlipayRequest.this.url + " total coast:" + (currentTime - currentTimeMillis) + " onGetInputTime coast:" + (onGetInputTime - currentTimeMillis) + " parseInput coast:" + (currentTime - onGetInputTime));
                                    AlipayRequest.this.complete();
                                    H5IOUtils.closeQuietly(inputStream);
                                    return;
                                }
                                AlipayRequest.this.eventHandler.data(buffer, length);
                            }
                        } catch (EOFException e) {
                            H5Log.w(AlipayRequest.this.TAG, "handleResponse eof " + e.toString());
                        } catch (Throwable throwable) {
                            try {
                                H5Log.e(AlipayRequest.this.TAG, throwable);
                            } finally {
                                AlipayRequest.this.complete();
                                H5IOUtils.closeQuietly(inputStream);
                            }
                        }
                    }
                });
            }
            return true;
        }
        H5Log.d(this.TAG, "request url " + this.url + " sync " + isSynchronous() + " capture " + this.capture);
        handleServiceWorker();
        if (interceptInValidDomain()) {
            return true;
        }
        handle478Header(add478Header);
        this.useSpdy = useSpdy2;
        this.mHighPriorityRequest = AlipayNetwork.isHighPriority(this.appId, this.url);
        H5HttpUrlRequest h5HttpUrlRequest = new H5HttpUrlRequest(this.url);
        h5HttpUrlRequest.setCapture(this.capture);
        h5HttpUrlRequest.setUseSystemH2(AlipayNetwork.isUseHttp2(this.url));
        h5HttpUrlRequest.setRadicalStrategy(this.mHighPriorityRequest);
        if (!this.isTinyApp || BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5ConfigUtil.getConfig("h5_getReferWithAppIdInResource"))) {
            h5HttpUrlRequest.setRefer(this.currentMainDocUrl);
        } else {
            h5HttpUrlRequest.setRefer(getReferWithAppId());
        }
        handleSocketTimeout(h5HttpUrlRequest);
        try {
            H5Log.d(this.TAG, "isUploadLog : " + H5Flag.isUploadLog);
            h5HttpUrlRequest.setPrintUrlToMonitorLog(H5Flag.isUploadLog);
        } catch (Throwable t) {
            H5Log.e(this.TAG, t);
        }
        if (!TextUtils.isEmpty(this.appId)) {
            h5HttpUrlRequest.addTags("bizId", this.appId);
        }
        if (this.isTinyApp) {
            h5HttpUrlRequest.addTags(TransportConstants.KEY_H5_APP_TYPE, "mini_app");
        }
        setRequestMethod(h5HttpUrlRequest);
        if (useSpdy2) {
            h5HttpUrlRequest.linkType = 1;
            h5HttpUrlRequest.addTags("linkType", ExtTransportStrategy.EXT_PROTO_SPDY);
        } else {
            h5HttpUrlRequest.linkType = 2;
            h5HttpUrlRequest.addTags("linkType", "http");
            h5HttpUrlRequest.addTags("spdyRetry", spdyRetry);
        }
        for (String key : this.headers.keySet()) {
            String value = this.headers.get(key);
            if (!"Content-Length".equals(key)) {
                boolean needEncodeValue = false;
                if (this.jsonArrayEncodeHeader != null && !this.jsonArrayEncodeHeader.isEmpty()) {
                    int i = 0;
                    while (true) {
                        if (i >= this.jsonArrayEncodeHeader.size()) {
                            break;
                        } else if (this.jsonArrayEncodeHeader.getString(i).equalsIgnoreCase(key)) {
                            needEncodeValue = true;
                            break;
                        } else {
                            i++;
                        }
                    }
                }
                if (needEncodeValue) {
                    H5Log.d(this.TAG, "use ZURLEncodedUtil encode request header " + key);
                    value = ZURLEncodedUtil.urlEncode(value);
                }
                H5ConfigProvider configProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                if (configProvider != null) {
                    if ((!TextUtils.equals("NO", configProvider.getConfig("h5_enableDetectIfHasContent"))) && MtopJSParam.USER_AGENT.equalsIgnoreCase(key)) {
                        Matcher matcher = Pattern.compile(" ChannelId\\(([^)]*)\\)").matcher(value);
                        if (matcher != null && matcher.find()) {
                            String tmp = matcher.group(0);
                            if (!TextUtils.isEmpty(tmp)) {
                                this.pageId = Integer.parseInt(tmp.substring(11, tmp.length() - 1));
                                H5Log.debug(this.TAG, "pageId " + this.pageId);
                                value = value.replace(tmp, "");
                            }
                        }
                        if (this.requestType == 0) {
                            this.h5Page4Landing = this.h5Page;
                        }
                    }
                }
                h5HttpUrlRequest.addHeader(key, value);
                H5Log.d(this.TAG, "request headers " + key + Token.SEPARATOR + value);
            }
        }
        if (useSpdy2) {
            H5Log.d(this.TAG, "request extra headers su584publicid & su584userid & su584appid");
            if (!TextUtils.isEmpty(this.publicId)) {
                h5HttpUrlRequest.addHeader("su584publicid", this.publicId);
            }
            String userId = CommonUtil.getUserId();
            if (!TextUtils.isEmpty(userId)) {
                h5HttpUrlRequest.addHeader("su584userid", userId);
            }
            if (!TextUtils.isEmpty(this.appId)) {
                h5HttpUrlRequest.addHeader("su584appid", this.appId);
            }
            if (!TextUtils.isEmpty(this.releaseType)) {
                h5HttpUrlRequest.addHeader("x-release-type", this.releaseType);
            }
            h5HttpUrlRequest.addHeader("su584bizscenario", this.bizScenario);
        }
        if (useSpdy2 && this.isTinyApp) {
            h5HttpUrlRequest.addHeader("su584channelapplet", "Y");
            h5HttpUrlRequest.addHeader("su584appletpage", ZURLEncodedUtil.urlEncode(this.currentMainDocUrl));
            h5HttpUrlRequest.addHeader("su584appversion", this.appVersion);
            h5HttpUrlRequest.addHeader("su584tinyappversion", this.packageNick);
        }
        if (enableLdcLevel()) {
            if (!TextUtils.isEmpty(H5Utils.ldcLevel)) {
                h5HttpUrlRequest.addHeader("x-ldcid-level", H5Utils.ldcLevel);
            }
            if (!TextUtils.isEmpty(this.mLdcUserGroup)) {
                h5HttpUrlRequest.addHeader("x-user-group", this.mLdcUserGroup);
            }
        }
        if (H5PreConnectManager.isPreConnectEnabled()) {
            H5PreConnectManager.refreshPreConnect(this.url);
        }
        try {
            h5HttpUrlRequest.setAsyncMonitorLog(true);
        } catch (Throwable throwable) {
            H5Log.e(this.TAG, throwable);
        }
        if (this.uploadFileLength > 0) {
            if (this.uploadFileMap == null || this.uploadFileMap.isEmpty()) {
                h5HttpUrlRequest.setReqData(initDatas());
            } else {
                MultiInputStream initRequestInputStream = initRequestInputStream();
                h5HttpUrlRequest.setInputStream(initRequestInputStream);
                try {
                    H5Log.d(this.TAG, "setInputStream dataLengthAvail " + initRequestInputStream.available() + ", dataLengthCal " + this.calculateDataLength);
                } catch (IOException e) {
                    H5Log.e(this.TAG, "setInputStream available exception ", e);
                }
                h5HttpUrlRequest.setDataLength(this.calculateDataLength);
            }
        }
        if (this.devDebugProvider != null) {
            H5Service h5Service = H5ServiceUtils.getH5Service();
            if (!(h5Service == null || h5Service.getBugMeManager() == null || !h5Service.getBugMeManager().hasAccessToDebug(this.pageUrl))) {
                JSONObject jSONObject = new JSONObject(5);
                jSONObject.put((String) "reqUrl", (Object) this.url);
                jSONObject.put((String) "reqId", (Object) Integer.valueOf(this.bugmeReqId));
                jSONObject.put((String) "method", (Object) this.method);
                this.devDebugProvider.netWorkLog(H5PageData.KEY_UC_START, this.viewId, jSONObject);
            }
        }
        H5RequestListener requestListener = (H5RequestListener) H5Utils.getProvider(H5RequestListener.class.getName());
        if (requestListener != null) {
            requestListener.onRequest(this.appId, this.pageUrl, this.url, this.isTinyApp, null);
        }
        if (isSupportNetworkSupervisor()) {
            H5Log.d(this.TAG, "h5netsupervisor request begin");
            long start = SystemClock.uptimeMillis();
            H5NetworkSuRequest suRequest = new H5NetworkSuRequest();
            suRequest.setUrl(this.url);
            suRequest.setHeaders(this.headers);
            suRequest.setMethod(this.method);
            byte[] reqData = h5HttpUrlRequest.getReqData();
            if (reqData == null) {
                InputStream inputStream = h5HttpUrlRequest.getInputStream();
                if (inputStream != null) {
                    reqData = H5IOUtils.inputToByte(inputStream);
                    H5IOUtils.closeQuietly(inputStream);
                    h5HttpUrlRequest.setInputStream(new ByteArrayInputStream(reqData));
                }
            }
            suRequest.setBody(reqData);
            H5NetworkSuScheduler.getInstance().post(suRequest);
            H5Log.d(this.TAG, "h5netsupervisor request end cost " + (SystemClock.uptimeMillis() - start));
        }
        asyncRequest(h5HttpUrlRequest);
        return true;
    }

    private String getReferWithAppId() {
        Uri uri = H5UrlHelper.parseUrl(this.currentMainDocUrl);
        if (uri == null) {
            return this.currentMainDocUrl;
        }
        String path = uri.getPath();
        if (TextUtils.isEmpty(path)) {
            return this.currentMainDocUrl;
        }
        try {
            int pathIndex = this.currentMainDocUrl.indexOf(path);
            if (pathIndex == -1) {
                return this.currentMainDocUrl;
            }
            return this.currentMainDocUrl.substring(0, pathIndex) + "/" + this.appId + "/" + this.appVersion + this.currentMainDocUrl.substring(pathIndex, this.currentMainDocUrl.length());
        } catch (Throwable thr) {
            H5Log.e(this.TAG, "getReferWithAppId subString", thr);
            return this.currentMainDocUrl;
        }
    }

    private boolean isSupportNetworkSupervisor() {
        boolean enable;
        if (!BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5ConfigUtil.getConfig("enableH5NetworkSupervisor"))) {
            enable = true;
        } else {
            enable = false;
        }
        return !H5Utils.isInTinyProcess() && enable && !CommonUtil.isApk(this.url) && (this.requestType == 0 || this.requestType == 1 || this.requestType == 4 || this.requestType == 14);
    }

    private boolean enableLdcLevel() {
        if (this.isTinyApp || !CommonUtil.isMainDoc(this.requestType)) {
            return false;
        }
        Uri uri = H5UrlHelper.parseUrl(this.url);
        if (uri == null) {
            return false;
        }
        JSONObject jsonObjLdcLevel = H5ConfigUtil.getConfigJSONObject("h5_ldcLevelConfig");
        if (!H5Utils.getBoolean(jsonObjLdcLevel, (String) "enable", false)) {
            return false;
        }
        boolean isUrlMatch = CommonUtil.isUrlMatch(uri.getHost(), H5Utils.getJSONArray(jsonObjLdcLevel, "domainList", null));
        this.mLdcUserGroup = H5Utils.getString(jsonObjLdcLevel, (String) "group", (String) "");
        return isUrlMatch;
    }

    private void asyncRequest(H5HttpUrlRequest h5HttpUrlRequest) {
        H5Log.d(this.TAG, "asyncRequest " + this.url);
        this.mRequestDuration = System.currentTimeMillis();
        h5HttpUrlRequest.setTransportCallback(new H5RequestAdapter());
        h5HttpUrlRequest.setPerformanceDataCallback(new NetPerformanceDataCallbackImpl());
        this.futureResponse = this.h5NetworkManager.enqueue(h5HttpUrlRequest);
    }

    public void setNetWorkManager(H5NetworkManager netWorkManager) {
        this.h5NetworkManager = netWorkManager;
    }

    public void setCheckCrossOrigin(boolean check) {
        this.checkCrossOrigin = check;
    }

    /* access modifiers changed from: private */
    public void monitorLogger(String url2, String method2, int errorCode, String errorMsg, String isSpdy, String spdyRetry) {
        H5LogData logData = H5LogData.seedId("H5_AL_NETWORK_PERFORMANCE_ERROR").param1().add(AjxDebugConfig.PERFORMANCE, null).param2().add("appId", this.appId).add("url", this.pageUrl).add("viewId", url2).add("refViewId", url2).add("token", H5Logger.getToken()).param3().add("targetUrl", url2).add("method", method2).add("type", CommonUtil.getMimeType(url2)).add("SPDY", isSpdy).add("SPDYRetry", spdyRetry).param4().add("errorCode", Integer.valueOf(errorCode)).add("errorDesc", errorMsg).add("resLdcLevelId", H5Utils.ldcLevel).add("ldcUserGroup", this.mLdcUserGroup);
        if (AlipayNetwork.sEnableUploadNetInfoWhenError) {
            logData.param4().add("netinfo", NetInfoGenerator.generateNetInfo(this.TAG, this.mNetPerformanceData, null, this.mHighPriorityRequest, errorCode, -1));
        }
        if (this.h5Page != null) {
            logData.param4().addUniteParam(this.h5Page.getPageData());
        }
        H5LogUtil.logH5Exception(logData);
        H5Log.d(this.TAG, "trigger abnormal log upload immediately.");
        LoggerFactory.getLogContext().upload(LogCategory.CATEGORY_WEBAPP);
    }

    private InputStream handlerInput(InputStream realStream) {
        if (H5Utils.isDebuggable(H5Utils.getContext()) && realStream != null) {
            H5GetAllResponse h5GetAllResponse = (H5GetAllResponse) H5Utils.getProvider(H5GetAllResponse.class.getName());
            if (h5GetAllResponse != null) {
                boolean enableStamper = H5Utils.getConfigBoolean(H5Utils.getContext(), "h5_stamper");
                H5Log.d(this.TAG, "h5_stamper is " + enableStamper);
                if (enableStamper) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    try {
                        byte[] buffer2 = new byte[1024];
                        while (true) {
                            int len = realStream.read(buffer2);
                            if (len > -1) {
                                baos.write(buffer2, 0, len);
                            } else {
                                baos.flush();
                                InputStream stream1 = new ByteArrayInputStream(baos.toByteArray());
                                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(baos.toByteArray());
                                h5GetAllResponse.setData(this.url, stream1);
                                return byteArrayInputStream;
                            }
                        }
                    } catch (Exception e) {
                        H5Log.d(this.TAG, "excetion : " + e);
                    }
                } else {
                    H5Log.d(this.TAG, "h5GetAllResponse is null");
                }
            }
        }
        return null;
    }

    /* JADX WARNING: type inference failed for: r39v0 */
    /* JADX WARNING: type inference failed for: r39v1, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r39v2, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r39v3 */
    /* JADX WARNING: type inference failed for: r39v4, types: [java.io.Closeable, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r39v5 */
    /* JADX WARNING: type inference failed for: r1v0, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r56v0, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r40v0 */
    /* JADX WARNING: type inference failed for: r40v1, types: [java.io.Closeable, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r39v6 */
    /* JADX WARNING: type inference failed for: r39v7 */
    /* JADX WARNING: type inference failed for: r39v8 */
    /* JADX WARNING: type inference failed for: r39v9, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r0v90, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r39v10 */
    /* JADX WARNING: type inference failed for: r39v11 */
    /* JADX WARNING: type inference failed for: r0v135, types: [java.io.ByteArrayInputStream] */
    /* JADX WARNING: type inference failed for: r39v13 */
    /* JADX WARNING: type inference failed for: r40v2 */
    /* JADX WARNING: type inference failed for: r0v140, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r39v14, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r39v15 */
    /* JADX WARNING: type inference failed for: r39v16 */
    /* JADX WARNING: type inference failed for: r39v17 */
    /* JADX WARNING: type inference failed for: r39v18 */
    /* JADX WARNING: type inference failed for: r39v19 */
    /* JADX WARNING: type inference failed for: r39v20 */
    /* JADX WARNING: type inference failed for: r39v21 */
    /* JADX WARNING: type inference failed for: r39v22 */
    /* JADX WARNING: type inference failed for: r39v23 */
    /* JADX WARNING: type inference failed for: r39v24 */
    /* JADX WARNING: type inference failed for: r39v25 */
    /* JADX WARNING: type inference failed for: r39v26 */
    /* JADX WARNING: type inference failed for: r39v27 */
    /* JADX WARNING: type inference failed for: r39v28 */
    /* JADX WARNING: type inference failed for: r39v29 */
    /* JADX WARNING: type inference failed for: r39v30 */
    /* JADX WARNING: type inference failed for: r0v349, types: [java.io.ByteArrayInputStream] */
    /* JADX WARNING: type inference failed for: r0v350, types: [java.io.ByteArrayInputStream] */
    /* JADX WARNING: type inference failed for: r39v31 */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r39v3
      assigns: []
      uses: []
      mth insns count: 1281
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:205:0x0798  */
    /* JADX WARNING: Removed duplicated region for block: B:213:0x07b4  */
    /* JADX WARNING: Unknown variable types count: 14 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleResponse(com.alipay.mobile.common.transport.h5.H5HttpUrlResponse r64) {
        /*
            r63 = this;
            r39 = 0
            r29 = 0
            java.lang.Class<com.alipay.mobile.nebula.callback.H5RequestListener> r3 = com.alipay.mobile.nebula.callback.H5RequestListener.class
            java.lang.String r3 = r3.getName()
            java.lang.Object r2 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r3)
            com.alipay.mobile.nebula.callback.H5RequestListener r2 = (com.alipay.mobile.nebula.callback.H5RequestListener) r2
            if (r2 == 0) goto L_0x0026
            r0 = r63
            java.lang.String r3 = r0.appId
            r0 = r63
            java.lang.String r4 = r0.pageUrl
            r0 = r63
            java.lang.String r5 = r0.url
            r0 = r63
            boolean r6 = r0.isTinyApp
            r7 = 0
            r2.onResponse(r3, r4, r5, r6, r7)
        L_0x0026:
            r51 = 1
            int r3 = r64.getCode()     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            r0.statusCode = r3     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            int r3 = r0.statusCode     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            boolean r3 = r0.handle478StatusCode(r3)     // Catch:{ Throwable -> 0x046e }
            if (r3 == 0) goto L_0x012a
            r51 = 0
            if (r51 == 0) goto L_0x0050
            r0 = r63
            com.uc.webview.export.internal.interfaces.EventHandler r3 = r0.eventHandler     // Catch:{ Throwable -> 0x00f6 }
            r3.endData()     // Catch:{ Throwable -> 0x00f6 }
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x00f6 }
            java.lang.String r5 = "call endData"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Throwable -> 0x00f6 }
        L_0x0050:
            r63.complete()
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r39)     // Catch:{ Throwable -> 0x0104 }
        L_0x0056:
            boolean r3 = android.text.TextUtils.isEmpty(r29)
            if (r3 != 0) goto L_0x00bc
            java.lang.String r3 = "\\|"
            r0 = r29
            java.lang.String[] r30 = r0.split(r3)
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "alipayDowngrade downgradeRulesArray[0] "
            java.lang.StringBuilder r5 = r5.append(r7)
            r7 = 0
            r7 = r30[r7]
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r7 = ", downgradeRulesArray[1]"
            java.lang.StringBuilder r5 = r5.append(r7)
            r7 = 1
            r7 = r30[r7]
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r3 = 1
            r3 = r30[r3]
            int r28 = java.lang.Integer.parseInt(r3)
            r3 = 0
            r3 = r30[r3]
            int r50 = java.lang.Integer.parseInt(r3)
            r3 = -1
            r0 = r50
            if (r0 != r3) goto L_0x0112
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "alipayDowngrade scope == -1"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r0 = r63
            java.lang.String r3 = r0.url
            r0 = r28
            com.alipay.mobile.nebulauc.impl.network.AlipaySpdyDowngrade.addMemoryDowngradeRule(r3, r0)
        L_0x00b3:
            r3 = 0
            java.lang.String r5 = "NO"
            r7 = 0
            r0 = r63
            r0.request(r3, r5, r7)
        L_0x00bc:
            long r8 = java.lang.System.currentTimeMillis()
            r0 = r63
            long r10 = r0.mRequestDuration
            long r8 = r8 - r10
            r0 = r63
            r0.mRequestDuration = r8
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "request duation = "
            java.lang.StringBuilder r5 = r5.append(r7)
            r0 = r63
            long r8 = r0.mRequestDuration
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r7 = " url= "
            java.lang.StringBuilder r5 = r5.append(r7)
            r0 = r63
            java.lang.String r7 = r0.url
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
        L_0x00f5:
            return
        L_0x00f6:
            r33 = move-exception
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "eventHandler.endData() exception : "
            r0 = r33
            com.alipay.mobile.nebula.util.H5Log.e(r3, r5, r0)
            goto L_0x0050
        L_0x0104:
            r57 = move-exception
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "handleResponse close inputStream exception."
            r0 = r57
            com.alipay.mobile.nebula.util.H5Log.e(r3, r5, r0)
            goto L_0x0056
        L_0x0112:
            if (r50 <= 0) goto L_0x00b3
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "alipayDowngrade scope > 0"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r0 = r63
            java.lang.String r3 = r0.url
            r0 = r50
            long r8 = (long) r0
            r0 = r28
            com.alipay.mobile.nebulauc.impl.network.AlipaySpdyDowngrade.addDiskDowngradeRule(r3, r8, r0)
            goto L_0x00b3
        L_0x012a:
            boolean r3 = r63.interceptSubResponse4Tiny(r64)     // Catch:{ Throwable -> 0x046e }
            if (r3 == 0) goto L_0x0269
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x046e }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x046e }
            r5.<init>()     // Catch:{ Throwable -> 0x046e }
            java.lang.String r7 = "interceptSubResponse4Tiny url "
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            java.lang.String r7 = r0.url     // Catch:{ Throwable -> 0x046e }
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x046e }
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r3 = "H5_interceptSubResponse4Tiny"
            com.alipay.mobile.nebula.log.H5LogData r3 = com.alipay.mobile.nebula.log.H5LogData.seedId(r3)     // Catch:{ Throwable -> 0x046e }
            com.alipay.mobile.nebula.log.H5LogData r3 = r3.param4()     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = "appId"
            r0 = r63
            java.lang.String r7 = r0.appId     // Catch:{ Throwable -> 0x046e }
            com.alipay.mobile.nebula.log.H5LogData r3 = r3.add(r5, r7)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = "url"
            r0 = r63
            java.lang.String r7 = r0.url     // Catch:{ Throwable -> 0x046e }
            com.alipay.mobile.nebula.log.H5LogData r3 = r3.add(r5, r7)     // Catch:{ Throwable -> 0x046e }
            com.alipay.mobile.nebula.log.H5LogUtil.logNebulaTech(r3)     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            com.uc.webview.export.internal.interfaces.EventHandler r3 = r0.eventHandler     // Catch:{ Throwable -> 0x046e }
            r5 = 404(0x194, float:5.66E-43)
            java.lang.String r7 = "can not request subresource that not in package"
            r3.error(r5, r7)     // Catch:{ Throwable -> 0x046e }
            r51 = 0
            if (r51 == 0) goto L_0x018e
            r0 = r63
            com.uc.webview.export.internal.interfaces.EventHandler r3 = r0.eventHandler     // Catch:{ Throwable -> 0x0235 }
            r3.endData()     // Catch:{ Throwable -> 0x0235 }
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x0235 }
            java.lang.String r5 = "call endData"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Throwable -> 0x0235 }
        L_0x018e:
            r63.complete()
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r39)     // Catch:{ Throwable -> 0x0243 }
        L_0x0194:
            boolean r3 = android.text.TextUtils.isEmpty(r29)
            if (r3 != 0) goto L_0x01fa
            java.lang.String r3 = "\\|"
            r0 = r29
            java.lang.String[] r30 = r0.split(r3)
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "alipayDowngrade downgradeRulesArray[0] "
            java.lang.StringBuilder r5 = r5.append(r7)
            r7 = 0
            r7 = r30[r7]
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r7 = ", downgradeRulesArray[1]"
            java.lang.StringBuilder r5 = r5.append(r7)
            r7 = 1
            r7 = r30[r7]
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r3 = 1
            r3 = r30[r3]
            int r28 = java.lang.Integer.parseInt(r3)
            r3 = 0
            r3 = r30[r3]
            int r50 = java.lang.Integer.parseInt(r3)
            r3 = -1
            r0 = r50
            if (r0 != r3) goto L_0x0251
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "alipayDowngrade scope == -1"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r0 = r63
            java.lang.String r3 = r0.url
            r0 = r28
            com.alipay.mobile.nebulauc.impl.network.AlipaySpdyDowngrade.addMemoryDowngradeRule(r3, r0)
        L_0x01f1:
            r3 = 0
            java.lang.String r5 = "NO"
            r7 = 0
            r0 = r63
            r0.request(r3, r5, r7)
        L_0x01fa:
            long r8 = java.lang.System.currentTimeMillis()
            r0 = r63
            long r10 = r0.mRequestDuration
            long r8 = r8 - r10
            r0 = r63
            r0.mRequestDuration = r8
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "request duation = "
            java.lang.StringBuilder r5 = r5.append(r7)
            r0 = r63
            long r8 = r0.mRequestDuration
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r7 = " url= "
            java.lang.StringBuilder r5 = r5.append(r7)
            r0 = r63
            java.lang.String r7 = r0.url
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            goto L_0x00f5
        L_0x0235:
            r33 = move-exception
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "eventHandler.endData() exception : "
            r0 = r33
            com.alipay.mobile.nebula.util.H5Log.e(r3, r5, r0)
            goto L_0x018e
        L_0x0243:
            r57 = move-exception
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "handleResponse close inputStream exception."
            r0 = r57
            com.alipay.mobile.nebula.util.H5Log.e(r3, r5, r0)
            goto L_0x0194
        L_0x0251:
            if (r50 <= 0) goto L_0x01f1
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "alipayDowngrade scope > 0"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r0 = r63
            java.lang.String r3 = r0.url
            r0 = r50
            long r8 = (long) r0
            r0 = r28
            com.alipay.mobile.nebulauc.impl.network.AlipaySpdyDowngrade.addDiskDowngradeRule(r3, r8, r0)
            goto L_0x01f1
        L_0x0269:
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x046e }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x046e }
            r5.<init>()     // Catch:{ Throwable -> 0x046e }
            java.lang.String r7 = "handleResponse status: "
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            int r7 = r0.statusCode     // Catch:{ Throwable -> 0x046e }
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x046e }
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Throwable -> 0x046e }
            com.alipay.mobile.common.transport.http.HttpUrlHeader r3 = r64.getHeader()     // Catch:{ Throwable -> 0x046e }
            java.util.Map r14 = r3.toMultimap()     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            int r3 = r0.statusCode     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            java.lang.String r3 = r0.getProtocolFromHeader(r14, r3)     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            r0.protocol = r3     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            int r3 = r0.requestType     // Catch:{ Throwable -> 0x046e }
            boolean r3 = com.alipay.mobile.nebulauc.util.CommonUtil.isMainDoc(r3)     // Catch:{ Throwable -> 0x046e }
            if (r3 == 0) goto L_0x02b8
            r0 = r63
            com.alipay.mobile.h5container.api.H5PageData r3 = r0.pageData     // Catch:{ Throwable -> 0x046e }
            if (r3 == 0) goto L_0x02b8
            r0 = r63
            com.alipay.mobile.h5container.api.H5PageData r3 = r0.pageData     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            int r5 = r0.statusCode     // Catch:{ Throwable -> 0x046e }
            r3.setStatusCode(r5)     // Catch:{ Throwable -> 0x046e }
        L_0x02b8:
            boolean r3 = com.alipay.mobile.nebulauc.impl.setup.UcNetworkSetup.sUcRequestSettingEnabled     // Catch:{ Throwable -> 0x046e }
            if (r3 == 0) goto L_0x045d
            com.alibaba.fastjson.JSONArray r3 = com.alipay.mobile.nebulauc.impl.setup.UcNetworkSetup.sH2AppIdBlackList     // Catch:{ Throwable -> 0x046e }
            if (r3 == 0) goto L_0x02cc
            com.alibaba.fastjson.JSONArray r3 = com.alipay.mobile.nebulauc.impl.setup.UcNetworkSetup.sH2AppIdBlackList     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            java.lang.String r5 = r0.appId     // Catch:{ Throwable -> 0x046e }
            boolean r3 = r3.contains(r5)     // Catch:{ Throwable -> 0x046e }
            if (r3 != 0) goto L_0x045d
        L_0x02cc:
            r0 = r63
            java.lang.String r3 = r0.protocol     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = "h2"
            boolean r3 = r3.contains(r5)     // Catch:{ Throwable -> 0x046e }
            if (r3 != 0) goto L_0x02e4
            java.lang.String r3 = "spdy"
            r0 = r63
            java.lang.String r5 = r0.protocol     // Catch:{ Throwable -> 0x046e }
            boolean r3 = r3.equals(r5)     // Catch:{ Throwable -> 0x046e }
            if (r3 == 0) goto L_0x045d
        L_0x02e4:
            r0 = r63
            com.uc.webview.export.internal.interfaces.EventHandler r3 = r0.eventHandler     // Catch:{ Throwable -> 0x046e }
            r5 = 2
            r7 = 0
            r0 = r63
            int r8 = r0.statusCode     // Catch:{ Throwable -> 0x046e }
            java.lang.String r9 = ""
            r3.status(r5, r7, r8, r9)     // Catch:{ Throwable -> 0x046e }
        L_0x02f3:
            r34 = 0
            r26 = 0
            r41 = 0
            java.lang.String r6 = ""
            java.lang.String r12 = ""
            java.util.ArrayList r16 = new java.util.ArrayList     // Catch:{ Throwable -> 0x046e }
            r16.<init>()     // Catch:{ Throwable -> 0x046e }
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            long r10 = r0.costTime     // Catch:{ Throwable -> 0x046e }
            long r8 = r8 - r10
            r0 = r63
            r0.costTime = r8     // Catch:{ Throwable -> 0x046e }
            java.util.Set r3 = r14.keySet()     // Catch:{ Throwable -> 0x046e }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ Throwable -> 0x046e }
        L_0x0317:
            boolean r5 = r3.hasNext()     // Catch:{ Throwable -> 0x046e }
            if (r5 == 0) goto L_0x0587
            java.lang.Object r43 = r3.next()     // Catch:{ Throwable -> 0x046e }
            java.lang.String r43 = (java.lang.String) r43     // Catch:{ Throwable -> 0x046e }
            r0 = r43
            java.lang.Object r61 = r14.get(r0)     // Catch:{ Throwable -> 0x046e }
            java.util.List r61 = (java.util.List) r61     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = "Content-Encoding"
            r0 = r43
            boolean r27 = r5.equalsIgnoreCase(r0)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = "Transfer-Encoding"
            r0 = r43
            boolean r59 = r5.equalsIgnoreCase(r0)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = "x-alipay-downgrade"
            r0 = r43
            boolean r24 = r5.equalsIgnoreCase(r0)     // Catch:{ Throwable -> 0x046e }
            r46 = 0
            r0 = r63
            com.alibaba.fastjson.JSONArray r5 = r0.jsonArrayEncodeHeader     // Catch:{ Throwable -> 0x046e }
            if (r5 == 0) goto L_0x0377
            r0 = r63
            com.alibaba.fastjson.JSONArray r5 = r0.jsonArrayEncodeHeader     // Catch:{ Throwable -> 0x046e }
            boolean r5 = r5.isEmpty()     // Catch:{ Throwable -> 0x046e }
            if (r5 != 0) goto L_0x0377
            r37 = 0
        L_0x0357:
            r0 = r63
            com.alibaba.fastjson.JSONArray r5 = r0.jsonArrayEncodeHeader     // Catch:{ Throwable -> 0x046e }
            int r5 = r5.size()     // Catch:{ Throwable -> 0x046e }
            r0 = r37
            if (r0 >= r5) goto L_0x0377
            r0 = r63
            com.alibaba.fastjson.JSONArray r5 = r0.jsonArrayEncodeHeader     // Catch:{ Throwable -> 0x046e }
            r0 = r37
            java.lang.String r5 = r5.getString(r0)     // Catch:{ Throwable -> 0x046e }
            r0 = r43
            boolean r5 = r5.equalsIgnoreCase(r0)     // Catch:{ Throwable -> 0x046e }
            if (r5 == 0) goto L_0x0583
            r46 = 1
        L_0x0377:
            java.lang.String r5 = "content-type"
            r0 = r43
            boolean r5 = r5.equalsIgnoreCase(r0)     // Catch:{ Throwable -> 0x046e }
            if (r5 == 0) goto L_0x0388
            r0 = r16
            r1 = r61
            r0.addAll(r1)     // Catch:{ Throwable -> 0x046e }
        L_0x0388:
            java.lang.String r5 = "etag"
            r0 = r43
            boolean r5 = r5.equalsIgnoreCase(r0)     // Catch:{ Throwable -> 0x046e }
            if (r5 == 0) goto L_0x03a3
            if (r61 == 0) goto L_0x03a3
            int r5 = r61.size()     // Catch:{ Throwable -> 0x046e }
            if (r5 <= 0) goto L_0x03a3
            r5 = 0
            r0 = r61
            java.lang.Object r12 = r0.get(r5)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ Throwable -> 0x046e }
        L_0x03a3:
            java.util.Iterator r5 = r61.iterator()     // Catch:{ Throwable -> 0x046e }
        L_0x03a7:
            boolean r7 = r5.hasNext()     // Catch:{ Throwable -> 0x046e }
            if (r7 == 0) goto L_0x0317
            java.lang.Object r60 = r5.next()     // Catch:{ Throwable -> 0x046e }
            java.lang.String r60 = (java.lang.String) r60     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            java.lang.String r7 = r0.TAG     // Catch:{ Throwable -> 0x046e }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x046e }
            r8.<init>()     // Catch:{ Throwable -> 0x046e }
            java.lang.String r9 = "handleResponse headers "
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x046e }
            r0 = r43
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r9 = " "
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x046e }
            r0 = r60
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x046e }
            com.alipay.mobile.nebula.util.H5Log.d(r7, r8)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r7 = "content-type"
            r0 = r43
            boolean r7 = r0.equalsIgnoreCase(r7)     // Catch:{ Throwable -> 0x046e }
            if (r7 == 0) goto L_0x03f1
            java.lang.String r7 = "image"
            r0 = r60
            boolean r7 = r0.contains(r7)     // Catch:{ Throwable -> 0x046e }
            if (r7 == 0) goto L_0x03f1
            r41 = 1
        L_0x03f1:
            java.lang.String r7 = "content-length"
            r0 = r43
            boolean r7 = r0.equalsIgnoreCase(r7)     // Catch:{ Throwable -> 0x046e }
            if (r7 == 0) goto L_0x03fd
            r6 = r60
        L_0x03fd:
            if (r27 == 0) goto L_0x040b
            java.lang.String r7 = "gzip"
            r0 = r60
            boolean r7 = r7.equalsIgnoreCase(r0)     // Catch:{ Throwable -> 0x046e }
            if (r7 == 0) goto L_0x040b
            r34 = 1
        L_0x040b:
            if (r59 == 0) goto L_0x0419
            java.lang.String r7 = "chunked"
            r0 = r60
            boolean r7 = r7.equalsIgnoreCase(r0)     // Catch:{ Throwable -> 0x046e }
            if (r7 == 0) goto L_0x0419
            r26 = 1
        L_0x0419:
            if (r24 == 0) goto L_0x041d
            r29 = r60
        L_0x041d:
            if (r46 == 0) goto L_0x03a7
            r0 = r63
            java.lang.String r7 = r0.TAG     // Catch:{ Throwable -> 0x046e }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x046e }
            r8.<init>()     // Catch:{ Throwable -> 0x046e }
            java.lang.String r9 = "use ZURLEncodedUtil encode response header "
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x046e }
            r0 = r43
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x046e }
            com.alipay.mobile.nebula.util.H5Log.d(r7, r8)     // Catch:{ Throwable -> 0x046e }
            r38 = 0
        L_0x043d:
            int r7 = r61.size()     // Catch:{ Throwable -> 0x046e }
            r0 = r38
            if (r0 >= r7) goto L_0x03a7
            r0 = r61
            r1 = r38
            java.lang.Object r47 = r0.get(r1)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r47 = (java.lang.String) r47     // Catch:{ Throwable -> 0x046e }
            java.lang.String r7 = com.alipay.mobile.common.transport.utils.ZURLEncodedUtil.urlEncode(r47)     // Catch:{ Throwable -> 0x046e }
            r0 = r61
            r1 = r38
            r0.set(r1, r7)     // Catch:{ Throwable -> 0x046e }
            int r38 = r38 + 1
            goto L_0x043d
        L_0x045d:
            r0 = r63
            com.uc.webview.export.internal.interfaces.EventHandler r3 = r0.eventHandler     // Catch:{ Throwable -> 0x046e }
            r5 = 0
            r7 = 0
            r0 = r63
            int r8 = r0.statusCode     // Catch:{ Throwable -> 0x046e }
            java.lang.String r9 = ""
            r3.status(r5, r7, r8, r9)     // Catch:{ Throwable -> 0x046e }
            goto L_0x02f3
        L_0x046e:
            r55 = move-exception
        L_0x046f:
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ all -> 0x0795 }
            java.lang.String r5 = "handleResponse exception."
            r0 = r55
            com.alipay.mobile.nebula.util.H5Log.e(r3, r5, r0)     // Catch:{ all -> 0x0795 }
            r3 = 1
            r0 = r63
            r0.handleResponseGetError = r3     // Catch:{ all -> 0x0795 }
            r0 = r63
            java.lang.String r0 = r0.url     // Catch:{ Throwable -> 0x0df3 }
            r18 = r0
            r0 = r63
            java.lang.String r0 = r0.method     // Catch:{ Throwable -> 0x0df3 }
            r19 = r0
            r20 = 6
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0df3 }
            r3.<init>()     // Catch:{ Throwable -> 0x0df3 }
            java.lang.String r5 = "handleResponse exception "
            java.lang.StringBuilder r3 = r3.append(r5)     // Catch:{ Throwable -> 0x0df3 }
            java.lang.String r5 = r55.getMessage()     // Catch:{ Throwable -> 0x0df3 }
            java.lang.StringBuilder r3 = r3.append(r5)     // Catch:{ Throwable -> 0x0df3 }
            java.lang.String r21 = r3.toString()     // Catch:{ Throwable -> 0x0df3 }
            r0 = r63
            boolean r3 = r0.useSpdy     // Catch:{ Throwable -> 0x0df3 }
            if (r3 == 0) goto L_0x0de2
            java.lang.String r22 = "YES"
        L_0x04ac:
            java.lang.String r23 = "NO"
            r17 = r63
            r17.monitorLogger(r18, r19, r20, r21, r22, r23)     // Catch:{ Throwable -> 0x0df3 }
            boolean r3 = com.alipay.mobile.nebulauc.impl.network.AlipayNetwork.sEnableSendErrorToUcWhenException     // Catch:{ Throwable -> 0x0df3 }
            if (r3 == 0) goto L_0x0de6
            r0 = r63
            com.uc.webview.export.internal.interfaces.EventHandler r3 = r0.eventHandler     // Catch:{ Throwable -> 0x0df3 }
            r5 = -7
            java.lang.String r7 = "handleResponse exception"
            r3.error(r5, r7)     // Catch:{ Throwable -> 0x0df3 }
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x0df3 }
            java.lang.String r5 = "call eventHandler.error"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Throwable -> 0x0df3 }
        L_0x04ca:
            if (r51 == 0) goto L_0x04dc
            r0 = r63
            com.uc.webview.export.internal.interfaces.EventHandler r3 = r0.eventHandler     // Catch:{ Throwable -> 0x0e03 }
            r3.endData()     // Catch:{ Throwable -> 0x0e03 }
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x0e03 }
            java.lang.String r5 = "call endData"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Throwable -> 0x0e03 }
        L_0x04dc:
            r63.complete()
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r39)     // Catch:{ Throwable -> 0x0e11 }
        L_0x04e2:
            boolean r3 = android.text.TextUtils.isEmpty(r29)
            if (r3 != 0) goto L_0x0548
            java.lang.String r3 = "\\|"
            r0 = r29
            java.lang.String[] r30 = r0.split(r3)
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "alipayDowngrade downgradeRulesArray[0] "
            java.lang.StringBuilder r5 = r5.append(r7)
            r7 = 0
            r7 = r30[r7]
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r7 = ", downgradeRulesArray[1]"
            java.lang.StringBuilder r5 = r5.append(r7)
            r7 = 1
            r7 = r30[r7]
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r3 = 1
            r3 = r30[r3]
            int r28 = java.lang.Integer.parseInt(r3)
            r3 = 0
            r3 = r30[r3]
            int r50 = java.lang.Integer.parseInt(r3)
            r3 = -1
            r0 = r50
            if (r0 != r3) goto L_0x0e1f
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "alipayDowngrade scope == -1"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r0 = r63
            java.lang.String r3 = r0.url
            r0 = r28
            com.alipay.mobile.nebulauc.impl.network.AlipaySpdyDowngrade.addMemoryDowngradeRule(r3, r0)
        L_0x053f:
            r3 = 0
            java.lang.String r5 = "NO"
            r7 = 0
            r0 = r63
            r0.request(r3, r5, r7)
        L_0x0548:
            long r8 = java.lang.System.currentTimeMillis()
            r0 = r63
            long r10 = r0.mRequestDuration
            long r8 = r8 - r10
            r0 = r63
            r0.mRequestDuration = r8
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "request duation = "
            java.lang.StringBuilder r5 = r5.append(r7)
            r0 = r63
            long r8 = r0.mRequestDuration
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r7 = " url= "
            java.lang.StringBuilder r5 = r5.append(r7)
            r0 = r63
            java.lang.String r7 = r0.url
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            goto L_0x00f5
        L_0x0583:
            int r37 = r37 + 1
            goto L_0x0357
        L_0x0587:
            if (r41 == 0) goto L_0x060d
            r45 = 0
            java.lang.String r3 = "h5_imageMonitor"
            java.lang.String r36 = com.alipay.mobile.nebulauc.util.H5ConfigUtil.getConfig(r3)     // Catch:{ Throwable -> 0x046e }
            boolean r3 = android.text.TextUtils.isEmpty(r36)     // Catch:{ Throwable -> 0x046e }
            if (r3 != 0) goto L_0x05a3
            java.lang.String r3 = "on"
            r0 = r36
            boolean r3 = r3.equalsIgnoreCase(r0)     // Catch:{ Throwable -> 0x046e }
            if (r3 == 0) goto L_0x0775
            r45 = 1
        L_0x05a3:
            if (r45 == 0) goto L_0x060d
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x046e }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x046e }
            r5.<init>()     // Catch:{ Throwable -> 0x046e }
            java.lang.String r7 = "url="
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            java.lang.String r7 = r0.url     // Catch:{ Throwable -> 0x046e }
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r7 = "^status="
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            int r7 = r0.statusCode     // Catch:{ Throwable -> 0x046e }
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r7 = "^size="
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Throwable -> 0x046e }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r7 = "^costTime="
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            long r8 = r0.costTime     // Catch:{ Throwable -> 0x046e }
            java.lang.StringBuilder r5 = r5.append(r8)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x046e }
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Throwable -> 0x046e }
            com.alipay.mobile.h5container.api.H5PageImage r4 = new com.alipay.mobile.h5container.api.H5PageImage     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            java.lang.String r5 = r0.url     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            int r7 = r0.statusCode     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            long r8 = r0.costTime     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            java.lang.String r10 = r0.appId     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            java.lang.String r11 = r0.appVersion     // Catch:{ Throwable -> 0x046e }
            r4.<init>(r5, r6, r7, r8, r10, r11, r12)     // Catch:{ Throwable -> 0x046e }
            com.alipay.mobile.h5container.api.H5PageImageManager r3 = com.alipay.mobile.h5container.api.H5PageImageManager.getInstance()     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            java.lang.String r5 = r0.currentMainDocUrl     // Catch:{ Throwable -> 0x046e }
            r3.put(r5, r4)     // Catch:{ Throwable -> 0x046e }
        L_0x060d:
            r0 = r63
            int r3 = r0.requestType     // Catch:{ Throwable -> 0x046e }
            boolean r3 = com.alipay.mobile.nebulauc.util.CommonUtil.isMainDoc(r3)     // Catch:{ Throwable -> 0x046e }
            if (r3 == 0) goto L_0x069e
            java.lang.String r3 = "x-content-version"
            boolean r3 = r14.containsKey(r3)     // Catch:{ Exception -> 0x0789 }
            if (r3 == 0) goto L_0x0653
            java.lang.String r3 = "x-content-version"
            java.lang.Object r3 = r14.get(r3)     // Catch:{ Exception -> 0x0789 }
            java.util.List r3 = (java.util.List) r3     // Catch:{ Exception -> 0x0789 }
            r5 = 0
            java.lang.Object r62 = r3.get(r5)     // Catch:{ Exception -> 0x0789 }
            java.lang.String r62 = (java.lang.String) r62     // Catch:{ Exception -> 0x0789 }
            r0 = r63
            com.alipay.mobile.h5container.api.H5PageData r3 = r0.pageData     // Catch:{ Exception -> 0x0789 }
            r0 = r62
            r3.setXContentVersion(r0)     // Catch:{ Exception -> 0x0789 }
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Exception -> 0x0789 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0789 }
            r5.<init>()     // Catch:{ Exception -> 0x0789 }
            java.lang.String r7 = "x-content-version "
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Exception -> 0x0789 }
            r0 = r62
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Exception -> 0x0789 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0789 }
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Exception -> 0x0789 }
        L_0x0653:
            java.lang.String r3 = "Request-Id"
            boolean r3 = r14.containsKey(r3)     // Catch:{ Exception -> 0x0789 }
            if (r3 == 0) goto L_0x0673
            java.lang.String r3 = "Request-Id"
            java.lang.Object r3 = r14.get(r3)     // Catch:{ Exception -> 0x0789 }
            java.util.List r3 = (java.util.List) r3     // Catch:{ Exception -> 0x0789 }
            r5 = 0
            java.lang.Object r49 = r3.get(r5)     // Catch:{ Exception -> 0x0789 }
            java.lang.String r49 = (java.lang.String) r49     // Catch:{ Exception -> 0x0789 }
            r0 = r63
            com.alipay.mobile.h5container.api.H5PageData r3 = r0.pageData     // Catch:{ Exception -> 0x0789 }
            r0 = r49
            r3.setRequestId(r0)     // Catch:{ Exception -> 0x0789 }
        L_0x0673:
            java.lang.String r3 = "EagleId"
            boolean r3 = r14.containsKey(r3)     // Catch:{ Exception -> 0x0789 }
            if (r3 == 0) goto L_0x0693
            java.lang.String r3 = "EagleId"
            java.lang.Object r3 = r14.get(r3)     // Catch:{ Exception -> 0x0789 }
            java.util.List r3 = (java.util.List) r3     // Catch:{ Exception -> 0x0789 }
            r5 = 0
            java.lang.Object r32 = r3.get(r5)     // Catch:{ Exception -> 0x0789 }
            java.lang.String r32 = (java.lang.String) r32     // Catch:{ Exception -> 0x0789 }
            r0 = r63
            com.alipay.mobile.h5container.api.H5PageData r3 = r0.pageData     // Catch:{ Exception -> 0x0789 }
            r0 = r32
            r3.setEagleId(r0)     // Catch:{ Exception -> 0x0789 }
        L_0x0693:
            r0 = r63
            com.alipay.mobile.h5container.api.H5PageData r3 = r0.pageData     // Catch:{ Exception -> 0x0789 }
            r0 = r63
            boolean r5 = r0.useSpdy     // Catch:{ Exception -> 0x0789 }
            r3.setSpdy(r5)     // Catch:{ Exception -> 0x0789 }
        L_0x069e:
            r0 = r63
            com.alipay.mobile.nebula.webview.APWebViewClient r3 = r0.apWebViewClient     // Catch:{ Throwable -> 0x046e }
            if (r3 == 0) goto L_0x06ab
            r0 = r63
            com.alipay.mobile.nebula.webview.APWebViewClient r3 = r0.apWebViewClient     // Catch:{ Throwable -> 0x046e }
            r3.onReceivedResponseHeader(r14)     // Catch:{ Throwable -> 0x046e }
        L_0x06ab:
            boolean r3 = android.text.TextUtils.isEmpty(r29)     // Catch:{ Throwable -> 0x046e }
            if (r3 != 0) goto L_0x0883
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = "handleResponse alipayDowngrade return"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Throwable -> 0x046e }
            r51 = 0
            if (r51 == 0) goto L_0x06ce
            r0 = r63
            com.uc.webview.export.internal.interfaces.EventHandler r3 = r0.eventHandler     // Catch:{ Throwable -> 0x084e }
            r3.endData()     // Catch:{ Throwable -> 0x084e }
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x084e }
            java.lang.String r5 = "call endData"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Throwable -> 0x084e }
        L_0x06ce:
            r63.complete()
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r39)     // Catch:{ Throwable -> 0x085c }
        L_0x06d4:
            boolean r3 = android.text.TextUtils.isEmpty(r29)
            if (r3 != 0) goto L_0x073a
            java.lang.String r3 = "\\|"
            r0 = r29
            java.lang.String[] r30 = r0.split(r3)
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "alipayDowngrade downgradeRulesArray[0] "
            java.lang.StringBuilder r5 = r5.append(r7)
            r7 = 0
            r7 = r30[r7]
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r7 = ", downgradeRulesArray[1]"
            java.lang.StringBuilder r5 = r5.append(r7)
            r7 = 1
            r7 = r30[r7]
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r3 = 1
            r3 = r30[r3]
            int r28 = java.lang.Integer.parseInt(r3)
            r3 = 0
            r3 = r30[r3]
            int r50 = java.lang.Integer.parseInt(r3)
            r3 = -1
            r0 = r50
            if (r0 != r3) goto L_0x086a
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "alipayDowngrade scope == -1"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r0 = r63
            java.lang.String r3 = r0.url
            r0 = r28
            com.alipay.mobile.nebulauc.impl.network.AlipaySpdyDowngrade.addMemoryDowngradeRule(r3, r0)
        L_0x0731:
            r3 = 0
            java.lang.String r5 = "NO"
            r7 = 0
            r0 = r63
            r0.request(r3, r5, r7)
        L_0x073a:
            long r8 = java.lang.System.currentTimeMillis()
            r0 = r63
            long r10 = r0.mRequestDuration
            long r8 = r8 - r10
            r0 = r63
            r0.mRequestDuration = r8
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "request duation = "
            java.lang.StringBuilder r5 = r5.append(r7)
            r0 = r63
            long r8 = r0.mRequestDuration
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r7 = " url= "
            java.lang.StringBuilder r5 = r5.append(r7)
            r0 = r63
            java.lang.String r7 = r0.url
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            goto L_0x00f5
        L_0x0775:
            java.lang.String r3 = "onlyTinyApp"
            r0 = r36
            boolean r3 = r3.equalsIgnoreCase(r0)     // Catch:{ Throwable -> 0x046e }
            if (r3 == 0) goto L_0x05a3
            r0 = r63
            boolean r3 = r0.isTinyApp     // Catch:{ Throwable -> 0x046e }
            if (r3 == 0) goto L_0x05a3
            r45 = 1
            goto L_0x05a3
        L_0x0789:
            r31 = move-exception
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x046e }
            r0 = r31
            com.alipay.mobile.nebula.util.H5Log.e(r3, r0)     // Catch:{ Throwable -> 0x046e }
            goto L_0x069e
        L_0x0795:
            r3 = move-exception
        L_0x0796:
            if (r51 == 0) goto L_0x07a8
            r0 = r63
            com.uc.webview.export.internal.interfaces.EventHandler r5 = r0.eventHandler     // Catch:{ Throwable -> 0x0e38 }
            r5.endData()     // Catch:{ Throwable -> 0x0e38 }
            r0 = r63
            java.lang.String r5 = r0.TAG     // Catch:{ Throwable -> 0x0e38 }
            java.lang.String r7 = "call endData"
            com.alipay.mobile.nebula.util.H5Log.d(r5, r7)     // Catch:{ Throwable -> 0x0e38 }
        L_0x07a8:
            r63.complete()
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r39)     // Catch:{ Throwable -> 0x0e46 }
        L_0x07ae:
            boolean r5 = android.text.TextUtils.isEmpty(r29)
            if (r5 != 0) goto L_0x0814
            java.lang.String r5 = "\\|"
            r0 = r29
            java.lang.String[] r30 = r0.split(r5)
            r0 = r63
            java.lang.String r5 = r0.TAG
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "alipayDowngrade downgradeRulesArray[0] "
            java.lang.StringBuilder r7 = r7.append(r8)
            r8 = 0
            r8 = r30[r8]
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = ", downgradeRulesArray[1]"
            java.lang.StringBuilder r7 = r7.append(r8)
            r8 = 1
            r8 = r30[r8]
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r5, r7)
            r5 = 1
            r5 = r30[r5]
            int r28 = java.lang.Integer.parseInt(r5)
            r5 = 0
            r5 = r30[r5]
            int r50 = java.lang.Integer.parseInt(r5)
            r5 = -1
            r0 = r50
            if (r0 != r5) goto L_0x0e54
            r0 = r63
            java.lang.String r5 = r0.TAG
            java.lang.String r7 = "alipayDowngrade scope == -1"
            com.alipay.mobile.nebula.util.H5Log.d(r5, r7)
            r0 = r63
            java.lang.String r5 = r0.url
            r0 = r28
            com.alipay.mobile.nebulauc.impl.network.AlipaySpdyDowngrade.addMemoryDowngradeRule(r5, r0)
        L_0x080b:
            r5 = 0
            java.lang.String r7 = "NO"
            r8 = 0
            r0 = r63
            r0.request(r5, r7, r8)
        L_0x0814:
            long r8 = java.lang.System.currentTimeMillis()
            r0 = r63
            long r10 = r0.mRequestDuration
            long r8 = r8 - r10
            r0 = r63
            r0.mRequestDuration = r8
            r0 = r63
            java.lang.String r5 = r0.TAG
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "request duation = "
            java.lang.StringBuilder r7 = r7.append(r8)
            r0 = r63
            long r8 = r0.mRequestDuration
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = " url= "
            java.lang.StringBuilder r7 = r7.append(r8)
            r0 = r63
            java.lang.String r8 = r0.url
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r5, r7)
            throw r3
        L_0x084e:
            r33 = move-exception
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "eventHandler.endData() exception : "
            r0 = r33
            com.alipay.mobile.nebula.util.H5Log.e(r3, r5, r0)
            goto L_0x06ce
        L_0x085c:
            r57 = move-exception
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "handleResponse close inputStream exception."
            r0 = r57
            com.alipay.mobile.nebula.util.H5Log.e(r3, r5, r0)
            goto L_0x06d4
        L_0x086a:
            if (r50 <= 0) goto L_0x0731
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "alipayDowngrade scope > 0"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r0 = r63
            java.lang.String r3 = r0.url
            r0 = r50
            long r8 = (long) r0
            r0 = r28
            com.alipay.mobile.nebulauc.impl.network.AlipaySpdyDowngrade.addDiskDowngradeRule(r3, r8, r0)
            goto L_0x0731
        L_0x0883:
            r0 = r63
            com.uc.webview.export.internal.interfaces.EventHandler r3 = r0.eventHandler     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            int r0 = r0.statusCode     // Catch:{ Throwable -> 0x046e }
            r17 = r0
            r0 = r63
            com.alipay.mobile.h5container.api.H5Page r0 = r0.h5Page     // Catch:{ Throwable -> 0x046e }
            r18 = r0
            r13 = r63
            r15 = r6
            java.util.Map r5 = r13.handleResponseHeaders(r14, r15, r16, r17, r18)     // Catch:{ Throwable -> 0x046e }
            r3.headers(r5)     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x046e }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x046e }
            r5.<init>()     // Catch:{ Throwable -> 0x046e }
            java.lang.String r7 = "handleResponse gzip "
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Throwable -> 0x046e }
            r0 = r34
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x046e }
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x046e }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x046e }
            r5.<init>()     // Catch:{ Throwable -> 0x046e }
            java.lang.String r7 = "handleResponse chunked "
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Throwable -> 0x046e }
            r0 = r26
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x046e }
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            java.lang.String r3 = r0.protocol     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            r0.setProtocolToPageData(r3)     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            com.alipay.mobile.nebula.provider.H5DevDebugProvider r3 = r0.devDebugProvider     // Catch:{ Throwable -> 0x046e }
            if (r3 == 0) goto L_0x0960
            com.alipay.mobile.h5container.service.H5Service r35 = com.alipay.mobile.nebula.util.H5ServiceUtils.getH5Service()     // Catch:{ Throwable -> 0x046e }
            if (r35 == 0) goto L_0x0960
            com.alipay.mobile.nebula.dev.H5BugMeManager r3 = r35.getBugMeManager()     // Catch:{ Throwable -> 0x046e }
            if (r3 == 0) goto L_0x0960
            com.alipay.mobile.nebula.dev.H5BugMeManager r3 = r35.getBugMeManager()     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            java.lang.String r5 = r0.pageUrl     // Catch:{ Throwable -> 0x046e }
            boolean r3 = r3.hasAccessToDebug(r5)     // Catch:{ Throwable -> 0x046e }
            if (r3 == 0) goto L_0x0960
            com.alibaba.fastjson.JSONObject r42 = new com.alibaba.fastjson.JSONObject     // Catch:{ Throwable -> 0x046e }
            r3 = 5
            r0 = r42
            r0.<init>(r3)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r3 = "reqUrl"
            r0 = r63
            java.lang.String r5 = r0.url     // Catch:{ Throwable -> 0x046e }
            r0 = r42
            r0.put(r3, r5)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r3 = "reqId"
            r0 = r63
            int r5 = r0.bugmeReqId     // Catch:{ Throwable -> 0x046e }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ Throwable -> 0x046e }
            r0 = r42
            r0.put(r3, r5)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r3 = "method"
            r0 = r63
            java.lang.String r5 = r0.method     // Catch:{ Throwable -> 0x046e }
            r0 = r42
            r0.put(r3, r5)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r3 = "statusCode"
            r0 = r63
            int r5 = r0.statusCode     // Catch:{ Throwable -> 0x046e }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ Throwable -> 0x046e }
            r0 = r42
            r0.put(r3, r5)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r3 = "fromLocalPkg"
            r5 = 0
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ Throwable -> 0x046e }
            r0 = r42
            r0.put(r3, r5)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r3 = "protocol"
            r0 = r63
            java.lang.String r5 = r0.protocol     // Catch:{ Throwable -> 0x046e }
            r0 = r42
            r0.put(r3, r5)     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            com.alipay.mobile.nebula.provider.H5DevDebugProvider r3 = r0.devDebugProvider     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = "finish"
            r0 = r63
            java.lang.String r7 = r0.viewId     // Catch:{ Throwable -> 0x046e }
            r0 = r42
            r3.netWorkLog(r5, r7, r0)     // Catch:{ Throwable -> 0x046e }
        L_0x0960:
            java.io.InputStream r39 = r64.getInputStream()     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            int r3 = r0.statusCode     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            boolean r3 = r0.isRedirect(r3)     // Catch:{ Throwable -> 0x046e }
            if (r3 == 0) goto L_0x0a7d
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = "handleResponse isRedirect return"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            com.alipay.mobile.h5container.api.H5Page r3 = r0.h5Page4Landing     // Catch:{ Throwable -> 0x046e }
            if (r3 == 0) goto L_0x0990
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = "setContentBeforeRedirect false"
            com.alipay.mobile.nebula.util.H5Log.debug(r3, r5)     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            com.alipay.mobile.h5container.api.H5Page r3 = r0.h5Page4Landing     // Catch:{ Throwable -> 0x046e }
            r5 = 0
            r3.setContentBeforeRedirect(r5)     // Catch:{ Throwable -> 0x046e }
        L_0x0990:
            if (r51 == 0) goto L_0x09a2
            r0 = r63
            com.uc.webview.export.internal.interfaces.EventHandler r3 = r0.eventHandler     // Catch:{ Throwable -> 0x0a49 }
            r3.endData()     // Catch:{ Throwable -> 0x0a49 }
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x0a49 }
            java.lang.String r5 = "call endData"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Throwable -> 0x0a49 }
        L_0x09a2:
            r63.complete()
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r39)     // Catch:{ Throwable -> 0x0a57 }
        L_0x09a8:
            boolean r3 = android.text.TextUtils.isEmpty(r29)
            if (r3 != 0) goto L_0x0a0e
            java.lang.String r3 = "\\|"
            r0 = r29
            java.lang.String[] r30 = r0.split(r3)
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "alipayDowngrade downgradeRulesArray[0] "
            java.lang.StringBuilder r5 = r5.append(r7)
            r7 = 0
            r7 = r30[r7]
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r7 = ", downgradeRulesArray[1]"
            java.lang.StringBuilder r5 = r5.append(r7)
            r7 = 1
            r7 = r30[r7]
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r3 = 1
            r3 = r30[r3]
            int r28 = java.lang.Integer.parseInt(r3)
            r3 = 0
            r3 = r30[r3]
            int r50 = java.lang.Integer.parseInt(r3)
            r3 = -1
            r0 = r50
            if (r0 != r3) goto L_0x0a65
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "alipayDowngrade scope == -1"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r0 = r63
            java.lang.String r3 = r0.url
            r0 = r28
            com.alipay.mobile.nebulauc.impl.network.AlipaySpdyDowngrade.addMemoryDowngradeRule(r3, r0)
        L_0x0a05:
            r3 = 0
            java.lang.String r5 = "NO"
            r7 = 0
            r0 = r63
            r0.request(r3, r5, r7)
        L_0x0a0e:
            long r8 = java.lang.System.currentTimeMillis()
            r0 = r63
            long r10 = r0.mRequestDuration
            long r8 = r8 - r10
            r0 = r63
            r0.mRequestDuration = r8
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "request duation = "
            java.lang.StringBuilder r5 = r5.append(r7)
            r0 = r63
            long r8 = r0.mRequestDuration
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r7 = " url= "
            java.lang.StringBuilder r5 = r5.append(r7)
            r0 = r63
            java.lang.String r7 = r0.url
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            goto L_0x00f5
        L_0x0a49:
            r33 = move-exception
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "eventHandler.endData() exception : "
            r0 = r33
            com.alipay.mobile.nebula.util.H5Log.e(r3, r5, r0)
            goto L_0x09a2
        L_0x0a57:
            r57 = move-exception
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "handleResponse close inputStream exception."
            r0 = r57
            com.alipay.mobile.nebula.util.H5Log.e(r3, r5, r0)
            goto L_0x09a8
        L_0x0a65:
            if (r50 <= 0) goto L_0x0a05
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "alipayDowngrade scope > 0"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r0 = r63
            java.lang.String r3 = r0.url
            r0 = r50
            long r8 = (long) r0
            r0 = r28
            com.alipay.mobile.nebulauc.impl.network.AlipaySpdyDowngrade.addDiskDowngradeRule(r3, r8, r0)
            goto L_0x0a05
        L_0x0a7d:
            if (r39 != 0) goto L_0x0b8c
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = "handleResponse inputStream null return"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            com.alipay.mobile.h5container.api.H5Page r3 = r0.h5Page4Landing     // Catch:{ Throwable -> 0x046e }
            if (r3 == 0) goto L_0x0a9f
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = "setContentBeforeRedirect false"
            com.alipay.mobile.nebula.util.H5Log.debug(r3, r5)     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            com.alipay.mobile.h5container.api.H5Page r3 = r0.h5Page4Landing     // Catch:{ Throwable -> 0x046e }
            r5 = 0
            r3.setContentBeforeRedirect(r5)     // Catch:{ Throwable -> 0x046e }
        L_0x0a9f:
            if (r51 == 0) goto L_0x0ab1
            r0 = r63
            com.uc.webview.export.internal.interfaces.EventHandler r3 = r0.eventHandler     // Catch:{ Throwable -> 0x0b58 }
            r3.endData()     // Catch:{ Throwable -> 0x0b58 }
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x0b58 }
            java.lang.String r5 = "call endData"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Throwable -> 0x0b58 }
        L_0x0ab1:
            r63.complete()
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r39)     // Catch:{ Throwable -> 0x0b66 }
        L_0x0ab7:
            boolean r3 = android.text.TextUtils.isEmpty(r29)
            if (r3 != 0) goto L_0x0b1d
            java.lang.String r3 = "\\|"
            r0 = r29
            java.lang.String[] r30 = r0.split(r3)
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "alipayDowngrade downgradeRulesArray[0] "
            java.lang.StringBuilder r5 = r5.append(r7)
            r7 = 0
            r7 = r30[r7]
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r7 = ", downgradeRulesArray[1]"
            java.lang.StringBuilder r5 = r5.append(r7)
            r7 = 1
            r7 = r30[r7]
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r3 = 1
            r3 = r30[r3]
            int r28 = java.lang.Integer.parseInt(r3)
            r3 = 0
            r3 = r30[r3]
            int r50 = java.lang.Integer.parseInt(r3)
            r3 = -1
            r0 = r50
            if (r0 != r3) goto L_0x0b74
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "alipayDowngrade scope == -1"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r0 = r63
            java.lang.String r3 = r0.url
            r0 = r28
            com.alipay.mobile.nebulauc.impl.network.AlipaySpdyDowngrade.addMemoryDowngradeRule(r3, r0)
        L_0x0b14:
            r3 = 0
            java.lang.String r5 = "NO"
            r7 = 0
            r0 = r63
            r0.request(r3, r5, r7)
        L_0x0b1d:
            long r8 = java.lang.System.currentTimeMillis()
            r0 = r63
            long r10 = r0.mRequestDuration
            long r8 = r8 - r10
            r0 = r63
            r0.mRequestDuration = r8
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "request duation = "
            java.lang.StringBuilder r5 = r5.append(r7)
            r0 = r63
            long r8 = r0.mRequestDuration
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r7 = " url= "
            java.lang.StringBuilder r5 = r5.append(r7)
            r0 = r63
            java.lang.String r7 = r0.url
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            goto L_0x00f5
        L_0x0b58:
            r33 = move-exception
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "eventHandler.endData() exception : "
            r0 = r33
            com.alipay.mobile.nebula.util.H5Log.e(r3, r5, r0)
            goto L_0x0ab1
        L_0x0b66:
            r57 = move-exception
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "handleResponse close inputStream exception."
            r0 = r57
            com.alipay.mobile.nebula.util.H5Log.e(r3, r5, r0)
            goto L_0x0ab7
        L_0x0b74:
            if (r50 <= 0) goto L_0x0b14
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "alipayDowngrade scope > 0"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r0 = r63
            java.lang.String r3 = r0.url
            r0 = r50
            long r8 = (long) r0
            r0 = r28
            com.alipay.mobile.nebulauc.impl.network.AlipaySpdyDowngrade.addDiskDowngradeRule(r3, r8, r0)
            goto L_0x0b14
        L_0x0b8c:
            r0 = r63
            com.alipay.mobile.h5container.api.H5Page r3 = r0.h5Page4Landing     // Catch:{ Throwable -> 0x046e }
            if (r3 == 0) goto L_0x0ba3
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = "setContentBeforeRedirect true"
            com.alipay.mobile.nebula.util.H5Log.debug(r3, r5)     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            com.alipay.mobile.h5container.api.H5Page r3 = r0.h5Page4Landing     // Catch:{ Throwable -> 0x046e }
            r5 = 1
            r3.setContentBeforeRedirect(r5)     // Catch:{ Throwable -> 0x046e }
        L_0x0ba3:
            r58 = 0
            r3 = 4096(0x1000, float:5.74E-42)
            byte[] r0 = new byte[r3]     // Catch:{ Throwable -> 0x046e }
            r25 = r0
            r0 = r63
            boolean r3 = r0.mIsFallbackRequest     // Catch:{ Throwable -> 0x046e }
            if (r3 == 0) goto L_0x0bc1
            r0 = r63
            com.alipay.mobile.h5container.api.H5Page r3 = r0.h5Page     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            java.lang.String r5 = r0.mFallbackOriginUrl     // Catch:{ Throwable -> 0x046e }
            r0 = r39
            r1 = r34
            java.io.InputStream r39 = com.alipay.mobile.nebulauc.impl.network.FallbackRequestHelper.setFallbackCache(r0, r1, r3, r5)     // Catch:{ Throwable -> 0x046e }
        L_0x0bc1:
            r0 = r63
            r1 = r39
            java.io.InputStream r56 = r0.handlerInput(r1)     // Catch:{ Throwable -> 0x046e }
            if (r56 == 0) goto L_0x0e7f
            r39 = r56
            r40 = r39
        L_0x0bcf:
            boolean r3 = r63.isSupportNetworkSupervisor()     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            if (r3 == 0) goto L_0x0e7b
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            java.lang.String r5 = "h5netsupervisor response begin"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            long r52 = android.os.SystemClock.uptimeMillis()     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            com.alipay.mobile.nebula.networksupervisor.H5NetworkSuResponse r54 = new com.alipay.mobile.nebula.networksupervisor.H5NetworkSuResponse     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            r54.<init>()     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            r0 = r63
            java.lang.String r3 = r0.url     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            r0 = r54
            r0.setUrl(r3)     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            r0 = r63
            int r3 = r0.statusCode     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            r0 = r54
            r0.setStatus(r3)     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            r0 = r54
            r0.setHeaders(r14)     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            r0 = r63
            java.lang.String r3 = r0.method     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            r0 = r54
            r0.setMethod(r3)     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            if (r34 == 0) goto L_0x0d7a
            r0 = r63
            boolean r3 = r0.mIsFallbackRequest     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            if (r3 != 0) goto L_0x0d7a
            r3 = 1
        L_0x0c10:
            r0 = r54
            r0.setGzip(r3)     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            r0 = r54
            r1 = r26
            r0.setChunked(r1)     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            java.lang.String r3 = "publicid"
            r0 = r63
            java.lang.String r5 = r0.publicId     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            r0 = r54
            r0.putExtra(r3, r5)     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            java.lang.String r3 = "appid"
            r0 = r63
            java.lang.String r5 = r0.appId     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            r0 = r54
            r0.putExtra(r3, r5)     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            if (r40 == 0) goto L_0x0e77
            byte[] r48 = com.alipay.mobile.nebula.util.H5IOUtils.inputToByte(r40)     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r40)     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            java.io.ByteArrayInputStream r39 = new java.io.ByteArrayInputStream     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            r0 = r39
            r1 = r48
            r0.<init>(r1)     // Catch:{ Throwable -> 0x0e72, all -> 0x0e6d }
            r0 = r54
            r1 = r48
            r0.setBody(r1)     // Catch:{ Throwable -> 0x046e }
        L_0x0c4b:
            com.alipay.mobile.nebula.networksupervisor.H5NetworkSuScheduler r3 = com.alipay.mobile.nebula.networksupervisor.H5NetworkSuScheduler.getInstance()     // Catch:{ Throwable -> 0x046e }
            r0 = r54
            r3.post(r0)     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x046e }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x046e }
            r5.<init>()     // Catch:{ Throwable -> 0x046e }
            java.lang.String r7 = "h5netsupervisor response end cost "
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Throwable -> 0x046e }
            long r8 = android.os.SystemClock.uptimeMillis()     // Catch:{ Throwable -> 0x046e }
            long r8 = r8 - r52
            java.lang.StringBuilder r5 = r5.append(r8)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x046e }
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Throwable -> 0x046e }
        L_0x0c74:
            java.lang.String r3 = "eventHandler.data"
            r5 = 0
            r7 = 2
            java.lang.String[] r7 = new java.lang.String[r7]     // Catch:{ Throwable -> 0x046e }
            r8 = 0
            java.lang.String r9 = "size"
            r7[r8] = r9     // Catch:{ Throwable -> 0x046e }
            r8 = 1
            r7[r8] = r6     // Catch:{ Throwable -> 0x046e }
            com.alipay.mobile.nebula.data.H5Trace.sessionBegin(r3, r5, r7)     // Catch:{ Throwable -> 0x046e }
        L_0x0c85:
            r44 = -1
            r0 = r39
            r1 = r25
            int r44 = r0.read(r1)     // Catch:{ EOFException -> 0x0d7d }
        L_0x0c8f:
            r3 = -1
            r0 = r44
            if (r0 != r3) goto L_0x0d9e
            java.lang.String r3 = "eventHandler.data"
            r5 = 0
            r7 = 2
            java.lang.String[] r7 = new java.lang.String[r7]     // Catch:{ Throwable -> 0x046e }
            r8 = 0
            java.lang.String r9 = "size"
            r7[r8] = r9     // Catch:{ Throwable -> 0x046e }
            r8 = 1
            r7[r8] = r6     // Catch:{ Throwable -> 0x046e }
            com.alipay.mobile.nebula.data.H5Trace.sessionEnd(r3, r5, r7)     // Catch:{ Throwable -> 0x046e }
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x046e }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x046e }
            r5.<init>()     // Catch:{ Throwable -> 0x046e }
            java.lang.String r7 = "handleResponse end "
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Throwable -> 0x046e }
            r0 = r58
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x046e }
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Throwable -> 0x046e }
            if (r51 == 0) goto L_0x0cd3
            r0 = r63
            com.uc.webview.export.internal.interfaces.EventHandler r3 = r0.eventHandler     // Catch:{ Throwable -> 0x0dad }
            r3.endData()     // Catch:{ Throwable -> 0x0dad }
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x0dad }
            java.lang.String r5 = "call endData"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Throwable -> 0x0dad }
        L_0x0cd3:
            r63.complete()
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r39)     // Catch:{ Throwable -> 0x0dbb }
        L_0x0cd9:
            boolean r3 = android.text.TextUtils.isEmpty(r29)
            if (r3 != 0) goto L_0x0d3f
            java.lang.String r3 = "\\|"
            r0 = r29
            java.lang.String[] r30 = r0.split(r3)
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "alipayDowngrade downgradeRulesArray[0] "
            java.lang.StringBuilder r5 = r5.append(r7)
            r7 = 0
            r7 = r30[r7]
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r7 = ", downgradeRulesArray[1]"
            java.lang.StringBuilder r5 = r5.append(r7)
            r7 = 1
            r7 = r30[r7]
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r3 = 1
            r3 = r30[r3]
            int r28 = java.lang.Integer.parseInt(r3)
            r3 = 0
            r3 = r30[r3]
            int r50 = java.lang.Integer.parseInt(r3)
            r3 = -1
            r0 = r50
            if (r0 != r3) goto L_0x0dc9
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "alipayDowngrade scope == -1"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r0 = r63
            java.lang.String r3 = r0.url
            r0 = r28
            com.alipay.mobile.nebulauc.impl.network.AlipaySpdyDowngrade.addMemoryDowngradeRule(r3, r0)
        L_0x0d36:
            r3 = 0
            java.lang.String r5 = "NO"
            r7 = 0
            r0 = r63
            r0.request(r3, r5, r7)
        L_0x0d3f:
            long r8 = java.lang.System.currentTimeMillis()
            r0 = r63
            long r10 = r0.mRequestDuration
            long r8 = r8 - r10
            r0 = r63
            r0.mRequestDuration = r8
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "request duation = "
            java.lang.StringBuilder r5 = r5.append(r7)
            r0 = r63
            long r8 = r0.mRequestDuration
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r7 = " url= "
            java.lang.StringBuilder r5 = r5.append(r7)
            r0 = r63
            java.lang.String r7 = r0.url
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            goto L_0x00f5
        L_0x0d7a:
            r3 = 0
            goto L_0x0c10
        L_0x0d7d:
            r31 = move-exception
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x046e }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x046e }
            r5.<init>()     // Catch:{ Throwable -> 0x046e }
            java.lang.String r7 = "handleResponse eof "
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r7 = r31.toString()     // Catch:{ Throwable -> 0x046e }
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Throwable -> 0x046e }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x046e }
            com.alipay.mobile.nebula.util.H5Log.w(r3, r5)     // Catch:{ Throwable -> 0x046e }
            goto L_0x0c8f
        L_0x0d9e:
            r0 = r63
            com.uc.webview.export.internal.interfaces.EventHandler r3 = r0.eventHandler     // Catch:{ Throwable -> 0x046e }
            r0 = r25
            r1 = r44
            r3.data(r0, r1)     // Catch:{ Throwable -> 0x046e }
            int r58 = r58 + r44
            goto L_0x0c85
        L_0x0dad:
            r33 = move-exception
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "eventHandler.endData() exception : "
            r0 = r33
            com.alipay.mobile.nebula.util.H5Log.e(r3, r5, r0)
            goto L_0x0cd3
        L_0x0dbb:
            r57 = move-exception
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "handleResponse close inputStream exception."
            r0 = r57
            com.alipay.mobile.nebula.util.H5Log.e(r3, r5, r0)
            goto L_0x0cd9
        L_0x0dc9:
            if (r50 <= 0) goto L_0x0d36
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "alipayDowngrade scope > 0"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r0 = r63
            java.lang.String r3 = r0.url
            r0 = r50
            long r8 = (long) r0
            r0 = r28
            com.alipay.mobile.nebulauc.impl.network.AlipaySpdyDowngrade.addDiskDowngradeRule(r3, r8, r0)
            goto L_0x0d36
        L_0x0de2:
            java.lang.String r22 = "NO"
            goto L_0x04ac
        L_0x0de6:
            r51 = 0
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ Throwable -> 0x0df3 }
            java.lang.String r5 = "not call eventHandler.error"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)     // Catch:{ Throwable -> 0x0df3 }
            goto L_0x04ca
        L_0x0df3:
            r57 = move-exception
            r0 = r63
            java.lang.String r3 = r0.TAG     // Catch:{ all -> 0x0795 }
            java.lang.String r5 = "handleResponse exception eventHandler.error"
            r0 = r57
            com.alipay.mobile.nebula.util.H5Log.e(r3, r5, r0)     // Catch:{ all -> 0x0795 }
            r51 = 0
            goto L_0x04ca
        L_0x0e03:
            r33 = move-exception
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "eventHandler.endData() exception : "
            r0 = r33
            com.alipay.mobile.nebula.util.H5Log.e(r3, r5, r0)
            goto L_0x04dc
        L_0x0e11:
            r57 = move-exception
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "handleResponse close inputStream exception."
            r0 = r57
            com.alipay.mobile.nebula.util.H5Log.e(r3, r5, r0)
            goto L_0x04e2
        L_0x0e1f:
            if (r50 <= 0) goto L_0x053f
            r0 = r63
            java.lang.String r3 = r0.TAG
            java.lang.String r5 = "alipayDowngrade scope > 0"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            r0 = r63
            java.lang.String r3 = r0.url
            r0 = r50
            long r8 = (long) r0
            r0 = r28
            com.alipay.mobile.nebulauc.impl.network.AlipaySpdyDowngrade.addDiskDowngradeRule(r3, r8, r0)
            goto L_0x053f
        L_0x0e38:
            r33 = move-exception
            r0 = r63
            java.lang.String r5 = r0.TAG
            java.lang.String r7 = "eventHandler.endData() exception : "
            r0 = r33
            com.alipay.mobile.nebula.util.H5Log.e(r5, r7, r0)
            goto L_0x07a8
        L_0x0e46:
            r57 = move-exception
            r0 = r63
            java.lang.String r5 = r0.TAG
            java.lang.String r7 = "handleResponse close inputStream exception."
            r0 = r57
            com.alipay.mobile.nebula.util.H5Log.e(r5, r7, r0)
            goto L_0x07ae
        L_0x0e54:
            if (r50 <= 0) goto L_0x080b
            r0 = r63
            java.lang.String r5 = r0.TAG
            java.lang.String r7 = "alipayDowngrade scope > 0"
            com.alipay.mobile.nebula.util.H5Log.d(r5, r7)
            r0 = r63
            java.lang.String r5 = r0.url
            r0 = r50
            long r8 = (long) r0
            r0 = r28
            com.alipay.mobile.nebulauc.impl.network.AlipaySpdyDowngrade.addDiskDowngradeRule(r5, r8, r0)
            goto L_0x080b
        L_0x0e6d:
            r3 = move-exception
            r39 = r40
            goto L_0x0796
        L_0x0e72:
            r55 = move-exception
            r39 = r40
            goto L_0x046f
        L_0x0e77:
            r39 = r40
            goto L_0x0c4b
        L_0x0e7b:
            r39 = r40
            goto L_0x0c74
        L_0x0e7f:
            r40 = r39
            goto L_0x0bcf
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulauc.impl.network.AlipayRequest.handleResponse(com.alipay.mobile.common.transport.h5.H5HttpUrlResponse):void");
    }

    private Map<String, List<String>> handleResponseHeaders(Map<String, List<String>> originResHeaders, String size, List<String> contentType, int statusCode2, H5Page h5Page2) {
        if (this.mIsFallbackRequest) {
            H5Log.d(this.TAG, "set fallback request header fallbackUrl : " + this.mFallbackOriginUrl);
            return FallbackRequestHelper.getFallbackHeaders(this.pageUrl, this.mFallbackOriginUrl, size, contentType);
        }
        Map responseHeaders = new HashMap();
        responseHeaders.putAll(originResHeaders);
        if (h5Page2 == null && statusCode2 == 404) {
            JSONObject fallbackAsyncSwitch = H5ConfigUtil.getConfigJSONObject("h5_enableNetworkFallbackAsync");
            boolean enableAsyncFallback = H5Utils.getBoolean(fallbackAsyncSwitch, (String) "enable", false);
            boolean enableNoStoreFor404 = H5Utils.getBoolean(fallbackAsyncSwitch, (String) "enableNoStoreFor404", false);
            if (enableAsyncFallback && enableNoStoreFor404) {
                List cacheControl = new ArrayList();
                cacheControl.add("no-store");
                responseHeaders.put("Cache-Control", cacheControl);
                H5Log.d(this.TAG, "set no-store for 404 response: " + this.url);
            }
        }
        String cosUrl = H5ResourceCORSUtil.getCORSUrl(this.pageUrl);
        if (!H5Utils.enableCheckCrossOrigin() || !this.checkCrossOrigin || originResHeaders.containsKey("Access-Control-Allow-Credentials") || originResHeaders.containsKey("access-control-allow-credentials") || originResHeaders.containsKey("Access-Control-Allow-Origin") || originResHeaders.containsKey("access-control-allow-origin") || TextUtils.isEmpty(cosUrl)) {
            return responseHeaders;
        }
        H5Log.d(this.TAG, "set access-control-allow-origin header cosUrl : " + cosUrl);
        List acao = new ArrayList();
        acao.add(cosUrl);
        responseHeaders.put("Access-Control-Allow-Origin", acao);
        return responseHeaders;
    }

    private String getProtocolFromHeader(Map<String, List<String>> responseHeaders, int statusCode2) {
        List spdyHeader = responseHeaders.get("x-via-mobileproxy");
        List httpProtocol = responseHeaders.get("X-Android-Selected-Protocol");
        List spdyProxy = responseHeaders.get("x-spdy-proxy");
        if (spdyHeader != null && !spdyHeader.isEmpty() && "1".equals(spdyHeader.get(0))) {
            return ExtTransportStrategy.EXT_PROTO_SPDY;
        }
        if (httpProtocol != null && !httpProtocol.isEmpty()) {
            return (String) httpProtocol.get(0);
        }
        if (spdyProxy != null && !spdyProxy.isEmpty() && "1".equals(spdyProxy.get(0))) {
            return ExtTransportStrategy.EXT_PROTO_SPDY;
        }
        if (!this.mUrlConnectSwitch || VERSION.SDK_INT < 23 || statusCode2 != 304) {
            return "h1";
        }
        return "h2";
    }

    private void setProtocolToPageData(String protocol2) {
        if (this.pageData != null) {
            this.pageData.updateRequestCountByProtocal(protocol2);
            if (!TextUtils.equals("h2", this.pageData.getProtocol())) {
                if (protocol2.contains("h2")) {
                    this.pageData.setProtocol("h2");
                } else {
                    this.pageData.setProtocol("h1");
                }
            }
            this.pageData.setFunctionHasCallback(2);
            this.pageData.setLastResponseTimestamp(System.currentTimeMillis());
        }
    }

    private boolean isRedirect(int statusCode2) {
        switch (statusCode2) {
            case 301:
            case 302:
            case 303:
            case SecExceptionCode.SEC_ERROR_STA_ILLEGEL_KEY /*307*/:
                return true;
            default:
                return false;
        }
    }

    public boolean isCapture() {
        return this.capture;
    }

    public void setCapture(boolean capture2) {
        this.capture = capture2;
    }

    private void handleServiceWorker() {
        Uri uri = H5UrlHelper.parseUrl(this.url);
        if (uri != null) {
            try {
                Set queryKeys = uri.getQueryParameterNames();
                if (queryKeys != null && queryKeys.contains("from_service_worker") && queryKeys.contains("source") && H5PatternHelper.matchRegex("^.*[.]alipay-eco[.](com|net)$", uri.getHost())) {
                    H5Log.d(this.TAG, "handleServiceWorker bingo, change url to cdn host");
                    this.url = uri.getQueryParameter("source");
                }
            } catch (Throwable throwable) {
                H5Log.e(this.TAG, throwable);
            }
        }
    }

    private boolean interceptInValidDomain() {
        if (AlipayNetwork.sInterceptInvalidDomain && !TextUtils.isEmpty(H5AppUtil.matchAppId(this.url))) {
            H5Log.w(this.TAG, "Invalid domain : " + this.url);
            if (this.eventHandler != null) {
                this.eventHandler.error(9, "UnknownHostException");
                return true;
            }
        }
        return false;
    }

    private boolean handle478StatusCode(int statusCode2) {
        JSONObject jsonObj478 = H5ConfigUtil.getConfigJSONObject("h5_478Config");
        boolean enable478 = false;
        if (jsonObj478 != null) {
            enable478 = "YES".equals(jsonObj478.getString("enableConfig"));
        }
        H5Log.d(this.TAG, "enable478 " + enable478);
        if (!enable478 || statusCode2 != 478) {
            return false;
        }
        H5Log.d(this.TAG, "receive 478 statuscode");
        H5LogUtil.logNebulaTech(H5LogData.seedId("H5_RECEIVED_478").param3().add("url", this.url));
        H5478Provider h5478Provider = (H5478Provider) H5Utils.getProvider(H5478Provider.class.getName());
        if (h5478Provider != null) {
            h5478Provider.doLogin(new H5478Listener() {
                public void onLoginResult(boolean success, String sessionId) {
                    String sessionOri;
                    H5Log.d(AlipayRequest.this.TAG, "handle478StatusCode onLoginResult " + success + ", sessionId " + sessionId + ", success " + success);
                    if (AlipayRequest.this.useNew478Cookie) {
                        if (success) {
                            String cookie = CookieAccessHelper.getCookie(AlipayRequest.this.getUrl(), H5Utils.getContext());
                            if (!TextUtils.isEmpty(cookie)) {
                                AlipayRequest.this.headers.put("Cookie", cookie);
                            }
                        }
                    } else if (!TextUtils.isEmpty(sessionId)) {
                        Iterator it = AlipayRequest.this.headers.keySet().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            String key = (String) it.next();
                            if ("Cookie".equalsIgnoreCase(key)) {
                                String value = (String) AlipayRequest.this.headers.get(key);
                                if (!TextUtils.isEmpty(value)) {
                                    H5Log.d(AlipayRequest.this.TAG, "handle478StatusCode before cookie " + value);
                                    int start = value.indexOf("ALIPAYJSESSIONID=");
                                    if (start == -1) {
                                        value = value + "; ALIPAYJSESSIONID=" + sessionId;
                                    } else {
                                        String subValue = value.substring(start);
                                        int end = subValue.indexOf("; ");
                                        if (end == -1) {
                                            sessionOri = subValue.substring(17);
                                        } else {
                                            sessionOri = subValue.substring(17, end);
                                        }
                                        H5Log.d(AlipayRequest.this.TAG, "handle478StatusCode sessionOri " + sessionOri + ", sessionId " + sessionId);
                                        if (!TextUtils.equals(sessionId, sessionOri)) {
                                            StringBuilder builder = new StringBuilder(subValue);
                                            if (end == -1) {
                                                builder.replace(17, subValue.length(), sessionId);
                                            } else {
                                                builder.replace(17, end, sessionId);
                                            }
                                            value = value.substring(0, start) + builder.toString();
                                        }
                                    }
                                    H5Log.d(AlipayRequest.this.TAG, "handle478StatusCode after cookie " + value);
                                    AlipayRequest.this.headers.put(key, value);
                                } else {
                                    AlipayRequest.this.headers.put(key, "ALIPAYJSESSIONID=" + sessionId);
                                }
                            }
                        }
                    }
                    AlipayRequest.this.request(AlipayRequest.this.useSpdy, "NO", false);
                }
            });
        }
        return true;
    }

    private void handle478Header(boolean add478Header) {
        JSONObject jsonObj478 = H5ConfigUtil.getConfigJSONObject("h5_478Config");
        boolean enable478 = false;
        if (jsonObj478 != null) {
            enable478 = "YES".equals(jsonObj478.getString("enableConfig"));
        }
        H5Log.d(this.TAG, "enable478 " + enable478);
        if (enable478) {
            if (!add478Header) {
                H5Log.d(this.TAG, "remove request 478header");
                this.headers.remove("X-Alipay-Client-Session");
            } else if (H5ConfigUtil.isAlipayDomains(this.url)) {
                H5Log.d(this.TAG, "add request 478header");
                this.headers.put("X-Alipay-Client-Session", "check");
            }
        }
    }

    private boolean interceptSubResponse4Tiny(H5HttpUrlResponse httpUrlResponse) {
        if (!(this.appId == null || this.requestType == 0 || this.requestType == 1)) {
            H5ApiManager h5TinyAppService = (H5ApiManager) H5Utils.getProvider(H5ApiManager.class.getName());
            if (h5TinyAppService == null) {
                return false;
            }
            if (h5TinyAppService.hasPermissionFile(this.appId, this.h5Page)) {
                Uri uri = H5UrlHelper.parseUrl(this.url);
                if (uri != null) {
                    if (h5TinyAppService.hasPermission(this.appId, uri.getHost(), H5ApiManager.validDomain, this.h5Page)) {
                        return false;
                    }
                    if (H5ConfigUtil.isAliDomains(this.url)) {
                        return false;
                    }
                    Map responseHeaders = httpUrlResponse.getHeader().toMultimap();
                    List valueList = null;
                    Iterator<String> it = responseHeaders.keySet().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        String key = it.next();
                        if ("Content-Type".equalsIgnoreCase(key)) {
                            valueList = responseHeaders.get(key);
                            break;
                        }
                    }
                    String mimeType = null;
                    if (valueList != null && !valueList.isEmpty()) {
                        mimeType = (String) valueList.get(0);
                    }
                    if (TextUtils.isEmpty(mimeType)) {
                        return false;
                    }
                    boolean validMimeType = h5TinyAppService.hasPermission(this.appId, mimeType.toLowerCase(), H5ApiManager.Valid_SubResMimeList, this.h5Page);
                    H5Log.d(this.TAG, "mimeType " + mimeType + " validMimeType:" + validMimeType + Token.SEPARATOR + this.url);
                    if (validMimeType) {
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean subResUseHttpLinkInTinyApp() {
        H5ApiManager h5TinyAppService = (H5ApiManager) H5Utils.getProvider(H5ApiManager.class.getName());
        if (h5TinyAppService == null || !h5TinyAppService.hasPermissionFile(this.appId, this.h5Page)) {
            return false;
        }
        String extend = H5FileUtil.getFileExtensionFromUrl(this.url);
        H5Log.d(this.TAG, "getExtension " + extend + Token.SEPARATOR + this.url);
        if (TextUtils.isEmpty(extend)) {
            return false;
        }
        boolean result = h5TinyAppService.hasPermission(this.appId, extend.toLowerCase(), H5ApiManager.HttpLink_SubResMimeList, this.h5Page);
        H5Log.d(this.TAG, "getExtension " + extend + " useHttp " + result + Token.SEPARATOR + this.url);
        return result;
    }

    private void handleSocketTimeout(H5HttpUrlRequest h5HttpUrlRequest) {
        JSONObject configObj = H5ConfigUtil.getConfigJSONObject("h5_socketTimeoutConfig");
        if (configObj != null && !configObj.isEmpty() && "YES".equalsIgnoreCase(H5Utils.getString(configObj, (String) "enableConfig"))) {
            JSONArray hostList = H5Utils.getJSONArray(configObj, "hostList", null);
            if (hostList == null || hostList.isEmpty()) {
                setSocketTimeout(h5HttpUrlRequest, configObj);
                return;
            }
            Uri uri = H5UrlHelper.parseUrl(this.url);
            if (uri != null) {
                String host = uri.getHost();
                for (int i = 0; i < hostList.size(); i++) {
                    if (H5PatternHelper.matchRegex(hostList.getString(i), host)) {
                        setSocketTimeout(h5HttpUrlRequest, configObj);
                        return;
                    }
                }
            }
        }
    }

    private void setSocketTimeout(H5HttpUrlRequest h5HttpUrlRequest, JSONObject configObj) {
        int timeOut = -1;
        switch (NetworkUtils.getNetworkType(H5Utils.getContext())) {
            case 1:
                timeOut = H5Utils.getInt(configObj, (String) UtilityImpl.NET_TYPE_2G);
                break;
            case 2:
            case 4:
                timeOut = H5Utils.getInt(configObj, (String) "34g");
                break;
            case 3:
                timeOut = H5Utils.getInt(configObj, (String) "wifi");
                break;
        }
        if (timeOut != -1) {
            h5HttpUrlRequest.setTimeout((long) timeOut);
        }
    }

    private static boolean enableModifyEmbedWebViewParam() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_enableModifyEmbedWebViewParam"))) {
            return true;
        }
        return false;
    }
}
