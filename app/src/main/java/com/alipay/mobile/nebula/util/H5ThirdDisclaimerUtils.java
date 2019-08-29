package com.alipay.mobile.nebula.util;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider.OnConfigChangeListener;
import com.taobao.accs.common.Constants;

public class H5ThirdDisclaimerUtils {
    private static final String TAG = "H5ThirdDisclaimerUtils";
    private static boolean needJsapiDiscaimer = true;
    private static boolean needKeyboardHint = false;
    private static boolean needWapDialog = true;

    public static void initConfig() {
        H5ConfigProvider configProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (configProvider != null) {
            applyConfig(configProvider.getConfigWithNotifyChange("h5_payPrompt", new OnConfigChangeListener() {
                public final void onChange(String newValue) {
                    H5ThirdDisclaimerUtils.applyConfig(newValue);
                }
            }));
        }
    }

    /* access modifiers changed from: private */
    public static void applyConfig(String configStr) {
        boolean z;
        boolean z2 = true;
        try {
            H5Log.d(TAG, "applyConfig " + configStr);
            JSONObject configObject = H5Utils.parseObject(configStr);
            if (configObject != null) {
                if (!"NO".equalsIgnoreCase(H5Utils.getString(configObject, (String) "wapDialog"))) {
                    z = true;
                } else {
                    z = false;
                }
                needWapDialog = z;
                if ("NO".equalsIgnoreCase(H5Utils.getString(configObject, (String) "jsapiDiscaimer"))) {
                    z2 = false;
                }
                needJsapiDiscaimer = z2;
                needKeyboardHint = "YES".equalsIgnoreCase(H5Utils.getString(configObject, (String) "keyboardHint"));
            }
        } catch (Throwable t) {
            H5Log.e(TAG, "initConfig error", t);
        }
    }

    public static boolean isNeedWapDialog() {
        return needWapDialog;
    }

    public static boolean isNeedJsapiDiscaimer() {
        return needJsapiDiscaimer;
    }

    public static boolean isNeedKeyboardHint() {
        return needKeyboardHint;
    }

    public static int needShowDisclaimer(Bundle startParams, String currentUrl) {
        String url;
        H5ConfigProvider configProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (configProvider == null) {
            return 0;
        }
        if ("YES".equals(H5Utils.getString(startParams, (String) "fromLiveChannel"))) {
            H5Log.d(TAG, "needShowDisclaimer disable by fromLiveChannel");
            return 0;
        }
        if (TextUtils.isEmpty(currentUrl)) {
            url = H5Utils.getString(startParams, (String) "url");
        } else {
            url = currentUrl;
        }
        String host = null;
        Uri uri = H5UrlHelper.parseUrl(url);
        if (uri != null) {
            host = uri.getHost();
        }
        H5Log.d(TAG, "needShowDisclaimer url " + url + ", host " + host);
        if (configProvider.isAliDomains(url)) {
            H5Log.d(TAG, "needShowDisclaimer bingo alidomains");
            return 0;
        } else if (H5Utils.getBoolean(startParams, (String) "isTinyApp", false)) {
            H5Log.d(TAG, "needShowDisclaimer bingo isTinyApp");
            return 0;
        } else {
            String configStr = configProvider.getConfigWithProcessCache("h5_thirdDisclaimer_rules");
            H5Log.debug(TAG, "needShowDisclaimer configStr " + configStr);
            JSONObject configObj = H5Utils.parseObject(configStr);
            JSONArray whiteListArray = H5Utils.getJSONArray(configObj, "whiteList", null);
            if (whiteListArray != null && !whiteListArray.isEmpty() && !TextUtils.isEmpty(host)) {
                for (int i = 0; i < whiteListArray.size(); i++) {
                    if (H5PatternHelper.matchRegex(whiteListArray.getString(i), host)) {
                        H5Log.d(TAG, "needShowDisclaimer bingo whiteList");
                        return 0;
                    }
                }
            }
            String mode = H5Utils.getString(configObj, (String) Constants.KEY_MODE);
            boolean showThirdDisclaimer = H5Utils.getBoolean(startParams, (String) H5Param.LONG_SHOW_THIRDDISCLAIMER, false);
            H5Log.d(TAG, "needShowDisclaimer showThirdDisclaimer " + showThirdDisclaimer);
            boolean isInBlackList = false;
            JSONArray blackListArray = H5Utils.getJSONArray(configObj, "blackList", null);
            if (blackListArray != null && !blackListArray.isEmpty() && !TextUtils.isEmpty(host)) {
                int i2 = 0;
                while (true) {
                    if (i2 >= blackListArray.size()) {
                        break;
                    } else if (H5PatternHelper.matchRegex(blackListArray.getString(i2), host)) {
                        H5Log.d(TAG, "needShowDisclaimer bingo blackList");
                        isInBlackList = true;
                        break;
                    } else {
                        i2++;
                    }
                }
            }
            if (showThirdDisclaimer || isInBlackList) {
                if (TextUtils.equals(mode, "ignoreoptionuseblacklist")) {
                    if (isInBlackList) {
                        return 1;
                    }
                } else if (TextUtils.equals(mode, "usetimeout")) {
                    return 2;
                } else {
                    if (TextUtils.equals(mode, "default")) {
                        return 1;
                    }
                }
            }
            return 0;
        }
    }
}
