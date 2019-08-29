package com.alipay.mobile.nebula.appcenter;

import com.alipay.mobile.nebula.appcenter.download.H5DownloadCallback;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.callback.H5AppInstallCallback;

public interface H5BaseApp {
    void downloadApp();

    void downloadApp(H5DownloadCallback h5DownloadCallback);

    void downloadApp(H5DownloadCallback h5DownloadCallback, String str);

    String getAppVersion();

    String getDownloadLocalPath();

    String getDownloadUrl();

    String getInstalledPath();

    boolean installApp();

    boolean installApp(H5AppInstallCallback h5AppInstallCallback);

    boolean installApp(boolean z);

    boolean isAvailable();

    boolean isDownloading();

    boolean isInstalled();

    void setAppInfo(AppInfo appInfo);
}
