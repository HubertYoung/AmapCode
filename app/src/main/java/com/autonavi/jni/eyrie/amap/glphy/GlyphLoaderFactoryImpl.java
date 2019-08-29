package com.autonavi.jni.eyrie.amap.glphy;

import com.autonavi.jni.eyrie.amap.maps.IGlyphLoaderFactory;

public class GlyphLoaderFactoryImpl implements IGlyphLoaderFactory {
    public long createGlyphLoader() {
        return GlyphLoader.createGlyphLoader();
    }

    public void destroyGlyphLoader(long j) {
        GlyphLoader.destroyGlyphLoader(j);
    }
}
