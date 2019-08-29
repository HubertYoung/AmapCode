package com.autonavi.minimap.ackor.ackorplatform;

public abstract class IDeviceService {
    public static final int eOrientationLandscapeLeft = 3;
    public static final int eOrientationLandscapeRight = 4;
    public static final int eOrientationPortrait = 1;
    public static final int eOrientationPortraitUpsideDown = 2;

    public interface INativeFont {
        public static final int kFontStyleBold = 1;
        public static final int kFontStyleItalic = 2;
        public static final int kFontStyleNormal = 0;
        public static final int kFontStyleUnderline = 4;

        int getFontHeight();

        int getStringWrapHeight(String str, int i);

        int getStringWrapWidth(String str);
    }

    /* access modifiers changed from: protected */
    public abstract INativeFont createNativeFont(int i, int i2);

    /* access modifiers changed from: protected */
    public abstract void destroyNativeFont(INativeFont iNativeFont);

    /* access modifiers changed from: protected */
    public abstract int getDeviceHeight();

    /* access modifiers changed from: protected */
    public abstract int getDeviceOrientation();

    /* access modifiers changed from: protected */
    public abstract int getDeviceWidth();
}
