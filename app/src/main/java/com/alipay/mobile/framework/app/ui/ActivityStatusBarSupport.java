package com.alipay.mobile.framework.app.ui;

public interface ActivityStatusBarSupport {
    public static final int NOT_SUPPORT = 0;
    public static final int SUPPORT_BY_COLOR = 1;
    public static final int SUPPORT_BY_FULLSCREEN = 3;
    public static final int SUPPORT_BY_FULLSCREEN_AND_TITLEBAR_HEIGHT = 2;

    int getStatusBarColor();

    int getSupportType();
}
