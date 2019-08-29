package com.autonavi.minimap.base.overlay;

public class OverlayTexureCacheUtil {
    public static void clearTextureCache() {
        SimpleMarkerFactory.clearMarkerCache();
        SimpleMarkerFactory.clearLineTextureCache();
        OverlayDebugUtil.clearDebugCache();
    }
}
