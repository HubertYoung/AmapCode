package com.alipay.mobile.beehive.photo.view.video;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class VideoCoverDisplayView extends ImageView {
    public VideoCoverDisplayView(Context context) {
        super(context);
    }

    public VideoCoverDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoCoverDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (drawable instanceof BitmapDrawable) {
            adaptScaleType(((BitmapDrawable) drawable).getBitmap());
        }
    }

    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        adaptScaleType(bm);
    }

    private void adaptScaleType(Bitmap bmp) {
        if (bmp == null) {
            return;
        }
        if (bmp.getWidth() >= bmp.getHeight()) {
            setScaleType(ScaleType.FIT_CENTER);
        } else {
            setScaleType(ScaleType.CENTER_CROP);
        }
    }
}
