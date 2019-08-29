package defpackage;

/* renamed from: enq reason: default package */
/* compiled from: HexUtil */
public final class enq {
    protected static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String a(byte[] bArr) {
        char[] cArr = new char[(bArr.length + bArr.length)];
        int length = cArr.length - 1;
        for (int length2 = bArr.length - 1; length2 >= 0; length2--) {
            byte b = bArr[length2];
            int i = length - 1;
            cArr[length] = a[b & 15];
            length = i - 1;
            cArr[i] = a[(b >>> 4) & 15];
        }
        return new String(cArr);
    }
}
