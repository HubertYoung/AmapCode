package com.autonavi.widget.pulltorefresh.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.autonavi.minimap.R;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;

@SuppressLint({"ViewConstructor"})
public class IndicatorLayout extends FrameLayout implements AnimationListener {
    static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;
    private ImageView mArrowImageView;
    private Animation mInAnim;
    private Animation mOutAnim;
    private final Animation mResetRotateAnimation;
    private final Animation mRotateAnimation;

    /* renamed from: com.autonavi.widget.pulltorefresh.internal.IndicatorLayout$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[Mode.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.autonavi.widget.pulltorefresh.PullToRefreshBase$Mode[] r0 = com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.autonavi.widget.pulltorefresh.PullToRefreshBase$Mode r1 = com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode.PULL_FROM_END     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.autonavi.widget.pulltorefresh.PullToRefreshBase$Mode r1 = com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode.PULL_FROM_START     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.widget.pulltorefresh.internal.IndicatorLayout.AnonymousClass1.<clinit>():void");
        }
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public IndicatorLayout(Context context, Mode mode) {
        int i;
        int i2;
        super(context);
        this.mArrowImageView = new ImageView(context);
        Drawable drawable = getResources().getDrawable(R.drawable.pulltorefresh_refresh_icon);
        this.mArrowImageView.setImageDrawable(drawable);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.pulltorefresh_indicator_internal_padding);
        this.mArrowImageView.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        addView(this.mArrowImageView);
        if (AnonymousClass1.a[mode.ordinal()] != 1) {
            i = R.anim.pulltorefresh_slide_in_from_top;
            i2 = R.anim.pulltorefresh_slide_out_to_top;
            setBackgroundResource(R.drawable.pulltorefresh_refresh_icon);
        } else {
            i = R.anim.pulltorefresh_slide_in_from_bottom;
            int i3 = R.anim.pulltorefresh_slide_out_to_bottom;
            setBackgroundResource(R.drawable.pulltorefresh_refresh_icon);
            this.mArrowImageView.setScaleType(ScaleType.MATRIX);
            Matrix matrix = new Matrix();
            matrix.setRotate(180.0f, ((float) drawable.getIntrinsicWidth()) / 2.0f, ((float) drawable.getIntrinsicHeight()) / 2.0f);
            this.mArrowImageView.setImageMatrix(matrix);
            i2 = i3;
        }
        this.mInAnim = AnimationUtils.loadAnimation(context, i);
        this.mInAnim.setAnimationListener(this);
        this.mOutAnim = AnimationUtils.loadAnimation(context, i2);
        this.mOutAnim.setAnimationListener(this);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, -180.0f, 1, 0.5f, 1, 0.5f);
        this.mRotateAnimation = rotateAnimation;
        this.mRotateAnimation.setInterpolator(linearInterpolator);
        this.mRotateAnimation.setDuration(150);
        this.mRotateAnimation.setFillAfter(true);
        RotateAnimation rotateAnimation2 = new RotateAnimation(-180.0f, 0.0f, 1, 0.5f, 1, 0.5f);
        this.mResetRotateAnimation = rotateAnimation2;
        this.mResetRotateAnimation.setInterpolator(linearInterpolator);
        this.mResetRotateAnimation.setDuration(150);
        this.mResetRotateAnimation.setFillAfter(true);
    }

    public final boolean isVisible() {
        Animation animation = getAnimation();
        return animation != null ? this.mInAnim == animation : getVisibility() == 0;
    }

    public void hide() {
        startAnimation(this.mOutAnim);
    }

    public void show() {
        this.mArrowImageView.clearAnimation();
        startAnimation(this.mInAnim);
    }

    public void onAnimationEnd(Animation animation) {
        if (animation == this.mOutAnim) {
            this.mArrowImageView.clearAnimation();
            setVisibility(8);
        } else if (animation == this.mInAnim) {
            setVisibility(0);
        }
        clearAnimation();
    }

    public void onAnimationStart(Animation animation) {
        setVisibility(0);
    }

    public void releaseToRefresh() {
        this.mArrowImageView.startAnimation(this.mRotateAnimation);
    }

    public void pullToRefresh() {
        this.mArrowImageView.startAnimation(this.mResetRotateAnimation);
    }
}
