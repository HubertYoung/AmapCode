package com.alipay.mobile.common.transport.http;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public final class GwCookieCacheHelper {
    public static final String TAG = "GwCookieCacheHelper";
    public static final Map<String, Map<String, String>> cookieCacheMaps = new LinkedHashMap(4);

    public static final synchronized void setCookies(String domain, Map<String, String> map) {
        synchronized (GwCookieCacheHelper.class) {
            try {
                if (!TextUtils.isEmpty(domain) && !map.isEmpty()) {
                    getCookieMapByDomain(domain).putAll(map);
                }
            } catch (Throwable ex) {
                LogCatUtil.error((String) TAG, "setCookies ex:" + ex.toString());
            }
        }
        return;
    }

    public static final Map<String, String> getCookieMapByDomain(String domain) {
        if (TextUtils.isEmpty(domain)) {
            LogCatUtil.warn((String) TAG, (String) "[getCookieMapByDomain] domain is empty.");
            return null;
        }
        Map oldMap = cookieCacheMaps.get(domain);
        if (oldMap != null) {
            return oldMap;
        }
        Map oldMap2 = new LinkedHashMap();
        cookieCacheMaps.put(domain, oldMap2);
        return oldMap2;
    }

    public static final String getCookieWrapper(Context context, String url, TransportContext transportContext) {
        String localCookie;
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        if (context == null) {
            return "";
        }
        boolean isLoadLocalCookies = false;
        if (TransportConfigureManager.getInstance().equalsString(TransportConfigureItem.COOKIE_CACHE_SWITCH, "T") && transportContext != null && transportContext.bizType == 1) {
            String cacheCookie = getCookie(MiscUtils.getTopDomain(url));
            if (!TextUtils.isEmpty(cacheCookie)) {
                LogCatUtil.info(TAG, "[getCookieWrapper] Use old cache cookie. add cookie=[" + cacheCookie + "]. url=" + url);
                return cacheCookie;
            }
            isLoadLocalCookies = true;
        }
        if (!MiscUtils.isInAlipayClient(context) || !MiscUtils.isPushProcess(context)) {
            if (isLoadLocalCookies) {
                synchronized (GwCookieCacheHelper.class) {
                    try {
                        String cacheCookie2 = getCookie(MiscUtils.getTopDomain(url));
                        if (!TextUtils.isEmpty(cacheCookie2)) {
                            LogCatUtil.info(TAG, "[getCookieWrapper] Use new cache cookie. add cookie=[" + cacheCookie2 + "]. url=" + url);
                            return cacheCookie2;
                        }
                        localCookie = CookieAccessHelper.getCookie(url, context);
                        if (TextUtils.isEmpty(localCookie)) {
                            return "";
                        }
                        cookieStringToMap(localCookie, getCookieMapByDomain(url));
                        LogCatUtil.info(TAG, "[getCookieWrapper] Cookie Cache load finish.");
                    }
                }
            } else {
                localCookie = CookieAccessHelper.getCookie(url, context);
                if (TextUtils.isEmpty(localCookie)) {
                    return "";
                }
            }
            LogCatUtil.debug(TAG, "[getCookieWrapper] Use cookie manager. add cookie=[" + localCookie + "] . url=" + url);
            return localCookie;
        }
        LogCatUtil.info(TAG, "[getCookieWrapper] Another process is not operating a cookie.");
        return "";
    }

    public static final synchronized String getCookie(String domain) {
        String str;
        synchronized (GwCookieCacheHelper.class) {
            try {
                if (cookieCacheMaps.isEmpty() || cookieCacheMaps.get(domain) == null) {
                    str = "";
                } else if (cookieCacheMaps.get(domain).isEmpty()) {
                    str = "";
                } else {
                    str = toCookieString(cookieCacheMaps.get(domain));
                }
            } catch (Throwable ex) {
                LogCatUtil.error((String) TAG, "getCookie ex:" + ex.toString());
                str = "";
            }
        }
        return str;
    }

    public static final String toCookieString(Map<String, String> cookieMap) {
        StringBuilder cookieString = new StringBuilder();
        for (Entry cookieEntry : cookieMap.entrySet()) {
            cookieString.append((String) cookieEntry.getKey()).append("=").append((String) cookieEntry.getValue()).append("; ");
        }
        return cookieString.deleteCharAt(cookieString.length() - 2).toString().trim();
    }

    public static final synchronized void removeAllCookie() {
        synchronized (GwCookieCacheHelper.class) {
            try {
                cookieCacheMaps.clear();
            } catch (Throwable e) {
                LogCatUtil.error((String) TAG, "removeAllCookie ex: " + e.toString());
            }
        }
        return;
    }

    public static void cookieStringToMap(String localCookie, Map<String, String> map) {
        if (TextUtils.isEmpty(localCookie)) {
            LogCatUtil.warn((String) TAG, (String) "[cookieStringToMap] localCookie is empty.");
        } else if (map == null) {
            LogCatUtil.warn((String) TAG, (String) "[cookieStringToMap] map is null.");
        } else {
            StringTokenizer stringTokenizer = new StringTokenizer(localCookie, ";");
            while (stringTokenizer.hasMoreTokens()) {
                String kvPairStr = stringTokenizer.nextToken().trim();
                if (TextUtils.isEmpty(kvPairStr)) {
                    LogCatUtil.warn((String) TAG, (String) "[cookieStringToMap] kvPairStr is empty.");
                } else {
                    int firstEqualSymbolIndex = kvPairStr.indexOf("=", 0);
                    if (firstEqualSymbolIndex < 0) {
                        LogCatUtil.warn((String) TAG, (String) "[cookieStringToMap] Not find '=' symbol.");
                    } else {
                        String key = kvPairStr.substring(0, firstEqualSymbolIndex);
                        int valueStartIndex = firstEqualSymbolIndex + 1;
                        String value = "";
                        if (kvPairStr.length() - valueStartIndex > 0) {
                            value = kvPairStr.substring(valueStartIndex);
                        }
                        map.put(key, value);
                    }
                }
            }
        }
    }
}
