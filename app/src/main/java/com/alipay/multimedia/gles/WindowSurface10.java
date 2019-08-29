package com.alipay.multimedia.gles;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;

public class WindowSurface10 extends EglSurfaceBase10 {
    private Surface b;
    private boolean c;

    public WindowSurface10(EglCore10 eglCore, Surface surface, boolean releaseSurface) {
        super(eglCore);
        createWindowSurface(surface);
        this.b = surface;
        this.c = releaseSurface;
    }

    public WindowSurface10(EglCore10 eglCore, SurfaceHolder surface) {
        super(eglCore);
        createWindowSurface(surface);
    }

    public WindowSurface10(EglCore10 eglCore, SurfaceTexture surfaceTexture) {
        super(eglCore);
        createWindowSurface(surfaceTexture);
    }

    public void release() {
        releaseEglSurface();
        if (this.b != null) {
            if (this.c) {
                this.b.release();
            }
            this.b = null;
        }
    }

    public void recreate(EglCore10 newEglCore) {
        if (this.b == null) {
            throw new RuntimeException("not yet implemented for SurfaceTexture");
        }
        this.a = newEglCore;
        createWindowSurface(this.b);
    }
}
