package defpackage;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.standardar.common.IMUReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: ci reason: default package */
/* compiled from: Utils */
public final class ci {
    public static String d(String str) {
        return str == null ? "" : str;
    }

    public static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        char[] charArray = str.toCharArray();
        if (charArray.length < 7 || charArray.length > 15) {
            return false;
        }
        int i = 0;
        int i2 = 0;
        for (char c : charArray) {
            if (c >= '0' && c <= '9') {
                i = ((i * 10) + c) - 48;
                if (i > 255) {
                    return false;
                }
            } else if (c != '.') {
                return false;
            } else {
                i2++;
                if (i2 > 3) {
                    return false;
                }
                i = 0;
            }
        }
        return true;
    }

    public static boolean b(String str) {
        int i;
        int i2;
        boolean z;
        int i3;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        char[] charArray = str.toCharArray();
        if (charArray.length < 2) {
            return false;
        }
        if (charArray[0] != ':') {
            i3 = 0;
            z = false;
            i2 = 0;
            i = 0;
        } else if (charArray[1] != ':') {
            return false;
        } else {
            i3 = 1;
            z = false;
            i2 = 0;
            i = 1;
        }
        boolean z2 = true;
        while (i3 < charArray.length) {
            char c = charArray[i3];
            int digit = Character.digit(c, 16);
            if (digit != -1) {
                i2 = (i2 << 4) + digit;
                if (i2 > 65535) {
                    return false;
                }
                z2 = false;
            } else if (c != ':') {
                return false;
            } else {
                i++;
                if (i > 7) {
                    return false;
                }
                if (!z2) {
                    i2 = 0;
                    z2 = true;
                } else if (z) {
                    return false;
                } else {
                    z = true;
                }
            }
            i3++;
        }
        if (z || i >= 7) {
            return true;
        }
        return false;
    }

    public static boolean c(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        char[] charArray = str.toCharArray();
        if (charArray.length <= 0 || charArray.length > 255) {
            return false;
        }
        boolean z = false;
        for (int i = 0; i < charArray.length; i++) {
            if ((charArray[i] >= 'A' && charArray[i] <= 'Z') || ((charArray[i] >= 'a' && charArray[i] <= 'z') || charArray[i] == '*')) {
                z = true;
            } else if (!((charArray[i] >= '0' && charArray[i] <= '9') || charArray[i] == '.' || charArray[i] == '-')) {
                return false;
            }
        }
        return z;
    }

    public static String a(long j) {
        StringBuilder sb = new StringBuilder(16);
        long j2 = IMUReader.NS_PER_SECOND;
        do {
            sb.append(j / j2);
            sb.append(DjangoUtils.EXTENSION_SEPARATOR);
            j %= j2;
            j2 /= 1000;
        } while (j2 > 0);
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public static String a(Map<String, String> map, String str) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder(64);
        try {
            for (Entry next : map.entrySet()) {
                if (next.getKey() != null) {
                    sb.append(URLEncoder.encode((String) next.getKey(), str));
                    sb.append("=");
                    sb.append(URLEncoder.encode(d((String) next.getValue()), str).replace("+", "%20"));
                    sb.append("&");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
        } catch (UnsupportedEncodingException unused) {
            cl.e("Request", "format params failed", null, new Object[0]);
        }
        return sb.toString();
    }
}
