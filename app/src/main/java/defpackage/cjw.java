package defpackage;

import android.text.TextUtils;
import com.autonavi.server.aos.serverkey;

/* renamed from: cjw reason: default package */
/* compiled from: UidDecoder */
public final class cjw {
    public static String a(String str) {
        String str2;
        try {
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            str2 = serverkey.amapDecode(str);
            return str2;
        } catch (Throwable unused) {
            str2 = "";
        }
    }

    public static String b(String str) {
        String str2;
        try {
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            str2 = serverkey.amapEncode(str);
            return str2;
        } catch (Throwable unused) {
            str2 = "";
        }
    }
}
