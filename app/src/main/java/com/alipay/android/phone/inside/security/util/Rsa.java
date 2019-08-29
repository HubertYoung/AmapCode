package com.alipay.android.phone.inside.security.util;

import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class Rsa {
    public static byte[] a(String str, String str2) throws Exception {
        return a(str.getBytes("utf-8"), str2);
    }

    private static byte[] a(byte[] bArr, String str) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            int i = 0;
            try {
                PublicKey generatePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(str, 0)));
                Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                instance.init(1, generatePublic);
                int blockSize = instance.getBlockSize();
                while (i < bArr.length) {
                    byteArrayOutputStream2.write(instance.doFinal(bArr, i, bArr.length - i < blockSize ? bArr.length - i : blockSize));
                    i += blockSize;
                }
                byte[] byteArray = byteArrayOutputStream2.toByteArray();
                try {
                    byteArrayOutputStream2.close();
                } catch (IOException unused) {
                }
                return byteArray;
            } catch (Exception e) {
                e = e;
                byteArrayOutputStream = byteArrayOutputStream2;
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    byteArrayOutputStream2 = byteArrayOutputStream;
                }
            } catch (Throwable th2) {
                th = th2;
                if (byteArrayOutputStream2 != null) {
                    try {
                        byteArrayOutputStream2.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            throw e;
        }
    }
}
