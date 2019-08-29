package com.uc.webview.export;

import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.uc.webview.export.annotations.Api;

@Api
/* compiled from: ProGuard */
public class Build {
    public static String CORE_TIME = a((String) "190606185002", (String) "");
    public static String CORE_VERSION = a((String) "3.10.46.0", (String) "");
    public static String CPU_ARCH = a((String) "armeabi-v7a", (String) "armv7-a");
    public static boolean IS_INTERNATIONAL_VERSION = false;
    public static int PACK_TYPE = a((String) "@USE_KERNEL_TYPE@", 4);
    public static String SDK_BMODE = a((String) "@WEBVIEW_SDK_BMODE@", (String) "WWW");
    public static String SDK_BTYPE = a((String) "@WEBVIEW_SDK_BTYPE@", (String) "UC");
    public static String SDK_FR = a((String) "@WEBVIEW_SDK_FR@", (String) "android");
    public static String SDK_LANG = a((String) "@WEBVIEW_SDK_LANG@", (String) "zh-CN");
    public static String SDK_PRD = a((String) "@WEBVIEW_SDK_PRD@", (String) "uc_webview_pro");
    public static String SDK_PROFILE_ID = a((String) "@WEBVIEW_SDK_PFID@", (String) "");
    public static String SDK_SUBVER = a((String) "@WEBVIEW_SDK_SUBVER@", (String) "release");
    public static String TIME = a((String) "190606185002", (String) "");
    public static String TYPE = a((String) "@WEBVIEW_SDK_TYPE@", (String) "");
    public static String UCM_SUPPORT_SDK_MIN = "";
    public static String UCM_VERSION = a((String) "11.8.8.968", (String) "");

    @Api
    /* compiled from: ProGuard */
    public static class Version {
        public static int API_LEVEL = 16;
        public static int BUILD_SERIAL = Build.a((String) "2", 2);
        public static int MAJOR = Build.a((String) "2", 2);
        public static int MINOR = Build.a((String) "13", 13);
        public static String NAME = null;
        public static int PATCH = Build.a((String) "87", 0);
        public static String SUPPORT_U4_MIN = Build.a((String) "@WEBVIEW_SDK_SUPPORT_U4_MIN@", (String) "2.13.1.0");
        public static String SUPPORT_UCM_MIN = "99999.99999.99999.99999";

        static {
            StringBuilder sb = new StringBuilder();
            sb.append(MAJOR);
            sb.append(".");
            sb.append(MINOR);
            sb.append(".");
            sb.append(BUILD_SERIAL);
            sb.append(".");
            sb.append(PATCH);
            NAME = sb.toString();
        }
    }

    static int a(String str, int i) {
        if (str.startsWith(AUScreenAdaptTool.PREFIX_ID)) {
            return i;
        }
        return Integer.parseInt(str);
    }

    static String a(String str, String str2) {
        return str.startsWith(AUScreenAdaptTool.PREFIX_ID) ? str2 : str;
    }
}
