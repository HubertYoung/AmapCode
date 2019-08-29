package com.taobao.accs.antibrush;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.client.GlobalConfig;
import com.taobao.accs.utl.ALog;
import java.util.StringTokenizer;

public class CookieMgr {
    public static final String KEY_SEC = "sec";
    public static final String TAG = "CookieMgr";
    private static volatile boolean isSetup = false;
    public static CookieManager webkitCookMgr;

    public static synchronized void setup(Context context) {
        synchronized (CookieMgr.class) {
            try {
                if (!GlobalConfig.enableCookie) {
                    ALog.i(TAG, "disable cookie", new Object[0]);
                } else if (!isSetup) {
                    if (VERSION.SDK_INT < 21) {
                        CookieSyncManager.createInstance(context);
                    }
                    CookieManager instance = CookieManager.getInstance();
                    webkitCookMgr = instance;
                    instance.setAcceptCookie(true);
                    if (VERSION.SDK_INT < 21) {
                        webkitCookMgr.removeExpiredCookie();
                    }
                    isSetup = true;
                }
            } catch (Throwable th) {
                ALog.e(TAG, "setup", th, new Object[0]);
            }
        }
    }

    private static boolean checkSetup() {
        if (!isSetup && GlobalClientInfo.mContext != null) {
            setup(GlobalClientInfo.mContext);
        }
        return isSetup;
    }

    public static synchronized String getCookieSec(String str) {
        String str2;
        synchronized (CookieMgr.class) {
            if (!checkSetup()) {
                ALog.e(TAG, "cookieMgr not setup", new Object[0]);
                return null;
            }
            try {
                str2 = parse(webkitCookMgr.getCookie(str));
            } catch (Throwable th) {
                ALog.e(TAG, "get cookie failed. url=".concat(String.valueOf(str)), th, new Object[0]);
                str2 = null;
            }
        }
        return str2;
    }

    public static String parse(String str) {
        String str2 = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(str, ";");
        do {
            try {
                String nextToken = stringTokenizer.nextToken();
                int indexOf = nextToken.indexOf(61);
                if (indexOf != -1) {
                    String trim = nextToken.substring(0, indexOf).trim();
                    String trim2 = nextToken.substring(indexOf + 1).trim();
                    if (KEY_SEC.equals(trim)) {
                        str2 = stripOffSurroundingQuote(trim2);
                    }
                } else {
                    throw new IllegalArgumentException("Invalid cookie name-value pair");
                }
            } catch (Throwable th) {
                ALog.e(TAG, "parse", th, new Object[0]);
            }
        } while (stringTokenizer.hasMoreTokens());
        return str2;
    }

    private static String stripOffSurroundingQuote(String str) {
        if (str == null || str.length() <= 2 || str.charAt(0) != '\"' || str.charAt(str.length() - 1) != '\"') {
            return (str == null || str.length() <= 2 || str.charAt(0) != '\'' || str.charAt(str.length() - 1) != '\'') ? str : str.substring(1, str.length() - 1);
        }
        return str.substring(1, str.length() - 1);
    }
}
