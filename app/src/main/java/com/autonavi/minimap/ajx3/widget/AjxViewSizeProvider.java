package com.autonavi.minimap.ajx3.widget;

public class AjxViewSizeProvider {
    private static IAjxViewSizeProvider sImpl;

    public interface IAjxViewSizeProvider {
        float[] requestViewSize(String str, String str2);
    }

    public static final void register(IAjxViewSizeProvider iAjxViewSizeProvider) {
        sImpl = iAjxViewSizeProvider;
    }

    public static float[] requestViewSize(String str, String str2) {
        if (sImpl != null) {
            return sImpl.requestViewSize(str, str2);
        }
        return new float[2];
    }
}
