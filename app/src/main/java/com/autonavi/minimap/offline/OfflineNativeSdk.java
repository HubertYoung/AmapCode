package com.autonavi.minimap.offline;

import android.support.annotation.Nullable;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ackor.ackoroffline.IOfflineService;
import com.autonavi.minimap.offline.alc.ALCOfflineLog;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.offline.helper.NativeSdkHelper;
import com.autonavi.minimap.offline.init.OfflineDataInit;
import com.autonavi.minimap.offline.nativesupport.OfflineServiceImpl;
import com.autonavi.minimap.offline.notification.NotificationHelper;
import com.autonavi.minimap.offline.utils.OfflineSpUtil;
import com.autonavi.minimap.offlinesdk.CityManager;
import com.autonavi.minimap.offlinesdk.DownloadManager;
import com.autonavi.minimap.offlinesdk.ICityDownloadObserver;
import com.autonavi.minimap.offlinesdk.ICityListUpgradeObserver;
import com.autonavi.minimap.offlinesdk.ICityManager;
import com.autonavi.minimap.offlinesdk.IDataInitObserver;
import com.autonavi.minimap.offlinesdk.IDownloadManager;
import com.autonavi.minimap.offlinesdk.IOfflineDataErrorObserver;
import com.autonavi.minimap.offlinesdk.OfflineConfig;
import com.autonavi.minimap.offlinesdk.OfflineSDK;
import com.autonavi.minimap.offlinesdk.model.CityListNotifyInfo;
import com.autonavi.minimap.offlinesdk.model.DownloadListType;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;

public class OfflineNativeSdk implements IOfflineNativeSdk {
    private static final int CITY_LIST_UPDATE_SUCCESS = 1;
    private static volatile OfflineNativeSdk sInstance;
    /* access modifiers changed from: private */
    public boolean mAE8UpdateFlag = false;
    /* access modifiers changed from: private */
    public ICityDownloadObserver mCityDownloadObserver;
    private ICityManager mCityManager;
    private IDownloadManager mDownloadManager;
    /* access modifiers changed from: private */
    public volatile boolean mISInit = false;
    /* access modifiers changed from: private */
    public boolean mIsIniting = false;
    private OfflineConfig mOfflineConfig = new OfflineConfig();
    /* access modifiers changed from: private */
    public IOfflineDataErrorObserver mOfflineDataErrorObserver;
    /* access modifiers changed from: private */
    public OfflineSDK mOfflineSDK = new OfflineSDK();
    /* access modifiers changed from: private */
    public IOfflineService mOfflineService = new OfflineServiceImpl();
    /* access modifiers changed from: private */
    public ICityDownloadObserver mOtherDownloadObserver;
    /* access modifiers changed from: private */
    public final Object mRequestOfflineDataLock = new Object();
    /* access modifiers changed from: private */
    public ICityListUpgradeObserver mUpgradeObserver;

    private OfflineNativeSdk() {
        initOfflineConfig();
        this.mUpgradeObserver = new ICityListUpgradeObserver() {
            public final void onCityListUpdated() {
                OfflineSpUtil.setAe8RedNeedShowSp(true);
            }

            public final void onCityDataUpdated(int[] iArr, int[] iArr2) {
                if (iArr != null && iArr.length > 0) {
                    OfflineSpUtil.setAe8RedNeedShowSp(true);
                }
            }

            public final void onCityDataUpdateFinish(int i) {
                if (i == 1) {
                    OfflineSpUtil.recordOfflineDataUpdateDate();
                }
            }
        };
        this.mCityDownloadObserver = new ICityDownloadObserver() {
            volatile int a = -1;
            volatile long b = 0;

            public final void onStart(int i) {
                if (OfflineNativeSdk.this.mOtherDownloadObserver != null) {
                    OfflineNativeSdk.this.mOtherDownloadObserver.onStart(i);
                }
            }

            public final void onStatusChange(int i, int i2, int i3) {
                if (OfflineNativeSdk.this.mOtherDownloadObserver != null) {
                    OfflineNativeSdk.this.mOtherDownloadObserver.onStatusChange(i, i2, i3);
                }
            }

            public final void onCityStatusChange(int i, int i2) {
                if (OfflineNativeSdk.this.mOtherDownloadObserver != null) {
                    OfflineNativeSdk.this.mOtherDownloadObserver.onCityStatusChange(i, i2);
                }
            }

            public final void onAllStatusChange(int i, int i2, int i3, int i4) {
                if (OfflineNativeSdk.this.mOtherDownloadObserver != null) {
                    OfflineNativeSdk.this.mOtherDownloadObserver.onAllStatusChange(i, i2, i3, i4);
                }
            }

            public final void onCityListStatusChange(CityListNotifyInfo[] cityListNotifyInfoArr, CityListNotifyInfo[] cityListNotifyInfoArr2) {
                if (OfflineNativeSdk.this.mOtherDownloadObserver != null) {
                    OfflineNativeSdk.this.mOtherDownloadObserver.onCityListStatusChange(cityListNotifyInfoArr, cityListNotifyInfoArr2);
                }
            }

            public final void onError(int i, int i2, int i3) {
                if (OfflineNativeSdk.this.mOtherDownloadObserver != null) {
                    OfflineNativeSdk.this.mOtherDownloadObserver.onError(i, i2, i3);
                }
                long currentTimeMillis = System.currentTimeMillis() - this.b;
                if (this.a != i3 || currentTimeMillis > 1000) {
                    NativeSdkHelper.showErrorToast(i3);
                    this.b = System.currentTimeMillis();
                }
                this.a = i3;
                NativeSdkHelper.showErrorNotification(i3, i);
            }

            public final void onProgress(int i, int i2, long j, long j2) {
                if (OfflineNativeSdk.this.mOtherDownloadObserver != null) {
                    OfflineNativeSdk.this.mOtherDownloadObserver.onProgress(i, i2, j, j2);
                }
            }

            public final void onAllProgress(int i, long j, long j2, long j3, long j4, long j5, long j6) {
                if (OfflineNativeSdk.this.mOtherDownloadObserver != null) {
                    OfflineNativeSdk.this.mOtherDownloadObserver.onAllProgress(i, j, j2, j3, j4, j5, j6);
                }
            }

            public final void onCityProgress(int i, long j, long j2) {
                if (OfflineNativeSdk.this.mOtherDownloadObserver != null) {
                    OfflineNativeSdk.this.mOtherDownloadObserver.onCityProgress(i, j, j2);
                }
            }

            public final void onCityMergeProgress(int i, int i2) {
                if (OfflineNativeSdk.this.mOtherDownloadObserver != null) {
                    OfflineNativeSdk.this.mOtherDownloadObserver.onCityMergeProgress(i, i2);
                }
            }

            public final int onFinish(int i, int i2) {
                if (OfflineNativeSdk.this.mOtherDownloadObserver != null) {
                    OfflineNativeSdk.this.mOtherDownloadObserver.onFinish(i, i2);
                }
                if (i2 == 0) {
                    int[] downloadCityList = OfflineNativeSdk.this.mOfflineSDK.getDownloadManager().getDownloadCityList(DownloadListType.DOWNLOAD_LIST_TYPE_DOWNLOADING);
                    if (downloadCityList != null && downloadCityList.length == 0) {
                        NotificationHelper.getInstance().update(AMapAppGlobal.getApplication().getString(R.string.offline_map_curve), AMapAppGlobal.getApplication().getString(R.string.offline_download_complete));
                    }
                }
                return 0;
            }
        };
        this.mOfflineDataErrorObserver = new IOfflineDataErrorObserver() {
            public final boolean onDataEngineNotMachted(int i, int i2) {
                NativeSdkHelper.showOfflineDataVersionMismatchTip();
                return false;
            }

            public final boolean onDataOpenError(int i, int i2) {
                NativeSdkHelper.showOfflineDataErrorTips();
                return false;
            }

            public final boolean onDataParseError(int i, int i2) {
                NativeSdkHelper.showOfflineDataErrorTips();
                return false;
            }

            public final boolean onDataVersionNotMatched(int i, int i2) {
                NativeSdkHelper.showOfflineDataVersionMismatchTip();
                return false;
            }
        };
    }

    public static synchronized IOfflineNativeSdk getInstance() {
        OfflineNativeSdk offlineNativeSdk;
        synchronized (OfflineNativeSdk.class) {
            try {
                if (sInstance == null) {
                    sInstance = new OfflineNativeSdk();
                }
                offlineNativeSdk = sInstance;
            }
        }
        return offlineNativeSdk;
    }

    @Nullable
    public ICityManager getCityManager() {
        CityManager cityManager = this.mOfflineSDK.getCityManager();
        if (cityManager == null) {
            return null;
        }
        if (this.mCityManager == null) {
            this.mCityManager = new NativeCityManager(cityManager);
        }
        return this.mCityManager;
    }

    @Nullable
    public IDownloadManager getDownloadManager() {
        DownloadManager downloadManager = this.mOfflineSDK.getDownloadManager();
        if (downloadManager == null) {
            return null;
        }
        if (this.mDownloadManager == null) {
            this.mDownloadManager = new NativeDownloadManager(downloadManager);
        }
        return this.mDownloadManager;
    }

    public OfflineConfig getOfflineConfig() {
        return this.mOfflineConfig;
    }

    public synchronized boolean isInit() {
        return this.mISInit;
    }

    public void init() {
        final long currentTimeMillis = System.currentTimeMillis();
        OfflineDataInit.getInstance().initData();
        init(this.mOfflineConfig, new IDataInitObserver() {
            public final void onOfflineDestroy() {
            }

            public final void onDataInitComplete(int i) {
                OfflineNativeSdk.this.mIsIniting = false;
                synchronized (OfflineNativeSdk.this.mRequestOfflineDataLock) {
                    OfflineNativeSdk.this.mRequestOfflineDataLock.notifyAll();
                }
                OfflineNativeSdk.this.addInitTime(currentTimeMillis, System.currentTimeMillis());
                if (i == 0) {
                    OfflineNativeSdk.this.mISInit = true;
                    OfflineNativeSdk.this.mOfflineSDK.getDownloadManager().bindObserverForAllCities(OfflineNativeSdk.this.mCityDownloadObserver);
                    OfflineNativeSdk.this.mOfflineSDK.getCityManager().registerCityListUpgradeObserver(OfflineNativeSdk.this.mUpgradeObserver);
                    OfflineNativeSdk.this.mOfflineSDK.getDownloadManager().bindOfflineDataErrorObserver(OfflineNativeSdk.this.mOfflineDataErrorObserver);
                    if (OfflineNativeSdk.this.mAE8UpdateFlag || OfflineNativeSdk.this.mOfflineSDK.getDownloadManager().isUpdateBeforeCompileVersion()) {
                        OfflineNativeSdk.getInstance().notifyConfigChanged("WifiAutoUpdate", "true");
                        OfflineSpUtil.setAe8RedNeedShowSp(true);
                        OfflineSpUtil.setWifiAutoUpdateSp(true);
                        OfflineNativeSdk.this.mAE8UpdateFlag = false;
                    }
                    NativeSdkHelper.showOfflineDataMergeTips(OfflineNativeSdk.this.mOfflineSDK.getDownloadManager().isUpdateBeforeCompileVersion());
                    NativeSdkHelper.checkDownloadCity();
                    NativeSdkHelper.increaseTrafficRandom();
                }
            }
        });
        if (isInit()) {
            NativeSdkHelper.increaseTrafficRandom();
            NativeSdkHelper.checkDownloadCity();
        }
        checkWifiAutoUpdateState();
    }

    /* access modifiers changed from: private */
    public void addInitTime(long j, long j2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("offlineNativeSdk init time = ");
        stringBuffer.append(j);
        stringBuffer.append("\n");
        stringBuffer.append("offlineNativeSdk init end time = ");
        stringBuffer.append(j2);
        stringBuffer.append("\n");
        ALCOfflineLog.p3("P0012", ALCTtsConstant.EVENT_ID_TTS_INIT_ERROR, stringBuffer.toString());
    }

    public void destroy() {
        synchronized (this.mRequestOfflineDataLock) {
            this.mRequestOfflineDataLock.notifyAll();
        }
        if (isInit()) {
            notifyPause();
            if (this.mOfflineSDK.getDownloadManager() != null) {
                this.mOfflineSDK.getDownloadManager().pauseDownloadAllAuto();
            }
            if (this.mOfflineSDK.getCityManager() != null) {
                this.mOfflineSDK.getCityManager().registerCityListUpgradeObserver(null);
            }
        }
    }

    private void notifyPause() {
        DownloadManager downloadManager = this.mOfflineSDK.getDownloadManager();
        if (downloadManager != null) {
            int[] downloadCityList = downloadManager.getDownloadCityList(DownloadListType.DOWNLOAD_LIST_TYPE_DOWNLOADING);
            if (downloadCityList != null && downloadCityList.length != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(AMapAppGlobal.getApplication().getString(R.string.offline_download_pause));
                sb.append("（");
                sb.append(AMapAppGlobal.getApplication().getString(R.string.offline_download_uncomplete));
                sb.append(downloadCityList.length);
                sb.append("个）");
                NotificationHelper.getInstance().update(AMapAppGlobal.getApplication().getString(R.string.offline_map_curve), sb.toString());
            }
        }
    }

    public void notifyConfigChanged(String str, String str2) {
        if (getInstance().isInit()) {
            this.mOfflineSDK.notifyConfigChanged(str, str2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0042, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void init(@android.support.annotation.NonNull final com.autonavi.minimap.offlinesdk.OfflineConfig r5, @android.support.annotation.NonNull final com.autonavi.minimap.offlinesdk.IDataInitObserver r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            boolean r0 = r4.mISInit     // Catch:{ all -> 0x0043 }
            if (r0 != 0) goto L_0x0041
            boolean r0 = r4.mIsIniting     // Catch:{ all -> 0x0043 }
            if (r0 == 0) goto L_0x000a
            goto L_0x0041
        L_0x000a:
            r0 = 1
            r4.mIsIniting = r0     // Catch:{ all -> 0x0043 }
            java.lang.Thread r0 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0043 }
            long r0 = r0.getId()     // Catch:{ all -> 0x0043 }
            android.os.Looper r2 = android.os.Looper.getMainLooper()     // Catch:{ all -> 0x0043 }
            java.lang.Thread r2 = r2.getThread()     // Catch:{ all -> 0x0043 }
            long r2 = r2.getId()     // Catch:{ all -> 0x0043 }
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 != 0) goto L_0x002e
            com.autonavi.minimap.offlinesdk.OfflineSDK r0 = r4.mOfflineSDK     // Catch:{ all -> 0x0043 }
            com.autonavi.minimap.ackor.ackoroffline.IOfflineService r1 = r4.mOfflineService     // Catch:{ all -> 0x0043 }
            r0.init(r5, r6, r1)     // Catch:{ all -> 0x0043 }
            monitor-exit(r4)
            return
        L_0x002e:
            android.os.Handler r0 = new android.os.Handler     // Catch:{ all -> 0x0043 }
            android.os.Looper r1 = android.os.Looper.getMainLooper()     // Catch:{ all -> 0x0043 }
            r0.<init>(r1)     // Catch:{ all -> 0x0043 }
            com.autonavi.minimap.offline.OfflineNativeSdk$5 r1 = new com.autonavi.minimap.offline.OfflineNativeSdk$5     // Catch:{ all -> 0x0043 }
            r1.<init>(r5, r6)     // Catch:{ all -> 0x0043 }
            r0.post(r1)     // Catch:{ all -> 0x0043 }
            monitor-exit(r4)
            return
        L_0x0041:
            monitor-exit(r4)
            return
        L_0x0043:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.OfflineNativeSdk.init(com.autonavi.minimap.offlinesdk.OfflineConfig, com.autonavi.minimap.offlinesdk.IDataInitObserver):void");
    }

    public void bindObserverForAllCities(ICityDownloadObserver iCityDownloadObserver) {
        this.mOtherDownloadObserver = iCityDownloadObserver;
    }

    private void initOfflineConfig() {
        this.mOfflineConfig.OfflineAOSDomain = ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.OFFLINE_AOS_URL_KEY);
        this.mOfflineConfig.OfflineDataPath = PathManager.a().a(DirType.OFFLINE);
        this.mOfflineConfig.OfflinePath = AMapAppGlobal.getApplication().getFilesDir().getAbsolutePath().replace(AutoJsonUtils.JSON_FILES, "databases/");
        this.mOfflineConfig.WifiAutoUpdate = OfflineSpUtil.getWifiAutoUpdateSp();
    }

    @Deprecated
    public void waitOfflineDataReady() {
        while (!this.mISInit) {
            try {
                synchronized (this.mRequestOfflineDataLock) {
                    this.mRequestOfflineDataLock.wait();
                }
            } catch (InterruptedException unused) {
            }
        }
    }

    public String getOfflineEngineVersion() {
        if (!isInit()) {
            return "n/a";
        }
        return this.mOfflineSDK.getEngineVersion();
    }

    public void setAE8UpdateFlag(boolean z) {
        this.mAE8UpdateFlag = z;
    }

    private void checkWifiAutoUpdateState() {
        if (getInstance().isInit() && OfflineSpUtil.getWifiAutoUpdateSp()) {
            notifyConfigChanged("WifiAutoUpdate", "false");
            notifyConfigChanged("WifiAutoUpdate", "true");
        }
    }
}
