package com.autonavi.minimap.offline.map.inter;

import android.content.Context;
import android.content.Intent;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.NodeDialogFragmentOnClickListener;
import com.autonavi.minimap.offline.inter.inner.ICheckOfflineResponse;
import java.util.ArrayList;
import java.util.List;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepImplementations
@KeepName
public interface IOfflineManager extends bie {
    public static final String PAGE_DOWNLOADED = "showDownloaded";
    public static final String SHOW_ENLARGEMENT_DOWNLOAD = "showEnlargementDownload";
    public static final String SHOW_MAP_DOWNLOAD = "showMapDownload";
    public static final String SHOW_MAP_POI_ROUTE_DOWNLOAD = "showPoiRouteDownload";
    public static final String SHOW_SPOT_GUIDE_DOWNLOAD = "showSpotDownload";

    public interface AutoApkUpdateCallback {
        void onUpdate(String str);
    }

    String adcodeToPinyin(int i);

    void checkAutoApkUpdate(AutoApkUpdateCallback autoApkUpdateCallback);

    boolean checkCityDownload(int i);

    boolean checkCityDownloadMapStatus(int i);

    boolean checkCurrentCityDownloadMapStatus();

    boolean checkIsUpdate();

    boolean checkOfflineCity(int i);

    void checkOfflineNavi(bid bid, ICheckOfflineResponse iCheckOfflineResponse);

    void checkOfflineNavi(bid bid, ICheckOfflineResponse iCheckOfflineResponse, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener2);

    void deal(bid bid, Intent intent);

    void destroy();

    void enterAlongWayDownload(ArrayList<lj> arrayList);

    void enterAlongWayDownload(int[] iArr);

    void enterOffFrmDownloadMap();

    String get3dCrossDataVersionForCurCity();

    String get3dCrossVerSion(long j);

    int[] getCityDownloadedVerByAdcode(int i);

    String getCrossDataVersionForCurCity();

    List<String> getDownloadCityUpgradeList();

    String getMapDataVersionForCurCity();

    String getOfflineDBFilePath();

    String getOfflineDBPath();

    String getOfflineDateUpdateVer();

    String getOfflineEngineVersion();

    String getOfflineLogFilePath();

    String getPoiDataVersionForCurCity();

    String getRouteDataVersionForCurCity();

    String getRouteEnlargeUpdateVer();

    int getVersionByMap();

    boolean has3dCross();

    boolean hasCross();

    boolean hasOfflineData();

    void initOfflinePath();

    void initialize();

    boolean isDBException();

    boolean isHaveDownloadingCity();

    boolean isOfflineDataReady();

    boolean isOfflineDataUnzipping();

    boolean isOfflineDataUpdate();

    boolean isRoadEnlargeUpdate();

    boolean isShowOfflineAE8UpdateTip();

    void notifyCityDataError(String str);

    void pauseAll();

    void pauseAllByNavi();

    void putOffLatestVerByAppInit(Context context, String str, String str2, String str3, String str4, String str5);

    void recoveryDownload();

    void requestGpu3dSupport(bid bid);

    void resumeWifi();

    void saveErrors(Throwable th, String str);

    void setAutoDownloadInWifiSwitchState(boolean z);

    void setIsDBException(boolean z);

    void setMapOnlineVersion(String str);

    void setNeedUpgradeSp();

    void startSwithcStorage(bid bid, int i);
}
