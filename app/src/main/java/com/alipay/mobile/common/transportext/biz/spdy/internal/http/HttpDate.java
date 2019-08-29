package com.alipay.mobile.common.transportext.biz.spdy.internal.http;

import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

final class HttpDate {
    private static final DateFormat[] BROWSER_COMPATIBLE_DATE_FORMATS = new DateFormat[14];
    private static final String[] BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS = {"EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z", "EEE MMM d yyyy HH:mm:ss z"};
    private static final ThreadLocal<DateFormat> STANDARD_DATE_FORMAT = new ThreadLocal<DateFormat>() {
        /* access modifiers changed from: protected */
        public final DateFormat initialValue() {
            DateFormat rfc1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
            rfc1123.setTimeZone(TimeZone.getTimeZone("GMT"));
            return rfc1123;
        }
    };

    public static Date parse(String value) {
        try {
            return STANDARD_DATE_FORMAT.get().parse(value);
        } catch (ParseException ignored) {
            LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "parse(" + value + ") exception : " + ignored.toString());
            synchronized (BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS) {
                int i = 0;
                int count = BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS.length;
                while (i < count) {
                    DateFormat format = BROWSER_COMPATIBLE_DATE_FORMATS[i];
                    if (format == null) {
                        format = new SimpleDateFormat(BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS[i], Locale.US);
                        BROWSER_COMPATIBLE_DATE_FORMATS[i] = format;
                    }
                    try {
                        return format.parse(value);
                    } catch (ParseException ignored2) {
                        LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "format(" + value + ") exception : " + ignored2.toString());
                        i++;
                    }
                }
                return null;
            }
        }
    }

    public static String format(Date value) {
        return STANDARD_DATE_FORMAT.get().format(value);
    }

    private HttpDate() {
    }
}
