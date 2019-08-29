package com.alipay.mobile.common.logging.helper;

import android.content.Context;
import com.alipay.mobile.common.logging.api.DeviceHWInfo;
import com.alipay.mobile.common.logging.api.LogContext.DevicePerformanceScore;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public final class DevicePerformanceScoreHelper {
    private static DevicePerformanceScoreHelper a;
    private DevicePerformanceScore b = DevicePerformanceScore.LOW;

    private DevicePerformanceScoreHelper(Context context) {
        long totalRam = DeviceHWInfo.getTotalMemory(context);
        if (totalRam == -1) {
            this.b = DevicePerformanceScore.LOW;
        } else if (totalRam < 3670016000L) {
            this.b = DevicePerformanceScore.LOW;
        } else {
            this.b = DevicePerformanceScore.HIGH;
        }
    }

    public final DevicePerformanceScore a() {
        LoggerFactory.getTraceLogger().info("DevicePerformanceScoreHelper", "performance score: " + this.b);
        return this.b;
    }

    public static DevicePerformanceScoreHelper a(Context context) {
        if (a == null) {
            synchronized (DevicePerformanceScoreHelper.class) {
                try {
                    if (a == null) {
                        a = new DevicePerformanceScoreHelper(context);
                    }
                }
            }
        }
        return a;
    }
}
