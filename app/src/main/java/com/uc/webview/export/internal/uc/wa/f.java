package com.uc.webview.export.internal.uc.wa;

import com.uc.webview.export.internal.utility.Log;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: ProGuard */
final class f {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    static String a(String str) {
        try {
            byte[] bytes = str.getBytes("utf-8");
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bytes);
            byte[] digest = instance.digest();
            int length = digest.length;
            StringBuilder sb = new StringBuilder(length * 2);
            int i = length + 0;
            for (int i2 = 0; i2 < i; i2++) {
                byte b = digest[i2];
                char c = a[(b & 240) >> 4];
                char c2 = a[b & 15];
                sb.append(c);
                sb.append(c2);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e("SDKWaStat", "", e);
            return null;
        } catch (UnsupportedEncodingException e2) {
            Log.e("SDKWaStat", "", e2);
            return null;
        }
    }

    static byte[] a(byte[] bArr) throws Exception {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        SecretKeySpec secretKeySpec = new SecretKeySpec(new byte[]{38, 40, 85, 99, 83, 100, 107, 56, 56, 40, 56, 56, 35, 61, 61, 61}, "AES");
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(1, secretKeySpec, ivParameterSpec);
        return instance.doFinal(b(bArr));
    }

    private static byte[] b(byte[] bArr) {
        byte[] bArr2 = new byte[(bArr.length + 16)];
        int length = bArr.length;
        bArr2[0] = (byte) ((length >> 0) & 255);
        bArr2[1] = (byte) ((length >> 8) & 255);
        bArr2[2] = (byte) ((length >> 16) & 255);
        bArr2[3] = (byte) ((length >> 24) & 255);
        for (int i = 4; i < 16; i++) {
            bArr2[i] = 0;
        }
        System.arraycopy(bArr, 0, bArr2, 16, bArr.length);
        return bArr2;
    }
}
