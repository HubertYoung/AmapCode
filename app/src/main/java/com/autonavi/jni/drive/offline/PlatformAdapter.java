package com.autonavi.jni.drive.offline;

import android.content.Context;

public class PlatformAdapter {
    private static final String TAG = "DriveOfflineManager";
    private BehaviorService mBehaviorService;
    private Context mContext;
    private DeviceStorage mDeviceStorage;
    private HttpRequestQueue mHttpRequestQueue;
    private Logger mLogger;
    private NetworkStatusMonitor mNetworkStatusMonitor;
    private long mPtr;

    public PlatformAdapter(Context context) {
        this.mContext = context;
    }

    public HttpRequestQueue getDefaultRequestQueue() {
        if (this.mHttpRequestQueue == null) {
            this.mHttpRequestQueue = new HttpRequestQueue();
            new StringBuilder("PlatformAdapter getDefaultRequestQueue is called()---mHttpRequestQueue=").append(this.mHttpRequestQueue);
        }
        return this.mHttpRequestQueue;
    }

    public NetworkStatusMonitor getNetworkMonitor() {
        if (this.mNetworkStatusMonitor == null) {
            this.mNetworkStatusMonitor = new NetworkStatusMonitor(this.mContext);
            new StringBuilder("PlatformAdapter getNetworkMonitor is called()---mNetworkStatusMonitor=").append(this.mNetworkStatusMonitor);
        }
        return this.mNetworkStatusMonitor;
    }

    public DeviceStorage getDeviceStorage() {
        if (this.mDeviceStorage == null) {
            this.mDeviceStorage = new DeviceStorage();
        }
        return this.mDeviceStorage;
    }

    public BehaviorService getBehaviorService() {
        if (this.mBehaviorService == null) {
            this.mBehaviorService = new BehaviorService();
            new StringBuilder("PlatformAdapter getBehaviorService is called()---mBehaviorService=").append(this.mBehaviorService);
        }
        return this.mBehaviorService;
    }

    public Logger getLogger() {
        if (this.mLogger == null) {
            this.mLogger = new Logger();
            new StringBuilder("PlatformAdapter getLogger is called()---mLogger=").append(this.mLogger);
        }
        return this.mLogger;
    }
}
