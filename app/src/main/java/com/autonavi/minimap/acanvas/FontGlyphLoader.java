package com.autonavi.minimap.acanvas;

public class FontGlyphLoader {
    public static long create() {
        return ACanvasJNI.createGlyphLoader();
    }

    public static void destory(long j) {
        ACanvasJNI.destroyGlyphLoader(j);
    }
}
