package com.autonavi.widget.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class BalloonImg extends BalloonLayout {
    private ImageView mImageView;

    /* access modifiers changed from: protected */
    public boolean shouldDrawBalloon() {
        return false;
    }

    public BalloonImg(Context context) {
        this(context, null);
    }

    public BalloonImg(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BalloonImg(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void init(Context context) {
        super.init(context);
        this.mImageView = new ImageView(context);
        addView(this.mImageView);
    }

    public void setScaleType(ScaleType scaleType) {
        this.mImageView.setScaleType(scaleType);
    }

    public void setImageResource(int i) {
        this.mImageView.setImageResource(i);
    }

    public void setImageDrawable(Drawable drawable) {
        this.mImageView.setImageDrawable(drawable);
    }
}
