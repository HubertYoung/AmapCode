package com.autonavi.miniapp.plugin.lbs;

import com.alipay.mobile.nebula.util.H5Log;

public class AdcodeConverter {
    private static final int[] MUNICIPALITIES = {110000, 120000, 310000, 500000};

    public static int convert2MiniApp(int i) {
        for (int i2 : MUNICIPALITIES) {
            if (i2 == i) {
                return i + 100;
            }
        }
        return i;
    }

    public static String convert2MiniApp(String str) {
        try {
            int parseInt = Integer.parseInt(str);
            for (int i : MUNICIPALITIES) {
                if (i == parseInt) {
                    return String.valueOf(parseInt + 100);
                }
            }
        } catch (Exception e) {
            H5Log.e(H5Log.TAG, "convert cityAdCode exception", e);
        }
        return str;
    }

    static String getMiniCityCode(String str) {
        try {
            return String.valueOf((Integer.parseInt(str) / 100) * 100);
        } catch (Exception e) {
            H5Log.e(H5Log.TAG, "convert cityAdCode exception", e);
            return "0";
        }
    }
}
