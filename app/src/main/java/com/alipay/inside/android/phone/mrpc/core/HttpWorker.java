package com.alipay.inside.android.phone.mrpc.core;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.security.InsideRpcPack;
import com.alipay.inside.android.phone.mrpc.core.monitor.DataItemsUtil;
import com.alipay.inside.android.phone.mrpc.core.monitor.RPCDataContainer;
import com.alipay.inside.android.phone.mrpc.core.monitor.RPCDataParser;
import com.alipay.inside.android.phone.mrpc.core.utils.IOUtil;
import com.alipay.inside.android.phone.mrpc.core.utils.MiscUtils;
import com.alipay.inside.android.phone.mrpc.core.utils.NetworkUtils;
import com.alipay.inside.android.phone.mrpc.core.utils.SelfEncryptUtils;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class HttpWorker implements Callable<Response> {
    public static final String EXPIRES_PATTERN = "EEE, dd-MMM-yyyy HH:mm:ss z";
    private static final String TAG = "HttpWorker";
    private static final HttpRequestRetryHandler sHttpRequestRetryHandler = new ZHttpRequestRetryHandler();
    private InsideRpcPack clientRpcPack;
    private RPCDataContainer dataContainer = new RPCDataContainer();
    protected Context mContext;
    private CookieStore mCookieStore = new BasicCookieStore();
    private HttpHost mHttpHost;
    protected HttpManager mHttpManager;
    private HttpUriRequest mHttpRequest;
    private HttpContext mLocalContext = new BasicHttpContext();
    private String mOperationType;
    private AbstractHttpEntity mPostDataEntity;
    protected HttpUrlRequest mRequest;
    private int mRetryTimes = 0;
    private Response mRpcResponse;
    private URL mTargetUrl;
    String mUrl;

    /* access modifiers changed from: protected */
    public boolean willHandleOtherCode(int i, String str) {
        return i == 304;
    }

    public HttpWorker(HttpManager httpManager, HttpUrlRequest httpUrlRequest) {
        this.mHttpManager = httpManager;
        this.mContext = this.mHttpManager.mContext;
        this.mRequest = httpUrlRequest;
        this.clientRpcPack = new InsideRpcPack();
    }

    /* access modifiers changed from: protected */
    public URI getUri() throws URISyntaxException {
        String url = this.mRequest.getUrl();
        if (this.mUrl != null) {
            url = this.mUrl;
        }
        if (url != null) {
            return new URI(url);
        }
        throw new RuntimeException("url should not be null");
    }

    /* access modifiers changed from: protected */
    public AbstractHttpEntity getPostData() throws Exception {
        if (this.mPostDataEntity != null) {
            return this.mPostDataEntity;
        }
        byte[] reqData = this.mRequest.getReqData();
        if (reqData != null) {
            this.mPostDataEntity = SelfEncryptUtils.getEncryptedEntity(reqData, this.clientRpcPack, this.dataContainer);
            this.mPostDataEntity.setContentType(this.mRequest.getContentType());
        }
        return this.mPostDataEntity;
    }

    /* access modifiers changed from: protected */
    public ArrayList<Header> getHeaders() {
        return this.mRequest.getHeaders();
    }

    private HttpUriRequest getHttpUriRequest() throws Exception {
        if (this.mHttpRequest != null) {
            return this.mHttpRequest;
        }
        AbstractHttpEntity postData = getPostData();
        if (postData != null) {
            HttpPost httpPost = new HttpPost(getUri());
            httpPost.setEntity(postData);
            this.mHttpRequest = httpPost;
        } else {
            this.mHttpRequest = new HttpGet(getUri());
        }
        return this.mHttpRequest;
    }

    public Response call() throws HttpException {
        try {
            this.dataContainer.putDataItem("API", getOperationType());
            if (getTransportCallback() != null) {
                getTransportCallback().onPreExecute(this.mRequest);
            }
            addRequestHeaders();
            this.mLocalContext.setAttribute("http.cookie-store", this.mCookieStore);
            getHttpClient().setHttpRequestRetryHandler(sHttpRequestRetryHandler);
            long currentTimeMillis = System.currentTimeMillis();
            HttpResponse executeRequest = executeRequest();
            this.mHttpManager.addConnectTime(System.currentTimeMillis() - currentTimeMillis);
            processRespCookies(executeRequest);
            this.mRpcResponse = processResponse(executeRequest, this.mRequest);
            trafficMonitor(this.mRpcResponse);
            Response response = this.mRpcResponse;
            finallyProcess();
            return response;
        } catch (HttpException e) {
            Response processException = processException("HttpException", e.getCode(), e, false);
            finallyProcess();
            return processException;
        } catch (URISyntaxException e2) {
            try {
                getTransportCallback().onFailed(this.mRequest, 10, e2.toString());
            } catch (Exception e3) {
                TraceLogger f = LoggerFactory.f();
                StringBuilder sb = new StringBuilder("getTransportCallback().onFailed1 exception : ");
                sb.append(e3.toString());
                f.c((String) "HttpWorker", sb.toString());
            }
            RPCDataContainer rPCDataContainer = this.dataContainer;
            StringBuilder sb2 = new StringBuilder("URISyntaxException:");
            sb2.append(e2.getMessage());
            rPCDataContainer.putDataItem("ERROR", sb2.toString());
            throw new RuntimeException("Url parser error!", e2.getCause());
        } catch (SSLHandshakeException e4) {
            Response processException2 = processException("SSLHandshakeException", 2, e4, false);
            finallyProcess();
            return processException2;
        } catch (SSLPeerUnverifiedException e5) {
            Response processException3 = processException("SSLPeerUnverifiedException", 2, e5, false);
            finallyProcess();
            return processException3;
        } catch (SSLException e6) {
            Response processException4 = processException("SSLException", 2, e6, false);
            finallyProcess();
            return processException4;
        } catch (GeneralSecurityException e7) {
            Response processException5 = processException(e7.getClass().getSimpleName(), 2, e7, false);
            finallyProcess();
            return processException5;
        } catch (ConnectionPoolTimeoutException e8) {
            Response processException6 = processException("ConnectionPoolTimeoutException", 3, e8, false);
            finallyProcess();
            return processException6;
        } catch (ConnectTimeoutException e9) {
            Response processException7 = processException("ConnectTimeoutException", 3, e9, false);
            finallyProcess();
            return processException7;
        } catch (SocketTimeoutException e10) {
            Response processException8 = processException("SocketTimeoutException", 4, e10, false);
            finallyProcess();
            return processException8;
        } catch (NoHttpResponseException e11) {
            Response processException9 = processException("NoHttpResponseException", 5, e11, false);
            finallyProcess();
            return processException9;
        } catch (HttpHostConnectException e12) {
            Response processException10 = processException("HttpHostConnectException", 8, e12, false);
            finallyProcess();
            return processException10;
        } catch (ClientProtocolException e13) {
            Response processException11 = processException("ClientProtocolException", 0, e13, true);
            finallyProcess();
            return processException11;
        } catch (UnknownHostException e14) {
            Response processException12 = processException("UnknownHostException", 9, e14, false);
            finallyProcess();
            return processException12;
        } catch (IOException e15) {
            Response processException13 = processException("IOException", 6, e15, false);
            finallyProcess();
            return processException13;
        } catch (NullPointerException e16) {
            Response processException14 = processException("NullPointerException", 0, e16, true);
            finallyProcess();
            return processException14;
        } catch (Throwable th) {
            finallyProcess();
            throw th;
        }
    }

    private void finallyProcess() {
        try {
            logResponseInfo(this.mRpcResponse);
            putCommonMonitorDataItems();
            RPCDataParser.buildAndWriteLog(this.dataContainer);
        } catch (Throwable th) {
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("finallyProcess ex:");
            sb.append(th.toString());
            f.d("HttpWorker", sb.toString());
        }
    }

    private void logResponseInfo(Response response) {
        if (response != null && (response instanceof HttpUrlResponse)) {
            HttpUrlHeader header = ((HttpUrlResponse) response).getHeader();
            TraceLogger f = LoggerFactory.f();
            Object[] objArr = new Object[4];
            objArr[0] = Long.valueOf(Thread.currentThread().getId());
            objArr[1] = this.mOperationType;
            objArr[2] = header != null ? header.toString() : "is null";
            objArr[3] = "";
            f.b((String) "HttpWorker", String.format("threadid = %s; Response success. mOperationType=[%s] . response header=[%s].  response body = %s  ", objArr));
        }
    }

    /* access modifiers changed from: protected */
    public Response processException(String str, int i, Throwable th, boolean z) throws HttpException {
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("proceExce,code=[");
        sb.append(i);
        sb.append("] canRetry=[");
        sb.append(z);
        sb.append("] e=[");
        sb.append(th.toString());
        sb.append("]");
        f.b("HttpWorker", sb.toString(), th);
        RPCDataContainer rPCDataContainer = this.dataContainer;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(":");
        sb2.append(th.getMessage());
        DataItemsUtil.putDataItem2Container(rPCDataContainer, "ERROR", sb2.toString());
        if (!z || this.mRetryTimes > 0) {
            abortRequest();
            if (getTransportCallback() != null) {
                getTransportCallback().onFailed(this.mRequest, i, th.toString());
            }
            throw new HttpException(Integer.valueOf(i), th.toString());
        }
        DataItemsUtil.removeFromContainer(this.dataContainer, "ERROR");
        DataItemsUtil.putDataItem2Container(this.dataContainer, "RETRY", "T");
        TraceLogger f2 = LoggerFactory.f();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append(", retry");
        f2.b((String) "HttpWorker", sb3.toString());
        this.mRetryTimes++;
        return call();
    }

    private void putCommonMonitorDataItems() {
        try {
            DataItemsUtil.putDataItem2Container(this.dataContainer, "NETTYPE", String.valueOf(NetworkUtils.getNetworkType(this.mContext)));
            DataItemsUtil.putDataItem2Container(this.dataContainer, "API", getOperationType());
            DataItemsUtil.putDataItem2Container(this.dataContainer, "UUID", this.mRequest.getTag("UUID"));
        } catch (Throwable th) {
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("monitorLog ex:");
            sb.append(th.toString());
            f.d("HttpWorker", sb.toString());
        }
    }

    private void trafficMonitor(Response response) {
        if (((response == null || response.getResData() == null) ? -1 : (long) response.getResData().length) == -1 && (response instanceof HttpUrlResponse)) {
            try {
                Long.parseLong(((HttpUrlResponse) response).getHeader().getHead("Content-Length"));
            } catch (Exception unused) {
                LoggerFactory.f().d("HttpWorker", "parse Content-Length error");
            }
        }
        String url = this.mRequest.getUrl();
        if (url != null && !TextUtils.isEmpty(getOperationType())) {
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            sb.append(MetaRecord.LOG_SEPARATOR);
            sb.append(getOperationType());
        }
    }

    /* access modifiers changed from: protected */
    public void processRespCookies(HttpResponse httpResponse) throws URISyntaxException {
        List<Cookie> cookies = this.mCookieStore.getCookies();
        if (MiscUtils.isDebugger(this.mContext) && (cookies == null || cookies.isEmpty())) {
            Header[] headers = httpResponse.getHeaders("Set-cookie");
            if (headers != null) {
                for (Header value : headers) {
                    String value2 = value.getValue();
                    if (!TextUtils.isEmpty(value2)) {
                        try {
                            List<HttpCookie> parse = HttpCookie.parse(value2);
                            if (parse != null) {
                                for (HttpCookie next : parse) {
                                    BasicClientCookie basicClientCookie = new BasicClientCookie(next.getName(), next.getValue());
                                    basicClientCookie.setDomain(next.getDomain());
                                    basicClientCookie.setPath(next.getPath());
                                    if (next.getMaxAge() > 0) {
                                        basicClientCookie.setExpiryDate(new Date(System.currentTimeMillis() + next.getMaxAge()));
                                    }
                                    this.mCookieStore.addCookie(basicClientCookie);
                                }
                            }
                        } catch (Throwable th) {
                            TraceLogger f = LoggerFactory.f();
                            StringBuilder sb = new StringBuilder("Set-cookie only debug: ");
                            sb.append(th.toString());
                            f.d("HttpWorker", sb.toString());
                        }
                    }
                }
            }
        }
        handleResetCookie(cookies);
        if (!cookies.isEmpty()) {
            for (Cookie cookie : cookies) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(cookie.getName());
                sb2.append("=");
                sb2.append(cookie.getValue());
                if (!TextUtils.isEmpty(cookie.getDomain())) {
                    sb2.append("; domain=");
                    sb2.append(cookie.getDomain());
                }
                if (!TextUtils.isEmpty(cookie.getPath())) {
                    sb2.append("; path=");
                    sb2.append(cookie.getPath());
                }
                if (cookie.getExpiryDate() != null) {
                    try {
                        String formatDate = DateUtils.formatDate(cookie.getExpiryDate(), "EEE, dd-MMM-yyyy HH:mm:ss z");
                        sb2.append("; expires=");
                        sb2.append(formatDate);
                    } catch (Throwable th2) {
                        TraceLogger f2 = LoggerFactory.f();
                        StringBuilder sb3 = new StringBuilder("expires format exception. ");
                        sb3.append(th2.toString());
                        f2.d("HttpWorker", sb3.toString());
                    }
                }
                if (cookie.isSecure()) {
                    sb2.append("; Secure");
                }
                String sb4 = sb2.toString();
                String url = this.mRequest.getUrl();
                String domain = cookie.getDomain();
                if (TextUtils.isEmpty(domain)) {
                    domain = url;
                } else if (!domain.startsWith(".")) {
                    domain = ".".concat(String.valueOf(domain));
                }
                TraceLogger f3 = LoggerFactory.f();
                StringBuilder sb5 = new StringBuilder("set cookie. domain=");
                sb5.append(domain);
                sb5.append("  cookie=");
                sb5.append(sb4);
                sb5.append(" .url=");
                sb5.append(url);
                f3.a((String) "HttpWorker", sb5.toString());
                CookieAccessHelper.setCookie(domain, sb4, this.mContext);
            }
        }
    }

    private void handleResetCookie(List<Cookie> list) {
        if (this.mRequest.isResetCookie()) {
            LoggerFactory.f().a((String) "HttpWorker", (String) "== reset cookie ==");
            specialProcessForALIPAYJSESSIONID(list);
            return;
        }
        MiscUtils.isDebugger(this.mContext);
    }

    private void specialProcessForALIPAYJSESSIONID(List<Cookie> list) {
        for (Cookie name : list) {
            String name2 = name.getName();
            if (!TextUtils.isEmpty(name2) && name2.equalsIgnoreCase("ALIPAYJSESSIONID")) {
                try {
                    resetCookie();
                    return;
                } catch (Throwable th) {
                    LoggerFactory.f().b("HttpWorker", "reset cookie fail!", th);
                }
            }
        }
    }

    private void resetCookie() {
        if (getClass().getSimpleName().equals(HttpWorker.class.getSimpleName()) && !TextUtils.isEmpty(getOperationType())) {
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("reset cookie.  API=");
            sb.append(getOperationType());
            f.a((String) "HttpWorker", sb.toString());
            CookieAccessHelper.removeAllCookie(this.mContext);
            long currentTimeMillis = System.currentTimeMillis();
            while (CookieAccessHelper.getCookieManager().hasCookies() && System.currentTimeMillis() - currentTimeMillis < 1000) {
                Thread.yield();
            }
            if (!CookieAccessHelper.getCookieManager().hasCookies()) {
                LoggerFactory.f().a((String) "HttpWorker", (String) "reset cookie success!");
            } else {
                LoggerFactory.f().a((String) "HttpWorker", (String) "reset cookie fail!");
            }
        }
    }

    private void abortRequest() {
        if (this.mHttpRequest != null) {
            this.mHttpRequest.abort();
        }
    }

    private TransportCallback getTransportCallback() {
        return this.mRequest.getCallback();
    }

    private HttpResponse executeRequest() throws Exception {
        HttpResponse executeHttpClientRequest = executeHttpClientRequest();
        if (executeHttpClientRequest != null) {
            DataItemsUtil.putDataItem2Container(this.dataContainer, "HRC", String.valueOf(executeHttpClientRequest.getStatusLine().getStatusCode()));
        }
        return executeHttpClientRequest;
    }

    private HttpResponse executeHttpClientRequest() throws IOException {
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("By Http/Https to request. operationType=");
        sb.append(getOperationType());
        sb.append(" url=");
        sb.append(this.mHttpRequest.getURI().toString());
        f.a((String) "HttpWorker", sb.toString());
        this.dataContainer.timeItemDot("RPC_ALL_TIME");
        getHttpClient().getParams().setParameter("http.route.default-proxy", getProxy());
        HttpHost httpHost = getHttpHost();
        if (getTargetPort() == 80) {
            httpHost = new HttpHost(getTargetURL().getHost());
        }
        return getHttpClient().execute(httpHost, (HttpRequest) this.mHttpRequest, this.mLocalContext);
    }

    private String getOperationType() {
        if (!TextUtils.isEmpty(this.mOperationType)) {
            return this.mOperationType;
        }
        this.mOperationType = this.mRequest.getTag(TransportConstants.KEY_OPERATION_TYPE);
        return this.mOperationType;
    }

    private AndroidHttpClient getHttpClient() {
        return this.mHttpManager.getHttpClient();
    }

    private void addRequestHeaders() throws Exception {
        ArrayList<Header> headers = getHeaders();
        if (headers != null && !headers.isEmpty()) {
            Iterator<Header> it = headers.iterator();
            while (it.hasNext()) {
                getHttpUriRequest().addHeader(it.next());
            }
        }
        AndroidHttpClient.modifyRequestToKeepAlive(getHttpUriRequest());
        addCookie2Header();
    }

    /* access modifiers changed from: protected */
    public void addCookie2Header() throws Exception {
        String cookie = CookieAccessHelper.getCookie(this.mRequest.getUrl(), this.mContext);
        if (!TextUtils.isEmpty(cookie)) {
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("add cookie=[");
            sb.append(cookie);
            sb.append("]. url=");
            sb.append(this.mRequest.getUrl());
            f.a((String) "HttpWorker", sb.toString());
            getHttpUriRequest().addHeader("Cookie", cookie);
        }
    }

    private HttpHost getHttpHost() throws MalformedURLException {
        if (this.mHttpHost != null) {
            return this.mHttpHost;
        }
        URL targetURL = getTargetURL();
        this.mHttpHost = new HttpHost(targetURL.getHost(), getTargetPort(), targetURL.getProtocol());
        return this.mHttpHost;
    }

    private int getTargetPort() throws MalformedURLException {
        URL targetURL = getTargetURL();
        if (targetURL.getPort() == -1) {
            return targetURL.getDefaultPort();
        }
        return targetURL.getPort();
    }

    private URL getTargetURL() throws MalformedURLException {
        if (this.mTargetUrl != null) {
            return this.mTargetUrl;
        }
        this.mTargetUrl = new URL(this.mRequest.getUrl());
        return this.mTargetUrl;
    }

    private HttpHost getProxy() {
        return NetworkUtils.getProxy(this.mContext);
    }

    /* access modifiers changed from: protected */
    public HashMap<String, String> getContentType(String str) {
        HashMap<String, String> hashMap = new HashMap<>();
        String[] split = str.split(";");
        int length = split.length;
        for (int i = 0; i < length; i++) {
            String str2 = split[i];
            String[] split2 = str2.indexOf(61) == -1 ? new String[]{"Content-Type", str2} : str2.split("=");
            hashMap.put(split2[0], split2[1]);
        }
        return hashMap;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x00c5 A[SYNTHETIC, Splitter:B:19:0x00c5] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alipay.inside.android.phone.mrpc.core.Response handleResponse(org.apache.http.HttpResponse r9, int r10, java.lang.String r11) throws java.lang.Exception {
        /*
            r8 = this;
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r0 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r1 = "HttpWorker"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "开始handle，handleResponse-1,"
            r2.<init>(r3)
            java.lang.Thread r3 = java.lang.Thread.currentThread()
            long r3 = r3.getId()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r0.a(r1, r2)
            org.apache.http.HttpEntity r0 = r9.getEntity()
            r1 = 0
            if (r0 == 0) goto L_0x00e2
            org.apache.http.StatusLine r2 = r9.getStatusLine()
            int r2 = r2.getStatusCode()
            r3 = 200(0xc8, float:2.8E-43)
            if (r2 != r3) goto L_0x00e2
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r2 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r3 = "HttpWorker"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "200，开始处理，handleResponse-2,threadid = "
            r4.<init>(r5)
            java.lang.Thread r5 = java.lang.Thread.currentThread()
            long r5 = r5.getId()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r2.a(r3, r4)
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x00c1 }
            r2.<init>()     // Catch:{ all -> 0x00c1 }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00bf }
            r5 = 0
            r8.writeData(r0, r5, r2)     // Catch:{ all -> 0x00bf }
            byte[] r0 = r2.toByteArray()     // Catch:{ all -> 0x00bf }
            com.alipay.android.phone.inside.security.InsideRpcPack r1 = r8.clientRpcPack     // Catch:{ all -> 0x00bf }
            com.alipay.inside.android.phone.mrpc.core.monitor.RPCDataContainer r5 = r8.dataContainer     // Catch:{ all -> 0x00bf }
            byte[] r0 = com.alipay.inside.android.phone.mrpc.core.utils.SelfEncryptUtils.getDecryptedContent(r0, r1, r5)     // Catch:{ all -> 0x00bf }
            com.alipay.inside.android.phone.mrpc.core.HttpManager r1 = r8.mHttpManager     // Catch:{ all -> 0x00bf }
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00bf }
            r7 = 0
            long r5 = r5 - r3
            r1.addSocketTime(r5)     // Catch:{ all -> 0x00bf }
            com.alipay.inside.android.phone.mrpc.core.HttpManager r1 = r8.mHttpManager     // Catch:{ all -> 0x00bf }
            int r3 = r0.length     // Catch:{ all -> 0x00bf }
            long r3 = (long) r3     // Catch:{ all -> 0x00bf }
            r1.addDataSize(r3)     // Catch:{ all -> 0x00bf }
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x00bf }
            java.lang.String r3 = "HttpWorker"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00bf }
            java.lang.String r5 = "res:"
            r4.<init>(r5)     // Catch:{ all -> 0x00bf }
            int r5 = r0.length     // Catch:{ all -> 0x00bf }
            r4.append(r5)     // Catch:{ all -> 0x00bf }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x00bf }
            r1.a(r3, r4)     // Catch:{ all -> 0x00bf }
            com.alipay.inside.android.phone.mrpc.core.HttpUrlResponse r1 = new com.alipay.inside.android.phone.mrpc.core.HttpUrlResponse     // Catch:{ all -> 0x00bf }
            com.alipay.inside.android.phone.mrpc.core.HttpUrlHeader r3 = r8.handleResponseHeader(r9)     // Catch:{ all -> 0x00bf }
            r1.<init>(r3, r10, r11, r0)     // Catch:{ all -> 0x00bf }
            r8.fillResponse(r1, r9)     // Catch:{ all -> 0x00bf }
            r2.close()     // Catch:{ IOException -> 0x00b2 }
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r9 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r10 = "HttpWorker"
            java.lang.String r11 = "finally,handleResponse"
            r9.a(r10, r11)
            goto L_0x00eb
        L_0x00b2:
            r9 = move-exception
            java.lang.RuntimeException r10 = new java.lang.RuntimeException
            java.lang.String r11 = "ArrayOutputStream close error!"
            java.lang.Throwable r9 = r9.getCause()
            r10.<init>(r11, r9)
            throw r10
        L_0x00bf:
            r9 = move-exception
            goto L_0x00c3
        L_0x00c1:
            r9 = move-exception
            r2 = r1
        L_0x00c3:
            if (r2 == 0) goto L_0x00d6
            r2.close()     // Catch:{ IOException -> 0x00c9 }
            goto L_0x00d6
        L_0x00c9:
            r9 = move-exception
            java.lang.RuntimeException r10 = new java.lang.RuntimeException
            java.lang.String r11 = "ArrayOutputStream close error!"
            java.lang.Throwable r9 = r9.getCause()
            r10.<init>(r11, r9)
            throw r10
        L_0x00d6:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r10 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r11 = "HttpWorker"
            java.lang.String r0 = "finally,handleResponse"
            r10.a(r11, r0)
            throw r9
        L_0x00e2:
            if (r0 != 0) goto L_0x00eb
            org.apache.http.StatusLine r9 = r9.getStatusLine()
            r9.getStatusCode()
        L_0x00eb:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.inside.android.phone.mrpc.core.HttpWorker.handleResponse(org.apache.http.HttpResponse, int, java.lang.String):com.alipay.inside.android.phone.mrpc.core.Response");
    }

    /* access modifiers changed from: protected */
    public HttpUrlHeader handleResponseHeader(HttpResponse httpResponse) {
        Header[] allHeaders;
        HttpUrlHeader httpUrlHeader = new HttpUrlHeader();
        for (Header header : httpResponse.getAllHeaders()) {
            httpUrlHeader.setHead(header.getName(), header.getValue());
        }
        return httpUrlHeader;
    }

    /* access modifiers changed from: protected */
    public void fillResponse(HttpUrlResponse httpUrlResponse, HttpResponse httpResponse) {
        String str;
        long period = getPeriod(httpResponse);
        Header contentType = httpResponse.getEntity().getContentType();
        String str2 = null;
        if (contentType != null) {
            HashMap<String, String> contentType2 = getContentType(contentType.getValue());
            str2 = contentType2.get("charset");
            str = contentType2.get("Content-Type");
        } else {
            str = null;
        }
        httpUrlResponse.setContentType(str);
        httpUrlResponse.setCharset(str2);
        httpUrlResponse.setCreateTime(System.currentTimeMillis());
        httpUrlResponse.setPeriod(period);
    }

    /* access modifiers changed from: protected */
    public long getPeriod(HttpResponse httpResponse) {
        long j;
        Header firstHeader = httpResponse.getFirstHeader("Cache-Control");
        if (firstHeader != null) {
            String[] split = firstHeader.getValue().split("=");
            if (split.length >= 2) {
                try {
                    return parserMaxage(split);
                } catch (NumberFormatException e) {
                    LoggerFactory.f().a((String) "HttpWorker", (Throwable) e);
                }
            }
        }
        Header firstHeader2 = httpResponse.getFirstHeader("Expires");
        if (firstHeader2 != null) {
            try {
                j = AndroidHttpClient.parseDate(firstHeader2.getValue()) - System.currentTimeMillis();
            } catch (Throwable th) {
                LoggerFactory.f().c((String) "inside", th);
            }
            return j;
        }
        j = 0;
        return j;
    }

    /* access modifiers changed from: protected */
    public long parserMaxage(String[] strArr) {
        for (int i = 0; i < strArr.length; i++) {
            if ("max-age".equalsIgnoreCase(strArr[i])) {
                int i2 = i + 1;
                if (strArr[i2] != null) {
                    try {
                        return Long.parseLong(strArr[i2]);
                    } catch (Exception e) {
                        LoggerFactory.f().b((String) "rpc", (Throwable) e);
                    }
                } else {
                    continue;
                }
            }
        }
        return 0;
    }

    public HttpUrlRequest getRequest() {
        return this.mRequest;
    }

    /* access modifiers changed from: protected */
    public void writeData(HttpEntity httpEntity, long j, OutputStream outputStream) throws Exception {
        if (outputStream == null) {
            httpEntity.consumeContent();
            throw new IllegalArgumentException("Output stream may not be null");
        }
        InputStream content = httpEntity.getContent();
        long contentLength = httpEntity.getContentLength();
        try {
            byte[] bArr = new byte[2048];
            while (true) {
                int read = content.read(bArr);
                if (read == -1 || this.mRequest.isCanceled()) {
                    outputStream.flush();
                } else {
                    outputStream.write(bArr, 0, read);
                    j += (long) read;
                    if (getTransportCallback() != null && contentLength > 0) {
                        getTransportCallback().onProgressUpdate(this.mRequest, ((double) j) / ((double) contentLength));
                    }
                }
            }
            outputStream.flush();
            this.dataContainer.timeItemRelease("RPC_ALL_TIME");
            IOUtil.closeStream(content);
        } catch (Exception e) {
            LoggerFactory.f().a((String) "HttpWorker", e.getCause());
            StringBuilder sb = new StringBuilder("HttpWorker Request Error!");
            sb.append(e.getLocalizedMessage());
            throw new Exception(sb.toString());
        } catch (Throwable th) {
            this.dataContainer.timeItemRelease("RPC_ALL_TIME");
            IOUtil.closeStream(content);
            throw th;
        }
    }

    public Response processResponse(HttpResponse httpResponse, HttpUrlRequest httpUrlRequest) throws Exception {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        String reasonPhrase = httpResponse.getStatusLine().getReasonPhrase();
        if (statusCode == 200 || willHandleOtherCode(statusCode, reasonPhrase)) {
            return handleResponse(httpResponse, statusCode, reasonPhrase);
        }
        throw new HttpException(Integer.valueOf(httpResponse.getStatusLine().getStatusCode()), httpResponse.getStatusLine().getReasonPhrase());
    }
}
