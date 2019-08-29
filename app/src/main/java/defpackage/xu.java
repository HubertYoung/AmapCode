package defpackage;

/* renamed from: xu reason: default package */
/* compiled from: DatabaseSecurity */
public final class xu {
    public static String a(String str) {
        if (str == null) {
            return null;
        }
        try {
            byte[] bytes = str.getBytes("UTF-8");
            int length = bytes.length;
            byte[] bArr = new byte[(length + 1)];
            bArr[0] = (byte) (44380 ^ length);
            if (length != 0) {
                System.arraycopy(bytes, 0, bArr, 1, length);
                a(bArr, length);
            }
            return agv.a(bArr);
        } catch (Exception unused) {
            return str;
        }
    }

    public static String b(String str) {
        if (str == null) {
            return null;
        }
        byte[] a = agv.a(str);
        if (a == null) {
            return str;
        }
        int length = a.length;
        if (length == 0) {
            return str;
        }
        int i = length - 1;
        if (a[0] != ((byte) (44380 ^ i))) {
            return str;
        }
        if (i != 0) {
            a(a, i);
        }
        try {
            return new String(a, 1, i, "UTF-8");
        } catch (Exception unused) {
            return str;
        }
    }

    private static void a(byte[] bArr, int i) {
        if (bArr != null && i >= 0) {
            if (1 <= bArr.length - i) {
                int i2 = i + 1;
                boolean z = true;
                for (int i3 = 1; i3 < i2; i3++) {
                    bArr[i3] = (byte) (bArr[i3] ^ (z ? (byte) 22535 : 33457));
                    z = !z;
                }
            }
        }
    }
}
