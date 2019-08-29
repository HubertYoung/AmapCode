package com.autonavi.minimap.ajx3.widget.view.video.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;
import com.autonavi.minimap.ajx3.widget.view.video.util.Utils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class VideoTextureView extends TextureView implements VideoSizeChangedListener {
    public static final int SCALE_TYPE_CENTER = 0;
    public static final int SCALE_TYPE_CENTER_CROP = 2;
    public static final int SCALE_TYPE_CENTER_INSIDE = 3;
    public static final int SCALE_TYPE_FIX_XY = 1;
    private static Field mSurfaceField;
    private static Field mUpdateSurfaceField;
    private static Method nCreateNativeWindowMethod;
    private static Method nDestroyNativeWindowMethod;
    private static Class[] paraTypes = {SurfaceTexture.class};
    private int mScaleType = 3;
    public int mVideoHeight = 0;
    public int mVideoWidth = 0;

    public VideoTextureView(Context context) {
        super(context);
    }

    public VideoTextureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public VideoTextureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @TargetApi(21)
    public VideoTextureView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public void onVideoSizeChanged(int i, int i2) {
        setVideoSize(i, i2);
    }

    public void setVideoSize(int i, int i2) {
        if (this.mVideoWidth != i || this.mVideoHeight != i2) {
            this.mVideoWidth = i;
            this.mVideoHeight = i2;
            requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int defaultSize = getDefaultSize(getSuggestedMinimumWidth(), i);
        int defaultSize2 = getDefaultSize(getSuggestedMinimumHeight(), i2);
        switch (this.mScaleType) {
            case 0:
                updateScaleTypeCenter(defaultSize, defaultSize2);
                return;
            case 1:
                updateScaleTypeFixXy(defaultSize, defaultSize2);
                return;
            case 2:
                updateScaleTypeCenterCrop(defaultSize, defaultSize2);
                return;
            case 3:
                updateScaleTypeCenterInside(defaultSize, defaultSize2);
                break;
        }
    }

    public void setScaleType(int i) {
        if (i >= 0 && i <= 3) {
            this.mScaleType = i;
            requestLayout();
        }
    }

    private void updateScaleTypeCenter(int i, int i2) {
        if (this.mVideoWidth <= 0 || this.mVideoHeight <= 0) {
            setMeasuredDimension(i, i2);
        } else {
            setMeasuredDimension(this.mVideoWidth, this.mVideoHeight);
        }
    }

    private void updateScaleTypeFixXy(int i, int i2) {
        setMeasuredDimension(i, i2);
    }

    private void updateScaleTypeCenterCrop(int i, int i2) {
        if (this.mVideoWidth > 0 && this.mVideoHeight > this.mVideoWidth) {
            setMeasuredDimension(i, (this.mVideoHeight * i) / this.mVideoWidth);
        } else if (this.mVideoHeight <= 0 || this.mVideoWidth <= this.mVideoHeight) {
            setMeasuredDimension(i, i2);
        } else {
            setMeasuredDimension((this.mVideoWidth * i2) / this.mVideoHeight, i2);
        }
    }

    private void updateScaleTypeCenterInside(int i, int i2) {
        if (this.mVideoWidth > 0 && this.mVideoHeight > this.mVideoWidth) {
            setMeasuredDimension((this.mVideoWidth * i2) / this.mVideoHeight, i2);
        } else if (this.mVideoHeight <= 0 || this.mVideoWidth <= this.mVideoHeight) {
            setMeasuredDimension(i, i2);
        } else {
            setMeasuredDimension(i, (this.mVideoHeight * i) / this.mVideoWidth);
        }
    }

    private void initInvokeVariablesIfNeeded() throws NoSuchFieldException, NoSuchMethodException {
        if (mSurfaceField == null) {
            Field declaredField = TextureView.class.getDeclaredField("mSurface");
            mSurfaceField = declaredField;
            declaredField.setAccessible(true);
        }
        if (mUpdateSurfaceField == null) {
            Field declaredField2 = TextureView.class.getDeclaredField("mUpdateSurface");
            mUpdateSurfaceField = declaredField2;
            declaredField2.setAccessible(true);
        }
        if (nCreateNativeWindowMethod == null) {
            Method declaredMethod = TextureView.class.getDeclaredMethod("nCreateNativeWindow", paraTypes);
            nCreateNativeWindowMethod = declaredMethod;
            declaredMethod.setAccessible(true);
        }
        if (nDestroyNativeWindowMethod == null) {
            Method declaredMethod2 = TextureView.class.getDeclaredMethod("nDestroyNativeWindow", new Class[0]);
            nDestroyNativeWindowMethod = declaredMethod2;
            declaredMethod2.setAccessible(true);
        }
    }

    public void setSurfaceTexture(SurfaceTexture surfaceTexture) {
        if (VERSION.SDK_INT <= 16) {
            super.setSurfaceTexture(surfaceTexture);
            return;
        }
        try {
            initInvokeVariablesIfNeeded();
            SurfaceTexture surfaceTexture2 = (SurfaceTexture) mSurfaceField.get(this);
            if (surfaceTexture == null) {
                throw new NullPointerException("surfaceTexture must not be null");
            } else if (surfaceTexture == surfaceTexture2) {
                throw new IllegalArgumentException("Trying to setSurfaceTexture to the same SurfaceTexture that's already set.");
            } else {
                if (surfaceTexture2 != null) {
                    nDestroyNativeWindowMethod.invoke(this, new Object[0]);
                    surfaceTexture2.release();
                }
                mSurfaceField.set(this, surfaceTexture);
                nCreateNativeWindowMethod.invoke(this, new Object[]{surfaceTexture2});
                mUpdateSurfaceField.set(this, Boolean.TRUE);
                if (isHardwareAccelerated() && (getParent() instanceof View)) {
                    ((View) getParent()).invalidate();
                }
            }
        } catch (Exception e) {
            Utils.log(e.toString());
        }
    }
}
