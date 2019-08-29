package com.autonavi.minimap.offline.helper;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.SdCardInfo;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.Builder;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.NodeDialogFragmentOnClickListener;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.offline.OfflineNativeSdk;
import com.autonavi.minimap.offline.OfflineSDK;
import com.autonavi.minimap.offline.WorkThreadManager;
import com.autonavi.minimap.offline.WorkThreadManager.OfflineTask;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.autonavi.minimap.offline.model.compat.CompatHelper;
import com.autonavi.minimap.offline.notification.NotificationHelper;
import com.autonavi.minimap.offline.utils.CityHelper;
import com.autonavi.minimap.offline.utils.OfflineSpUtil;
import com.autonavi.minimap.offline.utils.OfflineUtil;
import com.autonavi.minimap.offlinesdk.ICityManager;
import com.autonavi.minimap.offlinesdk.IDownloadManager;
import com.autonavi.minimap.offlinesdk.model.CityInfo;
import com.autonavi.minimap.offlinesdk.model.DownloadListType;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class NativeSdkHelper {
    private static final String db_name_main_line = "ackor_offline.db";

    public static void checkDownloadCity() {
        if (isNeedUpgradeOfflineData()) {
            ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
            if (cityManager != null) {
                cityManager.checkUpdateStatus();
            }
        }
    }

    private static boolean isNeedUpgradeOfflineData() {
        return (OfflineUtil.isMobileConnected(OfflineUtil.getContext()) || OfflineUtil.isWifiConnected(OfflineUtil.getContext())) && !OfflineSpUtil.isAlreadyUpdateOfflineDataToday();
    }

    public static void deleteUselessFile() {
        WorkThreadManager.start(new OfflineTask<String>() {
            public final /* synthetic */ Object doBackground() throws Exception {
                ArrayList<SdCardInfo> sDCardInfoList = FileUtil.getSDCardInfoList(OfflineUtil.getContext().getApplicationContext(), false);
                if (sDCardInfoList != null && !sDCardInfoList.isEmpty()) {
                    Iterator<SdCardInfo> it = sDCardInfoList.iterator();
                    while (it.hasNext()) {
                        SdCardInfo next = it.next();
                        if (next.b != null) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(next.b);
                            sb.append(CompatHelper.DB_OFFLINE_SDCARD_PATH_DBACKOR);
                            File file = new File(sb.toString());
                            if (file.exists() && file.isFile()) {
                                FilePathHelper.deleteFileInSafely(file);
                            }
                        }
                    }
                }
                NativeSdkHelper.deleteAe8Data();
                NativeSdkHelper.deleteV5Data();
                FilePathHelper.deleteFolder(FilePathHelper.getInstance().getCompileBeta1DataDir(), true);
                return "";
            }
        });
    }

    /* access modifiers changed from: private */
    public static void deleteV5Data() {
        FilePathHelper.deleteFolder(FilePathHelper.getInstance().getV5MapZipDir(), true);
        FilePathHelper.deleteFolder(FilePathHelper.getInstance().getV5MapFileDir(), true);
        FilePathHelper.deleteFolder(FilePathHelper.getInstance().getV5PoiDir(), true);
        FilePathHelper.deleteFolder(FilePathHelper.getInstance().getV5RoadEnglargeDir(), true);
        FilePathHelper.deleteFolder(FilePathHelper.getInstance().getV5RouteFileDir(), true);
    }

    /* access modifiers changed from: private */
    public static void deleteAe8Data() {
        FilePathHelper.deleteFolder(FilePathHelper.getInstance().getPoiFileDir(), true);
        FilePathHelper.deleteFolder(FilePathHelper.getInstance().getMapFileDir(), true);
        FilePathHelper.deleteFolder(FilePathHelper.getInstance().getRouteFileDir(), true);
        FilePathHelper.deleteFolder(FilePathHelper.getInstance().get3dCrossFileDir(), true);
        FilePathHelper.deleteFolder(FilePathHelper.getInstance().getCrossFileDir(), true);
        FilePathHelper.deleteFolder(FilePathHelper.getInstance().getTempDownloadDir(), true);
        FilePathHelper.deleteFolder(FilePathHelper.getInstance().getTempUnzipDir(), true);
    }

    public static void showOfflineDataMergeTips(boolean z) {
        if (OfflineSDK.getInstance().showOfflineAE8UpdateTip() || z) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public final void run() {
                    if (AMapPageUtil.isHomePage()) {
                        if (OfflineUtil.isWifiConnected(AMapAppGlobal.getApplication())) {
                            ToastHelper.showToast("离线地图全新升级！当前为WI-FI，正在更新");
                            return;
                        }
                        Builder builder = new Builder(AMapAppGlobal.getApplication());
                        builder.setTitle((CharSequence) AMapAppGlobal.getApplication().getApplicationContext().getString(R.string.offline_upgrade_dialog_title));
                        builder.setMessage((CharSequence) AMapAppGlobal.getApplication().getApplicationContext().getString(R.string.offline_upgrade_dialog_message));
                        builder.setPositiveButton((CharSequence) AMapAppGlobal.getApplication().getApplicationContext().getString(R.string.offline_upgrade_dialog_button), (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                            public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                            }
                        });
                        AMapPageUtil.startAlertDialogPage(builder);
                        OfflineSDK.getInstance().setNeedUpgradeSp();
                        OfflineSDK.getInstance().setIsUpgradeAe8Version(false);
                    }
                }
            });
        }
    }

    public static void showOfflineDataMergeTips() {
        showOfflineDataMergeTips(false);
    }

    @SuppressFBWarnings({"DMI_BIGDECIMAL_CONSTRUCTED_FROM_DOUBLE"})
    public static void increaseTrafficRandom() {
        OfflineUtil.runOnWorkThread(new Runnable() {
            public final void run() {
                if (CityHelper.hasCityDownloaded()) {
                    if (!OfflineSpUtil.isTodayEnterApp()) {
                        OfflineSpUtil.setTodaySavedTraffic(0.0f);
                    }
                    OfflineSpUtil.recordUpdateDownloadListDate();
                    float trafficSavedSp = OfflineSpUtil.getTrafficSavedSp();
                    float nextInt = ((float) (new Random().nextInt(470) + 30)) / 1000.0f;
                    float todaySavedTraffic = OfflineSpUtil.getTodaySavedTraffic() + nextInt;
                    float f = nextInt + trafficSavedSp;
                    int compareTo = new BigDecimal((double) todaySavedTraffic).compareTo(new BigDecimal(7.2d));
                    if (compareTo == -1 || compareTo == 0) {
                        OfflineSpUtil.setTrafficSavedSp(f);
                        OfflineSpUtil.setTodaySavedTraffic(todaySavedTraffic);
                    }
                }
            }
        });
    }

    public static void cleanIncreaseTrafficRandom() {
        OfflineSpUtil.setTrafficSavedSp(0.0f);
        OfflineSpUtil.setTodaySavedTraffic(0.0f);
    }

    public static void showOfflineDataErrorTips() {
        String str;
        Context appContext = AMapPageUtil.getAppContext();
        if (appContext != null) {
            if (aaw.c(appContext)) {
                str = appContext.getResources().getString(R.string.engine_offline_data_error_network);
            } else {
                str = appContext.getResources().getString(R.string.engine_offline_data_error_no_network);
            }
            ToastHelper.showToast(str);
        }
    }

    public static void showErrorToast(int i) {
        String str = i != 1 ? i != 4 ? i != 8 ? i != 10 ? "" : "离线数据下载失败，请检查读写权限并重启设备后重试" : "存储空间不足解压所选数据，请及时清理空间" : "存储空间不足导致下载失败，请清理空间后重试" : "网络异常，离线数据下载失败，请稍后再试";
        if (!TextUtils.isEmpty(str)) {
            ToastHelper.showToast(str);
        }
    }

    public static void showErrorNotification(int i, int i2) {
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
        if (downloadManager != null && cityManager != null) {
            if (i == 4) {
                int[] downloadCityList = downloadManager.getDownloadCityList(DownloadListType.DOWNLOAD_LIST_TYPE_DOWNLOADING);
                if (!(downloadCityList == null || downloadCityList.length == 0)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(AMapAppGlobal.getApplication().getString(R.string.offline_download_error_pause));
                    NotificationHelper.getInstance().update(AMapAppGlobal.getApplication().getString(R.string.offline_map_curve), sb.toString());
                }
            } else if (i == 2 || i == 3 || i == 5 || i == 6) {
                CityInfo cityById = cityManager.getCityById(i2);
                if (cityById != null) {
                    StringBuilder sb2 = new StringBuilder();
                    if (TextUtils.isEmpty(cityById.name)) {
                        sb2.append(cityById.name);
                        sb2.append(AMapAppGlobal.getApplication().getString(R.string.offline_download_error));
                        NotificationHelper.getInstance().update(AMapAppGlobal.getApplication().getString(R.string.offline_map_curve), sb2.toString());
                    }
                }
            } else if (i == 8) {
                int[] downloadCityList2 = downloadManager.getDownloadCityList(DownloadListType.DOWNLOAD_LIST_TYPE_DOWNLOADING);
                if (!(downloadCityList2 == null || downloadCityList2.length == 0)) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(AMapAppGlobal.getApplication().getString(R.string.offline_download_error_unzip_no_space));
                    NotificationHelper.getInstance().update(AMapAppGlobal.getApplication().getString(R.string.offline_map_curve), sb3.toString());
                }
            } else {
                int[] downloadCityList3 = downloadManager.getDownloadCityList(DownloadListType.DOWNLOAD_LIST_TYPE_DOWNLOADING);
                if (downloadCityList3 != null && downloadCityList3.length != 0) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(AMapAppGlobal.getApplication().getString(R.string.offline_download_pause));
                    sb4.append("（");
                    sb4.append(AMapAppGlobal.getApplication().getString(R.string.offline_download_uncomplete));
                    sb4.append(downloadCityList3.length);
                    sb4.append("个）");
                    NotificationHelper.getInstance().update(AMapAppGlobal.getApplication().getString(R.string.offline_map_curve), sb4.toString());
                }
            }
        }
    }

    public static void showOfflineDataVersionMismatchTip() {
        String str;
        Context appContext = AMapPageUtil.getAppContext();
        if (appContext != null) {
            if (aaw.c(appContext)) {
                str = appContext.getResources().getString(R.string.engine_offline_data_version_error_network);
            } else {
                str = appContext.getResources().getString(R.string.engine_offline_data_version_error_no_network);
            }
            ToastHelper.showToast(str);
        }
    }
}
