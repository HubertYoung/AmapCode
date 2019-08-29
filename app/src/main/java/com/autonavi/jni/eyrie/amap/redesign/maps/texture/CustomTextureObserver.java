package com.autonavi.jni.eyrie.amap.redesign.maps.texture;

public class CustomTextureObserver implements ICustomTextureObserver {
    private final long mNativePtr;
    private final OverlayTextureParam mTextureParam;

    private static native void nativeOnCustomTextureLoaded(long j, OverlayTextureParam overlayTextureParam, TextureWrapper textureWrapper);

    private CustomTextureObserver(long j, OverlayTextureParam overlayTextureParam) {
        this.mNativePtr = j;
        this.mTextureParam = overlayTextureParam;
    }

    public void onCustomTextureLoaded(TextureWrapper textureWrapper) {
        if (!textureWrapper.isAnchorSetted) {
            textureWrapper.anchorX = this.mTextureParam.anchorX;
            textureWrapper.anchorY = this.mTextureParam.anchorY;
        }
        nativeOnCustomTextureLoaded(this.mNativePtr, this.mTextureParam, textureWrapper);
    }
}
