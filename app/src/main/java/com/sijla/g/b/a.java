package com.sijla.g.b;

import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.autonavi.link.protocol.http.MultipartUtility;
import com.sijla.g.a.e;
import com.sijla.g.b;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.TrustManagerFactory;
import org.json.JSONObject;

public class a {
    private static volatile a b;
    private boolean a = true;

    private a() {
    }

    public static a a() {
        if (b == null) {
            synchronized (a.class) {
                try {
                    if (b == null) {
                        b = new a();
                    }
                }
            }
        }
        return b;
    }

    public com.sijla.g.b.a.a a(String str, JSONObject jSONObject, com.sijla.g.b.b.a aVar) {
        com.sijla.g.b.a.a aVar2;
        com.sijla.g.b.a.a aVar3 = new com.sijla.g.b.a.a();
        if (!this.a) {
            return aVar3;
        }
        HttpURLConnection httpURLConnection = null;
        if (jSONObject != null) {
            try {
                if (jSONObject.length() > 0) {
                    str = b(str, jSONObject);
                }
            } catch (Exception e) {
                e = e;
                try {
                    aVar3.a(400);
                    aVar3.a((Object) e.getMessage());
                    b.a(httpURLConnection);
                    aVar2 = aVar3;
                    return aVar2;
                } catch (Throwable th) {
                    th = th;
                    b.a(httpURLConnection);
                    throw th;
                }
            }
        }
        HttpURLConnection a2 = a(str);
        try {
            a(a2, (String) "get", (byte[]) null);
            aVar2 = a(a2.getResponseCode(), a2, aVar);
            b.a(a2);
        } catch (Exception e2) {
            e = e2;
            httpURLConnection = a2;
            aVar3.a(400);
            aVar3.a((Object) e.getMessage());
            b.a(httpURLConnection);
            aVar2 = aVar3;
            return aVar2;
        } catch (Throwable th2) {
            httpURLConnection = a2;
            th = th2;
            b.a(httpURLConnection);
            throw th;
        }
        return aVar2;
    }

    public com.sijla.g.b.a.a a(String str, JSONObject jSONObject) {
        return a(str, a(jSONObject).toString().getBytes(), (com.sijla.g.b.b.a) new com.sijla.g.b.b.a.b());
    }

    public com.sijla.g.b.a.a b(String str, JSONObject jSONObject, com.sijla.g.b.b.a aVar) {
        return a(str, a(jSONObject).toString().getBytes(), aVar);
    }

    public com.sijla.g.b.a.a a(String str, byte[] bArr, com.sijla.g.b.b.a aVar) {
        HttpURLConnection httpURLConnection;
        com.sijla.g.b.a.a aVar2;
        OutputStream outputStream;
        com.sijla.g.b.a.a aVar3 = new com.sijla.g.b.a.a();
        if (!this.a) {
            return aVar3;
        }
        OutputStream outputStream2 = null;
        try {
            httpURLConnection = a(str);
            try {
                a(httpURLConnection, (String) "post", bArr);
                outputStream = httpURLConnection.getOutputStream();
            } catch (Exception e) {
                e = e;
                try {
                    aVar3.a(400);
                    aVar3.a((Object) e.getMessage());
                    b.a(outputStream2);
                    b.a(httpURLConnection);
                    aVar2 = aVar3;
                    return aVar2;
                } catch (Throwable th) {
                    th = th;
                    b.a(outputStream2);
                    b.a(httpURLConnection);
                    throw th;
                }
            }
            try {
                outputStream.write(bArr);
                outputStream.flush();
                aVar2 = a(httpURLConnection.getResponseCode(), httpURLConnection, aVar);
                b.a(outputStream);
                b.a(httpURLConnection);
            } catch (Exception e2) {
                e = e2;
                outputStream2 = outputStream;
                aVar3.a(400);
                aVar3.a((Object) e.getMessage());
                b.a(outputStream2);
                b.a(httpURLConnection);
                aVar2 = aVar3;
                return aVar2;
            } catch (Throwable th2) {
                th = th2;
                outputStream2 = outputStream;
                b.a(outputStream2);
                b.a(httpURLConnection);
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            httpURLConnection = null;
            aVar3.a(400);
            aVar3.a((Object) e.getMessage());
            b.a(outputStream2);
            b.a(httpURLConnection);
            aVar2 = aVar3;
            return aVar2;
        } catch (Throwable th3) {
            th = th3;
            httpURLConnection = null;
            b.a(outputStream2);
            b.a(httpURLConnection);
            throw th;
        }
        return aVar2;
    }

    public com.sijla.g.b.a.a a(String str, JSONObject jSONObject, Map<String, File> map) {
        return a(str, jSONObject, map, new com.sijla.g.b.b.a.b());
    }

    public com.sijla.g.b.a.a a(String str, JSONObject jSONObject, Map<String, File> map, com.sijla.g.b.b.a aVar) {
        DataOutputStream dataOutputStream;
        HttpURLConnection httpURLConnection;
        char c;
        Throwable th;
        int i;
        com.sijla.g.b.a.a aVar2;
        Exception exc;
        JSONObject jSONObject2 = jSONObject;
        com.sijla.g.b.a.a aVar3 = new com.sijla.g.b.a.a();
        if (!this.a) {
            return aVar3;
        }
        String uuid = UUID.randomUUID().toString();
        DataOutputStream dataOutputStream2 = null;
        try {
            httpURLConnection = a(str);
            try {
                httpURLConnection.setReadTimeout(6000);
                httpURLConnection.setConnectTimeout(6000);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty(H5AppHttpRequest.HEADER_CONNECTION, "Keep-Alive");
                httpURLConnection.setRequestProperty("Charset", "UTF-8");
                httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=".concat(String.valueOf(uuid)));
                dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                if (jSONObject2 != null) {
                    try {
                        StringBuilder sb = new StringBuilder();
                        Iterator<String> keys = jSONObject.keys();
                        while (keys.hasNext()) {
                            String next = keys.next();
                            String optString = jSONObject2.optString(next);
                            sb.append("--");
                            sb.append(uuid);
                            sb.append(MultipartUtility.LINE_FEED);
                            StringBuilder sb2 = new StringBuilder("Content-Disposition: form-data; name=\"");
                            sb2.append(next);
                            sb2.append("\"");
                            sb2.append(MultipartUtility.LINE_FEED);
                            sb.append(sb2.toString());
                            StringBuilder sb3 = new StringBuilder("Content-Type: text/plain; charset=");
                            sb3.append("UTF-8");
                            sb3.append(MultipartUtility.LINE_FEED);
                            sb.append(sb3.toString());
                            sb.append("Content-Transfer-Encoding: 8bit".concat(String.valueOf(MultipartUtility.LINE_FEED)));
                            sb.append(MultipartUtility.LINE_FEED);
                            sb.append(optString);
                            sb.append(MultipartUtility.LINE_FEED);
                        }
                        dataOutputStream.write(sb.toString().getBytes());
                    } catch (Exception e) {
                        exc = e;
                        dataOutputStream2 = dataOutputStream;
                        try {
                            aVar3.a(400);
                            aVar3.a((Object) exc.getMessage());
                            b.a(dataOutputStream2);
                            b.a(httpURLConnection);
                            aVar2 = aVar3;
                            return aVar2;
                        } catch (Throwable th2) {
                            i = 1;
                            c = 0;
                            th = th2;
                            dataOutputStream = dataOutputStream2;
                            Closeable[] closeableArr = new Closeable[i];
                            closeableArr[c] = dataOutputStream;
                            b.a(closeableArr);
                            HttpURLConnection[] httpURLConnectionArr = new HttpURLConnection[i];
                            httpURLConnectionArr[c] = httpURLConnection;
                            b.a(httpURLConnectionArr);
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        i = 1;
                        c = 0;
                        Closeable[] closeableArr2 = new Closeable[i];
                        closeableArr2[c] = dataOutputStream;
                        b.a(closeableArr2);
                        HttpURLConnection[] httpURLConnectionArr2 = new HttpURLConnection[i];
                        httpURLConnectionArr2[c] = httpURLConnection;
                        b.a(httpURLConnectionArr2);
                        throw th;
                    }
                }
                if (map != null) {
                    for (Entry next2 : map.entrySet()) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("--");
                        sb4.append(uuid);
                        sb4.append(MultipartUtility.LINE_FEED);
                        StringBuilder sb5 = new StringBuilder("Content-Disposition: form-data; name=\"file\"; filename=\"");
                        sb5.append((String) next2.getKey());
                        sb5.append("\"");
                        sb5.append(MultipartUtility.LINE_FEED);
                        sb4.append(sb5.toString());
                        StringBuilder sb6 = new StringBuilder("Content-Type: application/octet-stream; charset=");
                        sb6.append("UTF-8");
                        sb6.append(MultipartUtility.LINE_FEED);
                        sb4.append(sb6.toString());
                        sb4.append(MultipartUtility.LINE_FEED);
                        dataOutputStream.write(sb4.toString().getBytes());
                        FileInputStream fileInputStream = new FileInputStream((File) next2.getValue());
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int read = fileInputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            dataOutputStream.write(bArr, 0, read);
                        }
                        fileInputStream.close();
                        dataOutputStream.write(MultipartUtility.LINE_FEED.getBytes());
                    }
                }
                StringBuilder sb7 = new StringBuilder();
                sb7.append("--");
                sb7.append(uuid);
                sb7.append("--");
                sb7.append(MultipartUtility.LINE_FEED);
                dataOutputStream.write(sb7.toString().getBytes());
                dataOutputStream.flush();
                aVar2 = a(httpURLConnection.getResponseCode(), httpURLConnection, aVar);
                b.a(dataOutputStream);
                b.a(httpURLConnection);
            } catch (Exception e2) {
                exc = e2;
                aVar3.a(400);
                aVar3.a((Object) exc.getMessage());
                b.a(dataOutputStream2);
                b.a(httpURLConnection);
                aVar2 = aVar3;
                return aVar2;
            } catch (Throwable th4) {
                th = th4;
                dataOutputStream = null;
                i = 1;
                c = 0;
                Closeable[] closeableArr22 = new Closeable[i];
                closeableArr22[c] = dataOutputStream;
                b.a(closeableArr22);
                HttpURLConnection[] httpURLConnectionArr22 = new HttpURLConnection[i];
                httpURLConnectionArr22[c] = httpURLConnection;
                b.a(httpURLConnectionArr22);
                throw th;
            }
        } catch (Exception e3) {
            exc = e3;
            httpURLConnection = null;
            aVar3.a(400);
            aVar3.a((Object) exc.getMessage());
            b.a(dataOutputStream2);
            b.a(httpURLConnection);
            aVar2 = aVar3;
            return aVar2;
        } catch (Throwable th5) {
            th = th5;
            httpURLConnection = null;
            dataOutputStream = null;
            i = 1;
            c = 0;
            Closeable[] closeableArr222 = new Closeable[i];
            closeableArr222[c] = dataOutputStream;
            b.a(closeableArr222);
            HttpURLConnection[] httpURLConnectionArr222 = new HttpURLConnection[i];
            httpURLConnectionArr222[c] = httpURLConnection;
            b.a(httpURLConnectionArr222);
            throw th;
        }
        return aVar2;
    }

    private com.sijla.g.b.a.a a(int i, HttpURLConnection httpURLConnection, com.sijla.g.b.b.a aVar) {
        Closeable[] closeableArr;
        InputStream errorStream;
        com.sijla.g.b.a.a a2;
        com.sijla.g.b.a.a aVar2 = new com.sijla.g.b.a.a();
        InputStream inputStream = null;
        if (200 == i) {
            try {
                errorStream = httpURLConnection.getInputStream();
                try {
                    a2 = aVar.a(i, errorStream);
                } catch (IOException e) {
                    e = e;
                    inputStream = errorStream;
                    try {
                        e.printStackTrace();
                        aVar2.a(414);
                        closeableArr = new Closeable[]{inputStream};
                        b.a(closeableArr);
                        return aVar2;
                    } catch (Throwable th) {
                        th = th;
                        b.a(inputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    inputStream = errorStream;
                    b.a(inputStream);
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
                e.printStackTrace();
                aVar2.a(414);
                closeableArr = new Closeable[]{inputStream};
                b.a(closeableArr);
                return aVar2;
            }
        } else {
            if (204 != i) {
                errorStream = httpURLConnection.getErrorStream();
                a2 = new com.sijla.g.b.b.a.b().a(i, errorStream);
            }
            aVar2.a(i);
            closeableArr = new Closeable[]{inputStream};
            b.a(closeableArr);
            return aVar2;
        }
        inputStream = errorStream;
        aVar2 = a2;
        aVar2.a(i);
        closeableArr = new Closeable[]{inputStream};
        b.a(closeableArr);
        return aVar2;
    }

    public void a(boolean z) {
        this.a = z;
    }

    public boolean b() {
        return this.a;
    }

    public StringBuffer a(JSONObject jSONObject) {
        StringBuffer stringBuffer = new StringBuffer();
        boolean z = true;
        try {
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                if (z) {
                    z = false;
                } else {
                    stringBuffer.append("&");
                }
                String next = keys.next();
                stringBuffer.append(next);
                stringBuffer.append("=");
                stringBuffer.append(URLEncoder.encode(jSONObject.optString(next), "UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }

    private String b(String str, JSONObject jSONObject) {
        String stringBuffer = a(jSONObject).toString();
        if (str.contains("?")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("&");
            sb.append(stringBuffer);
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append("?");
        sb2.append(stringBuffer);
        return sb2.toString();
    }

    private void a(HttpURLConnection httpURLConnection, String str, byte[] bArr) {
        httpURLConnection.setReadTimeout(6000);
        httpURLConnection.setConnectTimeout(6000);
        httpURLConnection.setRequestProperty("Qt-v", String.valueOf(com.sijla.b.a.a));
        if (str.toUpperCase().equals("POST")) {
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(bArr.length));
        }
    }

    public HttpURLConnection a(String str) {
        URL url = new URL(str);
        try {
            if (!"https".equalsIgnoreCase(url.getProtocol())) {
                return (HttpURLConnection) url.openConnection();
            }
            com.sijla.g.b.c.a a2 = com.sijla.g.b.c.a.a();
            a2.a((String) "TLS");
            InputStream[] c = c();
            if (c != null) {
                TrustManagerFactory a3 = a(c);
                if (a3 != null) {
                    a2.a(a3.getTrustManagers());
                }
            }
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setSSLSocketFactory(a2.b());
            httpsURLConnection.setHostnameVerifier(new com.sijla.g.b.c.b());
            return httpsURLConnection;
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        } catch (IOException unused) {
            return (HttpURLConnection) url.openConnection();
        }
    }

    public TrustManagerFactory a(InputStream... inputStreamArr) {
        try {
            CertificateFactory instance = CertificateFactory.getInstance("X.509");
            KeyStore instance2 = KeyStore.getInstance(KeyStore.getDefaultType());
            instance2.load(null);
            int length = inputStreamArr.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                InputStream inputStream = inputStreamArr[i];
                int i3 = i2 + 1;
                instance2.setCertificateEntry(Integer.toString(i2), instance.generateCertificate(inputStream));
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException unused) {
                    }
                }
                i++;
                i2 = i3;
            }
            TrustManagerFactory instance3 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            instance3.init(instance2);
            return instance3;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private InputStream[] c() {
        String[] strArr = {"-----BEGIN CERTIFICATE-----\nMIIEbzCCA1egAwIBAgIDAjpzMA0GCSqGSIb3DQEBCwUAMEIxCzAJBgNVBAYTAlVT\nMRYwFAYDVQQKEw1HZW9UcnVzdCBJbmMuMRswGQYDVQQDExJHZW9UcnVzdCBHbG9i\nYWwgQ0EwHhcNMTQwNjExMjIwMjU5WhcNMjIwNTIwMjIwMjU5WjBmMQswCQYDVQQG\nEwJVUzEWMBQGA1UEChMNR2VvVHJ1c3QgSW5jLjEdMBsGA1UECxMURG9tYWluIFZh\nbGlkYXRlZCBTU0wxIDAeBgNVBAMTF0dlb1RydXN0IERWIFNTTCBDQSAtIEczMIIB\nIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs0Q6bLCuyxT5jBl0NFypaeOI\nU3elp/+90TwNJ+TerX+80ZBYk9am2jmcreEOVkbulZ4QaEycK/ZqOouAgYcGVyUa\nVlKU3ZDrZzve+q42aNNiafZsgiRET4dcmBGVZGvoDNHd5ieXrszikWpBErar5cxu\nzCO4Y4ofMZMtBsT36D1YzZcIRmx7dMD4/DE7p3/Xj7DJFWNQehJN9RIeo35V43W3\n6h7qMSwITtjLQ3SJJLzSDh7w2wUk9oq/ECeEQRr2GFPukdBUF9N9Pn6yfai/27kh\nKvCJuQhuWrNe6oK4ficLzFZzgQVP45YtcdV4p2DD1+yqORoFOYKB4BUsNdHuJQID\nAQABo4IBSDCCAUQwHwYDVR0jBBgwFoAUwHqYaI2J+6sFZAwRfap9ZbjKzE4wHQYD\nVR0OBBYEFK1lIoWQ0DvjoUmLN/nxCx1fF6B3MBIGA1UdEwEB/wQIMAYBAf8CAQAw\nDgYDVR0PAQH/BAQDAgEGMDUGA1UdHwQuMCwwKqAooCaGJGh0dHA6Ly9nLnN5bWNi\nLmNvbS9jcmxzL2d0Z2xvYmFsLmNybDAuBggrBgEFBQcBAQQiMCAwHgYIKwYBBQUH\nMAGGEmh0dHA6Ly9nLnN5bWNkLmNvbTBMBgNVHSAERTBDMEEGCmCGSAGG+EUBBzYw\nMzAxBggrBgEFBQcCARYlaHR0cDovL3d3dy5nZW90cnVzdC5jb20vcmVzb3VyY2Vz\nL2NwczApBgNVHREEIjAgpB4wHDEaMBgGA1UEAxMRU3ltYW50ZWNQS0ktMS02OTkw\nDQYJKoZIhvcNAQELBQADggEBAE4nuBrHO9xdu54aNSMeiFWQ0eyGnIi34B9nh+J8\ntUMDDrYC6OD/hoQZcenyS/WeLi5e26vWHE7EPrgseIZxEK6NxXC/pPmJ5rTt6Evt\nfAkqCQgGPtTh3oKSDDQwNQrBYHXKtlVrqgBCyz/7EOH7hcEhkHIrbsDondm1WlCO\nNB67OKc8Mb168kOL6xbKrZveax74T7ZeSikfehTukfSUT6S9m3Z6vPFRepaogQ6D\nhz+Lrl4ymzSesufbL+wCoOH9UVL+LNs2usHWXktYbd7G4eH6mgMsW6Lhs5v5NuzB\nc/ozEmaV42kQtteqM/r2nUFtliq6voMxQX8MCtJp1vw1TMM=\n-----END CERTIFICATE-----", "-----BEGIN CERTIFICATE-----\nMIIEtjCCA56gAwIBAgIQDCdXKjCbfeuWt9IzhkVAAzANBgkqhkiG9w0BAQsFADBe\nMQswCQYDVQQGEwJVUzEVMBMGA1UEChMMRGlnaUNlcnQgSW5jMRkwFwYDVQQLExB3\nd3cuZGlnaWNlcnQuY29tMR0wGwYDVQQDExRHZW9UcnVzdCBSU0EgQ0EgMjAxODAe\nFw0xODAxMzEwMDAwMDBaFw0xOTEyMTQxMjAwMDBaMBwxGjAYBgNVBAMTEXd3dy5x\nY2hhbm5lbDAxLmNuMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3xwM\nr3n8r6enystnDnmi5pMSTAaqGA0gbB2HuhxsJeHx+ZgqQ/KSatyvN2MXKlNdkHyR\nLhkJPzkfd0uU6qlMWmzlL7x4MCnMqC8P3w0jX54J9Gtyk8aFVEBVx7eNsN35YD+J\no9mOe3R9ZD6CSkPY3GhUQhzdm2TFbYKrnQnhZElFtfC7DEcBMtswI6qQI0SGoLZ4\nmR4/6PSPUd9Y8SWa6LXZslqBwU8B+/frKfNiSnN5SzaN5e/t3bfwp27xpiaiE5s0\nSMYRZ9m25qh7PUqu6/OtW0lhc6fn4zPcm2DVwfLHvOgQ8SpDbTzzTRgfHPwZPnOk\nzqFKZVkvKCmFs8w4IwIDAQABo4IBsDCCAawwHwYDVR0jBBgwFoAUkFj/sJx1qFFU\nd7Ht8qNDFjiebMUwHQYDVR0OBBYEFGk3ScCTvRdigaEJmPzlIzt6BaJHMCsGA1Ud\nEQQkMCKCEXd3dy5xY2hhbm5lbDAxLmNugg1xY2hhbm5lbDAxLmNuMA4GA1UdDwEB\n/wQEAwIFoDAdBgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwPgYDVR0fBDcw\nNTAzoDGgL4YtaHR0cDovL2NkcC5nZW90cnVzdC5jb20vR2VvVHJ1c3RSU0FDQTIw\nMTguY3JsMEwGA1UdIARFMEMwNwYJYIZIAYb9bAECMCowKAYIKwYBBQUHAgEWHGh0\ndHBzOi8vd3d3LmRpZ2ljZXJ0LmNvbS9DUFMwCAYGZ4EMAQIBMHUGCCsGAQUFBwEB\nBGkwZzAmBggrBgEFBQcwAYYaaHR0cDovL3N0YXR1cy5nZW90cnVzdC5jb20wPQYI\nKwYBBQUHMAKGMWh0dHA6Ly9jYWNlcnRzLmdlb3RydXN0LmNvbS9HZW9UcnVzdFJT\nQUNBMjAxOC5jcnQwCQYDVR0TBAIwADANBgkqhkiG9w0BAQsFAAOCAQEASKxkkBF4\nrxQOIl8wgBerMiEBp3jMpmJpzdi3n/owMsJ3QA0JVj9kl801Vjbw2GWQoFmzhYDv\nc3ybkPv3/t8VHW4XVGWaMJKTAukBBWCxy4EQ4Jhfjk/GJTpHfawC2D9T4dziRLqB\nnEXySzq/WGQqD30I5znlROrxjktUd+AokJyMH9LqOsixQIX4LnqeTECFkMVZ3PUd\nhVBYYv2MzkBYA1X+R6w9BOnGo3q2nTqdnX8ZnXv8/Z3TXOU6NhOh2vhqM7nmjDZW\n20gFktMTcs5+yx8o2+3LMe33VBLfWVqhF30KKvwPJI7GjlhbsgdBGOMFQ2tJ3C1j\nDKdVmDX3lhKVkg==\n-----END CERTIFICATE-----\n", "-----BEGIN CERTIFICATE-----\nMIIGuTCCBaGgAwIBAgIQaCZrW1rq9ItKEJNp/UiEjTANBgkqhkiG9w0BAQsFADBm\nMQswCQYDVQQGEwJVUzEWMBQGA1UEChMNR2VvVHJ1c3QgSW5jLjEdMBsGA1UECxMU\nRG9tYWluIFZhbGlkYXRlZCBTU0wxIDAeBgNVBAMTF0dlb1RydXN0IERWIFNTTCBD\nQSAtIEczMB4XDTE3MDMzMDAwMDAwMFoXDTIwMDMyOTIzNTk1OVowHDEaMBgGA1UE\nAwwRd3d3LnFjaGFubmVsMDMuY24wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEK\nAoIBAQDYrvfMHY3AanZoFTuuyFeYhcUrat8sLaXgeAYJ+CEZchpa+PfEFRCSTS58\nU2jAMKHS6l69TnQgiL27OqaLrw7LttTH3Scc/jM9pE8Ag70LTDTRgC5LCEXZZRVk\n/ZUgK5K6VJSo9awjt27ERxSURhq9qt0RoDFKLZdG+tZfzowT/YwPRwx/nGtE77BW\nmtfj2wQ3H07gYqk304glF/X9VhwZqPL8DlUApCaOP3X4hfFANNQU0ou1gwNoc0qd\nPFp03eqYJaME89jW8lIdHtk3zZO+7yGwpv+o4MO3LPVvbndA+IziFlvCVN6R4pc8\n7dAYLCZgQgwtzr5P/YyPkU1Rp1zJAgMBAAGjggOrMIIDpzArBgNVHREEJDAighF3\nd3cucWNoYW5uZWwwMy5jboINcWNoYW5uZWwwMy5jbjAJBgNVHRMEAjAAMCsGA1Ud\nHwQkMCIwIKAeoByGGmh0dHA6Ly9ndC5zeW1jYi5jb20vZ3QuY3JsMIGdBgNVHSAE\ngZUwgZIwgY8GBmeBDAECATCBhDA/BggrBgEFBQcCARYzaHR0cHM6Ly93d3cuZ2Vv\ndHJ1c3QuY29tL3Jlc291cmNlcy9yZXBvc2l0b3J5L2xlZ2FsMEEGCCsGAQUFBwIC\nMDUMM2h0dHBzOi8vd3d3Lmdlb3RydXN0LmNvbS9yZXNvdXJjZXMvcmVwb3NpdG9y\neS9sZWdhbDAfBgNVHSMEGDAWgBStZSKFkNA746FJizf58QsdXxegdzAOBgNVHQ8B\nAf8EBAMCBaAwHQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMFcGCCsGAQUF\nBwEBBEswSTAfBggrBgEFBQcwAYYTaHR0cDovL2d0LnN5bWNkLmNvbTAmBggrBgEF\nBQcwAoYaaHR0cDovL2d0LnN5bWNiLmNvbS9ndC5jcnQwggH1BgorBgEEAdZ5AgQC\nBIIB5QSCAeEB3wB1AN3rHSt6DU+mIIuBrYFocH4ujp0B1VyIjT0RxM227L7MAAAB\nWx1S3EAAAAQDAEYwRAIgNRQvItTQyD13ljs6rmDumoHtSG7pOr8UuTiO4Nos+T8C\nIE2bP9ygHWJEsFOXvOqcVvnlImXQZKL+DT8kWn5vLo8wAHYApLkJkLQYWBSHuxOi\nzGdwCjw1mAT5G9+443fNDsgN3BAAAAFbHVLccAAABAMARzBFAiA/2shPBKOiztdd\n/UHJDtqNxc9Y9jUihgRpFE9ou7ZreQIhAPszi/sJH+QzY3r1epAFcZUdm7qXIKBD\n5qXki8EGe5h9AHYA7ku9t3XOYLrhQmkfq+GeZqMPfl+wctiDAMR7iXqo/csAAAFb\nHVLeNgAABAMARzBFAiEAtAMqltW0QL6i0bwH1vWAenaXdzdpCjWyxZOwvfuOGaQC\nIFuUjraKUJXwSO0E2JGWqoy0EUm/qtM9YyAhqeQemMWUAHYAvHjh38X2PGhGSTNN\noQ+hXwl5aSAJwIG08/aRfz7ZuKUAAAFbHVLdNwAABAMARzBFAiEArq7CmUK14Lj/\nGRV+Ir2KWws+rsfVkfduZU74m6575sgCIHwlhF0xcOftjYrmk448C2kca68YzQkx\npM0M5m/w6OrGMA0GCSqGSIb3DQEBCwUAA4IBAQBZR0xqdYaTqa6XFneUOV0L6m7a\nYOs8M/MBpvZmrA0dpQxKotNMoxZdGGOj/AdWIvYWJeiGUmIXLXEnCbu0R9DvSQfT\nPtma1rTof6P5SV1JiLfLrLPhJp84QYGhFomn+6YndRxdji7o7CoB0ClYTeCsVmF4\nGbRF/w3TsCcNClw2gwmE34AN+5pSS4yt9/u9Qi5PcH5vViTjrG733iEhTy+wCF8J\nrP629TDsGrtPOTHibEpc/av9q+y8k6t5f1h8ATH90hPC+alndsKkHJ13YGQygGLZ\nibyT/15XaH9JSVgk6YwXB9GBmBZCjwZGv4dj9QPwSmuMJ/yNxZ1xCg0dzPMw\n-----END CERTIFICATE-----", "-----BEGIN CERTIFICATE-----\nMIIFuTCCBKGgAwIBAgIQDO1HK/8bnqB2tCqXNm7k/TANBgkqhkiG9w0BAQsFADBe\nMQswCQYDVQQGEwJVUzEVMBMGA1UEChMMRGlnaUNlcnQgSW5jMRkwFwYDVQQLExB3\nd3cuZGlnaWNlcnQuY29tMR0wGwYDVQQDExRSYXBpZFNTTCBSU0EgQ0EgMjAxODAe\nFw0xODA3MzEwMDAwMDBaFw0xOTA3MzExMjAwMDBaMBoxGDAWBgNVBAMMDyoucWNo\nYW5uZWwwMy5jbjCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBANvxOEGK\n2V0XWeseUo89I2524LeEb1GpbocjR9AAiysagg6QtDt5PTywSJopE/gQXGZm9/YW\nxWyl3Uy6qtJrYM8DQVNY0KFftOPJPtCGJzfCHR8PUZ3p2648VuKN/wu8IXlo4NNg\nyZVJW0A4Sc6x4zOV6iRcYuA8KrNoAoguG+RfC7e7oFafW7JQSZbz7LJ0UdXflYEx\nOZuTlCUgiuK2PZFz8jAeNh8QVCuGYXLBcmm9eLgidLmN6+IDNCkmkCgYiAJrHcBl\n4AiCM1921BrmHjd1I5YFytP+0es4r3O8JogWSbRhnj744C17H/O5xEmIc3r81sA/\npAh/gljoLVLyXmECAwEAAaOCArUwggKxMB8GA1UdIwQYMBaAFFPKF1n8a8ADIS8a\nruSqqByCVtp1MB0GA1UdDgQWBBQ830bI+0nJGqoRp6Dg3vNJAXiXPzApBgNVHREE\nIjAggg8qLnFjaGFubmVsMDMuY26CDXFjaGFubmVsMDMuY24wDgYDVR0PAQH/BAQD\nAgWgMB0GA1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjA+BgNVHR8ENzA1MDOg\nMaAvhi1odHRwOi8vY2RwLnJhcGlkc3NsLmNvbS9SYXBpZFNTTFJTQUNBMjAxOC5j\ncmwwTAYDVR0gBEUwQzA3BglghkgBhv1sAQIwKjAoBggrBgEFBQcCARYcaHR0cHM6\nLy93d3cuZGlnaWNlcnQuY29tL0NQUzAIBgZngQwBAgEwdQYIKwYBBQUHAQEEaTBn\nMCYGCCsGAQUFBzABhhpodHRwOi8vc3RhdHVzLnJhcGlkc3NsLmNvbTA9BggrBgEF\nBQcwAoYxaHR0cDovL2NhY2VydHMucmFwaWRzc2wuY29tL1JhcGlkU1NMUlNBQ0Ey\nMDE4LmNydDAJBgNVHRMEAjAAMIIBAwYKKwYBBAHWeQIEAgSB9ASB8QDvAHYAu9nf\nvB+KcbWTlCOXqpJ7RzhXlQqrUugakJZkNo4e0YUAAAFk7poA0wAABAMARzBFAiEA\np5hIz6re5Z/0jT4hOpMTS4ZJyzNHAlWq7jNUohkLvMcCIAuGGLvwbVtfC0qkqbWH\n6DRg/croTkYT0ORke+93COSzAHUAh3W/51l8+IxDmV+9827/Vo1HVjb/SrVgwbTq\n/16ggw8AAAFk7poBUgAABAMARjBEAiBm/WHdzP7Ebxwmrv+Yva9ZrK2DAOyOYjCw\nNH7rlTQ9+AIgH/2usUo0Qz+DylJ3mAM6DWE0dDP18j5HzwIs/B4/tfMwDQYJKoZI\nhvcNAQELBQADggEBAMIYKNUEwsYzNdEgtchOkj9JjvMZY20ITVWlXFScATM0e1D8\nj2IX2Jo7NanVsDIjJgZZqJhNPBAjPL99APQJaVmuFZCZ09penwDkvk2W9UD4+cf5\nRkIZr1BYJ1I+ZRiEQll2NeLRgLzRZrZyYVhfjvPFz6BVz/0RNpA7vaR9NkIuzFo6\nw49HPoOS436nXXFZQ1GKWj3N6ZOSKl6rpoSKZ7144lDsbGsa6Pu4PbgDToVLi2xo\n3A3MyUpNoNSCgMdbf9i53Dr15/XrPxFis+j9LrY6rtafm1GYrEAGbsMHyAGrgAb6\nCL0w4JItjg/9YRQfA/y7HIr3jIg1U7qSfJ+fmyE=\n-----END CERTIFICATE-----\n"};
        InputStream[] inputStreamArr = new InputStream[4];
        for (int i = 0; i < 4; i++) {
            try {
                inputStreamArr[i] = e.a(strArr[i].getBytes("UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return inputStreamArr;
    }
}
