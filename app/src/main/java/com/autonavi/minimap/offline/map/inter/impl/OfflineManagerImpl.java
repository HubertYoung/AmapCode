package com.autonavi.minimap.offline.map.inter.impl;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.constants.AppLinkConstants;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.Builder;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.NodeDialogFragmentOnClickListener;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.offline.OfflineNativeSdk;
import com.autonavi.minimap.offline.OfflineSDK;
import com.autonavi.minimap.offline.WorkThreadManager;
import com.autonavi.minimap.offline.WorkThreadManager.OfflineTask;
import com.autonavi.minimap.offline.auto.model.ATJsApkInfo.ATJsApkItem;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATApkMemo;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATApkPackage;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATApkPackageResponse;
import com.autonavi.minimap.offline.auto.protocol.ApkDownloadPersistence;
import com.autonavi.minimap.offline.auto.protocol.ApkUpdateHandler;
import com.autonavi.minimap.offline.auto.protocol.ApkUpdateHandler.UpdateCallback;
import com.autonavi.minimap.offline.externalimport.IExternalService;
import com.autonavi.minimap.offline.helper.JsAlongWayRouteCityHelper;
import com.autonavi.minimap.offline.init.OfflineDataInit;
import com.autonavi.minimap.offline.inter.inner.ICheckOfflineResponse;
import com.autonavi.minimap.offline.koala.Koala;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.minimap.offline.map.inter.IOfflineManager.AutoApkUpdateCallback;
import com.autonavi.minimap.offline.uiutils.UiController;
import com.autonavi.minimap.offline.uiutils.UiUtils;
import com.autonavi.minimap.offline.util.JsNativeFacade;
import com.autonavi.minimap.offline.util.JsPath;
import com.autonavi.minimap.offline.utils.CityHelper;
import com.autonavi.minimap.offline.utils.OfflineSpUtil;
import com.autonavi.minimap.offline.utils.OfflineUtil;
import com.autonavi.minimap.offline.utils.UserReport;
import com.autonavi.minimap.offlinesdk.ICityManager;
import com.autonavi.minimap.offlinesdk.IDownloadManager;
import com.autonavi.minimap.offlinesdk.OfflineConfig;
import com.autonavi.minimap.offlinesdk.model.CityInfo;
import com.autonavi.minimap.offlinesdk.model.DownloadListType;
import com.autonavi.minimap.offlinesdk.model.PackageStates;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class OfflineManagerImpl implements IOfflineManager {
    private static final String DEFAULT_VOICE = "默认语音";
    private static final String TAG = "OfflineManagerImpl";
    private static final Object mLockObject = new Object();
    /* access modifiers changed from: private */
    public static boolean offlineMorePageFlag = false;
    private boolean mIsScheduled = false;
    private IExternalService mService = ((IExternalService) ank.a(IExternalService.class));
    private boolean mUseDefaultVoice = false;

    private void checkOnMainThread() {
    }

    private void checkOnWorkThread() {
    }

    public void enterAlongWayDownload(ArrayList<lj> arrayList) {
    }

    public String getRouteEnlargeUpdateVer() {
        return "";
    }

    public int getVersionByMap() {
        return 1;
    }

    public void initOfflinePath() {
    }

    public boolean isRoadEnlargeUpdate() {
        return false;
    }

    public void pauseAll() {
    }

    public void resumeWifi() {
    }

    public void setMapOnlineVersion(String str) {
    }

    public void enterAlongWayDownload(int[] iArr) {
        JSONObject jSONObject;
        if (this.mService == null) {
            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.alongwayquery_error_nocity));
            return;
        }
        CityInfo[] cityInfos = JsNativeFacade.getInstance().getCityInfos(iArr);
        if (!aaw.c(this.mService.getApplication())) {
            ToastHelper.showToast(this.mService.getApplication().getString(R.string.offline_neterror));
        } else if (cityInfos.length < 2) {
            ToastHelper.showToast(this.mService.getApplication().getString(R.string.alongwayquery_error_nocity));
        } else {
            try {
                jSONObject = JsAlongWayRouteCityHelper.convertRouteBundleJO(cityInfos, "5");
            } catch (JSONException unused) {
                jSONObject = null;
            }
            if (jSONObject == null) {
                ToastHelper.showToast(this.mService.getApplication().getString(R.string.alongwayquery_error_nocity));
                return;
            }
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("url", JsPath.ALONG_WAY_PATH);
                pageBundle.putLong("startTime", System.currentTimeMillis());
                pageBundle.putString("jsData", jSONObject.toString());
                pageContext.startPage(Ajx3Page.class, pageBundle);
            }
        }
    }

    public void enterOffFrmDownloadMap() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("download_currentcity_map", "下载当前城市的离线地图");
        UiController.startCityDataFragment(pageBundle, AMapPageUtil.getPageContext());
    }

    public void deal(bid bid, Intent intent) {
        UiController.deal(bid, intent);
    }

    public void checkOfflineNavi(bid bid, ICheckOfflineResponse iCheckOfflineResponse) {
        UiUtils.checkOfflineNavi(bid, iCheckOfflineResponse);
    }

    public boolean checkOfflineCity(int i) {
        return OfflineDataInit.getInstance().checkIsCityDataDownloaded(i);
    }

    public void checkOfflineNavi(bid bid, ICheckOfflineResponse iCheckOfflineResponse, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener, NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener2) {
        if (bid != null && bid.isAlive()) {
            final FragmentActivity fragmentActivity = (FragmentActivity) DoNotUseTool.getActivity();
            if (fragmentActivity == null) {
                if (iCheckOfflineResponse != null) {
                    iCheckOfflineResponse.callback(false);
                }
                return;
            }
            final ICheckOfflineResponse iCheckOfflineResponse2 = iCheckOfflineResponse;
            final NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener3 = nodeDialogFragmentOnClickListener;
            final NodeDialogFragmentOnClickListener nodeDialogFragmentOnClickListener4 = nodeDialogFragmentOnClickListener2;
            AnonymousClass1 r0 = new OfflineTask() {
                public final Object doBackground() throws Exception {
                    if (OfflineManagerImpl.this.isOfflineDataUnzipping()) {
                        fragmentActivity.runOnUiThread(new Runnable() {
                            public final void run() {
                                if (iCheckOfflineResponse2 != null) {
                                    iCheckOfflineResponse2.callback(false);
                                }
                                try {
                                    AMapPageUtil.startAlertDialogPage(new Builder(fragmentActivity).setTitle((CharSequence) "离线导航数据安装中，请稍后再试").setPositiveButton((CharSequence) "确定", (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                                        public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                                        }
                                    }));
                                } catch (Throwable th) {
                                    DebugLog.error(th);
                                }
                            }
                        });
                        return null;
                    }
                    GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                    lj b2 = latestPosition != null ? li.a().b(latestPosition.x, latestPosition.y) : null;
                    if (b2 == null) {
                        fragmentActivity.runOnUiThread(new Runnable() {
                            public final void run() {
                                ToastHelper.showLongToast("当前定位不成功，无法选择离线优先模式进行导航");
                                if (iCheckOfflineResponse2 != null) {
                                    iCheckOfflineResponse2.callback(false);
                                }
                            }
                        });
                        return null;
                    }
                    final int i = -1;
                    try {
                        i = b2.j;
                    } catch (NumberFormatException e2) {
                        kf.a((Throwable) e2);
                    }
                    String str = b2.a;
                    boolean checkIsNaviDataDownloaded = OfflineDataInit.getInstance().checkIsNaviDataDownloaded(i);
                    iCheckOfflineResponse2.callback(checkIsNaviDataDownloaded);
                    if (!checkIsNaviDataDownloaded) {
                        final String str2 = "";
                        if (i > 0) {
                            StringBuilder sb = new StringBuilder("您还没有下载");
                            sb.append(str);
                            sb.append("离线导航数据，无法离线导航，是否下载？");
                            str2 = sb.toString();
                        }
                        fragmentActivity.runOnUiThread(new Runnable() {
                            public final void run() {
                                try {
                                    AMapPageUtil.startAlertDialogPage(new Builder(fragmentActivity).setTitle((CharSequence) str2).setAutoFinished(false).setPositiveButton((CharSequence) "立即下载", (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                                        public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                                            PageBundle pageBundle = new PageBundle();
                                            pageBundle.putString(UiController.OFFLiNE_DOWNOAD_CURRENT_CITY_NAVI, "Current_city_navi");
                                            pageBundle.putBoolean("showPoiRouteDownload", true);
                                            if (i > 0) {
                                                pageBundle.putInt(UiController.OFFLiNE_DOWNOAD_CURRENT_CITY_NAVI, i);
                                            }
                                            if (nodeAlertDialogPage != null && nodeAlertDialogPage.isAlive()) {
                                                nodeAlertDialogPage.finish();
                                            }
                                            UiController.startCityDataFragment(pageBundle, nodeAlertDialogPage);
                                            if (OfflineManagerImpl.offlineMorePageFlag && iCheckOfflineResponse2 != null) {
                                                iCheckOfflineResponse2.callback(true);
                                                OfflineManagerImpl.offlineMorePageFlag = false;
                                            }
                                            if (nodeDialogFragmentOnClickListener3 != null) {
                                                nodeDialogFragmentOnClickListener3.onClick(null);
                                            }
                                        }
                                    }).setNegativeButton((CharSequence) "忽略", (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                                        public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                                            if (nodeAlertDialogPage != null && nodeAlertDialogPage.isAlive()) {
                                                nodeAlertDialogPage.finish();
                                            }
                                            if (OfflineManagerImpl.offlineMorePageFlag) {
                                                if (iCheckOfflineResponse2 != null) {
                                                    iCheckOfflineResponse2.callback(true);
                                                    OfflineManagerImpl.offlineMorePageFlag = false;
                                                }
                                                if (nodeDialogFragmentOnClickListener4 != null) {
                                                    nodeDialogFragmentOnClickListener4.onClick(null);
                                                }
                                            }
                                        }
                                    }));
                                } catch (Throwable th) {
                                    DebugLog.error(th);
                                }
                            }
                        });
                    }
                    return null;
                }
            };
            WorkThreadManager.start(r0);
        }
    }

    public String adcodeToPinyin(int i) {
        ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
        if (cityManager == null) {
            return "";
        }
        CityInfo cityByAdcode = cityManager.getCityByAdcode(i);
        return cityByAdcode != null ? cityByAdcode.pinyin : "";
    }

    public void initialize() {
        OfflineNativeSdk.getInstance().init();
        OfflineNetStatusTracer.get().begin();
        Koala.bindService(AMapAppGlobal.getApplication());
    }

    public void destroy() {
        OfflineNativeSdk.getInstance().destroy();
        OfflineSDK.destroy();
        Koala.unbindService(AMapAppGlobal.getApplication());
        OfflineNetStatusTracer.get().end();
    }

    public boolean isDBException() {
        return OfflineUtil.isDBException();
    }

    public void setIsDBException(boolean z) {
        OfflineUtil.setIsDBException(z);
    }

    public boolean checkCityDownloadMapStatus(int i) {
        return OfflineSDK.getInstance().checkDownloadCityStatus(i);
    }

    public boolean checkCityDownload(int i) {
        return OfflineSDK.getInstance().checkDownloadCity(i);
    }

    public boolean checkCurrentCityDownloadMapStatus() {
        return OfflineSDK.getInstance().checkCurrentDownloadCityStatus();
    }

    public boolean checkIsUpdate() {
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        return downloadManager != null && downloadManager.havingUpdateCity();
    }

    public void notifyCityDataError(String str) {
        UserReport.actionLogV2(UserReport.PAGE_OFFLINEDATA_DOWNLOADEDMGR, UserReport.BTN_OFFLINEDATA_DOWNLOADMGR_MAPENGINEMD5ERROR);
        ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
        if (cityManager != null) {
            CityInfo cityByPinyin = cityManager.getCityByPinyin(str);
            if (cityByPinyin != null) {
                IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
                if (downloadManager != null) {
                    downloadManager.pauseDownloadByCityId(cityByPinyin.cityId);
                }
            }
        }
    }

    public String getOfflineDateUpdateVer() {
        if (!OfflineSpUtil.getAe8RedNeedShowSp()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(OfflineSpUtil.getOfflineDataUpdateMapVer());
        sb.append(",");
        sb.append(OfflineSpUtil.getOfflineDataUpdateNaviVer());
        return sb.toString();
    }

    public boolean isOfflineDataUpdate() {
        return OfflineSpUtil.getAe8RedNeedShowSp();
    }

    public String getOfflineDBPath() {
        OfflineConfig offlineConfig = OfflineNativeSdk.getInstance().getOfflineConfig();
        String str = offlineConfig != null ? offlineConfig.OfflinePath : "";
        if (!TextUtils.isEmpty(str)) {
            if (new File(str).exists()) {
                return str;
            }
            String currentSDCardOfflineDbPath = OfflineSDK.getCurrentSDCardOfflineDbPath();
            if (new File(currentSDCardOfflineDbPath).exists()) {
                return currentSDCardOfflineDbPath;
            }
        }
        return "";
    }

    public boolean isShowOfflineAE8UpdateTip() {
        return OfflineSDK.getInstance().showOfflineAE8UpdateTip();
    }

    public void setNeedUpgradeSp() {
        OfflineSDK.getInstance().setNeedUpgradeSp();
        OfflineSDK.getInstance().setIsUpgradeAe8Version(false);
    }

    public void putOffLatestVerByAppInit(Context context, String str, String str2, String str3, String str4, String str5) {
        OfflineSDK.getInstance().putOffLatestVerByAppInit(context, str, str2, str3, str4, null);
    }

    public int[] getCityDownloadedVerByAdcode(int i) {
        return getDownloadCityVersionByAdcode(i);
    }

    public int[] getDownloadCityVersionByAdcode(int i) {
        try {
            PackageStates[] packageStatesByAdcode = JsNativeFacade.getInstance().getPackageStatesByAdcode(i);
            if (packageStatesByAdcode != null && packageStatesByAdcode.length > 0) {
                int i2 = 0;
                int i3 = 0;
                for (PackageStates packageStates : packageStatesByAdcode) {
                    if (packageStates.packageType == 0) {
                        i2 = packageStates.pacState.downloadStatus;
                    } else {
                        i3 = packageStates.pacState.downloadStatus;
                    }
                }
                return new int[]{i2, i3};
            }
        } catch (Throwable unused) {
        }
        return new int[]{0, 0};
    }

    public boolean isOfflineDataUnzipping() {
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        return downloadManager != null && downloadManager.isAnyTaskUnzipping();
    }

    public void pauseAllByNavi() {
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        if (downloadManager != null) {
            if (OfflineUtil.isNetworkConnected(AMapAppGlobal.getApplication()) && isHaveDownloadingCity()) {
                ToastHelper.showToast("进入导航，暂停离线数据下载");
            }
            downloadManager.pauseDownloadAllAuto();
        }
    }

    public void recoveryDownload() {
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        if (downloadManager != null) {
            downloadManager.resumeDownloadForWifi();
        }
    }

    public void saveErrors(Throwable th, String str) {
        OfflineSDK.getInstance().saveErrors(th, str);
    }

    public String getMapDataVersionForCurCity() {
        String str = "-1";
        CityInfo currentMapViewCity = CityHelper.getCurrentMapViewCity();
        if (currentMapViewCity != null) {
            IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
            if (downloadManager == null) {
                return "-1";
            }
            str = downloadManager.getDataVersion(currentMapViewCity.adcode, 0);
        }
        return str;
    }

    public String getRouteDataVersionForCurCity() {
        String str = "-1";
        CityInfo currentMapViewCity = CityHelper.getCurrentMapViewCity();
        if (currentMapViewCity != null) {
            IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
            if (downloadManager == null) {
                return "-1";
            }
            str = downloadManager.getDataVersion(currentMapViewCity.adcode, 1);
        }
        return str;
    }

    public String getCrossDataVersionForCurCity() {
        String str = "-1";
        CityInfo currentMapViewCity = CityHelper.getCurrentMapViewCity();
        if (currentMapViewCity != null) {
            IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
            if (downloadManager == null) {
                return "-1";
            }
            str = downloadManager.getDataVersion(currentMapViewCity.adcode, 3);
        }
        return str;
    }

    public String getPoiDataVersionForCurCity() {
        String str = "-1";
        CityInfo currentMapViewCity = CityHelper.getCurrentMapViewCity();
        if (currentMapViewCity != null) {
            IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
            if (downloadManager == null) {
                return "-1";
            }
            str = downloadManager.getDataVersion(currentMapViewCity.adcode, 2);
        }
        return str;
    }

    public String get3dCrossDataVersionForCurCity() {
        String str = "-1";
        CityInfo currentMapViewCity = CityHelper.getCurrentMapViewCity();
        if (currentMapViewCity != null) {
            IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
            if (downloadManager == null) {
                return "-1";
            }
            str = downloadManager.getDataVersion(currentMapViewCity.adcode, 4);
        }
        return str;
    }

    public String get3dCrossVerSion(long j) {
        try {
            if (DoNotUseTool.getMapView() != null) {
                ahh.b(String.valueOf(j));
                String str = null;
                if (TextUtils.isEmpty(str)) {
                    return "-1";
                }
                if (str.length() > 8) {
                    str = str.substring(0, 8);
                }
                return str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-1";
    }

    public boolean hasCross() {
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        return downloadManager != null && downloadManager.havingCross();
    }

    public boolean hasOfflineData() {
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        return downloadManager != null && downloadManager.havingMap();
    }

    public void requestGpu3dSupport(bid bid) {
        OfflineSDK.getInstance().requestGpu3dSupport(bid);
    }

    public List<String> getDownloadCityUpgradeList() {
        ArrayList arrayList = new ArrayList();
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        if (downloadManager == null) {
            return arrayList;
        }
        int[] downloadCityList = downloadManager.getDownloadCityList(DownloadListType.DOWNLOAD_LIST_TYPE_UPDATE);
        if (downloadCityList == null || downloadCityList.length == 0) {
            return arrayList;
        }
        ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
        if (cityManager == null) {
            return arrayList;
        }
        for (int cityById : downloadCityList) {
            CityInfo cityById2 = cityManager.getCityById(cityById);
            if (cityById2 != null) {
                arrayList.add(cityById2.name);
            }
        }
        return arrayList;
    }

    public void checkAutoApkUpdate(final AutoApkUpdateCallback autoApkUpdateCallback) {
        ApkUpdateHandler.get().checkUpdate(new UpdateCallback() {
            public final void onUpdate(ATApkPackageResponse aTApkPackageResponse, ATApkPackage aTApkPackage) {
                if (!(aTApkPackage == null || aTApkPackage.getMemo() == null || aTApkPackage.getMemo().size() <= 0)) {
                    ATApkMemo aTApkMemo = aTApkPackage.getMemo().get(0);
                    ATJsApkItem latestApkItem = ApkDownloadPersistence.get().getLatestApkItem();
                    if (latestApkItem == null) {
                        latestApkItem = new ATJsApkItem();
                    }
                    if (OfflineManagerImpl.this.hasNewApkVersion(aTApkMemo, latestApkItem) && autoApkUpdateCallback != null) {
                        autoApkUpdateCallback.onUpdate(aTApkMemo.getApp_name());
                        return;
                    }
                }
                if (autoApkUpdateCallback != null) {
                    autoApkUpdateCallback.onUpdate("");
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean hasNewApkVersion(ATApkMemo aTApkMemo, ATJsApkItem aTJsApkItem) {
        int safeParseInt = safeParseInt(aTApkMemo.getAppver());
        int safeParseInt2 = safeParseInt(aTJsApkItem.version);
        if (safeParseInt > safeParseInt2) {
            return true;
        }
        if (safeParseInt < safeParseInt2) {
            return false;
        }
        int safeParseInt3 = safeParseInt(safeSlice(aTApkMemo.getDiv(), 4));
        int safeParseInt4 = safeParseInt(safeSlice(aTJsApkItem.div, 4));
        if (safeParseInt3 > safeParseInt4) {
            return true;
        }
        return safeParseInt3 >= safeParseInt4 && safeParseInt(aTApkMemo.getBuild()) > safeParseInt(aTJsApkItem.build);
    }

    private String safeSlice(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (i >= str.length()) {
            return "";
        }
        return str.substring(i);
    }

    private int safeParseInt(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (Throwable unused) {
            return 0;
        }
    }

    public Map<String, String> getLocalAutoApkInfo() {
        ATJsApkItem latestApkItem = ApkDownloadPersistence.get().getLatestApkItem();
        if (latestApkItem == null) {
            latestApkItem = new ATJsApkItem();
        }
        HashMap hashMap = new HashMap();
        hashMap.put("build", TextUtils.isEmpty(latestApkItem.build) ? "0" : latestApkItem.build);
        hashMap.put(LocationParams.PARA_COMMON_DIV, TextUtils.isEmpty(latestApkItem.div) ? "0" : latestApkItem.div);
        hashMap.put("appver", TextUtils.isEmpty(latestApkItem.version) ? "0" : latestApkItem.version);
        return hashMap;
    }

    public boolean isHaveDownloadingCity() {
        int[] downloadingCityIds = JsNativeFacade.getInstance().getDownloadingCityIds();
        return downloadingCityIds != null && downloadingCityIds.length > 0;
    }

    public boolean has3dCross() {
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        return downloadManager != null && downloadManager.having3dCross();
    }

    public boolean isOfflineDataReady() {
        return OfflineNativeSdk.getInstance().isInit();
    }

    public void startSwithcStorage(bid bid, int i) {
        if (bid != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(AppLinkConstants.REQUESTCODE, i);
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("url", JsPath.SRORAGE_PATH);
                pageBundle.putLong("startTime", System.currentTimeMillis());
                pageBundle.putString("jsData", jSONObject.toString());
                bid.startPage(Ajx3Page.class, pageBundle);
            } catch (JSONException unused) {
            }
        }
    }

    public String getOfflineEngineVersion() {
        String offlineEngineVersion = OfflineNativeSdk.getInstance().getOfflineEngineVersion();
        return offlineEngineVersion != null ? offlineEngineVersion : "";
    }

    public String getOfflineDBFilePath() {
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        return downloadManager != null ? downloadManager.getOfflineDataBaseFile() : "";
    }

    public String getOfflineLogFilePath() {
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        return downloadManager != null ? downloadManager.getOfflineLogFile() : "";
    }

    public void setAutoDownloadInWifiSwitchState(boolean z) {
        OfflineNativeSdk.getInstance().notifyConfigChanged("WifiAutoUpdate", String.valueOf(z));
    }
}
