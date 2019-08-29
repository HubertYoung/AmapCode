package com.autonavi.minimap.offline.util;

import android.support.annotation.Nullable;
import com.autonavi.minimap.offline.OfflineNativeSdk;
import com.autonavi.minimap.offlinesdk.ICityManager;
import com.autonavi.minimap.offlinesdk.IDownloadManager;
import com.autonavi.minimap.offlinesdk.model.CityInfo;
import com.autonavi.minimap.offlinesdk.model.DownloadListType;
import com.autonavi.minimap.offlinesdk.model.PackageInfo;
import com.autonavi.minimap.offlinesdk.model.PackageStates;
import com.autonavi.minimap.offlinesdk.model.ProvinceInfo;
import java.util.ArrayList;
import java.util.HashMap;

public final class JsNativeFacade {
    private static volatile JsNativeFacade sInstance;

    private JsNativeFacade() {
    }

    public static JsNativeFacade getInstance() {
        if (sInstance == null) {
            synchronized (JsNativeFacade.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new JsNativeFacade();
                    }
                }
            }
        }
        return sInstance;
    }

    public final CityInfo[] getCityInfos(int[] iArr) {
        if (iArr == null || iArr.length == 0) {
            return new CityInfo[0];
        }
        ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
        if (cityManager == null) {
            return new CityInfo[0];
        }
        ArrayList arrayList = new ArrayList();
        for (int cityByAdcode : iArr) {
            CityInfo cityByAdcode2 = cityManager.getCityByAdcode(cityByAdcode);
            if (cityByAdcode2 != null) {
                arrayList.add(cityByAdcode2);
            }
        }
        JsArraysUtils.clearNull(arrayList);
        return (CityInfo[]) arrayList.toArray(new CityInfo[arrayList.size()]);
    }

    @Nullable
    public final ProvinceInfo getProvinceInfoByCityId(int i) {
        ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
        if (cityManager == null) {
            return null;
        }
        CityInfo cityById = cityManager.getCityById(i);
        if (cityById == null) {
            cityById = cityManager.getCityByAdcode(i);
        }
        if (cityById == null) {
            return null;
        }
        ProvinceInfo provinceByAdcode = cityManager.getProvinceByAdcode(cityById.provinceAdcode);
        if (provinceByAdcode == null) {
            return null;
        }
        return provinceByAdcode;
    }

    public final PackageStates[] getPackageStatesByAdcode(int i) {
        ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
        if (cityManager == null) {
            return new PackageStates[0];
        }
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        if (downloadManager == null) {
            return new PackageStates[0];
        }
        CityInfo cityByAdcode = cityManager.getCityByAdcode(i);
        if (cityByAdcode == null) {
            return new PackageStates[0];
        }
        PackageStates[] cityDataStatus = downloadManager.getCityDataStatus(cityByAdcode.cityId);
        if (cityDataStatus != null) {
            return cityDataStatus;
        }
        return new PackageStates[0];
    }

    public final PackageStates[] getPackageStatesByCityid(int i) {
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        if (downloadManager == null) {
            return new PackageStates[0];
        }
        PackageStates[] cityDataStatus = downloadManager.getCityDataStatus(i);
        if (cityDataStatus != null) {
            return cityDataStatus;
        }
        return new PackageStates[0];
    }

    public final long getDataSize(CityInfo[] cityInfoArr) {
        if (cityInfoArr == null || cityInfoArr.length == 0) {
            return 0;
        }
        long j = 0;
        for (CityInfo cityInfo : cityInfoArr) {
            if (cityInfo != null) {
                PackageInfo[] packageInfoArr = cityInfo.items;
                if (packageInfoArr != null) {
                    long j2 = j;
                    for (PackageInfo packageInfo : packageInfoArr) {
                        if (packageInfo != null) {
                            j2 += packageInfo.packageBytes;
                        }
                    }
                    j = j2;
                }
            }
        }
        return j;
    }

    public final long getDataSizeByAdcode(int[] iArr) {
        CityInfo[] cityInfos = getCityInfos(iArr);
        if (cityInfos == null) {
            return 0;
        }
        return getDataSize(cityInfos);
    }

    public final int[] getDownloadingCityIds() {
        int i;
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        if (downloadManager == null) {
            return new int[0];
        }
        int[] downloadCityList = downloadManager.getDownloadCityList(DownloadListType.DOWNLOAD_LIST_TYPE_DOWNLOADING);
        if (downloadCityList == null) {
            return new int[0];
        }
        ArrayList arrayList = new ArrayList(downloadCityList.length);
        for (int i2 : downloadCityList) {
            PackageStates[] cityDataStatus = downloadManager.getCityDataStatus(i2);
            if (cityDataStatus != null) {
                int length = cityDataStatus.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        i = 0;
                        break;
                    }
                    PackageStates packageStates = cityDataStatus[i3];
                    if (packageStates != null && packageStates.packageType == 2) {
                        i = packageStates.pacState.downloadStatus;
                        break;
                    }
                    i3++;
                }
                if (i == 2) {
                    arrayList.add(Integer.valueOf(i2));
                }
            }
        }
        return JsArraysUtils.toIntArrays(arrayList);
    }

    public final boolean isDownloadMapRoute(int i) {
        PackageStates[] packageStatesByAdcode = getInstance().getPackageStatesByAdcode(i);
        if (packageStatesByAdcode == null) {
            return false;
        }
        HashMap hashMap = new HashMap(packageStatesByAdcode.length);
        for (PackageStates packageStates : packageStatesByAdcode) {
            if (packageStates != null) {
                hashMap.put(Integer.valueOf(packageStates.packageType), Boolean.valueOf(true ^ needDownload(packageStates)));
            }
        }
        boolean booleanValue = hashMap.containsKey(Integer.valueOf(0)) ? ((Boolean) hashMap.get(Integer.valueOf(0))).booleanValue() : false;
        boolean booleanValue2 = hashMap.containsKey(Integer.valueOf(1)) ? ((Boolean) hashMap.get(Integer.valueOf(1))).booleanValue() : false;
        if (!booleanValue || !booleanValue2) {
            return false;
        }
        return true;
    }

    public final boolean needDownload(PackageStates packageStates) {
        return (packageStates.pacState.downloadStatus == 7 && packageStates.pacState.updateFlag == 0) ? false : true;
    }
}
