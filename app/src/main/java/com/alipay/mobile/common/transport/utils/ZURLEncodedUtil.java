package com.alipay.mobile.common.transport.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

public class ZURLEncodedUtil {
    public static final String TAG = "ZURLEncodedUtil";

    public static String urlEncode(String pUrlStr) {
        try {
            URL url = a(pUrlStr);
            try {
                return UrlFixer.fixUrl(url.toString());
            } catch (Throwable e) {
                LogCatUtil.info("ZURLEncodedUtil", "New URI(" + url.toString() + ") exception " + e.toString());
                return url.toString();
            }
        } catch (MalformedURLException e2) {
            LogCatUtil.warn((String) "ZURLEncodedUtil", "checkURL exception " + e2.toString());
            return pUrlStr;
        }
    }

    private static URL a(String localUrlStr) {
        try {
            return new URL(localUrlStr);
        } catch (Exception e) {
            LogCatUtil.warn((String) "ZURLEncodedUtil", "new URL(" + localUrlStr + ")  exception " + e.toString());
            localUrlStr = URLDecoder.decode(localUrlStr);
        } catch (Throwable e1) {
            LogCatUtil.warn((String) "ZURLEncodedUtil", "decode uri=" + localUrlStr + ", exception " + e1.toString());
        }
        return new URL(localUrlStr);
    }
}
