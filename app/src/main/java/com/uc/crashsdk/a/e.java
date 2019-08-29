package com.uc.crashsdk.a;

import android.content.Context;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Build.VERSION;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: ProGuard */
public class e {
    static final /* synthetic */ boolean a;
    private static String b = "";
    private static a c;

    /* compiled from: ProGuard */
    public final class a {
        public boolean a;
        public String b;
    }

    static {
        boolean z;
        if (!e.class.desiredAssertionStatus()) {
            z = true;
        } else {
            z = false;
        }
        a = z;
    }

    private static boolean d() {
        String str;
        if (c != null) {
            return c.a;
        }
        if (c != null) {
            str = c.b;
        } else {
            NetworkInfo e = e();
            str = "unknown";
            if (e == null) {
                str = "no_network";
            } else {
                int type = e.getType();
                if (e.getType() == 1) {
                    str = "wifi";
                } else {
                    if (e.getExtraInfo() != null) {
                        str = e.getExtraInfo().toLowerCase();
                    }
                    if (type != 0) {
                        str = "wifi";
                    } else if (str.contains("cmwap")) {
                        str = "cmwap";
                    } else if (str.contains("cmnet")) {
                        str = "cmnet";
                    } else if (str.contains("uniwap")) {
                        str = "uniwap";
                    } else if (str.contains("uninet")) {
                        str = "uninet";
                    } else if (str.contains("3gwap")) {
                        str = "3gwap";
                    } else if (str.contains("3gnet")) {
                        str = "3gnet";
                    } else if (str.contains("ctwap")) {
                        str = "ctwap";
                    } else if (str.contains("ctnet")) {
                        str = "ctnet";
                    }
                }
            }
        }
        return "wifi".equals(str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001c, code lost:
        if (r1.isConnected() == false) goto L_0x001e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.net.NetworkInfo e() {
        /*
            r1 = 0
            java.lang.String r0 = "connectivity"
            java.lang.Object r0 = com.uc.crashsdk.e.a(r0)     // Catch:{ Exception -> 0x003c }
            android.net.ConnectivityManager r0 = (android.net.ConnectivityManager) r0     // Catch:{ Exception -> 0x003c }
            if (r0 != 0) goto L_0x0012
            java.lang.String r0 = "ConnectivityStatus, isQuickNet,ConnectivityManager==null"
            com.uc.crashsdk.a.c.c(r0)     // Catch:{ Exception -> 0x003c }
            r0 = r1
        L_0x0011:
            return r0
        L_0x0012:
            android.net.NetworkInfo r1 = r0.getActiveNetworkInfo()     // Catch:{ Exception -> 0x003c }
            if (r1 == 0) goto L_0x001e
            boolean r2 = r1.isConnected()     // Catch:{ Exception -> 0x0045 }
            if (r2 != 0) goto L_0x003a
        L_0x001e:
            android.net.NetworkInfo[] r2 = r0.getAllNetworkInfo()     // Catch:{ Exception -> 0x0045 }
            if (r2 == 0) goto L_0x003a
            r0 = 0
        L_0x0025:
            int r3 = r2.length     // Catch:{ Exception -> 0x0045 }
            if (r0 >= r3) goto L_0x003a
            r3 = r2[r0]     // Catch:{ Exception -> 0x0045 }
            if (r3 == 0) goto L_0x0037
            r3 = r2[r0]     // Catch:{ Exception -> 0x0045 }
            boolean r3 = r3.isConnected()     // Catch:{ Exception -> 0x0045 }
            if (r3 == 0) goto L_0x0037
            r0 = r2[r0]     // Catch:{ Exception -> 0x0045 }
            goto L_0x0011
        L_0x0037:
            int r0 = r0 + 1
            goto L_0x0025
        L_0x003a:
            r0 = r1
            goto L_0x0011
        L_0x003c:
            r0 = move-exception
            r4 = r0
            r0 = r1
            r1 = r4
        L_0x0040:
            r2 = 1
            com.uc.crashsdk.a.a.a(r1, r2)
            goto L_0x0011
        L_0x0045:
            r0 = move-exception
            r4 = r0
            r0 = r1
            r1 = r4
            goto L_0x0040
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.e.e():android.net.NetworkInfo");
    }

    public static String a() {
        if (VERSION.SDK_INT >= 11) {
            return System.getProperty("http.proxyHost");
        }
        Context a2 = com.uc.crashsdk.e.a();
        if (a2 == null) {
            return null;
        }
        String host = Proxy.getHost(a2);
        if (!d() || host == null || host.indexOf("10.0.0") == -1) {
            return host;
        }
        return "";
    }

    public static int b() {
        if (VERSION.SDK_INT >= 11) {
            String property = System.getProperty("http.proxyPort");
            if (property == null) {
                property = "-1";
            }
            try {
                return Integer.parseInt(property);
            } catch (Exception e) {
                return -1;
            }
        } else {
            Context a2 = com.uc.crashsdk.e.a();
            if (a2 == null) {
                return 80;
            }
            String host = Proxy.getHost(a2);
            int port = Proxy.getPort(a2);
            if (!d() || host == null || host.indexOf("10.0.0") == -1) {
                return port;
            }
            return -1;
        }
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        return a(bArr, bArr2, true, false);
    }

    public static byte[] a(byte[] bArr, byte[] bArr2, boolean z) {
        return a(bArr, bArr2, z, true);
    }

    private static byte[] a(byte[] bArr, byte[] bArr2, boolean z, boolean z2) {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(z ? 1 : 2, secretKeySpec, ivParameterSpec);
        if (!z) {
            return instance.doFinal(bArr);
        }
        if (!z2) {
            bArr = a(bArr);
        }
        return instance.doFinal(bArr);
    }

    private static byte[] a(byte[] bArr) {
        byte[] bArr2 = new byte[(bArr.length + 16)];
        int length = bArr.length;
        bArr2[0] = (byte) ((length >> 0) & 255);
        bArr2[1] = (byte) ((length >> 8) & 255);
        bArr2[2] = (byte) ((length >> 16) & 255);
        bArr2[3] = (byte) ((length >> 24) & 255);
        for (int i = 4; i < 16; i++) {
            bArr2[i] = 0;
        }
        System.arraycopy(bArr, 0, bArr2, 16, bArr.length);
        return bArr2;
    }

    public static byte[] c() {
        return new byte[]{48, 25, 6, 55};
    }

    public static byte[] a(String str, byte[] bArr) {
        InputStream inputStream;
        OutputStream outputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        ByteArrayOutputStream byteArrayOutputStream2;
        OutputStream outputStream2;
        InputStream inputStream2 = null;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(bArr.length));
            outputStream2 = httpURLConnection.getOutputStream();
            try {
                outputStream2.write(bArr);
                if (httpURLConnection.getResponseCode() != 200) {
                    b.a((Closeable) outputStream2);
                    b.a((Closeable) null);
                    b.a((Closeable) null);
                    return null;
                }
                InputStream inputStream3 = httpURLConnection.getInputStream();
                try {
                    byte[] bArr2 = new byte[1024];
                    byteArrayOutputStream2 = new ByteArrayOutputStream(inputStream3.available());
                    while (true) {
                        try {
                            int read = inputStream3.read(bArr2);
                            if (read != -1) {
                                byteArrayOutputStream2.write(bArr2, 0, read);
                            } else {
                                byte[] byteArray = byteArrayOutputStream2.toByteArray();
                                b.a((Closeable) outputStream2);
                                b.a((Closeable) inputStream3);
                                b.a((Closeable) byteArrayOutputStream2);
                                return byteArray;
                            }
                        } catch (Throwable th) {
                            th = th;
                            inputStream2 = inputStream3;
                            b.a((Closeable) outputStream2);
                            b.a((Closeable) inputStream2);
                            b.a((Closeable) byteArrayOutputStream2);
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    byteArrayOutputStream2 = null;
                    inputStream2 = inputStream3;
                    b.a((Closeable) outputStream2);
                    b.a((Closeable) inputStream2);
                    b.a((Closeable) byteArrayOutputStream2);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                byteArrayOutputStream2 = null;
                b.a((Closeable) outputStream2);
                b.a((Closeable) inputStream2);
                b.a((Closeable) byteArrayOutputStream2);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            outputStream2 = null;
            byteArrayOutputStream2 = null;
            b.a((Closeable) outputStream2);
            b.a((Closeable) inputStream2);
            b.a((Closeable) byteArrayOutputStream2);
            throw th;
        }
    }

    public static void a(byte[] bArr, int i, byte[] bArr2) {
        if (a || bArr2.length == 4) {
            for (int i2 = 0; i2 < 4; i2++) {
                bArr[i2 + i] = bArr2[i2];
            }
            return;
        }
        throw new AssertionError();
    }
}
