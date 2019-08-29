package com.alipay.mobile.nebulacore.appcenter.center;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5PageLoader;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.service.H5AppCenterService;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.nebula.appcenter.H5AppDBService;
import com.alipay.mobile.nebula.appcenter.H5RpcFailResult;
import com.alipay.mobile.nebula.appcenter.api.H5LoadPresetListen;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5AppCenterPresetProvider;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5EnvProvider;
import com.alipay.mobile.nebula.provider.H5ProviderManager;
import com.alipay.mobile.nebula.provider.H5TinyAppProvider;
import com.alipay.mobile.nebula.startParam.H5StartParamManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ParamParser;
import com.alipay.mobile.nebula.util.H5PresetResUtil;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5WalletWrapper;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackagePool;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.manager.H5ProviderManagerImpl;
import com.alipay.mobile.nebulacore.util.H5TimeUtil;
import com.alipay.mobile.security.bio.workspace.Env;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class H5AppCenter {
    private static H5AppProvider a;

    public static void setupPage(Bundle bundle, boolean hasCheck) {
        H5Utils.handleTinyAppKeyEvent((String) "package_prepare", (String) "H5AppCenter.setupPage()");
        long time = System.currentTimeMillis();
        a = (H5AppProvider) H5ProviderManagerImpl.getInstance().getProvider(H5AppProvider.class.getName());
        String appId = H5Utils.getString(bundle, (String) "appId");
        if (H5Utils.canTransferH5ToTiny(appId)) {
            bundle.putString("canTransferH5ToTiny", "YES");
        }
        bundle.putBoolean("isNotTinyProcess", H5Utils.isMainProcess());
        boolean isH5App = a != null && a.isH5App(appId);
        boolean isNebulaApp = false;
        if (a != null && a.isNebulaApp(appId)) {
            isNebulaApp = true;
        }
        if (isNebulaApp) {
            isH5App = true;
        }
        bundle.putBoolean(H5Param.isH5app, isH5App);
        H5Log.d("H5AppCenter", "setupPage appId " + appId + " isH5App " + isH5App + " isNebulaApp:" + isNebulaApp);
        bundle.putBoolean(H5Param.IS_NEBULA_APP, isNebulaApp);
        if (!TextUtils.isEmpty(H5Utils.getString(bundle, (String) "appConfigJson")) && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfig("H5_appConfigJson"))) {
            H5TinyAppProvider h5TinyAppProvider = (H5TinyAppProvider) Nebula.getProviderManager().getProvider(H5TinyAppProvider.class.getName());
            if (h5TinyAppProvider != null) {
                h5TinyAppProvider.handlerOnAppConfig(bundle);
            }
        }
        if (!H5AppUtil.isH5ContainerAppId(appId)) {
            bundle.putString("appId", appId);
            a(bundle, isNebulaApp, hasCheck);
        }
        if (!H5Flag.ucReady && H5Utils.getBoolean(bundle, (String) H5Param.FIRST_INIT_NOT_INIT_GLOBAL_APP, false)) {
            H5Log.d("H5AppCenter", "!H5Flag.ucReady && FIRST_INIT_USE_ANDROID_WEBVIEW not H5GlobalPackage.prepare()");
        } else if (!H5Utils.isInTinyProcess()) {
            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                public final void run() {
                    long time = System.currentTimeMillis();
                    H5GlobalPackage.prepare();
                    H5Log.d("H5AppCenter", "H5GlobalPackage.prepare cost " + (System.currentTimeMillis() - time));
                }
            });
        }
        initTinyAppRes(bundle, false);
        b(appId, bundle);
        c(appId, bundle);
        boolean isH5TinyApp = H5Utils.getBoolean(bundle, (String) "isTinyApp", false);
        if (isH5TinyApp) {
            String appVersion = H5Utils.getString(bundle, (String) "appVersion");
            LocalBroadcastManager manager = LocalBroadcastManager.getInstance(H5Utils.getContext());
            Intent intent = new Intent();
            H5Log.d("H5AppCenter", "tinyApp create， appId is " + appId + " , appVersion is " + appVersion);
            intent.setAction("com.alipay.mobile.nebula.tinyAppCreate");
            intent.putExtra("appId", appId);
            intent.putExtra("appVersion", appVersion);
            manager.sendBroadcast(intent);
        }
        String launchParamsTag = H5Utils.getString(bundle, (String) H5StartParamManager.launchParamsTag);
        String page = H5Utils.getString(bundle, (String) "page");
        boolean useNew = false;
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            useNew = !TextUtils.equals("NO", h5ConfigProvider.getConfig("h5_useNewLaunchParamsTagConcat"));
        }
        if (!TextUtils.isEmpty(page)) {
            if (useNew) {
                H5Log.d("H5AppCenter", " get launchParamsTag from URL");
                try {
                    URL url = new URL(new URL("https://www.alipay.com"), page);
                    String path = url.getPath();
                    if (!TextUtils.isEmpty(path)) {
                        launchParamsTag = path.substring(1);
                    }
                } catch (Throwable t) {
                    H5Log.e((String) "H5AppCenter", t);
                }
            } else {
                H5Log.d("H5AppCenter", " get launchParamsTag from page:" + page);
                launchParamsTag = page;
            }
        }
        H5Log.debug("H5AppCenter", " get launchParamsTag " + launchParamsTag);
        boolean haH5Pkg = false;
        long timeMillis = System.currentTimeMillis();
        if (isNebulaApp) {
            haH5Pkg = true;
            boolean needLock = false;
            if (isH5TinyApp) {
                needLock = true;
            }
            if (!TextUtils.isEmpty(launchParamsTag)) {
                needLock = true;
            }
            H5ContentPackagePool.preparePackage(bundle, needLock);
        } else if (a != null && a.hasPackage(appId, null)) {
            H5ContentPackagePool.preparePackage(bundle, false);
            haH5Pkg = true;
        }
        bundle.putBoolean(Nebula.HAS_H5_PKG, haH5Pkg);
        H5Log.d("H5AppCenter", "H5ContentPackagePool.preparePackage cost " + (System.currentTimeMillis() - timeMillis));
        if (!H5Utils.contains(bundle, (String) H5Param.LONG_BACK_BEHAVIOR)) {
            String bb = isH5App ? "pop" : H5Param.DEFAULT_LONG_BACK_BEHAVIOR;
            bundle.putString(H5Param.LONG_BACK_BEHAVIOR, bb);
            H5Log.d("H5AppCenter", "set " + appId + " back behavior as " + bb);
        }
        if (!TextUtils.isEmpty(launchParamsTag)) {
            Bundle launcherParam = H5StartParamManager.getInstance().getH5StartParam(appId, launchParamsTag);
            if (launcherParam != null && !launcherParam.isEmpty()) {
                H5Log.d("H5AppCenter", "launchParamsTag " + launcherParam);
                bundle.putAll(launcherParam);
            }
        } else {
            Bundle launcher = H5StartParamManager.getInstance().getH5StartParam(appId, "index");
            if (launcher != null && !launcher.isEmpty()) {
                H5Log.d("H5AppCenter", "launcher " + launcher);
                bundle.putAll(launcher);
            }
        }
        H5ParamParser.parseMagicOptions(bundle, "H5AppCenter");
        H5ParamParser.parse(bundle, false);
        boolean preAuth = H5Utils.getBoolean(bundle, (String) H5Param.PRE_AUTH, false);
        String text = H5Utils.getString(bundle, (String) H5Param.PRE_AUTH);
        bundle.putBoolean("requestPreAuth", preAuth || "YES".equalsIgnoreCase(text) || "TRUE".equalsIgnoreCase(text));
        H5TimeUtil.timeLog(H5TimeUtil.PREPARE_APP, time);
    }

    public static void initTinyAppRes(Bundle bundle, boolean isFromPre) {
        boolean tinyPubRes = false;
        if ("yes".equalsIgnoreCase(H5Utils.getString(bundle, (String) "tinyPubRes"))) {
            tinyPubRes = true;
        }
        if (H5Utils.getBoolean(bundle, (String) "isTinyApp", false) || tinyPubRes) {
            H5AppCenterPresetProvider h5AppCenterPresetProvider = (H5AppCenterPresetProvider) Nebula.getProviderManager().getProvider(H5AppCenterPresetProvider.class.getName());
            if (h5AppCenterPresetProvider != null) {
                String id = h5AppCenterPresetProvider.getTinyCommonApp();
                if (!TextUtils.isEmpty(id)) {
                    boolean add = true;
                    if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_addTinyRes"))) {
                        add = false;
                    }
                    if (add) {
                        H5GlobalPackage.addResourcePackage(H5GlobalPackage.TINY_RES_KEY, id, false, isFromPre);
                    }
                }
            }
        }
    }

    private static boolean a() {
        if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_useInstallVersion"))) {
            return false;
        }
        return true;
    }

    private static void a(Bundle bundle, boolean isNebulaApp, boolean hasCheck) {
        if (a == null) {
            H5Log.e((String) "H5AppCenter", (String) "failed to get app info!");
        }
        AppInfo nebulaAppInfo = null;
        String appId = H5Utils.getString(bundle, (String) "appId");
        String appVersion = "";
        String nebula_loading_version = H5Utils.getString(bundle, (String) H5Param.NEBULA_LOADING_VERSION);
        H5Log.d("H5AppCenter", "nebula_loading_version: " + nebula_loading_version);
        if (!TextUtils.isEmpty(nebula_loading_version) && nebula_loading_version.startsWith(appId) && a()) {
            try {
                bundle.remove(H5Param.NEBULA_LOADING_VERSION);
                String[] array = nebula_loading_version.split("_");
                if (array.length == 2) {
                    String loadingVer = array[1];
                    if (!TextUtils.isEmpty(loadingVer)) {
                        H5AppDBService h5AppDBService = H5ServiceUtils.getAppDBService();
                        if (h5AppDBService != null) {
                            nebulaAppInfo = h5AppDBService.getAppInfo(appId, loadingVer);
                            if (nebulaAppInfo != null) {
                                appVersion = loadingVer;
                                H5Log.d("H5AppCenter", "use loadingVer " + loadingVer);
                            } else {
                                H5Log.d("H5AppCenter", "appInfo is null not use installAppVersion");
                            }
                        }
                    }
                }
            } catch (Throwable throwable) {
                H5Log.e((String) "H5AppCenter", throwable);
            }
        }
        if (TextUtils.isEmpty(appVersion)) {
            appVersion = a.getVersion(appId);
            H5Log.d("H5AppCenter", "get appVersion from nebula " + appVersion);
        }
        String installVersion = H5Utils.getString(bundle, (String) H5Param.INSTALL_VERSION);
        if (!TextUtils.isEmpty(installVersion)) {
            H5Log.d("H5AppCenter", "use installVersion " + installVersion);
            appVersion = installVersion;
        }
        H5Log.d("H5AppCenter", "appCenterVersion：" + appVersion);
        if (isNebulaApp && !hasCheck) {
            H5AppUtil.updateApp(appId, null);
        }
        bundle.putString("appVersion", appVersion);
        bundle.putString("version", appVersion);
        if (nebulaAppInfo == null) {
            nebulaAppInfo = a.getAppInfo(appId, appVersion);
        }
        if (nebulaAppInfo != null) {
            bundle.putString("release_type", nebulaAppInfo.release_type);
            JSONObject jsonObject = H5Utils.parseObject(nebulaAppInfo.extend_info_jo);
            bundle.putString(H5AppUtil.package_nick, H5Utils.getString(jsonObject, (String) H5AppUtil.package_nick));
            JSONObject lottieConfig = H5Utils.getJSONObject(jsonObject, H5AppUtil.lottie_animation, null);
            if (lottieConfig != null) {
                bundle.putString(H5AppUtil.lottie_animation, lottieConfig.toJSONString());
            }
        }
        boolean isH5tinyApp = H5Utils.isTinyApp(nebulaAppInfo);
        H5Log.d("H5AppCenter", appId + " isH5tinyApp " + isH5tinyApp);
        if (isH5tinyApp) {
            bundle.putBoolean(H5Param.isH5app, true);
            bundle.putInt("app_channel", 4);
        }
        bundle.putBoolean("isTinyApp", isH5tinyApp);
        String cdnHost = a.getH5AppCdnBaseUrl(appId, appVersion);
        H5Log.d("H5AppCenter", "appId " + appId + " appVersion" + appVersion + "  cdnHost " + cdnHost);
        String debugCdn = H5Utils.getString(bundle, (String) "debugCdn");
        if (H5Utils.isDebuggable(H5Utils.getContext()) && !TextUtils.isEmpty(debugCdn)) {
            cdnHost = URLDecoder.decode(debugCdn);
            H5Log.d("H5AppCenter", "set cdn host as debugCdn " + cdnHost);
        }
        bundle.putString(H5Param.CDN_HOST, cdnHost);
        String installPath = a.getInstallPath(appId, appVersion);
        H5Log.d("H5AppCenter", "appId " + appId + " installPath " + installPath);
        if (TextUtils.isEmpty(installPath) && a.hasPackage(appId, appVersion)) {
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_APP_UNZIPPATH_ERROR").param1().add(appId, null).param2().add(String.valueOf(a.isInstalled(appId, appVersion)), null).param4().add("appId", appId).add("version", appVersion));
        }
        if (!TextUtils.isEmpty(installPath)) {
            String offlineHost = "file://" + installPath;
            if (!offlineHost.endsWith("/")) {
                offlineHost = offlineHost + "/";
            }
            bundle.putString(H5Param.OFFLINE_HOST, offlineHost);
        }
        String extraStr = a.getExtraJo(appId, appVersion);
        if (!TextUtils.isEmpty(extraStr)) {
            JSONObject extraJo = H5Utils.parseObject(extraStr);
            if (extraJo != null && !extraJo.isEmpty()) {
                String usePresetPopmenu = extraJo.getString("usePresetPopmenu");
                if (!TextUtils.isEmpty(usePresetPopmenu)) {
                    bundle.putString("usePresetPopmenu", usePresetPopmenu);
                } else {
                    bundle.putString("usePresetPopmenu", "NO");
                }
            }
        }
        Map<String, String> extra = a.getExtra(appId, appVersion);
        String launchParams = "";
        if (extra != null) {
            launchParams = extra.get(H5Param.LAUNCHER_PARAM);
            if (!TextUtils.isEmpty(launchParams)) {
                bundle.putString(H5Param.LAUNCHER_PARAM, launchParams);
                a(launchParams, bundle);
                if (H5AppUtil.enableDSL(bundle) && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfig("h5_enableDSL"))) {
                    H5Log.d("H5AppCenter", "enableDSL set isTinyApp true");
                    bundle.putBoolean("isTinyApp", true);
                }
            }
            String host = extra.get("host");
            if (!TextUtils.isEmpty(host)) {
                bundle.putString("host", host);
                initAppHost(host, bundle);
            }
        }
        a(bundle, launchParams, isH5tinyApp);
    }

    private static void a(String text, Bundle bundle) {
        if (TextUtils.isEmpty(text)) {
            H5Log.w("H5AppCenter", "invalid launch parameters");
        } else {
            H5ParamParser.setLauncherParams(H5Utils.parseObject(text), bundle);
        }
    }

    public static void initAppHost(String strHost, Bundle bundle) {
        if (TextUtils.isEmpty(strHost)) {
            H5Log.w("H5AppCenter", "invalid app host parameters");
            return;
        }
        JSONObject joHost = H5Utils.parseObject(strHost);
        if (joHost == null || joHost.isEmpty()) {
            H5Log.w("H5AppCenter", "can't parse host parameter as json");
            return;
        }
        boolean enabled = H5Utils.getBoolean(joHost, (String) "enable", true);
        H5Log.w("H5AppCenter", "map host enabled " + enabled);
        bundle.putBoolean(H5Param.MAP_HOST, enabled);
        String onlineHost = null;
        if (H5Utils.isDebuggable(H5Utils.getContext())) {
            H5ProviderManager h5ProviderManager = H5Utils.getH5ProviderManager();
            H5EnvProvider provider = null;
            if (h5ProviderManager != null) {
                provider = (H5EnvProvider) h5ProviderManager.getProvider(H5EnvProvider.class.getName());
            }
            String rpcUrl = null;
            if (provider != null) {
                rpcUrl = provider.getRpcUrl();
            }
            if (!TextUtils.isEmpty(rpcUrl) && rpcUrl.contains("alipay.com")) {
                onlineHost = H5Utils.getString(joHost, (String) "online");
            } else if (!TextUtils.isEmpty(rpcUrl) && rpcUrl.contains("test.alipay.net")) {
                onlineHost = H5Utils.getString(joHost, (String) "test");
            } else if (TextUtils.isEmpty(rpcUrl) || !rpcUrl.contains("mobilegwpre.alipay.com")) {
                onlineHost = H5Utils.getString(joHost, (String) "dev");
            } else {
                onlineHost = H5Utils.getString(joHost, (String) Env.NAME_PRE);
            }
        }
        if (TextUtils.isEmpty(onlineHost)) {
            onlineHost = H5Utils.getString(joHost, (String) "online");
        }
        if (!TextUtils.isEmpty(onlineHost) && !onlineHost.endsWith("/")) {
            onlineHost = onlineHost + "/";
        }
        bundle.putString(H5Param.ONLINE_HOST, onlineHost);
        if (TextUtils.equals("NO", H5Environment.getConfigWithProcessCache("h5_preventClearSwInAppCenter")) || !bundle.getBoolean("preventAutoLoginLoop")) {
            Nebula.clearServiceWork(bundle);
        }
        H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
        if (h5EventHandlerService != null) {
            h5EventHandlerService.setStartParams(bundle);
        }
        H5Log.d("H5AppCenter", "onlineHost " + onlineHost);
    }

    private static void a(Bundle bundle, String launchParams, boolean isTinyApp) {
        String url;
        int appType;
        String url2 = null;
        String base64Url = H5Utils.getString(bundle, (String) H5Param.LONG_URL_WITH_ENTRY_KEY);
        boolean hasEntryParam = false;
        if (TextUtils.isEmpty(base64Url)) {
            base64Url = H5Utils.getString(bundle, (String) H5Param.LONG_URL_IN_BASE64);
        } else {
            hasEntryParam = true;
        }
        if (!TextUtils.isEmpty(base64Url)) {
            try {
                url2 = H5Utils.base64ToString(base64Url, 0);
                if (hasEntryParam) {
                    url2 = "/www/" + url2;
                }
            } catch (IllegalArgumentException e) {
                H5Log.e("H5AppCenter", "exception detail", e);
            }
        }
        if (TextUtils.isEmpty(url2)) {
            url2 = H5Utils.getString(bundle, (String) "url");
            H5Log.d("H5AppCenter", "getUrl form LONG_URL:" + url2);
        }
        if (TextUtils.isEmpty(url2)) {
            url2 = H5Utils.getString(bundle, (String) H5Param.URL);
            H5Log.d("H5AppCenter", "getUrl form URL:" + url2);
        }
        if (TextUtils.isEmpty(url2) && !TextUtils.isEmpty(launchParams)) {
            JSONObject jsonObject = H5Utils.parseObject(launchParams);
            url2 = H5Utils.getString(jsonObject, (String) "url");
            if (TextUtils.isEmpty(url2)) {
                url2 = H5Utils.getString(jsonObject, (String) H5Param.URL);
                H5Log.d("H5AppCenter", "getUrl form launchParams u " + url2);
            } else {
                H5Log.d("H5AppCenter", "getUrl form launchParams url " + url2);
            }
        }
        if (TextUtils.isEmpty(url)) {
            H5Log.e((String) "H5AppCenter", (String) "both url and entry are empty, FATAL_ERROR!");
            return;
        }
        String onlineHost = H5Utils.getString(bundle, (String) H5Param.ONLINE_HOST);
        String offLineHost = H5Utils.getString(bundle, (String) H5Param.OFFLINE_HOST);
        boolean mapHost = H5Utils.getBoolean(bundle, (String) H5Param.MAP_HOST, false);
        if (url.startsWith("/")) {
            String url3 = url.substring(1, url.length());
            if (mapHost) {
                url = onlineHost + url3;
            } else {
                url = offLineHost + url3;
            }
            if (mapHost) {
                appType = 1;
            } else {
                appType = 0;
            }
        } else if (!TextUtils.isEmpty(onlineHost) && url.startsWith(onlineHost)) {
            appType = 1;
        } else if (TextUtils.isEmpty(offLineHost) || !url.startsWith(offLineHost)) {
            appType = 2;
        } else {
            appType = 0;
        }
        if (isTinyApp || "yes".equalsIgnoreCase(H5Utils.getString(bundle, (String) H5Param.ENABLE_DSL))) {
            String page = H5Utils.getString(bundle, (String) "page");
            if (!TextUtils.isEmpty(page)) {
                boolean useNew = false;
                H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                if (h5ConfigProvider != null) {
                    useNew = !TextUtils.equals("NO", h5ConfigProvider.getConfig("h5_useNewPageParamConcat"));
                }
                if (!useNew) {
                    int anchorIndex = url.indexOf(35);
                    if (anchorIndex != -1) {
                        H5Log.d("H5AppCenter", "page:" + page + " beforeUrl " + url);
                        url = url.substring(0, anchorIndex + 1) + page;
                    }
                } else if (!bundle.containsKey("preventAutoLoginLoop") || !H5Utils.getBoolean(bundle, (String) "preventAutoLoginLoop", false)) {
                    url = onlineHost + "index.html#" + page;
                }
            }
        }
        H5Log.d("H5AppCenter", "set final url " + url + " appType " + appType);
        H5PageLoader.mainUrl = url;
        bundle.putString("url", url);
        bundle.remove(H5Param.URL);
        bundle.putInt("appType", appType);
    }

    private static void b(String mainAppId, Bundle bundle) {
        H5Log.d("H5AppCenter", "addLauncherParamResourcePackage for " + mainAppId);
        Set<String> packageList = new HashSet<>();
        boolean needUnzipPresetResource = false;
        JSONArray nbPkgResList = H5Utils.parseArray(H5Utils.getString(bundle, (String) "nbpkgres"));
        if (nbPkgResList != null && nbPkgResList.size() > 0) {
            H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) Nebula.getProviderManager().getProvider(H5ConfigProvider.class.getName());
            if (h5ConfigProvider != null) {
                JSONObject joConfig = H5Utils.parseObject(h5ConfigProvider.getConfig("h5_pkgresmode"));
                String value = H5Utils.getString(joConfig, (String) "switchextend");
                int limit = 3;
                try {
                    limit = Integer.parseInt(H5Utils.getString(joConfig, (String) H5RpcFailResult.LIMIT, (String) "3"));
                } catch (NumberFormatException e) {
                    H5Log.e((String) "H5AppCenter", (Throwable) e);
                }
                if (!CameraParams.FLASH_MODE_OFF.equalsIgnoreCase(value)) {
                    int size = nbPkgResList.size();
                    if (size > 0) {
                        if (size > limit) {
                            H5Log.d("H5AppCenter", "packageList.size() : " + nbPkgResList.size() + " limit : " + limit);
                            return;
                        }
                        for (int i = 0; i < size; i++) {
                            String appId = nbPkgResList.getString(i);
                            if (appId.equalsIgnoreCase(H5PresetResUtil.APP_RESOURCE_PACKAGE_ID)) {
                                needUnzipPresetResource = true;
                            }
                            if (!appId.equals(mainAppId)) {
                                packageList.add(appId);
                            }
                        }
                    }
                } else {
                    return;
                }
            }
        }
        if (needUnzipPresetResource) {
            long time = System.currentTimeMillis();
            H5Log.d("H5AppCenter", "h5_specialSyncUnzip " + H5PresetResUtil.APP_RESOURCE_PACKAGE_ID + " begin");
            final long j = time;
            AnonymousClass2 r0 = new H5LoadPresetListen() {
                public final void getPresetPath(String path) {
                    if (!TextUtils.isEmpty(path)) {
                        H5Log.d("H5AppCenter", "h5_specialSyncUnzip " + H5PresetResUtil.APP_RESOURCE_PACKAGE_ID + " finish cost " + ((System.currentTimeMillis() - j)));
                    }
                }
            };
            H5PresetResUtil.unzipPresetResourcePkgByPageSetup(r0);
        }
        try {
            for (String appId2 : packageList) {
                H5GlobalPackage.addResourcePackage(mainAppId, appId2, true, false);
            }
        } catch (Throwable t) {
            H5Log.e("H5AppCenter", "addResourcePackage", t);
        }
    }

    private static void c(String mainAppId, Bundle bundle) {
        String launchUrl = H5Utils.getString(bundle, (String) "url");
        final Set launchUrls = new HashSet();
        if (Nebula.isTinyWebView(bundle)) {
            mainAppId = H5Utils.getString(bundle, (String) "parentAppId");
            H5Log.d("H5AppCenter", "in tiny web-view, use " + mainAppId + " as appId");
            JSONObject configObj = H5Utils.parseObject(H5WalletWrapper.getConfigWithProcessCache("h5_tinyAppPublicUrl"));
            if ("YES".equalsIgnoreCase(H5Utils.getString(configObj, (String) "enable"))) {
                JSONArray array = H5Utils.getJSONArray(configObj, mainAppId, null);
                if (array != null && array.size() > 0) {
                    int size = array.size();
                    for (int i = 0; i < size; i++) {
                        try {
                            launchUrls.add(array.getString(i));
                        } catch (Throwable t) {
                            H5Log.e("H5AppCenter", "parse h5_tinyAppPublicUrl config error", t);
                        }
                    }
                }
            }
        } else if (!TextUtils.isEmpty(launchUrl)) {
            launchUrls.add(launchUrl);
        }
        if (launchUrls.size() != 0) {
            final String finalMainAppId = mainAppId;
            H5Utils.getExecutor(H5ThreadType.NORMAL).execute(new Runnable() {
                public final void run() {
                    boolean z;
                    H5Log.d("H5AppCenter", "begin findUrlMappedAppId " + finalMainAppId);
                    H5AppCenterService appCenterService = H5ServiceUtils.getAppCenterService();
                    if (appCenterService != null) {
                        if (!"NO".equalsIgnoreCase(H5Utils.getString(H5Utils.parseObject(H5WalletWrapper.getConfigWithProcessCache("h5_resManifest")), (String) "matchMainUrl", (String) null))) {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (z) {
                            for (String launchUrl : launchUrls) {
                                String appId = appCenterService.findUrlMappedAppId(launchUrl);
                                H5Log.d("H5AppCenter", "findUrlMappedAppId " + appId + " for launchUrl " + launchUrl);
                                if (!TextUtils.isEmpty(appId) && !appId.equals(finalMainAppId)) {
                                    H5GlobalPackage.addResourcePackage(finalMainAppId, appId, true, false);
                                }
                            }
                        }
                    }
                    H5Log.d("H5AppCenter", "end findUrlMappedAppId " + finalMainAppId);
                }
            });
        }
    }
}
