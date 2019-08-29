package com.alipay.mobile.common.logging.http;

import android.content.Context;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.http.AndroidHttpClient;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.common.logging.api.http.BaseHttpClient;
import com.alipay.mobile.common.logging.util.NetUtil;
import com.alipay.mobile.common.logging.util.ZipUtil;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class HttpClient extends BaseHttpClient {
    private static AndroidHttpClient a;
    private Context b;
    private String c;
    private HttpRequest d;
    private HttpResponse e;
    private long f = -1;
    private long g = -1;

    public HttpClient(String url, Context context) {
        super(url, context);
        this.b = context;
        this.c = url;
    }

    public void setUrl(String url) {
        this.c = url;
    }

    public void setContext(Context context) {
        this.b = context;
    }

    public long getRequestLength() {
        if (this.f > 0) {
            return this.f;
        }
        if (this.d instanceof HttpPost) {
            try {
                HttpEntity httpEntity = this.d.getEntity();
                if (httpEntity != null) {
                    return httpEntity.getContentLength();
                }
            } catch (Throwable t) {
                Log.w("LogHttpClient", t);
            }
        }
        return -1;
    }

    public int getResponseCode() {
        if (this.e != null) {
            try {
                StatusLine status = this.e.getStatusLine();
                if (status != null) {
                    return status.getStatusCode();
                }
            } catch (Throwable t) {
                Log.w("LogHttpClient", t);
            }
        }
        return -1;
    }

    public String getResponseContent() {
        if (this.e != null) {
            try {
                HttpEntity entity = this.e.getEntity();
                if (a(this.e)) {
                    byte[] data = ZipUtil.unCompressGzip(this.e.getEntity().getContent());
                    if (data != null) {
                        this.g = (long) data.length;
                        return new String(data, "UTF-8");
                    }
                } else if (entity != null) {
                    return EntityUtils.toString(entity);
                }
            } catch (Throwable t) {
                Log.w("LogHttpClient", t);
            }
        }
        return null;
    }

    private static boolean a(HttpResponse httpResponse) {
        if (httpResponse == null) {
            return false;
        }
        Header[] headers = httpResponse.getHeaders(TransportConstants.KEY_X_CONTENT_ENCODING);
        if (headers == null || headers.length <= 0) {
            return false;
        }
        String value = headers[0].getValue();
        if (value == null || value.toLowerCase().indexOf("gzip") < 0) {
            return false;
        }
        return true;
    }

    public long getResponseLength() {
        if (this.g > 0) {
            return this.g;
        }
        if (this.e != null) {
            try {
                HttpEntity entity = this.e.getEntity();
                if (entity != null) {
                    return entity.getContentLength();
                }
            } catch (Throwable t) {
                Log.w("LogHttpClient", t);
            }
        }
        return -1;
    }

    private URL a() {
        if (TextUtils.isEmpty(this.c)) {
            return null;
        }
        try {
            return new URL(this.c);
        } catch (Throwable e2) {
            Log.w("LogHttpClient", e2);
            return null;
        }
    }

    private HttpHost b() {
        URL url = a();
        if (url == null) {
            return null;
        }
        String host = url.getHost();
        if (TextUtils.isEmpty(host)) {
            return null;
        }
        String protocol = url.getProtocol();
        if (TextUtils.isEmpty(protocol)) {
            return null;
        }
        int port = url.getPort();
        if (port <= 0) {
            port = "https".equalsIgnoreCase(protocol) ? 443 : 80;
        }
        try {
            return new HttpHost(host, port, protocol);
        } catch (Throwable e2) {
            Log.w("LogHttpClient", e2);
            return null;
        }
    }

    private HttpHost c() {
        NetworkInfo network = NetUtil.getActiveNetworkInfo(this.b);
        if (network != null && network.isAvailable() && network.getType() == 0) {
            try {
                String host = Proxy.getDefaultHost();
                if (!TextUtils.isEmpty(host)) {
                    return new HttpHost(host, Proxy.getDefaultPort());
                }
            } catch (Throwable e2) {
                Log.w("LogHttpClient", e2);
            }
        }
        return null;
    }

    public void closeStreamForNextExecute() {
        this.f = -1;
        this.g = -1;
        if (this.d != null) {
            try {
                if (this.d instanceof HttpGet) {
                    this.d.abort();
                } else if (this.d instanceof HttpPost) {
                    this.d.abort();
                }
            } catch (Throwable th) {
            }
            this.d = null;
        }
        if (this.e != null) {
            try {
                HttpEntity entity = this.e.getEntity();
                if (entity != null) {
                    InputStream stream = entity.getContent();
                    if (stream != null) {
                        stream.close();
                    }
                }
            } catch (Throwable th2) {
            }
            this.e = null;
        }
    }

    public HttpResponse synchronousRequestByGET(Map<String, String> paramData) {
        closeStreamForNextExecute();
        try {
            String params = NetUtil.formatParamStringForGET(paramData);
            this.d = new HttpGet(TextUtils.isEmpty(params) ? this.c : this.c + '?' + params);
            this.d.addHeader("Content-type", "text/xml");
            this.d.addHeader("Accept-Encoding", "gzip");
            d();
            this.e = a.execute(b(), this.d);
            return this.e;
        } catch (Throwable e2) {
            closeStreamForNextExecute();
            throw new IllegalStateException(e2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0027 A[Catch:{ NullPointerException -> 0x0073, Throwable -> 0x003f }, LOOP:0: B:10:0x0021->B:12:0x0027, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.http.HttpResponse synchronousRequestByPOST(byte[] r11, java.util.Map<java.lang.String, java.lang.String> r12) {
        /*
            r10 = this;
            r10.closeStreamForNextExecute()
            if (r11 == 0) goto L_0x0008
            int r7 = r11.length     // Catch:{ Throwable -> 0x003f }
            if (r7 != 0) goto L_0x0049
        L_0x0008:
            org.apache.http.client.methods.HttpGet r7 = new org.apache.http.client.methods.HttpGet     // Catch:{ Throwable -> 0x003f }
            java.lang.String r8 = r10.c     // Catch:{ Throwable -> 0x003f }
            r7.<init>(r8)     // Catch:{ Throwable -> 0x003f }
            r10.d = r7     // Catch:{ Throwable -> 0x003f }
        L_0x0011:
            if (r12 == 0) goto L_0x005f
            int r7 = r12.size()     // Catch:{ Throwable -> 0x003f }
            if (r7 <= 0) goto L_0x005f
            java.util.Set r7 = r12.entrySet()     // Catch:{ Throwable -> 0x003f }
            java.util.Iterator r7 = r7.iterator()     // Catch:{ Throwable -> 0x003f }
        L_0x0021:
            boolean r8 = r7.hasNext()     // Catch:{ Throwable -> 0x003f }
            if (r8 == 0) goto L_0x005f
            java.lang.Object r1 = r7.next()     // Catch:{ Throwable -> 0x003f }
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch:{ Throwable -> 0x003f }
            java.lang.Object r3 = r1.getKey()     // Catch:{ Throwable -> 0x003f }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ Throwable -> 0x003f }
            java.lang.Object r5 = r1.getValue()     // Catch:{ Throwable -> 0x003f }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Throwable -> 0x003f }
            org.apache.http.HttpRequest r8 = r10.d     // Catch:{ Throwable -> 0x003f }
            r8.addHeader(r3, r5)     // Catch:{ Throwable -> 0x003f }
            goto L_0x0021
        L_0x003f:
            r0 = move-exception
            r10.closeStreamForNextExecute()
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            r7.<init>(r0)
            throw r7
        L_0x0049:
            org.apache.http.client.methods.HttpPost r2 = new org.apache.http.client.methods.HttpPost     // Catch:{ Throwable -> 0x003f }
            java.lang.String r7 = r10.c     // Catch:{ Throwable -> 0x003f }
            r2.<init>(r7)     // Catch:{ Throwable -> 0x003f }
            org.apache.http.entity.ByteArrayEntity r6 = new org.apache.http.entity.ByteArrayEntity     // Catch:{ Throwable -> 0x003f }
            r6.<init>(r11)     // Catch:{ Throwable -> 0x003f }
            r2.setEntity(r6)     // Catch:{ Throwable -> 0x003f }
            int r7 = r11.length     // Catch:{ Throwable -> 0x003f }
            long r7 = (long) r7     // Catch:{ Throwable -> 0x003f }
            r10.f = r7     // Catch:{ Throwable -> 0x003f }
            r10.d = r2     // Catch:{ Throwable -> 0x003f }
            goto L_0x0011
        L_0x005f:
            r10.d()     // Catch:{ Throwable -> 0x003f }
            org.apache.http.HttpHost r4 = r10.b()     // Catch:{ Throwable -> 0x003f }
            android.net.http.AndroidHttpClient r7 = a     // Catch:{ NullPointerException -> 0x0073 }
            org.apache.http.HttpRequest r8 = r10.d     // Catch:{ NullPointerException -> 0x0073 }
            org.apache.http.HttpResponse r7 = r7.execute(r4, r8)     // Catch:{ NullPointerException -> 0x0073 }
            r10.e = r7     // Catch:{ NullPointerException -> 0x0073 }
        L_0x0070:
            org.apache.http.HttpResponse r7 = r10.e
            return r7
        L_0x0073:
            r7 = move-exception
            android.net.http.AndroidHttpClient r7 = a     // Catch:{ Throwable -> 0x003f }
            org.apache.http.HttpRequest r8 = r10.d     // Catch:{ Throwable -> 0x003f }
            org.apache.http.protocol.BasicHttpContext r9 = new org.apache.http.protocol.BasicHttpContext     // Catch:{ Throwable -> 0x003f }
            r9.<init>()     // Catch:{ Throwable -> 0x003f }
            org.apache.http.HttpResponse r7 = r7.execute(r4, r8, r9)     // Catch:{ Throwable -> 0x003f }
            r10.e = r7     // Catch:{ Throwable -> 0x003f }
            goto L_0x0070
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.http.HttpClient.synchronousRequestByPOST(byte[], java.util.Map):org.apache.http.HttpResponse");
    }

    private void d() {
        if (a == null) {
            synchronized (HttpClient.class) {
                if (a == null) {
                    try {
                        AndroidHttpClient newInstance = AndroidHttpClient.newInstance(BehavorReporter.PROVIDE_BY_ALIPAY, this.b);
                        a = newInstance;
                        HttpParams params = newInstance.getParams();
                        if (params != null) {
                            params.setParameter("http.connection.timeout", Integer.valueOf(30000));
                            params.setParameter("http.socket.timeout", Integer.valueOf(300000));
                        }
                    } catch (Throwable t) {
                        Log.w("LogHttpClient", t);
                    }
                }
            }
        }
        if (a != null) {
            try {
                HttpParams params2 = a.getParams();
                if (params2 != null) {
                    params2.setParameter("http.route.default-proxy", c());
                }
            } catch (Throwable t2) {
                Log.w("LogHttpClient", t2);
            }
        }
    }
}
