package com.alipay.mobile.security.bio.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PreferenceHelper {
    public static final String KEY_FACEDETECT_SERVER_CONFIGS = "facedetect_server_configs";
    public static final String KEY_FACEDETECT_SERVER_CONFIGS_VERSION = "facedetect_server_configs_version";
    public static final String KEY_FACEDETECT_SOUND_ENABLE = "facedetect_sound_enable";
    private static Editor a = null;
    private static SharedPreferences b = null;

    private static Editor a(Context context) {
        if (a == null) {
            a = PreferenceManager.getDefaultSharedPreferences(context).edit();
        }
        return a;
    }

    private static SharedPreferences b(Context context) {
        if (b == null) {
            b = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return b;
    }

    public static String getValue(Context context, String str) {
        return b(context).getString(SignHelper.SHA1(str), "");
    }

    public static void setValue(Context context, String str, String str2) {
        a(context).putString(SignHelper.SHA1(str), str2).commit();
    }

    public static String getServerConfig(Context context) {
        return b(context).getString(SignHelper.SHA1(KEY_FACEDETECT_SERVER_CONFIGS), "");
    }

    public static void setServerConfig(Context context, String str) {
        a(context).putString(SignHelper.SHA1(KEY_FACEDETECT_SERVER_CONFIGS), str).commit();
    }

    public static boolean getSoundEnable(Context context) {
        return b(context).getBoolean(SignHelper.SHA1(KEY_FACEDETECT_SOUND_ENABLE), true);
    }

    public static void setSoundEnable(Context context, boolean z) {
        a(context).putBoolean(SignHelper.SHA1(KEY_FACEDETECT_SOUND_ENABLE), z);
    }

    public static String getServerConfigVersion(Context context) {
        return b(context).getString(SignHelper.SHA1(KEY_FACEDETECT_SERVER_CONFIGS_VERSION), "");
    }

    public static void setServerConfigVersion(Context context, String str) {
        a(context).putString(SignHelper.SHA1(KEY_FACEDETECT_SERVER_CONFIGS_VERSION), str).commit();
    }
}
