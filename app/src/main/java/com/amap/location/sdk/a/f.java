package com.amap.location.sdk.a;

import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;
import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/* compiled from: RSAUtils */
public class f {
    public static synchronized byte[] a(byte[] bArr, String str) throws Exception {
        byte[] byteArray;
        byte[] bArr2;
        synchronized (f.class) {
            try {
                PublicKey generatePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(d.a(str)));
                Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                instance.init(2, generatePublic);
                int length = bArr.length;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int i = 0;
                int i2 = 0;
                while (true) {
                    int i3 = length - i;
                    if (i3 > 0) {
                        if (i3 > 256) {
                            bArr2 = instance.doFinal(bArr, i, 256);
                        } else {
                            bArr2 = instance.doFinal(bArr, i, i3);
                        }
                        byteArrayOutputStream.write(bArr2, 0, bArr2.length);
                        i2++;
                        i = i2 * 256;
                    } else {
                        byteArray = byteArrayOutputStream.toByteArray();
                        byteArrayOutputStream.close();
                    }
                }
            }
        }
        return byteArray;
    }

    public static synchronized byte[] b(byte[] bArr, String str) throws Exception {
        byte[] byteArray;
        byte[] bArr2;
        synchronized (f.class) {
            try {
                PublicKey generatePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(d.a(str)));
                Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                instance.init(1, generatePublic);
                int length = bArr.length;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int i = 0;
                int i2 = 0;
                while (true) {
                    int i3 = length - i;
                    if (i3 > 0) {
                        if (i3 > 245) {
                            bArr2 = instance.doFinal(bArr, i, FavoritesPointFragment.REQUEST_TAG_SELECT);
                        } else {
                            bArr2 = instance.doFinal(bArr, i, i3);
                        }
                        byteArrayOutputStream.write(bArr2, 0, bArr2.length);
                        i2++;
                        i = i2 * FavoritesPointFragment.REQUEST_TAG_SELECT;
                    } else {
                        byteArray = byteArrayOutputStream.toByteArray();
                        byteArrayOutputStream.close();
                    }
                }
            }
        }
        return byteArray;
    }
}
