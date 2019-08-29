package com.google.zxing.client.result;

import com.alipay.mobile.nebula.filecache.FileCache;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CalendarParsedResult extends ParsedResult {
    private static final Pattern DATE_TIME = Pattern.compile("[0-9]{8}(T[0-9]{6}Z?)?");
    private static final Pattern RFC2445_DURATION = Pattern.compile("P(?:(\\d+)W)?(?:(\\d+)D)?(?:T(?:(\\d+)H)?(?:(\\d+)M)?(?:(\\d+)S)?)?");
    private static final long[] RFC2445_DURATION_FIELD_UNITS = {FileCache.EXPIRE_TIME, 86400000, 3600000, 60000, 1000};
    private final String[] attendees;
    private final String description;
    private final Date end;
    private final boolean endAllDay;
    private final double latitude;
    private final String location;
    private final double longitude;
    private final String organizer;
    private final Date start;
    private final boolean startAllDay;
    private final String summary;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004a, code lost:
        r4 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public CalendarParsedResult(java.lang.String r9, java.lang.String r10, java.lang.String r11, java.lang.String r12, java.lang.String r13, java.lang.String r14, java.lang.String[] r15, java.lang.String r16, double r17, double r19) {
        /*
            r8 = this;
            r1 = r8
            com.google.zxing.client.result.ParsedResultType r3 = com.google.zxing.client.result.ParsedResultType.CALENDAR
            r1.<init>(r3)
            r3 = r9
            r1.summary = r3
            java.util.Date r3 = parseDate(r10)     // Catch:{ ParseException -> 0x006f }
            r1.start = r3     // Catch:{ ParseException -> 0x006f }
            if (r11 != 0) goto L_0x002d
            long r3 = parseDurationMS(r12)
            r5 = 0
            int r5 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r5 >= 0) goto L_0x001d
            r3 = 0
            goto L_0x002a
        L_0x001d:
            java.util.Date r5 = new java.util.Date
            java.util.Date r6 = r1.start
            long r6 = r6.getTime()
            long r6 = r6 + r3
            r5.<init>(r6)
            r3 = r5
        L_0x002a:
            r1.end = r3
            goto L_0x0033
        L_0x002d:
            java.util.Date r3 = parseDate(r11)     // Catch:{ ParseException -> 0x0063 }
            r1.end = r3     // Catch:{ ParseException -> 0x0063 }
        L_0x0033:
            int r3 = r10.length()
            r4 = 0
            r5 = 1
            r6 = 8
            if (r3 != r6) goto L_0x003f
            r3 = 1
            goto L_0x0040
        L_0x003f:
            r3 = 0
        L_0x0040:
            r1.startAllDay = r3
            if (r11 == 0) goto L_0x004b
            int r2 = r11.length()
            if (r2 != r6) goto L_0x004b
            r4 = 1
        L_0x004b:
            r1.endAllDay = r4
            r2 = r13
            r1.location = r2
            r2 = r14
            r1.organizer = r2
            r2 = r15
            r1.attendees = r2
            r2 = r16
            r1.description = r2
            r2 = r17
            r1.latitude = r2
            r2 = r19
            r1.longitude = r2
            return
        L_0x0063:
            r0 = move-exception
            r2 = r0
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            java.lang.String r2 = r2.toString()
            r3.<init>(r2)
            throw r3
        L_0x006f:
            r0 = move-exception
            r2 = r0
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            java.lang.String r2 = r2.toString()
            r3.<init>(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.client.result.CalendarParsedResult.<init>(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String[], java.lang.String, double, double):void");
    }

    public final String getSummary() {
        return this.summary;
    }

    public final Date getStart() {
        return this.start;
    }

    public final boolean isStartAllDay() {
        return this.startAllDay;
    }

    public final Date getEnd() {
        return this.end;
    }

    public final boolean isEndAllDay() {
        return this.endAllDay;
    }

    public final String getLocation() {
        return this.location;
    }

    public final String getOrganizer() {
        return this.organizer;
    }

    public final String[] getAttendees() {
        return this.attendees;
    }

    public final String getDescription() {
        return this.description;
    }

    public final double getLatitude() {
        return this.latitude;
    }

    public final double getLongitude() {
        return this.longitude;
    }

    public final String getDisplayResult() {
        StringBuilder sb = new StringBuilder(100);
        maybeAppend(this.summary, sb);
        maybeAppend(format(this.startAllDay, this.start), sb);
        maybeAppend(format(this.endAllDay, this.end), sb);
        maybeAppend(this.location, sb);
        maybeAppend(this.organizer, sb);
        maybeAppend(this.attendees, sb);
        maybeAppend(this.description, sb);
        return sb.toString();
    }

    private static Date parseDate(String str) throws ParseException {
        Date date;
        if (!DATE_TIME.matcher(str).matches()) {
            throw new ParseException(str, 0);
        } else if (str.length() == 8) {
            return buildDateFormat().parse(str);
        } else {
            if (str.length() == 16 && str.charAt(15) == 'Z') {
                Date parse = buildDateTimeFormat().parse(str.substring(0, 15));
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                long time = parse.getTime() + ((long) gregorianCalendar.get(15));
                gregorianCalendar.setTime(new Date(time));
                date = new Date(time + ((long) gregorianCalendar.get(16)));
            } else {
                date = buildDateTimeFormat().parse(str);
            }
            return date;
        }
    }

    private static String format(boolean z, Date date) {
        DateFormat dateFormat;
        if (date == null) {
            return null;
        }
        if (z) {
            dateFormat = DateFormat.getDateInstance(2);
        } else {
            dateFormat = DateFormat.getDateTimeInstance(2, 2);
        }
        return dateFormat.format(date);
    }

    private static long parseDurationMS(CharSequence charSequence) {
        if (charSequence == null) {
            return -1;
        }
        Matcher matcher = RFC2445_DURATION.matcher(charSequence);
        if (!matcher.matches()) {
            return -1;
        }
        long j = 0;
        int i = 0;
        while (i < RFC2445_DURATION_FIELD_UNITS.length) {
            int i2 = i + 1;
            String group = matcher.group(i2);
            if (group != null) {
                j += RFC2445_DURATION_FIELD_UNITS[i] * ((long) Integer.parseInt(group));
            }
            i = i2;
        }
        return j;
    }

    private static DateFormat buildDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat;
    }

    private static DateFormat buildDateTimeFormat() {
        return new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.ENGLISH);
    }
}
