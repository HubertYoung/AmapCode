package com.ali.auth.third.core.cookies;

import android.text.format.Time;
import com.alibaba.wireless.security.SecExceptionCode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class b {
    private static final Pattern a = Pattern.compile("([0-9]{1,2})[- ]([A-Za-z]{3,9})[- ]([0-9]{2,4})[ ]([0-9]{1,2}:[0-9][0-9]:[0-9][0-9])");
    private static final Pattern b = Pattern.compile("[ ]([A-Za-z]{3,9})[ ]+([0-9]{1,2})[ ]([0-9]{1,2}:[0-9][0-9]:[0-9][0-9])[ ]([0-9]{2,4})");

    static class a {
        int a;
        int b;
        int c;

        a(int i, int i2, int i3) {
            this.a = i;
            this.b = i2;
            this.c = i3;
        }
    }

    public static long a(String str) throws IllegalArgumentException {
        int i;
        int i2;
        int i3;
        a aVar;
        int i4;
        int i5;
        int i6;
        Matcher matcher = a.matcher(str);
        if (matcher.find()) {
            int b2 = b(matcher.group(1));
            int c = c(matcher.group(2));
            int d = d(matcher.group(3));
            aVar = e(matcher.group(4));
            i2 = c;
            i3 = b2;
            i = d;
        } else {
            Matcher matcher2 = b.matcher(str);
            if (matcher2.find()) {
                int c2 = c(matcher2.group(1));
                int b3 = b(matcher2.group(2));
                a e = e(matcher2.group(3));
                i = d(matcher2.group(4));
                i2 = c2;
                i3 = b3;
                aVar = e;
            } else {
                throw new IllegalArgumentException();
            }
        }
        if (i >= 2038) {
            i6 = 1;
            i5 = 0;
            i4 = 2038;
        } else {
            i4 = i;
            i6 = i3;
            i5 = i2;
        }
        Time time = new Time("UTC");
        time.set(aVar.c, aVar.b, aVar.a, i6, i5, i4);
        return time.toMillis(false);
    }

    private static int b(String str) {
        return str.length() == 2 ? ((str.charAt(0) - '0') * 10) + (str.charAt(1) - '0') : str.charAt(0) - '0';
    }

    private static int c(String str) {
        int lowerCase = ((Character.toLowerCase(str.charAt(0)) + Character.toLowerCase(str.charAt(1))) + Character.toLowerCase(str.charAt(2))) - 291;
        if (lowerCase == 22) {
            return 0;
        }
        if (lowerCase == 26) {
            return 7;
        }
        if (lowerCase == 29) {
            return 2;
        }
        if (lowerCase == 32) {
            return 3;
        }
        if (lowerCase == 40) {
            return 6;
        }
        if (lowerCase == 42) {
            return 5;
        }
        if (lowerCase == 48) {
            return 10;
        }
        switch (lowerCase) {
            case 9:
                return 11;
            case 10:
                return 1;
            default:
                switch (lowerCase) {
                    case 35:
                        return 9;
                    case 36:
                        return 4;
                    case 37:
                        return 8;
                    default:
                        throw new IllegalArgumentException();
                }
        }
    }

    private static int d(String str) {
        if (str.length() == 2) {
            int charAt = ((str.charAt(0) - '0') * 10) + (str.charAt(1) - '0');
            return charAt >= 70 ? charAt + SecExceptionCode.SEC_ERROR_AVMP : charAt + 2000;
        } else if (str.length() == 3) {
            return ((str.charAt(0) - '0') * 100) + ((str.charAt(1) - '0') * 10) + (str.charAt(2) - '0') + SecExceptionCode.SEC_ERROR_AVMP;
        } else {
            if (str.length() == 4) {
                return ((str.charAt(0) - '0') * 1000) + ((str.charAt(1) - '0') * 100) + ((str.charAt(2) - '0') * 10) + (str.charAt(3) - '0');
            }
            return 1970;
        }
    }

    private static a e(String str) {
        int i;
        int charAt = str.charAt(0) - '0';
        if (str.charAt(1) != ':') {
            i = 2;
            charAt = (charAt * 10) + (str.charAt(1) - '0');
        } else {
            i = 1;
        }
        int i2 = i + 1;
        int i3 = i2 + 1;
        int i4 = i3 + 1 + 1;
        return new a(charAt, ((str.charAt(i2) - '0') * 10) + (str.charAt(i3) - '0'), ((str.charAt(i4) - '0') * 10) + (str.charAt(i4 + 1) - '0'));
    }
}
