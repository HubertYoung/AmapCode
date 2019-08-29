package com.alipay.mobile.common.logging.util.network;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.taobao.accs.utl.UtilityImpl;

public class NetWorkProvider {
    public static NetWorkProvider INSTANCE = null;
    public static final String NETWORK_UNKNOWN = "unknown";
    private static long b;
    private static int c = -1;
    private Context a;
    private LogNetworkConnReceiver d;

    public class LogNetworkConnReceiver extends RigorousNetworkConnReceiver {
        public LogNetworkConnReceiver(Context context) {
            super(context);
        }

        /* access modifiers changed from: protected */
        public void onReceivee(Context context, Intent intent) {
            NetWorkProvider.a(context);
        }
    }

    public static synchronized NetWorkProvider createInstance(Context context) {
        NetWorkProvider netWorkProvider;
        synchronized (NetWorkProvider.class) {
            if (INSTANCE == null) {
                INSTANCE = new NetWorkProvider(context);
            }
            netWorkProvider = INSTANCE;
        }
        return netWorkProvider;
    }

    public static synchronized NetWorkProvider getInstance() {
        NetWorkProvider netWorkProvider;
        synchronized (NetWorkProvider.class) {
            try {
                if (INSTANCE == null) {
                    throw new IllegalStateException("need createInstance before use");
                }
                netWorkProvider = INSTANCE;
            }
        }
        return netWorkProvider;
    }

    public NetWorkProvider(Context context) {
        this.a = context;
        registerLogNetworkConnReceiver(this.a);
    }

    public void registerLogNetworkConnReceiver(Context context) {
        this.d = new LogNetworkConnReceiver(context);
        this.d.register();
    }

    public String getCurrentNetworkType2Str() {
        switch (getCurrentNetworkType()) {
            case 1:
                return UtilityImpl.NET_TYPE_2G;
            case 2:
                return UtilityImpl.NET_TYPE_3G;
            case 3:
                return "wifi";
            case 4:
                return UtilityImpl.NET_TYPE_4G;
            default:
                return "unknown";
        }
    }

    public int getCurrentNetworkType() {
        if (c == -1 || c == 0) {
            a(this.a);
        }
        return c;
    }

    /* access modifiers changed from: private */
    public static int a(Context context) {
        long time = SystemClock.uptimeMillis();
        if (time - b > 2000) {
            c = NetworkUtils.getNetworkType(context);
            b = time;
        }
        return c;
    }
}
