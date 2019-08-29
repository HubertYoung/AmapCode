package com.alipay.mobile.common.transport.monitor.networkqos;

import com.alipay.mobile.common.transport.utils.LogCatUtil;

public class WestWoodModel {
    public static Double ERROR = Double.valueOf(-100.0d);
    private static int a = 0;
    private static double b;
    private static double c;

    public synchronized double calBw(double bk, double ts) {
        double doubleValue;
        try {
            if (Math.abs(ts) < 5.0E-5d) {
                LogCatUtil.debug("WestWoodModel", "calBw error,ts can't small than 0.5ms");
                doubleValue = ERROR.doubleValue();
            } else {
                b = a(bk, ts);
                if (a == 0) {
                    a++;
                    doubleValue = (bk / ts) * 7.62939453125E-6d;
                    c = doubleValue;
                } else {
                    a++;
                    doubleValue = (0.75d * c) + (0.25d * b);
                    c = doubleValue;
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "WestWoodModel", ex);
            doubleValue = ERROR.doubleValue();
        }
        return doubleValue;
    }

    private static double a(double bk, double ts) {
        if (a == 0) {
            double d = (bk / ts) * 7.62939453125E-6d;
            b = d;
            return d;
        }
        double d2 = (0.75d * b) + (((0.25d * bk) / ts) * 7.62939453125E-6d);
        b = d2;
        return d2;
    }
}
