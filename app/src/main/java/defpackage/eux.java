package defpackage;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import com.alipay.multimedia.img.utils.ImageFileType;

/* renamed from: eux reason: default package */
/* compiled from: Base64 */
public final class eux extends euy {
    static final byte[] a = {13, 10};
    private static final byte[] e = {65, 66, 67, 68, 69, 70, ImageFileType.HEAD_GIF_0, ImageFileType.HEAD_HEVC_0, 73, 74, 75, 76, 77, 78, 79, 80, 81, ImageFileType.HEAD_WEBP_0, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] f = {65, 66, 67, 68, 69, 70, ImageFileType.HEAD_GIF_0, ImageFileType.HEAD_HEVC_0, 73, 74, 75, 76, 77, 78, 79, 80, 81, ImageFileType.HEAD_WEBP_0, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
    private static final byte[] g = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51};
    private final byte[] h;
    private final byte[] i;
    private final byte[] j;
    private final int k;
    private final int l;

    public eux() {
        this(0);
    }

    private eux(byte b) {
        this(a);
    }

    private eux(byte[] bArr) {
        this(bArr, false);
    }

    private eux(byte[] bArr, boolean z) {
        super(bArr == null ? 0 : bArr.length);
        this.i = g;
        if (bArr == null) {
            this.l = 4;
            this.j = null;
        } else if (b(bArr)) {
            String a2 = euz.a(bArr, "UTF-8");
            StringBuilder sb = new StringBuilder("lineSeparator must not contain base64 characters: [");
            sb.append(a2);
            sb.append("]");
            throw new IllegalArgumentException(sb.toString());
        } else {
            this.l = 4;
            this.j = null;
        }
        this.k = this.l - 1;
        this.h = z ? f : e;
    }

    /* JADX WARNING: type inference failed for: r2v33 */
    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r2v2, types: [byte, int] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(byte[] r8, int r9, int r10, defpackage.euy.a r11) {
        /*
            r7 = this;
            boolean r0 = r11.f
            if (r0 == 0) goto L_0x0005
            return
        L_0x0005:
            r0 = 0
            r1 = 1
            if (r10 >= 0) goto L_0x00df
            r11.f = r1
            int r8 = r11.h
            if (r8 != 0) goto L_0x0014
            int r8 = r7.d
            if (r8 != 0) goto L_0x0014
            return
        L_0x0014:
            int r8 = r7.l
            byte[] r8 = a(r8, r11)
            int r9 = r11.d
            int r10 = r11.h
            switch(r10) {
                case 0: goto L_0x00bc;
                case 1: goto L_0x007e;
                case 2: goto L_0x0037;
                default: goto L_0x0021;
            }
        L_0x0021:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "Impossible modulus "
            r9.<init>(r10)
            int r10 = r11.h
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x0037:
            int r10 = r11.d
            int r1 = r10 + 1
            r11.d = r1
            byte[] r1 = r7.h
            int r2 = r11.a
            int r2 = r2 >> 10
            r2 = r2 & 63
            byte r1 = r1[r2]
            r8[r10] = r1
            int r10 = r11.d
            int r1 = r10 + 1
            r11.d = r1
            byte[] r1 = r7.h
            int r2 = r11.a
            int r2 = r2 >> 4
            r2 = r2 & 63
            byte r1 = r1[r2]
            r8[r10] = r1
            int r10 = r11.d
            int r1 = r10 + 1
            r11.d = r1
            byte[] r1 = r7.h
            int r2 = r11.a
            int r2 = r2 << 2
            r2 = r2 & 63
            byte r1 = r1[r2]
            r8[r10] = r1
            byte[] r10 = r7.h
            byte[] r1 = e
            if (r10 != r1) goto L_0x00bc
            int r10 = r11.d
            int r1 = r10 + 1
            r11.d = r1
            byte r1 = r7.c
            r8[r10] = r1
            goto L_0x00bc
        L_0x007e:
            int r10 = r11.d
            int r1 = r10 + 1
            r11.d = r1
            byte[] r1 = r7.h
            int r2 = r11.a
            int r2 = r2 >> 2
            r2 = r2 & 63
            byte r1 = r1[r2]
            r8[r10] = r1
            int r10 = r11.d
            int r1 = r10 + 1
            r11.d = r1
            byte[] r1 = r7.h
            int r2 = r11.a
            int r2 = r2 << 4
            r2 = r2 & 63
            byte r1 = r1[r2]
            r8[r10] = r1
            byte[] r10 = r7.h
            byte[] r1 = e
            if (r10 != r1) goto L_0x00bc
            int r10 = r11.d
            int r1 = r10 + 1
            r11.d = r1
            byte r1 = r7.c
            r8[r10] = r1
            int r10 = r11.d
            int r1 = r10 + 1
            r11.d = r1
            byte r1 = r7.c
            r8[r10] = r1
        L_0x00bc:
            int r10 = r11.g
            int r1 = r11.d
            int r1 = r1 - r9
            int r10 = r10 + r1
            r11.g = r10
            int r9 = r7.d
            if (r9 <= 0) goto L_0x00de
            int r9 = r11.g
            if (r9 <= 0) goto L_0x00de
            byte[] r9 = r7.j
            int r10 = r11.d
            byte[] r1 = r7.j
            int r1 = r1.length
            java.lang.System.arraycopy(r9, r0, r8, r10, r1)
            int r8 = r11.d
            byte[] r9 = r7.j
            int r9 = r9.length
            int r8 = r8 + r9
            r11.d = r8
        L_0x00de:
            return
        L_0x00df:
            r2 = r9
            r9 = 0
        L_0x00e1:
            if (r9 >= r10) goto L_0x0172
            int r3 = r7.l
            byte[] r3 = a(r3, r11)
            int r4 = r11.h
            int r4 = r4 + r1
            int r4 = r4 % 3
            r11.h = r4
            int r4 = r2 + 1
            byte r2 = r8[r2]
            if (r2 >= 0) goto L_0x00f8
            int r2 = r2 + 256
        L_0x00f8:
            int r5 = r11.a
            int r5 = r5 << 8
            int r5 = r5 + r2
            r11.a = r5
            int r2 = r11.h
            if (r2 != 0) goto L_0x016d
            int r2 = r11.d
            int r5 = r2 + 1
            r11.d = r5
            byte[] r5 = r7.h
            int r6 = r11.a
            int r6 = r6 >> 18
            r6 = r6 & 63
            byte r5 = r5[r6]
            r3[r2] = r5
            int r2 = r11.d
            int r5 = r2 + 1
            r11.d = r5
            byte[] r5 = r7.h
            int r6 = r11.a
            int r6 = r6 >> 12
            r6 = r6 & 63
            byte r5 = r5[r6]
            r3[r2] = r5
            int r2 = r11.d
            int r5 = r2 + 1
            r11.d = r5
            byte[] r5 = r7.h
            int r6 = r11.a
            int r6 = r6 >> 6
            r6 = r6 & 63
            byte r5 = r5[r6]
            r3[r2] = r5
            int r2 = r11.d
            int r5 = r2 + 1
            r11.d = r5
            byte[] r5 = r7.h
            int r6 = r11.a
            r6 = r6 & 63
            byte r5 = r5[r6]
            r3[r2] = r5
            int r2 = r11.g
            int r2 = r2 + 4
            r11.g = r2
            int r2 = r7.d
            if (r2 <= 0) goto L_0x016d
            int r2 = r7.d
            int r5 = r11.g
            if (r2 > r5) goto L_0x016d
            byte[] r2 = r7.j
            int r5 = r11.d
            byte[] r6 = r7.j
            int r6 = r6.length
            java.lang.System.arraycopy(r2, r0, r3, r5, r6)
            int r2 = r11.d
            byte[] r3 = r7.j
            int r3 = r3.length
            int r2 = r2 + r3
            r11.d = r2
            r11.g = r0
        L_0x016d:
            int r9 = r9 + 1
            r2 = r4
            goto L_0x00e1
        L_0x0172:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eux.a(byte[], int, int, euy$a):void");
    }

    /* access modifiers changed from: protected */
    public final boolean a(byte b) {
        return b >= 0 && b < this.i.length && this.i[b] != -1;
    }

    public static byte[] a(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        eux eux = new eux(a, true);
        long c = eux.c(bArr);
        if (c > 2147483647L) {
            StringBuilder sb = new StringBuilder("Input array too big, the output array would be bigger (");
            sb.append(c);
            sb.append(") than the specified maximum size of 2147483647");
            throw new IllegalArgumentException(sb.toString());
        } else if (bArr == null || bArr.length == 0) {
            return bArr;
        } else {
            a aVar = new a();
            eux.a(bArr, 0, bArr.length, aVar);
            eux.a(bArr, 0, -1, aVar);
            byte[] bArr2 = new byte[(aVar.d - aVar.e)];
            int length = bArr2.length;
            if (aVar.c != null) {
                int min = Math.min(aVar.c != null ? aVar.d - aVar.e : 0, length);
                System.arraycopy(aVar.c, aVar.e, bArr2, 0, min);
                aVar.e += min;
                if (aVar.e >= aVar.d) {
                    aVar.c = null;
                }
            }
            return bArr2;
        }
    }
}
