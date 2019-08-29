package defpackage;

import java.io.UnsupportedEncodingException;

/* renamed from: euz reason: default package */
/* compiled from: StringUtils */
public final class euz {
    public static String a(byte[] bArr, String str) {
        if (bArr == null) {
            return null;
        }
        try {
            return new String(bArr, str);
        } catch (UnsupportedEncodingException e) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(": ");
            sb.append(e);
            throw new IllegalStateException(sb.toString());
        }
    }
}
