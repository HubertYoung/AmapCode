package com.autonavi.minimap.offline;

import android.content.Context;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.autonavi.minimap.offline.init.OfflineDataInit;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.autonavi.minimap.offline.model.compat.CompatHelper;
import com.autonavi.minimap.offline.util.JsNativeFacade;
import com.autonavi.minimap.offline.utils.OfflineLog;
import com.autonavi.minimap.offline.utils.OfflineSpUtil;
import com.autonavi.minimap.offline.utils.OfflineUtil;
import com.autonavi.minimap.offlinesdk.model.PackageStates;
import java.io.File;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public final class OfflineSDK {
    public static final int DOWNLOAD_STATE_0_UNDOWNLOAD = 0;
    public static final int DOWNLOAD_STATE_10_NET_ERROR = 10;
    public static final int DOWNLOAD_STATE_11_MD5_ERROR = 11;
    public static final int DOWNLOAD_STATE_12_DOWNLOAD_COMPLETED_BY_PP = 12;
    public static final int DOWNLOAD_STATE_1_LOADING = 1;
    public static final int DOWNLOAD_STATE_2_WAITING = 2;
    public static final int DOWNLOAD_STATE_3_PAUSE = 3;
    public static final int DOWNLOAD_STATE_4_DOWNLOAD_COMPLETED = 4;
    public static final int DOWNLOAD_STATE_5_DATA_ERROR = 5;
    public static final int DOWNLOAD_STATE_64_UPGRADE = 64;
    public static final int DOWNLOAD_STATE_6_UNZIPPING_FINISH = 6;
    public static final int DOWNLOAD_STATE_7_UNZIPPING = 7;
    public static final int DOWNLOAD_STATE_8_UNZIPPING_ERROR = 8;
    public static final int DOWNLOAD_STATE_9_INSTALL_COMPLETED = 9;
    public static final int GANGAO_ADCODE = 2;
    public static final int JCB_ADCODE = 0;
    public static final int MUNICIPALITY_ADCODE = 1;
    private static final String TAG = "OfflineSDK";
    private static final ReentrantLock mLock = new ReentrantLock();
    /* access modifiers changed from: private */
    public static final Object mLockObject = new Object();
    private static volatile OfflineSDK sINSTANCE;
    private ExecutorService mErrorsExcutor = Executors.newFixedThreadPool(3);
    private HashSet mHasShownCitySet = new HashSet();

    private OfflineSDK() {
    }

    public static OfflineSDK getInstance() {
        mLock.lock();
        try {
            if (sINSTANCE == null) {
                sINSTANCE = new OfflineSDK();
            }
            return sINSTANCE;
        } finally {
            try {
                mLock.unlock();
            } catch (Exception unused) {
            }
        }
    }

    public static void destroy() {
        OfflineLog.d(TAG, "destroy start");
        OfflineDataInit.getInstance().resetPauseByNavi();
        FilePathHelper.destroy();
        OfflineDataInit.getInstance().setOfflineDataFlag(false);
    }

    public static String getCurrentOfflineDbPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(OfflineUtil.getDatabasesDir());
        sb.append("OfflineDbV6.db");
        return sb.toString();
    }

    public static String getCurrentSDCardOfflineDbPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(PathManager.a().b());
        sb.append(File.separator);
        sb.append(CompatHelper.DB_OFFLINE_SDCARD_PATH_DBV6);
        return sb.toString();
    }

    public final void init() {
        OfflineDataInit.getInstance().initData();
    }

    public final void requestGpu3dSupport(bid bid) {
        OfflineDataInit.getInstance().requestGpu3dSupport(bid);
    }

    public final void setNeedUpgradeSp() {
        OfflineSpUtil.setWifiAutoUpdateSp(true);
    }

    public final void waitOfflineDataReady() {
        OfflineDataInit.getInstance().waitOfflineDataReady();
    }

    public final void setIsUpgradeAe8Version(boolean z) {
        OfflineDataInit.getInstance().setIsUpgradeAe8Version(z);
    }

    public final boolean showOfflineAE8UpdateTip() {
        return OfflineDataInit.getInstance().showOfflineAE8UpdateTip();
    }

    public final void putOffLatestVerByAppInit(Context context, String str, String str2, String str3, String str4, String str5) {
        OfflineDataInit.getInstance().putOffLatestVerByAppInit(context, str, str2, str3, str4, str5);
    }

    public final String getOffLatestVerByAppInit(Context context, String str) {
        if (context != null) {
            return OfflineDataInit.getInstance().getOffLatestVerByAppInit(context, str);
        }
        return null;
    }

    public final boolean checkIsNaviDataDownloaded(int i) {
        return OfflineDataInit.getInstance().checkIsNaviDataDownloaded(i);
    }

    public final boolean checkDownloadCityStatus(int i) {
        if (isShowCityInMessageBox(i)) {
            return true;
        }
        PackageStates[] packageStatesByAdcode = JsNativeFacade.getInstance().getPackageStatesByAdcode(i);
        if (packageStatesByAdcode == null || packageStatesByAdcode.length == 0) {
            return false;
        }
        for (PackageStates packageStates : packageStatesByAdcode) {
            if (packageStates.packageType == 0 && packageStates.pacState.downloadStatus != 0) {
                return true;
            }
        }
        return false;
    }

    public final boolean contrastDownloadStatus(int i, int i2, int i3) {
        PackageStates[] packageStatesByAdcode = JsNativeFacade.getInstance().getPackageStatesByAdcode(i);
        if (packageStatesByAdcode == null || packageStatesByAdcode.length == 0) {
            return false;
        }
        for (PackageStates packageStates : packageStatesByAdcode) {
            if (packageStates.packageType == i2 && packageStates.pacState.downloadStatus == i3) {
                return true;
            }
        }
        return false;
    }

    public final int getDownloadingDataType() {
        int[] downloadingCityIds = JsNativeFacade.getInstance().getDownloadingCityIds();
        int i = (downloadingCityIds == null || downloadingCityIds.length <= 0) ? 0 : 1;
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager == null || !iVoicePackageManager.isVoiceInDownloading()) {
            return i;
        }
        return 2;
    }

    public final boolean checkDownloadCity(int i) {
        return contrastDownloadStatus(i, 0, 7);
    }

    public final boolean isShowCityInMessageBox(int i) {
        if (this.mHasShownCitySet.contains(Integer.valueOf(i))) {
            return true;
        }
        this.mHasShownCitySet.add(Integer.valueOf(i));
        return false;
    }

    public final boolean checkCurrentDownloadCityStatus() {
        int mapViewAdcode = OfflineUtil.getMapViewAdcode();
        if (this.mHasShownCitySet.contains(Integer.valueOf(mapViewAdcode))) {
            return true;
        }
        if (mapViewAdcode > 0) {
            return checkDownloadCityStatus(mapViewAdcode);
        }
        return false;
    }

    public final void saveErrors(final Throwable th, String str) {
        try {
            this.mErrorsExcutor.execute(new Runnable() {
                /* JADX WARNING: Unknown top exception splitter block from list: {B:33:0x0081=Splitter:B:33:0x0081, B:19:0x0069=Splitter:B:19:0x0069} */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final void run() {
                    /*
                        r7 = this;
                        java.lang.Object r0 = com.autonavi.minimap.offline.OfflineSDK.mLockObject
                        monitor-enter(r0)
                        java.lang.Throwable r1 = r2     // Catch:{ all -> 0x0093 }
                        if (r1 == 0) goto L_0x0091
                        r1 = 0
                        java.io.File r2 = new java.io.File     // Catch:{ Throwable -> 0x007f, all -> 0x007b }
                        com.amap.bundle.blutils.PathManager r3 = com.amap.bundle.blutils.PathManager.a()     // Catch:{ Throwable -> 0x007f, all -> 0x007b }
                        java.lang.String r3 = r3.b()     // Catch:{ Throwable -> 0x007f, all -> 0x007b }
                        java.lang.String r4 = ".offline_error"
                        r2.<init>(r3, r4)     // Catch:{ Throwable -> 0x007f, all -> 0x007b }
                        long r3 = r2.length()     // Catch:{ Throwable -> 0x007f, all -> 0x007b }
                        r5 = 3145728(0x300000, double:1.554196E-317)
                        int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                        if (r3 <= 0) goto L_0x002d
                        boolean r3 = r2.delete()     // Catch:{ Throwable -> 0x007f, all -> 0x007b }
                        if (r3 == 0) goto L_0x002d
                        r2.createNewFile()     // Catch:{ Throwable -> 0x007f, all -> 0x007b }
                    L_0x002d:
                        java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x007f, all -> 0x007b }
                        r4 = 1
                        r3.<init>(r2, r4)     // Catch:{ Throwable -> 0x007f, all -> 0x007b }
                        java.io.StringWriter r2 = new java.io.StringWriter     // Catch:{ Throwable -> 0x0077, all -> 0x0074 }
                        r2.<init>()     // Catch:{ Throwable -> 0x0077, all -> 0x0074 }
                        java.io.PrintWriter r4 = new java.io.PrintWriter     // Catch:{ Throwable -> 0x0077, all -> 0x0074 }
                        r4.<init>(r2)     // Catch:{ Throwable -> 0x0077, all -> 0x0074 }
                        java.lang.Throwable r1 = r2     // Catch:{ Throwable -> 0x0072, all -> 0x0070 }
                        r1.printStackTrace(r4)     // Catch:{ Throwable -> 0x0072, all -> 0x0070 }
                        java.text.SimpleDateFormat r1 = new java.text.SimpleDateFormat     // Catch:{ Throwable -> 0x0072, all -> 0x0070 }
                        java.lang.String r2 = "yyyy年MM月dd日 HH:mm:ss "
                        r1.<init>(r2)     // Catch:{ Throwable -> 0x0072, all -> 0x0070 }
                        java.util.Date r1 = new java.util.Date     // Catch:{ Throwable -> 0x0072, all -> 0x0070 }
                        long r5 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0072, all -> 0x0070 }
                        r1.<init>(r5)     // Catch:{ Throwable -> 0x0072, all -> 0x0070 }
                        java.lang.StringBuffer r1 = new java.lang.StringBuffer     // Catch:{ Throwable -> 0x0072, all -> 0x0070 }
                        r1.<init>()     // Catch:{ Throwable -> 0x0072, all -> 0x0070 }
                        java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0072, all -> 0x0070 }
                        java.nio.charset.Charset r2 = java.nio.charset.Charset.defaultCharset()     // Catch:{ Throwable -> 0x0072, all -> 0x0070 }
                        if (r1 == 0) goto L_0x0069
                        byte[] r1 = r1.getBytes(r2)     // Catch:{ Throwable -> 0x0072, all -> 0x0070 }
                        r3.write(r1)     // Catch:{ Throwable -> 0x0072, all -> 0x0070 }
                    L_0x0069:
                        defpackage.ahe.a(r3)     // Catch:{ all -> 0x0093 }
                    L_0x006c:
                        defpackage.ahe.a(r4)     // Catch:{ all -> 0x0093 }
                        goto L_0x0091
                    L_0x0070:
                        r2 = move-exception
                        goto L_0x008a
                    L_0x0072:
                        r2 = move-exception
                        goto L_0x0079
                    L_0x0074:
                        r2 = move-exception
                        r4 = r1
                        goto L_0x008a
                    L_0x0077:
                        r2 = move-exception
                        r4 = r1
                    L_0x0079:
                        r1 = r3
                        goto L_0x0081
                    L_0x007b:
                        r2 = move-exception
                        r3 = r1
                        r4 = r3
                        goto L_0x008a
                    L_0x007f:
                        r2 = move-exception
                        r4 = r1
                    L_0x0081:
                        r2.printStackTrace()     // Catch:{ all -> 0x0088 }
                        defpackage.ahe.a(r1)     // Catch:{ all -> 0x0093 }
                        goto L_0x006c
                    L_0x0088:
                        r2 = move-exception
                        r3 = r1
                    L_0x008a:
                        defpackage.ahe.a(r3)     // Catch:{ all -> 0x0093 }
                        defpackage.ahe.a(r4)     // Catch:{ all -> 0x0093 }
                        throw r2     // Catch:{ all -> 0x0093 }
                    L_0x0091:
                        monitor-exit(r0)     // Catch:{ all -> 0x0093 }
                        return
                    L_0x0093:
                        r1 = move-exception
                        monitor-exit(r0)     // Catch:{ all -> 0x0093 }
                        throw r1
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.OfflineSDK.AnonymousClass1.run():void");
                }
            });
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
    }
}
