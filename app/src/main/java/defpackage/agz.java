package defpackage;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/* renamed from: agz reason: default package */
/* compiled from: ThreeDesUtil */
public final class agz {
    public static byte[] a(String str, byte[] bArr, String str2, String str3) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DESEDE");
            sb.append("/");
            sb.append(str2);
            sb.append("/");
            sb.append(str3);
            String sb2 = sb.toString();
            SecretKey generateSecret = SecretKeyFactory.getInstance("DESEDE").generateSecret(new DESedeKeySpec(bArr));
            Cipher instance = Cipher.getInstance(sb2);
            instance.init(1, generateSecret, new IvParameterSpec(new byte[8]));
            return instance.doFinal(str.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
