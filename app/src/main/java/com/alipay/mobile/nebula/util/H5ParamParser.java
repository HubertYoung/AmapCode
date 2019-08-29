package com.alipay.mobile.nebula.util;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Param.ParamType;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.startParam.H5StartParamManager;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class H5ParamParser {
    private static final String LAUNCHER_PARAM_URL = "launcherParamUrl";
    public static final String TAG = "H5ParamParser";
    private static Map<String, H5ParamImpl> paramMap = new HashMap<String, H5ParamImpl>() {
        {
            put("url", new H5ParamImpl("url", H5Param.URL, ParamType.STRING, ""));
            put(H5Param.LONG_DEFAULT_TITLE, new H5ParamImpl(H5Param.LONG_DEFAULT_TITLE, "dt", ParamType.STRING, ""));
            put(H5Param.LONG_TITLE_IMAGE, new H5ParamImpl(H5Param.LONG_TITLE_IMAGE, "ti", ParamType.STRING, ""));
            put(H5Param.LONG_SHOW_TITLEBAR, new H5ParamImpl(H5Param.LONG_SHOW_TITLEBAR, "st", ParamType.BOOLEAN, Boolean.valueOf(true)));
            put("showFavorites", new H5ParamImpl("showFavorites", H5Param.SHOW_FAVORITES, ParamType.BOOLEAN, Boolean.valueOf(false)));
            put("showReportBtn", new H5ParamImpl("showReportBtn", H5Param.SHOW_REPORT_BTN, ParamType.BOOLEAN, Boolean.valueOf(false)));
            put(H5Param.LONG_SHOW_TOOLBAR, new H5ParamImpl(H5Param.LONG_SHOW_TOOLBAR, H5Param.SHOW_TOOLBAR, ParamType.BOOLEAN, Boolean.valueOf(false)));
            put("showLoading", new H5ParamImpl("showLoading", H5Param.SHOW_LOADING, ParamType.BOOLEAN, Boolean.valueOf(false)));
            put(H5Param.LONG_CLOSE_BUTTON_TEXT, new H5ParamImpl(H5Param.LONG_CLOSE_BUTTON_TEXT, H5Param.CLOSE_BUTTON_TEXT, ParamType.STRING, ""));
            put(H5Param.LONG_SSO_LOGIN_ENABLE, new H5ParamImpl(H5Param.LONG_SSO_LOGIN_ENABLE, "le", ParamType.BOOLEAN, Boolean.valueOf(true)));
            put(H5Param.LONG_SAFEPAY_ENABLE, new H5ParamImpl(H5Param.LONG_SAFEPAY_ENABLE, H5Param.SAFEPAY_ENABLE, ParamType.BOOLEAN, Boolean.valueOf(true)));
            put(H5Param.LONG_SAFEPAY_CONTEXT, new H5ParamImpl(H5Param.LONG_SAFEPAY_CONTEXT, H5Param.SAFEPAY_CONTEXT, ParamType.STRING, ""));
            put("readTitle", new H5ParamImpl("readTitle", "rt", ParamType.BOOLEAN, Boolean.valueOf(true)));
            put(H5Param.LONG_BIZ_SCENARIO, new H5ParamImpl(H5Param.LONG_BIZ_SCENARIO, "bz", ParamType.STRING, ""));
            put(H5Param.LONG_ANTI_PHISHING, new H5ParamImpl(H5Param.LONG_ANTI_PHISHING, H5Param.ANTI_PHISHING, ParamType.BOOLEAN, Boolean.valueOf(true)));
            put(H5Param.LONG_TOOLBAR_MENU, new H5ParamImpl(H5Param.LONG_TOOLBAR_MENU, "tm", ParamType.STRING, ""));
            put(H5Param.LONG_BACK_BEHAVIOR, new H5ParamImpl(H5Param.LONG_BACK_BEHAVIOR, H5Param.BACK_BEHAVIOR, ParamType.STRING, H5Param.DEFAULT_LONG_BACK_BEHAVIOR));
            put("pullRefresh", new H5ParamImpl("pullRefresh", "pr", ParamType.BOOLEAN, Boolean.valueOf(false)));
            put("showTitleLoading", new H5ParamImpl("showTitleLoading", H5Param.SHOW_TITLE_LOADING, ParamType.BOOLEAN, Boolean.valueOf(false)));
            put(H5Param.LONG_SHOW_PROGRESS, new H5ParamImpl(H5Param.LONG_SHOW_PROGRESS, "sp", ParamType.BOOLEAN, Boolean.valueOf(false)));
            put(H5Param.LONG_SMART_TOOLBAR, new H5ParamImpl(H5Param.LONG_SMART_TOOLBAR, H5Param.SMART_TOOLBAR, ParamType.BOOLEAN, Boolean.valueOf(false)));
            put(H5Param.LONG_ENABLE_PROXY, new H5ParamImpl(H5Param.LONG_ENABLE_PROXY, H5Param.ENABLE_PROXY, ParamType.BOOLEAN, Boolean.valueOf(false)));
            put("canPullDown", new H5ParamImpl("canPullDown", H5Param.CAN_PULL_DOWN, ParamType.BOOLEAN, Boolean.valueOf(true)));
            put("showDomain", new H5ParamImpl("showDomain", H5Param.SHOW_DOMAIN, ParamType.BOOLEAN, Boolean.valueOf(true)));
            put("prefetchLocation", new H5ParamImpl("prefetchLocation", H5Param.PREFETCH_LOCATION, ParamType.BOOLEAN, Boolean.valueOf(false)));
            put("showOptionMenu", new H5ParamImpl("showOptionMenu", H5Param.SHOW_OPTION_MENU, ParamType.BOOLEAN, Boolean.valueOf(true)));
            put("backgroundColor", new H5ParamImpl("backgroundColor", "bc", ParamType.INT, Integer.valueOf(-1)));
            put(H5Param.LONG_INTERCEPT_JUMP, new H5ParamImpl(H5Param.LONG_INTERCEPT_JUMP, H5Param.INTERCEPT_JUMP, ParamType.BOOLEAN, Boolean.valueOf(true)));
            put(H5Param.LONG_CLOSE_AFTER_PAY_FINISH, new H5ParamImpl(H5Param.LONG_CLOSE_AFTER_PAY_FINISH, "cf", ParamType.BOOLEAN, Boolean.valueOf(true)));
            put(H5Param.LONG_TRANSPARENT, new H5ParamImpl(H5Param.LONG_TRANSPARENT, H5Param.TRANSPARENT, ParamType.BOOLEAN, Boolean.valueOf(false)));
            put(H5Param.LONG_FULLSCREEN, new H5ParamImpl(H5Param.LONG_FULLSCREEN, H5Param.FULLSCREEN, ParamType.BOOLEAN, Boolean.valueOf(false)));
            put(H5Param.LONG_LANDSCAPE, new H5ParamImpl(H5Param.LONG_LANDSCAPE, "ls", ParamType.STRING, ""));
            put(H5Param.LONG_ENABLE_SCROLLBAR, new H5ParamImpl(H5Param.LONG_ENABLE_SCROLLBAR, H5Param.ENABLE_SCROLLBAR, ParamType.BOOLEAN, Boolean.valueOf(true)));
            put(H5Param.LONG_DELAY_RENDER, new H5ParamImpl(H5Param.LONG_DELAY_RENDER, H5Param.DELAY_RENDER, ParamType.BOOLEAN, Boolean.valueOf(false)));
            put(H5Param.LONG_CAN_DESTROY, new H5ParamImpl(H5Param.LONG_CAN_DESTROY, H5Param.CAN_DESTROY, ParamType.BOOLEAN, Boolean.valueOf(false)));
            put(H5Param.LONG_TRANSPARENT_TITLE, new H5ParamImpl(H5Param.LONG_TRANSPARENT_TITLE, H5Param.TRANSPARENT_TITLE, ParamType.STRING, ""));
            put(H5Param.LONG_TITLE_BAR_COLOR, new H5ParamImpl(H5Param.LONG_TITLE_BAR_COLOR, H5Param.TITLE_BAR_COLOR, ParamType.INT, Integer.valueOf(ViewCompat.MEASURED_SIZE_MASK)));
            put(H5Param.LONG_TITLE_SCROLLDISTANCE, new H5ParamImpl(H5Param.LONG_TITLE_SCROLLDISTANCE, H5Param.TITLE_SCROLLDISTANCE, ParamType.INT, Integer.valueOf(255)));
            put(H5Param.LONG_BOUNCE_TOP_COLOR, new H5ParamImpl(H5Param.LONG_BOUNCE_TOP_COLOR, H5Param.BOUNCE_TOP_COLOR, ParamType.INT, Integer.valueOf(H5Param.DEFAULT_LONG_BOUNCE_TOP_COLOR)));
            put(H5Param.LONG_ALLOWS_BOUNCE_VERTICAL, new H5ParamImpl(H5Param.LONG_ALLOWS_BOUNCE_VERTICAL, H5Param.ALLOWS_BOUNCE_VERTICAL, ParamType.STRING, ""));
            put(H5Param.LONG_NB_UPDATE, new H5ParamImpl(H5Param.LONG_NB_UPDATE, H5Param.NB_UPDATE, ParamType.STRING, "async"));
            put(H5Param.LONG_NB_OFFLINE, new H5ParamImpl(H5Param.LONG_NB_OFFLINE, H5Param.NB_OFFLINE, ParamType.STRING, "async"));
            put(H5Param.LONG_NB_URL, new H5ParamImpl(H5Param.LONG_NB_URL, H5Param.NB_URL, ParamType.STRING, ""));
            put(H5Param.LONG_OB_VERSION, new H5ParamImpl(H5Param.LONG_OB_VERSION, H5Param.OB_VERSION, ParamType.STRING, ""));
            put(H5Param.LONG_NB_VERSION, new H5ParamImpl(H5Param.LONG_NB_VERSION, H5Param.NB_VERSION, ParamType.STRING, ""));
            put(H5Param.LONG_NAV_SEARCH_BAR_TYPE, new H5ParamImpl(H5Param.LONG_NAV_SEARCH_BAR_TYPE, H5Param.NAV_SEARCH_BAR_TYPE, ParamType.STRING, ""));
            put(H5Param.LONG_NAV_SEARCH_BAR_PLACEHOLDER, new H5ParamImpl(H5Param.LONG_NAV_SEARCH_BAR_PLACEHOLDER, H5Param.NAV_SEARCH_BAR_PLACEHOLDER, ParamType.STRING, ""));
            put(H5Param.LONG_NAV_SEARCH_BAR_VALUE, new H5ParamImpl(H5Param.LONG_NAV_SEARCH_BAR_VALUE, H5Param.NAV_SEARCH_BAR_VALUE, ParamType.STRING, ""));
            put(H5Param.LONG_NAV_SEARCH_BAR_MAX_LENGTH, new H5ParamImpl(H5Param.LONG_NAV_SEARCH_BAR_MAX_LENGTH, H5Param.NAV_SEARCH_BAR_MAX_LENGTH, ParamType.INT, Integer.valueOf(0)));
            put(H5Param.LONG_NAV_SEARCH_BAR_SEARCH_PLACEHOLDER, new H5ParamImpl(H5Param.LONG_NAV_SEARCH_BAR_SEARCH_PLACEHOLDER, H5Param.NAV_SEARCH_BAR_SEARCH_PLACEHOLDER, ParamType.BOOLEAN, Boolean.valueOf(false)));
            put(H5Param.LONG_BACKBTN_IMAGE, new H5ParamImpl(H5Param.LONG_BACKBTN_IMAGE, H5Param.BACKBTN_IMAGE, ParamType.STRING, "default"));
            put(H5Param.LONG_BACKBTN_TEXTCOLOR, new H5ParamImpl(H5Param.LONG_BACKBTN_TEXTCOLOR, H5Param.BACKBTN_TEXTCOLOR, ParamType.INT, Integer.valueOf(-16777216)));
            put(H5Param.LONG_TITLE_COLOR, new H5ParamImpl(H5Param.LONG_TITLE_COLOR, "tc", ParamType.INT, Integer.valueOf(-16777216)));
            put(H5Param.LONG_TRANSPARENT_TITLE_TEXTAUTO, new H5ParamImpl(H5Param.LONG_TRANSPARENT_TITLE_TEXTAUTO, H5Param.TRANSPARENT_TITLE_TEXTAUTO, ParamType.STRING, "NO"));
            put(H5Param.LONG_PRESSO_LOGIN, new H5ParamImpl(H5Param.LONG_PRESSO_LOGIN, H5Param.PRESSO_LOGIN, ParamType.STRING, "YES"));
            put(H5Param.LONG_PRESSO_LOGIN_BINDINGPAGE, new H5ParamImpl(H5Param.LONG_PRESSO_LOGIN_BINDINGPAGE, H5Param.PRESSO_LOGIN_BINDINGPAGE, ParamType.STRING, ""));
            put(H5Param.LONG_PRESSO_LOGIN_URL, new H5ParamImpl(H5Param.LONG_PRESSO_LOGIN_URL, H5Param.PRESSO_LOGIN_URL, ParamType.STRING, ""));
            put("tabBarJson", new H5ParamImpl("tabBarJson", "tabBarJson", ParamType.STRING, ""));
            put("enableTabBar", new H5ParamImpl("enableTabBar", "enableTabBar", ParamType.STRING, "default"));
            put("tabItemCount", new H5ParamImpl("tabItemCount", "tabItemCount", ParamType.INT, Integer.valueOf(-1)));
            put("preventAutoLoginLoop", new H5ParamImpl("preventAutoLoginLoop", "preventAutoLoginLoop", ParamType.BOOLEAN, Boolean.valueOf(false)));
            put(H5Param.LONG_TRANS_ANIMATE, new H5ParamImpl(H5Param.LONG_TRANS_ANIMATE, H5Param.TRANS_ANIMATE, ParamType.BOOLEAN, Boolean.valueOf(false)));
            put(H5Param.LONG_NB_OFFMODE, new H5ParamImpl(H5Param.LONG_NB_OFFMODE, H5Param.NB_OFFMODE, ParamType.STRING, "force"));
            put("openUrlMethod", new H5ParamImpl("openUrlMethod", "openUrlMethod", ParamType.STRING, "GET"));
            put("openUrlPostParams", new H5ParamImpl("openUrlPostParams", "openUrlPostParams", ParamType.STRING, ""));
            put(H5Param.LONG_NB_APP_TYPE, new H5ParamImpl(H5Param.LONG_NB_APP_TYPE, H5Param.NB_APP_TYPE, ParamType.STRING, ""));
            put(H5Param.LONG_SHARETOKENPARAMS, new H5ParamImpl(H5Param.LONG_SHARETOKENPARAMS, H5Param.SHARETOKENPARAMS, ParamType.STRING, ""));
            put(H5Param.PULL_INTERCEPT_DISTANCE, new H5ParamImpl(H5Param.PULL_INTERCEPT_DISTANCE, H5Param.PITD, ParamType.INT, Integer.valueOf(0)));
            put(H5Param.BACKGROUND_IMAGE_URL, new H5ParamImpl(H5Param.BACKGROUND_IMAGE_URL, H5Param.BGIU, ParamType.STRING, ""));
            put(H5Param.BACKGROUND_IMAGE_COLOR, new H5ParamImpl(H5Param.BACKGROUND_IMAGE_COLOR, H5Param.BGIC, ParamType.INT, Integer.valueOf(-1)));
            put(H5Param.LONG_TITLE_PENETRATE, new H5ParamImpl(H5Param.LONG_TITLE_PENETRATE, "tp", ParamType.STRING, "NO"));
        }
    };

    public static Bundle transParamParse(Bundle bundle) {
        if (bundle != null) {
            String transParam = H5Utils.getString(bundle, (String) H5Param.TRANSPARENT);
            if (TextUtils.isEmpty(transParam)) {
                transParam = H5Utils.getString(bundle, (String) H5Param.LONG_TRANSPARENT);
            }
            if (TextUtils.isEmpty(transParam) && (H5Utils.getBoolean(bundle, (String) H5Param.LONG_TRANSPARENT, false) || H5Utils.getBoolean(bundle, (String) H5Param.TRANSPARENT, false))) {
                transParam = "YES";
            }
            if ("YES".equals(transParam)) {
                bundle.remove("st");
                bundle.remove(H5Param.LONG_SHOW_TITLEBAR);
                bundle.remove(H5Param.SHOW_TOOLBAR);
                bundle.remove(H5Param.LONG_SHOW_TOOLBAR);
                bundle.remove("sp");
                bundle.remove(H5Param.LONG_SHOW_PROGRESS);
                bundle.remove(H5Param.SHOW_DOMAIN);
                bundle.remove("showDomain");
                bundle.remove(H5Param.CAN_PULL_DOWN);
                bundle.remove("canPullDown");
                bundle.remove(H5Param.ALLOWS_BOUNCE_VERTICAL);
                bundle.remove(H5Param.LONG_ALLOWS_BOUNCE_VERTICAL);
                bundle.putString("st", "NO");
                bundle.putString(H5Param.LONG_SHOW_TITLEBAR, "NO");
                bundle.putString(H5Param.SHOW_TOOLBAR, "NO");
                bundle.putString(H5Param.LONG_SHOW_TOOLBAR, "NO");
                bundle.putString("sp", "NO");
                bundle.putString(H5Param.LONG_SHOW_PROGRESS, "NO");
                bundle.putString(H5Param.SHOW_DOMAIN, "NO");
                bundle.putString("showDomain", "NO");
                bundle.putString(H5Param.CAN_PULL_DOWN, "NO");
                bundle.putString("canPullDown", "NO");
                bundle.putString(H5Param.ALLOWS_BOUNCE_VERTICAL, "NO");
                bundle.putString(H5Param.LONG_ALLOWS_BOUNCE_VERTICAL, "NO");
                if (!bundle.containsKey("backgroundColor") && !bundle.containsKey("bc")) {
                    String fullScreenStr = H5Utils.getString(bundle, (String) H5Param.LONG_FULLSCREEN);
                    if (!H5Utils.getBoolean(bundle, (String) H5Param.LONG_FULLSCREEN, false) && (TextUtils.isEmpty(fullScreenStr) || !fullScreenStr.equals("YES"))) {
                        String transAnimateStr = H5Utils.getString(bundle, (String) H5Param.LONG_TRANS_ANIMATE);
                        boolean transAnimate = H5Utils.getBoolean(bundle, (String) H5Param.LONG_TRANS_ANIMATE, false);
                        if ((TextUtils.isEmpty(transAnimateStr) || !"YES".equals(transAnimateStr)) && !transAnimate) {
                            bundle.putInt("bc", 855638016);
                            bundle.putInt("backgroundColor", 855638016);
                        } else {
                            bundle.putInt("bc", -1291845632);
                            bundle.putInt("backgroundColor", -1291845632);
                        }
                    }
                }
            }
        }
        return bundle;
    }

    public static Bundle parse(Bundle bundle, String name, boolean fillDefault) {
        return !paramMap.containsKey(name) ? bundle : paramMap.get(name).unify(bundle, fillDefault);
    }

    public static Bundle parse(Bundle bundle, boolean fillDefault) {
        if (bundle != null) {
            transParamParse(bundle);
            paramMap.get(H5Param.LONG_SHOW_PROGRESS).setDefaultValue(Boolean.valueOf("20000067".equals(H5Utils.getString(bundle, (String) "appId"))));
            if (fillDefault) {
                preFillDefault(bundle);
            }
            for (String longName : paramMap.keySet()) {
                bundle = paramMap.get(longName).unify(bundle, fillDefault);
            }
            String allowsBounceVertical = H5Utils.getString(bundle, (String) H5Param.LONG_ALLOWS_BOUNCE_VERTICAL, (String) "");
            H5Log.d(TAG, "merge LONG_ALLOWS_BOUNCE_VERTICAL & LONG_CAN_PULL_DOWN " + allowsBounceVertical);
            if (!TextUtils.isEmpty(allowsBounceVertical)) {
                if ("YES".equals(allowsBounceVertical)) {
                    bundle.putBoolean("canPullDown", true);
                } else if ("NO".equals(allowsBounceVertical)) {
                    bundle.putBoolean("canPullDown", false);
                }
            }
            int tabItemCount = H5Utils.getInt(bundle, (String) "tabItemCount", -1);
            String enableTabBar = H5Utils.getString(bundle, (String) "enableTabBar", (String) "default");
            H5Log.d(TAG, "tabItemCount " + tabItemCount + ", enableTabBar " + enableTabBar);
            if (TextUtils.equals("default", enableTabBar)) {
                if (tabItemCount != -1) {
                    bundle.putString("enableTabBar", "YES");
                }
            } else if (TextUtils.equals("YES", enableTabBar) && tabItemCount == -1) {
                bundle.putInt("tabItemCount", 4);
            }
            if (bundle.containsKey(H5Param.LONG_SHOW_THIRDDISCLAIMER)) {
                H5Log.d(TAG, "parse contains LONG_SHOW_THIRDDISCLAIMER force set true");
                bundle.remove(H5Param.LONG_SHOW_THIRDDISCLAIMER);
                bundle.putBoolean(H5Param.LONG_SHOW_THIRDDISCLAIMER, true);
            }
        }
        return bundle;
    }

    public static H5ParamImpl getParamImp(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        for (String longName : paramMap.keySet()) {
            H5ParamImpl param = paramMap.get(longName);
            String ln = param.getLongName();
            String sn = param.getShortName();
            if (key.equals(ln)) {
                return param;
            }
            if (key.equals(sn)) {
                return param;
            }
        }
        return null;
    }

    public static void remove(Bundle bundle, String key) {
        if (bundle != null && !TextUtils.isEmpty(key)) {
            H5ParamImpl paramImpl = getParamImp(key);
            if (paramImpl != null) {
                bundle.remove(paramImpl.getLongName());
                bundle.remove(paramImpl.getShortName());
            }
        }
    }

    private static void preFillDefault(Bundle bundle) {
        boolean z;
        boolean z2 = true;
        if (!bundle.containsKey("showOptionMenu") && !bundle.containsKey(H5Param.SHOW_OPTION_MENU)) {
            H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (h5ConfigProvider == null || BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_preFillDefault_h5App"))) {
                String appId = H5Utils.getString(bundle, (String) "appId");
                boolean value = false;
                if (TextUtils.isEmpty(appId)) {
                    appId = "20000067";
                }
                if (H5AppUtil.isH5ContainerAppId(appId) || "20000101".equals(appId) || "20000042".equals(appId)) {
                    value = true;
                }
                H5Log.d(TAG, "pre-fill set showOptionMenu as " + value);
                bundle.putBoolean("showOptionMenu", value);
                return;
            }
            boolean isH5App = H5Utils.getBoolean(bundle, (String) H5Param.isH5app, false);
            StringBuilder sb = new StringBuilder("pre-fill set showOptionMenu as ");
            if (!isH5App) {
                z = true;
            } else {
                z = false;
            }
            H5Log.d(TAG, sb.append(z).toString());
            if (isH5App) {
                z2 = false;
            }
            bundle.putBoolean("showOptionMenu", z2);
        }
    }

    public static void parseMagicOptions(Bundle params, String TAG2) {
        if (params == null) {
            H5Log.w(TAG2, "invalid magic parameter!");
            return;
        }
        String urlStr = H5Utils.getString(params, (String) H5Param.URL);
        if (TextUtils.isEmpty(urlStr)) {
            urlStr = H5Utils.getString(params, (String) "url");
        }
        if (TextUtils.isEmpty(urlStr)) {
            H5Log.w(TAG2, "no url found in magic parameter");
            return;
        }
        String decodedOptions = null;
        String optionsStr = H5UrlHelper.getParam(H5UrlHelper.parseUrl(urlStr), "__webview_options__", null);
        if (TextUtils.isEmpty(optionsStr)) {
            H5Log.w(TAG2, "no magic options found");
            return;
        }
        H5Log.d(TAG2, "found magic options " + optionsStr);
        try {
            decodedOptions = URLDecoder.decode(optionsStr, "UTF-8");
        } catch (Throwable t) {
            H5Log.e(TAG2, "magic options decode exp ", t);
        }
        if (TextUtils.isEmpty(decodedOptions)) {
            H5Log.e(TAG2, (String) "failed to decode magic options");
        } else if (decodedOptions == null) {
            try {
                H5Log.d(TAG2, "decodedOptions is null.");
            } catch (Exception e) {
                H5Log.e(TAG2, "failed to decode magic option.", e);
            }
        } else {
            for (String split : decodedOptions.split("&")) {
                String[] values = split.split("=");
                if (values.length >= 2) {
                    String key = URLDecoder.decode(values[0], "UTF-8");
                    String value = URLDecoder.decode(values[1], "UTF-8");
                    if (H5StartParamManager.launchParamsTag.equalsIgnoreCase(key) && !TextUtils.isEmpty(value)) {
                        Bundle launcherParam = H5StartParamManager.getInstance().getH5StartParam(H5Utils.getString(params, (String) "appId"), value);
                        if (launcherParam != null && !launcherParam.isEmpty()) {
                            H5Log.d(TAG2, "launchParamsTag " + launcherParam);
                            params.putAll(launcherParam);
                        }
                    }
                    if (!H5Param.DELAY_RENDER.equals(key) && !H5Param.LONG_DELAY_RENDER.equals(key) && !H5Param.TRANSPARENT.equals(key) && !H5Param.LONG_TRANSPARENT.equals(key) && !H5Param.ORIGIN_FROM_EXTERNAL.equals(key) && !H5Utils.SCAN_TYPE_KEY.equals(key) && !"schemeInnerSource".equals(key) && !H5Param.SCENEPARAMS_SHARETOKEN.equals(key)) {
                        remove(params, key);
                        params.putString(key, value);
                        H5Log.d(TAG2, "decode magic option [key] " + key + " [value] " + value);
                    }
                }
            }
            if (params.containsKey(H5Param.LONG_SHOW_THIRDDISCLAIMER)) {
                H5Log.d(TAG2, "parseMagicOptions contains LONG_SHOW_THIRDDISCLAIMER force set true");
                params.remove(H5Param.LONG_SHOW_THIRDDISCLAIMER);
                params.putBoolean(H5Param.LONG_SHOW_THIRDDISCLAIMER, true);
            }
        }
    }

    public static void setLauncherParams(JSONObject launchParams, Bundle bundle) {
        if (launchParams == null || launchParams.isEmpty()) {
            H5Log.e((String) TAG, (String) "can't parse launch parameters as json");
            return;
        }
        Set keys = launchParams.keySet();
        bundle.putString(LAUNCHER_PARAM_URL, H5Utils.getString(launchParams, (String) "url"));
        for (String key : keys) {
            H5ParamImpl paramImpl = getParamImp(key);
            if (paramImpl == null || ((!bundle.containsKey(paramImpl.getLongName()) && !bundle.containsKey(paramImpl.getShortName())) || (bundle.get(paramImpl.getLongName()) == null && bundle.get(paramImpl.getShortName()) == null))) {
                Object value = launchParams.get(key);
                if (value instanceof String) {
                    String stringValue = (String) value;
                    if (!bundle.containsKey(key) || TextUtils.isEmpty(H5Utils.getString(bundle, key))) {
                        bundle.putString(key, stringValue);
                    } else {
                        H5Log.d(TAG, "bundle contain " + key + " value:" + H5Utils.getString(bundle, key) + " not to merge appInfo");
                    }
                } else if (value instanceof Boolean) {
                    bundle.putBoolean(key, ((Boolean) value).booleanValue());
                } else if (value instanceof BigDecimal) {
                    bundle.putDouble(key, ((BigDecimal) value).doubleValue());
                } else if (value instanceof Integer) {
                    bundle.putInt(key, ((Integer) value).intValue());
                } else if (value instanceof JSONObject) {
                    bundle.putString(key, ((JSONObject) value).toString());
                } else if (value instanceof JSONArray) {
                    bundle.putString(key, ((JSONArray) value).toString());
                } else {
                    H5Log.d(TAG, "ignore launch param [key] " + key + " [value] " + value);
                }
                H5Log.d(TAG, "read launch param [key] " + key + " [value] " + value);
            } else {
                H5Log.d(TAG, "merge config [key] " + key + " already exists and value not empty");
            }
        }
    }
}
