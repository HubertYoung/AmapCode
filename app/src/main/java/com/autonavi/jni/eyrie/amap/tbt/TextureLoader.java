package com.autonavi.jni.eyrie.amap.tbt;

public abstract class TextureLoader {
    private long mShadow = 0;

    public abstract boolean loadCustomTextureData(int i, TextureParam textureParam, CustomTextureObserver customTextureObserver);

    public abstract boolean loadTextureData(int i, TextureParam textureParam, TextureWrapper textureWrapper);
}
