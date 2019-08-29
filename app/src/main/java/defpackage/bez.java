package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.logs.AMapLog;

/* renamed from: bez reason: default package */
/* compiled from: UIAMapLogUtil */
public final class bez {
    public static void a(String str, String str2, bew... bewArr) {
        if (TextUtils.isEmpty(str)) {
            str = "ui_template";
        }
        AMapLog.info("basemap.uitemplate", str, a(str2, bewArr));
    }

    public static void b(String str, String str2, bew... bewArr) {
        if (b.a) {
            AMapLog.debug("basemap.uitemplate", str, a(str2, bewArr));
        }
    }

    private static String a(String str, bew... bewArr) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
        }
        if (bewArr != null && bewArr.length > 0) {
            for (bew bew : bewArr) {
                sb.append(Token.SEPARATOR);
                sb.append(bew.a);
                sb.append(":");
                sb.append(bew.b);
            }
        }
        return sb.toString();
    }
}
