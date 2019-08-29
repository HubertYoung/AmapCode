package com.alipay.mobile.common.transport.monitor.networkqos;

public class WestWoodManager {
    private static WestWoodManager b;
    private WestWoodModel a = new WestWoodModel();

    public static WestWoodManager getInstance() {
        if (b != null) {
            return b;
        }
        synchronized (WestWoodManager.class) {
            try {
                if (b == null) {
                    b = new WestWoodManager();
                }
            }
        }
        return b;
    }

    private WestWoodManager() {
    }

    public double calBw(double bk, double ts) {
        return this.a.calBw(bk, ts);
    }
}
