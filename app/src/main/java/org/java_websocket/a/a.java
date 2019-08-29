package org.java_websocket.a;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import com.alipay.multimedia.img.utils.ImageFileType;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class a {
    static final /* synthetic */ boolean a = true;
    private static final byte[] b = {65, 66, 67, 68, 69, 70, ImageFileType.HEAD_GIF_0, ImageFileType.HEAD_HEVC_0, 73, 74, 75, 76, 77, 78, 79, 80, 81, ImageFileType.HEAD_WEBP_0, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] c = {-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};
    private static final byte[] d = {65, 66, 67, 68, 69, 70, ImageFileType.HEAD_GIF_0, ImageFileType.HEAD_HEVC_0, 73, 74, 75, 76, 77, 78, 79, 80, 81, ImageFileType.HEAD_WEBP_0, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
    private static final byte[] e = {-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, 63, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};
    private static final byte[] f = {45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, ImageFileType.HEAD_GIF_0, ImageFileType.HEAD_HEVC_0, 73, 74, 75, 76, 77, 78, 79, 80, 81, ImageFileType.HEAD_WEBP_0, 83, 84, 85, 86, 87, 88, 89, 90, 95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};
    private static final byte[] g = {-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 0, -9, -9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, -9, -9, -9, -1, -9, -9, -9, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, -9, -9, -9, -9, 37, -9, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};

    /* renamed from: org.java_websocket.a.a$a reason: collision with other inner class name */
    public static class C0092a extends FilterOutputStream {
        private boolean a;
        private int b;
        private byte[] c;
        private int d;
        private int e;
        private boolean f;
        private byte[] g;
        private boolean h;
        private int i;
        private byte[] j;

        public void write(int i2) {
            if (this.h) {
                this.out.write(i2);
                return;
            }
            if (this.a) {
                byte[] bArr = this.c;
                int i3 = this.b;
                this.b = i3 + 1;
                bArr[i3] = (byte) i2;
                if (this.b >= this.d) {
                    this.out.write(a.a(this.c, 0, this.d, this.g, 0, this.i));
                    this.e += 4;
                    if (this.f && this.e >= 76) {
                        this.out.write(10);
                        this.e = 0;
                    }
                    this.b = 0;
                }
            } else {
                int i4 = i2 & 127;
                if (this.j[i4] > -5) {
                    byte[] bArr2 = this.c;
                    int i5 = this.b;
                    this.b = i5 + 1;
                    bArr2[i5] = (byte) i2;
                    if (this.b >= this.d) {
                        this.out.write(this.g, 0, a.a(this.c, this.g, this.i));
                        this.b = 0;
                    }
                } else if (this.j[i4] != -5) {
                    throw new IOException("Invalid character in Base64 data.");
                }
            }
        }

        public void write(byte[] bArr, int i2, int i3) {
            if (this.h) {
                this.out.write(bArr, i2, i3);
                return;
            }
            for (int i4 = 0; i4 < i3; i4++) {
                write(bArr[i2 + i4]);
            }
        }

        public void close() {
            if (this.b > 0) {
                if (this.a) {
                    this.out.write(a.a(this.c, 0, this.b, this.g, 0, this.i));
                    this.b = 0;
                } else {
                    throw new IOException("Base64 input not properly padded.");
                }
            }
            super.close();
            this.c = null;
            this.out = null;
        }
    }

    private a() {
    }

    public static String a(byte[] bArr) {
        String str;
        try {
            str = a(bArr, bArr.length);
        } catch (IOException e2) {
            if (!a) {
                throw new AssertionError(e2.getMessage());
            }
            str = null;
        }
        if (a || str != null) {
            return str;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: private */
    public static byte[] a(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        byte[] bArr3 = (i4 & 16) == 16 ? d : (i4 & 32) == 32 ? f : b;
        int i5 = 0;
        int i6 = (i2 > 1 ? (bArr[i + 1] << 24) >>> 16 : 0) | (i2 > 0 ? (bArr[i] << 24) >>> 8 : 0);
        if (i2 > 2) {
            i5 = (bArr[i + 2] << 24) >>> 24;
        }
        int i7 = i6 | i5;
        switch (i2) {
            case 1:
                bArr2[i3] = bArr3[i7 >>> 18];
                bArr2[i3 + 1] = bArr3[(i7 >>> 12) & 63];
                bArr2[i3 + 2] = 61;
                bArr2[i3 + 3] = 61;
                return bArr2;
            case 2:
                bArr2[i3] = bArr3[i7 >>> 18];
                bArr2[i3 + 1] = bArr3[(i7 >>> 12) & 63];
                bArr2[i3 + 2] = bArr3[(i7 >>> 6) & 63];
                bArr2[i3 + 3] = 61;
                return bArr2;
            case 3:
                bArr2[i3] = bArr3[i7 >>> 18];
                bArr2[i3 + 1] = bArr3[(i7 >>> 12) & 63];
                bArr2[i3 + 2] = bArr3[(i7 >>> 6) & 63];
                bArr2[i3 + 3] = bArr3[i7 & 63];
                return bArr2;
            default:
                return bArr2;
        }
    }

    private static String a(byte[] bArr, int i) {
        byte[] bArr2;
        if (bArr == null) {
            throw new NullPointerException("Cannot serialize a null array.");
        } else if (i < 0) {
            throw new IllegalArgumentException("Cannot have length offset: ".concat(String.valueOf(i)));
        } else if (i + 0 > bArr.length) {
            throw new IllegalArgumentException(String.format("Cannot have offset of %d and length of %d with array of length %d", new Object[]{Integer.valueOf(0), Integer.valueOf(i), Integer.valueOf(bArr.length)}));
        } else {
            int i2 = 4;
            int i3 = (i / 3) * 4;
            if (i % 3 <= 0) {
                i2 = 0;
            }
            byte[] bArr3 = new byte[(i3 + i2)];
            int i4 = i - 2;
            int i5 = 0;
            int i6 = 0;
            while (i5 < i4) {
                a(bArr, i5 + 0, 3, bArr3, i6, 0);
                i5 += 3;
                i6 += 4;
            }
            if (i5 < i) {
                a(bArr, i5 + 0, i - i5, bArr3, i6, 0);
                i6 += 4;
            }
            if (i6 <= bArr3.length - 1) {
                bArr2 = new byte[i6];
                System.arraycopy(bArr3, 0, bArr2, 0, i6);
            } else {
                bArr2 = bArr3;
            }
            try {
                return new String(bArr2, "US-ASCII");
            } catch (UnsupportedEncodingException unused) {
                return new String(bArr2);
            }
        }
    }

    static /* synthetic */ int a(byte[] bArr, byte[] bArr2, int i) {
        if (bArr == null) {
            throw new NullPointerException("Source array was null.");
        } else if (bArr2 == null) {
            throw new NullPointerException("Destination array was null.");
        } else if (3 >= bArr.length) {
            throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and still process four bytes.", new Object[]{Integer.valueOf(bArr.length), Integer.valueOf(0)}));
        } else if (2 >= bArr2.length) {
            throw new IllegalArgumentException(String.format("Destination array with length %d cannot have offset of %d and still store three bytes.", new Object[]{Integer.valueOf(bArr2.length), Integer.valueOf(0)}));
        } else {
            byte[] bArr3 = (i & 16) == 16 ? e : (i & 32) == 32 ? g : c;
            if (bArr[2] == 61) {
                bArr2[0] = (byte) ((((bArr3[bArr[1]] & 255) << ClientRpcPack.SYMMETRIC_ENCRYPT_3DES) | ((bArr3[bArr[0]] & 255) << 18)) >>> 16);
                return 1;
            } else if (bArr[3] == 61) {
                int i2 = ((bArr3[bArr[2]] & 255) << 6) | ((bArr3[bArr[0]] & 255) << 18) | ((bArr3[bArr[1]] & 255) << ClientRpcPack.SYMMETRIC_ENCRYPT_3DES);
                bArr2[0] = (byte) (i2 >>> 16);
                bArr2[1] = (byte) (i2 >>> 8);
                return 2;
            } else {
                byte b2 = (bArr3[bArr[3]] & 255) | ((bArr3[bArr[0]] & 255) << 18) | ((bArr3[bArr[1]] & 255) << ClientRpcPack.SYMMETRIC_ENCRYPT_3DES) | ((bArr3[bArr[2]] & 255) << 6);
                bArr2[0] = (byte) (b2 >> 16);
                bArr2[1] = (byte) (b2 >> 8);
                bArr2[2] = (byte) b2;
                return 3;
            }
        }
    }
}
