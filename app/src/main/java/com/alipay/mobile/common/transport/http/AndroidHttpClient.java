package com.alipay.mobile.common.transport.http;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpConstants;
import com.alipay.mobile.common.netsdkextdependapi.appinfo.AppInfoUtil;
import com.alipay.mobile.common.transport.utils.DebugLogConfig;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.impl.conn.DefaultHttpRoutePlanner;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpContext;

public final class AndroidHttpClient implements HttpClient {
    public static final int CONNECTION_POOL_TIMEOUT = Integer.MAX_VALUE;
    public static long DEFAULT_SYNC_MIN_GZIP_BYTES = 160;
    public static int INSTANCE_TYPE_BIG = 1;
    public static int INSTANCE_TYPE_NORMAL = 0;
    public static final int MAX_CONNECTIONS = 70;
    public static final int MAX_ROUTE = 30;
    public static final int NORMAL_ROUTE = 6;
    private static String[] a = {"text/", "application/xml", "application/json"};
    /* access modifiers changed from: private */
    public static final HttpRequestInterceptor b = new HttpRequestInterceptor() {
        public final void process(HttpRequest request, HttpContext context) {
            if (Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper()) {
                throw new RuntimeException("This thread forbids HTTP requests");
            }
        }
    };
    private final HttpClient c;
    private RuntimeException d = new IllegalStateException("AndroidHttpClient created and never closed");
    /* access modifiers changed from: private */
    public volatile LoggingConfiguration e;

    class CurlLogger implements HttpRequestInterceptor {
        private CurlLogger() {
        }

        public void process(HttpRequest request, HttpContext context) {
            LoggingConfiguration configuration = AndroidHttpClient.this.e;
            if (configuration != null && configuration.a() && (request instanceof HttpUriRequest)) {
                configuration.a(AndroidHttpClient.a((HttpUriRequest) request, true));
            }
        }
    }

    class LoggingConfiguration {
        private final String a;
        private final int b;

        private LoggingConfiguration(String tag, int level) {
            this.a = tag;
            this.b = level;
        }

        /* access modifiers changed from: private */
        public boolean a() {
            if (MiscUtils.isDebugger(TransportEnvUtil.getContext())) {
                return true;
            }
            if (this.a.length() > 23) {
                return false;
            }
            return Log.isLoggable(this.a, this.b);
        }

        /* access modifiers changed from: private */
        public void a(String message) {
            if (MiscUtils.isDebugger(TransportEnvUtil.getContext())) {
                LogCatUtil.printInfo("AndroidHttpClient", "AndroidHttpClient level=[" + this.b + "]  tag=[" + this.a + "]  message=[" + message + "]");
            } else {
                Log.println(this.b, this.a, message);
            }
        }
    }

    public static AndroidHttpClient newInstance(String userAgent, Context context) {
        return newInstance(userAgent, context, INSTANCE_TYPE_NORMAL);
    }

    public static AndroidHttpClient newInstance(String userAgent, Context context, int instanceType) {
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setStaleCheckingEnabled(params, true);
        HttpConnectionParams.setConnectionTimeout(params, HttpConstants.CONNECTION_TIME_OUT);
        HttpConnectionParams.setSoTimeout(params, 30000);
        HttpConnectionParams.setSocketBufferSize(params, 8192);
        HttpClientParams.setRedirecting(params, false);
        HttpConnectionParams.setTcpNoDelay(params, true);
        setMaxTotalConnections(params, instanceType);
        HttpProtocolParams.setUserAgent(params, userAgent);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", new ZSSLSocketFactory(), 443));
        ClientConnectionManager manager = new ZThreadSafeClientConnManager(params, schemeRegistry);
        a();
        return new AndroidHttpClient(manager, params);
    }

    public static void setMaxTotalConnections(HttpParams params, int instanceType) {
        ConnManagerParams.setTimeout(params, 2147483647L);
        int routeLinkCount = 6;
        if (instanceType == INSTANCE_TYPE_BIG) {
            routeLinkCount = 30;
        }
        ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRouteBean(routeLinkCount));
        ConnManagerParams.setMaxTotalConnections(params, 70);
    }

    private static void a() {
        if (TransportEnvUtil.getContext() != null && AppInfoUtil.isDebuggable()) {
            DebugLogConfig.enableHttpClient();
            LogCatUtil.printInfo("AndroidHttpClient", "Open HttpClient Log !");
        }
    }

    public static AndroidHttpClient newInstance(String userAgent) {
        return newInstance(userAgent, null);
    }

    public static AndroidHttpClient newDefaultInstance() {
        return newInstance("Android_Ant_Client", null);
    }

    public static AndroidHttpClient newInstanceOfBigConn(String userAgent) {
        return newInstance(userAgent, null, INSTANCE_TYPE_BIG);
    }

    private AndroidHttpClient(ClientConnectionManager ccm, HttpParams params) {
        this.c = new DefaultHttpClient(ccm, params) {
            /* access modifiers changed from: protected */
            public BasicHttpProcessor createHttpProcessor() {
                BasicHttpProcessor processor = AndroidHttpClient.super.createHttpProcessor();
                processor.addRequestInterceptor(AndroidHttpClient.b);
                processor.addRequestInterceptor(new CurlLogger());
                return processor;
            }

            /* access modifiers changed from: protected */
            public HttpContext createHttpContext() {
                HttpContext context = new BasicHttpContext();
                context.setAttribute("http.authscheme-registry", getAuthSchemes());
                context.setAttribute("http.cookiespec-registry", getCookieSpecs());
                context.setAttribute("http.auth.credentials-provider", getCredentialsProvider());
                return context;
            }

            /* access modifiers changed from: protected */
            public ConnectionKeepAliveStrategy createConnectionKeepAliveStrategy() {
                return new ConnectionKeepAliveStrategy() {
                    public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                        return StatisticConfig.MIN_UPLOAD_INTERVAL;
                    }
                };
            }

            /* access modifiers changed from: protected */
            public HttpRoutePlanner createHttpRoutePlanner() {
                return new DefaultHttpRoutePlanner(getConnectionManager().getSchemeRegistry());
            }

            /* access modifiers changed from: protected */
            public RedirectHandler createRedirectHandler() {
                return new DefaultRedirectHandler();
            }
        };
        enableCurlLogging("AndroidHttpClient", 2);
    }

    public static void modifyRequestToAcceptGzipResponse(HttpRequest request) {
        request.addHeader("Accept-Encoding", "gzip");
    }

    public final void setHttpRequestRetryHandler(HttpRequestRetryHandler retryHandler) {
        this.c.setHttpRequestRetryHandler(retryHandler);
    }

    public static void modifyRequestToKeepAlive(HttpRequest request) {
        request.addHeader(H5AppHttpRequest.HEADER_CONNECTION, "Keep-Alive");
    }

    public static InputStream getUngzippedContent(HttpEntity entity) {
        return getUngzippedContent(entity.getContent(), entity.getContentEncoding());
    }

    public static InputStream getUngzippedContent(InputStream responseStream, Header header) {
        if (responseStream == null || header == null) {
            return responseStream;
        }
        String contentEncoding = header.getValue();
        if (contentEncoding == null || !contentEncoding.contains("gzip")) {
            return responseStream;
        }
        return new GZIPInputStream(responseStream);
    }

    public final void close() {
        if (this.d != null) {
            getConnectionManager().shutdown();
            this.d = null;
        }
    }

    public final HttpParams getParams() {
        return this.c.getParams();
    }

    public final ClientConnectionManager getConnectionManager() {
        return this.c.getConnectionManager();
    }

    public final RedirectHandler getRedirectHandler() {
        return this.c.getRedirectHandler();
    }

    public final HttpResponse execute(HttpUriRequest request) {
        return this.c.execute(request);
    }

    public final HttpResponse execute(HttpUriRequest request, HttpContext context) {
        return this.c.execute(request, context);
    }

    public final HttpResponse execute(HttpHost target, HttpRequest request) {
        return this.c.execute(target, request);
    }

    public final HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context) {
        return this.c.execute(target, request, context);
    }

    public final <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler) {
        return this.c.execute(request, responseHandler);
    }

    public final <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) {
        return this.c.execute(request, responseHandler, context);
    }

    public final <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler) {
        return this.c.execute(target, request, responseHandler);
    }

    public final <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) {
        return this.c.execute(target, request, responseHandler, context);
    }

    public static AbstractHttpEntity getCompressedEntity(byte[] data, ContentResolver resolver) {
        LogCatUtil.info("RPC_PERF", "gzip...");
        if (((long) data.length) < getMinGzipSize(resolver)) {
            return new ByteArrayEntity(data);
        }
        ByteArrayOutputStream arr = new ByteArrayOutputStream();
        OutputStream zipper = new GZIPOutputStream(arr);
        zipper.write(data);
        zipper.close();
        AbstractHttpEntity entity = new ByteArrayEntity(arr.toByteArray());
        entity.setContentEncoding("gzip");
        LogCatUtil.info("RPC_PERF", "gzip size:" + data.length + "->" + entity.getContentLength());
        return entity;
    }

    public static long getMinGzipSize(ContentResolver resolver) {
        return DEFAULT_SYNC_MIN_GZIP_BYTES;
    }

    public final void enableCurlLogging(String name, int level) {
        if (name == null) {
            throw new IllegalArgumentException("name may not be null");
        } else if (level < 2 || level > 7) {
            throw new IllegalArgumentException("Level is out of range [2..7]");
        } else {
            this.e = new LoggingConfiguration(name, level);
        }
    }

    public final void disableCurlLogging() {
        this.e = null;
    }

    /* access modifiers changed from: private */
    @TargetApi(8)
    public static String a(HttpUriRequest request, boolean logAuthToken) {
        Header[] allHeaders;
        StringBuilder builder = new StringBuilder();
        builder.append("curl ");
        for (Header header : request.getAllHeaders()) {
            if (logAuthToken || (!TextUtils.equals(header.getName(), "Authorization") && !TextUtils.equals(header.getName(), "Cookie"))) {
                builder.append("--header \"");
                builder.append(header.toString().trim());
                builder.append("\" ");
            }
        }
        URI uri = request.getURI();
        HttpRequest original = null;
        if (request instanceof RequestWrapper) {
            original = ((RequestWrapper) request).getOriginal();
        }
        if (original != null && (original instanceof HttpUriRequest)) {
            uri = ((HttpUriRequest) original).getURI();
        }
        builder.append("\"");
        builder.append(uri);
        builder.append("\"");
        if (!(request instanceof HttpEntityEnclosingRequest)) {
            return builder.toString();
        }
        HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
        if (entity == null || !entity.isRepeatable()) {
            return builder.toString();
        }
        if (entity.getContentLength() > 0) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            entity.writeTo(stream);
            a(request, builder, stream);
        } else {
            builder.append(" [NO DATA]");
        }
        return builder.toString();
    }

    private static void a(HttpUriRequest request, StringBuilder builder, ByteArrayOutputStream stream) {
        if (a(request)) {
            builder.insert(0, "echo '" + Base64.encodeToString(stream.toByteArray(), 2) + "' | base64 -d > /tmp/$$.bin; ");
            builder.append(" --data-binary @/tmp/$$.bin");
            return;
        }
        builder.append(" --data-ascii \"").append(stream.toString()).append("\"");
    }

    private static boolean a(HttpUriRequest request) {
        Header[] headers = request.getHeaders("content-encoding");
        if (headers != null) {
            for (Header header : headers) {
                if ("gzip".equalsIgnoreCase(header.getValue())) {
                    return true;
                }
            }
        }
        Header[] headers2 = request.getHeaders("content-type");
        if (headers2 == null) {
            return true;
        }
        for (Header header2 : headers2) {
            for (String contentType : a) {
                if (header2.getValue().startsWith(contentType)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static long parseDate(String dateString) {
        return HttpDateTime.parse(dateString);
    }
}
