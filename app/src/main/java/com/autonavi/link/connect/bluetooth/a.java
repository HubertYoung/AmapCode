package com.autonavi.link.connect.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import com.autonavi.link.connect.model.DiscoverInfo;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.json.JSONException;

/* compiled from: BluetoothHandShake */
public class a {
    private static final String a = "a";
    private final Handler b;
    private boolean c;
    private DiscoverInfo d;
    private Object e = new Object();

    public a(Handler handler) {
        this.b = handler;
        this.c = false;
    }

    public DiscoverInfo a() {
        DiscoverInfo discoverInfo;
        synchronized (this.e) {
            try {
                discoverInfo = this.d;
            }
        }
        return discoverInfo;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0084, code lost:
        r2 = com.autonavi.link.connect.b.a.a((java.io.InputStream) new java.io.ByteArrayInputStream(r4));
        r4 = r1.e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x008f, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0092, code lost:
        if (r1.d != null) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0094, code lost:
        r1.d = new com.autonavi.link.connect.model.DiscoverInfo();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x009b, code lost:
        r1.d.deviceName = r2.i;
        r1.d.appId = r2.j;
        r1.d.appName = r2.k;
        r1.d.appVersion = r2.l;
        r1.d.sdkVersion = r2.b;
        r1.d.IP = "127.0.0.1";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00bf, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        r3.write(com.autonavi.link.connect.bluetooth.c.a(1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:?, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(java.io.InputStream r20, java.io.OutputStream r21) {
        /*
            r19 = this;
            r1 = r19
            r2 = r20
            r3 = r21
        L_0x0006:
            boolean r4 = r1.c
            r5 = 1
            r6 = 0
            if (r4 != 0) goto L_0x00e8
            int r7 = r20.available()     // Catch:{ Exception -> 0x00dc }
            if (r7 <= 0) goto L_0x00d5
            java.io.ByteArrayOutputStream r7 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x00dc }
            r7.<init>()     // Catch:{ Exception -> 0x00dc }
            r8 = 22
            byte[] r9 = new byte[r8]     // Catch:{ Exception -> 0x00dc }
            r10 = 16
            byte[] r11 = new byte[r10]     // Catch:{ Exception -> 0x00dc }
            r12 = -1
            r15 = r11
            r13 = r12
            r11 = 1
            r12 = 0
        L_0x0025:
            boolean r4 = r1.c     // Catch:{ Exception -> 0x00dc }
            r16 = 0
            if (r4 != 0) goto L_0x006e
            if (r11 == 0) goto L_0x0059
            byte[] r4 = new byte[r5]     // Catch:{ Exception -> 0x00dc }
            r2.read(r4, r6, r5)     // Catch:{ Exception -> 0x00dc }
            int r5 = r12 + 1
            byte r4 = r4[r6]     // Catch:{ Exception -> 0x00dc }
            r9[r12] = r4     // Catch:{ Exception -> 0x00dc }
            if (r5 != r8) goto L_0x0057
            byte r4 = r9[r6]     // Catch:{ Exception -> 0x00dc }
            if (r4 != r10) goto L_0x006e
            r4 = 1
            byte r12 = r9[r4]     // Catch:{ Exception -> 0x00dc }
            r4 = 85
            if (r12 != r4) goto L_0x006e
            r4 = 6
            r11 = 2
            byte[] r12 = java.util.Arrays.copyOfRange(r9, r11, r4)     // Catch:{ Exception -> 0x00dc }
            int r11 = com.autonavi.link.connect.bluetooth.c.a(r12)     // Catch:{ Exception -> 0x00dc }
            long r11 = (long) r11     // Catch:{ Exception -> 0x00dc }
            byte[] r4 = java.util.Arrays.copyOfRange(r9, r4, r8)     // Catch:{ Exception -> 0x00dc }
            r15 = r4
            r13 = r11
            r11 = 0
        L_0x0057:
            r12 = r5
            goto L_0x006c
        L_0x0059:
            r4 = 4092(0xffc, float:5.734E-42)
            byte[] r4 = new byte[r4]     // Catch:{ Exception -> 0x00dc }
            int r5 = r2.read(r4)     // Catch:{ Exception -> 0x00dc }
            r7.write(r4, r6, r5)     // Catch:{ Exception -> 0x00dc }
            long r4 = (long) r5     // Catch:{ Exception -> 0x00dc }
            long r13 = r13 - r4
            int r4 = (r13 > r16 ? 1 : (r13 == r16 ? 0 : -1))
            if (r4 > 0) goto L_0x006c
            r11 = 1
            goto L_0x006e
        L_0x006c:
            r5 = 1
            goto L_0x0025
        L_0x006e:
            boolean r4 = r1.c     // Catch:{ Exception -> 0x00dc }
            if (r4 == 0) goto L_0x0074
            goto L_0x00e8
        L_0x0074:
            int r4 = (r13 > r16 ? 1 : (r13 == r16 ? 0 : -1))
            if (r4 != 0) goto L_0x00d5
            if (r11 == 0) goto L_0x00d5
            byte[] r4 = r7.toByteArray()     // Catch:{ Exception -> 0x00dc }
            boolean r5 = com.autonavi.link.connect.bluetooth.c.a(r4, r15)     // Catch:{ Exception -> 0x00dc }
            if (r5 == 0) goto L_0x00ce
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x00dc }
            r2.<init>(r4)     // Catch:{ Exception -> 0x00dc }
            com.autonavi.link.connect.model.b r2 = com.autonavi.link.connect.b.a.a(r2)     // Catch:{ Exception -> 0x00dc }
            java.lang.Object r4 = r1.e     // Catch:{ Exception -> 0x00dc }
            monitor-enter(r4)     // Catch:{ Exception -> 0x00dc }
            com.autonavi.link.connect.model.DiscoverInfo r5 = r1.d     // Catch:{ all -> 0x00ca }
            if (r5 != 0) goto L_0x009b
            com.autonavi.link.connect.model.DiscoverInfo r5 = new com.autonavi.link.connect.model.DiscoverInfo     // Catch:{ all -> 0x00ca }
            r5.<init>()     // Catch:{ all -> 0x00ca }
            r1.d = r5     // Catch:{ all -> 0x00ca }
        L_0x009b:
            com.autonavi.link.connect.model.DiscoverInfo r5 = r1.d     // Catch:{ all -> 0x00ca }
            java.lang.String r7 = r2.i     // Catch:{ all -> 0x00ca }
            r5.deviceName = r7     // Catch:{ all -> 0x00ca }
            com.autonavi.link.connect.model.DiscoverInfo r5 = r1.d     // Catch:{ all -> 0x00ca }
            java.lang.String r7 = r2.j     // Catch:{ all -> 0x00ca }
            r5.appId = r7     // Catch:{ all -> 0x00ca }
            com.autonavi.link.connect.model.DiscoverInfo r5 = r1.d     // Catch:{ all -> 0x00ca }
            java.lang.String r7 = r2.k     // Catch:{ all -> 0x00ca }
            r5.appName = r7     // Catch:{ all -> 0x00ca }
            com.autonavi.link.connect.model.DiscoverInfo r5 = r1.d     // Catch:{ all -> 0x00ca }
            java.lang.String r7 = r2.l     // Catch:{ all -> 0x00ca }
            r5.appVersion = r7     // Catch:{ all -> 0x00ca }
            com.autonavi.link.connect.model.DiscoverInfo r5 = r1.d     // Catch:{ all -> 0x00ca }
            java.lang.String r2 = r2.b     // Catch:{ all -> 0x00ca }
            r5.sdkVersion = r2     // Catch:{ all -> 0x00ca }
            com.autonavi.link.connect.model.DiscoverInfo r2 = r1.d     // Catch:{ all -> 0x00ca }
            java.lang.String r5 = "127.0.0.1"
            r2.IP = r5     // Catch:{ all -> 0x00ca }
            monitor-exit(r4)     // Catch:{ all -> 0x00ca }
            r4 = 1
            byte[] r2 = com.autonavi.link.connect.bluetooth.c.a(r4)     // Catch:{ Exception -> 0x00dc }
            r3.write(r2)     // Catch:{ Exception -> 0x00dc }
            r6 = 1
            goto L_0x00e8
        L_0x00ca:
            r0 = move-exception
            r2 = r0
            monitor-exit(r4)     // Catch:{ all -> 0x00ca }
            throw r2     // Catch:{ Exception -> 0x00dc }
        L_0x00ce:
            byte[] r4 = com.autonavi.link.connect.bluetooth.c.a(r6)     // Catch:{ Exception -> 0x00dc }
            r3.write(r4)     // Catch:{ Exception -> 0x00dc }
        L_0x00d5:
            r4 = 10
            java.lang.Thread.sleep(r4)     // Catch:{ Exception -> 0x00dc }
            goto L_0x0006
        L_0x00dc:
            android.os.Handler r2 = r1.b
            r3 = -2
            r4 = -1
            r5 = 2
            android.os.Message r2 = r2.obtainMessage(r5, r3, r4)
            r2.sendToTarget()
        L_0x00e8:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.link.connect.bluetooth.a.a(java.io.InputStream, java.io.OutputStream):boolean");
    }

    private boolean a(InputStream inputStream, OutputStream outputStream, byte[] bArr) {
        boolean z = false;
        boolean z2 = false;
        while (!z && !this.c) {
            try {
                outputStream.write(16);
                outputStream.write(85);
                outputStream.write(c.a(bArr.length));
                outputStream.write(c.b(bArr));
                outputStream.write(bArr);
                outputStream.flush();
                byte[] bArr2 = new byte[4];
                int i = 0;
                while (true) {
                    if (this.c) {
                        break;
                    }
                    byte[] bArr3 = new byte[1];
                    inputStream.read(bArr3, 0, 1);
                    int i2 = i + 1;
                    bArr2[i] = bArr3[0];
                    if (i2 != 4) {
                        i = i2;
                    } else if (c.a(bArr2) == 1) {
                        z = true;
                        z2 = true;
                    }
                }
            } catch (Exception unused) {
                this.b.obtainMessage(2, -2, -1).sendToTarget();
            }
        }
        return z2;
    }

    public boolean a(BluetoothSocket bluetoothSocket) {
        boolean z = false;
        try {
            InputStream inputStream = bluetoothSocket.getInputStream();
            OutputStream outputStream = bluetoothSocket.getOutputStream();
            if (!(inputStream == null || outputStream == null)) {
                try {
                    boolean a2 = a(inputStream, outputStream);
                    if (!a2) {
                        return a2;
                    }
                    boolean a3 = a(inputStream, outputStream, com.autonavi.link.connect.b.a.b(null, null, null));
                    if (!a3) {
                        return a3;
                    }
                    z = a3;
                } catch (IOException | Exception | JSONException unused) {
                }
            }
            return z;
        } catch (IOException unused2) {
            return false;
        }
    }

    public boolean b(BluetoothSocket bluetoothSocket) {
        boolean z = false;
        try {
            InputStream inputStream = bluetoothSocket.getInputStream();
            OutputStream outputStream = bluetoothSocket.getOutputStream();
            if (!(inputStream == null || outputStream == null)) {
                try {
                    boolean a2 = a(inputStream, outputStream, com.autonavi.link.connect.b.a.b(null, null, null));
                    if (!a2) {
                        return a2;
                    }
                    try {
                        z = a(inputStream, outputStream);
                        if (!z) {
                            return z;
                        }
                    } catch (IOException | Exception | JSONException unused) {
                        z = a2;
                    }
                } catch (IOException | Exception | JSONException unused2) {
                }
            }
            return z;
        } catch (IOException unused3) {
            return false;
        }
    }

    public void b() {
        this.c = true;
    }
}
