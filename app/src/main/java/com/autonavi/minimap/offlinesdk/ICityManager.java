package com.autonavi.minimap.offlinesdk;

import com.autonavi.minimap.offlinesdk.model.CityIdsInfo;
import com.autonavi.minimap.offlinesdk.model.CityInfo;
import com.autonavi.minimap.offlinesdk.model.ProvinceInfo;

public interface ICityManager {
    void backupConfig();

    void checkUpdateStatus();

    void deleteCityListFile();

    CityInfo[] getAllCities();

    ProvinceInfo[] getAllProvinces();

    CityInfo getCityByAdcode(int i);

    CityInfo getCityById(int i);

    CityInfo getCityByPinyin(String str);

    String getCityListVersion();

    ProvinceInfo getProvinceByAdcode(int i);

    void registerCityListUpgradeObserver(ICityListUpgradeObserver iCityListUpgradeObserver);

    CityIdsInfo searchByChineseKey(String str);

    CityIdsInfo searchByEnglishKey(String str);
}
