package com.alipay.mobile.nebula.dev;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5DevConfig {
    public static final String BATCHED_SERVER = "https://hpmweb.alipay.com/report/android/batch";
    public static final String DEFAULT_SERVER = "https://hpmweb.alipay.com/report/android";
    public static final String H5_ALIPAY_NETWORK = "h5_alipay_network";
    public static final String H5_BUG_ME_DEBUG_SWITCH = "h5_bug_me_debug_switch";
    public static final String H5_BUG_ME_DEBUG_SWITCH_KEEP = "h5_bug_me_debug_switch_keep";
    public static final String H5_BUG_ME_DOM_DEBUG = "h5_bug_me_dom_debug";
    public static final String H5_BUG_ME_FORCE_NO_DUMP = "h5_bug_me_force_no_dump";
    public static final String H5_BUG_ME_JS_INJECTOR = "h5_bug_me_js_injector";
    public static final String H5_BUG_ME_SHOW_ICON = "h5_bug_me_show_icon";
    public static final String H5_BUG_ME_SUPER_USER = "h5_bug_me_super_user";
    public static final String H5_BUG_ME_WIRED_DEBUG = "h5_bug_me_wired_debug";
    public static final String H5_DELETE_UNUSED_APP_PACKAGE = "h5_delete_unused_app_package";
    public static String H5_DEV_URL = null;
    public static final String H5_JSAPI_PERMISSION = "h5_jsapi_permission";
    public static final String H5_LAUNCH_URL = "h5_param_url";
    public static String H5_LOAD_JS = "";
    public static final String H5_POST_EVENT = "h5_post_event";
    public static final String H5_PREFER_APP_LIST = "h5_prefer_app_list";
    public static final String H5_READ_SNAPSHOT = "h5_read_snapshot";
    public static final String H5_READ_USE_WEBVIEW_CONFIG = "h5_read_use_webview_config";
    public static final String H5_TRACE_DEBUG_SWITCH = "h5_trace_debug_switch";
    public static final String H5_UPLOAD_ALL_APP_INFO = "h5_upload_all_app_info";
    public static final String H5_USE_PRESET_PKG_INFO = "h5_use_preset_pkg_info";
    public static final String H5_USE_UC_WEBVIEW = "h5_use_uc_webview";
    private static final String TAG = "H5DevConfig";
    public static final String h5_not_use_tiny_permission = "h5_not_use_tiny_permission";
    public static final String h5_read_use_dev_app_config = "h5_read_use_dev_app_config";
    public static final String h5_read_use_dev_db = "h5_read_use_dev_db";

    public static boolean getBooleanConfig(String key, boolean df) {
        try {
            if (H5Utils.getContext() == null) {
                return df;
            }
            return PreferenceManager.getDefaultSharedPreferences(H5Utils.getContext()).getBoolean(key, df);
        } catch (Throwable th) {
            return df;
        }
    }

    public static String getStringConfig(String key, String df) {
        try {
            if (H5Utils.getContext() == null) {
                return df;
            }
            return PreferenceManager.getDefaultSharedPreferences(H5Utils.getContext()).getString(key, df);
        } catch (Throwable th) {
            return df;
        }
    }

    public static void setStringConfig(String key, String value) {
        if (H5Utils.getContext() == null) {
            H5Log.e((String) TAG, (String) "h5DevGlobal.getContext is null");
        } else {
            PreferenceManager.getDefaultSharedPreferences(H5Utils.getContext()).edit().putString(key, value).apply();
        }
    }

    public static void setBooleanConfig(String key, boolean value) {
        if (H5Utils.getContext() == null) {
            H5Log.e((String) TAG, (String) "h5DevGlobal.getContext is null");
        } else {
            PreferenceManager.getDefaultSharedPreferences(H5Utils.getContext()).edit().putBoolean(key, value).apply();
        }
    }

    public static void debugSwitch(boolean debugSwitch, boolean domDebug, boolean wiredDebug, boolean showIcon, boolean performanceTrace) {
        if (H5Utils.getContext() == null) {
            H5Log.e((String) TAG, (String) "h5DevGlobal.getContext is null");
            return;
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(H5Utils.getContext());
        H5Log.d(TAG, "debugSwitch:" + debugSwitch);
        boolean previewsSwitch = sp.getBoolean(H5_BUG_ME_DEBUG_SWITCH, false);
        sp.edit().putBoolean(H5_BUG_ME_DEBUG_SWITCH, debugSwitch).putBoolean(H5_BUG_ME_DOM_DEBUG, domDebug).putBoolean(H5_BUG_ME_WIRED_DEBUG, wiredDebug).putBoolean(H5_BUG_ME_SHOW_ICON, showIcon).putBoolean(H5_TRACE_DEBUG_SWITCH, performanceTrace).apply();
        if (previewsSwitch != debugSwitch) {
            H5Service service = H5ServiceUtils.getH5Service();
            if (service != null) {
                service.getBugMeManager().onBugMeSwitched(debugSwitch);
            }
        }
    }

    public static void resetBugMeSettings() {
        if (H5Utils.getContext() == null) {
            H5Log.e((String) TAG, (String) "h5DevGlobal.getContext is null");
            return;
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(H5Utils.getContext());
        H5Log.d(TAG, "resetBugMeSettings");
        boolean previewsSwitch = sp.getBoolean(H5_BUG_ME_DEBUG_SWITCH, false);
        sp.edit().putBoolean(H5_BUG_ME_DEBUG_SWITCH, false).putBoolean(H5_BUG_ME_DOM_DEBUG, false).putBoolean(H5_BUG_ME_SHOW_ICON, false).putBoolean(H5_BUG_ME_WIRED_DEBUG, false).putBoolean(H5_BUG_ME_DEBUG_SWITCH_KEEP, false).putBoolean(H5_BUG_ME_SUPER_USER, false).apply();
        if (previewsSwitch) {
            H5Service service = H5ServiceUtils.getH5Service();
            if (service != null) {
                service.getBugMeManager().onBugMeSwitched(false);
            }
        }
    }
}
