package com.ali.auth.third.core.storage.a;

import android.util.Base64;
import com.ali.auth.third.core.util.CommonUtils;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class a {
    public static boolean a = false;
    private static final byte[] b = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public static String a(String str, String str2) throws GeneralSecurityException {
        try {
            SecretKeySpec a2 = a(str);
            c("message", str2);
            String encodeToString = Base64.encodeToString(a(a2, b, str2.getBytes("UTF-8")), 2);
            c("Base64.NO_WRAP", encodeToString);
            return encodeToString;
        } catch (UnsupportedEncodingException e) {
            throw new GeneralSecurityException(e);
        }
    }

    private static SecretKeySpec a(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest instance = MessageDigest.getInstance("SHA-256");
        byte[] bytes = str.getBytes("UTF-8");
        instance.update(bytes, 0, bytes.length);
        byte[] digest = instance.digest();
        a((String) "SHA-256 key ", digest);
        return new SecretKeySpec(digest, "AES");
    }

    private static void a(String str, byte[] bArr) {
        if (a) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("[");
            sb.append(bArr.length);
            sb.append("] [");
            sb.append(CommonUtils.getHashString(bArr));
            sb.append("]");
        }
    }

    public static byte[] a(SecretKeySpec secretKeySpec, byte[] bArr, byte[] bArr2) throws GeneralSecurityException {
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS7Padding");
        instance.init(1, secretKeySpec, new IvParameterSpec(bArr));
        byte[] doFinal = instance.doFinal(bArr2);
        a((String) "cipherText", doFinal);
        return doFinal;
    }

    public static String b(String str, String str2) throws GeneralSecurityException {
        try {
            SecretKeySpec a2 = a(str);
            c("base64EncodedCipherText", str2);
            byte[] decode = Base64.decode(str2, 2);
            a((String) "decodedCipherText", decode);
            byte[] b2 = b(a2, b, decode);
            a((String) "decryptedBytes", b2);
            String str3 = new String(b2, "UTF-8");
            c("message", str3);
            return str3;
        } catch (UnsupportedEncodingException e) {
            throw new GeneralSecurityException(e);
        }
    }

    public static byte[] b(SecretKeySpec secretKeySpec, byte[] bArr, byte[] bArr2) throws GeneralSecurityException {
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS7Padding");
        instance.init(2, secretKeySpec, new IvParameterSpec(bArr));
        byte[] doFinal = instance.doFinal(bArr2);
        a((String) "decryptedBytes", doFinal);
        return doFinal;
    }

    private static void c(String str, String str2) {
        if (a) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("[");
            sb.append(str2.length());
            sb.append("] [");
            sb.append(str2);
            sb.append("]");
        }
    }
}
