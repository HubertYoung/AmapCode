package com.alipay.mobile.security.bio.utils;

import android.util.Base64;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public abstract class DESCoder {
    public static final String ALGORITHM = "DESede";

    public static byte[] decryptBASE64(String str) {
        return Base64.decode(str, 0);
    }

    public static String encryptBASE64(byte[] bArr) {
        return Base64.encodeToString(bArr, 0);
    }

    public static byte[] encryptMode(byte[] bArr, String str) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(a(str), ALGORITHM);
            Cipher instance = Cipher.getInstance(ALGORITHM);
            instance.init(1, secretKeySpec);
            return instance.doFinal(bArr);
        } catch (NoSuchAlgorithmException e) {
            BioLog.e(e.toString());
        } catch (NoSuchPaddingException e2) {
            BioLog.e(e2.toString());
        } catch (Exception e3) {
            BioLog.e(e3.toString());
        }
        return null;
    }

    public static byte[] decryptMode(byte[] bArr, String str) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(a(str), ALGORITHM);
            Cipher instance = Cipher.getInstance(ALGORITHM);
            instance.init(2, secretKeySpec);
            return instance.doFinal(bArr);
        } catch (NoSuchAlgorithmException e) {
            BioLog.e(e.toString());
        } catch (NoSuchPaddingException e2) {
            BioLog.e(e2.toString());
        } catch (Exception e3) {
            BioLog.e(e3.toString());
        }
        return null;
    }

    private static byte[] a(String str) {
        byte[] bArr = new byte[24];
        byte[] bytes = str.getBytes("UTF-8");
        if (24 > bytes.length) {
            System.arraycopy(bytes, 0, bArr, 0, bytes.length);
        } else {
            System.arraycopy(bytes, 0, bArr, 0, 24);
        }
        return bArr;
    }
}
