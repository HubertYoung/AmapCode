package com.alipay.mobile.common.logging.strategy;

import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class LogLengthConfig {
    private static LogLengthConfig c;
    private String a = null;
    private boolean b = true;

    public static synchronized LogLengthConfig a() {
        LogLengthConfig logLengthConfig;
        synchronized (LogLengthConfig.class) {
            try {
                if (c == null) {
                    c = new LogLengthConfig();
                }
                logLengthConfig = c;
            }
        }
        return logLengthConfig;
    }

    public final boolean b() {
        return this.b;
    }

    public final void c() {
        if ("yes".equals(LoggerFactory.getLogContext().getApplicationContext().getSharedPreferences("UseLogHttpClientConfig", 0).getString("LogLengthLimitDisable", BQCCameraParam.VALUE_NO))) {
            this.b = false;
        } else {
            this.b = true;
        }
    }
}
