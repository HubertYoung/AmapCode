package com.autonavi.link.connect.wifi.a;

import android.os.Handler;
import com.autonavi.link.utils.Logger;
import java.net.Socket;

/* compiled from: ClientReceiveThread */
public class a extends Thread {
    private static final String a = "a";
    private final int b = 1;
    private final String c;
    private final int d;
    private boolean e = true;
    private boolean f = false;
    private int g = 0;
    private Socket h;
    private Handler i;

    public a(String str, int i2, Handler handler) {
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append(" , ClientReceiveThread init ");
        Logger.d((String) "hehe", sb.toString(), new Object[0]);
        this.i = handler;
        this.c = str;
        this.d = i2;
    }

    public void interrupt() {
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append(" , ClientReceiveThread interrupt ");
        Logger.d((String) "hehe", sb.toString(), new Object[0]);
        this.e = false;
        this.f = true;
        try {
            if (this.h != null && !this.h.isClosed()) {
                this.h.close();
                this.h = null;
            }
        } catch (Exception unused) {
        }
        super.interrupt();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:100:0x03d6, code lost:
        r10.h.close();
        r10.h = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x03dd, code lost:
        r3 = new java.lang.StringBuilder();
        r3.append(a);
        r3.append(" , ClientReceiveThread finally sendEmptyMessage MSG_CLIENT_STATE_DISCONNECT--> begin");
        com.autonavi.link.utils.Logger.d((java.lang.String) "hehe", r3.toString(), new java.lang.Object[0]);
        r10.i.sendEmptyMessage(102);
        r0 = "hehe";
        r1 = new java.lang.StringBuilder();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x0420, code lost:
        r5 = new java.lang.StringBuilder();
        r5.append(a);
        r5.append(" , ClientReceiveThread finally ");
        com.autonavi.link.utils.Logger.d((java.lang.String) "hehe", r5.toString(), new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x004a, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x0446, code lost:
        r10.h.close();
        r10.h = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x044d, code lost:
        r4 = new java.lang.StringBuilder();
        r4.append(a);
        r4.append(" , ClientReceiveThread finally sendEmptyMessage MSG_CLIENT_STATE_DISCONNECT--> begin");
        com.autonavi.link.utils.Logger.d((java.lang.String) "hehe", r4.toString(), new java.lang.Object[0]);
        r10.i.sendEmptyMessage(102);
        r3 = new java.lang.StringBuilder();
        r3.append(a);
        r3.append(" , ClientReceiveThread finally sendEmptyMessage MSG_CLIENT_STATE_DISCONNECT--> success");
        com.autonavi.link.utils.Logger.d((java.lang.String) "hehe", r3.toString(), new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x0486, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x004d, code lost:
        if ((r4 instanceof java.net.SocketTimeoutException) != false) goto L_0x004f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x004f, code lost:
        r7 = new java.lang.StringBuilder();
        r7.append(a);
        r7.append(" , ClientReceiveThread connect SocketTimeoutException---> ");
        r7.append(r4);
        r7.append(", time--> ");
        r7.append(r0);
        com.autonavi.link.utils.Logger.d((java.lang.String) "hehe", r7.toString(), new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0074, code lost:
        if (r0 == 1) goto L_0x0076;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0076, code lost:
        r4 = new java.lang.StringBuilder();
        r4.append(a);
        r4.append(" , ClientReceiveThread connect IOException---> chong lian wu ci shi bai tui chu");
        com.autonavi.link.utils.Logger.d((java.lang.String) "hehe", r4.toString(), new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0092, code lost:
        sleep(2000);
        r4 = r0 + 1;
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x009d, code lost:
        r4 = new java.lang.StringBuilder();
        r4.append(a);
        r4.append(" , ClientReceiveThread connect success");
        com.autonavi.link.utils.Logger.d((java.lang.String) "hehe", r4.toString(), new java.lang.Object[0]);
        r10.f = true;
        r10.e = true;
        r0 = r10.h.getOutputStream();
        r4 = r10.h.getInputStream();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00c9, code lost:
        if (r10.g != 0) goto L_0x0120;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00cb, code lost:
        r0.write(com.autonavi.link.connect.b.a.b(com.autonavi.link.LinkSDK.getInstance().getWifiInstance().getLocalAddress(), "8721", null));
        r0.flush();
        r0 = com.autonavi.link.connect.b.a.a(r4);
        r6 = new com.autonavi.link.connect.model.DiscoverInfo();
        r6.deviceName = r0.i;
        r6.httpPort = "8721";
        r6.IP = r0.f;
        r6.sdkVersion = r0.b;
        r6.appId = r0.j;
        r6.appVersion = r0.l;
        r6.appName = r0.k;
        com.autonavi.link.connect.a.b.a().a(r0.f, "8721", r0.m);
        r10.i.obtainMessage(101, r6).sendToTarget();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0120, code lost:
        r10.g = 0;
        r6 = new java.lang.StringBuilder();
        r6.append(a);
        r6.append(" , ClientReceiveThread run socket begin");
        com.autonavi.link.utils.Logger.d((java.lang.String) "hehe", r6.toString(), new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x013e, code lost:
        if (r10.e == false) goto L_0x01d0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0140, code lost:
        sleep(500);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r6 = new java.lang.StringBuilder();
        r6.append(a);
        r6.append(" , ClientReceiveThread run read-->  begin");
        com.autonavi.link.utils.Logger.d((java.lang.String) "hehe", r6.toString(), new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0163, code lost:
        if (r4.read() >= 0) goto L_0x013c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0165, code lost:
        r6 = new java.lang.StringBuilder();
        r6.append(a);
        r6.append(" , ClientReceiveThread run read--> error  -1");
        com.autonavi.link.utils.Logger.d((java.lang.String) "hehe", r6.toString(), new java.lang.Object[0]);
        r10.e = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x01d2, code lost:
        if (r10.h == null) goto L_0x01e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x01da, code lost:
        if (r10.h.isClosed() != false) goto L_0x01e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x01dc, code lost:
        r10.h.close();
        r10.h = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x021c, code lost:
        r6 = new java.lang.StringBuilder();
        r6.append(a);
        r6.append(" , ClientReceiveThread connect other IOException---> ");
        r6.append(r4);
        r6.append(", time--> ");
        r6.append(r0);
        com.autonavi.link.utils.Logger.d((java.lang.String) "hehe", r6.toString(), new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x02a9, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x02ac, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:?, code lost:
        r5 = new java.lang.StringBuilder();
        r5.append(a);
        r5.append(" , ClientReceiveThread Exception ");
        r5.append(r0);
        com.autonavi.link.utils.Logger.d((java.lang.String) "hehe", r5.toString(), new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x02ca, code lost:
        r4 = new java.lang.StringBuilder();
        r4.append(a);
        r4.append(" , ClientReceiveThread finally ");
        com.autonavi.link.utils.Logger.d((java.lang.String) "hehe", r4.toString(), new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x02f0, code lost:
        r10.h.close();
        r10.h = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x031f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:?, code lost:
        r5 = new java.lang.StringBuilder();
        r5.append(a);
        r5.append(" , ClientReceiveThread JSONException ");
        r5.append(r0);
        com.autonavi.link.utils.Logger.d((java.lang.String) "hehe", r5.toString(), new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x033d, code lost:
        r4 = new java.lang.StringBuilder();
        r4.append(a);
        r4.append(" , ClientReceiveThread finally ");
        com.autonavi.link.utils.Logger.d((java.lang.String) "hehe", r4.toString(), new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0363, code lost:
        r10.h.close();
        r10.h = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x0392, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:?, code lost:
        r5 = new java.lang.StringBuilder();
        r5.append(a);
        r5.append(" , ClientReceiveThread IOException ");
        r5.append(r0);
        com.autonavi.link.utils.Logger.d((java.lang.String) "hehe", r5.toString(), new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x03b0, code lost:
        r4 = new java.lang.StringBuilder();
        r4.append(a);
        r4.append(" , ClientReceiveThread finally ");
        com.autonavi.link.utils.Logger.d((java.lang.String) "hehe", r4.toString(), new java.lang.Object[0]);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x02ac A[ExcHandler: Exception (r0v23 'e' java.lang.Exception A[CUSTOM_DECLARE]), Splitter:B:3:0x001f] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x031f A[ExcHandler: JSONException (r0v13 'e' org.json.JSONException A[CUSTOM_DECLARE]), Splitter:B:3:0x001f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r10 = this;
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = a
            r1.append(r2)
            java.lang.String r2 = " , ClientReceiveThread run "
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.autonavi.link.utils.Logger.d(r0, r1, r3)
        L_0x001b:
            r0 = 0
        L_0x001c:
            r1 = 102(0x66, float:1.43E-43)
            r3 = 0
            boolean r4 = r10.f     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            if (r4 != 0) goto L_0x0241
            java.net.Socket r4 = new java.net.Socket     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r4.<init>()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r10.h = r4     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.net.Socket r4 = r10.h     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r5 = 5000(0x1388, float:7.006E-42)
            r4.setSoTimeout(r5)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.net.Socket r4 = r10.h     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r5 = 1
            r4.setTcpNoDelay(r5)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.net.Socket r4 = r10.h     // Catch:{ IOException -> 0x004a, JSONException -> 0x031f, Exception -> 0x02ac }
            java.net.InetSocketAddress r6 = new java.net.InetSocketAddress     // Catch:{ IOException -> 0x004a, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r7 = r10.c     // Catch:{ IOException -> 0x004a, JSONException -> 0x031f, Exception -> 0x02ac }
            int r8 = r10.d     // Catch:{ IOException -> 0x004a, JSONException -> 0x031f, Exception -> 0x02ac }
            r6.<init>(r7, r8)     // Catch:{ IOException -> 0x004a, JSONException -> 0x031f, Exception -> 0x02ac }
            r7 = 3000(0xbb8, float:4.204E-42)
            r4.connect(r6, r7)     // Catch:{ IOException -> 0x004a, JSONException -> 0x031f, Exception -> 0x02ac }
            r4 = r0
            r0 = 1
            goto L_0x009b
        L_0x004a:
            r4 = move-exception
            boolean r6 = r4 instanceof java.net.SocketTimeoutException     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            if (r6 == 0) goto L_0x021c
            java.lang.String r6 = "hehe"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r7.<init>()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r8 = a     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r7.append(r8)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r8 = " , ClientReceiveThread connect SocketTimeoutException---> "
            r7.append(r8)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r7.append(r4)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r4 = ", time--> "
            r7.append(r4)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r7.append(r0)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r4 = r7.toString()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.Object[] r7 = new java.lang.Object[r2]     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            com.autonavi.link.utils.Logger.d(r6, r4, r7)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            if (r0 != r5) goto L_0x0092
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r4.<init>()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r5 = a     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r4.append(r5)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r5 = " , ClientReceiveThread connect IOException---> chong lian wu ci shi bai tui chu"
            r4.append(r5)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            com.autonavi.link.utils.Logger.d(r0, r4, r5)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            goto L_0x0241
        L_0x0092:
            r6 = 2000(0x7d0, double:9.88E-321)
            sleep(r6)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            int r0 = r0 + 1
            r4 = r0
            r0 = 0
        L_0x009b:
            if (r0 == 0) goto L_0x01ff
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r4.<init>()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r6 = a     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r4.append(r6)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r6 = " , ClientReceiveThread connect success"
            r4.append(r6)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.Object[] r6 = new java.lang.Object[r2]     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            com.autonavi.link.utils.Logger.d(r0, r4, r6)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r10.f = r5     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r10.e = r5     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.net.Socket r0 = r10.h     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.io.OutputStream r0 = r0.getOutputStream()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.net.Socket r4 = r10.h     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.io.InputStream r4 = r4.getInputStream()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            int r6 = r10.g     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            if (r6 != 0) goto L_0x0120
            com.autonavi.link.LinkSDK r6 = com.autonavi.link.LinkSDK.getInstance()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            com.autonavi.link.connect.wifi.ShareNetManager r6 = r6.getWifiInstance()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r6 = r6.getLocalAddress()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r7 = "8721"
            byte[] r6 = com.autonavi.link.connect.b.a.b(r6, r7, r3)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r0.write(r6)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r0.flush()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            com.autonavi.link.connect.model.b r0 = com.autonavi.link.connect.b.a.a(r4)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            com.autonavi.link.connect.model.DiscoverInfo r6 = new com.autonavi.link.connect.model.DiscoverInfo     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r6.<init>()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r7 = r0.i     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r6.deviceName = r7     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r7 = "8721"
            r6.httpPort = r7     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r7 = r0.f     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r6.IP = r7     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r7 = r0.b     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r6.sdkVersion = r7     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r7 = r0.j     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r6.appId = r7     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r7 = r0.l     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r6.appVersion = r7     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r7 = r0.k     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r6.appName = r7     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            com.autonavi.link.connect.a.b r7 = com.autonavi.link.connect.a.b.a()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r8 = r0.f     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r9 = "8721"
            java.lang.String r0 = r0.m     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r7.a(r8, r9, r0)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            android.os.Handler r0 = r10.i     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r7 = 101(0x65, float:1.42E-43)
            android.os.Message r0 = r0.obtainMessage(r7, r6)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r0.sendToTarget()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
        L_0x0120:
            r10.g = r2     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r6.<init>()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r7 = a     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r6.append(r7)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r7 = " , ClientReceiveThread run socket begin"
            r6.append(r7)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r6 = r6.toString()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.Object[] r7 = new java.lang.Object[r2]     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            com.autonavi.link.utils.Logger.d(r0, r6, r7)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
        L_0x013c:
            boolean r0 = r10.e     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            if (r0 == 0) goto L_0x01d0
            r6 = 500(0x1f4, double:2.47E-321)
            sleep(r6)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0182, JSONException -> 0x031f, Exception -> 0x02ac }
            r6.<init>()     // Catch:{ IOException -> 0x0182, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r7 = a     // Catch:{ IOException -> 0x0182, JSONException -> 0x031f, Exception -> 0x02ac }
            r6.append(r7)     // Catch:{ IOException -> 0x0182, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r7 = " , ClientReceiveThread run read-->  begin"
            r6.append(r7)     // Catch:{ IOException -> 0x0182, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r6 = r6.toString()     // Catch:{ IOException -> 0x0182, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.Object[] r7 = new java.lang.Object[r2]     // Catch:{ IOException -> 0x0182, JSONException -> 0x031f, Exception -> 0x02ac }
            com.autonavi.link.utils.Logger.d(r0, r6, r7)     // Catch:{ IOException -> 0x0182, JSONException -> 0x031f, Exception -> 0x02ac }
            int r0 = r4.read()     // Catch:{ IOException -> 0x0182, JSONException -> 0x031f, Exception -> 0x02ac }
            if (r0 >= 0) goto L_0x013c
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0182, JSONException -> 0x031f, Exception -> 0x02ac }
            r6.<init>()     // Catch:{ IOException -> 0x0182, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r7 = a     // Catch:{ IOException -> 0x0182, JSONException -> 0x031f, Exception -> 0x02ac }
            r6.append(r7)     // Catch:{ IOException -> 0x0182, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r7 = " , ClientReceiveThread run read--> error  -1"
            r6.append(r7)     // Catch:{ IOException -> 0x0182, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r6 = r6.toString()     // Catch:{ IOException -> 0x0182, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.Object[] r7 = new java.lang.Object[r2]     // Catch:{ IOException -> 0x0182, JSONException -> 0x031f, Exception -> 0x02ac }
            com.autonavi.link.utils.Logger.d(r0, r6, r7)     // Catch:{ IOException -> 0x0182, JSONException -> 0x031f, Exception -> 0x02ac }
            r10.e = r2     // Catch:{ IOException -> 0x0182, JSONException -> 0x031f, Exception -> 0x02ac }
            goto L_0x013c
        L_0x0182:
            r0 = move-exception
            java.lang.String r6 = "hehe"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r7.<init>()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r8 = a     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r7.append(r8)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r8 = " , ClientReceiveThread run read--> IOException "
            r7.append(r8)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r7.append(r0)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r7 = r7.toString()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.Object[] r8 = new java.lang.Object[r2]     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            com.autonavi.link.utils.Logger.d(r6, r7, r8)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            boolean r0 = r0 instanceof java.net.SocketTimeoutException     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            if (r0 == 0) goto L_0x013c
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r6.<init>()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r7 = a     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r6.append(r7)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r7 = " , ClientReceiveThread run read--> SocketTimeoutException"
            r6.append(r7)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r6 = r6.toString()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.Object[] r7 = new java.lang.Object[r2]     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            com.autonavi.link.utils.Logger.d(r0, r6, r7)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r10.e = r2     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            int r0 = r10.g     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            if (r0 <= 0) goto L_0x01c6
            r0 = 1
            goto L_0x01c7
        L_0x01c6:
            r0 = 0
        L_0x01c7:
            r10.f = r0     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            int r0 = r10.g     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            int r0 = r0 + r5
            r10.g = r0     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            goto L_0x013c
        L_0x01d0:
            java.net.Socket r0 = r10.h     // Catch:{ IOException -> 0x01e3, JSONException -> 0x031f, Exception -> 0x02ac }
            if (r0 == 0) goto L_0x01e3
            java.net.Socket r0 = r10.h     // Catch:{ IOException -> 0x01e3, JSONException -> 0x031f, Exception -> 0x02ac }
            boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x01e3, JSONException -> 0x031f, Exception -> 0x02ac }
            if (r0 != 0) goto L_0x01e3
            java.net.Socket r0 = r10.h     // Catch:{ IOException -> 0x01e3, JSONException -> 0x031f, Exception -> 0x02ac }
            r0.close()     // Catch:{ IOException -> 0x01e3, JSONException -> 0x031f, Exception -> 0x02ac }
            r10.h = r3     // Catch:{ IOException -> 0x01e3, JSONException -> 0x031f, Exception -> 0x02ac }
        L_0x01e3:
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r4.<init>()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r5 = a     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r4.append(r5)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r5 = " , ClientReceiveThread run socket end"
            r4.append(r5)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            com.autonavi.link.utils.Logger.d(r0, r4, r5)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            goto L_0x001b
        L_0x01ff:
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r5.<init>()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r6 = a     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r5.append(r6)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r6 = " , ClientReceiveThread connect success"
            r5.append(r6)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.Object[] r6 = new java.lang.Object[r2]     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            com.autonavi.link.utils.Logger.d(r0, r5, r6)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r0 = r4
            goto L_0x001c
        L_0x021c:
            java.lang.String r5 = "hehe"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r6.<init>()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r7 = a     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r6.append(r7)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r7 = " , ClientReceiveThread connect other IOException---> "
            r6.append(r7)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r6.append(r4)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r4 = ", time--> "
            r6.append(r4)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            r6.append(r0)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.String r0 = r6.toString()     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
            com.autonavi.link.utils.Logger.d(r5, r0, r4)     // Catch:{ IOException -> 0x0392, JSONException -> 0x031f, Exception -> 0x02ac }
        L_0x0241:
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = a
            r4.append(r5)
            java.lang.String r5 = " , ClientReceiveThread finally "
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.Object[] r5 = new java.lang.Object[r2]
            com.autonavi.link.utils.Logger.d(r0, r4, r5)
            java.net.Socket r0 = r10.h     // Catch:{ IOException -> 0x026e }
            if (r0 == 0) goto L_0x026e
            java.net.Socket r0 = r10.h     // Catch:{ IOException -> 0x026e }
            boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x026e }
            if (r0 != 0) goto L_0x026e
            java.net.Socket r0 = r10.h     // Catch:{ IOException -> 0x026e }
            r0.close()     // Catch:{ IOException -> 0x026e }
            r10.h = r3     // Catch:{ IOException -> 0x026e }
        L_0x026e:
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = a
            r3.append(r4)
            java.lang.String r4 = " , ClientReceiveThread finally sendEmptyMessage MSG_CLIENT_STATE_DISCONNECT--> begin"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            java.lang.Object[] r4 = new java.lang.Object[r2]
            com.autonavi.link.utils.Logger.d(r0, r3, r4)
            android.os.Handler r0 = r10.i
            r0.sendEmptyMessage(r1)
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
        L_0x0294:
            java.lang.String r3 = a
            r1.append(r3)
            java.lang.String r3 = " , ClientReceiveThread finally sendEmptyMessage MSG_CLIENT_STATE_DISCONNECT--> success"
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.autonavi.link.utils.Logger.d(r0, r1, r3)
            goto L_0x0405
        L_0x02a9:
            r0 = move-exception
            goto L_0x0420
        L_0x02ac:
            r0 = move-exception
            java.lang.String r4 = "hehe"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x02a9 }
            r5.<init>()     // Catch:{ all -> 0x02a9 }
            java.lang.String r6 = a     // Catch:{ all -> 0x02a9 }
            r5.append(r6)     // Catch:{ all -> 0x02a9 }
            java.lang.String r6 = " , ClientReceiveThread Exception "
            r5.append(r6)     // Catch:{ all -> 0x02a9 }
            r5.append(r0)     // Catch:{ all -> 0x02a9 }
            java.lang.String r0 = r5.toString()     // Catch:{ all -> 0x02a9 }
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ all -> 0x02a9 }
            com.autonavi.link.utils.Logger.d(r4, r0, r5)     // Catch:{ all -> 0x02a9 }
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = a
            r4.append(r5)
            java.lang.String r5 = " , ClientReceiveThread finally "
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.Object[] r5 = new java.lang.Object[r2]
            com.autonavi.link.utils.Logger.d(r0, r4, r5)
            java.net.Socket r0 = r10.h     // Catch:{ IOException -> 0x02f7 }
            if (r0 == 0) goto L_0x02f7
            java.net.Socket r0 = r10.h     // Catch:{ IOException -> 0x02f7 }
            boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x02f7 }
            if (r0 != 0) goto L_0x02f7
            java.net.Socket r0 = r10.h     // Catch:{ IOException -> 0x02f7 }
            r0.close()     // Catch:{ IOException -> 0x02f7 }
            r10.h = r3     // Catch:{ IOException -> 0x02f7 }
        L_0x02f7:
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = a
            r3.append(r4)
            java.lang.String r4 = " , ClientReceiveThread finally sendEmptyMessage MSG_CLIENT_STATE_DISCONNECT--> begin"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            java.lang.Object[] r4 = new java.lang.Object[r2]
            com.autonavi.link.utils.Logger.d(r0, r3, r4)
            android.os.Handler r0 = r10.i
            r0.sendEmptyMessage(r1)
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            goto L_0x0294
        L_0x031f:
            r0 = move-exception
            java.lang.String r4 = "hehe"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x02a9 }
            r5.<init>()     // Catch:{ all -> 0x02a9 }
            java.lang.String r6 = a     // Catch:{ all -> 0x02a9 }
            r5.append(r6)     // Catch:{ all -> 0x02a9 }
            java.lang.String r6 = " , ClientReceiveThread JSONException "
            r5.append(r6)     // Catch:{ all -> 0x02a9 }
            r5.append(r0)     // Catch:{ all -> 0x02a9 }
            java.lang.String r0 = r5.toString()     // Catch:{ all -> 0x02a9 }
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ all -> 0x02a9 }
            com.autonavi.link.utils.Logger.d(r4, r0, r5)     // Catch:{ all -> 0x02a9 }
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = a
            r4.append(r5)
            java.lang.String r5 = " , ClientReceiveThread finally "
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.Object[] r5 = new java.lang.Object[r2]
            com.autonavi.link.utils.Logger.d(r0, r4, r5)
            java.net.Socket r0 = r10.h     // Catch:{ IOException -> 0x036a }
            if (r0 == 0) goto L_0x036a
            java.net.Socket r0 = r10.h     // Catch:{ IOException -> 0x036a }
            boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x036a }
            if (r0 != 0) goto L_0x036a
            java.net.Socket r0 = r10.h     // Catch:{ IOException -> 0x036a }
            r0.close()     // Catch:{ IOException -> 0x036a }
            r10.h = r3     // Catch:{ IOException -> 0x036a }
        L_0x036a:
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = a
            r3.append(r4)
            java.lang.String r4 = " , ClientReceiveThread finally sendEmptyMessage MSG_CLIENT_STATE_DISCONNECT--> begin"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            java.lang.Object[] r4 = new java.lang.Object[r2]
            com.autonavi.link.utils.Logger.d(r0, r3, r4)
            android.os.Handler r0 = r10.i
            r0.sendEmptyMessage(r1)
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            goto L_0x0294
        L_0x0392:
            r0 = move-exception
            java.lang.String r4 = "hehe"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x02a9 }
            r5.<init>()     // Catch:{ all -> 0x02a9 }
            java.lang.String r6 = a     // Catch:{ all -> 0x02a9 }
            r5.append(r6)     // Catch:{ all -> 0x02a9 }
            java.lang.String r6 = " , ClientReceiveThread IOException "
            r5.append(r6)     // Catch:{ all -> 0x02a9 }
            r5.append(r0)     // Catch:{ all -> 0x02a9 }
            java.lang.String r0 = r5.toString()     // Catch:{ all -> 0x02a9 }
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ all -> 0x02a9 }
            com.autonavi.link.utils.Logger.d(r4, r0, r5)     // Catch:{ all -> 0x02a9 }
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = a
            r4.append(r5)
            java.lang.String r5 = " , ClientReceiveThread finally "
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.Object[] r5 = new java.lang.Object[r2]
            com.autonavi.link.utils.Logger.d(r0, r4, r5)
            java.net.Socket r0 = r10.h     // Catch:{ IOException -> 0x03dd }
            if (r0 == 0) goto L_0x03dd
            java.net.Socket r0 = r10.h     // Catch:{ IOException -> 0x03dd }
            boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x03dd }
            if (r0 != 0) goto L_0x03dd
            java.net.Socket r0 = r10.h     // Catch:{ IOException -> 0x03dd }
            r0.close()     // Catch:{ IOException -> 0x03dd }
            r10.h = r3     // Catch:{ IOException -> 0x03dd }
        L_0x03dd:
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = a
            r3.append(r4)
            java.lang.String r4 = " , ClientReceiveThread finally sendEmptyMessage MSG_CLIENT_STATE_DISCONNECT--> begin"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            java.lang.Object[] r4 = new java.lang.Object[r2]
            com.autonavi.link.utils.Logger.d(r0, r3, r4)
            android.os.Handler r0 = r10.i
            r0.sendEmptyMessage(r1)
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            goto L_0x0294
        L_0x0405:
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = a
            r1.append(r3)
            java.lang.String r3 = " , ClientReceiveThread end "
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.autonavi.link.utils.Logger.d(r0, r1, r2)
            return
        L_0x0420:
            java.lang.String r4 = "hehe"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = a
            r5.append(r6)
            java.lang.String r6 = " , ClientReceiveThread finally "
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            java.lang.Object[] r6 = new java.lang.Object[r2]
            com.autonavi.link.utils.Logger.d(r4, r5, r6)
            java.net.Socket r4 = r10.h     // Catch:{ IOException -> 0x044d }
            if (r4 == 0) goto L_0x044d
            java.net.Socket r4 = r10.h     // Catch:{ IOException -> 0x044d }
            boolean r4 = r4.isClosed()     // Catch:{ IOException -> 0x044d }
            if (r4 != 0) goto L_0x044d
            java.net.Socket r4 = r10.h     // Catch:{ IOException -> 0x044d }
            r4.close()     // Catch:{ IOException -> 0x044d }
            r10.h = r3     // Catch:{ IOException -> 0x044d }
        L_0x044d:
            java.lang.String r3 = "hehe"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = a
            r4.append(r5)
            java.lang.String r5 = " , ClientReceiveThread finally sendEmptyMessage MSG_CLIENT_STATE_DISCONNECT--> begin"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.Object[] r5 = new java.lang.Object[r2]
            com.autonavi.link.utils.Logger.d(r3, r4, r5)
            android.os.Handler r3 = r10.i
            r3.sendEmptyMessage(r1)
            java.lang.String r1 = "hehe"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = a
            r3.append(r4)
            java.lang.String r4 = " , ClientReceiveThread finally sendEmptyMessage MSG_CLIENT_STATE_DISCONNECT--> success"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.autonavi.link.utils.Logger.d(r1, r3, r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.link.connect.wifi.a.a.run():void");
    }
}
