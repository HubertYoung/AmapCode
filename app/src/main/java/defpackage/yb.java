package defpackage;

import android.text.TextUtils;
import android.util.Base64;
import com.autonavi.server.aos.serverkey;

/* renamed from: yb reason: default package */
/* compiled from: SpEncryptUtil */
final class yb {
    static String a(String str) {
        String encodeToString = Base64.encodeToString(str.getBytes(), 2);
        StringBuilder sb = new StringBuilder("qplxzg");
        sb.append(encodeToString);
        sb.append("gffdge");
        return sb.toString();
    }

    static String b(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        return serverkey.amapDecodeV2(str);
    }

    static String c(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        return serverkey.amapEncodeV2(str);
    }
}
