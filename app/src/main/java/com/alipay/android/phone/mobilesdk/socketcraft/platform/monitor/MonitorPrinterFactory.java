package com.alipay.android.phone.mobilesdk.socketcraft.platform.monitor;

import com.alipay.android.phone.mobilesdk.socketcraft.platform.PlatformUtil;

public class MonitorPrinterFactory {
    private static MonitorPrinter monitorPrinter;

    public static final MonitorPrinter getInstance() {
        if (monitorPrinter != null) {
            return monitorPrinter;
        }
        synchronized (MonitorPrinterFactory.class) {
            try {
                if (monitorPrinter != null) {
                    MonitorPrinter monitorPrinter2 = monitorPrinter;
                    return monitorPrinter2;
                }
                if (PlatformUtil.isAndroidMPaaSPlatform()) {
                    monitorPrinter = PlatformUtil.createMPaaSMonitorPrinter();
                } else {
                    monitorPrinter = new DefaultMonitorPrinter();
                }
                MonitorPrinter monitorPrinter3 = monitorPrinter;
                return monitorPrinter3;
            }
        }
    }
}
