package defpackage;

import android.net.Uri;
import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/* renamed from: agt reason: default package */
/* compiled from: URIUtil */
public final class agt {
    public static int a(Uri uri, String str, int i) {
        String queryParameter = uri.getQueryParameter(str);
        if (TextUtils.isEmpty(queryParameter)) {
            return i;
        }
        try {
            i = (int) Float.parseFloat(queryParameter);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static double a(Uri uri, String str) {
        double d;
        String queryParameter = uri.getQueryParameter(str);
        if (TextUtils.isEmpty(queryParameter)) {
            return -1.0d;
        }
        try {
            d = Double.parseDouble(queryParameter);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            d = -1.0d;
        }
        return d;
    }

    public static Map<String, String> a(String str, String str2) throws IllegalArgumentException {
        HashMap hashMap = new HashMap();
        try {
            String rawQuery = new URI(str).getRawQuery();
            if (rawQuery == null || rawQuery.isEmpty()) {
                return hashMap;
            }
            return b(rawQuery, str2);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static Map<String, String> b(String str, String str2) throws IllegalArgumentException {
        HashMap hashMap = new HashMap();
        Scanner scanner = new Scanner(str);
        scanner.useDelimiter("&");
        while (scanner.hasNext()) {
            String[] split = scanner.next().split("=");
            if (split.length > 0 && split.length <= 2) {
                String c = c(split[0], str2);
                String str3 = null;
                if (split.length == 2) {
                    str3 = c(split[1], str2);
                }
                hashMap.put(c, str3);
            }
        }
        return hashMap;
    }

    private static String c(String str, String str2) throws IllegalArgumentException {
        if (str2 == null) {
            str2 = "ISO-8859-1";
        }
        try {
            return URLDecoder.decode(str, str2);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
