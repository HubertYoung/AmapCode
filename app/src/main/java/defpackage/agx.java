package defpackage;

/* renamed from: agx reason: default package */
/* compiled from: HexUtil */
public final class agx {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String a(byte[] bArr) {
        return a(bArr, false);
    }

    public static String a(byte[] bArr, boolean z) {
        if (bArr == null) {
            return null;
        }
        char[] cArr = new char[(bArr.length + bArr.length)];
        int length = bArr.length - 1;
        int length2 = cArr.length - 1;
        while (length >= 0) {
            byte b = bArr[length];
            int i = b & 15;
            if (z && i >= 10) {
                i += 6;
            }
            int i2 = length2 - 1;
            cArr[length2] = a[i];
            int i3 = (b >>> 4) & 15;
            if (z && i3 >= 10) {
                i3 += 6;
            }
            cArr[i2] = a[i3];
            length--;
            length2 = i2 - 1;
        }
        return new String(cArr);
    }
}
