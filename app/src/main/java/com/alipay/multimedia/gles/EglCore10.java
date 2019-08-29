package com.alipay.multimedia.gles;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.alipay.alipaylogger.Log;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

@TargetApi(18)
public final class EglCore10 {
    public static final int FLAG_RECORDABLE = 1;
    public static final int FLAG_TRY_GLES3 = 2;
    final int a;
    final int b;
    private EGLDisplay c;
    private EGLContext d;
    private EGLConfig e;
    private int f;
    private EGL10 g;
    private int[] h;

    public EglCore10() {
        this(null, 0);
    }

    public EglCore10(EGLContext sharedContext, int flags) {
        this.c = EGL10.EGL_NO_DISPLAY;
        this.d = EGL10.EGL_NO_CONTEXT;
        this.e = null;
        this.f = -1;
        this.a = 4;
        this.b = 12440;
        if (this.c != EGL10.EGL_NO_DISPLAY) {
            throw new RuntimeException("EGL already set up, flag: " + flags);
        }
        sharedContext = sharedContext == null ? EGL10.EGL_NO_CONTEXT : sharedContext;
        this.g = (EGL10) EGLContext.getEGL();
        this.c = this.g.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        if (this.c == EGL10.EGL_NO_DISPLAY) {
            throw new RuntimeException("unable to get EGL10 display");
        }
        int[] version = new int[2];
        if (!this.g.eglInitialize(this.c, version)) {
            this.c = null;
            throw new RuntimeException("unable to initialize EGL10");
        }
        Log.d("EglCore", "after eglInitialize, version:" + version[0] + ":" + version[1]);
        if (this.d == EGL10.EGL_NO_CONTEXT) {
            EGLConfig config = chooseConfig(this.g, this.c);
            if (config == null) {
                throw new RuntimeException("Unable to find a suitable EGLConfig");
            }
            EGLContext context = this.g.eglCreateContext(this.c, config, sharedContext, new int[]{12440, 2, 12344});
            a("eglCreateContext");
            this.e = config;
            this.d = context;
            this.f = 2;
        }
        int[] values = new int[1];
        this.g.eglQueryContext(this.c, this.d, 12440, values);
        Log.d("EglCore", "EGLContext created, client version " + values[0]);
    }

    public final void release() {
        if (this.c != EGL10.EGL_NO_DISPLAY) {
            this.g.eglMakeCurrent(this.c, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            this.g.eglDestroyContext(this.c, this.d);
            this.g.eglTerminate(this.c);
        }
        this.c = EGL10.EGL_NO_DISPLAY;
        this.d = EGL10.EGL_NO_CONTEXT;
        this.e = null;
    }

    /* access modifiers changed from: protected */
    public final void finalize() {
        try {
            if (this.c != EGL10.EGL_NO_DISPLAY) {
                Log.d("EglCore", "WARNING: EglCore was not explicitly released -- state may be leaked");
                release();
            }
        } finally {
            super.finalize();
        }
    }

    public final void releaseSurface(EGLSurface eglSurface) {
        this.g.eglDestroySurface(this.c, eglSurface);
    }

    public final EGLSurface createWindowSurface(Object surface) {
        if ((surface instanceof Surface) || (surface instanceof SurfaceTexture) || (surface instanceof SurfaceHolder)) {
            EGLSurface eglSurface = this.g.eglCreateWindowSurface(this.c, this.e, surface, new int[]{12344});
            a("eglCreateWindowSurface");
            if (eglSurface != null) {
                return eglSurface;
            }
            throw new RuntimeException("surface was null");
        }
        throw new RuntimeException("invalid surface: " + surface);
    }

    public final EGLSurface createOffscreenSurface(int width, int height) {
        EGLSurface eglSurface = this.g.eglCreatePbufferSurface(this.c, this.e, new int[]{12375, width, 12374, height, 12344});
        a("eglCreatePbufferSurface");
        if (eglSurface != null) {
            return eglSurface;
        }
        throw new RuntimeException("surface was null");
    }

    public final void makeCurrent(EGLSurface eglSurface) {
        if (this.c == EGL10.EGL_NO_DISPLAY) {
            Log.d("EglCore", "NOTE: makeCurrent w/o display");
        }
        if (!this.g.eglMakeCurrent(this.c, eglSurface, eglSurface, this.d)) {
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }

    public final void makeCurrent(EGLSurface drawSurface, EGLSurface readSurface) {
        if (this.c == EGL10.EGL_NO_DISPLAY) {
            Log.d("EglCore", "NOTE: makeCurrent w/o display");
        }
        if (!this.g.eglMakeCurrent(this.c, drawSurface, readSurface, this.d)) {
            throw new RuntimeException("eglMakeCurrent(draw,read) failed");
        }
    }

    public final void makeNothingCurrent() {
        if (!this.g.eglMakeCurrent(this.c, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT)) {
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }

    public final boolean swapBuffers(EGLSurface eglSurface) {
        return this.g.eglSwapBuffers(this.c, eglSurface);
    }

    public final void setPresentationTime(EGLSurface eglSurface, long nsecs) {
    }

    public final boolean isCurrent(EGLSurface eglSurface) {
        return this.d.equals(this.g.eglGetCurrentContext()) && eglSurface.equals(this.g.eglGetCurrentSurface(12377));
    }

    public final int querySurface(EGLSurface eglSurface, int what) {
        int[] value = new int[1];
        this.g.eglQuerySurface(this.c, eglSurface, what, value);
        return value[0];
    }

    public final String queryString(int what) {
        return this.g.eglQueryString(this.c, what);
    }

    public final int getGlVersion() {
        return this.f;
    }

    public static void logCurrent(String msg) {
    }

    private void a(String msg) {
        int error = this.g.eglGetError();
        if (error != 12288) {
            throw new RuntimeException(msg + ": EGL error: 0x" + Integer.toHexString(error));
        }
    }

    public final EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
        EGLConfig config;
        this.h = new int[1];
        int[] configSpec = {12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 16, 12352, 4, 12338, 1, 12337, 4, 12344};
        int numConfigs = this.h[0];
        if (numConfigs <= 0) {
            configSpec = new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 16, 12352, 4, 12512, 1, 12513, 2, 12344};
            numConfigs = this.h[0];
            if (numConfigs <= 0) {
                configSpec = new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 16, 12352, 4, 12344};
                if (!egl.eglChooseConfig(display, configSpec, null, 0, this.h)) {
                    throw new IllegalArgumentException("3rd eglChooseConfig failed");
                }
                numConfigs = this.h[0];
                if (numConfigs <= 0) {
                    throw new IllegalArgumentException("No configs match configSpec");
                }
            }
        }
        EGLConfig[] configs = new EGLConfig[numConfigs];
        if (!egl.eglChooseConfig(display, configSpec, configs, numConfigs, this.h)) {
            throw new IllegalArgumentException("data eglChooseConfig failed");
        }
        int index = -1;
        int i = 0;
        while (true) {
            if (i >= configs.length) {
                break;
            } else if (a(egl, display, configs[i]) == 8) {
                index = i;
                break;
            } else {
                i++;
            }
        }
        if (index == -1) {
            Log.d("EglCore", "Did not find sane config, using first");
        }
        if (configs.length > 0) {
            config = configs[index];
        } else {
            config = null;
        }
        if (config != null) {
            return config;
        }
        throw new IllegalArgumentException("No config chosen");
    }

    private int a(EGL10 egl, EGLDisplay display, EGLConfig config) {
        if (egl.eglGetConfigAttrib(display, config, 12321, this.h)) {
            return this.h[0];
        }
        return 0;
    }

    public final GL10 getGl10() {
        return (GL10) this.d.getGL();
    }
}
