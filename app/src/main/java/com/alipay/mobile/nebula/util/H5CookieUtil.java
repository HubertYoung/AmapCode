package com.alipay.mobile.nebula.util;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.alipay.mobile.nebula.provider.H5TinyAppProvider;

public class H5CookieUtil {
    public static String getCookie(String url) {
        try {
            CookieManager cookieManager = CookieManager.getInstance();
            String cookieStr = cookieManager.getCookie(url);
            if (!TextUtils.isEmpty(cookieStr)) {
                return cookieStr;
            }
            Uri uri = H5UrlHelper.parseUrl(url);
            String domain = null;
            if (uri != null) {
                domain = uri.getHost();
            }
            if (uri == null || TextUtils.isEmpty(domain)) {
                return cookieStr;
            }
            return cookieManager.getCookie(domain);
        } catch (Throwable throwable) {
            H5Log.e((String) "H5CookieUtil", throwable);
            return null;
        }
    }

    public static void setCookie(String url, String cookieValue) {
        try {
            CookieManager manager = CookieManager.getInstance();
            manager.setAcceptCookie(true);
            manager.setCookie(url, cookieValue);
            CookieSyncManager.createInstance(H5Utils.getContext()).sync();
        } catch (Throwable throwable) {
            H5Log.e((String) "H5CookieUtil", throwable);
        }
    }

    public static String getCookie(Bundle startParams, String url) {
        if (!H5Utils.getBoolean(startParams, (String) "isTinyApp", false)) {
            return getCookie(url);
        }
        H5TinyAppProvider provider = (H5TinyAppProvider) H5Utils.getProvider(H5TinyAppProvider.class.getName());
        if (provider != null) {
            return provider.getCookie(startParams, url);
        }
        return getCookie(url);
    }

    public static void setCookie(Bundle startParams, String url, String cookieValue) {
        if (H5Utils.getBoolean(startParams, (String) "isTinyApp", false)) {
            H5TinyAppProvider provider = (H5TinyAppProvider) H5Utils.getProvider(H5TinyAppProvider.class.getName());
            if (provider != null) {
                provider.setCookie(startParams, url, cookieValue);
            } else {
                setCookie(url, cookieValue);
            }
        } else {
            setCookie(url, cookieValue);
        }
    }
}
