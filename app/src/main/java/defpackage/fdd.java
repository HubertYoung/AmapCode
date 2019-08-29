package defpackage;

import java.util.Locale;

/* renamed from: fdd reason: default package */
/* compiled from: StringUtils */
public final class fdd {
    public static boolean a(String str) {
        return !b(str);
    }

    public static boolean b(String str) {
        if (str != null) {
            int length = str.length();
            if (length != 0) {
                for (int i = 0; i < length; i++) {
                    if (!Character.isWhitespace(str.charAt(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return true;
    }

    public static String a(String str, String str2) {
        if (b(str) || b(str2)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str.trim());
        sb.append("$");
        sb.append(str2.trim());
        return sb.toString();
    }

    public static String b(String str, String str2) {
        if (b(str) || b(str2)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str.trim());
        sb.append("$");
        sb.append(str2.trim());
        return sb.toString().toLowerCase(Locale.US);
    }
}
