package com.autonavi.jni.ae.gmap.glyph;

public interface IGlyphLoaderFactory {
    long createGlyphLoader();

    void destroyGlyphLoader(long j);
}
