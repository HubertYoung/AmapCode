package defpackage;

import java.security.MessageDigest;
import mtopsdk.common.util.TBSdkLog;

/* renamed from: fgx reason: default package */
/* compiled from: SecurityUtils */
public final class fgx {
    public static String a(String str) {
        return str == null ? "" : str;
    }

    public static String b(String str) {
        if (fdd.b(str)) {
            return str;
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes("utf-8"));
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                String hexString = Integer.toHexString(b & 255);
                while (hexString.length() < 2) {
                    hexString = "0".concat(String.valueOf(hexString));
                }
                stringBuffer.append(hexString);
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            TBSdkLog.b((String) "mtopsdk.SecurityUtils", "[getMd5] compute md5 value failed for source str=".concat(String.valueOf(str)), (Throwable) e);
            return null;
        }
    }
}
