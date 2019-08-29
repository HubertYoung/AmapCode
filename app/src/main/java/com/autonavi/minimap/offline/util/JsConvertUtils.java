package com.autonavi.minimap.offline.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.autonavi.minimap.offline.OfflineNativeSdk;
import com.autonavi.minimap.offline.model.City;
import com.autonavi.minimap.offline.model.DownloadCity;
import com.autonavi.minimap.offline.model.DownloadProvince;
import com.autonavi.minimap.offline.model.Province;
import com.autonavi.minimap.offline.utils.OfflineUtil;
import com.autonavi.minimap.offlinesdk.ICityManager;
import com.autonavi.minimap.offlinesdk.IDownloadManager;
import com.autonavi.minimap.offlinesdk.model.CityInfo;
import com.autonavi.minimap.offlinesdk.model.DownloadListType;
import com.autonavi.minimap.offlinesdk.model.PackageInfo;
import com.autonavi.minimap.offlinesdk.model.PackageStates;
import com.autonavi.minimap.offlinesdk.model.ProvinceInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class JsConvertUtils {
    public static final int CATEGORY_ITEM_TYPE_DOWNLOADING = 4;
    private static final int DOWNLOAD_STATE_0_UNDOWNLOAD = 0;
    private static final List<Province> EMPTY_PROVINCE_LIST = new ArrayList(0);
    private static final int[] GANGAO_ADCODE_ARRAY = {810000, 820000};
    private static final int JCB_ADCODE = 0;
    private static final String NO_UPDATE_FLAG = "0";
    private static final int PACKAGE_MAP_TYPE = 0;
    private static final int PACKAGE_ROUTE_TYPE = 1;
    static final int PACKAGE_STATE_CITY_TYPE = 2;
    public static final int PACKAGE_STATE_MAP_TYPE = 0;
    private static final int PACKAGE_STATE_ROUTE_TYPE = 1;
    private static final Integer[] POPULAR_ADCODE_ARRAY;
    public static final List<Integer> POPULAR_ADCODE_LIST;
    private static final Integer[] ZHIXIA_ADCODE_ARRAY;
    public static final List<Integer> ZHIXIA_ADCODE_LIST;

    static {
        Integer[] numArr = {Integer.valueOf(110000), Integer.valueOf(310000), Integer.valueOf(440100), Integer.valueOf(440300)};
        POPULAR_ADCODE_ARRAY = numArr;
        POPULAR_ADCODE_LIST = Collections.unmodifiableList(Arrays.asList(numArr));
        Integer[] numArr2 = {Integer.valueOf(110000), Integer.valueOf(310000), Integer.valueOf(500000), Integer.valueOf(120000)};
        ZHIXIA_ADCODE_ARRAY = numArr2;
        ZHIXIA_ADCODE_LIST = Collections.unmodifiableList(Arrays.asList(numArr2));
    }

    private JsConvertUtils() {
    }

    @Nullable
    public static Province convertNativeProvince(ProvinceInfo provinceInfo) {
        ICityManager iCityManager;
        int[] iArr;
        ProvinceInfo provinceInfo2 = provinceInfo;
        ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
        if (cityManager == null) {
            return null;
        }
        Province province = new Province();
        province.setName(provinceInfo2.name);
        province.setJianpin(provinceInfo2.jianpin);
        province.setPinyin(provinceInfo2.pinyin);
        province.setAdCode(String.valueOf(provinceInfo2.adcode));
        int[] iArr2 = provinceInfo2.cityIdList;
        if (iArr2 == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        HashSet hashSet3 = new HashSet();
        int length = iArr2.length;
        int i = 0;
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        while (i < length) {
            CityInfo cityById = cityManager.getCityById(iArr2[i]);
            if (cityById != null) {
                City convertCity = convertCity(cityById);
                iArr = iArr2;
                iCityManager = cityManager;
                if (!TextUtils.equals(convertCity.getUpdateFlag(), "0") && convertCity.getDownloadStatus() == 7) {
                    province.setUpdateFlag(convertCity.getUpdateFlag());
                    j3 += (long) Integer.parseInt(convertCity.getUpdateSize());
                }
                arrayList.add(convertCity);
                j += convertCity.getMapSize();
                j2 += convertCity.getRouteSize();
                hashSet.add(Integer.valueOf(convertCity.getMapState()));
                hashSet2.add(Integer.valueOf(convertCity.getNaviState()));
                hashSet3.add(Integer.valueOf(convertCity.getDownloadStatus()));
            } else {
                iArr = iArr2;
                iCityManager = cityManager;
            }
            i++;
            iArr2 = iArr;
            cityManager = iCityManager;
        }
        int provinceDownloadState = getProvinceDownloadState(hashSet);
        int provinceDownloadState2 = getProvinceDownloadState(hashSet2);
        int provinceCityDownloadState = getProvinceCityDownloadState(hashSet, hashSet2, hashSet3);
        province.setMapState(provinceDownloadState);
        province.setNaviState(provinceDownloadState2);
        province.setDownloadStatus(provinceCityDownloadState);
        province.setCityList(arrayList);
        province.setMapSize(j);
        province.setRouteSize(j2);
        province.setUpdateSize(String.valueOf(j3));
        return province;
    }

    public static List<DownloadProvince> convertDownloadedProvinces(int[] iArr, int i) {
        ArrayList arrayList = new ArrayList();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
        if (cityManager == null) {
            return arrayList;
        }
        for (int i2 : iArr) {
            CityInfo cityById = cityManager.getCityById(i2);
            if (cityById != null) {
                ArrayList arrayList2 = (ArrayList) linkedHashMap.get(Integer.valueOf(cityById.provinceAdcode));
                if (arrayList2 != null) {
                    arrayList2.add(Integer.valueOf(i2));
                } else {
                    ArrayList arrayList3 = new ArrayList();
                    arrayList3.add(Integer.valueOf(i2));
                    linkedHashMap.put(Integer.valueOf(cityById.provinceAdcode), arrayList3);
                }
            }
        }
        for (Entry entry : linkedHashMap.entrySet()) {
            ProvinceInfo provinceByAdcode = cityManager.getProvinceByAdcode(((Integer) entry.getKey()).intValue());
            if (provinceByAdcode != null) {
                ArrayList arrayList4 = (ArrayList) entry.getValue();
                if (arrayList4 != null && !arrayList4.isEmpty()) {
                    provinceByAdcode.cityIdList = JsArraysUtils.toIntArrays(arrayList4);
                    DownloadProvince convertDownloadProvince = convertDownloadProvince(provinceByAdcode, i);
                    if (convertDownloadProvince != null) {
                        arrayList.add(convertDownloadProvince);
                    }
                }
            }
        }
        return arrayList;
    }

    @Nullable
    public static DownloadProvince convertDownloadProvince(ProvinceInfo provinceInfo, int i) {
        ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
        if (cityManager == null) {
            return null;
        }
        DownloadProvince downloadProvince = new DownloadProvince();
        downloadProvince.setName(provinceInfo.name);
        downloadProvince.setCode(i);
        downloadProvince.setJianpin(provinceInfo.jianpin);
        downloadProvince.setPinyin(provinceInfo.pinyin);
        downloadProvince.setAdCode(String.valueOf(provinceInfo.adcode));
        int[] iArr = provinceInfo.cityIdList;
        if (iArr == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        for (int cityById : iArr) {
            CityInfo cityById2 = cityManager.getCityById(cityById);
            if (cityById2 != null) {
                DownloadCity convertDownloadCity = convertDownloadCity(cityById2);
                if (!TextUtils.equals(convertDownloadCity.getUpdateFlag(), "0")) {
                    downloadProvince.setUpdateFlag(convertDownloadCity.getUpdateFlag());
                    j3 += (long) Integer.parseInt(convertDownloadCity.getUpdateSize());
                }
                if (!TextUtils.equals(convertDownloadCity.getMapUpdateFlag(), "0")) {
                    downloadProvince.setMapUpdateFlag(convertDownloadCity.getMapUpdateFlag());
                }
                if (!TextUtils.equals(convertDownloadCity.getRouteUpdateFlag(), "0")) {
                    downloadProvince.setRouteUpdateFlag(convertDownloadCity.getRouteUpdateFlag());
                }
                arrayList.add(convertDownloadCity);
                j += convertDownloadCity.getMapSize();
                j2 += convertDownloadCity.getRouteSize();
            }
        }
        downloadProvince.setCityList(arrayList);
        downloadProvince.setMapSize(j);
        downloadProvince.setRouteSize(j2);
        downloadProvince.setUpdateSize(String.valueOf(j3));
        return downloadProvince;
    }

    public static List<DownloadProvince> getDownloadProvince() {
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        if (downloadManager == null) {
            return new ArrayList(0);
        }
        int[] downloadCityList = downloadManager.getDownloadCityList(DownloadListType.DOWNLOAD_LIST_TYPE_DOWNLOADING);
        int[] downloadCityList2 = downloadManager.getDownloadCityList(DownloadListType.DOWNLOAD_LIST_TYPE_DOWNLOADED);
        if ((downloadCityList2 == null || downloadCityList2.length == 0) && (downloadCityList == null || downloadCityList.length == 0)) {
            return getRecommendDownloadProvinces();
        }
        ArrayList arrayList = new ArrayList();
        if (!(downloadCityList == null || downloadCityList.length == 0)) {
            ProvinceInfo provinceInfo = new ProvinceInfo();
            StringBuilder sb = new StringBuilder();
            sb.append(downloadCityList.length);
            sb.append("个地区");
            provinceInfo.name = sb.toString();
            provinceInfo.cityIdList = downloadCityList;
            DownloadProvince convertDownloadProvince = convertDownloadProvince(provinceInfo, 4);
            if (convertDownloadProvince != null) {
                arrayList.add(convertDownloadProvince);
            }
        }
        if (downloadCityList2 != null && downloadCityList2.length > 0) {
            List<DownloadProvince> convertDownloadedProvinces = convertDownloadedProvinces(downloadCityList2, 5);
            if (convertDownloadedProvinces != null) {
                arrayList.addAll(convertDownloadedProvinces);
            }
        }
        return arrayList;
    }

    public static List<DownloadProvince> getRecommendDownloadProvinces() {
        ArrayList arrayList = new ArrayList(1);
        int currentCityAdcode = OfflineUtil.getCurrentCityAdcode();
        int[] iArr = currentCityAdcode != 0 ? new int[]{0, currentCityAdcode} : new int[]{0};
        ProvinceInfo provinceInfo = new ProvinceInfo();
        provinceInfo.cityIdList = iArr;
        DownloadProvince convertDownloadProvince = convertDownloadProvince(provinceInfo, 1);
        if (convertDownloadProvince != null) {
            arrayList.add(convertDownloadProvince);
        }
        return arrayList;
    }

    public static City convertCity(CityInfo cityInfo) {
        return convertCity(cityInfo, true);
    }

    public static City convertCity(CityInfo cityInfo, boolean z) {
        PackageInfo[] packageInfoArr;
        City city = new City();
        city.setName(cityInfo.name);
        city.setJianpin(cityInfo.jianpin);
        city.setPinyin(cityInfo.pinyin);
        city.setAdCode(z ? cityInfo.cityId : cityInfo.adcode);
        city.setProvinceAdcode(String.valueOf(cityInfo.provinceAdcode));
        city.setIsCurrentCity(cityInfo.adcode == OfflineUtil.getCurrentCityAdcode() ? "1" : "0");
        for (PackageInfo packageInfo : cityInfo.items) {
            if (packageInfo != null) {
                if (packageInfo.packageType == 0) {
                    city.setMapSize(packageInfo.packageBytes);
                } else if (packageInfo.packageType == 1) {
                    city.setRouteSize(packageInfo.packageBytes);
                }
            }
        }
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        PackageStates[] packageStatesArr = null;
        if (downloadManager != null) {
            packageStatesArr = downloadManager.getCityDataStatus(cityInfo.cityId);
        }
        if (packageStatesArr != null) {
            long j = 0;
            for (PackageStates packageStates : packageStatesArr) {
                int i = packageStates.pacState.downloadStatus;
                String valueOf = String.valueOf(packageStates.pacState.updateFlag);
                if (packageStates.packageType == 0) {
                    city.setMapState(i);
                    city.setMapUpdateFlag(valueOf);
                    j += packageStates.pacState.updateSize;
                } else if (packageStates.packageType == 1) {
                    city.setNaviState(i);
                    city.setRouteUpdateFlag(valueOf);
                    j += packageStates.pacState.updateSize;
                } else if (packageStates.packageType == 2) {
                    city.setDownloadStatus(i);
                    city.setUpdateFlag(valueOf);
                }
            }
            city.setUpdateSize(String.valueOf(j));
        } else {
            city.setMapState(0);
            city.setNaviState(0);
            city.setDownloadStatus(0);
        }
        return city;
    }

    public static DownloadCity convertDownloadCity(CityInfo cityInfo) {
        PackageInfo[] packageInfoArr;
        DownloadCity downloadCity = new DownloadCity();
        downloadCity.setName(cityInfo.name);
        downloadCity.setJianpin(cityInfo.jianpin);
        downloadCity.setPinyin(cityInfo.pinyin);
        downloadCity.setAdCode(String.valueOf(cityInfo.cityId));
        downloadCity.setProvinceAdcode(String.valueOf(cityInfo.provinceAdcode));
        downloadCity.setIsCurrentCity((OfflineUtil.getCurrentCityAdcode() != 0 && cityInfo.adcode == OfflineUtil.getCurrentCityAdcode()) ? "1" : "0");
        for (PackageInfo packageInfo : cityInfo.items) {
            if (packageInfo != null) {
                if (packageInfo.packageType == 0) {
                    downloadCity.setMapSize(packageInfo.packageBytes);
                } else if (packageInfo.packageType == 1) {
                    downloadCity.setRouteSize(packageInfo.packageBytes);
                }
            }
        }
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        PackageStates[] packageStatesArr = null;
        if (downloadManager != null) {
            packageStatesArr = downloadManager.getCityDataStatus(cityInfo.cityId);
        }
        if (packageStatesArr != null) {
            long j = 0;
            for (PackageStates packageStates : packageStatesArr) {
                String valueOf = String.valueOf(packageStates.pacState.updateFlag);
                int i = packageStates.pacState.downloadStatus;
                long j2 = packageStates.pacState.transBytes;
                if (packageStates.packageType == 0) {
                    downloadCity.setMapState(i);
                    downloadCity.setDownloadedMapSize(j2);
                    downloadCity.setMapUpdateFlag(valueOf);
                    j += packageStates.pacState.updateSize;
                } else if (packageStates.packageType == 1) {
                    downloadCity.setNaviState(i);
                    downloadCity.setDownloadedRouteSize(j2);
                    downloadCity.setRouteUpdateFlag(valueOf);
                    j += packageStates.pacState.updateSize;
                } else if (packageStates.packageType == 2) {
                    downloadCity.setDownloadStatus(i);
                    downloadCity.setCurTransDataSize(packageStates.pacState.transBytes);
                    downloadCity.setCurAllDataSize(packageStates.pacState.allBytes);
                    downloadCity.setUpdateFlag(valueOf);
                }
                downloadCity.setDownloadType(packageStates.packageType);
            }
            downloadCity.setUpdateSize(String.valueOf(j));
        } else {
            downloadCity.setMapState(0);
            downloadCity.setNaviState(0);
            downloadCity.setDownloadStatus(0);
        }
        downloadCity.setDownloadingProgress(getDownloadingProgress(downloadCity));
        return downloadCity;
    }

    public static List<Province> convertProvince() {
        ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
        if (cityManager == null) {
            return EMPTY_PROVINCE_LIST;
        }
        CityInfo[] allCities = cityManager.getAllCities();
        ProvinceInfo[] allProvinces = cityManager.getAllProvinces();
        if (allCities == null || allProvinces == null) {
            return EMPTY_PROVINCE_LIST;
        }
        ArrayList arrayList = new ArrayList();
        for (ProvinceInfo convertNativeProvince : allProvinces) {
            Province convertNativeProvince2 = convertNativeProvince(convertNativeProvince);
            if (convertNativeProvince2 != null) {
                arrayList.add(convertNativeProvince2);
            }
        }
        return arrayList;
    }

    public static List<City> convertHotCity() {
        ArrayList arrayList = new ArrayList();
        ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
        if (cityManager == null) {
            return arrayList;
        }
        CityInfo cityByAdcode = cityManager.getCityByAdcode(0);
        if (cityByAdcode != null) {
            arrayList.add(convertCity(cityByAdcode));
        }
        int currentCityAdcode = OfflineUtil.getCurrentCityAdcode();
        if (currentCityAdcode != 0) {
            CityInfo cityByAdcode2 = cityManager.getCityByAdcode(currentCityAdcode);
            if (cityByAdcode2 != null) {
                arrayList.add(convertCity(cityByAdcode2));
            }
        }
        for (Integer intValue : POPULAR_ADCODE_ARRAY) {
            int intValue2 = intValue.intValue();
            if (intValue2 != currentCityAdcode) {
                CityInfo cityByAdcode3 = cityManager.getCityByAdcode(intValue2);
                if (cityByAdcode3 != null) {
                    arrayList.add(convertCity(cityByAdcode3));
                }
            }
        }
        return arrayList;
    }

    public static int getDownloadingProgress(DownloadCity downloadCity) {
        int downloadType = downloadCity.getDownloadType();
        long curTransDataSize = downloadCity.getCurTransDataSize();
        long curAllDataSize = downloadCity.getCurAllDataSize();
        if (downloadType != 2 || (curAllDataSize <= 0 && curTransDataSize <= 0)) {
            return 0;
        }
        return (int) ((curTransDataSize * 100) / curAllDataSize);
    }

    public static String checkAdCode(@NonNull String str) {
        return !TextUtils.equals("0", str) ? str : "000000";
    }

    public static String checkCityId(@NonNull String str) {
        return checkAdCode(str);
    }

    private static int getProvinceDownloadState(Set<Integer> set) {
        if (set.contains(Integer.valueOf(0))) {
            return 0;
        }
        return (set.size() != 1 || !set.contains(Integer.valueOf(7))) ? 2 : 7;
    }

    private static int getProvinceCityDownloadState(Set<Integer> set, Set<Integer> set2, Set<Integer> set3) {
        if ((set.size() == 2 && set.contains(Integer.valueOf(2)) && !set.contains(Integer.valueOf(0))) || (set2.size() == 2 && set2.contains(Integer.valueOf(2)) && !set2.contains(Integer.valueOf(0)))) {
            return 2;
        }
        if (set.size() != 1 || !set.contains(Integer.valueOf(7)) || set2.size() != 1 || !set2.contains(Integer.valueOf(7))) {
            return getProvinceDownloadState(set3);
        }
        return 7;
    }
}
