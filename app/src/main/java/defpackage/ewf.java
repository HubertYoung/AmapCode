package defpackage;

import android.text.TextUtils;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/* renamed from: ewf reason: default package */
/* compiled from: DESUtil */
public class ewf {
    private static final String a = "ewf";

    public static String a(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        try {
            SecretKey generateSecret = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(str.getBytes()));
            Cipher instance = Cipher.getInstance("DES/CBC/PKCS5Padding");
            instance.init(1, generateSecret, new IvParameterSpec("12345678".getBytes()));
            return a(instance.doFinal(str2.getBytes()));
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("Collected:");
            sb.append(e.getMessage());
            euw.a(sb.toString());
            return str2;
        } catch (UnsatisfiedLinkError e2) {
            StringBuilder sb2 = new StringBuilder("Collected:");
            sb2.append(e2.getMessage());
            euw.a(sb2.toString());
            return str2;
        }
    }

    public static String b(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        try {
            SecretKey generateSecret = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(str.getBytes()));
            Cipher instance = Cipher.getInstance("DES/CBC/PKCS5Padding");
            instance.init(2, generateSecret, new IvParameterSpec("12345678".getBytes()));
            return new String(instance.doFinal(b(str2.getBytes())));
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("Collected:");
            sb.append(e.getMessage());
            euw.a(sb.toString());
            return str2;
        } catch (UnsatisfiedLinkError e2) {
            StringBuilder sb2 = new StringBuilder("Collected:");
            sb2.append(e2.getMessage());
            euw.a(sb2.toString());
            return str2;
        }
    }

    private static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (bArr != null && i < bArr.length) {
            String hexString = Integer.toHexString(bArr[i] & 255);
            if (hexString.length() == 1) {
                sb.append('0');
            }
            sb.append(hexString);
            i++;
        }
        return sb.toString().toUpperCase(Locale.CHINA);
    }

    private static byte[] b(byte[] bArr) {
        if (bArr.length % 2 != 0) {
            throw new IllegalArgumentException();
        }
        byte[] bArr2 = new byte[(bArr.length / 2)];
        for (int i = 0; i < bArr.length; i += 2) {
            bArr2[i / 2] = (byte) Integer.parseInt(new String(bArr, i, 2), 16);
        }
        return bArr2;
    }
}
