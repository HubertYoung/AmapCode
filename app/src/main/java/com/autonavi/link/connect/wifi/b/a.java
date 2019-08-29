package com.autonavi.link.connect.wifi.b;

import android.os.Handler;
import com.autonavi.link.utils.Logger;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.util.Enumeration;
import org.apache.http.conn.util.InetAddressUtils;

/* compiled from: ServerReceiveThread */
public class a extends Thread {
    private static final String a = "a";
    private ServerSocket b;
    private boolean c = true;
    private Handler d;
    private boolean e = false;
    private int f = 0;
    private final int g = 1;

    public a(int i, Handler handler) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append(" , ServerReceiveThread init ");
        Logger.d((String) "hehe", sb.toString(), new Object[0]);
        this.d = handler;
        this.b = new ServerSocket(i);
    }

    public void interrupt() {
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append(" , ServerReceiveThread interrupt ");
        Logger.d((String) "hehe", sb.toString(), new Object[0]);
        this.c = false;
        this.e = true;
        try {
            if (this.b != null && !this.b.isClosed()) {
                this.b.close();
                this.b = null;
            }
        } catch (Exception unused) {
        }
        super.interrupt();
    }

    /* JADX WARNING: type inference failed for: r6v8, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r5v19, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r5v20 */
    /* JADX WARNING: type inference failed for: r6v9 */
    /* JADX WARNING: type inference failed for: r6v10 */
    /* JADX WARNING: type inference failed for: r5v28 */
    /* JADX WARNING: type inference failed for: r5v29 */
    /* JADX WARNING: type inference failed for: r5v30 */
    /* JADX WARNING: type inference failed for: r6v12 */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x02e5, code lost:
        r1 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x02e7, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:?, code lost:
        r5 = new java.lang.StringBuilder();
        r5.append(a);
        r5.append(" , ServerReceiveThread JSONException--> ");
        r5.append(r3);
        com.autonavi.link.utils.Logger.d((java.lang.String) "hehe", r5.toString(), new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x032b, code lost:
        r13.b.close();
        r13.b = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x0334, code lost:
        r1 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x0337, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:?, code lost:
        r5 = new java.lang.StringBuilder();
        r5.append(a);
        r5.append(" , ServerReceiveThread IOException--> ");
        r5.append(r3);
        com.autonavi.link.utils.Logger.d((java.lang.String) "hehe", r5.toString(), new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x037b, code lost:
        r13.b.close();
        r13.b = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x0384, code lost:
        r1 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x0387, code lost:
        r4 = "hehe";
        r5 = new java.lang.StringBuilder();
        r5.append(a);
        r6 = " , ServerReceiveThread finally";
        r5.append(r6);
        com.autonavi.link.utils.Logger.d(r4, r5.toString(), new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x03ad, code lost:
        r13.b.close();
        r13.b = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x03b5, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x03b6, code lost:
        r1.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x03b9, code lost:
        r13.d.sendEmptyMessage(200);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x03be, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0296, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0299, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:?, code lost:
        r5 = new java.lang.StringBuilder();
        r5.append(a);
        r5.append(" , ServerReceiveThread Exception--> ");
        r5.append(r3);
        com.autonavi.link.utils.Logger.d((java.lang.String) "hehe", r5.toString(), new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x02dd, code lost:
        r13.b.close();
        r13.b = null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:75:0x025d */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02e7 A[ExcHandler: JSONException (r3v4 'e' org.json.JSONException A[CUSTOM_DECLARE]), Splitter:B:3:0x001e] */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x0337 A[ExcHandler: IOException (r3v1 'e' java.io.IOException A[CUSTOM_DECLARE]), Splitter:B:3:0x001e] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0250 A[SYNTHETIC, Splitter:B:65:0x0250] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0255 A[SYNTHETIC, Splitter:B:69:0x0255] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x025a A[SYNTHETIC, Splitter:B:73:0x025a] */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r13 = this;
            java.lang.String r0 = "hehe"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = a
            r1.append(r2)
            java.lang.String r2 = " , ServerReceiveThread run begin "
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.autonavi.link.utils.Logger.d(r0, r1, r3)
        L_0x001b:
            r0 = 200(0xc8, float:2.8E-43)
            r1 = 0
            boolean r3 = r13.e     // Catch:{ IOException -> 0x0337, JSONException -> 0x02e7, Exception -> 0x0299 }
            if (r3 != 0) goto L_0x025e
            java.lang.String r3 = "hehe"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0337, JSONException -> 0x02e7, Exception -> 0x0299 }
            r4.<init>()     // Catch:{ IOException -> 0x0337, JSONException -> 0x02e7, Exception -> 0x0299 }
            java.lang.String r5 = a     // Catch:{ IOException -> 0x0337, JSONException -> 0x02e7, Exception -> 0x0299 }
            r4.append(r5)     // Catch:{ IOException -> 0x0337, JSONException -> 0x02e7, Exception -> 0x0299 }
            java.lang.String r5 = " , ServerReceiveThread run begin -->kai shi xun huan --> "
            r4.append(r5)     // Catch:{ IOException -> 0x0337, JSONException -> 0x02e7, Exception -> 0x0299 }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x0337, JSONException -> 0x02e7, Exception -> 0x0299 }
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ IOException -> 0x0337, JSONException -> 0x02e7, Exception -> 0x0299 }
            com.autonavi.link.utils.Logger.d(r3, r4, r5)     // Catch:{ IOException -> 0x0337, JSONException -> 0x02e7, Exception -> 0x0299 }
            java.net.ServerSocket r3 = r13.b     // Catch:{ all -> 0x024a }
            java.net.Socket r3 = r3.accept()     // Catch:{ all -> 0x024a }
            r4 = 1
            r13.c = r4     // Catch:{ all -> 0x0247 }
            r13.e = r4     // Catch:{ all -> 0x0247 }
            android.os.Handler r5 = r13.d     // Catch:{ all -> 0x0247 }
            boolean r5 = r5.hasMessages(r0)     // Catch:{ all -> 0x0247 }
            if (r5 == 0) goto L_0x0054
            android.os.Handler r5 = r13.d     // Catch:{ all -> 0x0247 }
            r5.removeMessages(r0)     // Catch:{ all -> 0x0247 }
        L_0x0054:
            r3.setTcpNoDelay(r4)     // Catch:{ all -> 0x0247 }
            r5 = 5000(0x1388, float:7.006E-42)
            r3.setSoTimeout(r5)     // Catch:{ all -> 0x0247 }
            java.io.InputStream r5 = r3.getInputStream()     // Catch:{ all -> 0x0247 }
            java.io.OutputStream r6 = r3.getOutputStream()     // Catch:{ all -> 0x0244 }
            int r7 = r13.f     // Catch:{ all -> 0x0242 }
            if (r7 != 0) goto L_0x00e4
            java.lang.String r7 = "hehe"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0242 }
            r8.<init>()     // Catch:{ all -> 0x0242 }
            java.lang.String r9 = a     // Catch:{ all -> 0x0242 }
            r8.append(r9)     // Catch:{ all -> 0x0242 }
            java.lang.String r9 = " , ServerReceiveThread run begin -->kai shi xun huan --> huo qu shu ju"
            r8.append(r9)     // Catch:{ all -> 0x0242 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0242 }
            java.lang.Object[] r9 = new java.lang.Object[r2]     // Catch:{ all -> 0x0242 }
            com.autonavi.link.utils.Logger.d(r7, r8, r9)     // Catch:{ all -> 0x0242 }
            com.autonavi.link.connect.model.b r7 = com.autonavi.link.connect.b.a.a(r5)     // Catch:{ all -> 0x0242 }
            com.autonavi.link.connect.model.DiscoverInfo r8 = new com.autonavi.link.connect.model.DiscoverInfo     // Catch:{ all -> 0x0242 }
            r8.<init>()     // Catch:{ all -> 0x0242 }
            java.lang.String r9 = r7.i     // Catch:{ all -> 0x0242 }
            r8.deviceName = r9     // Catch:{ all -> 0x0242 }
            java.lang.String r9 = r7.f     // Catch:{ all -> 0x0242 }
            r8.IP = r9     // Catch:{ all -> 0x0242 }
            java.lang.String r9 = r7.g     // Catch:{ all -> 0x0242 }
            r8.httpPort = r9     // Catch:{ all -> 0x0242 }
            java.lang.String r9 = r7.b     // Catch:{ all -> 0x0242 }
            r8.sdkVersion = r9     // Catch:{ all -> 0x0242 }
            java.lang.String r9 = r7.j     // Catch:{ all -> 0x0242 }
            r8.appId = r9     // Catch:{ all -> 0x0242 }
            java.lang.String r9 = r7.l     // Catch:{ all -> 0x0242 }
            r8.appVersion = r9     // Catch:{ all -> 0x0242 }
            java.lang.String r7 = r7.k     // Catch:{ all -> 0x0242 }
            r8.appName = r7     // Catch:{ all -> 0x0242 }
            android.os.Handler r7 = r13.d     // Catch:{ all -> 0x0242 }
            r9 = 202(0xca, float:2.83E-43)
            android.os.Message r7 = r7.obtainMessage(r9, r8)     // Catch:{ all -> 0x0242 }
            r7.sendToTarget()     // Catch:{ all -> 0x0242 }
            com.autonavi.link.LinkSDK r7 = com.autonavi.link.LinkSDK.getInstance()     // Catch:{ all -> 0x0242 }
            com.autonavi.link.connect.wifi.ShareNetManager r7 = r7.getWifiInstance()     // Catch:{ all -> 0x0242 }
            java.lang.String r7 = r7.getLocalAddress()     // Catch:{ all -> 0x0242 }
            java.lang.String r8 = "8721"
            byte[] r7 = com.autonavi.link.connect.b.a.b(r7, r8, r1)     // Catch:{ all -> 0x0242 }
            r6.write(r7)     // Catch:{ all -> 0x0242 }
            r6.flush()     // Catch:{ all -> 0x0242 }
            java.lang.String r7 = "hehe"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0242 }
            r8.<init>()     // Catch:{ all -> 0x0242 }
            java.lang.String r9 = a     // Catch:{ all -> 0x0242 }
            r8.append(r9)     // Catch:{ all -> 0x0242 }
            java.lang.String r9 = " , ServerReceiveThread run begin -->kai shi xun huan --> fa song shu ju"
            r8.append(r9)     // Catch:{ all -> 0x0242 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0242 }
            java.lang.Object[] r9 = new java.lang.Object[r2]     // Catch:{ all -> 0x0242 }
            com.autonavi.link.utils.Logger.d(r7, r8, r9)     // Catch:{ all -> 0x0242 }
        L_0x00e4:
            r13.f = r2     // Catch:{ all -> 0x0242 }
            java.lang.String r7 = "hehe"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0242 }
            r8.<init>()     // Catch:{ all -> 0x0242 }
            java.lang.String r9 = a     // Catch:{ all -> 0x0242 }
            r8.append(r9)     // Catch:{ all -> 0x0242 }
            java.lang.String r9 = " , ServerReceiveThread run --> wei chi socket -->"
            r8.append(r9)     // Catch:{ all -> 0x0242 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0242 }
            java.lang.Object[] r9 = new java.lang.Object[r2]     // Catch:{ all -> 0x0242 }
            com.autonavi.link.utils.Logger.d(r7, r8, r9)     // Catch:{ all -> 0x0242 }
            java.lang.String r7 = "0"
            byte[] r7 = r7.getBytes()     // Catch:{ all -> 0x0242 }
            r8 = 0
        L_0x0107:
            boolean r9 = r13.c     // Catch:{ all -> 0x0242 }
            if (r9 == 0) goto L_0x0231
            java.lang.String r9 = "hehe"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x0242 }
            r10.<init>()     // Catch:{ all -> 0x0242 }
            java.lang.String r11 = a     // Catch:{ all -> 0x0242 }
            r10.append(r11)     // Catch:{ all -> 0x0242 }
            java.lang.String r11 = " , ServerReceiveThread run --> wei chi socket --> send message"
            r10.append(r11)     // Catch:{ all -> 0x0242 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x0242 }
            java.lang.Object[] r11 = new java.lang.Object[r2]     // Catch:{ all -> 0x0242 }
            com.autonavi.link.utils.Logger.d(r9, r10, r11)     // Catch:{ all -> 0x0242 }
            android.os.Handler r9 = r13.d     // Catch:{ all -> 0x0242 }
            r10 = 2000(0x7d0, double:9.88E-321)
            r9.sendEmptyMessageDelayed(r0, r10)     // Catch:{ all -> 0x0242 }
            java.lang.String r9 = "hehe"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            r10.<init>()     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            java.lang.String r11 = a     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            r10.append(r11)     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            java.lang.String r11 = " , ServerReceiveThread run --> wei chi socket --> write data begin"
            r10.append(r11)     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            java.lang.String r10 = r10.toString()     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            java.lang.Object[] r11 = new java.lang.Object[r2]     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            com.autonavi.link.utils.Logger.d(r9, r10, r11)     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            r6.write(r7)     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            java.lang.String r9 = "hehe"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            r10.<init>()     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            java.lang.String r11 = a     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            r10.append(r11)     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            java.lang.String r11 = " , ServerReceiveThread run --> wei chi socket --> write data success begin to flush"
            r10.append(r11)     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            java.lang.String r10 = r10.toString()     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            java.lang.Object[] r11 = new java.lang.Object[r2]     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            com.autonavi.link.utils.Logger.d(r9, r10, r11)     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            r6.flush()     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            java.lang.String r9 = "hehe"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            r10.<init>()     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            java.lang.String r11 = a     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            r10.append(r11)     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            java.lang.String r11 = " , ServerReceiveThread run --> wei chi socket --> write data flush success "
            r10.append(r11)     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            java.lang.String r10 = r10.toString()     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            java.lang.Object[] r11 = new java.lang.Object[r2]     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            com.autonavi.link.utils.Logger.d(r9, r10, r11)     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            android.os.Handler r9 = r13.d     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            r9.removeMessages(r0)     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            java.lang.String r9 = "hehe"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            r10.<init>()     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            java.lang.String r11 = a     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            r10.append(r11)     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            java.lang.String r11 = " , ServerReceiveThread run --> wei chi socket --> write data remove message"
            r10.append(r11)     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            java.lang.String r10 = r10.toString()     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            java.lang.Object[] r11 = new java.lang.Object[r2]     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            com.autonavi.link.utils.Logger.d(r9, r10, r11)     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            r9 = 800(0x320, double:3.953E-321)
            sleep(r9)     // Catch:{ InterruptedException -> 0x022d, Exception -> 0x01a6 }
            goto L_0x0107
        L_0x01a6:
            r9 = move-exception
            java.lang.String r10 = "hehe"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x0242 }
            r11.<init>()     // Catch:{ all -> 0x0242 }
            java.lang.String r12 = a     // Catch:{ all -> 0x0242 }
            r11.append(r12)     // Catch:{ all -> 0x0242 }
            java.lang.String r12 = " , ServerReceiveThread run --> wei chi socket --> Exception--> "
            r11.append(r12)     // Catch:{ all -> 0x0242 }
            r11.append(r9)     // Catch:{ all -> 0x0242 }
            java.lang.String r9 = " ,sendTime--> "
            r11.append(r9)     // Catch:{ all -> 0x0242 }
            r11.append(r8)     // Catch:{ all -> 0x0242 }
            java.lang.String r9 = r11.toString()     // Catch:{ all -> 0x0242 }
            java.lang.Object[] r11 = new java.lang.Object[r2]     // Catch:{ all -> 0x0242 }
            com.autonavi.link.utils.Logger.d(r10, r9, r11)     // Catch:{ all -> 0x0242 }
            r9 = 5
            if (r8 != r9) goto L_0x01df
            r13.c = r2     // Catch:{ all -> 0x0242 }
            int r9 = r13.f     // Catch:{ all -> 0x0242 }
            if (r9 <= 0) goto L_0x01d7
            r9 = 1
            goto L_0x01d8
        L_0x01d7:
            r9 = 0
        L_0x01d8:
            r13.e = r9     // Catch:{ all -> 0x0242 }
            int r9 = r13.f     // Catch:{ all -> 0x0242 }
            int r9 = r9 + r4
            r13.f = r9     // Catch:{ all -> 0x0242 }
        L_0x01df:
            int r8 = r8 + 1
            boolean r9 = com.autonavi.link.utils.Logger.getIsLog()     // Catch:{ all -> 0x0242 }
            if (r9 == 0) goto L_0x0107
            java.net.InetAddress r9 = r13.a()     // Catch:{ all -> 0x0242 }
            java.lang.String r10 = "hehe"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x0242 }
            r11.<init>()     // Catch:{ all -> 0x0242 }
            java.lang.String r12 = a     // Catch:{ all -> 0x0242 }
            r11.append(r12)     // Catch:{ all -> 0x0242 }
            java.lang.String r12 = " , ServerReceiveThread run --> wei chi socket --> Exception--> address--> "
            r11.append(r12)     // Catch:{ all -> 0x0242 }
            r11.append(r9)     // Catch:{ all -> 0x0242 }
            java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x0242 }
            java.lang.Object[] r12 = new java.lang.Object[r2]     // Catch:{ all -> 0x0242 }
            com.autonavi.link.utils.Logger.d(r10, r11, r12)     // Catch:{ all -> 0x0242 }
            if (r9 == 0) goto L_0x0107
            java.lang.String r10 = "hehe"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x0242 }
            r11.<init>()     // Catch:{ all -> 0x0242 }
            java.lang.String r12 = a     // Catch:{ all -> 0x0242 }
            r11.append(r12)     // Catch:{ all -> 0x0242 }
            java.lang.String r12 = " , ServerReceiveThread run --> wei chi socket --> Exception--> getBroadcast(address)--> "
            r11.append(r12)     // Catch:{ all -> 0x0242 }
            java.net.InetAddress r9 = r13.a(r9)     // Catch:{ all -> 0x0242 }
            r11.append(r9)     // Catch:{ all -> 0x0242 }
            java.lang.String r9 = r11.toString()     // Catch:{ all -> 0x0242 }
            java.lang.Object[] r11 = new java.lang.Object[r2]     // Catch:{ all -> 0x0242 }
            com.autonavi.link.utils.Logger.d(r10, r9, r11)     // Catch:{ all -> 0x0242 }
            goto L_0x0107
        L_0x022d:
            r13.c = r2     // Catch:{ all -> 0x0242 }
            goto L_0x0107
        L_0x0231:
            if (r5 == 0) goto L_0x0236
            r5.close()     // Catch:{ Exception -> 0x0236, IOException -> 0x0337, JSONException -> 0x02e7 }
        L_0x0236:
            if (r6 == 0) goto L_0x023b
            r6.close()     // Catch:{ Exception -> 0x023b, IOException -> 0x0337, JSONException -> 0x02e7 }
        L_0x023b:
            if (r3 == 0) goto L_0x001b
            r3.close()     // Catch:{ Exception -> 0x001b, IOException -> 0x0337, JSONException -> 0x02e7 }
            goto L_0x001b
        L_0x0242:
            r4 = move-exception
            goto L_0x024e
        L_0x0244:
            r4 = move-exception
            r6 = r1
            goto L_0x024e
        L_0x0247:
            r4 = move-exception
            r5 = r1
            goto L_0x024d
        L_0x024a:
            r4 = move-exception
            r3 = r1
            r5 = r3
        L_0x024d:
            r6 = r5
        L_0x024e:
            if (r5 == 0) goto L_0x0253
            r5.close()     // Catch:{ Exception -> 0x0253, IOException -> 0x0337, JSONException -> 0x02e7 }
        L_0x0253:
            if (r6 == 0) goto L_0x0258
            r6.close()     // Catch:{ Exception -> 0x0258, IOException -> 0x0337, JSONException -> 0x02e7 }
        L_0x0258:
            if (r3 == 0) goto L_0x025d
            r3.close()     // Catch:{ Exception -> 0x025d, IOException -> 0x0337, JSONException -> 0x02e7 }
        L_0x025d:
            throw r4     // Catch:{ IOException -> 0x0337, JSONException -> 0x02e7, Exception -> 0x0299 }
        L_0x025e:
            java.lang.String r3 = "hehe"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = a
            r4.append(r5)
            java.lang.String r5 = " , ServerReceiveThread finally"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.autonavi.link.utils.Logger.d(r3, r4, r2)
            java.net.ServerSocket r2 = r13.b     // Catch:{ Exception -> 0x028c }
            if (r2 == 0) goto L_0x0290
            java.net.ServerSocket r2 = r13.b     // Catch:{ Exception -> 0x028c }
            boolean r2 = r2.isClosed()     // Catch:{ Exception -> 0x028c }
            if (r2 != 0) goto L_0x0290
            java.net.ServerSocket r2 = r13.b     // Catch:{ Exception -> 0x028c }
            r2.close()     // Catch:{ Exception -> 0x028c }
            r13.b = r1     // Catch:{ Exception -> 0x028c }
            goto L_0x0290
        L_0x028c:
            r1 = move-exception
        L_0x028d:
            r1.printStackTrace()
        L_0x0290:
            android.os.Handler r1 = r13.d
            r1.sendEmptyMessage(r0)
            return
        L_0x0296:
            r3 = move-exception
            goto L_0x0387
        L_0x0299:
            r3 = move-exception
            java.lang.String r4 = "hehe"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0296 }
            r5.<init>()     // Catch:{ all -> 0x0296 }
            java.lang.String r6 = a     // Catch:{ all -> 0x0296 }
            r5.append(r6)     // Catch:{ all -> 0x0296 }
            java.lang.String r6 = " , ServerReceiveThread Exception--> "
            r5.append(r6)     // Catch:{ all -> 0x0296 }
            r5.append(r3)     // Catch:{ all -> 0x0296 }
            java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x0296 }
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ all -> 0x0296 }
            com.autonavi.link.utils.Logger.d(r4, r3, r5)     // Catch:{ all -> 0x0296 }
            java.lang.String r3 = "hehe"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = a
            r4.append(r5)
            java.lang.String r5 = " , ServerReceiveThread finally"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.autonavi.link.utils.Logger.d(r3, r4, r2)
            java.net.ServerSocket r2 = r13.b     // Catch:{ Exception -> 0x02e5 }
            if (r2 == 0) goto L_0x0290
            java.net.ServerSocket r2 = r13.b     // Catch:{ Exception -> 0x02e5 }
            boolean r2 = r2.isClosed()     // Catch:{ Exception -> 0x02e5 }
            if (r2 != 0) goto L_0x0290
            java.net.ServerSocket r2 = r13.b     // Catch:{ Exception -> 0x02e5 }
            r2.close()     // Catch:{ Exception -> 0x02e5 }
            r13.b = r1     // Catch:{ Exception -> 0x02e5 }
            goto L_0x0290
        L_0x02e5:
            r1 = move-exception
            goto L_0x028d
        L_0x02e7:
            r3 = move-exception
            java.lang.String r4 = "hehe"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0296 }
            r5.<init>()     // Catch:{ all -> 0x0296 }
            java.lang.String r6 = a     // Catch:{ all -> 0x0296 }
            r5.append(r6)     // Catch:{ all -> 0x0296 }
            java.lang.String r6 = " , ServerReceiveThread JSONException--> "
            r5.append(r6)     // Catch:{ all -> 0x0296 }
            r5.append(r3)     // Catch:{ all -> 0x0296 }
            java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x0296 }
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ all -> 0x0296 }
            com.autonavi.link.utils.Logger.d(r4, r3, r5)     // Catch:{ all -> 0x0296 }
            java.lang.String r3 = "hehe"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = a
            r4.append(r5)
            java.lang.String r5 = " , ServerReceiveThread finally"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.autonavi.link.utils.Logger.d(r3, r4, r2)
            java.net.ServerSocket r2 = r13.b     // Catch:{ Exception -> 0x0334 }
            if (r2 == 0) goto L_0x0290
            java.net.ServerSocket r2 = r13.b     // Catch:{ Exception -> 0x0334 }
            boolean r2 = r2.isClosed()     // Catch:{ Exception -> 0x0334 }
            if (r2 != 0) goto L_0x0290
            java.net.ServerSocket r2 = r13.b     // Catch:{ Exception -> 0x0334 }
            r2.close()     // Catch:{ Exception -> 0x0334 }
            r13.b = r1     // Catch:{ Exception -> 0x0334 }
            goto L_0x0290
        L_0x0334:
            r1 = move-exception
            goto L_0x028d
        L_0x0337:
            r3 = move-exception
            java.lang.String r4 = "hehe"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0296 }
            r5.<init>()     // Catch:{ all -> 0x0296 }
            java.lang.String r6 = a     // Catch:{ all -> 0x0296 }
            r5.append(r6)     // Catch:{ all -> 0x0296 }
            java.lang.String r6 = " , ServerReceiveThread IOException--> "
            r5.append(r6)     // Catch:{ all -> 0x0296 }
            r5.append(r3)     // Catch:{ all -> 0x0296 }
            java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x0296 }
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ all -> 0x0296 }
            com.autonavi.link.utils.Logger.d(r4, r3, r5)     // Catch:{ all -> 0x0296 }
            java.lang.String r3 = "hehe"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = a
            r4.append(r5)
            java.lang.String r5 = " , ServerReceiveThread finally"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.autonavi.link.utils.Logger.d(r3, r4, r2)
            java.net.ServerSocket r2 = r13.b     // Catch:{ Exception -> 0x0384 }
            if (r2 == 0) goto L_0x0290
            java.net.ServerSocket r2 = r13.b     // Catch:{ Exception -> 0x0384 }
            boolean r2 = r2.isClosed()     // Catch:{ Exception -> 0x0384 }
            if (r2 != 0) goto L_0x0290
            java.net.ServerSocket r2 = r13.b     // Catch:{ Exception -> 0x0384 }
            r2.close()     // Catch:{ Exception -> 0x0384 }
            r13.b = r1     // Catch:{ Exception -> 0x0384 }
            goto L_0x0290
        L_0x0384:
            r1 = move-exception
            goto L_0x028d
        L_0x0387:
            java.lang.String r4 = "hehe"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = a
            r5.append(r6)
            java.lang.String r6 = " , ServerReceiveThread finally"
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.autonavi.link.utils.Logger.d(r4, r5, r2)
            java.net.ServerSocket r2 = r13.b     // Catch:{ Exception -> 0x03b5 }
            if (r2 == 0) goto L_0x03b9
            java.net.ServerSocket r2 = r13.b     // Catch:{ Exception -> 0x03b5 }
            boolean r2 = r2.isClosed()     // Catch:{ Exception -> 0x03b5 }
            if (r2 != 0) goto L_0x03b9
            java.net.ServerSocket r2 = r13.b     // Catch:{ Exception -> 0x03b5 }
            r2.close()     // Catch:{ Exception -> 0x03b5 }
            r13.b = r1     // Catch:{ Exception -> 0x03b5 }
            goto L_0x03b9
        L_0x03b5:
            r1 = move-exception
            r1.printStackTrace()
        L_0x03b9:
            android.os.Handler r1 = r13.d
            r1.sendEmptyMessage(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.link.connect.wifi.b.a.run():void");
    }

    private InetAddress a() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface nextElement = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = nextElement.getInetAddresses();
                while (true) {
                    if (inetAddresses.hasMoreElements()) {
                        InetAddress nextElement2 = inetAddresses.nextElement();
                        if (!nextElement2.isLoopbackAddress() && nextElement.getDisplayName().contains("wlan0") && InetAddressUtils.isIPv4Address(nextElement2.getHostAddress())) {
                            return nextElement2;
                        }
                    }
                }
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    private InetAddress a(InetAddress inetAddress) {
        if (inetAddress == null) {
            return null;
        }
        try {
            NetworkInterface byInetAddress = NetworkInterface.getByInetAddress(inetAddress);
            if (byInetAddress != null) {
                for (InterfaceAddress broadcast : byInetAddress.getInterfaceAddresses()) {
                    InetAddress broadcast2 = broadcast.getBroadcast();
                    if (broadcast2 != null) {
                        return broadcast2;
                    }
                }
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }
}
