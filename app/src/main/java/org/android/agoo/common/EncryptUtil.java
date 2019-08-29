package org.android.agoo.common;

import com.alipay.multimedia.img.utils.ImageFileType;
import com.alipay.security.mobile.module.crypto.CryptoUtil;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class EncryptUtil {
    private static byte[] a = {ImageFileType.HEAD_WEBP_0, 22, 50, 44, -16, 124, -40, -114, -87, -40, 37, 23, -56, 23, -33, 75};
    private static ThreadLocal<Cipher> b = new ThreadLocal<>();
    private static final AlgorithmParameterSpec c = new IvParameterSpec(a);

    public static final byte[] a(byte[] bArr, SecretKeySpec secretKeySpec, byte[] bArr2) throws IllegalArgumentException {
        try {
            return a(secretKeySpec, bArr2).doFinal(bArr);
        } catch (IllegalBlockSizeException e) {
            StringBuilder sb = new StringBuilder("AES decrypt error:");
            sb.append(e.getMessage());
            throw new IllegalArgumentException(sb.toString(), e);
        } catch (BadPaddingException e2) {
            StringBuilder sb2 = new StringBuilder("AES decrypt error:");
            sb2.append(e2.getMessage());
            throw new IllegalArgumentException(sb2.toString(), e2);
        }
    }

    private static final Cipher a(SecretKeySpec secretKeySpec, byte[] bArr) {
        Cipher a2 = a();
        try {
            a2.init(2, secretKeySpec, new IvParameterSpec(bArr));
            return a2;
        } catch (InvalidKeyException e) {
            StringBuilder sb = new StringBuilder("init Chipher error:");
            sb.append(e.getMessage());
            throw new RuntimeException(sb.toString(), e);
        } catch (InvalidAlgorithmParameterException e2) {
            StringBuilder sb2 = new StringBuilder("init Chipher error:");
            sb2.append(e2.getMessage());
            throw new RuntimeException(sb2.toString(), e2);
        } catch (IllegalArgumentException e3) {
            StringBuilder sb3 = new StringBuilder("init Chipher error:");
            sb3.append(e3.getMessage());
            throw new RuntimeException(sb3.toString(), e3);
        }
    }

    private static final Cipher a() {
        Cipher cipher = b.get();
        if (cipher == null) {
            try {
                cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                b.set(cipher);
            } catch (NoSuchAlgorithmException e) {
                StringBuilder sb = new StringBuilder("get Chipher error:");
                sb.append(e.getMessage());
                throw new RuntimeException(sb.toString(), e);
            } catch (NoSuchPaddingException e2) {
                StringBuilder sb2 = new StringBuilder("get Chipher error:");
                sb2.append(e2.getMessage());
                throw new RuntimeException(sb2.toString(), e2);
            }
        }
        return cipher;
    }

    public static final byte[] a(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            return instance.digest();
        } catch (Throwable th) {
            throw new RuntimeException("md5 value Throwable", th);
        }
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, CryptoUtil.HMAC_SHA1);
        try {
            Mac instance = Mac.getInstance(CryptoUtil.HMAC_SHA1);
            instance.init(secretKeySpec);
            return instance.doFinal(bArr2);
        } catch (Throwable th) {
            throw new RuntimeException("HmacSHA1 Throwable", th);
        }
    }

    public static byte[] a(String str) {
        int length = str.length();
        byte[] bArr = new byte[(length / 2)];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }
}
