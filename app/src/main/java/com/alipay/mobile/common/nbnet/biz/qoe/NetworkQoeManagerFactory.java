package com.alipay.mobile.common.nbnet.biz.qoe;

import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.transport.monitor.DeviceTrafficStateInfo;
import com.alipay.mobile.common.transport.monitor.DeviceTrafficStateInfo.DeviceTrafficStateInfoDelta;
import com.alipay.mobile.common.transport.utils.NetBeanFactory;

public class NetworkQoeManagerFactory {
    static Class<?> a = null;
    private static EmptyNetworkQoeManager b = null;

    class EmptyNetworkQoeManager implements NetworkQoeManager {
        EmptyNetworkQoeManager() {
        }

        public final int a() {
            return 0;
        }

        public final int a(int rtt) {
            return 0;
        }

        public final int b(int rtt) {
            return 0;
        }

        public final void a(long everyStartTime) {
        }

        public final DeviceTrafficStateInfo b() {
            return new DeviceTrafficStateInfo();
        }

        public final DeviceTrafficStateInfoDelta a(DeviceTrafficStateInfo startInfo) {
            return startInfo.getDiff(new DeviceTrafficStateInfo());
        }
    }

    public static NetworkQoeManager a() {
        try {
            if (a == null) {
                a = Class.forName("com.alipay.mobile.common.nbnet.integration.DefaultNetworkQoeManager");
            }
            return (NetworkQoeManager) NetBeanFactory.getBean(a);
        } catch (Throwable e) {
            NBNetLogCat.b("NetworkQoeManager", "getDefaultNetworkQoeManager fail", e);
            return b();
        }
    }

    private static EmptyNetworkQoeManager b() {
        if (b != null) {
            return b;
        }
        EmptyNetworkQoeManager emptyNetworkQoeManager = new EmptyNetworkQoeManager();
        b = emptyNetworkQoeManager;
        return emptyNetworkQoeManager;
    }
}
