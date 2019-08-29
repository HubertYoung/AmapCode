package com.alipay.mobile.beehive.capture.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class CustomBgImageView extends ImageView {
    public CustomBgImageView(Context context) {
        super(context);
    }

    public CustomBgImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomBgImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        updateBgByScaleType();
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        updateBgByScaleType();
    }

    private void updateBgByScaleType() {
        if (getScaleType() == ScaleType.FIT_CENTER) {
            setBackgroundColor(-16777216);
        } else {
            setBackgroundColor(0);
        }
    }
}
