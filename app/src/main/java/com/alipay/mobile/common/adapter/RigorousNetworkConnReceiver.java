package com.alipay.mobile.common.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.utils.LogCatUtil;

public abstract class RigorousNetworkConnReceiver extends BroadcastReceiver {
    private int a = -1;
    private int b = -1;
    private Boolean c = null;
    private Context d;

    /* access modifiers changed from: protected */
    public abstract void onReceivee(Context context, Intent intent);

    public RigorousNetworkConnReceiver(Context context) {
        this.d = context;
        this.c = null;
        this.a = -1;
        this.b = -1;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void register() {
        try {
            this.d.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        } catch (Exception e) {
            LogCatUtil.warn((String) "RigorousNetworkConnReceiver", (Throwable) e);
        }
    }

    public void unregister() {
        try {
            this.d.unregisterReceiver(this);
        } catch (Exception e) {
            LogCatUtil.warn((String) "RigorousNetworkConnReceiver", (Throwable) e);
        }
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            try {
                LoggerFactory.getTraceLogger().info("monitor", "onReceive at: " + getClass().getName() + ", Intent: " + intent);
                if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                    int networkState = a(context);
                    if (networkState == 0) {
                        LogCatUtil.debug("RigorousNetworkConnReceiver", "NetworkConnectivityReceiver#onReceive 网络不可用");
                        onReceivee(context, intent);
                    } else if (networkState == 1) {
                        LogCatUtil.debug("RigorousNetworkConnReceiver", "NetworkConnectivityReceiver#onReceive 网络变化");
                        onReceivee(context, intent);
                    }
                }
            } catch (Throwable e) {
                LogCatUtil.error("RigorousNetworkConnReceiver", "onReceive exception:", e);
            }
        }
    }

    private int a(Context context) {
        NetworkInfo activeNetworkInfo = null;
        try {
            activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Throwable e) {
            LogCatUtil.warn((String) "RigorousNetworkConnReceiver", "getActiveNetworkInfo exception. " + e.toString());
        }
        if (activeNetworkInfo == null) {
            LogCatUtil.info("RigorousNetworkConnReceiver", "当前无网络!");
            this.c = Boolean.valueOf(false);
            return 0;
        }
        boolean available = activeNetworkInfo.isAvailable();
        boolean connected = activeNetworkInfo.isConnected();
        int type = activeNetworkInfo.getType();
        int subType = activeNetworkInfo.getSubtype();
        String extraInfo = activeNetworkInfo.getExtraInfo();
        if (this.a == -1 || this.b == -1 || this.c == null) {
            LogCatUtil.info("RigorousNetworkConnReceiver", " New contivity broadcast！");
        } else if (this.c.booleanValue() == connected && this.a == type && this.b == subType) {
            LogCatUtil.info("RigorousNetworkConnReceiver", " Old contivity broadcast！");
            return 2;
        }
        this.c = Boolean.valueOf(connected);
        this.a = type;
        this.b = subType;
        LogCatUtil.debug("RigorousNetworkConnReceiver", " type=[" + type + "] subType=[" + subType + "]  available=[" + available + "] connected=[" + connected + "] detailedState=[" + activeNetworkInfo.getDetailedState() + "] extraInfo=[" + extraInfo + "]");
        LogCatUtil.debug("RigorousNetworkConnReceiver", " activeNetworkInfo hashcode=" + activeNetworkInfo.hashCode() + "  activeNetworkInfo = [" + activeNetworkInfo.toString() + "]\n\n\n");
        if (this.c.booleanValue()) {
            return 1;
        }
        return 0;
    }
}
