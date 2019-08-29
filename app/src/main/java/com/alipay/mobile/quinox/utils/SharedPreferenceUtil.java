package com.alipay.mobile.quinox.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SharedPreferenceUtil {
    public static final String CONFIG_KEY_CUBE_SAMPLE = "cube_sample";
    public static final String CONFIG_KEY_DO_DEXPATCH_FUSE = "dexp_do_fuse";
    public static final String CONFIG_KEY_ENABLE_MTK_PPS_SDK = "enable_mtk_pps_sdk";
    public static final String CONFIG_KEY_INSTANT_START_APP = "ig_instantStartApp";
    public static final String CONFIG_KEY_INSTANT_START_APP_SOURCE_APPID = "ig_instantStartAppSourceAppid";
    public static final String CONFIG_KEY_IS_DELAY_DYNAMIC_RELEASE = "ig_isDelayDR";
    public static final String CONFIG_KEY_IS_POST_IF_MAINLOOP = "ig_isPostIfMain";
    public static final String CONFIG_KEY_LOADING_PEND_TIMEOUT = "ig_loadingPendTimeout";
    public static final String CONFIG_KEY_PRELAUNCH_PRELOAD = "prelaunch_preload";
    public static final String CONFIG_KEY_PRELAUNCH_PRELOAD2 = "prelaunch_preload2";
    public static final String CONFIG_KEY_PROFILO_CONFIG = "profilo_config";
    public static final String CONFIG_KEY_USE_MTK_PPS_SDK = "use_mtk_pps_sdk";
    public static SharedPreferences defaultSp;
    private static SharedPreferenceUtil sInstance;
    private SharedPreferences mDefaultSharedPreferences;
    private Map<String, SharedPreferences> mPrivateSharedPreferencesMap = new ConcurrentHashMap();

    public static SharedPreferenceUtil getInstance() {
        if (sInstance == null) {
            synchronized (SharedPreferenceUtil.class) {
                if (sInstance == null) {
                    sInstance = new SharedPreferenceUtil();
                }
            }
        }
        return sInstance;
    }

    private SharedPreferenceUtil() {
    }

    public SharedPreferences getDefaultSharedPreference(@NonNull Context context) {
        if (this.mDefaultSharedPreferences == null) {
            this.mDefaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return this.mDefaultSharedPreferences;
    }

    public SharedPreferences getSharedPreferences(@NonNull Context context, @NonNull String str, int i) {
        if (i != 0) {
            throw new RuntimeException("Only Support Context.MODE_PRIVATE!");
        }
        SharedPreferences sharedPreferences = this.mPrivateSharedPreferencesMap.get(str);
        if (sharedPreferences == null) {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext != null) {
                context = applicationContext;
            }
            sharedPreferences = context.getSharedPreferences(str, i);
            if (sharedPreferences != null) {
                this.mPrivateSharedPreferencesMap.put(str, sharedPreferences);
            }
        }
        return sharedPreferences;
    }

    public SharedPreferences getSharedPreferencesBottom(@NonNull Context context, @NonNull String str, int i) {
        if (i != 0) {
            throw new RuntimeException("Only Support Context.MODE_PRIVATE!");
        }
        SharedPreferences sharedPreferences = this.mPrivateSharedPreferencesMap.get(str);
        if (sharedPreferences == null) {
            getDefaultSharedPreference(context).getBoolean("quinox_sp_replace", false);
            sharedPreferences = context.getSharedPreferences(str, i);
            if (sharedPreferences != null) {
                this.mPrivateSharedPreferencesMap.put(str, sharedPreferences);
            }
        }
        return sharedPreferences;
    }

    public void removeSharedPreferenceCache(String str) {
        this.mPrivateSharedPreferencesMap.remove(str);
    }

    public String[] getSyncConfigKeys() {
        return new String[]{CONFIG_KEY_INSTANT_START_APP, CONFIG_KEY_LOADING_PEND_TIMEOUT, CONFIG_KEY_IS_POST_IF_MAINLOOP, CONFIG_KEY_IS_DELAY_DYNAMIC_RELEASE, CONFIG_KEY_INSTANT_START_APP_SOURCE_APPID, CONFIG_KEY_DO_DEXPATCH_FUSE, CONFIG_KEY_ENABLE_MTK_PPS_SDK, CONFIG_KEY_USE_MTK_PPS_SDK, CONFIG_KEY_PROFILO_CONFIG, CONFIG_KEY_CUBE_SAMPLE, CONFIG_KEY_PRELAUNCH_PRELOAD, CONFIG_KEY_PRELAUNCH_PRELOAD2};
    }
}
