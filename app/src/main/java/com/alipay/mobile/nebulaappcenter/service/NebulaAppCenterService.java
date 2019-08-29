package com.alipay.mobile.nebulaappcenter.service;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5PreSetPkgInfo;
import com.alipay.mobile.nebula.appcenter.H5AppDBService;
import com.alipay.mobile.nebula.appcenter.H5BaseApp;
import com.alipay.mobile.nebula.appcenter.api.H5LoadPresetListen;
import com.alipay.mobile.nebula.appcenter.config.H5NebulaAppConfigManager;
import com.alipay.mobile.nebula.appcenter.config.H5NebulaAppConfigs;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadRequest;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.model.AppRes;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.appcenter.util.H5LoadingApp;
import com.alipay.mobile.nebula.appcenter.wifidownload.H5WifiDownloadList;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory;
import com.alipay.mobile.nebula.wallet.H5WalletWrapper;
import com.alipay.mobile.nebulaappcenter.app.H5App;
import com.alipay.mobile.nebulaappcenter.app.H5NebulaDBService;
import com.alipay.mobile.nebulaappcenter.dbbean.H5AppConfigBean;
import com.alipay.mobile.nebulaappcenter.dbbean.H5NebulaAppBean;
import com.alipay.mobile.nebulaappcenter.dbhelp.H5DBUtil;
import com.alipay.mobile.nebulaappcenter.util.H5AppGlobal;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class NebulaAppCenterService {
    private static List<H5PreSetPkgInfo> b;
    private H5NebulaDBService a;

    public final void a() {
        H5Log.d("H5AppCenterServiceImpl", "create DBService.");
        this.a = H5NebulaDBService.getInstance();
        H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
            public final void run() {
                NebulaAppCenterService.j();
                NebulaAppCenterService.i();
            }
        });
    }

    public static void b() {
        H5Log.d("H5AppCenterServiceImpl", "onDestroy");
    }

    /* access modifiers changed from: private */
    public static void i() {
        String path = H5AppGlobal.a((String) "/nebulaApps/");
        if (!TextUtils.isEmpty(path) && path.contains("/nebulaApps/")) {
            File deleteFile = new File(path);
            boolean exist = H5FileUtil.exists(deleteFile);
            H5Log.d("H5AppCenterServiceImpl", "delete file " + path + " exist:" + exist);
            if (exist) {
                H5FileUtil.delete(deleteFile);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void j() {
        try {
            String downloadDirectoryPath = H5DownloadRequest.getOldDownloadDir(H5Utils.getContext()) + "/";
            if (!TextUtils.isEmpty(downloadDirectoryPath)) {
                H5Log.d("H5AppCenterServiceImpl", downloadDirectoryPath);
                if (downloadDirectoryPath.contains("nebulaH5App")) {
                    File deleteFile = new File(downloadDirectoryPath);
                    boolean exist = H5FileUtil.exists(deleteFile);
                    if (exist) {
                        H5FileUtil.delete(deleteFile);
                    }
                    H5Log.d("H5AppCenterServiceImpl", "delete file " + downloadDirectoryPath + " exist:" + exist);
                }
            }
        } catch (Exception e) {
            H5Log.e((String) "H5AppCenterServiceImpl", (Throwable) e);
        }
    }

    public final void a(InputStream inputStream) {
        if (inputStream != null) {
            try {
                String presetJSON = H5AppGlobal.a(inputStream);
                H5Log.d("H5AppCenterServiceImpl", "loadPresetApp:" + presetJSON);
                JSONObject appResJO = H5Utils.parseObject(presetJSON);
                AppRes appRes = new AppRes();
                appRes.config = H5Utils.jsonToMap(H5Utils.toJSONString(H5Utils.getJSONObject(appResJO, "config", null)));
                JSONArray dataList = H5Utils.getJSONArray(appResJO, "data", null);
                if (dataList == null) {
                    H5Log.e((String) "H5AppCenterServiceImpl", (String) "dataList == null return");
                    return;
                }
                appRes.data = new ArrayList();
                for (int index = 0; index < dataList.size(); index++) {
                    AppInfo appInfo = null;
                    try {
                        if (dataList.get(index) instanceof JSONObject) {
                            appInfo = H5AppUtil.toAppInfo((JSONObject) dataList.get(index));
                        } else {
                            H5Log.d("H5AppCenterServiceImpl", "dataList.get(index) is not JSONObject");
                        }
                    } catch (Exception e) {
                        H5Log.e((String) "H5AppCenterServiceImpl", (Throwable) e);
                    }
                    if (appInfo == null) {
                        H5Log.d("H5AppCenterServiceImpl", "appInfo == null continue ");
                    } else {
                        H5Log.d("H5AppCenterServiceImpl", "preset appId:" + appInfo.app_id);
                        appRes.data.add(appInfo);
                    }
                }
                a(appRes, false, false);
            } catch (Exception e2) {
                H5Log.e("H5AppCenterServiceImpl", "Exception:", e2);
            }
        }
    }

    public static H5AppDBService c() {
        return H5NebulaDBService.getInstance();
    }

    public static H5BaseApp d() {
        return new H5App();
    }

    public static String e() {
        return "1.3.0.0";
    }

    public static void a(List<H5PreSetPkgInfo> list) {
        b = list;
    }

    public static void a(List<H5PreSetPkgInfo> h5PreSetPkgInfo, H5LoadPresetListen h5LoadPresetListen) {
        for (H5PreSetPkgInfo preSetPkgInfo : h5PreSetPkgInfo) {
            if (preSetPkgInfo != null && preSetPkgInfo.getInputStream() != null && !TextUtils.isEmpty(preSetPkgInfo.getAppId()) && !TextUtils.isEmpty(preSetPkgInfo.getVersion())) {
                H5App h5App = new H5App();
                AppInfo appInfo = new AppInfo();
                appInfo.app_id = preSetPkgInfo.getAppId();
                appInfo.version = preSetPkgInfo.getVersion();
                appInfo.package_url = preSetPkgInfo.getDownloadUrl();
                h5App.setAppInfo(appInfo);
                h5App.presetApp(preSetPkgInfo.getInputStream(), h5LoadPresetListen);
            }
        }
    }

    public final void a(AppRes appRes, boolean forceSync) {
        a(appRes, forceSync, true);
    }

    public final void a(AppRes appRes, boolean forceSync, boolean downLoadAmr) {
        a(appRes, forceSync, downLoadAmr, false);
    }

    public final void a(AppRes appRes, boolean forceSync, boolean downLoadAmr, boolean downloadRandom) {
        a(appRes, forceSync, downLoadAmr, downloadRandom, (String) null);
    }

    public final void a(AppRes appRes, boolean forceSync, boolean downLoadAmr, boolean downloadRandom, String scene) {
        if (appRes == null || this.a == null) {
            H5Log.e((String) "H5AppCenterServiceImpl", (String) "appRes==null");
            return;
        }
        long beginSetAppConfig = System.currentTimeMillis();
        b(appRes, forceSync);
        H5Log.d("H5AppCenterServiceImpl", "handle discard data: " + appRes.discardData);
        if (appRes.discardData != null && !appRes.discardData.isEmpty()) {
            for (Entry discardItem : appRes.discardData.entrySet()) {
                String appId = (String) discardItem.getKey();
                List<String> versions = (List) discardItem.getValue();
                if (versions != null && versions.size() > 0) {
                    for (String version : versions) {
                        H5Log.d("H5AppCenterServiceImpl", "delete discard app: " + appId + Token.SEPARATOR + version);
                        this.a.deleteAppInfo(appId, version);
                    }
                }
            }
        }
        Long setAppInfoTime = Long.valueOf(System.currentTimeMillis());
        if (appRes.data != null && !appRes.data.isEmpty()) {
            try {
                if (!H5DBUtil.e() || !forceSync) {
                    for (int index = 0; index <= appRes.data.size() - 1; index++) {
                        if (!(appRes.data == null || appRes.data.get(index) == null)) {
                            AppInfo appInfo = appRes.data.get(index);
                            long beginSetUpAppInfo = System.currentTimeMillis();
                            if (forceSync || this.a.getAppInfo(appInfo.app_id, appInfo.version) == null) {
                                a(appInfo, forceSync, downLoadAmr, downloadRandom, scene);
                            } else {
                                H5Log.d("H5AppCenterServiceImpl", " has set Preset App not add to db data" + appInfo.app_id + " version:" + appInfo.version);
                            }
                            H5Log.d("H5NebulaAppDBCost", appInfo.app_id + " setUpInfo cost" + (System.currentTimeMillis() - beginSetUpAppInfo));
                        }
                    }
                } else {
                    a(appRes.data, downLoadAmr, downloadRandom, scene);
                }
            } catch (Throwable throwable) {
                H5Log.e((String) "H5AppCenterServiceImpl", throwable);
            }
        }
        if (appRes.removeAppIdList != null && !appRes.removeAppIdList.isEmpty()) {
            Map map = this.a.getAllApp();
            if (map != null && !map.isEmpty()) {
                for (Entry appEntry : map.entrySet()) {
                    if (!TextUtils.isEmpty((CharSequence) appEntry.getKey()) && appRes.removeAppIdList.contains(appEntry.getKey())) {
                        List<AppInfo> versionList = (List) appEntry.getValue();
                        if (versionList != null && !versionList.isEmpty()) {
                            for (AppInfo appInfo2 : versionList) {
                                H5App h5App = new H5App();
                                if (appInfo2 != null) {
                                    h5App.setAppInfo(appInfo2);
                                    H5FileUtil.delete(h5App.getDownloadLocalPath());
                                    H5FileUtil.delete(h5App.getInstalledPath());
                                    this.a.deleteAppInfo(appInfo2.app_id, appInfo2.version);
                                }
                            }
                        }
                    }
                }
            }
        }
        H5Log.d("H5NebulaAppDBCost", "AppRes total cost" + (System.currentTimeMillis() - beginSetAppConfig) + " setAppConfig:" + (setAppInfoTime.longValue() - beginSetAppConfig) + " setAllAppInfo:" + (System.currentTimeMillis() - setAppInfoTime.longValue()));
    }

    private void b(AppRes appRes, boolean forceSync) {
        H5Log.d("H5AppCenterServiceImpl", "[applyAppResConfig] fromNewConfig: " + appRes.fromNewConfig);
        if (!appRes.fromNewConfig && H5NebulaAppConfigManager.enableNewConfig()) {
            H5Log.d("H5AppCenterServiceImpl", "[applyAppResConfig] not read appRes.config because inWallet and configSwitchOpen");
        } else if (appRes.config != null) {
            String appPoolLimit = "";
            String updateReqRate = "";
            String limitReqRate = "";
            String preReqRate = "";
            String asyncReqRate = "";
            if (!forceSync) {
                try {
                    if (this.a.hasSetConfig()) {
                        H5Log.d("H5AppCenterServiceImpl", " has set Preset App not add to db config");
                        return;
                    }
                } catch (Exception e) {
                    H5Log.e((String) "H5AppCenterServiceImpl", (Throwable) e);
                    return;
                }
            }
            if (appRes.config.get(H5NebulaAppConfigs.APP_POOL_LIMIT_SHORT) != null) {
                appPoolLimit = String.valueOf(appRes.config.get(H5NebulaAppConfigs.APP_POOL_LIMIT_SHORT));
            }
            if (appRes.config.get(H5NebulaAppConfigs.APP_POOL_LIMIT) != null) {
                appPoolLimit = String.valueOf(appRes.config.get(H5NebulaAppConfigs.APP_POOL_LIMIT));
            }
            if (appRes.config.get(H5NebulaAppConfigs.UPDATE_REQ_RATE_SHORT) != null) {
                updateReqRate = String.valueOf(appRes.config.get(H5NebulaAppConfigs.UPDATE_REQ_RATE_SHORT));
            }
            if (appRes.config.get(H5NebulaAppConfigs.UPDATE_REQ_RATE) != null) {
                updateReqRate = String.valueOf(appRes.config.get(H5NebulaAppConfigs.UPDATE_REQ_RATE));
            }
            if (appRes.config.get(H5NebulaAppConfigs.LIMIT_REQ_RATE_SHORT) != null) {
                limitReqRate = String.valueOf(appRes.config.get(H5NebulaAppConfigs.LIMIT_REQ_RATE_SHORT));
            }
            if (appRes.config.get(H5NebulaAppConfigs.LIMIT_REQ_RATE) != null) {
                limitReqRate = String.valueOf(appRes.config.get(H5NebulaAppConfigs.LIMIT_REQ_RATE));
            }
            JSONObject extraConfig = new JSONObject();
            if (appRes.config.get("pr") != null) {
                preReqRate = String.valueOf(appRes.config.get("pr"));
            }
            if (appRes.config.get(H5NebulaAppConfigs.PRE_REQ_RATE) != null) {
                preReqRate = String.valueOf(appRes.config.get(H5NebulaAppConfigs.PRE_REQ_RATE));
            }
            if (!TextUtils.isEmpty(preReqRate)) {
                extraConfig.put((String) H5NebulaAppConfigs.PRE_REQ_RATE, (Object) preReqRate);
            } else {
                extraConfig.put((String) H5NebulaAppConfigs.PRE_REQ_RATE, (Object) "");
            }
            if (appRes.config.get(H5NebulaAppConfigs.ASY_REQ_RATE_SHORT) != null) {
                asyncReqRate = String.valueOf(appRes.config.get(H5NebulaAppConfigs.ASY_REQ_RATE_SHORT));
            }
            if (appRes.config.get(H5NebulaAppConfigs.ASY_REQ_RATE) != null) {
                asyncReqRate = String.valueOf(appRes.config.get(H5NebulaAppConfigs.ASY_REQ_RATE));
            }
            if (!TextUtils.isEmpty(asyncReqRate)) {
                extraConfig.put((String) H5NebulaAppConfigs.ASY_REQ_RATE, (Object) asyncReqRate);
            } else {
                extraConfig.put((String) H5NebulaAppConfigs.ASY_REQ_RATE, (Object) "");
            }
            String prepareTime = "";
            if (appRes.config.get("et") != null) {
                prepareTime = String.valueOf(appRes.config.get("et"));
            }
            if (appRes.config.get(H5NebulaAppConfigs.EXPIRE_TIME) != null) {
                prepareTime = String.valueOf(appRes.config.get(H5NebulaAppConfigs.EXPIRE_TIME));
            }
            if (!TextUtils.isEmpty(prepareTime)) {
                extraConfig.put((String) H5NebulaAppConfigs.EXPIRE_TIME, (Object) prepareTime);
            } else {
                extraConfig.put((String) H5NebulaAppConfigs.EXPIRE_TIME, (Object) "");
            }
            String resInvalidTime = "";
            if (appRes.config.get(H5NebulaAppConfigs.RES_INVALID_TIME_SHORT) != null) {
                resInvalidTime = String.valueOf(appRes.config.get(H5NebulaAppConfigs.RES_INVALID_TIME_SHORT));
            }
            if (appRes.config.get(H5NebulaAppConfigs.RES_INVALID_TIME) != null) {
                resInvalidTime = String.valueOf(appRes.config.get(H5NebulaAppConfigs.RES_INVALID_TIME));
            }
            if (!TextUtils.isEmpty(resInvalidTime)) {
                extraConfig.put((String) H5NebulaAppConfigs.RES_INVALID_TIME, (Object) resInvalidTime);
            } else {
                extraConfig.put((String) H5NebulaAppConfigs.RES_INVALID_TIME, (Object) "259200");
            }
            String forceReqRate = "";
            if (appRes.config.get(H5NebulaAppConfigs.PRE_FORCE_REQ_RATE_SHORT) != null) {
                forceReqRate = String.valueOf(appRes.config.get(H5NebulaAppConfigs.PRE_FORCE_REQ_RATE_SHORT));
            }
            if (appRes.config.get(H5NebulaAppConfigs.PRE_FORCE_REQ_RATE) != null) {
                forceReqRate = String.valueOf(appRes.config.get(H5NebulaAppConfigs.PRE_FORCE_REQ_RATE));
            }
            if (!TextUtils.isEmpty(forceReqRate)) {
                extraConfig.put((String) H5NebulaAppConfigs.PRE_FORCE_REQ_RATE, (Object) forceReqRate);
            } else {
                extraConfig.put((String) H5NebulaAppConfigs.PRE_FORCE_REQ_RATE, (Object) "");
            }
            H5Log.d("H5AppCenterServiceImpl", "[applyAppResConfig] extraConfig: " + extraConfig);
            H5AppConfigBean h5AppConfigBean = new H5AppConfigBean();
            if (!TextUtils.isEmpty(appPoolLimit)) {
                h5AppConfigBean.setApp_pool_limit(Integer.parseInt(appPoolLimit));
            } else {
                h5AppConfigBean.setApp_pool_limit(0);
            }
            if (!TextUtils.isEmpty(updateReqRate)) {
                h5AppConfigBean.setNormalReqRate(Double.parseDouble(updateReqRate));
            } else {
                h5AppConfigBean.setNormalReqRate(0.0d);
            }
            if (!TextUtils.isEmpty(limitReqRate)) {
                h5AppConfigBean.setLimitReqRate(Double.parseDouble(limitReqRate));
            } else {
                h5AppConfigBean.setLimitReqRate(0.0d);
            }
            if (!extraConfig.isEmpty()) {
                h5AppConfigBean.setExtra(extraConfig.toJSONString());
            } else {
                h5AppConfigBean.setExtra("");
            }
            this.a.createOrUpdateConfig(h5AppConfigBean);
            H5Log.d("H5AppCenterServiceImpl", "[applyAppResConfig] appPoolLimit " + appPoolLimit + " updateReqRate:" + updateReqRate + " limitReqRate:" + limitReqRate + " preReqRate:" + preReqRate);
        }
    }

    public final void a(AppInfo appInfo, boolean forceSync) {
        a(appInfo, forceSync, true);
    }

    public final void a(AppInfo appInfo, boolean forceSync, boolean downLoadAmr) {
        a(appInfo, forceSync, downLoadAmr, false);
    }

    public final void a(AppInfo appInfo, boolean forceSync, boolean downLoadAmr, boolean downloadRando) {
        a(appInfo, forceSync, downLoadAmr, downloadRando, (String) null);
    }

    public final void f() {
        Map allHigAppInfoMap = this.a.getAllHighestAppInfo();
        if (allHigAppInfoMap == null || allHigAppInfoMap.isEmpty()) {
            H5Log.d("H5AppCenterServiceImpl", "allHigAppInfoMap == null");
            return;
        }
        List higVersionAmrList = new ArrayList();
        for (Entry<String, AppInfo> value : allHigAppInfoMap.entrySet()) {
            AppInfo appInfo = (AppInfo) value.getValue();
            if (appInfo != null) {
                H5BaseApp h5BaseApp = new H5App();
                h5BaseApp.setAppInfo(appInfo);
                higVersionAmrList.add(h5BaseApp.getDownloadLocalPath());
            }
        }
        String downloadAmrPath = H5DownloadRequest.getDefaultDownloadDir(H5Utils.getContext());
        if (higVersionAmrList.size() == 0 || TextUtils.isEmpty(downloadAmrPath) || !downloadAmrPath.contains(H5DownloadRequest.nebulaDownload)) {
            H5Log.d("H5AppCenterServiceImpl", "empty return, downloadAmrPath : " + downloadAmrPath);
            return;
        }
        File downloadDirectory = new File(downloadAmrPath);
        if (!downloadDirectory.isDirectory()) {
            H5Log.d("H5AppCenterServiceImpl", downloadAmrPath + " downloadDirectory is not Directory");
            return;
        }
        List<String> allAmrList = new ArrayList<>();
        File[] children = downloadDirectory.listFiles();
        if (children != null && children.length != 0) {
            for (File child : children) {
                allAmrList.add(child.getAbsolutePath());
            }
            if (allAmrList.size() != 0) {
                for (String amrPath : allAmrList) {
                    if (!higVersionAmrList.contains(amrPath) && !amrPath.contains("directory_monitor")) {
                        H5Log.d("H5AppCenterServiceImpl", "delete amr : " + amrPath);
                        H5FileUtil.delete(amrPath);
                    }
                }
            }
        }
    }

    public static void a(long expiredTime) {
        File lastFile;
        String installPath = H5AppGlobal.a((String) "/nebulaInstallApps/");
        if (!TextUtils.isEmpty(installPath)) {
            List<String> deleteInstallList = new ArrayList<>();
            File installDirectory = new File(installPath);
            if (!installDirectory.isDirectory()) {
                H5Log.d("H5AppCenterServiceImpl", "[clearAppUnzipPackage] " + installPath + " installDirectory is not Directory");
                return;
            }
            File[] appIdDirectory = installDirectory.listFiles();
            if (appIdDirectory != null && appIdDirectory.length != 0) {
                int length = appIdDirectory.length;
                for (int i = 0; i < length; i++) {
                    File appIdFile = appIdDirectory[i];
                    if (appIdFile != null && appIdFile.isDirectory()) {
                        File[] installFile = appIdFile.listFiles();
                        if (installFile == null || installFile.length <= 1) {
                            lastFile = (installFile == null || installFile.length <= 0) ? null : installFile[0];
                        } else {
                            File latestFile = installFile[0];
                            for (int index = 1; index < installFile.length; index++) {
                                File file = installFile[index];
                                if (!(file == null || latestFile == null)) {
                                    String canDeletePath = file.lastModified() > latestFile.lastModified() ? latestFile.getAbsolutePath() : file.getAbsolutePath();
                                    H5Log.d("H5AppCenterServiceImpl", "[clearAppUnzipPackage] add duplicated old package: (" + canDeletePath);
                                    deleteInstallList.add(canDeletePath);
                                    if (file.lastModified() > latestFile.lastModified()) {
                                        latestFile = file;
                                    }
                                }
                            }
                            lastFile = latestFile;
                        }
                        boolean needDeleteByExpired = !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5WalletWrapper.getConfig("h5_needDeleteByExpired4A"));
                        if (lastFile != null && needDeleteByExpired) {
                            if (lastFile.lastModified() + expiredTime < System.currentTimeMillis()) {
                                H5Log.d("H5AppCenterServiceImpl", "[clearAppUnzipPackage] add expired (lastModified: " + lastFile.lastModified() + ") " + lastFile);
                                deleteInstallList.add(lastFile.getAbsolutePath());
                            }
                        }
                    }
                }
                if (deleteInstallList.size() == 0) {
                    H5Log.d("H5AppCenterServiceImpl", "[clearAppUnzipPackage] deleteInstallList is Empty");
                    return;
                }
                for (String deletePath : deleteInstallList) {
                    H5Log.d("H5AppCenterServiceImpl", "[clearAppUnzipPackage] delete installPkg : " + deletePath);
                    H5FileUtil.delete(deletePath);
                }
            }
        }
    }

    private void a(List<AppInfo> appInfoList, boolean downLoadAmr, boolean downloadRandom, String scene) {
        for (AppInfo appInfo : appInfoList) {
            b(appInfo);
        }
        this.a.saveAppInfoList(appInfoList);
        for (AppInfo appInfo2 : appInfoList) {
            a(appInfo2);
            a(appInfo2, downLoadAmr, downloadRandom, scene);
        }
    }

    private void a(AppInfo appInfo, boolean forceSync, boolean downLoadAmr, boolean downloadRandom, String scene) {
        if (appInfo == null) {
            H5Log.e((String) "H5AppCenterServiceImpl", (String) "appInfo == null return");
            return;
        }
        b(appInfo);
        this.a.saveAppInfo(appInfo, forceSync);
        a(appInfo);
        a(appInfo, downLoadAmr, downloadRandom, scene);
    }

    private void a(AppInfo appInfo, boolean downLoadAmr, boolean downloadRandom, String scene) {
        H5Log.d("H5AppCenterServiceImpl", "downApp:" + appInfo.app_id + " version:" + appInfo.version + " auto_install:" + appInfo.auto_install + " downLoadAmr:" + downLoadAmr + " downloadRandom " + downloadRandom);
        if (!H5AppUtil.downloadH5App(appInfo)) {
            this.a.updateUnavailableReason(appInfo.app_id, appInfo.version, "1");
            if (appInfo.auto_install == 0) {
                H5WifiDownloadList.put(appInfo.app_id, appInfo.version);
                H5Log.d("H5AppCenterServiceImpl", "not in wifi save data to H5WifiDownloadList" + appInfo.app_id + Token.SEPARATOR + appInfo.version);
            }
        } else if (downLoadAmr) {
            a(appInfo, scene);
        }
    }

    private static void a(AppInfo appInfo) {
        if (b != null && !b.isEmpty()) {
            for (H5PreSetPkgInfo h5PreSetPkgInfo : b) {
                if (h5PreSetPkgInfo != null && h5PreSetPkgInfo.getInputStream() != null && TextUtils.equals(h5PreSetPkgInfo.getAppId(), appInfo.app_id) && TextUtils.equals(h5PreSetPkgInfo.getVersion(), appInfo.version)) {
                    H5Log.d("H5AppCenterServiceImpl", "set h5PreSetPkgInfo appId:" + appInfo.app_id + " version:" + appInfo.version);
                    a(h5PreSetPkgInfo.getInputStream(), appInfo, h5PreSetPkgInfo.getForceSync());
                }
            }
        }
    }

    private void b(AppInfo appInfo) {
        if (appInfo != null) {
            H5App h5AppNew = new H5App();
            h5AppNew.setAppInfo(appInfo);
            String downloadFilePathNew = h5AppNew.getDownloadedFilePath();
            H5Log.d("H5AppCenterServiceImpl", "downloadPathNew : " + downloadFilePathNew);
            List list = this.a.getCanDeleteAppPooIdList(appInfo.app_id);
            int limit = this.a.getAppPoolLimit();
            if (list != null && !list.isEmpty() && limit != 0) {
                while (list != null && !list.isEmpty() && list.size() >= limit) {
                    H5Log.d("H5AppCenterServiceImpl", "getCanDeleteAppPooIdList size:" + list.size() + " limit:" + limit);
                    H5NebulaAppBean h5NebulaAppBean = list.get(0);
                    H5App h5App = new H5App();
                    if (h5NebulaAppBean != null) {
                        if (H5LoadingApp.contain(h5NebulaAppBean.getApp_id(), h5NebulaAppBean.getVersion())) {
                            H5Log.d("H5AppCenterServiceImpl", "H5LoadingApp contain this not delete");
                        } else if (!H5DBUtil.d() || !h5NebulaAppBean.getVersion().equalsIgnoreCase(this.a.findInstallAppVersion(h5NebulaAppBean.getApp_id()))) {
                            H5Log.d("deleteAppInfo:" + h5NebulaAppBean.getApp_id() + " version:" + h5NebulaAppBean.getVersion());
                            AppInfo deleteApp = new AppInfo();
                            deleteApp.app_id = h5NebulaAppBean.getApp_id();
                            deleteApp.version = h5NebulaAppBean.getVersion();
                            deleteApp.package_url = h5NebulaAppBean.getPackage_url();
                            h5App.setAppInfo(deleteApp);
                            String deleteDownloadPath = h5App.getDownloadedFilePath();
                            H5Log.d("H5AppCenterServiceImpl", "deleteDownloadPath : " + deleteDownloadPath);
                            if (!TextUtils.equals(deleteDownloadPath, downloadFilePathNew)) {
                                H5FileUtil.delete(deleteDownloadPath);
                            }
                            H5FileUtil.delete(h5App.getInstalledPath());
                            this.a.deleteAppInfo(h5NebulaAppBean.getApp_id(), h5NebulaAppBean.getVersion());
                        } else {
                            H5Log.d("H5AppCenterServiceImpl", "not delete install app : " + h5NebulaAppBean.getApp_id() + " version : " + h5NebulaAppBean.getVersion());
                        }
                    }
                    list.remove(0);
                }
            }
        }
    }

    private static void a(AppInfo appInfo, String scene) {
        if (appInfo != null) {
            H5App h5App = new H5App();
            h5App.setAppInfo(appInfo);
            h5App.downloadApp(null, scene);
        }
    }

    private static void a(InputStream inputStream, AppInfo appInfo, boolean forceSync) {
        H5App h5App = new H5App();
        h5App.setAppInfo(appInfo);
        h5App.presetAppPackage(inputStream, forceSync);
    }
}
