package com.autonavi.minimap.acanvas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Build.VERSION;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;

@SuppressLint({"ViewConstructor"})
public class ACanvasView extends TextureView implements SurfaceTextureListener {
    private final int DEFAULT_VALUE = -1;
    private ACanvasContext2D mContext2D;
    private int mHeight = -1;
    private SurfaceTextureListener mListener;
    private Surface mSurface;
    private SurfaceTexture mSurfaceTexture;
    private int mWidth = -1;

    public ACanvasView(Context context) {
        super(context);
        setOpaque(false);
        setLayerType(2, null);
        super.setSurfaceTextureListener(this);
    }

    public String getCanvasId() {
        if (this.mContext2D != null) {
            return this.mContext2D.getCanvasId();
        }
        return null;
    }

    public void bindContext2D(ACanvasContext2D aCanvasContext2D) {
        if (aCanvasContext2D == null) {
            throw new RuntimeException("ACanvasView bind context2D can't empty!");
        }
        this.mContext2D = aCanvasContext2D;
        if (this.mWidth == -1 || this.mHeight == -1) {
            this.mWidth = this.mContext2D.getWidth();
            this.mHeight = this.mContext2D.getHeight();
        } else {
            this.mContext2D.onSizeChanged(this.mWidth, this.mHeight);
        }
        if (this.mSurface != null) {
            this.mContext2D.onSurfaceChanged(this.mSurface);
        }
        StringBuilder sb = new StringBuilder("bindContext2D. ");
        sb.append(this.mContext2D);
        ACanvasLog.i(sb.toString());
    }

    public void release() {
        if (this.mSurface != null) {
            this.mSurface.release();
            this.mSurface = null;
        }
        if (this.mSurfaceTexture != null) {
            this.mSurfaceTexture.release();
            this.mSurfaceTexture = null;
        }
    }

    public void setSurfaceTextureListener(SurfaceTextureListener surfaceTextureListener) {
        this.mListener = surfaceTextureListener;
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        StringBuilder sb = new StringBuilder("onSurfaceTextureAvailable. ");
        sb.append(this.mContext2D);
        ACanvasLog.d(sb.toString());
        onSurfaceChanged(surfaceTexture, i, i2);
        if (this.mListener != null) {
            this.mListener.onSurfaceTextureAvailable(surfaceTexture, i, i2);
        }
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        StringBuilder sb = new StringBuilder("onSurfaceTextureSizeChanged. ");
        sb.append(this.mContext2D);
        ACanvasLog.d(sb.toString());
        onSurfaceChanged(surfaceTexture, i, i2);
        if (this.mListener != null) {
            this.mListener.onSurfaceTextureSizeChanged(surfaceTexture, i, i2);
        }
    }

    private void onSurfaceChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        if (!(this.mWidth == i && this.mHeight == i2)) {
            this.mWidth = i;
            this.mHeight = i2;
            if (this.mContext2D != null) {
                this.mContext2D.onSizeChanged(i, i2);
            }
        }
        if (this.mSurfaceTexture != surfaceTexture) {
            if (this.mSurfaceTexture == null || VERSION.SDK_INT < 16) {
                this.mSurfaceTexture = surfaceTexture;
                if (this.mSurface != null) {
                    this.mSurface.release();
                    this.mSurface = null;
                }
                this.mSurface = new Surface(surfaceTexture);
                if (this.mContext2D != null) {
                    this.mContext2D.onSurfaceChanged(this.mSurface);
                }
            } else {
                setSurfaceTexture(this.mSurfaceTexture);
            }
        }
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        StringBuilder sb = new StringBuilder("onSurfaceTextureDestroyed. ");
        sb.append(this.mContext2D);
        ACanvasLog.d(sb.toString());
        if (this.mListener != null) {
            this.mListener.onSurfaceTextureDestroyed(surfaceTexture);
        }
        if (this.mContext2D == null) {
            release();
        }
        return false;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        if (this.mListener != null) {
            this.mListener.onSurfaceTextureUpdated(surfaceTexture);
        }
    }

    public void redraw() {
        if (this.mContext2D != null) {
            this.mContext2D.redraw();
        }
    }
}
