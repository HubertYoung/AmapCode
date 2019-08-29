package com.autonavi.minimap.offlinesdk;

public interface ICityListUpgradeObserver {
    void onCityDataUpdateFinish(int i);

    void onCityDataUpdated(int[] iArr, int[] iArr2);

    void onCityListUpdated();
}
