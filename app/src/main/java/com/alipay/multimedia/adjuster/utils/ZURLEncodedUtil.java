package com.alipay.multimedia.adjuster.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

public class ZURLEncodedUtil {
    public static final String TAG = "ZURLEncodedUtil";
    private static final Log logger = Log.getLogger((String) "ZURLEncodedUtil");

    public static String urlEncode(String pUrlStr) {
        String localUrlStr;
        try {
            URL url = getURL(pUrlStr);
            try {
                return UrlFixer.fixUrl(url.toString());
            } catch (Throwable e) {
                if (url != null) {
                    localUrlStr = url.toString();
                } else {
                    localUrlStr = pUrlStr;
                }
                logger.i("New URI(" + localUrlStr + ") exception " + e.toString(), new Object[0]);
                return url.toString();
            }
        } catch (MalformedURLException e2) {
            logger.w("checkURL exception " + e2.toString(), new Object[0]);
            return pUrlStr;
        }
    }

    private static URL getURL(String localUrlStr) {
        try {
            return new URL(localUrlStr);
        } catch (Exception e) {
            logger.w("new URL(" + localUrlStr + ")  exception " + e.toString(), new Object[0]);
            localUrlStr = URLDecoder.decode(localUrlStr);
        } catch (Throwable e1) {
            logger.w("decode uri=" + localUrlStr + ", exception " + e1.toString(), new Object[0]);
        }
        return new URL(localUrlStr);
    }
}
