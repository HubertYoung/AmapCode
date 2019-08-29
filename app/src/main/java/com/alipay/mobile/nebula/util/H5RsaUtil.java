package com.alipay.mobile.nebula.util;

import android.text.TextUtils;
import android.util.Base64;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class H5RsaUtil {
    private static final String ALGORITHM = "RSA";
    public static final String TAG = "RsaUtil";

    public static boolean verify(String filePath, String publicKey, String base64Sign) {
        boolean verified = false;
        BufferedInputStream bis = null;
        byte[] buffer = H5IOUtils.getBuf(1024);
        try {
            PublicKey pubKey = KeyFactory.getInstance(ALGORITHM).generatePublic(new X509EncodedKeySpec(Base64.decode(publicKey, 2)));
            byte[] sigToVerify = Base64.decode(base64Sign, 2);
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(pubKey);
            BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(filePath));
            while (bis2.available() != 0) {
                try {
                    signature.update(buffer, 0, bis2.read(buffer));
                } catch (Throwable th) {
                    th = th;
                    bis = bis2;
                    H5IOUtils.returnBuf(buffer);
                    H5IOUtils.closeQuietly(bis);
                    throw th;
                }
            }
            verified = signature.verify(sigToVerify);
            H5IOUtils.returnBuf(buffer);
            H5IOUtils.closeQuietly(bis2);
            BufferedInputStream bufferedInputStream = bis2;
        } catch (Throwable th2) {
            e = th2;
            H5Log.e(TAG, "verify exception", e);
            H5IOUtils.returnBuf(buffer);
            H5IOUtils.closeQuietly(bis);
            H5Log.d(TAG, "signature verify result " + verified);
            return verified;
        }
        H5Log.d(TAG, "signature verify result " + verified);
        return verified;
    }

    private static PublicKey getPublicKeyFromX509(String algorithm, String bysKey) {
        try {
            return KeyFactory.getInstance(algorithm).generatePublic(new X509EncodedKeySpec(H5Base64.decode(bysKey)));
        } catch (Throwable th) {
            throw new RuntimeException("KeyError");
        }
    }

    public static String encrypt(String content, String key) {
        try {
            PublicKey pubkey = getPublicKeyFromX509(ALGORITHM, key);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(1, pubkey);
            return H5Base64.encode(cipher.doFinal(content.getBytes("UTF-8")));
        } catch (Exception e) {
            if (TextUtils.equals("KeyError", e.getMessage())) {
                return "KeyError";
            }
            H5Log.e(TAG, "exception detail", e);
            return null;
        }
    }

    public static PrivateKey getPrivateKey(String algorithm, String key) {
        try {
            return KeyFactory.getInstance(algorithm).generatePrivate(new PKCS8EncodedKeySpec(H5Base64.decode(key)));
        } catch (Throwable th) {
            throw new RuntimeException("KeyError");
        }
    }

    public static String decrypt(String content, String key) {
        try {
            PrivateKey privateKey = getPrivateKey(ALGORITHM, key);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(2, privateKey);
            return new String(cipher.doFinal(H5Base64.decode(content)));
        } catch (Exception e) {
            if (TextUtils.equals("KeyError", e.getMessage())) {
                return "KeyError";
            }
            H5Log.e(TAG, "exception detail", e);
            return null;
        }
    }
}
