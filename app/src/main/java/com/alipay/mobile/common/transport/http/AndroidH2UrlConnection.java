package com.alipay.mobile.common.transport.http;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.io.RpcBufferedOutputStream;
import com.alipay.mobile.common.transport.utils.DebugLogConfig;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.autonavi.minimap.ajx3.modules.ModuleSocket;
import com.taobao.accs.utl.BaseMonitor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class AndroidH2UrlConnection {
    private static AndroidH2UrlConnection a;
    private Context b;
    private HttpUrlRequest c = null;
    private Object d;

    @Deprecated
    public static synchronized AndroidH2UrlConnection newInstance(Context context) {
        AndroidH2UrlConnection instance;
        synchronized (AndroidH2UrlConnection.class) {
            instance = getInstance(context);
        }
        return instance;
    }

    public static synchronized AndroidH2UrlConnection getInstance(Context context) {
        AndroidH2UrlConnection androidH2UrlConnection;
        synchronized (AndroidH2UrlConnection.class) {
            if (a != null) {
                androidH2UrlConnection = a;
            } else {
                synchronized (AndroidH2UrlConnection.class) {
                    if (a == null) {
                        a = new AndroidH2UrlConnection(context);
                    }
                }
                androidH2UrlConnection = a;
            }
        }
        return androidH2UrlConnection;
    }

    private AndroidH2UrlConnection(Context context) {
        this.b = context.getApplicationContext();
        System.setProperty("http.keepAliveDuration", "30000");
        System.setProperty("http.maxConnections", "19");
        DebugLogConfig.enableH2Logger();
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x01b0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.http.HttpResponse execute(org.apache.http.HttpHost r22, org.apache.http.HttpRequest r23, org.apache.http.protocol.HttpContext r24) {
        /*
            r21 = this;
            r12 = 0
            r16 = 0
            r5 = 0
            java.lang.String r17 = "NET_CONTEXT"
            r0 = r24
            r1 = r17
            java.lang.Object r17 = r0.getAttribute(r1)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r0 = r17
            com.alipay.mobile.common.transport.context.TransportContext r0 = (com.alipay.mobile.common.transport.context.TransportContext) r0     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r12 = r0
            com.alipay.mobile.common.transport.monitor.DataContainer r17 = r12.getCurrentDataContainer()     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.lang.String r18 = "NETTUNNEL"
            java.lang.String r19 = "URLCONNECTION"
            r17.putDataItem(r18, r19)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r0 = r23
            org.apache.http.client.methods.HttpUriRequest r0 = (org.apache.http.client.methods.HttpUriRequest) r0     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r11 = r0
            byte r0 = r12.bizType     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r17 = r0
            r18 = 1
            r0 = r17
            r1 = r18
            if (r0 != r1) goto L_0x007b
            com.alipay.mobile.common.transport.config.TransportConfigureManager r17 = com.alipay.mobile.common.transport.config.TransportConfigureManager.getInstance()     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            com.alipay.mobile.common.transport.config.TransportConfigureItem r18 = com.alipay.mobile.common.transport.config.TransportConfigureItem.H2_RPC_GWHOST     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.lang.String r9 = r17.getStringValue(r18)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            org.apache.http.params.HttpParams r17 = r11.getParams()     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.lang.String r18 = "downgradeHttpsHost"
            r0 = r17
            r1 = r18
            r0.setParameter(r1, r9)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r17 = 0
            r18 = 0
            r0 = r17
            r1 = r18
            org.apache.http.client.methods.HttpUriRequest r11 = com.alipay.mobile.common.transport.utils.DownloadUtils.constructNewHttpUriRequest(r11, r0, r1)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            org.apache.http.HttpHost r15 = new org.apache.http.HttpHost     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.net.URI r17 = r11.getURI()     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.net.URL r17 = r17.toURL()     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.lang.String r17 = r17.getHost()     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.net.URI r18 = r11.getURI()     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            int r18 = r18.getPort()     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.net.URI r19 = r11.getURI()     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.lang.String r19 = r19.getScheme()     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r0 = r17
            r1 = r18
            r2 = r19
            r15.<init>(r0, r1, r2)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r22 = r15
        L_0x007b:
            r0 = r21
            java.net.HttpURLConnection r16 = r0.openConnection(r11, r12)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r0 = r21
            android.content.Context r0 = r0.b     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r17 = r0
            int r6 = com.alipay.mobile.common.transport.TransportStrategy.getConnTimeout(r17)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r0 = r16
            r0.setConnectTimeout(r6)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r0 = r21
            android.content.Context r0 = r0.b     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r17 = r0
            int r7 = com.alipay.mobile.common.transport.TransportStrategy.getReadTimeout(r17)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r0 = r16
            r0.setReadTimeout(r7)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.lang.String r17 = "AndroidH2UrlConnection"
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.lang.String r19 = "url: "
            r18.<init>(r19)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.net.URI r19 = r11.getURI()     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.net.URL r19 = r19.toURL()     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.lang.String r19 = r19.toString()     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.lang.String r19 = " ,connectTimeout: "
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r0 = r18
            java.lang.StringBuilder r18 = r0.append(r6)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.lang.String r19 = " ,readTimeout: "
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r0 = r18
            java.lang.StringBuilder r18 = r0.append(r7)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.lang.String r18 = r18.toString()     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r17, r18)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r17 = 0
            r16.setInstanceFollowRedirects(r17)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            c(r16)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r0 = r23
            r1 = r16
            a(r0, r1)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r0 = r23
            r1 = r16
            a(r12, r0, r1)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r0 = r23
            r1 = r16
            a(r0, r12, r1)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.lang.Object r5 = e(r16)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            org.apache.http.message.BasicHttpResponse r14 = d(r16)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            if (r5 != 0) goto L_0x0102
            java.lang.Object r5 = e(r16)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
        L_0x0102:
            r0 = r22
            r1 = r23
            r2 = r24
            com.alipay.mobile.common.transport.utils.HttpUtils.extractCookiesFromResponse(r0, r1, r14, r2)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.lang.String r17 = "X-Android-Selected-Protocol"
            r0 = r17
            org.apache.http.Header r10 = r14.getFirstHeader(r0)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.lang.String r13 = ""
            if (r10 == 0) goto L_0x012e
            java.lang.String r13 = r10.getValue()     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            boolean r17 = android.text.TextUtils.isEmpty(r13)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            if (r17 != 0) goto L_0x012e
            com.alipay.mobile.common.transport.monitor.DataContainer r17 = r12.getCurrentDataContainer()     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.lang.String r18 = "PROTOCOL"
            r0 = r17
            r1 = r18
            r0.putDataItem(r1, r13)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
        L_0x012e:
            java.lang.String r17 = "AndroidH2UrlConnection"
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.lang.String r19 = "[execute] urlconnection code:"
            r18.<init>(r19)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            int r19 = r16.getResponseCode()     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.lang.String r19 = ",protocol:"
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            boolean r19 = android.text.TextUtils.isEmpty(r13)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            if (r19 == 0) goto L_0x014d
            java.lang.String r13 = "null"
        L_0x014d:
            r0 = r18
            java.lang.StringBuilder r18 = r0.append(r13)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            java.lang.String r18 = r18.toString()     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r17, r18)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            com.alipay.mobile.common.transport.http.AndroidH2Watchdog r17 = com.alipay.mobile.common.transport.http.AndroidH2Watchdog.getInstance()     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            byte r0 = r12.bizType     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
            r18 = r0
            r17.resetFailCount(r18)     // Catch:{ RequestSwitchDirectionException -> 0x0166, Throwable -> 0x0168 }
        L_0x0165:
            return r14
        L_0x0166:
            r17 = move-exception
            throw r17
        L_0x0168:
            r8 = move-exception
            java.lang.String r17 = "AndroidH2UrlConnection"
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01ed }
            java.lang.String r19 = "[execute] ex:"
            r18.<init>(r19)     // Catch:{ Throwable -> 0x01ed }
            java.lang.String r19 = r8.toString()     // Catch:{ Throwable -> 0x01ed }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ Throwable -> 0x01ed }
            java.lang.String r18 = r18.toString()     // Catch:{ Throwable -> 0x01ed }
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r17, r18)     // Catch:{ Throwable -> 0x01ed }
            com.alipay.mobile.common.transport.http.AndroidH2Watchdog r17 = com.alipay.mobile.common.transport.http.AndroidH2Watchdog.getInstance()     // Catch:{ Throwable -> 0x01ed }
            byte r0 = r12.bizType     // Catch:{ Throwable -> 0x01ed }
            r18 = r0
            java.lang.String r19 = ""
            java.lang.String r20 = r8.getMessage()     // Catch:{ Throwable -> 0x01ed }
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r0.reportH2Error(r1, r2, r3, r8)     // Catch:{ Throwable -> 0x01ed }
            r16.disconnect()     // Catch:{ Throwable -> 0x01ed }
        L_0x019d:
            boolean r0 = r8 instanceof java.io.IOException     // Catch:{ Throwable -> 0x01d3 }
            r17 = r0
            if (r17 == 0) goto L_0x01a6
            c(r5)     // Catch:{ Throwable -> 0x01d3 }
        L_0x01a6:
            android.content.Context r17 = com.alipay.mobile.common.transport.utils.TransportEnvUtil.getContext()
            boolean r17 = com.alipay.mobile.common.transport.utils.NetworkUtils.isNetworkAvailable(r17)
            if (r17 != 0) goto L_0x01ba
            java.lang.String r17 = "AndroidH2UrlConnection"
            java.lang.String r18 = "[execute] AndroidH2UrlConnection. isNetworkAvailable == false "
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r17, r18)
            a(r8)
        L_0x01ba:
            com.alipay.mobile.common.transport.monitor.DataContainer r17 = r12.getCurrentDataContainer()
            java.lang.String r18 = "ERROR"
            java.lang.String r19 = r8.getMessage()
            r17.putDataItem(r18, r19)
            a(r8)
            java.lang.String r17 = "AndroidH2UrlConnection"
            java.lang.String r18 = "[execute] It's impossible to get here"
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r17, r18)
            r14 = 0
            goto L_0x0165
        L_0x01d3:
            r4 = move-exception
            java.lang.String r17 = "AndroidH2UrlConnection"
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            java.lang.String r19 = "[execute] closeSocket error. "
            r18.<init>(r19)
            java.lang.String r19 = r4.toString()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r17, r18)
            goto L_0x01a6
        L_0x01ed:
            r17 = move-exception
            goto L_0x019d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.http.AndroidH2UrlConnection.execute(org.apache.http.HttpHost, org.apache.http.HttpRequest, org.apache.http.protocol.HttpContext):org.apache.http.HttpResponse");
    }

    @TargetApi(9)
    private static void a(Throwable e) {
        if (e instanceof IOException) {
            throw ((IOException) e);
        }
        throw new IOException(e);
    }

    public HttpURLConnection openConnection(HttpUriRequest httpUriRequest, TransportContext netContext) {
        HttpURLConnection urlConnection = (HttpURLConnection) httpUriRequest.getURI().toURL().openConnection(obtainProxy(httpUriRequest));
        a(urlConnection);
        return urlConnection;
    }

    private void a(HttpURLConnection urlConnection) {
        try {
            Object objHttpURLConnectionImpl = b(urlConnection);
            Field fieldOkHttpClient = objHttpURLConnectionImpl.getClass().getDeclaredField("client");
            fieldOkHttpClient.setAccessible(true);
            Object objOkHttpClient = fieldOkHttpClient.get(objHttpURLConnectionImpl);
            a(objOkHttpClient);
            Method methodSetProtocols = objOkHttpClient.getClass().getDeclaredMethod("setProtocols", new Class[]{List.class});
            b(objOkHttpClient);
            if (VERSION.SDK_INT >= 23) {
                Field fieldDefaultProtocols = objOkHttpClient.getClass().getDeclaredField("DEFAULT_PROTOCOLS");
                fieldDefaultProtocols.setAccessible(true);
                methodSetProtocols.invoke(objOkHttpClient, new Object[]{fieldDefaultProtocols.get(objOkHttpClient.getClass())});
            }
            LogCatUtil.debug("AndroidH2UrlConnection", "modifyParamtersInUrlConnection success");
        } catch (Throwable ex) {
            LogCatUtil.error("AndroidH2UrlConnection", "modifyParamtersInUrlConnection ex:" + ex.toString(), ex);
        }
    }

    private static void a(Object objOkHttpClient) {
        try {
            Field fieldConnectionPool = objOkHttpClient.getClass().getDeclaredField("connectionPool");
            fieldConnectionPool.setAccessible(true);
            Object objectConnectionPool = fieldConnectionPool.get(objOkHttpClient);
            Class clazzConnectionPool = objectConnectionPool.getClass();
            Field fieldMaxIdleConnections = clazzConnectionPool.getDeclaredField("maxIdleConnections");
            fieldMaxIdleConnections.setAccessible(true);
            fieldMaxIdleConnections.set(objectConnectionPool, Integer.valueOf(19));
            Field fieldKeepAliveDurationNs = clazzConnectionPool.getDeclaredField("keepAliveDurationNs");
            fieldKeepAliveDurationNs.setAccessible(true);
            fieldKeepAliveDurationNs.set(objectConnectionPool, Long.valueOf(30000000000L));
        } catch (Throwable ex) {
            LogCatUtil.error((String) "AndroidH2UrlConnection", "hookConnectionPool ex:" + ex.toString());
        }
    }

    private static Object b(HttpURLConnection urlConnection) {
        Field fieldDelegate = urlConnection.getClass().getDeclaredField("delegate");
        fieldDelegate.setAccessible(true);
        return fieldDelegate.get(urlConnection);
    }

    private static void c(HttpURLConnection urlConnection) {
        try {
            if (urlConnection.getUseCaches()) {
                urlConnection.setUseCaches(false);
            }
        } catch (Exception e) {
            LogCatUtil.error((String) "AndroidH2UrlConnection", "setUseCaches2False exception=" + e.getMessage());
        }
    }

    private static void a(HttpRequest request, HttpURLConnection urlConnection) {
        if (request instanceof HttpRequestBase) {
            urlConnection.setRequestMethod(((HttpRequestBase) request).getMethod());
        } else {
            urlConnection.setRequestMethod(request.getRequestLine().getMethod());
        }
    }

    private static void a(TransportContext netContext, HttpRequest request, HttpURLConnection urlConnection) {
        b(netContext, request, urlConnection);
        HttpEntity entity = a(request);
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

    private static void b(TransportContext netContext, HttpRequest request, HttpURLConnection urlConnection) {
        int i = 0;
        if (VERSION.SDK_INT >= 26) {
            Header[] allHeaders = request.getAllHeaders();
            int length = allHeaders.length;
            while (i < length) {
                Header header = allHeaders[i];
                urlConnection.addRequestProperty(header.getName(), header.getValue());
                i++;
            }
        } else if (netContext.isRpcBizType()) {
            Header[] allHeaders2 = request.getAllHeaders();
            int length2 = allHeaders2.length;
            while (i < length2) {
                Header header2 = allHeaders2[i];
                urlConnection.setRequestProperty(header2.getName(), header2.getValue());
                i++;
            }
        } else {
            HashMap backupHeaders = new HashMap();
            Header[] allHeaders3 = request.getAllHeaders();
            int length3 = allHeaders3.length;
            while (i < length3) {
                Header header3 = allHeaders3[i];
                String headerKey = header3.getName();
                if (TextUtils.isEmpty(headerKey)) {
                    LogCatUtil.warn((String) "AndroidH2UrlConnection", (String) "O, headerKey is null.");
                } else {
                    String headerKey2 = headerKey.toLowerCase(Locale.US);
                    String value = (String) backupHeaders.get(headerKey2);
                    if (TextUtils.isEmpty(value)) {
                        urlConnection.addRequestProperty(header3.getName(), header3.getValue());
                        backupHeaders.put(headerKey2, header3.getValue());
                    } else if (!TextUtils.equals(value, header3.getValue())) {
                        String errMsg = "There is a duplicate header that needs to be switched to http/1.1 。key=[" + header3.getName() + "], value1=[" + value + "]、value2=[" + header3.getValue() + "].";
                        LogCatUtil.warn((String) "AndroidH2UrlConnection", errMsg);
                        throw new RequestSwitchDirectionException(errMsg);
                    }
                }
                i++;
            }
        }
    }

    private static HttpEntity a(HttpRequest request) {
        if (!(request instanceof HttpEntityEnclosingRequest)) {
            return null;
        }
        HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
        if (entity == null) {
            return null;
        }
        return entity;
    }

    private static void a(HttpRequest request, TransportContext netContext, HttpURLConnection urlConnection) {
        HttpEntity entity = a(request);
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

    private static BasicHttpResponse d(HttpURLConnection urlConnection) {
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

    /* access modifiers changed from: protected */
    public HttpUrlRequest getHttpUrlRequest(HttpContext context) {
        if (this.c != null) {
            return this.c;
        }
        HttpUrlRequest originHttpUrlRequest = null;
        try {
            originHttpUrlRequest = (HttpUrlRequest) context.getAttribute(TransportConstants.KEY_ORIGIN_REQUEST);
            this.c = originHttpUrlRequest;
            return originHttpUrlRequest;
        } catch (Throwable ex) {
            LogCatUtil.error((String) "AndroidH2UrlConnection", "HttpUrlRequest cast fail. " + ex.toString());
            return originHttpUrlRequest;
        }
    }

    public Proxy obtainProxy(HttpUriRequest httpUriRequest) {
        try {
            HttpHost host = (HttpHost) httpUriRequest.getParams().getParameter("http.route.default-proxy");
            Proxy proxy = null;
            if (!(host == null || ConnRouteParams.NO_HOST == host)) {
                proxy = new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(host.getHostName(), host.getPort()));
            }
            if (proxy != null) {
                return proxy;
            }
            return Proxy.NO_PROXY;
        } catch (Throwable e) {
            LogCatUtil.warn("AndroidH2UrlConnection", "obtainProxy fail", e);
            return Proxy.NO_PROXY;
        }
    }

    private void b(Object objOkHttpClient) {
        Field networkField;
        if (objOkHttpClient == null) {
            LogCatUtil.warn((String) "AndroidH2UrlConnection", (String) "objOkHttpClient is null");
            return;
        }
        try {
            if (VERSION.SDK_INT >= 26) {
                networkField = objOkHttpClient.getClass().getDeclaredField(BaseMonitor.COUNT_POINT_DNS);
            } else {
                networkField = objOkHttpClient.getClass().getDeclaredField("network");
            }
            if (networkField == null) {
                LogCatUtil.info("AndroidH2UrlConnection", "network field is null, return.");
                return;
            }
            networkField.setAccessible(true);
            if (this.d != null) {
                networkField.set(objOkHttpClient, this.d);
                return;
            }
            synchronized (AndroidH2UrlConnection.class) {
                if (this.d != null) {
                    networkField.set(objOkHttpClient, this.d);
                    return;
                }
                Object rawNetworkObj = networkField.get(objOkHttpClient);
                if (rawNetworkObj == null) {
                    LogCatUtil.info("AndroidH2UrlConnection", "Raw network is null, return.");
                    return;
                }
                this.d = java.lang.reflect.Proxy.newProxyInstance(networkField.getType().getClassLoader(), new Class[]{networkField.getType()}, AndroidH2DnsHandler.getInstance());
                AndroidH2DnsHandler.getInstance().setRawAndroidH2DnsHandler(rawNetworkObj);
                networkField.set(objOkHttpClient, this.d);
            }
        } catch (Throwable e) {
            LogCatUtil.error((String) "AndroidH2UrlConnection", "hookH2Dns fail, error=[" + e.toString() + "]");
        }
    }

    private static void c(Object connection) {
        if (connection == null) {
            LogCatUtil.warn((String) "AndroidH2UrlConnection", (String) "[closeSocket] connection is null.");
            return;
        }
        try {
            Field socketField = connection.getClass().getDeclaredField(ModuleSocket.MODULE_NAME);
            socketField.setAccessible(true);
            Socket socket = (Socket) socketField.get(connection);
            if (socket == null || socket.isClosed()) {
                LogCatUtil.warn((String) "AndroidH2UrlConnection", "[closeSocket] Socket " + (socket == null ? "it's null." : "closed."));
                return;
            }
            socket.close();
            LogCatUtil.info("AndroidH2UrlConnection", "[closeSocket] closeSocket success.");
        } catch (Throwable e) {
            Throwable tmpThrowable = e;
            if (e instanceof InvocationTargetException) {
                tmpThrowable = ((InvocationTargetException) e).getTargetException();
            }
            LogCatUtil.warn("AndroidH2UrlConnection", " closeSocket failed. ", tmpThrowable);
        }
    }

    private static Object e(HttpURLConnection httpURLConnection) {
        Object connection;
        try {
            Object httpURLConnectionImpl = b(httpURLConnection);
            if (httpURLConnectionImpl == null) {
                return null;
            }
            Field httpEngineField = httpURLConnectionImpl.getClass().getDeclaredField("httpEngine");
            httpEngineField.setAccessible(true);
            Object httpEngin = httpEngineField.get(httpURLConnectionImpl);
            if (httpEngin == null) {
                return null;
            }
            if (VERSION.SDK_INT >= 26) {
                Field httpStreamField = httpEngin.getClass().getDeclaredField("httpStream");
                httpStreamField.setAccessible(true);
                Object http2xStreamObj = httpStreamField.get(httpEngin);
                Field framedConnectionField = http2xStreamObj.getClass().getDeclaredField("framedConnection");
                framedConnectionField.setAccessible(true);
                connection = framedConnectionField.get(http2xStreamObj);
            } else {
                Field connectionField = httpEngin.getClass().getDeclaredField("connection");
                connectionField.setAccessible(true);
                connection = connectionField.get(httpEngin);
            }
            if (connection != null) {
                return connection;
            }
            LogCatUtil.warn((String) "AndroidH2UrlConnection", (String) "[getCurrentConnection] connection is null.");
            return null;
        } catch (Throwable e) {
            LogCatUtil.warn((String) "AndroidH2UrlConnection", "getCurrentConnection failed. errmsg: " + e.toString());
            return null;
        }
    }
}
