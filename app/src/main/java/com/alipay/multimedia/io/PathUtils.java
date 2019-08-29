package com.alipay.multimedia.io;

import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.util.Locale;

public class PathUtils {
    public static boolean isFile(String uri) {
        if (uri == null) {
            return false;
        }
        String uri2 = uri.trim();
        if (uri2.startsWith("/") || uri2.toLowerCase(Locale.US).startsWith("file://")) {
            return true;
        }
        return false;
    }

    public static boolean isHttpUrl(String uri) {
        return isHttp(uri) || isHttps(uri);
    }

    public static boolean isHttp(String uri) {
        if (uri != null) {
            return uri.trim().toLowerCase(Locale.US).startsWith(AjxHttpLoader.DOMAIN_HTTP);
        }
        return false;
    }

    public static boolean isHttps(String uri) {
        if (uri != null) {
            return uri.trim().toLowerCase(Locale.US).startsWith(AjxHttpLoader.DOMAIN_HTTPS);
        }
        return false;
    }

    public static String trimFilePath(String uri) {
        if (uri == null) {
            return uri;
        }
        String uri2 = uri.trim();
        if (uri2.startsWith("file://")) {
            return uri2.substring(7);
        }
        return uri2;
    }
}
