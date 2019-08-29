package defpackage;

import android.util.Base64;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

/* renamed from: asn reason: default package */
/* compiled from: SignUtilsForBusCard */
public final class asn {
    public static String a(String str, String str2) {
        try {
            PrivateKey generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(str2, 0)));
            Signature instance = Signature.getInstance("SHA1WithRSA");
            instance.initSign(generatePrivate);
            instance.update(str.getBytes("UTF-8"));
            return Base64.encodeToString(instance.sign(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
