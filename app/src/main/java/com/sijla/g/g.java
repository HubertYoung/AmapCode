package com.sijla.g;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.Locale;

public class g {
    public static final String a = System.getProperty("line.separator");
    private static boolean b = false;
    private static boolean c = false;
    private static boolean d = false;
    private static boolean e = false;
    private static boolean f = false;

    public static void a(String str, Object obj, String str2) {
    }

    private static String a(StackTraceElement stackTraceElement) {
        String className = stackTraceElement.getClassName();
        String substring = className.substring(className.lastIndexOf(".") + 1);
        return String.format(Locale.getDefault(), "%s.%s(L:%d)", new Object[]{substring, stackTraceElement.getMethodName(), Integer.valueOf(stackTraceElement.getLineNumber())});
    }

    public static void a(String str) {
        a("QLOG", str);
    }

    public static void a(String str, String str2) {
        if (b) {
            a(a());
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(Token.SEPARATOR);
            sb.append(str2);
        }
    }

    private static StackTraceElement a() {
        return Thread.currentThread().getStackTrace()[5];
    }
}
