package com.alipay.mobile.common.transport.iprank.mng.speedtest.impl;

import com.alipay.mobile.common.transport.ext.ExtTransportOffice;
import com.alipay.mobile.common.transport.iprank.mng.speedtest.BaseSpeedTest;
import com.alipay.mobile.common.transport.utils.LogCatUtil;

public class SocketTestImpl implements BaseSpeedTest {
    public int speedTest(String ip, int port) {
        try {
            return ExtTransportOffice.getInstance().getSpeeTestImpl().speedTest(ip, port);
        } catch (Exception ex) {
            LogCatUtil.error("IPR_SocketTestImpl", "SocketTestImpl exception", ex);
            return -1;
        }
    }

    public boolean isActivate() {
        return true;
    }

    public int getPriority() {
        return 100;
    }
}
