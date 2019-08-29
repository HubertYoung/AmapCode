package com.alipay.multimedia.gles;

public class OffscreenSurface14 extends EglSurfaceBase {
    public OffscreenSurface14(EglCore eglCore, int width, int height) {
        super(eglCore);
        createOffscreenSurface(width, height);
    }

    public void release() {
        releaseEglSurface();
    }
}
