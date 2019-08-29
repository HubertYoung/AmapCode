package com.alipay.mobile.android.verify.logger;

public class AndroidLogAdapter implements LogAdapter {
    private final FormatStrategy formatStrategy;

    public boolean isLoggable(int i, String str) {
        return true;
    }

    public AndroidLogAdapter() {
        this.formatStrategy = PrettyFormatStrategy.newBuilder().build();
    }

    public AndroidLogAdapter(FormatStrategy formatStrategy2) {
        this.formatStrategy = formatStrategy2;
    }

    public void log(int i, String str, String str2) {
        this.formatStrategy.log(i, str, str2);
    }
}
