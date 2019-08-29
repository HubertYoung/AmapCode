package com.alibaba.baichuan.android.trade.utils.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

public class HttpHelper {
    public static final int INVALID_RESPONSE_CODE = -999;

    public static class HttpHelpterException extends Exception {
        public int statusCode;

        HttpHelpterException(Throwable th) {
            super(th);
        }
    }

    private static InputStream a(String str) {
        int i = -999;
        try {
            HttpURLConnection b = b(str);
            int responseCode = b.getResponseCode();
            try {
                a(responseCode);
                return b.getInputStream();
            } catch (Exception e) {
                e = e;
                i = responseCode;
                HttpHelpterException httpHelpterException = new HttpHelpterException(e);
                httpHelpterException.statusCode = i;
                throw httpHelpterException;
            }
        } catch (Exception e2) {
            e = e2;
            HttpHelpterException httpHelpterException2 = new HttpHelpterException(e);
            httpHelpterException2.statusCode = i;
            throw httpHelpterException2;
        }
    }

    private static String a(InputStream inputStream, String str) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    return new String(byteArrayOutputStream.toByteArray(), str);
                }
                byteArrayOutputStream.write(bArr, 0, read);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void a(int i) {
        if (i != 200) {
            throw new RuntimeException("http request exception, response code: ".concat(String.valueOf(i)));
        }
    }

    private static HttpURLConnection b(String str) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setConnectTimeout(getConnectionTimeout(5000));
            httpURLConnection.setReadTimeout(getReadTimeout(5000));
            return httpURLConnection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String encodeRequest(Map map) {
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        for (Entry entry : map.entrySet()) {
            if (z) {
                try {
                    sb.append("&");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            } else {
                z = true;
            }
            sb.append((String) entry.getKey());
            sb.append("=");
            sb.append(URLEncoder.encode((String) entry.getValue(), "UTF-8"));
        }
        return sb.toString();
    }

    public static String get(String str, Map map) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(map == null ? "" : "?");
            sb.append(map == null ? "" : encodeRequest(map));
            return IOUtils.toString(a(sb.toString()), "UTF-8");
        } catch (Exception e) {
            if (e instanceof HttpHelpterException) {
                throw e;
            }
            throw new HttpHelpterException(e);
        }
    }

    public static String getCharset() {
        return "UTF-8";
    }

    public static int getConnectionTimeout(int i) {
        return i;
    }

    public static int getReadTimeout(int i) {
        return i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0065 A[SYNTHETIC, Splitter:B:29:0x0065] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String postForm(java.util.Map r3, java.lang.String r4) {
        /*
            r0 = 0
            java.lang.String r3 = encodeRequest(r3)     // Catch:{ Exception -> 0x0057, all -> 0x0054 }
            java.lang.String r1 = "UTF-8"
            byte[] r3 = r3.getBytes(r1)     // Catch:{ Exception -> 0x0057, all -> 0x0054 }
            java.net.HttpURLConnection r4 = b(r4)     // Catch:{ Exception -> 0x0057, all -> 0x0054 }
            r1 = 1
            r4.setDoInput(r1)     // Catch:{ Exception -> 0x0052 }
            r4.setDoOutput(r1)     // Catch:{ Exception -> 0x0052 }
            java.lang.String r1 = "POST"
            r4.setRequestMethod(r1)     // Catch:{ Exception -> 0x0052 }
            r1 = 0
            r4.setUseCaches(r1)     // Catch:{ Exception -> 0x0052 }
            java.lang.String r1 = "Content-Type"
            java.lang.String r2 = "application/x-www-form-urlencoded"
            r4.setRequestProperty(r1, r2)     // Catch:{ Exception -> 0x0052 }
            java.io.OutputStream r1 = r4.getOutputStream()     // Catch:{ Exception -> 0x0052 }
            r1.write(r3)     // Catch:{ Exception -> 0x004f, all -> 0x004c }
            r1.flush()     // Catch:{ Exception -> 0x004f, all -> 0x004c }
            int r3 = r4.getResponseCode()     // Catch:{ Exception -> 0x004f, all -> 0x004c }
            a(r3)     // Catch:{ Exception -> 0x004f, all -> 0x004c }
            java.io.InputStream r3 = r4.getInputStream()     // Catch:{ Exception -> 0x004f, all -> 0x004c }
            java.lang.String r0 = getCharset()     // Catch:{ Exception -> 0x004f, all -> 0x004c }
            java.lang.String r3 = a(r3, r0)     // Catch:{ Exception -> 0x004f, all -> 0x004c }
            com.alibaba.baichuan.android.trade.utils.http.IOUtils.closeQuietly(r1)
            if (r4 == 0) goto L_0x004b
            r4.disconnect()     // Catch:{ Exception -> 0x004b }
        L_0x004b:
            return r3
        L_0x004c:
            r3 = move-exception
            r0 = r1
            goto L_0x0060
        L_0x004f:
            r3 = move-exception
            r0 = r1
            goto L_0x0059
        L_0x0052:
            r3 = move-exception
            goto L_0x0059
        L_0x0054:
            r3 = move-exception
            r4 = r0
            goto L_0x0060
        L_0x0057:
            r3 = move-exception
            r4 = r0
        L_0x0059:
            com.alibaba.baichuan.android.trade.utils.http.HttpHelper$HttpHelpterException r1 = new com.alibaba.baichuan.android.trade.utils.http.HttpHelper$HttpHelpterException     // Catch:{ all -> 0x005f }
            r1.<init>(r3)     // Catch:{ all -> 0x005f }
            throw r1     // Catch:{ all -> 0x005f }
        L_0x005f:
            r3 = move-exception
        L_0x0060:
            com.alibaba.baichuan.android.trade.utils.http.IOUtils.closeQuietly(r0)
            if (r4 == 0) goto L_0x0068
            r4.disconnect()     // Catch:{ Exception -> 0x0068 }
        L_0x0068:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.baichuan.android.trade.utils.http.HttpHelper.postForm(java.util.Map, java.lang.String):java.lang.String");
    }
}
