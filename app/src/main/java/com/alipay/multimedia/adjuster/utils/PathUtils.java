package com.alipay.multimedia.adjuster.utils;

import android.net.Uri;
import android.text.TextUtils;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import java.io.File;

public class PathUtils {
    private static final String ASSET_PATH_FLAG = (File.separator + "[asset]" + File.separator);
    public static final String CONTENT_SCHEMA = "content://";

    public static String extractPath(String path) {
        if (TextUtils.isEmpty(path)) {
            return path;
        }
        switch (Scheme.ofUri(path)) {
            case FILE:
                String path2 = Scheme.FILE.crop(path);
                if (TextUtils.isEmpty(path2) || !path2.startsWith(ASSET_PATH_FLAG)) {
                    return path2;
                }
                return path2.substring(ASSET_PATH_FLAG.length());
            case HTTP:
            case HTTPS:
                return urlEncode(path);
            default:
                return path;
        }
    }

    private static String urlEncode(String url) {
        return ZURLEncodedUtil.urlEncode(url);
    }

    public static boolean isLocalFile(String in) {
        return (!TextUtils.isEmpty(in) && (in.startsWith("/") || isLocalFile(Uri.parse(in)))) || in.startsWith(BehavorReporter.PROVIDE_BY_LOCAL);
    }

    public static boolean isLocalFile(Uri uri) {
        if (uri != null && "file".equalsIgnoreCase(uri.getScheme()) && !hasHost(uri)) {
            return true;
        }
        return false;
    }

    public static boolean hasHost(Uri uri) {
        String host = uri.getHost();
        return host != null && !"".equals(host);
    }

    public static boolean isContentUriPath(String uri) {
        if (!TextUtils.isEmpty(uri)) {
            return uri.startsWith("content://");
        }
        return false;
    }
}
