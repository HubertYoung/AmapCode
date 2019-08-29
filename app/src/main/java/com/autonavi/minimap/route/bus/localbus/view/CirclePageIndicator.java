package com.autonavi.minimap.route.bus.localbus.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import com.autonavi.minimap.R;

public class CirclePageIndicator extends View implements PageIndicator {
    private static final int INVALID_POINTER = -1;
    private int mActivePointerId;
    private boolean mCentered;
    private int mCurrentPage;
    private float mDistance;
    private boolean mIsDragging;
    private float mLastMotionX;
    private OnPageChangeListener mListener;
    private int mOrientation;
    private float mPageOffset;
    private final Paint mPaintFill;
    private final Paint mPaintPageFill;
    private final Paint mPaintStroke;
    private float mRadius;
    private int mScrollState;
    private boolean mSnap;
    private int mSnapPage;
    private int mTouchSlop;
    private ViewPager mViewPager;

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
                return new SavedState[i];
            }

            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, 0);
            }
        };
        int a;

        /* synthetic */ SavedState(Parcel parcel, byte b) {
            this(parcel);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.a = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.a);
        }
    }

    public CirclePageIndicator(Context context) {
        this(context, null);
    }

    public CirclePageIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.vpiCirclePageIndicatorStyle);
    }

    public CirclePageIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPaintPageFill = new Paint(1);
        this.mPaintStroke = new Paint(1);
        this.mPaintFill = new Paint(1);
        this.mLastMotionX = -1.0f;
        this.mActivePointerId = -1;
        if (!isInEditMode()) {
            Resources resources = getResources();
            int color = resources.getColor(R.color.default_circle_indicator_page_color);
            int color2 = resources.getColor(R.color.default_circle_indicator_fill_color);
            int integer = resources.getInteger(R.integer.default_circle_indicator_orientation);
            int color3 = resources.getColor(R.color.default_circle_indicator_stroke_color);
            float dimension = resources.getDimension(R.dimen.default_circle_indicator_stroke_width);
            float dimension2 = resources.getDimension(R.dimen.default_circle_indicator_radius);
            boolean z = resources.getBoolean(R.bool.default_circle_indicator_centered);
            boolean z2 = resources.getBoolean(R.bool.default_circle_indicator_snap);
            float dimension3 = resources.getDimension(R.dimen.default_circle_indicator_stroke_width);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CirclePageIndicator, i, 0);
            this.mCentered = obtainStyledAttributes.getBoolean(R.styleable.CirclePageIndicator_centered, z);
            this.mOrientation = obtainStyledAttributes.getInt(R.styleable.CirclePageIndicator_android_orientation, integer);
            this.mPaintPageFill.setStyle(Style.FILL);
            this.mPaintPageFill.setColor(obtainStyledAttributes.getColor(R.styleable.CirclePageIndicator_pageColor, color));
            this.mPaintStroke.setStyle(Style.STROKE);
            this.mPaintStroke.setColor(obtainStyledAttributes.getColor(R.styleable.CirclePageIndicator_strokeColor, color3));
            this.mPaintStroke.setStrokeWidth(obtainStyledAttributes.getDimension(R.styleable.CirclePageIndicator_route_strokeWidth, dimension));
            this.mPaintFill.setStyle(Style.FILL);
            this.mPaintFill.setColor(obtainStyledAttributes.getColor(R.styleable.CirclePageIndicator_fillColor, color2));
            this.mRadius = obtainStyledAttributes.getDimension(R.styleable.CirclePageIndicator_route_radius, dimension2);
            this.mSnap = obtainStyledAttributes.getBoolean(R.styleable.CirclePageIndicator_snap, z2);
            this.mDistance = obtainStyledAttributes.getDimension(R.styleable.CirclePageIndicator_distance, dimension3);
            Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.CirclePageIndicator_android_background);
            if (drawable != null) {
                setBackgroundDrawable(drawable);
            }
            obtainStyledAttributes.recycle();
            this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context));
        }
    }

    public void setCentered(boolean z) {
        this.mCentered = z;
        invalidate();
    }

    public boolean isCentered() {
        return this.mCentered;
    }

    public void setPageColor(int i) {
        this.mPaintPageFill.setColor(i);
        invalidate();
    }

    public int getPageColor() {
        return this.mPaintPageFill.getColor();
    }

    public void setFillColor(int i) {
        this.mPaintFill.setColor(i);
        invalidate();
    }

    public int getFillColor() {
        return this.mPaintFill.getColor();
    }

    public void setOrientation(int i) {
        switch (i) {
            case 0:
            case 1:
                this.mOrientation = i;
                requestLayout();
                return;
            default:
                throw new IllegalArgumentException("Orientation must be either HORIZONTAL or VERTICAL.");
        }
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setStrokeColor(int i) {
        this.mPaintStroke.setColor(i);
        invalidate();
    }

    public int getStrokeColor() {
        return this.mPaintStroke.getColor();
    }

    public void setStrokeWidth(float f) {
        this.mPaintStroke.setStrokeWidth(f);
        invalidate();
    }

    public float getStrokeWidth() {
        return this.mPaintStroke.getStrokeWidth();
    }

    public void setRadius(float f) {
        this.mRadius = f;
        invalidate();
    }

    public float getRadius() {
        return this.mRadius;
    }

    public void setSnap(boolean z) {
        this.mSnap = z;
        invalidate();
    }

    public boolean isSnap() {
        return this.mSnap;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int i;
        int i2;
        int i3;
        int i4;
        float f;
        float f2;
        super.onDraw(canvas);
        if (this.mViewPager != null) {
            int count = this.mViewPager.getAdapter().getCount();
            if (count != 0) {
                if (this.mCurrentPage >= count) {
                    setCurrentItem(count - 1);
                    return;
                }
                if (this.mOrientation == 0) {
                    i4 = getWidth();
                    i3 = getPaddingLeft();
                    i2 = getPaddingRight();
                    i = getPaddingTop();
                } else {
                    i4 = getHeight();
                    i3 = getPaddingTop();
                    i2 = getPaddingBottom();
                    i = getPaddingLeft();
                }
                float f3 = (this.mRadius * 2.0f) + this.mDistance;
                float f4 = ((float) i) + this.mRadius;
                float f5 = ((float) i3) + this.mRadius;
                if (this.mCentered) {
                    f5 += (((float) ((i4 - i3) - i2)) / 2.0f) - ((((float) count) * f3) / 2.0f);
                }
                float f6 = this.mRadius;
                if (this.mPaintStroke.getStrokeWidth() > 0.0f) {
                    f6 -= this.mPaintStroke.getStrokeWidth() / 2.0f;
                }
                for (int i5 = 0; i5 < count; i5++) {
                    float f7 = (((float) i5) * f3) + f5;
                    if (this.mOrientation == 0) {
                        f2 = f4;
                    } else {
                        f2 = f7;
                        f7 = f4;
                    }
                    if (this.mPaintPageFill.getAlpha() > 0) {
                        canvas.drawCircle(f7, f2, f6, this.mPaintPageFill);
                    }
                    if (((double) Math.abs(f6 - this.mRadius)) < 1.0E-7d) {
                        canvas.drawCircle(f7, f2, this.mRadius, this.mPaintStroke);
                    }
                }
                float f8 = ((float) (this.mSnap ? this.mSnapPage : this.mCurrentPage)) * f3;
                if (!this.mSnap) {
                    f8 += this.mPageOffset * f3;
                }
                if (this.mOrientation == 0) {
                    f = f8 + f5;
                } else {
                    float f9 = f4;
                    f4 = f8 + f5;
                    f = f9;
                }
                canvas.drawCircle(f, f4, this.mRadius, this.mPaintFill);
            }
        }
    }

    public void setDistance(float f) {
        this.mDistance = f;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (super.onTouchEvent(motionEvent)) {
            return true;
        }
        int i = 0;
        if (this.mViewPager == null || this.mViewPager.getAdapter().getCount() == 0) {
            return false;
        }
        int action = motionEvent.getAction() & 255;
        switch (action) {
            case 0:
                this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                this.mLastMotionX = motionEvent.getX();
                break;
            case 1:
            case 3:
                if (!this.mIsDragging) {
                    int count = this.mViewPager.getAdapter().getCount();
                    float width = (float) getWidth();
                    float f = width / 2.0f;
                    float f2 = width / 6.0f;
                    if (this.mCurrentPage > 0 && motionEvent.getX() < f - f2) {
                        if (action != 3) {
                            this.mViewPager.setCurrentItem(this.mCurrentPage - 1);
                        }
                        return true;
                    } else if (this.mCurrentPage < count - 1 && motionEvent.getX() > f + f2) {
                        if (action != 3) {
                            this.mViewPager.setCurrentItem(this.mCurrentPage + 1);
                        }
                        return true;
                    }
                }
                this.mIsDragging = false;
                this.mActivePointerId = -1;
                if (this.mViewPager.isFakeDragging()) {
                    try {
                        this.mViewPager.endFakeDrag();
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }
                break;
            case 2:
                int findPointerIndex = MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId);
                if (findPointerIndex >= 0) {
                    float x = MotionEventCompat.getX(motionEvent, findPointerIndex);
                    float f3 = x - this.mLastMotionX;
                    if (!this.mIsDragging && Math.abs(f3) > ((float) this.mTouchSlop)) {
                        this.mIsDragging = true;
                    }
                    if (this.mIsDragging) {
                        this.mLastMotionX = x;
                        if (this.mViewPager.isFakeDragging() || this.mViewPager.beginFakeDrag()) {
                            try {
                                this.mViewPager.fakeDragBy(f3);
                                break;
                            } catch (Exception e2) {
                                e2.printStackTrace();
                                break;
                            }
                        }
                    }
                }
                break;
            case 5:
                int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
                this.mLastMotionX = MotionEventCompat.getX(motionEvent, actionIndex);
                this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, actionIndex);
                break;
            case 6:
                int actionIndex2 = MotionEventCompat.getActionIndex(motionEvent);
                if (MotionEventCompat.getPointerId(motionEvent, actionIndex2) == this.mActivePointerId) {
                    if (actionIndex2 == 0) {
                        i = 1;
                    }
                    this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, i);
                }
                this.mLastMotionX = MotionEventCompat.getX(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId));
                break;
        }
        return true;
    }

    public void setViewPager(ViewPager viewPager) {
        if (this.mViewPager != viewPager) {
            if (this.mViewPager != null) {
                this.mViewPager.setOnPageChangeListener(null);
            }
            if (viewPager.getAdapter() == null) {
                throw new IllegalStateException("ViewPager does not have adapter instance.");
            }
            this.mViewPager = viewPager;
            this.mViewPager.setOnPageChangeListener(this);
            invalidate();
        }
    }

    public void setViewPager(ViewPager viewPager, int i) {
        setViewPager(viewPager);
        setCurrentItem(i);
    }

    public void setCurrentItem(int i) {
        if (this.mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        this.mViewPager.setCurrentItem(i);
        this.mCurrentPage = i;
        invalidate();
    }

    public void notifyDataSetChanged() {
        invalidate();
    }

    public void onPageScrollStateChanged(int i) {
        this.mScrollState = i;
        if (this.mListener != null) {
            this.mListener.onPageScrollStateChanged(i);
        }
    }

    public void onPageScrolled(int i, float f, int i2) {
        this.mCurrentPage = i;
        this.mPageOffset = f;
        invalidate();
        if (this.mListener != null) {
            this.mListener.onPageScrolled(i, f, i2);
        }
    }

    public void onPageSelected(int i) {
        if (this.mSnap || this.mScrollState == 0) {
            this.mCurrentPage = i;
            this.mSnapPage = i;
            invalidate();
        }
        if (this.mListener != null) {
            this.mListener.onPageSelected(i);
        }
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mListener = onPageChangeListener;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        if (this.mOrientation == 0) {
            setMeasuredDimension(measureLong(i), measureShort(i2));
        } else {
            setMeasuredDimension(measureShort(i), measureLong(i2));
        }
    }

    private int measureLong(int i) {
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        if (mode == 1073741824 || this.mViewPager == null) {
            return size;
        }
        int count = this.mViewPager.getAdapter().getCount();
        int paddingLeft = (int) (((float) (getPaddingLeft() + getPaddingRight())) + (((float) (count * 2)) * this.mRadius) + (((float) (count - 1)) * this.mRadius) + 1.0f);
        return mode == Integer.MIN_VALUE ? Math.min(paddingLeft, size) : paddingLeft;
    }

    private int measureShort(int i) {
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        if (mode == 1073741824) {
            return size;
        }
        int paddingTop = (int) ((this.mRadius * 2.0f) + ((float) getPaddingTop()) + ((float) getPaddingBottom()) + 1.0f);
        return mode == Integer.MIN_VALUE ? Math.min(paddingTop, size) : paddingTop;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mCurrentPage = savedState.a;
        this.mSnapPage = savedState.a;
        requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.a = this.mCurrentPage;
        return savedState;
    }
}
