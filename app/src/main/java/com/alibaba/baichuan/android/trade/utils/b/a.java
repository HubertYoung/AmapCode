package com.alibaba.baichuan.android.trade.utils.b;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class a {
    public static boolean a(Context context) {
        if (context == null) {
            return false;
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected();
    }
}
