package com.autonavi.minimap.offline.init;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.offline.OfflineNativeSdk;
import com.autonavi.minimap.offline.helper.NativeSdkHelper;
import com.autonavi.minimap.offline.model.NetworkRequestHelper;
import com.autonavi.minimap.offline.model.compat.CompatHelper;
import com.autonavi.minimap.offline.util.JsNativeFacade;
import com.autonavi.minimap.offline.utils.OfflineLog;
import com.autonavi.minimap.offline.utils.OfflineUtil;
import com.autonavi.minimap.offlinesdk.IDownloadManager;
import com.autonavi.minimap.offlinesdk.model.PackageStates;
import java.util.concurrent.locks.ReentrantLock;

public final class OfflineDataInit {
    private static final String TAG = "OfflineDataInit";
    static final ReentrantLock mLock = new ReentrantLock();
    private static OfflineDataInit sINSTANCE;
    private volatile boolean isPauseByNavi = false;
    private boolean isUpgradeAe8TTSVersion = false;
    private boolean isUpgradeAe8Version = false;
    private boolean mIsOfflineDataReady = false;
    private boolean mIsTTSDataReady = false;
    private String mOfflineVersion;
    private Object mPauseByNaviLock = new Object();
    private Object mRequestOfflineDataLock = new Object();
    private Object mRequestTTSDataLock = new Object();

    private OfflineDataInit() {
    }

    public static OfflineDataInit getInstance() {
        mLock.lock();
        try {
            if (sINSTANCE == null) {
                sINSTANCE = new OfflineDataInit();
            }
            return sINSTANCE;
        } finally {
            try {
                mLock.unlock();
            } catch (Exception unused) {
            }
        }
    }

    public final void setMapOnlineVersion(String str) {
        this.mOfflineVersion = str;
    }

    public final void putOffLatestVerByAppInit(Context context, String str, String str2, String str3, String str4, String str5) {
        Editor edit = context.getSharedPreferences("AppInit_OffVersion", 0).edit();
        edit.putString("offlineMapVersion", str);
        edit.putString("offlineNaviDataVersion", str2);
        edit.putString("dialectVoiceVersion", str3);
        edit.putString("roadEnlargeVersion", str4);
        edit.putString("offMode2Ver", str5);
        edit.apply();
    }

    public final String getOffLatestVerByAppInit(Context context, String str) {
        return context.getSharedPreferences("AppInit_OffVersion", 0).getString(str, "");
    }

    public final void resetPauseByNavi() {
        this.isPauseByNavi = false;
    }

    public final void initData() {
        getInstance().setOfflineDataFlag(false);
        OfflineLog.d(TAG, "initData start");
        NativeSdkHelper.deleteUselessFile();
        CompatHelper.createInstance().compat();
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager != null) {
            iVoicePackageManager.initialization();
        }
        setOfflineDataReady();
    }

    public final void requestGpu3dSupport(bid bid) {
        if (OfflineUtil.isMobileConnected(OfflineUtil.getContext()) || OfflineUtil.isWifiConnected(OfflineUtil.getContext())) {
            bty mapView = DoNotUseTool.getMapView();
            if (mapView != null) {
                NetworkRequestHelper.requestGpu3dSupport(mapView.ak(), null);
            }
        }
    }

    public final synchronized boolean isOfflineDataReady() {
        return this.mIsOfflineDataReady;
    }

    public final synchronized void setOfflineDataFlag(boolean z) {
        this.mIsOfflineDataReady = z;
    }

    public final synchronized boolean isTTSDataReady() {
        return this.mIsTTSDataReady;
    }

    public final void waitOfflineDataReady() {
        while (!this.mIsOfflineDataReady) {
            try {
                OfflineLog.d(TAG, "waitOfflineDataReady start");
                synchronized (this.mRequestOfflineDataLock) {
                    this.mRequestOfflineDataLock.wait();
                }
                OfflineLog.d(TAG, "waitOfflineDataReady end");
            } catch (InterruptedException unused) {
            }
        }
    }

    public final void waitRevertForPauseByNavi() {
        while (this.isPauseByNavi) {
            try {
                OfflineLog.d(TAG, "waitRevertForPauseByNavi start");
                synchronized (this.mPauseByNaviLock) {
                    this.mPauseByNaviLock.wait();
                }
                OfflineLog.d(TAG, "waitRevertForPauseByNavi end");
            } catch (InterruptedException unused) {
            }
        }
    }

    private void setOfflineDataReady() {
        try {
            OfflineLog.d(TAG, "setOfflineDataReady");
            this.mIsOfflineDataReady = true;
            synchronized (this.mRequestOfflineDataLock) {
                this.mRequestOfflineDataLock.notifyAll();
            }
        } catch (Exception unused) {
        }
    }

    private void setTTSDataReady() {
        try {
            OfflineLog.d(TAG, "setTTSDataReady");
            this.mIsTTSDataReady = true;
            synchronized (this.mRequestTTSDataLock) {
                this.mRequestTTSDataLock.notifyAll();
            }
        } catch (Exception unused) {
        }
    }

    public final boolean checkIsNaviDataDownloaded(int i) {
        PackageStates[] packageStatesByAdcode = JsNativeFacade.getInstance().getPackageStatesByAdcode(i);
        if (packageStatesByAdcode == null || packageStatesByAdcode.length == 0) {
            return false;
        }
        int i2 = 0;
        while (i2 < packageStatesByAdcode.length) {
            PackageStates packageStates = packageStatesByAdcode[i2];
            if (packageStates.packageType != 1) {
                i2++;
            } else if (packageStates.pacState.downloadStatus == 7 || packageStates.pacState.updateFlag != 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public final boolean checkIsCityDataDownloaded(int i) {
        PackageStates[] packageStatesByAdcode = JsNativeFacade.getInstance().getPackageStatesByAdcode(i);
        if (packageStatesByAdcode == null || packageStatesByAdcode.length == 0 || packageStatesByAdcode.length <= 0) {
            return false;
        }
        PackageStates packageStates = packageStatesByAdcode[0];
        if (packageStates.pacState.downloadStatus == 7 || packageStates.pacState.updateFlag != 0) {
            return true;
        }
        return false;
    }

    public final boolean isUpgradeAe8Version() {
        return this.isUpgradeAe8Version;
    }

    public final boolean showOfflineAE8UpdateTip() {
        if (!this.isUpgradeAe8Version) {
            return false;
        }
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        if (downloadManager == null) {
            return false;
        }
        int[] allDownloadCityList = downloadManager.getAllDownloadCityList();
        boolean z = allDownloadCityList != null && allDownloadCityList.length > 0;
        if (!this.isUpgradeAe8Version || !z) {
            return false;
        }
        return true;
    }

    public final void setIsUpgradeAe8Version(boolean z) {
        this.isUpgradeAe8Version = z;
    }

    public final boolean isUpgradeAe8TTSVersion() {
        return this.isUpgradeAe8TTSVersion;
    }

    public final void setIsUpgradeAe8TTSVersion(boolean z) {
        this.isUpgradeAe8TTSVersion = z;
    }
}
