package com.autonavi.minimap.offline;

import android.support.annotation.NonNull;
import com.autonavi.minimap.offlinesdk.ICityDownloadObserver;
import com.autonavi.minimap.offlinesdk.IDownloadManager;
import com.autonavi.minimap.offlinesdk.IOfflineDataErrorObserver;
import com.autonavi.minimap.offlinesdk.IOfflineSwitchDirPathObserver;
import com.autonavi.minimap.offlinesdk.model.DownloadInfo;
import com.autonavi.minimap.offlinesdk.model.DownloadListType;
import com.autonavi.minimap.offlinesdk.model.PackageStates;
import java.util.List;

public class NativeDownloadManager implements IDownloadManager {
    private IDownloadManager mDownloadManager;

    NativeDownloadManager(@NonNull IDownloadManager iDownloadManager) {
        this.mDownloadManager = iDownloadManager;
    }

    public int[] getAllDownloadCityList() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            return this.mDownloadManager.getAllDownloadCityList();
        }
        return new int[0];
    }

    public int[] getDownloadCityList(DownloadListType downloadListType) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            return this.mDownloadManager.getDownloadCityList(downloadListType);
        }
        return new int[0];
    }

    public PackageStates[] getCityDataStatus(int i) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            return this.mDownloadManager.getCityDataStatus(i);
        }
        return new PackageStates[0];
    }

    public boolean isAnyTaskUnzipping() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            return this.mDownloadManager.isAnyTaskUnzipping();
        }
        return false;
    }

    public boolean isAnyMapDownloadedExcludeNational() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            return this.mDownloadManager.isAnyMapDownloadedExcludeNational();
        }
        return false;
    }

    public void deleteOfflineDatas(int[] iArr) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.deleteOfflineDatas(iArr);
        }
    }

    public void deleteOfflineDataByCityId(int i) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.deleteOfflineDataByCityId(i);
        }
    }

    public void startDownload(DownloadInfo downloadInfo) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.startDownload(downloadInfo);
        }
    }

    public void startDownloadList(List<DownloadInfo> list) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.startDownloadList(list);
        }
    }

    public void startDownloadAndCheckNetwork(DownloadInfo downloadInfo) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.startDownloadAndCheckNetwork(downloadInfo);
        }
    }

    public void startDownloadAndCheckNetworkList(List<DownloadInfo> list) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.startDownloadAndCheckNetworkList(list);
        }
    }

    public void pauseDownloadByCityId(int i) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.pauseDownloadByCityId(i);
        }
    }

    public void cancelDownloadByCityId(int i) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.cancelDownloadByCityId(i);
        }
    }

    public void resumeDownloadByCityId(int i) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.resumeDownloadByCityId(i);
        }
    }

    public void updateDownload(DownloadInfo downloadInfo) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.updateDownload(downloadInfo);
        }
    }

    public void pauseDownloadByCityIdArray(int[] iArr) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.pauseDownloadByCityIdArray(iArr);
        }
    }

    public void cancelDownloadByCityIdArray(int[] iArr) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.cancelDownloadByCityIdArray(iArr);
        }
    }

    public void resumeDownloadByCityIdArray(int[] iArr) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.resumeDownloadByCityIdArray(iArr);
        }
    }

    public void startDownloadAll() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.startDownloadAll();
        }
    }

    public void startUpdateAll() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.startUpdateAll();
        }
    }

    public void pauseDownloadAll() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.pauseDownloadAll();
        }
    }

    public void bindObserverForAllCities(ICityDownloadObserver iCityDownloadObserver) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.bindObserverForAllCities(iCityDownloadObserver);
        }
    }

    public String getAlinkSyncDataStr() {
        return OfflineNativeSdk.getInstance().isInit() ? this.mDownloadManager.getAlinkSyncDataStr() : "";
    }

    public void switchDir2Target(String str, IOfflineSwitchDirPathObserver iOfflineSwitchDirPathObserver) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.switchDir2Target(str, iOfflineSwitchDirPathObserver);
        }
    }

    public int switchDirCheck(String str) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            return this.mDownloadManager.switchDirCheck(str);
        }
        return -1;
    }

    public void cancelSwitchDir() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.cancelSwitchDir();
        }
    }

    public void backupConfig() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.backupConfig();
        }
    }

    public boolean havingUpdateCity() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            return this.mDownloadManager.havingUpdateCity();
        }
        return false;
    }

    public boolean havingDownloadingCity() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            return this.mDownloadManager.havingDownloadingCity();
        }
        return false;
    }

    public boolean havingPauseCity() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            return this.mDownloadManager.havingPauseCity();
        }
        return false;
    }

    public void pauseDownloadAllAuto() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.pauseDownloadAllAuto();
        }
    }

    public void resumeDownloadForWifi() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.resumeDownloadForWifi();
        }
    }

    public void bindOfflineDataErrorObserver(IOfflineDataErrorObserver iOfflineDataErrorObserver) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mDownloadManager.bindOfflineDataErrorObserver(iOfflineDataErrorObserver);
        }
    }

    public String getOfflineLogFile() {
        return OfflineNativeSdk.getInstance().isInit() ? this.mDownloadManager.getOfflineLogFile() : "";
    }

    public String getDataVersion(int i, int i2) {
        return OfflineNativeSdk.getInstance().isInit() ? this.mDownloadManager.getDataVersion(i, i2) : "";
    }

    public boolean having3dCross() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            return this.mDownloadManager.having3dCross();
        }
        return false;
    }

    public boolean havingCross() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            return this.mDownloadManager.havingCross();
        }
        return false;
    }

    public boolean havingMap() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            return this.mDownloadManager.havingMap();
        }
        return false;
    }

    public boolean isUpdateBeforeCompileVersion() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            return this.mDownloadManager.isUpdateBeforeCompileVersion();
        }
        return false;
    }

    public String getOfflineDataBaseFile() {
        return this.mDownloadManager.getOfflineDataBaseFile();
    }
}
