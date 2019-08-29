package defpackage;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.yunos.carkitsdk.TransferInfo;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/* renamed from: fca reason: default package */
/* compiled from: FileReceiver */
public final class fca implements Runnable {
    private static int c = 51000;
    public TransferInfo a;
    public int b;
    private ServerSocket d;
    private Socket e;
    private long f = 0;
    private fce g;
    private fcd h;

    public fca(fcc fcc, fce fce, fcd fcd) {
        fcc fcc2 = fcc;
        this.h = fcd;
        TransferInfo transferInfo = new TransferInfo(fcc2.a, fcc2.g, fcc2.h, fcc2.c, fcc2.d, fcc2.i, fcc2.j, fcc2.k);
        this.a = transferInfo;
        this.f = fcc2.l;
        this.g = fce;
        this.d = b();
        StringBuilder sb = new StringBuilder("create FileReceiver: tranferId=");
        sb.append(this.a.i);
        sb.append(Token.SEPARATOR);
        sb.append(this.a.b);
        sb.append("  ");
        sb.append(this.a.a);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|8|9|10|(2:12|20)(1:19)|17) */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003d, code lost:
        r3 = c + 1;
        c = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0043, code lost:
        if (r3 == 52000) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0045, code lost:
        c = 51000;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0047, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0024, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x0026 */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x003a A[DONT_GENERATE] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0000 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.net.ServerSocket b() {
        /*
            r5 = this;
        L_0x0000:
            r0 = 51000(0xc738, float:7.1466E-41)
            r1 = 52000(0xcb20, float:7.2868E-41)
            java.net.ServerSocket r2 = new java.net.ServerSocket     // Catch:{ IOException -> 0x0026 }
            r2.<init>()     // Catch:{ IOException -> 0x0026 }
            java.net.InetSocketAddress r3 = new java.net.InetSocketAddress     // Catch:{ IOException -> 0x0026 }
            int r4 = c     // Catch:{ IOException -> 0x0026 }
            r3.<init>(r4)     // Catch:{ IOException -> 0x0026 }
            r2.bind(r3)     // Catch:{ IOException -> 0x0026 }
            int r3 = c     // Catch:{ IOException -> 0x0026 }
            r5.b = r3     // Catch:{ IOException -> 0x0026 }
            int r3 = c
            int r3 = r3 + 1
            c = r3
            if (r3 != r1) goto L_0x0023
            c = r0
        L_0x0023:
            return r2
        L_0x0024:
            r2 = move-exception
            goto L_0x003d
        L_0x0026:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0024 }
            java.lang.String r3 = "bind data socket failed on port "
            r2.<init>(r3)     // Catch:{ all -> 0x0024 }
            int r3 = c     // Catch:{ all -> 0x0024 }
            r2.append(r3)     // Catch:{ all -> 0x0024 }
            int r2 = c
            int r2 = r2 + 1
            c = r2
            if (r2 != r1) goto L_0x0000
            c = r0
            goto L_0x0000
        L_0x003d:
            int r3 = c
            int r3 = r3 + 1
            c = r3
            if (r3 != r1) goto L_0x0047
            c = r0
        L_0x0047:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fca.b():java.net.ServerSocket");
    }

    /* access modifiers changed from: 0000 */
    public final void a(int i) {
        this.a.h = 5;
        this.a.l = i;
    }

    public final void a() {
        this.a.h = 5;
        c();
    }

    private void c() {
        try {
            if (this.d != null) {
                this.d.close();
            }
            if (this.e != null) {
                this.e.close();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x01b0  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x01c3  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x01f3 A[SYNTHETIC, Splitter:B:55:0x01f3] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x022f  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0242  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0272 A[SYNTHETIC, Splitter:B:71:0x0272] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r14 = this;
            java.io.File r0 = new java.io.File
            com.yunos.carkitsdk.TransferInfo r1 = r14.a
            java.lang.String r1 = r1.b
            r0.<init>(r1)
            boolean r1 = r0.exists()
            if (r1 == 0) goto L_0x001e
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "file exist, will overwrite: "
            r1.<init>(r2)
            com.yunos.carkitsdk.TransferInfo r2 = r14.a
            java.lang.String r2 = r2.b
            r1.append(r2)
            goto L_0x0030
        L_0x001e:
            java.io.File r1 = new java.io.File
            java.lang.String r2 = r0.getParent()
            r1.<init>(r2)
            boolean r2 = r1.exists()
            if (r2 != 0) goto L_0x0030
            r1.mkdirs()
        L_0x0030:
            r1 = 0
            r2 = 64768(0xfd00, float:9.0759E-41)
            byte[] r2 = new byte[r2]
            r3 = 4
            r4 = 3
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x017a }
            r5.<init>(r0)     // Catch:{ IOException -> 0x017a }
            java.net.ServerSocket r0 = r14.d     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            java.net.Socket r0 = r0.accept()     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            r14.e = r0     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            java.net.Socket r0 = r14.e     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            r1 = 0
            r0.setSoTimeout(r1)     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            java.net.Socket r0 = r14.e     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            r6 = 1048576(0x100000, float:1.469368E-39)
            r0.setReceiveBufferSize(r6)     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            java.lang.String r6 = "transferId="
            r0.<init>(r6)     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            com.yunos.carkitsdk.TransferInfo r6 = r14.a     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            int r6 = r6.i     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            r0.append(r6)     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            java.lang.String r6 = " length="
            r0.append(r6)     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            com.yunos.carkitsdk.TransferInfo r6 = r14.a     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            long r6 = r6.e     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            r0.append(r6)     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            java.lang.String r6 = " accept a data socket"
            r0.append(r6)     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            java.net.Socket r0 = r14.e     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            java.io.InputStream r0 = r0.getInputStream()     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            com.yunos.carkitsdk.TransferInfo r6 = r14.a     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            r7 = 2
            r6.h = r7     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            fce r6 = r14.g     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            com.yunos.carkitsdk.TransferInfo r7 = r14.a     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            r6.c(r7)     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            r6 = 0
        L_0x0085:
            com.yunos.carkitsdk.TransferInfo r8 = r14.a     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            boolean r8 = r8.a()     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            if (r8 == 0) goto L_0x008e
            goto L_0x00f3
        L_0x008e:
            int r8 = r0.read(r2)     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            r9 = -1
            if (r8 != r9) goto L_0x00a9
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            java.lang.String r1 = "return -1, transferId="
            r0.<init>(r1)     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            com.yunos.carkitsdk.TransferInfo r1 = r14.a     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            int r1 = r1.i     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            r0.append(r1)     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            java.lang.String r1 = " "
            r0.append(r1)     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            goto L_0x00f3
        L_0x00a9:
            r5.write(r2, r1, r8)     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            com.yunos.carkitsdk.TransferInfo r9 = r14.a     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            com.yunos.carkitsdk.TransferInfo r10 = r14.a     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            long r10 = r10.c     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            long r12 = (long) r8     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            long r10 = r10 + r12
            r9.c = r10     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            com.yunos.carkitsdk.TransferInfo r8 = r14.a     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            long r8 = r8.c     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            r10 = 100
            long r8 = r8 * r10
            com.yunos.carkitsdk.TransferInfo r10 = r14.a     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            long r10 = r10.e     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            long r8 = r8 / r10
            int r8 = (int) r8     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            com.yunos.carkitsdk.TransferInfo r9 = r14.a     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            long r9 = r9.c     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            r11 = 0
            long r9 = r9 - r6
            com.yunos.carkitsdk.TransferInfo r11 = r14.a     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            int r11 = r11.k     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            if (r8 > r11) goto L_0x00d7
            r11 = 1048576(0x100000, double:5.180654E-318)
            int r9 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r9 <= 0) goto L_0x00e6
        L_0x00d7:
            com.yunos.carkitsdk.TransferInfo r6 = r14.a     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            r6.k = r8     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            fce r6 = r14.g     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            com.yunos.carkitsdk.TransferInfo r7 = r14.a     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            r6.c(r7)     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            com.yunos.carkitsdk.TransferInfo r6 = r14.a     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            long r6 = r6.c     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
        L_0x00e6:
            com.yunos.carkitsdk.TransferInfo r8 = r14.a     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            long r8 = r8.c     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            com.yunos.carkitsdk.TransferInfo r10 = r14.a     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            long r10 = r10.e     // Catch:{ IOException -> 0x0173, all -> 0x0170 }
            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r8 >= 0) goto L_0x00f3
            goto L_0x0085
        L_0x00f3:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "transferId="
            r0.<init>(r1)
            com.yunos.carkitsdk.TransferInfo r1 = r14.a
            int r1 = r1.i
            r0.append(r1)
            java.lang.String r1 = " length="
            r0.append(r1)
            com.yunos.carkitsdk.TransferInfo r1 = r14.a
            long r1 = r1.e
            r0.append(r1)
            java.lang.String r1 = "transfered bytes="
            r0.append(r1)
            com.yunos.carkitsdk.TransferInfo r1 = r14.a
            long r1 = r1.c
            r0.append(r1)
            com.yunos.carkitsdk.TransferInfo r0 = r14.a
            long r0 = r0.c
            com.yunos.carkitsdk.TransferInfo r2 = r14.a
            long r6 = r2.e
            int r0 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r0 != 0) goto L_0x0138
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "receive file complete transferId="
            r0.<init>(r1)
            com.yunos.carkitsdk.TransferInfo r1 = r14.a
            int r1 = r1.i
            r0.append(r1)
            com.yunos.carkitsdk.TransferInfo r0 = r14.a
            r0.h = r4
            goto L_0x0153
        L_0x0138:
            com.yunos.carkitsdk.TransferInfo r0 = r14.a
            boolean r0 = r0.a()
            if (r0 != 0) goto L_0x0145
            com.yunos.carkitsdk.TransferInfo r0 = r14.a
            r0.h = r3
            goto L_0x0153
        L_0x0145:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "receive file is cancel transferId="
            r0.<init>(r1)
            com.yunos.carkitsdk.TransferInfo r1 = r14.a
            int r1 = r1.i
            r0.append(r1)
        L_0x0153:
            fce r0 = r14.g
            com.yunos.carkitsdk.TransferInfo r1 = r14.a
            r0.c(r1)
            fcd r0 = r14.h
            com.yunos.carkitsdk.TransferInfo r1 = r14.a
            int r1 = r1.i
            r0.b(r1)
            r14.c()
            r5.close()     // Catch:{ IOException -> 0x016a }
            return
        L_0x016a:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x01fc
        L_0x0170:
            r0 = move-exception
            goto L_0x01fd
        L_0x0173:
            r0 = move-exception
            r1 = r5
            goto L_0x017b
        L_0x0176:
            r0 = move-exception
            r5 = r1
            goto L_0x01fd
        L_0x017a:
            r0 = move-exception
        L_0x017b:
            r0.printStackTrace()     // Catch:{ all -> 0x0176 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "transferId="
            r0.<init>(r2)
            com.yunos.carkitsdk.TransferInfo r2 = r14.a
            int r2 = r2.i
            r0.append(r2)
            java.lang.String r2 = " length="
            r0.append(r2)
            com.yunos.carkitsdk.TransferInfo r2 = r14.a
            long r5 = r2.e
            r0.append(r5)
            java.lang.String r2 = "transfered bytes="
            r0.append(r2)
            com.yunos.carkitsdk.TransferInfo r2 = r14.a
            long r5 = r2.c
            r0.append(r5)
            com.yunos.carkitsdk.TransferInfo r0 = r14.a
            long r5 = r0.c
            com.yunos.carkitsdk.TransferInfo r0 = r14.a
            long r7 = r0.e
            int r0 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r0 != 0) goto L_0x01c3
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "receive file complete transferId="
            r0.<init>(r2)
            com.yunos.carkitsdk.TransferInfo r2 = r14.a
            int r2 = r2.i
            r0.append(r2)
            com.yunos.carkitsdk.TransferInfo r0 = r14.a
            r0.h = r4
            goto L_0x01de
        L_0x01c3:
            com.yunos.carkitsdk.TransferInfo r0 = r14.a
            boolean r0 = r0.a()
            if (r0 != 0) goto L_0x01d0
            com.yunos.carkitsdk.TransferInfo r0 = r14.a
            r0.h = r3
            goto L_0x01de
        L_0x01d0:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "receive file is cancel transferId="
            r0.<init>(r2)
            com.yunos.carkitsdk.TransferInfo r2 = r14.a
            int r2 = r2.i
            r0.append(r2)
        L_0x01de:
            fce r0 = r14.g
            com.yunos.carkitsdk.TransferInfo r2 = r14.a
            r0.c(r2)
            fcd r0 = r14.h
            com.yunos.carkitsdk.TransferInfo r2 = r14.a
            int r2 = r2.i
            r0.b(r2)
            r14.c()
            if (r1 == 0) goto L_0x01fc
            r1.close()     // Catch:{ IOException -> 0x01f7 }
            return
        L_0x01f7:
            r0 = move-exception
            r0.printStackTrace()
            return
        L_0x01fc:
            return
        L_0x01fd:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "transferId="
            r1.<init>(r2)
            com.yunos.carkitsdk.TransferInfo r2 = r14.a
            int r2 = r2.i
            r1.append(r2)
            java.lang.String r2 = " length="
            r1.append(r2)
            com.yunos.carkitsdk.TransferInfo r2 = r14.a
            long r6 = r2.e
            r1.append(r6)
            java.lang.String r2 = "transfered bytes="
            r1.append(r2)
            com.yunos.carkitsdk.TransferInfo r2 = r14.a
            long r6 = r2.c
            r1.append(r6)
            com.yunos.carkitsdk.TransferInfo r1 = r14.a
            long r1 = r1.c
            com.yunos.carkitsdk.TransferInfo r6 = r14.a
            long r6 = r6.e
            int r1 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r1 != 0) goto L_0x0242
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "receive file complete transferId="
            r1.<init>(r2)
            com.yunos.carkitsdk.TransferInfo r2 = r14.a
            int r2 = r2.i
            r1.append(r2)
            com.yunos.carkitsdk.TransferInfo r1 = r14.a
            r1.h = r4
            goto L_0x025d
        L_0x0242:
            com.yunos.carkitsdk.TransferInfo r1 = r14.a
            boolean r1 = r1.a()
            if (r1 != 0) goto L_0x024f
            com.yunos.carkitsdk.TransferInfo r1 = r14.a
            r1.h = r3
            goto L_0x025d
        L_0x024f:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "receive file is cancel transferId="
            r1.<init>(r2)
            com.yunos.carkitsdk.TransferInfo r2 = r14.a
            int r2 = r2.i
            r1.append(r2)
        L_0x025d:
            fce r1 = r14.g
            com.yunos.carkitsdk.TransferInfo r2 = r14.a
            r1.c(r2)
            fcd r1 = r14.h
            com.yunos.carkitsdk.TransferInfo r2 = r14.a
            int r2 = r2.i
            r1.b(r2)
            r14.c()
            if (r5 == 0) goto L_0x027a
            r5.close()     // Catch:{ IOException -> 0x0276 }
            goto L_0x027a
        L_0x0276:
            r1 = move-exception
            r1.printStackTrace()
        L_0x027a:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fca.run():void");
    }
}
