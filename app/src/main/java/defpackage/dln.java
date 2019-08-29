package defpackage;

/* renamed from: dln reason: default package */
/* compiled from: SPCodeConvert */
public final class dln {
    static int a(byte b) {
        if (b >= 48 && b <= 57) {
            return b - 48;
        }
        if (b < 65 || b > 78) {
            return 24;
        }
        return (b - 65) + 10;
    }

    static int a(byte[] bArr) {
        int length = bArr.length;
        for (int i = 0; i < length; i++) {
            if (bArr[i] == 0) {
                return i;
            }
        }
        return length;
    }
}
