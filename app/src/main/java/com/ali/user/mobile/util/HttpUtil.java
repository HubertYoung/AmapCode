package com.ali.user.mobile.util;

import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.ali.user.mobile.AliUserInit;
import com.ali.user.mobile.log.AliUserLog;

public class HttpUtil {
    public static void a(String str, String str2) {
        try {
            AliUserLog.c("AliuserLogin.HttpUtil", String.format("set cookie [url: %s, value: %s]", new Object[]{str, str2}));
            CookieSyncManager.createInstance(AliUserInit.b());
            CookieManager.getInstance().setCookie(str, str2);
            CookieSyncManager.getInstance().sync();
        } catch (Throwable th) {
            AliUserLog.b((String) "AliuserLogin.HttpUtil", th);
        }
    }
}
