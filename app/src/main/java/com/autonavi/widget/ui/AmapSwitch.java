package com.autonavi.widget.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.CompoundButton;
import com.autonavi.minimap.R;

public class AmapSwitch extends CompoundButton {
    private static final int BACKGROUD_ANIM_1st_DURATION_IN_TRACK = 50;
    private static final int BACKGROUD_ANIM_2nd_DURATION_IN_TRACK = 200;
    private static final int BACKGROUD_ANIM_DURATION_IN_TRACK = 300;
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final String TAG = "Amap_Switch";
    private static final int TOUCH_MODE_DOWN = 1;
    private static final int TOUCH_MODE_DRAGGING = 2;
    private static final int TOUCH_MODE_IDLE = 0;
    private int deltaHeight;
    private int deltaWidth;
    float mAnimDuration;
    private Context mContext;
    private int mDefaultBgBottom;
    private int mDefaultBgLeft;
    private int mDefaultBgRight;
    private int mDefaultBgTop;
    float mInnerBorderWithPx;
    Interpolator mInterpolator;
    private boolean mLastChecked;
    float mMaxAnimDuration;
    private boolean mReverse;
    /* access modifiers changed from: private */
    public boolean mRunning;
    float mStartPosition;
    long mStartTime;
    private boolean mStateChangedFromStopDrag;
    float mSwitchAnimDuration;
    private int mSwitchBottom;
    private int mSwitchHeight;
    private int mSwitchLeft;
    private int mSwitchRight;
    long mSwitchStartTime;
    private int mSwitchTop;
    /* access modifiers changed from: private */
    public final Runnable mSwitchUpdater;
    private int mSwitchWidth;
    private Drawable mThumbDrawable;
    private int mThumbHeight;
    private float mThumbPosition;
    private int mThumbWidth;
    private int mTouchMode;
    private int mTouchSlop;
    private float mTouchX;
    private float mTouchY;
    private Drawable mTrackDefaultDrawable;
    private Drawable mTrackDrawable;
    private int mTrackOffColor;
    private int mTrackOnColor;
    private final Rect mTrackPaddingRect;
    boolean mTumbRunning;
    /* access modifiers changed from: private */
    public final Runnable mUpdater;
    private boolean stopDragFlag;
    private int switchAnimHeight;
    private int switchAnimWidth;

    public AmapSwitch(Context context) {
        this(context, null);
    }

    public AmapSwitch(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.amap_switch_styleattr);
    }

    public AmapSwitch(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mThumbPosition = 0.0f;
        this.deltaHeight = 0;
        this.deltaWidth = 0;
        this.mDefaultBgLeft = 0;
        this.mDefaultBgTop = 0;
        this.mDefaultBgRight = 0;
        this.mDefaultBgBottom = 0;
        this.stopDragFlag = false;
        this.mTrackPaddingRect = new Rect();
        this.mMaxAnimDuration = 300.0f;
        this.mTumbRunning = false;
        this.mRunning = false;
        this.switchAnimWidth = 0;
        this.switchAnimHeight = 0;
        this.mReverse = false;
        this.mLastChecked = false;
        this.mStateChangedFromStopDrag = false;
        this.mSwitchUpdater = new Runnable() {
            public final void run() {
                float f;
                long currentTimeMillis = System.currentTimeMillis() - AmapSwitch.this.mSwitchStartTime;
                float min = Math.min(1.0f, ((float) currentTimeMillis) / AmapSwitch.this.mSwitchAnimDuration);
                int i = (currentTimeMillis > 50 ? 1 : (currentTimeMillis == 50 ? 0 : -1));
                if (i <= 0) {
                    f = 1.0f - AmapSwitch.this.mInterpolator.getInterpolation(1.0f - min);
                } else if (i <= 0 || currentTimeMillis >= 200) {
                    f = currentTimeMillis >= 200 ? AmapSwitch.this.mInterpolator.getInterpolation(min) : 0.0f;
                } else {
                    float interpolation = 1.0f - AmapSwitch.this.mInterpolator.getInterpolation(1.0f - min);
                    f = interpolation + ((AmapSwitch.this.mInterpolator.getInterpolation(Math.min(1.0f, 200.0f / AmapSwitch.this.mSwitchAnimDuration)) - interpolation) * Math.min(1.0f, ((float) (currentTimeMillis - 50)) / 150.0f));
                }
                AmapSwitch.this.setSwtichBgSize(f);
                if (min != 1.0f) {
                    if (AmapSwitch.this.mRunning) {
                        if (AmapSwitch.this.getHandler() != null) {
                            AmapSwitch.this.getHandler().post(AmapSwitch.this.mSwitchUpdater);
                            return;
                        }
                    }
                }
                AmapSwitch.this.stopSwitchAnimation();
            }
        };
        this.mUpdater = new Runnable() {
            public final void run() {
                float min = Math.min(1.0f, ((float) (System.currentTimeMillis() - AmapSwitch.this.mStartTime)) / AmapSwitch.this.mAnimDuration);
                float interpolation = AmapSwitch.this.mInterpolator.getInterpolation(min);
                AmapSwitch.this.setThumbPosition((AmapSwitch.this.mStartPosition * (1.0f - interpolation)) + interpolation);
                if (min != 1.0f) {
                    if (AmapSwitch.this.mTumbRunning) {
                        if (AmapSwitch.this.getHandler() != null) {
                            AmapSwitch.this.getHandler().post(AmapSwitch.this.mUpdater);
                            return;
                        }
                    }
                }
                AmapSwitch.this.stopAnimation();
            }
        };
        this.mContext = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AmapSwitch, i, 0);
        this.mThumbDrawable = obtainStyledAttributes.getDrawable(R.styleable.AmapSwitch_amap_thumb);
        if (this.mThumbDrawable == null) {
            this.mThumbDrawable = ContextCompat.getDrawable(this.mContext, R.drawable.switch_def_thumb_selector);
        }
        this.mTrackDrawable = obtainStyledAttributes.getDrawable(R.styleable.AmapSwitch_amap_track);
        if (this.mTrackDrawable == null) {
            this.mTrackDrawable = ContextCompat.getDrawable(this.mContext, R.drawable.switch_def_track);
        }
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.AmapSwitch_switch_on_color, -1);
        if (resourceId == -1) {
            this.mTrackOnColor = ContextCompat.getColor(this.mContext, R.color.c_12);
        } else {
            this.mTrackOnColor = ContextCompat.getColor(this.mContext, resourceId);
        }
        int resourceId2 = obtainStyledAttributes.getResourceId(R.styleable.AmapSwitch_switch_off_color, -1);
        if (resourceId2 == -1) {
            this.mTrackOffColor = ContextCompat.getColor(this.mContext, R.color.c_5_d);
        } else {
            this.mTrackOffColor = ContextCompat.getColor(this.mContext, resourceId2);
        }
        this.mInnerBorderWithPx = obtainStyledAttributes.getDimension(R.styleable.AmapSwitch_inner_border, TypedValue.applyDimension(1, 1.5f, getResources().getDisplayMetrics()));
        this.mTrackDefaultDrawable = obtainStyledAttributes.getDrawable(R.styleable.AmapSwitch_switch_background);
        if (this.mTrackDefaultDrawable == null) {
            this.mTrackDefaultDrawable = ContextCompat.getDrawable(this.mContext, R.drawable.switch_track_bg);
        }
        this.mTrackDrawable.getPadding(this.mTrackPaddingRect);
        obtainStyledAttributes.recycle();
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mInterpolator = new DecelerateInterpolator();
        refreshDrawableState();
        setClickable(true);
    }

    private boolean hitThumb(float f, float f2) {
        int i = (this.mSwitchLeft + ((int) (this.mThumbPosition + 0.5f))) - this.mTouchSlop;
        return f > ((float) i) && f < ((float) ((this.mThumbWidth + i) + this.mTouchSlop)) && f2 > ((float) (this.mSwitchTop - this.mTouchSlop)) && f2 < ((float) (this.mSwitchBottom + this.mTouchSlop));
    }

    private void startTrackAnimation() {
        if (getHandler() != null) {
            this.mSwitchStartTime = System.currentTimeMillis();
            this.switchAnimHeight = this.mDefaultBgBottom - this.mDefaultBgTop;
            this.switchAnimWidth = this.mDefaultBgRight - this.mDefaultBgLeft;
            if (this.mReverse) {
                this.deltaHeight = this.switchAnimHeight / 2;
                this.deltaWidth = this.switchAnimWidth / 2;
            }
            this.mSwitchAnimDuration = 300.0f;
            this.mRunning = true;
            getHandler().post(this.mSwitchUpdater);
        }
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return super.onTouchEvent(motionEvent);
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                if (isEnabled() && hitThumb(x, y)) {
                    this.mTouchMode = 1;
                    this.stopDragFlag = false;
                    this.mTouchX = x;
                    this.mTouchY = y;
                }
                if (!isChecked()) {
                    this.mReverse = false;
                    startTrackAnimation();
                    break;
                }
                break;
            case 1:
            case 3:
                if (this.mTouchMode != 2) {
                    this.mTouchMode = 0;
                    if (!isChecked()) {
                        this.mReverse = true;
                        startTrackAnimation();
                        break;
                    }
                } else {
                    stopDrag(motionEvent);
                    return true;
                }
                break;
            case 2:
                switch (this.mTouchMode) {
                    case 1:
                        float x2 = motionEvent.getX();
                        float y2 = motionEvent.getY();
                        if (Math.abs(x2 - this.mTouchX) > ((float) (this.mTouchSlop / 2)) || Math.abs(y2 - this.mTouchY) > ((float) (this.mTouchSlop / 2))) {
                            this.mTouchMode = 2;
                            if (getParent() != null) {
                                getParent().requestDisallowInterceptTouchEvent(true);
                            }
                            this.mTouchX = x2;
                            this.mTouchY = y2;
                            return true;
                        }
                    case 2:
                        float x3 = motionEvent.getX();
                        float max = Math.max(0.0f, Math.min(this.mThumbPosition + (x3 - this.mTouchX), (float) getThumbScrollRange()));
                        if (this.mThumbPosition > ((float) (getThumbScrollRange() / 2))) {
                            asyncSetChecked(true);
                        } else {
                            asyncSetChecked(false);
                        }
                        if (max != this.mThumbPosition) {
                            this.mThumbPosition = max;
                            this.mTouchX = x3;
                            invalidate();
                        }
                        return true;
                }
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    private void cancelSuperTouch(MotionEvent motionEvent) {
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        obtain.setAction(3);
        super.onTouchEvent(obtain);
        obtain.recycle();
    }

    private void stopDrag(MotionEvent motionEvent) {
        this.mTouchMode = 0;
        this.stopDragFlag = true;
        boolean z = motionEvent.getAction() == 1 && isEnabled();
        if (z) {
            this.mStateChangedFromStopDrag = true;
        }
        cancelSuperTouch(motionEvent);
        this.mStateChangedFromStopDrag = false;
        if (z) {
            asyncSetChecked(getTargetCheckedState());
            startAnimation();
        }
    }

    public void asyncSetChecked(final boolean z) {
        getHandler().post(new Runnable() {
            public final void run() {
                AmapSwitch.this.setChecked(z);
            }
        });
    }

    private boolean getTargetCheckedState() {
        return this.mThumbPosition >= ((float) (getThumbScrollRange() / 2));
    }

    private float getThumbPosition() {
        float thumbScrollRange = (float) getThumbScrollRange();
        float f = isChecked() ? thumbScrollRange : 0.0f;
        float f2 = thumbScrollRange - f;
        return (this.mThumbPosition - f2) / (f - f2);
    }

    /* access modifiers changed from: private */
    public void setThumbPosition(float f) {
        float thumbScrollRange = (float) getThumbScrollRange();
        float f2 = isChecked() ? thumbScrollRange : 0.0f;
        float f3 = thumbScrollRange - f2;
        this.mThumbPosition = f3 + ((f2 - f3) * f);
        invalidate();
    }

    /* access modifiers changed from: private */
    public void setSwtichBgSize(float f) {
        float[] fArr;
        float[] fArr2 = {(float) this.switchAnimHeight, (float) this.switchAnimWidth};
        if (this.mReverse) {
            fArr = fArr2;
        } else {
            fArr = new float[]{0.0f, 0.0f};
        }
        float f2 = fArr2[0] - fArr[0];
        float f3 = fArr2[1] - fArr[1];
        this.deltaHeight = (int) ((((float) this.switchAnimHeight) - (f2 + ((fArr[0] - f2) * f))) / 2.0f);
        this.deltaWidth = (int) ((((float) this.switchAnimWidth) - (f3 + ((fArr[1] - f3) * f))) / 2.0f);
        invalidate();
    }

    private void resetAnimation() {
        this.mStartTime = System.currentTimeMillis();
        this.mStartPosition = getThumbPosition();
        this.mAnimDuration = (float) ((int) (this.mMaxAnimDuration * (1.0f - this.mStartPosition)));
    }

    private void startAnimation() {
        if (getHandler() != null) {
            resetAnimation();
            this.mTumbRunning = true;
            getHandler().post(this.mUpdater);
        } else {
            setThumbPosition(1.0f);
        }
        invalidate();
    }

    /* access modifiers changed from: private */
    public void stopSwitchAnimation() {
        this.mRunning = false;
        if (getHandler() != null) {
            getHandler().removeCallbacks(this.mSwitchUpdater);
        }
        invalidate();
    }

    public void toggle() {
        super.toggle();
        this.stopDragFlag = false;
        startAnimation();
        if (!isChecked()) {
            this.mReverse = true;
            startTrackAnimation();
        }
    }

    /* access modifiers changed from: private */
    public void stopAnimation() {
        this.mTumbRunning = false;
        setThumbPosition(1.0f);
        if (getHandler() != null) {
            getHandler().removeCallbacks(this.mUpdater);
        }
        if (this.stopDragFlag && !isChecked()) {
            this.mReverse = true;
            startTrackAnimation();
        }
        invalidate();
    }

    public void onMeasure(int i, int i2) {
        if (!this.stopDragFlag || this.mSwitchWidth <= 0 || this.mSwitchHeight <= 0) {
            this.mThumbWidth = this.mThumbDrawable.getIntrinsicWidth();
            this.mThumbHeight = this.mThumbDrawable.getIntrinsicHeight();
            int intrinsicWidth = this.mTrackDrawable.getIntrinsicWidth();
            int max = Math.max(this.mTrackDrawable.getIntrinsicHeight(), (int) (((float) this.mThumbDrawable.getIntrinsicHeight()) + this.mInnerBorderWithPx));
            this.mSwitchWidth = intrinsicWidth;
            this.mSwitchHeight = max;
            super.onMeasure(i, i2);
            int measuredHeight = getMeasuredHeight();
            int measuredWidth = getMeasuredWidth();
            if (measuredHeight != max) {
                setMeasuredDimension(getMeasuredWidth(), max);
            }
            if (measuredWidth != intrinsicWidth) {
                setMeasuredDimension(intrinsicWidth, getMeasuredHeight());
            }
            return;
        }
        setMeasuredDimension(this.mSwitchWidth, this.mSwitchHeight);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mSwitchBottom = this.mSwitchHeight - getPaddingBottom();
        this.mSwitchTop = this.mSwitchBottom - this.mSwitchHeight;
        this.mSwitchRight = this.mSwitchWidth - getPaddingRight();
        this.mSwitchLeft = this.mSwitchRight - this.mSwitchWidth;
        this.mThumbPosition = isChecked() ? (float) getThumbScrollRange() : 0.0f;
        int intrinsicHeight = this.mTrackDrawable.getIntrinsicHeight();
        this.mTrackDrawable.setBounds(this.mSwitchLeft, this.mSwitchTop, this.mSwitchRight, this.mSwitchTop + intrinsicHeight);
        this.mDefaultBgBottom = this.mSwitchTop + intrinsicHeight;
        this.mDefaultBgLeft = this.mSwitchLeft;
        this.mDefaultBgRight = this.mSwitchRight;
        this.mDefaultBgTop = this.mSwitchTop;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int i;
        int i2 = (int) (this.mThumbPosition + 0.5f);
        int i3 = (int) this.mInnerBorderWithPx;
        if (i2 > i3) {
            int i4 = i2 - i3;
            i = (i2 + this.mThumbWidth) - i3;
            i3 = i4;
        } else {
            i = this.mThumbWidth + i3;
        }
        if (isChecked()) {
            ((GradientDrawable) ((StateListDrawable) this.mTrackDrawable).getCurrent()).setColor(this.mTrackOnColor);
        } else {
            ((GradientDrawable) ((StateListDrawable) this.mTrackDrawable).getCurrent()).setColor(this.mTrackOffColor);
        }
        this.mTrackDrawable.draw(canvas);
        if (!isChecked() && this.mTouchMode != 2) {
            this.mTrackDefaultDrawable.setBounds(this.mDefaultBgLeft + this.deltaWidth, this.mDefaultBgTop + this.deltaHeight, this.mDefaultBgRight - this.deltaWidth, this.mDefaultBgBottom - this.deltaHeight);
            this.mTrackDefaultDrawable.draw(canvas);
        }
        float f = this.mInnerBorderWithPx;
        this.mThumbDrawable.setBounds(i3, (int) (((float) this.mSwitchTop) + f), i, (int) (((float) this.mSwitchTop) + f + ((float) this.mThumbDrawable.getIntrinsicHeight())));
        this.mThumbDrawable.draw(canvas);
    }

    public int getCompoundPaddingRight() {
        return super.getCompoundPaddingRight() + this.mSwitchWidth;
    }

    public int getCompoundPaddingTop() {
        return super.getCompoundPaddingTop() + this.mSwitchHeight;
    }

    private int getThumbScrollRange() {
        if (this.mTrackDrawable == null) {
            return 0;
        }
        return ((this.mSwitchWidth - this.mThumbWidth) - this.mTrackPaddingRect.left) - this.mTrackPaddingRect.right;
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (isChecked()) {
            mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setState(drawableState);
        }
        this.stopDragFlag = false;
        startAnimation();
        if (!isChecked() && (isChecked() != this.mLastChecked || this.mStateChangedFromStopDrag)) {
            this.mReverse = true;
            startTrackAnimation();
        }
        this.mLastChecked = isChecked();
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mThumbDrawable || drawable == this.mTrackDrawable;
    }

    public void setTrackOnColor(@ColorInt int i) {
        this.mTrackOnColor = i;
        invalidate();
    }

    public void setTrackOffColor(@ColorInt int i) {
        this.mTrackOffColor = i;
        invalidate();
    }

    public void setTrackDefaultDrawable(@NonNull Drawable drawable) {
        this.mTrackDefaultDrawable = drawable;
        invalidate();
    }

    public void setThumbDrawable(@NonNull Drawable drawable) {
        this.mThumbDrawable = drawable;
        invalidate();
    }

    public void setTrackDrawable(@NonNull Drawable drawable) {
        this.mTrackDrawable = drawable;
        invalidate();
    }
}
