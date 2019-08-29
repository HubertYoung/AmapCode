package com.autonavi.jni.eyrie.amap.tbt;

public class CustomTextureObserver {
    private long mShadow = 0;

    private native void nativeOnCustomTextureLoaded(long j, int i, TextureWrapper textureWrapper);

    public void onCustomTextureLoaded(int i, TextureWrapper textureWrapper) {
        nativeOnCustomTextureLoaded(this.mShadow, i, textureWrapper);
    }
}
