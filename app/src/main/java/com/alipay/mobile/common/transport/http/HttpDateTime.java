package com.alipay.mobile.common.transport.http;

import android.text.format.Time;
import com.alibaba.wireless.security.SecExceptionCode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HttpDateTime {
    private static final Pattern a = Pattern.compile("([0-9]{1,2})[- ]([A-Za-z]{3,9})[- ]([0-9]{2,4})[ ]([0-9]{1,2}:[0-9][0-9]:[0-9][0-9])");
    private static final Pattern b = Pattern.compile("[ ]([A-Za-z]{3,9})[ ]+([0-9]{1,2})[ ]([0-9]{1,2}:[0-9][0-9]:[0-9][0-9])[ ]([0-9]{2,4})");

    class TimeOfDay {
        int hour;
        int minute;
        int second;

        TimeOfDay(int h, int m, int s) {
            this.hour = h;
            this.minute = m;
            this.second = s;
        }
    }

    public static long parse(String timeString) {
        int month;
        int date;
        TimeOfDay timeOfDay;
        int year;
        Matcher rfcMatcher = a.matcher(timeString);
        if (rfcMatcher.find()) {
            date = a(rfcMatcher.group(1));
            month = b(rfcMatcher.group(2));
            year = c(rfcMatcher.group(3));
            timeOfDay = d(rfcMatcher.group(4));
        } else {
            Matcher ansicMatcher = b.matcher(timeString);
            if (ansicMatcher.find()) {
                month = b(ansicMatcher.group(1));
                date = a(ansicMatcher.group(2));
                timeOfDay = d(ansicMatcher.group(3));
                year = c(ansicMatcher.group(4));
            } else {
                throw new IllegalArgumentException();
            }
        }
        if (year >= 2038) {
            year = 2038;
            month = 0;
            date = 1;
        }
        Time time = new Time("UTC");
        time.set(timeOfDay.second, timeOfDay.minute, timeOfDay.hour, date, month, year);
        return time.toMillis(false);
    }

    private static int a(String dateString) {
        if (dateString.length() == 2) {
            return ((dateString.charAt(0) - '0') * 10) + (dateString.charAt(1) - '0');
        }
        return dateString.charAt(0) - '0';
    }

    private static int b(String monthString) {
        switch (((Character.toLowerCase(monthString.charAt(0)) + Character.toLowerCase(monthString.charAt(1))) + Character.toLowerCase(monthString.charAt(2))) - 291) {
            case 9:
                return 11;
            case 10:
                return 1;
            case 22:
                return 0;
            case 26:
                return 7;
            case 29:
                return 2;
            case 32:
                return 3;
            case 35:
                return 9;
            case 36:
                return 4;
            case 37:
                return 8;
            case 40:
                return 6;
            case 42:
                return 5;
            case 48:
                return 10;
            default:
                throw new IllegalArgumentException();
        }
    }

    private static int c(String yearString) {
        if (yearString.length() == 2) {
            int year = ((yearString.charAt(0) - '0') * 10) + (yearString.charAt(1) - '0');
            if (year >= 70) {
                return year + SecExceptionCode.SEC_ERROR_AVMP;
            }
            return year + 2000;
        } else if (yearString.length() == 3) {
            return ((yearString.charAt(0) - '0') * 100) + ((yearString.charAt(1) - '0') * 10) + (yearString.charAt(2) - '0') + SecExceptionCode.SEC_ERROR_AVMP;
        } else {
            if (yearString.length() == 4) {
                return ((yearString.charAt(0) - '0') * 1000) + ((yearString.charAt(1) - '0') * 100) + ((yearString.charAt(2) - '0') * 10) + (yearString.charAt(3) - '0');
            }
            return 1970;
        }
    }

    private static TimeOfDay d(String timeString) {
        int i = 0 + 1;
        int hour = timeString.charAt(0) - '0';
        if (timeString.charAt(1) != ':') {
            i++;
            hour = (hour * 10) + (timeString.charAt(1) - '0');
        }
        int i2 = i + 1;
        int i3 = i2 + 1;
        int i4 = i3 + 1 + 1;
        return new TimeOfDay(hour, ((timeString.charAt(i2) - '0') * 10) + (timeString.charAt(i3) - '0'), ((timeString.charAt(i4) - '0') * 10) + (timeString.charAt(i4 + 1) - '0'));
    }
}
