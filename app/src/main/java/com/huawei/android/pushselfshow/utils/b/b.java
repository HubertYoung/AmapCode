package com.huawei.android.pushselfshow.utils.b;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.utils.a;
import java.io.File;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;

public class b {
    public Handler a;
    public Context b;
    public String c;
    public String d;
    public boolean e;
    private boolean f;
    private Runnable g;

    public b() {
        this.a = null;
        this.e = false;
        this.g = new c(this);
        this.f = false;
    }

    public b(Handler handler, Context context, String str, String str2) {
        this.a = null;
        this.e = false;
        this.g = new c(this);
        this.a = handler;
        this.b = context;
        this.c = str;
        this.d = str2;
        this.f = false;
    }

    public static String a(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(b(context));
        sb.append(File.separator);
        sb.append("richpush");
        return sb.toString();
    }

    public static void a(HttpClient httpClient) {
        if (httpClient != null) {
            try {
                httpClient.getConnectionManager().shutdown();
            } catch (Exception e2) {
                StringBuilder sb = new StringBuilder("close input stream failed.");
                sb.append(e2.getMessage());
                c.c("PushSelfShowLog", sb.toString(), e2);
            }
        }
    }

    public static void a(HttpRequestBase httpRequestBase) {
        if (httpRequestBase != null) {
            try {
                httpRequestBase.abort();
            } catch (Exception e2) {
                StringBuilder sb = new StringBuilder("close input stream failed.");
                sb.append(e2.getMessage());
                c.c("PushSelfShowLog", sb.toString(), e2);
            }
        }
    }

    public static String b(Context context) {
        String str;
        try {
            str = !Environment.getExternalStorageState().equals("mounted") ? context.getFilesDir().getPath() : a.k(context);
        } catch (Exception e2) {
            c.c("PushSelfShowLog", "getLocalFileHeader failed ", e2);
            str = "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(File.separator);
        sb.append("PushService");
        String sb2 = sb.toString();
        c.a("PushSelfShowLog", "getFileHeader:".concat(String.valueOf(sb2)));
        return sb2;
    }

    public static String c(Context context) {
        String str;
        try {
            if (!Environment.getExternalStorageState().equals("mounted")) {
                return null;
            }
            str = a.k(context);
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(File.separator);
            sb.append("PushService");
            return sb.toString();
        } catch (Exception e2) {
            c.c("PushSelfShowLog", "getLocalFileHeader failed ", e2);
            str = "";
        }
    }

    public String a(Context context, String str, String str2) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(System.currentTimeMillis());
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(str2);
            String sb4 = sb3.toString();
            StringBuilder sb5 = new StringBuilder();
            sb5.append(a(context));
            sb5.append(File.separator);
            sb5.append(sb2);
            String sb6 = sb5.toString();
            StringBuilder sb7 = new StringBuilder();
            sb7.append(sb6);
            sb7.append(File.separator);
            sb7.append(sb4);
            String sb8 = sb7.toString();
            File file = new File(sb6);
            if (!file.exists()) {
                StringBuilder sb9 = new StringBuilder("dir is not exists(),");
                sb9.append(file.getAbsolutePath());
                c.a("PushSelfShowLog", sb9.toString());
            } else {
                a.a(file);
            }
            if (file.mkdirs()) {
                c.a("PushSelfShowLog", "dir.mkdirs() success  ");
            }
            StringBuilder sb10 = new StringBuilder("begin to download zip file, path is ");
            sb10.append(sb8);
            sb10.append(",dir is ");
            sb10.append(file.getAbsolutePath());
            c.a("PushSelfShowLog", sb10.toString());
            if (b(context, str, sb8)) {
                return sb8;
            }
            c.a("PushSelfShowLog", "download richpush file failed，clear temp file");
            if (file.exists()) {
                a.a(file);
            }
            return null;
        } catch (Exception e2) {
            StringBuilder sb11 = new StringBuilder("download err");
            sb11.append(e2.toString());
            c.a("PushSelfShowLog", sb11.toString());
        }
    }

    public void a() {
        this.f = true;
    }

    public void a(String str) {
        Message message = new Message();
        message.what = 1;
        message.obj = str;
        StringBuilder sb = new StringBuilder("mDownloadHandler = ");
        sb.append(this.a);
        c.a("PushSelfShowLog", sb.toString());
        if (this.a != null) {
            this.a.sendMessageDelayed(message, 1);
        }
    }

    public void b() {
        new Thread(this.g).start();
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x01e6 A[SYNTHETIC, Splitter:B:100:0x01e6] */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x0204 A[SYNTHETIC, Splitter:B:105:0x0204] */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x0230 A[SYNTHETIC, Splitter:B:116:0x0230] */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x024e A[SYNTHETIC, Splitter:B:121:0x024e] */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x026c A[SYNTHETIC, Splitter:B:126:0x026c] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x01c8 A[SYNTHETIC, Splitter:B:95:0x01c8] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean b(android.content.Context r9, java.lang.String r10, java.lang.String r11) {
        /*
            r8 = this;
            r0 = 0
            r1 = 0
            org.apache.http.impl.client.DefaultHttpClient r2 = new org.apache.http.impl.client.DefaultHttpClient     // Catch:{ IOException -> 0x01a4, all -> 0x019d }
            r2.<init>()     // Catch:{ IOException -> 0x01a4, all -> 0x019d }
            org.apache.http.client.methods.HttpGet r3 = new org.apache.http.client.methods.HttpGet     // Catch:{ IOException -> 0x0198, all -> 0x0192 }
            r3.<init>(r10)     // Catch:{ IOException -> 0x0198, all -> 0x0192 }
            com.huawei.android.pushselfshow.utils.b.d r4 = new com.huawei.android.pushselfshow.utils.b.d     // Catch:{ IOException -> 0x018d, all -> 0x0188 }
            r4.<init>(r9)     // Catch:{ IOException -> 0x018d, all -> 0x0188 }
            org.apache.http.HttpResponse r9 = r4.a(r10, r2, r3)     // Catch:{ IOException -> 0x018d, all -> 0x0188 }
            if (r9 != 0) goto L_0x0025
            java.lang.String r9 = "PushSelfShowLog"
            java.lang.String r10 = "fail, httprespone  is null"
            com.huawei.android.pushagent.a.a.c.a(r9, r10)     // Catch:{ IOException -> 0x018d, all -> 0x0188 }
        L_0x001e:
            a(r3)
            a(r2)
            return r1
        L_0x0025:
            org.apache.http.StatusLine r10 = r9.getStatusLine()     // Catch:{ IOException -> 0x018d, all -> 0x0188 }
            if (r10 == 0) goto L_0x0053
            org.apache.http.StatusLine r10 = r9.getStatusLine()     // Catch:{ IOException -> 0x018d, all -> 0x0188 }
            int r10 = r10.getStatusCode()     // Catch:{ IOException -> 0x018d, all -> 0x0188 }
            r4 = 200(0xc8, float:2.8E-43)
            if (r10 == r4) goto L_0x0053
            java.lang.String r10 = "PushSelfShowLog"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x018d, all -> 0x0188 }
            java.lang.String r4 = "fail, httprespone status is "
            r11.<init>(r4)     // Catch:{ IOException -> 0x018d, all -> 0x0188 }
            org.apache.http.StatusLine r9 = r9.getStatusLine()     // Catch:{ IOException -> 0x018d, all -> 0x0188 }
            int r9 = r9.getStatusCode()     // Catch:{ IOException -> 0x018d, all -> 0x0188 }
            r11.append(r9)     // Catch:{ IOException -> 0x018d, all -> 0x0188 }
            java.lang.String r9 = r11.toString()     // Catch:{ IOException -> 0x018d, all -> 0x0188 }
            com.huawei.android.pushagent.a.a.c.a(r10, r9)     // Catch:{ IOException -> 0x018d, all -> 0x0188 }
            goto L_0x001e
        L_0x0053:
            java.io.BufferedInputStream r10 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x018d, all -> 0x0188 }
            org.apache.http.HttpEntity r9 = r9.getEntity()     // Catch:{ IOException -> 0x018d, all -> 0x0188 }
            java.io.InputStream r9 = r9.getContent()     // Catch:{ IOException -> 0x018d, all -> 0x0188 }
            r10.<init>(r9)     // Catch:{ IOException -> 0x018d, all -> 0x0188 }
            java.lang.String r9 = "PushSelfShowLog"
            java.lang.String r4 = "begin to write content to "
            java.lang.String r5 = java.lang.String.valueOf(r11)     // Catch:{ IOException -> 0x0185, all -> 0x0181 }
            java.lang.String r4 = r4.concat(r5)     // Catch:{ IOException -> 0x0185, all -> 0x0181 }
            com.huawei.android.pushagent.a.a.c.a(r9, r4)     // Catch:{ IOException -> 0x0185, all -> 0x0181 }
            java.io.File r9 = new java.io.File     // Catch:{ IOException -> 0x0185, all -> 0x0181 }
            r9.<init>(r11)     // Catch:{ IOException -> 0x0185, all -> 0x0181 }
            boolean r9 = r9.exists()     // Catch:{ IOException -> 0x0185, all -> 0x0181 }
            if (r9 == 0) goto L_0x0096
            java.lang.String r9 = "PushSelfShowLog"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0185, all -> 0x0181 }
            java.lang.String r5 = "file delete "
            r4.<init>(r5)     // Catch:{ IOException -> 0x0185, all -> 0x0181 }
            java.io.File r5 = new java.io.File     // Catch:{ IOException -> 0x0185, all -> 0x0181 }
            r5.<init>(r11)     // Catch:{ IOException -> 0x0185, all -> 0x0181 }
            boolean r5 = r5.delete()     // Catch:{ IOException -> 0x0185, all -> 0x0181 }
            r4.append(r5)     // Catch:{ IOException -> 0x0185, all -> 0x0181 }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x0185, all -> 0x0181 }
            com.huawei.android.pushagent.a.a.c.a(r9, r4)     // Catch:{ IOException -> 0x0185, all -> 0x0181 }
        L_0x0096:
            java.io.FileOutputStream r9 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0185, all -> 0x0181 }
            r9.<init>(r11)     // Catch:{ IOException -> 0x0185, all -> 0x0181 }
            java.io.BufferedOutputStream r11 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x017a, all -> 0x0174 }
            r11.<init>(r9)     // Catch:{ IOException -> 0x017a, all -> 0x0174 }
            r0 = 32768(0x8000, float:4.5918E-41)
            byte[] r0 = new byte[r0]     // Catch:{ IOException -> 0x016f, all -> 0x0168 }
        L_0x00a5:
            int r4 = r10.read(r0)     // Catch:{ IOException -> 0x016f, all -> 0x0168 }
            r5 = 1
            if (r4 >= 0) goto L_0x0110
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r4 = "downLoad success "
            com.huawei.android.pushagent.a.a.c.a(r0, r4)     // Catch:{ IOException -> 0x016f, all -> 0x0168 }
            r8.e = r1     // Catch:{ IOException -> 0x016f, all -> 0x0168 }
            a(r3)
            a(r2)
            r11.close()     // Catch:{ IOException -> 0x00bf }
            goto L_0x00d7
        L_0x00bf:
            r11 = move-exception
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = " bos download  error"
            r1.<init>(r2)
            java.lang.String r2 = r11.toString()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.huawei.android.pushagent.a.a.c.c(r0, r1, r11)
        L_0x00d7:
            r10.close()     // Catch:{ IOException -> 0x00db }
            goto L_0x00f3
        L_0x00db:
            r10 = move-exception
            java.lang.String r11 = "PushSelfShowLog"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = " bis download  error"
            r0.<init>(r1)
            java.lang.String r1 = r10.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.huawei.android.pushagent.a.a.c.c(r11, r0, r10)
        L_0x00f3:
            r9.close()     // Catch:{ Exception -> 0x00f7 }
            return r5
        L_0x00f7:
            r9 = move-exception
            java.lang.String r10 = "PushSelfShowLog"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r0 = "out download  error"
            r11.<init>(r0)
            java.lang.String r0 = r9.toString()
            r11.append(r0)
            java.lang.String r11 = r11.toString()
            com.huawei.android.pushagent.a.a.c.c(r10, r11, r9)
            return r5
        L_0x0110:
            r8.e = r5     // Catch:{ IOException -> 0x016f, all -> 0x0168 }
            r11.write(r0, r1, r4)     // Catch:{ IOException -> 0x016f, all -> 0x0168 }
            boolean r4 = r8.f     // Catch:{ IOException -> 0x016f, all -> 0x0168 }
            if (r4 == 0) goto L_0x00a5
            a(r3)
            a(r2)
            r11.close()     // Catch:{ IOException -> 0x0123 }
            goto L_0x013b
        L_0x0123:
            r11 = move-exception
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = " bos download  error"
            r2.<init>(r3)
            java.lang.String r3 = r11.toString()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.huawei.android.pushagent.a.a.c.c(r0, r2, r11)
        L_0x013b:
            r10.close()     // Catch:{ IOException -> 0x013f }
            goto L_0x0157
        L_0x013f:
            r10 = move-exception
            java.lang.String r11 = "PushSelfShowLog"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = " bis download  error"
            r0.<init>(r2)
            java.lang.String r2 = r10.toString()
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            com.huawei.android.pushagent.a.a.c.c(r11, r0, r10)
        L_0x0157:
            r9.close()     // Catch:{ Exception -> 0x015c }
            goto L_0x0220
        L_0x015c:
            r9 = move-exception
            java.lang.String r10 = "PushSelfShowLog"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r0 = "out download  error"
            r11.<init>(r0)
            goto L_0x0212
        L_0x0168:
            r0 = move-exception
            r7 = r11
            r11 = r9
            r9 = r0
            r0 = r7
            goto L_0x0228
        L_0x016f:
            r0 = move-exception
            r7 = r3
            r3 = r9
            r9 = r0
            goto L_0x017f
        L_0x0174:
            r11 = move-exception
            r7 = r11
            r11 = r9
            r9 = r7
            goto L_0x0228
        L_0x017a:
            r11 = move-exception
            r7 = r3
            r3 = r9
            r9 = r11
            r11 = r0
        L_0x017f:
            r0 = r7
            goto L_0x01a9
        L_0x0181:
            r9 = move-exception
            r11 = r0
            goto L_0x0228
        L_0x0185:
            r9 = move-exception
            r11 = r0
            goto L_0x0190
        L_0x0188:
            r9 = move-exception
            r10 = r0
            r11 = r10
            goto L_0x0228
        L_0x018d:
            r9 = move-exception
            r10 = r0
            r11 = r10
        L_0x0190:
            r0 = r3
            goto L_0x019b
        L_0x0192:
            r9 = move-exception
            r10 = r0
            r11 = r10
            r3 = r11
            goto L_0x0228
        L_0x0198:
            r9 = move-exception
            r10 = r0
            r11 = r10
        L_0x019b:
            r3 = r11
            goto L_0x01a9
        L_0x019d:
            r9 = move-exception
            r10 = r0
            r11 = r10
            r2 = r11
            r3 = r2
            goto L_0x0228
        L_0x01a4:
            r9 = move-exception
            r10 = r0
            r11 = r10
            r2 = r11
            r3 = r2
        L_0x01a9:
            java.lang.String r4 = "PushSelfShowLog"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0223 }
            java.lang.String r6 = "downLoadSgThread download  error"
            r5.<init>(r6)     // Catch:{ all -> 0x0223 }
            java.lang.String r6 = r9.toString()     // Catch:{ all -> 0x0223 }
            r5.append(r6)     // Catch:{ all -> 0x0223 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0223 }
            com.huawei.android.pushagent.a.a.c.c(r4, r5, r9)     // Catch:{ all -> 0x0223 }
            a(r0)
            a(r2)
            if (r11 == 0) goto L_0x01e4
            r11.close()     // Catch:{ IOException -> 0x01cc }
            goto L_0x01e4
        L_0x01cc:
            r9 = move-exception
            java.lang.String r11 = "PushSelfShowLog"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = " bos download  error"
            r0.<init>(r2)
            java.lang.String r2 = r9.toString()
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            com.huawei.android.pushagent.a.a.c.c(r11, r0, r9)
        L_0x01e4:
            if (r10 == 0) goto L_0x0202
            r10.close()     // Catch:{ IOException -> 0x01ea }
            goto L_0x0202
        L_0x01ea:
            r9 = move-exception
            java.lang.String r10 = "PushSelfShowLog"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r0 = " bis download  error"
            r11.<init>(r0)
            java.lang.String r0 = r9.toString()
            r11.append(r0)
            java.lang.String r11 = r11.toString()
            com.huawei.android.pushagent.a.a.c.c(r10, r11, r9)
        L_0x0202:
            if (r3 == 0) goto L_0x0220
            r3.close()     // Catch:{ Exception -> 0x0208 }
            goto L_0x0220
        L_0x0208:
            r9 = move-exception
            java.lang.String r10 = "PushSelfShowLog"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r0 = "out download  error"
            r11.<init>(r0)
        L_0x0212:
            java.lang.String r0 = r9.toString()
            r11.append(r0)
            java.lang.String r11 = r11.toString()
            com.huawei.android.pushagent.a.a.c.c(r10, r11, r9)
        L_0x0220:
            r8.e = r1
            return r1
        L_0x0223:
            r9 = move-exception
            r7 = r0
            r0 = r11
            r11 = r3
            r3 = r7
        L_0x0228:
            a(r3)
            a(r2)
            if (r0 == 0) goto L_0x024c
            r0.close()     // Catch:{ IOException -> 0x0234 }
            goto L_0x024c
        L_0x0234:
            r0 = move-exception
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = " bos download  error"
            r2.<init>(r3)
            java.lang.String r3 = r0.toString()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.huawei.android.pushagent.a.a.c.c(r1, r2, r0)
        L_0x024c:
            if (r10 == 0) goto L_0x026a
            r10.close()     // Catch:{ IOException -> 0x0252 }
            goto L_0x026a
        L_0x0252:
            r10 = move-exception
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = " bis download  error"
            r1.<init>(r2)
            java.lang.String r2 = r10.toString()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.huawei.android.pushagent.a.a.c.c(r0, r1, r10)
        L_0x026a:
            if (r11 == 0) goto L_0x0288
            r11.close()     // Catch:{ Exception -> 0x0270 }
            goto L_0x0288
        L_0x0270:
            r10 = move-exception
            java.lang.String r11 = "PushSelfShowLog"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "out download  error"
            r0.<init>(r1)
            java.lang.String r1 = r10.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.huawei.android.pushagent.a.a.c.c(r11, r0, r10)
        L_0x0288:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.utils.b.b.b(android.content.Context, java.lang.String, java.lang.String):boolean");
    }

    public void c() {
        Message message = new Message();
        message.what = 2;
        StringBuilder sb = new StringBuilder("mDownloadHandler = ");
        sb.append(this.a);
        c.a("PushSelfShowLog", sb.toString());
        if (this.a != null) {
            this.a.sendMessageDelayed(message, 1);
        }
    }
}
