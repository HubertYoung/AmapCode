package com.alipay.mobile.common.transport.monitor.networkqos;

import com.alipay.mobile.common.transport.monitor.DeviceTrafficStateInfo;
import com.alipay.mobile.common.transport.monitor.DeviceTrafficStateInfo.DeviceTrafficStateInfoDelta;
import com.alipay.mobile.common.transport.utils.LogCatUtil;

public class DeviceTrafficManager {
    private static DeviceTrafficManager c;
    private double a = 0.0d;
    private double b = 0.0d;

    public static DeviceTrafficManager getInstance() {
        if (c != null) {
            return c;
        }
        synchronized (DeviceTrafficManager.class) {
            try {
                if (c == null) {
                    c = new DeviceTrafficManager();
                }
            }
        }
        return c;
    }

    private DeviceTrafficManager() {
    }

    public DeviceTrafficStateInfo startTrafficMonitor() {
        return new DeviceTrafficStateInfo();
    }

    public DeviceTrafficStateInfoDelta stopTrafficMonitor(DeviceTrafficStateInfo startInfo) {
        if (startInfo == null) {
            return null;
        }
        return startInfo.getDiff(new DeviceTrafficStateInfo());
    }

    public void setSpeed(double sped) {
        this.a = sped;
    }

    public void setBandwidth(double bw) {
        this.b = bw;
    }

    public double getSpeed() {
        return this.a;
    }

    public double getBandwidth() {
        return this.b;
    }

    public void calcSpeedAndBandwidth(long traffic, double mDeltaTS) {
        if (mDeltaTS != 0.0d) {
            this.a = ((double) (8 * traffic)) / ((mDeltaTS * 1024.0d) * 1024.0d);
            this.b = WestWoodManager.getInstance().calBw((double) traffic, mDeltaTS);
            LogCatUtil.debug("DTrafficManager", "input: traffic=[" + traffic + " byte] delta=[" + mDeltaTS + " s] speed=[" + String.format("%.4f", new Object[]{Double.valueOf(this.a)}) + "] bandwidth=[" + String.format("%.4f", new Object[]{Double.valueOf(this.b)}) + "]");
        }
    }
}
