package com.sina.weibo.sdk.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Pair;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.sina.weibo.sdk.utils.LogUtil;

public class NetStateManager {
    public static NetState CUR_NETSTATE = NetState.Mobile;
    /* access modifiers changed from: private */
    public static Context mContext;

    public enum NetState {
        Mobile,
        WIFI,
        NOWAY
    }

    public class NetStateReceive extends BroadcastReceiver {
        public NetStateReceive() {
        }

        public void onReceive(Context context, Intent intent) {
            NetStateManager.mContext = context;
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                if (!wifiManager.isWifiEnabled() || -1 == connectionInfo.getNetworkId()) {
                    NetStateManager.CUR_NETSTATE = NetState.Mobile;
                }
            }
        }
    }

    public static Pair<String, Integer> getAPN() {
        Pair<String, Integer> pair = null;
        Cursor query = mContext != null ? mContext.getContentResolver().query(Uri.parse("content://telephony/carriers/preferapn"), null, null, null, null) : null;
        if (query != null && query.moveToFirst()) {
            String string = query.getString(query.getColumnIndex(CommonUtils.APN_PROP_PROXY));
            if (string != null && string.trim().length() > 0) {
                pair = new Pair<>(string, Integer.valueOf(80));
            }
            query.close();
        }
        return pair;
    }

    public static boolean isNetworkConnected(Context context) {
        NetworkInfo networkInfo;
        if (context == null) {
            LogUtil.e("Weibosdk", "unexpected null context in isNetworkConnected");
            return false;
        } else if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) != 0) {
            return false;
        } else {
            try {
                networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            } catch (NullPointerException unused) {
                networkInfo = null;
            }
            if (networkInfo == null || !networkInfo.isAvailable()) {
                return false;
            }
            return true;
        }
    }
}
