package defpackage;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import com.alipay.multimedia.img.utils.ImageFileType;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/* renamed from: agu reason: default package */
/* compiled from: Base64 */
class agu {
    static final /* synthetic */ boolean a = true;
    private static final byte[] b = {65, 66, 67, 68, 69, 70, ImageFileType.HEAD_GIF_0, ImageFileType.HEAD_HEVC_0, 73, 74, 75, 76, 77, 78, 79, 80, 81, ImageFileType.HEAD_WEBP_0, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] c = {-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};
    private static final byte[] d = {65, 66, 67, 68, 69, 70, ImageFileType.HEAD_GIF_0, ImageFileType.HEAD_HEVC_0, 73, 74, 75, 76, 77, 78, 79, 80, 81, ImageFileType.HEAD_WEBP_0, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
    private static final byte[] e = {-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, 63, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};
    private static final byte[] f = {45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, ImageFileType.HEAD_GIF_0, ImageFileType.HEAD_HEVC_0, 73, 74, 75, 76, 77, 78, 79, 80, 81, ImageFileType.HEAD_WEBP_0, 83, 84, 85, 86, 87, 88, 89, 90, 95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};
    private static final byte[] g = {-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 0, -9, -9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, -9, -9, -9, -1, -9, -9, -9, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, -9, -9, -9, -9, 37, -9, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};

    private agu() {
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

    private static byte[] b(byte[] bArr, int i) throws IOException {
        int i2;
        if (bArr == null) {
            throw new NullPointerException("Cannot decode null source array.");
        }
        int i3 = i + 0;
        if (i3 > bArr.length) {
            throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and process %d bytes.", new Object[]{Integer.valueOf(bArr.length), Integer.valueOf(0), Integer.valueOf(i)}));
        } else if (i == 0) {
            return new byte[0];
        } else {
            if (i < 4) {
                throw new IllegalArgumentException("Base64-encoded string must have at least four characters, but length specified was ".concat(String.valueOf(i)));
            }
            byte[] bArr2 = c;
            byte[] bArr3 = new byte[((i * 3) / 4)];
            byte[] bArr4 = new byte[4];
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            while (i4 < i3) {
                byte b2 = bArr2[bArr[i4] & 255];
                if (b2 >= -5) {
                    if (b2 >= -1) {
                        int i7 = i5 + 1;
                        bArr4[i5] = bArr[i4];
                        if (i7 > 3) {
                            if (i6 >= 0) {
                                int i8 = i6 + 2;
                                if (i8 < bArr3.length) {
                                    byte[] bArr5 = c;
                                    if (bArr4[2] == 61) {
                                        bArr3[i6] = (byte) ((((bArr5[bArr4[0]] & 255) << 18) | ((bArr5[bArr4[1]] & 255) << ClientRpcPack.SYMMETRIC_ENCRYPT_3DES)) >>> 16);
                                        i2 = 1;
                                    } else if (bArr4[3] == 61) {
                                        int i9 = ((bArr5[bArr4[0]] & 255) << 18) | ((bArr5[bArr4[1]] & 255) << ClientRpcPack.SYMMETRIC_ENCRYPT_3DES) | ((bArr5[bArr4[2]] & 255) << 6);
                                        bArr3[i6] = (byte) (i9 >>> 16);
                                        bArr3[i6 + 1] = (byte) (i9 >>> 8);
                                        i2 = 2;
                                    } else {
                                        byte b3 = (bArr5[bArr4[3]] & 255) | ((bArr5[bArr4[0]] & 255) << 18) | ((bArr5[bArr4[1]] & 255) << ClientRpcPack.SYMMETRIC_ENCRYPT_3DES) | ((bArr5[bArr4[2]] & 255) << 6);
                                        bArr3[i6] = (byte) (b3 >> 16);
                                        bArr3[i6 + 1] = (byte) (b3 >> 8);
                                        bArr3[i8] = (byte) b3;
                                        i2 = 3;
                                    }
                                    i6 += i2;
                                    if (bArr[i4] == 61) {
                                        break;
                                    }
                                    i5 = 0;
                                }
                            }
                            throw new IllegalArgumentException(String.format("Destination array with length %d cannot have offset of %d and still store three bytes.", new Object[]{Integer.valueOf(bArr3.length), Integer.valueOf(i6)}));
                        }
                        i5 = i7;
                    }
                    i4++;
                } else {
                    throw new IOException(String.format("Bad Base64 input character decimal %d in array position %d", new Object[]{Integer.valueOf(bArr[i4] & 255), Integer.valueOf(i4)}));
                }
            }
            byte[] bArr6 = new byte[i6];
            System.arraycopy(bArr3, 0, bArr6, 0, i6);
            return bArr6;
        }
    }

    public static byte[] a(String str) throws IOException {
        return b(str);
    }

    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r3v1, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARNING: type inference failed for: r2v6, types: [java.util.zip.GZIPInputStream] */
    /* JADX WARNING: type inference failed for: r5v0 */
    /* JADX WARNING: type inference failed for: r3v2 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r5v1, types: [java.util.zip.GZIPInputStream] */
    /* JADX WARNING: type inference failed for: r2v8, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r5v2 */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r3v5, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARNING: type inference failed for: r5v3 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r5v4 */
    /* JADX WARNING: type inference failed for: r5v5 */
    /* JADX WARNING: type inference failed for: r5v6, types: [java.util.zip.GZIPInputStream] */
    /* JADX WARNING: type inference failed for: r2v11 */
    /* JADX WARNING: type inference failed for: r2v12 */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r2v14 */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r5v7 */
    /* JADX WARNING: type inference failed for: r3v7 */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r3v9 */
    /* JADX WARNING: type inference failed for: r5v8 */
    /* JADX WARNING: type inference failed for: r5v9 */
    /* JADX WARNING: type inference failed for: r5v10 */
    /* JADX WARNING: Can't wrap try/catch for region: R(10:54|55|56|57|58|59|60|61|62|63) */
    /* JADX WARNING: Can't wrap try/catch for region: R(12:17|18|(3:19|20|(1:22)(1:66))|23|24|25|26|27|28|29|30|70) */
    /* JADX WARNING: Can't wrap try/catch for region: R(12:39|40|41|46|47|48|49|50|51|52|53|71) */
    /* JADX WARNING: Can't wrap try/catch for region: R(9:46|47|48|49|50|51|52|53|71) */
    /* JADX WARNING: Code restructure failed: missing block: B:67:?, code lost:
        return r6;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x0057 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:28:0x005a */
    /* JADX WARNING: Missing exception handler attribute for start block: B:50:0x007d */
    /* JADX WARNING: Missing exception handler attribute for start block: B:52:0x0080 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:59:0x008a */
    /* JADX WARNING: Missing exception handler attribute for start block: B:61:0x008d */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v2
      assigns: []
      uses: []
      mth insns count: 77
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 10 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] b(java.lang.String r6) throws java.io.IOException {
        /*
            if (r6 != 0) goto L_0x000a
            java.lang.NullPointerException r6 = new java.lang.NullPointerException
            java.lang.String r0 = "Input string was null."
            r6.<init>(r0)
            throw r6
        L_0x000a:
            java.lang.String r0 = "US-ASCII"
            byte[] r0 = r6.getBytes(r0)     // Catch:{ UnsupportedEncodingException -> 0x0011 }
            goto L_0x0015
        L_0x0011:
            byte[] r0 = r6.getBytes()
        L_0x0015:
            int r6 = r0.length
            byte[] r6 = b(r0, r6)
            int r0 = r6.length
            r1 = 4
            if (r0 < r1) goto L_0x0091
            r0 = 0
            byte r1 = r6[r0]
            r1 = r1 & 255(0xff, float:3.57E-43)
            r2 = 1
            byte r2 = r6[r2]
            int r2 = r2 << 8
            r3 = 65280(0xff00, float:9.1477E-41)
            r2 = r2 & r3
            r1 = r1 | r2
            r2 = 35615(0x8b1f, float:4.9907E-41)
            if (r2 != r1) goto L_0x0091
            r1 = 2048(0x800, float:2.87E-42)
            byte[] r1 = new byte[r1]
            r2 = 0
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0074, all -> 0x0070 }
            r3.<init>()     // Catch:{ IOException -> 0x0074, all -> 0x0070 }
            java.io.ByteArrayInputStream r4 = new java.io.ByteArrayInputStream     // Catch:{ IOException -> 0x006b, all -> 0x0068 }
            r4.<init>(r6)     // Catch:{ IOException -> 0x006b, all -> 0x0068 }
            java.util.zip.GZIPInputStream r5 = new java.util.zip.GZIPInputStream     // Catch:{ IOException -> 0x0065, all -> 0x0063 }
            r5.<init>(r4)     // Catch:{ IOException -> 0x0065, all -> 0x0063 }
        L_0x0046:
            int r2 = r5.read(r1)     // Catch:{ IOException -> 0x0061, all -> 0x005f }
            if (r2 < 0) goto L_0x0050
            r3.write(r1, r0, r2)     // Catch:{ IOException -> 0x0061, all -> 0x005f }
            goto L_0x0046
        L_0x0050:
            byte[] r0 = r3.toByteArray()     // Catch:{ IOException -> 0x0061, all -> 0x005f }
            r3.close()     // Catch:{ Exception -> 0x0057 }
        L_0x0057:
            r5.close()     // Catch:{ Exception -> 0x005a }
        L_0x005a:
            r4.close()     // Catch:{ Exception -> 0x005d }
        L_0x005d:
            r6 = r0
            goto L_0x0091
        L_0x005f:
            r6 = move-exception
            goto L_0x0086
        L_0x0061:
            r0 = move-exception
            goto L_0x006e
        L_0x0063:
            r6 = move-exception
            goto L_0x0087
        L_0x0065:
            r0 = move-exception
            r5 = r2
            goto L_0x006e
        L_0x0068:
            r6 = move-exception
            r4 = r2
            goto L_0x0087
        L_0x006b:
            r0 = move-exception
            r4 = r2
            r5 = r4
        L_0x006e:
            r2 = r3
            goto L_0x0077
        L_0x0070:
            r6 = move-exception
            r3 = r2
            r4 = r3
            goto L_0x0087
        L_0x0074:
            r0 = move-exception
            r4 = r2
            r5 = r4
        L_0x0077:
            r0.printStackTrace()     // Catch:{ all -> 0x0084 }
            r2.close()     // Catch:{ Exception -> 0x007d }
        L_0x007d:
            r5.close()     // Catch:{ Exception -> 0x0080 }
        L_0x0080:
            r4.close()     // Catch:{ Exception -> 0x0091 }
            goto L_0x0091
        L_0x0084:
            r6 = move-exception
            r3 = r2
        L_0x0086:
            r2 = r5
        L_0x0087:
            r3.close()     // Catch:{ Exception -> 0x008a }
        L_0x008a:
            r2.close()     // Catch:{ Exception -> 0x008d }
        L_0x008d:
            r4.close()     // Catch:{ Exception -> 0x0090 }
        L_0x0090:
            throw r6
        L_0x0091:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.agu.b(java.lang.String):byte[]");
    }

    private static byte[] a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        byte[] bArr3 = b;
        int i4 = 0;
        int i5 = (i2 > 0 ? (bArr[i] << 24) >>> 8 : 0) | (i2 > 1 ? (bArr[i + 1] << 24) >>> 16 : 0);
        if (i2 > 2) {
            i4 = (bArr[i + 2] << 24) >>> 24;
        }
        int i6 = i5 | i4;
        switch (i2) {
            case 1:
                bArr2[i3] = bArr3[i6 >>> 18];
                bArr2[i3 + 1] = bArr3[(i6 >>> 12) & 63];
                bArr2[i3 + 2] = 61;
                bArr2[i3 + 3] = 61;
                return bArr2;
            case 2:
                bArr2[i3] = bArr3[i6 >>> 18];
                bArr2[i3 + 1] = bArr3[(i6 >>> 12) & 63];
                bArr2[i3 + 2] = bArr3[(i6 >>> 6) & 63];
                bArr2[i3 + 3] = 61;
                return bArr2;
            case 3:
                bArr2[i3] = bArr3[i6 >>> 18];
                bArr2[i3 + 1] = bArr3[(i6 >>> 12) & 63];
                bArr2[i3 + 2] = bArr3[(i6 >>> 6) & 63];
                bArr2[i3 + 3] = bArr3[i6 & 63];
                return bArr2;
            default:
                return bArr2;
        }
    }

    private static String a(byte[] bArr, int i) throws IOException {
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
                a(bArr, i5 + 0, 3, bArr3, i6);
                i5 += 3;
                i6 += 4;
            }
            if (i5 < i) {
                a(bArr, i5 + 0, i - i5, bArr3, i6);
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
}
