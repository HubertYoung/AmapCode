package defpackage;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* renamed from: cp reason: default package */
/* compiled from: HMacUtil */
public final class cp {
    public static String a(byte[] bArr, byte[] bArr2) {
        try {
            return cz.a(b(bArr, bArr2));
        } catch (Throwable th) {
            cl.d("awcn.HMacUtil", "hmacSha1Hex", null, "result", "", th);
            r6 = "";
            return "";
        }
    }

    private static byte[] b(byte[] bArr, byte[] bArr2) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "HmacSHA256");
        try {
            Mac instance = Mac.getInstance("HmacSHA256");
            instance.init(secretKeySpec);
            return instance.doFinal(bArr2);
        } catch (InvalidKeyException | NoSuchAlgorithmException unused) {
            return null;
        }
    }
}
