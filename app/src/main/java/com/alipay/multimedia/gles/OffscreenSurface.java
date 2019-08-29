package com.alipay.multimedia.gles;

public class OffscreenSurface extends EglSurfaceBase10 {
    public OffscreenSurface(EglCore10 eglCore, int width, int height) {
        super(eglCore);
        createOffscreenSurface(width, height);
    }

    public void release() {
        releaseEglSurface();
    }
}
