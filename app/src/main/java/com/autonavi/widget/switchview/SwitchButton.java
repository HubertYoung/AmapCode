package com.autonavi.widget.switchview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Property;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.CompoundButton;
import com.autonavi.widget.R;

public class SwitchButton extends CompoundButton {
    private static final int DEFAULT_BORDER_COLOR = Color.parseColor("#e9e9e9");
    private static final float DEFAULT_BORDER_WIDTH = 1.5f;
    private static final int DEFAULT_CHECKED_COLOR = Color.parseColor("#40b7ff");
    private static final int DEFAULT_HEIGHT = 30;
    private static final int DEFAULT_UNCHECKED_COLOR = Color.parseColor("#ffffff");
    private static final int DEFAULT_WIDTH = 50;
    private static final int THUMB_ANIMATION_DURATION = 250;
    private static final int TOUCH_MODE_DOWN = 1;
    private static final int TOUCH_MODE_DRAGGING = 2;
    private static final int TOUCH_MODE_IDLE = 0;
    private final a<SwitchButton> THUMB_POS;
    private float mMinFlingVelocity;
    private Drawable mThumbDrawable;
    private int mThumbPadding;
    /* access modifiers changed from: private */
    public int mThumbPosition;
    private int mTouchMode;
    private int mTouchSlop;
    private float mTouchX;
    private float mTouchY;
    private Drawable mTrackDrawable;
    private VelocityTracker mVelocityTracker;

    public static abstract class a<T> extends Property<T, Float> {
        public abstract void a(T t, float f);

        public /* synthetic */ void set(Object obj, Object obj2) {
            a(obj, ((Float) obj2).floatValue());
        }

        public a(String str) {
            super(Float.class, str);
        }
    }

    public SwitchButton(Context context) {
        this(context, null);
    }

    public SwitchButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SwitchButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mThumbPosition = Integer.MIN_VALUE;
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mThumbPadding = 1;
        this.THUMB_POS = new a<SwitchButton>("thumbPos") {
            public final /* synthetic */ void a(Object obj, float f) {
                ((SwitchButton) obj).setThumbPosition(f);
            }

            public final /* synthetic */ Object get(Object obj) {
                return Float.valueOf((float) ((SwitchButton) obj).mThumbPosition);
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SwitchButton, i, 0);
        if (obtainStyledAttributes != null) {
            this.mThumbDrawable = obtainStyledAttributes.getDrawable(R.styleable.SwitchButton_switch_thumb);
            this.mThumbPadding = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SwitchButton_switch_thumbPadding, dp2px((float) this.mThumbPadding));
            this.mTrackDrawable = obtainStyledAttributes.getDrawable(R.styleable.SwitchButton_switch_track);
            obtainStyledAttributes.recycle();
        }
        setClickable(true);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinFlingVelocity = (float) viewConfiguration.getScaledMinimumFlingVelocity();
    }

    private Drawable createDefaultTrackDrawable(int i, int i2) {
        float f = (float) (i2 / 2);
        float[] fArr = {f, f, f, f, f, f, f, f};
        StateListDrawable stateListDrawable = new StateListDrawable();
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadii(fArr);
        gradientDrawable.setStroke(dp2px(DEFAULT_BORDER_WIDTH), DEFAULT_BORDER_COLOR);
        gradientDrawable.setColor(DEFAULT_UNCHECKED_COLOR);
        setTrackBounds(gradientDrawable, i, i2);
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setCornerRadii(fArr);
        gradientDrawable2.setColor(DEFAULT_CHECKED_COLOR);
        setTrackBounds(gradientDrawable2, i, i2);
        stateListDrawable.addState(new int[]{16842912}, gradientDrawable2);
        stateListDrawable.addState(new int[0], gradientDrawable);
        setTrackBounds(stateListDrawable, i, i2);
        stateListDrawable.setCallback(this);
        return stateListDrawable;
    }

    private void setTrackBounds(Drawable drawable, int i, int i2) {
        drawable.setBounds(getPaddingLeft(), getPaddingTop(), i - getPaddingRight(), i2 - getPaddingBottom());
    }

    private Drawable createDefaultThumbDrawable(int i, int i2) {
        float f = (float) ((i2 / 2) - this.mThumbPadding);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(DEFAULT_UNCHECKED_COLOR);
        gradientDrawable.setCornerRadii(new float[]{f, f, f, f, f, f, f, f});
        gradientDrawable.setStroke(dp2px(DEFAULT_BORDER_WIDTH), DEFAULT_BORDER_COLOR);
        setThumbBounds(gradientDrawable, i, i2);
        return gradientDrawable;
    }

    private void setThumbBounds(Drawable drawable, int i, int i2) {
        int paddingTop = ((int) ((float) ((((i2 - getPaddingTop()) - getPaddingBottom()) / 2) - this.mThumbPadding))) * 2;
        drawable.setBounds(this.mThumbPadding + getPaddingLeft(), this.mThumbPadding + getPaddingTop(), this.mThumbPadding + paddingTop + getPaddingLeft(), this.mThumbPadding + paddingTop + getPaddingTop());
    }

    public void setChecked(boolean z) {
        setChecked(z, true);
    }

    public void setChecked(boolean z, boolean z2) {
        super.setChecked(z);
        if (z2) {
            animateThumbToCheckedState(z);
            return;
        }
        if (this.mThumbPosition != Integer.MIN_VALUE) {
            this.mThumbPosition = z ? getThumbPositionRange() : 0;
            invalidate();
        }
    }

    public void setThumbDrawable(Drawable drawable) {
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.setCallback(null);
        }
        this.mThumbDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        requestLayout();
    }

    public Drawable getThumbDrawable() {
        return this.mThumbDrawable;
    }

    public void setTrackDrawable(Drawable drawable) {
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setCallback(null);
        }
        this.mTrackDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        requestLayout();
    }

    public Drawable getTrackDrawable() {
        return this.mTrackDrawable;
    }

    public void setThumbPadding(int i) {
        this.mThumbPadding = i;
        requestLayout();
    }

    public int getThumbPadding() {
        return this.mThumbPadding;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mVelocityTracker.addMovement(motionEvent);
        switch (motionEvent.getActionMasked()) {
            case 0:
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                if (isEnabled() && hitThumb(x, y)) {
                    this.mTouchMode = 1;
                    this.mTouchX = x;
                    this.mTouchY = y;
                    break;
                }
            case 1:
            case 3:
                if (this.mTouchMode != 2) {
                    this.mTouchMode = 0;
                    this.mVelocityTracker.clear();
                    break;
                } else {
                    stopDrag(motionEvent);
                    super.onTouchEvent(motionEvent);
                    return true;
                }
            case 2:
                switch (this.mTouchMode) {
                    case 1:
                        float x2 = motionEvent.getX();
                        float y2 = motionEvent.getY();
                        if (Math.abs(x2 - this.mTouchX) > ((float) this.mTouchSlop) || Math.abs(y2 - this.mTouchY) > ((float) this.mTouchSlop)) {
                            this.mTouchMode = 2;
                            getParent().requestDisallowInterceptTouchEvent(true);
                            this.mTouchX = x2;
                            this.mTouchY = y2;
                            return true;
                        }
                    case 2:
                        float x3 = motionEvent.getX();
                        float constrain = constrain(((float) this.mThumbPosition) + (x3 - this.mTouchX));
                        if (constrain != ((float) this.mThumbPosition)) {
                            this.mTouchX = x3;
                            setThumbPosition(constrain);
                        }
                        return true;
                }
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        setMeasuredDimension(resolveSize(dp2px(50.0f) + getPaddingLeft() + getPaddingRight(), i), resolveSize(dp2px(30.0f) + getPaddingTop() + getPaddingBottom(), i2));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mThumbDrawable == null) {
            this.mThumbDrawable = createDefaultThumbDrawable(getMeasuredWidth(), getMeasuredHeight());
        } else if (this.mThumbDrawable.getBounds().width() == 0) {
            setThumbBounds(this.mThumbDrawable, getMeasuredWidth(), getMeasuredHeight());
        }
        if (this.mTrackDrawable == null) {
            this.mTrackDrawable = createDefaultTrackDrawable(getMeasuredWidth(), getMeasuredHeight());
        } else if (this.mTrackDrawable.getBounds().width() == 0) {
            setTrackBounds(this.mTrackDrawable, getMeasuredWidth(), getMeasuredHeight());
        }
        if (this.mThumbPosition == Integer.MIN_VALUE) {
            this.mThumbPosition = (int) getThumbPossition(isChecked());
            if (this.mTrackDrawable instanceof StateListDrawable) {
                this.mTrackDrawable.setState(getDrawableState());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mTrackDrawable instanceof StateListDrawable) {
            this.mTrackDrawable.setState(getDrawableState());
        }
    }

    private void stopDrag(MotionEvent motionEvent) {
        boolean z;
        this.mTouchMode = 0;
        boolean z2 = true;
        boolean z3 = motionEvent.getAction() == 1 && isEnabled();
        boolean isChecked = isChecked();
        if (z3) {
            this.mVelocityTracker.computeCurrentVelocity(1000);
            float xVelocity = this.mVelocityTracker.getXVelocity();
            if (Math.abs(xVelocity) > this.mMinFlingVelocity) {
                if (xVelocity <= 0.0f) {
                    z2 = false;
                }
                z = z2;
            } else {
                z = getTargetCheckedState();
            }
        } else {
            z = isChecked;
        }
        if (z != isChecked) {
            playSoundEffect(0);
        }
        setChecked(z);
        cancelSuperTouch(motionEvent);
    }

    private void cancelSuperTouch(MotionEvent motionEvent) {
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        obtain.setAction(3);
        super.onTouchEvent(obtain);
        obtain.recycle();
    }

    private boolean getTargetCheckedState() {
        return this.mThumbPosition > ((int) (((((float) getWidth()) / 2.0f) - (((float) this.mThumbDrawable.getBounds().width()) / 2.0f)) + 0.5f));
    }

    /* access modifiers changed from: 0000 */
    public float constrain(float f) {
        float f2 = (float) (-this.mThumbPadding);
        float width = (float) ((((getWidth() - this.mThumbDrawable.getBounds().width()) - this.mThumbPadding) - getPaddingRight()) - getPaddingLeft());
        if (f <= f2) {
            return f2;
        }
        return f >= width ? width : f;
    }

    private boolean hitThumb(float f, float f2) {
        if (this.mThumbDrawable == null) {
            return false;
        }
        Rect rect = new Rect();
        this.mThumbDrawable.getPadding(rect);
        int i = this.mThumbDrawable.getBounds().top - this.mTouchSlop;
        int i2 = this.mThumbDrawable.getBounds().left - this.mTouchSlop;
        int width = this.mThumbDrawable.getBounds().width() + i2 + rect.left + rect.right + this.mTouchSlop;
        int i3 = this.mThumbDrawable.getBounds().bottom + this.mTouchSlop;
        if (f <= ((float) i2) || f >= ((float) width) || f2 <= ((float) i) || f2 >= ((float) i3)) {
            return false;
        }
        return true;
    }

    private void animateThumbToCheckedState(boolean z) {
        if (getWidth() != 0 && getHeight() != 0) {
            float thumbPossition = getThumbPossition(z);
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, this.THUMB_POS, new float[]{thumbPossition});
            ofFloat.setDuration(250);
            if (VERSION.SDK_INT >= 18) {
                ofFloat.setAutoCancel(true);
            }
            ofFloat.start();
        }
    }

    private float getThumbPossition(boolean z) {
        if (z) {
            return (float) getThumbPositionRange();
        }
        return 0.0f;
    }

    private int getThumbPositionRange() {
        return getWidth() - getHeight();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        this.mTrackDrawable.draw(canvas);
        translateThumb(this.mThumbDrawable, this.mThumbPosition);
        this.mThumbDrawable.draw(canvas);
    }

    private void translateThumb(Drawable drawable, int i) {
        drawable.setBounds(this.mThumbPadding + i + getPaddingLeft(), this.mThumbPadding + getPaddingTop(), this.mThumbPadding + this.mThumbDrawable.getBounds().width() + i + getPaddingLeft(), this.mThumbPadding + this.mThumbDrawable.getBounds().height() + getPaddingTop());
    }

    /* access modifiers changed from: private */
    public void setThumbPosition(float f) {
        this.mThumbPosition = (int) f;
        invalidate();
    }

    private int dp2px(float f) {
        return (int) ((f * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }
}
