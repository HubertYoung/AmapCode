package org.nanohttpd.protocols.http.content;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Cookie {
    private final String a;
    private final String b;
    private final String c;

    public static String getHTTPTime(int days) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        calendar.add(5, days);
        return dateFormat.format(calendar.getTime());
    }

    public Cookie(String name, String value) {
        this(name, value, 30);
    }

    public Cookie(String name, String value, int numDays) {
        this.a = name;
        this.b = value;
        this.c = getHTTPTime(numDays);
    }

    public Cookie(String name, String value, String expires) {
        this.a = name;
        this.b = value;
        this.c = expires;
    }

    public String getHTTPHeader() {
        return String.format("%s=%s; expires=%s", new Object[]{this.a, this.b, this.c});
    }
}
