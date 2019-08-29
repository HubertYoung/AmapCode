package com.alipay.android.phone.inside.log.util.sec;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;
import javax.crypto.Cipher;

public class EncryptUtil {
    private static String a;

    public static class Rsa {
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

    public static String a(Context context) {
        if (TextUtils.isEmpty(a)) {
            String str = "";
            try {
                String packageName = context.getApplicationContext().getPackageName();
                try {
                    str = Base64.encodeToString(packageName.getBytes(), 10);
                } catch (Throwable unused) {
                    str = packageName;
                }
            } catch (Throwable unused2) {
            }
            if (TextUtils.isEmpty(str)) {
                str = "unknow";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("0000000000000000000000000000");
            a = sb.toString().substring(0, 24);
        }
        return a;
    }

    public static String a(int i) {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            switch (random.nextInt(3)) {
                case 0:
                    stringBuffer.append(String.valueOf((char) ((int) Math.round((Math.random() * 25.0d) + 65.0d))));
                    break;
                case 1:
                    stringBuffer.append(String.valueOf((char) ((int) Math.round((Math.random() * 25.0d) + 97.0d))));
                    break;
                case 2:
                    stringBuffer.append(String.valueOf(new Random().nextInt(10)));
                    break;
            }
        }
        return stringBuffer.toString();
    }
}
