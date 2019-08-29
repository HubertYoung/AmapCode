package defpackage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* renamed from: ewn reason: default package */
/* compiled from: MD5Utility */
public class ewn {
    private static final String a = "ewn";

    public static String a(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                byte b2 = b & 255;
                if (b2 < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(b2));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            StringBuilder sb = new StringBuilder("Collected:");
            sb.append(e.getMessage());
            euw.a(sb.toString());
            return "";
        }
    }
}
