package defpackage;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/* renamed from: agw reason: default package */
/* compiled from: DesUtil */
public final class agw {
    public static String a(String str, String str2) {
        try {
            return agv.a(a(1, str2).doFinal(str.getBytes("UTF-8")));
        } catch (Exception unused) {
            return null;
        }
    }

    public static String b(String str, String str2) {
        try {
            return new String(a(2, str2).doFinal(agv.a(str)), "UTF-8");
        } catch (Exception unused) {
            return null;
        }
    }

    private static Cipher a(int i, String str) throws Exception {
        SecretKey generateSecret = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(str.getBytes("UTF-8")));
        Cipher instance = Cipher.getInstance("DES");
        instance.init(i, generateSecret, new SecureRandom());
        return instance;
    }
}
