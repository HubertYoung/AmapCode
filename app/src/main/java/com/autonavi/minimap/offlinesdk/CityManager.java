package com.autonavi.minimap.offlinesdk;

import com.autonavi.minimap.offlinesdk.model.CityIdsInfo;
import com.autonavi.minimap.offlinesdk.model.CityInfo;
import com.autonavi.minimap.offlinesdk.model.ProvinceInfo;

public class CityManager implements ICityManager {
    long mPtr;

    private native void nativeBackupConfig(long j);

    private native void nativeCheckUpdateStatus(long j);

    private native void nativeDeleteCityListFile(long j);

    private native CityInfo[] nativeGetAllCities(long j);

    private native ProvinceInfo[] nativeGetAllProvinces(long j);

    private native CityInfo nativeGetCityByAdcode(int i, long j);

    private native CityInfo nativeGetCityById(int i, long j);

    private native CityInfo nativeGetCityByPinyin(String str, long j);

    private native String nativeGetCityListVersion(long j);

    private native ProvinceInfo nativeGetProvinceByAdcode(int i, long j);

    private native void nativeRegisterCityListUpgradeObserver(ICityListUpgradeObserver iCityListUpgradeObserver, long j);

    private native CityIdsInfo nativeSearchByChineseKey(String str, long j);

    private native CityIdsInfo nativeSearchByEnglishKey(String str, long j);

    protected CityManager(long j) {
        this.mPtr = j;
    }

    public ProvinceInfo[] getAllProvinces() {
        return nativeGetAllProvinces(this.mPtr);
    }

    public CityInfo[] getAllCities() {
        return nativeGetAllCities(this.mPtr);
    }

    public String getCityListVersion() {
        return nativeGetCityListVersion(this.mPtr);
    }

    public CityInfo getCityById(int i) {
        return nativeGetCityById(i, this.mPtr);
    }

    public CityInfo getCityByAdcode(int i) {
        return nativeGetCityByAdcode(i, this.mPtr);
    }

    public CityInfo getCityByPinyin(String str) {
        return nativeGetCityByPinyin(str, this.mPtr);
    }

    public ProvinceInfo getProvinceByAdcode(int i) {
        return nativeGetProvinceByAdcode(i, this.mPtr);
    }

    public void registerCityListUpgradeObserver(ICityListUpgradeObserver iCityListUpgradeObserver) {
        nativeRegisterCityListUpgradeObserver(iCityListUpgradeObserver, this.mPtr);
    }

    public CityIdsInfo searchByChineseKey(String str) {
        if (str == null) {
            str = "";
        }
        return nativeSearchByChineseKey(str.trim(), this.mPtr);
    }

    public CityIdsInfo searchByEnglishKey(String str) {
        if (str == null) {
            str = "";
        }
        return nativeSearchByEnglishKey(str.trim(), this.mPtr);
    }

    public void checkUpdateStatus() {
        nativeCheckUpdateStatus(this.mPtr);
    }

    public void deleteCityListFile() {
        nativeDeleteCityListFile(this.mPtr);
    }

    public void backupConfig() {
        nativeBackupConfig(this.mPtr);
    }
}
