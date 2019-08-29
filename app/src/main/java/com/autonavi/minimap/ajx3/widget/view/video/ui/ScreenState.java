package com.autonavi.minimap.ajx3.widget.view.video.ui;

public final class ScreenState {
    public static final int SCREEN_STATE_FULLSCREEN = 2;
    public static final int SCREEN_STATE_NORMAL = 1;
    public static final int SCREEN_STATE_SMALL_WINDOW = 3;

    public static boolean isFullScreen(int i) {
        return i == 2;
    }

    public static boolean isNormal(int i) {
        return i == 1;
    }

    public static boolean isSmallWindow(int i) {
        return i == 3;
    }
}
