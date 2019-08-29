package com.alipay.mobile.scansdk.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import com.alipay.android.phone.scancode.export.R;
import com.autonavi.widget.ui.BalloonLayout;

public class ScanRayView extends ImageView {
    private ScaleAnimation animation;
    private boolean isAnimate = false;
    private ScaleFinderView mFinderView = null;

    public ScanRayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScanRayView(Context context) {
        super(context);
    }

    public void draw(Canvas canvas) {
        int[] location = new int[2];
        getLocationOnScreen(location);
        if (this.mFinderView != null) {
            int[] l = new int[2];
            this.mFinderView.getLocationInWindow(l);
            location[1] = location[1] - l[1];
            this.mFinderView.setTargetLocation(location[0], location[1], location[0] + getWidth(), location[1] + getHeight());
        }
        super.draw(canvas);
    }

    public void startScaleAnimation() {
        setImageResource(R.drawable.scan_ray);
        if (this.animation == null) {
            this.animation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
            this.animation.setDuration(BalloonLayout.DEFAULT_DISPLAY_DURATION);
            this.animation.setFillAfter(true);
            this.animation.setRepeatCount(-1);
            this.animation.setInterpolator(new AccelerateDecelerateInterpolator());
            startAnimation(this.animation);
            this.isAnimate = true;
        }
    }

    public void setFinderView(ScaleFinderView FinderView) {
        this.mFinderView = FinderView;
    }

    public void stopScaleAnimation() {
        setImageResource(0);
        if (this.animation != null) {
            clearAnimation();
            this.animation = null;
            this.isAnimate = false;
        }
    }

    public boolean isAnimate() {
        return this.isAnimate;
    }
}
