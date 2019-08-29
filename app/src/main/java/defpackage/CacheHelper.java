package defpackage;

import anetwork.channel.cache.Cache.Entry;
import com.autonavi.minimap.offline.auto.protocol.request.AutoDownloadLogRequest;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/* renamed from: do reason: invalid class name and default package and case insensitive filesystem */
public final class CacheHelper {
    private static final TimeZone a = TimeZone.getTimeZone("GMT");
    private static final DateFormat b;

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        b = simpleDateFormat;
        simpleDateFormat.setTimeZone(a);
    }

    public static String a(long j) {
        return b.format(new Date(j));
    }

    private static long a(String str) {
        if (str.length() == 0) {
            return 0;
        }
        try {
            ParsePosition parsePosition = new ParsePosition(0);
            Date parse = b.parse(str, parsePosition);
            if (parsePosition.getIndex() == str.length()) {
                return parse.getTime();
            }
        } catch (Exception unused) {
        }
        return 0;
    }

    public static Entry a(Map<String, List<String>> map) {
        long j;
        long j2;
        Map<String, List<String>> map2 = map;
        long currentTimeMillis = System.currentTimeMillis();
        String a2 = cq.a(map2, (String) "Cache-Control");
        boolean z = true;
        int i = 0;
        if (a2 != null) {
            String[] split = a2.split(",");
            j = 0;
            while (true) {
                if (i >= split.length) {
                    break;
                }
                String trim = split[i].trim();
                if (trim.equals("no-store")) {
                    return null;
                }
                if (trim.equals("no-cache")) {
                    j = 0;
                    break;
                }
                if (trim.startsWith("max-age=")) {
                    try {
                        j = Long.parseLong(trim.substring(8));
                    } catch (Exception unused) {
                    }
                }
                i++;
            }
        } else {
            j = 0;
            z = false;
        }
        String a3 = cq.a(map2, (String) AutoDownloadLogRequest.AUTO_KEY_DATE);
        long a4 = a3 != null ? a(a3) : 0;
        String a5 = cq.a(map2, (String) "Expires");
        long a6 = a5 != null ? a(a5) : 0;
        String a7 = cq.a(map2, (String) "Last-Modified");
        long a8 = a7 != null ? a(a7) : 0;
        String a9 = cq.a(map2, (String) "ETag");
        if (z) {
            currentTimeMillis += j * 1000;
        } else if (a4 <= 0 || a6 < a4) {
            j2 = a8;
            if (j2 <= 0) {
                currentTimeMillis = 0;
            }
            if (currentTimeMillis != 0 && a9 == null) {
                return null;
            }
            Entry entry = new Entry();
            entry.etag = a9;
            entry.ttl = currentTimeMillis;
            entry.serverDate = a4;
            entry.lastModified = j2;
            entry.responseHeaders = map2;
            return entry;
        } else {
            currentTimeMillis += a6 - a4;
        }
        j2 = a8;
        if (currentTimeMillis != 0) {
        }
        Entry entry2 = new Entry();
        entry2.etag = a9;
        entry2.ttl = currentTimeMillis;
        entry2.serverDate = a4;
        entry2.lastModified = j2;
        entry2.responseHeaders = map2;
        return entry2;
    }
}
