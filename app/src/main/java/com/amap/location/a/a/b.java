package com.amap.location.a.a;

import android.text.TextUtils;
import com.alipay.multimedia.img.utils.ImageFileType;
import com.amap.location.common.e.a;
import com.amap.location.common.f.c;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import javax.crypto.Cipher;

/* compiled from: CloudUtils */
public class b {
    private static final byte[] a = {48, 92, 48, 13, 6, 9, 42, -122, ImageFileType.HEAD_HEVC_0, -122, -9, 13, 1, 1, 1, 5, 0, 3, 75, 0, 48, ImageFileType.HEAD_HEVC_0, 2, 65, 0, -18, 74, 99, -65, 73, -9, -121, 104, -44, 122, 126, -72, -9, 4, -10, -30, 102, -73, 101, 13, -119, -83, -90, -101, 87, -8, -70, 57, 80, 65, 125, -117, -49, -118, 45, -73, 75, 39, -45, -16, -116, 34, -36, -118, -121, -78, -72, Byte.MIN_VALUE, 67, -15, -31, 23, -7, -21, -72, -127, -89, -95, -23, 121, -60, 24, 5, -75, 2, 3, 1, 0, 1};

    public static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        int indexOf = str.indexOf("$");
        if (indexOf <= 0) {
            return false;
        }
        try {
            String substring = str.substring(0, indexOf);
            String substring2 = str.substring(indexOf + 1, str.length());
            if (TextUtils.isEmpty(substring)) {
                return false;
            }
            byte[] a2 = a(c.a(substring, (String) "", (String) ""));
            byte[] b = b(substring2);
            if (a2 != null) {
                if (b != null) {
                    return Arrays.equals(a2, b);
                }
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    public static byte[] a(String str, byte[] bArr, a aVar) {
        try {
            if (aVar.f() != null) {
                HashMap hashMap = new HashMap();
                hashMap.put("et", "111");
                hashMap.put("Accept-Encoding", "gzip");
                a aVar2 = new a();
                aVar2.b = hashMap;
                aVar2.c = bArr;
                aVar2.a = str;
                com.amap.location.common.e.b a2 = aVar.f().a(aVar2);
                if (a2 == null || a2.a != 200) {
                    return null;
                }
                return a2.c;
            }
            com.amap.location.common.d.a.d("CloudUtils", "网络库为空");
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static byte[] b(String str) {
        try {
            return MessageDigest.getInstance("SHA1").digest(str.getBytes());
        } catch (Exception unused) {
            return null;
        }
    }

    private static byte[] a(byte[] bArr) {
        try {
            PublicKey generatePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(a));
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            instance.init(2, generatePublic);
            return instance.doFinal(bArr);
        } catch (Exception unused) {
            return null;
        }
    }
}
