package com.alipay.mobile.scansdk.ui;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.TextureView;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.lang.reflect.Field;

public class APTextureView extends TextureView {
    private static final String TAG = "APTextureView";
    private Field mSurfaceField = null;

    public APTextureView(Context context) {
        super(context);
    }

    public APTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public APTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        try {
            super.onDetachedFromWindow();
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().error((String) TAG, "onDetachedFromWindow exception:" + ex.getMessage());
        }
    }

    public void setSurfaceTexture(SurfaceTexture surfaceTexture) {
        super.setSurfaceTexture(surfaceTexture);
        afterSetSurfaceTexture();
    }

    private void afterSetSurfaceTexture() {
        LoggerFactory.getTraceLogger().debug(TAG, "afterSetSurfaceTexture Build.VERSION.SDK_INT:" + VERSION.SDK_INT);
        if (VERSION.SDK_INT >= 16 && VERSION.SDK_INT <= 20) {
            try {
                if (this.mSurfaceField == null) {
                    this.mSurfaceField = TextureView.class.getDeclaredField("mSurface");
                    this.mSurfaceField.setAccessible(true);
                }
                SurfaceTexture innerSurface = (SurfaceTexture) this.mSurfaceField.get(this);
                if (innerSurface != null && !(innerSurface instanceof APSurfaceTexture)) {
                    APSurfaceTexture wrapSurface = new APSurfaceTexture();
                    wrapSurface.mSurface = innerSurface;
                    this.mSurfaceField.set(this, wrapSurface);
                    LoggerFactory.getTraceLogger().debug(TAG, "afterSetSurfaceTexture wrap mSurface");
                }
            } catch (Exception ex) {
                LoggerFactory.getTraceLogger().error((String) TAG, "afterSetSurfaceTexture exception:" + ex.getMessage());
            }
        }
    }
}
