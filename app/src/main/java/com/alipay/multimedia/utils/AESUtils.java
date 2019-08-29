package com.alipay.multimedia.utils;

import android.util.Base64;
import com.alipay.alipaylogger.Log;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    public static Cipher initAESCipher(String encryptKey, int cipherMode) {
        Cipher cipher = null;
        try {
            byte[] raw = encryptKey.getBytes();
            byte[] key = new byte[32];
            System.arraycopy(raw, 0, key, 0, raw.length);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(cipherMode, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            return cipher;
        } catch (NoSuchAlgorithmException e) {
            Log.e("AESUtils", "NoSuchAlgorithmException");
            return cipher;
        } catch (NoSuchPaddingException e2) {
            Log.e("AESUtils", "NoSuchPaddingException");
            return cipher;
        } catch (InvalidKeyException e3) {
            Log.e("AESUtils", "InvalidKeyException");
            return cipher;
        } catch (InvalidAlgorithmParameterException e4) {
            Log.e("AESUtils", "InvalidAlgorithmParameterException");
            return cipher;
        }
    }

    public static String encryptStr(String encryptKey, String source) {
        try {
            return Base64.encodeToString(initAESCipher(encryptKey, 1).doFinal(source.getBytes()), 0);
        } catch (Throwable e) {
            Log.e("AESUtils", "Throwable.e=" + e.getMessage());
            return null;
        }
    }

    public static String decryptStr(String key, String data) {
        try {
            return new String(initAESCipher(key, 2).doFinal(Base64.decode(data.getBytes(), 0)));
        } catch (Throwable e) {
            Log.e("AESUtils", "decrypt fail.e=" + e.getMessage());
            return null;
        }
    }
}
