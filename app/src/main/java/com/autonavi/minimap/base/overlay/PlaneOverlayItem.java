package com.autonavi.minimap.base.overlay;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.autonavi.minimap.base.overlay.PlaneOverlay;

public abstract class PlaneOverlayItem<E extends PlaneOverlay> {
    private Bitmap mBitmap;
    public int mHeight;
    public int mIndex;
    public long mItemId;
    public Marker mMarker;
    public int mWidth;
    public int mWinX;
    public int mWinY;

    /* access modifiers changed from: protected */
    @NonNull
    public abstract Bitmap generateBitmap();

    public PlaneOverlayItem() {
    }

    public PlaneOverlayItem(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    public Bitmap getBitmap() {
        if (this.mBitmap == null) {
            this.mBitmap = generateBitmap();
        }
        return this.mBitmap;
    }
}
