package com.autonavi.minimap.offlinesdk;

import com.autonavi.minimap.offlinesdk.model.DownloadInfo;
import com.autonavi.minimap.offlinesdk.model.DownloadListType;
import com.autonavi.minimap.offlinesdk.model.PackageStates;
import java.util.List;

public interface IDownloadManager {
    void backupConfig();

    void bindObserverForAllCities(ICityDownloadObserver iCityDownloadObserver);

    void bindOfflineDataErrorObserver(IOfflineDataErrorObserver iOfflineDataErrorObserver);

    void cancelDownloadByCityId(int i);

    void cancelDownloadByCityIdArray(int[] iArr);

    void cancelSwitchDir();

    void deleteOfflineDataByCityId(int i);

    void deleteOfflineDatas(int[] iArr);

    String getAlinkSyncDataStr();

    int[] getAllDownloadCityList();

    PackageStates[] getCityDataStatus(int i);

    String getDataVersion(int i, int i2);

    int[] getDownloadCityList(DownloadListType downloadListType);

    String getOfflineDataBaseFile();

    String getOfflineLogFile();

    boolean having3dCross();

    boolean havingCross();

    boolean havingDownloadingCity();

    boolean havingMap();

    boolean havingPauseCity();

    boolean havingUpdateCity();

    boolean isAnyMapDownloadedExcludeNational();

    boolean isAnyTaskUnzipping();

    boolean isUpdateBeforeCompileVersion();

    void pauseDownloadAll();

    void pauseDownloadAllAuto();

    void pauseDownloadByCityId(int i);

    void pauseDownloadByCityIdArray(int[] iArr);

    void resumeDownloadByCityId(int i);

    void resumeDownloadByCityIdArray(int[] iArr);

    void resumeDownloadForWifi();

    void startDownload(DownloadInfo downloadInfo);

    void startDownloadAll();

    void startDownloadAndCheckNetwork(DownloadInfo downloadInfo);

    void startDownloadAndCheckNetworkList(List<DownloadInfo> list);

    void startDownloadList(List<DownloadInfo> list);

    void startUpdateAll();

    void switchDir2Target(String str, IOfflineSwitchDirPathObserver iOfflineSwitchDirPathObserver);

    int switchDirCheck(String str);

    void updateDownload(DownloadInfo downloadInfo);
}
