package com.alipay.mobile.securitycommon.aliauth.util;

import android.os.Build.VERSION;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.alipay.mobile.account.adapter.CommonAdapter;

public class CookieUtil {
    public static void deleteCookiesForDomain(String str) {
        CookieManager instance = CookieManager.getInstance();
        instance.setAcceptCookie(true);
        if (VERSION.SDK_INT < 11 && str.startsWith(".")) {
            str = str.substring(1);
        }
        String cookie = instance.getCookie(str);
        if (!TextUtils.isEmpty(cookie)) {
            String[] split = cookie.split(";");
            CommonAdapter.a();
            CookieSyncManager createInstance = CookieSyncManager.createInstance(CommonAdapter.b());
            for (String split2 : split) {
                String[] split3 = split2.split("=");
                StringBuilder sb = new StringBuilder();
                sb.append(split3[0]);
                sb.append("=; Expires=Wed, 31 Dec 2015 23:59:59 GMT");
                instance.setCookie(str, sb.toString());
                createInstance.sync();
            }
        }
    }
}
