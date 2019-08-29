package com.alipay.mobile.nebula.appcenter;

import com.alipay.mobile.nebula.appcenter.db.H5GetAppInfoListen;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import java.util.List;
import java.util.Map;

public interface H5AppDBService {

    public interface ClearTableCallback {
        void getCleared();
    }

    @Deprecated
    void cleanAppLimit(String str, String str2);

    @Deprecated
    void cleanFailedRequestAppList(Map<String, String> map);

    void clearAllTable(ClearTableCallback clearTableCallback);

    void clearPresetMemory();

    void clearUpdateTime(String str);

    void createOrUpdateAppPoolLimit(int i);

    void createOrUpdateLimitReqRate(double d);

    void createOrUpdateNormalReqRate(double d);

    void deleteAppInfo(String str, String str2);

    void deleteAppInstall(String str);

    String findInstallAppVersion(String str);

    Map<String, List<AppInfo>> getAllApp();

    Map<String, AppInfo> getAllHighestAppInfo();

    Map<String, String> getAllHighestAppVersion();

    Map<String, String> getAllHighestLocalReportAppVersion();

    AppInfo getAppInfo(String str, String str2);

    void getAppInfoAsync(String str, String str2, H5GetAppInfoListen h5GetAppInfoListen);

    List<AppInfo> getAppInfoList(String str, boolean z);

    String getConfigExtra();

    @Deprecated
    Map<String, String> getFailedRequestAppList();

    String getHighestAppVersion(String str);

    Map<String, String> getInstalledApp();

    String getLastAllUpdateTime();

    double getLimitReqRate();

    String getMatchHighestAppVersion(String str, String str2);

    double getNormalReqRate();

    @Deprecated
    int getStrictReqRate();

    String getUpdateAppTime(String str, String str2);

    @Deprecated
    boolean isLimitApp(String str, String str2);

    void markNoDeleteAppVersion(String str, String str2);

    void onSwitchAccount();

    boolean rpcIsLimit();

    void saveAppInfo(AppInfo appInfo, boolean z);

    @Deprecated
    void setFailedRequestAppList(Map<String, String> map);

    void setRpcIsLimit(boolean z);

    void unMarkNoDeleteAppVersion(String str, String str2);

    @Deprecated
    void updateAppLimit(String str, String str2);

    void updateCurrentAppUpdateTime(String str, String str2);

    void updateUnavailableReason(String str, String str2, String str3);

    void updateUpdateTime(String str, String str2);
}
