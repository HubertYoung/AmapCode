package com.taobao.applink.util;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TBAppLinkUtil {
    public static final String ACTION_CUSTOM = "com.taobao.open.intent.action.GETWAY";
    public static final String ACTION_JUMP = "ali.open.nav";
    public static final String APLUS_BASE_URL = "https://wgo.mmstat.com/%s";
    public static final String APLUS_H5_OPEN = "/minitrade.1.21.2";
    public static final String APLUS_NATIVE_OPEN = "/minitrade.1.21.1";
    public static final String AUTH_ACTION = "com.taobao.applink.ACTION_AUTH_SUCCESS";
    private static final int BASE_CONSTANT = 0;
    public static final String BASE_TB_VERSION = "5.2.0";
    public static final String BASE_URL = "tbopen://m.taobao.com/tbopen/index.html?";
    public static final String CLIENT_URL = "minitrade.1.200.1";
    public static final String CONFIG_BASE_URL_ONLINE = "https://nbsdk-baichuan.alicdn.com/2.0.0/applink.htm?plat=android&appKey=%s";
    public static final String CONFIG_BASE_URL_PRE = "http://100.69.205.47/100.0.0/applink.htm?plat=android&appKey=%s";
    public static final String CONFIG_URL = "minitrade.1.200.2";
    public static final String CRASH_URL = "minitrade.1.200.3";
    public static final String DOWNLOAD_TAOBAO_URL = "http://huodong.m.taobao.com/go/2085.html";
    public static final String GO_AUTH_H5URL = "http://oauth.m.taobao.com/authorize?response_type=code&client_id=%s&redirect_uri=%s&view=wap";
    public static final String GO_DETAIL_H5URL = "http://h5.m.taobao.com/awp/core/detail.htm?id=%s&";
    public static final String GO_SHOP_H5URL = "http://shop.m.taobao.com/shop/shopIndex.htm?shop_id=%s&";
    public static final int H5_OPEN = 1;
    public static final String METHOD = "method";
    public static final int NATIVE_OPEN = 2;
    public static final String NONE_CLIENT_URL = "minitrade.1.200.4";
    public static final String SDKVERSION = "2.0.0";
    public static final String START_SERVER_ACTION = "com.taobao.open.intent.action.AUTH";
    public static final String TAG = "AppLink";
    public static final String TAOBAO_SCHEME = "taobao_scheme";
    public static final String TAOPACKAGENAME = "com.taobao.taobao";
    public static final String TMALLPACKAGENAME = "com.tmall.wireless";
    public static final String TMALL_ACTON = "com.tmall.wireless.intent.action.APPLINK";
    public static final String TMALL_BASE_SCHEME_URL = "tmall://page.tm/appLink?";
    public static final String TMALL_SCHEME = "tmall_scheme";
    public static final String TMALL_START_SERVER_ACTION = "com.tmall.wireless.intent.action.AUTH";
    private static Application sApplication;

    public static synchronized Application getApplication() {
        Application application;
        synchronized (TBAppLinkUtil.class) {
            if (sApplication == null) {
                sApplication = getSystemApp();
            }
            application = sApplication;
        }
        return application;
    }

    public static String getClientUrl() {
        return String.format(APLUS_BASE_URL, new Object[]{CLIENT_URL});
    }

    public static String getConfigUrl() {
        return String.format(APLUS_BASE_URL, new Object[]{CONFIG_URL});
    }

    public static String getCrashUrl() {
        return String.format(APLUS_BASE_URL, new Object[]{CRASH_URL});
    }

    public static String getNoneClientUrl() {
        return String.format(APLUS_BASE_URL, new Object[]{NONE_CLIENT_URL});
    }

    public static String getPackageName(String str) {
        String str2 = TAOPACKAGENAME;
        if (TAOBAO_SCHEME.equals(str)) {
            return str2;
        }
        if (TMALL_SCHEME.equals(str)) {
            str2 = TMALLPACKAGENAME;
        }
        return str2;
    }

    public static String getServerAction(String str) {
        String str2 = START_SERVER_ACTION;
        if (TAOBAO_SCHEME.equals(str)) {
            return str2;
        }
        if (TMALL_SCHEME.equals(str)) {
            str2 = TMALL_START_SERVER_ACTION;
        }
        return str2;
    }

    private static Application getSystemApp() {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread", new Class[0]);
            Field declaredField = cls.getDeclaredField("mInitialApplication");
            declaredField.setAccessible(true);
            return (Application) declaredField.get(declaredMethod.invoke(null, new Object[0]));
        } catch (Exception unused) {
            return null;
        }
    }

    public static boolean isSupportAppLinkSDK(Context context, String str) {
        boolean isSupportTaobao = isSupportTaobao(context);
        if (TAOBAO_SCHEME.equals(str)) {
            return isSupportTaobao;
        }
        if (TMALL_SCHEME.equals(str)) {
            isSupportTaobao = isSupportTmall(context);
            if (!isSupportTaobao) {
                isSupportTaobao = isSupportTaobao(context);
            }
        }
        return isSupportTaobao;
    }

    private static boolean isSupportTaobao(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent();
        Uri parse = Uri.parse(BASE_URL);
        intent.setAction(ACTION_CUSTOM);
        intent.setData(parse);
        try {
            if (packageManager.getPackageInfo(TAOPACKAGENAME, 0) != null && packageManager.queryIntentActivities(intent, 65536).size() > 0) {
                return true;
            }
        } catch (Exception unused) {
        }
        return false;
    }

    private static boolean isSupportTmall(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent();
        intent.setAction(TMALL_ACTON);
        intent.setData(Uri.parse(TMALL_BASE_SCHEME_URL));
        try {
            if (packageManager.getPackageInfo(TMALLPACKAGENAME, 0) != null && packageManager.queryIntentActivities(intent, 65536).size() > 0) {
                return true;
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public static void setApplication(Context context) {
        if (sApplication == null) {
            sApplication = (Application) context.getApplicationContext();
        }
    }
}
