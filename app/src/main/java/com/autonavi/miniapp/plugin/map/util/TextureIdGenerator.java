package com.autonavi.miniapp.plugin.map.util;

public class TextureIdGenerator {
    private static final int DEFAULT_TEXTURE_INDEX = 1;
    private int textureIndex = 1;

    public int generate() {
        int i = this.textureIndex + 1;
        this.textureIndex = i;
        return i;
    }

    public void reset() {
        this.textureIndex = 1;
    }
}
