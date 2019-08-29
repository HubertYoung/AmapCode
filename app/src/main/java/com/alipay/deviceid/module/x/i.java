package com.alipay.deviceid.module.x;

import com.alipay.android.phone.inside.offlinecode.utils.HexUtils;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.PBEKeySpec;

public final class i {
    private static String a = new String("idnjfhncnsfuobcnt847y929o449u474w7j3h22aoddc98euk#%&&)*&^%#");

    public static String a() {
        String str = new String();
        for (int i = 0; i < a.length() - 1; i += 4) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(a.charAt(i));
            str = sb.toString();
        }
        return str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:?, code lost:
        r7 = a(r7.getBytes());
        r8 = r8.getBytes();
        r1 = new javax.crypto.spec.SecretKeySpec(r7, "AES");
        r7 = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding");
        r7.init(1, r1, new javax.crypto.spec.IvParameterSpec(new byte[r7.getBlockSize()]));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0083, code lost:
        return b(r7.doFinal(r8));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0085, code lost:
        return null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:4:0x0054 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r7, java.lang.String r8) {
        /*
            r0 = 1
            javax.crypto.spec.PBEKeySpec r1 = a(r7)     // Catch:{ Exception -> 0x0054 }
            byte[] r2 = r8.getBytes()     // Catch:{ Exception -> 0x0054 }
            java.lang.String r3 = "PBKDF2WithHmacSHA1"
            javax.crypto.SecretKeyFactory r3 = javax.crypto.SecretKeyFactory.getInstance(r3)     // Catch:{ Exception -> 0x0054 }
            javax.crypto.SecretKey r3 = r3.generateSecret(r1)     // Catch:{ Exception -> 0x0054 }
            byte[] r3 = r3.getEncoded()     // Catch:{ Exception -> 0x0054 }
            javax.crypto.spec.SecretKeySpec r4 = new javax.crypto.spec.SecretKeySpec     // Catch:{ Exception -> 0x0054 }
            java.lang.String r5 = "AES"
            r4.<init>(r3, r5)     // Catch:{ Exception -> 0x0054 }
            java.lang.String r3 = "AES/CBC/PKCS5Padding"
            javax.crypto.Cipher r3 = javax.crypto.Cipher.getInstance(r3)     // Catch:{ Exception -> 0x0054 }
            javax.crypto.spec.IvParameterSpec r5 = new javax.crypto.spec.IvParameterSpec     // Catch:{ Exception -> 0x0054 }
            int r6 = r3.getBlockSize()     // Catch:{ Exception -> 0x0054 }
            byte[] r6 = new byte[r6]     // Catch:{ Exception -> 0x0054 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x0054 }
            r3.init(r0, r4, r5)     // Catch:{ Exception -> 0x0054 }
            byte[] r1 = r1.getSalt()     // Catch:{ Exception -> 0x0054 }
            int r4 = r1.length     // Catch:{ Exception -> 0x0054 }
            int r5 = r2.length     // Catch:{ Exception -> 0x0054 }
            int r5 = r3.getOutputSize(r5)     // Catch:{ Exception -> 0x0054 }
            int r4 = r4 + r5
            java.nio.ByteBuffer r4 = java.nio.ByteBuffer.allocate(r4)     // Catch:{ Exception -> 0x0054 }
            r4.put(r1)     // Catch:{ Exception -> 0x0054 }
            java.nio.ByteBuffer r1 = java.nio.ByteBuffer.wrap(r2)     // Catch:{ Exception -> 0x0054 }
            r3.doFinal(r1, r4)     // Catch:{ Exception -> 0x0054 }
            byte[] r1 = r4.array()     // Catch:{ Exception -> 0x0054 }
            java.lang.String r1 = b(r1)     // Catch:{ Exception -> 0x0054 }
            return r1
        L_0x0054:
            byte[] r7 = r7.getBytes()     // Catch:{ Exception -> 0x0084 }
            byte[] r7 = a(r7)     // Catch:{ Exception -> 0x0084 }
            byte[] r8 = r8.getBytes()     // Catch:{ Exception -> 0x0084 }
            javax.crypto.spec.SecretKeySpec r1 = new javax.crypto.spec.SecretKeySpec     // Catch:{ Exception -> 0x0084 }
            java.lang.String r2 = "AES"
            r1.<init>(r7, r2)     // Catch:{ Exception -> 0x0084 }
            java.lang.String r7 = "AES/CBC/PKCS5Padding"
            javax.crypto.Cipher r7 = javax.crypto.Cipher.getInstance(r7)     // Catch:{ Exception -> 0x0084 }
            javax.crypto.spec.IvParameterSpec r2 = new javax.crypto.spec.IvParameterSpec     // Catch:{ Exception -> 0x0084 }
            int r3 = r7.getBlockSize()     // Catch:{ Exception -> 0x0084 }
            byte[] r3 = new byte[r3]     // Catch:{ Exception -> 0x0084 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0084 }
            r7.init(r0, r1, r2)     // Catch:{ Exception -> 0x0084 }
            byte[] r7 = r7.doFinal(r8)     // Catch:{ Exception -> 0x0084 }
            java.lang.String r7 = b(r7)     // Catch:{ Exception -> 0x0084 }
            return r7
        L_0x0084:
            r7 = 0
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.deviceid.module.x.i.a(java.lang.String, java.lang.String):java.lang.String");
    }

    private static PBEKeySpec a(String str) {
        Class<?> cls = Class.forName(new String(g.a("amF2YS5zZWN1cml0eS5TZWN1cmVSYW5kb20=")));
        Object newInstance = cls.newInstance();
        byte[] bArr = new byte[16];
        Method method = cls.getMethod("nextBytes", new Class[]{bArr.getClass()});
        method.setAccessible(true);
        method.invoke(newInstance, new Object[]{bArr});
        return new PBEKeySpec(str.toCharArray(), bArr, 10, 128);
    }

    private static byte[] a(byte[] bArr) {
        KeyGenerator instance = KeyGenerator.getInstance("AES");
        SecureRandom instance2 = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        Method method = SecureRandom.class.getMethod("setSeed", new Class[]{bArr.getClass()});
        method.setAccessible(true);
        method.invoke(instance2, new Object[]{bArr});
        instance.init(128, instance2);
        return instance.generateKey().getEncoded();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:11|12|13) */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        r9 = a(r9.getBytes());
        r10 = b(r10);
        r2 = new javax.crypto.spec.SecretKeySpec(r9, "AES");
        r9 = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding");
        r9.init(2, r2, new javax.crypto.spec.IvParameterSpec(new byte[r9.getBlockSize()]));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x008f, code lost:
        return new java.lang.String(r9.doFinal(r10));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0090, code lost:
        return null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x005f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String b(java.lang.String r9, java.lang.String r10) {
        /*
            r0 = 2
            r1 = 0
            javax.crypto.spec.PBEKeySpec r2 = a(r9)     // Catch:{ Exception -> 0x005f }
            byte[] r3 = b(r10)     // Catch:{ Exception -> 0x005f }
            int r4 = r3.length     // Catch:{ Exception -> 0x005f }
            r5 = 16
            if (r4 > r5) goto L_0x0011
            r2 = r1
            goto L_0x0051
        L_0x0011:
            javax.crypto.spec.PBEKeySpec r4 = new javax.crypto.spec.PBEKeySpec     // Catch:{ Exception -> 0x005f }
            char[] r2 = r2.getPassword()     // Catch:{ Exception -> 0x005f }
            byte[] r6 = java.util.Arrays.copyOf(r3, r5)     // Catch:{ Exception -> 0x005f }
            r7 = 10
            r8 = 128(0x80, float:1.794E-43)
            r4.<init>(r2, r6, r7, r8)     // Catch:{ Exception -> 0x005f }
            java.lang.String r2 = "PBKDF2WithHmacSHA1"
            javax.crypto.SecretKeyFactory r2 = javax.crypto.SecretKeyFactory.getInstance(r2)     // Catch:{ Exception -> 0x005f }
            javax.crypto.SecretKey r2 = r2.generateSecret(r4)     // Catch:{ Exception -> 0x005f }
            byte[] r2 = r2.getEncoded()     // Catch:{ Exception -> 0x005f }
            javax.crypto.spec.SecretKeySpec r4 = new javax.crypto.spec.SecretKeySpec     // Catch:{ Exception -> 0x005f }
            java.lang.String r6 = "AES"
            r4.<init>(r2, r6)     // Catch:{ Exception -> 0x005f }
            java.lang.String r2 = "AES/CBC/PKCS5Padding"
            javax.crypto.Cipher r2 = javax.crypto.Cipher.getInstance(r2)     // Catch:{ Exception -> 0x005f }
            javax.crypto.spec.IvParameterSpec r6 = new javax.crypto.spec.IvParameterSpec     // Catch:{ Exception -> 0x005f }
            int r7 = r2.getBlockSize()     // Catch:{ Exception -> 0x005f }
            byte[] r7 = new byte[r7]     // Catch:{ Exception -> 0x005f }
            r6.<init>(r7)     // Catch:{ Exception -> 0x005f }
            r2.init(r0, r4, r6)     // Catch:{ Exception -> 0x005f }
            int r4 = r3.length     // Catch:{ Exception -> 0x005f }
            int r4 = r4 - r5
            byte[] r2 = r2.doFinal(r3, r5, r4)     // Catch:{ Exception -> 0x005f }
        L_0x0051:
            if (r2 != 0) goto L_0x0059
            java.lang.Exception r2 = new java.lang.Exception     // Catch:{ Exception -> 0x005f }
            r2.<init>()     // Catch:{ Exception -> 0x005f }
            throw r2     // Catch:{ Exception -> 0x005f }
        L_0x0059:
            java.lang.String r3 = new java.lang.String     // Catch:{ Exception -> 0x005f }
            r3.<init>(r2)     // Catch:{ Exception -> 0x005f }
            return r3
        L_0x005f:
            byte[] r9 = r9.getBytes()     // Catch:{ Exception -> 0x0090 }
            byte[] r9 = a(r9)     // Catch:{ Exception -> 0x0090 }
            byte[] r10 = b(r10)     // Catch:{ Exception -> 0x0090 }
            javax.crypto.spec.SecretKeySpec r2 = new javax.crypto.spec.SecretKeySpec     // Catch:{ Exception -> 0x0090 }
            java.lang.String r3 = "AES"
            r2.<init>(r9, r3)     // Catch:{ Exception -> 0x0090 }
            java.lang.String r9 = "AES/CBC/PKCS5Padding"
            javax.crypto.Cipher r9 = javax.crypto.Cipher.getInstance(r9)     // Catch:{ Exception -> 0x0090 }
            javax.crypto.spec.IvParameterSpec r3 = new javax.crypto.spec.IvParameterSpec     // Catch:{ Exception -> 0x0090 }
            int r4 = r9.getBlockSize()     // Catch:{ Exception -> 0x0090 }
            byte[] r4 = new byte[r4]     // Catch:{ Exception -> 0x0090 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0090 }
            r9.init(r0, r2, r3)     // Catch:{ Exception -> 0x0090 }
            byte[] r9 = r9.doFinal(r10)     // Catch:{ Exception -> 0x0090 }
            java.lang.String r10 = new java.lang.String     // Catch:{ Exception -> 0x0090 }
            r10.<init>(r9)     // Catch:{ Exception -> 0x0090 }
            return r10
        L_0x0090:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.deviceid.module.x.i.b(java.lang.String, java.lang.String):java.lang.String");
    }

    private static String b(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte b : bArr) {
            stringBuffer.append(HexUtils.HEX_CHARS.charAt((b >> 4) & 15));
            stringBuffer.append(HexUtils.HEX_CHARS.charAt(b & 15));
        }
        return stringBuffer.toString();
    }

    private static byte[] b(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = Integer.valueOf(str.substring(i2, i2 + 2), 16).byteValue();
        }
        return bArr;
    }
}
