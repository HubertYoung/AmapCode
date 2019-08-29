package com.autonavi.widget.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.GravityCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.autonavi.minimap.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class BalloonLayout extends ViewGroup {
    public static final int BOTTOM = 4;
    public static final long DEFAULT_DISPLAY_DURATION = 3000;
    public static final long DEFAULT_ENTER_DURATION = 225;
    public static final long DEFAULT_LEAVE_DURATION = 125;
    public static final int INVALID_VALUE = -1;
    public static final int LEFT = 1;
    public static final int NONE = 0;
    public static final int RIGHT = 3;
    public static final int TOP = 2;
    protected ViewPropertyAnimator mAnimator;
    protected int mArrowDirection;
    protected float mArrowHeight;
    protected float mArrowOffset;
    protected boolean mArrowOffsetReverse;
    protected float mArrowWidth;
    protected float mArrowX;
    protected float mArrowY;
    protected a mBalloonDrawable;
    protected int mBubbleColor;
    protected float mCornersRadius;
    protected int mGravity;
    protected Runnable mHideRunnable;
    /* access modifiers changed from: private */
    public boolean mInAnimator;
    /* access modifiers changed from: private */
    public boolean mIsShowing;
    protected int mMaxWidth;
    /* access modifiers changed from: private */
    public boolean mOutAnimator;
    protected float mRelativePivotX;
    protected float mRelativePivotY;
    protected int mShadowColor;
    protected float mShadowRadius;
    protected float mShadowX;
    protected float mShadowY;
    protected Runnable mShowRunnable;
    protected int mStrokeColor;
    protected float mStrokeWidth;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ArrowDirection {
    }

    class a extends Drawable {
        private RectF b = new RectF();
        private Path c = new Path();
        private Paint d = new Paint(1);
        private Paint e = new Paint(1);
        private Paint f = new Paint(1);

        public final int getOpacity() {
            return -3;
        }

        public a() {
            this.e.setStyle(Style.STROKE);
            a();
        }

        public final void a() {
            this.b.set(0.0f, 0.0f, (float) BalloonLayout.this.getWidth(), (float) BalloonLayout.this.getHeight());
            if (b()) {
                BalloonLayout.this.mArrowX = -1.0f;
                BalloonLayout.this.mArrowY = -1.0f;
                c();
                this.f.setColor(BalloonLayout.this.mStrokeWidth > 0.0f ? BalloonLayout.this.mStrokeColor : BalloonLayout.this.mBubbleColor);
                this.f.setShadowLayer(BalloonLayout.this.mShadowRadius, BalloonLayout.this.mShadowX, BalloonLayout.this.mShadowY, BalloonLayout.this.mShadowColor);
                this.f.setStrokeWidth(BalloonLayout.this.mStrokeWidth);
                this.d.setColor(BalloonLayout.this.mBubbleColor);
                this.e.setColor(BalloonLayout.this.mStrokeColor);
                this.e.setStrokeWidth(BalloonLayout.this.mStrokeWidth);
                BalloonLayout.this.setLayerType(1, this.f);
                BalloonLayout.this.setLayerType(1, this.e);
            }
        }

        private boolean b() {
            return this.b.width() > 0.0f && this.b.height() > 0.0f;
        }

        private void c() {
            this.c.reset();
            switch (BalloonLayout.this.mArrowDirection) {
                case 1:
                    e();
                    break;
                case 2:
                    f();
                    break;
                case 3:
                    g();
                    break;
                case 4:
                    h();
                    break;
                default:
                    i();
                    break;
            }
            this.c.close();
        }

        private float d() {
            if (BalloonLayout.this.mArrowHeight <= 0.0f || BalloonLayout.this.mArrowWidth <= 0.0f) {
                return 0.0f;
            }
            return BalloonLayout.this.mArrowHeight;
        }

        private void e() {
            float f2;
            float f3;
            float d2 = ((this.b.left + BalloonLayout.this.mShadowRadius) - BalloonLayout.this.mShadowX) + (BalloonLayout.this.mStrokeWidth / 2.0f) + BalloonLayout.this.mCornersRadius + d();
            float f4 = (((this.b.right - BalloonLayout.this.mShadowRadius) - BalloonLayout.this.mShadowX) - (BalloonLayout.this.mStrokeWidth / 2.0f)) - BalloonLayout.this.mCornersRadius;
            float f5 = ((this.b.top + BalloonLayout.this.mShadowRadius) - BalloonLayout.this.mShadowY) + (BalloonLayout.this.mStrokeWidth / 2.0f);
            this.c.moveTo(d2, f5);
            this.c.lineTo(f4, f5);
            if (BalloonLayout.this.mCornersRadius > 0.0f) {
                this.c.arcTo(new RectF(f4, f5, BalloonLayout.this.mCornersRadius + f4, BalloonLayout.this.mCornersRadius + f5), 270.0f, 90.0f);
                f4 += BalloonLayout.this.mCornersRadius;
                f5 += BalloonLayout.this.mCornersRadius;
            }
            float f6 = (((this.b.bottom - BalloonLayout.this.mShadowRadius) - BalloonLayout.this.mShadowY) - (BalloonLayout.this.mStrokeWidth / 2.0f)) - BalloonLayout.this.mCornersRadius;
            this.c.lineTo(f4, f6);
            if (BalloonLayout.this.mCornersRadius > 0.0f) {
                this.c.arcTo(new RectF(f4 - BalloonLayout.this.mCornersRadius, f6, f4, BalloonLayout.this.mCornersRadius + f6), 0.0f, 90.0f);
                f6 += BalloonLayout.this.mCornersRadius;
            }
            this.c.lineTo(d2, f6);
            if (BalloonLayout.this.mCornersRadius > 0.0f) {
                this.c.arcTo(new RectF(d2 - BalloonLayout.this.mCornersRadius, f6 - BalloonLayout.this.mCornersRadius, d2, f6), 90.0f, 90.0f);
                d2 -= BalloonLayout.this.mCornersRadius;
                f6 -= BalloonLayout.this.mCornersRadius;
            }
            if (BalloonLayout.this.mArrowOffset < 0.0f) {
                f3 = (((f6 - f5) - BalloonLayout.this.mArrowWidth) / 2.0f) + f5;
                f2 = BalloonLayout.this.mArrowWidth + f3;
            } else if (BalloonLayout.this.mArrowOffsetReverse) {
                f2 = Math.min(this.b.bottom - BalloonLayout.this.mArrowOffset, f6);
                f3 = f2 - BalloonLayout.this.mArrowWidth;
            } else {
                f3 = Math.max(this.b.top + BalloonLayout.this.mArrowOffset, f5);
                f2 = BalloonLayout.this.mArrowWidth + f3;
            }
            float max = Math.max(f3, f5);
            float min = Math.min(f2, f6);
            this.c.lineTo(d2, min);
            float f7 = min - ((min - max) / 2.0f);
            this.c.lineTo(d2 - d(), f7);
            BalloonLayout.this.mArrowX = (d2 - d()) - this.b.left;
            BalloonLayout.this.mArrowY = f7;
            this.c.lineTo(d2, max);
            this.c.lineTo(d2, f5);
            this.c.arcTo(new RectF(d2, f5 - BalloonLayout.this.mCornersRadius, BalloonLayout.this.mCornersRadius + d2, f5), 180.0f, 90.0f);
        }

        private void f() {
            float f2;
            float f3;
            float f4 = ((this.b.left + BalloonLayout.this.mShadowRadius) - BalloonLayout.this.mShadowX) + (BalloonLayout.this.mStrokeWidth / 2.0f) + BalloonLayout.this.mCornersRadius;
            float f5 = (((this.b.right - BalloonLayout.this.mShadowRadius) - BalloonLayout.this.mShadowX) - (BalloonLayout.this.mStrokeWidth / 2.0f)) - BalloonLayout.this.mCornersRadius;
            float d2 = ((this.b.top + BalloonLayout.this.mShadowRadius) - BalloonLayout.this.mShadowY) + (BalloonLayout.this.mStrokeWidth / 2.0f) + d();
            this.c.moveTo(f4, d2);
            if (BalloonLayout.this.mArrowOffset < 0.0f) {
                f3 = (((f5 - f4) - BalloonLayout.this.mArrowWidth) / 2.0f) + f4;
                f2 = BalloonLayout.this.mArrowWidth + f3;
            } else if (BalloonLayout.this.mArrowOffsetReverse) {
                f2 = Math.min(this.b.right - BalloonLayout.this.mArrowOffset, f5);
                f3 = f2 - BalloonLayout.this.mArrowWidth;
            } else {
                f3 = Math.max(this.b.left + BalloonLayout.this.mArrowOffset, f4);
                f2 = BalloonLayout.this.mArrowWidth + f3;
            }
            float max = Math.max(f3, f4);
            float min = Math.min(f2, f5);
            this.c.lineTo(max, d2);
            float f6 = max + ((min - max) / 2.0f);
            this.c.lineTo(f6, d2 - BalloonLayout.this.mArrowHeight);
            BalloonLayout.this.mArrowX = f6;
            BalloonLayout.this.mArrowY = d2 - BalloonLayout.this.mArrowHeight;
            this.c.lineTo(min, d2);
            this.c.lineTo(f5, d2);
            if (BalloonLayout.this.mCornersRadius > 0.0f) {
                this.c.arcTo(new RectF(f5, d2, BalloonLayout.this.mCornersRadius + f5, BalloonLayout.this.mCornersRadius + d2), 270.0f, 90.0f);
                f5 += BalloonLayout.this.mCornersRadius;
                d2 += BalloonLayout.this.mCornersRadius;
            }
            float f7 = (((this.b.bottom - BalloonLayout.this.mShadowRadius) - BalloonLayout.this.mShadowY) - (BalloonLayout.this.mStrokeWidth / 2.0f)) - BalloonLayout.this.mCornersRadius;
            this.c.lineTo(f5, f7);
            if (BalloonLayout.this.mCornersRadius > 0.0f) {
                this.c.arcTo(new RectF(f5 - BalloonLayout.this.mCornersRadius, f7, f5, BalloonLayout.this.mCornersRadius + f7), 0.0f, 90.0f);
                f7 += BalloonLayout.this.mCornersRadius;
            }
            this.c.lineTo(f4, f7);
            if (BalloonLayout.this.mCornersRadius > 0.0f) {
                this.c.arcTo(new RectF(f4 - BalloonLayout.this.mCornersRadius, f7 - BalloonLayout.this.mCornersRadius, f4, f7), 90.0f, 90.0f);
                f4 -= BalloonLayout.this.mCornersRadius;
            }
            this.c.lineTo(f4, d2);
            this.c.arcTo(new RectF(f4, d2 - BalloonLayout.this.mCornersRadius, BalloonLayout.this.mCornersRadius + f4, d2), 180.0f, 90.0f);
        }

        private void g() {
            float f2;
            float f3;
            float f4 = ((this.b.left + BalloonLayout.this.mShadowRadius) - BalloonLayout.this.mShadowX) + (BalloonLayout.this.mStrokeWidth / 2.0f) + BalloonLayout.this.mCornersRadius;
            float d2 = ((((this.b.right - BalloonLayout.this.mShadowRadius) - BalloonLayout.this.mShadowX) - (BalloonLayout.this.mStrokeWidth / 2.0f)) - BalloonLayout.this.mCornersRadius) - d();
            float f5 = ((this.b.top + BalloonLayout.this.mShadowRadius) - BalloonLayout.this.mShadowY) + (BalloonLayout.this.mStrokeWidth / 2.0f);
            this.c.moveTo(f4, f5);
            this.c.lineTo(d2, f5);
            if (BalloonLayout.this.mCornersRadius > 0.0f) {
                this.c.arcTo(new RectF(d2, f5, BalloonLayout.this.mCornersRadius + d2, BalloonLayout.this.mCornersRadius + f5), 270.0f, 90.0f);
                d2 += BalloonLayout.this.mCornersRadius;
                f5 += BalloonLayout.this.mCornersRadius;
            }
            float f6 = (((this.b.bottom - BalloonLayout.this.mShadowRadius) - BalloonLayout.this.mShadowY) - (BalloonLayout.this.mStrokeWidth / 2.0f)) - BalloonLayout.this.mCornersRadius;
            if (BalloonLayout.this.mArrowOffset < 0.0f) {
                f3 = (((f6 - f5) - BalloonLayout.this.mArrowWidth) / 2.0f) + f5;
                f2 = BalloonLayout.this.mArrowWidth + f3;
            } else if (BalloonLayout.this.mArrowOffsetReverse) {
                f2 = Math.min(this.b.bottom - BalloonLayout.this.mArrowOffset, f6);
                f3 = f2 - BalloonLayout.this.mArrowWidth;
            } else {
                f3 = Math.max(this.b.top + BalloonLayout.this.mArrowOffset, f5);
                f2 = BalloonLayout.this.mArrowWidth + f3;
            }
            float max = Math.max(f3, f5);
            float min = Math.min(f2, f6);
            this.c.lineTo(d2, max);
            float f7 = max + ((min - max) / 2.0f);
            this.c.lineTo(d() + d2, f7);
            BalloonLayout.this.mArrowX = d() + d2;
            BalloonLayout.this.mArrowY = f7;
            this.c.lineTo(d2, min);
            this.c.lineTo(d2, f6);
            if (BalloonLayout.this.mCornersRadius > 0.0f) {
                this.c.arcTo(new RectF(d2 - BalloonLayout.this.mCornersRadius, f6, d2, BalloonLayout.this.mCornersRadius + f6), 0.0f, 90.0f);
                f6 += BalloonLayout.this.mCornersRadius;
            }
            this.c.lineTo(f4, f6);
            if (BalloonLayout.this.mCornersRadius > 0.0f) {
                this.c.arcTo(new RectF(f4 - BalloonLayout.this.mCornersRadius, f6 - BalloonLayout.this.mCornersRadius, f4, f6), 90.0f, 90.0f);
                f4 -= BalloonLayout.this.mCornersRadius;
            }
            this.c.lineTo(f4, f5);
            this.c.arcTo(new RectF(f4, f5 - BalloonLayout.this.mCornersRadius, BalloonLayout.this.mCornersRadius + f4, f5), 180.0f, 90.0f);
        }

        private void h() {
            float f2;
            float f3;
            float f4 = ((this.b.left + BalloonLayout.this.mShadowRadius) - BalloonLayout.this.mShadowX) + (BalloonLayout.this.mStrokeWidth / 2.0f) + BalloonLayout.this.mCornersRadius;
            float f5 = (((this.b.right - BalloonLayout.this.mShadowRadius) - BalloonLayout.this.mShadowX) - (BalloonLayout.this.mStrokeWidth / 2.0f)) - BalloonLayout.this.mCornersRadius;
            float f6 = ((this.b.top + BalloonLayout.this.mShadowRadius) - BalloonLayout.this.mShadowY) + (BalloonLayout.this.mStrokeWidth / 2.0f);
            this.c.moveTo(f4, f6);
            this.c.lineTo(f5, f6);
            if (BalloonLayout.this.mCornersRadius > 0.0f) {
                this.c.arcTo(new RectF(f5, f6, BalloonLayout.this.mCornersRadius + f5, BalloonLayout.this.mCornersRadius + f6), 270.0f, 90.0f);
                f5 += BalloonLayout.this.mCornersRadius;
                f6 += BalloonLayout.this.mCornersRadius;
            }
            float d2 = ((((this.b.bottom - BalloonLayout.this.mShadowRadius) - BalloonLayout.this.mShadowY) - (BalloonLayout.this.mStrokeWidth / 2.0f)) - BalloonLayout.this.mCornersRadius) - d();
            this.c.lineTo(f5, d2);
            if (BalloonLayout.this.mCornersRadius > 0.0f) {
                this.c.arcTo(new RectF(f5 - BalloonLayout.this.mCornersRadius, d2, f5, BalloonLayout.this.mCornersRadius + d2), 0.0f, 90.0f);
                f5 -= BalloonLayout.this.mCornersRadius;
                d2 += BalloonLayout.this.mCornersRadius;
            }
            if (BalloonLayout.this.mArrowOffset < 0.0f) {
                f3 = (((f5 - f4) - BalloonLayout.this.mArrowWidth) / 2.0f) + f4;
                f2 = BalloonLayout.this.mArrowWidth + f3;
            } else if (BalloonLayout.this.mArrowOffsetReverse) {
                f2 = Math.min(this.b.right - BalloonLayout.this.mArrowOffset, f5);
                f3 = f2 - BalloonLayout.this.mArrowWidth;
            } else {
                f3 = Math.max(this.b.left + BalloonLayout.this.mArrowOffset, f4);
                f2 = BalloonLayout.this.mArrowWidth + f3;
            }
            float max = Math.max(f3, f4);
            float min = Math.min(f2, f5);
            this.c.lineTo(min, d2);
            float f7 = min - ((min - max) / 2.0f);
            this.c.lineTo(f7, BalloonLayout.this.mArrowHeight + d2);
            BalloonLayout.this.mArrowX = f7;
            BalloonLayout.this.mArrowY = BalloonLayout.this.mArrowHeight + d2;
            this.c.lineTo(max, d2);
            this.c.lineTo(f4, d2);
            if (BalloonLayout.this.mCornersRadius > 0.0f) {
                this.c.arcTo(new RectF(f4 - BalloonLayout.this.mCornersRadius, d2 - BalloonLayout.this.mCornersRadius, f4, d2), 90.0f, 90.0f);
                f4 -= BalloonLayout.this.mCornersRadius;
            }
            this.c.lineTo(f4, f6);
            this.c.arcTo(new RectF(f4, f6 - BalloonLayout.this.mCornersRadius, BalloonLayout.this.mCornersRadius + f4, f6), 180.0f, 90.0f);
        }

        private void i() {
            float f2 = ((this.b.left + BalloonLayout.this.mShadowRadius) - BalloonLayout.this.mShadowX) + (BalloonLayout.this.mStrokeWidth / 2.0f) + BalloonLayout.this.mCornersRadius;
            float f3 = (((this.b.right - BalloonLayout.this.mShadowRadius) - BalloonLayout.this.mShadowX) - (BalloonLayout.this.mStrokeWidth / 2.0f)) - BalloonLayout.this.mCornersRadius;
            float f4 = ((this.b.top + BalloonLayout.this.mShadowRadius) - BalloonLayout.this.mShadowY) + (BalloonLayout.this.mStrokeWidth / 2.0f);
            this.c.moveTo(f2, f4);
            this.c.lineTo(f3, f4);
            if (BalloonLayout.this.mCornersRadius > 0.0f) {
                this.c.arcTo(new RectF(f3, f4, BalloonLayout.this.mCornersRadius + f3, BalloonLayout.this.mCornersRadius + f4), 270.0f, 90.0f);
                f3 += BalloonLayout.this.mCornersRadius;
                f4 += BalloonLayout.this.mCornersRadius;
            }
            float f5 = (((this.b.bottom - BalloonLayout.this.mShadowRadius) - BalloonLayout.this.mShadowY) - (BalloonLayout.this.mStrokeWidth / 2.0f)) - BalloonLayout.this.mCornersRadius;
            this.c.lineTo(f3, f5);
            if (BalloonLayout.this.mCornersRadius > 0.0f) {
                this.c.arcTo(new RectF(f3 - BalloonLayout.this.mCornersRadius, f5, f3, BalloonLayout.this.mCornersRadius + f5), 0.0f, 90.0f);
                f5 += BalloonLayout.this.mCornersRadius;
            }
            this.c.lineTo(f2, f5);
            if (BalloonLayout.this.mCornersRadius > 0.0f) {
                this.c.arcTo(new RectF(f2 - BalloonLayout.this.mCornersRadius, f5 - BalloonLayout.this.mCornersRadius, f2, f5), 90.0f, 90.0f);
                f2 -= BalloonLayout.this.mCornersRadius;
            }
            this.c.lineTo(f2, f4);
            this.c.arcTo(new RectF(f2, f4 - BalloonLayout.this.mCornersRadius, BalloonLayout.this.mCornersRadius + f2, f4), 180.0f, 90.0f);
        }

        public final void draw(Canvas canvas) {
            if (BalloonLayout.this.mShadowRadius > 0.0f) {
                canvas.drawPath(this.c, this.f);
            }
            if (BalloonLayout.this.mStrokeWidth > 0.0f) {
                canvas.drawPath(this.c, this.e);
            }
            if (BalloonLayout.this.shouldDrawBalloon()) {
                canvas.drawPath(this.c, this.d);
            }
        }

        public final void setAlpha(int i) {
            this.d.setAlpha(i);
            this.e.setAlpha(i);
        }

        public final void setColorFilter(ColorFilter colorFilter) {
            this.d.setColorFilter(colorFilter);
            this.e.setColorFilter(colorFilter);
        }

        public final int getIntrinsicWidth() {
            return (int) this.b.width();
        }

        public final int getIntrinsicHeight() {
            return (int) this.b.height();
        }
    }

    /* access modifiers changed from: protected */
    public void initDefaultValues() {
    }

    /* access modifiers changed from: protected */
    public boolean shouldDrawBalloon() {
        return true;
    }

    public BalloonLayout(Context context) {
        this(context, null, 0);
    }

    public BalloonLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BalloonLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMaxWidth = 21474836;
        this.mArrowWidth = 0.0f;
        this.mArrowHeight = 0.0f;
        this.mArrowDirection = 0;
        this.mArrowOffset = -1.0f;
        this.mArrowOffsetReverse = false;
        this.mShadowX = 0.0f;
        this.mShadowY = 0.0f;
        this.mShadowRadius = 0.0f;
        this.mShadowColor = -16777216;
        this.mCornersRadius = 0.0f;
        this.mBubbleColor = -1;
        this.mStrokeWidth = 0.0f;
        this.mStrokeColor = -16777216;
        this.mGravity = 8388627;
        this.mRelativePivotX = -1.0f;
        this.mRelativePivotY = -1.0f;
        this.mArrowX = -1.0f;
        this.mArrowY = -1.0f;
        this.mInAnimator = false;
        this.mOutAnimator = false;
        this.mIsShowing = false;
        this.mHideRunnable = new Runnable() {
            public final void run() {
                BalloonLayout.this.hide();
            }
        };
        this.mShowRunnable = new Runnable() {
            public final void run() {
                BalloonLayout.this.setupPivot();
                BalloonLayout.this.setAlpha(0.0f);
                BalloonLayout.this.setScaleX(0.8f);
                BalloonLayout.this.setScaleY(0.8f);
                BalloonLayout.this.setVisibility(0);
                BalloonLayout.this.mAnimator = BalloonLayout.this.animate().alpha(1.0f).scaleY(1.0f).scaleX(1.0f).setDuration(225).setInterpolator(new AccelerateDecelerateInterpolator());
                BalloonLayout.this.mAnimator.setListener(new AnimatorListenerAdapter() {
                    public final void onAnimationCancel(Animator animator) {
                        BalloonLayout.this.mInAnimator = false;
                    }

                    public final void onAnimationEnd(Animator animator) {
                        BalloonLayout.this.mInAnimator = false;
                    }
                });
                BalloonLayout.this.mAnimator.start();
            }
        };
        initDefaultValues();
        initFromAttributes(context, attributeSet);
        init(context);
    }

    /* access modifiers changed from: protected */
    public void init(Context context) {
        shouldDrawBalloon();
        this.mBalloonDrawable = new a();
    }

    /* access modifiers changed from: protected */
    public void initFromAttributes(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BalloonLayout);
        this.mMaxWidth = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.BalloonLayout_balloon_maxWidth, this.mMaxWidth);
        this.mArrowWidth = obtainStyledAttributes.getDimension(R.styleable.BalloonLayout_balloon_arrowWidth, this.mArrowWidth);
        this.mArrowHeight = obtainStyledAttributes.getDimension(R.styleable.BalloonLayout_balloon_arrowHeight, this.mArrowHeight);
        this.mArrowDirection = obtainStyledAttributes.getInt(R.styleable.BalloonLayout_balloon_arrowDirection, this.mArrowDirection);
        this.mArrowOffset = obtainStyledAttributes.getDimension(R.styleable.BalloonLayout_balloon_arrowOffset, this.mArrowOffset);
        this.mArrowOffsetReverse = obtainStyledAttributes.getBoolean(R.styleable.BalloonLayout_balloon_arrowOffsetReverse, this.mArrowOffsetReverse);
        this.mShadowX = obtainStyledAttributes.getDimension(R.styleable.BalloonLayout_balloon_shadowX, this.mShadowX);
        this.mShadowY = obtainStyledAttributes.getDimension(R.styleable.BalloonLayout_balloon_shadowY, this.mShadowY);
        this.mShadowRadius = obtainStyledAttributes.getDimension(R.styleable.BalloonLayout_balloon_shadowRadius, this.mShadowRadius);
        this.mShadowColor = obtainStyledAttributes.getColor(R.styleable.BalloonLayout_balloon_shadowColor, this.mShadowColor);
        this.mCornersRadius = obtainStyledAttributes.getDimension(R.styleable.BalloonLayout_balloon_cornersRadius, this.mCornersRadius);
        this.mBubbleColor = obtainStyledAttributes.getColor(R.styleable.BalloonLayout_balloon_bubbleColor, this.mBubbleColor);
        this.mStrokeWidth = obtainStyledAttributes.getDimension(R.styleable.BalloonLayout_balloon_strokeWidth, this.mStrokeWidth);
        this.mStrokeColor = obtainStyledAttributes.getColor(R.styleable.BalloonLayout_balloon_strokeColor, this.mStrokeColor);
        this.mGravity = obtainStyledAttributes.getInt(R.styleable.BalloonLayout_gravity, this.mGravity);
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        float extraWith = getExtraWith();
        float extraHeight = getExtraHeight();
        View childAt = getChildAt(0);
        if (!(childAt == null || childAt.getVisibility() == 8)) {
            int i5 = this.mMaxWidth;
            if (mode == 0) {
                i3 = MeasureSpec.makeMeasureSpec(Math.max(0, (int) (((double) (((float) i5) - extraWith)) + 0.5d)), Integer.MIN_VALUE);
            } else {
                i3 = MeasureSpec.makeMeasureSpec(Math.max(0, (int) (((double) (((float) Math.min(size, i5)) - extraWith)) + 0.5d)), Integer.MIN_VALUE);
            }
            int i6 = this.mMaxWidth * 10;
            if (mode2 == 0) {
                i4 = MeasureSpec.makeMeasureSpec(Math.max(0, (int) (((double) (((float) i6) - extraHeight)) + 0.5d)), Integer.MIN_VALUE);
            } else {
                i4 = MeasureSpec.makeMeasureSpec(Math.max(0, (int) (((double) (((float) size2) - extraHeight)) + 0.5d)), Integer.MIN_VALUE);
            }
            childAt.measure(i3, i4);
            extraWith += (float) childAt.getMeasuredWidth();
            extraHeight += (float) childAt.getMeasuredHeight();
        }
        setMeasuredDimension(resolveSize(Math.max(getSuggestedMinimumWidth(), (int) (((double) extraWith) + 0.5d)), i), resolveSize(Math.max(getSuggestedMinimumHeight(), (int) (((double) extraHeight) + 0.5d)), i2));
    }

    /* access modifiers changed from: protected */
    public float getExtraWith() {
        return (this.mShadowRadius * 2.0f) + this.mStrokeWidth + getArrowHeight(1) + getArrowHeight(3) + ((float) getPaddingLeft()) + ((float) getPaddingRight());
    }

    /* access modifiers changed from: protected */
    public float getExtraHeight() {
        return (this.mShadowRadius * 2.0f) + this.mStrokeWidth + getArrowHeight(2) + getArrowHeight(4) + ((float) getPaddingTop()) + ((float) getPaddingBottom());
    }

    /* access modifiers changed from: protected */
    public float getArrowHeight(int i) {
        if (this.mArrowDirection != i || this.mArrowWidth <= 0.0f || this.mArrowHeight <= 0.0f) {
            return 0.0f;
        }
        return this.mArrowHeight;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        View childAt = getChildAt(0);
        if (childAt != null && childAt.getVisibility() != 8) {
            float paddingLeft = (((this.mShadowRadius + (this.mStrokeWidth / 2.0f)) + ((float) getPaddingLeft())) + getArrowHeight(1)) - this.mShadowX;
            float paddingTop = (((this.mShadowRadius + (this.mStrokeWidth / 2.0f)) + ((float) getPaddingTop())) + getArrowHeight(2)) - this.mShadowY;
            int i5 = this.mGravity & 112;
            int i6 = this.mGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
            if (i6 == 1) {
                paddingLeft += ((((float) (i3 - i)) - getExtraWith()) - ((float) childAt.getMeasuredWidth())) / 2.0f;
            } else if (i6 == 5) {
                paddingLeft = (((((((float) (i3 - i)) - this.mShadowRadius) - (this.mStrokeWidth / 2.0f)) - ((float) getPaddingRight())) - getArrowHeight(3)) - this.mShadowX) - ((float) childAt.getMeasuredWidth());
            }
            if (i5 == 16) {
                paddingTop += ((((float) (i4 - i2)) - getExtraHeight()) - ((float) childAt.getMeasuredHeight())) / 2.0f;
            } else if (i5 == 80) {
                paddingTop = (((((((float) (i4 - i2)) - this.mShadowRadius) - (this.mStrokeWidth / 2.0f)) - ((float) getPaddingBottom())) - getArrowHeight(4)) - this.mShadowY) - ((float) childAt.getMeasuredHeight());
            }
            double d = ((double) paddingLeft) + 0.5d;
            double d2 = ((double) paddingTop) + 0.5d;
            childAt.layout((int) d, (int) d2, (int) (d + ((double) childAt.getMeasuredWidth())), (int) (d2 + ((double) childAt.getMeasuredHeight())));
            this.mBalloonDrawable.a();
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        if (this.mBalloonDrawable != null && getWidth() > 0 && getHeight() > 0) {
            this.mBalloonDrawable.draw(canvas);
        }
        super.dispatchDraw(canvas);
    }

    public BalloonLayout setGravity(int i) {
        this.mGravity = i;
        requestLayout();
        return this;
    }

    public BalloonLayout setMaxWidth(int i) {
        this.mMaxWidth = i;
        requestLayout();
        return this;
    }

    public BalloonLayout setArrowDirection(int i) {
        this.mArrowDirection = i;
        requestLayout();
        return this;
    }

    public BalloonLayout setArrowOffset(int i, boolean z) {
        this.mArrowOffset = (float) i;
        this.mArrowOffsetReverse = z;
        requestLayout();
        return this;
    }

    public BalloonLayout setArrowWidth(float f) {
        this.mArrowWidth = f;
        requestLayout();
        return this;
    }

    public BalloonLayout setArrowHeight(float f) {
        this.mArrowHeight = f;
        requestLayout();
        return this;
    }

    public BalloonLayout setCornersRadius(float f) {
        this.mCornersRadius = f;
        requestLayout();
        return this;
    }

    public BalloonLayout setBubbleColor(int i) {
        this.mBubbleColor = i;
        requestLayout();
        return this;
    }

    public BalloonLayout setStroke(float f, int i) {
        this.mStrokeWidth = f;
        this.mStrokeColor = i;
        requestLayout();
        return this;
    }

    public BalloonLayout setShadowLayer(float f, float f2, float f3, int i) {
        this.mShadowRadius = f;
        this.mShadowX = f2;
        this.mShadowY = f3;
        this.mShadowColor = i;
        requestLayout();
        return this;
    }

    public BalloonLayout setRelativePivotX(float f) {
        this.mRelativePivotX = f;
        return this;
    }

    public BalloonLayout setRelativePivotY(float f) {
        this.mRelativePivotY = f;
        return this;
    }

    private void clearAnimator() {
        if (this.mAnimator != null && (this.mInAnimator || this.mOutAnimator)) {
            this.mAnimator.cancel();
        }
        this.mInAnimator = false;
        this.mOutAnimator = false;
        this.mAnimator = null;
    }

    public void show() {
        removeCallbacks(this.mHideRunnable);
        removeCallbacks(this.mShowRunnable);
        clearAnimator();
        this.mInAnimator = true;
        this.mIsShowing = true;
        post(this.mShowRunnable);
    }

    public void show(long j) {
        show();
        postDelayed(this.mHideRunnable, j + 225);
    }

    public void showAutoDismiss() {
        show(DEFAULT_DISPLAY_DURATION);
    }

    public void hide() {
        if (getVisibility() != 8 && !this.mOutAnimator) {
            removeCallbacks(this.mHideRunnable);
            removeCallbacks(this.mShowRunnable);
            clearAnimator();
            this.mOutAnimator = true;
            this.mIsShowing = false;
            setupPivot();
            setAlpha(1.0f);
            setScaleX(1.0f);
            setScaleY(1.0f);
            setVisibility(0);
            this.mAnimator = animate().alpha(0.0f).scaleY(0.8f).scaleX(0.8f).setDuration(125).setInterpolator(new AccelerateDecelerateInterpolator());
            this.mAnimator.setListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    BalloonLayout.this.mOutAnimator = false;
                    if (!BalloonLayout.this.mIsShowing) {
                        BalloonLayout.this.hideStatus();
                    }
                }

                public final void onAnimationCancel(Animator animator) {
                    BalloonLayout.this.mOutAnimator = false;
                    if (!BalloonLayout.this.mIsShowing) {
                        BalloonLayout.this.hideStatus();
                    }
                }
            });
            this.mAnimator.start();
        }
    }

    public void setupPivot() {
        float f = 0.5f;
        if (this.mRelativePivotX >= 0.0f || this.mArrowX < 0.0f) {
            measureUnspecifiedIfNeed();
            setPivotX(((float) (getWidth() > 0 ? getWidth() : getMeasuredWidth())) * (this.mRelativePivotX >= 0.0f ? this.mRelativePivotX : 0.5f));
        } else {
            setPivotX(this.mArrowX);
        }
        if (this.mRelativePivotY >= 0.0f || this.mArrowY < 0.0f) {
            measureUnspecifiedIfNeed();
            float height = (float) (getHeight() > 0 ? getHeight() : getMeasuredHeight());
            if (this.mRelativePivotY >= 0.0f) {
                f = this.mRelativePivotY;
            }
            setPivotY(height * f);
            return;
        }
        setPivotY(this.mArrowY);
    }

    private void measureUnspecifiedIfNeed() {
        if (getWidth() <= 0 && getMeasuredWidth() <= 0) {
            measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        }
    }

    /* access modifiers changed from: private */
    public void hideStatus() {
        setVisibility(8);
        setAlpha(1.0f);
        setScaleX(1.0f);
        setScaleY(1.0f);
    }
}
