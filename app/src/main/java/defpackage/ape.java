package defpackage;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

/* renamed from: ape reason: default package */
/* compiled from: SignUtils */
public final class ape {
    public static String a(String str, String str2) {
        try {
            PrivateKey generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(apc.a(str2)));
            Signature instance = Signature.getInstance("SHA1WithRSA");
            instance.initSign(generatePrivate);
            instance.update(str.getBytes("UTF-8"));
            return apc.a(instance.sign());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
