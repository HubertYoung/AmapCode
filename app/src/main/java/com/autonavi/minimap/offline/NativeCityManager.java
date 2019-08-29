package com.autonavi.minimap.offline;

import com.autonavi.minimap.offlinesdk.ICityListUpgradeObserver;
import com.autonavi.minimap.offlinesdk.ICityManager;
import com.autonavi.minimap.offlinesdk.model.CityIdsInfo;
import com.autonavi.minimap.offlinesdk.model.CityInfo;
import com.autonavi.minimap.offlinesdk.model.ProvinceInfo;

public class NativeCityManager implements ICityManager {
    private ICityManager mCityManager;

    NativeCityManager(ICityManager iCityManager) {
        this.mCityManager = iCityManager;
    }

    public ProvinceInfo[] getAllProvinces() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            return this.mCityManager.getAllProvinces();
        }
        return new ProvinceInfo[0];
    }

    public CityInfo[] getAllCities() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            return this.mCityManager.getAllCities();
        }
        return new CityInfo[0];
    }

    public String getCityListVersion() {
        return OfflineNativeSdk.getInstance().isInit() ? this.mCityManager.getCityListVersion() : "";
    }

    public CityInfo getCityById(int i) {
        if (!OfflineNativeSdk.getInstance().isInit()) {
            return new CityInfo();
        }
        if (this.mCityManager.getCityById(i) != null) {
            return this.mCityManager.getCityById(i);
        }
        return this.mCityManager.getCityByAdcode(i);
    }

    public CityInfo getCityByAdcode(int i) {
        if (!OfflineNativeSdk.getInstance().isInit()) {
            return new CityInfo();
        }
        if (this.mCityManager.getCityByAdcode(i) != null) {
            return this.mCityManager.getCityByAdcode(i);
        }
        return this.mCityManager.getCityById(i);
    }

    public CityInfo getCityByPinyin(String str) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            return this.mCityManager.getCityByPinyin(str);
        }
        return new CityInfo();
    }

    public ProvinceInfo getProvinceByAdcode(int i) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            return this.mCityManager.getProvinceByAdcode(i);
        }
        return new ProvinceInfo();
    }

    public void registerCityListUpgradeObserver(ICityListUpgradeObserver iCityListUpgradeObserver) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mCityManager.registerCityListUpgradeObserver(iCityListUpgradeObserver);
        }
    }

    public CityIdsInfo searchByChineseKey(String str) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            return this.mCityManager.searchByChineseKey(str);
        }
        return new CityIdsInfo();
    }

    public CityIdsInfo searchByEnglishKey(String str) {
        if (OfflineNativeSdk.getInstance().isInit()) {
            return this.mCityManager.searchByEnglishKey(str);
        }
        return new CityIdsInfo();
    }

    public void checkUpdateStatus() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mCityManager.checkUpdateStatus();
        }
    }

    public void deleteCityListFile() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mCityManager.deleteCityListFile();
        }
    }

    public void backupConfig() {
        if (OfflineNativeSdk.getInstance().isInit()) {
            this.mCityManager.backupConfig();
        }
    }
}
