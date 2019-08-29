package defpackage;

import com.autonavi.minimap.route.bus.model.Bus;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* renamed from: dux reason: default package */
/* compiled from: BusLineFavoriteUtil */
public final class dux {
    public static String a(Bus bus) {
        String a = ebk.a();
        cos b = b(bus);
        com com2 = (com) ank.a(com.class);
        if (com2 != null) {
            coq a2 = com2.a(a);
            if (a2 != null) {
                cor b2 = a2.b(b);
                if (b2 != null) {
                    return b2.f();
                }
                cor a3 = a2.a(c(bus));
                if (a3 != null) {
                    return a3.f();
                }
            }
        }
        return null;
    }

    public static cos b(Bus bus) {
        cos cos = new cos();
        cos.a = 0;
        cos.j = bus.length;
        cos.h = bus.name;
        cos.l = bus;
        return cos;
    }

    private static String c(Bus bus) {
        String str = "";
        if (bus != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(bus.name);
            sb.append("+");
            sb.append(bus.startName);
            sb.append("+");
            sb.append(bus.endName);
            str = sb.toString();
        }
        return a(str);
    }

    private static String a(String str) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            try {
                messageDigest.reset();
                messageDigest.update(str.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException unused) {
            }
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException unused2) {
            messageDigest = null;
        }
        if (messageDigest == null) {
            return "";
        }
        byte[] digest = messageDigest.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            if (Integer.toHexString(digest[i] & 255).length() == 1) {
                stringBuffer.append("0");
                stringBuffer.append(Integer.toHexString(digest[i] & 255));
            } else {
                stringBuffer.append(Integer.toHexString(digest[i] & 255));
            }
        }
        return stringBuffer.toString();
    }
}
