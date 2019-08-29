package com.ali.auth.third.core.rpc.a;

import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.core.util.Base64;
import com.ali.auth.third.core.util.Base64URLSafe;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class a {
    private static ThreadLocal<a> a = new ThreadLocal<>();
    private static final byte[] b = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private IvParameterSpec c = null;
    private Cipher d = null;

    private a() {
        b();
    }

    public static a a() {
        if (a.get() == null) {
            a.set(new a());
        }
        return a.get();
    }

    private void b() {
        if (this.d == null) {
            try {
                this.d = Cipher.getInstance("AES/CBC/PKCS5Padding");
                this.c = new IvParameterSpec(b);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
                SDKLogger.e("auth.AESCrypto", "AES:generateCipher:generate cipher error", e);
            }
        }
    }

    public String a(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        try {
            byte[] a2 = a(str.getBytes("UTF-8"), str2.getBytes("UTF-8"));
            if (a2 != null) {
                return Base64.encode(a2);
            }
        } catch (UnsupportedEncodingException e) {
            StringBuilder sb = new StringBuilder("AES:encrypt:");
            sb.append(str);
            sb.append(":String to Byte Array Error");
            SDKLogger.e("auth.AESCrypto", sb.toString(), e);
        }
        return null;
    }

    public byte[] a(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr2 == null) {
            return null;
        }
        try {
            this.d.init(1, new SecretKeySpec(bArr2, "AES"), this.c);
            return this.d.doFinal(bArr);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            StringBuilder sb = new StringBuilder("AES:encrypt:");
            sb.append(bArr);
            sb.append(":encrypt data error");
            SDKLogger.e("auth.AESCrypto", sb.toString(), e);
            return null;
        }
    }

    public String b(String str, String str2) {
        StringBuilder sb;
        String str3;
        String trim = str.trim();
        int intValue = Integer.valueOf(trim.substring(0, 8), 16).intValue();
        String substring = intValue == trim.length() - 8 ? trim.substring(8) : trim.substring(8, intValue + 8);
        int length = substring.length() % 4;
        if (length == 1) {
            sb = new StringBuilder();
            sb.append(substring);
            str3 = "===";
        } else if (length == 2) {
            sb = new StringBuilder();
            sb.append(substring);
            str3 = "==";
        } else {
            if (length == 3) {
                sb = new StringBuilder();
                sb.append(substring);
                str3 = "=";
            }
            return c(substring, str2);
        }
        sb.append(str3);
        substring = sb.toString();
        return c(substring, str2);
    }

    public byte[] b(byte[] bArr, byte[] bArr2) {
        if (bArr == null) {
            return null;
        }
        try {
            this.d.init(2, new SecretKeySpec(bArr2, "AES"), this.c);
            return this.d.doFinal(bArr);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            StringBuilder sb = new StringBuilder("AES:decrypt:");
            sb.append(bArr);
            sb.append(":decrypt data error");
            SDKLogger.e("auth.AESCrypto", sb.toString(), e);
            return null;
        }
    }

    public String c(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        try {
            byte[] b2 = b(Base64URLSafe.decode(str), str2.getBytes("UTF-8"));
            if (b2 != null) {
                return new String(b2, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            StringBuilder sb = new StringBuilder("AES:decrypt:");
            sb.append(str);
            sb.append(":String to Byte Array Error");
            SDKLogger.e("auth.AESCrypto", sb.toString(), e);
        }
        return null;
    }
}
