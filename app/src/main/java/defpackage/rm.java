package defpackage;

import android.text.TextUtils;
import java.text.DecimalFormat;

/* renamed from: rm reason: default package */
/* compiled from: NaviTextUtil */
public final class rm {
    public static String a(String str, String str2, int i, int i2) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str2)) {
            sb.append("推荐走");
            sb.append(str2);
            sb.append(",");
        }
        String b = b(i);
        if (!TextUtils.isEmpty(b)) {
            sb.append("全程");
            sb.append(b);
            sb.append(",");
        }
        String a = a(i2);
        if (!TextUtils.isEmpty(a)) {
            sb.append("约");
            sb.append(a);
            sb.append("到达,");
        }
        return str.replace("<routeIntroFromApp>", sb.toString());
    }

    private static String a(int i) {
        String str;
        if (i <= 0) {
            return "";
        }
        int round = Math.round(((float) i) / 60.0f);
        if (round <= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(i);
            sb.append("秒");
            str = sb.toString();
        } else if (round >= 60) {
            int i2 = round % 60;
            int i3 = round / 60;
            if (i2 == 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(i3);
                sb2.append("小时");
                str = sb2.toString();
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(i3);
                sb3.append("小时");
                sb3.append(i2);
                sb3.append("分钟");
                str = sb3.toString();
            }
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(round);
            sb4.append("分钟");
            str = sb4.toString();
        }
        return str;
    }

    private static String b(int i) {
        if (i > 0 && i < 1000) {
            StringBuilder sb = new StringBuilder();
            sb.append(i);
            sb.append("米");
            return sb.toString();
        } else if (i < 1000) {
            return "";
        } else {
            String format = new DecimalFormat("0.0").format((double) (((float) i) / 1000.0f));
            if (format.endsWith(".0")) {
                format = format.replace(".0", "");
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(format);
            sb2.append("公里");
            return sb2.toString();
        }
    }
}
