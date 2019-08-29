package com.alipay.mobile.nebula.provider;

import android.os.Bundle;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadCallback;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.model.AppReq;
import com.alipay.mobile.nebula.callback.H5AppInstallCallback;
import java.util.Map;

public interface H5AppProvider {
    void clearResourceAppCache();

    void downloadApp(String str, String str2);

    void downloadApp(String str, String str2, H5DownloadCallback h5DownloadCallback);

    void downloadApp(String str, String str2, H5DownloadCallback h5DownloadCallback, String str3);

    boolean enableMultiProcess(String str, Bundle bundle);

    String getAppDesc(String str);

    void getAppFromServerWhenAppIsEmpty(String str);

    AppInfo getAppInfo(String str);

    AppInfo getAppInfo(String str, String str2);

    AppInfo getAppInfo(String str, String str2, String str3);

    String getAppName(String str);

    String getAppName(String str, String str2);

    String getConfigExtra(String str);

    String getDownloadLocalPath(String str, String str2);

    Map<String, String> getExtra(String str, String str2);

    String getExtraJo(String str, String str2);

    String getH5AppCdnBaseUrl(String str, String str2);

    String getIconUrl(String str);

    String getIconUrl(String str, String str2);

    String getInstallPath(String str, String str2);

    int getLocalReport(String str, String str2);

    String getPackageNick(String str);

    String getPackageNick(String str, String str2);

    long getPackageSize(String str, String str2);

    String getScene(String str, String str2);

    String getSlogan(String str, String str2);

    String getSubUrl(String str, String str2);

    String getThirdPlatform(String str, String str2);

    String getVersion(String str);

    String getVhost(String str, String str2);

    String getWalletAppDesc(String str);

    String getWalletAppName(String str);

    String getWalletConfigNebulaVersion(String str);

    String getWalletIconUrl(String str);

    String getWalletVersion(String str);

    JSONObject getlaunchParams(String str);

    boolean hasPackage(String str, String str2);

    void installApp(String str, String str2);

    void installApp(String str, String str2, H5AppInstallCallback h5AppInstallCallback);

    void installApp(String str, String str2, boolean z);

    boolean isAutoInstall(String str, String str2);

    boolean isAvailable(String str, String str2);

    boolean isH5App(String str);

    boolean isH5TinyApp(String str);

    boolean isInstalled(String str, String str2);

    boolean isNebulaApp(String str);

    boolean isOffline(String str);

    boolean isRNApp(String str);

    boolean isResourceApp(String str);

    boolean isSmallProgramFromOpenPlat(String str);

    boolean isUseAppX(String str);

    boolean isXiaoChengXu(String str);

    AppReq makeAppReq(H5UpdateAppParam h5UpdateAppParam);

    void offlineFromOpenPlat(String str);

    Map<String, String> queryAllH5AppVersionFromAppCenter();

    void showOfflinePage(String str, Bundle bundle);

    Map<String, String> syncAppManage();

    void updateApp(H5UpdateAppParam h5UpdateAppParam);
}
