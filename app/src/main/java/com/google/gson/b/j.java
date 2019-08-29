package com.google.gson.b;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* compiled from: PreJava9DateFormatProvider */
public class j {
    public static DateFormat a(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(a(i));
        sb.append(Token.SEPARATOR);
        sb.append(b(i2));
        return new SimpleDateFormat(sb.toString(), Locale.US);
    }

    private static String a(int i) {
        switch (i) {
            case 0:
                return "EEEE, MMMM d, yyyy";
            case 1:
                return "MMMM d, yyyy";
            case 2:
                return "MMM d, yyyy";
            case 3:
                return "M/d/yy";
            default:
                throw new IllegalArgumentException("Unknown DateFormat style: ".concat(String.valueOf(i)));
        }
    }

    private static String b(int i) {
        switch (i) {
            case 0:
            case 1:
                return "h:mm:ss a z";
            case 2:
                return "h:mm:ss a";
            case 3:
                return "h:mm a";
            default:
                throw new IllegalArgumentException("Unknown DateFormat style: ".concat(String.valueOf(i)));
        }
    }
}
