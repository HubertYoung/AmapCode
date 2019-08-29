package com.autonavi.minimap.bundle.qrscan.scanner;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.TextureView;
import java.lang.reflect.Field;

public class APTextureView extends TextureView {
    private static final String TAG = "APTextureView";
    private Field mSurfaceField = null;

    public APTextureView(Context context) {
        super(context);
    }

    public APTextureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public APTextureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        try {
            super.onDetachedFromWindow();
        } catch (Exception e) {
            new StringBuilder("onDetachedFromWindow exception:").append(e.getMessage());
        }
    }

    public void setSurfaceTexture(SurfaceTexture surfaceTexture) {
        super.setSurfaceTexture(surfaceTexture);
        afterSetSurfaceTexture();
    }

    private void afterSetSurfaceTexture() {
        new StringBuilder("afterSetSurfaceTexture Build.VERSION.SDK_INT:").append(VERSION.SDK_INT);
        if (VERSION.SDK_INT >= 16 && VERSION.SDK_INT <= 20) {
            try {
                if (this.mSurfaceField == null) {
                    this.mSurfaceField = TextureView.class.getDeclaredField("mSurface");
                    this.mSurfaceField.setAccessible(true);
                }
                SurfaceTexture surfaceTexture = (SurfaceTexture) this.mSurfaceField.get(this);
                if (surfaceTexture != null && !(surfaceTexture instanceof APSurfaceTexture)) {
                    APSurfaceTexture aPSurfaceTexture = new APSurfaceTexture();
                    aPSurfaceTexture.mSurface = surfaceTexture;
                    this.mSurfaceField.set(this, aPSurfaceTexture);
                }
            } catch (Exception e) {
                new StringBuilder("afterSetSurfaceTexture exception:").append(e.getMessage());
            }
        }
    }
}
