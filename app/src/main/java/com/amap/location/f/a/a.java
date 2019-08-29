package com.amap.location.f.a;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;

/* compiled from: ByteUtil */
class a {
    static byte[] a(b bVar) {
        if (bVar == null || bVar.b == 0) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(32);
        byteArrayOutputStream.write(bVar.b);
        if (bVar.a == null) {
            byteArrayOutputStream.write(a(0), 0, 4);
        } else {
            byteArrayOutputStream.write(a(bVar.a.size()), 0, 4);
            Iterator<a> it = bVar.a.iterator();
            while (it.hasNext()) {
                a next = it.next();
                byteArrayOutputStream.write(a(next.a), 0, 4);
                byteArrayOutputStream.write(a(next.b), 0, 4);
                byteArrayOutputStream.write(a(next.c), 0, 4);
                byteArrayOutputStream.write(a(next.d), 0, 4);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    static byte[] a(g gVar) {
        if (gVar == null || (gVar.a == 0 && gVar.b.size() == 0)) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        byteArrayOutputStream.write(a(gVar.a), 0, 8);
        if (gVar.b == null) {
            byteArrayOutputStream.write(a(0), 0, 4);
        } else {
            byteArrayOutputStream.write(a(gVar.b.size()), 0, 4);
            Iterator<Long> it = gVar.b.iterator();
            while (it.hasNext()) {
                byteArrayOutputStream.write(a(it.next().longValue()), 0, 8);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    static b a(byte[] bArr) {
        if (bArr != null) {
            int i = 5;
            if (bArr.length >= 5) {
                try {
                    b bVar = new b();
                    int i2 = 0;
                    bVar.b = bArr[0];
                    int b = b(bArr, 1);
                    while (i2 < b) {
                        int i3 = i + 16;
                        if (i3 > bArr.length) {
                            return bVar;
                        }
                        a aVar = new a();
                        aVar.a = b(bArr, i);
                        aVar.b = b(bArr, i + 4);
                        aVar.c = b(bArr, i + 8);
                        aVar.d = b(bArr, i + 12);
                        bVar.a.add(aVar);
                        i2++;
                        i = i3;
                    }
                    return bVar;
                } catch (Exception unused) {
                    return new b();
                }
            }
        }
        return new b();
    }

    static g b(byte[] bArr) {
        if (bArr != null) {
            int i = 12;
            if (bArr.length >= 12) {
                try {
                    g gVar = new g();
                    int i2 = 0;
                    gVar.a = a(bArr, 0);
                    int b = b(bArr, 8);
                    while (i2 < b) {
                        int i3 = i + 8;
                        if (i3 > bArr.length) {
                            return gVar;
                        }
                        gVar.b.add(Long.valueOf(a(bArr, i)));
                        i2++;
                        i = i3;
                    }
                    return gVar;
                } catch (Exception unused) {
                    return new g();
                }
            }
        }
        return new g();
    }

    private static byte[] a(long j) {
        byte[] bArr = new byte[8];
        for (int i = 0; i < 8; i++) {
            bArr[i] = Long.valueOf(255 & j).byteValue();
            j >>= 8;
        }
        return bArr;
    }

    private static long a(byte[] bArr, int i) {
        return (((long) (bArr[i + 7] & 255)) << 56) | ((long) (bArr[i] & 255)) | (((long) (bArr[i + 1] & 255)) << 8) | (((long) (bArr[i + 2] & 255)) << 16) | (((long) (bArr[i + 3] & 255)) << 24) | (((long) (bArr[i + 4] & 255)) << 32) | (((long) (bArr[i + 5] & 255)) << 40) | (((long) (bArr[i + 6] & 255)) << 48);
    }

    private static byte[] a(int i) {
        byte[] bArr = new byte[4];
        for (int i2 = 0; i2 < 4; i2++) {
            bArr[i2] = Integer.valueOf(i & 255).byteValue();
            i >>= 8;
        }
        return bArr;
    }

    private static int b(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | ((bArr[i + 2] & 255) << 16) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8);
    }
}
