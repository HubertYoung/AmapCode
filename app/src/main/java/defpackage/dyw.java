package defpackage;

import com.autonavi.common.SuperId;
import java.security.MessageDigest;

/* renamed from: dyw reason: default package */
/* compiled from: StringUtils */
public final class dyw {
    private static final String[] a = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", SuperId.BIT_1_RQBXY, SuperId.BIT_1_NEARBY_SEARCH, "d", "e", "f"};

    public static String a(String str) {
        String str2;
        try {
            str2 = new String(str);
            try {
                return a(MessageDigest.getInstance("MD5").digest(str2.getBytes()));
            } catch (Exception unused) {
                return str2;
            }
        } catch (Exception unused2) {
            str2 = null;
            return str2;
        }
    }

    private static String a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i];
            int i2 = b < 0 ? b + 256 : b;
            StringBuilder sb = new StringBuilder();
            sb.append(a[i2 / 16]);
            sb.append(a[i2 % 16]);
            stringBuffer.append(sb.toString());
        }
        return stringBuffer.toString();
    }
}
