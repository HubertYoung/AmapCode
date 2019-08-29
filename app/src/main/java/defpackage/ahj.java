package defpackage;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/* renamed from: ahj reason: default package */
/* compiled from: UrlEncode */
public final class ahj {
    public static byte[] a(String str) {
        byte[] bArr;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            bArr = URLEncoder.encode(str, "UTF-8").getBytes("UTF-8");
            int i = 0;
            while (i < bArr.length) {
                try {
                    bArr[i] = (byte) (bArr[i] + 16);
                    i++;
                } catch (Exception unused) {
                }
            }
        } catch (Exception unused2) {
            bArr = null;
        }
        return bArr;
    }

    public static String a(String str, String str2) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str2;
        }
    }
}
