package com.alipay.mobile.tinyappcommon.utils.net;

import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alipay.mobile.nebula.util.H5CookieUtil;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.api.TinyAppMixActionService;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import com.alipay.mobile.tinyappcommon.utils.TinyAppParamUtils;

public final class TinyAppCookieUtils {
    public static String getCookie(Bundle startParams, String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        if (H5Utils.getBoolean(startParams, (String) "isTinyApp", false)) {
            return H5CookieUtil.getCookie(partCookieUrlToAppDomain(startParams, url));
        }
        return H5CookieUtil.getCookie(url);
    }

    public static void setCookie(Bundle startParams, String url, String cookieValue) {
        if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(cookieValue)) {
            if (H5Utils.getBoolean(startParams, (String) "isTinyApp", false)) {
                H5CookieUtil.setCookie(partCookieUrlToAppDomain(startParams, url), partCookieValueToAppDomain(startParams, cookieValue));
            } else {
                H5CookieUtil.setCookie(url, cookieValue);
            }
        }
    }

    private static String partCookieUrlToAppDomain(Bundle startParams, String url) {
        String appId = TinyAppParamUtils.getAppId(startParams);
        if (TextUtils.isEmpty(appId) || !shouldCookiePart(appId)) {
            return url;
        }
        int schemaIndex = url.indexOf("://");
        if (schemaIndex == -1) {
            return url;
        }
        int portIndex = url.indexOf(":", schemaIndex + 3);
        int domainIndex = url.indexOf("/", schemaIndex + 3);
        if (domainIndex == -1) {
            return url + "." + appId;
        }
        if (portIndex == -1 || portIndex >= domainIndex) {
            return url.substring(0, domainIndex) + "." + appId + url.substring(domainIndex);
        }
        return url.substring(0, portIndex) + "." + appId + url.substring(portIndex);
    }

    private static String partCookieValueToAppDomain(Bundle startParams, String cookieValue) {
        String appId = TinyAppParamUtils.getAppId(startParams);
        if (TextUtils.isEmpty(appId) || !shouldCookiePart(appId)) {
            return cookieValue;
        }
        Point domainPosition = getCookieDomainValuePosition(cookieValue);
        if (domainPosition.x < 0 || domainPosition.x > domainPosition.y || domainPosition.y >= cookieValue.length()) {
            return cookieValue;
        }
        return cookieValue.substring(0, domainPosition.x) + (cookieValue.substring(domainPosition.x, domainPosition.y + 1) + "." + appId) + cookieValue.substring(domainPosition.y + 1);
    }

    private static Point getCookieDomainValuePosition(String cookieValue) {
        Point position = new Point(-1, -1);
        if (!TextUtils.isEmpty(cookieValue)) {
            int indexOfDomain = cookieValue.indexOf("Domain=");
            if (indexOfDomain == -1) {
                indexOfDomain = cookieValue.indexOf("domain=");
            }
            if (indexOfDomain != -1) {
                position.x = indexOfDomain + 8;
                int endOfDomain = cookieValue.indexOf(";", position.x);
                if (endOfDomain == -1) {
                    position.y = cookieValue.length() - 1;
                } else {
                    position.y = endOfDomain - 1;
                }
                if (position.x > position.y) {
                    position.y = -1;
                    position.x = -1;
                }
            }
        }
        return position;
    }

    private static boolean shouldCookiePart(String appId) {
        TinyAppMixActionService service = TinyAppService.get().getMixActionService();
        if (service == null) {
            return true;
        }
        JSONArray whiteList = service.getCookiePartWhiteList();
        if (whiteList == null) {
            return true;
        }
        if (whiteList.contains("all") || whiteList.contains(appId)) {
            return false;
        }
        return true;
    }
}
