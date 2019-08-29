package com.alipay.android.phone.inside.offlinecode.utils;

import com.alipay.mobile.security.bio.utils.DESCoder;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DesEncrypt {
    public static byte[] xOr(byte[] bArr, byte[] bArr2) throws Exception {
        if (bArr == null || bArr2 == null) {
            throw new Exception("left or right is null.");
        } else if (bArr.length != bArr2.length) {
            throw new Exception("left.length != right.length");
        } else {
            byte[] bArr3 = new byte[8];
            for (int i = 0; i < bArr.length; i++) {
                bArr3[i] = (byte) (bArr[i] ^ bArr2[i]);
            }
            return bArr3;
        }
    }

    public static byte[] encryptForDesCbc(byte[] bArr, byte[] bArr2) throws Exception {
        try {
            SecureRandom secureRandom = new SecureRandom();
            SecretKey generateSecret = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(bArr));
            Cipher instance = Cipher.getInstance("DES/CBC/NoPadding");
            instance.init(1, generateSecret, new IvParameterSpec(new byte[8]), secureRandom);
            return instance.doFinal(bArr2);
        } catch (Exception e) {
            throw e;
        }
    }

    public static byte[] encryptFor3DesCbc(byte[] bArr, byte[] bArr2) throws Exception {
        try {
            byte[] bArr3 = new byte[24];
            System.arraycopy(bArr, 0, bArr3, 0, 16);
            System.arraycopy(bArr, 0, bArr3, 16, 8);
            Cipher instance = Cipher.getInstance("DESede/CBC/NoPadding");
            instance.init(1, new SecretKeySpec(bArr3, DESCoder.ALGORITHM), new IvParameterSpec(new byte[8]));
            return instance.doFinal(bArr2);
        } catch (Exception e) {
            throw e;
        }
    }
}
