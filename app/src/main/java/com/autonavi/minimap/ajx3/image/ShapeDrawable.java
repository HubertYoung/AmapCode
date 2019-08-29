package com.autonavi.minimap.ajx3.image;

import android.content.res.Resources;
import android.graphics.Bitmap;

public class ShapeDrawable extends android.graphics.drawable.ShapeDrawable {
    private Bitmap mBitmap;

    public ShapeDrawable(Resources resources, PictureParams pictureParams, Bitmap bitmap) {
        super(pictureParams.canSupportBorderClip ? new AlmightyShapeWithoutBorder(resources, pictureParams, bitmap) : new AlmightyShape(resources, pictureParams, bitmap));
        this.mBitmap = bitmap;
    }

    public int getIntrinsicWidth() {
        int intrinsicWidth = super.getIntrinsicWidth();
        return (intrinsicWidth > 0 || this.mBitmap == null) ? intrinsicWidth : this.mBitmap.getWidth();
    }

    public int getIntrinsicHeight() {
        int intrinsicHeight = super.getIntrinsicHeight();
        return (intrinsicHeight > 0 || this.mBitmap == null) ? intrinsicHeight : this.mBitmap.getHeight();
    }
}
