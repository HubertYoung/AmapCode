package com.alipay.tianyan.mobilesdk;

import com.alipay.tianyan.mobilesdk.TianyanLoggingDelegator.LoggingHttpClientGetter;

public class TianyanLoggingHolder {
    public static TianyanLoggingHolder INSTANCE;
    private LoggingHttpClientGetter a;

    public static synchronized TianyanLoggingHolder getInstance() {
        TianyanLoggingHolder tianyanLoggingHolder;
        synchronized (TianyanLoggingHolder.class) {
            try {
                if (INSTANCE == null) {
                    INSTANCE = new TianyanLoggingHolder();
                }
                tianyanLoggingHolder = INSTANCE;
            }
        }
        return tianyanLoggingHolder;
    }

    public LoggingHttpClientGetter getLoggingHttpClientGetter() {
        return this.a;
    }

    public void setLoggingHttpClientGetter(LoggingHttpClientGetter loggingHttpClientGetter) {
        this.a = loggingHttpClientGetter;
    }
}
