package com.alipay.mobile.tinyappcommon.remotedebug;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.alipay.mobile.nebula.util.H5Log;

/* compiled from: RemoteDebugUtils */
public class b {
    private static final String a = b.class.getSimpleName();

    public static boolean a(Context context) {
        try {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService("connectivity");
            if (connMgr == null) {
                return false;
            }
            NetworkInfo info = connMgr.getActiveNetworkInfo();
            if (info != null && info.isConnectedOrConnecting()) {
                return true;
            }
            NetworkInfo mobNetInfo = connMgr.getNetworkInfo(0);
            NetworkInfo wifiNetInfo = connMgr.getNetworkInfo(1);
            if (wifiNetInfo != null && wifiNetInfo.isConnectedOrConnecting()) {
                return true;
            }
            if (mobNetInfo.isConnectedOrConnecting()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            H5Log.e(a, "isNetAvailable: [ Exception " + e + " ]");
            return false;
        }
    }
}
