package com.alipay.mobile.common.transport.iprank.mng.speedtest;

public interface BaseSpeedTest {
    int getPriority();

    boolean isActivate();

    int speedTest(String str, int i);
}
