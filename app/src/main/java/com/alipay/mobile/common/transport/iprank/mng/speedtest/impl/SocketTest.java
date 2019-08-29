package com.alipay.mobile.common.transport.iprank.mng.speedtest.impl;

import com.alipay.mobile.common.transport.iprank.mng.speedtest.BaseSpeedTest;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketTest implements BaseSpeedTest {
    public static final String TAG = "IPR_socketTest";

    public int speedTest(String ip, int port) {
        try {
            long begin = System.currentTimeMillis();
            new Socket().connect(new InetSocketAddress(ip, 80), 5000);
            return (int) (System.currentTimeMillis() - begin);
        } catch (Exception e) {
            LogCatUtil.warn((String) TAG, (Throwable) e);
            return -1;
        }
    }

    public boolean isActivate() {
        return false;
    }

    public int getPriority() {
        return 10;
    }
}
