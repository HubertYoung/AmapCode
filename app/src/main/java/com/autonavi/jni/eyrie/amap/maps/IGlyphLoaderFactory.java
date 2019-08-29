package com.autonavi.jni.eyrie.amap.maps;

public interface IGlyphLoaderFactory {
    long createGlyphLoader();

    void destroyGlyphLoader(long j);
}
