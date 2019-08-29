package com.alipay.mobile.nebula.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.util.Constants;
import com.taobao.accs.utl.UtilityImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class H5NetworkUtil {
    private static final String ONLINE_GW = "https://mobilegw.alipay.com/mgw.htm";
    public static final String PRE_GW = "https://mobilegwpre.alipay.com/mgw.htm";
    public static final String TAG = "NetworkUtil";
    private static H5NetworkUtil instance;
    private Context context;
    private List<NetworkListener> listenerList = Collections.synchronizedList(new ArrayList());
    private Network network = null;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            H5NetworkUtil.this.updateNetwork();
        }
    };

    public enum Network {
        NETWORK_WIFI,
        NETWORK_MOBILE_FAST,
        NETWORK_MOBILE_MIDDLE,
        NETWORK_MOBILE_SLOW,
        NETWORK_NO_CONNECTION,
        NETWORK_TYPE_UNKNOWN
    }

    public interface NetworkListener {
        void onNetworkChanged(Network network, Network network2);
    }

    private H5NetworkUtil() {
    }

    public static final H5NetworkUtil getInstance() {
        synchronized (H5NetworkUtil.class) {
            try {
                if (instance == null) {
                    instance = new H5NetworkUtil();
                }
            }
        }
        return instance;
    }

    public void init(Context context2) {
        if (this.context == null) {
            this.context = context2.getApplicationContext();
            registerReceiver();
        }
    }

    public void registerReceiver() {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            this.context.registerReceiver(this.receiver, filter);
        } catch (Throwable e) {
            H5Log.e((String) "NetworkUtil", e);
        }
    }

    public void unregisterReceiver() {
        try {
            this.context.unregisterReceiver(this.receiver);
        } catch (Throwable e) {
            H5Log.e("NetworkUtil", "exception detail", e);
        }
    }

    @Deprecated
    public final synchronized Network getNetworkType() {
        try {
        }
        return this.network;
    }

    public final synchronized String getNetworkString() {
        String str;
        try {
            if (this.network != null) {
                switch (this.network) {
                    case NETWORK_WIFI:
                        str = "WIFI";
                        break;
                    case NETWORK_MOBILE_FAST:
                        str = "4G";
                        break;
                    case NETWORK_MOBILE_MIDDLE:
                        str = "3G";
                        break;
                    case NETWORK_MOBILE_SLOW:
                        str = "2G";
                        break;
                    default:
                        str = "UNKNOWN";
                        break;
                }
            } else {
                str = "UNKNOWN";
            }
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

    public static String TransferNetworkType(Network network2) {
        switch (network2) {
            case NETWORK_WIFI:
                return "wifi";
            case NETWORK_MOBILE_FAST:
                return UtilityImpl.NET_TYPE_4G;
            case NETWORK_MOBILE_MIDDLE:
                return UtilityImpl.NET_TYPE_3G;
            case NETWORK_MOBILE_SLOW:
                return UtilityImpl.NET_TYPE_2G;
            case NETWORK_NO_CONNECTION:
                return Constants.ANIMATOR_NONE;
            default:
                return "unknown";
        }
    }

    /* access modifiers changed from: private */
    public final void updateNetwork() {
        NetworkInfo networkInfo = null;
        try {
            networkInfo = ((ConnectivityManager) this.context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Throwable t) {
            H5Log.e("NetworkUtil", "exception detail", t);
        }
        Network lastNetwork = this.network;
        this.network = detectNetwork(networkInfo);
        if (lastNetwork != null && this.network != lastNetwork && this.listenerList != null) {
            synchronized (this.listenerList) {
                for (NetworkListener listener : this.listenerList) {
                    H5Log.d("NetworkUtil", "onNetworkChanged");
                    if (listener != null) {
                        listener.onNetworkChanged(lastNetwork, this.network);
                    }
                }
            }
        }
    }

    private final Network detectNetwork(NetworkInfo info) {
        if (info == null) {
            try {
                return Network.NETWORK_NO_CONNECTION;
            } catch (Exception e) {
                H5Log.e((String) "NetworkUtil", (Throwable) e);
                return Network.NETWORK_TYPE_UNKNOWN;
            }
        } else if (!info.isConnected()) {
            return Network.NETWORK_NO_CONNECTION;
        } else {
            int type = info.getType();
            int subType = info.getSubtype();
            H5Log.d("NetworkUtil", "type: " + type + " subType: " + subType);
            if (type == 1 || type == 9) {
                return Network.NETWORK_WIFI;
            }
            if (type != 0) {
                return Network.NETWORK_TYPE_UNKNOWN;
            }
            switch (subType) {
                case 0:
                    return Network.NETWORK_TYPE_UNKNOWN;
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                    return Network.NETWORK_MOBILE_SLOW;
                case 13:
                    return Network.NETWORK_MOBILE_FAST;
                default:
                    return Network.NETWORK_MOBILE_MIDDLE;
            }
        }
    }

    public static String getGWFURL(Context context2) {
        if (!H5Utils.isDebug()) {
            return ONLINE_GW;
        }
        String gwf = H5Utils.getConfigString(context2, "GWFServerUrl");
        if (TextUtils.isEmpty(gwf)) {
            return ONLINE_GW;
        }
        return gwf;
    }
}
