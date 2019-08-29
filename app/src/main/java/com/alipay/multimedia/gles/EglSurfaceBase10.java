package com.alipay.multimedia.gles;

import android.annotation.TargetApi;
import com.alipay.alipaylogger.Log;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLSurface;

@TargetApi(17)
public class EglSurfaceBase10 {
    protected EglCore10 a;
    private EGLSurface b = EGL10.EGL_NO_SURFACE;
    private int c = -1;
    private int d = -1;

    protected EglSurfaceBase10(EglCore10 eglCore) {
        this.a = eglCore;
    }

    public void createWindowSurface(Object surface) {
        if (this.b != EGL10.EGL_NO_SURFACE) {
            throw new IllegalStateException("surface already created");
        }
        this.b = this.a.createWindowSurface(surface);
    }

    public void createOffscreenSurface(int width, int height) {
        if (this.b != EGL10.EGL_NO_SURFACE) {
            throw new IllegalStateException("surface already created");
        }
        this.b = this.a.createOffscreenSurface(width, height);
        this.c = width;
        this.d = height;
    }

    public int getWidth() {
        if (this.c < 0) {
            return this.a.querySurface(this.b, 12375);
        }
        return this.c;
    }

    public int getHeight() {
        if (this.d < 0) {
            return this.a.querySurface(this.b, 12374);
        }
        return this.d;
    }

    public void releaseEglSurface() {
        this.a.releaseSurface(this.b);
        this.b = EGL10.EGL_NO_SURFACE;
        this.d = -1;
        this.c = -1;
    }

    public void makeCurrent() {
        this.a.makeCurrent(this.b);
    }

    public void makeCurrentReadFrom(EglSurfaceBase10 readSurface) {
        this.a.makeCurrent(this.b, readSurface.b);
    }

    public boolean swapBuffers() {
        boolean result = this.a.swapBuffers(this.b);
        if (!result) {
            Log.d(GlUtil.TAG, "WARNING: swapBuffers() failed");
        }
        return result;
    }

    public void setPresentationTime(long nsecs) {
        this.a.setPresentationTime(this.b, nsecs);
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x008f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void saveFrame(java.io.File r12) {
        /*
            r11 = this;
            r0 = 0
            com.alipay.multimedia.gles.EglCore10 r1 = r11.a
            javax.microedition.khronos.egl.EGLSurface r4 = r11.b
            boolean r1 = r1.isCurrent(r4)
            if (r1 != 0) goto L_0x0013
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "Expected EGL context/surface is not current"
            r0.<init>(r1)
            throw r0
        L_0x0013:
            java.lang.String r10 = r12.toString()
            int r2 = r11.getWidth()
            int r3 = r11.getHeight()
            int r1 = r2 * r3
            int r1 = r1 * 4
            java.nio.ByteBuffer r6 = java.nio.ByteBuffer.allocateDirect(r1)
            java.nio.ByteOrder r1 = java.nio.ByteOrder.LITTLE_ENDIAN
            r6.order(r1)
            r4 = 6408(0x1908, float:8.98E-42)
            r5 = 5121(0x1401, float:7.176E-42)
            r1 = r0
            android.opengl.GLES20.glReadPixels(r0, r1, r2, r3, r4, r5, r6)
            java.lang.String r0 = "glReadPixels"
            com.alipay.multimedia.gles.GlUtil.checkGlError(r0)
            r6.rewind()
            r8 = 0
            java.io.BufferedOutputStream r9 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x008c }
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ all -> 0x008c }
            r0.<init>(r10)     // Catch:{ all -> 0x008c }
            r9.<init>(r0)     // Catch:{ all -> 0x008c }
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.ARGB_8888     // Catch:{ all -> 0x0093 }
            android.graphics.Bitmap r7 = android.graphics.Bitmap.createBitmap(r2, r3, r0)     // Catch:{ all -> 0x0093 }
            r7.copyPixelsFromBuffer(r6)     // Catch:{ all -> 0x0093 }
            android.graphics.Bitmap$CompressFormat r0 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ all -> 0x0093 }
            r1 = 70
            r7.compress(r0, r1, r9)     // Catch:{ all -> 0x0093 }
            r7.recycle()     // Catch:{ all -> 0x0093 }
            r9.close()
            java.lang.String r0 = "Alipay"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r4 = "Saved "
            r1.<init>(r4)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r4 = "x"
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r4 = " frame as '"
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.StringBuilder r1 = r1.append(r10)
            java.lang.String r4 = "'"
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r1 = r1.toString()
            com.alipay.alipaylogger.Log.d(r0, r1)
            return
        L_0x008c:
            r0 = move-exception
        L_0x008d:
            if (r8 == 0) goto L_0x0092
            r8.close()
        L_0x0092:
            throw r0
        L_0x0093:
            r0 = move-exception
            r8 = r9
            goto L_0x008d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.multimedia.gles.EglSurfaceBase10.saveFrame(java.io.File):void");
    }
}
