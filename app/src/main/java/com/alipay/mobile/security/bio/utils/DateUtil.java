package com.alipay.mobile.security.bio.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {
    public static final SimpleDateFormat DATE_FORMAT2 = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS", Locale.US);

    public static String getDetailDateFormat(long j) {
        String format;
        synchronized (DATE_FORMAT2) {
            format = DATE_FORMAT2.format(new Date(j));
        }
        return format;
    }

    public static String getDateFormat(long j) {
        Date date = new Date(j);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return new SimpleDateFormat("yyyyMMddhhmmss").format(gregorianCalendar.getTime());
    }

    public static String getSimpleDateFormat(long j) {
        Date date = new Date(j);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return new SimpleDateFormat("yyyyMMdd").format(gregorianCalendar.getTime());
    }
}
