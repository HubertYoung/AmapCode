package com.alipay.mobile.h5container.api;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.alipay.mobile.nebula.util.H5Log;
import java.util.HashMap;
import java.util.Set;

public class H5SsoFlagHolder {
    public static final String TAG = "H5SsoFlagHolder";
    public static boolean sCopyToMmstat = true;
    private static HashMap<String, Boolean> sLoginFlag = new HashMap<>();
    private static long sTbLoginTime = 0;

    public static void initFlag(Set<String> domains) {
        if (domains != null && !domains.isEmpty() && sLoginFlag.isEmpty()) {
            for (String domain : domains) {
                H5Log.d(TAG, "initFlag " + domain);
                sLoginFlag.put(domain, Boolean.valueOf(true));
            }
        }
    }

    public static boolean getFlag(String domainType) {
        boolean flag = false;
        if (sLoginFlag.containsKey(domainType)) {
            flag = sLoginFlag.get(domainType).booleanValue();
        }
        H5Log.d(TAG, "getFlag domain " + domainType + " flag " + flag);
        return flag;
    }

    public static void setFlag(String domainType, boolean flag) {
        if (!TextUtils.isEmpty(domainType)) {
            H5Log.d(TAG, "setFlag domain " + domainType + " flag " + flag);
            sLoginFlag.put(domainType, Boolean.valueOf(flag));
        }
    }

    public static void setNeedAutoLogin(boolean need) {
        H5Log.d(TAG, "setNeedAutoLogin:" + need);
        for (String key : sLoginFlag.keySet()) {
            sLoginFlag.put(key, Boolean.valueOf(need));
        }
        sTbLoginTime = 0;
    }

    public static long getTbLoginTime() {
        return sTbLoginTime;
    }

    public static void setTbLoginTime(long time) {
        H5Log.d(TAG, "setTbLoginTime " + time);
        sTbLoginTime = time;
    }

    public static boolean copyCookieToMmstat(Context context, boolean tabaoDomain, String cookie) {
        if (!tabaoDomain || !sCopyToMmstat || TextUtils.isEmpty(cookie)) {
            return false;
        }
        String[] cookieArray = cookie.split(";");
        if (cookieArray.length <= 0) {
            return false;
        }
        for (String trim : cookieArray) {
            String[] nameValuePair = trim.trim().split("=");
            if (nameValuePair.length == 2) {
                String cookieName = nameValuePair[0];
                String cookieValue = nameValuePair[1];
                if (TextUtils.equals(cookieName, "unb")) {
                    CookieManager manager = CookieManager.getInstance();
                    manager.setAcceptCookie(true);
                    manager.setCookie(".mmstat.com", "cnaui=" + cookieValue + ";");
                    CookieSyncManager.createInstance(context).sync();
                    return true;
                }
            }
        }
        return false;
    }
}
