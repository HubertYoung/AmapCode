package com.alipay.mobile.common.transportext.biz.spdy.apache;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.TransportStrategy;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportStrategy;
import com.alipay.mobile.common.transportext.biz.spdy.OkHttpClient;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.SpdyRequestRetryHandler;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.RequestLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class OkApacheClient implements HttpClient {
    public static final String REQUESTID = "requestId";
    private static final String TAG = "OkApacheClient";
    protected final OkHttpClient client;
    private Map<String, HttpURLConnection> connectionMap;
    protected Context mContext;
    private final HttpParams params;

    public OkApacheClient(Context context) {
        this(new OkHttpClient(), context);
    }

    /* JADX WARNING: type inference failed for: r0v1, types: [com.alipay.mobile.common.transportext.biz.spdy.apache.OkApacheClient$1, org.apache.http.params.HttpParams] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v1, types: [com.alipay.mobile.common.transportext.biz.spdy.apache.OkApacheClient$1, org.apache.http.params.HttpParams]
      assigns: [com.alipay.mobile.common.transportext.biz.spdy.apache.OkApacheClient$1]
      uses: [org.apache.http.params.HttpParams]
      mth insns count: 8
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
    public OkApacheClient(com.alipay.mobile.common.transportext.biz.spdy.OkHttpClient r2, android.content.Context r3) {
        /*
            r1 = this;
            r1.<init>()
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r1.connectionMap = r0
            com.alipay.mobile.common.transportext.biz.spdy.apache.OkApacheClient$1 r0 = new com.alipay.mobile.common.transportext.biz.spdy.apache.OkApacheClient$1
            r0.<init>()
            r1.params = r0
            r1.client = r2
            android.content.Context r0 = r3.getApplicationContext()
            r1.mContext = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.spdy.apache.OkApacheClient.<init>(com.alipay.mobile.common.transportext.biz.spdy.OkHttpClient, android.content.Context):void");
    }

    /* access modifiers changed from: protected */
    public HttpURLConnection openConnection(URL url, TransportContext netContext) {
        return this.client.open(url, netContext);
    }

    public HttpParams getParams() {
        return this.params;
    }

    public ClientConnectionManager getConnectionManager() {
        throw new UnsupportedOperationException();
    }

    public HttpResponse execute(HttpUriRequest request) {
        return execute((HttpHost) null, (HttpRequest) request, (HttpContext) null);
    }

    public HttpResponse execute(HttpUriRequest request, HttpContext context) {
        return execute((HttpHost) null, (HttpRequest) request, context);
    }

    public HttpResponse execute(HttpHost host, HttpRequest request) {
        return execute(host, request, (HttpContext) null);
    }

    /* JADX INFO: finally extract failed */
    public HttpResponse execute(HttpHost host, HttpRequest request, HttpContext context) {
        String requestId = null;
        try {
            RequestLine requestLine = request.getRequestLine();
            LogCatUtil.info(TAG, "ThreadId=[" + Thread.currentThread().getId() + "]   Receipt spdy request. url=" + requestLine.getUri());
            TransportContext transportContext = new TransportContext();
            transportContext.context = this.mContext;
            TransportStrategy.configInit(this.mContext, requestLine.getUri(), transportContext);
            ExtTransportStrategy.configInit(this.mContext, transportContext);
            getParams().setParameter("http.route.default-proxy", NetworkUtils.getProxyOfEnhanced(this.mContext));
            HttpURLConnection connection = openConnection(new URL(requestLine.getUri()), transportContext);
            if (context != null) {
                requestId = (String) context.getAttribute(REQUESTID);
                if (!TextUtils.isEmpty(requestId)) {
                    if (!this.connectionMap.containsKey(requestId)) {
                        this.connectionMap.put(requestId, connection);
                    } else {
                        LogCatUtil.warn((String) TAG, "requestId=[" + requestId + "],requestId 冲突");
                    }
                }
            } else {
                LogCatUtil.warn((String) TAG, (String) "HttpContext 为null");
            }
            setUseCaches2False(connection);
            connection.setRequestMethod(requestLine.getMethod());
            Header[] allHeaders = request.getAllHeaders();
            int length = allHeaders.length;
            for (int i = 0; i < length; i++) {
                Header header = allHeaders[i];
                connection.addRequestProperty(header.getName(), header.getValue());
            }
            if (request instanceof HttpEntityEnclosingRequest) {
                HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
                if (entity != null) {
                    connection.setDoOutput(true);
                    Header type = entity.getContentType();
                    if (type != null) {
                        connection.addRequestProperty(type.getName(), type.getValue());
                    }
                    Header encoding = entity.getContentEncoding();
                    if (encoding != null) {
                        connection.addRequestProperty(encoding.getName(), encoding.getValue());
                    }
                    if (entity.isChunked() || entity.getContentLength() < 0) {
                        connection.setChunkedStreamingMode(0);
                    } else if (entity.getContentLength() <= 8192) {
                        connection.addRequestProperty("Content-Length", Long.toString(entity.getContentLength()));
                    } else {
                        connection.setFixedLengthStreamingMode((int) entity.getContentLength());
                    }
                    entity.writeTo(connection.getOutputStream());
                }
            }
            int responseCode = connection.getResponseCode();
            LogCatUtil.info(TAG, "ThreadId=[" + Thread.currentThread().getId() + "] response status code=[" + responseCode + "]. ");
            BasicHttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, responseCode, connection.getResponseMessage());
            InputStreamEntity entity2 = new InputStreamEntity(responseCode < 400 ? connection.getInputStream() : connection.getErrorStream(), (long) connection.getContentLength());
            int i2 = 0;
            while (true) {
                String name = connection.getHeaderFieldKey(i2);
                if (name == null) {
                    break;
                }
                BasicHeader header2 = new BasicHeader(name, connection.getHeaderField(i2));
                response.addHeader(header2);
                if (name.equalsIgnoreCase("Content-Type")) {
                    entity2.setContentType(header2);
                } else if (name.equalsIgnoreCase(TransportConstants.KEY_X_CONTENT_ENCODING)) {
                    entity2.setContentEncoding(header2);
                }
                i2++;
            }
            response.setEntity(entity2);
            LogCatUtil.info(TAG, "ThreadId=[" + Thread.currentThread().getId() + "] return response.");
            try {
                if (!TextUtils.isEmpty(requestId)) {
                    this.connectionMap.remove(requestId);
                }
            } catch (Exception e) {
                LogCatUtil.error(TAG, "connectionMap.remove()异常", e);
            }
            return response;
        } catch (Throwable th) {
            try {
                if (!TextUtils.isEmpty(null)) {
                    this.connectionMap.remove(null);
                }
            } catch (Exception e2) {
                LogCatUtil.error(TAG, "connectionMap.remove()异常", e2);
            }
            throw th;
        }
    }

    public void close(String requestId) {
        HttpURLConnection conn = this.connectionMap.get(requestId);
        if (conn != null) {
            try {
                conn.getInputStream().close();
            } catch (Exception e) {
                LogCatUtil.error(TAG, "close()方法异常", e);
            }
            try {
                conn.getOutputStream().close();
            } catch (Exception e2) {
                LogCatUtil.error(TAG, "close()方法异常", e2);
            }
            try {
                this.connectionMap.remove(requestId);
            } catch (Exception e3) {
                LogCatUtil.error(TAG, "close()方法异常", e3);
            }
        }
    }

    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> handler) {
        return execute(null, request, handler, null);
    }

    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> handler, HttpContext context) {
        return execute(null, request, handler, context);
    }

    public <T> T execute(HttpHost host, HttpRequest request, ResponseHandler<? extends T> handler) {
        return execute(host, request, handler, null);
    }

    public <T> T execute(HttpHost host, HttpRequest request, ResponseHandler<? extends T> handler, HttpContext context) {
        HttpResponse response = execute(host, request, context);
        try {
            return handler.handleResponse(response);
        } finally {
            consumeContentQuietly(response);
        }
    }

    public void setRequestRetryHandler(SpdyRequestRetryHandler requestRetryHandler) {
        this.client.setRequestRetryHandler(requestRetryHandler);
    }

    private static void consumeContentQuietly(HttpResponse response) {
        try {
            response.getEntity().consumeContent();
        } catch (Throwable ignored) {
            LogCatUtil.debug(InnerLogUtil.MWALLET_SPDY_TAG, "consumeContentQuietly exception=" + ignored.getMessage());
        }
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
