package com.alipay.mobile.scansdk.ui;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.lang.reflect.Method;

public class APSurfaceTexture extends SurfaceTexture {
    private static final String TAG = "APSurfaceTexture";
    public SurfaceTexture mSurface = null;

    public APSurfaceTexture() {
        super(0);
    }

    @TargetApi(16)
    public void attachToGLContext(int texName) {
        this.mSurface.attachToGLContext(texName);
    }

    @TargetApi(16)
    public void detachFromGLContext() {
        try {
            this.mSurface.detachFromGLContext();
        } catch (Exception ex) {
            try {
                Method nativeMethod = SurfaceTexture.class.getDeclaredMethod("nativeDetachFromGLContext", new Class[0]);
                nativeMethod.setAccessible(true);
                LoggerFactory.getTraceLogger().debug(TAG, "nativeDetachFromGLContext invoke retCode:" + ((Integer) nativeMethod.invoke(this.mSurface, new Object[0])).intValue());
            } catch (Exception e) {
                LoggerFactory.getTraceLogger().error((String) TAG, "nativeDetachFromGLContext invoke exception:" + e.getMessage());
            }
            LoggerFactory.getTraceLogger().error((String) TAG, "mSurface.detachFromGLContext() exception:" + ex.getMessage());
        }
    }

    public boolean equals(Object o) {
        return this.mSurface.equals(o);
    }

    public long getTimestamp() {
        return this.mSurface.getTimestamp();
    }

    public void getTransformMatrix(float[] mtx) {
        this.mSurface.getTransformMatrix(mtx);
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
    public void setDefaultBufferSize(int width, int height) {
        this.mSurface.setDefaultBufferSize(width, height);
    }

    public void setOnFrameAvailableListener(OnFrameAvailableListener listener) {
        this.mSurface.setOnFrameAvailableListener(listener);
    }

    public String toString() {
        return this.mSurface.toString();
    }

    public void updateTexImage() {
        this.mSurface.updateTexImage();
    }
}
