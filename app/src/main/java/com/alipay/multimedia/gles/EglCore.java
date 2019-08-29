package com.alipay.multimedia.gles;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.view.Surface;
import com.alipay.alipaylogger.Log;

@TargetApi(18)
public final class EglCore {
    public static final int FLAG_RECORDABLE = 1;
    public static final int FLAG_TRY_GLES3 = 2;
    private EGLDisplay a;
    private EGLContext b;
    private EGLConfig c;

    public EglCore() {
        this(null, 0);
    }

    public EglCore(EGLContext sharedContext, int flags) {
        this.a = EGL14.EGL_NO_DISPLAY;
        this.b = EGL14.EGL_NO_CONTEXT;
        this.c = null;
        if (this.a != EGL14.EGL_NO_DISPLAY) {
            throw new RuntimeException("EGL already set up");
        }
        sharedContext = sharedContext == null ? EGL14.EGL_NO_CONTEXT : sharedContext;
        this.a = EGL14.eglGetDisplay(0);
        if (this.a == EGL14.EGL_NO_DISPLAY) {
            throw new RuntimeException("unable to get EGL14 display");
        }
        int[] version = new int[2];
        if (!EGL14.eglInitialize(this.a, version, 0, version, 1)) {
            this.a = null;
            throw new RuntimeException("unable to initialize EGL14");
        }
        if ((flags & 2) != 0) {
            EGLConfig config = a(flags, 3);
            if (config != null) {
                EGLContext context = EGL14.eglCreateContext(this.a, config, sharedContext, new int[]{12440, 3, 12344}, 0);
                if (EGL14.eglGetError() == 12288) {
                    this.c = config;
                    this.b = context;
                }
            }
        }
        if (this.b == EGL14.EGL_NO_CONTEXT) {
            EGLConfig config2 = a(flags, 2);
            if (config2 == null) {
                throw new RuntimeException("Unable to find a suitable EGLConfig");
            }
            EGLContext context2 = EGL14.eglCreateContext(this.a, config2, sharedContext, new int[]{12440, 2, 12344}, 0);
            a("eglCreateContext");
            this.c = config2;
            this.b = context2;
        }
        int[] values = new int[1];
        EGL14.eglQueryContext(this.a, this.b, 12440, values, 0);
        Log.d("EglCore", "EGLContext created, client version " + values[0]);
    }

    private EGLConfig a(int flags, int version) {
        int renderableType = 4;
        if (version >= 3) {
            renderableType = 68;
        }
        int[] attribList = {12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, renderableType, 12344, 0, 12344};
        if ((flags & 1) != 0) {
            attribList[10] = 12610;
            attribList[11] = 1;
        }
        EGLConfig[] configs = new EGLConfig[1];
        if (EGL14.eglChooseConfig(this.a, attribList, 0, configs, 0, 1, new int[1], 0)) {
            return configs[0];
        }
        Log.d("EglCore", "unable to find RGB8888 / " + version + " EGLConfig");
        return null;
    }

    public final void release() {
        if (this.a != EGL14.EGL_NO_DISPLAY) {
            EGL14.eglMakeCurrent(this.a, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
            EGL14.eglDestroyContext(this.a, this.b);
            EGL14.eglTerminate(this.a);
        }
        this.a = EGL14.EGL_NO_DISPLAY;
        this.b = EGL14.EGL_NO_CONTEXT;
        this.c = null;
    }

    /* access modifiers changed from: protected */
    public final void finalize() {
        try {
            if (this.a != EGL14.EGL_NO_DISPLAY) {
                Log.d("EglCore", "WARNING: EglCore was not explicitly released -- state may be leaked");
                release();
            }
        } finally {
            super.finalize();
        }
    }

    public final void releaseSurface(EGLSurface eglSurface) {
        EGL14.eglDestroySurface(this.a, eglSurface);
    }

    public final EGLSurface createWindowSurface(Object surface) {
        if ((surface instanceof Surface) || (surface instanceof SurfaceTexture)) {
            EGLSurface eglSurface = EGL14.eglCreateWindowSurface(this.a, this.c, surface, new int[]{12344}, 0);
            a("eglCreateWindowSurface");
            if (eglSurface != null) {
                return eglSurface;
            }
            throw new RuntimeException("surface was null");
        }
        throw new RuntimeException("invalid surface: " + surface);
    }

    public final EGLSurface createOffscreenSurface(int width, int height) {
        EGLSurface eglSurface = EGL14.eglCreatePbufferSurface(this.a, this.c, new int[]{12375, width, 12374, height, 12344}, 0);
        a("eglCreatePbufferSurface");
        if (eglSurface != null) {
            return eglSurface;
        }
        throw new RuntimeException("surface was null");
    }

    public final void makeCurrent(EGLSurface eglSurface) {
        if (this.a == EGL14.EGL_NO_DISPLAY) {
            Log.d("EglCore", "NOTE: makeCurrent w/o display");
        }
        if (!EGL14.eglMakeCurrent(this.a, eglSurface, eglSurface, this.b)) {
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }

    public final void makeCurrent(EGLSurface drawSurface, EGLSurface readSurface) {
        if (this.a == EGL14.EGL_NO_DISPLAY) {
            Log.d("EglCore", "NOTE: makeCurrent w/o display");
        }
        if (!EGL14.eglMakeCurrent(this.a, drawSurface, readSurface, this.b)) {
            throw new RuntimeException("eglMakeCurrent(draw,read) failed");
        }
    }

    public final void makeNothingCurrent() {
        if (!EGL14.eglMakeCurrent(this.a, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT)) {
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }

    public final boolean swapBuffers(EGLSurface eglSurface) {
        return EGL14.eglSwapBuffers(this.a, eglSurface);
    }

    public final void setPresentationTime(EGLSurface eglSurface, long nsecs) {
        EGLExt.eglPresentationTimeANDROID(this.a, eglSurface, nsecs);
    }

    public final boolean isCurrent(EGLSurface eglSurface) {
        return this.b.equals(EGL14.eglGetCurrentContext()) && eglSurface.equals(EGL14.eglGetCurrentSurface(12377));
    }

    public final int querySurface(EGLSurface eglSurface, int what) {
        int[] value = new int[1];
        EGL14.eglQuerySurface(this.a, eglSurface, what, value, 0);
        return value[0];
    }

    public final String queryString(int what) {
        return EGL14.eglQueryString(this.a, what);
    }

    private static void a(String msg) {
        int error = EGL14.eglGetError();
        if (error != 12288) {
            throw new RuntimeException(msg + ": EGL error: 0x" + Integer.toHexString(error));
        }
    }
}
