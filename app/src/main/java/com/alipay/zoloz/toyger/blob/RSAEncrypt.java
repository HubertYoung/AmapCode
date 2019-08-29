package com.alipay.zoloz.toyger.blob;

import android.util.Base64;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAEncrypt {
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final String privateKeyName = "privateKey.keystore";
    public static final String publickKeyName = "publicKey.keystore";

    public static RSAPublicKey loadPublicKeyByStr(String str) {
        String[] split;
        StringBuilder sb = new StringBuilder();
        for (String str2 : str.split("\n")) {
            if (str2.charAt(0) != '-') {
                sb.append(str2);
                sb.append(13);
            }
        }
        try {
            return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(sb.toString(), 0)));
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e2) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e3) {
            throw new Exception("公钥数据为空");
        }
    }

    public static byte[] encrypt(RSAPublicKey rSAPublicKey, byte[] bArr) {
        if (rSAPublicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        try {
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            instance.init(1, rSAPublicKey);
            return instance.doFinal(bArr);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e2) {
            return null;
        } catch (InvalidKeyException e3) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e4) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e5) {
            throw new Exception("明文数据已损坏");
        }
    }
}
