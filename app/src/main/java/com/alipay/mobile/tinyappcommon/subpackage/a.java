package com.alipay.mobile.tinyappcommon.subpackage;

import android.text.TextUtils;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadCallback;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadRequest;
import com.alipay.mobile.nebula.appcenter.download.H5ExternalDownloadManager;
import com.alipay.mobile.nebula.appcenter.downloadImpl.H5AppDownLoadImpl;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5SecurityUtil;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.util.H5ZipUtil;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: DownloadProvider */
public final class a extends b {
    private H5ExternalDownloadManager a = ((H5ExternalDownloadManager) H5Utils.getProvider(H5ExternalDownloadManager.class.getName()));
    private String b;
    private ConcurrentHashMap<String, b> c;

    public a(String appId) {
        this.b = appId;
        if (this.a == null) {
            H5Log.w("subpackage.DownloadProvider", "get h5ExternalDownloadManager failed, use H5AppDownLoadImpl");
            this.a = new H5AppDownLoadImpl();
        }
        this.c = new ConcurrentHashMap<>();
    }

    private H5DownloadRequest a(String downloadUrl) {
        if (TextUtils.isEmpty(this.b) || TextUtils.isEmpty(downloadUrl)) {
            return null;
        }
        H5DownloadRequest request = new SubPackageDownloadRequest();
        request.setAppId(this.b);
        request.setDescription("Subpackage Download");
        request.setTitle("Subpackage Download");
        request.setDownloadUrl(downloadUrl);
        request.setFileName(c(this.b, downloadUrl));
        return request;
    }

    public final void a(String downloadUrl, b downloadCallback) {
        if (!TextUtils.isEmpty(downloadUrl)) {
            c installCallback = downloadCallback.a();
            if (installCallback == null) {
                H5Log.e((String) "subpackage.DownloadProvider", (String) "SubPackageInstallCallback in SubPackageDownloadCallback is null");
            } else if (e(this.b, downloadUrl)) {
                H5Log.d("subpackage.DownloadProvider", "prepareSubpackage Package has been downloaded and installed, appId: " + this.b + " url:" + downloadUrl);
                downloadCallback.onFinish(null, a(downloadUrl, this.b));
                installCallback.a(true, downloadUrl);
            } else if (e(downloadUrl)) {
                H5Log.d("subpackage.DownloadProvider", "prepareSubpackage Package downloaded not installed, to install, appId: " + this.b + " url:" + downloadUrl);
                downloadCallback.onFinish(null, a(downloadUrl, this.b));
                if (b(this.b, downloadUrl)) {
                    installCallback.a(true, downloadUrl);
                    return;
                }
                installCallback.a(false, downloadUrl);
                H5Log.e((String) "subpackage.DownloadProvider", (String) "prepareSubpackage install failed");
            } else {
                H5Log.d("subpackage.DownloadProvider", "prepareSubpackage Package not downloaded not installed, to download and install, appId: " + this.b + " url:" + downloadUrl);
                if (this.c != null) {
                    this.c.put(downloadUrl, downloadCallback);
                }
                a(downloadUrl, (H5DownloadCallback) this);
            }
        }
    }

    public final void onPrepare(H5DownloadRequest h5DownloadRequest) {
        if (h5DownloadRequest != null) {
            String downloadUrl = h5DownloadRequest.getDownloadUrl();
            if (!TextUtils.isEmpty(downloadUrl)) {
                b downloadCallback = this.c.get(downloadUrl);
                if (downloadCallback != null) {
                    downloadCallback.onPrepare(h5DownloadRequest);
                }
            }
        }
    }

    public final void onProgress(H5DownloadRequest h5DownloadRequest, int progress) {
        if (h5DownloadRequest != null) {
            String downloadUrl = h5DownloadRequest.getDownloadUrl();
            if (!TextUtils.isEmpty(downloadUrl) && this.c != null) {
                b downloadCallback = this.c.get(downloadUrl);
                if (downloadCallback != null) {
                    downloadCallback.onProgress(h5DownloadRequest, progress);
                }
            }
        }
    }

    public final void onCancel(H5DownloadRequest h5DownloadRequest) {
        if (h5DownloadRequest != null) {
            String downloadUrl = h5DownloadRequest.getDownloadUrl();
            if (!TextUtils.isEmpty(downloadUrl) && this.c != null) {
                b downloadCallback = this.c.get(downloadUrl);
                if (downloadCallback != null) {
                    downloadCallback.onCancel(h5DownloadRequest);
                    this.c.remove(downloadUrl);
                }
            }
        }
    }

    public final void onFinish(H5DownloadRequest h5DownloadRequest, String savePath) {
        H5Log.e((String) "subpackage.DownloadProvider", (String) "subpackage get add download onFinished");
        if (h5DownloadRequest != null) {
            String downloadUrl = h5DownloadRequest.getDownloadUrl();
            String appId = h5DownloadRequest.getAppId();
            if (!TextUtils.isEmpty(downloadUrl) && !TextUtils.isEmpty(appId) && this.c != null) {
                b downloadCallback = this.c.get(downloadUrl);
                if (downloadCallback == null) {
                    H5Log.e((String) "subpackage.DownloadProvider", (String) "get SubPackageDownloadCallback from map Failed");
                    return;
                }
                downloadCallback.onFinish(h5DownloadRequest, savePath);
                boolean installResult = b(appId, downloadUrl);
                c installCallback = downloadCallback.a();
                if (installCallback == null) {
                    H5Log.e((String) "subpackage.DownloadProvider", (String) "SubPackageInstallCallback from SubPackageDownloadCallback is null");
                } else if (installResult) {
                    installCallback.a(true, downloadUrl);
                } else {
                    installCallback.a(false, downloadUrl);
                }
                this.c.remove(downloadUrl);
            }
        }
    }

    public final void onFailed(H5DownloadRequest h5DownloadRequest, int errorCode, String errorMsg) {
        if (h5DownloadRequest != null) {
            String downloadUrl = h5DownloadRequest.getDownloadUrl();
            if (!TextUtils.isEmpty(downloadUrl) && this.c != null) {
                b downloadCallback = this.c.get(downloadUrl);
                H5Log.d("subpackage.DownloadProvidersubpackage download onFailed: " + h5DownloadRequest.getDownloadUrl() + ", errorCode: " + errorCode + ", errorMsg: " + errorMsg);
                final String downloadFilePath = c(h5DownloadRequest.getDownloadUrl());
                if (!TextUtils.isEmpty(downloadFilePath)) {
                    H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                        public final void run() {
                            if (H5FileUtil.exists(downloadFilePath)) {
                                H5Log.d("subpackage.DownloadProvider", "onFailed delete : " + downloadFilePath);
                                H5FileUtil.delete(downloadFilePath);
                            }
                        }
                    });
                }
                if (downloadCallback != null) {
                    downloadCallback.onFailed(h5DownloadRequest, errorCode, errorMsg);
                    this.c.remove(downloadUrl);
                }
            }
        }
    }

    private void a(String downloadUrl, H5DownloadCallback callback) {
        H5Log.d("subpackage.DownloadProvider", "addDownload subpackage url:" + downloadUrl);
        if (downloadUrl.startsWith("http")) {
            H5DownloadRequest downloadRequest = a(downloadUrl);
            if (downloadRequest != null && this.a != null) {
                try {
                    this.a.addDownload(downloadRequest, callback);
                } catch (Throwable throwable) {
                    H5Log.e((String) "subpackage.DownloadProvider", throwable);
                    H5LogData logData = H5LogData.seedId("TINY_APP_SUBPACKAGE_DOWNLOAD_EXCEPTION");
                    logData.param1().add(TinyAppSubPackagePlugin.DOWNLOAD_URL, downloadUrl);
                    H5LogUtil.logNebulaTech(logData);
                }
            }
        }
    }

    private boolean b(String appId, String downloadUrl) {
        if (!e(downloadUrl)) {
            return e(appId, downloadUrl);
        }
        boolean installSuccess = a(appId, downloadUrl, c(downloadUrl));
        if (installSuccess) {
            return installSuccess;
        }
        H5LogData logData = H5LogData.seedId("TINY_APP_SUBPACKAGE_INSTALL_FAILED");
        logData.param1().add(TinyAppSubPackagePlugin.DOWNLOAD_URL, downloadUrl).add("DownloadedFilePath", c(downloadUrl));
        H5LogUtil.logNebulaTech(logData);
        return installSuccess;
    }

    private static boolean a(String appId, String downloadUrl, String downloadFilePath) {
        if (TextUtils.isEmpty(downloadFilePath)) {
            H5Log.e((String) "subpackage.DownloadProvider", (String) "installSubPackage failed, download file path is null");
            return false;
        }
        try {
            File file = new File(downloadFilePath);
            if (!file.exists()) {
                return false;
            }
            if (e(appId, downloadUrl)) {
                return true;
            }
            b(a(downloadUrl, appId));
            if (!H5ZipUtil.unZip(downloadFilePath, a(downloadUrl, appId)) || !e(appId, downloadUrl)) {
                try {
                    H5Log.e((String) "subpackage.DownloadProvider", (String) "subpackage unzip fail");
                    H5FileUtil.delete(file);
                    H5Log.e((String) "subpackage.DownloadProvider", (String) "installSubPackage unZipResult || isInstalled() return false");
                    return false;
                } catch (Exception e) {
                    H5Log.e("subpackage.DownloadProvider", "installSubPackage delete exception", e);
                    return false;
                }
            } else {
                H5Log.d("subpackage.DownloadProvider", "installSubPackage success!");
                H5FileUtil.delete(file);
                return true;
            }
        } catch (Exception globalException) {
            H5Log.e("subpackage.DownloadProvider", "subpackage parse error: ", globalException);
            return false;
        }
    }

    private static void b(String filPath) {
        if (H5FileUtil.exists(filPath)) {
            H5Log.d("subpackage.DownloadProvider", "deleteOldPkgByFullInstall " + filPath);
            H5FileUtil.delete(filPath);
        }
    }

    private static String c(String appId, String url) {
        if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(url)) {
            return "";
        }
        return appId + "-" + H5SecurityUtil.getMD5(url);
    }

    private String c(String downloadUrl) {
        String downloadDirectoryPath = H5DownloadRequest.getDefaultDownloadDir(H5Utils.getContext());
        if (TextUtils.isEmpty(downloadDirectoryPath) || "/".equals(downloadDirectoryPath) || TextUtils.isEmpty(this.b) || TextUtils.isEmpty(downloadUrl)) {
            return "";
        }
        String downloadedFilePath = downloadDirectoryPath + "/" + c(this.b, downloadUrl);
        H5Log.d("subpackage.DownloadProvider", "downloadedFilePath:" + downloadedFilePath);
        return downloadedFilePath;
    }

    public static String a(String downloadUrl, String appId) {
        return d("/nebulaInstallApps/") + d(downloadUrl, appId);
    }

    private static String d(String downloadUrl, String appId) {
        if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(downloadUrl)) {
            return "";
        }
        return appId + "/" + H5SecurityUtil.getMD5(downloadUrl) + "/";
    }

    private static String d(String file) {
        try {
            return H5Utils.getContext().getFilesDir().getAbsolutePath() + file;
        } catch (Throwable throwable) {
            H5Log.e((String) "subpackage.DownloadProvider", throwable);
            return "";
        }
    }

    private boolean e(String downloadUrl) {
        String downloadPath = c(downloadUrl);
        boolean isAvailable = false;
        if (H5FileUtil.exists(downloadPath)) {
            isAvailable = true;
        }
        H5Log.d("subpackage.DownloadProvider", "isPkgAvailable: path:" + downloadPath + " isExist:" + isAvailable);
        return isAvailable;
    }

    private static boolean e(String appId, String downloadUrl) {
        String installedPath = a(downloadUrl, appId);
        if (TextUtils.isEmpty(installedPath)) {
            H5Log.e((String) "subpackage.DownloadProvider", (String) "getInstalledPath() is empty");
            return false;
        }
        try {
            File file = new File(installedPath);
            if (!file.exists()) {
                return false;
            }
            File[] listFiles = file.listFiles();
            int length = listFiles.length;
            H5Log.d("subpackage.DownloadProvider", "isInstalled length:" + length);
            if (listFiles == null || length <= 0) {
                return false;
            }
            boolean containTar = false;
            for (File name : listFiles) {
                String name2 = name.getName();
                H5Log.d("subpackage.DownloadProvider", "installed dir file " + name2);
                if (name2.contains("tar")) {
                    containTar = true;
                }
            }
            if (length < 4 && !containTar) {
                H5Log.e((String) "subpackage.DownloadProvider", (String) "installSubPackage files broken, delete broken files");
                b(installedPath);
                return false;
            } else if (containTar) {
                return true;
            } else {
                H5Log.e((String) "subpackage.DownloadProvider", (String) "installSubPackage files broken, delete broken files");
                b(installedPath);
                return false;
            }
        } catch (Exception e) {
            H5Log.e((String) "subpackage.DownloadProvider", (Throwable) e);
            return false;
        }
    }
}
