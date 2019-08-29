package defpackage;

import android.text.TextUtils;
import android.util.Base64;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/* renamed from: esw reason: default package */
public final class esw {
    private static byte[] b(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return new byte[0];
        }
    }

    public static String a(String str) {
        if (!TextUtils.isEmpty(str)) {
            String str2 = "";
            try {
                byte[] b = b("com.nearme.mcs");
                int length = b.length % 2 == 0 ? b.length : b.length - 1;
                for (int i = 0; i < length; i += 2) {
                    byte b2 = b[i];
                    int i2 = i + 1;
                    b[i] = b[i2];
                    b[i2] = b2;
                }
                if (b != null) {
                    str2 = new String(b, Charset.forName("UTF-8"));
                }
                Cipher instance = Cipher.getInstance("DES");
                instance.init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(Base64.decode(str2, 0))));
                return new String(instance.doFinal(Base64.decode(str, 0)), Charset.defaultCharset()).trim();
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("desDecrypt-");
                sb.append(e.getMessage());
                esx.b(sb.toString());
            }
        }
        r10 = "";
        return "";
    }
}
