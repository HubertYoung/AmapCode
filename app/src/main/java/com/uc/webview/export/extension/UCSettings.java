package com.uc.webview.export.extension;

import com.uc.webview.export.annotations.Api;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IGlobalSettings;
import com.uc.webview.export.internal.interfaces.InvokeObject;
import com.uc.webview.export.internal.interfaces.UCMobileWebKit;
import com.uc.webview.export.internal.utility.Log;
import java.util.HashSet;
import java.util.Set;

@Api
/* compiled from: ProGuard */
public abstract class UCSettings implements InvokeObject {
    public static final String CDKEY_MAX_REQ_PER_CLIENT = "max_req_per_client";
    public static final String CDKEY_MAX_REQ_PER_HOST = "max_req_per_host";
    public static final String CD_RESOURCE_DISABLE_SW_SCRIPTCACHE_LIST = "crwp_disable_sw_scriptcache_list";
    public static final String CD_RESOURCE_ENABLE_IMG_ERROR_INFO = "enable_img_error_info";
    public static final String CD_RESOURCE_FOCUS_AUTO_POPUP_INPUT_WHITELIST = "u4_focus_auto_popup_input_list";
    public static final String CD_RESOURCE_HYBRID_RENDER_EMBED_VIEW_ENABLE_LIST = "crwp_hybrid_render_embed_view_enable_list";
    public static final String CD_RESOURCE_STAT_FILTER_LIST = "stat_filter_list";
    public static int FORCE_USER_SCALABLE_DEFAULT = 0;
    public static int FORCE_USER_SCALABLE_DISABLE = 2;
    public static int FORCE_USER_SCALABLE_ENABLE = 1;
    public static final int FORM_SAVE_TYPE_AUTO = 1;
    public static final int FORM_SAVE_TYPE_NO = 2;
    public static final int FORM_SAVE_TYPE_PROMPT = 0;
    public static final int IMAGE_QUALITY_FULL_COLOR = 3;
    public static final int IMAGE_QUALITY_LOW_COLOR = 1;
    public static final int IMAGE_QUALITY_NO_IMAGE = 0;
    public static final int IMAGE_QUALITY_STANDARD = 2;
    public static final String KEY_ADBLOCK_WHITE_LIST = "resadwhitelist";
    public static final String KEY_COOKIES_BLACKLIST_FOR_JS = "CookiesBlacklistForJs";
    public static final String KEY_DISABLE_ACCELERATE_CANVAS = "DisableAccelerateCanvas";
    public static final String KEY_DISABLE_FLOAT_VIDEO_VIEW = "video_fixed_sw_hostlist";
    public static final String KEY_DISABLE_VIDEO_RESUME = "disable_video_resume";
    public static final String KEY_DONOT_PAUSE_AFTER_EXIT_VIDEO_FULLSCREEN = "crsp_npef";
    public static final String KEY_DONOT_PAUSE_AFTER_SHOW_MODE_CHANGED = "crsp_npsmc";
    public static final String KEY_ENABLE_VIDEO_AUTO_PLAY_LIST = "video_play_gesture_whitelist";
    public static final String KEY_NIGHT_MODE_COLOR = "NightModeColor";
    public static final String KEY_NO_DISPLAY_WANING_WHEN_PLAY_MEDIA_ON_MOBILE_NETWORK = "crsp_nwomn";
    public static final String KEY_SWS_WHITE_LIST = "sws_white_list";
    public static final String KEY_USE_RAW_VIDEO_CONTROLS = "u4xr_video_st_list";
    public static final String KEY_VIDEO_ENTER_VIEW_FULLSCREEN_ONLY = "crsp_fsa_bl";
    public static final String KEY_VIDEO_SUPPORT_RAW_CONTROLS_ATTR = "crsp_sp_rc";
    public static final String KEY_WEBAUDIO_DISABLE_DEFAULT_DECODER = "crsp_wddd";
    public static final int LAYOUT_MODE_ADAPT = 2;
    public static final int LAYOUT_MODE_ZOOM = 1;
    public static final int PREREAD_TYPE_NON = 0;
    public static final int PREREAD_TYPE_WAP = 1;
    public static final int PREREAD_TYPE_WAP_AND_WEB = 3;
    public static final int PREREAD_TYPE_WEB = 2;
    public static final String SDKUUID = "SDKUUID";
    public static final int THEME_BLUE = 3;
    public static final int THEME_DEFAULT = 0;
    public static final int THEME_GREEN = 1;
    public static final int THEME_GREY = 4;
    public static final int THEME_PINK = 2;
    public static final int THEME_TRANSPARENT = -1;
    private static Set<String> a;

    public abstract boolean getEnableUCProxy();

    public abstract boolean getForceUCProxy();

    public abstract int getUCCookieType();

    public abstract void setEnableUCProxy(boolean z);

    public abstract void setForceUCProxy(boolean z);

    public abstract void setUCCookieType(int i);

    static {
        HashSet hashSet = new HashSet();
        a = hashSet;
        hashSet.add(KEY_USE_RAW_VIDEO_CONTROLS);
        a.add(KEY_VIDEO_SUPPORT_RAW_CONTROLS_ATTR);
        a.add(KEY_VIDEO_ENTER_VIEW_FULLSCREEN_ONLY);
        a.add(KEY_DISABLE_FLOAT_VIDEO_VIEW);
        a.add(KEY_ENABLE_VIDEO_AUTO_PLAY_LIST);
        a.add(KEY_ADBLOCK_WHITE_LIST);
        a.add(CD_RESOURCE_STAT_FILTER_LIST);
        a.add(KEY_NIGHT_MODE_COLOR);
        a.add(CD_RESOURCE_FOCUS_AUTO_POPUP_INPUT_WHITELIST);
        a.add(CD_RESOURCE_HYBRID_RENDER_EMBED_VIEW_ENABLE_LIST);
        a.add(CD_RESOURCE_DISABLE_SW_SCRIPTCACHE_LIST);
        a.add(CD_RESOURCE_ENABLE_IMG_ERROR_INFO);
        a.add(KEY_COOKIES_BLACKLIST_FOR_JS);
    }

    public static void donotPauseAfterExitVideoFullScreen() {
        SDKFactory.d.updateBussinessInfo(2, 1, KEY_DONOT_PAUSE_AFTER_EXIT_VIDEO_FULLSCREEN, "1");
    }

    public static void updateBussinessInfo(int i, int i2, String str, Object obj) {
        String str2;
        if (SDKFactory.d != null && a.contains(str)) {
            UCMobileWebKit uCMobileWebKit = SDKFactory.d;
            if (obj instanceof String[]) {
                String[] strArr = (String[]) obj;
                StringBuilder sb = new StringBuilder();
                for (String trim : strArr) {
                    String trim2 = trim.trim();
                    if (trim2.length() != 0) {
                        sb.append(trim2);
                        sb.append("^^");
                    }
                }
                if (sb.length() > 0) {
                    sb.setLength(sb.length() - 2);
                    str2 = sb.toString();
                    uCMobileWebKit.updateBussinessInfo(i, i2, str, str2);
                }
            }
            str2 = obj instanceof String ? (String) obj : "";
            uCMobileWebKit.updateBussinessInfo(i, i2, str, str2);
        }
    }

    public static void setLayoutMode(int i) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null && iGlobalSettings.getIntValue("LayoutStyle") != i) {
            iGlobalSettings.setIntValue("LayoutStyle", i);
        }
    }

    public static int getLayoutMode() {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            return iGlobalSettings.getIntValue("LayoutStyle");
        }
        return -1;
    }

    public static void setNightMode(boolean z) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null && iGlobalSettings.getBoolValue("IsNightMode") != z) {
            iGlobalSettings.setBoolValue("IsNightMode", z);
        }
    }

    public static boolean isNightMode() {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            return iGlobalSettings.getBoolValue("IsNightMode");
        }
        return false;
    }

    public static void setEnableCustomErrorPage(boolean z) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null && iGlobalSettings.getBoolValue("EnableCustomErrPage") != z) {
            iGlobalSettings.setBoolValue("EnableCustomErrPage", z);
        }
    }

    public static void setEnableMediaCache(boolean z) {
        SDKFactory.a(z);
    }

    public static boolean isEnableCustomErrorPage() {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            return iGlobalSettings.getBoolValue("EnableCustomErrPage");
        }
        return false;
    }

    public static void setGlobalEnableUCProxy(boolean z) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setBoolValue("global_enable_ucproxy", z);
        }
    }

    public static void setEnableAdblock(boolean z) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setBoolValue("EnableAdBlock", z);
        }
    }

    public static void setEnableDispatcher(boolean z) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setBoolValue("enable_dispatcher", z);
        }
    }

    public static void setEnableMultiThreadParser(boolean z) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setBoolValue("enable_multithread_parser", z);
        }
    }

    public static void setEnableAllResourceCallBack(boolean z) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setBoolValue("enable_allresponse_callback", z);
        }
    }

    public static void setEnableRequestIntercept(boolean z) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setBoolValue("enable_request_intercept", z);
        }
    }

    public static boolean getEnableRequestIntercept() {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            return iGlobalSettings.getBoolValue("enable_request_intercept");
        }
        return false;
    }

    public static boolean getEnableAllResourceCallBack() {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            return iGlobalSettings.getBoolValue("enable_allresponse_callback");
        }
        return false;
    }

    public static void setPageCacheCapacity(int i) {
        if (i < 0 || i > 20) {
            StringBuilder sb = new StringBuilder("capacity : ");
            sb.append(i);
            sb.append(", should be a non-negative integer between 0 (no cache) and 20 (max).");
            throw new IllegalArgumentException(sb.toString());
        }
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setIntValue("CachePageNumber", i);
        }
    }

    public static int getPageCacheCapacity() {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            return iGlobalSettings.getIntValue("CachePageNumber");
        }
        return -1;
    }

    public void setEnableFastScroller(boolean z) {
        Log.w("UCSettings", "setEnableFastScroller not override");
    }

    public boolean enableFastScroller() {
        Log.w("UCSettings", "enableFastScroller not override");
        return false;
    }

    public static void setForceUserScalable(int i) {
        if (i == FORCE_USER_SCALABLE_DEFAULT || i == FORCE_USER_SCALABLE_ENABLE || i == FORCE_USER_SCALABLE_DISABLE) {
            IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
            if (iGlobalSettings != null) {
                iGlobalSettings.setIntValue("PageForceUserScalable", i);
                return;
            }
            return;
        }
        StringBuilder sb = new StringBuilder("enable : ");
        sb.append(i);
        sb.append(", should be one of FORCE_USER_SCALABLE_DEFAULT/FORCE_USER_SCALABLE_ENABLE/FORCE_USER_SCALABLE_DISABLE");
        throw new IllegalArgumentException(sb.toString());
    }

    public static void setPageColorTheme(int i) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            if (i == -1) {
                iGlobalSettings.setBoolValue("IsTransparentTheme", true);
                return;
            }
            if (iGlobalSettings.getBoolValue("IsTransparentTheme")) {
                iGlobalSettings.setBoolValue("IsTransparentTheme", false);
            }
            iGlobalSettings.setIntValue("PageColorTheme", i);
        }
    }

    public static int getPageColorTheme() {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings == null) {
            return 0;
        }
        if (iGlobalSettings.getBoolValue("IsTransparentTheme")) {
            return -1;
        }
        return iGlobalSettings.getIntValue("PageColorTheme");
    }

    public static void setImageQuality(int i) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setIntValue("ImageQuality", i);
        }
    }

    public static int getImageQuality() {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            return iGlobalSettings.getIntValue("ImageQuality");
        }
        return 2;
    }

    public static void setSmartReader(boolean z) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setBoolValue("EnableSmartReader", z);
        }
    }

    public static boolean getSmartReader() {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            return iGlobalSettings.getBoolValue("EnableSmartReader");
        }
        return false;
    }

    public static void setPrereadType(int i) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setIntValue("PrereadOptions", i);
        }
    }

    public static int getPrereadType() {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            return iGlobalSettings.getIntValue("PrereadOptions");
        }
        return 0;
    }

    public static void setFormSaveType(int i) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setIntValue("FormSave", i);
        }
    }

    public static int getFormSaveType() {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            return iGlobalSettings.getIntValue("FormSave");
        }
        return 0;
    }

    public static void setEnableUCVideoViewFullscreen(boolean z) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setBoolValue("enable_uc_videoview_fullscreen", z);
        }
    }

    public static boolean enableUCVideoViewFullscreen() {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            return iGlobalSettings.getBoolValue("enable_uc_videoview_fullscreen");
        }
        return false;
    }

    public static void disableNetwork(Boolean bool) {
        Log.w("UCSettings", "background netoff current value=".concat(String.valueOf(bool)));
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setBoolValue("OFFNET_ON", bool.booleanValue());
        }
    }

    public static boolean isNetworkDisabled() {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            return iGlobalSettings.getBoolValue("OFFNET_ON");
        }
        return false;
    }

    public static void setEnableUCParam(boolean z) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setBoolValue("SDKUCParam", z);
        }
    }

    public static boolean enableUCParam() {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            return iGlobalSettings.getBoolValue("SDKUCParam");
        }
        return false;
    }

    public static void setGlobalPrivateBrowsing(boolean z) {
        setGlobalBoolValue("IsNoFootmark", z);
    }

    public static boolean isGlobalPrivateBrowsingEnabled() {
        return getGlobalBoolValue("IsNoFootmark");
    }

    public static void setGlobalIntValue(String str, int i) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setIntValue(str, i);
        }
    }

    public static void setGlobalStringValue(String str, String str2) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setStringValue(str, str2);
        }
    }

    public static void setRIPort(int i) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setStringValue("SDKRIPort", String.valueOf(i));
        }
    }

    public static void setGlobalBoolValue(String str, boolean z) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setBoolValue(str, z);
        }
    }

    public static boolean getGlobalBoolValue(String str) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            return iGlobalSettings.getBoolValue(str);
        }
        return false;
    }
}
