package com.alipay.mobile.beehive.util;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.autonavi.minimap.ajx3.util.Constants;
import com.taobao.accs.utl.UtilityImpl;
import java.util.ArrayList;
import java.util.List;

public class NetworkUtil {
    public static final String TAG = "NetworkUtil";
    private static NetworkUtil instance;
    private Context context;
    private final List<NetworkListener> listenerList = new ArrayList();
    private Network network;
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            NetworkUtil.this.updateNetwork();
        }
    };

    public enum Network {
        NETWORK_WIFI,
        NETWORK_MOBILE_FAST,
        NETWORK_MOBILE_MIDDLE,
        NETWORK_MOBILE_SLOW,
        NETWORK_NONE
    }

    public interface NetworkListener {
        void onNetworkChanged(Network network, Network network2);
    }

    public static final NetworkUtil getInstance() {
        synchronized (NetworkUtil.class) {
            if (instance == null) {
                NetworkUtil networkUtil = new NetworkUtil();
                instance = networkUtil;
                networkUtil.init();
            }
        }
        return instance;
    }

    private NetworkUtil() {
    }

    public void init() {
        if (this.context == null) {
            this.context = LauncherApplicationAgent.getInstance().getApplicationContext();
            registerReceiver();
        }
    }

    public void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        this.context.registerReceiver(this.receiver, filter);
    }

    public void unregisterReceiver() {
        try {
            this.context.unregisterReceiver(this.receiver);
        } catch (IllegalArgumentException e) {
            LoggerFactory.getTraceLogger().error(NetworkUtil.class.getSimpleName(), (Throwable) e);
        }
    }

    public final synchronized Network getNetworkType() {
        return this.network;
    }

    public final synchronized String getNetworkString() {
        String str;
        if (this.network != null) {
            switch (this.network) {
                case NETWORK_WIFI:
                    str = "wifi";
                    break;
                case NETWORK_MOBILE_FAST:
                    str = UtilityImpl.NET_TYPE_4G;
                    break;
                case NETWORK_MOBILE_MIDDLE:
                    str = UtilityImpl.NET_TYPE_3G;
                    break;
                case NETWORK_MOBILE_SLOW:
                    str = UtilityImpl.NET_TYPE_2G;
                    break;
                default:
                    str = Constants.ANIMATOR_NONE;
                    break;
            }
        } else {
            str = Constants.ANIMATOR_NONE;
        }
        return str;
    }

    public final void addListener(NetworkListener listener) {
        if (listener != null && !this.listenerList.contains(listener)) {
            this.listenerList.add(listener);
        }
    }

    public final void removeListener(NetworkListener listener) {
        if (listener != null) {
            this.listenerList.remove(listener);
        }
    }

    /* access modifiers changed from: private */
    public final void updateNetwork() {
        NetworkInfo networkInfo = null;
        try {
            networkInfo = ((ConnectivityManager) this.context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().error(NetworkUtil.class.getSimpleName(), (Throwable) e);
        }
        Network lastNetwork = this.network;
        this.network = detectNetwork(networkInfo);
        if (lastNetwork != null && this.network != lastNetwork && this.listenerList != null) {
            for (NetworkListener onNetworkChanged : this.listenerList) {
                onNetworkChanged.onNetworkChanged(lastNetwork, this.network);
            }
        }
    }

    @TargetApi(3)
    private final Network detectNetwork(NetworkInfo info) {
        if (info == null || !info.isConnected()) {
            return Network.NETWORK_NONE;
        }
        int type = info.getType();
        int subType = info.getSubtype();
        if (type == 1 || type == 9) {
            return Network.NETWORK_WIFI;
        }
        if (type == 0) {
            switch (subType) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                    return Network.NETWORK_MOBILE_SLOW;
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 14:
                case 15:
                    return Network.NETWORK_MOBILE_MIDDLE;
                case 13:
                    return Network.NETWORK_MOBILE_FAST;
            }
        }
        return Network.NETWORK_NONE;
    }
}
