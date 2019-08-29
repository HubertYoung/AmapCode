package com.autonavi.indoor.onlinelocation;

import android.os.Handler;
import android.os.Message;
import com.autonavi.indoor.constant.Configuration;
import com.autonavi.indoor.util.L;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/* compiled from: NetworkRequestThread */
public class b extends Thread {
    public static a c = new a("SHUTDOWN", null);
    boolean a = false;
    protected Configuration b;
    private BlockingQueue<a> d = new ArrayBlockingQueue(1);
    private volatile boolean e;
    private volatile boolean f;
    private Handler g = null;

    /* compiled from: NetworkRequestThread */
    static class a {
        public String a;
        public byte[] b;

        public a(String str, byte[] bArr) {
            this.a = str;
            this.b = bArr;
        }
    }

    public b(Configuration configuration, Handler handler) {
        start();
        this.g = handler;
        this.b = configuration;
    }

    public void a(String str, byte[] bArr) {
        if (this.e || this.f) {
            if (L.isLogging) {
                L.d((String) "BuildingLocateThread has been stoped.");
            }
            return;
        }
        a aVar = new a(str, bArr);
        while (!this.d.offer(aVar)) {
            a aVar2 = (a) this.d.poll();
            if (L.isLogging) {
                StringBuilder sb = new StringBuilder("queen is full, poll one");
                sb.append(aVar2.b);
                L.d(sb.toString());
            }
        }
    }

    public void a() throws InterruptedException {
        if (this.e) {
            if (L.isLogging) {
                L.d((String) "Thread is shuttingDown...");
            }
            return;
        }
        this.e = true;
        this.a = false;
        if (L.isLogging) {
            L.d((String) "shutdown");
        }
        while (!this.d.offer(c)) {
            a aVar = (a) this.d.poll();
            if (L.isLogging) {
                StringBuilder sb = new StringBuilder("queen is full, clear:");
                sb.append(aVar.b);
                L.d(sb.toString());
            }
        }
        if (L.isLogging) {
            L.d((String) "stop BuildingLocateThread");
        }
    }

    public void run() {
        while (true) {
            try {
                if (this.e) {
                    if (L.isLogging) {
                        L.d((String) "Thread is shuttingDown.");
                    }
                    this.d.clear();
                } else {
                    a take = this.d.take();
                    if (take != c) {
                        a(take);
                    } else if (L.isLogging) {
                        L.d((String) "consum poison pill");
                    }
                }
            } catch (InterruptedException unused) {
                return;
            } finally {
                this.f = true;
            }
        }
        if (L.isLogging) {
            L.d((String) "end of run");
        }
        this.g = null;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(Handler handler) {
        return (handler == null || handler.getLooper() == null || handler.getLooper().getThread() == null || !handler.getLooper().getThread().isAlive()) ? false : true;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:109:0x029d=Splitter:B:109:0x029d, B:25:0x00cf=Splitter:B:25:0x00cf} */
    @android.support.annotation.RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(com.autonavi.indoor.onlinelocation.b.a r9) {
        /*
            r8 = this;
            r0 = 212(0xd4, float:2.97E-43)
            com.autonavi.indoor.constant.Configuration r1 = r8.b     // Catch:{ Throwable -> 0x02d0 }
            java.lang.String r1 = r1.getUrl()     // Catch:{ Throwable -> 0x02d0 }
            byte[] r9 = r9.b     // Catch:{ Throwable -> 0x02d0 }
            byte[] r2 = com.autonavi.indoor.util.MapUtils.amapEncode(r9)     // Catch:{ Throwable -> 0x02d0 }
            if (r2 == 0) goto L_0x001a
            boolean r9 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x02d0 }
            if (r9 == 0) goto L_0x0019
            java.lang.String r9 = "Request encryptData is null"
            com.autonavi.indoor.util.L.d(r9)     // Catch:{ Throwable -> 0x02d0 }
        L_0x0019:
            r9 = r2
        L_0x001a:
            boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x02d0 }
            if (r2 == 0) goto L_0x0038
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02d0 }
            java.lang.String r3 = "Request "
            r2.<init>(r3)     // Catch:{ Throwable -> 0x02d0 }
            r2.append(r1)     // Catch:{ Throwable -> 0x02d0 }
            java.lang.String r3 = ", buffer.length="
            r2.append(r3)     // Catch:{ Throwable -> 0x02d0 }
            int r3 = r9.length     // Catch:{ Throwable -> 0x02d0 }
            r2.append(r3)     // Catch:{ Throwable -> 0x02d0 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x02d0 }
            com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x02d0 }
        L_0x0038:
            boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x02d0 }
            r3 = 0
            if (r2 == 0) goto L_0x004e
            java.lang.String r2 = android.util.Base64.encodeToString(r9, r3)     // Catch:{ Throwable -> 0x02d0 }
            java.lang.String r4 = "Request body"
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x02d0 }
            java.lang.String r2 = r4.concat(r2)     // Catch:{ Throwable -> 0x02d0 }
            com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x02d0 }
        L_0x004e:
            java.net.URL r2 = new java.net.URL     // Catch:{ Throwable -> 0x02d0 }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x02d0 }
            java.net.URLConnection r2 = r2.openConnection()     // Catch:{ Throwable -> 0x02d0 }
            java.net.HttpURLConnection r2 = (java.net.HttpURLConnection) r2     // Catch:{ Throwable -> 0x02d0 }
            r4 = 10000(0x2710, float:1.4013E-41)
            r2.setReadTimeout(r4)     // Catch:{ Throwable -> 0x02a3 }
            r2.setConnectTimeout(r4)     // Catch:{ Throwable -> 0x02a3 }
            java.lang.String r4 = "POST"
            r2.setRequestMethod(r4)     // Catch:{ Throwable -> 0x02a3 }
            r4 = 1
            r2.setDoOutput(r4)     // Catch:{ Throwable -> 0x02a3 }
            r2.setDoInput(r4)     // Catch:{ Throwable -> 0x02a3 }
            r2.setUseCaches(r3)     // Catch:{ Throwable -> 0x02a3 }
            r2.setInstanceFollowRedirects(r4)     // Catch:{ Throwable -> 0x02a3 }
            java.lang.String r4 = "Accept-Language"
            java.lang.String r5 = "zh-CN"
            r2.setRequestProperty(r4, r5)     // Catch:{ Throwable -> 0x02a3 }
            java.lang.String r4 = "Charset"
            java.lang.String r5 = "UTF-8"
            r2.setRequestProperty(r4, r5)     // Catch:{ Throwable -> 0x02a3 }
            java.lang.String r4 = "Connection"
            java.lang.String r5 = "Keep-Alive"
            r2.setRequestProperty(r4, r5)     // Catch:{ Throwable -> 0x02a3 }
            com.autonavi.indoor.constant.Configuration r4 = r8.b     // Catch:{ Throwable -> 0x02a3 }
            com.autonavi.indoor.constant.Configuration$ServerType r4 = r4.mServerType     // Catch:{ Throwable -> 0x02a3 }
            com.autonavi.indoor.constant.Configuration$ServerType r5 = com.autonavi.indoor.constant.Configuration.ServerType.SERVER_LBS     // Catch:{ Throwable -> 0x02a3 }
            if (r4 != r5) goto L_0x0096
            com.autonavi.indoor.constant.Configuration r4 = r8.b     // Catch:{ Throwable -> 0x02a3 }
            com.autonavi.indoor.util.ProtocolHelper.setLBSHead(r2, r4, r1)     // Catch:{ Throwable -> 0x02a3 }
        L_0x0096:
            java.io.OutputStream r4 = r2.getOutputStream()     // Catch:{ Throwable -> 0x02a3 }
            r4.write(r9)     // Catch:{ Throwable -> 0x02a3 }
            r4.close()     // Catch:{ Throwable -> 0x02a3 }
            r2.connect()     // Catch:{ Throwable -> 0x02a3 }
            int r9 = r2.getResponseCode()     // Catch:{ Throwable -> 0x02a3 }
            r4 = 200(0xc8, float:2.8E-43)
            r5 = 213(0xd5, float:2.98E-43)
            if (r9 == r4) goto L_0x00d3
            r2.disconnect()     // Catch:{ Throwable -> 0x02a3 }
            java.lang.String r3 = "ResponseCode="
            java.lang.String r4 = java.lang.String.valueOf(r9)     // Catch:{ Throwable -> 0x02a3 }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ Throwable -> 0x02a3 }
            r8.a(r5, r3)     // Catch:{ Throwable -> 0x02a3 }
            boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x02a3 }
            if (r3 == 0) goto L_0x00cf
            java.lang.String r3 = "服务器建立连接失败，返回值错误code："
            java.lang.String r9 = java.lang.String.valueOf(r9)     // Catch:{ Throwable -> 0x02a3 }
            java.lang.String r9 = r3.concat(r9)     // Catch:{ Throwable -> 0x02a3 }
            com.autonavi.indoor.util.L.d(r9)     // Catch:{ Throwable -> 0x02a3 }
        L_0x00cf:
            r2.disconnect()     // Catch:{ Throwable -> 0x02d0 }
            return
        L_0x00d3:
            r2.getContentLength()     // Catch:{ Throwable -> 0x02a3 }
            java.lang.String r9 = r2.getContentType()     // Catch:{ Throwable -> 0x02a3 }
            boolean r4 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x02a3 }
            if (r4 == 0) goto L_0x00fb
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02a3 }
            java.lang.String r6 = "ResponseMessage="
            r4.<init>(r6)     // Catch:{ Throwable -> 0x02a3 }
            java.lang.String r6 = r2.getResponseMessage()     // Catch:{ Throwable -> 0x02a3 }
            r4.append(r6)     // Catch:{ Throwable -> 0x02a3 }
            java.lang.String r6 = ", ContentType="
            r4.append(r6)     // Catch:{ Throwable -> 0x02a3 }
            r4.append(r9)     // Catch:{ Throwable -> 0x02a3 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x02a3 }
            com.autonavi.indoor.util.L.d(r4)     // Catch:{ Throwable -> 0x02a3 }
        L_0x00fb:
            java.lang.String r4 = "application/json"
            boolean r9 = r9.startsWith(r4)     // Catch:{ Throwable -> 0x02a3 }
            if (r9 == 0) goto L_0x026f
            java.io.BufferedReader r9 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x02a3 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x02a3 }
            java.io.InputStream r4 = r2.getInputStream()     // Catch:{ Throwable -> 0x02a3 }
            r3.<init>(r4)     // Catch:{ Throwable -> 0x02a3 }
            r9.<init>(r3)     // Catch:{ Throwable -> 0x02a3 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02a3 }
            r3.<init>()     // Catch:{ Throwable -> 0x02a3 }
        L_0x0116:
            java.lang.String r4 = r9.readLine()     // Catch:{ Throwable -> 0x02a3 }
            if (r4 == 0) goto L_0x0131
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02a3 }
            r6.<init>()     // Catch:{ Throwable -> 0x02a3 }
            r6.append(r4)     // Catch:{ Throwable -> 0x02a3 }
            java.lang.String r4 = "\n"
            r6.append(r4)     // Catch:{ Throwable -> 0x02a3 }
            java.lang.String r4 = r6.toString()     // Catch:{ Throwable -> 0x02a3 }
            r3.append(r4)     // Catch:{ Throwable -> 0x02a3 }
            goto L_0x0116
        L_0x0131:
            r9.close()     // Catch:{ Throwable -> 0x02a3 }
            boolean r9 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x02a3 }
            if (r9 == 0) goto L_0x013f
            java.lang.String r9 = r3.toString()     // Catch:{ Throwable -> 0x02a3 }
            com.autonavi.indoor.util.L.d(r9)     // Catch:{ Throwable -> 0x02a3 }
        L_0x013f:
            org.json.JSONObject r9 = new org.json.JSONObject     // Catch:{ Throwable -> 0x02a3 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x02a3 }
            r9.<init>(r3)     // Catch:{ Throwable -> 0x02a3 }
            java.lang.String r3 = "status"
            boolean r3 = r9.has(r3)     // Catch:{ Throwable -> 0x02a3 }
            if (r3 == 0) goto L_0x022d
            java.lang.String r3 = "status"
            java.lang.String r3 = r9.getString(r3)     // Catch:{ Throwable -> 0x02a3 }
            boolean r4 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x02a3 }
            if (r4 == 0) goto L_0x0169
            java.lang.String r4 = "json status:"
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x02a3 }
            java.lang.String r3 = r4.concat(r3)     // Catch:{ Throwable -> 0x02a3 }
            com.autonavi.indoor.util.L.d(r3)     // Catch:{ Throwable -> 0x02a3 }
        L_0x0169:
            java.lang.String r3 = "info"
            java.lang.String r9 = r9.getString(r3)     // Catch:{ Throwable -> 0x02a3 }
            boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x02a3 }
            if (r3 == 0) goto L_0x0180
            java.lang.String r3 = "json info:"
            java.lang.String r4 = java.lang.String.valueOf(r9)     // Catch:{ Throwable -> 0x02a3 }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ Throwable -> 0x02a3 }
            com.autonavi.indoor.util.L.d(r3)     // Catch:{ Throwable -> 0x02a3 }
        L_0x0180:
            r3 = 721(0x2d1, float:1.01E-42)
            java.lang.String r4 = "OK"
            boolean r4 = r9.equals(r4)     // Catch:{ Throwable -> 0x02a3 }
            if (r4 == 0) goto L_0x0196
            boolean r9 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x02a3 }
            if (r9 == 0) goto L_0x0229
            java.lang.String r9 = "正常"
            com.autonavi.indoor.util.L.d(r9)     // Catch:{ Throwable -> 0x02a3 }
            goto L_0x0229
        L_0x0196:
            java.lang.String r4 = "INVALID_USER_KEY"
            boolean r4 = r9.equals(r4)     // Catch:{ Throwable -> 0x02a3 }
            if (r4 == 0) goto L_0x01ac
            r3 = 714(0x2ca, float:1.0E-42)
            boolean r9 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x02a3 }
            if (r9 == 0) goto L_0x0229
            java.lang.String r9 = "用户key非法或过期"
            com.autonavi.indoor.util.L.d(r9)     // Catch:{ Throwable -> 0x02a3 }
            goto L_0x0229
        L_0x01ac:
            java.lang.String r4 = "SERVICE_NOT_AVAILBALE"
            boolean r4 = r9.equals(r4)     // Catch:{ Throwable -> 0x02a3 }
            if (r4 == 0) goto L_0x01c1
            r3 = 715(0x2cb, float:1.002E-42)
            boolean r9 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x02a3 }
            if (r9 == 0) goto L_0x0229
            java.lang.String r9 = "请求服务不存在"
            com.autonavi.indoor.util.L.d(r9)     // Catch:{ Throwable -> 0x02a3 }
            goto L_0x0229
        L_0x01c1:
            java.lang.String r4 = "SERVICE_RESPONSE_ERROR"
            boolean r4 = r9.equals(r4)     // Catch:{ Throwable -> 0x02a3 }
            if (r4 == 0) goto L_0x01d6
            r3 = 716(0x2cc, float:1.003E-42)
            boolean r9 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x02a3 }
            if (r9 == 0) goto L_0x0229
            java.lang.String r9 = "请求服务响应错误"
            com.autonavi.indoor.util.L.d(r9)     // Catch:{ Throwable -> 0x02a3 }
            goto L_0x0229
        L_0x01d6:
            java.lang.String r4 = "INSUFFICIENT_PRIVILEGES"
            boolean r4 = r9.equals(r4)     // Catch:{ Throwable -> 0x02a3 }
            if (r4 == 0) goto L_0x01eb
            r3 = 717(0x2cd, float:1.005E-42)
            boolean r9 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x02a3 }
            if (r9 == 0) goto L_0x0229
            java.lang.String r9 = "无权限访问此服务"
            com.autonavi.indoor.util.L.d(r9)     // Catch:{ Throwable -> 0x02a3 }
            goto L_0x0229
        L_0x01eb:
            java.lang.String r4 = "OVER_QUOTA"
            boolean r4 = r9.equals(r4)     // Catch:{ Throwable -> 0x02a3 }
            if (r4 == 0) goto L_0x0200
            r3 = 718(0x2ce, float:1.006E-42)
            boolean r9 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x02a3 }
            if (r9 == 0) goto L_0x0229
            java.lang.String r9 = "请求超出配额"
            com.autonavi.indoor.util.L.d(r9)     // Catch:{ Throwable -> 0x02a3 }
            goto L_0x0229
        L_0x0200:
            java.lang.String r4 = "INVALID_PARAMS"
            boolean r4 = r9.equals(r4)     // Catch:{ Throwable -> 0x02a3 }
            if (r4 == 0) goto L_0x0215
            r3 = 719(0x2cf, float:1.008E-42)
            boolean r9 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x02a3 }
            if (r9 == 0) goto L_0x0229
            java.lang.String r9 = "请求参数非法"
            com.autonavi.indoor.util.L.d(r9)     // Catch:{ Throwable -> 0x02a3 }
            goto L_0x0229
        L_0x0215:
            java.lang.String r4 = "UNKNOWN_ERROR"
            boolean r9 = r9.equals(r4)     // Catch:{ Throwable -> 0x02a3 }
            if (r9 == 0) goto L_0x0229
            r3 = 720(0x2d0, float:1.009E-42)
            boolean r9 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x02a3 }
            if (r9 == 0) goto L_0x0229
            java.lang.String r9 = "未知错误"
            com.autonavi.indoor.util.L.d(r9)     // Catch:{ Throwable -> 0x02a3 }
        L_0x0229:
            r8.a(r3)     // Catch:{ Throwable -> 0x02a3 }
            goto L_0x025f
        L_0x022d:
            java.lang.String r3 = "result"
            boolean r3 = r9.has(r3)     // Catch:{ Throwable -> 0x02a3 }
            if (r3 == 0) goto L_0x025f
            java.lang.String r3 = "result"
            java.lang.String r3 = r9.getString(r3)     // Catch:{ Throwable -> 0x02a3 }
            boolean r4 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x02a3 }
            if (r4 == 0) goto L_0x024e
            java.lang.String r4 = "json result:"
            java.lang.String r6 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x02a3 }
            java.lang.String r4 = r4.concat(r6)     // Catch:{ Throwable -> 0x02a3 }
            com.autonavi.indoor.util.L.d(r4)     // Catch:{ Throwable -> 0x02a3 }
        L_0x024e:
            java.lang.String r4 = "false"
            boolean r3 = r3.equals(r4)     // Catch:{ Throwable -> 0x02a3 }
            if (r3 == 0) goto L_0x025f
            java.lang.String r3 = "message"
            java.lang.String r9 = r9.getString(r3)     // Catch:{ Throwable -> 0x02a3 }
            r8.a(r5, r9)     // Catch:{ Throwable -> 0x02a3 }
        L_0x025f:
            boolean r9 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x02a3 }
            if (r9 == 0) goto L_0x029d
            java.lang.Object r9 = r2.getContent()     // Catch:{ Throwable -> 0x02a3 }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x02a3 }
            com.autonavi.indoor.util.L.d(r9)     // Catch:{ Throwable -> 0x02a3 }
            goto L_0x029d
        L_0x026f:
            java.io.InputStream r9 = r2.getInputStream()     // Catch:{ Throwable -> 0x02a3 }
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x02a3 }
            r5 = 1024(0x400, float:1.435E-42)
            r4.<init>(r5)     // Catch:{ Throwable -> 0x02a3 }
            r5 = 512(0x200, float:7.175E-43)
            byte[] r5 = new byte[r5]     // Catch:{ Throwable -> 0x02a3 }
        L_0x027e:
            int r6 = r9.read(r5)     // Catch:{ Throwable -> 0x02a3 }
            r7 = -1
            if (r6 == r7) goto L_0x0289
            r4.write(r5, r3, r6)     // Catch:{ Throwable -> 0x02a3 }
            goto L_0x027e
        L_0x0289:
            r9.close()     // Catch:{ Throwable -> 0x02a3 }
            byte[] r9 = r4.toByteArray()     // Catch:{ Throwable -> 0x02a3 }
            r4.close()     // Catch:{ Throwable -> 0x02a3 }
            boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x02a3 }
            if (r3 == 0) goto L_0x029a
            com.autonavi.indoor.util.L.d(r9)     // Catch:{ Throwable -> 0x02a3 }
        L_0x029a:
            r8.a(r9)     // Catch:{ Throwable -> 0x02a3 }
        L_0x029d:
            r2.disconnect()     // Catch:{ Throwable -> 0x02d0 }
            return
        L_0x02a1:
            r9 = move-exception
            goto L_0x02cc
        L_0x02a3:
            r9 = move-exception
            boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x02a1 }
            if (r3 == 0) goto L_0x02c5
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x02a1 }
            java.lang.String r4 = "postRequest异常(在线定位)："
            r3.<init>(r4)     // Catch:{ all -> 0x02a1 }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x02a1 }
            r3.append(r9)     // Catch:{ all -> 0x02a1 }
            java.lang.String r9 = ", bid="
            r3.append(r9)     // Catch:{ all -> 0x02a1 }
            r3.append(r1)     // Catch:{ all -> 0x02a1 }
            java.lang.String r9 = r3.toString()     // Catch:{ all -> 0x02a1 }
            com.autonavi.indoor.util.L.d(r9)     // Catch:{ all -> 0x02a1 }
        L_0x02c5:
            r8.a(r0)     // Catch:{ all -> 0x02a1 }
            r2.disconnect()     // Catch:{ Throwable -> 0x02d0 }
            return
        L_0x02cc:
            r2.disconnect()     // Catch:{ Throwable -> 0x02d0 }
            throw r9     // Catch:{ Throwable -> 0x02d0 }
        L_0x02d0:
            r9 = move-exception
            boolean r1 = com.autonavi.indoor.util.L.isLogging
            if (r1 == 0) goto L_0x02e3
            java.lang.String r1 = "打开连接异常(在线定位)："
            java.lang.String r9 = java.lang.String.valueOf(r9)
            java.lang.String r9 = r1.concat(r9)
            com.autonavi.indoor.util.L.d(r9)
        L_0x02e3:
            r8.a(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.indoor.onlinelocation.b.a(com.autonavi.indoor.onlinelocation.b$a):void");
    }

    /* access modifiers changed from: 0000 */
    public void a(int i, Object obj) {
        try {
            if (a(this.g)) {
                Message obtain = Message.obtain(this.g, i);
                obtain.obj = obj;
                this.g.sendMessage(obtain);
            }
        } catch (Exception e2) {
            if (L.isLogging) {
                StringBuilder sb = new StringBuilder("sendMessage to handler failed:");
                sb.append(e2.toString());
                L.d(sb.toString());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int i) {
        try {
            if (a(this.g)) {
                this.g.sendEmptyMessage(i);
            }
        } catch (Exception e2) {
            if (L.isLogging) {
                StringBuilder sb = new StringBuilder("sendMessage to handler failed:");
                sb.append(e2.toString());
                L.d(sb.toString());
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:111:0x01ca  */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:63:0x0117=Splitter:B:63:0x0117, B:71:0x0158=Splitter:B:71:0x0158, B:79:0x0179=Splitter:B:79:0x0179} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(byte[] r18) {
        /*
            r17 = this;
            r1 = r17
            boolean r4 = com.autonavi.indoor.util.MapUtils.isEmpty(r18)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            r5 = 213(0xd5, float:2.98E-43)
            if (r4 == 0) goto L_0x0018
            boolean r4 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            if (r4 == 0) goto L_0x0011
            com.autonavi.indoor.util.L.d(r18)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
        L_0x0011:
            java.lang.String r4 = "response buffer is empty"
            r1.a(r5, r4)
            return
        L_0x0018:
            java.nio.ByteBuffer r4 = java.nio.ByteBuffer.wrap(r18)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            short r6 = r4.getShort()     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            r7 = 22
            if (r6 == r7) goto L_0x003b
            boolean r4 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            if (r4 == 0) goto L_0x0035
            java.lang.String r4 = "action_code:"
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            java.lang.String r4 = r4.concat(r6)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            com.autonavi.indoor.util.L.d(r4)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
        L_0x0035:
            java.lang.String r4 = "invalid action code"
            r1.a(r5, r4)
            return
        L_0x003b:
            int r7 = r4.getInt()     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            long r8 = r4.getLong()     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            int r10 = r4.getInt()     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            if (r10 == 0) goto L_0x0062
            boolean r4 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            if (r4 == 0) goto L_0x005b
            java.lang.String r4 = "status_code:"
            java.lang.String r6 = java.lang.String.valueOf(r10)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            java.lang.String r4 = r4.concat(r6)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            com.autonavi.indoor.util.L.d(r4)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
        L_0x005b:
            java.lang.String r4 = "status code error"
            r1.a(r5, r4)
            return
        L_0x0062:
            int r11 = r4.getInt()     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            byte r12 = r4.get()     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            byte r13 = r4.get()     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            boolean r14 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            if (r14 == 0) goto L_0x00ab
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            java.lang.String r15 = "decodeLocateResponse: action_code="
            r14.<init>(r15)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            r14.append(r6)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            java.lang.String r6 = ",action_version="
            r14.append(r6)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            r14.append(r7)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            java.lang.String r6 = ", status_code="
            r14.append(r6)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            r14.append(r10)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            java.lang.String r6 = ",len="
            r14.append(r6)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            r14.append(r11)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            java.lang.String r6 = ",LocationType(0GPS 1Wifi 2BLE)="
            r14.append(r6)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            r14.append(r12)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            java.lang.String r6 = ",Returncode="
            r14.append(r6)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            r14.append(r13)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            java.lang.String r6 = r14.toString()     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            com.autonavi.indoor.util.L.d(r6)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
        L_0x00ab:
            if (r13 >= 0) goto L_0x00cf
            boolean r4 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            if (r4 == 0) goto L_0x00bf
            java.lang.String r4 = "returncode:"
            java.lang.String r6 = java.lang.String.valueOf(r13)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            java.lang.String r4 = r4.concat(r6)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            com.autonavi.indoor.util.L.d(r4)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
        L_0x00bf:
            java.lang.String r4 = "error return code:"
            java.lang.String r6 = java.lang.String.valueOf(r13)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            java.lang.String r4 = r4.concat(r6)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            if (r4 == 0) goto L_0x00ce
            r1.a(r5, r4)
        L_0x00ce:
            return
        L_0x00cf:
            java.lang.String r6 = com.autonavi.indoor.util.MapUtils.getString(r4)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            byte r7 = r4.get()     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            if (r7 <= 0) goto L_0x019a
            double r10 = r4.getDouble()     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            r12 = -4582834833314545664(0xc066800000000000, double:-180.0)
            int r7 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r7 < 0) goto L_0x0179
            r14 = 4640537203540230144(0x4066800000000000, double:180.0)
            int r7 = (r10 > r14 ? 1 : (r10 == r14 ? 0 : -1))
            if (r7 <= 0) goto L_0x00f1
            goto L_0x0179
        L_0x00f1:
            double r2 = r4.getDouble()     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            int r7 = (r2 > r12 ? 1 : (r2 == r12 ? 0 : -1))
            if (r7 < 0) goto L_0x0158
            int r7 = (r2 > r14 ? 1 : (r2 == r14 ? 0 : -1))
            if (r7 <= 0) goto L_0x00fe
            goto L_0x0158
        L_0x00fe:
            r12 = 0
            int r7 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r7 != 0) goto L_0x0117
            int r7 = (r2 > r12 ? 1 : (r2 == r12 ? 0 : -1))
            if (r7 != 0) goto L_0x0117
            boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            if (r2 == 0) goto L_0x0111
            java.lang.String r2 = "invalid x=0,y=0"
            com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
        L_0x0111:
            java.lang.String r2 = "invalid x, y, both are zero."
            r1.a(r5, r2)
            return
        L_0x0117:
            com.autonavi.indoor.onlinelocation.a r5 = new com.autonavi.indoor.onlinelocation.a     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            r5.<init>()     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            r5.a = r10     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            r5.b = r2     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            byte r2 = r4.get()     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            r5.c = r2     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            byte r2 = r4.get()     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            float r2 = (float) r2     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            r5.d = r2     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            float r2 = r4.getFloat()     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            double r2 = (double) r2     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            r5.g = r2     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            float r2 = r4.getFloat()     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            double r2 = (double) r2     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            r5.h = r2     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            float r2 = r4.getFloat()     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            double r2 = (double) r2     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            r5.i = r2     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            float r2 = r4.getFloat()     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            double r2 = (double) r2     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            r5.k = r2     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            r5.j = r8     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            r5.l = r6     // Catch:{ Throwable -> 0x0154, all -> 0x014e }
            goto L_0x019b
        L_0x014e:
            r0 = move-exception
            r3 = r0
            r2 = 214(0xd6, float:3.0E-43)
            goto L_0x01c8
        L_0x0154:
            r0 = move-exception
            r2 = r0
            r3 = r5
            goto L_0x01ad
        L_0x0158:
            boolean r4 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            if (r4 == 0) goto L_0x0169
            java.lang.String r4 = "invalid y:"
            java.lang.String r6 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            java.lang.String r4 = r4.concat(r6)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            com.autonavi.indoor.util.L.d(r4)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
        L_0x0169:
            java.lang.String r4 = "invalid y:"
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            java.lang.String r2 = r4.concat(r2)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            if (r2 == 0) goto L_0x0178
            r1.a(r5, r2)
        L_0x0178:
            return
        L_0x0179:
            boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            if (r2 == 0) goto L_0x018a
            java.lang.String r2 = "invalid x:"
            java.lang.String r3 = java.lang.String.valueOf(r10)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            java.lang.String r2 = r2.concat(r3)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
        L_0x018a:
            java.lang.String r2 = "invalid x:"
            java.lang.String r3 = java.lang.String.valueOf(r10)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            java.lang.String r2 = r2.concat(r3)     // Catch:{ Throwable -> 0x01aa, all -> 0x01a4 }
            if (r2 == 0) goto L_0x0199
            r1.a(r5, r2)
        L_0x0199:
            return
        L_0x019a:
            r5 = 0
        L_0x019b:
            if (r5 == 0) goto L_0x01a3
            r2 = 214(0xd6, float:3.0E-43)
            r1.a(r2, r5)
            return
        L_0x01a3:
            return
        L_0x01a4:
            r0 = move-exception
            r3 = r0
            r2 = 214(0xd6, float:3.0E-43)
            r5 = 0
            goto L_0x01c8
        L_0x01aa:
            r0 = move-exception
            r2 = r0
            r3 = 0
        L_0x01ad:
            boolean r4 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x01c3 }
            if (r4 == 0) goto L_0x01ba
            com.autonavi.indoor.util.L.d(r2)     // Catch:{ all -> 0x01b5 }
            goto L_0x01ba
        L_0x01b5:
            r0 = move-exception
            r5 = r3
            r2 = 214(0xd6, float:3.0E-43)
            goto L_0x01c7
        L_0x01ba:
            if (r3 == 0) goto L_0x01c2
            r2 = 214(0xd6, float:3.0E-43)
            r1.a(r2, r3)
            return
        L_0x01c2:
            return
        L_0x01c3:
            r0 = move-exception
            r2 = 214(0xd6, float:3.0E-43)
            r5 = r3
        L_0x01c7:
            r3 = r0
        L_0x01c8:
            if (r5 == 0) goto L_0x01cd
            r1.a(r2, r5)
        L_0x01cd:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.indoor.onlinelocation.b.a(byte[]):void");
    }
}
