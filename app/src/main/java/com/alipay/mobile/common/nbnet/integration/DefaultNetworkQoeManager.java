package com.alipay.mobile.common.nbnet.integration;

import com.alipay.mobile.common.nbnet.biz.qoe.NetworkQoeManager;
import com.alipay.mobile.common.transport.monitor.DeviceTrafficStateInfo;
import com.alipay.mobile.common.transport.monitor.DeviceTrafficStateInfo.DeviceTrafficStateInfoDelta;
import com.alipay.mobile.common.transport.monitor.networkqos.AlipayQosService;

public class DefaultNetworkQoeManager implements NetworkQoeManager {
    public final int a() {
        AlipayQosService.getInstance().estimate(5000.0d, 4);
        return 0;
    }

    public final int a(int rtt) {
        if (((double) rtt) > 5000.0d) {
            AlipayQosService.getInstance().estimate(5000.0d, 4);
        } else {
            AlipayQosService.getInstance().estimate((double) rtt, 4);
        }
        return (int) AlipayQosService.getInstance().getRto();
    }

    public final int b(int rtt) {
        if (((double) rtt) > 5000.0d) {
            AlipayQosService.getInstance().estimate(5000.0d, 2);
        } else {
            AlipayQosService.getInstance().estimate((double) rtt, 2);
        }
        return (int) AlipayQosService.getInstance().getRto();
    }

    public final void a(long everyStartTime) {
        AlipayQosService.getInstance().estimateByStartTime(everyStartTime, 4);
    }

    public final DeviceTrafficStateInfo b() {
        return AlipayQosService.getInstance().startTrafficMonitor();
    }

    public final DeviceTrafficStateInfoDelta a(DeviceTrafficStateInfo startInfo) {
        return AlipayQosService.getInstance().stopTrafficMonitor(startInfo);
    }
}
