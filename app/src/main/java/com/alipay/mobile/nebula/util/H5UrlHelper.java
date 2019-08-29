package com.alipay.mobile.nebula.util;

import android.net.Uri;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class H5UrlHelper {
    public static final String TAG = "H5UrlHelper";
    private static LruCache<String, String> sCachedPurifyUrlMap = new LruCache<>(20);
    private static LruCache<String, Uri> sCachedUriMap = new LruCache<>(20);

    public static String purifyUrl(String url) {
        String result = (String) sCachedPurifyUrlMap.get(url);
        if (result != null) {
            return result;
        }
        String result2 = url;
        try {
            int pageHashIndex = result2.indexOf(MetaRecord.LOG_SEPARATOR);
            if (pageHashIndex != -1) {
                result2 = result2.substring(0, pageHashIndex);
            }
            if (result2.contains("??")) {
                sCachedPurifyUrlMap.put(url, result2);
                return result2;
            }
            int queryParamIndex = result2.indexOf("?");
            if (queryParamIndex != -1) {
                result2 = result2.substring(0, queryParamIndex);
            }
            sCachedPurifyUrlMap.put(url, result2);
            return result2;
        } catch (Throwable th) {
            sCachedPurifyUrlMap.put(url, result2);
            return result2;
        }
    }

    public static String getScheme(String url) {
        Uri uri = parseUrl(url);
        if (uri != null) {
            return uri.getScheme();
        }
        return null;
    }

    public static Uri parseUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        Uri uri = (Uri) sCachedUriMap.get(url);
        if (uri != null) {
            return uri;
        }
        try {
            uri = Uri.parse(url);
            sCachedUriMap.put(url, uri);
            return uri;
        } catch (Exception e) {
            H5Log.e(TAG, "parse url exception.", e);
            return uri;
        }
    }

    public static String getHost(String url) {
        Uri uri = parseUrl(url);
        if (uri != null) {
            return uri.getHost();
        }
        return null;
    }

    public static String getPath(String url) {
        Uri uri = parseUrl(url);
        if (uri != null) {
            return uri.getPath();
        }
        return null;
    }

    public static String getParam(Uri uri, String key, String defaultValue) {
        if (uri == null) {
            return defaultValue;
        }
        String value = null;
        try {
            value = uri.getQueryParameter(key);
        } catch (Exception e) {
            H5Log.e(TAG, "Exception", e);
        }
        if (TextUtils.isEmpty(value)) {
            value = defaultValue;
        }
        return value;
    }

    public static final String encode(String url) {
        String encoded = null;
        try {
            return URLEncoder.encode(url, "utf-8");
        } catch (Exception e) {
            H5Log.e(TAG, "Exception", e);
            return encoded;
        }
    }

    public static final String decode(String url) {
        String decoded = null;
        try {
            return URLDecoder.decode(url, "utf-8");
        } catch (Exception e) {
            H5Log.e(TAG, "Exception", e);
            return decoded;
        }
    }

    public static String getOnlineHost(String url) {
        if (!TextUtils.isEmpty(url) && !url.startsWith("file:///") && !url.startsWith("data:")) {
            return getHost(url);
        }
        return null;
    }

    public static boolean isUrlInDomainArray(String url, String[] domainArray) {
        int i = 0;
        boolean isIn = false;
        if (TextUtils.isEmpty(url) || domainArray == null || domainArray.length == 0) {
            return false;
        }
        Uri uri = parseUrl(url);
        if (uri == null) {
            H5Log.d(TAG, "parse auto url failed!");
            return false;
        }
        String host = uri.getHost();
        if (TextUtils.isEmpty(host)) {
            H5Log.d(TAG, "can't get url host");
            return false;
        }
        String host2 = host.toLowerCase();
        try {
            int length = domainArray.length;
            while (true) {
                if (i >= length) {
                    break;
                }
                String domain = domainArray[i];
                if (domain.equalsIgnoreCase(host2) || host2.endsWith("." + domain)) {
                    isIn = true;
                } else {
                    i++;
                }
            }
            isIn = true;
        } catch (Exception e) {
            H5Log.e(TAG, "match auto login exception.", e);
        }
        return isIn;
    }

    public static String stripAnchor(String url) {
        int anchorIndex = url.indexOf(35);
        if (anchorIndex == -1) {
            return url;
        }
        String afterUrl = url.substring(0, anchorIndex);
        H5Log.d(TAG, "stripAnchor url:" + url + " afterUrl:" + afterUrl);
        return afterUrl;
    }
}
