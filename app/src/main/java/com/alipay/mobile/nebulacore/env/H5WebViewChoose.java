package com.alipay.mobile.nebulacore.env;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.service.RnService;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5DeviceHelper;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5PatternHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.WebViewType;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class H5WebViewChoose {
    public static boolean useSysWebWillCrash() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) Nebula.getProviderManager().getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            String phoneInfo = Build.MANUFACTURER + MetaRecord.LOG_SEPARATOR + Build.MODEL + MetaRecord.LOG_SEPARATOR + VERSION.SDK_INT;
            JSONArray jsonArray = H5Utils.parseArray(h5ConfigProvider.getConfig("h5_first_init_use_android_webView_phone"));
            if (jsonArray != null && !jsonArray.isEmpty()) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    if (phoneInfo.equalsIgnoreCase(jsonArray.getString(i))) {
                        H5Log.d("H5WebViewFactory", "h5_first_init_use_android_webView_phone contain use uc");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static WebViewType getWebViewType(String bizType, Context context, Bundle startParams) {
        H5Log.d("H5WebViewFactory", "getWebViewType bizType " + bizType);
        if (RnService.isRnBiz(bizType)) {
            return WebViewType.RN_VIEW;
        }
        if (Nebula.DEBUG) {
            if (H5DevConfig.getBooleanConfig(H5DevConfig.H5_READ_USE_WEBVIEW_CONFIG, false)) {
                if (H5DevConfig.getBooleanConfig(H5DevConfig.H5_USE_UC_WEBVIEW, false)) {
                    H5Log.d("H5WebViewFactory", "read switch use uc webView");
                    return WebViewType.THIRD_PARTY;
                }
                H5Log.d("H5WebViewFactory", "read switch use android webView");
                return WebViewType.SYSTEM_BUILD_IN;
            } else if (Nebula.h5_dev_uc) {
                if (H5Utils.getConfigBoolean(context, "h5_uc_webview")) {
                    H5Log.d("H5WebViewFactory", "debug config to enable uc webview");
                    return WebViewType.THIRD_PARTY;
                }
                H5Log.d("H5WebViewFactory", "debug config to disable uc webview");
                return WebViewType.SYSTEM_BUILD_IN;
            }
        }
        if (a(H5Utils.getString(startParams, (String) "url"))) {
            H5Log.d("H5WebViewFactory", "degrade system by h5_degradeSysWebViewUrlPrefix");
            return WebViewType.SYSTEM_BUILD_IN;
        }
        String useSysWeb = H5Utils.getString(startParams, (String) H5Param.USE_SYS_WEBVIEW);
        if ("yes".equalsIgnoreCase(useSysWeb) && !useSysWebWillCrash()) {
            H5Log.d("H5WebViewFactory", "useSysWeb " + useSysWeb + " use SysWebview");
            return WebViewType.SYSTEM_BUILD_IN;
        } else if (!a(bizType, startParams) && !useSysWebWillCrash()) {
            return WebViewType.SYSTEM_BUILD_IN;
        } else {
            H5Log.d("H5WebViewFactory", "bizType match online config to use uc webview");
            return WebViewType.THIRD_PARTY;
        }
    }

    private static boolean a(String url) {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) Nebula.getProviderManager().getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            JSONArray configArray = h5ConfigProvider.getConfigJSONArray("h5_degradeSysWebViewUrlRegexs");
            if (configArray != null) {
                int size = configArray.size();
                int i = 0;
                while (i < size) {
                    String val = configArray.getString(i);
                    Pattern pattern = H5PatternHelper.compile(val);
                    if (pattern == null || !pattern.matcher(url).find()) {
                        i++;
                    } else {
                        H5Log.d("H5WebViewFactory", "degradeSysWebViewByUrlRegex: " + val);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean a(String bizType, Bundle startParams) {
        boolean usage;
        boolean enableExternalWebView = true;
        String webviewConfig = H5Environment.getConfig(H5Utils.KEY_H5_WEBVIEW_CONFIG);
        H5Log.d("H5WebViewFactory", "h5_webViewConfig " + webviewConfig);
        JSONObject joConfig = H5Utils.parseObject(webviewConfig);
        String value = H5Utils.getString(joConfig, (String) H5Utils.KEY_ENABLE_EXTERNAL_WEBVIEW);
        if (!TextUtils.isEmpty(value)) {
            enableExternalWebView = "YES".equalsIgnoreCase(value);
        }
        if (!enableExternalWebView) {
            return false;
        }
        JSONObject externalWebViewUsageRule = null;
        JSONObject externalWebViewSdkVersion = null;
        JSONArray externalWebViewModel = null;
        JSONArray externalWebView4UC = null;
        int cpuType = 2;
        try {
            externalWebViewUsageRule = H5Utils.getJSONObject(joConfig, H5Utils.KEY_EXTERNAL_WEBVIEW_USAGE_RULE, null);
            externalWebViewSdkVersion = H5Utils.getJSONObject(joConfig, H5Utils.KEY_EXTERNAL_WEBVIEW_SDK_VERSION, null);
            externalWebViewModel = H5Utils.getJSONArray(joConfig, H5Utils.KEY_EXTERNAL_WEBVIEW_MODEL, null);
            externalWebView4UC = H5Utils.getJSONArray(joConfig, "h5_externalWebView4UC", null);
            cpuType = H5Utils.getInt(joConfig, (String) "h5_externalWebView4CPU", 2);
        } catch (Exception e) {
            H5Log.e("H5WebViewFactory", "exception detail", e);
        }
        boolean usage2 = true;
        if (H5DeviceHelper.x86(cpuType)) {
            H5Log.d("H5WebViewFactory", "x86 cpu use system webview.");
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_USE_AndroidWebview").param1().add("x86内核降级系统webview", null).param2().add(H5Utils.getString(startParams, (String) "appId"), null).param3().add(String.valueOf(H5Utils.getBoolean(startParams, (String) "isTinyApp", false)), null));
            usage2 = false;
        }
        if (usage2 && a(externalWebView4UC, startParams)) {
            H5Log.d("H5WebViewFactory", "disable ucwebview by h5_externalWebView4UC");
            usage2 = false;
        }
        if (usage) {
            HashMap<String, Integer> b = b(externalWebViewSdkVersion);
            int currentSdkVersion = VERSION.SDK_INT;
            if (!b.containsKey("min") || !b.containsKey("max")) {
                H5Log.d("H5WebViewFactory", "disable ucwebview by h5_externalWebViewSdkVersion");
                usage = false;
            } else {
                usage = b.get("min").intValue() <= currentSdkVersion && currentSdkVersion <= b.get("max").intValue();
                if (!usage) {
                    H5Log.d("H5WebViewFactory", "disable ucwebview by h5_externalWebViewSdkVersion");
                }
            }
        }
        if (usage && externalWebViewModel != null) {
            int index = 0;
            while (true) {
                if (index >= externalWebViewModel.size()) {
                    break;
                }
                String item = externalWebViewModel.getString(index);
                if (!TextUtils.isEmpty(Build.MODEL) && Build.MODEL.equals(item)) {
                    H5Log.d("H5WebViewFactory", "disable ucwebview by h5_externalWebViewModel");
                    usage = false;
                    break;
                }
                index++;
            }
        }
        if (!usage) {
            H5Log.d("H5WebViewFactory", "disable ucwebview finally");
            return false;
        } else if (TextUtils.isEmpty(bizType)) {
            return true;
        } else {
            HashMap<String, String> a = a(externalWebViewUsageRule);
            if (a.size() == 0) {
                H5Log.d("H5WebViewFactory", "rulesInMap is null, default");
                return true;
            } else if ("SYSTEM_BUILD_IN".equals(a.get(bizType))) {
                H5Log.d("H5WebViewFactory", "disable ucwebview by h5_externalWebViewUsageRule");
                return false;
            } else {
                H5Log.d("H5WebViewFactory", "THIRDPARTY");
                return true;
            }
        }
    }

    private static HashMap<String, String> a(JSONObject config) {
        HashMap rules = new HashMap();
        if (config != null) {
            try {
                for (Entry entry : config.entrySet()) {
                    try {
                        rules.put(entry.getKey(), (String) entry.getValue());
                    } catch (ClassCastException e) {
                        H5Log.e("H5WebViewFactory", "exception detail", e);
                    }
                }
            } catch (Exception globalException) {
                H5Log.e("H5WebViewFactory", "exception detail.", globalException);
            }
        }
        return rules;
    }

    private static HashMap<String, Integer> b(JSONObject config) {
        HashMap version = new HashMap();
        if (config != null) {
            for (Entry entry : config.entrySet()) {
                version.put(entry.getKey(), (Integer) entry.getValue());
            }
        }
        return version;
    }

    public static void sendUcReceiver(boolean result) {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(H5Environment.getContext());
        Intent intent = new Intent(H5Param.H5_ACTION_UC_INIT_FINISH);
        intent.putExtra("result", result);
        manager.sendBroadcast(intent);
    }

    public static boolean notNeedInitUc(Bundle bundle) {
        boolean useUc;
        if (getWebViewType(getBizType(bundle), H5Environment.getContext(), bundle) == WebViewType.THIRD_PARTY) {
            useUc = true;
        } else {
            useUc = false;
        }
        H5Log.d("H5WebViewFactory", "ucReady " + H5Flag.ucReady + " useUc " + useUc + " needInitUc " + H5Flag.initUcNormal);
        if (H5Flag.ucReady || !useUc || !H5Flag.initUcNormal) {
            return true;
        }
        return false;
    }

    public static String getBizType(Bundle startParams) {
        if (startParams == null) {
            return null;
        }
        String bizType = H5Utils.getString(startParams, (String) "bizType", (String) "");
        if (TextUtils.isEmpty(bizType)) {
            bizType = H5Utils.getString(startParams, (String) H5Param.PUBLIC_ID, (String) "");
        }
        if (TextUtils.isEmpty(bizType)) {
            return H5Utils.getString(startParams, (String) "appId");
        }
        return bizType;
    }

    private static boolean a(JSONArray jsonArray, Bundle startParams) {
        boolean result = false;
        if (jsonArray != null && !jsonArray.isEmpty()) {
            String phoneInfo = Build.MANUFACTURER + MetaRecord.LOG_SEPARATOR + Build.MODEL + MetaRecord.LOG_SEPARATOR + VERSION.SDK_INT;
            String appId = H5Utils.getString(startParams, (String) "appId");
            String entryUrl = H5Utils.getString(startParams, (String) "url");
            H5Log.d("H5WebViewFactory", "disableUcWebView phoneInfo is " + phoneInfo + ", appId is " + appId + ", entryUrl is " + entryUrl);
            H5Log.d("H5WebViewFactory", "disableUcWebView jsonArray is " + jsonArray.toJSONString());
            int i = 0;
            while (true) {
                if (i >= jsonArray.size()) {
                    break;
                }
                H5Log.d("H5WebViewFactory", "disableUcWebView loop jsonArray round " + i);
                JSONObject object = jsonArray.getJSONObject(i);
                int objSize = object.size();
                if (object != null) {
                    boolean pi = false;
                    boolean ai = false;
                    boolean eu = false;
                    for (String key : object.keySet()) {
                        String value = object.getString(key);
                        if ("phoneInfo".equalsIgnoreCase(key)) {
                            pi = phoneInfo.equalsIgnoreCase(value);
                        }
                        if ("appId".equalsIgnoreCase(key)) {
                            ai = TextUtils.equals(appId, value);
                        }
                        if ("entryUrl".equalsIgnoreCase(key)) {
                            eu = TextUtils.equals(entryUrl, value);
                        }
                    }
                    if (objSize != 1 || (!pi && !ai && !eu)) {
                        if (objSize == 2) {
                            if (!pi || !ai) {
                                if (!pi || !eu) {
                                    if (ai && eu) {
                                        result = true;
                                        H5Log.d("H5WebViewFactory", "disableUcWebView loop object in round " + i + ", ai && eu break");
                                        break;
                                    }
                                } else {
                                    result = true;
                                    H5Log.d("H5WebViewFactory", "disableUcWebView loop object in round " + i + ", pi && eu break");
                                    break;
                                }
                            } else {
                                result = true;
                                H5Log.d("H5WebViewFactory", "disableUcWebView loop object in round " + i + ", pi && ai break");
                                break;
                            }
                        }
                        if (objSize == 3 && pi && ai && eu) {
                            result = true;
                            H5Log.d("H5WebViewFactory", "disableUcWebView loop object in round " + i + ", pi && ai && eu break");
                            break;
                        }
                    }
                }
                i++;
            }
            result = true;
            H5Log.d("H5WebViewFactory", "disableUcWebView loop object in round " + i + ", pi || ai || eu break");
        }
        H5Log.d("H5WebViewFactory", "disableUcWebView result " + result);
        return result;
    }
}
