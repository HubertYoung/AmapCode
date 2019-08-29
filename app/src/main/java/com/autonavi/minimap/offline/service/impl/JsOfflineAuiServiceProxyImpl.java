package com.autonavi.minimap.offline.service.impl;

import android.os.Build.VERSION;
import android.os.Handler;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alipay.mobile.beehive.cityselect.ui.SelectCityActivity;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Page.ResultType;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.offline.IOfflineNativeSdk;
import com.autonavi.minimap.offline.OfflineNativeSdk;
import com.autonavi.minimap.offline.OfflineSDK;
import com.autonavi.minimap.offline.helper.NativeSdkHelper;
import com.autonavi.minimap.offline.model.Province;
import com.autonavi.minimap.offline.model.UpdateInfo;
import com.autonavi.minimap.offline.service.IJsOfflineAuiServiceProxy;
import com.autonavi.minimap.offline.uiutils.UiUtils;
import com.autonavi.minimap.offline.util.JsConvertUtils;
import com.autonavi.minimap.offline.util.JsNativeFacade;
import com.autonavi.minimap.offline.utils.CityHelper;
import com.autonavi.minimap.offline.utils.OfflineSpUtil;
import com.autonavi.minimap.offline.utils.OfflineUtil;
import com.autonavi.minimap.offline.utils.UserReport;
import com.autonavi.minimap.offlinesdk.ICityDownloadObserver;
import com.autonavi.minimap.offlinesdk.ICityManager;
import com.autonavi.minimap.offlinesdk.IDownloadManager;
import com.autonavi.minimap.offlinesdk.OfflineConfig;
import com.autonavi.minimap.offlinesdk.model.CityInfo;
import com.autonavi.minimap.offlinesdk.model.CityListNotifyInfo;
import com.autonavi.minimap.offlinesdk.model.DownloadInfo;
import com.autonavi.minimap.offlinesdk.model.PackageStates;
import com.autonavi.minimap.offlinesdk.model.ProvinceInfo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsOfflineAuiServiceProxyImpl implements IJsOfflineAuiServiceProxy {
    public static final String AD_CODE = "adCode";
    public static final int CMD_ADD_CITY = 13;
    public static final int CMD_CANCEL_CITIES = 6;
    public static final int CMD_CANCEL_CITY = 10;
    public static final int CMD_DELETE_ALL = 3;
    public static final int CMD_DELETE_CITIES = 7;
    public static final int CMD_DELETE_CITY = 12;
    public static final int CMD_DOWNLOAD_ALL = 0;
    public static final int CMD_DOWNLOAD_CITIES = 4;
    public static final int CMD_PAUSE_ALL = 1;
    public static final int CMD_PAUSE_CITIES = 5;
    public static final int CMD_RESUME_CITIES = 8;
    public static final int CMD_RESUME_CITY = 11;
    public static final int CMD_UPDATE_ALL = 2;
    public static final int CMD_UPDATE_CITY = 9;
    public static final int DOWNLOAD_ERROR_404 = 9;
    public static final int DOWNLOAD_ERROR_GET_URL = 7;
    public static final int DOWNLOAD_ERROR_IO_ERROR = 10;
    public static final int DOWNLOAD_ERROR_MD5 = 2;
    public static final int DOWNLOAD_ERROR_NETWORK = 1;
    public static final int DOWNLOAD_ERROR_NOFILE = 5;
    public static final int DOWNLOAD_ERROR_NONE = 0;
    public static final int DOWNLOAD_ERROR_NOSPACE = 4;
    public static final int DOWNLOAD_ERROR_REPLACE_DATA = 6;
    public static final int DOWNLOAD_ERROR_UNZIP = 3;
    public static final int DOWNLOAD_ERROR_UZIP_NOSPACE = 8;
    public static final int DOWNLOAD_STATUS_CANCELED = 9;
    public static final int DOWNLOAD_STATUS_COMPLETED = 7;
    public static final int DOWNLOAD_STATUS_DOWNLOADED = 4;
    public static final int DOWNLOAD_STATUS_DOWNLOADING = 2;
    public static final int DOWNLOAD_STATUS_ERROR = 8;
    public static final int DOWNLOAD_STATUS_PAUSE = 3;
    public static final int DOWNLOAD_STATUS_UNDOWNLOAD = 0;
    public static final int DOWNLOAD_STATUS_UNZIPPED = 6;
    public static final int DOWNLOAD_STATUS_UNZIPPING = 5;
    public static final int DOWNLOAD_STATUS_WAITING = 1;
    public static final int DOWNLOAD_TYPE_CITY = 2;
    public static final int DOWNLOAD_TYPE_MAP = 0;
    public static final int DOWNLOAD_TYPE_ROUTE = 1;
    private static final String TAG = "ModuleJsOfflineAuiService";
    private Handler h = new Handler();
    private a mDownloadListener = new a(0);

    static class a implements ICityDownloadObserver {
        private long a;

        public final void onCityProgress(int i, long j, long j2) {
        }

        public final void onCityStatusChange(int i, int i2) {
        }

        public final void onProgress(int i, int i2, long j, long j2) {
        }

        public final void onStatusChange(int i, int i2, int i3) {
        }

        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }

        private static void a(UpdateInfo updateInfo) {
            JSONObject jSONObject = new JSONObject();
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(updateInfo.getCode());
                jSONObject.put("code", sb.toString());
                jSONObject.put("cityID", updateInfo.getCityId());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(updateInfo.getCityStatus());
                jSONObject.put("cityStatus", sb2.toString());
                StringBuilder sb3 = new StringBuilder();
                sb3.append(updateInfo.getMapItemStatus());
                jSONObject.put("mapItemStatus", sb3.toString());
                StringBuilder sb4 = new StringBuilder();
                sb4.append(updateInfo.getNaviItemStatus());
                jSONObject.put("naviItemStatus", sb4.toString());
                StringBuilder sb5 = new StringBuilder();
                sb5.append(updateInfo.getProgress());
                jSONObject.put("progress", sb5.toString());
            } catch (JSONException unused) {
            }
        }

        private static void b(UpdateInfo updateInfo) {
            try {
                JSONArray jSONArray = new JSONArray();
                JSONObject jSONObject = new JSONObject();
                StringBuilder sb = new StringBuilder();
                sb.append(updateInfo.getNaviItemStatus());
                jSONObject.put("naviItemStatus", sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(updateInfo.getMapItemStatus());
                jSONObject.put("mapItemStatus", sb2.toString());
                jSONObject.put("cityID", updateInfo.getCityId());
                StringBuilder sb3 = new StringBuilder();
                sb3.append(updateInfo.getCityStatus());
                jSONObject.put("cityStatus", sb3.toString());
                StringBuilder sb4 = new StringBuilder();
                sb4.append(updateInfo.getUpdateFlag());
                jSONObject.put("updateFlag", sb4.toString());
                StringBuilder sb5 = new StringBuilder();
                sb5.append(updateInfo.getMapUpdateFlag());
                jSONObject.put("mapUpdateFlag", sb5.toString());
                StringBuilder sb6 = new StringBuilder();
                sb6.append(updateInfo.getRouteUpdateFlag());
                jSONObject.put("routeUpdateFlag", sb6.toString());
                StringBuilder sb7 = new StringBuilder();
                sb7.append(updateInfo.getUpdateType());
                jSONObject.put("updateType", sb7.toString());
                jSONArray.put(jSONObject);
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("code", 1);
                jSONObject2.put(SelectCityActivity.EXTRA_PARAM_CITY_LIST, jSONArray);
            } catch (JSONException unused) {
            }
        }

        public final void onStart(int i) {
            UpdateInfo updateInfo = new UpdateInfo();
            updateInfo.setCityId(i);
            updateInfo.setCityStatus(2);
            updateInfo.setCode(1);
            b(updateInfo);
        }

        public final void onAllStatusChange(int i, int i2, int i3, int i4) {
            UpdateInfo updateInfo = new UpdateInfo();
            updateInfo.setCode(1);
            updateInfo.setCityId(i);
            updateInfo.setMapItemStatus(i2);
            updateInfo.setNaviItemStatus(i3);
            if (i4 == 5 || i4 == 6) {
                updateInfo.setProgress(100);
            }
            if (i4 == 9 || i4 == 0) {
                updateInfo.setMapItemStatus(0);
                updateInfo.setNaviItemStatus(0);
                i4 = 0;
            }
            PackageStates[] packageStatesByCityid = JsNativeFacade.getInstance().getPackageStatesByCityid(i);
            if (packageStatesByCityid != null) {
                for (PackageStates packageStates : packageStatesByCityid) {
                    if (packageStates.packageType == 2) {
                        updateInfo.setUpdateFlag(packageStates.pacState.updateFlag);
                    } else if (packageStates.packageType == 0) {
                        updateInfo.setMapUpdateFlag(packageStates.pacState.updateFlag);
                    } else if (packageStates.packageType == 1) {
                        updateInfo.setRouteUpdateFlag(packageStates.pacState.updateFlag);
                    }
                }
            }
            updateInfo.setCityStatus(i4);
            b(updateInfo);
            a();
            ProvinceInfo provinceInfoByCityId = JsNativeFacade.getInstance().getProvinceInfoByCityId(i);
            if (provinceInfoByCityId != null) {
                Province convertNativeProvince = JsConvertUtils.convertNativeProvince(provinceInfoByCityId);
                if (convertNativeProvince != null) {
                    UpdateInfo updateInfo2 = new UpdateInfo();
                    updateInfo2.setCityId(Integer.parseInt(convertNativeProvince.getAdCode()));
                    updateInfo2.setCityStatus(convertNativeProvince.getDownloadStatus());
                    updateInfo2.setMapItemStatus(convertNativeProvince.getMapState());
                    updateInfo2.setNaviItemStatus(convertNativeProvince.getNaviState());
                    updateInfo2.setCode(1);
                    updateInfo2.setUpdateFlag(Integer.parseInt(convertNativeProvince.getUpdateFlag()));
                    updateInfo2.setUpdateType(1);
                    b(updateInfo2);
                }
            }
        }

        public final void onCityListStatusChange(CityListNotifyInfo[] cityListNotifyInfoArr, CityListNotifyInfo[] cityListNotifyInfoArr2) {
            if (cityListNotifyInfoArr != null && cityListNotifyInfoArr.length != 0) {
                try {
                    JSONArray jSONArray = new JSONArray();
                    JSONObject jSONObject = new JSONObject();
                    for (int i = 0; i < cityListNotifyInfoArr.length; i++) {
                        JSONObject jSONObject2 = new JSONObject();
                        StringBuilder sb = new StringBuilder();
                        sb.append(cityListNotifyInfoArr[i].RouteStatus);
                        jSONObject2.put("naviItemStatus", sb.toString());
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(cityListNotifyInfoArr[i].MapStatus);
                        jSONObject2.put("mapItemStatus", sb2.toString());
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(cityListNotifyInfoArr[i].CityStatus);
                        jSONObject2.put("cityStatus", sb3.toString());
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(cityListNotifyInfoArr[i].CityId);
                        jSONObject2.put("cityID", JsConvertUtils.checkCityId(sb4.toString()));
                        jSONArray.put(jSONObject2);
                    }
                    if (cityListNotifyInfoArr2 != null) {
                        for (int i2 = 0; i2 < cityListNotifyInfoArr2.length; i2++) {
                            JSONObject jSONObject3 = new JSONObject();
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(cityListNotifyInfoArr2[i2].RouteStatus);
                            jSONObject3.put("naviItemStatus", sb5.toString());
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(cityListNotifyInfoArr2[i2].MapStatus);
                            jSONObject3.put("mapItemStatus", sb6.toString());
                            StringBuilder sb7 = new StringBuilder();
                            sb7.append(cityListNotifyInfoArr2[i2].CityStatus);
                            jSONObject3.put("cityStatus", sb7.toString());
                            jSONObject3.put("updateType", "1");
                            StringBuilder sb8 = new StringBuilder();
                            sb8.append(cityListNotifyInfoArr2[i2].CityId);
                            jSONObject3.put("cityID", sb8.toString());
                            jSONArray.put(jSONObject3);
                        }
                    }
                    jSONObject.put("code", 1);
                    jSONObject.put(SelectCityActivity.EXTRA_PARAM_CITY_LIST, jSONArray);
                } catch (JSONException unused) {
                }
                a();
            }
        }

        private static void a() {
            IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
            if (downloadManager != null) {
                String str = downloadManager.havingUpdateCity() ? "1" : "0";
                String str2 = downloadManager.havingPauseCity() ? "1" : "0";
                String str3 = downloadManager.havingDownloadingCity() ? "1" : "0";
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("code", 3);
                    jSONObject.put("allUpdateStatus", str);
                    jSONObject.put("allDownloadStatus", str2);
                    jSONObject.put("allPauseStatus", str3);
                } catch (JSONException unused) {
                }
            }
        }

        public final void onCityMergeProgress(int i, int i2) {
            UpdateInfo updateInfo = new UpdateInfo();
            updateInfo.setCode(2);
            updateInfo.setCityId(i);
            updateInfo.setCityStatus(5);
            updateInfo.setProgress(i2);
            a(updateInfo);
        }

        public final int onFinish(int i, int i2) {
            UpdateInfo updateInfo = new UpdateInfo();
            updateInfo.setCityId(i);
            PackageStates[] packageStatesByAdcode = JsNativeFacade.getInstance().getPackageStatesByAdcode(i);
            if (packageStatesByAdcode != null && packageStatesByAdcode.length > 0) {
                for (int i3 = 0; i3 < packageStatesByAdcode.length; i3++) {
                    if (packageStatesByAdcode[i3].packageType == 2) {
                        if (i2 == 0) {
                            updateInfo.setCityStatus(7);
                            updateInfo.setProgress(100);
                        } else {
                            updateInfo.setCityStatus(0);
                            updateInfo.setProgress(0);
                        }
                    } else if (packageStatesByAdcode[i3].packageType == 0) {
                        if (i2 == 0) {
                            updateInfo.setMapItemStatus(7);
                        } else {
                            updateInfo.setMapItemStatus(0);
                        }
                    } else if (i2 == 0) {
                        updateInfo.setNaviItemStatus(7);
                    } else {
                        updateInfo.setNaviItemStatus(0);
                    }
                }
            }
            if (i2 == 2 && !CityHelper.hasCityDownloaded()) {
                NativeSdkHelper.cleanIncreaseTrafficRandom();
            }
            updateInfo.setCode(1);
            b(updateInfo);
            return 0;
        }

        public final void onError(int i, int i2, int i3) {
            if (i3 == 4) {
                ToastHelper.showToast("存储空间不足导致下载失败，请清理空间后重试");
            } else if (i3 == 8) {
                ToastHelper.showToast("存储空间不足解压所选数据，请及时清理空间");
            }
            UpdateInfo updateInfo = new UpdateInfo();
            updateInfo.setCode(1);
            updateInfo.setCityId(i);
            PackageStates[] packageStatesByCityid = JsNativeFacade.getInstance().getPackageStatesByCityid(i);
            updateInfo.setMapItemStatus(0);
            updateInfo.setNaviItemStatus(0);
            updateInfo.setCityStatus(0);
            if (packageStatesByCityid != null) {
                for (PackageStates packageStates : packageStatesByCityid) {
                    if (packageStates.packageType == 2) {
                        updateInfo.setCityStatus(packageStates.pacState.downloadStatus);
                    }
                    if (packageStates.packageType == 0) {
                        updateInfo.setMapItemStatus(packageStates.pacState.downloadStatus);
                    }
                    if (packageStates.packageType == 1) {
                        updateInfo.setNaviItemStatus(packageStates.pacState.downloadStatus);
                    }
                }
            }
            updateInfo.setError(i3);
            b(updateInfo);
        }

        public final void onAllProgress(int i, long j, long j2, long j3, long j4, long j5, long j6) {
            if (!(System.currentTimeMillis() - this.a <= 500) || j5 == j6) {
                UpdateInfo updateInfo = new UpdateInfo();
                updateInfo.setCode(2);
                updateInfo.setCityId(i);
                updateInfo.setCityStatus(2);
                updateInfo.setProgress((int) ((j5 * 100) / j6));
                a(updateInfo);
                this.a = System.currentTimeMillis();
            }
        }
    }

    public boolean autoCarHasConnection() {
        return false;
    }

    public void doStorageChange(String str) {
    }

    public String value2json(long j) {
        return "";
    }

    public void viewMap(String str) {
        ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
        if (cityManager != null) {
            UiUtils.prepareViewMap(cityManager.getCityByAdcode(Integer.parseInt(str)));
        }
    }

    public String getCurrentOfflineStoragePath() {
        OfflineConfig offlineConfig = OfflineNativeSdk.getInstance().getOfflineConfig();
        return offlineConfig != null ? offlineConfig.OfflineDataPath : "";
    }

    public long getSavedTraffic() {
        return (long) (OfflineSpUtil.getTrafficSavedSp() * 1024.0f * 1024.0f);
    }

    public String getOfflineMapSwitchState() {
        return OfflineSpUtil.getOfflineMapPrioriSp() ? "1" : "0";
    }

    public void setOfflineMapSwitchState(String str) {
        OfflineSpUtil.setShowOfflineTipTimes(3);
        OfflineSpUtil.setOfflineMapPrioriSp("1".equals(str));
    }

    public String getOfflineNaviSwitchState() {
        return !OfflineSpUtil.getNaviConfigOnline() ? "1" : "0";
    }

    public void setOfflineNaviSwitchState(String str) {
        OfflineSpUtil.setNaviConfigOnline("0".equals(str));
    }

    public String getAutoDownloadInWifiSwitchState() {
        return OfflineSpUtil.getWifiAutoUpdateSp() ? "1" : "0";
    }

    public void setAutoDownloadInWifiSwitchState(String str) {
        IOfflineNativeSdk instance = OfflineNativeSdk.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append("1".equals(str));
        instance.notifyConfigChanged("WifiAutoUpdate", sb.toString());
        OfflineSpUtil.setWifiAutoUpdateSp("1".equals(str));
    }

    public void gotoFeedbackPage() {
        IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
        if (iErrorReportStarter != null) {
            iErrorReportStarter.startOfflineMapError(AMapPageUtil.getPageContext());
        }
    }

    public void gotoFAQPage() {
        OfflineSpUtil.setFrequentlyQuestionsRedSP(false);
        gotoCommonProblem();
        UserReport.actionLogV2("P00048", "B002");
    }

    public String getCurrentCityDownloadInfo() {
        JSONObject jSONObject = new JSONObject();
        int currentCityAdcode = OfflineUtil.getCurrentCityAdcode();
        ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
        if (cityManager == null) {
            return "";
        }
        CityInfo cityByAdcode = cityManager.getCityByAdcode(currentCityAdcode);
        if (cityByAdcode == null) {
            return "";
        }
        PackageStates[] packageStatesArr = null;
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        if (downloadManager != null) {
            packageStatesArr = downloadManager.getCityDataStatus(cityByAdcode.cityId);
        }
        int i = 0;
        try {
            jSONObject.put("cityID", cityByAdcode.cityId);
            jSONObject.put("cityStatus", 0);
            jSONObject.put("mapStatus", 0);
            jSONObject.put("routeStatus", 0);
        } catch (JSONException unused) {
        }
        if (packageStatesArr != null && packageStatesArr.length > 0) {
            while (i < packageStatesArr.length) {
                try {
                    PackageStates packageStates = packageStatesArr[i];
                    if (packageStates.packageType == 0) {
                        jSONObject.put("mapStatus", packageStates.pacState.downloadStatus);
                    } else if (packageStates.packageType == 1) {
                        jSONObject.put("routeStatus", packageStates.pacState.downloadStatus);
                    } else {
                        jSONObject.put("cityStatus", packageStates.pacState.downloadStatus);
                    }
                    i++;
                } catch (JSONException unused2) {
                }
            }
        }
        return jSONObject.toString();
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean hasNewFeaturesWithPageID(java.lang.String r4) {
        /*
            r3 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            r0 = -1
            int r2 = r4.hashCode()
            switch(r2) {
                case 49: goto L_0x0025;
                case 50: goto L_0x001b;
                case 51: goto L_0x0011;
                default: goto L_0x0010;
            }
        L_0x0010:
            goto L_0x002f
        L_0x0011:
            java.lang.String r2 = "3"
            boolean r4 = r4.equals(r2)
            if (r4 == 0) goto L_0x002f
            r4 = 2
            goto L_0x0030
        L_0x001b:
            java.lang.String r2 = "2"
            boolean r4 = r4.equals(r2)
            if (r4 == 0) goto L_0x002f
            r4 = 1
            goto L_0x0030
        L_0x0025:
            java.lang.String r2 = "1"
            boolean r4 = r4.equals(r2)
            if (r4 == 0) goto L_0x002f
            r4 = 0
            goto L_0x0030
        L_0x002f:
            r4 = -1
        L_0x0030:
            switch(r4) {
                case 0: goto L_0x003e;
                case 1: goto L_0x0039;
                case 2: goto L_0x0034;
                default: goto L_0x0033;
            }
        L_0x0033:
            goto L_0x0042
        L_0x0034:
            boolean r1 = com.autonavi.minimap.offline.utils.OfflineSpUtil.getFrequentlyQuestionsRedSp()
            goto L_0x0042
        L_0x0039:
            boolean r1 = com.autonavi.minimap.offline.utils.OfflineSpUtil.getOfflineMOreRedSp()
            goto L_0x0042
        L_0x003e:
            boolean r1 = com.autonavi.minimap.offline.utils.OfflineSpUtil.getAe8RedNeedShowSp()
        L_0x0042:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.service.impl.JsOfflineAuiServiceProxyImpl.hasNewFeaturesWithPageID(java.lang.String):boolean");
    }

    public void readNewFeaturesWithPageID(String str) {
        if (!TextUtils.isEmpty(str)) {
            char c = 65535;
            switch (str.hashCode()) {
                case 49:
                    if (str.equals("1")) {
                        c = 0;
                        break;
                    }
                    break;
                case 50:
                    if (str.equals("2")) {
                        c = 1;
                        break;
                    }
                    break;
                case 51:
                    if (str.equals("3")) {
                        c = 2;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    OfflineSpUtil.setAe8RedNeedShowSp(false);
                    return;
                case 1:
                    OfflineSpUtil.setOfflineMoreRedSP(false);
                    return;
                case 2:
                    OfflineSpUtil.setFrequentlyQuestionsRedSP(false);
                    break;
            }
        }
    }

    public boolean isDownloaded(String str) {
        boolean z = false;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            JSONArray jSONArray = new JSONArray(str);
            int length = jSONArray.length();
            HashSet hashSet = new HashSet(length);
            for (int i = 0; i < length; i++) {
                hashSet.add(Boolean.valueOf(JsNativeFacade.getInstance().isDownloadMapRoute(Integer.parseInt(((JSONObject) jSONArray.get(i)).optString("adCode")))));
            }
            z = !hashSet.contains(Boolean.FALSE);
        } catch (Exception unused) {
        }
        return z;
    }

    public void backupConfig() {
        OfflineNativeSdk.getInstance().getDownloadManager().backupConfig();
    }

    public boolean checkInit() {
        return OfflineNativeSdk.getInstance().isInit();
    }

    public String getCityDownloadStatusWithAdcode(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
        if (cityManager == null) {
            return "";
        }
        CityInfo cityByAdcode = cityManager.getCityByAdcode(Integer.parseInt(str));
        if (cityByAdcode == null) {
            return "";
        }
        PackageStates[] packageStatesByCityid = JsNativeFacade.getInstance().getPackageStatesByCityid(cityByAdcode.cityId);
        if (packageStatesByCityid == null) {
            return "";
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("mapItemStatus", "0");
            jSONObject.put("naviItemStatus", "0");
            jSONObject.put("cityStatus", "0");
            StringBuilder sb = new StringBuilder();
            sb.append(cityByAdcode.cityId);
            jSONObject.put("cityID", sb.toString());
            for (PackageStates packageStates : packageStatesByCityid) {
                if (packageStates.packageType == 0) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(packageStates.pacState.downloadStatus);
                    jSONObject.put("mapItemStatus", sb2.toString());
                } else if (packageStates.packageType == 1) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(packageStates.pacState.downloadStatus);
                    jSONObject.put("naviItemStatus", sb3.toString());
                } else {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(packageStates.pacState.downloadStatus);
                    jSONObject.put("cityStatus", sb4.toString());
                }
            }
            return jSONObject.toString();
        } catch (JSONException unused) {
            return "";
        }
    }

    public void setResult() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.setResult(ResultType.OK, null);
        }
    }

    public String getFreeDeviceSpace() {
        long j;
        long j2;
        StatFs statFs = new StatFs(OfflineNativeSdk.getInstance().getOfflineConfig().OfflinePath);
        if (VERSION.SDK_INT >= 18) {
            j2 = statFs.getBlockSizeLong();
            j = statFs.getAvailableBlocksLong();
        } else {
            j2 = (long) statFs.getBlockSize();
            j = (long) statFs.getAvailableBlocks();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(j * j2);
        return sb.toString();
    }

    public static void gotoCommonProblem() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            aja aja = new aja(ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.OFFLINEMAP_FAQ));
            aja.b = new ajf() {
                public final String b() {
                    return "常见问题";
                }
            };
            aix aix = (aix) defpackage.esb.a.a.a(aix.class);
            if (aix != null) {
                aix.a(pageContext, aja);
            }
        }
    }

    public void performDownloadCmd(int i, String str) {
        switch (i) {
            case 0:
                OfflineNativeSdk.getInstance().getDownloadManager().startDownloadAll();
                return;
            case 1:
                OfflineNativeSdk.getInstance().getDownloadManager().pauseDownloadAll();
                return;
            case 2:
                OfflineNativeSdk.getInstance().getDownloadManager().startUpdateAll();
                return;
            case 4:
                List<DownloadInfo> downloadCities = getDownloadCities(str);
                if (downloadCities != null && !downloadCities.isEmpty()) {
                    OfflineNativeSdk.getInstance().getDownloadManager().startDownloadList(downloadCities);
                    return;
                }
                return;
            case 5:
                OfflineNativeSdk.getInstance().getDownloadManager().pauseDownloadByCityIdArray(getCityIds(str));
                return;
            case 6:
                OfflineNativeSdk.getInstance().getDownloadManager().cancelDownloadByCityIdArray(getCityIds(str));
                return;
            case 8:
                List<DownloadInfo> downloadCities2 = getDownloadCities(str);
                if (downloadCities2 != null && downloadCities2.size() != 0) {
                    int size = downloadCities2.size();
                    int[] iArr = new int[size];
                    for (int i2 = 0; i2 < size; i2++) {
                        iArr[i2] = downloadCities2.get(i2).cityid;
                    }
                    OfflineNativeSdk.getInstance().getDownloadManager().resumeDownloadByCityIdArray(iArr);
                    return;
                }
                return;
            case 9:
                DownloadInfo downloadCity = getDownloadCity(str);
                if (downloadCity != null) {
                    OfflineNativeSdk.getInstance().getDownloadManager().updateDownload(downloadCity);
                    return;
                }
                break;
            case 10:
                DownloadInfo downloadCity2 = getDownloadCity(str);
                if (downloadCity2 != null) {
                    OfflineNativeSdk.getInstance().getDownloadManager().cancelDownloadByCityId(downloadCity2.cityid);
                    return;
                }
                break;
            case 11:
                DownloadInfo downloadCity3 = getDownloadCity(str);
                if (downloadCity3 != null) {
                    OfflineNativeSdk.getInstance().getDownloadManager().resumeDownloadByCityId(downloadCity3.cityid);
                    return;
                }
                break;
            case 12:
                DownloadInfo downloadCity4 = getDownloadCity(str);
                if (downloadCity4 != null) {
                    OfflineNativeSdk.getInstance().getDownloadManager().deleteOfflineDataByCityId(downloadCity4.cityid);
                    return;
                }
                break;
            case 13:
                List<DownloadInfo> downloadCities3 = getDownloadCities(str);
                if (downloadCities3 != null && downloadCities3.size() != 0) {
                    OfflineNativeSdk.getInstance().getDownloadManager().startDownloadAndCheckNetworkList(downloadCities3);
                    break;
                } else {
                    return;
                }
        }
    }

    private int[] getCityIds(String str) {
        List<DownloadInfo> downloadCities = getDownloadCities(str);
        int size = downloadCities.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = downloadCities.get(i).cityid;
        }
        return iArr;
    }

    @Nullable
    private DownloadInfo getDownloadCity(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
        if (cityManager == null) {
            return null;
        }
        DownloadInfo downloadInfo = new DownloadInfo();
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("adCode");
            String optString2 = jSONObject.has("types") ? jSONObject.optString("types") : "2";
            Integer valueOf = Integer.valueOf(Integer.parseInt(optString));
            CityInfo cityByAdcode = cityManager.getCityByAdcode(valueOf.intValue());
            downloadInfo.cityid = cityByAdcode != null ? cityByAdcode.cityId : valueOf.intValue();
            downloadInfo.types = Integer.parseInt(optString2);
        } catch (JSONException unused) {
        }
        return downloadInfo;
    }

    private List<DownloadInfo> getDownloadCities(String str) {
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(str)) {
            return arrayList;
        }
        ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
        if (cityManager == null) {
            return arrayList;
        }
        try {
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = (JSONObject) jSONArray.get(i);
                String optString = jSONObject.optString("adCode");
                String optString2 = jSONObject.has("types") ? jSONObject.optString("types") : "2";
                Integer valueOf = Integer.valueOf(Integer.parseInt(optString));
                CityInfo cityByAdcode = cityManager.getCityByAdcode(valueOf.intValue());
                DownloadInfo downloadInfo = new DownloadInfo();
                downloadInfo.cityid = cityByAdcode != null ? cityByAdcode.cityId : valueOf.intValue();
                downloadInfo.types = Integer.parseInt(optString2);
                arrayList.add(downloadInfo);
            }
        } catch (JSONException unused) {
        }
        return arrayList;
    }

    public int switchOfflineDataCheck(String str) {
        return OfflineNativeSdk.getInstance().getDownloadManager().switchDirCheck(str);
    }

    public boolean isDownloadingOfflineData() {
        return OfflineSDK.getInstance().getDownloadingDataType() != 0;
    }

    public void updateCityData(int[] iArr, int[] iArr2) {
        if (iArr.length != 0 && iArr2.length != 0 && iArr.length == iArr2.length) {
            try {
                JSONObject jSONObject = new JSONObject();
                JSONArray jSONArray = new JSONArray();
                IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
                for (int i = 0; i < iArr.length; i++) {
                    JSONObject jSONObject2 = new JSONObject();
                    PackageStates[] packageStatesArr = null;
                    if (downloadManager != null) {
                        packageStatesArr = downloadManager.getCityDataStatus(iArr[i]);
                    }
                    if (packageStatesArr != null) {
                        if (packageStatesArr.length == 3) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(packageStatesArr[2].pacState.updateFlag);
                            jSONObject2.put("updateFlag", sb.toString());
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(packageStatesArr[0].pacState.updateFlag);
                            jSONObject2.put("mapUpdateFlag", sb2.toString());
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(packageStatesArr[1].pacState.updateFlag);
                            jSONObject2.put("routeUpdateFlag", sb3.toString());
                        } else if (iArr2[i] == 0) {
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append(packageStatesArr[1].pacState.updateFlag);
                            jSONObject2.put("updateFlag", sb4.toString());
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(packageStatesArr[0].pacState.updateFlag);
                            jSONObject2.put("mapUpdateFlag", sb5.toString());
                            jSONObject2.put("routeUpdateFlag", "-1");
                        } else {
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(packageStatesArr[1].pacState.updateFlag);
                            jSONObject2.put("updateFlag", sb6.toString());
                            StringBuilder sb7 = new StringBuilder();
                            sb7.append(packageStatesArr[0].pacState.updateFlag);
                            jSONObject2.put("routeUpdateFlag", sb7.toString());
                            jSONObject2.put("mapUpdateFlag", "-1");
                        }
                    }
                    jSONObject2.put("cityID", iArr[i]);
                    jSONArray.put(jSONObject2);
                }
                jSONObject.put("code", 1);
                jSONObject.put(SelectCityActivity.EXTRA_PARAM_CITY_LIST, jSONArray);
            } catch (JSONException unused) {
            }
        }
    }
}
