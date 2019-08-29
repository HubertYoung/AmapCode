package com.autonavi.map.search.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;
import org.eclipse.mat.hprof.AbstractParser.Constants.DumpSegment;

public class SlidingUpPanelLayout extends ViewGroup {
    public static final int COLLAPSED_TYPE_NORMAL = 0;
    public static final int COLLAPSED_TYPE_TIPS = 1;
    private static final float DEFAULT_ANCHOR_POINT = 1.0f;
    private static final int[] DEFAULT_ATTRS = {16842927};
    private static final int DEFAULT_FADE_COLOR = -1728053248;
    private static final int DEFAULT_MIN_FLING_VELOCITY = 600;
    private static final boolean DEFAULT_OVERLAY_FLAG = false;
    private static final int DEFAULT_PANEL_HEIGHT = 48;
    private static final int DEFAULT_PARALAX_OFFSET = 0;
    private static final int DEFAULT_SHADOW_HEIGHT = 4;
    private static SlideState DEFAULT_SLIDE_STATE = SlideState.COLLAPSED;
    private static final String TAG = "SlidingUpPanelLayout";
    private boolean isConsumed;
    /* access modifiers changed from: private */
    public boolean isSlide;
    private float mAnchor;
    private int mAnchorHeight;
    /* access modifiers changed from: private */
    public float mAnchorPoint;
    /* access modifiers changed from: private */
    public boolean mCaptureViewOnDraggingState;
    private int mCollapsedType;
    private int mCoveredFadeColor;
    private b mDisallowInterceptTouchListener;
    /* access modifiers changed from: private */
    public boolean mDragEnable;
    /* access modifiers changed from: private */
    public final ViewDragHelper mDragHelper;
    private boolean mDragHorizontalIntercept;
    private int mDragOffset;
    private View mDragView;
    /* access modifiers changed from: private */
    public OnClickListener mDragViewClickListener;
    private int mDragViewResId;
    private float mExpandPointAbsolute;
    private boolean mFirstLayout;
    private Handler mHandler;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private int mInterceptTouchSlop;
    private boolean mIsScrollTop;
    private boolean mIsSlidingEnabled;
    /* access modifiers changed from: private */
    public boolean mIsSlidingUp;
    /* access modifiers changed from: private */
    public boolean mIsUnableToDrag;
    private SlideState mLastSlideState;
    /* access modifiers changed from: private */
    public String mLastState;
    /* access modifiers changed from: private */
    public c mLogListener;
    private int mLowerAnchorHeight;
    /* access modifiers changed from: private */
    public float mLowerAnchorPoint;
    private View mMainView;
    private int mMinFlingVelocity;
    private int mNTipsPanelHeight;
    private boolean mOverlayContent;
    private d mPanelDragStateChangeListener;
    private int mPanelHeight;
    private e mPanelSlideListener;
    private int mParallaxOffset;
    private int mShadowHeight;
    /* access modifiers changed from: private */
    public float mSlideOffset;
    /* access modifiers changed from: private */
    public int mSlideRange;
    /* access modifiers changed from: private */
    public SlideState mSlideState;
    /* access modifiers changed from: private */
    public View mSlideableView;
    private boolean mSwitchCollapsedType;
    /* access modifiers changed from: private */
    public int mTargetPanelTop;
    private final Rect mTmpRect;
    private float moveY1;
    private float moveY2;

    public static class LayoutParams extends MarginLayoutParams {
        private static final int[] a = {16843137};

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
            context.obtainStyledAttributes(attributeSet, a).recycle();
        }
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
        SlideState a;

        /* synthetic */ SavedState(Parcel parcel, byte b) {
            this(parcel);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            try {
                this.a = (SlideState) Enum.valueOf(SlideState.class, parcel.readString());
            } catch (IllegalArgumentException unused) {
                this.a = SlideState.COLLAPSED;
            }
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.a.toString());
        }
    }

    public enum SlideState {
        EXPANDED,
        COLLAPSED,
        ANCHORED,
        LOWERANCHORED,
        HIDDEN,
        DRAGGING,
        UNCHANGED
    }

    class a extends Callback {
        private a() {
        }

        /* synthetic */ a(SlidingUpPanelLayout slidingUpPanelLayout, byte b) {
            this();
        }

        public final boolean tryCaptureView(View view, int i) {
            boolean z = false;
            if ((!SlidingUpPanelLayout.this.mCaptureViewOnDraggingState && SlidingUpPanelLayout.this.mSlideState == SlideState.DRAGGING) || SlidingUpPanelLayout.this.mIsUnableToDrag) {
                return false;
            }
            if (view == SlidingUpPanelLayout.this.mSlideableView) {
                z = true;
            }
            if (z) {
                SlidingUpPanelLayout.this.isSlide = true;
            }
            return z;
        }

        public final void onViewDragStateChanged(int i) {
            if (SlidingUpPanelLayout.this.mDragHelper != null && SlidingUpPanelLayout.this.mDragHelper.getViewDragState() == 0) {
                SlidingUpPanelLayout.this.mTargetPanelTop = SlidingUpPanelLayout.this.mSlideableView.getTop();
                SlidingUpPanelLayout.this.mSlideOffset = SlidingUpPanelLayout.this.computeSlideOffset(SlidingUpPanelLayout.this.mSlideableView.getTop());
                String str = "";
                if (SlidingUpPanelLayout.this.mSlideOffset == 1.0f) {
                    if (SlidingUpPanelLayout.this.mSlideState != SlideState.EXPANDED) {
                        SlidingUpPanelLayout.this.updateObscuredViewVisibility();
                        SlidingUpPanelLayout.this.mSlideState = SlideState.EXPANDED;
                        SlidingUpPanelLayout.this.dispatchOnPanelExpanded(SlidingUpPanelLayout.this.mSlideableView);
                        str = SlidingUpPanelLayout.this.getResources().getString(R.string.expanded);
                    }
                } else if (SlidingUpPanelLayout.this.mSlideOffset == 0.0f) {
                    if (SlidingUpPanelLayout.this.mSlideState != SlideState.COLLAPSED) {
                        SlidingUpPanelLayout.this.mSlideState = SlideState.COLLAPSED;
                        SlidingUpPanelLayout.this.dispatchOnPanelCollapsed(SlidingUpPanelLayout.this.mSlideableView);
                        str = SlidingUpPanelLayout.this.getResources().getString(R.string.collapsed);
                    }
                } else if (SlidingUpPanelLayout.this.mSlideOffset >= 0.0f && SlidingUpPanelLayout.this.mSlideState != SlideState.ANCHORED) {
                    SlidingUpPanelLayout.this.updateObscuredViewVisibility();
                    if (((double) Math.abs(SlidingUpPanelLayout.this.mSlideOffset - SlidingUpPanelLayout.this.mAnchorPoint)) < 0.05d) {
                        SlidingUpPanelLayout.this.mSlideState = SlideState.ANCHORED;
                        SlidingUpPanelLayout.this.dispatchOnPanelAnchored(SlidingUpPanelLayout.this.mSlideableView);
                        if (SlidingUpPanelLayout.this.mAnchorPoint != 1.0f) {
                            SlidingUpPanelLayout.this.smoothSlideTo(SlidingUpPanelLayout.this.mAnchorPoint, 0);
                        }
                        str = SlidingUpPanelLayout.this.getResources().getString(R.string.anchored);
                    } else if (((double) Math.abs(SlidingUpPanelLayout.this.mSlideOffset - SlidingUpPanelLayout.this.mLowerAnchorPoint)) < 0.05d) {
                        SlidingUpPanelLayout.this.mSlideState = SlideState.LOWERANCHORED;
                        SlidingUpPanelLayout.this.dispatchOnPanelAnchoredAtLowerPos(SlidingUpPanelLayout.this.mSlideableView);
                        if (SlidingUpPanelLayout.this.mAnchorPoint != 1.0f) {
                            SlidingUpPanelLayout.this.smoothSlideTo(SlidingUpPanelLayout.this.mLowerAnchorPoint, 0);
                        }
                        str = SlidingUpPanelLayout.this.getResources().getString(R.string.loweranchored);
                    }
                }
                if (SlidingUpPanelLayout.this.mSlideState == SlideState.DRAGGING) {
                    float abs = Math.abs(SlidingUpPanelLayout.this.mSlideOffset - 1.0f);
                    float abs2 = SlidingUpPanelLayout.this.mAnchorPoint != 1.0f ? Math.abs(SlidingUpPanelLayout.this.mSlideOffset - SlidingUpPanelLayout.this.mAnchorPoint) : 1.0f;
                    float abs3 = Math.abs(SlidingUpPanelLayout.this.mSlideOffset);
                    if (abs < abs2 && abs < abs3) {
                        SlidingUpPanelLayout.this.smoothSlideTo(1.0f, 0);
                    } else if (SlidingUpPanelLayout.this.mAnchorPoint == 1.0f || abs2 >= abs || abs2 >= abs3) {
                        SlidingUpPanelLayout.this.smoothSlideTo(0.0f, 0);
                    } else {
                        SlidingUpPanelLayout.this.smoothSlideTo(SlidingUpPanelLayout.this.mAnchorPoint, 0);
                    }
                }
                if (SlidingUpPanelLayout.this.isSlide && !"".equals(str) && SlidingUpPanelLayout.this.mLogListener != null) {
                    SlidingUpPanelLayout.this.mLogListener;
                    SlidingUpPanelLayout.this.mLastState;
                }
                SlidingUpPanelLayout.this.mLastState = str;
                SlidingUpPanelLayout.this.isSlide = false;
            }
        }

        public final void onViewCaptured(View view, int i) {
            SlidingUpPanelLayout.this.setAllChildrenVisible();
        }

        public final void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            SlidingUpPanelLayout.this.onPanelDragged(i2);
            SlidingUpPanelLayout.this.invalidate();
        }

        public final void onViewReleased(View view, float f, float f2) {
            int i;
            if (SlidingUpPanelLayout.this.mIsSlidingUp) {
                f2 = -f2;
            }
            if (SlidingUpPanelLayout.this.mLowerAnchorPoint == -1.0f) {
                i = SlidingUpPanelLayout.this.getTargetPos(f2);
            } else {
                i = SlidingUpPanelLayout.this.getTargetPosAtLowerAnchor(f2);
            }
            if (SlidingUpPanelLayout.this.mDragHelper != null) {
                SlidingUpPanelLayout.this.mDragHelper.settleCapturedViewAt(view.getLeft(), i);
            }
            SlidingUpPanelLayout.this.invalidate();
        }

        public final int getViewVerticalDragRange(View view) {
            return SlidingUpPanelLayout.this.mSlideRange;
        }

        public final int clampViewPositionVertical(View view, int i, int i2) {
            int access$2200 = SlidingUpPanelLayout.this.computePanelTopPosition(0.0f);
            int access$22002 = SlidingUpPanelLayout.this.computePanelTopPosition(1.0f);
            if (SlidingUpPanelLayout.this.mIsSlidingUp) {
                return Math.min(Math.max(i, access$22002), access$2200);
            }
            return Math.min(Math.max(i, access$2200), access$22002);
        }
    }

    public interface b {
        boolean a();
    }

    public interface c {
    }

    public interface d {
    }

    public interface e {
    }

    public void setmNTipsPanelHeight(int i) {
        this.mNTipsPanelHeight = i;
    }

    public SlidingUpPanelLayout(Context context) {
        this(context, null);
    }

    public SlidingUpPanelLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingUpPanelLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNTipsPanelHeight = DumpSegment.ANDROID_ROOT_JNI_MONITOR;
        this.mCollapsedType = 0;
        this.mMinFlingVelocity = 600;
        this.mCoveredFadeColor = DEFAULT_FADE_COLOR;
        this.mPanelHeight = -1;
        this.mShadowHeight = -1;
        this.mParallaxOffset = -1;
        this.mOverlayContent = false;
        this.mDragViewResId = -1;
        this.mIsScrollTop = false;
        this.moveY1 = 0.0f;
        this.moveY2 = 0.0f;
        this.mSlideState = SlideState.COLLAPSED;
        this.mLastSlideState = SlideState.COLLAPSED;
        this.mSlideRange = -1;
        this.mAnchorPoint = 1.0f;
        this.mLowerAnchorPoint = -1.0f;
        this.mAnchor = 1.0f;
        this.mAnchorHeight = -1;
        this.mLowerAnchorHeight = -1;
        this.mExpandPointAbsolute = 1.0f;
        this.mHandler = new Handler();
        this.mFirstLayout = true;
        this.mSwitchCollapsedType = false;
        this.mTmpRect = new Rect();
        this.isSlide = false;
        this.mDragEnable = true;
        this.mCaptureViewOnDraggingState = true;
        this.isConsumed = true;
        if (isInEditMode()) {
            this.mDragHelper = null;
            return;
        }
        float f = 5.0f;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, DEFAULT_ATTRS);
            if (obtainStyledAttributes != null) {
                int i2 = obtainStyledAttributes.getInt(0, 0);
                if (i2 == 48 || i2 == 80) {
                    this.mIsSlidingUp = i2 == 80;
                    obtainStyledAttributes.recycle();
                } else {
                    throw new IllegalArgumentException("gravity must be set to either top or bottom");
                }
            }
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R.styleable.SlidingUpPanelLayout);
            if (obtainStyledAttributes2 != null) {
                this.mPanelHeight = obtainStyledAttributes2.getDimensionPixelSize(R.styleable.SlidingUpPanelLayout_panelHeight, -1);
                this.mShadowHeight = obtainStyledAttributes2.getDimensionPixelSize(R.styleable.SlidingUpPanelLayout_shadowHeight, -1);
                this.mParallaxOffset = obtainStyledAttributes2.getDimensionPixelSize(R.styleable.SlidingUpPanelLayout_paralaxOffset, -1);
                this.mDragOffset = obtainStyledAttributes2.getDimensionPixelSize(R.styleable.SlidingUpPanelLayout_dragOffset, -1);
                this.mMinFlingVelocity = obtainStyledAttributes2.getInt(R.styleable.SlidingUpPanelLayout_flingVelocity, 600);
                this.mCoveredFadeColor = obtainStyledAttributes2.getColor(R.styleable.SlidingUpPanelLayout_fadeColor, DEFAULT_FADE_COLOR);
                this.mDragViewResId = obtainStyledAttributes2.getResourceId(R.styleable.SlidingUpPanelLayout_dragView, -1);
                this.mOverlayContent = obtainStyledAttributes2.getBoolean(R.styleable.SlidingUpPanelLayout_overlay, false);
                this.mAnchor = obtainStyledAttributes2.getFloat(R.styleable.SlidingUpPanelLayout_anchorPoint, 1.0f);
                this.mAnchorHeight = obtainStyledAttributes2.getDimensionPixelSize(R.styleable.SlidingUpPanelLayout_anchorHeight, -1);
                this.mExpandPointAbsolute = obtainStyledAttributes2.getFloat(R.styleable.SlidingUpPanelLayout_expandPoint, 1.0f);
                f = obtainStyledAttributes2.getFloat(R.styleable.SlidingUpPanelLayout_sensitivity, 5.0f);
                this.mSlideState = SlideState.values()[obtainStyledAttributes2.getInt(R.styleable.SlidingUpPanelLayout_initialState, DEFAULT_SLIDE_STATE.ordinal())];
                obtainStyledAttributes2.recycle();
            }
        }
        float f2 = context.getResources().getDisplayMetrics().density;
        if (this.mPanelHeight == -1) {
            this.mPanelHeight = (int) ((48.0f * f2) + 0.5f);
        }
        if (this.mShadowHeight == -1) {
            this.mShadowHeight = (int) ((4.0f * f2) + 0.5f);
        }
        if (this.mParallaxOffset == -1) {
            this.mParallaxOffset = (int) (0.0f * f2);
        }
        setWillNotDraw(true);
        this.mDragHelper = ViewDragHelper.create(this, f, new a(this, 0));
        this.mDragHelper.setMinVelocity(((float) this.mMinFlingVelocity) * f2);
        this.mIsSlidingEnabled = true;
        if (this.mAnchorHeight != -1 && this.mAnchorHeight > this.mPanelHeight) {
            this.mAnchor = 1.0f;
            this.mAnchorPoint = 1.0f;
        }
        this.mInterceptTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public int getViewDragState() {
        return this.mDragHelper.getViewDragState();
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        if (this.mDragViewResId != -1) {
            setDragView(findViewById(this.mDragViewResId));
        }
    }

    public boolean isSlidingEnabled() {
        return this.mIsSlidingEnabled && this.mSlideableView != null;
    }

    public void setPanelHeight(int i) {
        this.mPanelHeight = i;
        if (this.mAnchor == 1.0f) {
            computeAnchorPoint((getHeight() - getPaddingBottom()) - getPaddingTop());
        } else {
            this.mAnchorPoint = this.mAnchor;
        }
        computeSlideRange();
        requestLayout();
    }

    public int getPanelHeight() {
        return this.mPanelHeight;
    }

    public int getSlideOffsetHeightAbsolute() {
        return (int) ((this.mSlideOffset * ((float) this.mSlideRange)) + ((float) this.mPanelHeight));
    }

    public void setPanelSlideListener(e eVar) {
        this.mPanelSlideListener = eVar;
    }

    public void setDragViewClickListener(OnClickListener onClickListener) {
        this.mDragViewClickListener = onClickListener;
    }

    public d getPanelDragStateChangeListener() {
        return this.mPanelDragStateChangeListener;
    }

    public void setPanelDragStateChangeListener(d dVar) {
        this.mPanelDragStateChangeListener = dVar;
    }

    public void setDragView(View view) {
        if (this.mDragView != null) {
            this.mDragView.setOnClickListener(null);
        }
        this.mDragView = view;
        if (this.mDragView != null) {
            this.mDragView.setClickable(true);
            this.mDragView.setFocusable(false);
            this.mDragView.setFocusableInTouchMode(false);
            this.mDragView.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (SlidingUpPanelLayout.this.mDragViewClickListener != null) {
                        SlidingUpPanelLayout.this.mDragViewClickListener.onClick(view);
                        return;
                    }
                    if (SlidingUpPanelLayout.this.mDragEnable) {
                        SlidingUpPanelLayout.this.onDragViewClick();
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void onDragViewClick() {
        if (isEnabled()) {
            switch (this.mSlideState) {
                case COLLAPSED:
                    if (this.mLowerAnchorPoint == -1.0f) {
                        anchorPanel();
                    } else {
                        anchorPanelAtLowerPos();
                    }
                    getResources().getString(R.string.anchored);
                    return;
                case ANCHORED:
                    this.mHandler.postDelayed(new Runnable() {
                        public final void run() {
                            SlidingUpPanelLayout.this.expandPanel();
                        }
                    }, 10);
                    getResources().getString(R.string.expanded);
                    return;
                case LOWERANCHORED:
                    this.mHandler.postDelayed(new Runnable() {
                        public final void run() {
                            SlidingUpPanelLayout.this.anchorPanel();
                        }
                    }, 10);
                    getResources().getString(R.string.anchored);
                    return;
                case EXPANDED:
                    this.mSlideState = SlideState.ANCHORED;
                    anchorPanel();
                    getResources().getString(R.string.anchored);
                    break;
            }
        }
    }

    public void setAnchorPoint(float f) {
        setAnchorPoint(f, true);
    }

    private void setAnchorPoint(float f, boolean z) {
        if (z) {
            this.mAnchorHeight = -1;
        }
        if (f > 0.0f && f <= 1.0f) {
            this.mAnchorPoint = f;
            if (z) {
                this.mAnchor = this.mAnchorPoint;
            }
            requestLayout();
        }
    }

    private void setLowerAnchorPoint(float f, boolean z) {
        if (z) {
            this.mLowerAnchorHeight = -1;
        }
        if (f > 0.0f && f <= 1.0f) {
            this.mLowerAnchorPoint = f;
            requestLayout();
        }
    }

    public int getAnchorHeight() {
        if (this.mAnchorHeight == -1) {
            return (int) ((this.mAnchorPoint * ((float) this.mSlideRange)) + ((float) getPaddingBottom()) + ((float) this.mPanelHeight));
        }
        return this.mAnchorHeight;
    }

    public void setAnchorHeight(int i) {
        this.mAnchorHeight = i;
        this.mAnchor = 1.0f;
        this.mAnchorPoint = 1.0f;
        computeAnchorPoint((getHeight() - getPaddingTop()) - getPaddingBottom());
        requestLayout();
    }

    public float getSlideOffset() {
        return this.mSlideOffset;
    }

    public void setLowerAnchorHeight(int i) {
        if (computePanelTopPosition(this.mAnchorPoint) >= i) {
            this.mLowerAnchorHeight = i;
            computeLowerAnchorPoint((getHeight() - getPaddingTop()) - getPaddingBottom());
            requestLayout();
        }
    }

    public void clearLowerAnchor() {
        this.mLowerAnchorHeight = -1;
        this.mLowerAnchorPoint = -1.0f;
    }

    public int getAnchorHeightByAnchorePoint(float f) {
        return getHeight() - computePanelTopPosition(f);
    }

    public float getAnchorPoint() {
        return this.mAnchorPoint;
    }

    public float getLowerAnchorPoint() {
        return this.mLowerAnchorPoint;
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnPanelSlide(View view) {
        if (this.mPanelSlideListener != null) {
            view.getTop();
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnPanelExpanded(View view) {
        this.mLastSlideState = SlideState.EXPANDED;
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnPanelCollapsed(View view) {
        this.mLastSlideState = SlideState.COLLAPSED;
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnPanelAnchored(View view) {
        this.mLastSlideState = SlideState.ANCHORED;
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnPanelAnchoredAtLowerPos(View view) {
        this.mLastSlideState = SlideState.LOWERANCHORED;
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnPanelHidden(View view) {
        this.mLastSlideState = SlideState.HIDDEN;
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
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mDragHelper.abort();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size2 = MeasureSpec.getSize(i2);
        if (mode != 1073741824) {
            throw new IllegalStateException("Width must have an exact value or MALTCH_PARENT");
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
            if (this.mSlideableView.getVisibility() == 8) {
                this.mSlideState = SlideState.HIDDEN;
            }
            int paddingTop = (size2 - getPaddingTop()) - getPaddingBottom();
            for (int i5 = 0; i5 < childCount; i5++) {
                View childAt = getChildAt(i5);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (childAt.getVisibility() != 8 || i5 != 0) {
                    int i6 = (childAt != this.mMainView || this.mOverlayContent || this.mSlideState == SlideState.HIDDEN) ? paddingTop : paddingTop - this.mPanelHeight;
                    if (layoutParams.width == -2) {
                        i3 = MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE);
                    } else if (layoutParams.width == -1) {
                        i3 = MeasureSpec.makeMeasureSpec(size, UCCore.VERIFY_POLICY_QUICK);
                    } else {
                        i3 = MeasureSpec.makeMeasureSpec(layoutParams.width, UCCore.VERIFY_POLICY_QUICK);
                    }
                    if (layoutParams.height == -2) {
                        i4 = MeasureSpec.makeMeasureSpec(i6, Integer.MIN_VALUE);
                    } else if (layoutParams.height == -1) {
                        i4 = MeasureSpec.makeMeasureSpec(i6, UCCore.VERIFY_POLICY_QUICK);
                    } else {
                        i4 = MeasureSpec.makeMeasureSpec(layoutParams.height, UCCore.VERIFY_POLICY_QUICK);
                    }
                    childAt.measure(i3, i4);
                    this.mSlideRange = (size2 - this.mPanelHeight) - this.mDragOffset;
                }
            }
            setMeasuredDimension(size, size2);
            if (this.mAnchor != 1.0f) {
                this.mAnchorPoint = this.mAnchor;
            } else if (this.mAnchorHeight != -1) {
                computeAnchorPoint(paddingTop);
            }
            if (this.mLowerAnchorHeight > 0) {
                computeLowerAnchorPoint(paddingTop);
            }
        }
    }

    private void computeAnchorPoint(int i) {
        float f = (float) (i - this.mPanelHeight);
        if (((double) Math.abs(f)) > 0.001d) {
            float f2 = ((float) (this.mAnchorHeight - this.mPanelHeight)) / f;
            if (((double) Math.abs(f2 - this.mAnchorPoint)) > 0.001d) {
                setAnchorPoint(f2, false);
            }
        }
    }

    private void computeLowerAnchorPoint(int i) {
        float f = (float) (i - this.mPanelHeight);
        if (((double) Math.abs(f)) > 0.001d) {
            float f2 = ((float) (this.mLowerAnchorHeight - this.mPanelHeight)) / f;
            if (((double) Math.abs(f2 - this.mAnchorPoint)) > 0.001d) {
                setLowerAnchorPoint(f2, false);
            }
        }
    }

    private void computeSlideRange() {
        this.mSlideRange = (((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom()) - this.mPanelHeight) - this.mDragOffset;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int childCount = getChildCount();
        if (this.mFirstLayout) {
            switch (this.mSlideState) {
                case ANCHORED:
                    this.mSlideOffset = this.mAnchorPoint;
                    this.mLastState = getResources().getString(R.string.anchored);
                    break;
                case LOWERANCHORED:
                    this.mSlideOffset = this.mLowerAnchorPoint;
                    this.mLastState = getResources().getString(R.string.loweranchored);
                    break;
                case EXPANDED:
                    this.mSlideOffset = 1.0f;
                    this.mLastState = getResources().getString(R.string.expanded);
                    break;
                case HIDDEN:
                    this.mSlideOffset = computeSlideOffset(computePanelTopPosition(0.0f) + (this.mIsSlidingUp ? this.mPanelHeight : -this.mPanelHeight));
                    break;
                default:
                    this.mSlideOffset = 0.0f;
                    this.mLastState = getResources().getString(R.string.collapsed);
                    break;
            }
            this.mLastSlideState = this.mSlideState;
            this.mTargetPanelTop = computePanelTopPosition(this.mSlideOffset);
        }
        if (this.mSlideState == SlideState.ANCHORED) {
            this.mSlideOffset = this.mAnchorPoint;
        }
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8 || (i5 != 0 && !this.mFirstLayout)) {
                int measuredHeight = childAt.getMeasuredHeight();
                int computePanelTopPosition = childAt == this.mSlideableView ? computePanelTopPosition(this.mSlideOffset) : paddingTop;
                if (!this.mIsSlidingUp && childAt == this.mMainView && !this.mOverlayContent) {
                    computePanelTopPosition = computePanelTopPosition(this.mSlideOffset) + this.mSlideableView.getMeasuredHeight();
                }
                childAt.layout(paddingLeft, computePanelTopPosition, childAt.getMeasuredWidth() + paddingLeft, measuredHeight + computePanelTopPosition);
            }
        }
        if (this.mFirstLayout) {
            updateObscuredViewVisibility();
        }
        this.mSwitchCollapsedType = false;
        this.mFirstLayout = false;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i2 != i4) {
            this.mFirstLayout = true;
        }
    }

    public void setIsScrollTop(boolean z) {
        this.mIsScrollTop = z;
    }

    public void setEnabled(boolean z) {
        if (!z) {
            collapsePanel();
        }
        super.setEnabled(z);
    }

    public void setOnDisallowInterceptTouchListener(b bVar) {
        this.mDisallowInterceptTouchListener = bVar;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        if (!this.mDragEnable) {
            this.mDragHelper.cancel();
            return super.onInterceptTouchEvent(motionEvent);
        } else if (!this.mCaptureViewOnDraggingState && this.mDragHelper.getViewDragState() != 0) {
            return super.onInterceptTouchEvent(motionEvent);
        } else {
            if (!isEnabled() || !this.mIsSlidingEnabled || (this.mIsUnableToDrag && actionMasked != 0)) {
                this.mDragHelper.cancel();
                return super.onInterceptTouchEvent(motionEvent);
            }
            boolean z = true;
            switch (actionMasked) {
                case 0:
                    this.mIsUnableToDrag = false;
                    this.mInitialMotionX = motionEvent.getX();
                    this.mInitialMotionY = motionEvent.getY();
                    if (this.mInitialMotionY <= ((float) this.mSlideableView.getTop()) && this.mSlideState != SlideState.EXPANDED) {
                        z = false;
                    }
                    this.isConsumed = z;
                    if (isDisallowInterceptTouchEvent(motionEvent)) {
                        return false;
                    }
                    break;
                case 1:
                case 3:
                    this.mDragHelper.cancel();
                    return super.onInterceptTouchEvent(motionEvent);
                case 2:
                    if (!isDragViewUnder((int) this.mInitialMotionX, (int) this.mInitialMotionY)) {
                        this.mDragHelper.cancel();
                        return super.onInterceptTouchEvent(motionEvent);
                    }
                    this.moveY1 = motionEvent.getY();
                    if (this.mIsScrollTop && this.mSlideOffset == 1.0f) {
                        if (this.moveY1 - this.mInitialMotionY >= ((float) this.mDragHelper.getTouchSlop())) {
                            shouldInterceptTouchEvent(motionEvent);
                            return true;
                        } else if (this.moveY1 - this.mInitialMotionY < ((float) (-this.mDragHelper.getTouchSlop()))) {
                            return super.onInterceptTouchEvent(motionEvent);
                        }
                    }
                    if (isDisallowInterceptTouchEvent(motionEvent)) {
                        return false;
                    }
                    break;
            }
            return shouldInterceptTouchEvent(motionEvent);
        }
    }

    private boolean isDisallowInterceptTouchEvent(MotionEvent motionEvent) {
        return this.mDisallowInterceptTouchListener != null && this.mDisallowInterceptTouchListener.a();
    }

    public boolean shouldInterceptTouchEvent(MotionEvent motionEvent) {
        try {
            return this.mDragHelper.shouldInterceptTouchEvent(motionEvent);
        } catch (ArrayIndexOutOfBoundsException unused) {
            return this.mSlideOffset != 1.0f;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isSlidingEnabled()) {
            return super.onTouchEvent(motionEvent);
        }
        try {
            this.mDragHelper.processTouchEvent(motionEvent);
        } catch (Throwable unused) {
        }
        return this.isConsumed || onInterceptTouchEventHorizontal(motionEvent);
    }

    private boolean onInterceptTouchEventHorizontal(MotionEvent motionEvent) {
        if (!this.isConsumed || !this.mDragHorizontalIntercept || motionEvent.getActionMasked() != 2 || Math.abs(motionEvent.getX() - this.mInitialMotionX) <= ((float) this.mInterceptTouchSlop)) {
            return false;
        }
        return true;
    }

    public boolean isCaptureViewOnDraggingState() {
        return this.mCaptureViewOnDraggingState;
    }

    public void setCaptureViewOnDraggingState(boolean z) {
        this.mCaptureViewOnDraggingState = z;
    }

    private boolean isDragViewUnder(int i, int i2) {
        if (this.mDragView == null) {
            return false;
        }
        int[] iArr = new int[2];
        this.mDragView.getLocationOnScreen(iArr);
        int[] iArr2 = new int[2];
        getLocationOnScreen(iArr2);
        int i3 = iArr2[0] + i;
        int i4 = iArr2[1] + i2;
        if (i3 < iArr[0] || i3 >= iArr[0] + this.mDragView.getWidth() || i4 < iArr[1] || i4 >= iArr[1] + this.mDragView.getHeight()) {
            return false;
        }
        return true;
    }

    private boolean expandPanel(View view, int i, float f) {
        return this.mFirstLayout || smoothSlideTo(f, i);
    }

    private boolean collapsePanel(View view, int i) {
        return this.mFirstLayout || smoothSlideTo(0.0f, i);
    }

    /* access modifiers changed from: private */
    public int computePanelTopPosition(float f) {
        int measuredHeight = this.mSlideableView != null ? this.mSlideableView.getMeasuredHeight() : 0;
        int i = (int) (f * ((float) this.mSlideRange));
        if (this.mIsSlidingUp) {
            return ((getMeasuredHeight() - getPaddingBottom()) - this.mPanelHeight) - i;
        }
        return (getPaddingTop() - measuredHeight) + this.mPanelHeight + i;
    }

    /* access modifiers changed from: private */
    public float computeSlideOffset(int i) {
        int computePanelTopPosition = computePanelTopPosition(0.0f);
        return (this.mIsSlidingUp ? (float) (computePanelTopPosition - i) : (float) (i - computePanelTopPosition)) / ((float) this.mSlideRange);
    }

    public boolean collapsePanel() {
        if (getVisibility() == 8 || !this.mDragEnable) {
            return false;
        }
        if (this.mFirstLayout) {
            this.mSlideState = SlideState.COLLAPSED;
            return true;
        } else if (this.mSlideState == SlideState.HIDDEN || this.mSlideState == SlideState.COLLAPSED) {
            return false;
        } else {
            return collapsePanel(this.mSlideableView, 0);
        }
    }

    public boolean expandPanel() {
        if (!this.mDragEnable) {
            return false;
        }
        if (!this.mFirstLayout) {
            return expandPanel(1.0f);
        }
        this.mSlideState = SlideState.EXPANDED;
        return true;
    }

    public boolean anchorPanel() {
        if (!this.mDragEnable) {
            return false;
        }
        if (!this.mFirstLayout) {
            return expandPanel(this.mAnchorPoint);
        }
        this.mSlideState = SlideState.ANCHORED;
        return true;
    }

    public boolean anchorPanelAtLowerPos() {
        if (!this.mDragEnable || this.mLowerAnchorPoint < 0.0f) {
            return false;
        }
        if (!this.mFirstLayout) {
            return expandPanel(this.mLowerAnchorPoint);
        }
        this.mSlideState = SlideState.LOWERANCHORED;
        return true;
    }

    public boolean expandPanel(float f) {
        if (this.mSlideableView == null || (this.mSlideState == SlideState.EXPANDED && f == 1.0f)) {
            return false;
        }
        this.mSlideableView.setVisibility(0);
        return expandPanel(this.mSlideableView, 0, f);
    }

    public SlideState getSlideState() {
        return this.mSlideState;
    }

    public int getSlideableViewTop() {
        if (this.mSlideableView != null) {
            return this.mSlideableView.getTop();
        }
        return 0;
    }

    public void showPanel() {
        if (this.mFirstLayout) {
            this.mSlideState = SlideState.COLLAPSED;
        } else if (this.mSlideableView != null && this.mSlideableView.getVisibility() != 0) {
            this.mSlideableView.setVisibility(0);
            requestLayout();
            smoothSlideTo(0.0f, 0);
        }
    }

    public void hidePanel() {
        if (this.mFirstLayout) {
            this.mSlideState = SlideState.HIDDEN;
        } else if (this.mSlideState != SlideState.DRAGGING && this.mSlideState != SlideState.HIDDEN) {
            smoothSlideTo(computeSlideOffset(computePanelTopPosition(0.0f) + (this.mIsSlidingUp ? this.mPanelHeight : -this.mPanelHeight)), 0);
        }
    }

    /* access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public void onPanelDragged(int i) {
        this.mSlideState = SlideState.DRAGGING;
        this.mSlideOffset = computeSlideOffset(i);
        dispatchOnPanelSlide(this.mSlideableView);
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j) {
        int save = canvas.save(2);
        if (isSlidingEnabled() && this.mSlideableView != view && !this.mOverlayContent) {
            canvas.getClipBounds(this.mTmpRect);
            if (this.mIsSlidingUp) {
                this.mTmpRect.bottom = Math.min(this.mTmpRect.bottom, this.mSlideableView.getTop());
            } else {
                this.mTmpRect.top = Math.max(this.mTmpRect.top, this.mSlideableView.getBottom());
            }
            canvas.clipRect(this.mTmpRect);
        }
        boolean drawChild = super.drawChild(canvas, view, j);
        canvas.restoreToCount(save);
        return drawChild;
    }

    /* access modifiers changed from: 0000 */
    public boolean smoothSlideTo(float f, int i) {
        if (!isSlidingEnabled()) {
            return false;
        }
        int computePanelTopPosition = computePanelTopPosition(f);
        this.mTargetPanelTop = computePanelTopPosition;
        if (!this.mDragHelper.smoothSlideViewTo(this.mSlideableView, this.mSlideableView.getLeft(), computePanelTopPosition)) {
            return false;
        }
        setAllChildrenVisible();
        ViewCompat.postInvalidateOnAnimation(this);
        return true;
    }

    public void computeScroll() {
        if (this.mDragHelper != null && this.mDragHelper.continueSettling(true)) {
            if (!isSlidingEnabled()) {
                this.mDragHelper.abort();
                return;
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
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
        savedState.a = this.mSlideState;
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mSlideState = savedState.a;
    }

    /* access modifiers changed from: private */
    public int getTargetPos(float f) {
        float f2;
        int height = getHeight();
        if (height == 0) {
            f2 = -1.0f;
        } else {
            f2 = ((this.mSlideOffset * ((float) this.mSlideRange)) + ((float) this.mPanelHeight)) / ((float) height);
        }
        if (f > 0.0f) {
            if (this.mSlideOffset <= this.mAnchorPoint) {
                int computePanelTopPosition = computePanelTopPosition(this.mAnchorPoint);
                SlideState slideState = SlideState.ANCHORED;
                return computePanelTopPosition;
            }
            int computePanelTopPosition2 = computePanelTopPosition(1.0f);
            SlideState slideState2 = SlideState.EXPANDED;
            return computePanelTopPosition2;
        } else if (f < 0.0f) {
            if (this.mSlideOffset > this.mAnchorPoint) {
                int computePanelTopPosition3 = computePanelTopPosition(this.mAnchorPoint);
                SlideState slideState3 = SlideState.ANCHORED;
                return computePanelTopPosition3;
            }
            int computePanelTopPosition4 = computePanelTopPosition(0.0f);
            SlideState slideState4 = SlideState.COLLAPSED;
            return computePanelTopPosition4;
        } else if (this.mLastSlideState == SlideState.ANCHORED) {
            if (this.mAnchorPoint != 1.0f && this.mExpandPointAbsolute == 1.0f && ((double) this.mSlideOffset) >= ((double) this.mAnchorPoint) + 0.1d) {
                int computePanelTopPosition5 = computePanelTopPosition(1.0f);
                SlideState slideState5 = SlideState.EXPANDED;
                return computePanelTopPosition5;
            } else if (this.mSlideOffset <= this.mAnchorPoint - getThreshold(this.mAnchorPoint, 0.0f)) {
                int computePanelTopPosition6 = computePanelTopPosition(0.0f);
                SlideState slideState6 = SlideState.COLLAPSED;
                return computePanelTopPosition6;
            } else if (this.mExpandPointAbsolute != 1.0f && f2 != -1.0f && f2 > this.mExpandPointAbsolute) {
                int computePanelTopPosition7 = computePanelTopPosition(1.0f);
                SlideState slideState7 = SlideState.EXPANDED;
                return computePanelTopPosition7;
            } else if (((double) this.mSlideOffset) > 0.1d || this.mSlideOffset >= this.mAnchorPoint) {
                int computePanelTopPosition8 = computePanelTopPosition(this.mAnchorPoint);
                SlideState slideState8 = SlideState.ANCHORED;
                return computePanelTopPosition8;
            } else {
                int computePanelTopPosition9 = computePanelTopPosition(0.0f);
                SlideState slideState9 = SlideState.COLLAPSED;
                return computePanelTopPosition9;
            }
        } else if (this.mLastSlideState == SlideState.EXPANDED) {
            if (this.mSlideOffset > 0.9f) {
                int computePanelTopPosition10 = computePanelTopPosition(1.0f);
                SlideState slideState10 = SlideState.EXPANDED;
                return computePanelTopPosition10;
            } else if (this.mAnchorPoint - this.mSlideOffset < 0.1f) {
                int computePanelTopPosition11 = computePanelTopPosition(this.mAnchorPoint);
                SlideState slideState11 = SlideState.ANCHORED;
                return computePanelTopPosition11;
            } else {
                int computePanelTopPosition12 = computePanelTopPosition(0.0f);
                SlideState slideState12 = SlideState.COLLAPSED;
                return computePanelTopPosition12;
            }
        } else if (this.mExpandPointAbsolute != 1.0f && f2 != -1.0f && f2 > this.mExpandPointAbsolute) {
            int computePanelTopPosition13 = computePanelTopPosition(1.0f);
            SlideState slideState13 = SlideState.EXPANDED;
            return computePanelTopPosition13;
        } else if (this.mSlideOffset > this.mAnchorPoint + 0.1f) {
            int computePanelTopPosition14 = computePanelTopPosition(1.0f);
            SlideState slideState14 = SlideState.EXPANDED;
            return computePanelTopPosition14;
        } else if (this.mSlideOffset > 0.1f) {
            int computePanelTopPosition15 = computePanelTopPosition(this.mAnchorPoint);
            SlideState slideState15 = SlideState.ANCHORED;
            return computePanelTopPosition15;
        } else {
            int computePanelTopPosition16 = computePanelTopPosition(0.0f);
            SlideState slideState16 = SlideState.COLLAPSED;
            return computePanelTopPosition16;
        }
    }

    /* access modifiers changed from: private */
    public int getTargetPosAtLowerAnchor(float f) {
        float f2;
        int height = getHeight();
        if (height == 0) {
            f2 = -1.0f;
        } else {
            f2 = ((this.mSlideOffset * ((float) this.mSlideRange)) + ((float) this.mPanelHeight)) / ((float) height);
        }
        if (f > 0.0f) {
            if (this.mSlideOffset <= this.mLowerAnchorPoint) {
                return computePanelTopPosition(this.mLowerAnchorPoint);
            }
            if (this.mSlideOffset <= this.mAnchorPoint) {
                return computePanelTopPosition(this.mAnchorPoint);
            }
            return computePanelTopPosition(1.0f);
        } else if (f >= 0.0f) {
            if (this.mLastSlideState == SlideState.ANCHORED) {
                if (this.mAnchorPoint != 1.0f && this.mExpandPointAbsolute == 1.0f && ((double) this.mSlideOffset) >= ((double) this.mAnchorPoint) + 0.1d) {
                    return computePanelTopPosition(1.0f);
                }
                if (this.mSlideOffset > this.mLowerAnchorPoint - getThreshold(this.mLowerAnchorPoint, 0.0f)) {
                    if (this.mSlideOffset <= this.mAnchorPoint - getThreshold(this.mAnchorPoint, this.mLowerAnchorPoint)) {
                        return computePanelTopPosition(this.mLowerAnchorPoint);
                    }
                    if (((double) this.mSlideOffset) >= ((double) this.mAnchorPoint) + 0.1d) {
                        return computePanelTopPosition(1.0f);
                    }
                    if (this.mExpandPointAbsolute != 1.0f && f2 != -1.0f && f2 > this.mExpandPointAbsolute) {
                        return computePanelTopPosition(1.0f);
                    }
                    if (((double) this.mSlideOffset) > 0.1d || this.mSlideOffset >= this.mAnchorPoint) {
                        return computePanelTopPosition(this.mAnchorPoint);
                    }
                }
            } else if (this.mLastSlideState == SlideState.LOWERANCHORED) {
                if (((double) this.mSlideOffset) > ((double) this.mAnchorPoint) + 0.1d) {
                    return computePanelTopPosition(1.0f);
                }
                if (this.mSlideOffset > this.mLowerAnchorPoint + getThreshold(this.mAnchorPoint, this.mLowerAnchorPoint)) {
                    return computePanelTopPosition(this.mAnchorPoint);
                }
                if (this.mSlideOffset >= this.mLowerAnchorPoint - getThreshold(this.mLowerAnchorPoint, 0.0f)) {
                    return computePanelTopPosition(this.mLowerAnchorPoint);
                }
            } else if (this.mLastSlideState == SlideState.EXPANDED) {
                if (this.mSlideOffset > 0.9f) {
                    return computePanelTopPosition(1.0f);
                }
                if (this.mSlideOffset < this.mAnchorPoint - getThreshold(this.mAnchorPoint, this.mLowerAnchorPoint)) {
                    return computePanelTopPosition(this.mLowerAnchorPoint);
                }
                return computePanelTopPosition(this.mAnchorPoint);
            } else if (this.mExpandPointAbsolute != 1.0f && f2 != -1.0f && f2 > this.mExpandPointAbsolute) {
                return computePanelTopPosition(1.0f);
            } else {
                if (((double) this.mSlideOffset) > ((double) this.mAnchorPoint) + 0.1d) {
                    return computePanelTopPosition(this.mAnchorPoint);
                }
                if (this.mSlideOffset > getThreshold(this.mAnchorPoint, this.mLowerAnchorPoint)) {
                    return computePanelTopPosition(this.mLowerAnchorPoint);
                }
            }
            return computePanelTopPosition(0.0f);
        } else if (this.mSlideOffset > this.mAnchorPoint) {
            return computePanelTopPosition(this.mAnchorPoint);
        } else {
            if (this.mSlideOffset > this.mLowerAnchorPoint) {
                return computePanelTopPosition(this.mLowerAnchorPoint);
            }
            return computePanelTopPosition(0.0f);
        }
    }

    private float getThreshold(float f, float f2) {
        return Math.abs(f2 - f) / 4.0f;
    }

    public View getDragView() {
        return this.mDragView;
    }

    public int getDragViewHeight() {
        if (this.mDragView == null) {
            return 0;
        }
        return this.mDragView.getHeight();
    }

    public void setDragEnable(boolean z) {
        this.mDragEnable = z;
    }

    public boolean isDragEnable() {
        return this.mDragEnable;
    }

    public void setDragHorizontalIntercept(boolean z) {
        this.mDragHorizontalIntercept = z;
    }

    public boolean isDragHorizontalIntercept() {
        return this.mDragHorizontalIntercept;
    }

    public int getDragOffset() {
        return this.mDragOffset;
    }

    public void resetCollapsedType() {
        this.mSwitchCollapsedType = true;
        setPanelHeight(agn.a(getContext(), 48.0f));
        this.mCollapsedType = 0;
    }

    public void setCollapsedHeight(int i) {
        setPanelHeight(i);
        this.mCollapsedType = 1;
    }

    public int getCollapsedType() {
        return this.mCollapsedType;
    }

    public void setLogEventListener(c cVar) {
        this.mLogListener = cVar;
    }

    public void setDragOffset(int i) {
        this.mDragOffset = i;
        requestLayout();
    }

    public int getTargetPanelTop() {
        return this.mTargetPanelTop;
    }
}
