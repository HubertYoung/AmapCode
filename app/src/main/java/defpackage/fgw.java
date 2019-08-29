package defpackage;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* renamed from: fgw reason: default package */
/* compiled from: HmacSha1Utils */
public final class fgw {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static char[] a(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[(length << 1)];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i + 1;
            cArr[i] = a[(bArr[i2] & 240) >>> 4];
            i = i3 + 1;
            cArr[i3] = a[bArr[i2] & 15];
        }
        return cArr;
    }

    public static String a(String str, String str2) {
        try {
            Mac instance = Mac.getInstance("HmacSha1");
            instance.init(new SecretKeySpec(str2.getBytes("utf-8"), "HmacSha1"));
            return new String(a(instance.doFinal(str.getBytes("utf-8"))));
        } catch (Exception unused) {
            return null;
        }
    }
}
