package com.alipay.mobile.nebula.util;

import android.net.Uri;
import com.alibaba.fastjson.JSONArray;
import java.util.List;

public class H5DomainUtil {
    private static final String TAG = "H5DomainUtil";

    public static String getNewDomainSuffix(String url) {
        Uri uri = H5UrlHelper.parseUrl(url);
        if (uri != null) {
            return uri.getScheme() + "://" + uri.getHost();
        }
        return null;
    }

    public static boolean isSomeDomainInternal(String domainSuffix, String regexArray) {
        if (domainSuffix == null) {
            return false;
        }
        try {
            JSONArray domainArray = H5Utils.parseArray(regexArray);
            if (domainArray == null) {
                return false;
            }
            for (int i = 0; i < domainArray.size(); i++) {
                if (H5PatternHelper.matchRegex(domainArray.getString(i), domainSuffix)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable t) {
            H5Log.e(TAG, "exception detail.", t);
            return false;
        }
    }

    public static boolean isInDomain(String domainSuffix, List<String> regexArray) {
        if (domainSuffix == null) {
            return false;
        }
        if (regexArray != null) {
            try {
                if (!regexArray.isEmpty()) {
                    for (String matchRegex : regexArray) {
                        if (H5PatternHelper.matchRegex(matchRegex, domainSuffix)) {
                            return true;
                        }
                    }
                }
            } catch (Throwable t) {
                H5Log.e(TAG, "exception detail.", t);
            }
        }
        return false;
    }
}
