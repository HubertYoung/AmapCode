package defpackage;

import java.io.UnsupportedEncodingException;

/* renamed from: abd reason: default package */
/* compiled from: EncodingUtils */
public final class abd {
    public static String a(byte[] bArr, int i, String str) {
        if (bArr == null) {
            throw new IllegalArgumentException("Parameter may not be null");
        } else if (str.length() == 0) {
            throw new IllegalArgumentException("charset may not be null or empty");
        } else {
            try {
                return new String(bArr, 0, i, str);
            } catch (UnsupportedEncodingException unused) {
                return new String(bArr, 0, i);
            }
        }
    }
}
