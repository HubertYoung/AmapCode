package com.autonavi.minimap.offline.utils;

import android.support.annotation.Nullable;
import com.autonavi.minimap.offline.OfflineNativeSdk;
import com.autonavi.minimap.offlinesdk.ICityManager;
import com.autonavi.minimap.offlinesdk.IDownloadManager;
import com.autonavi.minimap.offlinesdk.model.CityInfo;
import com.autonavi.minimap.offlinesdk.model.DownloadListType;

public class CityHelper {
    public static boolean isMunicipalitiesCity(long j) {
        return j == 110000 || j == 120000 || j == 310000 || j == 500000;
    }

    public static boolean isSpecialCity(long j) {
        return j == 810000 || j == 820000;
    }

    public static boolean isProvince(long j) {
        return j % 10000 == 0 || isSpecialCity(j) || isMunicipalitiesCity(j);
    }

    @Nullable
    public static CityInfo getCurrentMapViewCity() {
        int mapViewAdcode = OfflineUtil.getMapViewAdcode();
        ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
        if (cityManager != null) {
            return cityManager.getCityByAdcode(mapViewAdcode);
        }
        return null;
    }

    public static boolean hasCityDownloaded() {
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        if (downloadManager == null) {
            return false;
        }
        int[] downloadCityList = downloadManager.getDownloadCityList(DownloadListType.DOWNLOAD_LIST_TYPE_DOWNLOADED);
        if (downloadCityList == null || downloadCityList.length <= 0) {
            return false;
        }
        return true;
    }
}
