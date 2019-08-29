package com.ali.user.mobile.util;

import android.text.TextUtils;
import com.ali.auth.third.login.LoginConstants;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;

public class StringUtil {
    public static String a(String str, String str2) {
        if (!LoginConstants.TAOBAO_LOGIN.equals(str2)) {
            return b(str);
        }
        if (str.contains(AUScreenAdaptTool.PREFIX_ID) || str.matches("\\d{1,}")) {
            return b(str);
        }
        return str;
    }

    public static boolean a(String str) {
        if (!TextUtils.isEmpty(str)) {
            return str.matches("^(86){0,1}1\\d{10}$");
        }
        return false;
    }

    public static String b(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return str2;
        }
        if ((!TextUtils.isEmpty(str) && str.startsWith("+86")) || str.startsWith("86")) {
            return str2;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("-");
        sb.append(str2);
        return sb.toString().replace("+", "");
    }

    public static String b(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (!str.contains(AUScreenAdaptTool.PREFIX_ID)) {
            return c(str);
        }
        int indexOf = str.indexOf(64);
        String substring = str.substring(0, indexOf);
        String substring2 = str.substring(indexOf, str.length());
        if (substring.length() >= 3) {
            StringBuilder sb = new StringBuilder();
            sb.append(substring.substring(0, 3));
            sb.append("***");
            sb.append(substring2);
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(substring);
        sb2.append("***");
        sb2.append(substring2);
        return sb2.toString();
    }

    public static String c(String str) {
        if (str.matches("^(86){0,1}1\\d{10}$")) {
            String substring = str.substring(0, 3);
            String substring2 = str.substring(str.length() - 2, str.length());
            StringBuilder sb = new StringBuilder();
            sb.append(substring);
            sb.append("******");
            sb.append(substring2);
            return sb.toString();
        } else if (str.matches("^(00){0,1}(852-){1}0{0,1}(?:\\d{8}|\\d{9}|\\d{13})$")) {
            return g(str);
        } else {
            if (str.matches("^(00){0,1}(853-){1}6\\d{7}$")) {
                return g(str);
            }
            if (!str.matches("^(00){0,1}(886-){1}0{0,1}[6,7,9](?:\\d{7}|\\d{8}|\\d{10})$")) {
                return f(str) ? h(str) : str;
            }
            StringBuilder sb2 = new StringBuilder(str);
            int indexOf = str.indexOf(45);
            if (indexOf > 0) {
                sb2.replace(indexOf + 3, str.length() - 3, "****");
            } else {
                sb2.replace(2, str.length() - 3, "****");
            }
            return sb2.toString();
        }
    }

    public static boolean e(String str) {
        return str.matches("^(86){0,1}1\\d{10}$");
    }

    private static boolean f(String str) {
        int indexOf = str.indexOf(45);
        if (indexOf <= 0 || indexOf >= str.length() - 1) {
            return false;
        }
        String[] split = str.split("-");
        if (!split[0].matches("\\d{1,}") || !split[1].matches("\\d{1,}")) {
            return false;
        }
        return true;
    }

    private static String g(String str) {
        StringBuilder sb = new StringBuilder(str);
        int indexOf = str.indexOf(45);
        if (indexOf > 0) {
            sb.replace(indexOf + 3, str.length() - 2, "****");
        } else {
            sb.replace(2, str.length() - 2, "****");
        }
        return sb.toString();
    }

    private static String h(String str) {
        int indexOf = str.indexOf(45);
        if (indexOf <= 0 || indexOf >= str.length() - 1) {
            return str;
        }
        String[] split = str.split("-");
        if (2 != split.length) {
            return str;
        }
        int length = split[1].length() / 3;
        int i = split[1].length() % 3 != 0 ? length + 1 : length;
        int length2 = (split[1].length() - i) - length;
        StringBuilder sb = new StringBuilder(str.length());
        sb.append(split[0]);
        sb.append('-');
        sb.append(split[1].substring(0, i));
        for (int i2 = 0; i2 < length2; i2++) {
            sb.append('*');
        }
        sb.append(split[1].substring(i + length2));
        return sb.toString();
    }

    public static String a(String str, int i) {
        int i2;
        int i3;
        StringBuilder reverse = new StringBuilder(str).reverse();
        StringBuilder sb = new StringBuilder();
        if (str.length() % i == 0) {
            i2 = str.length() / i;
        } else {
            i2 = (str.length() / i) + 1;
        }
        int i4 = 0;
        while (true) {
            i3 = i2 - 1;
            if (i4 >= i3) {
                break;
            }
            int i5 = i4 * i;
            sb.append(reverse.subSequence(i5, i5 + i));
            sb.append(Token.SEPARATOR);
            i4++;
        }
        while (i3 < i2) {
            sb.append(reverse.subSequence(i3 * i, str.length()));
            i3++;
        }
        return sb.reverse().toString();
    }

    public static boolean d(String str) {
        return str.matches("^(86){0,1}1\\d{10}$") || str.matches("^(00){0,1}(852-){1}0{0,1}(?:\\d{8}|\\d{9}|\\d{13})$") || str.matches("^(00){0,1}(886-){1}0{0,1}[6,7,9](?:\\d{7}|\\d{8}|\\d{10})$") || f(str);
    }
}
