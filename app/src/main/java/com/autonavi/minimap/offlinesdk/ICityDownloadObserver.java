package com.autonavi.minimap.offlinesdk;

import com.autonavi.minimap.offlinesdk.model.CityListNotifyInfo;

public interface ICityDownloadObserver {
    void onAllProgress(int i, long j, long j2, long j3, long j4, long j5, long j6);

    void onAllStatusChange(int i, int i2, int i3, int i4);

    void onCityListStatusChange(CityListNotifyInfo[] cityListNotifyInfoArr, CityListNotifyInfo[] cityListNotifyInfoArr2);

    void onCityMergeProgress(int i, int i2);

    void onCityProgress(int i, long j, long j2);

    void onCityStatusChange(int i, int i2);

    void onError(int i, int i2, int i3);

    int onFinish(int i, int i2);

    void onProgress(int i, int i2, long j, long j2);

    void onStart(int i);

    void onStatusChange(int i, int i2, int i3);
}
