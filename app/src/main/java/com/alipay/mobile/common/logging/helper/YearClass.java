package com.alipay.mobile.common.logging.helper;

import android.content.Context;
import com.alipay.mobile.common.logging.api.DeviceHWInfo;
import java.util.ArrayList;
import java.util.Collections;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class YearClass {
    private static volatile Integer a;

    public static int a(Context c) {
        if (a == null) {
            synchronized (YearClass.class) {
                try {
                    if (a == null) {
                        a = Integer.valueOf(b(c));
                    }
                }
            }
        }
        return a.intValue();
    }

    private static void a(ArrayList<Integer> list, int value) {
        if (value != -1) {
            list.add(Integer.valueOf(value));
        }
    }

    private static int b(Context c) {
        long totalRam = DeviceHWInfo.getTotalMemory(c);
        if (totalRam == -1) {
            return c(c);
        }
        if (totalRam <= 805306368) {
            return DeviceHWInfo.getNumberOfCPUCores() <= 1 ? 2009 : 2010;
        }
        if (totalRam <= 1073741824) {
            if (DeviceHWInfo.getCPUMaxFreqKHz() < 1300000) {
                return 2011;
            }
            return 2012;
        } else if (totalRam <= IjkMediaMeta.AV_CH_LAYOUT_STEREO_DOWNMIX) {
            if (DeviceHWInfo.getCPUMaxFreqKHz() >= 1800000) {
                return 2013;
            }
            return 2012;
        } else if (totalRam <= IjkMediaMeta.AV_CH_WIDE_LEFT) {
            return 2013;
        } else {
            return totalRam <= 3221225472L ? 2014 : 2015;
        }
    }

    private static int c(Context c) {
        ArrayList componentYears = new ArrayList();
        a(componentYears, a());
        a(componentYears, b());
        a(componentYears, d(c));
        if (componentYears.isEmpty()) {
            return -1;
        }
        Collections.sort(componentYears);
        if ((componentYears.size() & 1) == 1) {
            return ((Integer) componentYears.get(componentYears.size() / 2)).intValue();
        }
        int baseIndex = (componentYears.size() / 2) - 1;
        return ((((Integer) componentYears.get(baseIndex + 1)).intValue() - ((Integer) componentYears.get(baseIndex)).intValue()) / 2) + ((Integer) componentYears.get(baseIndex)).intValue();
    }

    private static int a() {
        int cores = DeviceHWInfo.getNumberOfCPUCores();
        if (cores <= 0) {
            return -1;
        }
        if (cores == 1) {
            return 2008;
        }
        if (cores <= 3) {
            return 2011;
        }
        return 2012;
    }

    private static int b() {
        long clockSpeedKHz = (long) DeviceHWInfo.getCPUMaxFreqKHz();
        if (clockSpeedKHz == -1) {
            return -1;
        }
        if (clockSpeedKHz <= 528000) {
            return 2008;
        }
        if (clockSpeedKHz <= 620000) {
            return 2009;
        }
        if (clockSpeedKHz <= 1020000) {
            return 2010;
        }
        if (clockSpeedKHz <= 1220000) {
            return 2011;
        }
        if (clockSpeedKHz <= 1520000) {
            return 2012;
        }
        if (clockSpeedKHz <= 2020000) {
            return 2013;
        }
        return 2014;
    }

    private static int d(Context c) {
        long totalRam = DeviceHWInfo.getTotalMemory(c);
        if (totalRam <= 0) {
            return -1;
        }
        if (totalRam <= 201326592) {
            return 2008;
        }
        if (totalRam <= 304087040) {
            return 2009;
        }
        if (totalRam <= IjkMediaMeta.AV_CH_STEREO_LEFT) {
            return 2010;
        }
        if (totalRam <= 1073741824) {
            return 2011;
        }
        if (totalRam <= IjkMediaMeta.AV_CH_LAYOUT_STEREO_DOWNMIX) {
            return 2012;
        }
        if (totalRam <= IjkMediaMeta.AV_CH_WIDE_LEFT) {
            return 2013;
        }
        return 2014;
    }
}
