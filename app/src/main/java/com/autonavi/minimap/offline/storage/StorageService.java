package com.autonavi.minimap.offline.storage;

import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.offline.OfflineNativeSdk;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.autonavi.minimap.offlinesdk.IDownloadManager;
import com.autonavi.minimap.offlinesdk.IOfflineSwitchDirPathObserver;
import com.autonavi.minimap.offlinesdk.OfflineConfig;
import java.io.File;
import java.lang.ref.WeakReference;

public class StorageService {
    private static a mStorageChangeObserver;
    /* access modifiers changed from: private */
    public IStorageCallback mCallback;

    public interface IStorageCallback {
        public static final int ERROR_STORAGE_DOWNLOAD = 5;
        public static final int ERROR_STORAGE_IO_EXCEPTION = 4;
        public static final int ERROR_STORAGE_NO_ENGINE = 6;
        public static final int ERROR_STORAGE_NO_READ_AUTORY = 2;
        public static final int ERROR_STORAGE_NO_SPACE = 1;
        public static final int ERROR_STORAGE_NO_WRITE_AUTORY = 3;
        public static final int ERROR_SWITCH_CANCEL = 8;
        public static final int ERROR_SWITCH_TARGEPATH_EQUALS_SRCPATH = 7;
        public static final int STATUS_CHECK_STORAGE_ACCESS = 1;
        public static final int STATUS_CHECK_STORAGE_SPACE = 2;
        public static final int STATUS_COPY_DATA = 3;
        public static final int STATUS_DELETE_DATA = 4;
        public static final int STATUS_FAIL = 6;
        public static final int STATUS_FINISH = 5;

        void onError(int i, String str, String str2);

        void onProgress(int i);

        void onStatusChanged(int i);
    }

    static class a implements IOfflineSwitchDirPathObserver {
        WeakReference<StorageService> a;
        String b;
        String c;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }

        public final void onSwitchDirPathFinished() {
            StorageService storageService = (StorageService) this.a.get();
            if (storageService != null && storageService.mCallback != null) {
                OfflineConfig offlineConfig = OfflineNativeSdk.getInstance().getOfflineConfig();
                if (offlineConfig != null) {
                    StringBuilder sb = new StringBuilder("onSwitchDirPathFinished--");
                    sb.append(this.b);
                    AMapLog.d("doStorageChange", sb.toString());
                    PathManager a2 = PathManager.a();
                    DirType dirType = DirType.OFFLINE;
                    String str = this.b;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(File.separator);
                    sb2.append("autonavi");
                    sb2.append(DirType.OFFLINE.getPath());
                    a2.a(dirType, str.replace(sb2.toString(), ""));
                    FilePathHelper.destroy();
                    offlineConfig.OfflineDataPath = this.b;
                    storageService.mCallback.onStatusChanged(5);
                }
            }
        }

        public final void onSwitchDirPathFailed(int i) {
            StorageService storageService = (StorageService) this.a.get();
            if (storageService != null && storageService.mCallback != null) {
                AMapLog.d("doStorageChange", "onSwitchDirPathFailed--".concat(String.valueOf(i)));
                storageService.mCallback.onError(i, this.c, this.b);
            }
        }

        public final void onSwitchDirPathProcessing(int i) {
            StorageService storageService = (StorageService) this.a.get();
            if (storageService != null && storageService.mCallback != null) {
                storageService.mCallback.onProgress(i);
            }
        }
    }

    public StorageService(IStorageCallback iStorageCallback) {
        this.mCallback = iStorageCallback;
    }

    public void doStorageChange(String str, String str2) {
        if (mStorageChangeObserver == null) {
            mStorageChangeObserver = new a(0);
        }
        mStorageChangeObserver.a = new WeakReference<>(this);
        mStorageChangeObserver.c = str;
        mStorageChangeObserver.b = str2;
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        if (downloadManager != null) {
            downloadManager.switchDir2Target(str2, mStorageChangeObserver);
        }
    }
}
