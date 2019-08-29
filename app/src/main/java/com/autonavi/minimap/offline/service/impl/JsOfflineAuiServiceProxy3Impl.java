package com.autonavi.minimap.offline.service.impl;

import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;
import android.text.TextUtils;
import com.alipay.mobile.beehive.cityselect.ui.SelectCityActivity;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.offline.IOfflineNativeSdk;
import com.autonavi.minimap.offline.JsRequest.AbstractUseCallback;
import com.autonavi.minimap.offline.JsRequest.AllCityInProvinceRequest;
import com.autonavi.minimap.offline.JsRequest.AlongWayAllCityRequest;
import com.autonavi.minimap.offline.JsRequest.AlongWayAllCityRequest.Response;
import com.autonavi.minimap.offline.JsRequest.AlongWayRouteCityRequest;
import com.autonavi.minimap.offline.JsRequest.AlongWayRouteCityRequest.QueryCityParam;
import com.autonavi.minimap.offline.JsRequest.AlongWayRouteCityRequest.QueryResponse;
import com.autonavi.minimap.offline.JsRequest.OfflineAllCityRequest;
import com.autonavi.minimap.offline.JsRequest.OfflineAllCityRequest.CityInfoParam;
import com.autonavi.minimap.offline.JsRequest.OfflineAllCityRequest.CityInfoResponse;
import com.autonavi.minimap.offline.JsRequest.OfflineAllDownloadCityRequest;
import com.autonavi.minimap.offline.JsRequest.OfflineAllDownloadCityRequest.AllDownloadCityParam;
import com.autonavi.minimap.offline.JsRequest.OfflineAllDownloadCityRequest.AllDownloadCityResponse;
import com.autonavi.minimap.offline.JsRequest.OfflineAllHotCityRequest;
import com.autonavi.minimap.offline.JsRequest.OfflineAllHotCityRequest.AllHotCityResponse;
import com.autonavi.minimap.offline.JsRequest.OfflineAllHotCityRequest.Request;
import com.autonavi.minimap.offline.JsRequest.OfflineDeviceSpaceInfoRequest;
import com.autonavi.minimap.offline.JsRequest.OfflineSdCardListRequest;
import com.autonavi.minimap.offline.OfflineNativeSdk;
import com.autonavi.minimap.offline.OfflineSDK;
import com.autonavi.minimap.offline.WorkThreadManager;
import com.autonavi.minimap.offline.WorkThreadManager.OfflineTask;
import com.autonavi.minimap.offline.dataaccess.UseCase.UseCaseCallback;
import com.autonavi.minimap.offline.dataaccess.UseCaseHandler;
import com.autonavi.minimap.offline.model.Province;
import com.autonavi.minimap.offline.model.UpdateInfo;
import com.autonavi.minimap.offline.service.IJsOfflineAuiServiceProxy3;
import com.autonavi.minimap.offline.storage.StoragePresenter;
import com.autonavi.minimap.offline.uiutils.UiUtils;
import com.autonavi.minimap.offline.util.JsConvertUtils;
import com.autonavi.minimap.offline.util.JsNativeFacade;
import com.autonavi.minimap.offline.utils.OfflineSpUtil;
import com.autonavi.minimap.offline.utils.OfflineUtil;
import com.autonavi.minimap.offlinesdk.ICityDownloadObserver;
import com.autonavi.minimap.offlinesdk.ICityManager;
import com.autonavi.minimap.offlinesdk.IDownloadManager;
import com.autonavi.minimap.offlinesdk.OfflineConfig;
import com.autonavi.minimap.offlinesdk.model.CityInfo;
import com.autonavi.minimap.offlinesdk.model.CityListNotifyInfo;
import com.autonavi.minimap.offlinesdk.model.DownloadInfo;
import com.autonavi.minimap.offlinesdk.model.PackageStates;
import com.autonavi.minimap.offlinesdk.model.ProvinceInfo;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressFBWarnings({"RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT"})
public class JsOfflineAuiServiceProxy3Impl implements IJsOfflineAuiServiceProxy3 {
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
    public static final int DOWNLOAD_ERROR_GET_URL = 7;
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
    /* access modifiers changed from: private */
    public static StoragePresenter mStoragePresenter;
    /* access modifiers changed from: private */
    public Handler h = new Handler(Looper.getMainLooper());
    private a mDownloadListener = new a(this, 0);
    /* access modifiers changed from: private */
    public JsFunctionCallback mJsDownloadCallback;

    class a implements ICityDownloadObserver {
        private long b;
        private long c;
        private int d;

        public final void onCityProgress(int i, long j, long j2) {
        }

        public final void onCityStatusChange(int i, int i2) {
        }

        public final void onProgress(int i, int i2, long j, long j2) {
        }

        public final void onStatusChange(int i, int i2, int i3) {
        }

        private a() {
            this.c = -1;
            this.d = -1;
        }

        /* synthetic */ a(JsOfflineAuiServiceProxy3Impl jsOfflineAuiServiceProxy3Impl, byte b2) {
            this();
        }

        private void a(UpdateInfo updateInfo) {
            JSONObject jSONObject = new JSONObject();
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(updateInfo.getCode());
                jSONObject.put("code", sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(updateInfo.getCityId());
                jSONObject.put("cityID", sb2.toString());
                StringBuilder sb3 = new StringBuilder();
                sb3.append(updateInfo.getCityStatus());
                jSONObject.put("cityStatus", sb3.toString());
                StringBuilder sb4 = new StringBuilder();
                sb4.append(updateInfo.getMapItemStatus());
                jSONObject.put("mapItemStatus", sb4.toString());
                StringBuilder sb5 = new StringBuilder();
                sb5.append(updateInfo.getNaviItemStatus());
                jSONObject.put("naviItemStatus", sb5.toString());
                StringBuilder sb6 = new StringBuilder();
                sb6.append(updateInfo.getProgress());
                jSONObject.put("progress", sb6.toString());
                if (JsOfflineAuiServiceProxy3Impl.this.mJsDownloadCallback != null) {
                    JsOfflineAuiServiceProxy3Impl.this.mJsDownloadCallback.callback(jSONObject.toString());
                }
            } catch (JSONException unused) {
            }
        }

        private void a(String str) {
            if (JsOfflineAuiServiceProxy3Impl.this.mJsDownloadCallback != null) {
                JsOfflineAuiServiceProxy3Impl.this.mJsDownloadCallback.callback(str);
            }
        }

        private void b(UpdateInfo updateInfo) {
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
                jSONArray.put(jSONObject);
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("code", 1);
                jSONObject2.put(SelectCityActivity.EXTRA_PARAM_CITY_LIST, jSONArray);
                a(jSONObject2.toString());
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
            StringBuilder sb = new StringBuilder("onAllStatusChange:");
            sb.append(i);
            sb.append("---");
            sb.append(i2);
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
                        jSONObject2.put("cityID", JsConvertUtils.checkCityId(String.valueOf(cityListNotifyInfoArr[i].CityId)));
                        jSONArray.put(jSONObject2);
                    }
                    if (cityListNotifyInfoArr2 != null) {
                        for (int i2 = 0; i2 < cityListNotifyInfoArr2.length; i2++) {
                            JSONObject jSONObject3 = new JSONObject();
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append(cityListNotifyInfoArr2[i2].RouteStatus);
                            jSONObject3.put("naviItemStatus", sb4.toString());
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(cityListNotifyInfoArr2[i2].MapStatus);
                            jSONObject3.put("mapItemStatus", sb5.toString());
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(cityListNotifyInfoArr2[i2].CityStatus);
                            jSONObject3.put("cityStatus", sb6.toString());
                            StringBuilder sb7 = new StringBuilder();
                            sb7.append(cityListNotifyInfoArr2[i2].CityId);
                            jSONObject3.put("cityID", sb7.toString());
                            jSONArray.put(jSONObject3);
                        }
                    }
                    jSONObject.put("code", 1);
                    jSONObject.put(SelectCityActivity.EXTRA_PARAM_CITY_LIST, jSONArray);
                    a(jSONObject.toString());
                } catch (JSONException unused) {
                }
                a();
            }
        }

        private void a() {
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
                    if (JsOfflineAuiServiceProxy3Impl.this.mJsDownloadCallback != null) {
                        JsOfflineAuiServiceProxy3Impl.this.mJsDownloadCallback.callback(jSONObject.toString());
                    }
                } catch (JSONException unused) {
                }
            }
        }

        public final void onError(int i, int i2, int i3) {
            if (this.d != i3 || System.currentTimeMillis() - this.c >= 1000) {
                this.d = i3;
                this.c = System.currentTimeMillis();
                if (i3 == 4) {
                    ToastHelper.showToast("存储空间不足导致下载失败，请清理空间后重试");
                } else if (i3 == 8) {
                    ToastHelper.showToast("存储空间不足解压所选数据，请及时清理空间");
                }
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
            updateInfo.setCode(1);
            b(updateInfo);
            return 0;
        }

        public final void onAllProgress(int i, long j, long j2, long j3, long j4, long j5, long j6) {
            if (!(System.currentTimeMillis() - this.b <= 500) || j5 == j6) {
                UpdateInfo updateInfo = new UpdateInfo();
                updateInfo.setCode(2);
                updateInfo.setCityId(i);
                updateInfo.setCityStatus(2);
                updateInfo.setProgress((int) ((j5 * 100) / j6));
                a(updateInfo);
                this.b = System.currentTimeMillis();
            }
        }
    }

    public void doStorageChange(String str) {
    }

    public String value2json(long j) {
        return "";
    }

    public void getAllCityInfo(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback == null) {
            AMapLog.w(TAG, "Js callback is null");
        } else {
            UseCaseHandler.getInstance().execute(new OfflineAllCityRequest(), new CityInfoParam(), new AbstractUseCallback<CityInfoResponse, Integer>() {
                public final /* synthetic */ void onSuccess(Object obj) {
                    jsFunctionCallback.callback(((CityInfoResponse) obj).getAllCityInfoStr());
                }
            });
        }
    }

    public void getHotCityInfo(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback == null) {
            AMapLog.w(TAG, "Js callback is null");
        } else {
            UseCaseHandler.getInstance().execute(new OfflineAllHotCityRequest(), new Request(), new AbstractUseCallback<AllHotCityResponse, Integer>() {
                public final /* synthetic */ void onSuccess(Object obj) {
                    jsFunctionCallback.callback(((AllHotCityResponse) obj).getAllHotCityInfo());
                }
            });
        }
    }

    public void getAllAlongWayCity(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback == null) {
            AMapLog.w(TAG, "Js callback is null");
        } else {
            UseCaseHandler.getInstance().execute(new AlongWayAllCityRequest(), new UseCaseCallback<Response, String>() {
                public final void onCancel() {
                }

                public final /* synthetic */ void onError(Object obj) {
                    AMapLog.w(JsOfflineAuiServiceProxy3Impl.TAG, "error : ".concat(String.valueOf((String) obj)));
                }

                public final /* synthetic */ void onSuccess(Object obj) {
                    jsFunctionCallback.callback(((Response) obj).getAlongWayCityJoStr());
                    AMapLog.w(JsOfflineAuiServiceProxy3Impl.TAG, "onSuccess");
                }
            });
        }
    }

    public void getCitiesInProvinces(String str, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback == null) {
            AMapLog.w(TAG, "Js callback is null");
        } else {
            UseCaseHandler.getInstance().execute(new AllCityInProvinceRequest(str), new UseCaseCallback<AllCityInProvinceRequest.Response, String>() {
                public final void onCancel() {
                }

                public final /* synthetic */ void onError(Object obj) {
                    AMapLog.w(JsOfflineAuiServiceProxy3Impl.TAG, "error : ".concat(String.valueOf((String) obj)));
                }

                public final /* synthetic */ void onSuccess(Object obj) {
                    jsFunctionCallback.callback(((AllCityInProvinceRequest.Response) obj).getAlongWayCityJoStr());
                    AMapLog.w(JsOfflineAuiServiceProxy3Impl.TAG, "onSuccess");
                }
            });
        }
    }

    public void requestAlongWayCities(int i, int i2, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback == null) {
            AMapLog.w(TAG, "Js callback is null");
        } else {
            UseCaseHandler.getInstance().execute(new AlongWayRouteCityRequest(), new QueryCityParam(i, i2), new UseCaseCallback<QueryResponse, String>() {
                public final void onCancel() {
                }

                public final /* synthetic */ void onError(Object obj) {
                    String str = (String) obj;
                    jsFunctionCallback.callback(str);
                    AMapLog.w(JsOfflineAuiServiceProxy3Impl.TAG, "error : ".concat(String.valueOf(str)));
                }

                public final /* synthetic */ void onSuccess(Object obj) {
                    jsFunctionCallback.callback(((QueryResponse) obj).getAlongRouteCities());
                    AMapLog.w(JsOfflineAuiServiceProxy3Impl.TAG, "onSuccess");
                }
            });
        }
    }

    public void getSdCardList(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback == null) {
            AMapLog.w(TAG, "Js callback is null");
        } else {
            UseCaseHandler.getInstance().execute(new OfflineSdCardListRequest(), new UseCaseCallback<OfflineSdCardListRequest.Response, Integer>() {
                public final void onCancel() {
                }

                public final /* bridge */ /* synthetic */ void onError(Object obj) {
                }

                public final /* synthetic */ void onSuccess(Object obj) {
                    jsFunctionCallback.callback(((OfflineSdCardListRequest.Response) obj).getSdCardListJoStr());
                }
            });
        }
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

    public void getSavedTraffic(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            WorkThreadManager.start(new OfflineTask<Object>() {
                public final Object doBackground() throws Exception {
                    final float trafficSavedSp = OfflineSpUtil.getTrafficSavedSp();
                    JsOfflineAuiServiceProxy3Impl.this.h.post(new Runnable() {
                        public final void run() {
                            jsFunctionCallback.callback(Long.valueOf((long) (trafficSavedSp * 1024.0f * 1024.0f)));
                        }
                    });
                    return null;
                }
            });
        }
    }

    public void getOfflineMapSwitchState(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            WorkThreadManager.start(new OfflineTask<Object>() {
                public final Object doBackground() throws Exception {
                    final String str = OfflineSpUtil.getOfflineMapPrioriSp() ? "1" : "0";
                    JsOfflineAuiServiceProxy3Impl.this.h.post(new Runnable() {
                        public final void run() {
                            jsFunctionCallback.callback(str);
                        }
                    });
                    return null;
                }
            });
        }
    }

    public void setOfflineMapSwitchState(String str) {
        OfflineSpUtil.setShowOfflineTipTimes(3);
        OfflineSpUtil.setOfflineMapPrioriSp("1".equals(str));
    }

    public void getOfflineNaviSwitchState(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            WorkThreadManager.start(new OfflineTask<Object>() {
                public final Object doBackground() throws Exception {
                    final String str = !OfflineSpUtil.getNaviConfigOnline() ? "1" : "0";
                    JsOfflineAuiServiceProxy3Impl.this.h.post(new Runnable() {
                        public final void run() {
                            jsFunctionCallback.callback(str);
                        }
                    });
                    return null;
                }
            });
        }
    }

    public void setOfflineNaviSwitchState(String str) {
        OfflineSpUtil.setNaviConfigOnline(!"1".equals(str));
    }

    public void getAutoDownloadInWifiSwitchState(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            WorkThreadManager.start(new OfflineTask<Object>() {
                public final Object doBackground() throws Exception {
                    final String str = OfflineSpUtil.getWifiAutoUpdateSp() ? "1" : "0";
                    JsOfflineAuiServiceProxy3Impl.this.h.post(new Runnable() {
                        public final void run() {
                            jsFunctionCallback.callback(str);
                        }
                    });
                    return null;
                }
            });
        }
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
            iErrorReportStarter.startOfflineMapError(AMapPageFramework.getPageContext());
        }
    }

    public void gotoFAQPage() {
        gotoCommonProblem();
    }

    public void getCurrentCityDownloadInfo(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            WorkThreadManager.start(new OfflineTask<Object>() {
                public final Object doBackground() throws Exception {
                    final String str;
                    JSONObject jSONObject = new JSONObject();
                    int currentCityAdcode = OfflineUtil.getCurrentCityAdcode();
                    ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
                    CityInfo cityByAdcode = cityManager != null ? cityManager.getCityByAdcode(currentCityAdcode) : null;
                    if (cityByAdcode == null) {
                        str = "";
                    } else {
                        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
                        PackageStates[] cityDataStatus = downloadManager != null ? downloadManager.getCityDataStatus(cityByAdcode.cityId) : null;
                        int i = 0;
                        try {
                            jSONObject.put("cityID", cityByAdcode.cityId);
                            jSONObject.put("cityStatus", 0);
                            jSONObject.put("mapStatus", 0);
                            jSONObject.put("routeStatus", 0);
                        } catch (JSONException unused) {
                        }
                        if (cityDataStatus != null && cityDataStatus.length > 0) {
                            while (i < cityDataStatus.length) {
                                try {
                                    PackageStates packageStates = cityDataStatus[i];
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
                        str = jSONObject.toString();
                    }
                    JsOfflineAuiServiceProxy3Impl.this.h.post(new Runnable() {
                        public final void run() {
                            jsFunctionCallback.callback(str);
                        }
                    });
                    return null;
                }
            });
        }
    }

    public void hasNewFeaturesWithPageID(final String str, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null && !TextUtils.isEmpty(str)) {
            WorkThreadManager.start(new OfflineTask<Object>() {
                public final Object doBackground() throws Exception {
                    final boolean z = "1".equals(str) ? OfflineSpUtil.getAe8RedNeedShowSp() : "2".equals(str) ? OfflineSpUtil.getOfflineMOreRedSp() : "3".equals(str) ? OfflineSpUtil.getFrequentlyQuestionsRedSp() : false;
                    JsOfflineAuiServiceProxy3Impl.this.h.post(new Runnable() {
                        public final void run() {
                            jsFunctionCallback.callback(Boolean.valueOf(z));
                        }
                    });
                    return null;
                }
            });
        }
    }

    public void readNewFeaturesWithPageID(String str) {
        if ("1".equals(str)) {
            OfflineSpUtil.setAe8RedNeedShowSp(false);
        } else if ("2".equals(str)) {
            OfflineSpUtil.setOfflineMoreRedSP(false);
        } else {
            if ("3".equals(str)) {
                OfflineSpUtil.setFrequentlyQuestionsRedSP(false);
            }
        }
    }

    public void isDownloaded(final String str, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            WorkThreadManager.start(new OfflineTask<Object>() {
                public final Object doBackground() throws Exception {
                    final boolean z = false;
                    if (!TextUtils.isEmpty(str)) {
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
                    }
                    JsOfflineAuiServiceProxy3Impl.this.h.post(new Runnable() {
                        public final void run() {
                            jsFunctionCallback.callback(Boolean.valueOf(z));
                        }
                    });
                    return null;
                }
            });
        }
    }

    public void backupConfig() {
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        if (downloadManager != null) {
            downloadManager.backupConfig();
        }
    }

    public void checkInit(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            WorkThreadManager.start(new OfflineTask<Object>() {
                public final Object doBackground() throws Exception {
                    final boolean isInit = OfflineNativeSdk.getInstance().isInit();
                    JsOfflineAuiServiceProxy3Impl.this.h.post(new Runnable() {
                        public final void run() {
                            jsFunctionCallback.callback(Boolean.valueOf(isInit));
                        }
                    });
                    return null;
                }
            });
        }
    }

    public void waitInit(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            WorkThreadManager.start(new OfflineTask<Object>() {
                public final Object doBackground() throws Exception {
                    OfflineNativeSdk.getInstance().waitOfflineDataReady();
                    JsOfflineAuiServiceProxy3Impl.this.h.post(new Runnable() {
                        public final void run() {
                            jsFunctionCallback.callback(new Object[0]);
                        }
                    });
                    return null;
                }
            });
        }
    }

    public void getCityDownloadStatusWithAdcode(final String str, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            WorkThreadManager.start(new OfflineTask<Object>() {
                public final Object doBackground() throws Exception {
                    final String str = "";
                    if (TextUtils.isEmpty(str)) {
                        str = "";
                    } else {
                        ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
                        CityInfo cityByAdcode = cityManager != null ? cityManager.getCityByAdcode(Integer.parseInt(str)) : null;
                        PackageStates[] packageStatesByCityid = cityByAdcode != null ? JsNativeFacade.getInstance().getPackageStatesByCityid(cityByAdcode.cityId) : null;
                        if (cityByAdcode == null || packageStatesByCityid == null) {
                            str = "";
                        } else {
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
                                str = jSONObject.toString();
                            } catch (JSONException unused) {
                            }
                        }
                    }
                    JsOfflineAuiServiceProxy3Impl.this.h.post(new Runnable() {
                        public final void run() {
                            jsFunctionCallback.callback(str);
                        }
                    });
                    return null;
                }
            });
        }
    }

    public void setResult() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.setResult(ResultType.OK, null);
        }
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

    public void getAllDownloadCityList(String str, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback == null) {
            AMapLog.w(TAG, "Js callback is null");
        } else {
            UseCaseHandler.getInstance().execute(new OfflineAllDownloadCityRequest(), new AllDownloadCityParam(str), new UseCaseCallback<AllDownloadCityResponse, Integer>() {
                public final void onCancel() {
                }

                public final /* synthetic */ void onSuccess(Object obj) {
                    jsFunctionCallback.callback(((AllDownloadCityResponse) obj).getDownloadCityInfoJOStr());
                }

                public final /* synthetic */ void onError(Object obj) {
                    AMapLog.d(JsOfflineAuiServiceProxy3Impl.TAG, "error");
                    jsFunctionCallback.callback("");
                }
            });
        }
    }

    public void performDownloadCmd(int i, String str) {
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        if (downloadManager != null) {
            switch (i) {
                case 0:
                    downloadManager.startDownloadAll();
                    return;
                case 1:
                    downloadManager.pauseDownloadAll();
                    return;
                case 2:
                    downloadManager.startUpdateAll();
                    return;
                case 4:
                    List<DownloadInfo> downloadCities = getDownloadCities(str);
                    if (downloadCities != null && !downloadCities.isEmpty()) {
                        downloadManager.startDownloadList(downloadCities);
                        return;
                    }
                    return;
                case 5:
                    downloadManager.pauseDownloadByCityIdArray(getCityIds(str));
                    return;
                case 6:
                    downloadManager.cancelDownloadByCityIdArray(getCityIds(str));
                    return;
                case 8:
                    List<DownloadInfo> downloadCities2 = getDownloadCities(str);
                    if (downloadCities2 != null && downloadCities2.size() != 0) {
                        int size = downloadCities2.size();
                        int[] iArr = new int[size];
                        for (int i2 = 0; i2 < size; i2++) {
                            iArr[i2] = downloadCities2.get(i2).cityid;
                        }
                        downloadManager.resumeDownloadByCityIdArray(iArr);
                        return;
                    }
                    return;
                case 9:
                    DownloadInfo downloadCity = getDownloadCity(str);
                    if (downloadCity != null) {
                        downloadManager.updateDownload(downloadCity);
                        return;
                    }
                    break;
                case 10:
                    DownloadInfo downloadCity2 = getDownloadCity(str);
                    if (downloadCity2 != null) {
                        downloadManager.cancelDownloadByCityId(downloadCity2.cityid);
                        return;
                    }
                    break;
                case 11:
                    DownloadInfo downloadCity3 = getDownloadCity(str);
                    if (downloadCity3 != null) {
                        downloadManager.resumeDownloadByCityId(downloadCity3.cityid);
                        return;
                    }
                    break;
                case 12:
                    DownloadInfo downloadCity4 = getDownloadCity(str);
                    if (downloadCity4 != null) {
                        downloadManager.deleteOfflineDataByCityId(downloadCity4.cityid);
                        return;
                    }
                    break;
                case 13:
                    List<DownloadInfo> downloadCities3 = getDownloadCities(str);
                    if (downloadCities3 != null && downloadCities3.size() != 0) {
                        downloadManager.startDownloadAndCheckNetworkList(downloadCities3);
                        break;
                    } else {
                        return;
                    }
                    break;
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

    private DownloadInfo getDownloadCity(String str) {
        CityInfo cityInfo = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        DownloadInfo downloadInfo = new DownloadInfo();
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("adCode");
            String optString2 = jSONObject.has("types") ? jSONObject.optString("types") : "2";
            Integer valueOf = Integer.valueOf(Integer.parseInt(optString));
            ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
            if (cityManager != null) {
                cityInfo = cityManager.getCityByAdcode(valueOf.intValue());
            }
            downloadInfo.cityid = cityInfo != null ? cityInfo.cityId : valueOf.intValue();
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
        try {
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = (JSONObject) jSONArray.get(i);
                String optString = jSONObject.optString("adCode");
                String optString2 = jSONObject.has("types") ? jSONObject.optString("types") : "2";
                Integer valueOf = Integer.valueOf(Integer.parseInt(optString));
                CityInfo cityInfo = null;
                ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
                if (cityManager != null) {
                    cityInfo = cityManager.getCityByAdcode(valueOf.intValue());
                }
                DownloadInfo downloadInfo = new DownloadInfo();
                downloadInfo.cityid = cityInfo != null ? cityInfo.cityId : valueOf.intValue();
                downloadInfo.types = Integer.parseInt(optString2);
                arrayList.add(downloadInfo);
            }
        } catch (JSONException unused) {
        }
        return arrayList;
    }

    public void getDeviceSpaceInfo(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            UseCaseHandler.getInstance().execute(new OfflineDeviceSpaceInfoRequest(), new UseCaseCallback<OfflineDeviceSpaceInfoRequest.Response, Integer>() {
                public final void onCancel() {
                }

                public final /* synthetic */ void onError(Object obj) {
                    jsFunctionCallback.callback("");
                    AMapLog.w(JsOfflineAuiServiceProxy3Impl.TAG, "error : ".concat(String.valueOf((Integer) obj)));
                }

                public final /* synthetic */ void onSuccess(Object obj) {
                    jsFunctionCallback.callback(((OfflineDeviceSpaceInfoRequest.Response) obj).getDeviceSpaceInfo());
                    AMapLog.w(JsOfflineAuiServiceProxy3Impl.TAG, "onSuccess");
                }
            });
        }
    }

    public void getFreeDeviceSpace(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            WorkThreadManager.start(new OfflineTask<Object>() {
                public final Object doBackground() throws Exception {
                    long j;
                    long j2;
                    OfflineConfig offlineConfig = OfflineNativeSdk.getInstance().getOfflineConfig();
                    if (offlineConfig == null) {
                        return null;
                    }
                    StatFs statFs = new StatFs(offlineConfig.OfflinePath);
                    if (VERSION.SDK_INT >= 18) {
                        j2 = statFs.getBlockSizeLong();
                        j = statFs.getAvailableBlocksLong();
                    } else {
                        j2 = (long) statFs.getBlockSize();
                        j = (long) statFs.getAvailableBlocks();
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(j * j2);
                    final String sb2 = sb.toString();
                    JsOfflineAuiServiceProxy3Impl.this.h.post(new Runnable() {
                        public final void run() {
                            jsFunctionCallback.callback(sb2);
                        }
                    });
                    return null;
                }
            });
        }
    }

    public void bindObserverForAllCities(JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            this.mJsDownloadCallback = jsFunctionCallback;
            OfflineNativeSdk.getInstance().bindObserverForAllCities(this.mDownloadListener);
        }
    }

    public void switchOfflineData(final String str, final String str2, JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            if (mStoragePresenter == null) {
                mStoragePresenter = new StoragePresenter();
            }
            mStoragePresenter.setJsCallback(jsFunctionCallback);
            this.h.post(new Runnable() {
                public final void run() {
                    JsOfflineAuiServiceProxy3Impl.mStoragePresenter.clickStorageItemView(str, str2);
                }
            });
        }
    }

    public void switchOfflineDataCheck(final String str, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            WorkThreadManager.start(new OfflineTask<Object>() {
                public final Object doBackground() throws Exception {
                    IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
                    final int switchDirCheck = downloadManager != null ? downloadManager.switchDirCheck(str) : -1;
                    JsOfflineAuiServiceProxy3Impl.this.h.post(new Runnable() {
                        public final void run() {
                            jsFunctionCallback.callback(Integer.valueOf(switchDirCheck));
                        }
                    });
                    return null;
                }
            });
        }
    }

    public void isDownloadingOfflineData(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            WorkThreadManager.start(new OfflineTask<Object>() {
                public final Object doBackground() throws Exception {
                    final int downloadingDataType = OfflineSDK.getInstance().getDownloadingDataType();
                    JsOfflineAuiServiceProxy3Impl.this.h.post(new Runnable() {
                        public final void run() {
                            JsFunctionCallback jsFunctionCallback = jsFunctionCallback;
                            boolean z = true;
                            Object[] objArr = new Object[1];
                            if (downloadingDataType == 0) {
                                z = false;
                            }
                            objArr[0] = Boolean.valueOf(z);
                            jsFunctionCallback.callback(objArr);
                        }
                    });
                    return null;
                }
            });
        }
    }

    private <T> String toJsonString(T t) {
        try {
            return JsonUtil.toString(t);
        } catch (Exception unused) {
            return "";
        }
    }

    private <T> Object toJson(T t) {
        try {
            return JsonUtil.toJson(t);
        } catch (Exception unused) {
            return "";
        }
    }

    public boolean autoCarHasConnection() {
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) defpackage.esb.a.a.a(IAutoRemoteController.class);
        return iAutoRemoteController != null && iAutoRemoteController.hasBoundToAuto();
    }
}
