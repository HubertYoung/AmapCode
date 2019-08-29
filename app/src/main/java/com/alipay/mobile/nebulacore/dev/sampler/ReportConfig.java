package com.alipay.mobile.nebulacore.dev.sampler;

public class ReportConfig {
    private static ReportConfig a;

    public static synchronized ReportConfig getInstance() {
        ReportConfig reportConfig;
        synchronized (ReportConfig.class) {
            try {
                if (a == null) {
                    a = new ReportConfig();
                }
                reportConfig = a;
            }
        }
        return reportConfig;
    }

    public int getSampleDelay() {
        return 500;
    }
}
