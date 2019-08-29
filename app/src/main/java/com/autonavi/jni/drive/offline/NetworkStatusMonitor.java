package com.autonavi.jni.drive.offline;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStatusMonitor {
    private static final int NETWORK_STATUS_2G = 3;
    private static final int NETWORK_STATUS_3G = 4;
    private static final int NETWORK_STATUS_4G = 5;
    private static final int NETWORK_STATUS_NOT_REACHABLE = 1;
    private static final int NETWORK_STATUS_UNKNOWN = 0;
    private static final int NETWORK_STATUS_WIFI = 2;
    private static final String TAG = "DriveOfflineManager";
    private Context mContext;
    private NetworkInfo mLastNetworkInfo;
    private long mPtr;

    private native void nativeNetworkStatusChanged(int i);

    public NetworkStatusMonitor(Context context) {
        this.mContext = context;
    }

    public void setNetworkStatus(NetworkInfo networkInfo) {
        this.mLastNetworkInfo = networkInfo;
    }

    public void onConnectionChanged(NetworkInfo networkInfo) {
        try {
            nativeNetworkStatusChanged(getCurrentStatus());
        } catch (UnsatisfiedLinkError unused) {
        }
    }

    public int getCurrentStatus() {
        if (this.mContext != null) {
            NetworkInfo activeNetworkInfo = getActiveNetworkInfo(this.mContext);
            if (activeNetworkInfo != null) {
                return getNetworkStatus(activeNetworkInfo);
            }
        }
        return getNetworkStatus(this.mLastNetworkInfo);
    }

    public static NetworkInfo getActiveNetworkInfo(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return null;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return activeNetworkInfo;
            }
            NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
            if (allNetworkInfo == null) {
                return null;
            }
            int i = 0;
            while (true) {
                if (i < allNetworkInfo.length) {
                    if (allNetworkInfo[i] != null && allNetworkInfo[i].isConnected()) {
                        activeNetworkInfo = allNetworkInfo[i];
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
            return activeNetworkInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int getNetworkStatus(NetworkInfo networkInfo) {
        if (networkInfo == null) {
            return 1;
        }
        switch (networkInfo.getType()) {
            case 0:
                return 5;
            case 1:
                return 2;
            default:
                return 1;
        }
    }
}
