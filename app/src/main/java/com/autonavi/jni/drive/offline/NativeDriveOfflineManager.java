package com.autonavi.jni.drive.offline;

import android.content.Context;
import android.net.NetworkInfo;
import com.autonavi.jni.drive.offline.BehaviorService.OnBehaviorLogInterface;
import com.autonavi.jni.drive.offline.HttpRequestQueue.OnCreateHttpRequestListener;
import com.autonavi.jni.drive.offline.Logger.OnLogCallBackListener;

public class NativeDriveOfflineManager {
    private static final String TAG = "OfflineManager";
    private PlatformAdapter mAdapter;
    private long mPtr;

    public native void nativeChangeAvailableCityList(int[] iArr);

    public native void nativeChangeCurrentCity(int i);

    public native void nativeChangePernanentCity(int i);

    public native void nativeDestroy();

    public native void nativeInit(String str, String str2, String str3, int i, int i2, int[] iArr);

    public native void nativePause();

    public native void nativeResume();

    public native void nativeStart();

    public NativeDriveOfflineManager(Context context) {
        this.mAdapter = new PlatformAdapter(context);
    }

    public PlatformAdapter getPlatformAdapter() {
        return this.mAdapter;
    }

    public void setNetworkStatus(NetworkInfo networkInfo) {
        this.mAdapter.getNetworkMonitor().setNetworkStatus(networkInfo);
    }

    public void onConnectionChanged(NetworkInfo networkInfo) {
        this.mAdapter.getNetworkMonitor().onConnectionChanged(networkInfo);
    }

    public void setOnLogCallBackListener(OnLogCallBackListener onLogCallBackListener) {
        this.mAdapter.getLogger().setOnLogCallBackListener(onLogCallBackListener);
    }

    public void setOnBehaviorLogInterface(OnBehaviorLogInterface onBehaviorLogInterface) {
        this.mAdapter.getBehaviorService().setOnBehaviorLogInterface(onBehaviorLogInterface);
    }

    public void setOnCreateHttpRequestListener(OnCreateHttpRequestListener onCreateHttpRequestListener) {
        this.mAdapter.getDefaultRequestQueue().setOnCreateHttpRequestListener(onCreateHttpRequestListener);
    }
}
