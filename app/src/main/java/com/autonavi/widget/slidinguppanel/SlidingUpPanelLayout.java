package com.autonavi.widget.slidinguppanel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.autonavi.widget.R;
import com.uc.webview.export.extension.UCCore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class SlidingUpPanelLayout extends ViewGroup {
    private static final float DEFAULT_ANCHOR_POINT = 1.0f;
    private static final int DEFAULT_MIN_FLING_VELOCITY = 600;
    private static final int DEFAULT_PANEL_HEIGHT = 48;
    private static PanelState DEFAULT_SLIDE_STATE = PanelState.COLLAPSED;
    private boolean mActionDownOnSlideableView;
    private int mAnchorHeight;
    /* access modifiers changed from: private */
    public int mAnchorHeightExtraIndex;
    private ArrayList<Integer> mAnchorHeightExtras;
    /* access modifiers changed from: private */
    public float mAnchorPoint;
    /* access modifiers changed from: private */
    public ArrayList<Float> mAnchorPointExtras;
    private float mAnchorPointInParent;
    /* access modifiers changed from: private */
    public boolean mCaptureViewOnDraggingState;
    private boolean mDragEnabled;
    /* access modifiers changed from: private */
    public final erk mDragHelper;
    private boolean mDragHorizontalIntercept;
    /* access modifiers changed from: private */
    public float mDragLeverage;
    private View mDragView;
    private int mDragViewResId;
    private int mExpandHeight;
    /* access modifiers changed from: private */
    public float mExpandPoint;
    private float mExpandPointInParent;
    private boolean mFirstLayout;
    private boolean mHookMotionMoveUpHandled;
    private boolean mHookMotionRunning;
    private float mHookMotionY;
    private erj mHookScrollableView;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private int mInterceptTouchSlop;
    private PanelState mLastNotDraggingSlideState;
    private PanelState mLastNotHiddenSlideState;
    private PanelState mLastSlideState;
    private View mMainView;
    private int mMinFlingVelocity;
    /* access modifiers changed from: private */
    public b mPanelDragStateChangeListener;
    private int mPanelHeight;
    private View mPanelHoverView;
    private List<c> mPanelSlideListeners;
    private int mPanelTransparentTop;
    private boolean mScrollAtTop;
    private erj mScrollableView;
    /* access modifiers changed from: private */
    public int mShrinkHeight;
    /* access modifiers changed from: private */
    public float mSlideOffset;
    /* access modifiers changed from: private */
    public float mSlideOffsetSlop;
    /* access modifiers changed from: private */
    public int mSlideRange;
    private int mSlideRangePadding;
    /* access modifiers changed from: private */
    public PanelState mSlideState;
    /* access modifiers changed from: private */
    public boolean mSlideStateWithoutLayout;
    /* access modifiers changed from: private */
    public View mSlideableView;

    public static class LayoutParams extends MarginLayoutParams {
        private static final int[] b = {16843137};
        public float a = 0.0f;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, b);
            if (obtainStyledAttributes != null) {
                this.a = obtainStyledAttributes.getFloat(0, 0.0f);
            }
            obtainStyledAttributes.recycle();
        }
    }

    public enum PanelState {
        EXPANDED,
        COLLAPSED,
        ANCHORED,
        HIDDEN,
        DRAGGING
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
                return new SavedState[i];
            }

            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, 0);
            }
        };
        PanelState a;

        /* synthetic */ SavedState(Parcel parcel, byte b) {
            this(parcel);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            PanelState panelState;
            super(parcel);
            String readString = parcel.readString();
            if (readString != null) {
                try {
                    panelState = (PanelState) Enum.valueOf(PanelState.class, readString);
                } catch (IllegalArgumentException unused) {
                    this.a = PanelState.COLLAPSED;
                    return;
                }
            } else {
                panelState = PanelState.COLLAPSED;
            }
            this.a = panelState;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.a == null ? null : this.a.toString());
        }
    }

    class a extends defpackage.erk.a {
        private a() {
        }

        /* synthetic */ a(SlidingUpPanelLayout slidingUpPanelLayout, byte b) {
            this();
        }

        public final boolean a(View view) {
            if ((SlidingUpPanelLayout.this.mCaptureViewOnDraggingState || SlidingUpPanelLayout.this.mSlideState != PanelState.DRAGGING) && view == SlidingUpPanelLayout.this.mSlideableView) {
                return true;
            }
            return false;
        }

        public final void a() {
            if (SlidingUpPanelLayout.this.mSlideStateWithoutLayout) {
                SlidingUpPanelLayout.this.mShrinkHeight = 0;
                return;
            }
            if (SlidingUpPanelLayout.this.mDragHelper.a == 0) {
                SlidingUpPanelLayout.this.mSlideOffset = SlidingUpPanelLayout.this.computeSlideOffset(SlidingUpPanelLayout.this.mSlideableView.getTop());
                if (SlidingUpPanelLayout.this.mSlideOffset == 1.0f) {
                    SlidingUpPanelLayout.this.updateObscuredViewVisibility();
                    SlidingUpPanelLayout.this.setPanelStateInternal(PanelState.EXPANDED);
                } else if (SlidingUpPanelLayout.this.mSlideOffset == 0.0f) {
                    SlidingUpPanelLayout.this.setPanelStateInternal(PanelState.COLLAPSED);
                } else if (SlidingUpPanelLayout.this.mSlideOffset < 0.0f) {
                    SlidingUpPanelLayout.this.mSlideableView.setVisibility(8);
                    SlidingUpPanelLayout.this.setPanelStateInternal(PanelState.HIDDEN);
                } else if (SlidingUpPanelLayout.this.mAnchorPoint == 1.0f || Math.abs(SlidingUpPanelLayout.this.mSlideOffset - SlidingUpPanelLayout.this.mAnchorPoint) > SlidingUpPanelLayout.this.mSlideOffsetSlop) {
                    int size = SlidingUpPanelLayout.this.mAnchorPointExtras.size();
                    int i = 0;
                    while (true) {
                        if (i >= size) {
                            break;
                        }
                        float floatValue = ((Float) SlidingUpPanelLayout.this.mAnchorPointExtras.get(i)).floatValue();
                        if (Math.abs(floatValue - SlidingUpPanelLayout.this.mSlideOffset) <= SlidingUpPanelLayout.this.mSlideOffsetSlop) {
                            SlidingUpPanelLayout.this.mAnchorHeightExtraIndex = i;
                            SlidingUpPanelLayout.this.mSlideOffset = floatValue;
                            SlidingUpPanelLayout.this.updateObscuredViewVisibility();
                            SlidingUpPanelLayout.this.setPanelStateInternal(PanelState.ANCHORED);
                            break;
                        }
                        i++;
                    }
                } else {
                    SlidingUpPanelLayout.this.mAnchorHeightExtraIndex = -1;
                    SlidingUpPanelLayout.this.mSlideOffset = SlidingUpPanelLayout.this.mAnchorPoint;
                    SlidingUpPanelLayout.this.updateObscuredViewVisibility();
                    SlidingUpPanelLayout.this.setPanelStateInternal(PanelState.ANCHORED);
                }
                if (SlidingUpPanelLayout.this.mSlideState == PanelState.DRAGGING) {
                    float a2 = a(SlidingUpPanelLayout.this.mSlideOffset, 0.0f);
                    if (a2 == 1.0f) {
                        float abs = Math.abs(SlidingUpPanelLayout.this.mSlideOffset - 1.0f);
                        float abs2 = SlidingUpPanelLayout.this.mAnchorPoint != 1.0f ? Math.abs(SlidingUpPanelLayout.this.mSlideOffset - SlidingUpPanelLayout.this.mAnchorPoint) : 1.0f;
                        float abs3 = Math.abs(SlidingUpPanelLayout.this.mSlideOffset);
                        if (abs >= abs2 || abs >= abs3) {
                            if (SlidingUpPanelLayout.this.mAnchorPoint == 1.0f || abs2 >= abs || abs2 >= abs3) {
                                if (abs3 >= abs || abs3 >= abs2) {
                                    if (!SlidingUpPanelLayout.this.smoothSlideTo(0.0f, 0)) {
                                        SlidingUpPanelLayout.this.mSlideOffset = 0.0f;
                                        SlidingUpPanelLayout.this.updateObscuredViewVisibility();
                                        SlidingUpPanelLayout.this.setPanelStateInternal(PanelState.COLLAPSED);
                                    }
                                } else if (!SlidingUpPanelLayout.this.smoothSlideTo(0.0f, 0)) {
                                    SlidingUpPanelLayout.this.mSlideOffset = 0.0f;
                                    SlidingUpPanelLayout.this.updateObscuredViewVisibility();
                                    SlidingUpPanelLayout.this.setPanelStateInternal(PanelState.COLLAPSED);
                                }
                            } else if (!SlidingUpPanelLayout.this.smoothSlideTo(SlidingUpPanelLayout.this.mAnchorPoint, 0)) {
                                SlidingUpPanelLayout.this.mAnchorHeightExtraIndex = -1;
                                SlidingUpPanelLayout.this.mSlideOffset = SlidingUpPanelLayout.this.mAnchorPoint;
                                SlidingUpPanelLayout.this.updateObscuredViewVisibility();
                                SlidingUpPanelLayout.this.setPanelStateInternal(PanelState.ANCHORED);
                            }
                        } else if (!SlidingUpPanelLayout.this.smoothSlideTo(1.0f, 0)) {
                            SlidingUpPanelLayout.this.updateObscuredViewVisibility();
                            SlidingUpPanelLayout.this.setPanelStateInternal(PanelState.EXPANDED);
                        }
                    } else if (!SlidingUpPanelLayout.this.smoothSlideTo(a2, 0)) {
                        SlidingUpPanelLayout.this.mAnchorHeightExtraIndex = SlidingUpPanelLayout.this.mAnchorPointExtras.indexOf(Float.valueOf(a2));
                        SlidingUpPanelLayout.this.mSlideOffset = a2;
                        SlidingUpPanelLayout.this.updateObscuredViewVisibility();
                        SlidingUpPanelLayout.this.setPanelStateInternal(PanelState.ANCHORED);
                    }
                }
            }
            SlidingUpPanelLayout.this.mShrinkHeight = 0;
        }

        public final void b() {
            SlidingUpPanelLayout.this.setAllChildrenVisible();
        }

        public final void a(int i) {
            SlidingUpPanelLayout.this.onPanelDragged(i);
            SlidingUpPanelLayout.this.invalidate();
        }

        public final void a(View view, float f) {
            int i;
            PanelState panelState = PanelState.COLLAPSED;
            float f2 = -f;
            float a2 = a(SlidingUpPanelLayout.this.mSlideOffset, f2);
            if (a2 != 1.0f) {
                i = SlidingUpPanelLayout.this.computePanelTopPosition(a2);
                SlidingUpPanelLayout.this.mAnchorPointExtras.indexOf(Float.valueOf(a2));
                panelState = PanelState.ANCHORED;
            } else {
                int i2 = (f2 > 0.0f ? 1 : (f2 == 0.0f ? 0 : -1));
                if (i2 > 0 && SlidingUpPanelLayout.this.mSlideOffset <= SlidingUpPanelLayout.this.mAnchorPoint) {
                    i = SlidingUpPanelLayout.this.computePanelTopPosition(SlidingUpPanelLayout.this.mAnchorPoint);
                    panelState = PanelState.ANCHORED;
                } else if (i2 <= 0 || SlidingUpPanelLayout.this.mSlideOffset <= SlidingUpPanelLayout.this.mAnchorPoint) {
                    int i3 = (f2 > 0.0f ? 1 : (f2 == 0.0f ? 0 : -1));
                    if (i3 >= 0 || SlidingUpPanelLayout.this.mSlideOffset < SlidingUpPanelLayout.this.mAnchorPoint) {
                        if (i3 >= 0 || SlidingUpPanelLayout.this.mSlideOffset >= SlidingUpPanelLayout.this.mAnchorPoint) {
                            if (SlidingUpPanelLayout.this.mSlideOffset >= SlidingUpPanelLayout.this.mExpandPoint) {
                                i = SlidingUpPanelLayout.this.computePanelTopPosition(1.0f);
                                panelState = PanelState.EXPANDED;
                            } else if (SlidingUpPanelLayout.this.mExpandPoint == 1.0f && SlidingUpPanelLayout.this.mSlideOffset >= (SlidingUpPanelLayout.this.mAnchorPoint + 1.0f) / 2.0f) {
                                i = SlidingUpPanelLayout.this.computePanelTopPosition(1.0f);
                                panelState = PanelState.EXPANDED;
                            } else if (SlidingUpPanelLayout.this.mSlideOffset >= SlidingUpPanelLayout.this.mAnchorPoint / 2.0f) {
                                i = SlidingUpPanelLayout.this.computePanelTopPosition(SlidingUpPanelLayout.this.mAnchorPoint);
                                panelState = PanelState.ANCHORED;
                            }
                        }
                        i = SlidingUpPanelLayout.this.computePanelTopPosition(0.0f);
                    } else {
                        i = SlidingUpPanelLayout.this.computePanelTopPosition(SlidingUpPanelLayout.this.mAnchorPoint);
                        panelState = PanelState.ANCHORED;
                    }
                } else {
                    i = SlidingUpPanelLayout.this.computePanelTopPosition(1.0f);
                    panelState = PanelState.EXPANDED;
                }
            }
            erk access$600 = SlidingUpPanelLayout.this.mDragHelper;
            int left = view.getLeft();
            if (!access$600.o) {
                throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
            }
            boolean a3 = access$600.a(left, i, (int) VelocityTrackerCompat.getXVelocity(access$600.i, access$600.c), (int) VelocityTrackerCompat.getYVelocity(access$600.i, access$600.c));
            SlidingUpPanelLayout.this.invalidate();
            if (a3 && SlidingUpPanelLayout.this.mPanelDragStateChangeListener != null && SlidingUpPanelLayout.this.mSlideState != panelState) {
                SlidingUpPanelLayout.this.mPanelDragStateChangeListener;
            }
        }

        private float a(float f, float f2) {
            float f3;
            int size = SlidingUpPanelLayout.this.mAnchorPointExtras.size();
            if (size == 0) {
                return 1.0f;
            }
            int i = 0;
            if (f2 > 0.0f) {
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    f3 = ((Float) SlidingUpPanelLayout.this.mAnchorPointExtras.get(i)).floatValue();
                    if (f3 > f) {
                        break;
                    }
                    i++;
                }
                return f3;
            } else if (f2 < 0.0f) {
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    f3 = ((Float) SlidingUpPanelLayout.this.mAnchorPointExtras.get(i)).floatValue();
                    if (f3 < f) {
                        break;
                    }
                    i++;
                }
                return f3;
            } else {
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < size; i2++) {
                    arrayList.add(Float.valueOf(Math.abs(((Float) SlidingUpPanelLayout.this.mAnchorPointExtras.get(i2)).floatValue() - f)));
                }
                arrayList.add(Float.valueOf(Math.abs(f)));
                arrayList.add(Float.valueOf(Math.abs(1.0f - f)));
                arrayList.add(Float.valueOf(Math.abs(SlidingUpPanelLayout.this.mExpandPoint - f)));
                arrayList.add(Float.valueOf(Math.abs(SlidingUpPanelLayout.this.mAnchorPoint - f)));
                int size2 = arrayList.size();
                float f4 = 1.0f;
                int i3 = -1;
                while (i < size2) {
                    float floatValue = ((Float) arrayList.get(i)).floatValue();
                    if (f4 > floatValue) {
                        i3 = i;
                        f4 = floatValue;
                    }
                    i++;
                }
                if (i3 >= 0 && i3 < size) {
                    f3 = ((Float) SlidingUpPanelLayout.this.mAnchorPointExtras.get(i3)).floatValue();
                    return f3;
                }
            }
            f3 = 1.0f;
            return f3;
        }

        public final int c() {
            return SlidingUpPanelLayout.this.mSlideRange;
        }

        public final int a(int i, int i2) {
            return Math.min(Math.max(i + (SlidingUpPanelLayout.this.mDragLeverage == 0.0f ? 0 : Math.round(SlidingUpPanelLayout.this.mDragLeverage * ((float) i2))), SlidingUpPanelLayout.this.computePanelTopPosition(1.0f)), SlidingUpPanelLayout.this.computePanelTopPosition(0.0f));
        }
    }

    public interface b {
    }

    public interface c {
        void onPanelSlide(View view, float f);

        void onPanelStateChanged(View view, PanelState panelState, PanelState panelState2);
    }

    static class d implements erj {
        private View a;

        public d(View view) {
            this.a = view;
        }

        public final int a() {
            return this.a.getScrollY();
        }
    }

    public static class e implements c {
        public void a() {
        }

        public void b() {
        }

        public void c() {
        }

        public void d() {
        }

        public void onPanelSlide(View view, float f) {
        }

        public void onPanelStateChanged(View view, PanelState panelState, PanelState panelState2) {
            switch (panelState2) {
                case COLLAPSED:
                    a();
                    return;
                case ANCHORED:
                    c();
                    return;
                case EXPANDED:
                    b();
                    return;
                case HIDDEN:
                    d();
                    break;
            }
        }
    }

    public SlidingUpPanelLayout(Context context) {
        this(context, null);
    }

    public SlidingUpPanelLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingUpPanelLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMinFlingVelocity = 600;
        this.mPanelHeight = -1;
        this.mDragViewResId = -1;
        this.mSlideState = DEFAULT_SLIDE_STATE;
        this.mLastSlideState = DEFAULT_SLIDE_STATE;
        this.mLastNotDraggingSlideState = DEFAULT_SLIDE_STATE;
        this.mLastNotHiddenSlideState = DEFAULT_SLIDE_STATE;
        this.mAnchorPoint = 1.0f;
        this.mDragEnabled = true;
        this.mPanelSlideListeners = new ArrayList();
        this.mFirstLayout = true;
        this.mSlideStateWithoutLayout = true;
        this.mAnchorHeight = -1;
        this.mAnchorPointInParent = 1.0f;
        this.mExpandHeight = -1;
        this.mExpandPointInParent = 1.0f;
        this.mExpandPoint = 1.0f;
        this.mCaptureViewOnDraggingState = true;
        this.mAnchorHeightExtras = new ArrayList<>();
        this.mAnchorHeightExtraIndex = -1;
        this.mAnchorPointExtras = new ArrayList<>();
        Interpolator interpolator = null;
        if (isInEditMode()) {
            this.mDragHelper = null;
            return;
        }
        float f = 5.0f;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SlidingUpPanelLayout);
            if (obtainStyledAttributes != null) {
                this.mPanelHeight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SlidingUpPanelLayout_panelHeight, -1);
                this.mMinFlingVelocity = obtainStyledAttributes.getInt(R.styleable.SlidingUpPanelLayout_flingVelocity, 600);
                this.mDragViewResId = obtainStyledAttributes.getResourceId(R.styleable.SlidingUpPanelLayout_dragView, -1);
                this.mAnchorPoint = obtainStyledAttributes.getFloat(R.styleable.SlidingUpPanelLayout_anchorPoint, 1.0f);
                this.mSlideState = PanelState.values()[obtainStyledAttributes.getInt(R.styleable.SlidingUpPanelLayout_initialState, DEFAULT_SLIDE_STATE.ordinal())];
                int resourceId = obtainStyledAttributes.getResourceId(R.styleable.SlidingUpPanelLayout_scrollInterpolator, -1);
                interpolator = resourceId != -1 ? AnimationUtils.loadInterpolator(context, resourceId) : interpolator;
                this.mSlideRangePadding = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.SlidingUpPanelLayout_slideRangePadding, 0);
                this.mAnchorHeight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SlidingUpPanelLayout_anchorHeight, -1);
                this.mExpandHeight = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.SlidingUpPanelLayout_expandHeight, -1);
                this.mExpandPointInParent = obtainStyledAttributes.getFloat(R.styleable.SlidingUpPanelLayout_expandPointInParent, 1.0f);
                this.mExpandPoint = obtainStyledAttributes.getFloat(R.styleable.SlidingUpPanelLayout_expandPoint, 1.0f);
                this.mAnchorPointInParent = obtainStyledAttributes.getFloat(R.styleable.SlidingUpPanelLayout_anchorPointInParent, 1.0f);
                f = obtainStyledAttributes.getFloat(R.styleable.SlidingUpPanelLayout_sensitivity, 5.0f);
            }
            obtainStyledAttributes.recycle();
        }
        float f2 = context.getResources().getDisplayMetrics().density;
        if (this.mPanelHeight == -1) {
            this.mPanelHeight = (int) ((48.0f * f2) + 0.5f);
        }
        this.mDragHelper = erk.a((ViewGroup) this, f, interpolator, (defpackage.erk.a) new a(this, 0));
        this.mDragHelper.j = ((float) this.mMinFlingVelocity) * f2;
        if (this.mAnchorPoint != 1.0f) {
            this.mAnchorPointInParent = 1.0f;
        }
        if (this.mAnchorHeight != -1 && this.mAnchorHeight > this.mPanelHeight) {
            this.mAnchorPointInParent = 1.0f;
            this.mAnchorPoint = 1.0f;
        }
        if (this.mExpandPoint != 1.0f) {
            this.mExpandPointInParent = 1.0f;
        }
        if (this.mExpandHeight != -1 && this.mExpandHeight > this.mPanelHeight) {
            this.mExpandPointInParent = 1.0f;
            this.mExpandPoint = 1.0f;
        }
        this.mInterceptTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        if (this.mDragViewResId != -1) {
            setDragView(findViewById(this.mDragViewResId));
        }
        this.mSlideableView = getChildCount() == 2 ? getChildAt(1) : null;
    }

    public boolean isScrollAtTop() {
        return this.mScrollAtTop;
    }

    public void setScrollAtTop(boolean z) {
        this.mScrollAtTop = z;
        this.mScrollableView = null;
    }

    public void setScrollAtTop(boolean z, View view) {
        this.mScrollAtTop = z;
        this.mScrollableView = view != null ? new d(view) : null;
    }

    public void setScrollAtTop(erj erj, boolean z) {
        this.mScrollAtTop = z;
        this.mScrollableView = erj;
    }

    public void setHookScrollableView(erj erj) {
        this.mHookScrollableView = erj;
    }

    public void setPanelTransparentTop(int i) {
        this.mPanelTransparentTop = i;
    }

    public void setPanelHoverView(View view) {
        this.mPanelHoverView = view;
    }

    public float getDragLeverage() {
        return this.mDragLeverage;
    }

    public void setDragLeverage(float f) {
        this.mDragLeverage = f;
    }

    public boolean isCaptureViewOnDraggingState() {
        return this.mCaptureViewOnDraggingState;
    }

    public void setCaptureViewOnDraggingState(boolean z) {
        this.mCaptureViewOnDraggingState = z;
    }

    public boolean isDragHorizontalIntercept() {
        return this.mDragHorizontalIntercept;
    }

    public void setDragHorizontalIntercept(boolean z) {
        this.mDragHorizontalIntercept = z;
    }

    public int getSlideRangePadding() {
        return this.mSlideRangePadding;
    }

    public void setSlideRangePadding(int i) {
        if (this.mSlideRangePadding != i) {
            this.mSlideRangePadding = i;
            requestLayout();
        }
    }

    public int getSlideOffsetHeight() {
        return (int) ((this.mSlideOffset * ((float) this.mSlideRange)) + ((float) this.mPanelHeight));
    }

    public void setDragEnabled(boolean z) {
        this.mDragEnabled = z;
    }

    public boolean isDragEnabled() {
        return this.mDragEnabled && this.mSlideableView != null;
    }

    public void setPanelHeight(int i) {
        if (getPanelHeight() != i) {
            this.mPanelHeight = i;
            requestLayout();
        }
    }

    public int getPanelHeight() {
        return this.mPanelHeight;
    }

    public int getMinFlingVelocity() {
        return this.mMinFlingVelocity;
    }

    public void setMinFlingVelocity(int i) {
        this.mMinFlingVelocity = i;
    }

    public void addPanelSlideListener(c cVar) {
        this.mPanelSlideListeners.add(cVar);
    }

    public void removePanelSlideListener(c cVar) {
        this.mPanelSlideListeners.remove(cVar);
    }

    public b getPanelDragStateChangeListener() {
        return this.mPanelDragStateChangeListener;
    }

    public void setPanelDragStateChangeListener(b bVar) {
        this.mPanelDragStateChangeListener = bVar;
    }

    public int getViewDragState() {
        return this.mDragHelper.a;
    }

    public View getSlideableView() {
        return this.mSlideableView;
    }

    public View getDragView() {
        return this.mDragView;
    }

    public void setDragView(View view) {
        this.mDragView = view;
    }

    public void setDragView(int i) {
        this.mDragViewResId = i;
        setDragView(findViewById(i));
    }

    public List<Integer> getAnchorHeightExtras() {
        return new ArrayList(this.mAnchorHeightExtras);
    }

    public void setAnchorHeightExtras(List<Integer> list) {
        if ((list != null && list.size() != 0) || this.mAnchorHeightExtras.size() != 0) {
            ArrayList arrayList = new ArrayList(list);
            if (arrayList.size() != 0) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    if (((Integer) it.next()) == null) {
                        throw new IllegalArgumentException("element can not be null");
                    }
                }
                Collections.sort(arrayList, new Comparator<Integer>() {
                    public final /* synthetic */ int compare(Object obj, Object obj2) {
                        return ((Integer) obj).intValue() - ((Integer) obj2).intValue();
                    }
                });
                this.mAnchorHeightExtras.clear();
                this.mAnchorHeightExtras.addAll(arrayList);
            } else {
                this.mAnchorHeightExtras.clear();
            }
            requestLayout();
        }
    }

    public float getExpandPoint() {
        return this.mExpandPoint;
    }

    public void setExpandPoint(float f) {
        setExpandPoint(f, true);
    }

    private void setExpandPoint(float f, boolean z) {
        if (f > 0.0f && f <= 1.0f) {
            if (z) {
                this.mExpandHeight = -1;
                this.mExpandPointInParent = 1.0f;
            }
            this.mExpandPoint = f;
        }
    }

    public int getExpandHeight() {
        if (this.mExpandHeight == -1) {
            return (int) ((this.mExpandPoint * ((float) this.mSlideRange)) + ((float) this.mPanelHeight) + ((float) getPaddingBottom()));
        }
        return this.mExpandHeight;
    }

    public void setExpandHeight(int i) {
        if (this.mExpandHeight != i) {
            this.mExpandHeight = i;
            this.mExpandPointInParent = 1.0f;
            this.mExpandPoint = 1.0f;
            requestLayout();
        }
    }

    public int getAnchorHeightExtraNow() {
        if (this.mSlideState != PanelState.ANCHORED || this.mAnchorHeightExtraIndex < 0 || this.mAnchorHeightExtraIndex >= this.mAnchorHeightExtras.size()) {
            return -1;
        }
        return this.mAnchorHeightExtras.get(this.mAnchorHeightExtraIndex).intValue();
    }

    public int getAnchorHeight() {
        if (this.mAnchorHeight == -1) {
            return (int) ((this.mAnchorPoint * ((float) this.mSlideRange)) + ((float) this.mPanelHeight) + ((float) getPaddingBottom()));
        }
        return this.mAnchorHeight;
    }

    public void setAnchorHeight(int i) {
        if (this.mAnchorHeight != i) {
            this.mAnchorHeight = i;
            this.mAnchorPointInParent = 1.0f;
            this.mAnchorPoint = 1.0f;
            requestLayout();
        }
    }

    private void setAnchorPoint(float f, boolean z) {
        if (f > 0.0f && f <= 1.0f) {
            if (z) {
                this.mAnchorHeight = -1;
                this.mAnchorPointInParent = 1.0f;
            }
            this.mAnchorPoint = f;
            if (z) {
                requestLayout();
            }
        }
    }

    public void setAnchorPoint(float f) {
        setAnchorPoint(f, true);
    }

    public float getAnchorPoint() {
        return this.mAnchorPoint;
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnPanelSlide(View view) {
        for (c onPanelSlide : this.mPanelSlideListeners) {
            onPanelSlide.onPanelSlide(view, this.mSlideOffset);
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnPanelStateChanged(View view, PanelState panelState, PanelState panelState2) {
        for (c onPanelStateChanged : this.mPanelSlideListeners) {
            onPanelStateChanged.onPanelStateChanged(view, panelState, panelState2);
        }
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: 0000 */
    public void updateObscuredViewVisibility() {
        int i;
        int i2;
        int i3;
        int i4;
        if (getChildCount() != 0) {
            int paddingLeft = getPaddingLeft();
            int width = getWidth() - getPaddingRight();
            int paddingTop = getPaddingTop();
            int height = getHeight() - getPaddingBottom();
            int i5 = 0;
            if (this.mSlideableView == null || !hasOpaqueBackground(this.mSlideableView)) {
                i4 = 0;
                i3 = 0;
                i2 = 0;
                i = 0;
            } else {
                i4 = this.mSlideableView.getLeft();
                i3 = this.mSlideableView.getRight();
                i2 = this.mSlideableView.getTop();
                i = this.mSlideableView.getBottom();
            }
            View childAt = getChildAt(0);
            int max = Math.max(paddingLeft, childAt.getLeft());
            int max2 = Math.max(paddingTop, childAt.getTop());
            int min = Math.min(width, childAt.getRight());
            int min2 = Math.min(height, childAt.getBottom());
            if (max >= i4 && max2 >= i2 && min <= i3 && min2 <= i) {
                i5 = 4;
            }
            childAt.setVisibility(i5);
        }
    }

    /* access modifiers changed from: 0000 */
    public void setAllChildrenVisible() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 4) {
                childAt.setVisibility(0);
            }
        }
    }

    private static boolean hasOpaqueBackground(View view) {
        Drawable background = view.getBackground();
        return background != null && background.getOpacity() == -1;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
        this.mSlideStateWithoutLayout = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mFirstLayout = true;
        this.mSlideStateWithoutLayout = true;
    }

    private void computeAnchorPointExtras(int i) {
        int size = this.mAnchorHeightExtras.size();
        if (this.mAnchorPointExtras.size() != size) {
            this.mAnchorPointExtras.clear();
        }
        float paddingTop = (float) ((((i - this.mPanelHeight) - getPaddingTop()) - getPaddingBottom()) - this.mSlideRangePadding);
        for (int i2 = 0; i2 < size; i2++) {
            float f = 1.0f;
            if (paddingTop != 0.0f) {
                float intValue = ((float) (this.mAnchorHeightExtras.get(i2).intValue() - this.mPanelHeight)) / paddingTop;
                if (intValue > 0.0f && intValue <= 1.0f) {
                    f = intValue;
                }
            }
            if (i2 == this.mAnchorPointExtras.size()) {
                this.mAnchorPointExtras.add(Float.valueOf(f));
            } else {
                this.mAnchorPointExtras.set(i2, Float.valueOf(f));
            }
        }
    }

    private void computeAnchorPoint(int i) {
        float paddingTop = (float) ((((i - this.mPanelHeight) - getPaddingTop()) - getPaddingBottom()) - this.mSlideRangePadding);
        if (paddingTop != 0.0f) {
            float f = ((float) (this.mAnchorHeight - this.mPanelHeight)) / paddingTop;
            if (f != this.mAnchorPoint) {
                setAnchorPoint(f, false);
            }
        }
    }

    private void computeAnchorPoint(float f) {
        if (this.mSlideRange != 0) {
            int measuredHeight = getMeasuredHeight();
            if (measuredHeight != 0) {
                float pointInParentToSlideRange = pointInParentToSlideRange(f, measuredHeight);
                if (pointInParentToSlideRange != this.mAnchorPoint) {
                    setAnchorPoint(pointInParentToSlideRange, false);
                }
            }
        }
    }

    private void computeExpandPoint(int i) {
        float paddingTop = (float) ((((i - this.mPanelHeight) - getPaddingTop()) - getPaddingBottom()) - this.mSlideRangePadding);
        if (paddingTop != 0.0f) {
            float f = ((float) (this.mExpandHeight - this.mPanelHeight)) / paddingTop;
            if (f != this.mExpandPoint) {
                setExpandPoint(f, false);
            }
        }
    }

    private void computeExpandPoint(float f) {
        if (this.mSlideRange != 0) {
            int measuredHeight = getMeasuredHeight();
            if (measuredHeight != 0) {
                float pointInParentToSlideRange = pointInParentToSlideRange(f, measuredHeight);
                if (pointInParentToSlideRange != this.mExpandPoint) {
                    this.mExpandPoint = pointInParentToSlideRange;
                }
            }
        }
    }

    private float pointInParentToSlideRange(float f, int i) {
        int i2;
        android.view.ViewGroup.LayoutParams layoutParams = getLayoutParams();
        int i3 = 0;
        if (layoutParams == null || layoutParams.height != -1 || !(layoutParams instanceof MarginLayoutParams)) {
            i2 = 0;
        } else {
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) layoutParams;
            i3 = marginLayoutParams.topMargin;
            i2 = marginLayoutParams.bottomMargin;
        }
        float f2 = (float) i;
        return ((((((((f * ((float) ((i3 + i) + i2))) - ((float) i2)) / f2) * f2) - ((float) this.mPanelHeight)) - ((float) getPaddingTop())) - ((float) getPaddingBottom())) - ((float) this.mSlideRangePadding)) / ((float) this.mSlideRange);
    }

    private void computeSlideRange() {
        this.mSlideRange = ((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom()) - this.mSlideRangePadding;
    }

    private void onMeasureComplete(int i) {
        if (this.mAnchorPointInParent != 1.0f) {
            computeAnchorPoint(this.mAnchorPointInParent);
        } else if (this.mAnchorHeight != -1) {
            computeAnchorPoint(i);
        }
        if (this.mExpandPointInParent != 1.0f) {
            computeExpandPoint(this.mExpandPointInParent);
        } else if (this.mExpandHeight != -1) {
            computeExpandPoint(i);
        }
        this.mSlideOffsetSlop = this.mSlideRange != 0 ? 1.0f / ((float) this.mSlideRange) : 0.001f;
        if (this.mAnchorHeightExtras.size() != 0) {
            computeAnchorPointExtras(i);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size2 = MeasureSpec.getSize(i2);
        if (mode != 1073741824) {
            throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
        } else if (mode2 != 1073741824) {
            throw new IllegalStateException("Height must have an exact value or MATCH_PARENT");
        } else {
            int childCount = getChildCount();
            if (childCount != 2) {
                throw new IllegalStateException("Sliding up panel layout must have exactly 2 children!");
            }
            this.mMainView = getChildAt(0);
            this.mSlideableView = getChildAt(1);
            if (this.mDragView == null) {
                setDragView(this.mSlideableView);
            }
            if (this.mSlideableView.getVisibility() != 0) {
                this.mSlideState = PanelState.HIDDEN;
            }
            int paddingTop = (size2 - getPaddingTop()) - getPaddingBottom();
            int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
            for (int i7 = 0; i7 < childCount; i7++) {
                View childAt = getChildAt(i7);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (childAt.getVisibility() != 8 || i7 != 0) {
                    if (childAt == this.mMainView) {
                        i4 = this.mSlideState != PanelState.HIDDEN ? paddingTop - this.mPanelHeight : paddingTop;
                        i3 = paddingLeft - (layoutParams.leftMargin + layoutParams.rightMargin);
                    } else {
                        i4 = childAt == this.mSlideableView ? paddingTop - layoutParams.topMargin : paddingTop;
                        i3 = paddingLeft;
                    }
                    if (layoutParams.width == -2) {
                        i5 = MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE);
                    } else if (layoutParams.width == -1) {
                        i5 = MeasureSpec.makeMeasureSpec(i3, UCCore.VERIFY_POLICY_QUICK);
                    } else {
                        i5 = MeasureSpec.makeMeasureSpec(layoutParams.width, UCCore.VERIFY_POLICY_QUICK);
                    }
                    if (layoutParams.height == -2) {
                        i6 = MeasureSpec.makeMeasureSpec(i4, Integer.MIN_VALUE);
                    } else {
                        if (layoutParams.a > 0.0f && layoutParams.a < 1.0f) {
                            i4 = (int) (((float) i4) * layoutParams.a);
                        } else if (layoutParams.height != -1) {
                            i4 = layoutParams.height;
                        }
                        i6 = MeasureSpec.makeMeasureSpec(i4, UCCore.VERIFY_POLICY_QUICK);
                    }
                    childAt.measure(i5, i6);
                    if (childAt == this.mSlideableView) {
                        this.mSlideRange = (this.mSlideableView.getMeasuredHeight() - this.mPanelHeight) - this.mSlideRangePadding;
                        this.mSlideRange = this.mSlideRange > 0 ? this.mSlideRange : 1;
                    }
                }
            }
            setMeasuredDimension(size, size2);
            onMeasureComplete(size2);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int childCount = getChildCount();
        if (this.mFirstLayout) {
            switch (this.mSlideState) {
                case ANCHORED:
                    if (this.mAnchorHeightExtraIndex >= 0 && this.mAnchorHeightExtraIndex < this.mAnchorPointExtras.size()) {
                        this.mSlideOffset = this.mAnchorPointExtras.get(this.mAnchorHeightExtraIndex).floatValue();
                        break;
                    } else {
                        this.mSlideOffset = this.mAnchorPoint;
                        break;
                    }
                case EXPANDED:
                    this.mSlideOffset = 1.0f;
                    break;
                case HIDDEN:
                    this.mSlideOffset = computeSlideOffset(computePanelTopPosition(0.0f) + this.mPanelHeight);
                    break;
                default:
                    this.mSlideOffset = 0.0f;
                    break;
            }
        }
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (childAt.getVisibility() != 8 || (i5 != 0 && !this.mFirstLayout)) {
                int measuredHeight = childAt.getMeasuredHeight();
                int computePanelTopPosition = childAt == this.mSlideableView ? computePanelTopPosition(this.mSlideOffset) : paddingTop;
                int i6 = layoutParams.leftMargin + paddingLeft;
                childAt.layout(i6, computePanelTopPosition, childAt.getMeasuredWidth() + i6, measuredHeight + computePanelTopPosition);
            }
        }
        if (this.mFirstLayout) {
            updateObscuredViewVisibility();
        }
        this.mFirstLayout = false;
        if (this.mSlideStateWithoutLayout && this.mSlideState != PanelState.DRAGGING) {
            this.mSlideStateWithoutLayout = false;
            dispatchOnPanelStateChanged(this.mSlideableView, this.mLastSlideState, this.mSlideState);
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mShrinkHeight = (i2 >= i4 || i2 <= 0) ? 0 : i4 - i2;
        if (i2 != i4) {
            this.mFirstLayout = true;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled() || !isDragEnabled()) {
            this.mDragHelper.a();
            return onInterceptTouchEventHorizontal(motionEvent);
        } else if (this.mHookMotionRunning) {
            return onInterceptTouchEventHorizontal(motionEvent);
        } else {
            if (!this.mCaptureViewOnDraggingState && this.mDragHelper.a != 0) {
                return onInterceptTouchEventHorizontal(motionEvent);
            }
            int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
            if (actionMasked == 3 || actionMasked == 1) {
                this.mDragHelper.a();
                return false;
            }
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int i = this.mDragHelper.b;
            if (actionMasked == 0) {
                this.mInitialMotionX = x;
                this.mInitialMotionY = y;
                this.mActionDownOnSlideableView = y > ((float) this.mSlideableView.getTop());
            } else if (actionMasked == 2) {
                if (this.mScrollAtTop) {
                    float f = y - this.mInitialMotionY;
                    if (f >= ((float) i)) {
                        shouldInterceptTouchEvent(motionEvent);
                        return true;
                    } else if (f < ((float) (-i)) && !isViewUnder(this.mDragView, (int) this.mInitialMotionX, (int) this.mInitialMotionY) && (this.mScrollableView == null || !(this.mScrollableView == null || this.mScrollableView.a() == 0))) {
                        this.mScrollAtTop = false;
                        return onInterceptTouchEventHorizontal(motionEvent);
                    }
                }
                if (!isViewUnder(this.mDragView, (int) this.mInitialMotionX, (int) this.mInitialMotionY)) {
                    this.mDragHelper.a();
                    return onInterceptTouchEventHorizontal(motionEvent);
                }
            }
            return shouldInterceptTouchEvent(motionEvent) || onInterceptTouchEventHorizontal(motionEvent);
        }
    }

    private boolean shouldInterceptTouchEvent(MotionEvent motionEvent) {
        try {
            erk erk = this.mDragHelper;
            int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
            int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
            if (actionMasked == 0) {
                erk.a();
            }
            if (erk.i == null) {
                erk.i = VelocityTracker.obtain();
            }
            erk.i.addMovement(motionEvent);
            switch (actionMasked) {
                case 0:
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    int pointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                    erk.a(x, y, pointerId);
                    View a2 = erk.a((int) x, (int) y);
                    if (a2 == erk.n && erk.a == 2) {
                        erk.a(a2, pointerId);
                    }
                    int i = erk.h[pointerId] & erk.k;
                    break;
                case 1:
                case 3:
                    erk.a();
                    break;
                case 2:
                    int pointerCount = MotionEventCompat.getPointerCount(motionEvent);
                    for (int i2 = 0; i2 < pointerCount && erk.d != null && erk.e != null; i2++) {
                        int pointerId2 = MotionEventCompat.getPointerId(motionEvent, i2);
                        if (pointerId2 < erk.d.length && pointerId2 < erk.e.length) {
                            float x2 = MotionEventCompat.getX(motionEvent, i2);
                            float y2 = MotionEventCompat.getY(motionEvent, i2);
                            float f = y2 - erk.e[pointerId2];
                            erk.b(x2 - erk.d[pointerId2], f, pointerId2);
                            if (erk.a != 1) {
                                View a3 = erk.a((int) x2, (int) y2);
                                if (a3 != null && erk.a(a3, f) && erk.a(a3, pointerId2)) {
                                }
                            }
                        }
                    }
                    erk.a(motionEvent);
                    break;
                case 5:
                    int pointerId3 = MotionEventCompat.getPointerId(motionEvent, actionIndex);
                    float x3 = MotionEventCompat.getX(motionEvent, actionIndex);
                    float y3 = MotionEventCompat.getY(motionEvent, actionIndex);
                    erk.a(x3, y3, pointerId3);
                    if (erk.a != 0 && erk.a == 2) {
                        View a4 = erk.a((int) x3, (int) y3);
                        if (a4 == erk.n) {
                            erk.a(a4, pointerId3);
                            break;
                        }
                    }
                    break;
                case 6:
                    erk.a(MotionEventCompat.getPointerId(motionEvent, actionIndex));
                    break;
            }
            if (erk.a == 1) {
                return true;
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    private boolean onInterceptTouchEventHorizontal(MotionEvent motionEvent) {
        if (!this.mActionDownOnSlideableView || !this.mDragHorizontalIntercept || motionEvent.getActionMasked() != 2 || Math.abs(motionEvent.getX() - this.mInitialMotionX) <= ((float) this.mInterceptTouchSlop)) {
            return false;
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int i;
        if (!isEnabled() || !isDragEnabled()) {
            return super.onTouchEvent(motionEvent);
        }
        try {
            erk erk = this.mDragHelper;
            int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
            int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
            if (actionMasked == 0) {
                erk.a();
            }
            if (erk.i == null) {
                erk.i = VelocityTracker.obtain();
            }
            erk.i.addMovement(motionEvent);
            int i2 = 0;
            switch (actionMasked) {
                case 0:
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    int pointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                    View a2 = erk.a((int) x, (int) y);
                    erk.a(x, y, pointerId);
                    erk.a(a2, pointerId);
                    int i3 = erk.h[pointerId] & erk.k;
                    break;
                case 1:
                    if (erk.a == 1) {
                        erk.c();
                    }
                    erk.a();
                    break;
                case 2:
                    if (erk.a == 1) {
                        int findPointerIndex = MotionEventCompat.findPointerIndex(motionEvent, erk.c);
                        float x2 = MotionEventCompat.getX(motionEvent, findPointerIndex);
                        int i4 = (int) (x2 - erk.f[erk.c]);
                        int y2 = (int) (MotionEventCompat.getY(motionEvent, findPointerIndex) - erk.g[erk.c]);
                        erk.n.getLeft();
                        int top = erk.n.getTop() + y2;
                        int left = erk.n.getLeft();
                        int top2 = erk.n.getTop();
                        if (i4 != 0) {
                            erk.n.offsetLeftAndRight(0 - left);
                        }
                        if (y2 != 0) {
                            top = erk.m.a(top, y2);
                            erk.n.offsetTopAndBottom(top - top2);
                        }
                        if (!(i4 == 0 && y2 == 0)) {
                            erk.m.a(top);
                        }
                        erk.a(motionEvent);
                        break;
                    } else {
                        int pointerCount = MotionEventCompat.getPointerCount(motionEvent);
                        while (i2 < pointerCount) {
                            int pointerId2 = MotionEventCompat.getPointerId(motionEvent, i2);
                            float x3 = MotionEventCompat.getX(motionEvent, i2);
                            float y3 = MotionEventCompat.getY(motionEvent, i2);
                            float f = y3 - erk.e[pointerId2];
                            erk.b(x3 - erk.d[pointerId2], f, pointerId2);
                            if (erk.a != 1) {
                                View a3 = erk.a((int) x3, (int) y3);
                                if (!erk.a(a3, f) || !erk.a(a3, pointerId2)) {
                                    i2++;
                                }
                            }
                            erk.a(motionEvent);
                            break;
                        }
                        erk.a(motionEvent);
                    }
                    break;
                case 3:
                    if (erk.a == 1) {
                        erk.a(0.0f);
                    }
                    erk.a();
                    break;
                case 5:
                    int pointerId3 = MotionEventCompat.getPointerId(motionEvent, actionIndex);
                    float x4 = MotionEventCompat.getX(motionEvent, actionIndex);
                    float y4 = MotionEventCompat.getY(motionEvent, actionIndex);
                    erk.a(x4, y4, pointerId3);
                    if (erk.a != 0) {
                        if (erk.a(erk.n, (int) x4, (int) y4)) {
                            erk.a(erk.n, pointerId3);
                            break;
                        }
                    } else {
                        erk.a(erk.a((int) x4, (int) y4), pointerId3);
                        break;
                    }
                    break;
                case 6:
                    int pointerId4 = MotionEventCompat.getPointerId(motionEvent, actionIndex);
                    if (erk.a == 1 && pointerId4 == erk.c) {
                        int pointerCount2 = MotionEventCompat.getPointerCount(motionEvent);
                        while (true) {
                            if (i2 < pointerCount2) {
                                int pointerId5 = MotionEventCompat.getPointerId(motionEvent, i2);
                                if (pointerId5 != erk.c) {
                                    if (erk.a((int) MotionEventCompat.getX(motionEvent, i2), (int) MotionEventCompat.getY(motionEvent, i2)) == erk.n && erk.a(erk.n, pointerId5)) {
                                        i = erk.c;
                                    }
                                }
                                i2++;
                            } else {
                                i = -1;
                            }
                        }
                        if (i == -1) {
                            erk.c();
                        }
                    }
                    erk.a(pointerId4);
                    break;
            }
            return this.mActionDownOnSlideableView;
        } catch (Exception unused) {
            return this.mActionDownOnSlideableView;
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        float y = motionEvent.getY();
        if (actionMasked == 0) {
            this.mHookMotionY = y;
            this.mHookMotionRunning = false;
            if (isPointOnPanelTransparentArea((int) motionEvent.getX(), (int) motionEvent.getY())) {
                return false;
            }
        } else if (actionMasked == 2) {
            float f = y - this.mHookMotionY;
            if (f > 0.0f) {
                if (this.mHookScrollableView != null && this.mSlideOffset >= 1.0f && this.mHookScrollableView.a() > 0) {
                    this.mHookMotionRunning = true;
                    return super.dispatchTouchEvent(motionEvent);
                } else if (this.mHookMotionRunning) {
                    dispatchTouchEventCancel(motionEvent);
                    this.mHookMotionMoveUpHandled = false;
                    this.mHookMotionRunning = false;
                    return super.dispatchTouchEvent(motionEvent);
                } else if (this.mSlideOffset < 1.0f) {
                    this.mHookMotionMoveUpHandled = false;
                }
            } else if (f < 0.0f) {
                if (this.mHookScrollableView != null && this.mSlideOffset >= 1.0f && this.mHookScrollableView.a() >= 0) {
                    if (!this.mHookMotionRunning && !this.mHookMotionMoveUpHandled) {
                        dispatchTouchEventCancel(motionEvent);
                        this.mHookMotionMoveUpHandled = true;
                    }
                    this.mHookMotionRunning = true;
                    return super.dispatchTouchEvent(motionEvent);
                } else if (this.mSlideOffset < 1.0f) {
                    this.mHookMotionMoveUpHandled = false;
                }
            }
            this.mHookMotionY = y;
        } else if (actionMasked == 1 && this.mHookMotionRunning) {
            this.mDragHelper.b(0);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public void dispatchTouchEventCancel(MotionEvent motionEvent) {
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        obtain.setAction(3);
        super.dispatchTouchEvent(obtain);
        obtain.recycle();
        motionEvent.setAction(0);
    }

    private boolean isViewUnder(View view, int i, int i2) {
        if (view == null) {
            return false;
        }
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int[] iArr2 = new int[2];
        getLocationOnScreen(iArr2);
        int i3 = iArr2[0] + i;
        int i4 = iArr2[1] + i2;
        if (i3 < iArr[0] || i3 >= iArr[0] + view.getWidth() || i4 < iArr[1] || i4 >= iArr[1] + view.getHeight()) {
            return false;
        }
        return true;
    }

    private boolean isPointOnPanelTransparentArea(int i, int i2) {
        if (this.mPanelTransparentTop == 0 || this.mSlideableView == null || isViewUnder(this.mPanelHoverView, i, i2)) {
            return false;
        }
        int[] iArr = new int[2];
        this.mSlideableView.getLocationOnScreen(iArr);
        int[] iArr2 = new int[2];
        getLocationOnScreen(iArr2);
        int i3 = iArr2[0] + i;
        int i4 = iArr2[1] + i2;
        if (i3 < iArr[0] || i3 >= iArr[0] + this.mSlideableView.getWidth() || i4 < iArr[1] || i4 >= iArr[1] + this.mPanelTransparentTop) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public int computePanelTopPosition(float f) {
        if (this.mSlideableView != null) {
            this.mSlideableView.getMeasuredHeight();
        }
        return ((getMeasuredHeight() - getPaddingBottom()) - this.mPanelHeight) - ((int) (f * ((float) this.mSlideRange)));
    }

    /* access modifiers changed from: private */
    public float computeSlideOffset(int i) {
        int computePanelTopPosition = computePanelTopPosition(0.0f);
        if (this.mSlideState != PanelState.DRAGGING || this.mLastSlideState != PanelState.COLLAPSED || this.mShrinkHeight <= 0 || (this.mShrinkHeight + computePanelTopPosition) - i >= 5) {
            return ((float) (computePanelTopPosition - i)) / ((float) this.mSlideRange);
        }
        return 0.0f;
    }

    public PanelState getLastPanelState() {
        return this.mLastNotDraggingSlideState;
    }

    public PanelState getPanelState() {
        return this.mSlideState;
    }

    public void setPanelState(PanelState panelState) {
        setPanelState(panelState, -1);
    }

    /* access modifiers changed from: private */
    public void setPanelStateInternal(PanelState panelState) {
        if (this.mSlideState != panelState || this.mSlideState == PanelState.ANCHORED) {
            this.mLastSlideState = this.mSlideState;
            PanelState panelState2 = this.mSlideState;
            this.mSlideState = panelState;
            beforePanelStateChangedDispatched();
            if (!this.mSlideStateWithoutLayout) {
                dispatchOnPanelStateChanged(this, panelState2, panelState);
            }
        }
    }

    private void beforePanelStateChangedDispatched() {
        this.mLastNotHiddenSlideState = this.mSlideState != PanelState.HIDDEN ? this.mSlideState : this.mLastNotHiddenSlideState;
        if (this.mSlideState == PanelState.ANCHORED) {
            if (this.mAnchorHeightExtraIndex < 0 || this.mAnchorHeightExtraIndex >= this.mAnchorHeightExtras.size() || this.mAnchorPointExtras.get(this.mAnchorHeightExtraIndex).floatValue() != ((float) getSlideOffsetHeight())) {
                this.mAnchorHeightExtraIndex = -1;
            }
        }
    }

    public void setPanelState(PanelState panelState, int i) {
        float f;
        if (panelState != null && panelState != PanelState.DRAGGING && isEnabled() && (this.mFirstLayout || this.mSlideableView != null)) {
            if (panelState == this.mSlideState && panelState != PanelState.ANCHORED && i != -1) {
                return;
            }
            if (this.mSlideState != PanelState.DRAGGING || this.mDragHelper.a == 0) {
                if (panelState == PanelState.ANCHORED) {
                    if (this.mSlideState != PanelState.ANCHORED || i == -1 || i != getAnchorHeightExtraNow()) {
                        this.mAnchorHeightExtraIndex = this.mAnchorHeightExtras.indexOf(Integer.valueOf(i));
                    } else {
                        return;
                    }
                }
                if (this.mFirstLayout) {
                    setPanelStateInternal(panelState);
                    return;
                }
                if (this.mSlideState == PanelState.HIDDEN) {
                    this.mSlideableView.setVisibility(0);
                    requestLayout();
                }
                switch (panelState) {
                    case COLLAPSED:
                        smoothSlideTo(0.0f, 0);
                        return;
                    case ANCHORED:
                        if (this.mAnchorHeightExtraIndex < 0 || this.mAnchorHeightExtraIndex >= this.mAnchorPointExtras.size()) {
                            f = this.mAnchorPoint;
                        } else {
                            f = this.mAnchorPointExtras.get(this.mAnchorHeightExtraIndex).floatValue();
                        }
                        smoothSlideTo(f, 0);
                        return;
                    case EXPANDED:
                        smoothSlideTo(1.0f, 0);
                        return;
                    case HIDDEN:
                        smoothSlideTo(computeSlideOffset(computePanelTopPosition(0.0f) + this.mPanelHeight), 0);
                        break;
                }
            }
        }
    }

    public void setPanelState(PanelState panelState, int i, boolean z) {
        if (z) {
            setPanelState(panelState, i);
            return;
        }
        this.mFirstLayout = true;
        this.mSlideStateWithoutLayout = true;
        if (this.mDragHelper.a != 0) {
            this.mDragHelper.b();
            ViewCompat.postInvalidateOnAnimation(this);
        }
        setPanelState(panelState, i);
        requestLayout();
    }

    public void setPanelState(PanelState panelState, boolean z) {
        setPanelState(panelState, -1, z);
    }

    public void showPanel() {
        setPanelState(this.mLastNotHiddenSlideState);
    }

    public void hidePanel() {
        setPanelState(PanelState.HIDDEN);
    }

    /* access modifiers changed from: private */
    public void onPanelDragged(int i) {
        if (this.mSlideState != PanelState.DRAGGING) {
            this.mLastNotDraggingSlideState = this.mSlideState;
        }
        setPanelStateInternal(PanelState.DRAGGING);
        this.mSlideOffset = computeSlideOffset(i);
        dispatchOnPanelSlide(this.mSlideableView);
    }

    /* access modifiers changed from: 0000 */
    public boolean smoothSlideTo(float f, int i) {
        if (!isEnabled() || this.mSlideableView == null) {
            return false;
        }
        int computePanelTopPosition = computePanelTopPosition(f);
        erk erk = this.mDragHelper;
        View view = this.mSlideableView;
        int left = this.mSlideableView.getLeft();
        erk.n = view;
        erk.c = -1;
        if (!erk.a(left, computePanelTopPosition, 0, 0)) {
            return false;
        }
        setAllChildrenVisible();
        ViewCompat.postInvalidateOnAnimation(this);
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0077  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void computeScroll() {
        /*
            r8 = this;
            erk r0 = r8.mDragHelper
            if (r0 == 0) goto L_0x0086
            erk r0 = r8.mDragHelper
            android.view.View r1 = r0.n
            if (r1 == 0) goto L_0x0074
            int r1 = r0.a
            r2 = 2
            if (r1 != r2) goto L_0x006e
            android.support.v4.widget.ScrollerCompat r1 = r0.l
            boolean r1 = r1.computeScrollOffset()
            android.support.v4.widget.ScrollerCompat r3 = r0.l
            int r3 = r3.getCurrX()
            android.support.v4.widget.ScrollerCompat r4 = r0.l
            int r4 = r4.getCurrY()
            android.view.View r5 = r0.n
            int r5 = r5.getLeft()
            int r5 = r3 - r5
            android.view.View r6 = r0.n
            int r6 = r6.getTop()
            int r6 = r4 - r6
            if (r5 == 0) goto L_0x0038
            android.view.View r7 = r0.n
            r7.offsetLeftAndRight(r5)
        L_0x0038:
            if (r6 == 0) goto L_0x003f
            android.view.View r7 = r0.n
            r7.offsetTopAndBottom(r6)
        L_0x003f:
            if (r5 != 0) goto L_0x0043
            if (r6 == 0) goto L_0x0048
        L_0x0043:
            erk$a r5 = r0.m
            r5.a(r4)
        L_0x0048:
            if (r1 == 0) goto L_0x0065
            android.support.v4.widget.ScrollerCompat r5 = r0.l
            int r5 = r5.getFinalX()
            if (r3 != r5) goto L_0x0065
            android.support.v4.widget.ScrollerCompat r3 = r0.l
            int r3 = r3.getFinalY()
            if (r4 != r3) goto L_0x0065
            android.support.v4.widget.ScrollerCompat r1 = r0.l
            r1.abortAnimation()
            android.support.v4.widget.ScrollerCompat r1 = r0.l
            boolean r1 = r1.isFinished()
        L_0x0065:
            if (r1 != 0) goto L_0x006e
            android.view.ViewGroup r1 = r0.p
            java.lang.Runnable r3 = r0.q
            r1.post(r3)
        L_0x006e:
            int r0 = r0.a
            if (r0 != r2) goto L_0x0074
            r0 = 1
            goto L_0x0075
        L_0x0074:
            r0 = 0
        L_0x0075:
            if (r0 == 0) goto L_0x0086
            boolean r0 = r8.isEnabled()
            if (r0 != 0) goto L_0x0083
            erk r0 = r8.mDragHelper
            r0.b()
            return
        L_0x0083:
            android.support.v4.view.ViewCompat.postInvalidateOnAnimation(r8)
        L_0x0086:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout.computeScroll():void");
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof MarginLayoutParams ? new LayoutParams((MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.mSlideState != PanelState.DRAGGING) {
            savedState.a = this.mSlideState;
        } else {
            savedState.a = this.mLastNotDraggingSlideState;
        }
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mSlideState = savedState.a != null ? savedState.a : DEFAULT_SLIDE_STATE;
    }
}
