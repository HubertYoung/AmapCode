package com.autonavi.map.widget;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.KeyEventCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import com.alipay.sdk.util.h;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class RecyclableViewPager extends ViewGroup {
    private static final int CLOSE_ENOUGH = 2;
    private static final Comparator<ItemInfo> COMPARATOR = new Comparator<ItemInfo>() {
        public final int compare(ItemInfo itemInfo, ItemInfo itemInfo2) {
            return itemInfo.position - itemInfo2.position;
        }
    };
    private static final boolean DEBUG = false;
    private static final int DEFAULT_GUTTER_SIZE = 16;
    private static final int DEFAULT_OFFSCREEN_PAGES = 2;
    private static final int DRAW_ORDER_DEFAULT = 0;
    private static final int DRAW_ORDER_FORWARD = 1;
    private static final int DRAW_ORDER_REVERSE = 2;
    private static final int INVALID_POINTER = -1;
    /* access modifiers changed from: private */
    public static final int[] LAYOUT_ATTRS = {16842931};
    private static final int MAX_SETTLE_DURATION = 600;
    private static final int MIN_DISTANCE_FOR_FLING = 25;
    private static final int MIN_FLING_VELOCITY = 400;
    public static final int MIN_RECYCLABLE_SIZE = 5;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "RecyclableViewPager";
    private static final boolean USE_CACHE = false;
    private static final Interpolator sInterpolator = new Interpolator() {
        public final float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    };
    private static final ViewPositionComparator sPositionComparator = new ViewPositionComparator();
    private boolean flingRight;
    private boolean isCanScrollable = true;
    private int mActivePointerId = -1;
    /* access modifiers changed from: private */
    public RecyclablePagerAdapter mAdapter;
    private OnAdapterChangeListener mAdapterChangeListener;
    private int mBottomPageBounds;
    private boolean mCalledSuper;
    private boolean mChangeData = false;
    private int mChildHeightMeasureSpec;
    private int mChildWidthMeasureSpec;
    private int mCloseEnough;
    /* access modifiers changed from: private */
    public int mCurItem = 0;
    private int mDecorChildCount;
    private int mDefaultGutterSize;
    private int mDrawingOrder;
    private ArrayList<View> mDrawingOrderedChildren;
    private final Runnable mEndScrollRunnable = new Runnable() {
        public void run() {
            RecyclableViewPager.this.setScrollState(0);
            RecyclableViewPager.this.populate();
        }
    };
    private int mExpectedAdapterCount;
    private long mFakeDragBeginTime;
    private boolean mFakeDragging;
    private boolean mFirstLayout = true;
    private float mFirstOffset = -3.4028235E38f;
    private int mFlingDistance;
    private int mGutterSize;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private OnPageChangeListener mInternalPageChangeListener;
    private boolean mIsBeingDragged;
    private boolean mIsUnableToDrag;
    private final ArrayList<ItemInfo> mItems = new ArrayList<>();
    private int mLastItem = 0;
    private float mLastMotionX;
    private float mLastMotionY;
    private float mLastOffset = Float.MAX_VALUE;
    private EdgeEffectCompat mLeftEdge;
    private Drawable mMarginDrawable;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private PagerObserver mObserver;
    private int mOffscreenPageLimit = 2;
    private OnPageChangeListener mOnPageChangeListener;
    private int mPageMargin;
    private PageTransformer mPageTransformer;
    private boolean mPopulatePending;
    private ArrayList<View> mRecycler;
    private Parcelable mRestoredAdapterState = null;
    private ClassLoader mRestoredClassLoader = null;
    private int mRestoredCurItem = -1;
    private EdgeEffectCompat mRightEdge;
    private int mScrollState = 0;
    private Scroller mScroller;
    private boolean mScrollingCacheEnabled;
    private Method mSetChildrenDrawingOrderEnabled;
    private final Rect mTempRect = new Rect();
    private int mTopPageBounds;
    private int mTouchSlop;
    private boolean mUseRecycler = true;
    private VelocityTracker mVelocityTracker;
    private int mVelocityX;

    interface Decor {
    }

    static class ItemInfo {
        Object object;
        float offset;
        int position;
        boolean scrolling;
        float widthFactor;

        ItemInfo() {
        }
    }

    public static class LayoutParams extends android.view.ViewGroup.LayoutParams {
        int childIndex;
        public int gravity;
        public boolean isDecor;
        boolean needsMeasure;
        int position;
        float widthFactor = 0.0f;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, RecyclableViewPager.LAYOUT_ATTRS);
            this.gravity = obtainStyledAttributes.getInteger(0, 48);
            obtainStyledAttributes.recycle();
        }
    }

    class MyAccessibilityDelegate extends AccessibilityDelegateCompat {
        MyAccessibilityDelegate() {
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName(RecyclableViewPager.class.getName());
            AccessibilityRecordCompat obtain = AccessibilityRecordCompat.obtain();
            obtain.setScrollable(canScroll());
            if (accessibilityEvent.getEventType() == 4096 && RecyclableViewPager.this.mAdapter != null) {
                obtain.setItemCount(RecyclableViewPager.this.mAdapter.getCount());
                obtain.setFromIndex(RecyclableViewPager.this.mCurItem);
                obtain.setToIndex(RecyclableViewPager.this.mCurItem);
            }
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setClassName(RecyclableViewPager.class.getName());
            accessibilityNodeInfoCompat.setScrollable(canScroll());
            if (RecyclableViewPager.this.canScrollHorizontally(1)) {
                accessibilityNodeInfoCompat.addAction(4096);
            }
            if (RecyclableViewPager.this.canScrollHorizontally(-1)) {
                accessibilityNodeInfoCompat.addAction(8192);
            }
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            if (i != 4096) {
                if (i != 8192 || !RecyclableViewPager.this.canScrollHorizontally(-1)) {
                    return false;
                }
                RecyclableViewPager.this.setCurrentItem(RecyclableViewPager.this.mCurItem - 1);
                return true;
            } else if (!RecyclableViewPager.this.canScrollHorizontally(1)) {
                return false;
            } else {
                RecyclableViewPager.this.setCurrentItem(RecyclableViewPager.this.mCurItem + 1);
                return true;
            }
        }

        private boolean canScroll() {
            return RecyclableViewPager.this.mAdapter != null && RecyclableViewPager.this.mAdapter.getCount() > 1;
        }
    }

    interface OnAdapterChangeListener {
        void onAdapterChanged(PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2);
    }

    public interface OnPageChangeListener {
        void onPageScrollStateChanged(int i);

        void onPageScrolled(int i, float f, int i2);

        void onPageSelected(int i);
    }

    public interface PageTransformer {
        void transformPage(View view, float f);
    }

    class PagerObserver extends DataSetObserver {
        private PagerObserver() {
        }

        public void onChanged() {
            RecyclableViewPager.this.dataSetChanged();
        }

        public void onInvalidated() {
            RecyclableViewPager.this.dataSetChanged();
        }
    }

    public static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() {
            public final SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public final SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        });
        Parcelable adapterState;
        ClassLoader loader;
        int position;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.position);
            parcel.writeParcelable(this.adapterState, i);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("FragmentPager.SavedState{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" position=");
            sb.append(this.position);
            sb.append(h.d);
            return sb.toString();
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel);
            classLoader = classLoader == null ? getClass().getClassLoader() : classLoader;
            this.position = parcel.readInt();
            this.adapterState = parcel.readParcelable(classLoader);
            this.loader = classLoader;
        }
    }

    public static class SimpleOnPageChangeListener implements OnPageChangeListener {
        public void onPageScrollStateChanged(int i) {
        }

        public void onPageScrolled(int i, float f, int i2) {
        }

        public void onPageSelected(int i) {
        }
    }

    static class ViewPositionComparator implements Comparator<View> {
        ViewPositionComparator() {
        }

        public int compare(View view, View view2) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            LayoutParams layoutParams2 = (LayoutParams) view2.getLayoutParams();
            if (layoutParams.isDecor != layoutParams2.isDecor) {
                return layoutParams.isDecor ? 1 : -1;
            }
            return layoutParams.position - layoutParams2.position;
        }
    }

    /* access modifiers changed from: protected */
    public boolean canScroll(View view, boolean z, int i, int i2, int i3) {
        return false;
    }

    public RecyclableViewPager(Context context) {
        super(context);
        initViewPager();
    }

    public RecyclableViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initViewPager();
    }

    /* access modifiers changed from: 0000 */
    public void initViewPager() {
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        Context context = getContext();
        this.mScroller = new Scroller(context, sInterpolator);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        float f = context.getResources().getDisplayMetrics().density;
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
        this.mMinimumVelocity = (int) (400.0f * f);
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mLeftEdge = new EdgeEffectCompat(context);
        this.mRightEdge = new EdgeEffectCompat(context);
        this.mFlingDistance = (int) (25.0f * f);
        this.mCloseEnough = (int) (2.0f * f);
        this.mDefaultGutterSize = (int) (f * 16.0f);
        ViewCompat.setAccessibilityDelegate(this, new MyAccessibilityDelegate());
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        removeCallbacks(this.mEndScrollRunnable);
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: private */
    public void setScrollState(int i) {
        if (this.mScrollState != i) {
            this.mScrollState = i;
            if (this.mPageTransformer != null) {
                enableLayers(i != 0);
            }
            if (this.mOnPageChangeListener != null) {
                this.mOnPageChangeListener.onPageScrollStateChanged(i);
            }
        }
    }

    public void setAdapter(RecyclablePagerAdapter recyclablePagerAdapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mObserver);
            this.mAdapter.startUpdate((ViewGroup) this);
            for (int i = 0; i < this.mItems.size(); i++) {
                ItemInfo itemInfo = this.mItems.get(i);
                this.mAdapter.destroyItem((ViewGroup) this, itemInfo.position, itemInfo.object);
            }
            this.mAdapter.finishUpdate((ViewGroup) this);
            this.mItems.clear();
            removeNonDecorViews();
            this.mLastItem = 0;
            this.mCurItem = 0;
            scrollTo(0, 0);
        }
        RecyclablePagerAdapter recyclablePagerAdapter2 = this.mAdapter;
        this.mAdapter = recyclablePagerAdapter;
        this.mExpectedAdapterCount = 0;
        if (this.mAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new PagerObserver();
            }
            this.mAdapter.registerDataSetObserver(this.mObserver);
            this.mPopulatePending = false;
            boolean z = this.mFirstLayout;
            this.mFirstLayout = true;
            this.mExpectedAdapterCount = this.mAdapter.getCount();
            if (this.mRestoredCurItem >= 0) {
                this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
                setCurrentItemInternal(this.mRestoredCurItem, false, true);
                this.mRestoredCurItem = -1;
                this.mRestoredAdapterState = null;
                this.mRestoredClassLoader = null;
            } else if (!z) {
                populate();
            } else {
                requestLayout();
            }
        }
        if (this.mAdapterChangeListener != null && recyclablePagerAdapter2 != recyclablePagerAdapter) {
            this.mAdapterChangeListener.onAdapterChanged(recyclablePagerAdapter2, recyclablePagerAdapter);
        }
    }

    private void removeNonDecorViews() {
        int i = 0;
        while (i < getChildCount()) {
            if (!((LayoutParams) getChildAt(i).getLayoutParams()).isDecor) {
                removeViewAt(i);
                i--;
            }
            i++;
        }
    }

    public PagerAdapter getAdapter() {
        return this.mAdapter;
    }

    /* access modifiers changed from: 0000 */
    public void setOnAdapterChangeListener(OnAdapterChangeListener onAdapterChangeListener) {
        this.mAdapterChangeListener = onAdapterChangeListener;
    }

    private int getClientWidth() {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
    }

    public void setCurrentItem(int i) {
        this.mPopulatePending = false;
        setCurrentItemInternal(i, !this.mFirstLayout, false);
    }

    public void setCurrentItem(int i, boolean z) {
        this.mPopulatePending = false;
        setCurrentItemInternal(i, z, false);
    }

    public int getCurrentItem() {
        return this.mCurItem;
    }

    /* access modifiers changed from: 0000 */
    public void setCurrentItemInternal(int i, boolean z, boolean z2) {
        setCurrentItemInternal(i, z, z2, 0);
    }

    /* access modifiers changed from: 0000 */
    public void setCurrentItemInternal(int i, boolean z, boolean z2, int i2) {
        this.mVelocityX = i2;
        if (this.mAdapter == null || this.mAdapter.getCount() <= 0) {
            setScrollingCacheEnabled(false);
        } else if (z2 || this.mCurItem != i || this.mItems.size() == 0) {
            boolean z3 = true;
            if (i < 0) {
                i = 0;
            } else if (i >= this.mAdapter.getCount()) {
                i = this.mAdapter.getCount() - 1;
            }
            int i3 = this.mOffscreenPageLimit;
            if (i > this.mCurItem + i3 || i < this.mCurItem - i3) {
                for (int i4 = 0; i4 < this.mItems.size(); i4++) {
                    this.mItems.get(i4).scrolling = true;
                }
            }
            if (this.mCurItem == i) {
                z3 = false;
            }
            if (this.mFirstLayout) {
                this.mLastItem = this.mCurItem;
                this.mCurItem = i;
                if (z3 && this.mOnPageChangeListener != null) {
                    this.mOnPageChangeListener.onPageSelected(i % this.mAdapter.getRealCount());
                }
                if (z3 && this.mInternalPageChangeListener != null) {
                    this.mInternalPageChangeListener.onPageSelected(i % this.mAdapter.getRealCount());
                }
                requestLayout();
                return;
            }
            populate(i);
            scrollToItem(i, z, i2, z3);
        } else {
            setScrollingCacheEnabled(false);
        }
    }

    private void scrollToItem(int i, boolean z, int i2, boolean z2) {
        ItemInfo infoForPosition = infoForPosition(i);
        int clientWidth = infoForPosition != null ? (int) (((float) getClientWidth()) * Math.max(this.mFirstOffset, Math.min(infoForPosition.offset, this.mLastOffset))) : 0;
        if (z) {
            smoothScrollTo(clientWidth, 0, i2);
            if (z2 && this.mOnPageChangeListener != null) {
                this.mOnPageChangeListener.onPageSelected(i % this.mAdapter.getRealCount());
            }
            if (z2 && this.mInternalPageChangeListener != null) {
                this.mInternalPageChangeListener.onPageSelected(i % this.mAdapter.getRealCount());
            }
        } else {
            if (z2 && this.mOnPageChangeListener != null) {
                this.mOnPageChangeListener.onPageSelected(i % this.mAdapter.getRealCount());
            }
            if (z2 && this.mInternalPageChangeListener != null) {
                this.mInternalPageChangeListener.onPageSelected(i % this.mAdapter.getRealCount());
            }
            completeScroll(false);
            scrollTo(clientWidth, 0);
            pageScrolled(clientWidth);
        }
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    public void setPageTransformer(boolean z, PageTransformer pageTransformer) {
        if (VERSION.SDK_INT >= 11) {
            int i = 1;
            boolean z2 = pageTransformer != null;
            boolean z3 = z2 != (this.mPageTransformer != null);
            this.mPageTransformer = pageTransformer;
            setChildrenDrawingOrderEnabledCompat(z2);
            if (z2) {
                if (z) {
                    i = 2;
                }
                this.mDrawingOrder = i;
            } else {
                this.mDrawingOrder = 0;
            }
            if (z3) {
                populate();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x001c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setChildrenDrawingOrderEnabledCompat(boolean r7) {
        /*
            r6 = this;
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 7
            if (r0 < r1) goto L_0x002a
            java.lang.reflect.Method r0 = r6.mSetChildrenDrawingOrderEnabled
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x001c
            java.lang.Class<android.view.ViewGroup> r0 = android.view.ViewGroup.class
            java.lang.String r3 = "setChildrenDrawingOrderEnabled"
            java.lang.Class[] r4 = new java.lang.Class[r2]     // Catch:{ NoSuchMethodException -> 0x001c }
            java.lang.Class r5 = java.lang.Boolean.TYPE     // Catch:{ NoSuchMethodException -> 0x001c }
            r4[r1] = r5     // Catch:{ NoSuchMethodException -> 0x001c }
            java.lang.reflect.Method r0 = r0.getDeclaredMethod(r3, r4)     // Catch:{ NoSuchMethodException -> 0x001c }
            r6.mSetChildrenDrawingOrderEnabled = r0     // Catch:{ NoSuchMethodException -> 0x001c }
        L_0x001c:
            java.lang.reflect.Method r0 = r6.mSetChildrenDrawingOrderEnabled     // Catch:{ Exception -> 0x002a }
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x002a }
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ Exception -> 0x002a }
            r2[r1] = r7     // Catch:{ Exception -> 0x002a }
            r0.invoke(r6, r2)     // Catch:{ Exception -> 0x002a }
            return
        L_0x002a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.widget.RecyclableViewPager.setChildrenDrawingOrderEnabledCompat(boolean):void");
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int i, int i2) {
        if (this.mDrawingOrder == 2) {
            i2 = (i - 1) - i2;
        }
        return ((LayoutParams) this.mDrawingOrderedChildren.get(i2).getLayoutParams()).childIndex;
    }

    /* access modifiers changed from: 0000 */
    public OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener onPageChangeListener) {
        OnPageChangeListener onPageChangeListener2 = this.mInternalPageChangeListener;
        this.mInternalPageChangeListener = onPageChangeListener;
        return onPageChangeListener2;
    }

    public int getOffscreenPageLimit() {
        return this.mOffscreenPageLimit;
    }

    public void setOffscreenPageLimit(int i) {
        if (i < 2) {
            StringBuilder sb = new StringBuilder("Requested offscreen page limit ");
            sb.append(i);
            sb.append(" too small; defaulting to 2");
            i = 2;
        }
        if (i != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = i;
            populate();
        }
    }

    public void setUseRecycler(boolean z) {
        this.mUseRecycler = z;
    }

    public void setPageMargin(int i) {
        int i2 = this.mPageMargin;
        this.mPageMargin = i;
        int width = getWidth();
        recomputeScrollPosition(width, width, i, i2);
        requestLayout();
    }

    public int getPageMargin() {
        return this.mPageMargin;
    }

    public void setPageMarginDrawable(Drawable drawable) {
        this.mMarginDrawable = drawable;
        if (drawable != null) {
            refreshDrawableState();
        }
        setWillNotDraw(drawable == null);
        invalidate();
    }

    public void setPageMarginDrawable(int i) {
        setPageMarginDrawable(getContext().getResources().getDrawable(i));
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mMarginDrawable;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mMarginDrawable;
        if (drawable != null && drawable.isStateful()) {
            drawable.setState(getDrawableState());
        }
    }

    /* access modifiers changed from: 0000 */
    public float distanceInfluenceForSnapDuration(float f) {
        return (float) Math.sin((double) ((float) (((double) (f - 0.5f)) * 0.4712389167638204d)));
    }

    /* access modifiers changed from: 0000 */
    public void smoothScrollTo(int i, int i2) {
        smoothScrollTo(i, i2, 0);
    }

    /* access modifiers changed from: 0000 */
    public void smoothScrollTo(int i, int i2, int i3) {
        int i4;
        if (getChildCount() == 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int i5 = i - scrollX;
        int i6 = i2 - scrollY;
        if (i5 == 0 && i6 == 0) {
            completeScroll(false);
            populate();
            setScrollState(0);
            return;
        }
        setScrollingCacheEnabled(true);
        setScrollState(2);
        int clientWidth = getClientWidth();
        int i7 = clientWidth / 2;
        float f = (float) clientWidth;
        float f2 = (float) i7;
        float distanceInfluenceForSnapDuration = f2 + (distanceInfluenceForSnapDuration(Math.min(1.0f, (((float) Math.abs(i5)) * 1.0f) / f)) * f2);
        int abs = Math.abs(i3);
        if (abs > 0) {
            i4 = Math.round(Math.abs(distanceInfluenceForSnapDuration / ((float) abs)) * 1000.0f) * 4;
        } else {
            i4 = (int) (((((float) Math.abs(i5)) / ((f * this.mAdapter.getPageWidth(this.mCurItem)) + ((float) this.mPageMargin))) + 1.0f) * 100.0f);
        }
        this.mScroller.startScroll(scrollX, scrollY, i5, i6, Math.min(i4, 600));
        ViewCompat.postInvalidateOnAnimation(this);
    }

    private void refreshView(ItemInfo itemInfo) {
        if (this.mUseRecycler) {
            itemInfo.widthFactor = this.mAdapter.getPageWidth(itemInfo.position);
            this.mAdapter.refreshView((View) itemInfo.object, itemInfo.position % this.mAdapter.getRealCount());
        }
    }

    /* access modifiers changed from: 0000 */
    public ItemInfo addNewItem(int i, int i2) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.position = i;
        if (this.mUseRecycler) {
            itemInfo.object = this.mAdapter.instantiateItemFromRecycler(this, getRecycleView(), i % this.mAdapter.getRealCount());
            if (itemInfo.object == null) {
                throw new RuntimeException(getContext().getString(R.string.adapter_instantiateitemfromrecycler_return_null));
            }
        } else {
            itemInfo.object = this.mAdapter.instantiateItem((ViewGroup) this, i % this.mAdapter.getRealCount());
        }
        itemInfo.widthFactor = this.mAdapter.getPageWidth(i);
        if (i2 < 0 || i2 >= this.mItems.size()) {
            this.mItems.add(itemInfo);
        } else {
            this.mItems.add(i2, itemInfo);
        }
        return itemInfo;
    }

    private View getRecycleView() {
        if (this.mRecycler == null || this.mRecycler.size() <= 0) {
            return null;
        }
        return this.mRecycler.remove(0);
    }

    private void recycleView(View view) {
        if (this.mRecycler == null) {
            this.mRecycler = new ArrayList<>();
        }
        this.mRecycler.add(view);
    }

    /* access modifiers changed from: 0000 */
    public void dataSetChanged() {
        int count = this.mAdapter.getCount();
        this.mExpectedAdapterCount = count;
        boolean z = this.mItems.size() < (this.mOffscreenPageLimit * 2) + 1 && this.mItems.size() < count;
        int i = this.mCurItem;
        this.mChangeData = true;
        boolean z2 = z;
        int i2 = i;
        int i3 = 0;
        boolean z3 = false;
        while (i3 < this.mItems.size()) {
            ItemInfo itemInfo = this.mItems.get(i3);
            int itemPosition = this.mAdapter.getItemPosition(itemInfo.object);
            if (itemPosition != -1) {
                if (itemPosition == -2) {
                    this.mItems.remove(i3);
                    i3--;
                    if (!z3) {
                        this.mAdapter.startUpdate((ViewGroup) this);
                        z3 = true;
                    }
                    this.mAdapter.destroyItem((ViewGroup) this, itemInfo.position, itemInfo.object);
                    if (this.mCurItem == itemInfo.position) {
                        i2 = Math.max(0, Math.min(this.mCurItem, count - 1));
                    }
                } else if (itemInfo.position != itemPosition) {
                    if (itemInfo.position == this.mCurItem) {
                        i2 = itemPosition;
                    }
                    itemInfo.position = itemPosition;
                }
                z2 = true;
            }
            i3++;
        }
        if (z3) {
            this.mAdapter.finishUpdate((ViewGroup) this);
        }
        if (z2) {
            int childCount = getChildCount();
            for (int i4 = 0; i4 < childCount; i4++) {
                LayoutParams layoutParams = (LayoutParams) getChildAt(i4).getLayoutParams();
                if (!layoutParams.isDecor) {
                    layoutParams.widthFactor = 0.0f;
                }
            }
            setCurrentItemInternal(i2, false, true);
            requestLayout();
        }
    }

    /* access modifiers changed from: 0000 */
    public void populate() {
        populate(this.mCurItem);
    }

    /* access modifiers changed from: 0000 */
    public void populate(int i) {
        int i2;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        String str;
        if (this.mCurItem != i) {
            i2 = this.mCurItem < i ? 66 : 17;
            this.mLastItem = this.mCurItem;
            this.mCurItem = i;
        } else {
            i2 = 2;
        }
        if (this.mAdapter == null) {
            sortChildDrawingOrder();
        } else if (this.mPopulatePending) {
            sortChildDrawingOrder();
        } else if (getWindowToken() != null) {
            this.mAdapter.startUpdate((ViewGroup) this);
            int count = this.mAdapter.getCount();
            if (count != this.mExpectedAdapterCount) {
                try {
                    str = getResources().getResourceName(getId());
                } catch (NotFoundException unused) {
                    str = Integer.toHexString(getId());
                }
                StringBuilder sb = new StringBuilder("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: ");
                sb.append(this.mExpectedAdapterCount);
                sb.append(", found: ");
                sb.append(count);
                sb.append(" Pager id: ");
                sb.append(str);
                sb.append(" Pager class: ");
                sb.append(getClass());
                sb.append(" Problematic adapter: ");
                sb.append(this.mAdapter.getClass());
                throw new IllegalStateException(sb.toString());
            }
            int i3 = 0;
            boolean z6 = true;
            while (i3 < this.mItems.size()) {
                ItemInfo itemInfo = this.mItems.get(i3);
                int i4 = this.mCurItem - 2;
                while (true) {
                    if (i4 > this.mCurItem + 2) {
                        z5 = true;
                        break;
                    }
                    if (itemInfo.position == getRealPosition(i4)) {
                        z5 = false;
                        break;
                    }
                    i4++;
                }
                if (z5) {
                    this.mAdapter.destroyItem((ViewGroup) this, itemInfo.position, itemInfo.object);
                    if (this.mUseRecycler) {
                        recycleView((View) itemInfo.object);
                    }
                    this.mItems.remove(itemInfo);
                    i3--;
                    z6 = false;
                }
                i3++;
            }
            for (int i5 = this.mCurItem - 1; i5 >= this.mCurItem - 2; i5--) {
                if (i5 >= 0 || needRecycle()) {
                    int realPosition = getRealPosition(i5);
                    Iterator<ItemInfo> it = this.mItems.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z3 = z6;
                            z4 = false;
                            break;
                        }
                        ItemInfo next = it.next();
                        if (next.position == realPosition) {
                            if (this.mChangeData) {
                                refreshView(next);
                                z4 = true;
                                z3 = false;
                            } else {
                                z3 = z6;
                                z4 = true;
                            }
                        }
                    }
                    if (!z4) {
                        addNewItem(realPosition, 0);
                        z6 = false;
                    } else {
                        z6 = z3;
                    }
                }
            }
            for (int i6 = this.mCurItem; i6 <= this.mCurItem + 2; i6++) {
                int realPosition2 = getRealPosition(i6);
                Iterator<ItemInfo> it2 = this.mItems.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        z = z6;
                        z2 = false;
                        break;
                    }
                    ItemInfo next2 = it2.next();
                    if (next2.position == realPosition2) {
                        if (this.mChangeData) {
                            refreshView(next2);
                            z2 = true;
                            z = false;
                        } else {
                            z = z6;
                            z2 = true;
                        }
                    }
                }
                if (!z2) {
                    addNewItem(realPosition2, -1);
                    z6 = false;
                } else {
                    z6 = z;
                }
            }
            this.mChangeData = false;
            ArrayList arrayList = new ArrayList();
            for (int i7 = this.mCurItem - 2; i7 <= this.mCurItem + 2; i7++) {
                if (i7 >= 0 || needRecycle()) {
                    int realPosition3 = getRealPosition(i7);
                    Iterator<ItemInfo> it3 = this.mItems.iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            break;
                        }
                        ItemInfo next3 = it3.next();
                        if (next3.position == realPosition3) {
                            arrayList.add(next3);
                            break;
                        }
                    }
                }
            }
            this.mItems.clear();
            this.mItems.addAll(arrayList);
            ItemInfo itemInfo2 = null;
            calculatePageOffsets(null, 0, null);
            this.mAdapter.setPrimaryItem((ViewGroup) this, this.mCurItem, (Object) null);
            if (!(!z6 || this.mAdapter == null || this.mAdapter.getRealCount() != 5 || getRight() == 0 || getBottom() == 0)) {
                requestLayout();
            }
            this.mAdapter.finishUpdate((ViewGroup) this);
            int childCount = getChildCount();
            for (int i8 = 0; i8 < childCount; i8++) {
                View childAt = getChildAt(i8);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                layoutParams.childIndex = i8;
                if ((!layoutParams.isDecor && layoutParams.widthFactor == 0.0f) || childCount == 1) {
                    ItemInfo infoForChild = infoForChild(childAt);
                    if (infoForChild != null) {
                        layoutParams.widthFactor = infoForChild.widthFactor;
                        layoutParams.position = infoForChild.position;
                    }
                }
            }
            sortChildDrawingOrder();
            if (hasFocus()) {
                View findFocus = findFocus();
                if (findFocus != null) {
                    itemInfo2 = infoForAnyChild(findFocus);
                }
                if (itemInfo2 == null || itemInfo2.position != this.mCurItem) {
                    for (int i9 = 0; i9 < getChildCount(); i9++) {
                        View childAt2 = getChildAt(i9);
                        ItemInfo infoForChild2 = infoForChild(childAt2);
                        if (infoForChild2 != null && infoForChild2.position == this.mCurItem && childAt2.requestFocus(i2)) {
                            break;
                        }
                    }
                }
            }
            scrollTo(getRate(), 0);
        }
    }

    private int getRealPosition(int i) {
        int count = this.mAdapter.getCount();
        if (i < 0) {
            return i + count;
        }
        return i >= count ? i % count : i;
    }

    private void sortChildDrawingOrder() {
        if (this.mDrawingOrder != 0) {
            if (this.mDrawingOrderedChildren == null) {
                this.mDrawingOrderedChildren = new ArrayList<>();
            } else {
                this.mDrawingOrderedChildren.clear();
            }
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                this.mDrawingOrderedChildren.add(getChildAt(i));
            }
            Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
        }
    }

    private void calculatePageOffsets(ItemInfo itemInfo, int i, ItemInfo itemInfo2) {
        int clientWidth = getClientWidth();
        float f = 0.0f;
        float f2 = clientWidth > 0 ? ((float) this.mPageMargin) / ((float) clientWidth) : 0.0f;
        this.mFirstOffset = -3.4028235E38f;
        this.mLastOffset = Float.MAX_VALUE;
        Iterator<ItemInfo> it = this.mItems.iterator();
        while (it.hasNext()) {
            ItemInfo next = it.next();
            next.offset = f;
            f += next.widthFactor + f2;
        }
    }

    private int getRate() {
        int clientWidth = getClientWidth();
        float f = clientWidth > 0 ? ((float) this.mPageMargin) / ((float) clientWidth) : 0.0f;
        if (this.mItems == null || this.mItems.size() == 0) {
            return 1;
        }
        float f2 = (this.mItems.get(0).widthFactor + f) * 2.0f * ((float) clientWidth);
        if (this.flingRight) {
            return (int) f2;
        }
        return (int) Math.floor((double) (f2 + 0.5f));
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.position = this.mCurItem;
        if (this.mAdapter != null) {
            savedState.adapterState = this.mAdapter.saveState();
        }
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (this.mAdapter != null) {
            this.mAdapter.restoreState(savedState.adapterState, savedState.loader);
            setCurrentItemInternal(savedState.position, false, true);
            return;
        }
        this.mRestoredCurItem = savedState.position;
        this.mRestoredAdapterState = savedState.adapterState;
        this.mRestoredClassLoader = savedState.loader;
    }

    public void addView(View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        if (view != null && layoutParams != null) {
            if (!checkLayoutParams(layoutParams)) {
                layoutParams = generateLayoutParams(layoutParams);
            }
            LayoutParams layoutParams2 = (LayoutParams) layoutParams;
            layoutParams2.isDecor |= view instanceof Decor;
            if (!this.mInLayout) {
                super.addView(view, i, layoutParams);
            } else if (layoutParams2 == null || !layoutParams2.isDecor) {
                layoutParams2.needsMeasure = true;
                addViewInLayout(view, i, layoutParams);
            } else {
                throw new IllegalStateException("Cannot add pager decor view during layout");
            }
        }
    }

    public void removeView(View view) {
        if (this.mInLayout) {
            removeViewInLayout(view);
        } else {
            super.removeView(view);
        }
    }

    /* access modifiers changed from: 0000 */
    public ItemInfo infoForChild(View view) {
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo itemInfo = this.mItems.get(i);
            if (this.mAdapter.isViewFromObject(view, itemInfo.object)) {
                return itemInfo;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public ItemInfo infoForAnyChild(View view) {
        while (true) {
            ViewParent parent = view.getParent();
            if (parent == this) {
                return infoForChild(view);
            }
            if (parent != null && (parent instanceof View)) {
                view = (View) parent;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public ItemInfo infoForPosition(int i) {
        for (int i2 = 0; i2 < this.mItems.size(); i2++) {
            ItemInfo itemInfo = this.mItems.get(i2);
            if (itemInfo.position == i) {
                return itemInfo;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00a5  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00b4  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00ba  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r18, int r19) {
        /*
            r17 = this;
            r0 = r17
            r1 = 0
            r2 = r18
            int r2 = getDefaultSize(r1, r2)
            r3 = r19
            int r3 = getDefaultSize(r1, r3)
            r0.setMeasuredDimension(r2, r3)
            int r2 = r17.getMeasuredWidth()
            int r3 = r2 / 10
            int r4 = r0.mDefaultGutterSize
            int r3 = java.lang.Math.min(r3, r4)
            r0.mGutterSize = r3
            int r3 = r17.getPaddingLeft()
            int r2 = r2 - r3
            int r3 = r17.getPaddingRight()
            int r2 = r2 - r3
            int r3 = r17.getMeasuredHeight()
            int r4 = r17.getPaddingTop()
            int r3 = r3 - r4
            int r4 = r17.getPaddingBottom()
            int r3 = r3 - r4
            int r4 = r17.getChildCount()
            r5 = r3
            r3 = r2
            r2 = 0
        L_0x003f:
            r6 = 8
            r7 = 1
            r8 = 1073741824(0x40000000, float:2.0)
            if (r2 >= r4) goto L_0x00c6
            android.view.View r9 = r0.getChildAt(r2)
            int r10 = r9.getVisibility()
            if (r10 == r6) goto L_0x00c1
            android.view.ViewGroup$LayoutParams r6 = r9.getLayoutParams()
            com.autonavi.map.widget.RecyclableViewPager$LayoutParams r6 = (com.autonavi.map.widget.RecyclableViewPager.LayoutParams) r6
            if (r6 == 0) goto L_0x00c1
            boolean r10 = r6.isDecor
            if (r10 == 0) goto L_0x00c1
            int r10 = r6.gravity
            r10 = r10 & 7
            int r11 = r6.gravity
            r11 = r11 & 112(0x70, float:1.57E-43)
            r12 = 48
            if (r11 == r12) goto L_0x006f
            r12 = 80
            if (r11 != r12) goto L_0x006d
            goto L_0x006f
        L_0x006d:
            r11 = 0
            goto L_0x0070
        L_0x006f:
            r11 = 1
        L_0x0070:
            r12 = 3
            if (r10 == r12) goto L_0x0078
            r12 = 5
            if (r10 != r12) goto L_0x0077
            goto L_0x0078
        L_0x0077:
            r7 = 0
        L_0x0078:
            r10 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r11 == 0) goto L_0x0081
            r10 = 1073741824(0x40000000, float:2.0)
        L_0x007e:
            r12 = -2147483648(0xffffffff80000000, float:-0.0)
            goto L_0x0085
        L_0x0081:
            if (r7 == 0) goto L_0x007e
            r12 = 1073741824(0x40000000, float:2.0)
        L_0x0085:
            int r13 = r6.width
            r14 = -1
            r15 = -2
            if (r13 == r15) goto L_0x0097
            int r10 = r6.width
            if (r10 == r14) goto L_0x0093
            int r10 = r6.width
            r13 = r10
            goto L_0x0094
        L_0x0093:
            r13 = r3
        L_0x0094:
            r10 = 1073741824(0x40000000, float:2.0)
            goto L_0x0098
        L_0x0097:
            r13 = r3
        L_0x0098:
            int r1 = r6.height
            if (r1 == r15) goto L_0x00a5
            int r1 = r6.height
            if (r1 == r14) goto L_0x00a3
            int r1 = r6.height
            goto L_0x00a7
        L_0x00a3:
            r1 = r5
            goto L_0x00a7
        L_0x00a5:
            r1 = r5
            r8 = r12
        L_0x00a7:
            int r6 = android.view.View.MeasureSpec.makeMeasureSpec(r13, r10)
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r8)
            r9.measure(r6, r1)
            if (r11 == 0) goto L_0x00ba
            int r1 = r9.getMeasuredHeight()
            int r5 = r5 - r1
            goto L_0x00c1
        L_0x00ba:
            if (r7 == 0) goto L_0x00c1
            int r1 = r9.getMeasuredWidth()
            int r3 = r3 - r1
        L_0x00c1:
            int r2 = r2 + 1
            r1 = 0
            goto L_0x003f
        L_0x00c6:
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r3, r8)
            r0.mChildWidthMeasureSpec = r1
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r8)
            r0.mChildHeightMeasureSpec = r1
            r0.mInLayout = r7
            r17.populate()
            r1 = 0
            r0.mInLayout = r1
            int r2 = r17.getChildCount()
        L_0x00de:
            if (r1 >= r2) goto L_0x0106
            android.view.View r4 = r0.getChildAt(r1)
            int r5 = r4.getVisibility()
            if (r5 == r6) goto L_0x0103
            android.view.ViewGroup$LayoutParams r5 = r4.getLayoutParams()
            com.autonavi.map.widget.RecyclableViewPager$LayoutParams r5 = (com.autonavi.map.widget.RecyclableViewPager.LayoutParams) r5
            boolean r7 = r5.isDecor
            if (r7 != 0) goto L_0x0103
            float r7 = (float) r3
            float r5 = r5.widthFactor
            float r7 = r7 * r5
            int r5 = (int) r7
            int r5 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r8)
            int r7 = r0.mChildHeightMeasureSpec
            r4.measure(r5, r7)
        L_0x0103:
            int r1 = r1 + 1
            goto L_0x00de
        L_0x0106:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.widget.RecyclableViewPager.onMeasure(int, int):void");
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3) {
            recomputeScrollPosition(i, i3, this.mPageMargin, this.mPageMargin);
        }
    }

    private void recomputeScrollPosition(int i, int i2, int i3, int i4) {
        float f = 0.0f;
        if (i2 <= 0 || this.mItems.isEmpty()) {
            ItemInfo infoForPosition = infoForPosition(this.mCurItem);
            if (infoForPosition != null) {
                f = Math.min(infoForPosition.offset, this.mLastOffset);
            }
            int paddingLeft = (int) (f * ((float) ((i - getPaddingLeft()) - getPaddingRight())));
            if (paddingLeft != getScrollX()) {
                completeScroll(false);
                scrollTo(paddingLeft, getScrollY());
            }
            return;
        }
        int scrollX = (int) ((((float) getScrollX()) / ((float) (((i2 - getPaddingLeft()) - getPaddingRight()) + i4))) * ((float) (((i - getPaddingLeft()) - getPaddingRight()) + i3)));
        scrollTo(scrollX, getScrollY());
        if (!this.mScroller.isFinished()) {
            int duration = this.mScroller.getDuration() - this.mScroller.timePassed();
            ItemInfo infoForPosition2 = infoForPosition(this.mCurItem);
            if (infoForPosition2 != null) {
                f = infoForPosition2.offset * ((float) i);
            }
            this.mScroller.startScroll(scrollX, 0, (int) f, 0, duration);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int childCount = getChildCount();
        int i8 = i3 - i;
        int i9 = i4 - i2;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int scrollX = getScrollX();
        int i10 = paddingBottom;
        int i11 = 0;
        int i12 = paddingTop;
        int i13 = paddingLeft;
        int i14 = 0;
        while (true) {
            i5 = 8;
            if (i14 >= childCount) {
                break;
            }
            View childAt = getChildAt(i14);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isDecor) {
                    int i15 = layoutParams.gravity & 7;
                    int i16 = layoutParams.gravity & 112;
                    if (i15 == 1) {
                        i6 = Math.max((i8 - childAt.getMeasuredWidth()) / 2, i13);
                    } else if (i15 == 3) {
                        i6 = i13;
                        i13 = childAt.getMeasuredWidth() + i13;
                    } else if (i15 != 5) {
                        i6 = i13;
                    } else {
                        i6 = (i8 - paddingRight) - childAt.getMeasuredWidth();
                        paddingRight += childAt.getMeasuredWidth();
                    }
                    if (i16 == 16) {
                        i7 = Math.max((i9 - childAt.getMeasuredHeight()) / 2, i12);
                    } else if (i16 == 48) {
                        i7 = i12;
                        i12 = childAt.getMeasuredHeight() + i12;
                    } else if (i16 != 80) {
                        i7 = i12;
                    } else {
                        i7 = (i9 - i10) - childAt.getMeasuredHeight();
                        i10 += childAt.getMeasuredHeight();
                    }
                    int i17 = i6 + scrollX;
                    childAt.layout(i17, i7, childAt.getMeasuredWidth() + i17, i7 + childAt.getMeasuredHeight());
                    i11++;
                }
            }
            i14++;
        }
        int i18 = (i8 - i13) - paddingRight;
        int i19 = 0;
        while (i19 < childCount) {
            View childAt2 = getChildAt(i19);
            if (childAt2.getVisibility() != i5) {
                LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                if (!layoutParams2.isDecor) {
                    ItemInfo infoForChild = infoForChild(childAt2);
                    if (infoForChild != null) {
                        float f = (float) i18;
                        int floor = ((int) Math.floor((double) ((infoForChild.offset * f) + (((float) getClientWidth()) * ((1.0f - this.mAdapter.getPageWidth(i19)) / 2.0f)) + 0.5f))) + i13;
                        if (layoutParams2.needsMeasure) {
                            layoutParams2.needsMeasure = false;
                            childAt2.measure(MeasureSpec.makeMeasureSpec((int) (f * layoutParams2.widthFactor), UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec((i9 - i12) - i10, UCCore.VERIFY_POLICY_QUICK));
                        }
                        childAt2.layout(floor, i12, childAt2.getMeasuredWidth() + floor, childAt2.getMeasuredHeight() + i12);
                    }
                }
            }
            i19++;
            i5 = 8;
        }
        this.mTopPageBounds = i12;
        this.mBottomPageBounds = i9 - i10;
        this.mDecorChildCount = i11;
        scrollTo(getRate(), 0);
        if (this.mAdapter != null) {
            pageScrolled(getRate());
        }
        this.mFirstLayout = false;
    }

    public void computeScroll() {
        if (this.mScroller.isFinished() || !this.mScroller.computeScrollOffset()) {
            completeScroll(true);
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int currX = this.mScroller.getCurrX();
        int currY = this.mScroller.getCurrY();
        if (!(scrollX == currX && scrollY == currY)) {
            scrollTo(currX, currY);
            if (!pageScrolled(currX)) {
                this.mScroller.abortAnimation();
                scrollTo(0, currY);
            }
        }
        ViewCompat.postInvalidateOnAnimation(this);
    }

    private boolean pageScrolled(int i) {
        if (this.mItems.size() == 0) {
            this.mCalledSuper = false;
            onPageScrolled(0, 0.0f, 0);
            if (this.mCalledSuper) {
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        ItemInfo infoForCurrentScrollPosition = infoForCurrentScrollPosition();
        if (infoForCurrentScrollPosition == null) {
            return false;
        }
        int clientWidth = getClientWidth();
        int i2 = this.mPageMargin + clientWidth;
        float f = (float) clientWidth;
        int i3 = infoForCurrentScrollPosition.position;
        float f2 = ((((float) i) / f) - infoForCurrentScrollPosition.offset) / (infoForCurrentScrollPosition.widthFactor + (((float) this.mPageMargin) / f));
        this.mCalledSuper = false;
        onPageScrolled(i3, f2, (int) (((float) i2) * f2));
        if (this.mCalledSuper) {
            return true;
        }
        throw new IllegalStateException("onPageScrolled did not call superclass implementation");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0065  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onPageScrolled(int r13, float r14, int r15) {
        /*
            r12 = this;
            int r0 = r12.mDecorChildCount
            r1 = 0
            r2 = 1
            if (r0 <= 0) goto L_0x006c
            int r0 = r12.getScrollX()
            int r3 = r12.getPaddingLeft()
            int r4 = r12.getPaddingRight()
            int r5 = r12.getWidth()
            int r6 = r12.getChildCount()
            r7 = r4
            r4 = r3
            r3 = 0
        L_0x001d:
            if (r3 >= r6) goto L_0x006c
            android.view.View r8 = r12.getChildAt(r3)
            android.view.ViewGroup$LayoutParams r9 = r8.getLayoutParams()
            com.autonavi.map.widget.RecyclableViewPager$LayoutParams r9 = (com.autonavi.map.widget.RecyclableViewPager.LayoutParams) r9
            boolean r10 = r9.isDecor
            if (r10 == 0) goto L_0x0069
            int r9 = r9.gravity
            r9 = r9 & 7
            if (r9 == r2) goto L_0x004e
            r10 = 3
            if (r9 == r10) goto L_0x0048
            r10 = 5
            if (r9 == r10) goto L_0x003b
            r9 = r4
            goto L_0x005d
        L_0x003b:
            int r9 = r5 - r7
            int r10 = r8.getMeasuredWidth()
            int r9 = r9 - r10
            int r10 = r8.getMeasuredWidth()
            int r7 = r7 + r10
            goto L_0x005a
        L_0x0048:
            int r9 = r8.getWidth()
            int r9 = r9 + r4
            goto L_0x005d
        L_0x004e:
            int r9 = r8.getMeasuredWidth()
            int r9 = r5 - r9
            int r9 = r9 / 2
            int r9 = java.lang.Math.max(r9, r4)
        L_0x005a:
            r11 = r9
            r9 = r4
            r4 = r11
        L_0x005d:
            int r4 = r4 + r0
            int r10 = r8.getLeft()
            int r4 = r4 - r10
            if (r4 == 0) goto L_0x0068
            r8.offsetLeftAndRight(r4)
        L_0x0068:
            r4 = r9
        L_0x0069:
            int r3 = r3 + 1
            goto L_0x001d
        L_0x006c:
            com.autonavi.map.widget.RecyclableViewPager$OnPageChangeListener r0 = r12.mOnPageChangeListener
            if (r0 == 0) goto L_0x007d
            com.autonavi.map.widget.RecyclableViewPager$OnPageChangeListener r0 = r12.mOnPageChangeListener
            com.autonavi.map.widget.RecyclablePagerAdapter r3 = r12.mAdapter
            int r3 = r3.getRealCount()
            int r3 = r13 % r3
            r0.onPageScrolled(r3, r14, r15)
        L_0x007d:
            com.autonavi.map.widget.RecyclableViewPager$OnPageChangeListener r0 = r12.mInternalPageChangeListener
            if (r0 == 0) goto L_0x008d
            com.autonavi.map.widget.RecyclableViewPager$OnPageChangeListener r0 = r12.mInternalPageChangeListener
            com.autonavi.map.widget.RecyclablePagerAdapter r3 = r12.mAdapter
            int r3 = r3.getRealCount()
            int r13 = r13 % r3
            r0.onPageScrolled(r13, r14, r15)
        L_0x008d:
            com.autonavi.map.widget.RecyclableViewPager$PageTransformer r13 = r12.mPageTransformer
            if (r13 == 0) goto L_0x00bd
            int r13 = r12.getScrollX()
            int r14 = r12.getChildCount()
        L_0x0099:
            if (r1 >= r14) goto L_0x00bd
            android.view.View r15 = r12.getChildAt(r1)
            android.view.ViewGroup$LayoutParams r0 = r15.getLayoutParams()
            com.autonavi.map.widget.RecyclableViewPager$LayoutParams r0 = (com.autonavi.map.widget.RecyclableViewPager.LayoutParams) r0
            boolean r0 = r0.isDecor
            if (r0 != 0) goto L_0x00ba
            int r0 = r15.getLeft()
            int r0 = r0 - r13
            float r0 = (float) r0
            int r3 = r12.getClientWidth()
            float r3 = (float) r3
            float r0 = r0 / r3
            com.autonavi.map.widget.RecyclableViewPager$PageTransformer r3 = r12.mPageTransformer
            r3.transformPage(r15, r0)
        L_0x00ba:
            int r1 = r1 + 1
            goto L_0x0099
        L_0x00bd:
            r12.mCalledSuper = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.widget.RecyclableViewPager.onPageScrolled(int, float, int):void");
    }

    private void completeScroll(boolean z) {
        boolean z2 = this.mScrollState == 2;
        if (z2) {
            setScrollingCacheEnabled(false);
            this.mScroller.abortAnimation();
            int scrollX = getScrollX();
            int scrollY = getScrollY();
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            if (!(scrollX == currX && scrollY == currY)) {
                scrollTo(currX, currY);
            }
        }
        this.mPopulatePending = false;
        boolean z3 = z2;
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo itemInfo = this.mItems.get(i);
            if (itemInfo.scrolling) {
                itemInfo.scrolling = false;
                z3 = true;
            }
        }
        if (z3) {
            if (z) {
                ViewCompat.postOnAnimation(this, this.mEndScrollRunnable);
                return;
            }
            this.mEndScrollRunnable.run();
        }
    }

    private boolean isGutterDrag(float f, float f2) {
        return (f < ((float) this.mGutterSize) && f2 > 0.0f) || (f > ((float) (getWidth() - this.mGutterSize)) && f2 < 0.0f);
    }

    private void enableLayers(boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ViewCompat.setLayerType(getChildAt(i), z ? 2 : 0, null);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0065, code lost:
        if (canScroll(r6, false, (int) r1, (int) r10, (int) r12) == false) goto L_0x0067;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r16) {
        /*
            r15 = this;
            r6 = r15
            r7 = r16
            int r0 = r16.getAction()
            r0 = r0 & 255(0xff, float:3.57E-43)
            r1 = -1
            r8 = 0
            r2 = 3
            if (r0 == r2) goto L_0x0119
            r9 = 1
            if (r0 != r9) goto L_0x0013
            goto L_0x0119
        L_0x0013:
            if (r0 == 0) goto L_0x001f
            boolean r2 = r6.mIsBeingDragged
            if (r2 == 0) goto L_0x001a
            return r9
        L_0x001a:
            boolean r2 = r6.mIsUnableToDrag
            if (r2 == 0) goto L_0x001f
            return r8
        L_0x001f:
            r2 = 2
            if (r0 == 0) goto L_0x00b9
            if (r0 == r2) goto L_0x002e
            r1 = 6
            if (r0 == r1) goto L_0x0029
            goto L_0x0107
        L_0x0029:
            r15.onSecondaryPointerUp(r16)
            goto L_0x0107
        L_0x002e:
            int r0 = r6.mActivePointerId
            if (r0 == r1) goto L_0x0107
            int r0 = android.support.v4.view.MotionEventCompat.findPointerIndex(r7, r0)
            float r10 = android.support.v4.view.MotionEventCompat.getX(r7, r0)
            float r1 = r6.mLastMotionX
            float r1 = r10 - r1
            float r11 = java.lang.Math.abs(r1)
            float r12 = android.support.v4.view.MotionEventCompat.getY(r7, r0)
            float r0 = r6.mInitialMotionY
            float r0 = r12 - r0
            float r13 = java.lang.Math.abs(r0)
            r0 = 0
            int r14 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1))
            if (r14 == 0) goto L_0x0067
            float r0 = r6.mLastMotionX
            boolean r0 = r6.isGutterDrag(r0, r1)
            if (r0 != 0) goto L_0x0067
            r2 = 0
            int r3 = (int) r1
            int r4 = (int) r10
            int r5 = (int) r12
            r0 = r6
            r1 = r6
            boolean r0 = r0.canScroll(r1, r2, r3, r4, r5)
            if (r0 != 0) goto L_0x006d
        L_0x0067:
            boolean r0 = r6.canSelfScroll()
            if (r0 != 0) goto L_0x0074
        L_0x006d:
            r6.mLastMotionX = r10
            r6.mLastMotionY = r12
            r6.mIsUnableToDrag = r9
            return r8
        L_0x0074:
            int r0 = r6.mTouchSlop
            float r0 = (float) r0
            int r0 = (r11 > r0 ? 1 : (r11 == r0 ? 0 : -1))
            if (r0 <= 0) goto L_0x00a2
            r0 = 1056964608(0x3f000000, float:0.5)
            float r11 = r11 * r0
            int r0 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r0 <= 0) goto L_0x00a2
            r6.mIsBeingDragged = r9
            r6.requestParentDisallowInterceptTouchEvent(r9)
            r6.setScrollState(r9)
            if (r14 <= 0) goto L_0x0094
            float r0 = r6.mInitialMotionX
            int r1 = r6.mTouchSlop
            float r1 = (float) r1
            float r0 = r0 + r1
            goto L_0x009a
        L_0x0094:
            float r0 = r6.mInitialMotionX
            int r1 = r6.mTouchSlop
            float r1 = (float) r1
            float r0 = r0 - r1
        L_0x009a:
            r6.mLastMotionX = r0
            r6.mLastMotionY = r12
            r6.setScrollingCacheEnabled(r9)
            goto L_0x00ab
        L_0x00a2:
            int r0 = r6.mTouchSlop
            float r0 = (float) r0
            int r0 = (r13 > r0 ? 1 : (r13 == r0 ? 0 : -1))
            if (r0 <= 0) goto L_0x00ab
            r6.mIsUnableToDrag = r9
        L_0x00ab:
            boolean r0 = r6.mIsBeingDragged
            if (r0 == 0) goto L_0x0107
            boolean r0 = r6.performDrag(r10)
            if (r0 == 0) goto L_0x0107
            android.support.v4.view.ViewCompat.postInvalidateOnAnimation(r6)
            goto L_0x0107
        L_0x00b9:
            float r0 = r16.getX()
            r6.mInitialMotionX = r0
            r6.mLastMotionX = r0
            float r0 = r16.getY()
            r6.mInitialMotionY = r0
            r6.mLastMotionY = r0
            int r0 = android.support.v4.view.MotionEventCompat.getPointerId(r7, r8)
            r6.mActivePointerId = r0
            r6.mIsUnableToDrag = r8
            android.widget.Scroller r0 = r6.mScroller
            r0.computeScrollOffset()
            int r0 = r6.mScrollState
            if (r0 != r2) goto L_0x0102
            android.widget.Scroller r0 = r6.mScroller
            int r0 = r0.getFinalX()
            android.widget.Scroller r1 = r6.mScroller
            int r1 = r1.getCurrX()
            int r0 = r0 - r1
            int r0 = java.lang.Math.abs(r0)
            int r1 = r6.mCloseEnough
            if (r0 <= r1) goto L_0x0102
            android.widget.Scroller r0 = r6.mScroller
            r0.abortAnimation()
            r6.mPopulatePending = r8
            r6.populate()
            r6.mIsBeingDragged = r9
            r6.requestParentDisallowInterceptTouchEvent(r9)
            r6.setScrollState(r9)
            goto L_0x0107
        L_0x0102:
            r6.completeScroll(r8)
            r6.mIsBeingDragged = r8
        L_0x0107:
            android.view.VelocityTracker r0 = r6.mVelocityTracker
            if (r0 != 0) goto L_0x0111
            android.view.VelocityTracker r0 = android.view.VelocityTracker.obtain()
            r6.mVelocityTracker = r0
        L_0x0111:
            android.view.VelocityTracker r0 = r6.mVelocityTracker
            r0.addMovement(r7)
            boolean r0 = r6.mIsBeingDragged
            return r0
        L_0x0119:
            r6.mIsBeingDragged = r8
            r6.mIsUnableToDrag = r8
            r6.mActivePointerId = r1
            android.view.VelocityTracker r0 = r6.mVelocityTracker
            if (r0 == 0) goto L_0x012b
            android.view.VelocityTracker r0 = r6.mVelocityTracker
            r0.recycle()
            r0 = 0
            r6.mVelocityTracker = r0
        L_0x012b:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.widget.RecyclableViewPager.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mFakeDragging) {
            return true;
        }
        boolean z = false;
        if ((motionEvent.getAction() == 0 && motionEvent.getEdgeFlags() != 0) || this.mAdapter == null || this.mAdapter.getCount() == 0) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        switch (motionEvent.getAction() & 255) {
            case 0:
                this.mScroller.abortAnimation();
                this.mPopulatePending = false;
                float x = motionEvent.getX();
                this.mInitialMotionX = x;
                this.mLastMotionX = x;
                float y = motionEvent.getY();
                this.mInitialMotionY = y;
                this.mLastMotionY = y;
                this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                break;
            case 1:
                if (this.mIsBeingDragged && isCanScroll()) {
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                    int xVelocity = (int) VelocityTrackerCompat.getXVelocity(velocityTracker, this.mActivePointerId);
                    this.mPopulatePending = true;
                    int clientWidth = getClientWidth();
                    int scrollX = getScrollX();
                    ItemInfo infoForCurrentScrollPosition = infoForCurrentScrollPosition();
                    if (infoForCurrentScrollPosition != null) {
                        setCurrentItemInternal(determineTargetPage(infoForCurrentScrollPosition.position, ((((float) scrollX) / ((float) clientWidth)) - infoForCurrentScrollPosition.offset) / infoForCurrentScrollPosition.widthFactor, xVelocity, (int) (MotionEventCompat.getX(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId)) - this.mInitialMotionX)), true, true, xVelocity);
                        this.mActivePointerId = -1;
                        endDrag();
                        z = this.mLeftEdge.onRelease() | this.mRightEdge.onRelease();
                        break;
                    }
                }
                break;
            case 2:
                if (!this.mIsBeingDragged) {
                    int findPointerIndex = MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId);
                    float x2 = MotionEventCompat.getX(motionEvent, findPointerIndex);
                    float abs = Math.abs(x2 - this.mLastMotionX);
                    float y2 = MotionEventCompat.getY(motionEvent, findPointerIndex);
                    float abs2 = Math.abs(y2 - this.mLastMotionY);
                    if (abs > ((float) this.mTouchSlop) && abs > abs2 && isCanScroll()) {
                        this.mIsBeingDragged = true;
                        requestParentDisallowInterceptTouchEvent(true);
                        this.mLastMotionX = x2 - this.mInitialMotionX > 0.0f ? this.mInitialMotionX + ((float) this.mTouchSlop) : this.mInitialMotionX - ((float) this.mTouchSlop);
                        this.mLastMotionY = y2;
                        setScrollState(1);
                        setScrollingCacheEnabled(true);
                        ViewParent parent = getParent();
                        if (parent != null) {
                            parent.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
                if (this.mIsBeingDragged && isCanScroll()) {
                    z = false | performDrag(MotionEventCompat.getX(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId)));
                    break;
                }
            case 5:
                int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
                this.mLastMotionX = MotionEventCompat.getX(motionEvent, actionIndex);
                this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, actionIndex);
                break;
            case 6:
                onSecondaryPointerUp(motionEvent);
                this.mLastMotionX = MotionEventCompat.getX(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId));
                break;
        }
        if (z) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        return true;
    }

    private void requestParentDisallowInterceptTouchEvent(boolean z) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(z);
        }
    }

    private boolean performDrag(float f) {
        boolean z;
        this.mLastMotionX = f;
        float scrollX = ((float) getScrollX()) + (this.mLastMotionX - f);
        float clientWidth = (float) getClientWidth();
        float f2 = this.mFirstOffset * clientWidth;
        float f3 = this.mLastOffset * clientWidth;
        boolean z2 = false;
        ItemInfo itemInfo = this.mItems.get(0);
        boolean z3 = true;
        ItemInfo itemInfo2 = this.mItems.get(this.mItems.size() - 1);
        if (itemInfo.position != 0) {
            f2 = itemInfo.offset * clientWidth;
            z = false;
        } else {
            z = true;
        }
        if (itemInfo2.position != this.mAdapter.getCount() - 1) {
            f3 = itemInfo2.offset * clientWidth;
            z3 = false;
        }
        if (scrollX < f2) {
            if (z) {
                z2 = this.mLeftEdge.onPull(Math.abs(f2 - scrollX) / clientWidth);
            }
            scrollX = f2;
        } else if (scrollX > f3) {
            if (z3) {
                z2 = this.mRightEdge.onPull(Math.abs(scrollX - f3) / clientWidth);
            }
            scrollX = f3;
        }
        int i = (int) scrollX;
        this.mLastMotionX += scrollX - ((float) i);
        scrollTo(i, getScrollY());
        pageScrolled(i);
        return z2;
    }

    private ItemInfo infoForCurrentScrollPosition() {
        int clientWidth = getClientWidth();
        float f = 0.0f;
        float scrollX = clientWidth > 0 ? ((float) getScrollX()) / ((float) clientWidth) : 0.0f;
        if (clientWidth > 0) {
            f = ((float) this.mPageMargin) / ((float) clientWidth);
        }
        ItemInfo itemInfo = null;
        boolean z = true;
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo itemInfo2 = this.mItems.get(i);
            if (itemInfo2 != null) {
                float f2 = itemInfo2.offset;
                float f3 = itemInfo2.widthFactor + f2 + f;
                if (!z && scrollX < f2) {
                    return itemInfo;
                }
                if (scrollX < f3 || i == this.mItems.size() - 1) {
                    return itemInfo2;
                }
                itemInfo = itemInfo2;
                z = false;
            }
        }
        return itemInfo;
    }

    private int determineTargetPage(int i, float f, int i2, int i3) {
        if (i3 > 0) {
            this.flingRight = true;
        } else {
            this.flingRight = false;
        }
        if (Math.abs(i3) <= this.mFlingDistance || Math.abs(i2) <= this.mMinimumVelocity) {
            i = (int) (((float) i) + f + (i >= this.mCurItem ? 0.4f : 0.6f));
        } else if (i2 <= 0) {
            i++;
        }
        int count = this.mAdapter.getCount();
        return i >= count ? needRecycle() ? i % count : i : i < 0 ? i + count : i;
    }

    private boolean needRecycle() {
        return this.mAdapter.getCount() >= 5;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        int overScrollMode = ViewCompat.getOverScrollMode(this);
        boolean z = false;
        if (overScrollMode == 0 || (overScrollMode == 1 && this.mAdapter != null && this.mAdapter.getCount() > 1)) {
            if (!this.mLeftEdge.isFinished()) {
                int save = canvas.save();
                int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
                int width = getWidth();
                canvas.rotate(270.0f);
                canvas.translate((float) ((-height) + getPaddingTop()), this.mFirstOffset * ((float) width));
                this.mLeftEdge.setSize(height, width);
                z = false | this.mLeftEdge.draw(canvas);
                canvas.restoreToCount(save);
            }
            if (!this.mRightEdge.isFinished()) {
                int save2 = canvas.save();
                int width2 = getWidth();
                canvas.rotate(90.0f);
                canvas.translate((float) (-getPaddingTop()), (-(this.mLastOffset + 1.0f)) * ((float) width2));
                this.mRightEdge.setSize((getHeight() - getPaddingTop()) - getPaddingBottom(), width2);
                z |= this.mRightEdge.draw(canvas);
                canvas.restoreToCount(save2);
            }
        } else {
            this.mLeftEdge.finish();
            this.mRightEdge.finish();
        }
        if (z) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float f;
        float f2;
        super.onDraw(canvas);
        if (this.mPageMargin > 0 && this.mMarginDrawable != null && this.mItems.size() > 0 && this.mAdapter != null) {
            int scrollX = getScrollX();
            int width = getWidth();
            float f3 = (float) width;
            float f4 = ((float) this.mPageMargin) / f3;
            int i = 0;
            ItemInfo itemInfo = this.mItems.get(0);
            if (itemInfo != null) {
                float f5 = itemInfo.offset;
                int size = this.mItems.size();
                int i2 = itemInfo.position;
                int i3 = this.mItems.get(size - 1).position;
                while (i2 < i3) {
                    while (i2 > itemInfo.position && i < size) {
                        i++;
                        itemInfo = this.mItems.get(i);
                    }
                    if (i2 == itemInfo.position) {
                        f = (itemInfo.offset + itemInfo.widthFactor) * f3;
                        f5 = itemInfo.offset + itemInfo.widthFactor + f4;
                    } else {
                        float pageWidth = this.mAdapter.getPageWidth(i2);
                        f = (f5 + pageWidth) * f3;
                        f5 += pageWidth + f4;
                    }
                    if (((float) this.mPageMargin) + f > ((float) scrollX)) {
                        f2 = f4;
                        this.mMarginDrawable.setBounds((int) f, this.mTopPageBounds, (int) (((float) this.mPageMargin) + f + 0.5f), this.mBottomPageBounds);
                        this.mMarginDrawable.draw(canvas);
                    } else {
                        Canvas canvas2 = canvas;
                        f2 = f4;
                    }
                    if (f > ((float) (scrollX + width))) {
                        break;
                    }
                    i2++;
                    f4 = f2;
                }
            }
        }
    }

    public boolean beginFakeDrag() {
        if (this.mIsBeingDragged) {
            return false;
        }
        this.mFakeDragging = true;
        setScrollState(1);
        this.mLastMotionX = 0.0f;
        this.mInitialMotionX = 0.0f;
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            this.mVelocityTracker.clear();
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 0, 0.0f, 0.0f, 0);
        this.mVelocityTracker.addMovement(obtain);
        obtain.recycle();
        this.mFakeDragBeginTime = uptimeMillis;
        return true;
    }

    public void endFakeDrag() {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
        int xVelocity = (int) VelocityTrackerCompat.getXVelocity(velocityTracker, this.mActivePointerId);
        this.mPopulatePending = true;
        int clientWidth = getClientWidth();
        int scrollX = getScrollX();
        ItemInfo infoForCurrentScrollPosition = infoForCurrentScrollPosition();
        if (infoForCurrentScrollPosition != null) {
            setCurrentItemInternal(determineTargetPage(infoForCurrentScrollPosition.position, ((((float) scrollX) / ((float) clientWidth)) - infoForCurrentScrollPosition.offset) / infoForCurrentScrollPosition.widthFactor, xVelocity, (int) (this.mLastMotionX - this.mInitialMotionX)), true, true, xVelocity);
            endDrag();
            this.mFakeDragging = false;
        }
    }

    public void fakeDragBy(float f) {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        this.mLastMotionX += f;
        float scrollX = ((float) getScrollX()) - f;
        float clientWidth = (float) getClientWidth();
        float f2 = this.mFirstOffset * clientWidth;
        float f3 = this.mLastOffset * clientWidth;
        ItemInfo itemInfo = this.mItems.get(0);
        ItemInfo itemInfo2 = this.mItems.get(this.mItems.size() - 1);
        if (itemInfo.position != 0) {
            f2 = itemInfo.offset * clientWidth;
        }
        if (itemInfo2.position != this.mAdapter.getCount() - 1) {
            f3 = itemInfo2.offset * clientWidth;
        }
        if (scrollX < f2) {
            scrollX = f2;
        } else if (scrollX > f3) {
            scrollX = f3;
        }
        int i = (int) scrollX;
        this.mLastMotionX += scrollX - ((float) i);
        scrollTo(i, getScrollY());
        pageScrolled(i);
        MotionEvent obtain = MotionEvent.obtain(this.mFakeDragBeginTime, SystemClock.uptimeMillis(), 2, this.mLastMotionX, 0.0f, 0);
        this.mVelocityTracker.addMovement(obtain);
        obtain.recycle();
    }

    public boolean isFakeDragging() {
        return this.mFakeDragging;
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
        if (MotionEventCompat.getPointerId(motionEvent, actionIndex) == this.mActivePointerId) {
            int i = actionIndex == 0 ? 1 : 0;
            this.mLastMotionX = MotionEventCompat.getX(motionEvent, i);
            this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, i);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private void endDrag() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void setScrollingCacheEnabled(boolean z) {
        if (this.mScrollingCacheEnabled != z) {
            this.mScrollingCacheEnabled = z;
        }
    }

    public boolean canScrollHorizontally(int i) {
        return this.mAdapter != null;
    }

    /* access modifiers changed from: protected */
    public boolean canSelfScroll() {
        return getAdapter().getCount() != 1;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent);
    }

    public boolean executeKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 61) {
                switch (keyCode) {
                    case 21:
                        return arrowScroll(17);
                    case 22:
                        return arrowScroll(66);
                }
            } else if (VERSION.SDK_INT >= 11) {
                if (KeyEventCompat.hasNoModifiers(keyEvent)) {
                    return arrowScroll(2);
                }
                if (KeyEventCompat.hasModifiers(keyEvent, 1)) {
                    return arrowScroll(1);
                }
            }
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00b0, code lost:
        if (r7 != 2) goto L_0x00bb;
     */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00bd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean arrowScroll(int r7) {
        /*
            r6 = this;
            android.view.View r0 = r6.findFocus()
            r1 = 1
            r2 = 0
            r3 = 0
            if (r0 != r6) goto L_0x000a
            goto L_0x005d
        L_0x000a:
            if (r0 == 0) goto L_0x005c
            android.view.ViewParent r4 = r0.getParent()
        L_0x0010:
            boolean r5 = r4 instanceof android.view.ViewGroup
            if (r5 == 0) goto L_0x001d
            if (r4 != r6) goto L_0x0018
            r4 = 1
            goto L_0x001e
        L_0x0018:
            android.view.ViewParent r4 = r4.getParent()
            goto L_0x0010
        L_0x001d:
            r4 = 0
        L_0x001e:
            if (r4 != 0) goto L_0x005c
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.Class r5 = r0.getClass()
            java.lang.String r5 = r5.getSimpleName()
            r4.append(r5)
            android.view.ViewParent r0 = r0.getParent()
        L_0x0034:
            boolean r5 = r0 instanceof android.view.ViewGroup
            if (r5 == 0) goto L_0x004d
            java.lang.String r5 = " => "
            r4.append(r5)
            java.lang.Class r5 = r0.getClass()
            java.lang.String r5 = r5.getSimpleName()
            r4.append(r5)
            android.view.ViewParent r0 = r0.getParent()
            goto L_0x0034
        L_0x004d:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r5 = "arrowScroll tried to find focus based on non-child current focused view "
            r0.<init>(r5)
            java.lang.String r4 = r4.toString()
            r0.append(r4)
            goto L_0x005d
        L_0x005c:
            r3 = r0
        L_0x005d:
            android.view.FocusFinder r0 = android.view.FocusFinder.getInstance()
            android.view.View r0 = r0.findNextFocus(r6, r3, r7)
            r4 = 66
            r5 = 17
            if (r0 == 0) goto L_0x00a8
            if (r0 == r3) goto L_0x00a8
            if (r7 != r5) goto L_0x008d
            android.graphics.Rect r1 = r6.mTempRect
            android.graphics.Rect r1 = r6.getChildRectInPagerCoordinates(r1, r0)
            int r1 = r1.left
            android.graphics.Rect r2 = r6.mTempRect
            android.graphics.Rect r2 = r6.getChildRectInPagerCoordinates(r2, r3)
            int r2 = r2.left
            if (r3 == 0) goto L_0x0088
            if (r1 < r2) goto L_0x0088
            boolean r2 = r6.pageLeft()
            goto L_0x00bb
        L_0x0088:
            boolean r2 = r0.requestFocus()
            goto L_0x00bb
        L_0x008d:
            if (r7 != r4) goto L_0x00bb
            android.graphics.Rect r1 = r6.mTempRect
            android.graphics.Rect r1 = r6.getChildRectInPagerCoordinates(r1, r0)
            int r1 = r1.left
            android.graphics.Rect r2 = r6.mTempRect
            android.graphics.Rect r2 = r6.getChildRectInPagerCoordinates(r2, r3)
            int r2 = r2.left
            if (r3 == 0) goto L_0x00a3
            if (r1 <= r2) goto L_0x00b2
        L_0x00a3:
            boolean r2 = r0.requestFocus()
            goto L_0x00bb
        L_0x00a8:
            if (r7 == r5) goto L_0x00b7
            if (r7 != r1) goto L_0x00ad
            goto L_0x00b7
        L_0x00ad:
            if (r7 == r4) goto L_0x00b2
            r0 = 2
            if (r7 != r0) goto L_0x00bb
        L_0x00b2:
            boolean r2 = r6.pageRight()
            goto L_0x00bb
        L_0x00b7:
            boolean r2 = r6.pageLeft()
        L_0x00bb:
            if (r2 == 0) goto L_0x00c4
            int r7 = android.view.SoundEffectConstants.getContantForFocusDirection(r7)
            r6.playSoundEffect(r7)
        L_0x00c4:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.widget.RecyclableViewPager.arrowScroll(int):boolean");
    }

    private Rect getChildRectInPagerCoordinates(Rect rect, View view) {
        if (rect == null) {
            rect = new Rect();
        }
        if (view == null) {
            rect.set(0, 0, 0, 0);
            return rect;
        }
        rect.left = view.getLeft();
        rect.right = view.getRight();
        rect.top = view.getTop();
        rect.bottom = view.getBottom();
        ViewParent parent = view.getParent();
        while ((parent instanceof ViewGroup) && parent != this) {
            ViewGroup viewGroup = (ViewGroup) parent;
            rect.left += viewGroup.getLeft();
            rect.right += viewGroup.getRight();
            rect.top += viewGroup.getTop();
            rect.bottom += viewGroup.getBottom();
            parent = viewGroup.getParent();
        }
        return rect;
    }

    /* access modifiers changed from: 0000 */
    public boolean pageLeft() {
        if (this.mCurItem <= 0) {
            return false;
        }
        setCurrentItem(this.mCurItem - 1, true);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean pageRight() {
        if (this.mAdapter == null || this.mCurItem >= this.mAdapter.getCount() - 1) {
            return false;
        }
        setCurrentItem(this.mCurItem + 1, true);
        return true;
    }

    public void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        if (arrayList != null) {
            int size = arrayList.size();
            int descendantFocusability = getDescendantFocusability();
            if (descendantFocusability != 393216) {
                for (int i3 = 0; i3 < getChildCount(); i3++) {
                    View childAt = getChildAt(i3);
                    if (childAt.getVisibility() == 0) {
                        ItemInfo infoForChild = infoForChild(childAt);
                        if (infoForChild != null && infoForChild.position == this.mCurItem) {
                            childAt.addFocusables(arrayList, i, i2);
                        }
                    }
                }
            }
            if ((descendantFocusability == 262144 && size != arrayList.size()) || !isFocusable()) {
                return;
            }
            if (((i2 & 1) != 1 || !isInTouchMode() || isFocusableInTouchMode()) && arrayList != null) {
                arrayList.add(this);
            }
        }
    }

    public void addTouchables(ArrayList<View> arrayList) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0) {
                ItemInfo infoForChild = infoForChild(childAt);
                if (infoForChild != null && infoForChild.position == this.mCurItem) {
                    childAt.addTouchables(arrayList);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean onRequestFocusInDescendants(int i, Rect rect) {
        int i2;
        int i3;
        int childCount = getChildCount();
        int i4 = -1;
        if ((i & 2) != 0) {
            i4 = childCount;
            i3 = 0;
            i2 = 1;
        } else {
            i3 = childCount - 1;
            i2 = -1;
        }
        while (i3 != i4) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() == 0) {
                ItemInfo infoForChild = infoForChild(childAt);
                if (infoForChild != null && infoForChild.position == this.mCurItem && childAt.requestFocus(i, rect)) {
                    return true;
                }
            }
            i3 += i2;
        }
        return false;
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0) {
                ItemInfo infoForChild = infoForChild(childAt);
                if (infoForChild != null && infoForChild.position == this.mCurItem && childAt.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return generateDefaultLayoutParams();
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public void scrollTo(int i, int i2) {
        if (isCanScroll()) {
            super.scrollTo(i, i2);
        }
    }

    public void setCanScroll(boolean z) {
        this.isCanScrollable = z;
    }

    private boolean isCanScroll() {
        return this.isCanScrollable && this.mAdapter != null && this.mAdapter.getRealCount() > 1 && !this.mItems.isEmpty();
    }

    @Nullable
    public View getCurrentItemView() {
        String str = TAG;
        StringBuilder sb = new StringBuilder("getCurrentItemView,mCurItem:");
        sb.append(this.mCurItem);
        sb.append(",mLastItem:");
        sb.append(this.mLastItem);
        sb.append(",mVelocityX:");
        sb.append(this.mVelocityX);
        AMapLog.i(str, sb.toString());
        if (this.mItems == null || this.mItems.size() != 5) {
            return null;
        }
        return (View) this.mItems.get(2).object;
    }

    public View getNextItemView() {
        AMapLog.i(TAG, "getNextItemView");
        if (this.mItems == null || this.mItems.size() != 5) {
            return null;
        }
        if (this.mVelocityX == 0 || this.mLastItem == this.mCurItem) {
            return (View) this.mItems.get(2).object;
        }
        if (this.mVelocityX > 0) {
            this.mVelocityX = 0;
            return (View) this.mItems.get(1).object;
        } else if (this.mVelocityX >= 0) {
            return (View) this.mItems.get(2).object;
        } else {
            this.mVelocityX = 0;
            return (View) this.mItems.get(3).object;
        }
    }

    public boolean isScrollRight() {
        return this.mVelocityX > 0;
    }
}
