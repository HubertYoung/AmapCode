package com.alipay.mobile.nebula.baseprovider;

import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.framework.exception.IllegalParameterException;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.service.H5AppCenterService;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.appcenter.H5AppDBService;
import com.alipay.mobile.nebula.appcenter.H5BaseApp;
import com.alipay.mobile.nebula.appcenter.H5RpcFailResult;
import com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppScoreList;
import com.alipay.mobile.nebula.appcenter.apphandler.loadingview.H5NebulaAppCacheManager;
import com.alipay.mobile.nebula.appcenter.common.NebulaCommonInfo;
import com.alipay.mobile.nebula.appcenter.common.NebulaCommonManager;
import com.alipay.mobile.nebula.appcenter.config.H5NebulaAppConfigs;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadCallback;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadRequest;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.model.AppReq;
import com.alipay.mobile.nebula.appcenter.model.AppRes;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppBizHttpProviderImpl;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.callback.H5AppInstallCallback;
import com.alipay.mobile.nebula.callback.H5UpdateAppCallback;
import com.alipay.mobile.nebula.provider.H5AppBizRpcProvider;
import com.alipay.mobile.nebula.provider.H5AppCenterPresetProvider;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5WalletWrapper;
import com.alipay.mobile.tinyappcommon.mode.TinyAppEnvMode;
import com.uc.webview.export.internal.interfaces.IPreloadManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class H5BaseAppProvider implements H5AppProvider {
    public static final String TAG = "H5BaseAppProvider";
    private static final String config = "h5_enablestablerpc";
    public H5AppBizRpcProvider h5AppBizRpcProvider;
    public H5AppCenterService h5AppCenterService = H5ServiceUtils.getAppCenterService();
    public H5AppDBService h5AppDBService;
    public H5Service h5Service;

    public class H5RpcResult {
        boolean isLimit;
        boolean success;

        public H5RpcResult() {
        }
    }

    public abstract AppReq setReq(AppReq appReq);

    public H5BaseAppProvider() {
        if (this.h5AppCenterService != null) {
            this.h5AppDBService = this.h5AppCenterService.getAppDBService();
        }
        this.h5Service = H5ServiceUtils.getH5Service();
        if (this.h5Service != null) {
            this.h5AppBizRpcProvider = (H5AppBizRpcProvider) this.h5Service.getProviderManager().getProvider(H5AppBizRpcProvider.class.getName());
        }
        if (this.h5AppBizRpcProvider == null) {
            H5Log.e((String) TAG, (String) "h5AppBizRpcProvider == null use H5AppBizHttpProviderImpl");
            this.h5AppBizRpcProvider = new H5AppBizHttpProviderImpl();
        }
    }

    public void updateApp(final H5UpdateAppParam param) {
        ThreadPoolExecutor threadPoolExecutor;
        if (param != null) {
            H5UpdateAppCallback process = param.getUpdateCallback();
            try {
                if (this.h5Service != null) {
                    final long startTime = param.getStartTime() == 0 ? System.currentTimeMillis() : param.getStartTime();
                    if (param.isForceRpc()) {
                        threadPoolExecutor = H5Utils.getExecutor(H5ThreadType.URGENT_DISPLAY);
                    } else {
                        threadPoolExecutor = H5Utils.getExecutor("RPC");
                    }
                    threadPoolExecutor.execute(new Runnable() {
                        public void run() {
                            H5BaseAppProvider.this.updateAppWithReqFinally(param, startTime);
                        }
                    });
                } else if (process != null) {
                    process.onResult(false, false);
                }
            } catch (Throwable t) {
                if (process != null) {
                    process.onResult(false, false);
                }
                H5Log.e(TAG, "[updateApp] execute error!", t);
            }
        } else if (H5Utils.isDebug()) {
            throw new IllegalParameterException("updateApp param null!");
        }
    }

    public AppReq makeAppReq(H5UpdateAppParam param) {
        try {
            AppReq appReq = param.getAppReq();
            if (appReq == null) {
                appReq = new AppReq();
            }
            Map appMap = param.getAppMap();
            if (appMap == null) {
                appReq.openPlatReqMode = 1;
            } else {
                appReq.openPlatReqMode = 2;
            }
            if (TextUtils.isEmpty(appReq.reqmode)) {
                appReq.reqmode = "async";
            }
            if (!BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5WalletWrapper.getConfig("h5_reqModeSyncForceMgw"))) {
                H5Log.d(TAG, "[makeAppReq] fromWholeNetwork: " + param.isFromWholeNetwork());
                if (param.isFromWholeNetwork()) {
                    appReq.reqmode += "_sideMgw";
                } else {
                    appReq.reqmode += "_normal";
                }
            }
            ArrayList<String> arrayList = new ArrayList<>();
            List<String> resPackageList = param.getResPackageList();
            if (resPackageList != null && resPackageList.size() > 0) {
                arrayList.addAll(resPackageList);
            }
            String queryMainAppId = "";
            if (appMap != null && appMap.size() == 1) {
                for (Entry<String, String> key : appMap.entrySet()) {
                    queryMainAppId = (String) key.getKey();
                }
            }
            H5Log.d(TAG, "invoke rpc queryAppId " + queryMainAppId);
            boolean reqMatchTime = true;
            if (TextUtils.equals(appReq.nbsource, "debug")) {
                JSONObject jsonObject = new JSONObject();
                if (appMap != null && !appMap.isEmpty()) {
                    for (Entry appEntry : appMap.entrySet()) {
                        JSONObject entryJson = new JSONObject();
                        entryJson.put((String) "app_id", appEntry.getKey());
                        entryJson.put((String) "version", appEntry.getValue());
                        jsonObject.put((String) appEntry.getKey(), (Object) entryJson);
                    }
                }
                H5Log.d(TAG, "dev mode query " + jsonObject);
                appReq.query = jsonObject.toJSONString();
            } else {
                if (arrayList.size() > 0) {
                    for (String resId : arrayList) {
                        if (appMap != null) {
                            if (enableResDegrade()) {
                                appMap.put(resId, "*");
                            } else {
                                appMap.put(resId, getWalletConfigNebulaVersion(resId));
                            }
                        }
                    }
                }
                H5AppCenterPresetProvider h5AppCenterPresetProvider = (H5AppCenterPresetProvider) H5Utils.getProvider(H5AppCenterPresetProvider.class.getName());
                if (h5AppCenterPresetProvider != null) {
                    Set appSet = h5AppCenterPresetProvider.getCommonResourceAppList();
                    if (appSet != null && !appSet.isEmpty()) {
                        for (String id : appSet) {
                            if (isNebulaApp(id) && appMap != null && !appMap.containsKey(id)) {
                                H5Log.d(TAG, " add CommonResourceAppList to query " + id);
                                appMap.put(id, getWalletConfigNebulaVersion(id));
                                arrayList.add(id);
                            }
                        }
                    }
                }
                if (this.h5Service != null) {
                    NebulaCommonManager commonManager = this.h5Service.getNebulaCommonManager();
                    if (commonManager != null) {
                        List<NebulaCommonInfo> commonInfoList = commonManager.getNebulaAppCallbackList();
                        if (commonInfoList != null && !commonInfoList.isEmpty()) {
                            for (NebulaCommonInfo appIdList : commonInfoList) {
                                List list = appIdList.getAppIdList();
                                if (list != null && !list.isEmpty()) {
                                    for (String commonAppId : list) {
                                        if (appMap != null && !appMap.containsKey(commonAppId)) {
                                            H5Log.d(TAG, " add NebulaCommonInfo to query " + commonAppId);
                                            appMap.put(commonAppId, getWalletConfigNebulaVersion(commonAppId));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (this.h5AppBizRpcProvider == null || this.h5AppCenterService == null || this.h5AppDBService == null) {
                    H5Log.e((String) TAG, (String) "h5AppBizRpcProvider == null.");
                    return null;
                }
                if (appMap != null && !appMap.isEmpty()) {
                    JSONObject queryJson = new JSONObject();
                    double normalReqRate = this.h5AppDBService.getNormalReqRate();
                    H5Log.d(TAG, "[makeAppReq] normalReqRate : " + normalReqRate);
                    String asyncRateConfig = getConfigExtra(H5NebulaAppConfigs.ASY_REQ_RATE);
                    JSONObject asyncJson = H5Utils.parseObject(asyncRateConfig);
                    H5Log.d(TAG, "[makeAppReq] asyncRateConfig : " + asyncRateConfig);
                    for (Entry appEntry2 : appMap.entrySet()) {
                        String appId = (String) appEntry2.getKey();
                        String version = (String) appEntry2.getValue();
                        AppInfo appInfo = new AppInfo();
                        appInfo.app_id = appId;
                        appInfo.version = version;
                        if (!param.isForceRpc()) {
                            double updateRate = normalReqRate;
                            AppInfo info = this.h5AppDBService.getAppInfo(appId, version);
                            String lastUpdateTime = info != null ? info.update_app_time : "";
                            if (this.h5AppDBService.rpcIsLimit()) {
                                updateRate = this.h5AppDBService.getLimitReqRate();
                            } else {
                                JSONObject jsonObject2 = getlaunchParams(info);
                                String asyncReqRate = "";
                                if (jsonObject2 != null && !jsonObject2.isEmpty()) {
                                    asyncReqRate = H5Utils.getString(jsonObject2, (String) H5NebulaAppConfigs.ASY_REQ_RATE);
                                    if (TextUtils.isEmpty(asyncReqRate)) {
                                        asyncReqRate = H5Utils.getString(jsonObject2, (String) H5NebulaAppConfigs.ASY_REQ_RATE_SHORT);
                                    }
                                }
                                if (!TextUtils.isEmpty(asyncReqRate)) {
                                    updateRate = (double) H5Utils.parseInt(asyncReqRate);
                                } else if (asyncJson != null && !asyncJson.isEmpty()) {
                                    updateRate = asyncJson.containsKey(String.valueOf(info.app_channel)) ? (double) H5Utils.parseInt(H5Utils.getString(asyncJson, String.valueOf(info.app_channel))) : (double) H5Utils.parseInt(H5Utils.getString(asyncJson, (String) IPreloadManager.SIR_COMMON_TYPE));
                                }
                            }
                            H5Log.d(TAG, "lastUpdateTime:" + lastUpdateTime + " updateRate:" + updateRate);
                            if (!TextUtils.isEmpty(lastUpdateTime)) {
                                long diffSecond = (System.currentTimeMillis() - H5Utils.parseLong(lastUpdateTime)) / 1000;
                                H5Log.d(TAG, appId + ":diff(秒):" + diffSecond + " updateRate(秒):" + updateRate);
                                if (((double) diffSecond) < updateRate) {
                                    StringBuilder sb = new StringBuilder("appId:");
                                    H5Log.d(TAG, sb.append((String) appEntry2.getKey()).append(" timeDiff < updateRate, not to add query").toString());
                                } else {
                                    StringBuilder sb2 = new StringBuilder("appId:");
                                    H5Log.d(TAG, sb2.append((String) appEntry2.getKey()).append(" match time to add query").toString());
                                    if (info != null) {
                                        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                                        if (h5ConfigProvider != null && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_updateCurrentAppUpdateTime"))) {
                                            this.h5AppDBService.updateCurrentAppUpdateTime(appId, version);
                                        }
                                    }
                                }
                            }
                        }
                        JSONObject entryJson2 = new JSONObject();
                        entryJson2.put((String) "app_id", appEntry2.getKey());
                        entryJson2.put((String) "version", appEntry2.getValue());
                        if (arrayList.contains(appEntry2.getKey())) {
                            StringBuilder sb3 = new StringBuilder("add query resource : ");
                            H5Log.d(TAG, sb3.append((String) appEntry2.getKey()).append("version : ").append((String) appEntry2.getValue()).toString());
                            entryJson2.put((String) LogItem.MM_C15_K4_TIME, (Object) "res");
                        }
                        queryJson.put((String) appEntry2.getKey(), (Object) entryJson2);
                    }
                    if (!queryJson.isEmpty()) {
                        try {
                            appReq.query = queryJson.toJSONString();
                        } catch (Exception e) {
                            H5Log.e((String) TAG, (Throwable) e);
                        }
                    }
                }
                if (appMap != null) {
                    if (!appMap.isEmpty() && TextUtils.isEmpty(appReq.query)) {
                        reqMatchTime = false;
                    }
                }
                boolean support = true;
                try {
                    H5ConfigProvider h5ConfigProvider2 = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                    if (h5ConfigProvider2 != null) {
                        String value = h5ConfigProvider2.getConfig(config);
                        if (value != null && BQCCameraParam.VALUE_NO.equalsIgnoreCase(value)) {
                            support = false;
                        }
                    }
                    if (support && TextUtils.isEmpty(appReq.stableRpc)) {
                        appReq.stableRpc = "YES";
                    }
                } catch (Exception e2) {
                    H5Log.e((String) TAG, (Throwable) e2);
                }
                if (TextUtils.equals(appReq.stableRpc, "YES")) {
                    long time = System.currentTimeMillis();
                    Map allApp = this.h5AppDBService.getAllHighestLocalReportAppVersion();
                    long getAllAppTime = System.currentTimeMillis() - time;
                    JSONObject localAppInfoJson = new JSONObject();
                    if (allApp != null && !allApp.isEmpty()) {
                        for (Entry appEntry3 : allApp.entrySet()) {
                            if (!TextUtils.isEmpty((CharSequence) appEntry3.getValue()) && !TextUtils.isEmpty((CharSequence) appEntry3.getKey())) {
                                localAppInfoJson.put((String) appEntry3.getKey(), (Object) (String) appEntry3.getValue());
                            }
                        }
                    }
                    List smart = H5AppScoreList.getInstance().getAppListWithStrategy(2);
                    if (smart != null && !smart.isEmpty()) {
                        for (String appId2 : smart) {
                            H5Log.d(TAG, "H5AppScoreList " + appId2);
                            AppInfo appInfo2 = getAppInfo(appId2);
                            localAppInfoJson.put(appId2, (Object) appInfo2 != null ? appInfo2.version : "");
                        }
                    }
                    H5Log.d("H5NebulaAppRpcTimeCost", "ReadyAppReqInfo getLocalAppInfo cost " + ((System.currentTimeMillis() - time) - getAllAppTime) + " getAllAppTime：" + getAllAppTime);
                    if (!localAppInfoJson.isEmpty()) {
                        appReq.localAppInfo = localAppInfoJson.toJSONString();
                    }
                }
                Map installedApp = this.h5AppDBService.getInstalledApp();
                JSONObject exitJsonObject = new JSONObject();
                if (installedApp != null && !installedApp.isEmpty()) {
                    for (Entry appEntry4 : installedApp.entrySet()) {
                        JSONObject entry = new JSONObject();
                        entry.put((String) "app_id", appEntry4.getKey());
                        entry.put((String) "version", appEntry4.getValue());
                        exitJsonObject.put((String) appEntry4.getKey(), (Object) entry);
                    }
                }
                if (!exitJsonObject.isEmpty()) {
                    try {
                        appReq.existed = exitJsonObject.toJSONString();
                    } catch (Exception e3) {
                        H5Log.e((String) TAG, (Throwable) e3);
                    }
                }
            }
            appReq.platform = "android";
            appReq.client = H5Utils.getVersion();
            appReq.system = VERSION.RELEASE;
            appReq.sdk = this.h5AppCenterService.getSDKVersion();
            AppReq appReq2 = setReq(appReq);
            if (TextUtils.isEmpty(appReq2.bundleid)) {
                H5Log.e((String) TAG, (String) "appReq.bundleid is null not send request ");
                return null;
            }
            if (!TextUtils.isEmpty(appReq2.query)) {
                appReq2.preferLocal = "YES";
                H5ConfigProvider h5ConfigProvider3 = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                if (h5ConfigProvider3 != null) {
                    String value2 = h5ConfigProvider3.getConfig("h5_enablepreferlocal");
                    if (!TextUtils.isEmpty(value2) && BQCCameraParam.VALUE_NO.equalsIgnoreCase(value2)) {
                        appReq2.preferLocal = "NO";
                    }
                }
            }
            if (reqMatchTime) {
                return appReq2;
            }
            H5Log.d(TAG, " timeDiff < updateRate, this req is not send");
            return null;
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
        }
    }

    /* access modifiers changed from: private */
    public void updateAppWithReqFinally(@NonNull H5UpdateAppParam param, long startTime) {
        boolean success;
        H5Log.d(TAG, "[updateAppWithReqFinally] with param: " + param);
        H5UpdateAppCallback process = param.getUpdateCallback();
        boolean isLimitReq = false;
        try {
            long executeTime = System.currentTimeMillis();
            Map appMap = param.getAppMap();
            boolean fullRpc = false;
            if (appMap == null || appMap.isEmpty()) {
                fullRpc = true;
            }
            AppReq appReq = makeAppReq(param);
            long appReqTime = System.currentTimeMillis();
            if (appReq != null) {
                H5RpcResult h5RpcResult = request(appReq, param.isDownLoadAmr(), param.isDownloadRandom(), fullRpc);
                success = h5RpcResult.success;
                isLimitReq = h5RpcResult.isLimit;
            } else {
                success = false;
            }
            H5Log.d("H5NebulaAppRpcTimeCost", "Total Cost:" + (System.currentTimeMillis() - startTime) + " ThreadBeginExecuteTime:" + (executeTime - startTime) + " ReadyAppReqInfo " + (appReqTime - executeTime) + " Rpc+SaveApp:" + (System.currentTimeMillis() - appReqTime));
            if (process != null) {
                process.onResult(success, isLimitReq);
            }
            if (isLimitReq) {
                this.h5AppDBService.setRpcIsLimit(true);
                if (appMap != null) {
                    for (Entry entry : appMap.entrySet()) {
                        this.h5AppDBService.updateCurrentAppUpdateTime((String) entry.getKey(), (String) entry.getValue());
                    }
                }
            }
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
            if (process != null) {
                process.onResult(false, false);
            }
        }
    }

    private H5RpcResult getRpcResult(boolean success, boolean isLimit) {
        H5RpcResult h5RpcResult = new H5RpcResult();
        h5RpcResult.isLimit = isLimit;
        h5RpcResult.success = success;
        return h5RpcResult;
    }

    public H5RpcResult request(AppReq appReq, boolean downLoad, boolean downloadRandom, boolean fullRpc) {
        try {
            AppRes appRes = this.h5AppBizRpcProvider.app(appReq);
            Long saveTime = Long.valueOf(System.currentTimeMillis());
            if (appRes == null) {
                return getRpcResult(false, false);
            }
            if (this.h5AppDBService == null) {
                H5Log.d(TAG, "h5AppDBService init fail.");
                return getRpcResult(false, false);
            } else if (TextUtils.equals(H5RpcFailResult.LIMIT, appRes.rpcFailDes)) {
                return getRpcResult(false, true);
            } else {
                if (this.h5AppDBService.rpcIsLimit()) {
                    this.h5AppDBService.setRpcIsLimit(false);
                }
                if (TextUtils.equals(H5RpcFailResult.RESULT_CODE_NOT_100, appRes.rpcFailDes)) {
                    return getRpcResult(false, false);
                }
                if (TextUtils.equals(H5RpcFailResult.NOT_LOGIN, appRes.rpcFailDes)) {
                    return getRpcResult(false, false);
                }
                if (this.h5AppCenterService != null) {
                    String scene = "";
                    if (fullRpc) {
                        scene = H5DownloadRequest.FULL_RPC_SCENE;
                    }
                    this.h5AppCenterService.setUpInfo(appRes, true, downLoad, downloadRandom, scene);
                    List list = new ArrayList();
                    for (AppInfo appInfo : appRes.data) {
                        list.add(appInfo.app_id);
                    }
                    if (appRes.removeAppIdList != null && !appRes.removeAppIdList.isEmpty()) {
                        for (String id : appRes.removeAppIdList) {
                            list.add(id);
                        }
                    }
                    H5Utils.setNebulaAppCallback(0, list);
                    H5Log.d("H5NebulaAppRpcTimeCost", "SaveAppTime  cost " + (System.currentTimeMillis() - saveTime.longValue()));
                    return getRpcResult(true, false);
                }
                H5Log.d(TAG, "save db fail.");
                return getRpcResult(false, false);
            }
        } catch (Exception e) {
            H5Log.e(TAG, "updateApp exception", e);
            return getRpcResult(false, false);
        }
    }

    public Map<String, String> getExtra(String appId, String version) {
        AppInfo appInfo = queryNebulaApp(appId, version);
        if (appInfo != null) {
            return appInfo.extend_info;
        }
        return null;
    }

    public String getExtraJo(String appId, String version) {
        AppInfo appInfo = queryNebulaApp(appId, version);
        if (appInfo != null) {
            return appInfo.extend_info_jo;
        }
        return null;
    }

    public String getH5AppCdnBaseUrl(String appId, String version) {
        AppInfo appInfo = queryNebulaApp(appId, version);
        if (appInfo != null) {
            return appInfo.fallback_base_url;
        }
        return null;
    }

    public String getInstallPath(String appId, String version) {
        return getInstallPath(queryNebulaApp(appId, version));
    }

    private String getInstallPath(AppInfo appInfo) {
        if (appInfo == null) {
            return null;
        }
        H5BaseApp h5BaseApp = this.h5AppCenterService.getH5App();
        h5BaseApp.setAppInfo(appInfo);
        return h5BaseApp.getInstalledPath();
    }

    public String getDownloadLocalPath(String appId, String version) {
        AppInfo appInfo = queryNebulaApp(appId, version);
        if (appInfo == null) {
            return null;
        }
        H5BaseApp h5BaseApp = this.h5AppCenterService.getH5App();
        h5BaseApp.setAppInfo(appInfo);
        return h5BaseApp.getDownloadLocalPath();
    }

    public void installApp(String appId, String version) {
        AppInfo appInfo = queryNebulaApp(appId, version);
        if (appInfo != null) {
            H5BaseApp h5BaseApp = this.h5AppCenterService.getH5App();
            h5BaseApp.setAppInfo(appInfo);
            h5BaseApp.installApp();
        }
    }

    public void installApp(String appId, String version, H5AppInstallCallback h5AppInstallCallback) {
        AppInfo appInfo = queryNebulaApp(appId, version);
        if (appInfo != null) {
            H5BaseApp h5BaseApp = this.h5AppCenterService.getH5App();
            h5BaseApp.setAppInfo(appInfo);
            h5BaseApp.installApp(h5AppInstallCallback);
        }
    }

    public void installApp(String appId, String version, boolean async) {
        AppInfo appInfo = queryNebulaApp(appId, version);
        if (appInfo != null) {
            H5BaseApp h5BaseApp = this.h5AppCenterService.getH5App();
            h5BaseApp.setAppInfo(appInfo);
            h5BaseApp.installApp(async);
        }
    }

    public void downloadApp(String appId, String version) {
        downloadApp(appId, version, null);
    }

    public void downloadApp(String appId, String version, H5DownloadCallback h5DownloadCallback) {
        downloadApp(appId, version, h5DownloadCallback, null);
    }

    public void downloadApp(String appId, String version, H5DownloadCallback h5DownloadCallback, String scene) {
        AppInfo appInfo = queryNebulaApp(appId, version);
        if (appInfo != null) {
            H5BaseApp h5BaseApp = this.h5AppCenterService.getH5App();
            h5BaseApp.setAppInfo(appInfo);
            h5BaseApp.downloadApp(h5DownloadCallback, scene);
        }
    }

    public boolean isAvailable(String appId, String version) {
        AppInfo appInfo = queryNebulaApp(appId, version);
        if (appInfo == null) {
            return false;
        }
        H5BaseApp h5BaseApp = this.h5AppCenterService.getH5App();
        h5BaseApp.setAppInfo(appInfo);
        return h5BaseApp.isAvailable();
    }

    public boolean isH5App(String appId) {
        if (!TextUtils.isEmpty(appId) && queryNebulaApp(appId, getVersion(appId)) != null) {
            return true;
        }
        return false;
    }

    public boolean isInstalled(String appId, String version) {
        return isInstalled(queryNebulaApp(appId, version));
    }

    public boolean isInstalled(AppInfo appInfo) {
        if (appInfo == null) {
            return false;
        }
        H5BaseApp h5BaseApp = this.h5AppCenterService.getH5App();
        h5BaseApp.setAppInfo(appInfo);
        return h5BaseApp.isInstalled();
    }

    public boolean hasPackage(String appId, String version) {
        return hasPackage(queryNebulaApp(appId, version));
    }

    public boolean hasPackage(AppInfo appInfo) {
        if (appInfo == null) {
            return false;
        }
        H5BaseApp h5BaseApp = this.h5AppCenterService.getH5App();
        h5BaseApp.setAppInfo(appInfo);
        Uri uri = H5UrlHelper.parseUrl(h5BaseApp.getDownloadUrl());
        String scheme = uri == null ? null : uri.getScheme();
        if ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) {
            return true;
        }
        return false;
    }

    public String getVhost(String appId, String version) {
        if (this.h5AppDBService == null) {
            H5Log.d(TAG, "h5AppDBService init fail.");
            return null;
        }
        AppInfo appInfo = this.h5AppDBService.getAppInfo(appId, version);
        if (appInfo != null) {
            return appInfo.vhost;
        }
        return null;
    }

    public AppInfo getAppInfo(String appId, String scene, String version) {
        if (TextUtils.isEmpty(scene)) {
            return getAppInfo(appId, version);
        }
        if (!TextUtils.isEmpty(version) && !scene.equalsIgnoreCase("DEBUG") && !scene.equalsIgnoreCase(TinyAppEnvMode.TRIAL_NEBULA) && !scene.equalsIgnoreCase(TinyAppEnvMode.EXAMINE_NEBULA)) {
            return getAppInfo(appId, version);
        }
        if (this.h5AppDBService == null) {
            return null;
        }
        List appInfoList = this.h5AppDBService.getAppInfoList(appId, true);
        if (appInfoList == null || appInfoList.size() == 0) {
            return null;
        }
        for (int i = appInfoList.size() - 1; i >= 0; i--) {
            AppInfo appInfo = appInfoList.get(i);
            if (!TextUtils.isEmpty(appInfo.extend_info_jo)) {
                if (TextUtils.isEmpty(version) || TextUtils.equals(version, "*")) {
                    if (scene.equals(H5Utils.getString(H5Utils.parseObject(appInfo.extend_info_jo), (String) H5AppUtil.scene))) {
                        return appInfo;
                    }
                } else if (TextUtils.equals(version, appInfo.version) && scene.equals(H5Utils.getString(H5Utils.parseObject(appInfo.extend_info_jo), (String) H5AppUtil.scene))) {
                    return appInfo;
                }
            }
        }
        return null;
    }

    public AppInfo getAppInfo(String appId, String version) {
        AppInfo appInfo = queryNebulaApp(appId, version);
        if (appInfo != null) {
            return appInfo;
        }
        return null;
    }

    public AppInfo getAppInfo(String appId) {
        return getAppInfo(appId, getVersion(appId));
    }

    public String getVersion(String appId) {
        if (this.h5AppDBService != null) {
            return this.h5AppDBService.getHighestAppVersion(appId);
        }
        return null;
    }

    public boolean isAutoInstall(String appId, String version) {
        if (this.h5AppDBService == null || this.h5AppDBService.getAppInfo(appId, version) == null) {
            return false;
        }
        if (this.h5AppDBService.getAppInfo(appId, version).auto_install == 1) {
            return true;
        }
        return false;
    }

    private AppInfo queryNebulaApp(String appId, String version) {
        if (TextUtils.isEmpty(appId)) {
            return null;
        }
        if (version == null) {
            version = getVersion(appId);
        }
        if (this.h5AppDBService != null) {
            AppInfo appInfo = this.h5AppDBService.getAppInfo(appId, version);
            if (appInfo != null) {
                return appInfo;
            }
        }
        return null;
    }

    public String getSubUrl(String appId, String version) {
        AppInfo appInfo = getAppInfo(appId, version);
        if (appInfo != null) {
            return appInfo.sub_url;
        }
        return null;
    }

    public boolean isOffline(String s) {
        return false;
    }

    public boolean isNebulaApp(String appId) {
        return true;
    }

    public Map<String, String> syncAppManage() {
        return null;
    }

    public String getWalletVersion(String appId) {
        return null;
    }

    public String getWalletConfigNebulaVersion(String appId) {
        return "*";
    }

    public String getThirdPlatform(String appId, String version) {
        AppInfo appInfo = getAppInfo(appId, version);
        if (appInfo != null) {
            return appInfo.third_platform;
        }
        return null;
    }

    public long getPackageSize(String appId, String version) {
        AppInfo appInfo = getAppInfo(appId, version);
        if (appInfo != null) {
            return appInfo.size;
        }
        return 0;
    }

    public boolean isRNApp(String appId) {
        return H5AppUtil.isRNApp(getAppInfo(appId, getVersion(appId)));
    }

    public boolean isXiaoChengXu(String appId) {
        String appType = H5NebulaAppCacheManager.getAppType(appId);
        if ("nebulaH5App".equals(appType) || H5NebulaAppCacheManager.NEBULA_NATIVE_TINY_APP.equals(appType)) {
            return false;
        }
        if (!H5NebulaAppCacheManager.NEBULA_H5TINY_APP.equals(appType) && !isH5TinyApp(appId)) {
            return false;
        }
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null) {
            return false;
        }
        String value = h5ConfigProvider.getConfig("h5_open_multi_process");
        if (TextUtils.isEmpty(value)) {
            return false;
        }
        JSONArray jsonArray = H5Utils.parseArray(value);
        if (jsonArray == null || jsonArray.isEmpty()) {
            return false;
        }
        if (jsonArray.contains("all")) {
            JSONArray blackArray = H5Utils.parseArray(h5ConfigProvider.getConfig("h5_open_multi_process_blacklist"));
            if (blackArray == null || blackArray.isEmpty() || !blackArray.contains(appId)) {
                return true;
            }
            return false;
        } else if (jsonArray.contains(appId)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isH5TinyApp(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return false;
        }
        boolean isH5TinyApp = false;
        AppInfo appInfo = getAppInfo(appId, getVersion(appId));
        if (appInfo != null) {
            isH5TinyApp = H5Utils.isTinyApp(appInfo);
        }
        H5Log.d(TAG, appId + " isH5TinyApp " + isH5TinyApp);
        return isH5TinyApp;
    }

    public String getAppName(String appId) {
        AppInfo appInfo = getAppInfo(appId, getVersion(appId));
        if (appInfo != null) {
            return appInfo.name;
        }
        return null;
    }

    public String getAppName(String appId, String version) {
        AppInfo appInfo = getAppInfo(appId, version);
        if (appInfo != null) {
            return appInfo.name;
        }
        return null;
    }

    public String getAppDesc(String appId) {
        AppInfo appInfo = getAppInfo(appId, getVersion(appId));
        if (appInfo != null) {
            return appInfo.app_dsec;
        }
        return null;
    }

    public String getIconUrl(String appId, String version) {
        AppInfo appInfo = getAppInfo(appId, version);
        if (appInfo != null) {
            return appInfo.icon_url;
        }
        return null;
    }

    public String getIconUrl(String appId) {
        AppInfo appInfo = getAppInfo(appId, getVersion(appId));
        if (appInfo != null) {
            return appInfo.icon_url;
        }
        return null;
    }

    public String getWalletIconUrl(String appId) {
        return null;
    }

    public String getWalletAppName(String appId) {
        return null;
    }

    public String getWalletAppDesc(String appId) {
        return null;
    }

    public String getPackageNick(String appId) {
        return getPackageNick(appId, getVersion(appId));
    }

    public String getPackageNick(String appId, String version) {
        AppInfo appInfo = getAppInfo(appId, version);
        if (appInfo != null) {
            return H5Utils.getString(H5Utils.parseObject(appInfo.extend_info_jo), (String) H5AppUtil.package_nick);
        }
        return "";
    }

    public int getLocalReport(String appId, String version) {
        AppInfo appInfo = this.h5AppDBService.getAppInfo(appId, version);
        if (appInfo != null) {
            return appInfo.localReport;
        }
        return 0;
    }

    public JSONObject getlaunchParams(String appId) {
        AppInfo appInfo = getAppInfo(appId);
        if (appInfo == null) {
            return null;
        }
        return getlaunchParams(appInfo);
    }

    private JSONObject getlaunchParams(AppInfo appInfo) {
        if (appInfo == null) {
            return null;
        }
        return H5Utils.getJSONObject(H5Utils.parseObject(appInfo.extend_info_jo), H5Param.LAUNCHER_PARAM, null);
    }

    public String getConfigExtra(String key) {
        H5AppDBService appDBService = H5ServiceUtils.getAppDBService();
        if (appDBService != null) {
            String configExtra = appDBService.getConfigExtra();
            if (!TextUtils.isEmpty(configExtra)) {
                JSONObject jsonObject = H5Utils.parseObject(configExtra);
                if (jsonObject != null && !jsonObject.isEmpty()) {
                    return H5Utils.getString(jsonObject, key);
                }
            }
        }
        return null;
    }

    public String getScene(String appId, String version) {
        AppInfo appInfo = getAppInfo(appId, version);
        if (appInfo != null) {
            return H5Utils.getString(H5Utils.parseObject(appInfo.extend_info_jo), (String) H5AppUtil.scene);
        }
        return "";
    }

    public void showOfflinePage(String appId, Bundle bundle) {
    }

    public void getAppFromServerWhenAppIsEmpty(String appId) {
    }

    public void offlineFromOpenPlat(String appId) {
    }

    private static boolean enableResDegrade() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_nbresmode"))) {
            return true;
        }
        return false;
    }

    public boolean enableMultiProcess(String appId, Bundle bundle) {
        H5ConfigProvider configProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (configProvider != null && BQCCameraParam.VALUE_NO.equalsIgnoreCase(configProvider.getConfigWithProcessCache("h5_enableMultiProcess_new"))) {
            return isXiaoChengXu(appId);
        }
        if (!H5NebulaAppCacheManager.enableMulti(appId, bundle)) {
            return false;
        }
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null) {
            return false;
        }
        String value = h5ConfigProvider.getConfig("h5_open_multi_process");
        if (TextUtils.isEmpty(value)) {
            return false;
        }
        JSONArray jsonArray = H5Utils.parseArray(value);
        if (jsonArray == null || jsonArray.isEmpty()) {
            return false;
        }
        if (jsonArray.contains("all")) {
            JSONArray blackArray = H5Utils.parseArray(h5ConfigProvider.getConfig("h5_open_multi_process_blacklist"));
            if (blackArray == null || blackArray.isEmpty() || !blackArray.contains(appId)) {
                return true;
            }
            return false;
        } else if (jsonArray.contains(appId)) {
            return true;
        } else {
            return false;
        }
    }

    public String getSlogan(String appId, String version) {
        return null;
    }

    public boolean isSmallProgramFromOpenPlat(String appId) {
        return false;
    }

    public boolean isUseAppX(String appId) {
        return H5NebulaAppCacheManager.useAppX(appId);
    }

    public boolean isResourceApp(String appId) {
        return false;
    }

    public void clearResourceAppCache() {
    }

    public Map<String, String> queryAllH5AppVersionFromAppCenter() {
        return null;
    }
}
