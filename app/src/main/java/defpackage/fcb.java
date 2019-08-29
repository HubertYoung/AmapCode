package defpackage;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.yunos.carkitsdk.TransferInfo;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

/* renamed from: fcb reason: default package */
/* compiled from: FileSender */
public final class fcb implements Runnable {
    TransferInfo a;
    fce b;
    fcd c;
    private String d;
    private int e;
    private Socket f;
    private long g = 0;

    fcb(fcc fcc, fce fce, fcd fcd) {
        this.d = fcc.e;
        this.e = fcc.f;
        this.b = fce;
        this.c = fcd;
        this.g = fcc.l;
        TransferInfo transferInfo = new TransferInfo(fcc.a, fcc.g, fcc.b, fcc.h, fcc.c, fcc.d, fcc.i, fcc.k);
        this.a = transferInfo;
        StringBuilder sb = new StringBuilder("create FileSender: ");
        sb.append(this.a.b);
        sb.append(" src");
        sb.append(this.a.f);
        sb.append(Token.SEPARATOR);
        sb.append(this.a.g);
        sb.append(" ip=");
        sb.append(this.d);
        sb.append(" port=");
        sb.append(this.e);
        sb.append(" transferId=");
        sb.append(this.a.i);
        sb.append("seek=");
        sb.append(fcc.l);
    }

    /* access modifiers changed from: 0000 */
    public final void a(int i) {
        this.a.l = i;
        this.a.h = 5;
    }

    public final void a() {
        this.a.h = 5;
        try {
            this.f.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public final void run() {
        File file = new File(this.a.b);
        if (file.exists() && !file.isDirectory()) {
            b();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x01a2  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x01b6  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x01f8 A[Catch:{ IOException -> 0x0204 }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x01ff A[Catch:{ IOException -> 0x0204 }] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0226  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0268 A[Catch:{ IOException -> 0x0273 }] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x026f A[Catch:{ IOException -> 0x0273 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b() {
        /*
            r15 = this;
            java.io.File r0 = new java.io.File
            com.yunos.carkitsdk.TransferInfo r1 = r15.a
            java.lang.String r1 = r1.b
            r0.<init>(r1)
            java.net.Socket r1 = new java.net.Socket
            r1.<init>()
            r15.f = r1
            long r0 = r0.length()
            com.yunos.carkitsdk.TransferInfo r2 = r15.a
            r2.e = r0
            r2 = 0
            r3 = 4
            r4 = 3
            java.lang.String r5 = r15.d     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            java.net.InetAddress r5 = java.net.InetAddress.getByName(r5)     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            java.net.Socket r6 = r15.f     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            r6.bind(r2)     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            java.net.Socket r7 = r15.f     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            java.net.InetSocketAddress r8 = new java.net.InetSocketAddress     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            java.lang.String r5 = r5.getHostAddress()     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            int r9 = r15.e     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            r8.<init>(r5, r9)     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            r5 = 5000(0x1388, float:7.006E-42)
            r7.connect(r8, r5)     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            java.lang.String r7 = "connect tick="
            r5.<init>(r7)     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            long r9 = r6.longValue()     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            r6 = 0
            long r7 = r7 - r9
            r5.append(r7)     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            java.lang.String r6 = "connected to "
            r5.<init>(r6)     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            java.lang.String r6 = r15.d     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            r5.append(r6)     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            java.lang.String r6 = " port="
            r5.append(r6)     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            int r6 = r15.e     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            r5.append(r6)     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            java.lang.String r6 = " transferId="
            r5.append(r6)     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            com.yunos.carkitsdk.TransferInfo r6 = r15.a     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            int r6 = r6.i     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            r5.append(r6)     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            java.net.Socket r5 = r15.f     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            java.io.OutputStream r5 = r5.getOutputStream()     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            java.io.BufferedInputStream r6 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            com.yunos.carkitsdk.TransferInfo r8 = r15.a     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            java.lang.String r8 = r8.b     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            r7.<init>(r8)     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            r6.<init>(r7)     // Catch:{ IOException -> 0x0194, all -> 0x018f }
            long r7 = r15.g     // Catch:{ IOException -> 0x018d }
            r9 = 0
            int r2 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r2 <= 0) goto L_0x00b3
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x018d }
            java.lang.String r7 = "seek to "
            r2.<init>(r7)     // Catch:{ IOException -> 0x018d }
            long r7 = r15.g     // Catch:{ IOException -> 0x018d }
            r2.append(r7)     // Catch:{ IOException -> 0x018d }
            long r7 = r15.g     // Catch:{ IOException -> 0x018d }
            long r7 = r6.skip(r7)     // Catch:{ IOException -> 0x018d }
            java.lang.String r2 = "skiped "
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ IOException -> 0x018d }
            r2.concat(r7)     // Catch:{ IOException -> 0x018d }
            com.yunos.carkitsdk.TransferInfo r2 = r15.a     // Catch:{ IOException -> 0x018d }
            long r7 = r15.g     // Catch:{ IOException -> 0x018d }
            r2.c = r7     // Catch:{ IOException -> 0x018d }
        L_0x00b3:
            r2 = 64768(0xfd00, float:9.0759E-41)
            byte[] r2 = new byte[r2]     // Catch:{ IOException -> 0x018d }
            com.yunos.carkitsdk.TransferInfo r7 = r15.a     // Catch:{ IOException -> 0x018d }
            r8 = 1
            r7.h = r8     // Catch:{ IOException -> 0x018d }
            fce r7 = r15.b     // Catch:{ IOException -> 0x018d }
            com.yunos.carkitsdk.TransferInfo r8 = r15.a     // Catch:{ IOException -> 0x018d }
            r7.b(r8)     // Catch:{ IOException -> 0x018d }
            com.yunos.carkitsdk.TransferInfo r7 = r15.a     // Catch:{ IOException -> 0x018d }
            r8 = 2
            r7.h = r8     // Catch:{ IOException -> 0x018d }
        L_0x00c9:
            com.yunos.carkitsdk.TransferInfo r7 = r15.a     // Catch:{ IOException -> 0x018d }
            boolean r7 = r7.a()     // Catch:{ IOException -> 0x018d }
            if (r7 == 0) goto L_0x00d2
            goto L_0x0120
        L_0x00d2:
            int r7 = r6.read(r2)     // Catch:{ IOException -> 0x018d }
            r8 = -1
            if (r7 == r8) goto L_0x0120
            com.yunos.carkitsdk.TransferInfo r8 = r15.a     // Catch:{ IOException -> 0x018d }
            long r11 = r8.c     // Catch:{ IOException -> 0x018d }
            int r8 = (r11 > r0 ? 1 : (r11 == r0 ? 0 : -1))
            if (r8 == 0) goto L_0x0120
            r8 = 0
            r5.write(r2, r8, r7)     // Catch:{ IOException -> 0x018d }
            com.yunos.carkitsdk.TransferInfo r8 = r15.a     // Catch:{ IOException -> 0x018d }
            com.yunos.carkitsdk.TransferInfo r11 = r15.a     // Catch:{ IOException -> 0x018d }
            long r11 = r11.c     // Catch:{ IOException -> 0x018d }
            long r13 = (long) r7     // Catch:{ IOException -> 0x018d }
            long r11 = r11 + r13
            r8.c = r11     // Catch:{ IOException -> 0x018d }
            com.yunos.carkitsdk.TransferInfo r7 = r15.a     // Catch:{ IOException -> 0x018d }
            long r7 = r7.c     // Catch:{ IOException -> 0x018d }
            r11 = 100
            long r7 = r7 * r11
            com.yunos.carkitsdk.TransferInfo r11 = r15.a     // Catch:{ IOException -> 0x018d }
            long r11 = r11.e     // Catch:{ IOException -> 0x018d }
            long r7 = r7 / r11
            int r7 = (int) r7     // Catch:{ IOException -> 0x018d }
            com.yunos.carkitsdk.TransferInfo r8 = r15.a     // Catch:{ IOException -> 0x018d }
            long r11 = r8.c     // Catch:{ IOException -> 0x018d }
            r8 = 0
            long r11 = r11 - r9
            com.yunos.carkitsdk.TransferInfo r8 = r15.a     // Catch:{ IOException -> 0x018d }
            int r8 = r8.k     // Catch:{ IOException -> 0x018d }
            if (r7 > r8) goto L_0x0110
            r13 = 1048576(0x100000, double:5.180654E-318)
            int r8 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r8 <= 0) goto L_0x00c9
        L_0x0110:
            com.yunos.carkitsdk.TransferInfo r8 = r15.a     // Catch:{ IOException -> 0x018d }
            r8.k = r7     // Catch:{ IOException -> 0x018d }
            fce r7 = r15.b     // Catch:{ IOException -> 0x018d }
            com.yunos.carkitsdk.TransferInfo r8 = r15.a     // Catch:{ IOException -> 0x018d }
            r7.b(r8)     // Catch:{ IOException -> 0x018d }
            com.yunos.carkitsdk.TransferInfo r7 = r15.a     // Catch:{ IOException -> 0x018d }
            long r9 = r7.c     // Catch:{ IOException -> 0x018d }
            goto L_0x00c9
        L_0x0120:
            com.yunos.carkitsdk.TransferInfo r2 = r15.a
            long r7 = r2.c
            int r0 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
            if (r0 != 0) goto L_0x013c
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "transfer complete transferId="
            r0.<init>(r1)
            com.yunos.carkitsdk.TransferInfo r1 = r15.a
            int r1 = r1.i
            r0.append(r1)
            com.yunos.carkitsdk.TransferInfo r0 = r15.a
            r0.h = r4
            goto L_0x0165
        L_0x013c:
            com.yunos.carkitsdk.TransferInfo r0 = r15.a
            boolean r0 = r0.a()
            if (r0 != 0) goto L_0x0157
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "socket error transferId="
            r0.<init>(r1)
            com.yunos.carkitsdk.TransferInfo r1 = r15.a
            int r1 = r1.i
            r0.append(r1)
            com.yunos.carkitsdk.TransferInfo r0 = r15.a
            r0.h = r3
            goto L_0x0165
        L_0x0157:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "cancel local, not socket error transferId="
            r0.<init>(r1)
            com.yunos.carkitsdk.TransferInfo r1 = r15.a
            int r1 = r1.i
            r0.append(r1)
        L_0x0165:
            fce r0 = r15.b
            com.yunos.carkitsdk.TransferInfo r1 = r15.a
            r0.b(r1)
            fcd r0 = r15.c
            com.yunos.carkitsdk.TransferInfo r1 = r15.a
            int r1 = r1.i
            r0.a(r1)
            fcd r0 = r15.c
            r0.a()
            java.net.Socket r0 = r15.f     // Catch:{ IOException -> 0x0187 }
            if (r0 == 0) goto L_0x0183
            java.net.Socket r0 = r15.f     // Catch:{ IOException -> 0x0187 }
            r0.close()     // Catch:{ IOException -> 0x0187 }
        L_0x0183:
            r6.close()     // Catch:{ IOException -> 0x0187 }
            return
        L_0x0187:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0203
        L_0x018d:
            r2 = move-exception
            goto L_0x0197
        L_0x018f:
            r5 = move-exception
            r6 = r2
            r2 = r5
            goto L_0x020a
        L_0x0194:
            r5 = move-exception
            r6 = r2
            r2 = r5
        L_0x0197:
            r2.printStackTrace()     // Catch:{ all -> 0x0209 }
            com.yunos.carkitsdk.TransferInfo r2 = r15.a
            long r7 = r2.c
            int r0 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
            if (r0 != 0) goto L_0x01b6
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "transfer complete transferId="
            r0.<init>(r1)
            com.yunos.carkitsdk.TransferInfo r1 = r15.a
            int r1 = r1.i
            r0.append(r1)
            com.yunos.carkitsdk.TransferInfo r0 = r15.a
            r0.h = r4
            goto L_0x01df
        L_0x01b6:
            com.yunos.carkitsdk.TransferInfo r0 = r15.a
            boolean r0 = r0.a()
            if (r0 != 0) goto L_0x01d1
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "socket error transferId="
            r0.<init>(r1)
            com.yunos.carkitsdk.TransferInfo r1 = r15.a
            int r1 = r1.i
            r0.append(r1)
            com.yunos.carkitsdk.TransferInfo r0 = r15.a
            r0.h = r3
            goto L_0x01df
        L_0x01d1:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "cancel local, not socket error transferId="
            r0.<init>(r1)
            com.yunos.carkitsdk.TransferInfo r1 = r15.a
            int r1 = r1.i
            r0.append(r1)
        L_0x01df:
            fce r0 = r15.b
            com.yunos.carkitsdk.TransferInfo r1 = r15.a
            r0.b(r1)
            fcd r0 = r15.c
            com.yunos.carkitsdk.TransferInfo r1 = r15.a
            int r1 = r1.i
            r0.a(r1)
            fcd r0 = r15.c
            r0.a()
            java.net.Socket r0 = r15.f     // Catch:{ IOException -> 0x0204 }
            if (r0 == 0) goto L_0x01fd
            java.net.Socket r0 = r15.f     // Catch:{ IOException -> 0x0204 }
            r0.close()     // Catch:{ IOException -> 0x0204 }
        L_0x01fd:
            if (r6 == 0) goto L_0x0203
            r6.close()     // Catch:{ IOException -> 0x0204 }
            return
        L_0x0203:
            return
        L_0x0204:
            r0 = move-exception
            r0.printStackTrace()
            return
        L_0x0209:
            r2 = move-exception
        L_0x020a:
            com.yunos.carkitsdk.TransferInfo r5 = r15.a
            long r7 = r5.c
            int r0 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
            if (r0 != 0) goto L_0x0226
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "transfer complete transferId="
            r0.<init>(r1)
            com.yunos.carkitsdk.TransferInfo r1 = r15.a
            int r1 = r1.i
            r0.append(r1)
            com.yunos.carkitsdk.TransferInfo r0 = r15.a
            r0.h = r4
            goto L_0x024f
        L_0x0226:
            com.yunos.carkitsdk.TransferInfo r0 = r15.a
            boolean r0 = r0.a()
            if (r0 != 0) goto L_0x0241
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "socket error transferId="
            r0.<init>(r1)
            com.yunos.carkitsdk.TransferInfo r1 = r15.a
            int r1 = r1.i
            r0.append(r1)
            com.yunos.carkitsdk.TransferInfo r0 = r15.a
            r0.h = r3
            goto L_0x024f
        L_0x0241:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "cancel local, not socket error transferId="
            r0.<init>(r1)
            com.yunos.carkitsdk.TransferInfo r1 = r15.a
            int r1 = r1.i
            r0.append(r1)
        L_0x024f:
            fce r0 = r15.b
            com.yunos.carkitsdk.TransferInfo r1 = r15.a
            r0.b(r1)
            fcd r0 = r15.c
            com.yunos.carkitsdk.TransferInfo r1 = r15.a
            int r1 = r1.i
            r0.a(r1)
            fcd r0 = r15.c
            r0.a()
            java.net.Socket r0 = r15.f     // Catch:{ IOException -> 0x0273 }
            if (r0 == 0) goto L_0x026d
            java.net.Socket r0 = r15.f     // Catch:{ IOException -> 0x0273 }
            r0.close()     // Catch:{ IOException -> 0x0273 }
        L_0x026d:
            if (r6 == 0) goto L_0x0277
            r6.close()     // Catch:{ IOException -> 0x0273 }
            goto L_0x0277
        L_0x0273:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0277:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fcb.b():void");
    }
}
