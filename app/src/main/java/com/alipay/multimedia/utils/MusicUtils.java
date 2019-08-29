package com.alipay.multimedia.utils;

import android.text.TextUtils;
import android.util.Base64;
import com.alipay.multimedia.common.logging.MLog;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MusicUtils {
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String TAG = "MusicUtils";
    private static Map<String, Object> skeyMap = null;

    public static boolean isEncrptedMusic(String fileId) {
        if (!TextUtils.isEmpty(fileId) && fileId.startsWith("A*") && fileId.length() == 32 && ((Base64.decode(fileId.substring(2), 8)[21] >> 4) & 15) == 1) {
            return true;
        }
        return false;
    }

    public static Cipher initAESCipher(int cipherMode, String fileKey, boolean needPadding) {
        Cipher cipher = null;
        try {
            byte[] raw = Base64.decode(fileKey, 0);
            byte[] key = new byte[16];
            System.arraycopy(raw, 0, key, 0, raw.length);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            if (needPadding) {
                cipher = Cipher.getInstance("AES/ECB/NoPadding");
            } else {
                cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            }
            new IvParameterSpec(new byte[cipher.getBlockSize()]);
            cipher.init(cipherMode, skeySpec);
        } catch (NoSuchAlgorithmException e) {
            MLog.e(TAG, "NoSuchAlgorithmException.e=" + e);
        } catch (NoSuchPaddingException e2) {
            MLog.e(TAG, "NoSuchPaddingException.e=" + e2);
        } catch (InvalidKeyException e3) {
            MLog.e(TAG, "InvalidKeyException.e=" + e3);
        }
        return cipher;
    }

    public static byte[] decryptData(byte[] src, String fileKey, boolean needPadding) {
        byte[] result = null;
        try {
            return initAESCipher(2, fileKey, needPadding).doFinal(src);
        } catch (Throwable e) {
            MLog.e(TAG, "Throwable.e=" + e);
            return result;
        }
    }

    private static Map<String, Object> genKeyPair() {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        Map skeyMap2 = new HashMap(2);
        skeyMap2.put(PUBLIC_KEY, (RSAPublicKey) keyPair.getPublic());
        skeyMap2.put(PRIVATE_KEY, (RSAPrivateKey) keyPair.getPrivate());
        return skeyMap2;
    }

    public static String sign(String data) {
        try {
            try {
                try {
                    PrivateKey privateK = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(getPrivateKey(), 0)));
                    try {
                        Signature signature = Signature.getInstance("MD5withRSA");
                        try {
                            signature.initSign(privateK);
                            try {
                                signature.update(data.getBytes());
                                try {
                                    byte[] signed = signature.sign();
                                    if (signed != null) {
                                        return Base64.encodeToString(signed, 0);
                                    }
                                    MLog.e(TAG, "sign.signed is null...");
                                    return data;
                                } catch (SignatureException e) {
                                    MLog.e(TAG, "sign.sign() SignatureException exp=" + e);
                                    return data;
                                }
                            } catch (SignatureException e2) {
                                MLog.e(TAG, "sign.update() SignatureException exp=" + e2);
                                return data;
                            }
                        } catch (InvalidKeyException e3) {
                            MLog.e(TAG, "sign InvalidKeyException exp=" + e3);
                            return data;
                        }
                    } catch (NoSuchAlgorithmException e4) {
                        MLog.e(TAG, "sign MD5withRSA NoSuchAlgorithmException exp=" + e4);
                        return data;
                    }
                } catch (InvalidKeySpecException e5) {
                    MLog.e(TAG, "sign InvalidKeySpecException exp=" + e5);
                    return data;
                }
            } catch (NoSuchAlgorithmException e6) {
                MLog.e(TAG, "sign rsa NoSuchAlgorithmException exp=" + e6);
                return data;
            }
        } catch (Exception e7) {
            MLog.e(TAG, "sign getPrivateKey exp=" + e7);
            return data;
        }
    }

    public static boolean verify(String data, String sign) {
        boolean z = true;
        try {
            try {
                try {
                    PublicKey publicK = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(getPublicKey(), 0)));
                    try {
                        Signature signature = Signature.getInstance("MD5withRSA");
                        try {
                            signature.initVerify(publicK);
                            try {
                                signature.update(data.getBytes());
                                try {
                                    return signature.verify(Base64.decode(sign, 0));
                                } catch (SignatureException e) {
                                    MLog.e(TAG, "verify.verify SignatureException exp=" + e);
                                    return z;
                                }
                            } catch (SignatureException e2) {
                                MLog.e(TAG, "verify.update SignatureException exp=" + e2);
                                return z;
                            }
                        } catch (InvalidKeyException e3) {
                            MLog.e(TAG, "verify.InvalidKeyException exp=" + e3);
                            return z;
                        }
                    } catch (NoSuchAlgorithmException e4) {
                        MLog.e(TAG, "verify.MD5withRSA NoSuchAlgorithmException exp=" + e4);
                        return z;
                    }
                } catch (InvalidKeySpecException e5) {
                    MLog.e(TAG, "verify.InvalidKeySpecException exp=" + e5);
                    return z;
                }
            } catch (NoSuchAlgorithmException e6) {
                MLog.e(TAG, "verify.rsa NoSuchAlgorithmException exp=" + e6);
                return z;
            }
        } catch (Exception e7) {
            MLog.e(TAG, "verify.getPublicKey exp=" + e7);
            return z;
        }
    }

    private static String getPrivateKey() {
        if (skeyMap == null) {
            synchronized (MusicUtils.class) {
                if (skeyMap == null) {
                    skeyMap = genKeyPair();
                }
            }
        }
        return Base64.encodeToString(((Key) skeyMap.get(PRIVATE_KEY)).getEncoded(), 0);
    }

    private static String getPublicKey() {
        return Base64.encodeToString(((Key) skeyMap.get(PUBLIC_KEY)).getEncoded(), 0);
    }

    public static boolean isHttp(String path) {
        if (!TextUtils.isEmpty(path) && path.startsWith("http")) {
            return true;
        }
        return false;
    }
}
