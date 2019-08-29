package com.autonavi.minimap.bundle.qrscan.scanner;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import java.lang.reflect.Method;

public class APSurfaceTexture extends SurfaceTexture {
    private static final String TAG = "APSurfaceTexture";
    public SurfaceTexture mSurface = null;

    public APSurfaceTexture() {
        super(0);
    }

    @TargetApi(16)
    public void attachToGLContext(int i) {
        this.mSurface.attachToGLContext(i);
    }

    @TargetApi(16)
    public void detachFromGLContext() {
        try {
            this.mSurface.detachFromGLContext();
        } catch (Exception e) {
            try {
                Method declaredMethod = SurfaceTexture.class.getDeclaredMethod("nativeDetachFromGLContext", new Class[0]);
                declaredMethod.setAccessible(true);
                ((Integer) declaredMethod.invoke(this.mSurface, new Object[0])).intValue();
            } catch (Exception e2) {
                new StringBuilder("nativeDetachFromGLContext invoke exception:").append(e2.getMessage());
            }
            new StringBuilder("mSurface.detachFromGLContext() exception:").append(e.getMessage());
        }
    }

    public boolean equals(Object obj) {
        return this.mSurface.equals(obj);
    }

    public long getTimestamp() {
        return this.mSurface.getTimestamp();
    }

    public void getTransformMatrix(float[] fArr) {
        this.mSurface.getTransformMatrix(fArr);
    }

    public void release() {
        super.release();
        this.mSurface.release();
    }

    public int hashCode() {
        return this.mSurface.hashCode();
    }

    @TargetApi(19)
    public void releaseTexImage() {
        this.mSurface.releaseTexImage();
    }

    @TargetApi(15)
    public void setDefaultBufferSize(int i, int i2) {
        this.mSurface.setDefaultBufferSize(i, i2);
    }

    public void setOnFrameAvailableListener(OnFrameAvailableListener onFrameAvailableListener) {
        this.mSurface.setOnFrameAvailableListener(onFrameAvailableListener);
    }

    public String toString() {
        return this.mSurface.toString();
    }

    public void updateTexImage() {
        this.mSurface.updateTexImage();
    }
}
