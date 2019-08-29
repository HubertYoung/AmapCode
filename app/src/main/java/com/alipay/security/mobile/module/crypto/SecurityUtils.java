package com.alipay.security.mobile.module.crypto;

import com.alipay.android.phone.inside.offlinecode.utils.HexUtils;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class SecurityUtils {
    public static final String TAG = "SecurityUtils";
    private static String str = "idnjfhncnsfuobcnt847y929o449u474w7j3h22aoddc98euk#%&&)*&^%#";

    public static String getSeed() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length() - 1; i += 4) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    public static String encrypt(String str2, String str3) {
        byte[] bArr;
        try {
            bArr = encrypt(getRawKey(str2), str3.getBytes());
        } catch (Exception unused) {
            bArr = null;
        }
        return toHex(bArr);
    }

    public static String decrypt(String str2, String str3) {
        try {
            return new String(decrypt(getRawKey(str2), toByte(str3)));
        } catch (Exception unused) {
            return null;
        }
    }

    private static PBEKeySpec getRawKey(String str2) throws Exception {
        Class<?> cls = Class.forName(new String(Base64Util.decode("amF2YS5zZWN1cml0eS5TZWN1cmVSYW5kb20=")));
        Object newInstance = cls.newInstance();
        byte[] bArr = new byte[16];
        Method method = cls.getMethod("nextBytes", new Class[]{bArr.getClass()});
        method.setAccessible(true);
        method.invoke(newInstance, new Object[]{bArr});
        return new PBEKeySpec(str2.toCharArray(), bArr, 10, 128);
    }

    private static byte[] encrypt(PBEKeySpec pBEKeySpec, byte[] bArr) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(pBEKeySpec).getEncoded(), "AES");
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(1, secretKeySpec, new IvParameterSpec(new byte[instance.getBlockSize()]));
        byte[] salt = pBEKeySpec.getSalt();
        ByteBuffer allocate = ByteBuffer.allocate(salt.length + instance.getOutputSize(bArr.length));
        allocate.put(salt);
        instance.doFinal(ByteBuffer.wrap(bArr), allocate);
        return allocate.array();
    }

    private static byte[] decrypt(PBEKeySpec pBEKeySpec, byte[] bArr) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(pBEKeySpec.getPassword(), Arrays.copyOf(bArr, 16), 10, 128)).getEncoded(), "AES");
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(2, secretKeySpec, new IvParameterSpec(new byte[instance.getBlockSize()]));
        return instance.doFinal(bArr, 16, bArr.length - 16);
    }

    public static String toHex(String str2) {
        return toHex(str2.getBytes());
    }

    public static String fromHex(String str2) {
        return new String(toByte(str2));
    }

    public static byte[] toByte(String str2) {
        int length = str2.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = Integer.valueOf(str2.substring(i2, i2 + 2), 16).byteValue();
        }
        return bArr;
    }

    public static String toHex(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte appendHex : bArr) {
            appendHex(stringBuffer, appendHex);
        }
        return stringBuffer.toString();
    }

    private static void appendHex(StringBuffer stringBuffer, byte b) {
        stringBuffer.append(HexUtils.HEX_CHARS.charAt((b >> 4) & 15));
        stringBuffer.append(HexUtils.HEX_CHARS.charAt(b & 15));
    }
}
