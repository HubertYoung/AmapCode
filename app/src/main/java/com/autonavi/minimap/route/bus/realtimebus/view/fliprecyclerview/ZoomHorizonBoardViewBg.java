package com.autonavi.minimap.route.bus.realtimebus.view.fliprecyclerview;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View.OnLayoutChangeListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import com.autonavi.minimap.R;

public class ZoomHorizonBoardViewBg extends LinearLayout {
    private static final float ZOOM_ANIMATION_TIME = 400.0f;
    private int FROM_CORNER;
    private int MAX_CORNER;
    private int SHADOW_PADDING;
    private int SHADOW_X_DETA = 0;
    private int SHADOW_Y_DETA;
    private int WIDTH;
    private Paint mBackgroundPaint;
    private Paint mShadowPaint;
    /* access modifiers changed from: private */
    public ValueAnimator outAnimator;
    /* access modifiers changed from: private */
    public int trans;
    private ValueAnimator valueAnimator;

    public void addOnLayoutChangeListener(OnLayoutChangeListener onLayoutChangeListener) {
        super.addOnLayoutChangeListener(onLayoutChangeListener);
    }

    public ZoomHorizonBoardViewBg(Context context) {
        super(context);
        initBgPaint();
    }

    public ZoomHorizonBoardViewBg(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initBgPaint();
    }

    public ZoomHorizonBoardViewBg(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initBgPaint();
    }

    public ZoomHorizonBoardViewBg(Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initBgPaint();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.valueAnimator == null || !this.valueAnimator.isRunning()) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    private void initBgPaint() {
        setWillNotDraw(false);
        this.FROM_CORNER = agn.a(getContext(), 44.0f);
        this.MAX_CORNER = agn.a(getContext(), 6.0f);
        this.SHADOW_PADDING = agn.a(getContext(), 3.0f);
        this.SHADOW_Y_DETA = agn.a(getContext(), 1.0f);
        this.WIDTH = ags.a(getContext()).width() / 4;
        setMinimumHeight((this.FROM_CORNER * 2) + (this.SHADOW_PADDING * 2));
        this.mBackgroundPaint = new Paint();
        this.mBackgroundPaint.setColor(getResources().getColor(R.color.f_c_1));
        this.mBackgroundPaint.setAntiAlias(true);
        this.mShadowPaint = new Paint();
        this.mShadowPaint.setColor(getResources().getColor(R.color.c_t));
        this.mShadowPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OVER));
        this.mShadowPaint.setShadowLayer((float) this.SHADOW_PADDING, (float) this.SHADOW_X_DETA, (float) this.SHADOW_Y_DETA, Color.argb(64, 0, 0, 0));
        this.mShadowPaint.setAntiAlias(true);
        setLayerType(1, null);
    }

    public void startAnimation(boolean z) {
        if (this.valueAnimator != null && this.valueAnimator.isRunning()) {
            this.valueAnimator.cancel();
        }
        int[] iArr = new int[2];
        int i = 0;
        iArr[0] = z ? this.WIDTH : 0;
        if (!z) {
            i = this.WIDTH;
        }
        iArr[1] = i;
        this.valueAnimator = ValueAnimator.ofInt(iArr);
        this.valueAnimator.setDuration(400);
        this.valueAnimator.setInterpolator(new DecelerateInterpolator());
        this.valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ZoomHorizonBoardViewBg.this.trans = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                ZoomHorizonBoardViewBg.this.postInvalidate();
            }
        });
        this.valueAnimator.start();
    }

    public void stopAnimation(final AnimatorListener animatorListener) {
        if (this.outAnimator == null) {
            if (this.outAnimator != null && this.outAnimator.isRunning()) {
                this.outAnimator.cancel();
            }
            this.outAnimator = ValueAnimator.ofInt(new int[]{0, this.WIDTH});
            this.outAnimator.setDuration(400);
            this.outAnimator.setInterpolator(new AccelerateInterpolator());
            this.outAnimator.addUpdateListener(new AnimatorUpdateListener() {
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ZoomHorizonBoardViewBg.this.trans = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    ZoomHorizonBoardViewBg.this.postInvalidate();
                }
            });
            if (animatorListener != null) {
                this.outAnimator.addListener(new AnimatorListener() {
                    public final void onAnimationStart(Animator animator) {
                        if (animatorListener != null) {
                            animatorListener.onAnimationStart(animator);
                        }
                    }

                    public final void onAnimationEnd(Animator animator) {
                        if (animatorListener != null) {
                            animatorListener.onAnimationEnd(animator);
                        }
                        ZoomHorizonBoardViewBg.this.outAnimator = null;
                    }

                    public final void onAnimationCancel(Animator animator) {
                        if (animatorListener != null) {
                            animatorListener.onAnimationCancel(animator);
                        }
                    }

                    public final void onAnimationRepeat(Animator animator) {
                        if (animatorListener != null) {
                            animatorListener.onAnimationRepeat(animator);
                        }
                    }
                });
            }
            this.outAnimator.start();
        }
    }

    public void draw(Canvas canvas) {
        getLeft();
        getTop();
        canvas.saveLayer(0.0f, 0.0f, (float) getRight(), (float) getBottom(), null, 31);
        super.draw(canvas);
        StringBuilder sb = new StringBuilder("WIDTH - trans ");
        sb.append(this.WIDTH - this.trans);
        sb.append(" trans ");
        sb.append(this.trans);
        eao.e("zoomdraw", sb.toString());
        float f = (((float) this.trans) / ((float) this.WIDTH)) * ((float) (this.FROM_CORNER - this.MAX_CORNER));
        if (f > ((float) this.FROM_CORNER)) {
            f = (float) this.FROM_CORNER;
        } else if (f < ((float) this.MAX_CORNER)) {
            f = (float) this.MAX_CORNER;
        }
        RectF rectF = new RectF((float) (this.SHADOW_PADDING + 0 + this.trans), (float) (this.SHADOW_PADDING + 0), (float) (((getWidth() + 0) - this.SHADOW_PADDING) - this.trans), (float) ((getHeight() + 0) - this.SHADOW_PADDING));
        RectF rectF2 = new RectF((float) (this.SHADOW_PADDING + this.trans), (float) this.SHADOW_PADDING, (float) ((getWidth() - this.SHADOW_PADDING) - this.trans), (float) (getHeight() - this.SHADOW_PADDING));
        if (this.valueAnimator != null && this.valueAnimator.isRunning()) {
            this.mBackgroundPaint.setColor(getResources().getColor(R.color.f_c_1));
            this.mBackgroundPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
            canvas.drawRoundRect(rectF, f, f, this.mBackgroundPaint);
        }
        this.mBackgroundPaint.setColor(getResources().getColor(R.color.f_c_1));
        this.mBackgroundPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OVER));
        canvas.drawRoundRect(rectF, f, f, this.mBackgroundPaint);
        this.mShadowPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OVER));
        canvas.drawRoundRect(rectF2, f, f, this.mShadowPaint);
        canvas.saveLayer(0.0f, 0.0f, (float) getRight(), (float) getBottom(), null, 31);
    }
}
