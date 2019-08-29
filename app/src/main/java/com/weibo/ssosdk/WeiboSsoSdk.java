package com.weibo.ssosdk;

import android.text.TextUtils;
import com.alipay.mobile.antui.basic.AUButton;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONObject;

public class WeiboSsoSdk {
    private static WeiboSsoSdk c;
    private static fbk d;
    public a a;
    private volatile ReentrantLock b = new ReentrantLock(true);
    /* access modifiers changed from: private */
    public boolean e = true;
    private int f;

    public static final class a {
        public String a;
        private String b;

        static a a(String str) throws Exception {
            a aVar = new a();
            try {
                JSONObject jSONObject = new JSONObject(str);
                String optString = jSONObject.optString("retcode", "");
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                if (optString.equals("20000000")) {
                    if (jSONObject2 != null) {
                        aVar.a = jSONObject2.optString("aid", "");
                        aVar.b = jSONObject2.optString(AUButton.BTN_TYPE_SUB, "");
                        return aVar;
                    }
                }
                StringBuilder sb = new StringBuilder("error： ");
                sb.append(optString);
                sb.append(" msg:");
                sb.append(jSONObject.optString("msg", ""));
                throw new Exception(sb.toString());
            } catch (Exception e) {
                throw e;
            }
        }
    }

    private native String riseWind(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i, int i2);

    static {
        System.loadLibrary("wind");
    }

    private WeiboSsoSdk() throws Exception {
        if (d == null || !d.b()) {
            throw new Exception("config error");
        }
        this.f = 0;
        new Thread(new Runnable() {
            public final void run() {
                String str;
                while (true) {
                    try {
                        Thread.sleep(86400000);
                        if (WeiboSsoSdk.this.a == null || TextUtils.isEmpty(WeiboSsoSdk.this.a.a)) {
                            str = WeiboSsoSdk.b();
                        } else {
                            str = WeiboSsoSdk.this.a.a;
                        }
                        WeiboSsoSdk.a(WeiboSsoSdk.a(), str, 2);
                    } catch (Exception unused) {
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            public final void run() {
                String str;
                try {
                    Thread.sleep(60000);
                    if (WeiboSsoSdk.this.e) {
                        if (WeiboSsoSdk.this.a == null || TextUtils.isEmpty(WeiboSsoSdk.this.a.a)) {
                            str = WeiboSsoSdk.b();
                        } else {
                            str = WeiboSsoSdk.this.a.a;
                        }
                        WeiboSsoSdk.a(WeiboSsoSdk.this, str, 2);
                    }
                } catch (Exception unused) {
                }
            }
        }).start();
    }

    public static synchronized boolean a(fbk fbk) {
        synchronized (WeiboSsoSdk.class) {
            if (!fbk.b()) {
                return false;
            }
            if (d != null) {
                return false;
            }
            d = (fbk) fbk.clone();
            return true;
        }
    }

    public static synchronized WeiboSsoSdk a() throws Exception {
        WeiboSsoSdk weiboSsoSdk;
        synchronized (WeiboSsoSdk.class) {
            try {
                if (c == null) {
                    c = new WeiboSsoSdk();
                }
                weiboSsoSdk = c;
            }
        }
        return weiboSsoSdk;
    }

    private static String a(String str) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://login.sina.com.cn/visitor/signin").openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setReadTimeout(3000);
            httpURLConnection.setConnectTimeout(1000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(str.getBytes());
            outputStream.flush();
            if (httpURLConnection.getResponseCode() != 200) {
                return null;
            }
            InputStream inputStream = httpURLConnection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int read = inputStream.read(bArr);
                if (read != -1) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    inputStream.close();
                    byteArrayOutputStream.close();
                    return new String(byteArrayOutputStream.toByteArray());
                }
            }
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0025 A[SYNTHETIC, Splitter:B:15:0x0025] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x002b A[SYNTHETIC, Splitter:B:21:0x002b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String b() {
        /*
            r0 = 0
            java.io.File r1 = d()     // Catch:{ Exception -> 0x0029, all -> 0x0020 }
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0029, all -> 0x0020 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x0029, all -> 0x0020 }
            int r0 = r2.available()     // Catch:{ Exception -> 0x001e, all -> 0x001c }
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x001e, all -> 0x001c }
            r2.read(r0)     // Catch:{ Exception -> 0x001e, all -> 0x001c }
            java.lang.String r1 = new java.lang.String     // Catch:{ Exception -> 0x001e, all -> 0x001c }
            r1.<init>(r0)     // Catch:{ Exception -> 0x001e, all -> 0x001c }
            r2.close()     // Catch:{ IOException -> 0x001b }
        L_0x001b:
            return r1
        L_0x001c:
            r0 = move-exception
            goto L_0x0023
        L_0x001e:
            r0 = r2
            goto L_0x0029
        L_0x0020:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x0023:
            if (r2 == 0) goto L_0x0028
            r2.close()     // Catch:{ IOException -> 0x0028 }
        L_0x0028:
            throw r0
        L_0x0029:
            if (r0 == 0) goto L_0x002e
            r0.close()     // Catch:{ IOException -> 0x002e }
        L_0x002e:
            java.lang.String r0 = ""
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.weibo.ssosdk.WeiboSsoSdk.b():java.lang.String");
    }

    private static File d() {
        return new File(d.a.getFilesDir(), "weibo_sso_sdk_aid1");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:18|19|(0)|26|27) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x002c */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0029 A[SYNTHETIC, Splitter:B:24:0x0029] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x002f A[SYNTHETIC, Splitter:B:30:0x002f] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0036 A[DONT_GENERATE] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void b(java.lang.String r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = android.text.TextUtils.isEmpty(r4)     // Catch:{ all -> 0x0038 }
            if (r0 == 0) goto L_0x0009
            monitor-exit(r3)
            return
        L_0x0009:
            r0 = 0
            java.io.File r1 = d()     // Catch:{ Exception -> 0x002d, all -> 0x0026 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x002d, all -> 0x0026 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x002d, all -> 0x0026 }
            byte[] r4 = r4.getBytes()     // Catch:{ Exception -> 0x0024, all -> 0x0021 }
            r2.write(r4)     // Catch:{ Exception -> 0x0024, all -> 0x0021 }
            r2.close()     // Catch:{ IOException -> 0x001f }
            monitor-exit(r3)
            return
        L_0x001f:
            monitor-exit(r3)
            return
        L_0x0021:
            r4 = move-exception
            r0 = r2
            goto L_0x0027
        L_0x0024:
            r0 = r2
            goto L_0x002d
        L_0x0026:
            r4 = move-exception
        L_0x0027:
            if (r0 == 0) goto L_0x002c
            r0.close()     // Catch:{ IOException -> 0x002c }
        L_0x002c:
            throw r4     // Catch:{ all -> 0x0038 }
        L_0x002d:
            if (r0 == 0) goto L_0x0036
            r0.close()     // Catch:{ IOException -> 0x0034 }
            monitor-exit(r3)
            return
        L_0x0034:
            monitor-exit(r3)
            return
        L_0x0036:
            monitor-exit(r3)
            return
        L_0x0038:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.weibo.ssosdk.WeiboSsoSdk.b(java.lang.String):void");
    }

    static /* synthetic */ void a(WeiboSsoSdk weiboSsoSdk, String str, int i) throws Exception {
        String str2;
        WeiboSsoSdk weiboSsoSdk2 = weiboSsoSdk;
        if (!TextUtils.isEmpty(d.a(false))) {
            if (!weiboSsoSdk2.b.tryLock()) {
                weiboSsoSdk2.b.lock();
                weiboSsoSdk2.b.unlock();
                return;
            }
            weiboSsoSdk2.e = false;
            String a2 = fbj.a(d.a);
            try {
                str2 = URLEncoder.encode(str, "utf-8");
            } catch (UnsupportedEncodingException unused) {
                str2 = "";
            }
            String str3 = str2;
            WeiboSsoSdk weiboSsoSdk3 = weiboSsoSdk2;
            String a3 = a(weiboSsoSdk3.riseWind(d.a(true), d.a.getPackageName(), str3, a2, fbk.a(d.d), fbk.a(d.f), fbk.a(d.e), fbk.a(d.g), fbk.a(d.c), d.a(), i, weiboSsoSdk2.f));
            weiboSsoSdk2.f++;
            if (a3 != null) {
                try {
                    a a4 = a.a(a3);
                    if (!TextUtils.isEmpty(a4.a)) {
                        weiboSsoSdk2.b(a4.a);
                    }
                    if (i == 1) {
                        weiboSsoSdk2.a = a4;
                    }
                    weiboSsoSdk2.b.unlock();
                } catch (Exception e2) {
                    weiboSsoSdk2.b.unlock();
                    throw e2;
                }
            } else {
                weiboSsoSdk2.b.unlock();
                throw new Exception("network error.");
            }
        }
    }
}
