package com.alipay.mobile.nebula.appcenter.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ApplicationDescription;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.nebula.appcenter.H5AppDBService;
import com.alipay.mobile.nebula.appcenter.H5PresetInfo;
import com.alipay.mobile.nebula.appcenter.H5PresetPkg;
import com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppScoreList;
import com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList;
import com.alipay.mobile.nebula.appcenter.apphandler.H5TinyAppDebugMode;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadCallback;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.model.AppReq;
import com.alipay.mobile.nebula.appcenter.model.AppRes;
import com.alipay.mobile.nebula.callback.H5AppInstallCallback;
import com.alipay.mobile.nebula.callback.H5UpdateAppCallback;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5AppCenterPresetProvider;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.provider.H5ProviderManager;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5AppEngineList;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ParamParser;
import com.alipay.mobile.nebula.util.H5PatternHelper;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobileappconfig.core.model.hybirdPB.PackInfoReq;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class H5AppUtil {
    private static String TAG = "H5AppUtil";
    public static final String api_permission = "api_permission";
    private static final String[] appIds = {"20000067", "20000095", "20000096", "20000097", "20000098", "20000099"};
    private static Set<String> appSet = null;
    public static final String app_channel = "app_channel";
    public static final String app_type = "app_type";
    public static String currentPsd = "";
    public static final String down_type = "download_type";
    public static final String local_report = "local_report";
    public static final String lottie_animation = "lottieAnimation";
    public static final String package_nick = "package_nick";
    public static final String patchDict = "patchDict";
    public static final String preset = "preset";
    private static final String[] publicAppIds = {"20000101", "20000042", "20000249"};
    private static Set<String> publicAppSet = null;
    public static final String release_type = "release_type";
    public static final String scene = "scene";
    public static String secAppId = "";
    public static final String third_platform = "third_platform";

    public static String matchAppId(String url) {
        String str = "";
        try {
            if (!url.contains("h5app") && !url.contains("hybrid")) {
                return null;
            }
            if (url.contains("h5app")) {
                str = "\\d+\\.h5app\\.(alipay|m\\.taobao)\\.(net|com)";
            } else if (url.contains("hybrid")) {
                str = "^\\d+[.]hybrid[.]alipay-eco[.](com|net)$";
            }
            Pattern pattern = H5PatternHelper.compile(str);
            String host = H5UrlHelper.getHost(url);
            if (pattern == null || TextUtils.isEmpty(host)) {
                return null;
            }
            Matcher matcher = pattern.matcher(host);
            if (!matcher.find()) {
                return null;
            }
            H5Log.d(TAG, "group:" + matcher.group() + " url:" + url);
            String result = matcher.group();
            if (!TextUtils.isEmpty(result)) {
                return result.substring(0, result.indexOf("."));
            }
            return null;
        } catch (Exception e) {
            H5Log.e(TAG, (Throwable) e);
            return null;
        }
    }

    public static boolean isH5ContainerAppId(String appId) {
        if (appSet == null) {
            HashSet hashSet = new HashSet();
            appSet = hashSet;
            hashSet.addAll(Arrays.asList(appIds));
        }
        return appSet.contains(appId);
    }

    public static boolean isPublicAppId(String appId) {
        if (publicAppSet == null) {
            HashSet hashSet = new HashSet();
            publicAppSet = hashSet;
            hashSet.addAll(Arrays.asList(publicAppIds));
        }
        return publicAppSet.contains(appId);
    }

    public static List<String> getAppIds() {
        List ids = new LinkedList();
        ids.addAll(Arrays.asList(appIds));
        return ids;
    }

    public static AppInfo toAppInfo(JSONObject data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        AppInfo appInfo = new AppInfo();
        appInfo.app_id = H5Utils.getString(data, (String) "app_id");
        if (!TextUtils.isEmpty(H5Utils.getString(data, (String) "size"))) {
            appInfo.size = Long.parseLong(H5Utils.getString(data, (String) "size"));
        }
        appInfo.patch = H5Utils.getString(data, (String) "patch");
        appInfo.online = H5Utils.getInt(data, (String) "online");
        appInfo.fallback_base_url = H5Utils.getString(data, (String) "fallback_base_url");
        appInfo.package_url = H5Utils.getString(data, (String) "package_url");
        appInfo.version = H5Utils.getString(data, (String) "version");
        appInfo.app_dsec = H5Utils.getString(data, (String) "app_desc");
        appInfo.vhost = H5Utils.getString(data, (String) "vhost");
        appInfo.nbl_id = H5Utils.getString(data, (String) "nbl_id");
        appInfo.name = H5Utils.getString(data, (String) "name");
        appInfo.slogan = H5Utils.getString(data, (String) "slogan");
        JSONObject extendInfo = H5Utils.getJSONObject(data, "extend_info", null);
        String permission = H5Utils.getString(data, (String) "api_permission");
        if (!TextUtils.isEmpty(permission)) {
            extendInfo.put((String) "api_permission", (Object) permission);
        }
        String packageId = H5Utils.getString(data, (String) package_nick);
        if (!TextUtils.isEmpty(packageId)) {
            extendInfo.put((String) package_nick, (Object) packageId);
        }
        String sceneValue = H5Utils.getString(data, (String) scene);
        if (!TextUtils.isEmpty(sceneValue)) {
            extendInfo.put((String) scene, (Object) sceneValue);
        }
        appInfo.scene = sceneValue;
        appInfo.localReport = H5Utils.getInt(data, (String) local_report);
        JSONObject launchParams = H5Utils.getJSONObject(extendInfo, H5Param.LAUNCHER_PARAM, null);
        String nbAppType = H5Utils.getString(launchParams, (String) H5Param.LONG_NB_APP_TYPE);
        if (!TextUtils.isEmpty(nbAppType)) {
            appInfo.nbAppType = nbAppType;
        } else {
            appInfo.nbAppType = H5Utils.getString(launchParams, (String) H5Param.NB_APP_TYPE);
        }
        int presetValue = H5Utils.getInt(data, (String) preset);
        if (presetValue != 0) {
            extendInfo.put((String) preset, (Object) Integer.valueOf(presetValue));
        }
        appInfo.extend_info_jo = extendInfo.toJSONString();
        appInfo.third_platform = data.getString(third_platform);
        appInfo.app_type = H5Utils.getInt(data, (String) app_type);
        appInfo.app_channel = H5Utils.getInt(data, (String) "app_channel");
        appInfo.main_url = H5Utils.getString(data, (String) "main_url");
        appInfo.system_max = H5Utils.getString(data, (String) "system_max");
        appInfo.system_min = H5Utils.getString(data, (String) "system_min");
        appInfo.auto_install = H5Utils.getInt(data, (String) down_type);
        appInfo.icon_url = H5Utils.getString(data, (String) "icon_url");
        appInfo.release_type = H5Utils.getString(data, (String) "release_type");
        if (TextUtils.isEmpty(appInfo.release_type)) {
            appInfo.release_type = "ONLINE";
        }
        if (TextUtils.isEmpty(appInfo.patch)) {
            JSONObject patchDir = H5Utils.getJSONObject(data, patchDict, null);
            String installVersion = "";
            if (patchDir != null && !patchDir.isEmpty()) {
                H5AppDBService h5AppDBService = H5ServiceUtils.getAppDBService();
                if (h5AppDBService != null) {
                    installVersion = h5AppDBService.findInstallAppVersion(appInfo.app_id);
                    if (!TextUtils.isEmpty(installVersion)) {
                        Iterator it = patchDir.keySet().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            try {
                                String patchVersion = it.next();
                                String patchUrl = patchDir.get(patchVersion).toString();
                                if (TextUtils.equals(patchVersion, installVersion)) {
                                    appInfo.patch = patchUrl;
                                    break;
                                }
                            } catch (Exception e) {
                                H5Log.e(TAG, (Throwable) e);
                            }
                        }
                    }
                }
            }
            H5Log.d(TAG, "patchDir " + patchDir + " installVersion " + installVersion + " appInfo.patch：" + appInfo.patch);
        }
        if ("66666672".equals(appInfo.app_id)) {
            H5Log.d(TAG, "appInfo.app_id set app_channel 4 " + appInfo.app_id);
            appInfo.app_channel = 4;
        }
        if (!TextUtils.isEmpty(appInfo.patch)) {
            if (!enableH5AppPatch()) {
                appInfo.patch = "";
            } else if ("res".equalsIgnoreCase(appInfo.nbAppType) && !enableResPatch()) {
                appInfo.patch = "";
            }
        }
        if (TextUtils.isEmpty(appInfo.patch) || !patchBlackListContain(appInfo.app_id)) {
            return appInfo;
        }
        appInfo.patch = "";
        return appInfo;
    }

    static boolean enableResPatch() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_enable_resH5App_patch"))) {
            return true;
        }
        return false;
    }

    static boolean enableH5AppPatch() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_enable_H5App_patch"))) {
            return true;
        }
        return false;
    }

    static boolean patchBlackListContain(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return false;
        }
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null) {
            return false;
        }
        String regex = h5ConfigProvider.getConfigWithProcessCache("h5_patch_appId_blacklist");
        if (TextUtils.isEmpty(regex) || !H5PatternHelper.matchRegex(regex, appId)) {
            return false;
        }
        return true;
    }

    public static void updateApp(String appId, H5UpdateAppCallback process) {
        H5AppProvider nebulaAppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (nebulaAppProvider == null) {
            H5Log.e(TAG, (String) "nebulaAppProvider==null");
            return;
        }
        Map appMap = new HashMap();
        if (!TextUtils.isEmpty(appId)) {
            String walletConfigNebulaVersion = nebulaAppProvider.getWalletConfigNebulaVersion(appId);
            H5Log.d(TAG, "prepareApp: send rpc appId:" + appId + " walletConfigNebulaVersion:" + walletConfigNebulaVersion);
            appMap.put(appId, walletConfigNebulaVersion);
        }
        if (!appMap.isEmpty()) {
            nebulaAppProvider.updateApp(H5UpdateAppParam.newBuilder().setDownLoadAmr(true).setAppMap(appMap).setUpdateCallback(process).build());
        }
    }

    public static boolean isRNPackage(AppInfo appInfo) {
        if (appInfo != null) {
            String extraJOStr = appInfo.extend_info_jo;
            if (!TextUtils.isEmpty(extraJOStr)) {
                return Const.TYPE_RN.equals(H5Utils.getString(H5Utils.parseObject(extraJOStr), (String) "appTypeSwitch"));
            }
        }
        return false;
    }

    public static int compareVersion(String version1, String version2) {
        int len;
        try {
            if (TextUtils.isEmpty(version1) || TextUtils.isEmpty(version2) || version1.contains("*") || version2.contains("*")) {
                return 0;
            }
            String[] versionArray1 = version1.split("\\.");
            String[] versionArray2 = version2.split("\\.");
            int len1 = versionArray1.length;
            int len2 = versionArray2.length;
            if (len1 <= len2) {
                len = len1;
            } else {
                len = len2;
            }
            for (int i = 0; i < len; i++) {
                long x1 = Long.parseLong(versionArray1[i]);
                long x2 = Long.parseLong(versionArray2[i]);
                if (x1 > x2) {
                    return 1;
                }
                if (x1 < x2) {
                    return -1;
                }
            }
            if (len1 > len2) {
                for (int i2 = len; i2 < len1; i2++) {
                    if (Long.parseLong(versionArray1[i2]) > 0) {
                        return 1;
                    }
                }
            } else if (len1 < len2) {
                for (int i3 = len; i3 < len2; i3++) {
                    if (Long.parseLong(versionArray2[i3]) > 0) {
                        return -1;
                    }
                }
            }
            return 0;
        } catch (Exception e) {
            H5Log.e(TAG, (Throwable) e);
            return 0;
        }
    }

    public static boolean downloadH5App(AppInfo appInfo) {
        if (appInfo == null) {
            return false;
        }
        H5Log.d(TAG, "downloadH5App, appid: " + appInfo.app_id + ",downloadType: " + appInfo.auto_install);
        if (appInfo.auto_install == 1) {
            return true;
        }
        boolean canPreDownloadInWifi = false;
        List wifiPreDownloadList = H5AppScoreList.getInstance().getAppListWithStrategy(4);
        if (wifiPreDownloadList != null && !wifiPreDownloadList.isEmpty()) {
            canPreDownloadInWifi = wifiPreDownloadList.contains(appInfo.app_id);
        }
        if (!H5Utils.isInWifi()) {
            return false;
        }
        if (canPreDownloadInWifi || appInfo.auto_install == 0) {
            return true;
        }
        return false;
    }

    public static String getPackageNick(String appId) {
        H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider != null) {
            return h5AppProvider.getPackageNick(appId);
        }
        return null;
    }

    public static void appCenterLog(String seedId, AppInfo appInfo, String param3) {
        if (appInfo != null) {
            H5LogUtil.logNebulaTech(H5LogData.seedId(seedId).param1().add("monitor", null).param3().add(param3, null).param4().add(("appId=" + appInfo.app_id + "^version=" + appInfo.version) + param3, null));
        }
    }

    public static H5LogProvider getH5LogProvider() {
        H5ProviderManager h5ProviderManager = H5Utils.getH5ProviderManager();
        if (h5ProviderManager != null) {
            return (H5LogProvider) h5ProviderManager.getProvider(H5LogProvider.class.getName());
        }
        return null;
    }

    @Nullable
    public static String getTemplateAppId(AppInfo appInfo) {
        if (appInfo.extend_info == null) {
            return null;
        }
        String launchParams = appInfo.extend_info.get(H5Param.LAUNCHER_PARAM);
        if (launchParams != null) {
            return H5Utils.getString(JSONObject.parseObject(launchParams), (String) "templateAppId");
        }
        return null;
    }

    public static void setConfig(JSONObject result, AppRes appRes) {
        if (result.containsKey("config")) {
            appRes.config = H5Utils.jsonToMap(H5Utils.getJSONObject(result, "config", null));
            if (appRes.config == null) {
                appRes.config = new HashMap();
                return;
            }
            return;
        }
        appRes.config = null;
    }

    public static PackInfoReq getPkgReqFromAppReq(AppReq appReq) {
        PackInfoReq req = new PackInfoReq();
        req.platform = appReq.platform;
        req.system = appReq.system;
        req.client = appReq.client;
        req.sdk = appReq.sdk;
        req.env = appReq.env;
        req.channel = appReq.channel;
        req.bundleid = appReq.bundleid;
        req.query = appReq.query;
        req.existed = appReq.existed;
        req.grayRules = appReq.grayRules;
        req.localAppInfo = appReq.localAppInfo;
        req.stableRpc = appReq.stableRpc;
        req.scene = appReq.scene;
        req.preferLocal = appReq.preferLocal;
        req.reqmode = appReq.reqmode;
        return req;
    }

    public static boolean isOffLine(String appId) {
        H5AppProvider appProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (H5TinyAppDebugMode.enableTinyAppDebugMode()) {
            if (appProvider != null && appProvider.isOffline(appId)) {
                H5Log.d(TAG, "appId " + appId + " isOffline not to start.");
                return true;
            }
        } else if (appProvider != null && appProvider.isOffline(appId) && !H5DevAppList.getInstance().contains(appId)) {
            H5Log.d(TAG, "appId " + appId + " isOffline not to start");
            return true;
        }
        return false;
    }

    public static boolean isRNApp(AppInfo appInfo) {
        if (appInfo == null || appInfo.app_type != 5) {
            return false;
        }
        return true;
    }

    public static boolean isH5AppPkg(AppInfo appInfo) {
        if (appInfo == null || appInfo.app_type != 1) {
            return false;
        }
        return true;
    }

    public static void deleteNebulaInstallFileAndDB(String path, String appId) {
        H5Log.d(TAG, "deleteNebulaInstallFileAndDB " + appId + Token.SEPARATOR + path);
        if (path != null && path.contains("nebulaInstallApps")) {
            H5FileUtil.delete(path);
        }
        H5AppDBService appDBService = H5ServiceUtils.getAppDBService();
        if (appDBService != null) {
            appDBService.deleteAppInstall(appId);
        }
    }

    public static boolean isNativeApp(String appId) {
        if (!H5Utils.isInWallet()) {
            return false;
        }
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null && BQCCameraParam.VALUE_NO.equals(h5ConfigProvider.getConfigWithProcessCache("h5_getFromIsNativeApp"))) {
            return false;
        }
        H5AppCenterPresetProvider h5AppCenterPresetProvider = (H5AppCenterPresetProvider) H5Utils.getProvider(H5AppCenterPresetProvider.class.getName());
        if (h5AppCenterPresetProvider != null) {
            Set appSet2 = h5AppCenterPresetProvider.getCommonResourceAppList();
            if (appSet2 != null && !appSet2.isEmpty() && appSet2.contains(appId)) {
                return false;
            }
        }
        try {
            ApplicationDescription applicationDescription = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findDescriptionByAppId(appId);
            if (applicationDescription == null) {
                return false;
            }
            String type = applicationDescription.getEngineType();
            if (!TextUtils.isEmpty(type) && H5AppEngineList.appEngineList.contains(type)) {
                return false;
            }
            H5Log.d(TAG, appId + " isNativeApp " + applicationDescription);
            return true;
        } catch (Throwable throwable) {
            H5Log.e(TAG, throwable);
            return false;
        }
    }

    public static boolean enableDSL(Bundle bundle) {
        if ("yes".equalsIgnoreCase(H5Utils.getString(bundle, (String) H5Param.ENABLE_DSL))) {
            return true;
        }
        return false;
    }

    public static boolean isAppChannel4(Bundle bundle) {
        if (H5Utils.getInt(bundle, (String) "app_channel") == 4) {
            return true;
        }
        return false;
    }

    public static Bundle mergeConmonStartParam(Bundle bundle, AppInfo appInfo) {
        getLauncherParamFromExtendInfo(bundle, appInfo);
        H5ParamParser.parseMagicOptions(bundle, TAG);
        return bundle;
    }

    public static void getLauncherParamFromExtendInfo(Bundle copyParam, AppInfo appInfo) {
        if (appInfo != null && !TextUtils.isEmpty(appInfo.extend_info_jo)) {
            H5ParamParser.setLauncherParams(H5Utils.getJSONObject(H5Utils.parseObject(appInfo.extend_info_jo), H5Param.LAUNCHER_PARAM, null), copyParam);
        }
    }

    public static Bundle copyBundle(Bundle bundle) {
        Bundle copyBundle = null;
        if (bundle == null) {
            bundle = new Bundle();
        }
        try {
            copyBundle = (Bundle) bundle.clone();
        } catch (Throwable throwable) {
            H5Log.e(TAG, throwable);
        }
        H5Log.d(TAG, "copyBundle " + copyBundle);
        return copyBundle;
    }

    public static boolean isTinyWebView(Bundle bundle) {
        H5ApiManager h5ApiManager = (H5ApiManager) H5Utils.getProvider(H5ApiManager.class.getName());
        if (h5ApiManager != null) {
            String tag = h5ApiManager.getWebViewTag();
            if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(H5Utils.getString(bundle, tag))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTinyResAppId(String appId) {
        H5AppCenterPresetProvider h5AppCenterPresetProvider = (H5AppCenterPresetProvider) H5Utils.getProvider(H5AppCenterPresetProvider.class.getName());
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
                H5Log.d(TAG, "[prepareApp] downloadApp appId:" + appId + " version:" + version);
                provider.downloadApp(appId, version, new H5DownloadCallback(true, true, callback));
                return;
            }
            H5Log.d(TAG, "[prepareApp] install App appId:" + appId + " version:" + version);
            H5Utils.getExecutor(isTinyResAppId(appId) ? H5ThreadType.URGENT : H5ThreadType.IO).execute(new Runnable() {
                public final void run() {
                    provider.installApp(appId, version, callback);
                }
            });
        }
    }

    public static String getAppxAppId() {
        H5AppCenterPresetProvider h5AppCenterPresetProvider = (H5AppCenterPresetProvider) H5ServiceUtils.getH5Service().getProviderManager().getProvider(H5AppCenterPresetProvider.class.getName());
        if (h5AppCenterPresetProvider != null) {
            return h5AppCenterPresetProvider.getTinyCommonApp();
        }
        return "66666692";
    }

    public static boolean isTinyResPresetForceCheck() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null && "0".equals(h5ConfigProvider.getConfig("ta_appx_preset_check"))) {
            return false;
        }
        return true;
    }

    public static String getTinyResPresetVersion() {
        H5AppCenterPresetProvider presetProvider = (H5AppCenterPresetProvider) H5Utils.getProvider(H5AppCenterPresetProvider.class.getName());
        if (presetProvider == null) {
            return null;
        }
        String appId = presetProvider.getTinyCommonApp();
        if (appId == null) {
            return null;
        }
        H5PresetPkg h5PresetPkg = presetProvider.getH5PresetPkg();
        if (h5PresetPkg.getPreSetInfo() == null || !h5PresetPkg.getPreSetInfo().containsKey(appId)) {
            return null;
        }
        H5PresetInfo h5PresetInfo = h5PresetPkg.getPreSetInfo().get(appId);
        if (h5PresetInfo != null) {
            return h5PresetInfo.version;
        }
        return null;
    }
}
