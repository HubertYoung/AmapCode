package com.autonavi.link.adapter.server.c;

import com.autonavi.link.adapter.endian.LittleEndianInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/* compiled from: SocketStub */
public class a {
    protected Socket a;
    protected InputStream b;
    protected OutputStream c;
    private com.autonavi.link.adapter.b.a d;
    private boolean e = false;

    public a(Socket socket) throws IOException {
        this.a = socket;
        d();
    }

    private void d() throws IOException {
        this.b = this.a.getInputStream();
        this.c = this.a.getOutputStream();
    }

    public OutputStream a() {
        return this.c;
    }

    public void a(com.autonavi.link.adapter.b.a aVar) {
        this.d = aVar;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(14:1|2|3|4|5|6|(1:8)|9|10|(1:12)|13|14|(1:16)|(4:17|18|(1:20)|21)) */
    /* JADX WARNING: Can't wrap try/catch for region: R(17:1|2|3|4|5|6|(1:8)|9|10|(1:12)|13|14|(1:16)|17|18|(1:20)|21) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x001b */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0026 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0010 */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0014 A[Catch:{ Exception -> 0x001b }] */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x001f A[Catch:{ Exception -> 0x0026 }] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x002a A[Catch:{ Exception -> 0x0033 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void b() {
        /*
            r2 = this;
            monitor-enter(r2)
            r0 = 0
            r2.e = r0     // Catch:{ all -> 0x0035 }
            r0 = 0
            com.autonavi.link.adapter.b.a r1 = r2.d     // Catch:{ Exception -> 0x0010 }
            if (r1 == 0) goto L_0x0010
            com.autonavi.link.adapter.b.a r1 = r2.d     // Catch:{ Exception -> 0x0010 }
            r1.a(r0)     // Catch:{ Exception -> 0x0010 }
            r2.d = r0     // Catch:{ Exception -> 0x0010 }
        L_0x0010:
            java.io.InputStream r1 = r2.b     // Catch:{ Exception -> 0x001b }
            if (r1 == 0) goto L_0x001b
            java.io.InputStream r1 = r2.b     // Catch:{ Exception -> 0x001b }
            r1.close()     // Catch:{ Exception -> 0x001b }
            r2.b = r0     // Catch:{ Exception -> 0x001b }
        L_0x001b:
            java.io.OutputStream r1 = r2.c     // Catch:{ Exception -> 0x0026 }
            if (r1 == 0) goto L_0x0026
            java.io.OutputStream r1 = r2.c     // Catch:{ Exception -> 0x0026 }
            r1.close()     // Catch:{ Exception -> 0x0026 }
            r2.c = r0     // Catch:{ Exception -> 0x0026 }
        L_0x0026:
            java.net.Socket r1 = r2.a     // Catch:{ Exception -> 0x0033 }
            if (r1 == 0) goto L_0x0031
            java.net.Socket r1 = r2.a     // Catch:{ Exception -> 0x0033 }
            r1.close()     // Catch:{ Exception -> 0x0033 }
            r2.a = r0     // Catch:{ Exception -> 0x0033 }
        L_0x0031:
            monitor-exit(r2)
            return
        L_0x0033:
            monitor-exit(r2)
            return
        L_0x0035:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.link.adapter.server.c.a.b():void");
    }

    public void c() throws IOException, InterruptedException {
        this.e = true;
        byte[] bArr = new byte[204800];
        BufferedInputStream bufferedInputStream = new BufferedInputStream(this.b, 204800);
        LittleEndianInputStream littleEndianInputStream = new LittleEndianInputStream(bufferedInputStream);
        while (this.e) {
            try {
                int readInt = littleEndianInputStream.readInt();
                if (readInt > 204800 || readInt <= 0) {
                    break;
                }
                int i = 4;
                int i2 = readInt;
                int i3 = 0;
                while (i2 > 0) {
                    int read = bufferedInputStream.read(bArr, i, i2);
                    if (read == -1) {
                        break;
                    }
                    i3 += read;
                    i += read;
                    i2 = readInt - i3;
                }
                if (this.d != null) {
                    this.d.a(bArr, readInt + 4);
                }
            } catch (Exception unused) {
            }
        }
        littleEndianInputStream.close();
        bufferedInputStream.close();
        b();
    }
}
