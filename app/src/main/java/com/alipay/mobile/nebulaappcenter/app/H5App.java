package com.alipay.mobile.nebulaappcenter.app;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.nebula.appcenter.H5BaseApp;
import com.alipay.mobile.nebula.appcenter.H5PresetInfo;
import com.alipay.mobile.nebula.appcenter.H5PresetPkg;
import com.alipay.mobile.nebula.appcenter.api.H5LoadPresetListen;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppScoreList;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadCallback;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadRequest;
import com.alipay.mobile.nebula.appcenter.download.H5ExternalDownloadManager;
import com.alipay.mobile.nebula.appcenter.downloadImpl.H5AppDownLoadImpl;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.appcenter.wifidownload.H5WifiDownloadList;
import com.alipay.mobile.nebula.callback.H5AppInstallCallback;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5AppCenterPresetProvider;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5PatchProvider;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5NetworkUtil;
import com.alipay.mobile.nebula.util.H5NetworkUtil.Network;
import com.alipay.mobile.nebula.util.H5NetworkUtil.NetworkListener;
import com.alipay.mobile.nebula.util.H5SecurityUtil;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.util.H5ZipUtil;
import com.alipay.mobile.nebula.wallet.H5WalletWrapper;
import com.alipay.mobile.nebulaappcenter.util.H5AppGlobal;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class H5App extends H5DownloadCallback implements H5BaseApp {
    private static boolean m = false;
    /* access modifiers changed from: private */
    public String a = "H5NebulaApp";
    private final String b = H5AppGlobal.a((String) "/nebulaInstallApps/");
    /* access modifiers changed from: private */
    public H5ExternalDownloadManager c = ((H5ExternalDownloadManager) H5Utils.getProvider(H5ExternalDownloadManager.class.getName()));
    /* access modifiers changed from: private */
    public AppInfo d;
    private Context e = H5Utils.getContext();
    /* access modifiers changed from: private */
    public H5NebulaDBService f = H5NebulaDBService.getInstance();
    private NetworkListener g;
    private H5DownloadCallback h;
    private String i;
    /* access modifiers changed from: private */
    public boolean j = false;
    private boolean k = false;
    /* access modifiers changed from: private */
    public boolean l = false;
    private String n = null;
    private String o = null;
    private String p;

    public H5App() {
        if (this.c == null) {
            H5Log.w(this.a, "h5ExternalDownloadManager == null use H5AppDownLoadImpl");
            this.c = new H5AppDownLoadImpl();
        }
    }

    public String getAppId() {
        if (this.d != null) {
            return this.d.app_id;
        }
        return null;
    }

    private boolean a() {
        boolean enable = true;
        if (((H5PatchProvider) H5Utils.getProvider(H5PatchProvider.class.getName())) == null) {
            H5Log.e(this.a, (String) "H5PatchProvider ==null not use patch");
            enable = false;
        } else {
            if (TextUtils.isEmpty(c(this.d.app_id))) {
                enable = false;
            }
            if (!enable) {
                H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                if (h5ConfigProvider != null && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfig("h5_clear_patch"))) {
                    i();
                }
            }
        }
        H5Log.d(this.a, "enablePatch " + enable);
        return enable;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.d = appInfo;
        if (appInfo != null) {
            this.a += "_" + appInfo.app_id + "_" + appInfo.version;
        }
    }

    public String getDownloadUrl() {
        if (this.d == null) {
            return null;
        }
        if (TextUtils.isEmpty(this.d.patch) || !a()) {
            return this.d.package_url;
        }
        return this.d.patch;
    }

    public String getDownloadLocalPath() {
        return getDownloadedFilePath();
    }

    public String getAppVersion() {
        if (this.d != null) {
            return this.d.version;
        }
        return null;
    }

    private void b() {
        H5DownloadRequest h5DownloadRequest = c();
        if (TextUtils.isEmpty(h5DownloadRequest.getDownloadUrl()) || !h5DownloadRequest.getDownloadUrl().startsWith("http")) {
            return;
        }
        if (this.c != null) {
            H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                public void run() {
                    H5App.this.f.updateUnavailableReason(H5App.this.getAppId(), H5App.this.getAppVersion(), "5");
                }
            });
            try {
                this.c.addDownload(h5DownloadRequest, this);
                if (h5DownloadRequest.isFromPreDownload()) {
                    d();
                }
            } catch (Throwable throwable) {
                H5Log.e(this.a, throwable);
            }
        } else {
            H5Log.e(this.a, (String) "doDownloadApp h5ExternalDownloadManager is null");
        }
    }

    private H5DownloadRequest c() {
        String downloadUrl = getDownloadUrl();
        H5DownloadRequest request = new H5DownloadRequest();
        request.setAppId(getAppId());
        request.setVersion(getAppVersion());
        if (this.d != null) {
            request.setDescription(this.d.app_dsec);
            request.setTitle(this.d.name);
        }
        request.setDownloadUrl(downloadUrl);
        request.setFileName(f());
        request.setScene(this.i);
        return request;
    }

    private void d() {
        if (this.g == null) {
            if (this.d != null && this.d.auto_install == 1) {
                return;
            }
            if (this.h != null) {
                H5Log.d(this.a, " has downloadCallback not cacel");
                return;
            }
            this.g = new NetworkListener() {
                public void onNetworkChanged(Network last, Network next) {
                    if (next != Network.NETWORK_WIFI && H5App.this.c != null) {
                        H5Utils.executeOrdered("H5NebulaApp", new Runnable() {
                            public void run() {
                                try {
                                    H5App.this.f.updateUnavailableReason(H5App.this.getAppId(), H5App.this.getAppVersion(), "6");
                                    String download = H5App.this.getDownloadUrl();
                                    H5Log.d(H5App.this.a, " net change is not wifi and auto!=1 so cancel " + download);
                                    H5App.this.c.cancel(download);
                                } catch (Throwable throwable) {
                                    H5Log.e(H5App.this.a, throwable);
                                }
                            }
                        });
                    }
                }
            };
            H5NetworkUtil.getInstance().addListener(this.g);
        }
    }

    public void onPrepare(H5DownloadRequest h5DownloadRequest) {
        if (this.h != null) {
            this.h.onPrepare(h5DownloadRequest);
        }
    }

    public void onCancel(H5DownloadRequest h5DownloadRequest) {
        H5AppUtil.appCenterLog("H5_APP_DOWNLOAD", this.d, "^step=cancel");
        H5Log.d(this.a, "download onCancel");
        if (this.g != null) {
            H5NetworkUtil.getInstance().removeListener(this.g);
            this.g = null;
        }
        if (this.h != null) {
            this.h.onCancel(h5DownloadRequest);
        }
    }

    public void onFailed(H5DownloadRequest h5DownloadRequest, int errorCode, String errorMsg) {
        H5AppUtil.appCenterLog("H5_APP_DOWNLOAD", this.d, "^step=fail^err=[" + errorCode + "]" + errorMsg);
        if (this.d != null) {
            H5Log.d(this.a, "appId:" + this.d.app_id + " version:" + this.d.version + " download onFailed, errorCode:" + errorCode + ",errorMsg:" + errorMsg);
        }
        final String downloadFilePath = getDownloadedFilePath();
        if (this.d != null && !TextUtils.isEmpty(downloadFilePath)) {
            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                public void run() {
                    H5Log.d(H5App.this.a, "onFailed delete : " + downloadFilePath);
                    H5FileUtil.delete(downloadFilePath);
                }
            });
        }
        if (this.g != null) {
            H5NetworkUtil.getInstance().removeListener(this.g);
            this.g = null;
        }
        if (this.h != null) {
            this.h.onFailed(h5DownloadRequest, errorCode, errorMsg);
        }
        H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
            public void run() {
                H5App.this.f.updateUnavailableReason(H5App.this.d.app_id, H5App.this.d.version, "3");
            }
        });
        if (this.d != null) {
            H5WifiDownloadList.put(this.d.app_id, this.d.version);
        }
    }

    public void onProgress(H5DownloadRequest h5DownloadRequest, int progress) {
        if (this.h != null) {
            this.h.onProgress(h5DownloadRequest, progress);
        }
    }

    public void onFinish(H5DownloadRequest h5DownloadRequest, String savePath) {
        if (this.d != null) {
            H5Log.d(this.a, "NebulaApp离线包下载完成：appName:" + this.d.name + " appId:" + this.d.app_id + " version:" + this.d.version + " savePath:" + savePath);
        }
        if (this.g != null) {
            H5NetworkUtil.getInstance().removeListener(this.g);
            this.g = null;
        }
        if (this.h != null) {
            this.h.onFinish(h5DownloadRequest, savePath);
        } else if (this.d == null) {
        } else {
            if (e()) {
                H5Log.d(this.a, "disableAllPreInstall true, not pre install");
                return;
            }
            JSONObject extraInfo = H5Utils.parseObject(this.d.extend_info_jo);
            if ((extraInfo == null || extraInfo.getIntValue(H5AppUtil.preset) != 1) && !H5AppScoreList.getInstance().isInStrategy(this.d.app_id, 1)) {
                H5Utils.executeOrdered("H5NebulaApp", new Runnable() {
                    public void run() {
                        if (H5App.this.j) {
                            H5Log.d(H5App.this.a, "h5PatchFail to installApp");
                            H5App.this.installApp();
                        } else if ("yes".equalsIgnoreCase(H5WalletWrapper.getConfigWithProcessCache("h5_enablePreInstallOld"))) {
                            String path = H5App.this.c(H5App.this.d.app_id);
                            H5Log.d(H5App.this.a, "pre install app : " + H5App.this.d.app_id + " getLastPkgPath:" + path);
                            if (!TextUtils.isEmpty(path)) {
                                H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
                                if (h5AppProvider != null) {
                                    String currentVersion = h5AppProvider.getVersion(H5App.this.d.app_id);
                                    if (H5AppUtil.compareVersion(currentVersion, H5App.this.d.version) == 1) {
                                        H5Log.d(H5App.this.a, "currentVersion " + currentVersion + " > appInfo.version:" + H5App.this.d.version + " not to preInstall");
                                        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                                        if (h5ConfigProvider != null && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_preInstall_compare_version"))) {
                                            return;
                                        }
                                    }
                                }
                                H5App.this.l = true;
                                H5App.this.installApp();
                            }
                        }
                    }
                });
            } else {
                H5Utils.executeOrdered("H5NebulaApp", new Runnable() {
                    public void run() {
                        H5App.this.l = true;
                        H5Log.d(H5App.this.a, "preInstall by appscore");
                        H5App.this.installApp();
                    }
                });
            }
        }
    }

    private static boolean e() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        return h5ConfigProvider != null && BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_enableAllPreInstall"));
    }

    public boolean installApp() {
        boolean installSuccess;
        if (isPkgAvailable()) {
            installSuccess = a(getDownloadedFilePath());
        } else {
            installSuccess = isInstalled();
        }
        if (this.d != null) {
            H5Log.d(this.a, "installApp appId:" + this.d.app_id + " version:" + this.d.version + Token.SEPARATOR + installSuccess);
            if (!installSuccess) {
                this.f.updateUnavailableReason(this.d.app_id, this.d.version, "4");
            }
        }
        return installSuccess;
    }

    public boolean installApp(H5AppInstallCallback h5AppInstallListen) {
        boolean result = installApp();
        if (h5AppInstallListen != null) {
            h5AppInstallListen.onResult(result, this.j);
        }
        return result;
    }

    public boolean installApp(boolean async) {
        this.k = async;
        return installApp();
    }

    public void presetAppPackage(InputStream inputStream, boolean force) {
        if (presetH5pkg(inputStream, getInstalledPath())) {
            onInstallComplete(true);
        }
    }

    public void presetApp(InputStream inputStream, H5LoadPresetListen h5LoadPresetListen) {
        String installPath = getInstalledPath();
        boolean success = presetH5pkg(inputStream, installPath);
        if (h5LoadPresetListen != null) {
            H5Log.d(this.a, "presetApp " + success + Token.SEPARATOR + installPath);
            if (success) {
                onInstallComplete(true);
                h5LoadPresetListen.getPresetPath(installPath);
                return;
            }
            h5LoadPresetListen.getPresetPath(null);
        }
    }

    /* JADX INFO: finally extract failed */
    public boolean presetH5pkg(InputStream inputStream, String installPath) {
        try {
            if (H5FileUtil.exists(new File(installPath))) {
                H5Log.e(this.a, installPath + " presetAppPackage has exits,not to unzip");
                a((Closeable) inputStream);
                return true;
            } else if (!H5ZipUtil.unZip(inputStream, installPath)) {
                a((Closeable) inputStream);
                return false;
            } else {
                a((Closeable) inputStream);
                return true;
            }
        } catch (Exception globalException) {
            H5Log.e(this.a, (Throwable) globalException);
            H5FileUtil.delete(installPath);
            a((Closeable) inputStream);
            return false;
        } catch (Throwable th) {
            a((Closeable) inputStream);
            throw th;
        }
    }

    private void a(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e2) {
                H5Log.e(this.a, (Throwable) e2);
            }
        }
    }

    private String a(String fileKey) {
        if (!TextUtils.isEmpty(fileKey)) {
            return H5SecurityUtil.getMD5(fileKey);
        }
        return getAppVersion();
    }

    private String f() {
        return getAppId() + "-" + a(this.d.package_url);
    }

    public String getDownloadedFilePath() {
        String downloadDirectoryPath = H5DownloadRequest.getDefaultDownloadDir(H5Utils.getContext());
        if (TextUtils.isEmpty(downloadDirectoryPath) || "/".equals(downloadDirectoryPath)) {
            return "";
        }
        String downloadedFilePath = downloadDirectoryPath + "/" + f();
        H5Log.d(this.a, "downloadedFilePath:" + downloadedFilePath);
        return downloadedFilePath;
    }

    public boolean isPkgAvailable() {
        String downloadPath = getDownloadedFilePath();
        boolean isAvailable = false;
        if (H5FileUtil.exists(downloadPath)) {
            isAvailable = true;
        }
        H5Log.d(this.a, "isPkgAvailable: path:" + downloadPath + " isAvailable:" + isAvailable);
        return isAvailable;
    }

    public boolean isInstalled() {
        if (this.d == null) {
            return false;
        }
        File file = new File(getInstalledPath());
        H5Log.d(this.a, "isInstalled: " + file.exists() + " appId:" + this.d.app_id + " version:" + this.d.version);
        if (!file.exists()) {
            return false;
        }
        try {
            File[] listFiles = file.listFiles();
            if (listFiles == null || listFiles.length <= 0) {
                return false;
            }
            int length = listFiles.length;
            H5Log.d(this.a, "isInstalled length:" + length);
            if (length < 4) {
                String fileNote = "";
                boolean containTar = false;
                for (File name : listFiles) {
                    String name2 = name.getName();
                    H5Log.d(this.a, "install dir file " + name2);
                    fileNote = fileNote + "_" + name2;
                    if (name2.contains("tar")) {
                        containTar = true;
                    }
                }
                H5LogUtil.logNebulaTech(H5LogData.seedId("h5_nebulaApp_intallApp_exception").param1().add(getAppId(), null).param2().add(getAppVersion(), null).param3().add(fileNote, null));
                if (!H5Utils.isMain()) {
                    H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                    if (h5ConfigProvider != null && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfig("h5_delete_installAppFile")) && H5AppUtil.isH5AppPkg(this.d) && !containTar) {
                        H5AppUtil.deleteNebulaInstallFileAndDB(file.getAbsolutePath(), this.d.app_id);
                        H5Log.d(this.a, "install file is broken delete return notInstall");
                        return false;
                    }
                } else {
                    H5Log.d(this.a, "in Main thread not delete");
                }
            }
            return true;
        } catch (Exception e2) {
            H5Log.e(this.a, (Throwable) e2);
            return false;
        }
    }

    public String getInstalledPath() {
        if (this.d == null) {
            return "";
        }
        String path = this.b + g();
        H5Log.d(this.a, "getInstalledPath:" + path);
        return path;
    }

    private synchronized String g() {
        String fileName;
        String appId = getAppId();
        String packageUrl = this.d.package_url;
        if (this.p == null || appId == null || !appId.equals(this.n) || packageUrl == null || !packageUrl.equals(this.o)) {
            fileName = getAppId() + "/" + a(this.d.package_url) + "/";
            this.p = fileName;
            this.n = appId;
            this.o = packageUrl;
        } else {
            fileName = this.p;
        }
        return fileName;
    }

    private synchronized boolean a(String... params) {
        boolean z;
        String filePath = null;
        try {
            if (this.d == null) {
                z = false;
            } else {
                filePath = params[0];
                H5Log.d(this.a, "installApp filePath:" + filePath);
                if (!TextUtils.isEmpty(filePath)) {
                    File file = new File(filePath);
                    if (!file.exists()) {
                        H5Log.d(this.a, "installApp !file.exists()");
                        z = false;
                    } else if (isInstalled()) {
                        H5Log.d(this.a, "is install return");
                        z = true;
                    } else if (TextUtils.isEmpty(this.d.patch)) {
                        H5AppUtil.appCenterLog("H5_APP_UNZIP", this.d, "^step=start^isPatch=no");
                        long time = System.currentTimeMillis();
                        b(this.d.app_id);
                        boolean unZipResult = H5ZipUtil.unZip(filePath, getInstalledPath());
                        H5Log.d(this.a, getAppId() + " installApp spend unzip " + (System.currentTimeMillis() - time));
                        if (!unZipResult || !isInstalled()) {
                            try {
                                H5Log.d(this.a, "H5_APP_UNZIP fail");
                                H5FileUtil.delete(file);
                                H5AppUtil.appCenterLog("H5_APP_UNZIP", this.d, "^step=fail^isPatch=no");
                                H5Log.e(this.a, (String) "installApp !unZipResult || !isInstalled() return false");
                                z = false;
                            } catch (Exception e2) {
                                H5Log.e(this.a, "installApp delete exception", e2);
                                z = false;
                            }
                        } else {
                            H5FileUtil.delete(file);
                            onInstallComplete(true);
                            H5AppUtil.appCenterLog("H5_APP_UNZIP", this.d, "^step=success^isPatch=no");
                            z = true;
                        }
                    } else {
                        H5AppUtil.appCenterLog("H5_APP_UNZIP", this.d, "^step=start^isPatch=yes");
                        H5Log.d(this.a, "h5App patcher update. appId: " + getAppId() + "; filePath = " + filePath + " patch:" + this.d.patch + " patchInstalling : " + m);
                        if (!m || !h()) {
                            m = true;
                            String lathPath = c(this.d.app_id);
                            boolean patcherResult = a(filePath, this.d.app_id, lathPath);
                            this.j = patcherResult;
                            m = false;
                            H5LogUtil.logNebulaTech(H5LogData.seedId("h5_nebulaApp_installApp_patch").param1().add(getAppId(), null).param2().add(getAppVersion(), null).param3().add("success", Boolean.valueOf(patcherResult)));
                            if (!patcherResult) {
                                b(this.d.app_id);
                                H5FileUtil.delete(filePath);
                                i();
                                if (!this.l || H5Utils.isInWifi()) {
                                    b();
                                }
                                H5Log.e(this.a, (String) "installApp !patcherResult return false");
                                H5AppUtil.appCenterLog("H5_APP_UNZIP", this.d, "^step=fail^isPatch=yes");
                                z = false;
                            } else {
                                H5FileUtil.delete(file);
                                H5FileUtil.delete(lathPath);
                                onInstallComplete(true);
                                H5AppUtil.appCenterLog("H5_APP_UNZIP", this.d, "^step=success^isPatch=yes");
                                z = true;
                            }
                        } else {
                            H5Log.d(this.a, "patchInstalling return;");
                            H5AppUtil.appCenterLog("H5_APP_UNZIP", this.d, "^step=patchInstalling^isPatch=yes");
                            z = false;
                        }
                    }
                }
                z = false;
            }
        } catch (Exception globalException) {
            H5Log.e(this.a, "error", globalException);
            if (!TextUtils.isEmpty(this.d.patch)) {
                m = false;
            }
            H5FileUtil.delete(getInstalledPath());
            H5FileUtil.delete(filePath);
            if (!TextUtils.isEmpty(this.d.patch)) {
                H5Log.d(this.a, "h5App patcher fail And downloadAll start.");
                i();
                downloadApp();
            } else {
                onInstallComplete(false);
            }
        }
        return z;
    }

    private static boolean h() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_controlConcurrentPatch"))) {
            return true;
        }
        return false;
    }

    private void i() {
        H5Log.d(this.a, "clearPatchDBInfo ");
        this.d.patch = "";
        if (this.f != null) {
            this.f.saveAppInfo(this.d, true);
        }
    }

    public final void downloadApp() {
        j();
        if (this.h != null) {
            if (isPkgAvailable() || isInstalled()) {
                this.h.onFinish(c(), getDownloadedFilePath());
                return;
            }
        } else if (isPkgAvailable() || isInstalled() || isDownloading()) {
            return;
        }
        b();
    }

    public void downloadApp(H5DownloadCallback h5DownloadCallback) {
        downloadApp(h5DownloadCallback, null);
    }

    public void downloadApp(H5DownloadCallback h5DownloadCallback, String scene) {
        this.h = h5DownloadCallback;
        this.i = scene;
        downloadApp();
    }

    private void j() {
        H5AppCenterPresetProvider presetProvider = (H5AppCenterPresetProvider) H5Utils.getProvider(H5AppCenterPresetProvider.class.getName());
        if (presetProvider != null && presetProvider.getH5PresetPkg() != null) {
            try {
                H5PresetPkg h5PresetPkg = presetProvider.getH5PresetPkg();
                if (h5PresetPkg.getPreSetInfo() != null && h5PresetPkg.getPreSetInfo().containsKey(getAppId()) && !isInstalled()) {
                    H5PresetInfo details = h5PresetPkg.getPreSetInfo().get(getAppId());
                    String amrName = details.appId;
                    String version = details.version;
                    H5Log.d(this.a, "setWalletPreset getPreSetInfo  " + amrName + Token.SEPARATOR + version);
                    if (!TextUtils.isEmpty(amrName) && !TextUtils.isEmpty(version) && TextUtils.equals(version, getAppVersion())) {
                        InputStream isSrc = this.e.getAssets().open(h5PresetPkg.getPresetPath() + amrName);
                        if (isSrc != null) {
                            H5Log.d(this.a, "setWalletPreset " + getAppId() + Token.SEPARATOR + getAppVersion());
                            presetAppPackage(isSrc, true);
                        }
                    }
                }
            } catch (Exception e2) {
                H5Log.e(this.a, "setWalletPreset not exist", e2);
            }
        }
    }

    public boolean isDownloading() {
        if (this.c == null) {
            H5Log.e(this.a, (String) "isDownloading h5ExternalDownloadManager==null");
        }
        return this.c != null && this.c.isDownloading(getDownloadUrl());
    }

    private void b(String appId) {
        String path = this.b + appId + "/";
        if (H5FileUtil.exists(path)) {
            H5Log.d(this.a, "deleteOldPkgByFullInstall " + path);
            H5FileUtil.delete(path);
        }
    }

    /* access modifiers changed from: private */
    public String c(String appId) {
        String installVersion = this.f.findInstallAppVersion(appId);
        if (TextUtils.isEmpty(installVersion) || TextUtils.isEmpty(appId) || installVersion.equals(getAppVersion())) {
            return null;
        }
        AppInfo lastInfo = this.f.getAppInfo(appId, installVersion);
        if (lastInfo != null) {
            H5BaseApp h5BaseApp = new H5App();
            h5BaseApp.setAppInfo(lastInfo);
            String lastInstallPath = h5BaseApp.getInstalledPath();
            if (H5FileUtil.exists(lastInstallPath)) {
                H5Log.d(this.a, "lastInstallPath:" + lastInstallPath + " lastVersion:" + lastInfo.version);
                return lastInstallPath;
            }
        }
        return null;
    }

    public synchronized void onInstallComplete(boolean isInstallSuccess) {
        if (this.d != null) {
            H5Log.d(this.a, "onInstallComplete:" + isInstallSuccess + " version:" + this.d.version + " appId:" + this.d.app_id);
            if (isInstallSuccess) {
                try {
                    if (this.k) {
                        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                        if (h5ConfigProvider != null) {
                            if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("H5_async_install"))) {
                                this.f.insertInstalledAppInfo(getAppId(), getAppVersion(), getInstalledPath());
                            } else {
                                H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                                    public void run() {
                                        H5App.this.f.insertInstalledAppInfo(H5App.this.getAppId(), H5App.this.getAppVersion(), H5App.this.getInstalledPath());
                                    }
                                });
                            }
                        }
                    } else {
                        this.f.insertInstalledAppInfo(getAppId(), getAppVersion(), getInstalledPath());
                    }
                } catch (Throwable throwable) {
                    H5Log.e(this.a, throwable);
                }
            }
        }
        return;
    }

    private boolean a(String filePath, String appId, String lathPath) {
        if (TextUtils.isEmpty(filePath) || TextUtils.isEmpty(lathPath)) {
            return false;
        }
        File file = new File(filePath);
        String unzipDirectory = H5DownloadRequest.getDefaultDownloadDir(H5Utils.getContext()) + "/" + getAppId() + "_patch/";
        if (!H5ZipUtil.unZip(filePath, unzipDirectory)) {
            H5FileUtil.delete(unzipDirectory);
            H5FileUtil.delete(file);
            throw new Exception();
        }
        String patchPath = unzipDirectory + "/patch";
        String oldMd5 = "";
        String patchMd5 = "";
        JSONObject jsonObject = H5Utils.parseObject(d(unzipDirectory + "/md5.json"));
        if (jsonObject != null && !jsonObject.isEmpty()) {
            oldMd5 = H5Utils.getString(jsonObject, (String) "old");
            patchMd5 = H5Utils.getString(jsonObject, (String) "patch");
        }
        H5Log.d(this.a, "oldMd5 : " + oldMd5 + ", patchMd5: " + patchMd5);
        if (TextUtils.isEmpty(oldMd5) || TextUtils.isEmpty(patchMd5)) {
            return false;
        }
        boolean patcherResult = false;
        H5PatchProvider h5PatchProvider = (H5PatchProvider) H5Utils.getProvider(H5PatchProvider.class.getName());
        if (h5PatchProvider != null) {
            patcherResult = h5PatchProvider.patcherDir(this.e, getInstalledPath(), lathPath, patchPath, oldMd5, patchMd5);
        } else {
            H5Log.e(this.a, (String) "h5PatchProvider==null , setProvider for H5PatchProvider");
        }
        H5Log.d(this.a, "patcherResult :" + patcherResult + " appId:" + appId + " version:" + this.d.version);
        H5FileUtil.delete(unzipDirectory);
        return patcherResult;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0040, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0041, code lost:
        com.alipay.mobile.nebula.util.H5Log.d(r8.a, r2.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0023, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0024, code lost:
        com.alipay.mobile.nebula.util.H5Log.d(r8.a, r2.toString());
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x0023 A[ExcHandler: FileNotFoundException (r2v1 'e' java.io.FileNotFoundException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String d(java.lang.String r9) {
        /*
            r8 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x0023, IOException -> 0x0040 }
            r3.<init>(r9)     // Catch:{ FileNotFoundException -> 0x0023, IOException -> 0x0040 }
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch:{ FileNotFoundException -> 0x0023, IOException -> 0x0040 }
            java.io.InputStreamReader r6 = new java.io.InputStreamReader     // Catch:{ FileNotFoundException -> 0x0023, IOException -> 0x0040 }
            r6.<init>(r3)     // Catch:{ FileNotFoundException -> 0x0023, IOException -> 0x0040 }
            r5.<init>(r6)     // Catch:{ FileNotFoundException -> 0x0023, IOException -> 0x0040 }
            r6 = 1024(0x400, float:1.435E-42)
            char[] r1 = new char[r6]     // Catch:{ FileNotFoundException -> 0x0023, IOException -> 0x0040 }
        L_0x0018:
            int r4 = r5.read(r1)     // Catch:{ FileNotFoundException -> 0x0023, IOException -> 0x0040 }
            if (r4 <= 0) goto L_0x0032
            r6 = 0
            r0.append(r1, r6, r4)     // Catch:{ FileNotFoundException -> 0x0023, IOException -> 0x0040 }
            goto L_0x0018
        L_0x0023:
            r2 = move-exception
            java.lang.String r6 = r8.a
            java.lang.String r7 = r2.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r6, r7)
        L_0x002d:
            java.lang.String r6 = r0.toString()
            return r6
        L_0x0032:
            r5.close()     // Catch:{ FileNotFoundException -> 0x0023, IOException -> 0x0040 }
            r3.close()     // Catch:{ IOException -> 0x0039, FileNotFoundException -> 0x0023 }
            goto L_0x002d
        L_0x0039:
            r2 = move-exception
            java.lang.String r6 = r8.a     // Catch:{ FileNotFoundException -> 0x0023, IOException -> 0x0040 }
            com.alipay.mobile.nebula.util.H5Log.e(r6, r2)     // Catch:{ FileNotFoundException -> 0x0023, IOException -> 0x0040 }
            goto L_0x002d
        L_0x0040:
            r2 = move-exception
            java.lang.String r6 = r8.a
            java.lang.String r7 = r2.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r6, r7)
            goto L_0x002d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulaappcenter.app.H5App.d(java.lang.String):java.lang.String");
    }

    public boolean isAvailable() {
        return isInstalled() || isPkgAvailable();
    }
}
