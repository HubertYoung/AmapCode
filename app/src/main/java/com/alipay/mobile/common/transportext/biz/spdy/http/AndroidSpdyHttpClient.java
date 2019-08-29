package com.alipay.mobile.common.transportext.biz.spdy.http;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.TransportStrategy;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.ext.ExtTransportClient;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.http.HttpWorker;
import com.alipay.mobile.common.transport.io.RpcBufferedOutputStream;
import com.alipay.mobile.common.transport.strategy.NetworkTunnelStrategy;
import com.alipay.mobile.common.transport.utils.HttpUtils;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.RetryService;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportStrategy;
import com.alipay.mobile.common.transportext.biz.shared.spdy.SpdyAvalibleObservable;
import com.alipay.mobile.common.transportext.biz.shared.spdy.SpdyShortTimeoutHelper;
import com.alipay.mobile.common.transportext.biz.spdy.OkHttpClient;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.AbstractHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class AndroidSpdyHttpClient implements ExtTransportClient {
    private static AndroidSpdyHttpClient mAndroidSpdyHttpClient;
    private boolean executedPreConnect = false;
    /* access modifiers changed from: private */
    public OkHttpClient mClient;
    private Context mContext;
    private HttpUrlRequest mOriginHttpUrlRequest = null;
    private final HttpParams params = new SpdyHttpParams();

    public class ConnectionRunnable implements Runnable {
        public ConnectionRunnable() {
        }

        public void run() {
            AndroidSpdyHttpClient.this.connect();
        }
    }

    class SpdyHttpParams extends AbstractHttpParams {
        SpdyHttpParams() {
        }

        public Object getParameter(String name) {
            if (TextUtils.equals(name, "http.route.default-proxy")) {
                Proxy proxy = AndroidSpdyHttpClient.this.mClient.getProxy();
                if (proxy == null) {
                    return null;
                }
                InetSocketAddress address = (InetSocketAddress) proxy.address();
                if (address != null) {
                    return new HttpHost(address.getHostName(), address.getPort());
                }
                return null;
            }
            throw new IllegalArgumentException(name);
        }

        /* JADX WARNING: type inference failed for: r5v0, types: [org.apache.http.params.HttpParams, com.alipay.mobile.common.transportext.biz.spdy.http.AndroidSpdyHttpClient$SpdyHttpParams] */
        public HttpParams setParameter(String name, Object value) {
            if (TextUtils.equals(name, "http.route.default-proxy")) {
                HttpHost host = (HttpHost) value;
                Proxy proxy = null;
                if (host != null) {
                    proxy = new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(host.getHostName(), host.getPort()));
                }
                OkHttpClient access$000 = AndroidSpdyHttpClient.this.mClient;
                if (proxy == null) {
                    proxy = Proxy.NO_PROXY;
                }
                access$000.setProxy(proxy);
                return this;
            }
            throw new IllegalArgumentException(name);
        }

        public HttpParams copy() {
            throw new UnsupportedOperationException();
        }

        public boolean removeParameter(String name) {
            throw new UnsupportedOperationException();
        }
    }

    /* JADX WARNING: type inference failed for: r0v1, types: [org.apache.http.params.HttpParams, com.alipay.mobile.common.transportext.biz.spdy.http.AndroidSpdyHttpClient$SpdyHttpParams] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v1, types: [org.apache.http.params.HttpParams, com.alipay.mobile.common.transportext.biz.spdy.http.AndroidSpdyHttpClient$SpdyHttpParams]
      assigns: [com.alipay.mobile.common.transportext.biz.spdy.http.AndroidSpdyHttpClient$SpdyHttpParams]
      uses: [org.apache.http.params.HttpParams]
      mth insns count: 25
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
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private AndroidSpdyHttpClient(android.content.Context r3) {
        /*
            r2 = this;
            r2.<init>()
            r0 = 0
            r2.executedPreConnect = r0
            com.alipay.mobile.common.transportext.biz.spdy.http.AndroidSpdyHttpClient$SpdyHttpParams r0 = new com.alipay.mobile.common.transportext.biz.spdy.http.AndroidSpdyHttpClient$SpdyHttpParams
            r0.<init>()
            r2.params = r0
            r0 = 0
            r2.mOriginHttpUrlRequest = r0
            r0 = 1
            com.alipay.mobile.common.transportext.biz.spdy.AlipayOkHttpClientConfig.isUseNpn = r0
            android.content.Context r0 = r3.getApplicationContext()
            r2.mContext = r0
            com.alipay.mobile.common.transportext.biz.spdy.OkHttpClient r0 = new com.alipay.mobile.common.transportext.biz.spdy.OkHttpClient
            r0.<init>()
            r2.mClient = r0
            com.alipay.mobile.common.transportext.biz.spdy.OkHttpClient r0 = r2.mClient
            android.content.Context r1 = r3.getApplicationContext()
            r0.setContext(r1)
            com.alipay.mobile.common.transportext.biz.spdy.OkHttpClient r0 = r2.mClient
            com.alipay.mobile.common.transportext.biz.spdy.http.ZSpdyRequestRetryHandler r1 = new com.alipay.mobile.common.transportext.biz.spdy.http.ZSpdyRequestRetryHandler
            r1.<init>()
            r0.setRequestRetryHandler(r1)
            android.content.Context r0 = r2.mContext     // Catch:{ Throwable -> 0x004b }
            boolean r0 = com.alipay.mobile.common.transport.utils.MiscUtils.isOtherProcess(r0)     // Catch:{ Throwable -> 0x004b }
            if (r0 == 0) goto L_0x0043
            java.lang.String r0 = "alipay.spdy.keepAliveDuration"
            java.lang.String r1 = "30000"
            java.lang.System.setProperty(r0, r1)     // Catch:{ Throwable -> 0x004b }
        L_0x0042:
            return
        L_0x0043:
            java.lang.String r0 = "alipay.spdy.keepAliveDuration"
            java.lang.String r1 = "600000"
            java.lang.System.setProperty(r0, r1)     // Catch:{ Throwable -> 0x004b }
            goto L_0x0042
        L_0x004b:
            r0 = move-exception
            java.lang.String r0 = "AndroidSpdyHttpClient"
            java.lang.String r1 = "setProperty keepAliveDuration"
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r0, r1)
            goto L_0x0042
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.spdy.http.AndroidSpdyHttpClient.<init>(android.content.Context):void");
    }

    public static synchronized AndroidSpdyHttpClient newInstance(Context context) {
        AndroidSpdyHttpClient androidSpdyHttpClient;
        synchronized (AndroidSpdyHttpClient.class) {
            if (mAndroidSpdyHttpClient != null) {
                androidSpdyHttpClient = mAndroidSpdyHttpClient;
            } else {
                synchronized (AndroidSpdyHttpClient.class) {
                    if (mAndroidSpdyHttpClient == null) {
                        mAndroidSpdyHttpClient = new AndroidSpdyHttpClient(context);
                    }
                }
                androidSpdyHttpClient = mAndroidSpdyHttpClient;
            }
        }
        return androidSpdyHttpClient;
    }

    public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context) {
        TransportContext netContext = null;
        try {
            netContext = (TransportContext) context.getAttribute(TransportConstants.KEY_NET_CONTEXT);
            LogCatUtil.debug(InnerLogUtil.MWALLET_SPDY_TAG, "SPDY(ATLS) RPC REQUEST START!" + netContext.api);
            getClient().setConnectTimeout((long) TransportStrategy.getConnTimeout(this.mContext), TimeUnit.MILLISECONDS);
            getParams().setParameter("http.route.default-proxy", NetworkUtils.getProxyOfEnhanced(this.mContext));
            setStreamReadTimeout(netContext, request, context);
            LogCatUtil.info(HttpWorker.TAG, "spdy url: " + netContext.currentReqInfo.callUrl);
            HttpURLConnection urlConnection = openConnection(new URL(netContext.currentReqInfo.callUrl), netContext);
            urlConnection.setInstanceFollowRedirects(false);
            setUseCaches2False(urlConnection);
            setRequestMethod(request, urlConnection);
            addRequestProperties(request, urlConnection);
            postRequestBody(request, netContext, urlConnection, context);
            BasicHttpResponse response = obatinBasicHttpResponse(urlConnection);
            HttpUtils.extractCookiesFromResponse(target, request, response, context);
            LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "SPDY(ATLS)结果：" + urlConnection.getResponseCode());
            SpdyAvalibleObservable.getInstance().asyncNotifySpdyAvalible();
            return response;
        } catch (Exception e) {
            if (!NetworkUtils.isNetworkAvailable(TransportEnvUtil.getContext())) {
                LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, (String) "AndroidSpdyHttpClient. isNetworkAvailable == false ");
                throw e;
            } else if (request.getFirstHeader("spdy-proxy-url") != null) {
                throw e;
            } else if (!NetworkTunnelStrategy.getInstance().isCanUseSpdy()) {
                throw e;
            } else {
                HttpUrlRequest originHttpUrlRequest = getHttpUrlRequest(context);
                if (netContext == null || TextUtils.isEmpty(netContext.api) || !RetryService.getInstance().isSupportResend(netContext.api, originHttpUrlRequest.allowRetry) || context.getAttribute(TransportConstants.KEY_SPDY_RETRING) != null) {
                    netContext.getCurrentDataContainer().putDataItem("ERROR", e.getMessage());
                    throw e;
                }
                netContext.getCurrentDataContainer().putDataItem("RETRY", "T");
                LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "spdy retry for start again. api=[" + netContext.api + "]");
                context.setAttribute(TransportConstants.KEY_SPDY_RETRING, Boolean.TRUE);
                return execute(target, request, context);
            }
        }
    }

    /* access modifiers changed from: protected */
    public HttpUrlRequest getHttpUrlRequest(HttpContext context) {
        if (this.mOriginHttpUrlRequest != null) {
            return this.mOriginHttpUrlRequest;
        }
        HttpUrlRequest originHttpUrlRequest = null;
        try {
            originHttpUrlRequest = (HttpUrlRequest) context.getAttribute(TransportConstants.KEY_ORIGIN_REQUEST);
            this.mOriginHttpUrlRequest = originHttpUrlRequest;
            return originHttpUrlRequest;
        } catch (Throwable ex) {
            LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "HttpUrlRequest cast fail. " + ex.toString());
            return originHttpUrlRequest;
        }
    }

    private void setStreamReadTimeout(TransportContext netContext, HttpRequest request, HttpContext context) {
        int timeout;
        if (request.getFirstHeader("spdy-proxy-url") != null) {
            timeout = 45000;
        } else {
            if (context.getAttribute(TransportConstants.KEY_SPDY_RETRING) == null && !MiscUtils.isAtFrontDesk(this.mContext) && RetryService.getInstance().isSupportResend(netContext.api, getHttpUrlRequest(context).allowRetry)) {
                LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "AndroidSpdyHttpCllient. To perform a 'adjustmentSpdyTimeout' api=[" + netContext.api + "]");
                SpdyShortTimeoutHelper.adjustmentSpdyTimeout();
            }
            if (!ExtTransportStrategy.isUseSpdyShortReadTimeout() || !RetryService.getInstance().isSupportResend(netContext.api, getHttpUrlRequest(context).allowRetry)) {
                int soTimeout = HttpConnectionParams.getSoTimeout(request.getParams());
                if (soTimeout > 0) {
                    timeout = soTimeout;
                } else {
                    timeout = TransportStrategy.getReadTimeout(this.mContext);
                }
            } else {
                timeout = ExtTransportStrategy.getSpdyShortTimeout();
            }
        }
        LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "setStreamReadTimeout(" + timeout + ")");
        this.mClient.setStreamReadTimeout((long) timeout, TimeUnit.MILLISECONDS);
    }

    public int getModuleCategory() {
        return 0;
    }

    private BasicHttpResponse obatinBasicHttpResponse(HttpURLConnection urlConnection) {
        int responseCode = urlConnection.getResponseCode();
        BasicHttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, responseCode, urlConnection.getResponseMessage());
        InputStream responseBody = responseCode < 400 ? urlConnection.getInputStream() : urlConnection.getErrorStream();
        if (!(responseCode == 304 && responseBody.available() == 0)) {
            InputStreamEntity entity = new InputStreamEntity(responseBody, (long) urlConnection.getContentLength());
            int i = 0;
            while (true) {
                String name = urlConnection.getHeaderFieldKey(i);
                if (name == null) {
                    break;
                }
                BasicHeader header = new BasicHeader(name, urlConnection.getHeaderField(i));
                response.addHeader(header);
                if (name.equalsIgnoreCase("Content-Type")) {
                    entity.setContentType(header);
                } else if (name.equalsIgnoreCase(TransportConstants.KEY_X_CONTENT_ENCODING)) {
                    entity.setContentEncoding(header);
                } else {
                    name.equalsIgnoreCase("Content-Length");
                }
                i++;
            }
            response.setEntity(entity);
        }
        return response;
    }

    private void addRequestProperties(HttpRequest request, HttpURLConnection urlConnection) {
        Header[] allHeaders;
        for (Header header : request.getAllHeaders()) {
            urlConnection.addRequestProperty(header.getName(), header.getValue());
        }
        HttpEntity entity = getHttpEntity(request);
        if (entity != null) {
            Header type = entity.getContentType();
            if (type != null) {
                urlConnection.setRequestProperty(type.getName(), type.getValue());
            }
            Header encoding = entity.getContentEncoding();
            if (encoding != null) {
                urlConnection.setRequestProperty(encoding.getName(), encoding.getValue());
            }
            if (entity.isChunked() || entity.getContentLength() < 0) {
                urlConnection.setChunkedStreamingMode(0);
            } else if (entity.getContentLength() <= 8192) {
                urlConnection.setRequestProperty("Content-Length", Long.toString(entity.getContentLength()));
            } else {
                urlConnection.setFixedLengthStreamingMode((int) entity.getContentLength());
            }
        }
    }

    private HttpEntity getHttpEntity(HttpRequest request) {
        if (!(request instanceof HttpEntityEnclosingRequest)) {
            return null;
        }
        HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
        if (entity == null) {
            return null;
        }
        return entity;
    }

    private void postRequestBody(HttpRequest request, TransportContext netContext, HttpURLConnection urlConnection, HttpContext context) {
        HttpEntity entity = getHttpEntity(request);
        if (entity != null) {
            urlConnection.setDoOutput(true);
            if (entity instanceof ByteArrayEntity) {
                urlConnection.getOutputStream().write(EntityUtils.toByteArray(entity));
                urlConnection.getOutputStream().flush();
            } else {
                RpcBufferedOutputStream rpcBufferedOutputStream = new RpcBufferedOutputStream(urlConnection.getOutputStream());
                entity.writeTo(rpcBufferedOutputStream);
                rpcBufferedOutputStream.flush();
            }
            netContext.getCurrentDataContainer().putDataItem("REQ_SIZE", entity.getContentLength());
        }
    }

    private void setRequestMethod(HttpRequest request, HttpURLConnection urlConnection) {
        if (request instanceof HttpRequestBase) {
            urlConnection.setRequestMethod(((HttpRequestBase) request).getMethod());
        } else {
            urlConnection.setRequestMethod(request.getRequestLine().getMethod());
        }
    }

    public HttpURLConnection openConnection(URL url, TransportContext netContext) {
        return this.mClient.open(url, netContext);
    }

    public OkHttpClient getClient() {
        return this.mClient;
    }

    public HttpParams getParams() {
        return this.params;
    }

    public void asynPreConnect(ThreadPoolExecutor poolExecutor) {
        try {
            if (!this.executedPreConnect) {
                if (!NetworkTunnelStrategy.getInstance().isCanUseSpdyLongLink()) {
                    LogCatUtil.debug(InnerLogUtil.MWALLET_SPDY_TAG, "asynPreConnect. isCanUseSpdyLongLink is false, return false.");
                    return;
                }
                this.executedPreConnect = true;
                poolExecutor.execute(new ConnectionRunnable());
            }
        } catch (Exception e) {
            LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, (Throwable) e);
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean connect() {
        /*
            r9 = this;
            r4 = 0
            com.alipay.mobile.common.transport.strategy.NetworkTunnelStrategy r5 = com.alipay.mobile.common.transport.strategy.NetworkTunnelStrategy.getInstance()     // Catch:{ Exception -> 0x003f }
            boolean r5 = r5.isCanUseSpdyLongLink()     // Catch:{ Exception -> 0x003f }
            if (r5 != 0) goto L_0x0013
            java.lang.String r5 = "MWALLET_SPDY_LOG"
            java.lang.String r6 = "connect.  isCanUseSpdyLongLink is false, return false."
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r5, r6)     // Catch:{ Exception -> 0x003f }
        L_0x0012:
            return r4
        L_0x0013:
            com.alipay.mobile.common.transport.context.TransportContext r3 = new com.alipay.mobile.common.transport.context.TransportContext     // Catch:{ Exception -> 0x003f }
            r3.<init>()     // Catch:{ Exception -> 0x003f }
            android.content.Context r5 = r9.mContext     // Catch:{ Exception -> 0x003f }
            r3.context = r5     // Catch:{ Exception -> 0x003f }
            r5 = 2
            r3.choseExtLinkType = r5     // Catch:{ Exception -> 0x003f }
            android.content.Context r5 = r9.mContext     // Catch:{ Exception -> 0x003f }
            java.lang.String r6 = com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkUtils.getSpdyLongLinkOperType()     // Catch:{ Exception -> 0x003f }
            com.alipay.mobile.common.transport.TransportStrategy.configInit(r5, r6, r3)     // Catch:{ Exception -> 0x003f }
            android.content.Context r5 = r9.mContext     // Catch:{ Exception -> 0x003f }
            com.alipay.mobile.common.transportext.biz.shared.ExtTransportStrategy.configInit(r5, r3)     // Catch:{ Exception -> 0x003f }
            com.alipay.mobile.common.transport.context.TransportContext$SingleRPCReqConfig r5 = r3.currentReqInfo     // Catch:{ Exception -> 0x003f }
            if (r5 == 0) goto L_0x0037
            com.alipay.mobile.common.transport.context.TransportContext$SingleRPCReqConfig r5 = r3.currentReqInfo     // Catch:{ Exception -> 0x003f }
            boolean r5 = r5.use     // Catch:{ Exception -> 0x003f }
            if (r5 != 0) goto L_0x0046
        L_0x0037:
            java.lang.String r5 = "MWALLET_SPDY_LOG"
            java.lang.String r6 = " Current can't use spdy.  because by spdy swtich closed!."
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r5, r6)     // Catch:{ Exception -> 0x003f }
            goto L_0x0012
        L_0x003f:
            r1 = move-exception
            java.lang.String r5 = "MWALLET_SPDY_LOG"
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r5, r1)
            goto L_0x0012
        L_0x0046:
            com.alipay.mobile.common.transport.context.TransportContext$SingleRPCReqConfig r5 = r3.currentReqInfo     // Catch:{ Exception -> 0x003f }
            java.lang.String r5 = r5.protocol     // Catch:{ Exception -> 0x003f }
            java.lang.String r6 = "spdy"
            boolean r5 = android.text.TextUtils.equals(r5, r6)     // Catch:{ Exception -> 0x003f }
            if (r5 != 0) goto L_0x005a
            java.lang.String r5 = "MWALLET_SPDY_LOG"
            java.lang.String r6 = " Current can't use spdy.  because by protocol not equals spdy."
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r5, r6)     // Catch:{ Exception -> 0x003f }
            goto L_0x0012
        L_0x005a:
            com.alipay.mobile.common.transport.context.TransportContext$SingleRPCReqConfig r5 = r3.currentReqInfo     // Catch:{ Exception -> 0x003f }
            boolean r5 = r5.use     // Catch:{ Exception -> 0x003f }
            if (r5 != 0) goto L_0x0068
            java.lang.String r5 = "MWALLET_SPDY_LOG"
            java.lang.String r6 = " Current can't use spdy."
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r5, r6)     // Catch:{ Exception -> 0x003f }
            goto L_0x0012
        L_0x0068:
            org.apache.http.params.HttpParams r5 = r9.getParams()     // Catch:{ Exception -> 0x003f }
            java.lang.String r6 = "http.route.default-proxy"
            android.content.Context r7 = r9.mContext     // Catch:{ Exception -> 0x003f }
            org.apache.http.HttpHost r7 = com.alipay.mobile.common.transport.utils.NetworkUtils.getProxyOfEnhanced(r7)     // Catch:{ Exception -> 0x003f }
            r5.setParameter(r6, r7)     // Catch:{ Exception -> 0x003f }
            com.alipay.mobile.common.transportext.biz.spdy.OkHttpClient r5 = r9.getClient()     // Catch:{ Exception -> 0x003f }
            android.content.Context r6 = r9.mContext     // Catch:{ Exception -> 0x003f }
            int r6 = com.alipay.mobile.common.transport.TransportStrategy.getConnTimeout(r6)     // Catch:{ Exception -> 0x003f }
            long r6 = (long) r6     // Catch:{ Exception -> 0x003f }
            java.util.concurrent.TimeUnit r8 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ Exception -> 0x003f }
            r5.setConnectTimeout(r6, r8)     // Catch:{ Exception -> 0x003f }
            java.net.URL r5 = new java.net.URL     // Catch:{ Exception -> 0x003f }
            android.content.Context r6 = r9.mContext     // Catch:{ Exception -> 0x003f }
            java.lang.String r6 = com.alipay.mobile.common.transportext.biz.shared.ExtTransportStrategy.getSpdyUrl(r6)     // Catch:{ Exception -> 0x003f }
            r5.<init>(r6)     // Catch:{ Exception -> 0x003f }
            java.net.HttpURLConnection r2 = r9.openConnection(r5, r3)     // Catch:{ Exception -> 0x003f }
            r9.setUseCaches2False(r2)     // Catch:{ Exception -> 0x003f }
            r2.connect()     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r5 = "MWALLET_SPDY_LOG"
            java.lang.String r6 = " Spdy Connect success. "
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r5, r6)     // Catch:{ Exception -> 0x00a9 }
            com.alipay.mobile.common.transport.monitor.RPCDataParser.monitorLog(r3)     // Catch:{ Exception -> 0x003f }
            r4 = 1
            goto L_0x0012
        L_0x00a9:
            r1 = move-exception
            com.alipay.mobile.common.transport.monitor.DataContainer r0 = r3.getCurrentDataContainer()     // Catch:{ all -> 0x00cd }
            if (r0 == 0) goto L_0x00cc
            java.lang.String r5 = "ERROR"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x00cd }
            java.lang.String r7 = "Spdy connect fail. "
            r6.<init>(r7)     // Catch:{ all -> 0x00cd }
            java.lang.Throwable r7 = com.alipay.mobile.common.transport.utils.MiscUtils.getRootCause(r1)     // Catch:{ all -> 0x00cd }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x00cd }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x00cd }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x00cd }
            r0.putDataItem(r5, r6)     // Catch:{ all -> 0x00cd }
        L_0x00cc:
            throw r1     // Catch:{ all -> 0x00cd }
        L_0x00cd:
            r5 = move-exception
            com.alipay.mobile.common.transport.monitor.RPCDataParser.monitorLog(r3)     // Catch:{ Exception -> 0x003f }
            throw r5     // Catch:{ Exception -> 0x003f }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.spdy.http.AndroidSpdyHttpClient.connect():boolean");
    }

    public boolean isExecutedPreConnect() {
        return this.executedPreConnect;
    }

    private void setUseCaches2False(HttpURLConnection urlConnection) {
        try {
            if (urlConnection.getUseCaches()) {
                urlConnection.setUseCaches(false);
            }
        } catch (Exception e) {
            LogCatUtil.debug(InnerLogUtil.MWALLET_SPDY_TAG, "setUseCaches2False exception=" + e.getMessage());
        }
    }
}
