package defpackage;

import android.text.TextUtils;

/* renamed from: ayr reason: default package */
/* compiled from: CommuteStringUtils */
public final class ayr {
    public static double a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return Double.parseDouble(str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0.0d;
    }

    public static String a(int i, String str) {
        if (str == null) {
            return "";
        }
        if (i < 0 || str.length() <= i) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, i));
        sb.append("...");
        return sb.toString();
    }
}
