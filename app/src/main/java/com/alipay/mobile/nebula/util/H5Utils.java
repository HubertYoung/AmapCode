package com.alipay.mobile.nebula.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.TypedValue;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.androidannotations.utils.PermissionUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.APImageFormat;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.permission.RequestPermissionsResultCallback;
import com.alipay.mobile.h5container.api.H5Bundle;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.h5container.service.UcService;
import com.alipay.mobile.nebula.appcenter.H5PresetInfo;
import com.alipay.mobile.nebula.appcenter.H5PresetPkg;
import com.alipay.mobile.nebula.appcenter.listen.NebulaAppCallback;
import com.alipay.mobile.nebula.appcenter.listen.NebulaAppCallbackInfo;
import com.alipay.mobile.nebula.appcenter.listen.NebulaAppManager;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.io.PoolingByteArrayOutputStream;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.log.H5Logger;
import com.alipay.mobile.nebula.permission.H5PermissionCallback;
import com.alipay.mobile.nebula.process.H5EventHandler;
import com.alipay.mobile.nebula.provider.H5AppCenterPresetProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5EnvProvider;
import com.alipay.mobile.nebula.provider.H5LoginProvider;
import com.alipay.mobile.nebula.provider.H5ProviderManager;
import com.alipay.mobile.nebula.provider.H5UCProvider;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.wallet.H5WalletWrapper;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobile.tinyappcommon.h5plugin.H5VConsolePlugin;
import com.alipay.mobile.tinyappcommon.storage.TinyAppStorage;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import com.alipay.mobile.worker.remotedebug.TinyAppRemoteDebugInterceptor;
import com.alipay.mobile.worker.remotedebug.TinyAppRemoteDebugInterceptorManager;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class H5Utils {
    private static final int CLOSE_FLAG = -1;
    public static final String EGG_PAIN_IP = "0.0.0.0";
    public static final String FRAGMENT_ROOT_VIEW_TAG = "fragmentRootView";
    public static final String KEY_APK_WHITE_LIST = "h5_ApkWhiteList";
    public static final String KEY_CLEAR_STATE = "h5_ssoLoginNeedClearState";
    public static final String KEY_ENABLE_EXTERNAL_WEBVIEW = "h5_enableExternalWebView";
    public static final String KEY_ENTRANCE_WHITELIST = "h5_entranceWhiteListSwitch";
    public static final String KEY_EXTERNAL_WEBVIEW_MODEL = "h5_externalWebViewModel";
    public static final String KEY_EXTERNAL_WEBVIEW_SDK_VERSION = "h5_externalWebViewSdkVersion";
    public static final String KEY_EXTERNAL_WEBVIEW_USAGE_RULE = "h5_externalWebViewUsageRule";
    public static final String KEY_H5_AUTO_LOGIN_SWITCH = "h5_autoLoginSwitch";
    public static final String KEY_H5_CDN_WEBP_CONFIG = "h5_cdnWebPConfig";
    public static final String KEY_H5_COMMON_CONFIG = "h5_commonConfig";
    public static final String KEY_H5_FORCE_UC = "h5_forceUc";
    public static final String KEY_H5_SHARE_TO_ALP_TIMELINE_SWITCH = "h5_share2ALPTimeLineSwitch";
    public static final String KEY_H5_WEBVIEW_CONFIG = "h5_webViewConfig";
    public static final String KEY_INPUT_WARNING_TEXT = "h5_inputWarningText";
    public static final String KEY_INPUT_WHITE_LIST_SWITCH = "inputWhiteListSwitch";
    public static final String KEY_JSAPI_SWITCH = "jsApiSwitch";
    public static final String KEY_MAIN_SWITCH = "mainSwitch";
    public static final String KEY_REMOTE_DEBUG_LOG_CONTENT = "remote_debug_content";
    public static final String KEY_REMOTE_DEBUG_LOG_MSG = "remote_debug_msg";
    public static final String KEY_SHARE_CHANNELS = "h5_shareChannels";
    public static final String KEY_SSO_LOGIN = "h5_ssoLogin";
    public static final String KEY_SSO_LOGIN_SWITCH = "ssoLoginSwitch";
    public static final String KEY_TBSSO_TIMEOUT = "tbssoLoginTimeout";
    private static final int MAX_STR_LENGTH = 5000;
    public static final String NETWORK_TYPE_2G = "2G";
    public static final String NETWORK_TYPE_3G = "3G";
    public static final String NETWORK_TYPE_4G = "4G";
    public static final String NETWORK_TYPE_NOTREACHABLE = "NotReachable";
    public static final String NETWORK_TYPE_UNKNOWN = "UNKNOWN";
    public static final String NETWORK_TYPE_WIFI = "WIFI";
    public static final String SCAN_APP_ID = "10000007";
    public static final String SCAN_TYPE_KEY = "useScan";
    public static final String SCHEME_INNER_SOURCE = "schemeInnerSource";
    public static final int SEND_MSG_FROM_MAIN_PROCESS = 20000196;
    public static final int SEND_MSG_FROM_MAIN_PROCESS_CHOOSE_FILE = 200001964;
    public static final int SEND_MSG_FROM_MAIN_PROCESS_DOWNLOAD_APP_CANCEL = 200001961;
    public static final int SEND_MSG_FROM_MAIN_PROCESS_DOWNLOAD_APP_FAIL = 200001963;
    public static final int SEND_MSG_FROM_MAIN_PROCESS_DOWNLOAD_APP_FINISH = 200001962;
    public static final int SEND_MSG_FROM_MAIN_PROCESS_REMOTE_DEBUG_LOG = 200001966;
    public static final String TAG = "H5Utils";
    public static final String TRANSPARENT_AD_VIEW_TAG = "adView";
    private static String currentProcessName = null;
    private static Boolean isDebug = null;
    public static String ldcLevel;

    public static PackageInfo getPackageInfo(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        try {
            List packageInfos = context.getPackageManager().getInstalledPackages(0);
            if (packageInfos == null) {
                return getPkInfo(context, packageName);
            }
            for (int index = 0; index < packageInfos.size(); index++) {
                PackageInfo packageInfo = packageInfos.get(index);
                if (packageName.equals(packageInfo.packageName)) {
                    return packageInfo;
                }
            }
            return getPkInfo(context, packageName);
        } catch (Exception e) {
            H5Log.e(TAG, "exception detail", e);
            return null;
        }
    }

    public static PackageInfo getPkInfo(Context context, String packageName) {
        try {
            return context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (Exception e) {
            H5Log.e((String) TAG, (Throwable) e);
            return null;
        }
    }

    public static String getString(Bundle bundle, String key) {
        return getString(bundle, key, (String) "");
    }

    public static String getString(Bundle bundle, String key, String df) {
        if (df == null) {
            df = "";
        }
        return (String) getValue(bundle, key, (T) df);
    }

    public static boolean getBoolean(Bundle bundle, String key, boolean df) {
        return ((Boolean) getValue(bundle, key, (T) Boolean.valueOf(df))).booleanValue();
    }

    public static int getInt(Bundle bundle, String key) {
        return getInt(bundle, key, 0);
    }

    public static int getInt(Bundle bundle, String key, int df) {
        return ((Integer) getValue(bundle, key, (T) Integer.valueOf(df))).intValue();
    }

    public static long getLong(Bundle bundle, String key) {
        return getLong(bundle, key, 0);
    }

    public static long getLong(Bundle bundle, String key, long df) {
        return ((Long) getValue(bundle, key, (T) Long.valueOf(df))).longValue();
    }

    public static double getDouble(Bundle bundle, String key) {
        return getDouble(bundle, key, 0.0d);
    }

    public static double getDouble(Bundle bundle, String key, double df) {
        return ((Double) getValue(bundle, key, (T) Double.valueOf(df))).doubleValue();
    }

    public static boolean contains(Bundle bundle, String key) {
        boolean z = false;
        if (bundle == null || TextUtils.isEmpty(key)) {
            return z;
        }
        try {
            return bundle.containsKey(key);
        } catch (Exception e) {
            H5Log.e((String) TAG, (Throwable) e);
            return z;
        }
    }

    public static <T> T getValue(Bundle bundle, String key, T df) {
        if (bundle == null) {
            return df;
        }
        try {
            if (TextUtils.isEmpty(key) || df == null || !bundle.containsKey(key)) {
                return df;
            }
            T df2 = df;
            T t = bundle.get(key);
            if (t == null || !df2.getClass().isAssignableFrom(t.getClass())) {
                H5Log.d(TAG, "[key] " + key + " [value] " + t);
            } else {
                df2 = t;
            }
            return df2;
        } catch (Exception e) {
            H5Log.e((String) TAG, (Throwable) e);
            return df;
        }
    }

    public static JSONObject toJSONObject(Bundle bundle) {
        JSONObject joBundle = new JSONObject();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                joBundle.put(key, bundle.get(key));
            }
        }
        return joBundle;
    }

    public static String getString(JSONObject params, String key) {
        return getString(params, key, (String) "");
    }

    public static String getString(JSONObject params, String key, String df) {
        if (df == null) {
            df = "";
        }
        return (String) getValue(params, key, (T) df);
    }

    public static boolean getBoolean(JSONObject params, String key, boolean df) {
        return ((Boolean) getValue(params, key, (T) Boolean.valueOf(df))).booleanValue();
    }

    public static int getInt(JSONObject params, String key) {
        return getInt(params, key, 0);
    }

    public static int getInt(JSONObject params, String key, int df) {
        return ((Integer) getValue(params, key, (T) Integer.valueOf(df))).intValue();
    }

    public static long getLong(JSONObject params, String key) {
        return getLong(params, key, 0);
    }

    public static long getLong(JSONObject params, String key, long df) {
        return ((Long) getValue(params, key, (T) Long.valueOf(df))).longValue();
    }

    public static float getFloat(JSONObject params, String key) {
        return ((Float) getValue(params, key, (T) Float.valueOf(0.0f))).floatValue();
    }

    public static float getFloat(JSONObject params, String key, float df) {
        return ((Float) getValue(params, key, (T) Float.valueOf(df))).floatValue();
    }

    public static JSONObject getJSONObject(JSONObject params, String key, JSONObject df) {
        if (df == null) {
            df = new JSONObject();
        }
        return (JSONObject) getValue(params, key, (T) df);
    }

    public static JSONArray getJSONArray(JSONObject params, String key, JSONArray df) {
        if (df == null) {
            df = new JSONArray();
        }
        return (JSONArray) getValue(params, key, (T) df);
    }

    public static boolean contains(JSONObject params, String key) {
        return params != null && !params.isEmpty() && params.containsKey(key);
    }

    public static <T> T getValue(JSONObject params, String key, T df) {
        if (params == null || params.isEmpty() || df == null || !params.containsKey(key)) {
            return df;
        }
        T df2 = df;
        T t = params.get(key);
        if (t == null || !df2.getClass().isAssignableFrom(t.getClass())) {
            H5Log.w(TAG, "[key] " + key + " [value] " + t);
        } else {
            df2 = t;
        }
        return df2;
    }

    public static String getConfigString(Context context, String key) {
        String value = null;
        try {
            Uri contentUri = H5UrlHelper.parseUrl("content://com.alipay.setting/" + key);
            Cursor cursor = context.getContentResolver().query(contentUri, new String[]{""}, "", new String[0], "");
            while (cursor.moveToNext()) {
                value = cursor.getString(0);
            }
            cursor.close();
        } catch (Exception e) {
            H5Log.e(TAG, "exception detail", e);
        }
        return value;
    }

    public static boolean getConfigBoolean(Context context, String key) {
        boolean value = false;
        try {
            Uri contentUri = H5UrlHelper.parseUrl("content://com.alipay.setting/" + key);
            Cursor cursor = context.getContentResolver().query(contentUri, new String[]{""}, "", new String[0], "");
            if (cursor == null) {
                return false;
            }
            if (cursor.moveToNext()) {
                if (cursor.getInt(0) == 1) {
                    value = true;
                } else {
                    value = false;
                }
            }
            cursor.close();
            return value;
        } catch (Exception e) {
            H5Log.e(TAG, "Exception", e);
        }
    }

    public static Bundle toBundle(JSONObject params) {
        return toBundle(null, params);
    }

    public static Bundle toBundle(Bundle bundle, JSONObject params) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                try {
                    Object value = params.get(key);
                    if (value instanceof Integer) {
                        bundle.putInt(key, ((Integer) value).intValue());
                    } else if (value instanceof Boolean) {
                        bundle.putBoolean(key, ((Boolean) value).booleanValue());
                    } else if (value instanceof String) {
                        bundle.putString(key, (String) value);
                    } else if (value instanceof Long) {
                        bundle.putLong(key, ((Long) value).longValue());
                    } else if (value instanceof Double) {
                        bundle.putDouble(key, ((Double) value).doubleValue());
                    } else if (value instanceof Float) {
                        bundle.putDouble(key, new BigDecimal(Float.toString(((Float) value).floatValue())).doubleValue());
                    } else if (value instanceof JSON) {
                        bundle.putString(key, ((JSON) value).toJSONString());
                    } else if (value instanceof BigDecimal) {
                        bundle.putDouble(key, ((BigDecimal) value).doubleValue());
                    }
                } catch (Exception e) {
                    H5Log.e(TAG, "toBundle exception", e);
                }
            }
        }
        return bundle;
    }

    public static JSONObject parseObject(String text) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        boolean z = false;
        try {
            return JSON.parseObject(text);
        } catch (Exception e) {
            H5Log.e((String) TAG, (Throwable) e);
            return z;
        }
    }

    public static JSONArray parseArray(String text) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        boolean z = false;
        try {
            return JSON.parseArray(text);
        } catch (Exception e) {
            H5Log.e((String) TAG, (Throwable) e);
            return z;
        }
    }

    public static String base64ToString(String base64, int flag) {
        if (base64 == null || TextUtils.isEmpty(base64)) {
            return null;
        }
        return new String(Base64.decode(base64, flag));
    }

    public static boolean isMain() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public static void runOnMain(Runnable runnable) {
        if (runnable != null) {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                runnable.run();
            } else {
                new Handler(Looper.getMainLooper()).post(runnable);
            }
        }
    }

    public static void runNotOnMain(String threadType, Runnable runnable) {
        if (runnable != null) {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                getExecutor(threadType).execute(runnable);
            } else {
                runnable.run();
            }
        }
    }

    public static String getAbsoluteUrl(String currentUrl, String url, Bundle startParam) {
        Uri uri = H5UrlHelper.parseUrl(url);
        if (uri == null || !TextUtils.isEmpty(uri.getScheme())) {
            return url;
        }
        String absUrl = null;
        if (url.startsWith("//")) {
            if (TextUtils.isEmpty(currentUrl)) {
                return null;
            }
            Uri currentUri = H5UrlHelper.parseUrl(currentUrl);
            if (currentUri != null && !TextUtils.isEmpty(currentUri.getScheme())) {
                absUrl = currentUri.getScheme() + ":" + url;
            }
            H5Log.d(TAG, "getAbsoluteUrl // " + absUrl);
        } else if (url.startsWith("/")) {
            Uri currentUri2 = H5UrlHelper.parseUrl(currentUrl);
            if (currentUri2 == null) {
                return null;
            }
            String currentScheme = currentUri2.getScheme();
            String authority = currentUri2.getAuthority();
            if (!TextUtils.isEmpty(currentScheme) && !TextUtils.isEmpty(authority)) {
                absUrl = currentScheme + "://" + authority + url;
            }
            H5Log.d(TAG, "getAbsoluteUrl / " + absUrl);
        } else if (TextUtils.isEmpty(currentUrl)) {
            return null;
        } else {
            H5ConfigProvider provider = (H5ConfigProvider) getProvider(H5ConfigProvider.class.getName());
            if (provider != null && !"NO".equals(provider.getConfigWithProcessCache("h5_getAbsoluteUrlRemoveQuery"))) {
                currentUrl = H5UrlHelper.purifyUrl(currentUrl);
            }
            int index = currentUrl.lastIndexOf("/");
            if (index == -1) {
                return null;
            }
            absUrl = currentUrl.substring(0, index) + "/" + url;
            H5Log.d(TAG, "getAbsoluteUrl else " + absUrl);
        }
        return absUrl;
    }

    public static String getAbsoluteUrlV2(String currentUrl, String url, Bundle startParam) {
        String absUrl;
        Uri uri = H5UrlHelper.parseUrl(url);
        if (uri == null || !TextUtils.isEmpty(uri.getScheme())) {
            return url;
        }
        String absUrl2 = null;
        if (url.startsWith("./")) {
            Uri currentUri = H5UrlHelper.parseUrl(currentUrl);
            if (currentUri == null) {
                return null;
            }
            String currentScheme = currentUri.getScheme();
            String authority = currentUri.getAuthority();
            String path = currentUri.getEncodedPath();
            if (!TextUtils.isEmpty(path)) {
                absUrl = currentScheme + "://" + authority + path.substring(0, path.lastIndexOf("/")) + url.substring(1);
            } else {
                absUrl = currentScheme + "://" + authority + url.substring(1);
            }
            H5Log.d(TAG, "getAbsoluteUrlV2 ./ " + absUrl);
        } else {
            Uri currentUri2 = H5UrlHelper.parseUrl(currentUrl);
            if (currentUri2 == null) {
                return null;
            }
            String currentScheme2 = currentUri2.getScheme();
            String authority2 = currentUri2.getAuthority();
            if (!TextUtils.isEmpty(currentScheme2) && !TextUtils.isEmpty(authority2)) {
                absUrl2 = url.startsWith("/") ? currentScheme2 + "://" + authority2 + url : currentScheme2 + "://" + authority2 + "/" + url;
            }
            H5Log.d(TAG, "getAbsoluteUrlV2 / or else " + absUrl);
        }
        return absUrl;
    }

    public static String getAbsoluteUrlWithURLLib(String baseUrl, String url) {
        Uri uri = H5UrlHelper.parseUrl(url);
        if (uri == null || !TextUtils.isEmpty(uri.getScheme())) {
            return url;
        }
        String absUrl = null;
        try {
            absUrl = new URL(new URL(baseUrl), url).toString();
        } catch (MalformedURLException e) {
            H5Log.e(TAG, "getAbsoluteUrlWithURLLib fatal error ", e);
        }
        return absUrl;
    }

    public static boolean isStripLandingURLEnable(String url, String type) {
        boolean result = false;
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            JSONObject object = parseObject(h5ConfigProvider.getConfigWithProcessCache("h5_stripLandingConfig"));
            if (object != null && !object.isEmpty() && !TextUtils.isEmpty(type)) {
                result = getBoolean(object, type, false);
            }
        }
        H5Log.d(TAG, "isStripLandingURLEnable result " + result);
        return result;
    }

    public static String getStripLandingURL(String originUrl) {
        String originPrefix;
        String tempResultUrl;
        String str = null;
        String resultUrl = originUrl;
        if (!TextUtils.isEmpty(originUrl)) {
            Uri originUri = H5UrlHelper.parseUrl(originUrl);
            if (originUri != null) {
                originPrefix = originUri.getScheme() + "://" + originUri.getEncodedAuthority() + originUri.getEncodedPath() + "?";
            } else {
                originPrefix = null;
            }
            JSONArray landingPrefixs = null;
            JSONArray schemes = null;
            H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) getProvider(H5ConfigProvider.class.getName());
            if (h5ConfigProvider != null) {
                JSONObject object = parseObject(h5ConfigProvider.getConfigWithProcessCache("h5_stripLandingConfig"));
                if (object != null && !object.isEmpty()) {
                    landingPrefixs = getJSONArray(object, "urlPrefix", null);
                    schemes = getJSONArray(object, "scheme", null);
                }
            }
            if (ifMatchLanding(originPrefix, landingPrefixs, 0)) {
                if (originUri != null) {
                    tempResultUrl = originUri.getQueryParameter("scheme");
                } else {
                    tempResultUrl = null;
                }
                Uri tempResultUri = H5UrlHelper.parseUrl(tempResultUrl);
                if (tempResultUri != null) {
                    str = tempResultUri.getScheme();
                }
                if (ifMatchLanding(str, schemes, 1)) {
                    resultUrl = tempResultUrl;
                }
            }
        }
        H5Log.d(TAG, "getStripLandingURL resultUrl " + resultUrl);
        return resultUrl;
    }

    private static boolean ifMatchLanding(String prefix, JSONArray prefixs, int type) {
        if (prefixs != null && !prefixs.isEmpty()) {
            for (int i = 0; i < prefixs.size(); i++) {
                String item = prefixs.getString(i);
                if (type == 0) {
                    if (prefix.indexOf(item) != -1) {
                        return true;
                    }
                } else if (TextUtils.equals(prefix, item)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void landingMonitor(String origUrl, String parsedUrl, boolean result, String type, String appId, String publicId, String bizScenario) {
        final String str = origUrl;
        final boolean z = result;
        final String str2 = parsedUrl;
        final String str3 = type;
        final String str4 = appId;
        final String str5 = publicId;
        final String str6 = bizScenario;
        getExecutor(H5ThreadType.IO).execute(new Runnable() {
            public final void run() {
                H5LogData logData = H5LogData.seedId("H5_STRPLANDING_RESULT").param3().add("origUrl", str);
                if (z) {
                    logData.param3().add("parsedUrl", str2).add("result", "1").add("in", str3);
                } else {
                    logData.param3().add("result", "0").add("in", "unknown");
                }
                logData.param4().add("appId", str4).add(H5Param.PUBLIC_ID, str5).add(LogContext.STORAGE_REFVIEWID, H5Logger.getContextParam(LogContext.STORAGE_REFVIEWID)).add(H5Param.LONG_BIZ_SCENARIO, str6);
                H5LogUtil.logNebulaTech(logData);
            }
        });
    }

    public static String deParameterizeUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        try {
            new URL(url);
            String targetUrl = url;
            int inPageHashStart = url.indexOf(MetaRecord.LOG_SEPARATOR);
            if (inPageHashStart != -1) {
                targetUrl = targetUrl.substring(0, inPageHashStart);
            }
            int queryParamStart = url.lastIndexOf("?");
            if (queryParamStart != -1) {
                targetUrl = targetUrl.substring(0, queryParamStart);
            }
            return targetUrl;
        } catch (MalformedURLException e) {
            H5Log.e(TAG, "exception detail", e);
            return url;
        }
    }

    public static void runOnMain(Runnable runnable, long delay) {
        if (runnable != null) {
            new Handler(Looper.getMainLooper()).postDelayed(runnable, delay);
        }
    }

    public static Handler runOnMainHandler(Runnable runnable, long delay) {
        if (runnable == null) {
            return null;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable, delay);
        return handler;
    }

    public static int getUid(Context context) {
        try {
            if (context.getApplicationInfo().uid != 0) {
                return context.getApplicationInfo().uid;
            }
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 1).uid;
        } catch (NameNotFoundException e) {
            H5Log.e(TAG, "exception detail", e);
            return 0;
        } catch (RuntimeException e2) {
            H5Log.e(TAG, "exception detail", e2);
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0046 A[SYNTHETIC, Splitter:B:19:0x0046] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0056 A[SYNTHETIC, Splitter:B:25:0x0056] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.String read(java.lang.String r11) {
        /*
            r7 = 0
            r2 = 0
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x003c }
            r3.<init>(r11)     // Catch:{ Throwable -> 0x003c }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0066, all -> 0x0063 }
            r4.<init>(r3)     // Catch:{ Throwable -> 0x0066, all -> 0x0063 }
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0066, all -> 0x0063 }
            r0.<init>(r4)     // Catch:{ Throwable -> 0x0066, all -> 0x0063 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0066, all -> 0x0063 }
            r1.<init>()     // Catch:{ Throwable -> 0x0066, all -> 0x0063 }
            java.lang.String r5 = r0.readLine()     // Catch:{ Throwable -> 0x0066, all -> 0x0063 }
        L_0x001a:
            if (r5 == 0) goto L_0x0029
            r1.append(r5)     // Catch:{ Throwable -> 0x0066, all -> 0x0063 }
            r8 = 10
            r1.append(r8)     // Catch:{ Throwable -> 0x0066, all -> 0x0063 }
            java.lang.String r5 = r0.readLine()     // Catch:{ Throwable -> 0x0066, all -> 0x0063 }
            goto L_0x001a
        L_0x0029:
            java.lang.String r7 = r1.toString()     // Catch:{ Throwable -> 0x0066, all -> 0x0063 }
            r3.close()     // Catch:{ Throwable -> 0x0032 }
            r2 = r3
        L_0x0031:
            return r7
        L_0x0032:
            r6 = move-exception
            java.lang.String r8 = "H5Utils"
            java.lang.String r9 = "exception detail"
            com.alipay.mobile.nebula.util.H5Log.e(r8, r9, r6)
            r2 = r3
            goto L_0x0031
        L_0x003c:
            r6 = move-exception
        L_0x003d:
            java.lang.String r8 = "H5Utils"
            java.lang.String r9 = "Exception"
            com.alipay.mobile.nebula.util.H5Log.e(r8, r9, r6)     // Catch:{ all -> 0x0053 }
            if (r2 == 0) goto L_0x0031
            r2.close()     // Catch:{ Throwable -> 0x004a }
            goto L_0x0031
        L_0x004a:
            r6 = move-exception
            java.lang.String r8 = "H5Utils"
            java.lang.String r9 = "exception detail"
            com.alipay.mobile.nebula.util.H5Log.e(r8, r9, r6)
            goto L_0x0031
        L_0x0053:
            r8 = move-exception
        L_0x0054:
            if (r2 == 0) goto L_0x0059
            r2.close()     // Catch:{ Throwable -> 0x005a }
        L_0x0059:
            throw r8
        L_0x005a:
            r6 = move-exception
            java.lang.String r9 = "H5Utils"
            java.lang.String r10 = "exception detail"
            com.alipay.mobile.nebula.util.H5Log.e(r9, r10, r6)
            goto L_0x0059
        L_0x0063:
            r8 = move-exception
            r2 = r3
            goto L_0x0054
        L_0x0066:
            r6 = move-exception
            r2 = r3
            goto L_0x003d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebula.util.H5Utils.read(java.lang.String):java.lang.String");
    }

    /* JADX INFO: finally extract failed */
    public static byte[] readBytes(InputStream inputStream) {
        ByteArrayOutputStream byteBuffer = new PoolingByteArrayOutputStream();
        byte[] buffer = H5IOUtils.getBuf(1024);
        while (true) {
            try {
                int len = inputStream.read(buffer);
                if (len != -1) {
                    byteBuffer.write(buffer, 0, len);
                } else {
                    byte[] byteArray = byteBuffer.toByteArray();
                    H5IOUtils.returnBuf(buffer);
                    H5IOUtils.closeQuietly(byteBuffer);
                    H5IOUtils.closeQuietly(inputStream);
                    return byteArray;
                }
            } catch (Throwable th) {
                H5IOUtils.returnBuf(buffer);
                H5IOUtils.closeQuietly(byteBuffer);
                H5IOUtils.closeQuietly(inputStream);
                throw th;
            }
        }
    }

    public static String getClassName(Object object) {
        if (object == null) {
            return null;
        }
        String clazz = object.getClass().getCanonicalName();
        if (clazz == null) {
            return object.getClass().getName();
        }
        return clazz;
    }

    public static int parseInt(String text) {
        int result = 0;
        if (TextUtils.isEmpty(text)) {
            return 0;
        }
        try {
            result = Integer.parseInt(text);
        } catch (Throwable t) {
            H5Log.e(TAG, "parse int exception.", t);
        }
        return result;
    }

    public static long parseLong(String text) {
        long result = 0;
        if (TextUtils.isEmpty(text)) {
            return 0;
        }
        try {
            result = Long.parseLong(text);
        } catch (Throwable t) {
            H5Log.e(TAG, "parse long exception.", t);
        }
        return result;
    }

    public static float parseFloat(String text) {
        float result = 0.0f;
        if (TextUtils.isEmpty(text)) {
            return 0.0f;
        }
        try {
            result = Float.parseFloat(text);
        } catch (Throwable t) {
            H5Log.e(TAG, "parse long exception.", t);
        }
        return result;
    }

    public static final boolean isCss(String fileName) {
        return !TextUtils.isEmpty(fileName) && fileName.endsWith(".css");
    }

    public static final boolean isJavascript(String fileName) {
        return !TextUtils.isEmpty(fileName) && fileName.endsWith(".js");
    }

    public static final boolean isImage(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return false;
        }
        if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".gif") || fileName.endsWith(APImageFormat.SUFFIX_BMP) || fileName.endsWith(".tiff") || fileName.endsWith(".pcx") || fileName.endsWith(".tga") || fileName.endsWith(".exif") || fileName.endsWith(".fpx") || fileName.endsWith(".svg") || fileName.endsWith(".psd") || fileName.endsWith(".cdr") || fileName.endsWith(".pcd") || fileName.endsWith(".dxf") || fileName.endsWith(".ufo") || fileName.endsWith(".eps") || fileName.endsWith(".ai") || fileName.endsWith(".raw") || fileName.endsWith(".webp")) {
            return true;
        }
        return false;
    }

    public static boolean isInWifi() {
        String type = getNetworkType(getContext());
        H5Log.d(TAG, "getNetworkType " + type);
        return "WIFI".equals(type);
    }

    public static String getNetworkType(Context context) {
        NetworkInfo networkInfo = null;
        try {
            networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Throwable t) {
            H5Log.e(TAG, "get network info exception.", t);
        }
        if (networkInfo == null) {
            return NETWORK_TYPE_NOTREACHABLE;
        }
        switch (networkInfo.getType()) {
            case 1:
            case 9:
                return "WIFI";
            default:
                switch (((TelephonyManager) context.getSystemService("phone")).getNetworkType()) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                        return "2G";
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                    case 18:
                        return "3G";
                    case 13:
                        return "4G";
                    default:
                        return "UNKNOWN";
                }
        }
    }

    public static int dip2px(Context context, int dip) {
        try {
            return H5DimensionUtil.dip2px(context, (float) dip);
        } catch (Exception e) {
            H5Log.e((String) TAG, (Throwable) e);
            return 0;
        }
    }

    public static int pt2px(Context context, int dip) {
        try {
            return Math.round(TypedValue.applyDimension(3, (float) dip, context.getResources().getDisplayMetrics()));
        } catch (Exception e) {
            H5Log.e((String) TAG, (Throwable) e);
            return 0;
        }
    }

    public static Class<?> getClass(String bundleName, String className) {
        return H5WalletWrapper.getClass(bundleName, className);
    }

    public static Class<?> getClass(String bundleName, String className, boolean ignoreError) {
        return H5WalletWrapper.getClass(bundleName, className, ignoreError);
    }

    public static boolean isDebuggable(Context context) {
        if (isDebug != null) {
            return isDebug.booleanValue();
        }
        try {
            if ((context.getApplicationInfo().flags & 2) != 0) {
                return true;
            }
            return false;
        } catch (Throwable t) {
            H5Log.e(TAG, "exception detail", t);
            return false;
        }
    }

    public static boolean isDebug() {
        if (isDebug == null) {
            return isDebuggable(getContext());
        }
        return isDebug.booleanValue();
    }

    public static String toJSONString(Object object) {
        if (object == null) {
            return "";
        }
        return JSONObject.toJSONString(object);
    }

    public static String transMapToString(Map map) {
        String obj;
        StringBuffer sb = new StringBuffer();
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            StringBuffer append = sb.append(entry.getKey().toString()).append("'");
            if (entry.getValue() == null) {
                obj = "";
            } else {
                obj = entry.getValue().toString();
            }
            append.append(obj).append(iterator.hasNext() ? "^" : "");
        }
        return sb.toString();
    }

    public static Map transStringToMap(String mapString) {
        Map map = new HashMap();
        StringTokenizer entrys = new StringTokenizer(mapString, "^");
        while (entrys.hasMoreTokens()) {
            StringTokenizer items = new StringTokenizer(entrys.nextToken(), "'");
            map.put(items.nextToken(), items.hasMoreTokens() ? items.nextToken() : null);
        }
        return map;
    }

    public static final <T> T findServiceByInterface(String name) {
        return H5WalletWrapper.findServiceByInterface(name);
    }

    public static final <T> T getExtServiceByInterface(String name) {
        return H5WalletWrapper.findServiceByInterface(name);
    }

    @Nullable
    public static List<String> toStringArray(JSONArray jsonArray) {
        if (jsonArray != null) {
            try {
                int size = jsonArray.size();
                List list = new ArrayList();
                for (int i = 0; i < size; i++) {
                    list.add(jsonArray.getString(i));
                }
                return list;
            } catch (Throwable t) {
                H5Log.e(TAG, "toStringArray error: ", t);
            }
        }
        return null;
    }

    public static Map<String, Object> jsonToMap(String jsonStr) {
        return jsonToMap(parseObject(jsonStr));
    }

    public static Map<String, Object> jsonToMap(JSONObject jsonObject) {
        if (jsonObject == null || jsonObject.isEmpty()) {
            return null;
        }
        Map map = new HashMap();
        for (String key : jsonObject.keySet()) {
            try {
                map.put(key, jsonObject.get(key).toString());
            } catch (Exception e) {
                H5Log.e((String) TAG, (Throwable) e);
                return map;
            }
        }
        return map;
    }

    public static boolean isLogicIP(String ip) {
        if (ip == null) {
            return false;
        }
        if ("0.0.0.0".equals(ip.trim())) {
            return true;
        }
        return H5PatternHelper.matchRegex("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$", ip);
    }

    public static byte[] ipToBytesByReg(String ipAddr) {
        byte[] ret = new byte[4];
        try {
            String[] ipArr = ipAddr.split("\\.");
            ret[0] = (byte) (Integer.parseInt(ipArr[0]) & 255);
            ret[1] = (byte) (Integer.parseInt(ipArr[1]) & 255);
            ret[2] = (byte) (Integer.parseInt(ipArr[2]) & 255);
            ret[3] = (byte) (Integer.parseInt(ipArr[3]) & 255);
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

    public static Context getContext() {
        return H5WalletWrapper.getContext();
    }

    public static ThreadPoolExecutor getExecutor(String type) {
        return H5WalletWrapper.getExecutor(type);
    }

    public static ScheduledThreadPoolExecutor getScheduledExecutor() {
        return H5WalletWrapper.getScheduledExecutor();
    }

    public static void executeOrdered(String key, Runnable task) {
        H5WalletWrapper.executeOrdered(key, task);
    }

    public static Resources getNebulaResources() {
        return H5WalletWrapper.getNebulaResources();
    }

    public static Resources getNebulaBizResources() {
        return H5WalletWrapper.getNebulaBizResources();
    }

    public static Resources getNebulaCoreResources() {
        return H5WalletWrapper.getNebulaCoreResources();
    }

    public static H5ProviderManager getH5ProviderManager() {
        return H5WalletWrapper.getH5ProviderManager();
    }

    public static <T> T getProvider(String name) {
        H5ProviderManager h5ProviderManager = getH5ProviderManager();
        if (h5ProviderManager != null) {
            return h5ProviderManager.getProvider(name);
        }
        H5Log.e((String) TAG, (String) "h5ProviderManager == null");
        return null;
    }

    public static void setProvider(String name, Object provider) {
        H5ProviderManager h5ProviderManager = getH5ProviderManager();
        if (h5ProviderManager != null) {
            h5ProviderManager.setProvider(name, provider);
        } else {
            H5Log.e((String) TAG, (String) "h5ProviderManager == null");
        }
    }

    public static String getHpmFile(String appId, String version) {
        return H5WalletWrapper.getHpmFile(appId, version);
    }

    public static String getVersion() {
        try {
            return getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionName;
        } catch (Exception e) {
            H5Log.e((String) TAG, (Throwable) e);
            return "";
        }
    }

    public static void openUrl(String url) {
        if (isInWallet()) {
            if (!TextUtils.isEmpty(url) && url.startsWith("http")) {
                try {
                    url = URLEncoder.encode(url, "UTF-8");
                } catch (Exception e) {
                    H5Log.e((String) TAG, (Throwable) e);
                }
                url = "alipays://platformapi/startapp?appId=20000067&url=" + url;
            }
            H5EnvProvider h5EnvProvider = (H5EnvProvider) getProvider(H5EnvProvider.class.getName());
            if (h5EnvProvider != null) {
                h5EnvProvider.goToSchemeService(url);
                return;
            }
            return;
        }
        H5Service h5Service = H5ServiceUtils.getH5Service();
        if (h5Service != null) {
            H5Bundle h5Bundle = new H5Bundle();
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            h5Bundle.setParams(bundle);
            h5Service.startPage(null, h5Bundle);
        }
    }

    public static void openIntent(String url) {
        try {
            Intent launchIntent = Intent.parseUri(url, 1);
            launchIntent.addCategory("android.intent.category.BROWSABLE");
            launchIntent.setComponent(null);
            if (VERSION.SDK_INT >= 15) {
                launchIntent.setSelector(null);
            }
            Context context = getContext();
            launchIntent.setFlags(268435456);
            context.startActivity(launchIntent);
        } catch (Exception ex) {
            H5Log.e((String) TAG, "bad uri " + url + ": " + ex);
        }
    }

    public static boolean isInWallet() {
        Context context = getContext();
        if (context == null) {
            return true;
        }
        try {
            String pkgName = context.getPackageName();
            if (pkgName == null || !pkgName.contains("AlipayGphone")) {
                return false;
            }
            return true;
        } catch (Exception e) {
            H5Log.e((String) TAG, (Throwable) e);
            return true;
        }
    }

    public static void setNebulaAppCallback(int source, List<String> appIdList) {
        if (appIdList != null && !appIdList.isEmpty()) {
            H5Service h5Service = H5ServiceUtils.getH5Service();
            if (h5Service != null) {
                NebulaAppManager appManager = h5Service.getNebulaAppManager();
                if (appManager != null) {
                    List list = appManager.getNebulaAppCallbackList();
                    if (list != null && !list.isEmpty()) {
                        for (NebulaAppCallback callback : list) {
                            if (isDebuggable(getContext())) {
                                String log = "";
                                for (String id : appIdList) {
                                    log = log + Token.SEPARATOR + id;
                                }
                                H5Log.d(TAG, "setNebulaAppCallback " + getClassName(callback) + " source:" + source + " appIdList:" + log);
                            }
                            NebulaAppCallbackInfo nebulaAppCallbackInfo = new NebulaAppCallbackInfo();
                            nebulaAppCallbackInfo.setAppIdList(appIdList);
                            nebulaAppCallbackInfo.setCallBackSource(source);
                            callback.getCallback(nebulaAppCallbackInfo);
                        }
                    }
                }
            }
        }
    }

    public static boolean isMainProcess() {
        try {
            String pkgName = getContext().getPackageName();
            if (TextUtils.isEmpty(pkgName) || !pkgName.equalsIgnoreCase(getProcessName())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            H5Log.e((String) TAG, (Throwable) e);
            return true;
        }
    }

    public static String getProcessName() {
        try {
            if (!TextUtils.isEmpty(currentProcessName)) {
                return currentProcessName;
            }
            int pid = Process.myPid();
            for (RunningAppProcessInfo process : ((ActivityManager) getContext().getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses()) {
                if (process.pid == pid) {
                    currentProcessName = process.processName;
                }
            }
            return currentProcessName;
        } catch (Exception e) {
            H5Log.e((String) TAG, (Throwable) e);
        }
    }

    public static boolean isInTinyProcess() {
        String currentProcessName2 = getProcessName();
        return !TextUtils.isEmpty(currentProcessName2) && currentProcessName2.contains(ProcessInfo.ALIAS_LITE);
    }

    public static JSONObject getAuthInfo() {
        H5LoginProvider h5LoginProvider = (H5LoginProvider) getProvider(H5LoginProvider.class.getName());
        if (h5LoginProvider == null) {
            return null;
        }
        JSONObject result = new JSONObject();
        result.put((String) "nick", (Object) h5LoginProvider.getNick());
        result.put((String) "userAvatar", (Object) h5LoginProvider.getUserAvatar());
        return result;
    }

    public static boolean eventFromTinyProcess(H5Event h5Event) {
        if (h5Event == null || h5Event.getExtra() == null || !(h5Event.getExtra() instanceof JSONObject)) {
            return false;
        }
        return getBoolean((JSONObject) h5Event.getExtra(), (String) "isTinyApp", false);
    }

    public static void requestPermissions(Activity activity, String[] permissions, final H5PermissionCallback h5PermissionCallback) {
        if (!PermissionUtils.hasSelfPermissions(activity, permissions)) {
            if (VERSION.SDK_INT < 23 && h5PermissionCallback != null) {
                h5PermissionCallback.onRequestPermissionsResult(false);
            }
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().requestPermissions(permissions, 20000196, new RequestPermissionsResultCallback() {
                public final void onRequestPermissionsResult(int i, String[] strings, int[] ints) {
                    if (h5PermissionCallback != null) {
                        boolean success = PermissionUtils.verifyPermissions(ints);
                        H5Log.d(H5Utils.TAG, "requestPermissions onRequestPermissionsResult " + success);
                        h5PermissionCallback.onRequestPermissionsResult(success);
                    }
                }
            });
        } else if (h5PermissionCallback != null) {
            h5PermissionCallback.onRequestPermissionsResult(true);
            H5Log.d(TAG, "requestPermissions get true");
        }
    }

    public static String getShareLoadingScheme(String url, String appId, H5Page h5Page) {
        if (h5Page == null || h5Page.getParams() == null) {
            return url;
        }
        if (url.startsWith(BehavorReporter.PROVIDE_BY_ALIPAY)) {
            Uri alipaysUri = H5UrlHelper.parseUrl(url);
            if (alipaysUri == null) {
                return url;
            }
            String queryUrl = alipaysUri.getQueryParameter("url");
            String queryEnableTabbar = alipaysUri.getQueryParameter("enableTabBar");
            if (TextUtils.isEmpty(queryUrl) || !TextUtils.isEmpty(queryEnableTabbar) || TextUtils.isEmpty(queryUrl)) {
                return url;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(url);
            handleTabbarShareurl(H5TabbarUtils.getAbsoluteUrl(queryUrl, h5Page.getParams()), appId, h5Page, stringBuilder);
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        if (TextUtils.isEmpty(appId)) {
            appId = "20000067";
        }
        stringBuilder2.append("alipays://platformapi/startapp?appId=");
        stringBuilder2.append(appId);
        stringBuilder2.append("&url=");
        stringBuilder2.append(H5UrlHelper.encode(url));
        handleTabbarShareurl(url, appId, h5Page, stringBuilder2);
        return stringBuilder2.toString();
    }

    private static void handleTabbarShareurl(String url, String appId, H5Page h5Page, StringBuilder stringBuilder) {
        Bundle startParams = h5Page.getParams();
        String enableTabbar = getString(startParams, (String) "enableTabBar");
        H5Log.d(TAG, "generateAlipayScheme4Tabbar enableTabbar" + enableTabbar + ", url " + url);
        if (TextUtils.equals("YES", enableTabbar) && !TextUtils.isEmpty(url) && !url.contains("enableTabBar") && -1 == H5TabbarUtils.ifUrlMatch(appId, url, startParams)) {
            stringBuilder.append("&enableTabBar=NO");
        }
    }

    public static void setLdcLevel(String userId) {
        try {
            if (!TextUtils.isEmpty(userId) && userId.length() >= 11) {
                int id = Integer.parseInt(userId.substring(userId.length() - 3, userId.length() - 1));
                String levelId = "";
                if (id == 0) {
                    levelId = "1";
                } else if (id == 1 || id == 2) {
                    levelId = "3";
                } else if (id == 3 || id == 4) {
                    levelId = "5";
                } else if (id >= 5 && id <= 9) {
                    levelId = "10";
                } else if (id >= 10 && id <= 19) {
                    levelId = "20";
                } else if (id >= 20 && id <= 29) {
                    levelId = "30";
                } else if (id >= 30 && id <= 49) {
                    levelId = "50";
                }
                ldcLevel = levelId;
            }
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
        }
    }

    public static boolean isTinyApp(AppInfo appInfo) {
        return appInfo != null && appInfo.app_channel == 4;
    }

    public static boolean isInnerTinyApp(AppInfo appInfo) {
        return appInfo != null && appInfo.app_channel == 5;
    }

    public static boolean isUCM57() {
        H5UCProvider ucProvider = (H5UCProvider) getProvider(H5UCProvider.class.getName());
        return ucProvider != null && !ucProvider.isM40();
    }

    public static boolean containNebulaAddcors(String url) {
        return !TextUtils.isEmpty(url) && url.indexOf("nebula-addcors") > 0;
    }

    public static boolean enableCheckCrossOrigin() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_enableCheckCrossOrigin"))) {
            return true;
        }
        return false;
    }

    public static boolean enableJsApiPerformance() {
        if (isDebuggable(getContext())) {
            return true;
        }
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            return "yes".equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_enableJsApiPerformance"));
        }
        return false;
    }

    public static boolean enableExitAndStartAppOnMain() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_enableExitAndStartAppOnMain"))) {
            return true;
        }
        return false;
    }

    public static boolean addChooseImageCrossOrigin(String url) {
        return !TextUtils.isEmpty(url) && url.startsWith("https://resource/") && url.endsWith("image") && enableChooseImageCrossOrigin();
    }

    private static boolean enableChooseImageCrossOrigin() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfig("h5_enableChooseImageCrossOrigin"))) {
            return true;
        }
        return false;
    }

    public static boolean enableAddUseScan() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_enableAddUseScan"))) {
            return true;
        }
        return false;
    }

    public static boolean resolveExtApp(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        PackageManager pm = getContext().getPackageManager();
        Intent launchIntent = null;
        try {
            launchIntent = Intent.parseUri(url, 1);
        } catch (URISyntaxException e) {
            H5Log.e(TAG, "parse event exception.", e);
        }
        if (launchIntent != null) {
            launchIntent.addCategory("android.intent.category.BROWSABLE");
            launchIntent.setComponent(null);
        }
        if (pm.resolveActivity(launchIntent, 65536) != null) {
            return true;
        }
        return false;
    }

    public static void startExtActivity(Intent intent) {
        if (intent == null) {
            H5Log.w(TAG, "invalid event parameter");
            return;
        }
        Context context = getContext();
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    public static String getMaxLogStr(String logStr) {
        if (TextUtils.isEmpty(logStr)) {
            return logStr;
        }
        int maxLength = getMaxStrLength();
        if (maxLength == -1 || logStr.length() <= maxLength) {
            return logStr;
        }
        return logStr.substring(0, maxLength);
    }

    private static int getMaxStrLength() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            String value = h5ConfigProvider.getConfigWithProcessCache("h5_maxUrlLogLength");
            if (!TextUtils.isEmpty(value)) {
                try {
                    return Integer.parseInt(value);
                } catch (Throwable throwable) {
                    H5Log.e((String) TAG, throwable);
                }
            }
        }
        return 5000;
    }

    public static String getCleanUrl(String url) {
        if (!enableCleanUrl() || TextUtils.isEmpty(url)) {
            return url;
        }
        Uri uri = H5UrlHelper.parseUrl(url);
        if (uri == null) {
            return url;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String scheme = uri.getScheme();
        String authority = uri.getEncodedAuthority();
        String path = uri.getEncodedPath();
        stringBuilder.append(scheme);
        stringBuilder.append("://");
        stringBuilder.append(authority);
        stringBuilder.append(path);
        return stringBuilder.toString();
    }

    private static boolean enableCleanUrl() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_enableCleanUrl"))) {
            return true;
        }
        return false;
    }

    public static boolean isTinyMiniService(Bundle bundle) {
        String tinySource = getString(bundle, (String) "tinySource");
        String parentAppId = getString(bundle, (String) "parentAppId");
        String miniServiceId = getString(bundle, (String) "miniServiceId");
        if (TextUtils.isEmpty(tinySource) || TextUtils.isEmpty(parentAppId) || TextUtils.isEmpty(miniServiceId)) {
            return false;
        }
        H5Log.debug(TAG, "isTinyMiniService true");
        return true;
    }

    public static String loadJSScriptTag() {
        if (!isDebug() || !H5DevConfig.getBooleanConfig(H5DevConfig.H5_BUG_ME_JS_INJECTOR, false)) {
            return "";
        }
        String uid = "";
        H5LoginProvider h5LoginProvider = (H5LoginProvider) getProvider(H5LoginProvider.class.getName());
        if (h5LoginProvider != null) {
            uid = h5LoginProvider.getUserId();
        }
        return "<script charset=\"UTF-8\" src=\"" + "https://hpmweb.alipay.com/bugme/assets/mockScript" + "?timestamp=" + System.currentTimeMillis() + "&uid=" + uid + "\"></script>";
    }

    public static String getUserId() {
        H5LoginProvider h5LoginProvider = (H5LoginProvider) getProvider(H5LoginProvider.class.getName());
        if (h5LoginProvider != null) {
            return h5LoginProvider.getUserId();
        }
        return null;
    }

    public static String getUcVersion() {
        UcService ucService = H5ServiceUtils.getUcService();
        if (ucService != null) {
            return ucService.getUcVersion();
        }
        return "";
    }

    public static void handleTinyAppKeyEvent(String process, String section) {
        try {
            H5EventHandler h5EventHandlerService = (H5EventHandler) findServiceByInterface(H5EventHandlerService.class.getName());
            if (h5EventHandlerService != null) {
                h5EventHandlerService.onTinyAppProcessEvent(process, section);
            }
        } catch (Throwable thr) {
            H5Log.w(TAG, "handleTinyAppKeyEvent error", thr);
        }
    }

    public static void handleTinyAppKeyEvent(String[] processes, String section) {
        if (processes != null) {
            try {
                for (String handleTinyAppKeyEvent : processes) {
                    handleTinyAppKeyEvent(handleTinyAppKeyEvent, section);
                }
            } catch (Throwable thr) {
                H5Log.w(TAG, "handleTinyAppKeyEvent error", thr);
            }
        }
    }

    public static boolean canTransferH5ToTiny(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return false;
        }
        H5ApiManager h5TinyAppService = (H5ApiManager) getProvider(H5ApiManager.class.getName());
        if (h5TinyAppService == null) {
            return false;
        }
        Set whiteList = h5TinyAppService.getTransferToTinySet();
        if (whiteList != null) {
            return whiteList.contains(appId);
        }
        return false;
    }

    public static boolean canTransferH5ToTinyWithAnimation(String appId, Bundle startParams) {
        if (startParams == null || TextUtils.isEmpty(appId) || "yes".equalsIgnoreCase(getString(startParams, (String) "onlyOptionMenu")) || !canTransferH5ToTiny(appId)) {
            return false;
        }
        return true;
    }

    public static String getAppxMinVersion(AppInfo appInfo) {
        if (appInfo == null) {
            return null;
        }
        try {
            Map extendInfo = appInfo.extend_info;
            if (extendInfo == null || extendInfo.isEmpty()) {
                return null;
            }
            return JSON.parseObject(extendInfo.get(H5Param.LAUNCHER_PARAM)).getString("minSDKVersion");
        } catch (Throwable e) {
            H5Log.e((String) TAG, "getAppxMinVersion...e=" + e);
            return null;
        }
    }

    public static String getCurrentAvailableAppxVersion() {
        try {
            H5AppCenterPresetProvider h5AppCenterPresetProvider = (H5AppCenterPresetProvider) H5ServiceUtils.getH5Service().getProviderManager().getProvider(H5AppCenterPresetProvider.class.getName());
            if (h5AppCenterPresetProvider == null) {
                return null;
            }
            String appxAppId = h5AppCenterPresetProvider.getTinyCommonApp();
            String installedVersion = H5ServiceUtils.getAppDBService().findInstallAppVersion(appxAppId);
            if (!TextUtils.isEmpty(installedVersion)) {
                return installedVersion;
            }
            H5PresetPkg presetPkg = h5AppCenterPresetProvider.getH5PresetPkg();
            if (presetPkg == null) {
                return installedVersion;
            }
            Map presetInfoMap = presetPkg.getPreSetInfo();
            if (presetInfoMap == null) {
                return installedVersion;
            }
            H5PresetInfo presetInfo = presetInfoMap.get(appxAppId);
            if (presetInfo != null) {
                return presetInfo.version;
            }
            return installedVersion;
        } catch (Throwable th) {
            H5Log.e((String) TAG, (String) "getCurrentAvailableAppxVersion...");
            return null;
        }
    }

    public static String getConfigByConfigService(String configName) {
        String configValue = null;
        ConfigService configService = (ConfigService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(ConfigService.class.getName());
        if (configService == null) {
            H5Log.e((String) TAG, (String) "failed get config service");
        } else {
            long time = 0;
            if (isDebug()) {
                time = System.currentTimeMillis();
            }
            try {
                configValue = configService.getConfig(configName);
                String cost = "";
                if (isDebug()) {
                    cost = (System.currentTimeMillis() - time);
                }
                H5Log.d(TAG, "getConfig from ConfigService " + configName + Token.SEPARATOR + configValue + Token.SEPARATOR + cost);
            } catch (Throwable e) {
                H5Log.e(TAG, "getConfig exception", e);
            }
        }
        return configValue;
    }

    public static boolean isRemoteDebugConnected(String appId) {
        if (!TinyAppRemoteDebugInterceptorManager.get().isRemoteDebug()) {
            return false;
        }
        TinyAppRemoteDebugInterceptor interceptor = TinyAppRemoteDebugInterceptorManager.get().getTinyAppRemoteDebugInterceptor();
        if (interceptor != null) {
            return interceptor.isRemoteDebugConnected(appId);
        }
        return false;
    }

    public static boolean isVConsolePanelOpened() {
        if (TinyAppStorage.getInstance().getDebugPanelH5Page() != null) {
            return true;
        }
        return false;
    }

    public static void sendMsgToRemoteWorker(String msg) {
        TinyAppRemoteDebugInterceptor interceptor = TinyAppRemoteDebugInterceptorManager.get().getTinyAppRemoteDebugInterceptor();
        if (interceptor != null) {
            interceptor.sendMessageToRemoteWorker(msg);
        }
    }

    public static void sendMsgToRemoteWorkerOrVConsole(String appId, String type, String msg) {
        if (isRemoteDebugConnected(appId)) {
            sendMsgToRemoteWorker(type + ":" + msg);
            return;
        }
        H5Page debugH5Page = TinyAppStorage.getInstance().getDebugPanelH5Page();
        if (debugH5Page != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "type", (Object) type);
            jsonObject.put((String) "content", (Object) msg);
            JSONObject consoleData = new JSONObject();
            consoleData.put((String) "data", (Object) jsonObject);
            debugH5Page.getBridge().sendToWeb(H5VConsolePlugin.ON_TINY_DEBUG_CONSOLE, consoleData, null);
        }
    }

    public static String getAppxSDKVersion(String appId) {
        H5ApiManager h5TinyAppService = (H5ApiManager) getProvider(H5ApiManager.class.getName());
        if (h5TinyAppService == null) {
            return null;
        }
        return h5TinyAppService.getAppxSDKVersion(appId);
    }

    public static boolean isRemoteDebugSupport() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            return "yes".equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_tinyAppRemoteDebugLog"));
        }
        return false;
    }

    public static void handleTinyAppEnv(Context context) {
        try {
            Method checkEnvValid = Class.forName("com.autonavi.amapenvcheck.MEnvCheckUtil").getDeclaredMethod("checkEnvValid", new Class[]{Context.class});
            checkEnvValid.setAccessible(true);
            checkEnvValid.invoke(null, new Object[]{context});
        } catch (Throwable th) {
        }
    }
}
