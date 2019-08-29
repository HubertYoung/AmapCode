package com.alipay.mobile.nebulacore;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager.TaskDescription;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.Toast;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CallBack;
import com.alipay.mobile.h5container.api.H5Context;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5ImageListener;
import com.alipay.mobile.h5container.api.H5ImageLoader;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList;
import com.alipay.mobile.nebula.appcenter.apphandler.H5InstallAppAdvice;
import com.alipay.mobile.nebula.appcenter.apphandler.H5TinyAppDebugMode;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadCallback;
import com.alipay.mobile.nebula.appcenter.res.H5ResourceManager;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.callback.H5AppInstallCallback;
import com.alipay.mobile.nebula.dev.H5BugMeManager;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.process.H5EventHandler;
import com.alipay.mobile.nebula.provider.H5AppCenterPresetProvider;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5EnvProvider;
import com.alipay.mobile.nebula.provider.H5ImageProvider;
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.provider.H5ProviderManager;
import com.alipay.mobile.nebula.provider.H5TinyAppProvider;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5DomainUtil;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5ImageUtil;
import com.alipay.mobile.nebula.util.H5KeepAliveUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ParamParser;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5StatusBarUtils;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory;
import com.alipay.mobile.nebulacore.api.NebulaService;
import com.alipay.mobile.nebulacore.core.H5EventDispatcher;
import com.alipay.mobile.nebulacore.core.H5SessionImpl;
import com.alipay.mobile.nebulacore.core.NebulaServiceImpl;
import com.alipay.mobile.nebulacore.dev.provider.H5BugMeManagerImpl;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.manager.H5ProviderManagerImpl;
import com.alipay.mobile.nebulacore.plugin.H5UrlInterceptPlugin;
import com.alipay.mobile.nebulacore.ui.H5Activity;
import com.alipay.mobile.nebulacore.ui.H5Activity.H5Activity1;
import com.alipay.mobile.nebulacore.ui.H5Activity.H5Activity2;
import com.alipay.mobile.nebulacore.ui.H5Activity.H5Activity3;
import com.alipay.mobile.nebulacore.ui.H5Activity.H5Activity4;
import com.alipay.mobile.nebulacore.ui.H5Activity.H5Activity5;
import com.alipay.mobile.nebulacore.ui.H5FragmentManager;
import com.alipay.mobile.nebulacore.ui.H5MainProcTinyActivity;
import com.alipay.mobile.nebulacore.ui.H5MainProcTinyTransActivity;
import com.alipay.mobile.nebulacore.ui.H5TransActivity;
import com.alipay.mobile.nebulacore.ui.H5TransActivity.H5TransActivity2;
import com.alipay.mobile.nebulacore.ui.H5TransActivity.H5TransActivity3;
import com.alipay.mobile.nebulacore.ui.H5TransActivity.H5TransActivity4;
import com.alipay.mobile.nebulacore.ui.H5TransActivity.H5TransActivity5;
import com.alipay.mobile.nebulacore.util.AndroidBug5497Workaround;
import com.alipay.mobile.nebulacore.wallet.WalletContext;
import com.alipay.mobile.nebulacore.web.H5BridgePolicy;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Nebula {
    public static final boolean DEBUG = H5Utils.isDebuggable(H5Utils.getContext());
    public static final String DSL_ERROR = "dsl_error";
    public static final String H5_PAGE_RESUME = "h5_page_resume";
    public static final String HAS_H5_PKG = "hasH5Pkg";
    public static Class[] LITE_PROCESS_H5TRANS_ACTIVITY = {H5TransActivity2.class, H5TransActivity3.class, H5TransActivity4.class, H5TransActivity5.class};
    public static Class[] LITE_PROCESS_H5_ACTIVITY = {H5Activity1.class, H5Activity2.class, H5Activity3.class, H5Activity4.class, H5Activity5.class};
    public static final int LOAD_FROM_CORE = 1;
    public static final int LOAD_FROM_UC = 0;
    private static NebulaService a = null;
    public static final String appResume = "appResume";
    private static H5EventDispatcher b;
    private static Boolean c = null;
    private static H5BugMeManager d;
    private static int e = 1;
    private static int f = 0;
    private static H5EventHandler g;
    private static H5ApiManager h;
    public static boolean h5_dev_uc = false;
    private static Boolean i = null;
    public static boolean isDSL = false;

    public static int getWebViewId() {
        int i2 = e;
        e = i2 + 1;
        return i2;
    }

    public static int getPageId() {
        int i2 = f;
        f = i2 + 1;
        return i2;
    }

    public static NebulaService getService() {
        synchronized (Nebula.class) {
            if (a == null) {
                a = new NebulaServiceImpl();
            }
        }
        return a;
    }

    public static H5EventDispatcher getDispatcher() {
        synchronized (Nebula.class) {
            try {
                if (b == null) {
                    b = new H5EventDispatcher();
                }
            }
        }
        return b;
    }

    public static H5BugMeManager getH5BugMeManager() {
        synchronized (Nebula.class) {
            if (d == null) {
                d = new H5BugMeManagerImpl();
            }
        }
        return d;
    }

    public static H5ProviderManager getProviderManager() {
        return H5ProviderManagerImpl.getInstance();
    }

    public static boolean isDelayRender(Bundle bundle) {
        boolean delayRender = H5Utils.getBoolean(bundle, (String) H5Param.LONG_DELAY_RENDER, false);
        if (!delayRender) {
            return false;
        }
        H5Log.d("H5Nebula", "param delayRender " + delayRender);
        boolean isDelayRender = H5Utils.getBoolean(H5Utils.parseObject(H5Environment.getConfig(H5Utils.KEY_H5_COMMON_CONFIG)), (String) "h5_enableDelayRender", false);
        H5Log.d("H5Nebula", "config delayRender " + isDelayRender);
        return isDelayRender;
    }

    public static List<PackageInfo> getPackageInfos(Context ctx) {
        try {
            return ctx.getPackageManager().getInstalledPackages(0);
        } catch (Throwable throwable) {
            H5Log.e((String) "H5Nebula", throwable);
            return null;
        }
    }

    public static String getUCMPackageName(List<PackageInfo> pis) {
        if (pis == null) {
            return null;
        }
        for (PackageInfo pi : pis) {
            if (pi.packageName.equals("com.UCMobile")) {
                return "com.UCMobile";
            }
            if (pi.packageName.equals("com.UCMobile.intl")) {
                return "com.UCMobile.intl";
            }
            if (pi.packageName.equals("com.UCMobile.yunos")) {
                return "com.UCMobile.yunos";
            }
        }
        return null;
    }

    public static void startUCMIntentLoadUrl(Context context, Uri url, String ucmPkgName, H5BridgeContext bridgeContext) {
        if (ucmPkgName != null) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setDataAndType(url, "text/html");
            intent.setPackage(ucmPkgName);
            intent.setComponent(new ComponentName(ucmPkgName, "com.UCMobile.main.UCMobile"));
            intent.putExtra("uc_partner", "UCM_OPEN_FROM_ALIPAY_WEBVIEWSDK");
            try {
                context.startActivity(intent);
                if (bridgeContext != null) {
                    bridgeContext.sendSuccess();
                }
            } catch (Exception e2) {
                H5Log.e("H5Nebula", "startActivity exception.", e2);
                if (bridgeContext != null) {
                    bridgeContext.sendBridgeResult("success", Boolean.valueOf(false));
                }
            }
        }
    }

    public static boolean isRooted() {
        if (c != null) {
            return c.booleanValue();
        }
        boolean ret = false;
        Object value = null;
        try {
            value = Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class}).invoke(null, new Object[]{"ro.secure"});
        } catch (Throwable e2) {
            H5Log.e((String) DictionaryKeys.ENV_ROOT, "root " + e2.getMessage());
        }
        if (value != null && "1".equals(value)) {
            ret = false;
        } else if (value != null && "0".equals(value)) {
            ret = true;
        }
        if (!ret) {
            if (new File("/system/bin/su").exists()) {
                ret = true;
            } else if (new File("/system/xbin/su").exists()) {
                ret = true;
            }
        }
        Boolean valueOf = Boolean.valueOf(ret);
        c = valueOf;
        return valueOf.booleanValue();
    }

    public static boolean useH5StatusBar(H5Page h5Page) {
        if (h5Page == null) {
            return false;
        }
        boolean isTransparent = H5Utils.getBoolean(h5Page.getParams(), (String) H5Param.LONG_TRANSPARENT, false);
        if (!H5StatusBarUtils.isSupport() || !H5StatusBarUtils.isConfigSupport() || isTransparent) {
            return false;
        }
        return true;
    }

    public static boolean isTinyResAppId(String appId) {
        H5AppCenterPresetProvider h5AppCenterPresetProvider = (H5AppCenterPresetProvider) getProviderManager().getProvider(H5AppCenterPresetProvider.class.getName());
        if (h5AppCenterPresetProvider != null) {
            return TextUtils.equals(appId, h5AppCenterPresetProvider.getTinyCommonApp());
        }
        return false;
    }

    @WorkerThread
    public static void prepare(final String appId, final String version, final H5AppInstallCallback callback) {
        final H5AppProvider provider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (!TextUtils.isEmpty(appId) && provider != null) {
            if (!provider.isAvailable(appId, version)) {
                H5Log.d("H5Nebula", "[prepareApp] downloadApp appId:" + appId + " version:" + version);
                provider.downloadApp(appId, version, new H5DownloadCallback(true, true, callback));
                return;
            }
            H5Log.d("H5Nebula", "[prepareApp] install App appId:" + appId + " version:" + version);
            H5Utils.getExecutor(isTinyResAppId(appId) ? H5ThreadType.URGENT : H5ThreadType.IO).execute(new Runnable() {
                public final void run() {
                    provider.installApp(appId, version, callback);
                }
            });
        }
    }

    public static void commonParamParse(Bundle bundle) {
        H5ParamParser.parseMagicOptions(bundle, "H5Nebula");
        H5ParamParser.parse(bundle, H5Param.LONG_DELAY_RENDER, false);
        H5ParamParser.parse(bundle, H5Param.LONG_TRANSPARENT, false);
        H5ParamParser.parse(bundle, H5Param.LONG_FULLSCREEN, false);
        H5ParamParser.parse(bundle, H5Param.LONG_LANDSCAPE, false);
        H5ParamParser.parse(bundle, H5Param.LONG_TRANS_ANIMATE, false);
        H5ParamParser.parse(bundle, H5Param.NAV_SEARCH_BAR_PLACEHOLDER, false);
        H5ParamParser.parse(bundle, H5Param.NAV_SEARCH_BAR_VALUE, false);
        H5ParamParser.parse(bundle, H5Param.NAV_SEARCH_BAR_MAX_LENGTH, false);
    }

    public static Intent commonStartActivity(Context context, Bundle bundle) {
        Class clazz;
        try {
            Intent intent = new Intent();
            int lpid = 0;
            if (getH5EventHandler() != null) {
                lpid = getH5EventHandler().getLitePid();
                H5Log.d("H5Nebula", "lpid " + lpid);
            }
            boolean isTransparent = H5Utils.getBoolean(bundle, (String) H5Param.LONG_TRANSPARENT, false);
            boolean fullScreen = H5Utils.getBoolean(bundle, (String) H5Param.LONG_FULLSCREEN, false);
            String url = H5Utils.getString(bundle, (String) "url");
            if (fullScreen && !TextUtils.isEmpty(url) && H5AppUtil.isH5ContainerAppId(H5Utils.getString(bundle, (String) "appId"))) {
                H5ConfigProvider provider = (H5ConfigProvider) getProviderManager().getProvider(H5ConfigProvider.class.getName());
                if (!H5DomainUtil.isSomeDomainInternal(url, H5Environment.getConfig("h5_enableFullscreenList")) && (provider == null || !provider.isAliDomains(url))) {
                    fullScreen = false;
                    bundle.putBoolean(H5Param.LONG_FULLSCREEN, false);
                }
            }
            String landscape = H5Utils.getString(bundle, (String) H5Param.LONG_LANDSCAPE);
            if (fullScreen) {
                H5Log.d("H5Nebula", "fullScreen true,put transparent ");
                bundle.putBoolean(H5Param.LONG_TRANSPARENT, true);
            }
            boolean delayRender = isDelayRender(bundle);
            bundle.putBoolean(H5Param.LONG_DELAY_RENDER, delayRender);
            H5Log.d("H5Nebula", "config delayRender " + delayRender + " isTransparent " + isTransparent + " lpid:" + lpid);
            if (lpid == 0) {
                String appId = H5Utils.getString(bundle, (String) "appId", (String) null);
                Class transActivityClz = H5TransActivity.class;
                Class normalActivityClz = H5Activity.class;
                if (H5AppUtil.enableDSL(bundle) && H5KeepAliveUtil.enableMainProcKeepAlive(appId)) {
                    Activity activity = H5KeepAliveUtil.getRunningActivity();
                    if (activity instanceof H5Activity) {
                        ((H5Activity) activity).getH5Session().exitSession();
                        H5KeepAliveUtil.removeRunningTinyActivity();
                    }
                    if (activity == null || !activity.isFinishing()) {
                        H5Log.d(H5KeepAliveUtil.TAG, "use keep alive activity");
                        transActivityClz = H5MainProcTinyTransActivity.class;
                        normalActivityClz = H5MainProcTinyActivity.class;
                        H5KeepAliveUtil.updateFromActivityInMain();
                        intent.setFlags(268435456);
                        H5KeepAliveUtil.hasStartActivity = true;
                    } else {
                        H5Log.d(H5KeepAliveUtil.TAG, "activity finishing, not use keep alive activity");
                    }
                }
                if (delayRender || isTransparent) {
                    clazz = transActivityClz;
                } else {
                    clazz = normalActivityClz;
                }
            } else {
                getH5EventHandler().prepare();
                clazz = (delayRender || isTransparent) ? LITE_PROCESS_H5TRANS_ACTIVITY[lpid - 1] : LITE_PROCESS_H5_ACTIVITY[lpid - 1];
            }
            ComponentName componentName = new ComponentName(context, clazz);
            intent.setComponent(componentName);
            intent.putExtra("showLoadingView", isTransparent);
            intent.addFlags(65536);
            intent.putExtra(H5Param.LONG_FULLSCREEN, fullScreen);
            intent.putExtra(H5Param.LONG_LANDSCAPE, landscape);
            intent.putExtras(bundle);
            H5Log.d("H5Nebula", "commonStartActivity class: " + clazz + " bundle: " + bundle);
            return intent;
        } catch (Exception e2) {
            H5Log.e((String) "H5Nebula", (Throwable) e2);
            return null;
        }
    }

    public static void openInBrowser(H5Page h5Page, String url, H5BridgeContext bridgeContext) {
        if (!a() || !enableOpenScheme(url, h5Page.getParams())) {
            Uri uri = H5UrlHelper.parseUrl(url);
            if (uri != null) {
                String scheme = uri.getScheme();
                H5Log.d("H5Nebula", "openInBrowser scheme " + scheme);
                if (h5Page != null) {
                    String ucmPkgName = getUCMPackageName(getPackageInfos(h5Page.getContext().getContext()));
                    if (!TextUtils.isEmpty(ucmPkgName) && (TextUtils.equals(scheme, "http") || TextUtils.equals(scheme, "https"))) {
                        startUCMIntentLoadUrl(h5Page.getContext().getContext(), uri, ucmPkgName, bridgeContext);
                        return;
                    }
                }
                Intent openIntent = new Intent("android.intent.action.VIEW", uri);
                openIntent.setFlags(268435456);
                if (openIntent.resolveActivity(H5Environment.getContext().getPackageManager()) != null) {
                    H5Environment.startActivity(null, openIntent);
                    if (bridgeContext != null) {
                        bridgeContext.sendSuccess();
                    }
                } else if (bridgeContext != null) {
                    bridgeContext.sendBridgeResult("success", Boolean.valueOf(false));
                }
            }
        } else {
            H5Log.d("H5Nebula", "openInBrowser goToSchemeService : " + url);
        }
    }

    public static boolean enableOpenScheme(String url, Bundle params) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        boolean open = true;
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) getProviderManager().getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null && BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfig("h5_enableStartAppWithScheme"))) {
            open = false;
        }
        if (!open) {
            return false;
        }
        H5EnvProvider h5EnvProvider = (H5EnvProvider) getProviderManager().getProvider(H5EnvProvider.class.getName());
        if (h5EnvProvider == null || !h5EnvProvider.goToSchemeService(url, params)) {
            return false;
        }
        return true;
    }

    private static boolean a() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) getProviderManager().getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfig("h5_enableOpenInBrowserSchema"))) {
            return true;
        }
        return false;
    }

    public static void loadImage(String imageUrl, H5ImageListener h5ImageListener) {
        H5ImageUtil.loadImage(imageUrl, h5ImageListener);
    }

    public static void loadImageKeepSize(String imageUrl, final H5ImageListener h5ImageListener) {
        H5ImageProvider h5ImageProvider = (H5ImageProvider) getProviderManager().getProvider(H5ImageProvider.class.getName());
        if (h5ImageProvider != null) {
            h5ImageProvider.loadImageKeepSize(imageUrl, new H5ImageListener() {
                public final void onImage(Bitmap bitmap) {
                    if (h5ImageListener != null) {
                        h5ImageListener.onImage(bitmap);
                    }
                }
            });
        } else {
            H5Utils.getExecutor("RPC").execute(new H5ImageLoader(imageUrl, new H5ImageListener() {
                public final void onImage(Bitmap bitmap) {
                    if (h5ImageListener != null) {
                        h5ImageListener.onImage(bitmap);
                    }
                }
            }));
        }
    }

    public static void initSession(String appId, Bundle bundle, H5Context h5Context) {
        String sessionId;
        try {
            if (TextUtils.isEmpty(appId) || TextUtils.equals("10000111", appId)) {
                sessionId = H5Environment.getSessionId(h5Context, bundle);
            } else {
                sessionId = "session_" + appId;
            }
            if (h5Context instanceof WalletContext) {
                MicroApplication microApplication = ((WalletContext) h5Context).getMicroApplication();
                if (microApplication != null) {
                    sessionId = sessionId + "_" + microApplication.hashCode();
                }
            }
            bundle.putString("sessionId", sessionId);
            H5Log.d("H5Nebula", "sessionId " + sessionId);
            getService().getSession(sessionId);
        } catch (Exception e2) {
            H5Log.e((String) "H5Nebula", (Throwable) e2);
        }
    }

    public static void setWindowSoftInputMode(Activity activity, String appId, Bundle bundle, boolean isTransparent) {
        String useResize = H5Utils.getString(bundle, (String) "adjustResize");
        if ("yes".equalsIgnoreCase(useResize)) {
            H5Log.d("H5Nebula", "useResize " + useResize);
        } else if (!a(appId)) {
            return;
        }
        H5Log.d("H5Nebula", " AndroidBug5497Workaround ");
        AndroidBug5497Workaround.assistActivity(activity, isTransparent);
    }

    private static boolean a(String appId) {
        JSONObject appIdList = H5Utils.parseObject(H5Environment.getConfigWithProcessCache("h5_setH5AndroidBug5497Workaround"));
        if (appIdList != null && !appIdList.isEmpty()) {
            for (String key : appIdList.keySet()) {
                try {
                    JSONArray value = (JSONArray) appIdList.get(key);
                    if (TextUtils.equals(appId, key) && (value == null || value.isEmpty() || value.contains(Integer.valueOf(VERSION.SDK_INT)))) {
                        return true;
                    }
                } catch (Exception e2) {
                    H5Log.e((String) "H5Nebula", (Throwable) e2);
                }
            }
        }
        return false;
    }

    public static void removeBridgeTimeParam(JSONObject params) {
        if (params != null && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfig("h5_removeBridgeTimeParam"))) {
            if (params.containsKey(H5Param.ASYNCINDEX)) {
                params.remove(H5Param.ASYNCINDEX);
            }
            if (params.containsKey("perf_prepare_time")) {
                params.remove("perf_prepare_time");
            }
            if (params.containsKey("perf_open_app_time")) {
                params.remove("perf_open_app_time");
            }
            if (params.containsKey(Const.PERF_IS_PRELOAD)) {
                params.remove(Const.PERF_IS_PRELOAD);
            }
            if (params.containsKey("is_local")) {
                params.remove("is_local");
            }
            if (params.containsKey(Const.KEY_LITE_PROCESS_ID)) {
                params.remove(Const.KEY_LITE_PROCESS_ID);
            }
            if (params.containsKey(PointCutConstants.REALLY_STARTAPP)) {
                params.remove(PointCutConstants.REALLY_STARTAPP);
            }
            if (params.containsKey(PointCutConstants.REALLY_DOSTARTAPP)) {
                params.remove(PointCutConstants.REALLY_DOSTARTAPP);
            }
            if (params.containsKey(H5Param.LONG_PACKAGE_LOADING_SHOWN)) {
                params.remove(H5Param.LONG_PACKAGE_LOADING_SHOWN);
            }
            if (params.containsKey(H5Param.LONG_SAFEPAY_CONTEXT)) {
                params.remove(H5Param.LONG_SAFEPAY_CONTEXT);
            }
            if (params.containsKey(H5Param.LONG_REPORTURL)) {
                params.remove(H5Param.LONG_REPORTURL);
            }
            H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (provider != null && "YES".equalsIgnoreCase(provider.getConfigWithProcessCache("h5_removeUseScan"))) {
                params.remove("schemeInnerSource");
                params.remove(H5Utils.SCAN_TYPE_KEY);
            }
            if (params.containsKey(H5Param.FEEDBACK_EXT_PARAMS)) {
                params.remove(H5Param.FEEDBACK_EXT_PARAMS);
            }
            if (params.containsKey(H5Param.AUTH_CODE_KEY)) {
                params.remove(H5Param.AUTH_CODE_KEY);
            }
        }
    }

    public static String loadJsBridge(HashMap<String, String> bridgeParams, int type, int viewId) {
        String bridgeStr = H5ResourceManager.getRaw(R.raw.h5_bridge);
        if (DEBUG && H5FileUtil.exists((String) "/sdcard/h5_bridge_debug.js")) {
            bridgeStr = H5FileUtil.read((String) "/sdcard/h5_bridge_debug.js");
        }
        if (TextUtils.isEmpty(bridgeStr)) {
            H5Log.d("H5Nebula", "no bridge data defined!");
            return bridgeStr;
        }
        String paramsStr = "";
        for (String key : bridgeParams.keySet()) {
            paramsStr = paramsStr + ";AlipayJSBridge." + key + "=" + bridgeParams.get(key) + ";";
        }
        if (!TextUtils.isEmpty(paramsStr)) {
            bridgeStr = bridgeStr.replace("AlipayJSBridge.startupParams='{startupParams}'", paramsStr);
        } else {
            H5Log.d("H5Nebula", "no params data defined!");
        }
        if (H5BridgePolicy.get() == 1) {
            bridgeStr = bridgeStr.replace("var messenger=window.__alipayConsole__||window.console,log=messenger.log", "var messenger=window,log=window.prompt");
        }
        if (type == 1) {
            bridgeStr = bridgeStr.replace("console.log(\"begin load AlipayJSBridge\");", "console.log(\"begin load AlipayJSBridge from core raw\");");
        } else if (type == 0) {
            bridgeStr = bridgeStr.replace("console.log(\"begin load AlipayJSBridge\");", "console.log(\"begin load AlipayJSBridge from uc provider\");");
        }
        return b(bridgeStr.replace("'{APVIEWID}'", String.valueOf(viewId)));
    }

    private static String b(String bridgeStr) {
        H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        String result = bridgeStr;
        if (provider != null) {
            String configStr = provider.getConfigWithProcessCache("h5_patchJsbridge");
            if (TextUtils.isEmpty(configStr)) {
                return result;
            }
            result = result + "\n" + configStr;
        }
        return result;
    }

    public static void initInfo(final String appId, final Activity activity) {
        H5Utils.runOnMain(new Runnable() {
            public final void run() {
                Nebula.b(appId, activity);
            }
        }, 5000);
    }

    /* access modifiers changed from: private */
    public static void b(final String appId, final Activity activity) {
        if (VERSION.SDK_INT >= 21 && H5Utils.isInTinyProcess()) {
            try {
                H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
                    @TargetApi(21)
                    public final void run() {
                        if (activity != null && !activity.isFinishing()) {
                            H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
                            if (h5AppProvider != null) {
                                final String appName = h5AppProvider.getWalletAppName(appId);
                                String logUrl = h5AppProvider.getWalletIconUrl(appId);
                                H5Log.d("H5Nebula", "appName " + appName + " logUrl " + logUrl);
                                if (TextUtils.isEmpty(logUrl)) {
                                    activity.setTaskDescription(new TaskDescription(appName, null, 0));
                                    return;
                                }
                                final String finalLogUrl = logUrl;
                                Nebula.loadImage(logUrl, new H5ImageListener() {
                                    public void onImage(Bitmap bitmap) {
                                        H5Log.d("H5Nebula", "logUrl " + finalLogUrl + " Bitmap " + bitmap);
                                        activity.setTaskDescription(new TaskDescription(appName, bitmap, 0));
                                    }
                                });
                            }
                        }
                    }
                });
            } catch (Exception e2) {
                H5Log.e((String) "H5Nebula", (Throwable) e2);
            }
        }
    }

    public static boolean dispatchProcess(H5Event event, H5BridgeContext h5BridgeContext) {
        if (getH5EventHandler() == null || !getH5EventHandler().enableHandler(event.getAction())) {
            return false;
        }
        getH5EventHandler().handlerAction(event, h5BridgeContext);
        H5Log.d("H5Nebula", "H5ProcessUtil handlerAction ");
        return true;
    }

    public static H5EventHandler getH5EventHandler() {
        if (g == null) {
            g = (H5EventHandler) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
        }
        return g;
    }

    public static boolean needPageKeepAlive(H5Page h5Page, Activity activity) {
        boolean sessionExited;
        int fragmentCount;
        if (h5Page == null) {
            return false;
        }
        H5Session session = h5Page.getSession();
        if (session == null || session.isExited()) {
            sessionExited = true;
        } else {
            sessionExited = false;
        }
        if (!h5Page.isTinyApp() || sessionExited || !(activity instanceof H5Activity)) {
            return false;
        }
        H5Session h5Session = getService().getSession(H5Utils.getString(h5Page.getParams(), (String) "sessionId"));
        H5FragmentManager h5FragmentManager = ((H5Activity) activity).getH5FragmentManager();
        if (h5FragmentManager != null) {
            fragmentCount = h5FragmentManager.getFragmentCount();
        } else {
            fragmentCount = 0;
        }
        if (h5Session != null && (h5Session instanceof H5SessionImpl) && ((H5SessionImpl) h5Session).getH5SessionTabManager() != null && fragmentCount == ((H5SessionImpl) h5Session).getH5SessionTabManager().countTabFragments() && isTaskRoot(activity)) {
            return true;
        }
        if (fragmentCount != 1 || !isTaskRoot(activity)) {
            return false;
        }
        return true;
    }

    public static Stack<H5Page> getSessionPagesWithOutPrerender(Stack<H5Page> sessionPages) {
        Stack sessionPagesWithOutPrerender = (Stack) sessionPages.clone();
        Stack result = (Stack) sessionPagesWithOutPrerender.clone();
        Iterator it = sessionPagesWithOutPrerender.iterator();
        while (it.hasNext()) {
            H5Page page = (H5Page) it.next();
            if (H5Utils.getBoolean(page.getParams(), (String) H5Param.LONG_ISPRERENDER, false)) {
                result.remove(page);
            }
        }
        return result;
    }

    public static boolean doKeepAlive(Activity activity, Bundle bundle) {
        String appId = H5Utils.getString(bundle, (String) "appId");
        H5Log.d(H5KeepAliveUtil.TAG, "doKeepAlive for " + appId);
        boolean enableKeepAlive = H5KeepAliveUtil.enableKeepAlive(bundle, appId);
        if (enableKeepAlive && H5Utils.getBoolean(bundle, (String) DSL_ERROR, false)) {
            H5Log.d(H5KeepAliveUtil.TAG, "dslError true set enableKeepAlive==false");
            enableKeepAlive = false;
        }
        if (H5InstallAppAdvice.enableUseDevMode(bundle) && H5TinyAppDebugMode.enableTinyAppDebugMode() && !enableKeepAlive) {
            H5DevAppList.getInstance().remove(appId);
        }
        if (enableKeepAlive || !H5Utils.isMainProcess()) {
            H5EventHandler h5EventHandlerService = getH5EventHandler();
            if (h5EventHandlerService != null) {
                h5EventHandlerService.moveTaskToBackAndStop(activity, enableKeepAlive);
            }
            H5KeepAliveUtil.updateKeepAliveTime();
            return true;
        }
        H5Log.d(H5KeepAliveUtil.TAG, "not enableKeepAlive in main");
        return false;
    }

    public static boolean isTaskRoot(Activity activity) {
        return activity.isTaskRoot();
    }

    public static void moveTaskToBack(Activity activity) {
        if (getH5EventHandler() != null) {
            getH5EventHandler().moveTaskToBack(activity);
        }
    }

    public static int getHeight(H5Page h5Page, float density, DisplayMetrics displayMetrics) {
        boolean getHeightFromWebView = true;
        String getHeightWebview = H5Environment.getConfigWithProcessCache("h5_getWebViewHeight");
        if (!TextUtils.isEmpty(getHeightWebview) && BQCCameraParam.VALUE_NO.equalsIgnoreCase(getHeightWebview)) {
            getHeightFromWebView = false;
        }
        if (getHeightFromWebView) {
            return Math.round(((float) h5Page.getWebView().getView().getHeight()) / density);
        }
        if (displayMetrics != null) {
            return Math.round(((float) displayMetrics.heightPixels) / density);
        }
        return 0;
    }

    public static boolean useSW(Bundle bundle) {
        boolean isTinyApp = H5Utils.getBoolean(bundle, (String) "isTinyApp", false);
        boolean useSw = false;
        if ("yes".equalsIgnoreCase(H5Utils.getString(bundle, (String) H5Param.USE_SW))) {
            useSw = true;
        }
        if (isTinyApp || useSw) {
            return true;
        }
        return false;
    }

    public static void sendAppResume(H5Page h5Page, Bundle bundle, H5CallBack callBack) {
        H5Bridge h5Bridge = h5Page.getBridge();
        if (h5Bridge != null) {
            if (H5Utils.getBoolean(h5Page.getParams(), (String) "isTinyApp", false) || !TextUtils.isEmpty(H5Utils.getString(h5Page.getParams(), (String) "MINI-PROGRAM-WEB-VIEW-TAG"))) {
                H5TinyAppProvider h5TinyAppProvider = (H5TinyAppProvider) H5Utils.getProvider(H5TinyAppProvider.class.getName());
                if (h5TinyAppProvider != null) {
                    bundle = h5TinyAppProvider.handlerAppResume(h5Page, bundle);
                }
            }
            JSONObject data = new JSONObject();
            data.put((String) "data", (Object) H5Utils.toJSONObject(bundle));
            h5Bridge.sendToWeb(appResume, data, callBack);
        }
    }

    public static void clearServiceWork(Bundle bundle) {
        if (useSW(bundle)) {
            H5Service h5Service = H5ServiceUtils.getH5Service();
            if (h5Service != null) {
                String swHost = H5Utils.getString(bundle, (String) H5Param.ONLINE_HOST);
                H5Log.d("H5Nebula", "swHost " + swHost);
                if (!TextUtils.isEmpty(swHost)) {
                    h5Service.clearServiceWorker(swHost);
                }
            }
        }
    }

    public static boolean disableHWACByUCStyle() {
        if (TextUtils.equals("YES", H5Environment.getConfigWithProcessCache("h5_disableHWACByUCStyle"))) {
            return true;
        }
        return false;
    }

    public static void checkOffline(final Activity activity, final String appId) {
        try {
            H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
                public final void run() {
                    if (H5AppUtil.isOffLine(appId)) {
                        H5Log.w("H5Nebula", "appId:" + appId + " isOffline,not to startPage");
                        H5LogUtil.logNebulaTech(H5LogData.seedId("h5_app_offline").param1().add(appId, null).param2().add("app被应用中心下线了", null));
                        if ("yes".equalsIgnoreCase(H5Environment.getConfig("h5_use_log_offline"))) {
                            H5Utils.runOnMain(new Runnable() {
                                public void run() {
                                    if (activity != null && !activity.isFinishing()) {
                                        Toast.makeText(H5Utils.getContext(), H5Environment.getResources().getString(R.string.h5_app_offline), 0).show();
                                        activity.finish();
                                    }
                                }
                            });
                        } else if (activity != null && !activity.isFinishing() && H5Utils.isMainProcess()) {
                            activity.finish();
                            H5AppProvider h5AppProvider = (H5AppProvider) Nebula.getProviderManager().getProvider(H5AppProvider.class.getName());
                            if (h5AppProvider != null) {
                                h5AppProvider.showOfflinePage(appId, null);
                            }
                        }
                    }
                }
            });
        } catch (Throwable t) {
            H5Log.e("H5Nebula", "catch exception ", t);
        }
    }

    public static H5LogProvider getH5LogHandler() {
        return (H5LogProvider) getProviderManager().getProvider(H5LogProvider.class.getName());
    }

    public static H5ApiManager getH5TinyAppService() {
        if (h == null) {
            h = (H5ApiManager) getProviderManager().getProvider(H5ApiManager.class.getName());
        }
        return h;
    }

    public static boolean enableThrow() {
        if ("yes".equalsIgnoreCase(H5Environment.getConfig("h5_plugin_throwException"))) {
            return true;
        }
        return false;
    }

    public static Bundle copyBundle(Bundle bundle) {
        Bundle copyBundle = null;
        if (bundle == null) {
            bundle = new Bundle();
        }
        try {
            copyBundle = (Bundle) bundle.clone();
        } catch (Throwable throwable) {
            H5Log.e((String) "H5Nebula", throwable);
        }
        H5Log.d("H5Nebula", "copyBundle " + copyBundle);
        return copyBundle;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=com.alipay.mobile.h5container.api.H5Page, code=com.alipay.mobile.h5container.api.H5CoreNode, for r10v0, types: [com.alipay.mobile.h5container.api.H5CoreNode, com.alipay.mobile.h5container.api.H5Page] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean supportJsaApi(com.alipay.mobile.h5container.api.H5CoreNode r10, java.lang.String r11) {
        /*
            java.lang.Boolean r3 = i
            if (r3 != 0) goto L_0x004c
            long r4 = java.lang.System.currentTimeMillis()
            r0 = 0
            r2 = r10
        L_0x000a:
            boolean r3 = android.text.TextUtils.isEmpty(r11)
            if (r3 != 0) goto L_0x0021
            if (r0 != 0) goto L_0x0021
            if (r2 == 0) goto L_0x0021
            com.alipay.mobile.h5container.api.H5PluginManager r1 = r2.getPluginManager()
            com.alipay.mobile.h5container.api.H5CoreNode r2 = r2.getParent()
            boolean r0 = r1.canHandle(r11)
            goto L_0x000a
        L_0x0021:
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r0)
            i = r3
            java.lang.String r3 = "H5Nebula"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "supportGetLocation: "
            r6.<init>(r7)
            java.lang.Boolean r7 = i
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = " "
            java.lang.StringBuilder r6 = r6.append(r7)
            long r8 = java.lang.System.currentTimeMillis()
            long r8 = r8 - r4
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.String r6 = r6.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r3, r6)
        L_0x004c:
            java.lang.Boolean r3 = i
            boolean r3 = r3.booleanValue()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.Nebula.supportJsaApi(com.alipay.mobile.h5container.api.H5Page, java.lang.String):boolean");
    }

    public static boolean isTinyWebView(Bundle bundle) {
        H5ApiManager h5ApiManager = getH5TinyAppService();
        if (h5ApiManager != null) {
            String tag = h5ApiManager.getWebViewTag();
            if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(H5Utils.getString(bundle, tag))) {
                return true;
            }
        }
        return false;
    }

    public static boolean interceptSchemeForTiny(String url, H5Page h5Page) {
        boolean shouldIntercept;
        if (url == null) {
            return false;
        }
        if (url.startsWith("http") || url.startsWith("javascript")) {
            return false;
        }
        if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_interceptSchemeForTiny"))) {
            return false;
        }
        boolean hasPermissionFile = false;
        if (h5Page == null) {
            return false;
        }
        String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
        if (getH5TinyAppService() != null) {
            hasPermissionFile = getH5TinyAppService().hasPermissionFile(appId, h5Page);
        }
        if (!hasPermissionFile) {
            return false;
        }
        if (!getH5TinyAppService().hasPermissionOnScheme(appId, url, h5Page)) {
            shouldIntercept = true;
        } else {
            shouldIntercept = false;
        }
        if (!shouldIntercept) {
            return shouldIntercept;
        }
        H5LogUtil.logNebulaTech(H5LogData.seedId("H5_AL_NETWORK_PERMISSON_ERROR").param1().add("nothasPermissionOnScheme", null).param3().add("BanMainURL", url));
        h5Page.loadUrl(new StringBuilder(H5UrlInterceptPlugin.xiaochengxuUrlHeader).append(H5UrlHelper.encode(url)).toString());
        return shouldIntercept;
    }

    public static boolean enableNativeKeyboard(H5Page h5Page) {
        if (h5Page == null || !H5Utils.getBoolean(h5Page.getParams(), (String) "isTinyApp", false)) {
            return false;
        }
        JSONObject configObj = H5Utils.parseObject(H5Environment.getConfig("h5_nativeInput4Android"));
        if (configObj == null || !"YES".equalsIgnoreCase(H5Utils.getString(configObj, (String) FunctionSupportConfiger.SWITCH_TAG))) {
            return false;
        }
        String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
        JSONArray blackList = H5Utils.getJSONArray(configObj, "blackList", null);
        if (blackList == null || blackList.isEmpty() || !blackList.contains(appId)) {
            return true;
        }
        return false;
    }

    public static boolean enableLongClick(H5Page h5Page) {
        if (!H5Utils.getBoolean(h5Page.getParams(), (String) "isTinyApp", false)) {
            return true;
        }
        JSONObject configObj = H5Utils.parseObject(H5Environment.getConfig("h5_disableLongClick4AndroidInTiny"));
        if (configObj == null || !"YES".equalsIgnoreCase(H5Utils.getString(configObj, (String) "disable"))) {
            return true;
        }
        String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
        JSONArray whiteList = H5Utils.getJSONArray(configObj, "whiteList", null);
        if (whiteList == null || whiteList.isEmpty() || !whiteList.contains(appId)) {
            return false;
        }
        return true;
    }
}
