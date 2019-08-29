package com.alipay.mobile.common.logging.http;

import android.text.TextUtils;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class UploadUrlConfig {
    private static UploadUrlConfig a;
    private boolean b = false;
    private boolean c = false;
    private boolean d = false;
    private long e = 0;
    private long f = 0;

    public static synchronized UploadUrlConfig a() {
        UploadUrlConfig uploadUrlConfig;
        synchronized (UploadUrlConfig.class) {
            try {
                if (a == null) {
                    a = new UploadUrlConfig();
                }
                uploadUrlConfig = a;
            }
        }
        return uploadUrlConfig;
    }

    public final void b() {
        this.b = false;
        this.c = false;
    }

    public final boolean c() {
        return e() || d();
    }

    private boolean d() {
        if (this.b) {
            return this.d;
        }
        this.b = true;
        try {
            if ("yes".equals(LoggerFactory.getLogContext().getApplicationContext().getSharedPreferences(LogContext.LOG_HOST_CONFIG_SP, 4).getString(LogContext.LOG_HOST_CONFIG_SP_DISABLE_HTTPS, BQCCameraParam.VALUE_NO))) {
                LoggerFactory.getTraceLogger().info("UploadUrlConfig", "disable https, use http upload");
                this.d = true;
            } else {
                this.d = false;
            }
        } catch (Throwable e2) {
            LoggerFactory.getTraceLogger().error((String) "UploadUrlConfig", e2);
        }
        return this.d;
    }

    private boolean e() {
        if (!this.c) {
            f();
            this.c = true;
        }
        if (this.e == 0 || this.f == 0) {
            return false;
        }
        long curTimeSecond = System.currentTimeMillis() / 1000;
        if (curTimeSecond <= this.e || curTimeSecond >= this.f) {
            return false;
        }
        LoggerFactory.getTraceLogger().info("UploadUrlConfig", "disableHttpsInTime use http upload");
        return true;
    }

    private void f() {
        String disableHttpsTime = LoggerFactory.getLogContext().getApplicationContext().getSharedPreferences(LogContext.LOG_HOST_CONFIG_SP, 4).getString(LogContext.LOG_HOST_CONFIG_SP_DISABLE_HTTPS_TIME, "");
        if (!TextUtils.isEmpty(disableHttpsTime)) {
            String[] times = disableHttpsTime.split(",");
            if (times == null || times.length < 2) {
                LoggerFactory.getTraceLogger().info("UploadUrlConfig", "configTime is error");
                return;
            }
            long startTime = 0;
            long endTime = 0;
            try {
                startTime = Long.parseLong(times[0]);
                endTime = Long.parseLong(times[1]);
            } catch (Throwable th) {
                LoggerFactory.getTraceLogger().info("UploadUrlConfig", "convert time error");
            }
            if (startTime == 0 || endTime == 0) {
                LoggerFactory.getTraceLogger().info("UploadUrlConfig", "startTime or endTime is null");
                return;
            }
            this.e = startTime;
            this.f = endTime;
        }
    }
}
