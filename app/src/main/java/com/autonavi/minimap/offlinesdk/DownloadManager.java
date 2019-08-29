package com.autonavi.minimap.offlinesdk;

import com.autonavi.minimap.offlinesdk.model.DownloadInfo;
import com.autonavi.minimap.offlinesdk.model.DownloadListType;
import com.autonavi.minimap.offlinesdk.model.PackageStates;
import com.autonavi.minimap.offlinesdk.model.PackageType;
import java.util.List;

public class DownloadManager implements IDownloadManager {
    long mPtr;

    private native void nativeBackupConfig(long j);

    private native void nativeBindObserverForAllCities(ICityDownloadObserver iCityDownloadObserver, long j);

    private native void nativeBindOfflineDataErrorObserver(IOfflineDataErrorObserver iOfflineDataErrorObserver, long j);

    private native void nativeCancelDownloadByCityId(int i, long j);

    private native void nativeCancelDownloadByCityIdArray(int[] iArr, long j);

    private native void nativeCancelSwitchDir(long j);

    private native void nativeDeleteOfflineData(int i, int i2, long j);

    private native void nativeDeleteOfflineDataByCityId(int i, long j);

    private native void nativeDeleteOfflineDatas(int[] iArr, long j);

    private native String nativeGetAlinkSyncDataStr(long j);

    private native int[] nativeGetAllDownloadCityList(long j);

    private native PackageStates[] nativeGetCityDataStatus(int i, long j);

    private native String nativeGetDataVersion(int i, int i2, long j);

    private native int[] nativeGetDownloadCityList(int i, long j);

    private native String nativeGetOfflineDataBaseFile(long j);

    private native String nativeGetOfflineLogFile(long j);

    private native boolean nativeHaving3dCross(long j);

    private native boolean nativeHavingCross(long j);

    private native boolean nativeHavingDownloadingCity(long j);

    private native boolean nativeHavingMap(long j);

    private native boolean nativeHavingPauseCity(long j);

    private native boolean nativeHavingUpdateCity(long j);

    private native boolean nativeIsAnyMapDownloadedExcludeNational(long j);

    private native boolean nativeIsAnyTaskUnzipping(long j);

    private native boolean nativeIsUpdateBeforeCompileVersion(long j);

    private native void nativePauseDownloadAll(long j);

    private native void nativePauseDownloadAllAuto(long j);

    private native void nativePauseDownloadByCityId(int i, long j);

    private native void nativePauseDownloadByCityIdArray(int[] iArr, long j);

    private native void nativeResumeDownloadByCityId(int i, long j);

    private native void nativeResumeDownloadByCityIdArray(int[] iArr, long j);

    private native void nativeResumeDownloadForWifi(long j);

    private native void nativeStartDownload(int i, int i2, long j);

    private native void nativeStartDownloadAll(long j);

    private native void nativeStartDownloadAndCheckNetwork(int i, int i2, long j);

    private native void nativeStartDownloadAndCheckNetworkList(List<DownloadInfo> list, long j);

    private native void nativeStartDownloadList(List<DownloadInfo> list, long j);

    private native void nativeSwitchDir2Target(String str, IOfflineSwitchDirPathObserver iOfflineSwitchDirPathObserver, long j);

    private native int nativeSwitchDirCheck(String str, long j);

    private native void nativeUpdateDownload(int i, int i2, long j);

    private native void nativestartUpdateAll(long j);

    protected DownloadManager(long j) {
        this.mPtr = j;
    }

    public int[] getAllDownloadCityList() {
        return nativeGetAllDownloadCityList(this.mPtr);
    }

    public int[] getDownloadCityList(DownloadListType downloadListType) {
        return nativeGetDownloadCityList(downloadListType.ordinal(), this.mPtr);
    }

    public PackageStates[] getCityDataStatus(int i) {
        return nativeGetCityDataStatus(i, this.mPtr);
    }

    public boolean isAnyTaskUnzipping() {
        return nativeIsAnyTaskUnzipping(this.mPtr);
    }

    public boolean isAnyMapDownloadedExcludeNational() {
        return nativeIsAnyMapDownloadedExcludeNational(this.mPtr);
    }

    public void deleteOfflineData(int i, PackageType packageType) {
        nativeDeleteOfflineData(i, packageType.ordinal(), this.mPtr);
    }

    public void deleteOfflineDatas(int[] iArr) {
        nativeDeleteOfflineDatas(iArr, this.mPtr);
    }

    public void deleteOfflineDataByCityId(int i) {
        nativeDeleteOfflineDataByCityId(i, this.mPtr);
    }

    public void startDownload(DownloadInfo downloadInfo) {
        nativeStartDownload(downloadInfo.cityid, downloadInfo.types, this.mPtr);
    }

    public void startDownloadList(List<DownloadInfo> list) {
        if (list != null && list.size() > 0) {
            nativeStartDownloadList(list, this.mPtr);
        }
    }

    public void startDownloadAndCheckNetwork(DownloadInfo downloadInfo) {
        nativeStartDownloadAndCheckNetwork(downloadInfo.cityid, downloadInfo.types, this.mPtr);
    }

    public void startDownloadAndCheckNetworkList(List<DownloadInfo> list) {
        if (list != null && list.size() > 0) {
            nativeStartDownloadAndCheckNetworkList(list, this.mPtr);
        }
    }

    public void pauseDownloadByCityId(int i) {
        nativePauseDownloadByCityId(i, this.mPtr);
    }

    public void cancelDownloadByCityId(int i) {
        nativeCancelDownloadByCityId(i, this.mPtr);
    }

    public void resumeDownloadByCityId(int i) {
        nativeResumeDownloadByCityId(i, this.mPtr);
    }

    public void updateDownload(DownloadInfo downloadInfo) {
        nativeUpdateDownload(downloadInfo.cityid, downloadInfo.types, this.mPtr);
    }

    public void pauseDownloadByCityIdArray(int[] iArr) {
        nativePauseDownloadByCityIdArray(iArr, this.mPtr);
    }

    public void cancelDownloadByCityIdArray(int[] iArr) {
        nativeCancelDownloadByCityIdArray(iArr, this.mPtr);
    }

    public void resumeDownloadByCityIdArray(int[] iArr) {
        nativeResumeDownloadByCityIdArray(iArr, this.mPtr);
    }

    public void startDownloadAll() {
        nativeStartDownloadAll(this.mPtr);
    }

    public void startUpdateAll() {
        nativestartUpdateAll(this.mPtr);
    }

    public void resumeDownloadForWifi() {
        nativeResumeDownloadForWifi(this.mPtr);
    }

    public void pauseDownloadAll() {
        nativePauseDownloadAll(this.mPtr);
    }

    public void pauseDownloadAllAuto() {
        nativePauseDownloadAllAuto(this.mPtr);
    }

    public void bindObserverForAllCities(ICityDownloadObserver iCityDownloadObserver) {
        nativeBindObserverForAllCities(iCityDownloadObserver, this.mPtr);
    }

    public String getAlinkSyncDataStr() {
        return nativeGetAlinkSyncDataStr(this.mPtr);
    }

    public void switchDir2Target(String str, IOfflineSwitchDirPathObserver iOfflineSwitchDirPathObserver) {
        nativeSwitchDir2Target(str, iOfflineSwitchDirPathObserver, this.mPtr);
    }

    public int switchDirCheck(String str) {
        return nativeSwitchDirCheck(str, this.mPtr);
    }

    public void cancelSwitchDir() {
        nativeCancelSwitchDir(this.mPtr);
    }

    public void backupConfig() {
        nativeBackupConfig(this.mPtr);
    }

    public boolean havingUpdateCity() {
        return nativeHavingUpdateCity(this.mPtr);
    }

    public boolean havingDownloadingCity() {
        return nativeHavingDownloadingCity(this.mPtr);
    }

    public boolean havingPauseCity() {
        return nativeHavingPauseCity(this.mPtr);
    }

    public void bindOfflineDataErrorObserver(IOfflineDataErrorObserver iOfflineDataErrorObserver) {
        nativeBindOfflineDataErrorObserver(iOfflineDataErrorObserver, this.mPtr);
    }

    public String getDataVersion(int i, int i2) {
        return nativeGetDataVersion(i, i2, this.mPtr);
    }

    public boolean having3dCross() {
        return nativeHaving3dCross(this.mPtr);
    }

    public boolean havingCross() {
        return nativeHavingCross(this.mPtr);
    }

    public boolean havingMap() {
        return nativeHavingMap(this.mPtr);
    }

    public boolean isUpdateBeforeCompileVersion() {
        return nativeIsUpdateBeforeCompileVersion(this.mPtr);
    }

    public String getOfflineLogFile() {
        return nativeGetOfflineLogFile(this.mPtr);
    }

    public String getOfflineDataBaseFile() {
        return nativeGetOfflineDataBaseFile(this.mPtr);
    }
}
