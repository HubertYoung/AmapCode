package com.alipay.mobile.framework.service.textsize;

public class SizeUtil {
    public static float getTextSize(float defaultSize, int gear) {
        return getScale(gear) * defaultSize;
    }

    public static float getScale(int pos) {
        switch (pos) {
            case 0:
                return 0.875f;
            case 1:
                return 1.0f;
            case 2:
                return 1.125f;
            case 3:
                return 1.25f;
            case 4:
                return 1.375f;
            default:
                return 1.0f;
        }
    }

    public static float getFontSize(float paramFloat) {
        if (paramFloat == 0.875f) {
            return 14.0f;
        }
        if (paramFloat == 1.0f) {
            return 16.0f;
        }
        if (paramFloat == 1.125f) {
            return 18.0f;
        }
        if (paramFloat == 1.25f) {
            return 20.0f;
        }
        if (paramFloat == 1.375f) {
            return 22.0f;
        }
        return 16.0f;
    }
}
