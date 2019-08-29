package com.amap.bundle.commonui.switchbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
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
    private static final int BACKGROUD_ANIM_1ST_DURATION_IN_TRACK = 50;
    private static final int BACKGROUD_ANIM_2ND_DURATION_IN_TRACK = 200;
    private static final int BACKGROUD_ANIM_DURATION_IN_TRACK = 300;
    static final float BEGINE_PROGRESS = 0.0f;
    private static final int[] CHECKED_STATE_SET = {16842912};
    static final float END_PROGRESS = 1.0f;
    private static final int TOUCH_MODE_DOWN = 1;
    private static final int TOUCH_MODE_DRAGGING = 2;
    private static final int TOUCH_MODE_IDLE = 0;
    float mAnimDuration;
    private int mDefaultBgBottom;
    private int mDefaultBgLeft;
    private int mDefaultBgRight;
    private int mDefaultBgTop;
    private int mDeltaHeight;
    private int mDeltaWidth;
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
    private boolean mStopDragFlag;
    float mSwitchAnimDuration;
    private int mSwitchBottom;
    private int mSwitchHeight;
    private int mSwitchLeft;
    long mSwitchStartTime;
    private int mSwitchTop;
    /* access modifiers changed from: private */
    public final Runnable mSwitchUpdater;
    private int mSwitchWidth;
    private Drawable mThumbDrawable;
    private float mThumbPosition;
    private int mThumbWidth;
    private int mTouchMode;
    private int mTouchSlop;
    private float mTouchX;
    private float mTouchY;
    private Drawable mTrackDefaultDrawable;
    private Drawable mTrackDrawable;
    private int mTrackOnColor;
    private final Rect mTrackPaddingRect;
    boolean mTumbRunning;
    /* access modifiers changed from: private */
    public final Runnable mUpdater;
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
        this.mDeltaHeight = 0;
        this.mDeltaWidth = 0;
        this.mDefaultBgLeft = 0;
        this.mDefaultBgTop = 0;
        this.mDefaultBgRight = 0;
        this.mDefaultBgBottom = 0;
        this.mStopDragFlag = false;
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
                        Handler handler = AmapSwitch.this.getHandler();
                        if (handler != null) {
                            handler.post(AmapSwitch.this.mSwitchUpdater);
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
                        Handler handler = AmapSwitch.this.getHandler();
                        if (handler != null) {
                            handler.post(AmapSwitch.this.mUpdater);
                            return;
                        }
                    }
                }
                AmapSwitch.this.stopAnimation();
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AmapSwitch, i, 0);
        this.mThumbDrawable = obtainStyledAttributes.getDrawable(R.styleable.AmapSwitch_amap_thumb);
        if (this.mThumbDrawable == null) {
            this.mThumbDrawable = ContextCompat.getDrawable(context, R.drawable.switch_def_thumb_selector);
        }
        this.mTrackDrawable = obtainStyledAttributes.getDrawable(R.styleable.AmapSwitch_amap_track);
        if (this.mTrackDrawable == null) {
            this.mTrackDrawable = ContextCompat.getDrawable(context, R.drawable.switch_def_track);
        }
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.AmapSwitch_switch_on_color, -1);
        if (resourceId == -1) {
            this.mTrackOnColor = ContextCompat.getColor(context, R.color.c_12);
        } else {
            this.mTrackOnColor = ContextCompat.getColor(context, resourceId);
        }
        this.mInnerBorderWithPx = obtainStyledAttributes.getDimension(R.styleable.AmapSwitch_inner_border, TypedValue.applyDimension(1, 1.5f, getResources().getDisplayMetrics()));
        this.mTrackDefaultDrawable = obtainStyledAttributes.getDrawable(R.styleable.AmapSwitch_switch_background);
        if (this.mTrackDefaultDrawable == null) {
            this.mTrackDefaultDrawable = ContextCompat.getDrawable(context, R.drawable.switch_track_bg);
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
        Handler handler = getHandler();
        if (handler != null) {
            this.mSwitchStartTime = System.currentTimeMillis();
            this.switchAnimHeight = this.mDefaultBgBottom - this.mDefaultBgTop;
            this.switchAnimWidth = this.mDefaultBgRight - this.mDefaultBgLeft;
            if (this.mReverse) {
                this.mDeltaHeight = this.switchAnimHeight / 2;
                this.mDeltaWidth = this.switchAnimWidth / 2;
            }
            this.mSwitchAnimDuration = 300.0f;
            this.mRunning = true;
            handler.post(this.mSwitchUpdater);
        }
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean onTouchEvent = super.onTouchEvent(motionEvent);
        switch (motionEvent.getActionMasked()) {
            case 0:
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                if (isEnabled() && hitThumb(x, y)) {
                    this.mTouchMode = 1;
                    this.mStopDragFlag = false;
                    this.mTouchX = x;
                    this.mTouchY = y;
                }
                if (isChecked()) {
                    return onTouchEvent;
                }
                this.mReverse = false;
                startTrackAnimation();
                return onTouchEvent;
            case 1:
            case 3:
                if (this.mTouchMode == 2) {
                    doActionWhenDragStopped(motionEvent);
                    break;
                } else {
                    this.mTouchMode = 0;
                    if (isChecked()) {
                        return onTouchEvent;
                    }
                    this.mReverse = true;
                    startTrackAnimation();
                    return onTouchEvent;
                }
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
                            break;
                        } else {
                            return onTouchEvent;
                        }
                        break;
                    case 2:
                        float x3 = motionEvent.getX();
                        float max = Math.max(0.0f, Math.min(this.mThumbPosition + (x3 - this.mTouchX), (float) getThumbScrollRange()));
                        if (this.mThumbPosition > ((float) (getThumbScrollRange() / 2))) {
                            setCheckedSsync(true);
                        } else {
                            setCheckedSsync(false);
                        }
                        if (max != this.mThumbPosition) {
                            this.mThumbPosition = max;
                            this.mTouchX = x3;
                            invalidate();
                            break;
                        }
                        break;
                    default:
                        return onTouchEvent;
                }
            default:
                return onTouchEvent;
        }
        return true;
    }

    private void cancelSuperTouch(MotionEvent motionEvent) {
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        obtain.setAction(3);
        super.onTouchEvent(obtain);
        obtain.recycle();
    }

    private void doActionWhenDragStopped(MotionEvent motionEvent) {
        this.mTouchMode = 0;
        this.mStopDragFlag = true;
        boolean z = motionEvent.getAction() == 1 && isEnabled();
        if (z) {
            this.mStateChangedFromStopDrag = true;
        }
        cancelSuperTouch(motionEvent);
        this.mStateChangedFromStopDrag = false;
        if (z) {
            setCheckedSsync(getTargetCheckedState());
            startAnimation();
        }
    }

    public void setCheckedSsync(final boolean z) {
        Handler handler = getHandler();
        if (handler != null) {
            handler.post(new Runnable() {
                public final void run() {
                    AmapSwitch.this.setChecked(z);
                }
            });
        }
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
        this.mDeltaHeight = (int) ((((float) this.switchAnimHeight) - (f2 + ((fArr[0] - f2) * f))) / 2.0f);
        this.mDeltaWidth = (int) ((((float) this.switchAnimWidth) - (f3 + ((fArr[1] - f3) * f))) / 2.0f);
        invalidate();
    }

    private void resetAnimation() {
        this.mStartTime = System.currentTimeMillis();
        this.mStartPosition = getThumbPosition();
        this.mAnimDuration = (float) ((int) (this.mMaxAnimDuration * (1.0f - this.mStartPosition)));
    }

    private void startAnimation() {
        Handler handler = getHandler();
        if (handler != null) {
            resetAnimation();
            this.mTumbRunning = true;
            handler.post(this.mUpdater);
        } else {
            setThumbPosition(1.0f);
        }
        invalidate();
    }

    /* access modifiers changed from: private */
    public void stopSwitchAnimation() {
        this.mRunning = false;
        Handler handler = getHandler();
        if (handler != null) {
            handler.removeCallbacks(this.mSwitchUpdater);
        }
        invalidate();
    }

    public void toggle() {
        super.toggle();
        this.mStopDragFlag = false;
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
        Handler handler = getHandler();
        if (handler != null) {
            handler.removeCallbacks(this.mUpdater);
        }
        if (this.mStopDragFlag && !isChecked()) {
            this.mReverse = true;
            startTrackAnimation();
        }
        invalidate();
    }

    public void onMeasure(int i, int i2) {
        if (!this.mStopDragFlag || this.mSwitchWidth <= 0 || this.mSwitchHeight <= 0) {
            this.mThumbWidth = this.mThumbDrawable.getIntrinsicWidth();
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
        int paddingRight = this.mSwitchWidth - getPaddingRight();
        this.mSwitchLeft = paddingRight - this.mSwitchWidth;
        this.mThumbPosition = isChecked() ? (float) getThumbScrollRange() : 0.0f;
        int intrinsicHeight = this.mTrackDrawable.getIntrinsicHeight();
        Drawable drawable = this.mTrackDrawable;
        int i5 = this.mSwitchLeft;
        int i6 = this.mSwitchTop;
        drawable.setBounds(i5, i6, paddingRight, i6 + intrinsicHeight);
        this.mDefaultBgBottom = this.mSwitchTop + intrinsicHeight;
        this.mDefaultBgLeft = this.mSwitchLeft;
        this.mDefaultBgRight = paddingRight;
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
        }
        this.mTrackDrawable.draw(canvas);
        if (!isChecked() && this.mTouchMode != 2) {
            this.mTrackDefaultDrawable.setBounds(this.mDefaultBgLeft + this.mDeltaWidth, this.mDefaultBgTop + this.mDeltaHeight, this.mDefaultBgRight - this.mDeltaWidth, this.mDefaultBgBottom - this.mDeltaHeight);
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
        this.mStopDragFlag = false;
        startAnimation();
        if (!isChecked() && (isChecked() != this.mLastChecked || this.mStateChangedFromStopDrag)) {
            this.mReverse = true;
            startTrackAnimation();
        }
        this.mLastChecked = isChecked();
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(@NonNull Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mThumbDrawable || drawable == this.mTrackDrawable;
    }
}
