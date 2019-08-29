package com.autonavi.minimap.bl;

import com.autonavi.minimap.bl.net.IAosNetwork;
import com.autonavi.minimap.bl.net.IHttpNetwork;
import com.autonavi.minimap.bl.net.INetworkMonitor;
import com.autonavi.minimap.bl.net.INetworkProvider;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class NetworkService {
    private static volatile IAosNetwork sAosNetwork;
    private static volatile IHttpNetwork sHttpNetwork;
    private static volatile ctc sNetworkMonitor;

    public static IHttpNetwork getHttpNetwork() {
        if (sHttpNetwork == null) {
            synchronized (NetworkService.class) {
                if (sHttpNetwork == null) {
                    sHttpNetwork = createHttpNetwork();
                }
            }
        }
        return sHttpNetwork;
    }

    public static IAosNetwork getAosNetwork() {
        if (sAosNetwork == null) {
            synchronized (NetworkService.class) {
                try {
                    if (sAosNetwork == null) {
                        sAosNetwork = createAosNetwork();
                    }
                }
            }
        }
        return sAosNetwork;
    }

    public static INetworkMonitor getNetworkMonitor() {
        if (sNetworkMonitor == null) {
            synchronized (NetworkService.class) {
                if (sNetworkMonitor == null) {
                    sNetworkMonitor = new ctc(NetworkInitializer.context);
                }
            }
        }
        return sNetworkMonitor;
    }

    static synchronized void destroy() {
        synchronized (NetworkService.class) {
            if (sNetworkMonitor != null) {
                ctc ctc = sNetworkMonitor;
                ctc.a.unregisterReceiver(ctc.b);
                sNetworkMonitor = null;
            }
            sAosNetwork = null;
            sHttpNetwork = null;
        }
    }

    private static IAosNetwork createAosNetwork() {
        return new cta(NetworkInitializer.context, NetworkInitializer.config.e, NetworkInitializer.config.b);
    }

    private static IHttpNetwork createHttpNetwork() {
        return new HttpNetworkImpl(NetworkInitializer.context, NetworkInitializer.config.b);
    }

    private static INetworkProvider getNetworkProvider() {
        if (NetworkInitializer.config == null) {
            return null;
        }
        return NetworkInitializer.config.d;
    }
}
