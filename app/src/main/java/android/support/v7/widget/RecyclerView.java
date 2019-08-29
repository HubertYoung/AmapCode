package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.os.TraceCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v4.widget.ScrollerCompat;
import android.support.v7.recyclerview.R;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.alipay.sdk.util.h;
import com.uc.webview.export.extension.UCCore;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerView extends ViewGroup implements NestedScrollingChild, ScrollingView {
    private static final boolean DEBUG = false;
    private static final boolean DISPATCH_TEMP_DETACH = false;
    /* access modifiers changed from: private */
    public static final boolean FORCE_INVALIDATE_DISPLAY_LIST = (VERSION.SDK_INT == 18 || VERSION.SDK_INT == 19 || VERSION.SDK_INT == 20);
    public static final int HORIZONTAL = 0;
    private static final int INVALID_POINTER = -1;
    public static final int INVALID_TYPE = -1;
    private static final Class<?>[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = {Context.class, AttributeSet.class, Integer.TYPE, Integer.TYPE};
    private static final int MAX_SCROLL_DURATION = 2000;
    public static final long NO_ID = -1;
    public static final int NO_POSITION = -1;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "RecyclerView";
    public static final int TOUCH_SLOP_DEFAULT = 0;
    public static final int TOUCH_SLOP_PAGING = 1;
    private static final String TRACE_BIND_VIEW_TAG = "RV OnBindView";
    private static final String TRACE_CREATE_VIEW_TAG = "RV CreateView";
    private static final String TRACE_HANDLE_ADAPTER_UPDATES_TAG = "RV PartialInvalidate";
    private static final String TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG = "RV FullInvalidate";
    private static final String TRACE_ON_LAYOUT_TAG = "RV OnLayout";
    private static final String TRACE_SCROLL_TAG = "RV Scroll";
    public static final int VERTICAL = 1;
    /* access modifiers changed from: private */
    public static final Interpolator sQuinticInterpolator = new Interpolator() {
        public final float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    };
    /* access modifiers changed from: private */
    public RecyclerViewAccessibilityDelegate mAccessibilityDelegate;
    private final AccessibilityManager mAccessibilityManager;
    private OnItemTouchListener mActiveOnItemTouchListener;
    /* access modifiers changed from: private */
    public Adapter mAdapter;
    AdapterHelper mAdapterHelper;
    /* access modifiers changed from: private */
    public boolean mAdapterUpdateDuringMeasure;
    private EdgeEffectCompat mBottomGlow;
    private ChildDrawingOrderCallback mChildDrawingOrderCallback;
    ChildHelper mChildHelper;
    /* access modifiers changed from: private */
    public boolean mClipToPadding;
    /* access modifiers changed from: private */
    public boolean mDataSetHasChangedAfterLayout;
    private int mEatRequestLayout;
    private int mEatenAccessibilityChangeFlags;
    /* access modifiers changed from: private */
    public boolean mFirstLayoutComplete;
    /* access modifiers changed from: private */
    public boolean mHasFixedSize;
    private boolean mIgnoreMotionEventTillDown;
    private int mInitialTouchX;
    private int mInitialTouchY;
    /* access modifiers changed from: private */
    public boolean mIsAttached;
    ItemAnimator mItemAnimator;
    private ItemAnimatorListener mItemAnimatorListener;
    private Runnable mItemAnimatorRunner;
    /* access modifiers changed from: private */
    public final ArrayList<ItemDecoration> mItemDecorations;
    boolean mItemsAddedOrRemoved;
    boolean mItemsChanged;
    private int mLastTouchX;
    private int mLastTouchY;
    @VisibleForTesting
    LayoutManager mLayout;
    /* access modifiers changed from: private */
    public boolean mLayoutFrozen;
    private int mLayoutOrScrollCounter;
    /* access modifiers changed from: private */
    public boolean mLayoutRequestEaten;
    private EdgeEffectCompat mLeftGlow;
    private final int mMaxFlingVelocity;
    private final int mMinFlingVelocity;
    private final int[] mMinMaxLayoutPositions;
    private final int[] mNestedOffsets;
    private final RecyclerViewDataObserver mObserver;
    private List<OnChildAttachStateChangeListener> mOnChildAttachStateListeners;
    public final ArrayList<OnItemTouchListener> mOnItemTouchListeners;
    private SavedState mPendingSavedState;
    /* access modifiers changed from: private */
    public final boolean mPostUpdatesOnAnimation;
    /* access modifiers changed from: private */
    public boolean mPostedAnimatorRunner;
    final Recycler mRecycler;
    /* access modifiers changed from: private */
    public RecyclerListener mRecyclerListener;
    private EdgeEffectCompat mRightGlow;
    private final int[] mScrollConsumed;
    private float mScrollFactor;
    private OnScrollListener mScrollListener;
    private List<OnScrollListener> mScrollListeners;
    private final int[] mScrollOffset;
    private int mScrollPointerId;
    private int mScrollState;
    private final NestedScrollingChildHelper mScrollingChildHelper;
    final State mState;
    /* access modifiers changed from: private */
    public final Rect mTempRect;
    private EdgeEffectCompat mTopGlow;
    private int mTouchSlop;
    /* access modifiers changed from: private */
    public final Runnable mUpdateChildViewsRunnable;
    private VelocityTracker mVelocityTracker;
    /* access modifiers changed from: private */
    public final ViewFlinger mViewFlinger;
    private final ProcessCallback mViewInfoProcessCallback;
    final ViewInfoStore mViewInfoStore;

    public static abstract class Adapter<VH extends ViewHolder> {
        private boolean mHasStableIds = false;
        private final AdapterDataObservable mObservable = new AdapterDataObservable();

        public abstract int getItemCount();

        public long getItemId(int i) {
            return -1;
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        }

        public abstract void onBindViewHolder(VH vh, int i);

        public abstract VH onCreateViewHolder(ViewGroup viewGroup, int i);

        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        }

        public boolean onFailedToRecycleView(VH vh) {
            return false;
        }

        public void onViewAttachedToWindow(VH vh) {
        }

        public void onViewDetachedFromWindow(VH vh) {
        }

        public void onViewRecycled(VH vh) {
        }

        public void onBindViewHolder(VH vh, int i, List<Object> list) {
            onBindViewHolder(vh, i);
        }

        public final VH createViewHolder(ViewGroup viewGroup, int i) {
            TraceCompat.beginSection(RecyclerView.TRACE_CREATE_VIEW_TAG);
            VH onCreateViewHolder = onCreateViewHolder(viewGroup, i);
            onCreateViewHolder.mItemViewType = i;
            TraceCompat.endSection();
            return onCreateViewHolder;
        }

        public final void bindViewHolder(VH vh, int i) {
            vh.mPosition = i;
            if (hasStableIds()) {
                vh.mItemId = getItemId(i);
            }
            vh.setFlags(1, 519);
            TraceCompat.beginSection(RecyclerView.TRACE_BIND_VIEW_TAG);
            onBindViewHolder(vh, i, vh.getUnmodifiedPayloads());
            vh.clearPayload();
            TraceCompat.endSection();
        }

        public void setHasStableIds(boolean z) {
            if (hasObservers()) {
                throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
            }
            this.mHasStableIds = z;
        }

        public final boolean hasStableIds() {
            return this.mHasStableIds;
        }

        public final boolean hasObservers() {
            return this.mObservable.a();
        }

        public void registerAdapterDataObserver(AdapterDataObserver adapterDataObserver) {
            this.mObservable.registerObserver(adapterDataObserver);
        }

        public void unregisterAdapterDataObserver(AdapterDataObserver adapterDataObserver) {
            this.mObservable.unregisterObserver(adapterDataObserver);
        }

        public final void notifyDataSetChanged() {
            this.mObservable.b();
        }

        public final void notifyItemChanged(int i) {
            this.mObservable.a(i, 1);
        }

        public final void notifyItemChanged(int i, Object obj) {
            this.mObservable.a(i, 1, obj);
        }

        public final void notifyItemRangeChanged(int i, int i2) {
            this.mObservable.a(i, i2);
        }

        public final void notifyItemRangeChanged(int i, int i2, Object obj) {
            this.mObservable.a(i, i2, obj);
        }

        public final void notifyItemInserted(int i) {
            this.mObservable.b(i, 1);
        }

        public final void notifyItemMoved(int i, int i2) {
            this.mObservable.d(i, i2);
        }

        public final void notifyItemRangeInserted(int i, int i2) {
            this.mObservable.b(i, i2);
        }

        public final void notifyItemRemoved(int i) {
            this.mObservable.c(i, 1);
        }

        public final void notifyItemRangeRemoved(int i, int i2) {
            this.mObservable.c(i, i2);
        }
    }

    static class AdapterDataObservable extends Observable<AdapterDataObserver> {
        AdapterDataObservable() {
        }

        public final boolean a() {
            return !this.mObservers.isEmpty();
        }

        public final void b() {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) this.mObservers.get(size)).a();
            }
        }

        public final void a(int i, int i2) {
            a(i, i2, null);
        }

        public final void a(int i, int i2, Object obj) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) this.mObservers.get(size)).a(i, i2, obj);
            }
        }

        public final void b(int i, int i2) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) this.mObservers.get(size)).a(i, i2);
            }
        }

        public final void c(int i, int i2) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) this.mObservers.get(size)).b(i, i2);
            }
        }

        public final void d(int i, int i2) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) this.mObservers.get(size)).c(i, i2);
            }
        }
    }

    public static abstract class AdapterDataObserver {
        public void a() {
        }

        public void a(int i, int i2) {
        }

        public void a(int i, int i2, Object obj) {
        }

        public void b(int i, int i2) {
        }

        public void c(int i, int i2) {
        }
    }

    public interface ChildDrawingOrderCallback {
        int a(int i, int i2);
    }

    public static abstract class ItemAnimator {
        ItemAnimatorListener a = null;
        long b = 120;
        public long c = 120;
        public long d = 250;
        long e = 250;
        private ArrayList<ItemAnimatorFinishedListener> f = new ArrayList<>();

        @Retention(RetentionPolicy.SOURCE)
        public @interface AdapterChanges {
        }

        public interface ItemAnimatorFinishedListener {
        }

        interface ItemAnimatorListener {
            void a(ViewHolder viewHolder);
        }

        public static class ItemHolderInfo {
            public int a;
            public int b;
            public int c;
            public int d;

            public final ItemHolderInfo a(ViewHolder viewHolder) {
                View view = viewHolder.itemView;
                this.a = view.getLeft();
                this.b = view.getTop();
                this.c = view.getRight();
                this.d = view.getBottom();
                return this;
            }
        }

        public abstract void a();

        public abstract boolean a(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @Nullable ItemHolderInfo itemHolderInfo2);

        public abstract boolean a(@NonNull ViewHolder viewHolder, @NonNull ViewHolder viewHolder2, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2);

        public abstract boolean b();

        public abstract boolean b(@NonNull ViewHolder viewHolder, @Nullable ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2);

        public abstract void c();

        public abstract void c(ViewHolder viewHolder);

        public abstract boolean c(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2);

        public boolean g(@NonNull ViewHolder viewHolder) {
            return true;
        }

        static int e(ViewHolder viewHolder) {
            int access$6500 = viewHolder.mFlags & 14;
            if (viewHolder.isInvalid()) {
                return 4;
            }
            if ((access$6500 & 4) == 0) {
                int oldPosition = viewHolder.getOldPosition();
                int adapterPosition = viewHolder.getAdapterPosition();
                if (!(oldPosition == -1 || adapterPosition == -1 || oldPosition == adapterPosition)) {
                    access$6500 |= 2048;
                }
            }
            return access$6500;
        }

        public final void f(ViewHolder viewHolder) {
            if (this.a != null) {
                this.a.a(viewHolder);
            }
        }

        public boolean a(@NonNull ViewHolder viewHolder, @NonNull List<Object> list) {
            return g(viewHolder);
        }

        public final void d() {
            int size = this.f.size();
            for (int i = 0; i < size; i++) {
                this.f.get(i);
            }
            this.f.clear();
        }

        public static ItemHolderInfo e() {
            return new ItemHolderInfo();
        }

        @NonNull
        public static ItemHolderInfo d(@NonNull ViewHolder viewHolder) {
            return new ItemHolderInfo().a(viewHolder);
        }
    }

    class ItemAnimatorRestoreListener implements ItemAnimatorListener {
        private ItemAnimatorRestoreListener() {
        }

        /* synthetic */ ItemAnimatorRestoreListener(RecyclerView recyclerView, byte b) {
            this();
        }

        public final void a(ViewHolder viewHolder) {
            viewHolder.setIsRecyclable(true);
            if (viewHolder.mShadowedHolder != null && viewHolder.mShadowingHolder == null) {
                viewHolder.mShadowedHolder = null;
            }
            viewHolder.mShadowingHolder = null;
            if (!viewHolder.shouldBeKeptAsChild() && !RecyclerView.this.removeAnimatingView(viewHolder.itemView) && viewHolder.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
            }
        }
    }

    public static abstract class ItemDecoration {
        @Deprecated
        public void onDraw(Canvas canvas, RecyclerView recyclerView) {
        }

        @Deprecated
        public void onDrawOver(Canvas canvas, RecyclerView recyclerView) {
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView, State state) {
            onDraw(canvas, recyclerView);
        }

        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
            onDrawOver(canvas, recyclerView);
        }

        @Deprecated
        public void getItemOffsets(Rect rect, int i, RecyclerView recyclerView) {
            rect.set(0, 0, 0, 0);
        }

        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
            getItemOffsets(rect, ((LayoutParams) view.getLayoutParams()).c.getLayoutPosition(), recyclerView);
        }
    }

    public static abstract class LayoutManager {
        /* access modifiers changed from: private */
        public boolean mAutoMeasure = false;
        ChildHelper mChildHelper;
        private int mHeightSpec;
        boolean mIsAttachedToWindow = false;
        private boolean mMeasurementCacheEnabled = true;
        RecyclerView mRecyclerView;
        /* access modifiers changed from: private */
        public boolean mRequestedSimpleAnimations = false;
        @Nullable
        SmoothScroller mSmoothScroller;
        private int mWidthSpec;

        public static class Properties {
            public int a;
            public int b;
            public boolean c;
            public boolean d;
        }

        public boolean canScrollHorizontally() {
            return false;
        }

        public boolean canScrollVertically() {
            return false;
        }

        public boolean checkLayoutParams(LayoutParams layoutParams) {
            return layoutParams != null;
        }

        public int computeHorizontalScrollExtent(State state) {
            return 0;
        }

        public int computeHorizontalScrollOffset(State state) {
            return 0;
        }

        public int computeHorizontalScrollRange(State state) {
            return 0;
        }

        public int computeVerticalScrollExtent(State state) {
            return 0;
        }

        public int computeVerticalScrollOffset(State state) {
            return 0;
        }

        public int computeVerticalScrollRange(State state) {
            return 0;
        }

        public abstract LayoutParams generateDefaultLayoutParams();

        public int getBaseline() {
            return -1;
        }

        public int getSelectionModeForAccessibility(Recycler recycler, State state) {
            return 0;
        }

        public boolean isLayoutHierarchical(Recycler recycler, State state) {
            return false;
        }

        public void onAdapterChanged(Adapter adapter, Adapter adapter2) {
        }

        public boolean onAddFocusables(RecyclerView recyclerView, ArrayList<View> arrayList, int i, int i2) {
            return false;
        }

        @CallSuper
        public void onAttachedToWindow(RecyclerView recyclerView) {
        }

        @Deprecated
        public void onDetachedFromWindow(RecyclerView recyclerView) {
        }

        @Nullable
        public View onFocusSearchFailed(View view, int i, Recycler recycler, State state) {
            return null;
        }

        public View onInterceptFocusSearch(View view, int i) {
            return null;
        }

        public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        }

        public void onItemsChanged(RecyclerView recyclerView) {
        }

        public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
        }

        public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        }

        public void onItemsUpdated(RecyclerView recyclerView, int i, int i2) {
        }

        public void onLayoutChildren(Recycler recycler, State state) {
        }

        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        public Parcelable onSaveInstanceState() {
            return null;
        }

        public void onScrollStateChanged(int i) {
        }

        public boolean performAccessibilityActionForItem(Recycler recycler, State state, View view, int i, Bundle bundle) {
            return false;
        }

        public int scrollHorizontallyBy(int i, Recycler recycler, State state) {
            return 0;
        }

        public void scrollToPosition(int i) {
        }

        public int scrollVerticallyBy(int i, Recycler recycler, State state) {
            return 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean shouldMeasureTwice() {
            return false;
        }

        public void smoothScrollToPosition(RecyclerView recyclerView, State state, int i) {
        }

        public boolean supportsPredictiveItemAnimations() {
            return false;
        }

        /* access modifiers changed from: 0000 */
        public void setRecyclerView(RecyclerView recyclerView) {
            if (recyclerView == null) {
                this.mRecyclerView = null;
                this.mChildHelper = null;
                this.mWidthSpec = MeasureSpec.makeMeasureSpec(0, UCCore.VERIFY_POLICY_QUICK);
                this.mHeightSpec = MeasureSpec.makeMeasureSpec(0, UCCore.VERIFY_POLICY_QUICK);
                return;
            }
            this.mRecyclerView = recyclerView;
            this.mChildHelper = recyclerView.mChildHelper;
            this.mWidthSpec = MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), UCCore.VERIFY_POLICY_QUICK);
            this.mHeightSpec = MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), UCCore.VERIFY_POLICY_QUICK);
        }

        /* access modifiers changed from: 0000 */
        public void setMeasureSpecs(int i, int i2) {
            this.mWidthSpec = i;
            this.mHeightSpec = i2;
        }

        /* access modifiers changed from: 0000 */
        public void setMeasuredDimensionFromChildren(int i, int i2) {
            int childCount = getChildCount();
            if (childCount == 0) {
                this.mRecyclerView.defaultOnMeasure(i, i2);
                return;
            }
            int i3 = Integer.MAX_VALUE;
            int i4 = Integer.MAX_VALUE;
            int i5 = Integer.MIN_VALUE;
            int i6 = Integer.MIN_VALUE;
            for (int i7 = 0; i7 < childCount; i7++) {
                View childAt = getChildAt(i7);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int decoratedLeft = getDecoratedLeft(childAt) - layoutParams.leftMargin;
                int decoratedRight = getDecoratedRight(childAt) + layoutParams.rightMargin;
                int decoratedTop = getDecoratedTop(childAt) - layoutParams.topMargin;
                int decoratedBottom = getDecoratedBottom(childAt) + layoutParams.bottomMargin;
                if (decoratedLeft < i3) {
                    i3 = decoratedLeft;
                }
                if (decoratedRight > i5) {
                    i5 = decoratedRight;
                }
                if (decoratedTop < i4) {
                    i4 = decoratedTop;
                }
                if (decoratedBottom > i6) {
                    i6 = decoratedBottom;
                }
            }
            this.mRecyclerView.mTempRect.set(i3, i4, i5, i6);
            setMeasuredDimension(this.mRecyclerView.mTempRect, i, i2);
        }

        public void setMeasuredDimension(Rect rect, int i, int i2) {
            setMeasuredDimension(chooseSize(i, rect.width() + getPaddingLeft() + getPaddingRight(), getMinimumWidth()), chooseSize(i2, rect.height() + getPaddingTop() + getPaddingBottom(), getMinimumHeight()));
        }

        public void requestLayout() {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.requestLayout();
            }
        }

        public void assertInLayoutOrScroll(String str) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.assertInLayoutOrScroll(str);
            }
        }

        public static int chooseSize(int i, int i2, int i3) {
            int mode = MeasureSpec.getMode(i);
            int size = MeasureSpec.getSize(i);
            if (mode != Integer.MIN_VALUE) {
                return mode != 1073741824 ? Math.max(i2, i3) : size;
            }
            return Math.min(size, Math.max(i2, i3));
        }

        public void assertNotInLayoutOrScroll(String str) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.assertNotInLayoutOrScroll(str);
            }
        }

        public void setAutoMeasureEnabled(boolean z) {
            this.mAutoMeasure = z;
        }

        public boolean isAutoMeasureEnabled() {
            return this.mAutoMeasure;
        }

        /* access modifiers changed from: 0000 */
        public void dispatchAttachedToWindow(RecyclerView recyclerView) {
            this.mIsAttachedToWindow = true;
            onAttachedToWindow(recyclerView);
        }

        /* access modifiers changed from: 0000 */
        public void dispatchDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
            this.mIsAttachedToWindow = false;
            onDetachedFromWindow(recyclerView, recycler);
        }

        public boolean isAttachedToWindow() {
            return this.mIsAttachedToWindow;
        }

        public void postOnAnimation(Runnable runnable) {
            if (this.mRecyclerView != null) {
                ViewCompat.postOnAnimation(this.mRecyclerView, runnable);
            }
        }

        public boolean removeCallbacks(Runnable runnable) {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.removeCallbacks(runnable);
            }
            return false;
        }

        @CallSuper
        public void onDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
            onDetachedFromWindow(recyclerView);
        }

        public boolean getClipToPadding() {
            return this.mRecyclerView != null && this.mRecyclerView.mClipToPadding;
        }

        public LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            if (layoutParams instanceof LayoutParams) {
                return new LayoutParams((LayoutParams) layoutParams);
            }
            if (layoutParams instanceof MarginLayoutParams) {
                return new LayoutParams((MarginLayoutParams) layoutParams);
            }
            return new LayoutParams(layoutParams);
        }

        public LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
            return new LayoutParams(context, attributeSet);
        }

        public void startSmoothScroll(SmoothScroller smoothScroller) {
            if (!(this.mSmoothScroller == null || smoothScroller == this.mSmoothScroller || !this.mSmoothScroller.k)) {
                this.mSmoothScroller.b();
            }
            this.mSmoothScroller = smoothScroller;
            SmoothScroller smoothScroller2 = this.mSmoothScroller;
            smoothScroller2.h = this.mRecyclerView;
            smoothScroller2.i = this;
            if (smoothScroller2.g == -1) {
                throw new IllegalArgumentException("Invalid target position");
            }
            smoothScroller2.h.mState.a = smoothScroller2.g;
            smoothScroller2.k = true;
            smoothScroller2.j = true;
            smoothScroller2.l = smoothScroller2.h.mLayout.findViewByPosition(smoothScroller2.g);
            smoothScroller2.h.mViewFlinger.a();
        }

        public boolean isSmoothScrolling() {
            return this.mSmoothScroller != null && this.mSmoothScroller.k;
        }

        public int getLayoutDirection() {
            return ViewCompat.getLayoutDirection(this.mRecyclerView);
        }

        public void endAnimation(View view) {
            if (this.mRecyclerView.mItemAnimator != null) {
                this.mRecyclerView.mItemAnimator.c(RecyclerView.getChildViewHolderInt(view));
            }
        }

        public void addDisappearingView(View view) {
            addDisappearingView(view, -1);
        }

        public void addDisappearingView(View view, int i) {
            addViewInt(view, i, true);
        }

        public void addView(View view) {
            addView(view, -1);
        }

        public void addView(View view, int i) {
            addViewInt(view, i, false);
        }

        private void addViewInt(View view, int i, boolean z) {
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (z || childViewHolderInt.isRemoved()) {
                this.mRecyclerView.mViewInfoStore.b(childViewHolderInt);
            } else {
                this.mRecyclerView.mViewInfoStore.c(childViewHolderInt);
            }
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (childViewHolderInt.wasReturnedFromScrap() || childViewHolderInt.isScrap()) {
                if (childViewHolderInt.isScrap()) {
                    childViewHolderInt.unScrap();
                } else {
                    childViewHolderInt.clearReturnedFromScrapFlag();
                }
                this.mChildHelper.a(view, i, view.getLayoutParams(), false);
            } else if (view.getParent() == this.mRecyclerView) {
                int c = this.mChildHelper.c(view);
                if (i == -1) {
                    i = this.mChildHelper.a();
                }
                if (c == -1) {
                    StringBuilder sb = new StringBuilder("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:");
                    sb.append(this.mRecyclerView.indexOfChild(view));
                    throw new IllegalStateException(sb.toString());
                } else if (c != i) {
                    this.mRecyclerView.mLayout.moveView(c, i);
                }
            } else {
                this.mChildHelper.a(view, i, false);
                layoutParams.e = true;
                if (this.mSmoothScroller != null && this.mSmoothScroller.k) {
                    SmoothScroller smoothScroller = this.mSmoothScroller;
                    if (smoothScroller.a(view) == smoothScroller.g) {
                        smoothScroller.l = view;
                    }
                }
            }
            if (layoutParams.f) {
                childViewHolderInt.itemView.invalidate();
                layoutParams.f = false;
            }
        }

        public void removeView(View view) {
            ChildHelper childHelper = this.mChildHelper;
            int a = childHelper.a.a(view);
            if (a >= 0) {
                if (childHelper.b.d(a)) {
                    childHelper.b(view);
                }
                childHelper.a.a(a);
            }
        }

        public void removeViewAt(int i) {
            if (getChildAt(i) != null) {
                this.mChildHelper.a(i);
            }
        }

        public void removeAllViews() {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                this.mChildHelper.a(childCount);
            }
        }

        public int getPosition(View view) {
            return ((LayoutParams) view.getLayoutParams()).c.getLayoutPosition();
        }

        public int getItemViewType(View view) {
            return RecyclerView.getChildViewHolderInt(view).getItemViewType();
        }

        @Nullable
        public View findContainingItemView(View view) {
            if (this.mRecyclerView == null) {
                return null;
            }
            View findContainingItemView = this.mRecyclerView.findContainingItemView(view);
            if (findContainingItemView != null && !this.mChildHelper.d(findContainingItemView)) {
                return findContainingItemView;
            }
            return null;
        }

        public View findViewByPosition(int i) {
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(childAt);
                if (childViewHolderInt != null && childViewHolderInt.getLayoutPosition() == i && !childViewHolderInt.shouldIgnore() && (this.mRecyclerView.mState.c || !childViewHolderInt.isRemoved())) {
                    return childAt;
                }
            }
            return null;
        }

        public void detachView(View view) {
            int c = this.mChildHelper.c(view);
            if (c >= 0) {
                detachViewInternal(c, view);
            }
        }

        public void detachViewAt(int i) {
            detachViewInternal(i, getChildAt(i));
        }

        private void detachViewInternal(int i, View view) {
            this.mChildHelper.d(i);
        }

        public void attachView(View view, int i, LayoutParams layoutParams) {
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (childViewHolderInt.isRemoved()) {
                this.mRecyclerView.mViewInfoStore.b(childViewHolderInt);
            } else {
                this.mRecyclerView.mViewInfoStore.c(childViewHolderInt);
            }
            this.mChildHelper.a(view, i, layoutParams, childViewHolderInt.isRemoved());
        }

        public void attachView(View view, int i) {
            attachView(view, i, (LayoutParams) view.getLayoutParams());
        }

        public void attachView(View view) {
            attachView(view, -1);
        }

        public void removeDetachedView(View view) {
            this.mRecyclerView.removeDetachedView(view, false);
        }

        public void moveView(int i, int i2) {
            View childAt = getChildAt(i);
            if (childAt == null) {
                throw new IllegalArgumentException("Cannot move a child from non-existing index:".concat(String.valueOf(i)));
            }
            detachViewAt(i);
            attachView(childAt, i2);
        }

        public void detachAndScrapView(View view, Recycler recycler) {
            scrapOrRecycleView(recycler, this.mChildHelper.c(view), view);
        }

        public void detachAndScrapViewAt(int i, Recycler recycler) {
            scrapOrRecycleView(recycler, i, getChildAt(i));
        }

        public void removeAndRecycleView(View view, Recycler recycler) {
            removeView(view);
            recycler.a(view);
        }

        public void removeAndRecycleViewAt(int i, Recycler recycler) {
            View childAt = getChildAt(i);
            removeViewAt(i);
            recycler.a(childAt);
        }

        public int getChildCount() {
            if (this.mChildHelper != null) {
                return this.mChildHelper.a();
            }
            return 0;
        }

        public View getChildAt(int i) {
            if (this.mChildHelper != null) {
                return this.mChildHelper.b(i);
            }
            return null;
        }

        public int getWidthMode() {
            return MeasureSpec.getMode(this.mWidthSpec);
        }

        public int getHeightMode() {
            return MeasureSpec.getMode(this.mHeightSpec);
        }

        public int getWidth() {
            return MeasureSpec.getSize(this.mWidthSpec);
        }

        public int getHeight() {
            return MeasureSpec.getSize(this.mHeightSpec);
        }

        public int getPaddingLeft() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingLeft();
            }
            return 0;
        }

        public int getPaddingTop() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingTop();
            }
            return 0;
        }

        public int getPaddingRight() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingRight();
            }
            return 0;
        }

        public int getPaddingBottom() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingBottom();
            }
            return 0;
        }

        public int getPaddingStart() {
            if (this.mRecyclerView != null) {
                return ViewCompat.getPaddingStart(this.mRecyclerView);
            }
            return 0;
        }

        public int getPaddingEnd() {
            if (this.mRecyclerView != null) {
                return ViewCompat.getPaddingEnd(this.mRecyclerView);
            }
            return 0;
        }

        public boolean isFocused() {
            return this.mRecyclerView != null && this.mRecyclerView.isFocused();
        }

        public boolean hasFocus() {
            return this.mRecyclerView != null && this.mRecyclerView.hasFocus();
        }

        public View getFocusedChild() {
            if (this.mRecyclerView == null) {
                return null;
            }
            View focusedChild = this.mRecyclerView.getFocusedChild();
            if (focusedChild == null || this.mChildHelper.d(focusedChild)) {
                return null;
            }
            return focusedChild;
        }

        public int getItemCount() {
            Adapter adapter = this.mRecyclerView != null ? this.mRecyclerView.getAdapter() : null;
            if (adapter != null) {
                return adapter.getItemCount();
            }
            return 0;
        }

        public void offsetChildrenHorizontal(int i) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.offsetChildrenHorizontal(i);
            }
        }

        public void offsetChildrenVertical(int i) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.offsetChildrenVertical(i);
            }
        }

        public void ignoreView(View view) {
            if (view.getParent() != this.mRecyclerView || this.mRecyclerView.indexOfChild(view) == -1) {
                throw new IllegalArgumentException("View should be fully attached to be ignored");
            }
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            childViewHolderInt.addFlags(128);
            this.mRecyclerView.mViewInfoStore.d(childViewHolderInt);
        }

        public void stopIgnoringView(View view) {
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            childViewHolderInt.stopIgnoring();
            childViewHolderInt.resetInternal();
            childViewHolderInt.addFlags(4);
        }

        public void detachAndScrapAttachedViews(Recycler recycler) {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                scrapOrRecycleView(recycler, childCount, getChildAt(childCount));
            }
        }

        private void scrapOrRecycleView(Recycler recycler, int i, View view) {
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (!childViewHolderInt.shouldIgnore()) {
                if (!childViewHolderInt.isInvalid() || childViewHolderInt.isRemoved() || this.mRecyclerView.mAdapter.hasStableIds()) {
                    detachViewAt(i);
                    recycler.c(view);
                    this.mRecyclerView.mViewInfoStore.c(childViewHolderInt);
                    return;
                }
                removeViewAt(i);
                recycler.a(childViewHolderInt);
            }
        }

        public void measureChild(View view, int i, int i2) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Rect itemDecorInsetsForChild = this.mRecyclerView.getItemDecorInsetsForChild(view);
            int childMeasureSpec = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + i + itemDecorInsetsForChild.left + itemDecorInsetsForChild.right, layoutParams.width, canScrollHorizontally());
            int childMeasureSpec2 = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + i2 + itemDecorInsetsForChild.top + itemDecorInsetsForChild.bottom, layoutParams.height, canScrollVertically());
            if (shouldMeasureChild(view, childMeasureSpec, childMeasureSpec2, layoutParams)) {
                view.measure(childMeasureSpec, childMeasureSpec2);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean shouldReMeasureChild(View view, int i, int i2, LayoutParams layoutParams) {
            return !this.mMeasurementCacheEnabled || !isMeasurementUpToDate(view.getMeasuredWidth(), i, layoutParams.width) || !isMeasurementUpToDate(view.getMeasuredHeight(), i2, layoutParams.height);
        }

        /* access modifiers changed from: 0000 */
        public boolean shouldMeasureChild(View view, int i, int i2, LayoutParams layoutParams) {
            return view.isLayoutRequested() || !this.mMeasurementCacheEnabled || !isMeasurementUpToDate(view.getWidth(), i, layoutParams.width) || !isMeasurementUpToDate(view.getHeight(), i2, layoutParams.height);
        }

        public boolean isMeasurementCacheEnabled() {
            return this.mMeasurementCacheEnabled;
        }

        public void setMeasurementCacheEnabled(boolean z) {
            this.mMeasurementCacheEnabled = z;
        }

        private static boolean isMeasurementUpToDate(int i, int i2, int i3) {
            int mode = MeasureSpec.getMode(i2);
            int size = MeasureSpec.getSize(i2);
            if (i3 > 0 && i != i3) {
                return false;
            }
            if (mode == Integer.MIN_VALUE) {
                return size >= i;
            }
            if (mode != 0) {
                return mode == 1073741824 && size == i;
            }
            return true;
        }

        public void measureChildWithMargins(View view, int i, int i2) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Rect itemDecorInsetsForChild = this.mRecyclerView.getItemDecorInsetsForChild(view);
            int childMeasureSpec = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin + i + itemDecorInsetsForChild.left + itemDecorInsetsForChild.right, layoutParams.width, canScrollHorizontally());
            int childMeasureSpec2 = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + layoutParams.topMargin + layoutParams.bottomMargin + i2 + itemDecorInsetsForChild.top + itemDecorInsetsForChild.bottom, layoutParams.height, canScrollVertically());
            if (shouldMeasureChild(view, childMeasureSpec, childMeasureSpec2, layoutParams)) {
                view.measure(childMeasureSpec, childMeasureSpec2);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:2:0x000a, code lost:
            if (r3 >= 0) goto L_0x000c;
         */
        @java.lang.Deprecated
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static int getChildMeasureSpec(int r1, int r2, int r3, boolean r4) {
            /*
                int r1 = r1 - r2
                r2 = 0
                int r1 = java.lang.Math.max(r2, r1)
                r0 = 1073741824(0x40000000, float:2.0)
                if (r4 == 0) goto L_0x0012
                if (r3 < 0) goto L_0x0010
            L_0x000c:
                r1 = r3
            L_0x000d:
                r2 = 1073741824(0x40000000, float:2.0)
                goto L_0x001e
            L_0x0010:
                r1 = 0
                goto L_0x001e
            L_0x0012:
                if (r3 < 0) goto L_0x0015
                goto L_0x000c
            L_0x0015:
                r4 = -1
                if (r3 != r4) goto L_0x0019
                goto L_0x000d
            L_0x0019:
                r4 = -2
                if (r3 != r4) goto L_0x0010
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
            L_0x001e:
                int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r2)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.LayoutManager.getChildMeasureSpec(int, int, int, boolean):int");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:7:0x0019, code lost:
            if (r5 == 1073741824) goto L_0x0021;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static int getChildMeasureSpec(int r4, int r5, int r6, int r7, boolean r8) {
            /*
                int r4 = r4 - r6
                r6 = 0
                int r4 = java.lang.Math.max(r6, r4)
                r0 = -2
                r1 = -1
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = 1073741824(0x40000000, float:2.0)
                if (r8 == 0) goto L_0x001c
                if (r7 < 0) goto L_0x0013
            L_0x0010:
                r6 = 1073741824(0x40000000, float:2.0)
                goto L_0x0032
            L_0x0013:
                if (r7 != r1) goto L_0x0031
                if (r5 == r2) goto L_0x0021
                if (r5 == 0) goto L_0x0031
                if (r5 == r3) goto L_0x0021
                goto L_0x0031
            L_0x001c:
                if (r7 < 0) goto L_0x001f
                goto L_0x0010
            L_0x001f:
                if (r7 != r1) goto L_0x0024
            L_0x0021:
                r7 = r4
                r6 = r5
                goto L_0x0032
            L_0x0024:
                if (r7 != r0) goto L_0x0031
                if (r5 == r2) goto L_0x002d
                if (r5 != r3) goto L_0x002b
                goto L_0x002d
            L_0x002b:
                r7 = r4
                goto L_0x0032
            L_0x002d:
                r7 = r4
                r6 = -2147483648(0xffffffff80000000, float:-0.0)
                goto L_0x0032
            L_0x0031:
                r7 = 0
            L_0x0032:
                int r4 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r6)
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.LayoutManager.getChildMeasureSpec(int, int, int, int, boolean):int");
        }

        public int getDecoratedMeasuredWidth(View view) {
            Rect rect = ((LayoutParams) view.getLayoutParams()).d;
            return view.getMeasuredWidth() + rect.left + rect.right;
        }

        public int getDecoratedMeasuredHeight(View view) {
            Rect rect = ((LayoutParams) view.getLayoutParams()).d;
            return view.getMeasuredHeight() + rect.top + rect.bottom;
        }

        public void layoutDecorated(View view, int i, int i2, int i3, int i4) {
            Rect rect = ((LayoutParams) view.getLayoutParams()).d;
            view.layout(i + rect.left, i2 + rect.top, i3 - rect.right, i4 - rect.bottom);
        }

        public int getDecoratedLeft(View view) {
            return view.getLeft() - getLeftDecorationWidth(view);
        }

        public int getDecoratedTop(View view) {
            return view.getTop() - getTopDecorationHeight(view);
        }

        public int getDecoratedRight(View view) {
            return view.getRight() + getRightDecorationWidth(view);
        }

        public int getDecoratedBottom(View view) {
            return view.getBottom() + getBottomDecorationHeight(view);
        }

        public void calculateItemDecorationsForChild(View view, Rect rect) {
            if (this.mRecyclerView == null) {
                rect.set(0, 0, 0, 0);
            } else {
                rect.set(this.mRecyclerView.getItemDecorInsetsForChild(view));
            }
        }

        public int getTopDecorationHeight(View view) {
            return ((LayoutParams) view.getLayoutParams()).d.top;
        }

        public int getBottomDecorationHeight(View view) {
            return ((LayoutParams) view.getLayoutParams()).d.bottom;
        }

        public int getLeftDecorationWidth(View view) {
            return ((LayoutParams) view.getLayoutParams()).d.left;
        }

        public int getRightDecorationWidth(View view) {
            return ((LayoutParams) view.getLayoutParams()).d.right;
        }

        public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z) {
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int left = (view.getLeft() + rect.left) - view.getScrollX();
            int top = (view.getTop() + rect.top) - view.getScrollY();
            int width = rect.width() + left;
            int height = rect.height() + top;
            int i = left - paddingLeft;
            int min = Math.min(0, i);
            int i2 = top - paddingTop;
            int min2 = Math.min(0, i2);
            int width2 = width - (getWidth() - getPaddingRight());
            int max = Math.max(0, width2);
            int max2 = Math.max(0, height - (getHeight() - getPaddingBottom()));
            if (getLayoutDirection() != 1) {
                if (min == 0) {
                    min = Math.min(i, max);
                }
                max = min;
            } else if (max == 0) {
                max = Math.max(min, width2);
            }
            if (min2 == 0) {
                min2 = Math.min(i2, max2);
            }
            if (max == 0 && min2 == 0) {
                return false;
            }
            if (z) {
                recyclerView.scrollBy(max, min2);
            } else {
                recyclerView.smoothScrollBy(max, min2);
            }
            return true;
        }

        @Deprecated
        public boolean onRequestChildFocus(RecyclerView recyclerView, View view, View view2) {
            return isSmoothScrolling() || recyclerView.isComputingLayout();
        }

        public boolean onRequestChildFocus(RecyclerView recyclerView, State state, View view, View view2) {
            return onRequestChildFocus(recyclerView, view, view2);
        }

        public void onItemsUpdated(RecyclerView recyclerView, int i, int i2, Object obj) {
            onItemsUpdated(recyclerView, i, i2);
        }

        public void onMeasure(Recycler recycler, State state, int i, int i2) {
            this.mRecyclerView.defaultOnMeasure(i, i2);
        }

        public void setMeasuredDimension(int i, int i2) {
            this.mRecyclerView.setMeasuredDimension(i, i2);
        }

        public int getMinimumWidth() {
            return ViewCompat.getMinimumWidth(this.mRecyclerView);
        }

        public int getMinimumHeight() {
            return ViewCompat.getMinimumHeight(this.mRecyclerView);
        }

        /* access modifiers changed from: 0000 */
        public void stopSmoothScroller() {
            if (this.mSmoothScroller != null) {
                this.mSmoothScroller.b();
            }
        }

        /* access modifiers changed from: private */
        public void onSmoothScrollerStopped(SmoothScroller smoothScroller) {
            if (this.mSmoothScroller == smoothScroller) {
                this.mSmoothScroller = null;
            }
        }

        public void removeAndRecycleAllViews(Recycler recycler) {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                if (!RecyclerView.getChildViewHolderInt(getChildAt(childCount)).shouldIgnore()) {
                    removeAndRecycleViewAt(childCount, recycler);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            onInitializeAccessibilityNodeInfo(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, accessibilityNodeInfoCompat);
        }

        public void onInitializeAccessibilityNodeInfo(Recycler recycler, State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (ViewCompat.canScrollVertically(this.mRecyclerView, -1) || ViewCompat.canScrollHorizontally(this.mRecyclerView, -1)) {
                accessibilityNodeInfoCompat.addAction(8192);
                accessibilityNodeInfoCompat.setScrollable(true);
            }
            if (ViewCompat.canScrollVertically(this.mRecyclerView, 1) || ViewCompat.canScrollHorizontally(this.mRecyclerView, 1)) {
                accessibilityNodeInfoCompat.addAction(4096);
                accessibilityNodeInfoCompat.setScrollable(true);
            }
            accessibilityNodeInfoCompat.setCollectionInfo(CollectionInfoCompat.obtain(getRowCountForAccessibility(recycler, state), getColumnCountForAccessibility(recycler, state), isLayoutHierarchical(recycler, state), getSelectionModeForAccessibility(recycler, state)));
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            onInitializeAccessibilityEvent(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, accessibilityEvent);
        }

        public void onInitializeAccessibilityEvent(Recycler recycler, State state, AccessibilityEvent accessibilityEvent) {
            AccessibilityRecordCompat asRecord = AccessibilityEventCompat.asRecord(accessibilityEvent);
            if (this.mRecyclerView != null && asRecord != null) {
                boolean z = true;
                if (!ViewCompat.canScrollVertically(this.mRecyclerView, 1) && !ViewCompat.canScrollVertically(this.mRecyclerView, -1) && !ViewCompat.canScrollHorizontally(this.mRecyclerView, -1) && !ViewCompat.canScrollHorizontally(this.mRecyclerView, 1)) {
                    z = false;
                }
                asRecord.setScrollable(z);
                if (this.mRecyclerView.mAdapter != null) {
                    asRecord.setItemCount(this.mRecyclerView.mAdapter.getItemCount());
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void onInitializeAccessibilityNodeInfoForItem(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (childViewHolderInt != null && !childViewHolderInt.isRemoved() && !this.mChildHelper.d(childViewHolderInt.itemView)) {
                onInitializeAccessibilityNodeInfoForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, view, accessibilityNodeInfoCompat);
            }
        }

        public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(CollectionItemInfoCompat.obtain(canScrollVertically() ? getPosition(view) : 0, 1, canScrollHorizontally() ? getPosition(view) : 0, 1, false, false));
        }

        public void requestSimpleAnimationsInNextLayout() {
            this.mRequestedSimpleAnimations = true;
        }

        public int getRowCountForAccessibility(Recycler recycler, State state) {
            if (this.mRecyclerView == null || this.mRecyclerView.mAdapter == null || !canScrollVertically()) {
                return 1;
            }
            return this.mRecyclerView.mAdapter.getItemCount();
        }

        public int getColumnCountForAccessibility(Recycler recycler, State state) {
            if (this.mRecyclerView == null || this.mRecyclerView.mAdapter == null || !canScrollHorizontally()) {
                return 1;
            }
            return this.mRecyclerView.mAdapter.getItemCount();
        }

        /* access modifiers changed from: 0000 */
        public boolean performAccessibilityAction(int i, Bundle bundle) {
            return performAccessibilityAction(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, i, bundle);
        }

        /* JADX WARNING: Removed duplicated region for block: B:24:0x0074 A[ADDED_TO_REGION] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean performAccessibilityAction(android.support.v7.widget.RecyclerView.Recycler r2, android.support.v7.widget.RecyclerView.State r3, int r4, android.os.Bundle r5) {
            /*
                r1 = this;
                android.support.v7.widget.RecyclerView r2 = r1.mRecyclerView
                r3 = 0
                if (r2 != 0) goto L_0x0006
                return r3
            L_0x0006:
                r2 = 4096(0x1000, float:5.74E-42)
                r5 = 1
                if (r4 == r2) goto L_0x0044
                r2 = 8192(0x2000, float:1.14794E-41)
                if (r4 == r2) goto L_0x0012
                r2 = 0
            L_0x0010:
                r4 = 0
                goto L_0x0072
            L_0x0012:
                android.support.v7.widget.RecyclerView r2 = r1.mRecyclerView
                r4 = -1
                boolean r2 = android.support.v4.view.ViewCompat.canScrollVertically(r2, r4)
                if (r2 == 0) goto L_0x002b
                int r2 = r1.getHeight()
                int r0 = r1.getPaddingTop()
                int r2 = r2 - r0
                int r0 = r1.getPaddingBottom()
                int r2 = r2 - r0
                int r2 = -r2
                goto L_0x002c
            L_0x002b:
                r2 = 0
            L_0x002c:
                android.support.v7.widget.RecyclerView r0 = r1.mRecyclerView
                boolean r4 = android.support.v4.view.ViewCompat.canScrollHorizontally(r0, r4)
                if (r4 == 0) goto L_0x0010
                int r4 = r1.getWidth()
                int r0 = r1.getPaddingLeft()
                int r4 = r4 - r0
                int r0 = r1.getPaddingRight()
                int r4 = r4 - r0
                int r4 = -r4
                goto L_0x0072
            L_0x0044:
                android.support.v7.widget.RecyclerView r2 = r1.mRecyclerView
                boolean r2 = android.support.v4.view.ViewCompat.canScrollVertically(r2, r5)
                if (r2 == 0) goto L_0x005b
                int r2 = r1.getHeight()
                int r4 = r1.getPaddingTop()
                int r2 = r2 - r4
                int r4 = r1.getPaddingBottom()
                int r2 = r2 - r4
                goto L_0x005c
            L_0x005b:
                r2 = 0
            L_0x005c:
                android.support.v7.widget.RecyclerView r4 = r1.mRecyclerView
                boolean r4 = android.support.v4.view.ViewCompat.canScrollHorizontally(r4, r5)
                if (r4 == 0) goto L_0x0010
                int r4 = r1.getWidth()
                int r0 = r1.getPaddingLeft()
                int r4 = r4 - r0
                int r0 = r1.getPaddingRight()
                int r4 = r4 - r0
            L_0x0072:
                if (r2 != 0) goto L_0x0077
                if (r4 != 0) goto L_0x0077
                return r3
            L_0x0077:
                android.support.v7.widget.RecyclerView r3 = r1.mRecyclerView
                r3.scrollBy(r4, r2)
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.LayoutManager.performAccessibilityAction(android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.RecyclerView$State, int, android.os.Bundle):boolean");
        }

        /* access modifiers changed from: 0000 */
        public boolean performAccessibilityActionForItem(View view, int i, Bundle bundle) {
            return performAccessibilityActionForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, view, i, bundle);
        }

        public static Properties getProperties(Context context, AttributeSet attributeSet, int i, int i2) {
            Properties properties = new Properties();
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RecyclerView, i, i2);
            properties.a = obtainStyledAttributes.getInt(R.styleable.RecyclerView_android_orientation, 1);
            properties.b = obtainStyledAttributes.getInt(R.styleable.RecyclerView_spanCount, 1);
            properties.c = obtainStyledAttributes.getBoolean(R.styleable.RecyclerView_reverseLayout, false);
            properties.d = obtainStyledAttributes.getBoolean(R.styleable.RecyclerView_stackFromEnd, false);
            obtainStyledAttributes.recycle();
            return properties;
        }

        /* access modifiers changed from: 0000 */
        public void setExactMeasureSpecsFrom(RecyclerView recyclerView) {
            setMeasureSpecs(MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), UCCore.VERIFY_POLICY_QUICK));
        }

        /* access modifiers changed from: 0000 */
        public boolean hasFlexibleChildInBothOrientations() {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                android.view.ViewGroup.LayoutParams layoutParams = getChildAt(i).getLayoutParams();
                if (layoutParams.width < 0 && layoutParams.height < 0) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public void removeAndRecycleScrapInt(Recycler recycler) {
            int size = recycler.a.size();
            for (int i = size - 1; i >= 0; i--) {
                View view = recycler.a.get(i).itemView;
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
                if (!childViewHolderInt.shouldIgnore()) {
                    childViewHolderInt.setIsRecyclable(false);
                    if (childViewHolderInt.isTmpDetached()) {
                        this.mRecyclerView.removeDetachedView(view, false);
                    }
                    if (this.mRecyclerView.mItemAnimator != null) {
                        this.mRecyclerView.mItemAnimator.c(childViewHolderInt);
                    }
                    childViewHolderInt.setIsRecyclable(true);
                    recycler.b(view);
                }
            }
            recycler.a.clear();
            if (recycler.b != null) {
                recycler.b.clear();
            }
            if (size > 0) {
                this.mRecyclerView.invalidate();
            }
        }
    }

    public static class LayoutParams extends MarginLayoutParams {
        ViewHolder c;
        final Rect d = new Rect();
        boolean e = true;
        boolean f = false;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    public interface OnChildAttachStateChangeListener {
        void a(View view);
    }

    public interface OnItemTouchListener {
        boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent);

        void onRequestDisallowInterceptTouchEvent(boolean z);

        void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent);
    }

    public static abstract class OnScrollListener {
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
        }
    }

    public static class RecycledViewPool {
        public SparseArray<ArrayList<ViewHolder>> a = new SparseArray<>();
        int b = 0;
        private SparseIntArray c = new SparseIntArray();

        public final ViewHolder a(int i) {
            ArrayList arrayList = this.a.get(i);
            if (arrayList == null || arrayList.isEmpty()) {
                return null;
            }
            int size = arrayList.size() - 1;
            ViewHolder viewHolder = (ViewHolder) arrayList.get(size);
            arrayList.remove(size);
            return viewHolder;
        }

        public final void a(ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            ArrayList arrayList = this.a.get(itemViewType);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.a.put(itemViewType, arrayList);
                if (this.c.indexOfKey(itemViewType) < 0) {
                    this.c.put(itemViewType, 5);
                }
            }
            if (this.c.get(itemViewType) > arrayList.size()) {
                viewHolder.resetInternal();
                arrayList.add(viewHolder);
            }
        }

        /* access modifiers changed from: 0000 */
        public final void a() {
            this.b++;
        }

        /* access modifiers changed from: 0000 */
        public final void b() {
            this.b--;
        }
    }

    public final class Recycler {
        final ArrayList<ViewHolder> a = new ArrayList<>();
        /* access modifiers changed from: 0000 */
        public ArrayList<ViewHolder> b = null;
        final ArrayList<ViewHolder> c = new ArrayList<>();
        final List<ViewHolder> d = Collections.unmodifiableList(this.a);
        int e = 2;
        RecycledViewPool f;
        ViewCacheExtension g;

        public Recycler() {
        }

        public final void a() {
            this.a.clear();
            b();
        }

        private boolean c(ViewHolder viewHolder) {
            if (viewHolder.isRemoved()) {
                return RecyclerView.this.mState.c;
            }
            if (viewHolder.mPosition < 0 || viewHolder.mPosition >= RecyclerView.this.mAdapter.getItemCount()) {
                throw new IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position".concat(String.valueOf(viewHolder)));
            } else if (!RecyclerView.this.mState.c && RecyclerView.this.mAdapter.getItemViewType(viewHolder.mPosition) != viewHolder.getItemViewType()) {
                return false;
            } else {
                if (!RecyclerView.this.mAdapter.hasStableIds() || viewHolder.getItemId() == RecyclerView.this.mAdapter.getItemId(viewHolder.mPosition)) {
                    return true;
                }
                return false;
            }
        }

        public final int a(int i) {
            if (i < 0 || i >= RecyclerView.this.mState.a()) {
                StringBuilder sb = new StringBuilder("invalid position ");
                sb.append(i);
                sb.append(". State item count is ");
                sb.append(RecyclerView.this.mState.a());
                throw new IndexOutOfBoundsException(sb.toString());
            } else if (!RecyclerView.this.mState.c) {
                return i;
            } else {
                return RecyclerView.this.mAdapterHelper.b(i);
            }
        }

        public final View b(int i) {
            return d(i);
        }

        /* JADX WARNING: Removed duplicated region for block: B:11:0x0025  */
        /* JADX WARNING: Removed duplicated region for block: B:24:0x0057  */
        /* JADX WARNING: Removed duplicated region for block: B:63:0x0136  */
        /* JADX WARNING: Removed duplicated region for block: B:78:0x018d  */
        /* JADX WARNING: Removed duplicated region for block: B:82:0x0198  */
        /* JADX WARNING: Removed duplicated region for block: B:83:0x01a6  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private android.view.View d(int r9) {
            /*
                r8 = this;
                if (r9 < 0) goto L_0x01cb
                android.support.v7.widget.RecyclerView r0 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r0 = r0.mState
                int r0 = r0.a()
                if (r9 < r0) goto L_0x000e
                goto L_0x01cb
            L_0x000e:
                android.support.v7.widget.RecyclerView r0 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r0 = r0.mState
                boolean r0 = r0.c
                r1 = 0
                r2 = 1
                r3 = 0
                if (r0 == 0) goto L_0x0021
                android.support.v7.widget.RecyclerView$ViewHolder r0 = r8.e(r9)
                if (r0 == 0) goto L_0x0022
                r4 = 1
                goto L_0x0023
            L_0x0021:
                r0 = r1
            L_0x0022:
                r4 = 0
            L_0x0023:
                if (r0 != 0) goto L_0x0055
                android.support.v7.widget.RecyclerView$ViewHolder r0 = r8.f(r9)
                if (r0 == 0) goto L_0x0055
                boolean r5 = r8.c(r0)
                if (r5 != 0) goto L_0x0054
                r5 = 4
                r0.addFlags(r5)
                boolean r5 = r0.isScrap()
                if (r5 == 0) goto L_0x0046
                android.support.v7.widget.RecyclerView r5 = android.support.v7.widget.RecyclerView.this
                android.view.View r6 = r0.itemView
                r5.removeDetachedView(r6, r3)
                r0.unScrap()
                goto L_0x004f
            L_0x0046:
                boolean r5 = r0.wasReturnedFromScrap()
                if (r5 == 0) goto L_0x004f
                r0.clearReturnedFromScrapFlag()
            L_0x004f:
                r8.a(r0)
                r0 = r1
                goto L_0x0055
            L_0x0054:
                r4 = 1
            L_0x0055:
                if (r0 != 0) goto L_0x0117
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.AdapterHelper r1 = r1.mAdapterHelper
                int r1 = r1.b(r9)
                if (r1 < 0) goto L_0x00eb
                android.support.v7.widget.RecyclerView r5 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$Adapter r5 = r5.mAdapter
                int r5 = r5.getItemCount()
                if (r1 < r5) goto L_0x006f
                goto L_0x00eb
            L_0x006f:
                android.support.v7.widget.RecyclerView r5 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$Adapter r5 = r5.mAdapter
                int r5 = r5.getItemViewType(r1)
                android.support.v7.widget.RecyclerView r6 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$Adapter r6 = r6.mAdapter
                boolean r6 = r6.hasStableIds()
                if (r6 == 0) goto L_0x0098
                android.support.v7.widget.RecyclerView r0 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$Adapter r0 = r0.mAdapter
                long r6 = r0.getItemId(r1)
                android.support.v7.widget.RecyclerView$ViewHolder r0 = r8.a(r6, r5)
                if (r0 == 0) goto L_0x0098
                r0.mPosition = r1
                r4 = 1
            L_0x0098:
                if (r0 != 0) goto L_0x00c4
                android.support.v7.widget.RecyclerView$ViewCacheExtension r1 = r8.g
                if (r1 == 0) goto L_0x00c4
                android.support.v7.widget.RecyclerView$ViewCacheExtension r1 = r8.g
                android.view.View r1 = r1.a()
                if (r1 == 0) goto L_0x00c4
                android.support.v7.widget.RecyclerView r0 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$ViewHolder r0 = r0.getChildViewHolder(r1)
                if (r0 != 0) goto L_0x00b6
                java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
                java.lang.String r0 = "getViewForPositionAndType returned a view which does not have a ViewHolder"
                r9.<init>(r0)
                throw r9
            L_0x00b6:
                boolean r1 = r0.shouldIgnore()
                if (r1 == 0) goto L_0x00c4
                java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
                java.lang.String r0 = "getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view."
                r9.<init>(r0)
                throw r9
            L_0x00c4:
                if (r0 != 0) goto L_0x00dc
                android.support.v7.widget.RecyclerView$RecycledViewPool r0 = r8.c()
                android.support.v7.widget.RecyclerView$ViewHolder r0 = r0.a(r5)
                if (r0 == 0) goto L_0x00dc
                r0.resetInternal()
                boolean r1 = android.support.v7.widget.RecyclerView.FORCE_INVALIDATE_DISPLAY_LIST
                if (r1 == 0) goto L_0x00dc
                r8.d(r0)
            L_0x00dc:
                if (r0 != 0) goto L_0x0117
                android.support.v7.widget.RecyclerView r0 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$Adapter r0 = r0.mAdapter
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$ViewHolder r0 = r0.createViewHolder(r1, r5)
                goto L_0x0117
            L_0x00eb:
                java.lang.IndexOutOfBoundsException r0 = new java.lang.IndexOutOfBoundsException
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                java.lang.String r3 = "Inconsistency detected. Invalid item position "
                r2.<init>(r3)
                r2.append(r9)
                java.lang.String r9 = "(offset:"
                r2.append(r9)
                r2.append(r1)
                java.lang.String r9 = ").state:"
                r2.append(r9)
                android.support.v7.widget.RecyclerView r9 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r9 = r9.mState
                int r9 = r9.a()
                r2.append(r9)
                java.lang.String r9 = r2.toString()
                r0.<init>(r9)
                throw r0
            L_0x0117:
                if (r4 == 0) goto L_0x0145
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r1 = r1.mState
                boolean r1 = r1.c
                if (r1 != 0) goto L_0x0145
                r1 = 8192(0x2000, float:1.14794E-41)
                boolean r5 = r0.hasAnyOfTheFlags(r1)
                if (r5 == 0) goto L_0x0145
                r0.setFlags(r3, r1)
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r1 = r1.mState
                boolean r1 = r1.j
                if (r1 == 0) goto L_0x0145
                android.support.v7.widget.RecyclerView.ItemAnimator.e(r0)
                r0.getUnmodifiedPayloads()
                android.support.v7.widget.RecyclerView$ItemAnimator$ItemHolderInfo r1 = android.support.v7.widget.RecyclerView.ItemAnimator.d(r0)
                android.support.v7.widget.RecyclerView r5 = android.support.v7.widget.RecyclerView.this
                r5.recordAnimationInfoIfBouncedHiddenView(r0, r1)
            L_0x0145:
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r1 = r1.mState
                boolean r1 = r1.c
                if (r1 == 0) goto L_0x0156
                boolean r1 = r0.isBound()
                if (r1 == 0) goto L_0x0156
                r0.mPreLayoutPosition = r9
                goto L_0x0169
            L_0x0156:
                boolean r1 = r0.isBound()
                if (r1 == 0) goto L_0x016b
                boolean r1 = r0.needsUpdate()
                if (r1 != 0) goto L_0x016b
                boolean r1 = r0.isInvalid()
                if (r1 == 0) goto L_0x0169
                goto L_0x016b
            L_0x0169:
                r9 = 0
                goto L_0x0190
            L_0x016b:
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.AdapterHelper r1 = r1.mAdapterHelper
                int r1 = r1.b(r9)
                android.support.v7.widget.RecyclerView r5 = android.support.v7.widget.RecyclerView.this
                r0.mOwnerRecyclerView = r5
                android.support.v7.widget.RecyclerView r5 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$Adapter r5 = r5.mAdapter
                r5.bindViewHolder(r0, r1)
                android.view.View r1 = r0.itemView
                r8.d(r1)
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r1 = r1.mState
                boolean r1 = r1.c
                if (r1 == 0) goto L_0x018f
                r0.mPreLayoutPosition = r9
            L_0x018f:
                r9 = 1
            L_0x0190:
                android.view.View r1 = r0.itemView
                android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
                if (r1 != 0) goto L_0x01a6
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                android.view.ViewGroup$LayoutParams r1 = r1.generateDefaultLayoutParams()
                android.support.v7.widget.RecyclerView$LayoutParams r1 = (android.support.v7.widget.RecyclerView.LayoutParams) r1
                android.view.View r5 = r0.itemView
                r5.setLayoutParams(r1)
                goto L_0x01be
            L_0x01a6:
                android.support.v7.widget.RecyclerView r5 = android.support.v7.widget.RecyclerView.this
                boolean r5 = r5.checkLayoutParams(r1)
                if (r5 != 0) goto L_0x01bc
                android.support.v7.widget.RecyclerView r5 = android.support.v7.widget.RecyclerView.this
                android.view.ViewGroup$LayoutParams r1 = r5.generateLayoutParams(r1)
                android.support.v7.widget.RecyclerView$LayoutParams r1 = (android.support.v7.widget.RecyclerView.LayoutParams) r1
                android.view.View r5 = r0.itemView
                r5.setLayoutParams(r1)
                goto L_0x01be
            L_0x01bc:
                android.support.v7.widget.RecyclerView$LayoutParams r1 = (android.support.v7.widget.RecyclerView.LayoutParams) r1
            L_0x01be:
                r1.c = r0
                if (r4 == 0) goto L_0x01c5
                if (r9 == 0) goto L_0x01c5
                goto L_0x01c6
            L_0x01c5:
                r2 = 0
            L_0x01c6:
                r1.f = r2
                android.view.View r9 = r0.itemView
                return r9
            L_0x01cb:
                java.lang.IndexOutOfBoundsException r0 = new java.lang.IndexOutOfBoundsException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                java.lang.String r2 = "Invalid item position "
                r1.<init>(r2)
                r1.append(r9)
                java.lang.String r2 = "("
                r1.append(r2)
                r1.append(r9)
                java.lang.String r9 = "). Item count:"
                r1.append(r9)
                android.support.v7.widget.RecyclerView r9 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r9 = r9.mState
                int r9 = r9.a()
                r1.append(r9)
                java.lang.String r9 = r1.toString()
                r0.<init>(r9)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.Recycler.d(int):android.view.View");
        }

        private void d(View view) {
            if (RecyclerView.this.isAccessibilityEnabled()) {
                if (ViewCompat.getImportantForAccessibility(view) == 0) {
                    ViewCompat.setImportantForAccessibility(view, 1);
                }
                if (!ViewCompat.hasAccessibilityDelegate(view)) {
                    ViewCompat.setAccessibilityDelegate(view, RecyclerView.this.mAccessibilityDelegate.b);
                }
            }
        }

        private void d(ViewHolder viewHolder) {
            if (viewHolder.itemView instanceof ViewGroup) {
                a((ViewGroup) viewHolder.itemView, false);
            }
        }

        private void a(ViewGroup viewGroup, boolean z) {
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                if (childAt instanceof ViewGroup) {
                    a((ViewGroup) childAt, true);
                }
            }
            if (z) {
                if (viewGroup.getVisibility() == 4) {
                    viewGroup.setVisibility(0);
                    viewGroup.setVisibility(4);
                    return;
                }
                int visibility = viewGroup.getVisibility();
                viewGroup.setVisibility(4);
                viewGroup.setVisibility(visibility);
            }
        }

        public final void a(View view) {
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (childViewHolderInt.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(view, false);
            }
            if (childViewHolderInt.isScrap()) {
                childViewHolderInt.unScrap();
            } else if (childViewHolderInt.wasReturnedFromScrap()) {
                childViewHolderInt.clearReturnedFromScrapFlag();
            }
            a(childViewHolderInt);
        }

        /* access modifiers changed from: 0000 */
        public final void b() {
            for (int size = this.c.size() - 1; size >= 0; size--) {
                c(size);
            }
            this.c.clear();
        }

        /* access modifiers changed from: 0000 */
        public final void c(int i) {
            e(this.c.get(i));
            this.c.remove(i);
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Removed duplicated region for block: B:34:0x0083  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void a(android.support.v7.widget.RecyclerView.ViewHolder r6) {
            /*
                r5 = this;
                boolean r0 = r6.isScrap()
                r1 = 1
                r2 = 0
                if (r0 != 0) goto L_0x0098
                android.view.View r0 = r6.itemView
                android.view.ViewParent r0 = r0.getParent()
                if (r0 == 0) goto L_0x0012
                goto L_0x0098
            L_0x0012:
                boolean r0 = r6.isTmpDetached()
                if (r0 == 0) goto L_0x0028
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                java.lang.String r1 = "Tmp detached view should be removed from RecyclerView before it can be recycled: "
                java.lang.String r6 = java.lang.String.valueOf(r6)
                java.lang.String r6 = r1.concat(r6)
                r0.<init>(r6)
                throw r0
            L_0x0028:
                boolean r0 = r6.shouldIgnore()
                if (r0 == 0) goto L_0x0036
                java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
                java.lang.String r0 = "Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle."
                r6.<init>(r0)
                throw r6
            L_0x0036:
                boolean r0 = r6.doesTransientStatePreventRecycling()
                android.support.v7.widget.RecyclerView r3 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$Adapter r3 = r3.mAdapter
                if (r3 == 0) goto L_0x0052
                if (r0 == 0) goto L_0x0052
                android.support.v7.widget.RecyclerView r3 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$Adapter r3 = r3.mAdapter
                boolean r3 = r3.onFailedToRecycleView(r6)
                if (r3 == 0) goto L_0x0052
                r3 = 1
                goto L_0x0053
            L_0x0052:
                r3 = 0
            L_0x0053:
                if (r3 != 0) goto L_0x005e
                boolean r3 = r6.isRecyclable()
                if (r3 == 0) goto L_0x005c
                goto L_0x005e
            L_0x005c:
                r3 = 0
                goto L_0x0087
            L_0x005e:
                r3 = 14
                boolean r3 = r6.hasAnyOfTheFlags(r3)
                if (r3 != 0) goto L_0x0080
                java.util.ArrayList<android.support.v7.widget.RecyclerView$ViewHolder> r3 = r5.c
                int r3 = r3.size()
                int r4 = r5.e
                if (r3 != r4) goto L_0x0075
                if (r3 <= 0) goto L_0x0075
                r5.c(r2)
            L_0x0075:
                int r4 = r5.e
                if (r3 >= r4) goto L_0x0080
                java.util.ArrayList<android.support.v7.widget.RecyclerView$ViewHolder> r3 = r5.c
                r3.add(r6)
                r3 = 1
                goto L_0x0081
            L_0x0080:
                r3 = 0
            L_0x0081:
                if (r3 != 0) goto L_0x0087
                r5.e(r6)
                r2 = 1
            L_0x0087:
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.ViewInfoStore r1 = r1.mViewInfoStore
                r1.d(r6)
                if (r3 != 0) goto L_0x0097
                if (r2 != 0) goto L_0x0097
                if (r0 == 0) goto L_0x0097
                r0 = 0
                r6.mOwnerRecyclerView = r0
            L_0x0097:
                return
            L_0x0098:
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                java.lang.String r4 = "Scrapped or attached views may not be recycled. isScrap:"
                r3.<init>(r4)
                boolean r4 = r6.isScrap()
                r3.append(r4)
                java.lang.String r4 = " isAttached:"
                r3.append(r4)
                android.view.View r6 = r6.itemView
                android.view.ViewParent r6 = r6.getParent()
                if (r6 == 0) goto L_0x00b6
                goto L_0x00b7
            L_0x00b6:
                r1 = 0
            L_0x00b7:
                r3.append(r1)
                java.lang.String r6 = r3.toString()
                r0.<init>(r6)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.Recycler.a(android.support.v7.widget.RecyclerView$ViewHolder):void");
        }

        private void e(ViewHolder viewHolder) {
            ViewCompat.setAccessibilityDelegate(viewHolder.itemView, null);
            f(viewHolder);
            viewHolder.mOwnerRecyclerView = null;
            c().a(viewHolder);
        }

        /* access modifiers changed from: 0000 */
        public final void b(View view) {
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            childViewHolderInt.mScrapContainer = null;
            childViewHolderInt.mInChangeScrap = false;
            childViewHolderInt.clearReturnedFromScrapFlag();
            a(childViewHolderInt);
        }

        /* access modifiers changed from: 0000 */
        public final void c(View view) {
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (!childViewHolderInt.hasAnyOfTheFlags(12) && childViewHolderInt.isUpdated() && !RecyclerView.this.canReuseUpdatedViewHolder(childViewHolderInt)) {
                if (this.b == null) {
                    this.b = new ArrayList<>();
                }
                childViewHolderInt.setScrapContainer(this, true);
                this.b.add(childViewHolderInt);
            } else if (!childViewHolderInt.isInvalid() || childViewHolderInt.isRemoved() || RecyclerView.this.mAdapter.hasStableIds()) {
                childViewHolderInt.setScrapContainer(this, false);
                this.a.add(childViewHolderInt);
            } else {
                throw new IllegalArgumentException("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool.");
            }
        }

        /* access modifiers changed from: 0000 */
        public final void b(ViewHolder viewHolder) {
            if (viewHolder.mInChangeScrap) {
                this.b.remove(viewHolder);
            } else {
                this.a.remove(viewHolder);
            }
            viewHolder.mScrapContainer = null;
            viewHolder.mInChangeScrap = false;
            viewHolder.clearReturnedFromScrapFlag();
        }

        private ViewHolder e(int i) {
            if (this.b != null) {
                int size = this.b.size();
                if (size != 0) {
                    int i2 = 0;
                    int i3 = 0;
                    while (i3 < size) {
                        ViewHolder viewHolder = this.b.get(i3);
                        if (viewHolder.wasReturnedFromScrap() || viewHolder.getLayoutPosition() != i) {
                            i3++;
                        } else {
                            viewHolder.addFlags(32);
                            return viewHolder;
                        }
                    }
                    if (RecyclerView.this.mAdapter.hasStableIds()) {
                        int a2 = RecyclerView.this.mAdapterHelper.a(i, 0);
                        if (a2 > 0 && a2 < RecyclerView.this.mAdapter.getItemCount()) {
                            long itemId = RecyclerView.this.mAdapter.getItemId(a2);
                            while (i2 < size) {
                                ViewHolder viewHolder2 = this.b.get(i2);
                                if (viewHolder2.wasReturnedFromScrap() || viewHolder2.getItemId() != itemId) {
                                    i2++;
                                } else {
                                    viewHolder2.addFlags(32);
                                    return viewHolder2;
                                }
                            }
                        }
                    }
                    return null;
                }
            }
            return null;
        }

        private ViewHolder f(int i) {
            View view;
            int size = this.a.size();
            int i2 = 0;
            int i3 = 0;
            while (i3 < size) {
                ViewHolder viewHolder = this.a.get(i3);
                if (viewHolder.wasReturnedFromScrap() || viewHolder.getLayoutPosition() != i || viewHolder.isInvalid() || (!RecyclerView.this.mState.c && viewHolder.isRemoved())) {
                    i3++;
                } else {
                    viewHolder.addFlags(32);
                    return viewHolder;
                }
            }
            ChildHelper childHelper = RecyclerView.this.mChildHelper;
            int size2 = childHelper.c.size();
            int i4 = 0;
            while (true) {
                if (i4 >= size2) {
                    view = null;
                    break;
                }
                view = childHelper.c.get(i4);
                ViewHolder b2 = childHelper.a.b(view);
                if (b2.getLayoutPosition() == i && !b2.isInvalid() && !b2.isRemoved()) {
                    break;
                }
                i4++;
            }
            if (view != null) {
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
                ChildHelper childHelper2 = RecyclerView.this.mChildHelper;
                int a2 = childHelper2.a.a(view);
                if (a2 < 0) {
                    throw new IllegalArgumentException("view is not a child, cannot hide ".concat(String.valueOf(view)));
                } else if (!childHelper2.b.c(a2)) {
                    throw new RuntimeException("trying to unhide a view that was not hidden".concat(String.valueOf(view)));
                } else {
                    childHelper2.b.b(a2);
                    childHelper2.b(view);
                    int c2 = RecyclerView.this.mChildHelper.c(view);
                    if (c2 == -1) {
                        throw new IllegalStateException("layout index should not be -1 after unhiding a view:".concat(String.valueOf(childViewHolderInt)));
                    }
                    RecyclerView.this.mChildHelper.d(c2);
                    c(view);
                    childViewHolderInt.addFlags(8224);
                    return childViewHolderInt;
                }
            } else {
                int size3 = this.c.size();
                while (i2 < size3) {
                    ViewHolder viewHolder2 = this.c.get(i2);
                    if (viewHolder2.isInvalid() || viewHolder2.getLayoutPosition() != i) {
                        i2++;
                    } else {
                        this.c.remove(i2);
                        return viewHolder2;
                    }
                }
                return null;
            }
        }

        private ViewHolder a(long j, int i) {
            for (int size = this.a.size() - 1; size >= 0; size--) {
                ViewHolder viewHolder = this.a.get(size);
                if (viewHolder.getItemId() == j && !viewHolder.wasReturnedFromScrap()) {
                    if (i == viewHolder.getItemViewType()) {
                        viewHolder.addFlags(32);
                        if (viewHolder.isRemoved() && !RecyclerView.this.mState.c) {
                            viewHolder.setFlags(2, 14);
                        }
                        return viewHolder;
                    }
                    this.a.remove(size);
                    RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
                    b(viewHolder.itemView);
                }
            }
            for (int size2 = this.c.size() - 1; size2 >= 0; size2--) {
                ViewHolder viewHolder2 = this.c.get(size2);
                if (viewHolder2.getItemId() == j) {
                    if (i == viewHolder2.getItemViewType()) {
                        this.c.remove(size2);
                        return viewHolder2;
                    }
                    c(size2);
                }
            }
            return null;
        }

        private void f(ViewHolder viewHolder) {
            if (RecyclerView.this.mRecyclerListener != null) {
                RecyclerView.this.mRecyclerListener;
            }
            if (RecyclerView.this.mAdapter != null) {
                RecyclerView.this.mAdapter.onViewRecycled(viewHolder);
            }
            if (RecyclerView.this.mState != null) {
                RecyclerView.this.mViewInfoStore.d(viewHolder);
            }
        }

        /* access modifiers changed from: 0000 */
        public final RecycledViewPool c() {
            if (this.f == null) {
                this.f = new RecycledViewPool();
            }
            return this.f;
        }
    }

    public interface RecyclerListener {
    }

    class RecyclerViewDataObserver extends AdapterDataObserver {
        private RecyclerViewDataObserver() {
        }

        /* synthetic */ RecyclerViewDataObserver(RecyclerView recyclerView, byte b) {
            this();
        }

        public final void a() {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapter.hasStableIds()) {
                RecyclerView.this.mState.i = true;
                RecyclerView.this.setDataSetChangedAfterLayout();
            } else {
                RecyclerView.this.mState.i = true;
                RecyclerView.this.setDataSetChangedAfterLayout();
            }
            if (!RecyclerView.this.mAdapterHelper.d()) {
                RecyclerView.this.requestLayout();
            }
        }

        public final void a(int i, int i2, Object obj) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            AdapterHelper adapterHelper = RecyclerView.this.mAdapterHelper;
            adapterHelper.a.add(adapterHelper.a(4, i, i2, obj));
            adapterHelper.g |= 4;
            boolean z = true;
            if (adapterHelper.a.size() != 1) {
                z = false;
            }
            if (z) {
                b();
            }
        }

        public final void a(int i, int i2) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            AdapterHelper adapterHelper = RecyclerView.this.mAdapterHelper;
            boolean z = true;
            adapterHelper.a.add(adapterHelper.a(1, i, i2, null));
            adapterHelper.g |= 1;
            if (adapterHelper.a.size() != 1) {
                z = false;
            }
            if (z) {
                b();
            }
        }

        public final void b(int i, int i2) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            AdapterHelper adapterHelper = RecyclerView.this.mAdapterHelper;
            adapterHelper.a.add(adapterHelper.a(2, i, i2, null));
            adapterHelper.g |= 2;
            boolean z = true;
            if (adapterHelper.a.size() != 1) {
                z = false;
            }
            if (z) {
                b();
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:3:0x0023, code lost:
            if (r0.a.size() == 1) goto L_0x0027;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void c(int r6, int r7) {
            /*
                r5 = this;
                android.support.v7.widget.RecyclerView r0 = android.support.v7.widget.RecyclerView.this
                r1 = 0
                r0.assertNotInLayoutOrScroll(r1)
                android.support.v7.widget.RecyclerView r0 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.AdapterHelper r0 = r0.mAdapterHelper
                r2 = 1
                if (r6 == r7) goto L_0x0026
                java.util.ArrayList<android.support.v7.widget.AdapterHelper$UpdateOp> r3 = r0.a
                r4 = 8
                android.support.v7.widget.AdapterHelper$UpdateOp r6 = r0.a(r4, r6, r7, r1)
                r3.add(r6)
                int r6 = r0.g
                r6 = r6 | r4
                r0.g = r6
                java.util.ArrayList<android.support.v7.widget.AdapterHelper$UpdateOp> r6 = r0.a
                int r6 = r6.size()
                if (r6 != r2) goto L_0x0026
                goto L_0x0027
            L_0x0026:
                r2 = 0
            L_0x0027:
                if (r2 == 0) goto L_0x002c
                r5.b()
            L_0x002c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.RecyclerViewDataObserver.c(int, int):void");
        }

        private void b() {
            if (!RecyclerView.this.mPostUpdatesOnAnimation || !RecyclerView.this.mHasFixedSize || !RecyclerView.this.mIsAttached) {
                RecyclerView.this.mAdapterUpdateDuringMeasure = true;
                RecyclerView.this.requestLayout();
                return;
            }
            ViewCompat.postOnAnimation(RecyclerView.this, RecyclerView.this.mUpdateChildViewsRunnable);
        }
    }

    public static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
                return new SavedState[i];
            }

            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }
        };
        /* access modifiers changed from: 0000 */
        public Parcelable a;

        SavedState(Parcel parcel) {
            super(parcel);
            this.a = parcel.readParcelable(LayoutManager.class.getClassLoader());
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.a, 0);
        }
    }

    public static class SimpleOnItemTouchListener implements OnItemTouchListener {
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            return false;
        }

        public void onRequestDisallowInterceptTouchEvent(boolean z) {
        }

        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        }
    }

    public static abstract class SmoothScroller {
        private final Action a = new Action();
        int g = -1;
        RecyclerView h;
        LayoutManager i;
        boolean j;
        boolean k;
        View l;

        public static class Action {
            int a;
            private int b;
            private int c;
            private int d;
            private Interpolator e;
            private boolean f;
            private int g;

            public Action() {
                this(0);
            }

            private Action(byte b2) {
                this.a = -1;
                this.f = false;
                this.g = 0;
                this.b = 0;
                this.c = 0;
                this.d = Integer.MIN_VALUE;
                this.e = null;
            }

            public final void a(int i, int i2, int i3, Interpolator interpolator) {
                this.b = i;
                this.c = i2;
                this.d = i3;
                this.e = interpolator;
                this.f = true;
            }

            static /* synthetic */ void a(Action action, RecyclerView recyclerView) {
                if (action.a >= 0) {
                    int i = action.a;
                    action.a = -1;
                    recyclerView.jumpToPositionForSmoothScroller(i);
                    action.f = false;
                } else if (!action.f) {
                    action.g = 0;
                } else if (action.e != null && action.d <= 0) {
                    throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
                } else if (action.d <= 0) {
                    throw new IllegalStateException("Scroll duration must be a positive number");
                } else {
                    if (action.e != null) {
                        recyclerView.mViewFlinger.a(action.b, action.c, action.d, action.e);
                    } else if (action.d == Integer.MIN_VALUE) {
                        recyclerView.mViewFlinger.a(action.b, action.c);
                    } else {
                        recyclerView.mViewFlinger.a(action.b, action.c, action.d);
                    }
                    action.g++;
                    action.f = false;
                }
            }
        }

        /* access modifiers changed from: protected */
        public abstract void a();

        /* access modifiers changed from: protected */
        public abstract void a(int i2, int i3, Action action);

        /* access modifiers changed from: protected */
        public abstract void a(View view, Action action);

        /* access modifiers changed from: protected */
        public final void b() {
            if (this.k) {
                a();
                this.h.mState.a = -1;
                this.l = null;
                this.g = -1;
                this.j = false;
                this.k = false;
                this.i.onSmoothScrollerStopped(this);
                this.i = null;
                this.h = null;
            }
        }

        public final int a(View view) {
            return this.h.getChildLayoutPosition(view);
        }

        static /* synthetic */ void a(SmoothScroller smoothScroller, int i2, int i3) {
            RecyclerView recyclerView = smoothScroller.h;
            if (!smoothScroller.k || smoothScroller.g == -1 || recyclerView == null) {
                smoothScroller.b();
            }
            boolean z = false;
            smoothScroller.j = false;
            if (smoothScroller.l != null) {
                if (smoothScroller.a(smoothScroller.l) == smoothScroller.g) {
                    smoothScroller.a(smoothScroller.l, smoothScroller.a);
                    Action.a(smoothScroller.a, recyclerView);
                    smoothScroller.b();
                } else {
                    smoothScroller.l = null;
                }
            }
            if (smoothScroller.k) {
                smoothScroller.a(i2, i3, smoothScroller.a);
                if (smoothScroller.a.a >= 0) {
                    z = true;
                }
                Action.a(smoothScroller.a, recyclerView);
                if (z) {
                    if (smoothScroller.k) {
                        smoothScroller.j = true;
                        recyclerView.mViewFlinger.a();
                        return;
                    }
                    smoothScroller.b();
                }
            }
        }
    }

    public static class State {
        /* access modifiers changed from: 0000 */
        public int a = -1;
        int b = 0;
        /* access modifiers changed from: 0000 */
        public boolean c = false;
        /* access modifiers changed from: 0000 */
        public boolean d = false;
        /* access modifiers changed from: private */
        public int e = 1;
        private SparseArray<Object> f;
        /* access modifiers changed from: private */
        public int g = 0;
        /* access modifiers changed from: private */
        public int h = 0;
        /* access modifiers changed from: private */
        public boolean i = false;
        /* access modifiers changed from: private */
        public boolean j = false;
        /* access modifiers changed from: private */
        public boolean k = false;
        /* access modifiers changed from: private */
        public boolean l = false;

        static /* synthetic */ int a(State state, int i2) {
            int i3 = state.h + i2;
            state.h = i3;
            return i3;
        }

        /* access modifiers changed from: 0000 */
        public final void a(int i2) {
            if ((this.e & i2) == 0) {
                StringBuilder sb = new StringBuilder("Layout state should be one of ");
                sb.append(Integer.toBinaryString(i2));
                sb.append(" but it is ");
                sb.append(Integer.toBinaryString(this.e));
                throw new IllegalStateException(sb.toString());
            }
        }

        public final int a() {
            return this.c ? this.g - this.h : this.b;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("State{mTargetPosition=");
            sb.append(this.a);
            sb.append(", mData=");
            sb.append(this.f);
            sb.append(", mItemCount=");
            sb.append(this.b);
            sb.append(", mPreviousLayoutItemCount=");
            sb.append(this.g);
            sb.append(", mDeletedInvisibleItemCountSincePreviousLayout=");
            sb.append(this.h);
            sb.append(", mStructureChanged=");
            sb.append(this.i);
            sb.append(", mInPreLayout=");
            sb.append(this.c);
            sb.append(", mRunSimpleAnimations=");
            sb.append(this.j);
            sb.append(", mRunPredictiveAnimations=");
            sb.append(this.d);
            sb.append('}');
            return sb.toString();
        }
    }

    public static abstract class ViewCacheExtension {
        public abstract View a();
    }

    class ViewFlinger implements Runnable {
        int a;
        int b;
        ScrollerCompat c;
        private Interpolator e = RecyclerView.sQuinticInterpolator;
        private boolean f = false;
        private boolean g = false;

        public ViewFlinger() {
            this.c = ScrollerCompat.create(RecyclerView.this.getContext(), RecyclerView.sQuinticInterpolator);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:47:0x00f4, code lost:
            if (r12 > 0) goto L_0x00f8;
         */
        /* JADX WARNING: Removed duplicated region for block: B:45:0x00f0  */
        /* JADX WARNING: Removed duplicated region for block: B:51:0x0100  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r17 = this;
                r0 = r17
                android.support.v7.widget.RecyclerView r1 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$LayoutManager r1 = r1.mLayout
                if (r1 != 0) goto L_0x000c
                r17.b()
                return
            L_0x000c:
                r1 = 0
                r0.g = r1
                r2 = 1
                r0.f = r2
                android.support.v7.widget.RecyclerView r3 = android.support.v7.widget.RecyclerView.this
                r3.consumePendingUpdateOperations()
                android.support.v4.widget.ScrollerCompat r3 = r0.c
                android.support.v7.widget.RecyclerView r4 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$LayoutManager r4 = r4.mLayout
                android.support.v7.widget.RecyclerView$SmoothScroller r4 = r4.mSmoothScroller
                boolean r5 = r3.computeScrollOffset()
                if (r5 == 0) goto L_0x0174
                int r5 = r3.getCurrX()
                int r6 = r3.getCurrY()
                int r7 = r0.a
                int r7 = r5 - r7
                int r8 = r0.b
                int r8 = r6 - r8
                r0.a = r5
                r0.b = r6
                android.support.v7.widget.RecyclerView r9 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$Adapter r9 = r9.mAdapter
                if (r9 == 0) goto L_0x00b7
                android.support.v7.widget.RecyclerView r9 = android.support.v7.widget.RecyclerView.this
                r9.eatRequestLayout()
                android.support.v7.widget.RecyclerView r9 = android.support.v7.widget.RecyclerView.this
                r9.onEnterLayoutOrScroll()
                java.lang.String r9 = "RV Scroll"
                android.support.v4.os.TraceCompat.beginSection(r9)
                if (r7 == 0) goto L_0x0065
                android.support.v7.widget.RecyclerView r9 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$LayoutManager r9 = r9.mLayout
                android.support.v7.widget.RecyclerView r10 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$Recycler r10 = r10.mRecycler
                android.support.v7.widget.RecyclerView r11 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r11 = r11.mState
                int r9 = r9.scrollHorizontallyBy(r7, r10, r11)
                int r10 = r7 - r9
                goto L_0x0067
            L_0x0065:
                r9 = 0
                r10 = 0
            L_0x0067:
                if (r8 == 0) goto L_0x007c
                android.support.v7.widget.RecyclerView r11 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$LayoutManager r11 = r11.mLayout
                android.support.v7.widget.RecyclerView r12 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$Recycler r12 = r12.mRecycler
                android.support.v7.widget.RecyclerView r13 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r13 = r13.mState
                int r11 = r11.scrollVerticallyBy(r8, r12, r13)
                int r12 = r8 - r11
                goto L_0x007e
            L_0x007c:
                r11 = 0
                r12 = 0
            L_0x007e:
                android.support.v4.os.TraceCompat.endSection()
                android.support.v7.widget.RecyclerView r13 = android.support.v7.widget.RecyclerView.this
                r13.repositionShadowingViews()
                android.support.v7.widget.RecyclerView r13 = android.support.v7.widget.RecyclerView.this
                r13.onExitLayoutOrScroll()
                android.support.v7.widget.RecyclerView r13 = android.support.v7.widget.RecyclerView.this
                r13.resumeRequestLayout(r1)
                if (r4 == 0) goto L_0x00bb
                boolean r13 = r4.j
                if (r13 != 0) goto L_0x00bb
                boolean r13 = r4.k
                if (r13 == 0) goto L_0x00bb
                android.support.v7.widget.RecyclerView r13 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$State r13 = r13.mState
                int r13 = r13.a()
                if (r13 != 0) goto L_0x00a8
                r4.b()
                goto L_0x00bb
            L_0x00a8:
                int r14 = r4.g
                if (r14 < r13) goto L_0x00af
                int r13 = r13 - r2
                r4.g = r13
            L_0x00af:
                int r13 = r7 - r10
                int r14 = r8 - r12
                android.support.v7.widget.RecyclerView.SmoothScroller.a(r4, r13, r14)
                goto L_0x00bb
            L_0x00b7:
                r9 = 0
                r10 = 0
                r11 = 0
                r12 = 0
            L_0x00bb:
                android.support.v7.widget.RecyclerView r13 = android.support.v7.widget.RecyclerView.this
                java.util.ArrayList r13 = r13.mItemDecorations
                boolean r13 = r13.isEmpty()
                if (r13 != 0) goto L_0x00cc
                android.support.v7.widget.RecyclerView r13 = android.support.v7.widget.RecyclerView.this
                r13.invalidate()
            L_0x00cc:
                android.support.v7.widget.RecyclerView r13 = android.support.v7.widget.RecyclerView.this
                int r13 = android.support.v4.view.ViewCompat.getOverScrollMode(r13)
                r14 = 2
                if (r13 == r14) goto L_0x00da
                android.support.v7.widget.RecyclerView r13 = android.support.v7.widget.RecyclerView.this
                r13.considerReleasingGlowsOnScroll(r7, r8)
            L_0x00da:
                if (r10 != 0) goto L_0x00de
                if (r12 == 0) goto L_0x011c
            L_0x00de:
                float r13 = r3.getCurrVelocity()
                int r13 = (int) r13
                if (r10 == r5) goto L_0x00ed
                if (r10 >= 0) goto L_0x00e9
                int r15 = -r13
                goto L_0x00ee
            L_0x00e9:
                if (r10 <= 0) goto L_0x00ed
                r15 = r13
                goto L_0x00ee
            L_0x00ed:
                r15 = 0
            L_0x00ee:
                if (r12 == r6) goto L_0x00f7
                if (r12 >= 0) goto L_0x00f4
                int r13 = -r13
                goto L_0x00f8
            L_0x00f4:
                if (r12 <= 0) goto L_0x00f7
                goto L_0x00f8
            L_0x00f7:
                r13 = 0
            L_0x00f8:
                android.support.v7.widget.RecyclerView r2 = android.support.v7.widget.RecyclerView.this
                int r2 = android.support.v4.view.ViewCompat.getOverScrollMode(r2)
                if (r2 == r14) goto L_0x0105
                android.support.v7.widget.RecyclerView r2 = android.support.v7.widget.RecyclerView.this
                r2.absorbGlows(r15, r13)
            L_0x0105:
                if (r15 != 0) goto L_0x010f
                if (r10 == r5) goto L_0x010f
                int r2 = r3.getFinalX()
                if (r2 != 0) goto L_0x011c
            L_0x010f:
                if (r13 != 0) goto L_0x0119
                if (r12 == r6) goto L_0x0119
                int r2 = r3.getFinalY()
                if (r2 != 0) goto L_0x011c
            L_0x0119:
                r3.abortAnimation()
            L_0x011c:
                if (r9 != 0) goto L_0x0120
                if (r11 == 0) goto L_0x0125
            L_0x0120:
                android.support.v7.widget.RecyclerView r2 = android.support.v7.widget.RecyclerView.this
                r2.dispatchOnScrolled(r9, r11)
            L_0x0125:
                android.support.v7.widget.RecyclerView r2 = android.support.v7.widget.RecyclerView.this
                boolean r2 = r2.awakenScrollBars()
                if (r2 != 0) goto L_0x0132
                android.support.v7.widget.RecyclerView r2 = android.support.v7.widget.RecyclerView.this
                r2.invalidate()
            L_0x0132:
                if (r8 == 0) goto L_0x0142
                android.support.v7.widget.RecyclerView r2 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$LayoutManager r2 = r2.mLayout
                boolean r2 = r2.canScrollVertically()
                if (r2 == 0) goto L_0x0142
                if (r11 != r8) goto L_0x0142
                r2 = 1
                goto L_0x0143
            L_0x0142:
                r2 = 0
            L_0x0143:
                if (r7 == 0) goto L_0x0153
                android.support.v7.widget.RecyclerView r5 = android.support.v7.widget.RecyclerView.this
                android.support.v7.widget.RecyclerView$LayoutManager r5 = r5.mLayout
                boolean r5 = r5.canScrollHorizontally()
                if (r5 == 0) goto L_0x0153
                if (r9 != r7) goto L_0x0153
                r5 = 1
                goto L_0x0154
            L_0x0153:
                r5 = 0
            L_0x0154:
                if (r7 != 0) goto L_0x0158
                if (r8 == 0) goto L_0x0160
            L_0x0158:
                if (r5 != 0) goto L_0x0160
                if (r2 == 0) goto L_0x015d
                goto L_0x0160
            L_0x015d:
                r16 = 0
                goto L_0x0162
            L_0x0160:
                r16 = 1
            L_0x0162:
                boolean r2 = r3.isFinished()
                if (r2 != 0) goto L_0x016f
                if (r16 != 0) goto L_0x016b
                goto L_0x016f
            L_0x016b:
                r17.a()
                goto L_0x0174
            L_0x016f:
                android.support.v7.widget.RecyclerView r2 = android.support.v7.widget.RecyclerView.this
                r2.setScrollState(r1)
            L_0x0174:
                if (r4 == 0) goto L_0x0184
                boolean r2 = r4.j
                if (r2 == 0) goto L_0x017d
                android.support.v7.widget.RecyclerView.SmoothScroller.a(r4, r1, r1)
            L_0x017d:
                boolean r2 = r0.g
                if (r2 != 0) goto L_0x0184
                r4.b()
            L_0x0184:
                r0.f = r1
                boolean r1 = r0.g
                if (r1 == 0) goto L_0x018d
                r17.a()
            L_0x018d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.ViewFlinger.run():void");
        }

        /* access modifiers changed from: 0000 */
        public final void a() {
            if (this.f) {
                this.g = true;
                return;
            }
            RecyclerView.this.removeCallbacks(this);
            ViewCompat.postOnAnimation(RecyclerView.this, this);
        }

        /* access modifiers changed from: 0000 */
        public final void a(int i, int i2) {
            a(i, i2, b(i, i2));
        }

        private static float a(float f2) {
            return (float) Math.sin((double) ((float) (((double) (f2 - 0.5f)) * 0.4712389167638204d)));
        }

        private int b(int i, int i2) {
            int i3;
            int abs = Math.abs(i);
            int abs2 = Math.abs(i2);
            boolean z = abs > abs2;
            int sqrt = (int) Math.sqrt(0.0d);
            int sqrt2 = (int) Math.sqrt((double) ((i * i) + (i2 * i2)));
            int width = z ? RecyclerView.this.getWidth() : RecyclerView.this.getHeight();
            int i4 = width / 2;
            float f2 = (float) width;
            float f3 = (float) i4;
            float a2 = f3 + (a(Math.min(1.0f, (((float) sqrt2) * 1.0f) / f2)) * f3);
            if (sqrt > 0) {
                i3 = Math.round(Math.abs(a2 / ((float) sqrt)) * 1000.0f) * 4;
            } else {
                if (!z) {
                    abs = abs2;
                }
                i3 = (int) (((((float) abs) / f2) + 1.0f) * 300.0f);
            }
            return Math.min(i3, 2000);
        }

        public final void a(int i, int i2, int i3) {
            a(i, i2, i3, RecyclerView.sQuinticInterpolator);
        }

        public final void a(int i, int i2, int i3, Interpolator interpolator) {
            if (this.e != interpolator) {
                this.e = interpolator;
                this.c = ScrollerCompat.create(RecyclerView.this.getContext(), interpolator);
            }
            RecyclerView.this.setScrollState(2);
            this.b = 0;
            this.a = 0;
            this.c.startScroll(0, 0, i, i2, i3);
            a();
        }

        public final void b() {
            RecyclerView.this.removeCallbacks(this);
            this.c.abortAnimation();
        }
    }

    public static abstract class ViewHolder {
        static final int FLAG_ADAPTER_FULLUPDATE = 1024;
        static final int FLAG_ADAPTER_POSITION_UNKNOWN = 512;
        static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        static final int FLAG_BOUNCED_FROM_HIDDEN_LIST = 8192;
        static final int FLAG_BOUND = 1;
        static final int FLAG_IGNORE = 128;
        static final int FLAG_INVALID = 4;
        static final int FLAG_MOVED = 2048;
        static final int FLAG_NOT_RECYCLABLE = 16;
        static final int FLAG_REMOVED = 8;
        static final int FLAG_RETURNED_FROM_SCRAP = 32;
        static final int FLAG_TMP_DETACHED = 256;
        static final int FLAG_UPDATE = 2;
        private static final List<Object> FULLUPDATE_PAYLOADS = Collections.EMPTY_LIST;
        public final View itemView;
        /* access modifiers changed from: private */
        public int mFlags;
        /* access modifiers changed from: private */
        public boolean mInChangeScrap = false;
        private int mIsRecyclableCount = 0;
        long mItemId = -1;
        int mItemViewType = -1;
        int mOldPosition = -1;
        RecyclerView mOwnerRecyclerView;
        List<Object> mPayloads = null;
        int mPosition = -1;
        int mPreLayoutPosition = -1;
        /* access modifiers changed from: private */
        public Recycler mScrapContainer = null;
        ViewHolder mShadowedHolder = null;
        ViewHolder mShadowingHolder = null;
        List<Object> mUnmodifiedPayloads = null;
        private int mWasImportantForAccessibilityBeforeHidden = 0;

        public ViewHolder(View view) {
            if (view == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = view;
        }

        /* access modifiers changed from: 0000 */
        public void flagRemovedAndOffsetPosition(int i, int i2, boolean z) {
            addFlags(8);
            offsetPosition(i2, z);
            this.mPosition = i;
        }

        /* access modifiers changed from: 0000 */
        public void offsetPosition(int i, boolean z) {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
            if (this.mPreLayoutPosition == -1) {
                this.mPreLayoutPosition = this.mPosition;
            }
            if (z) {
                this.mPreLayoutPosition += i;
            }
            this.mPosition += i;
            if (this.itemView.getLayoutParams() != null) {
                ((LayoutParams) this.itemView.getLayoutParams()).e = true;
            }
        }

        /* access modifiers changed from: 0000 */
        public void clearOldPosition() {
            this.mOldPosition = -1;
            this.mPreLayoutPosition = -1;
        }

        /* access modifiers changed from: 0000 */
        public void saveOldPosition() {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean shouldIgnore() {
            return (this.mFlags & 128) != 0;
        }

        @Deprecated
        public final int getPosition() {
            return this.mPreLayoutPosition == -1 ? this.mPosition : this.mPreLayoutPosition;
        }

        public final int getLayoutPosition() {
            return this.mPreLayoutPosition == -1 ? this.mPosition : this.mPreLayoutPosition;
        }

        public final int getAdapterPosition() {
            if (this.mOwnerRecyclerView == null) {
                return -1;
            }
            return this.mOwnerRecyclerView.getAdapterPositionFor(this);
        }

        public final int getOldPosition() {
            return this.mOldPosition;
        }

        public final long getItemId() {
            return this.mItemId;
        }

        public final int getItemViewType() {
            return this.mItemViewType;
        }

        /* access modifiers changed from: 0000 */
        public boolean isScrap() {
            return this.mScrapContainer != null;
        }

        /* access modifiers changed from: 0000 */
        public void unScrap() {
            this.mScrapContainer.b(this);
        }

        /* access modifiers changed from: 0000 */
        public boolean wasReturnedFromScrap() {
            return (this.mFlags & 32) != 0;
        }

        /* access modifiers changed from: 0000 */
        public void clearReturnedFromScrapFlag() {
            this.mFlags &= -33;
        }

        /* access modifiers changed from: 0000 */
        public void clearTmpDetachFlag() {
            this.mFlags &= -257;
        }

        /* access modifiers changed from: 0000 */
        public void stopIgnoring() {
            this.mFlags &= -129;
        }

        /* access modifiers changed from: 0000 */
        public void setScrapContainer(Recycler recycler, boolean z) {
            this.mScrapContainer = recycler;
            this.mInChangeScrap = z;
        }

        /* access modifiers changed from: 0000 */
        public boolean isInvalid() {
            return (this.mFlags & 4) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean needsUpdate() {
            return (this.mFlags & 2) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean isBound() {
            return (this.mFlags & 1) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean isRemoved() {
            return (this.mFlags & 8) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean hasAnyOfTheFlags(int i) {
            return (i & this.mFlags) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean isTmpDetached() {
            return (this.mFlags & 256) != 0;
        }

        /* access modifiers changed from: 0000 */
        public boolean isAdapterPositionUnknown() {
            return (this.mFlags & 512) != 0 || isInvalid();
        }

        /* access modifiers changed from: 0000 */
        public void setFlags(int i, int i2) {
            this.mFlags = (i & i2) | (this.mFlags & (~i2));
        }

        /* access modifiers changed from: 0000 */
        public void addFlags(int i) {
            this.mFlags = i | this.mFlags;
        }

        /* access modifiers changed from: 0000 */
        public void addChangePayload(Object obj) {
            if (obj == null) {
                addFlags(1024);
                return;
            }
            if ((1024 & this.mFlags) == 0) {
                createPayloadsIfNeeded();
                this.mPayloads.add(obj);
            }
        }

        private void createPayloadsIfNeeded() {
            if (this.mPayloads == null) {
                this.mPayloads = new ArrayList();
                this.mUnmodifiedPayloads = Collections.unmodifiableList(this.mPayloads);
            }
        }

        /* access modifiers changed from: 0000 */
        public void clearPayload() {
            if (this.mPayloads != null) {
                this.mPayloads.clear();
            }
            this.mFlags &= -1025;
        }

        /* access modifiers changed from: 0000 */
        public List<Object> getUnmodifiedPayloads() {
            if ((this.mFlags & 1024) != 0) {
                return FULLUPDATE_PAYLOADS;
            }
            if (this.mPayloads == null || this.mPayloads.size() == 0) {
                return FULLUPDATE_PAYLOADS;
            }
            return this.mUnmodifiedPayloads;
        }

        /* access modifiers changed from: 0000 */
        public void resetInternal() {
            this.mFlags = 0;
            this.mPosition = -1;
            this.mOldPosition = -1;
            this.mItemId = -1;
            this.mPreLayoutPosition = -1;
            this.mIsRecyclableCount = 0;
            this.mShadowedHolder = null;
            this.mShadowingHolder = null;
            clearPayload();
            this.mWasImportantForAccessibilityBeforeHidden = 0;
        }

        /* access modifiers changed from: private */
        public void onEnteredHiddenState() {
            this.mWasImportantForAccessibilityBeforeHidden = ViewCompat.getImportantForAccessibility(this.itemView);
            ViewCompat.setImportantForAccessibility(this.itemView, 4);
        }

        /* access modifiers changed from: private */
        public void onLeftHiddenState() {
            ViewCompat.setImportantForAccessibility(this.itemView, this.mWasImportantForAccessibilityBeforeHidden);
            this.mWasImportantForAccessibilityBeforeHidden = 0;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("ViewHolder{");
            sb.append(Integer.toHexString(hashCode()));
            sb.append(" position=");
            sb.append(this.mPosition);
            sb.append(" id=");
            sb.append(this.mItemId);
            sb.append(", oldPos=");
            sb.append(this.mOldPosition);
            sb.append(", pLpos:");
            sb.append(this.mPreLayoutPosition);
            StringBuilder sb2 = new StringBuilder(sb.toString());
            if (isScrap()) {
                sb2.append(" scrap ");
                sb2.append(this.mInChangeScrap ? "[changeScrap]" : "[attachedScrap]");
            }
            if (isInvalid()) {
                sb2.append(" invalid");
            }
            if (!isBound()) {
                sb2.append(" unbound");
            }
            if (needsUpdate()) {
                sb2.append(" update");
            }
            if (isRemoved()) {
                sb2.append(" removed");
            }
            if (shouldIgnore()) {
                sb2.append(" ignored");
            }
            if (isTmpDetached()) {
                sb2.append(" tmpDetached");
            }
            if (!isRecyclable()) {
                StringBuilder sb3 = new StringBuilder(" not recyclable(");
                sb3.append(this.mIsRecyclableCount);
                sb3.append(")");
                sb2.append(sb3.toString());
            }
            if (isAdapterPositionUnknown()) {
                sb2.append(" undefined adapter position");
            }
            if (this.itemView.getParent() == null) {
                sb2.append(" no parent");
            }
            sb2.append(h.d);
            return sb2.toString();
        }

        public final void setIsRecyclable(boolean z) {
            this.mIsRecyclableCount = z ? this.mIsRecyclableCount - 1 : this.mIsRecyclableCount + 1;
            if (this.mIsRecyclableCount < 0) {
                this.mIsRecyclableCount = 0;
                new StringBuilder("isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for ").append(this);
            } else if (z || this.mIsRecyclableCount != 1) {
                if (z && this.mIsRecyclableCount == 0) {
                    this.mFlags &= -17;
                }
            } else {
                this.mFlags |= 16;
            }
        }

        public final boolean isRecyclable() {
            return (this.mFlags & 16) == 0 && !ViewCompat.hasTransientState(this.itemView);
        }

        /* access modifiers changed from: private */
        public boolean shouldBeKeptAsChild() {
            return (this.mFlags & 16) != 0;
        }

        /* access modifiers changed from: private */
        public boolean doesTransientStatePreventRecycling() {
            return (this.mFlags & 16) == 0 && ViewCompat.hasTransientState(this.itemView);
        }

        /* access modifiers changed from: 0000 */
        public boolean isUpdated() {
            return (this.mFlags & 2) != 0;
        }
    }

    public void onChildAttachedToWindow(View view) {
    }

    public void onChildDetachedFromWindow(View view) {
    }

    public void onScrollStateChanged(int i) {
    }

    public void onScrolled(int i, int i2) {
    }

    public void scrollTo(int i, int i2) {
    }

    public RecyclerView(Context context) {
        this(context, null);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mObserver = new RecyclerViewDataObserver(this, 0);
        this.mRecycler = new Recycler();
        this.mViewInfoStore = new ViewInfoStore();
        this.mUpdateChildViewsRunnable = new Runnable() {
            public void run() {
                if (RecyclerView.this.mFirstLayoutComplete && !RecyclerView.this.isLayoutRequested()) {
                    if (RecyclerView.this.mLayoutFrozen) {
                        RecyclerView.this.mLayoutRequestEaten = true;
                    } else {
                        RecyclerView.this.consumePendingUpdateOperations();
                    }
                }
            }
        };
        this.mTempRect = new Rect();
        this.mItemDecorations = new ArrayList<>();
        this.mOnItemTouchListeners = new ArrayList<>();
        this.mEatRequestLayout = 0;
        this.mDataSetHasChangedAfterLayout = false;
        this.mLayoutOrScrollCounter = 0;
        this.mItemAnimator = new DefaultItemAnimator();
        this.mScrollState = 0;
        this.mScrollPointerId = -1;
        this.mScrollFactor = Float.MIN_VALUE;
        this.mViewFlinger = new ViewFlinger();
        this.mState = new State();
        this.mItemsAddedOrRemoved = false;
        this.mItemsChanged = false;
        this.mItemAnimatorListener = new ItemAnimatorRestoreListener(this, 0);
        this.mPostedAnimatorRunner = false;
        this.mMinMaxLayoutPositions = new int[2];
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.mNestedOffsets = new int[2];
        this.mItemAnimatorRunner = new Runnable() {
            public void run() {
                if (RecyclerView.this.mItemAnimator != null) {
                    RecyclerView.this.mItemAnimator.a();
                }
                RecyclerView.this.mPostedAnimatorRunner = false;
            }
        };
        this.mViewInfoProcessCallback = new ProcessCallback() {
            public final void a(ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @Nullable ItemHolderInfo itemHolderInfo2) {
                RecyclerView.this.mRecycler.b(viewHolder);
                RecyclerView.this.animateDisappearance(viewHolder, itemHolderInfo, itemHolderInfo2);
            }

            public final void b(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo, ItemHolderInfo itemHolderInfo2) {
                RecyclerView.this.animateAppearance(viewHolder, itemHolderInfo, itemHolderInfo2);
            }

            public final void c(ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2) {
                viewHolder.setIsRecyclable(false);
                if (RecyclerView.this.mDataSetHasChangedAfterLayout) {
                    if (RecyclerView.this.mItemAnimator.a(viewHolder, viewHolder, itemHolderInfo, itemHolderInfo2)) {
                        RecyclerView.this.postAnimationRunner();
                    }
                } else if (RecyclerView.this.mItemAnimator.c(viewHolder, itemHolderInfo, itemHolderInfo2)) {
                    RecyclerView.this.postAnimationRunner();
                }
            }

            public final void a(ViewHolder viewHolder) {
                RecyclerView.this.mLayout.removeAndRecycleView(viewHolder.itemView, RecyclerView.this.mRecycler);
            }
        };
        setScrollContainer(true);
        setFocusableInTouchMode(true);
        this.mPostUpdatesOnAnimation = VERSION.SDK_INT >= 16;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        setWillNotDraw(ViewCompat.getOverScrollMode(this) == 2);
        this.mItemAnimator.a = this.mItemAnimatorListener;
        initAdapterManager();
        initChildrenHelper();
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
        this.mAccessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        setAccessibilityDelegateCompat(new RecyclerViewAccessibilityDelegate(this));
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RecyclerView, i, 0);
            String string = obtainStyledAttributes.getString(R.styleable.RecyclerView_layoutManager);
            obtainStyledAttributes.recycle();
            createLayoutManager(context, string, attributeSet, i, 0);
        }
        this.mScrollingChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }

    public RecyclerViewAccessibilityDelegate getCompatAccessibilityDelegate() {
        return this.mAccessibilityDelegate;
    }

    public void setAccessibilityDelegateCompat(RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate) {
        this.mAccessibilityDelegate = recyclerViewAccessibilityDelegate;
        ViewCompat.setAccessibilityDelegate(this, this.mAccessibilityDelegate);
    }

    private void createLayoutManager(Context context, String str, AttributeSet attributeSet, int i, int i2) {
        ClassLoader classLoader;
        Constructor<? extends U> constructor;
        if (str != null) {
            String trim = str.trim();
            if (trim.length() != 0) {
                String fullClassName = getFullClassName(context, trim);
                try {
                    if (isInEditMode()) {
                        classLoader = getClass().getClassLoader();
                    } else {
                        classLoader = context.getClassLoader();
                    }
                    Class<? extends U> asSubclass = classLoader.loadClass(fullClassName).asSubclass(LayoutManager.class);
                    Object[] objArr = null;
                    try {
                        constructor = asSubclass.getConstructor(LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE);
                        objArr = new Object[]{context, attributeSet, Integer.valueOf(i), Integer.valueOf(i2)};
                    } catch (NoSuchMethodException e) {
                        constructor = asSubclass.getConstructor(new Class[0]);
                    }
                    constructor.setAccessible(true);
                    setLayoutManager((LayoutManager) constructor.newInstance(objArr));
                } catch (NoSuchMethodException e2) {
                    e2.initCause(e);
                    StringBuilder sb = new StringBuilder();
                    sb.append(attributeSet.getPositionDescription());
                    sb.append(": Error creating LayoutManager ");
                    sb.append(fullClassName);
                    throw new IllegalStateException(sb.toString(), e2);
                } catch (ClassNotFoundException e3) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(attributeSet.getPositionDescription());
                    sb2.append(": Unable to find LayoutManager ");
                    sb2.append(fullClassName);
                    throw new IllegalStateException(sb2.toString(), e3);
                } catch (InvocationTargetException e4) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(attributeSet.getPositionDescription());
                    sb3.append(": Could not instantiate the LayoutManager: ");
                    sb3.append(fullClassName);
                    throw new IllegalStateException(sb3.toString(), e4);
                } catch (InstantiationException e5) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(attributeSet.getPositionDescription());
                    sb4.append(": Could not instantiate the LayoutManager: ");
                    sb4.append(fullClassName);
                    throw new IllegalStateException(sb4.toString(), e5);
                } catch (IllegalAccessException e6) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(attributeSet.getPositionDescription());
                    sb5.append(": Cannot access non-public constructor ");
                    sb5.append(fullClassName);
                    throw new IllegalStateException(sb5.toString(), e6);
                } catch (ClassCastException e7) {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(attributeSet.getPositionDescription());
                    sb6.append(": Class is not a LayoutManager ");
                    sb6.append(fullClassName);
                    throw new IllegalStateException(sb6.toString(), e7);
                }
            }
        }
    }

    private String getFullClassName(Context context, String str) {
        if (str.charAt(0) == '.') {
            StringBuilder sb = new StringBuilder();
            sb.append(context.getPackageName());
            sb.append(str);
            return sb.toString();
        } else if (str.contains(".")) {
            return str;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(RecyclerView.class.getPackage().getName());
            sb2.append(DjangoUtils.EXTENSION_SEPARATOR);
            sb2.append(str);
            return sb2.toString();
        }
    }

    private void initChildrenHelper() {
        this.mChildHelper = new ChildHelper(new Callback() {
            public final int a() {
                return RecyclerView.this.getChildCount();
            }

            public final void a(View view, int i) {
                RecyclerView.this.addView(view, i);
                RecyclerView.this.dispatchChildAttached(view);
            }

            public final int a(View view) {
                return RecyclerView.this.indexOfChild(view);
            }

            public final void a(int i) {
                View childAt = RecyclerView.this.getChildAt(i);
                if (childAt != null) {
                    RecyclerView.this.dispatchChildDetached(childAt);
                }
                RecyclerView.this.removeViewAt(i);
            }

            public final View b(int i) {
                return RecyclerView.this.getChildAt(i);
            }

            public final ViewHolder b(View view) {
                return RecyclerView.getChildViewHolderInt(view);
            }

            public final void a(View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
                if (childViewHolderInt != null) {
                    if (childViewHolderInt.isTmpDetached() || childViewHolderInt.shouldIgnore()) {
                        childViewHolderInt.clearTmpDetachFlag();
                    } else {
                        throw new IllegalArgumentException("Called attach on a child which is not detached: ".concat(String.valueOf(childViewHolderInt)));
                    }
                }
                RecyclerView.this.attachViewToParent(view, i, layoutParams);
            }

            public final void c(int i) {
                View b = b(i);
                if (b != null) {
                    ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(b);
                    if (childViewHolderInt != null) {
                        if (!childViewHolderInt.isTmpDetached() || childViewHolderInt.shouldIgnore()) {
                            childViewHolderInt.addFlags(256);
                        } else {
                            throw new IllegalArgumentException("called detach on an already detached child ".concat(String.valueOf(childViewHolderInt)));
                        }
                    }
                }
                RecyclerView.this.detachViewFromParent(i);
            }

            public final void c(View view) {
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
                if (childViewHolderInt != null) {
                    childViewHolderInt.onEnteredHiddenState();
                }
            }

            public final void d(View view) {
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
                if (childViewHolderInt != null) {
                    childViewHolderInt.onLeftHiddenState();
                }
            }

            public final void b() {
                int childCount = RecyclerView.this.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    RecyclerView.this.dispatchChildDetached(b(i));
                }
                RecyclerView.this.removeAllViews();
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void initAdapterManager() {
        this.mAdapterHelper = new AdapterHelper(new Callback() {
            public final ViewHolder a(int i) {
                ViewHolder findViewHolderForPosition = RecyclerView.this.findViewHolderForPosition(i, true);
                if (findViewHolderForPosition != null && !RecyclerView.this.mChildHelper.d(findViewHolderForPosition.itemView)) {
                    return findViewHolderForPosition;
                }
                return null;
            }

            public final void a(int i, int i2) {
                RecyclerView.this.offsetPositionRecordsForRemove(i, i2, true);
                RecyclerView.this.mItemsAddedOrRemoved = true;
                State.a(RecyclerView.this.mState, i2);
            }

            public final void b(int i, int i2) {
                RecyclerView.this.offsetPositionRecordsForRemove(i, i2, false);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            public final void a(int i, int i2, Object obj) {
                RecyclerView.this.viewRangeUpdate(i, i2, obj);
                RecyclerView.this.mItemsChanged = true;
            }

            public final void a(UpdateOp updateOp) {
                c(updateOp);
            }

            private void c(UpdateOp updateOp) {
                int i = updateOp.a;
                if (i != 4) {
                    if (i != 8) {
                        switch (i) {
                            case 1:
                                RecyclerView.this.mLayout.onItemsAdded(RecyclerView.this, updateOp.b, updateOp.d);
                                return;
                            case 2:
                                RecyclerView.this.mLayout.onItemsRemoved(RecyclerView.this, updateOp.b, updateOp.d);
                                return;
                        }
                    } else {
                        RecyclerView.this.mLayout.onItemsMoved(RecyclerView.this, updateOp.b, updateOp.d, 1);
                    }
                    return;
                }
                RecyclerView.this.mLayout.onItemsUpdated(RecyclerView.this, updateOp.b, updateOp.d, updateOp.c);
            }

            public final void b(UpdateOp updateOp) {
                c(updateOp);
            }

            public final void c(int i, int i2) {
                RecyclerView.this.offsetPositionRecordsForInsert(i, i2);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            public final void d(int i, int i2) {
                RecyclerView.this.offsetPositionRecordsForMove(i, i2);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }
        });
    }

    public void setHasFixedSize(boolean z) {
        this.mHasFixedSize = z;
    }

    public boolean hasFixedSize() {
        return this.mHasFixedSize;
    }

    public void setClipToPadding(boolean z) {
        if (z != this.mClipToPadding) {
            invalidateGlows();
        }
        this.mClipToPadding = z;
        super.setClipToPadding(z);
        if (this.mFirstLayoutComplete) {
            requestLayout();
        }
    }

    public void setScrollingTouchSlop(int i) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        switch (i) {
            case 0:
                break;
            case 1:
                this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
                return;
            default:
                StringBuilder sb = new StringBuilder("setScrollingTouchSlop(): bad argument constant ");
                sb.append(i);
                sb.append("; using default value");
                break;
        }
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
    }

    public void swapAdapter(Adapter adapter, boolean z) {
        setLayoutFrozen(false);
        setAdapterInternal(adapter, true, z);
        setDataSetChangedAfterLayout();
        requestLayout();
    }

    public void setAdapter(Adapter adapter) {
        setLayoutFrozen(false);
        setAdapterInternal(adapter, false, true);
        requestLayout();
    }

    private void setAdapterInternal(Adapter adapter, boolean z, boolean z2) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterAdapterDataObserver(this.mObserver);
            this.mAdapter.onDetachedFromRecyclerView(this);
        }
        if (!z || z2) {
            if (this.mItemAnimator != null) {
                this.mItemAnimator.c();
            }
            if (this.mLayout != null) {
                this.mLayout.removeAndRecycleAllViews(this.mRecycler);
                this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
            }
            this.mRecycler.a();
        }
        this.mAdapterHelper.a();
        Adapter adapter2 = this.mAdapter;
        this.mAdapter = adapter;
        if (adapter != null) {
            adapter.registerAdapterDataObserver(this.mObserver);
            adapter.onAttachedToRecyclerView(this);
        }
        if (this.mLayout != null) {
            this.mLayout.onAdapterChanged(adapter2, this.mAdapter);
        }
        Recycler recycler = this.mRecycler;
        Adapter adapter3 = this.mAdapter;
        recycler.a();
        RecycledViewPool c = recycler.c();
        if (adapter2 != null) {
            c.b();
        }
        if (!z && c.b == 0) {
            c.a.clear();
        }
        if (adapter3 != null) {
            c.a();
        }
        this.mState.i = true;
        markKnownViewsInvalid();
    }

    public Adapter getAdapter() {
        return this.mAdapter;
    }

    public void setRecyclerListener(RecyclerListener recyclerListener) {
        this.mRecyclerListener = recyclerListener;
    }

    public int getBaseline() {
        if (this.mLayout != null) {
            return this.mLayout.getBaseline();
        }
        return super.getBaseline();
    }

    public void addOnChildAttachStateChangeListener(OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        if (this.mOnChildAttachStateListeners == null) {
            this.mOnChildAttachStateListeners = new ArrayList();
        }
        this.mOnChildAttachStateListeners.add(onChildAttachStateChangeListener);
    }

    public void removeOnChildAttachStateChangeListener(OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        if (this.mOnChildAttachStateListeners != null) {
            this.mOnChildAttachStateListeners.remove(onChildAttachStateChangeListener);
        }
    }

    public void clearOnChildAttachStateChangeListeners() {
        if (this.mOnChildAttachStateListeners != null) {
            this.mOnChildAttachStateListeners.clear();
        }
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        if (layoutManager != this.mLayout) {
            stopScroll();
            if (this.mLayout != null) {
                if (this.mIsAttached) {
                    this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
                }
                this.mLayout.setRecyclerView(null);
            }
            this.mRecycler.a();
            ChildHelper childHelper = this.mChildHelper;
            Bucket bucket = childHelper.b;
            while (true) {
                bucket.a = 0;
                if (bucket.b == null) {
                    break;
                }
                bucket = bucket.b;
            }
            for (int size = childHelper.c.size() - 1; size >= 0; size--) {
                childHelper.a.d(childHelper.c.get(size));
                childHelper.c.remove(size);
            }
            childHelper.a.b();
            this.mLayout = layoutManager;
            if (layoutManager != null) {
                if (layoutManager.mRecyclerView != null) {
                    StringBuilder sb = new StringBuilder("LayoutManager ");
                    sb.append(layoutManager);
                    sb.append(" is already attached to a RecyclerView: ");
                    sb.append(layoutManager.mRecyclerView);
                    throw new IllegalArgumentException(sb.toString());
                }
                this.mLayout.setRecyclerView(this);
                if (this.mIsAttached) {
                    this.mLayout.dispatchAttachedToWindow(this);
                }
            }
            requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.mPendingSavedState != null) {
            savedState.a = this.mPendingSavedState.a;
        } else if (this.mLayout != null) {
            savedState.a = this.mLayout.onSaveInstanceState();
        } else {
            savedState.a = null;
        }
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        this.mPendingSavedState = (SavedState) parcelable;
        super.onRestoreInstanceState(this.mPendingSavedState.getSuperState());
        if (this.mLayout != null && this.mPendingSavedState.a != null) {
            this.mLayout.onRestoreInstanceState(this.mPendingSavedState.a);
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchFreezeSelfOnly(sparseArray);
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    private void addAnimatingView(ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        boolean z = view.getParent() == this;
        this.mRecycler.b(getChildViewHolder(view));
        if (viewHolder.isTmpDetached()) {
            this.mChildHelper.a(view, -1, view.getLayoutParams(), true);
        } else if (!z) {
            this.mChildHelper.a(view, -1, true);
        } else {
            ChildHelper childHelper = this.mChildHelper;
            int a = childHelper.a.a(view);
            if (a < 0) {
                throw new IllegalArgumentException("view is not a child, cannot hide ".concat(String.valueOf(view)));
            }
            childHelper.b.a(a);
            childHelper.a(view);
        }
    }

    /* access modifiers changed from: private */
    public boolean removeAnimatingView(View view) {
        eatRequestLayout();
        ChildHelper childHelper = this.mChildHelper;
        int a = childHelper.a.a(view);
        boolean z = true;
        if (a == -1) {
            childHelper.b(view);
        } else if (childHelper.b.c(a)) {
            childHelper.b.d(a);
            childHelper.b(view);
            childHelper.a.a(a);
        } else {
            z = false;
        }
        if (z) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(view);
            this.mRecycler.b(childViewHolderInt);
            this.mRecycler.a(childViewHolderInt);
        }
        resumeRequestLayout(!z);
        return z;
    }

    public LayoutManager getLayoutManager() {
        return this.mLayout;
    }

    public RecycledViewPool getRecycledViewPool() {
        return this.mRecycler.c();
    }

    public void setRecycledViewPool(RecycledViewPool recycledViewPool) {
        Recycler recycler = this.mRecycler;
        if (recycler.f != null) {
            recycler.f.b();
        }
        recycler.f = recycledViewPool;
        if (recycledViewPool != null) {
            RecycledViewPool recycledViewPool2 = recycler.f;
            RecyclerView.this.getAdapter();
            recycledViewPool2.a();
        }
    }

    public void setViewCacheExtension(ViewCacheExtension viewCacheExtension) {
        this.mRecycler.g = viewCacheExtension;
    }

    public void setItemViewCacheSize(int i) {
        Recycler recycler = this.mRecycler;
        recycler.e = i;
        for (int size = recycler.c.size() - 1; size >= 0 && recycler.c.size() > i; size--) {
            recycler.c(size);
        }
    }

    public int getScrollState() {
        return this.mScrollState;
    }

    /* access modifiers changed from: private */
    public void setScrollState(int i) {
        if (i != this.mScrollState) {
            this.mScrollState = i;
            if (i != 2) {
                stopScrollersInternal();
            }
            dispatchOnScrollStateChanged(i);
        }
    }

    public void addItemDecoration(ItemDecoration itemDecoration, int i) {
        if (this.mLayout != null) {
            this.mLayout.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
        }
        if (this.mItemDecorations.isEmpty()) {
            setWillNotDraw(false);
        }
        if (i < 0) {
            this.mItemDecorations.add(itemDecoration);
        } else {
            this.mItemDecorations.add(i, itemDecoration);
        }
        markItemDecorInsetsDirty();
        requestLayout();
    }

    public void addItemDecoration(ItemDecoration itemDecoration) {
        addItemDecoration(itemDecoration, -1);
    }

    public void removeItemDecoration(ItemDecoration itemDecoration) {
        if (this.mLayout != null) {
            this.mLayout.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout");
        }
        this.mItemDecorations.remove(itemDecoration);
        if (this.mItemDecorations.isEmpty()) {
            setWillNotDraw(ViewCompat.getOverScrollMode(this) == 2);
        }
        markItemDecorInsetsDirty();
        requestLayout();
    }

    public void setChildDrawingOrderCallback(ChildDrawingOrderCallback childDrawingOrderCallback) {
        if (childDrawingOrderCallback != this.mChildDrawingOrderCallback) {
            this.mChildDrawingOrderCallback = childDrawingOrderCallback;
            setChildrenDrawingOrderEnabled(this.mChildDrawingOrderCallback != null);
        }
    }

    @Deprecated
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mScrollListener = onScrollListener;
    }

    public void addOnScrollListener(OnScrollListener onScrollListener) {
        if (this.mScrollListeners == null) {
            this.mScrollListeners = new ArrayList();
        }
        this.mScrollListeners.add(onScrollListener);
    }

    public void removeOnScrollListener(OnScrollListener onScrollListener) {
        if (this.mScrollListeners != null) {
            this.mScrollListeners.remove(onScrollListener);
        }
    }

    public void clearOnScrollListeners() {
        if (this.mScrollListeners != null) {
            this.mScrollListeners.clear();
        }
    }

    public void scrollToPosition(int i) {
        if (!this.mLayoutFrozen) {
            stopScroll();
            if (this.mLayout != null) {
                this.mLayout.scrollToPosition(i);
                awakenScrollBars();
            }
        }
    }

    /* access modifiers changed from: private */
    public void jumpToPositionForSmoothScroller(int i) {
        if (this.mLayout != null) {
            this.mLayout.scrollToPosition(i);
            awakenScrollBars();
        }
    }

    public void smoothScrollToPosition(int i) {
        if (!this.mLayoutFrozen && this.mLayout != null) {
            this.mLayout.smoothScrollToPosition(this, this.mState, i);
        }
    }

    public void scrollBy(int i, int i2) {
        if (this.mLayout != null && !this.mLayoutFrozen) {
            boolean canScrollHorizontally = this.mLayout.canScrollHorizontally();
            boolean canScrollVertically = this.mLayout.canScrollVertically();
            if (canScrollHorizontally || canScrollVertically) {
                if (!canScrollHorizontally) {
                    i = 0;
                }
                if (!canScrollVertically) {
                    i2 = 0;
                }
                scrollByInternal(i, i2, null);
            }
        }
    }

    /* access modifiers changed from: private */
    public void consumePendingUpdateOperations() {
        if (this.mFirstLayoutComplete) {
            if (this.mDataSetHasChangedAfterLayout) {
                TraceCompat.beginSection(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
                dispatchLayout();
                TraceCompat.endSection();
            } else if (this.mAdapterHelper.d()) {
                if (!this.mAdapterHelper.a(4) || this.mAdapterHelper.a(11)) {
                    if (this.mAdapterHelper.d()) {
                        TraceCompat.beginSection(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
                        dispatchLayout();
                        TraceCompat.endSection();
                    }
                    return;
                }
                TraceCompat.beginSection(TRACE_HANDLE_ADAPTER_UPDATES_TAG);
                eatRequestLayout();
                this.mAdapterHelper.b();
                if (!this.mLayoutRequestEaten) {
                    if (hasUpdatedView()) {
                        dispatchLayout();
                    } else {
                        this.mAdapterHelper.c();
                    }
                }
                resumeRequestLayout(true);
                TraceCompat.endSection();
            }
        }
    }

    private boolean hasUpdatedView() {
        int a = this.mChildHelper.a();
        for (int i = 0; i < a; i++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.b(i));
            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore() && childViewHolderInt.isUpdated()) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean scrollByInternal(int i, int i2, MotionEvent motionEvent) {
        int i3;
        int i4;
        int i5;
        int i6;
        consumePendingUpdateOperations();
        if (this.mAdapter != null) {
            eatRequestLayout();
            onEnterLayoutOrScroll();
            TraceCompat.beginSection(TRACE_SCROLL_TAG);
            if (i != 0) {
                i6 = this.mLayout.scrollHorizontallyBy(i, this.mRecycler, this.mState);
                i5 = i - i6;
            } else {
                i6 = 0;
                i5 = 0;
            }
            if (i2 != 0) {
                i4 = this.mLayout.scrollVerticallyBy(i2, this.mRecycler, this.mState);
                i3 = i2 - i4;
            } else {
                i4 = 0;
                i3 = 0;
            }
            TraceCompat.endSection();
            repositionShadowingViews();
            onExitLayoutOrScroll();
            resumeRequestLayout(false);
        } else {
            i6 = 0;
            i5 = 0;
            i4 = 0;
            i3 = 0;
        }
        if (!this.mItemDecorations.isEmpty()) {
            invalidate();
        }
        if (dispatchNestedScroll(i6, i4, i5, i3, this.mScrollOffset)) {
            this.mLastTouchX -= this.mScrollOffset[0];
            this.mLastTouchY -= this.mScrollOffset[1];
            if (motionEvent != null) {
                motionEvent.offsetLocation((float) this.mScrollOffset[0], (float) this.mScrollOffset[1]);
            }
            int[] iArr = this.mNestedOffsets;
            iArr[0] = iArr[0] + this.mScrollOffset[0];
            int[] iArr2 = this.mNestedOffsets;
            iArr2[1] = iArr2[1] + this.mScrollOffset[1];
        } else if (ViewCompat.getOverScrollMode(this) != 2) {
            if (motionEvent != null) {
                pullGlows(motionEvent.getX(), (float) i5, motionEvent.getY(), (float) i3);
            }
            considerReleasingGlowsOnScroll(i, i2);
        }
        if (!(i6 == 0 && i4 == 0)) {
            dispatchOnScrolled(i6, i4);
        }
        if (!awakenScrollBars()) {
            invalidate();
        }
        if (i6 == 0 && i4 == 0) {
            return false;
        }
        return true;
    }

    public int computeHorizontalScrollOffset() {
        if (this.mLayout != null && this.mLayout.canScrollHorizontally()) {
            return this.mLayout.computeHorizontalScrollOffset(this.mState);
        }
        return 0;
    }

    public int computeHorizontalScrollExtent() {
        if (this.mLayout != null && this.mLayout.canScrollHorizontally()) {
            return this.mLayout.computeHorizontalScrollExtent(this.mState);
        }
        return 0;
    }

    public int computeHorizontalScrollRange() {
        if (this.mLayout != null && this.mLayout.canScrollHorizontally()) {
            return this.mLayout.computeHorizontalScrollRange(this.mState);
        }
        return 0;
    }

    public int computeVerticalScrollOffset() {
        if (this.mLayout != null && this.mLayout.canScrollVertically()) {
            return this.mLayout.computeVerticalScrollOffset(this.mState);
        }
        return 0;
    }

    public int computeVerticalScrollExtent() {
        if (this.mLayout != null && this.mLayout.canScrollVertically()) {
            return this.mLayout.computeVerticalScrollExtent(this.mState);
        }
        return 0;
    }

    public int computeVerticalScrollRange() {
        if (this.mLayout != null && this.mLayout.canScrollVertically()) {
            return this.mLayout.computeVerticalScrollRange(this.mState);
        }
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public void eatRequestLayout() {
        this.mEatRequestLayout++;
        if (this.mEatRequestLayout == 1 && !this.mLayoutFrozen) {
            this.mLayoutRequestEaten = false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void resumeRequestLayout(boolean z) {
        if (this.mEatRequestLayout <= 0) {
            this.mEatRequestLayout = 1;
        }
        if (!z) {
            this.mLayoutRequestEaten = false;
        }
        if (this.mEatRequestLayout == 1) {
            if (z && this.mLayoutRequestEaten && !this.mLayoutFrozen && this.mLayout != null && this.mAdapter != null) {
                dispatchLayout();
            }
            if (!this.mLayoutFrozen) {
                this.mLayoutRequestEaten = false;
            }
        }
        this.mEatRequestLayout--;
    }

    public void setLayoutFrozen(boolean z) {
        if (z != this.mLayoutFrozen) {
            assertNotInLayoutOrScroll("Do not setLayoutFrozen in layout or scroll");
            if (!z) {
                this.mLayoutFrozen = false;
                if (!(!this.mLayoutRequestEaten || this.mLayout == null || this.mAdapter == null)) {
                    requestLayout();
                }
                this.mLayoutRequestEaten = false;
                return;
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            onTouchEvent(MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0));
            this.mLayoutFrozen = true;
            this.mIgnoreMotionEventTillDown = true;
            stopScroll();
        }
    }

    public boolean isLayoutFrozen() {
        return this.mLayoutFrozen;
    }

    public void smoothScrollBy(int i, int i2) {
        if (this.mLayout != null && !this.mLayoutFrozen) {
            if (!this.mLayout.canScrollHorizontally()) {
                i = 0;
            }
            if (!this.mLayout.canScrollVertically()) {
                i2 = 0;
            }
            if (!(i == 0 && i2 == 0)) {
                this.mViewFlinger.a(i, i2);
            }
        }
    }

    public boolean fling(int i, int i2) {
        if (this.mLayout == null || this.mLayoutFrozen) {
            return false;
        }
        boolean canScrollHorizontally = this.mLayout.canScrollHorizontally();
        boolean canScrollVertically = this.mLayout.canScrollVertically();
        int i3 = (!canScrollHorizontally || Math.abs(i) < this.mMinFlingVelocity) ? 0 : i;
        int i4 = (!canScrollVertically || Math.abs(i2) < this.mMinFlingVelocity) ? 0 : i2;
        if (i3 == 0 && i4 == 0) {
            return false;
        }
        float f = (float) i3;
        float f2 = (float) i4;
        if (!dispatchNestedPreFling(f, f2)) {
            boolean z = canScrollHorizontally || canScrollVertically;
            dispatchNestedFling(f, f2, z);
            if (z) {
                int max = Math.max(-this.mMaxFlingVelocity, Math.min(i3, this.mMaxFlingVelocity));
                int max2 = Math.max(-this.mMaxFlingVelocity, Math.min(i4, this.mMaxFlingVelocity));
                ViewFlinger viewFlinger = this.mViewFlinger;
                RecyclerView.this.setScrollState(2);
                viewFlinger.b = 0;
                viewFlinger.a = 0;
                viewFlinger.c.fling(0, 0, max, max2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
                viewFlinger.a();
                return true;
            }
        }
        return false;
    }

    public void stopScroll() {
        setScrollState(0);
        stopScrollersInternal();
    }

    private void stopScrollersInternal() {
        this.mViewFlinger.b();
        if (this.mLayout != null) {
            this.mLayout.stopSmoothScroller();
        }
    }

    public int getMinFlingVelocity() {
        return this.mMinFlingVelocity;
    }

    public int getMaxFlingVelocity() {
        return this.mMaxFlingVelocity;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x005c, code lost:
        if (r6.mTopGlow.onPull((-r10) / ((float) getHeight()), r7 / ((float) getWidth())) != false) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x007a, code lost:
        if (r6.mBottomGlow.onPull(r10 / ((float) getHeight()), 1.0f - (r7 / ((float) getWidth()))) != false) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0020, code lost:
        if (r6.mLeftGlow.onPull((-r8) / ((float) getWidth()), 1.0f - (r9 / ((float) getHeight()))) != false) goto L_0x0022;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x003e, code lost:
        if (r6.mRightGlow.onPull(r8 / ((float) getWidth()), r9 / ((float) getHeight())) != false) goto L_0x0022;
     */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x005f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void pullGlows(float r7, float r8, float r9, float r10) {
        /*
            r6 = this;
            r0 = 0
            int r1 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            r2 = 1065353216(0x3f800000, float:1.0)
            r3 = 1
            if (r1 >= 0) goto L_0x0024
            r6.ensureLeftGlow()
            android.support.v4.widget.EdgeEffectCompat r1 = r6.mLeftGlow
            float r4 = -r8
            int r5 = r6.getWidth()
            float r5 = (float) r5
            float r4 = r4 / r5
            int r5 = r6.getHeight()
            float r5 = (float) r5
            float r9 = r9 / r5
            float r9 = r2 - r9
            boolean r9 = r1.onPull(r4, r9)
            if (r9 == 0) goto L_0x0041
        L_0x0022:
            r9 = 1
            goto L_0x0042
        L_0x0024:
            int r1 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            if (r1 <= 0) goto L_0x0041
            r6.ensureRightGlow()
            android.support.v4.widget.EdgeEffectCompat r1 = r6.mRightGlow
            int r4 = r6.getWidth()
            float r4 = (float) r4
            float r4 = r8 / r4
            int r5 = r6.getHeight()
            float r5 = (float) r5
            float r9 = r9 / r5
            boolean r9 = r1.onPull(r4, r9)
            if (r9 == 0) goto L_0x0041
            goto L_0x0022
        L_0x0041:
            r9 = 0
        L_0x0042:
            int r1 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r1 >= 0) goto L_0x005f
            r6.ensureTopGlow()
            android.support.v4.widget.EdgeEffectCompat r1 = r6.mTopGlow
            float r2 = -r10
            int r4 = r6.getHeight()
            float r4 = (float) r4
            float r2 = r2 / r4
            int r4 = r6.getWidth()
            float r4 = (float) r4
            float r7 = r7 / r4
            boolean r7 = r1.onPull(r2, r7)
            if (r7 == 0) goto L_0x007d
            goto L_0x007e
        L_0x005f:
            int r1 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r1 <= 0) goto L_0x007d
            r6.ensureBottomGlow()
            android.support.v4.widget.EdgeEffectCompat r1 = r6.mBottomGlow
            int r4 = r6.getHeight()
            float r4 = (float) r4
            float r4 = r10 / r4
            int r5 = r6.getWidth()
            float r5 = (float) r5
            float r7 = r7 / r5
            float r2 = r2 - r7
            boolean r7 = r1.onPull(r4, r2)
            if (r7 == 0) goto L_0x007d
            goto L_0x007e
        L_0x007d:
            r3 = r9
        L_0x007e:
            if (r3 != 0) goto L_0x0088
            int r7 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            if (r7 != 0) goto L_0x0088
            int r7 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r7 == 0) goto L_0x008b
        L_0x0088:
            android.support.v4.view.ViewCompat.postInvalidateOnAnimation(r6)
        L_0x008b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.pullGlows(float, float, float, float):void");
    }

    private void releaseGlows() {
        boolean onRelease = this.mLeftGlow != null ? this.mLeftGlow.onRelease() : false;
        if (this.mTopGlow != null) {
            onRelease |= this.mTopGlow.onRelease();
        }
        if (this.mRightGlow != null) {
            onRelease |= this.mRightGlow.onRelease();
        }
        if (this.mBottomGlow != null) {
            onRelease |= this.mBottomGlow.onRelease();
        }
        if (onRelease) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: private */
    public void considerReleasingGlowsOnScroll(int i, int i2) {
        boolean onRelease = (this.mLeftGlow == null || this.mLeftGlow.isFinished() || i <= 0) ? false : this.mLeftGlow.onRelease();
        if (this.mRightGlow != null && !this.mRightGlow.isFinished() && i < 0) {
            onRelease |= this.mRightGlow.onRelease();
        }
        if (this.mTopGlow != null && !this.mTopGlow.isFinished() && i2 > 0) {
            onRelease |= this.mTopGlow.onRelease();
        }
        if (this.mBottomGlow != null && !this.mBottomGlow.isFinished() && i2 < 0) {
            onRelease |= this.mBottomGlow.onRelease();
        }
        if (onRelease) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: 0000 */
    public void absorbGlows(int i, int i2) {
        if (i < 0) {
            ensureLeftGlow();
            this.mLeftGlow.onAbsorb(-i);
        } else if (i > 0) {
            ensureRightGlow();
            this.mRightGlow.onAbsorb(i);
        }
        if (i2 < 0) {
            ensureTopGlow();
            this.mTopGlow.onAbsorb(-i2);
        } else if (i2 > 0) {
            ensureBottomGlow();
            this.mBottomGlow.onAbsorb(i2);
        }
        if (i != 0 || i2 != 0) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: 0000 */
    public void ensureLeftGlow() {
        if (this.mLeftGlow == null) {
            this.mLeftGlow = new EdgeEffectCompat(getContext());
            if (this.mClipToPadding) {
                this.mLeftGlow.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
            } else {
                this.mLeftGlow.setSize(getMeasuredHeight(), getMeasuredWidth());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void ensureRightGlow() {
        if (this.mRightGlow == null) {
            this.mRightGlow = new EdgeEffectCompat(getContext());
            if (this.mClipToPadding) {
                this.mRightGlow.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
            } else {
                this.mRightGlow.setSize(getMeasuredHeight(), getMeasuredWidth());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void ensureTopGlow() {
        if (this.mTopGlow == null) {
            this.mTopGlow = new EdgeEffectCompat(getContext());
            if (this.mClipToPadding) {
                this.mTopGlow.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
            } else {
                this.mTopGlow.setSize(getMeasuredWidth(), getMeasuredHeight());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void ensureBottomGlow() {
        if (this.mBottomGlow == null) {
            this.mBottomGlow = new EdgeEffectCompat(getContext());
            if (this.mClipToPadding) {
                this.mBottomGlow.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
            } else {
                this.mBottomGlow.setSize(getMeasuredWidth(), getMeasuredHeight());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void invalidateGlows() {
        this.mBottomGlow = null;
        this.mTopGlow = null;
        this.mRightGlow = null;
        this.mLeftGlow = null;
    }

    public View focusSearch(View view, int i) {
        View onInterceptFocusSearch = this.mLayout.onInterceptFocusSearch(view, i);
        if (onInterceptFocusSearch != null) {
            return onInterceptFocusSearch;
        }
        View findNextFocus = FocusFinder.getInstance().findNextFocus(this, view, i);
        if (findNextFocus == null && this.mAdapter != null && this.mLayout != null && !isComputingLayout() && !this.mLayoutFrozen) {
            eatRequestLayout();
            findNextFocus = this.mLayout.onFocusSearchFailed(view, i, this.mRecycler, this.mState);
            resumeRequestLayout(false);
        }
        if (findNextFocus != null) {
            return findNextFocus;
        }
        return super.focusSearch(view, i);
    }

    public void requestChildFocus(View view, View view2) {
        if (!this.mLayout.onRequestChildFocus(this, this.mState, view, view2) && view2 != null) {
            this.mTempRect.set(0, 0, view2.getWidth(), view2.getHeight());
            android.view.ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
            if (layoutParams instanceof LayoutParams) {
                LayoutParams layoutParams2 = (LayoutParams) layoutParams;
                if (!layoutParams2.e) {
                    Rect rect = layoutParams2.d;
                    this.mTempRect.left -= rect.left;
                    this.mTempRect.right += rect.right;
                    this.mTempRect.top -= rect.top;
                    this.mTempRect.bottom += rect.bottom;
                }
            }
            offsetDescendantRectToMyCoords(view2, this.mTempRect);
            offsetRectIntoDescendantCoords(view, this.mTempRect);
            requestChildRectangleOnScreen(view, this.mTempRect, !this.mFirstLayoutComplete);
        }
        super.requestChildFocus(view, view2);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        return this.mLayout.requestChildRectangleOnScreen(this, view, rect, z);
    }

    public void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        if (this.mLayout == null || !this.mLayout.onAddFocusables(this, arrayList, i, i2)) {
            super.addFocusables(arrayList, i, i2);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mLayoutOrScrollCounter = 0;
        this.mIsAttached = true;
        this.mFirstLayoutComplete = false;
        if (this.mLayout != null) {
            this.mLayout.dispatchAttachedToWindow(this);
        }
        this.mPostedAnimatorRunner = false;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mItemAnimator != null) {
            this.mItemAnimator.c();
        }
        this.mFirstLayoutComplete = false;
        stopScroll();
        this.mIsAttached = false;
        if (this.mLayout != null) {
            this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
        }
        removeCallbacks(this.mItemAnimatorRunner);
        ViewInfoStore.b();
    }

    public boolean isAttachedToWindow() {
        return this.mIsAttached;
    }

    /* access modifiers changed from: 0000 */
    public void assertInLayoutOrScroll(String str) {
        if (isComputingLayout()) {
            return;
        }
        if (str == null) {
            throw new IllegalStateException("Cannot call this method unless RecyclerView is computing a layout or scrolling");
        }
        throw new IllegalStateException(str);
    }

    /* access modifiers changed from: 0000 */
    public void assertNotInLayoutOrScroll(String str) {
        if (!isComputingLayout()) {
            return;
        }
        if (str == null) {
            throw new IllegalStateException("Cannot call this method while RecyclerView is computing a layout or scrolling");
        }
        throw new IllegalStateException(str);
    }

    public void addOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.mOnItemTouchListeners.add(onItemTouchListener);
    }

    public void removeOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.mOnItemTouchListeners.remove(onItemTouchListener);
        if (this.mActiveOnItemTouchListener == onItemTouchListener) {
            this.mActiveOnItemTouchListener = null;
        }
    }

    private boolean dispatchOnItemTouchIntercept(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 3 || action == 0) {
            this.mActiveOnItemTouchListener = null;
        }
        int size = this.mOnItemTouchListeners.size();
        int i = 0;
        while (i < size) {
            OnItemTouchListener onItemTouchListener = this.mOnItemTouchListeners.get(i);
            if (!onItemTouchListener.onInterceptTouchEvent(this, motionEvent) || action == 3) {
                i++;
            } else {
                this.mActiveOnItemTouchListener = onItemTouchListener;
                return true;
            }
        }
        return false;
    }

    private boolean dispatchOnItemTouch(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (this.mActiveOnItemTouchListener != null) {
            if (action == 0) {
                this.mActiveOnItemTouchListener = null;
            } else {
                this.mActiveOnItemTouchListener.onTouchEvent(this, motionEvent);
                if (action == 3 || action == 1) {
                    this.mActiveOnItemTouchListener = null;
                }
                return true;
            }
        }
        if (action != 0) {
            int size = this.mOnItemTouchListeners.size();
            for (int i = 0; i < size; i++) {
                OnItemTouchListener onItemTouchListener = this.mOnItemTouchListeners.get(i);
                if (onItemTouchListener.onInterceptTouchEvent(this, motionEvent)) {
                    this.mActiveOnItemTouchListener = onItemTouchListener;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        if (this.mLayoutFrozen) {
            return false;
        }
        if (dispatchOnItemTouchIntercept(motionEvent)) {
            cancelTouch();
            return true;
        } else if (this.mLayout == null) {
            return false;
        } else {
            boolean canScrollHorizontally = this.mLayout.canScrollHorizontally();
            boolean canScrollVertically = this.mLayout.canScrollVertically();
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(motionEvent);
            int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
            int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
            switch (actionMasked) {
                case 0:
                    if (this.mIgnoreMotionEventTillDown) {
                        this.mIgnoreMotionEventTillDown = false;
                    }
                    this.mScrollPointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                    int x = (int) (motionEvent.getX() + 0.5f);
                    this.mLastTouchX = x;
                    this.mInitialTouchX = x;
                    int y = (int) (motionEvent.getY() + 0.5f);
                    this.mLastTouchY = y;
                    this.mInitialTouchY = y;
                    if (this.mScrollState == 2) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        setScrollState(1);
                    }
                    int[] iArr = this.mNestedOffsets;
                    this.mNestedOffsets[1] = 0;
                    iArr[0] = 0;
                    if (canScrollVertically) {
                        canScrollHorizontally |= true;
                    }
                    startNestedScroll(canScrollHorizontally ? 1 : 0);
                    break;
                case 1:
                    this.mVelocityTracker.clear();
                    stopNestedScroll();
                    break;
                case 2:
                    int findPointerIndex = MotionEventCompat.findPointerIndex(motionEvent, this.mScrollPointerId);
                    if (findPointerIndex >= 0) {
                        int x2 = (int) (MotionEventCompat.getX(motionEvent, findPointerIndex) + 0.5f);
                        int y2 = (int) (MotionEventCompat.getY(motionEvent, findPointerIndex) + 0.5f);
                        if (this.mScrollState != 1) {
                            int i = x2 - this.mInitialTouchX;
                            int i2 = y2 - this.mInitialTouchY;
                            int i3 = -1;
                            if (!canScrollHorizontally || Math.abs(i) <= this.mTouchSlop) {
                                z = false;
                            } else {
                                this.mLastTouchX = this.mInitialTouchX + (this.mTouchSlop * (i < 0 ? -1 : 1));
                                z = true;
                            }
                            if (canScrollVertically && Math.abs(i2) > this.mTouchSlop) {
                                int i4 = this.mInitialTouchY;
                                int i5 = this.mTouchSlop;
                                if (i2 >= 0) {
                                    i3 = 1;
                                }
                                this.mLastTouchY = i4 + (i5 * i3);
                                z = true;
                            }
                            if (z) {
                                setScrollState(1);
                                break;
                            }
                        }
                    } else {
                        StringBuilder sb = new StringBuilder("Error processing scroll; pointer index for id ");
                        sb.append(this.mScrollPointerId);
                        sb.append(" not found. Did any MotionEvents get skipped?");
                        return false;
                    }
                    break;
                case 3:
                    cancelTouch();
                    break;
                case 5:
                    this.mScrollPointerId = MotionEventCompat.getPointerId(motionEvent, actionIndex);
                    int x3 = (int) (MotionEventCompat.getX(motionEvent, actionIndex) + 0.5f);
                    this.mLastTouchX = x3;
                    this.mInitialTouchX = x3;
                    int y3 = (int) (MotionEventCompat.getY(motionEvent, actionIndex) + 0.5f);
                    this.mLastTouchY = y3;
                    this.mInitialTouchY = y3;
                    break;
                case 6:
                    onPointerUp(motionEvent);
                    break;
            }
            if (this.mScrollState == 1) {
                return true;
            }
            return false;
        }
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        int size = this.mOnItemTouchListeners.size();
        for (int i = 0; i < size; i++) {
            this.mOnItemTouchListeners.get(i).onRequestDisallowInterceptTouchEvent(z);
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int i;
        int i2;
        boolean z;
        boolean z2 = false;
        if (this.mLayoutFrozen || this.mIgnoreMotionEventTillDown) {
            return false;
        }
        if (dispatchOnItemTouch(motionEvent)) {
            cancelTouch();
            return true;
        } else if (this.mLayout == null) {
            return false;
        } else {
            boolean canScrollHorizontally = this.mLayout.canScrollHorizontally();
            boolean canScrollVertically = this.mLayout.canScrollVertically();
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            MotionEvent obtain = MotionEvent.obtain(motionEvent);
            int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
            int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
            if (actionMasked == 0) {
                int[] iArr = this.mNestedOffsets;
                this.mNestedOffsets[1] = 0;
                iArr[0] = 0;
            }
            obtain.offsetLocation((float) this.mNestedOffsets[0], (float) this.mNestedOffsets[1]);
            switch (actionMasked) {
                case 0:
                    this.mScrollPointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                    int x = (int) (motionEvent.getX() + 0.5f);
                    this.mLastTouchX = x;
                    this.mInitialTouchX = x;
                    int y = (int) (motionEvent.getY() + 0.5f);
                    this.mLastTouchY = y;
                    this.mInitialTouchY = y;
                    if (canScrollVertically) {
                        canScrollHorizontally |= true;
                    }
                    startNestedScroll(canScrollHorizontally ? 1 : 0);
                    break;
                case 1:
                    this.mVelocityTracker.addMovement(obtain);
                    this.mVelocityTracker.computeCurrentVelocity(1000, (float) this.mMaxFlingVelocity);
                    float f = canScrollHorizontally ? -VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mScrollPointerId) : 0.0f;
                    float f2 = canScrollVertically ? -VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mScrollPointerId) : 0.0f;
                    if ((f == 0.0f && f2 == 0.0f) || !fling((int) f, (int) f2)) {
                        setScrollState(0);
                    }
                    resetTouch();
                    z2 = true;
                    break;
                case 2:
                    int findPointerIndex = MotionEventCompat.findPointerIndex(motionEvent, this.mScrollPointerId);
                    if (findPointerIndex >= 0) {
                        int x2 = (int) (MotionEventCompat.getX(motionEvent, findPointerIndex) + 0.5f);
                        int y2 = (int) (MotionEventCompat.getY(motionEvent, findPointerIndex) + 0.5f);
                        int i3 = this.mLastTouchX - x2;
                        int i4 = this.mLastTouchY - y2;
                        if (dispatchNestedPreScroll(i3, i4, this.mScrollConsumed, this.mScrollOffset)) {
                            i3 -= this.mScrollConsumed[0];
                            i4 -= this.mScrollConsumed[1];
                            obtain.offsetLocation((float) this.mScrollOffset[0], (float) this.mScrollOffset[1]);
                            int[] iArr2 = this.mNestedOffsets;
                            iArr2[0] = iArr2[0] + this.mScrollOffset[0];
                            int[] iArr3 = this.mNestedOffsets;
                            iArr3[1] = iArr3[1] + this.mScrollOffset[1];
                        }
                        if (this.mScrollState != 1) {
                            if (!canScrollHorizontally || Math.abs(i2) <= this.mTouchSlop) {
                                z = false;
                            } else {
                                if (i2 > 0) {
                                    i2 -= this.mTouchSlop;
                                } else {
                                    i2 += this.mTouchSlop;
                                }
                                z = true;
                            }
                            if (canScrollVertically && Math.abs(i) > this.mTouchSlop) {
                                if (i > 0) {
                                    i -= this.mTouchSlop;
                                } else {
                                    i += this.mTouchSlop;
                                }
                                z = true;
                            }
                            if (z) {
                                setScrollState(1);
                            }
                        }
                        if (this.mScrollState == 1) {
                            this.mLastTouchX = x2 - this.mScrollOffset[0];
                            this.mLastTouchY = y2 - this.mScrollOffset[1];
                            if (!canScrollHorizontally) {
                                i2 = 0;
                            }
                            if (!canScrollVertically) {
                                i = 0;
                            }
                            if (scrollByInternal(i2, i, obtain)) {
                                getParent().requestDisallowInterceptTouchEvent(true);
                                break;
                            }
                        }
                    } else {
                        StringBuilder sb = new StringBuilder("Error processing scroll; pointer index for id ");
                        sb.append(this.mScrollPointerId);
                        sb.append(" not found. Did any MotionEvents get skipped?");
                        return false;
                    }
                    break;
                case 3:
                    cancelTouch();
                    break;
                case 5:
                    this.mScrollPointerId = MotionEventCompat.getPointerId(motionEvent, actionIndex);
                    int x3 = (int) (MotionEventCompat.getX(motionEvent, actionIndex) + 0.5f);
                    this.mLastTouchX = x3;
                    this.mInitialTouchX = x3;
                    int y3 = (int) (MotionEventCompat.getY(motionEvent, actionIndex) + 0.5f);
                    this.mLastTouchY = y3;
                    this.mInitialTouchY = y3;
                    break;
                case 6:
                    onPointerUp(motionEvent);
                    break;
            }
            if (!z2) {
                this.mVelocityTracker.addMovement(obtain);
            }
            obtain.recycle();
            return true;
        }
    }

    private void resetTouch() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.clear();
        }
        stopNestedScroll();
        releaseGlows();
    }

    private void cancelTouch() {
        resetTouch();
        setScrollState(0);
    }

    private void onPointerUp(MotionEvent motionEvent) {
        int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
        if (MotionEventCompat.getPointerId(motionEvent, actionIndex) == this.mScrollPointerId) {
            int i = actionIndex == 0 ? 1 : 0;
            this.mScrollPointerId = MotionEventCompat.getPointerId(motionEvent, i);
            int x = (int) (MotionEventCompat.getX(motionEvent, i) + 0.5f);
            this.mLastTouchX = x;
            this.mInitialTouchX = x;
            int y = (int) (MotionEventCompat.getY(motionEvent, i) + 0.5f);
            this.mLastTouchY = y;
            this.mInitialTouchY = y;
        }
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if (this.mLayout != null && !this.mLayoutFrozen && (MotionEventCompat.getSource(motionEvent) & 2) != 0 && motionEvent.getAction() == 8) {
            float f = this.mLayout.canScrollVertically() ? -MotionEventCompat.getAxisValue(motionEvent, 9) : 0.0f;
            float axisValue = this.mLayout.canScrollHorizontally() ? MotionEventCompat.getAxisValue(motionEvent, 10) : 0.0f;
            if (!(f == 0.0f && axisValue == 0.0f)) {
                float scrollFactor = getScrollFactor();
                scrollByInternal((int) (axisValue * scrollFactor), (int) (f * scrollFactor), motionEvent);
            }
        }
        return false;
    }

    private float getScrollFactor() {
        if (this.mScrollFactor == Float.MIN_VALUE) {
            TypedValue typedValue = new TypedValue();
            if (!getContext().getTheme().resolveAttribute(16842829, typedValue, true)) {
                return 0.0f;
            }
            this.mScrollFactor = typedValue.getDimension(getContext().getResources().getDisplayMetrics());
        }
        return this.mScrollFactor;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        if (this.mLayout == null) {
            defaultOnMeasure(i, i2);
            return;
        }
        boolean z = false;
        if (this.mLayout.mAutoMeasure) {
            int mode = MeasureSpec.getMode(i);
            int mode2 = MeasureSpec.getMode(i2);
            if (mode == 1073741824 && mode2 == 1073741824) {
                z = true;
            }
            this.mLayout.onMeasure(this.mRecycler, this.mState, i, i2);
            if (!z && this.mAdapter != null) {
                if (this.mState.e == 1) {
                    dispatchLayoutStep1();
                }
                this.mLayout.setMeasureSpecs(i, i2);
                this.mState.l = true;
                dispatchLayoutStep2();
                this.mLayout.setMeasuredDimensionFromChildren(i, i2);
                if (this.mLayout.shouldMeasureTwice()) {
                    this.mLayout.setMeasureSpecs(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), UCCore.VERIFY_POLICY_QUICK));
                    this.mState.l = true;
                    dispatchLayoutStep2();
                    this.mLayout.setMeasuredDimensionFromChildren(i, i2);
                }
            }
        } else if (this.mHasFixedSize) {
            this.mLayout.onMeasure(this.mRecycler, this.mState, i, i2);
        } else {
            if (this.mAdapterUpdateDuringMeasure) {
                eatRequestLayout();
                processAdapterUpdatesAndSetAnimationFlags();
                if (this.mState.d) {
                    this.mState.c = true;
                } else {
                    this.mAdapterHelper.e();
                    this.mState.c = false;
                }
                this.mAdapterUpdateDuringMeasure = false;
                resumeRequestLayout(false);
            }
            if (this.mAdapter != null) {
                this.mState.b = this.mAdapter.getItemCount();
            } else {
                this.mState.b = 0;
            }
            eatRequestLayout();
            this.mLayout.onMeasure(this.mRecycler, this.mState, i, i2);
            resumeRequestLayout(false);
            this.mState.c = false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void defaultOnMeasure(int i, int i2) {
        setMeasuredDimension(LayoutManager.chooseSize(i, getPaddingLeft() + getPaddingRight(), ViewCompat.getMinimumWidth(this)), LayoutManager.chooseSize(i2, getPaddingTop() + getPaddingBottom(), ViewCompat.getMinimumHeight(this)));
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3 || i2 != i4) {
            invalidateGlows();
        }
    }

    public void setItemAnimator(ItemAnimator itemAnimator) {
        if (this.mItemAnimator != null) {
            this.mItemAnimator.c();
            this.mItemAnimator.a = null;
        }
        this.mItemAnimator = itemAnimator;
        if (this.mItemAnimator != null) {
            this.mItemAnimator.a = this.mItemAnimatorListener;
        }
    }

    /* access modifiers changed from: private */
    public void onEnterLayoutOrScroll() {
        this.mLayoutOrScrollCounter++;
    }

    /* access modifiers changed from: private */
    public void onExitLayoutOrScroll() {
        this.mLayoutOrScrollCounter--;
        if (this.mLayoutOrScrollCounter <= 0) {
            this.mLayoutOrScrollCounter = 0;
            dispatchContentChangedIfNecessary();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean isAccessibilityEnabled() {
        return this.mAccessibilityManager != null && this.mAccessibilityManager.isEnabled();
    }

    private void dispatchContentChangedIfNecessary() {
        int i = this.mEatenAccessibilityChangeFlags;
        this.mEatenAccessibilityChangeFlags = 0;
        if (i != 0 && isAccessibilityEnabled()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain();
            obtain.setEventType(2048);
            AccessibilityEventCompat.setContentChangeTypes(obtain, i);
            sendAccessibilityEventUnchecked(obtain);
        }
    }

    public boolean isComputingLayout() {
        return this.mLayoutOrScrollCounter > 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean shouldDeferAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (!isComputingLayout()) {
            return false;
        }
        int contentChangeTypes = accessibilityEvent != null ? AccessibilityEventCompat.getContentChangeTypes(accessibilityEvent) : 0;
        if (contentChangeTypes == 0) {
            contentChangeTypes = 0;
        }
        this.mEatenAccessibilityChangeFlags = contentChangeTypes | this.mEatenAccessibilityChangeFlags;
        return true;
    }

    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (!shouldDeferAccessibilityEvent(accessibilityEvent)) {
            super.sendAccessibilityEventUnchecked(accessibilityEvent);
        }
    }

    public ItemAnimator getItemAnimator() {
        return this.mItemAnimator;
    }

    /* access modifiers changed from: private */
    public void postAnimationRunner() {
        if (!this.mPostedAnimatorRunner && this.mIsAttached) {
            ViewCompat.postOnAnimation(this, this.mItemAnimatorRunner);
            this.mPostedAnimatorRunner = true;
        }
    }

    private boolean predictiveItemAnimationsEnabled() {
        return this.mItemAnimator != null && this.mLayout.supportsPredictiveItemAnimations();
    }

    private void processAdapterUpdatesAndSetAnimationFlags() {
        if (this.mDataSetHasChangedAfterLayout) {
            this.mAdapterHelper.a();
            markKnownViewsInvalid();
            this.mLayout.onItemsChanged(this);
        }
        if (predictiveItemAnimationsEnabled()) {
            this.mAdapterHelper.b();
        } else {
            this.mAdapterHelper.e();
        }
        boolean z = true;
        boolean z2 = this.mItemsAddedOrRemoved || this.mItemsChanged;
        this.mState.j = this.mFirstLayoutComplete && this.mItemAnimator != null && (this.mDataSetHasChangedAfterLayout || z2 || this.mLayout.mRequestedSimpleAnimations) && (!this.mDataSetHasChangedAfterLayout || this.mAdapter.hasStableIds());
        State state = this.mState;
        if (!this.mState.j || !z2 || this.mDataSetHasChangedAfterLayout || !predictiveItemAnimationsEnabled()) {
            z = false;
        }
        state.d = z;
    }

    /* access modifiers changed from: 0000 */
    public void dispatchLayout() {
        if (this.mAdapter != null && this.mLayout != null) {
            boolean z = false;
            this.mState.l = false;
            if (this.mState.e == 1) {
                dispatchLayoutStep1();
                this.mLayout.setExactMeasureSpecsFrom(this);
                dispatchLayoutStep2();
            } else {
                AdapterHelper adapterHelper = this.mAdapterHelper;
                if (!adapterHelper.b.isEmpty() && !adapterHelper.a.isEmpty()) {
                    z = true;
                }
                if (!z && this.mLayout.getWidth() == getWidth() && this.mLayout.getHeight() == getHeight()) {
                    this.mLayout.setExactMeasureSpecsFrom(this);
                } else {
                    this.mLayout.setExactMeasureSpecsFrom(this);
                    dispatchLayoutStep2();
                }
            }
            dispatchLayoutStep3();
        }
    }

    private void dispatchLayoutStep1() {
        this.mState.a(1);
        this.mState.l = false;
        eatRequestLayout();
        this.mViewInfoStore.a();
        onEnterLayoutOrScroll();
        processAdapterUpdatesAndSetAnimationFlags();
        this.mState.k = this.mState.j && this.mItemsChanged;
        this.mItemsChanged = false;
        this.mItemsAddedOrRemoved = false;
        this.mState.c = this.mState.d;
        this.mState.b = this.mAdapter.getItemCount();
        findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        if (this.mState.j) {
            int a = this.mChildHelper.a();
            for (int i = 0; i < a; i++) {
                ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.b(i));
                if (!childViewHolderInt.shouldIgnore() && (!childViewHolderInt.isInvalid() || this.mAdapter.hasStableIds())) {
                    ItemAnimator.e(childViewHolderInt);
                    childViewHolderInt.getUnmodifiedPayloads();
                    this.mViewInfoStore.a(childViewHolderInt, ItemAnimator.e().a(childViewHolderInt));
                    if (this.mState.k && childViewHolderInt.isUpdated() && !childViewHolderInt.isRemoved() && !childViewHolderInt.shouldIgnore() && !childViewHolderInt.isInvalid()) {
                        this.mViewInfoStore.a(getChangedHolderKey(childViewHolderInt), childViewHolderInt);
                    }
                }
            }
        }
        if (this.mState.d) {
            saveOldPositions();
            boolean e = this.mState.i;
            this.mState.i = false;
            this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
            this.mState.i = e;
            for (int i2 = 0; i2 < this.mChildHelper.a(); i2++) {
                ViewHolder childViewHolderInt2 = getChildViewHolderInt(this.mChildHelper.b(i2));
                if (!childViewHolderInt2.shouldIgnore()) {
                    InfoRecord infoRecord = (InfoRecord) this.mViewInfoStore.a.get(childViewHolderInt2);
                    if (!((infoRecord == null || (infoRecord.a & 4) == 0) ? false : true)) {
                        ItemAnimator.e(childViewHolderInt2);
                        boolean hasAnyOfTheFlags = childViewHolderInt2.hasAnyOfTheFlags(8192);
                        childViewHolderInt2.getUnmodifiedPayloads();
                        ItemHolderInfo a2 = ItemAnimator.e().a(childViewHolderInt2);
                        if (hasAnyOfTheFlags) {
                            recordAnimationInfoIfBouncedHiddenView(childViewHolderInt2, a2);
                        } else {
                            ViewInfoStore viewInfoStore = this.mViewInfoStore;
                            InfoRecord infoRecord2 = (InfoRecord) viewInfoStore.a.get(childViewHolderInt2);
                            if (infoRecord2 == null) {
                                infoRecord2 = InfoRecord.a();
                                viewInfoStore.a.put(childViewHolderInt2, infoRecord2);
                            }
                            infoRecord2.a |= 2;
                            infoRecord2.b = a2;
                        }
                    }
                }
            }
            clearOldPositions();
        } else {
            clearOldPositions();
        }
        onExitLayoutOrScroll();
        resumeRequestLayout(false);
        this.mState.e = 2;
    }

    private void dispatchLayoutStep2() {
        eatRequestLayout();
        onEnterLayoutOrScroll();
        this.mState.a(6);
        this.mAdapterHelper.e();
        this.mState.b = this.mAdapter.getItemCount();
        this.mState.h = 0;
        this.mState.c = false;
        this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
        this.mState.i = false;
        this.mPendingSavedState = null;
        this.mState.j = this.mState.j && this.mItemAnimator != null;
        this.mState.e = 4;
        onExitLayoutOrScroll();
        resumeRequestLayout(false);
    }

    private void dispatchLayoutStep3() {
        this.mState.a(4);
        eatRequestLayout();
        onEnterLayoutOrScroll();
        this.mState.e = 1;
        if (this.mState.j) {
            for (int a = this.mChildHelper.a() - 1; a >= 0; a--) {
                ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.b(a));
                if (!childViewHolderInt.shouldIgnore()) {
                    long changedHolderKey = getChangedHolderKey(childViewHolderInt);
                    ItemHolderInfo a2 = ItemAnimator.e().a(childViewHolderInt);
                    ViewHolder viewHolder = (ViewHolder) this.mViewInfoStore.b.get(changedHolderKey);
                    if (viewHolder != null && !viewHolder.shouldIgnore()) {
                        boolean a3 = this.mViewInfoStore.a(viewHolder);
                        boolean a4 = this.mViewInfoStore.a(childViewHolderInt);
                        if (!a3 || viewHolder != childViewHolderInt) {
                            ItemHolderInfo a5 = this.mViewInfoStore.a(viewHolder, 4);
                            this.mViewInfoStore.b(childViewHolderInt, a2);
                            ItemHolderInfo a6 = this.mViewInfoStore.a(childViewHolderInt, 8);
                            if (a5 == null) {
                                handleMissingPreInfoForChangeError(changedHolderKey, childViewHolderInt, viewHolder);
                            } else {
                                animateChange(viewHolder, childViewHolderInt, a5, a6, a3, a4);
                            }
                        }
                    }
                    this.mViewInfoStore.b(childViewHolderInt, a2);
                }
            }
            ViewInfoStore viewInfoStore = this.mViewInfoStore;
            ProcessCallback processCallback = this.mViewInfoProcessCallback;
            for (int size = viewInfoStore.a.size() - 1; size >= 0; size--) {
                ViewHolder viewHolder2 = (ViewHolder) viewInfoStore.a.keyAt(size);
                InfoRecord infoRecord = (InfoRecord) viewInfoStore.a.removeAt(size);
                if ((infoRecord.a & 3) == 3) {
                    processCallback.a(viewHolder2);
                } else if ((infoRecord.a & 1) != 0) {
                    if (infoRecord.b == null) {
                        processCallback.a(viewHolder2);
                    } else {
                        processCallback.a(viewHolder2, infoRecord.b, infoRecord.c);
                    }
                } else if ((infoRecord.a & 14) == 14) {
                    processCallback.b(viewHolder2, infoRecord.b, infoRecord.c);
                } else if ((infoRecord.a & 12) == 12) {
                    processCallback.c(viewHolder2, infoRecord.b, infoRecord.c);
                } else if ((infoRecord.a & 4) != 0) {
                    processCallback.a(viewHolder2, infoRecord.b, null);
                } else if ((infoRecord.a & 8) != 0) {
                    processCallback.b(viewHolder2, infoRecord.b, infoRecord.c);
                }
                InfoRecord.a(infoRecord);
            }
        }
        this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
        this.mState.g = this.mState.b;
        this.mDataSetHasChangedAfterLayout = false;
        this.mState.j = false;
        this.mState.d = false;
        this.mLayout.mRequestedSimpleAnimations = false;
        if (this.mRecycler.b != null) {
            this.mRecycler.b.clear();
        }
        onExitLayoutOrScroll();
        resumeRequestLayout(false);
        this.mViewInfoStore.a();
        if (didChildRangeChange(this.mMinMaxLayoutPositions[0], this.mMinMaxLayoutPositions[1])) {
            dispatchOnScrolled(0, 0);
        }
    }

    private void handleMissingPreInfoForChangeError(long j, ViewHolder viewHolder, ViewHolder viewHolder2) {
        int a = this.mChildHelper.a();
        int i = 0;
        while (i < a) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.b(i));
            if (childViewHolderInt == viewHolder || getChangedHolderKey(childViewHolderInt) != j) {
                i++;
            } else if (this.mAdapter == null || !this.mAdapter.hasStableIds()) {
                StringBuilder sb = new StringBuilder("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:");
                sb.append(childViewHolderInt);
                sb.append(" \n View Holder 2:");
                sb.append(viewHolder);
                throw new IllegalStateException(sb.toString());
            } else {
                StringBuilder sb2 = new StringBuilder("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:");
                sb2.append(childViewHolderInt);
                sb2.append(" \n View Holder 2:");
                sb2.append(viewHolder);
                throw new IllegalStateException(sb2.toString());
            }
        }
        StringBuilder sb3 = new StringBuilder("Problem while matching changed view holders with the newones. The pre-layout information for the change holder ");
        sb3.append(viewHolder2);
        sb3.append(" cannot be found but it is necessary for ");
        sb3.append(viewHolder);
    }

    /* access modifiers changed from: private */
    public void recordAnimationInfoIfBouncedHiddenView(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo) {
        viewHolder.setFlags(0, 8192);
        if (this.mState.k && viewHolder.isUpdated() && !viewHolder.isRemoved() && !viewHolder.shouldIgnore()) {
            this.mViewInfoStore.a(getChangedHolderKey(viewHolder), viewHolder);
        }
        this.mViewInfoStore.a(viewHolder, itemHolderInfo);
    }

    private void findMinMaxChildLayoutPositions(int[] iArr) {
        int a = this.mChildHelper.a();
        if (a == 0) {
            iArr[0] = 0;
            iArr[1] = 0;
            return;
        }
        int i = Integer.MAX_VALUE;
        int i2 = Integer.MIN_VALUE;
        for (int i3 = 0; i3 < a; i3++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.b(i3));
            if (!childViewHolderInt.shouldIgnore()) {
                int layoutPosition = childViewHolderInt.getLayoutPosition();
                if (layoutPosition < i) {
                    i = layoutPosition;
                }
                if (layoutPosition > i2) {
                    i2 = layoutPosition;
                }
            }
        }
        iArr[0] = i;
        iArr[1] = i2;
    }

    private boolean didChildRangeChange(int i, int i2) {
        int a = this.mChildHelper.a();
        if (a == 0) {
            return (i == 0 && i2 == 0) ? false : true;
        }
        for (int i3 = 0; i3 < a; i3++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.b(i3));
            if (!childViewHolderInt.shouldIgnore()) {
                int layoutPosition = childViewHolderInt.getLayoutPosition();
                if (layoutPosition < i || layoutPosition > i2) {
                    return true;
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void removeDetachedView(View view, boolean z) {
        ViewHolder childViewHolderInt = getChildViewHolderInt(view);
        if (childViewHolderInt != null) {
            if (childViewHolderInt.isTmpDetached()) {
                childViewHolderInt.clearTmpDetachFlag();
            } else if (!childViewHolderInt.shouldIgnore()) {
                throw new IllegalArgumentException("Called removeDetachedView with a view which is not flagged as tmp detached.".concat(String.valueOf(childViewHolderInt)));
            }
        }
        dispatchChildDetached(view);
        super.removeDetachedView(view, z);
    }

    /* access modifiers changed from: 0000 */
    public long getChangedHolderKey(ViewHolder viewHolder) {
        return this.mAdapter.hasStableIds() ? viewHolder.getItemId() : (long) viewHolder.mPosition;
    }

    /* access modifiers changed from: private */
    public void animateAppearance(@NonNull ViewHolder viewHolder, @Nullable ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2) {
        viewHolder.setIsRecyclable(false);
        if (this.mItemAnimator.b(viewHolder, itemHolderInfo, itemHolderInfo2)) {
            postAnimationRunner();
        }
    }

    /* access modifiers changed from: private */
    public void animateDisappearance(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @Nullable ItemHolderInfo itemHolderInfo2) {
        addAnimatingView(viewHolder);
        viewHolder.setIsRecyclable(false);
        if (this.mItemAnimator.a(viewHolder, itemHolderInfo, itemHolderInfo2)) {
            postAnimationRunner();
        }
    }

    private void animateChange(@NonNull ViewHolder viewHolder, @NonNull ViewHolder viewHolder2, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2, boolean z, boolean z2) {
        viewHolder.setIsRecyclable(false);
        if (z) {
            addAnimatingView(viewHolder);
        }
        if (viewHolder != viewHolder2) {
            if (z2) {
                addAnimatingView(viewHolder2);
            }
            viewHolder.mShadowedHolder = viewHolder2;
            addAnimatingView(viewHolder);
            this.mRecycler.b(viewHolder);
            viewHolder2.setIsRecyclable(false);
            viewHolder2.mShadowingHolder = viewHolder;
        }
        if (this.mItemAnimator.a(viewHolder, viewHolder2, itemHolderInfo, itemHolderInfo2)) {
            postAnimationRunner();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        TraceCompat.beginSection(TRACE_ON_LAYOUT_TAG);
        dispatchLayout();
        TraceCompat.endSection();
        this.mFirstLayoutComplete = true;
    }

    public void requestLayout() {
        if (this.mEatRequestLayout != 0 || this.mLayoutFrozen) {
            this.mLayoutRequestEaten = true;
        } else {
            super.requestLayout();
        }
    }

    /* access modifiers changed from: 0000 */
    public void markItemDecorInsetsDirty() {
        int b = this.mChildHelper.b();
        for (int i = 0; i < b; i++) {
            ((LayoutParams) this.mChildHelper.c(i).getLayoutParams()).e = true;
        }
        Recycler recycler = this.mRecycler;
        int size = recycler.c.size();
        for (int i2 = 0; i2 < size; i2++) {
            LayoutParams layoutParams = (LayoutParams) recycler.c.get(i2).itemView.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.e = true;
            }
        }
    }

    public void draw(Canvas canvas) {
        boolean z;
        boolean z2;
        super.draw(canvas);
        int size = this.mItemDecorations.size();
        boolean z3 = false;
        for (int i = 0; i < size; i++) {
            this.mItemDecorations.get(i).onDrawOver(canvas, this, this.mState);
        }
        if (this.mLeftGlow == null || this.mLeftGlow.isFinished()) {
            z = false;
        } else {
            int save = canvas.save();
            int paddingBottom = this.mClipToPadding ? getPaddingBottom() : 0;
            canvas.rotate(270.0f);
            canvas.translate((float) ((-getHeight()) + paddingBottom), 0.0f);
            z = this.mLeftGlow != null && this.mLeftGlow.draw(canvas);
            canvas.restoreToCount(save);
        }
        if (this.mTopGlow != null && !this.mTopGlow.isFinished()) {
            int save2 = canvas.save();
            if (this.mClipToPadding) {
                canvas.translate((float) getPaddingLeft(), (float) getPaddingTop());
            }
            z |= this.mTopGlow != null && this.mTopGlow.draw(canvas);
            canvas.restoreToCount(save2);
        }
        if (this.mRightGlow != null && !this.mRightGlow.isFinished()) {
            int save3 = canvas.save();
            int width = getWidth();
            int paddingTop = this.mClipToPadding ? getPaddingTop() : 0;
            canvas.rotate(90.0f);
            canvas.translate((float) (-paddingTop), (float) (-width));
            z |= this.mRightGlow != null && this.mRightGlow.draw(canvas);
            canvas.restoreToCount(save3);
        }
        if (this.mBottomGlow == null || this.mBottomGlow.isFinished()) {
            z2 = z;
        } else {
            int save4 = canvas.save();
            canvas.rotate(180.0f);
            if (this.mClipToPadding) {
                canvas.translate((float) ((-getWidth()) + getPaddingRight()), (float) ((-getHeight()) + getPaddingBottom()));
            } else {
                canvas.translate((float) (-getWidth()), (float) (-getHeight()));
            }
            if (this.mBottomGlow != null && this.mBottomGlow.draw(canvas)) {
                z3 = true;
            }
            z2 = z3 | z;
            canvas.restoreToCount(save4);
        }
        if (!z2 && this.mItemAnimator != null && this.mItemDecorations.size() > 0 && this.mItemAnimator.b()) {
            z2 = true;
        }
        if (z2) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = this.mItemDecorations.size();
        for (int i = 0; i < size; i++) {
            this.mItemDecorations.get(i).onDraw(canvas, this, this.mState);
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && this.mLayout.checkLayoutParams((LayoutParams) layoutParams);
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        if (this.mLayout != null) {
            return this.mLayout.generateDefaultLayoutParams();
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager");
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        if (this.mLayout != null) {
            return this.mLayout.generateLayoutParams(getContext(), attributeSet);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager");
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        if (this.mLayout != null) {
            return this.mLayout.generateLayoutParams(layoutParams);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager");
    }

    public boolean isAnimating() {
        return this.mItemAnimator != null && this.mItemAnimator.b();
    }

    /* access modifiers changed from: 0000 */
    public void saveOldPositions() {
        int b = this.mChildHelper.b();
        for (int i = 0; i < b; i++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.c(i));
            if (!childViewHolderInt.shouldIgnore()) {
                childViewHolderInt.saveOldPosition();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void clearOldPositions() {
        int b = this.mChildHelper.b();
        for (int i = 0; i < b; i++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.c(i));
            if (!childViewHolderInt.shouldIgnore()) {
                childViewHolderInt.clearOldPosition();
            }
        }
        Recycler recycler = this.mRecycler;
        int size = recycler.c.size();
        for (int i2 = 0; i2 < size; i2++) {
            recycler.c.get(i2).clearOldPosition();
        }
        int size2 = recycler.a.size();
        for (int i3 = 0; i3 < size2; i3++) {
            recycler.a.get(i3).clearOldPosition();
        }
        if (recycler.b != null) {
            int size3 = recycler.b.size();
            for (int i4 = 0; i4 < size3; i4++) {
                recycler.b.get(i4).clearOldPosition();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void offsetPositionRecordsForMove(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int b = this.mChildHelper.b();
        if (i < i2) {
            i5 = i;
            i4 = i2;
            i3 = -1;
        } else {
            i4 = i;
            i5 = i2;
            i3 = 1;
        }
        for (int i9 = 0; i9 < b; i9++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.c(i9));
            if (childViewHolderInt != null && childViewHolderInt.mPosition >= i5 && childViewHolderInt.mPosition <= i4) {
                if (childViewHolderInt.mPosition == i) {
                    childViewHolderInt.offsetPosition(i2 - i, false);
                } else {
                    childViewHolderInt.offsetPosition(i3, false);
                }
                this.mState.i = true;
            }
        }
        Recycler recycler = this.mRecycler;
        if (i < i2) {
            i8 = i;
            i7 = i2;
            i6 = -1;
        } else {
            i7 = i;
            i8 = i2;
            i6 = 1;
        }
        int size = recycler.c.size();
        for (int i10 = 0; i10 < size; i10++) {
            ViewHolder viewHolder = recycler.c.get(i10);
            if (viewHolder != null && viewHolder.mPosition >= i8 && viewHolder.mPosition <= i7) {
                if (viewHolder.mPosition == i) {
                    viewHolder.offsetPosition(i2 - i, false);
                } else {
                    viewHolder.offsetPosition(i6, false);
                }
            }
        }
        requestLayout();
    }

    /* access modifiers changed from: 0000 */
    public void offsetPositionRecordsForInsert(int i, int i2) {
        int b = this.mChildHelper.b();
        for (int i3 = 0; i3 < b; i3++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.c(i3));
            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore() && childViewHolderInt.mPosition >= i) {
                childViewHolderInt.offsetPosition(i2, false);
                this.mState.i = true;
            }
        }
        Recycler recycler = this.mRecycler;
        int size = recycler.c.size();
        for (int i4 = 0; i4 < size; i4++) {
            ViewHolder viewHolder = recycler.c.get(i4);
            if (viewHolder != null && viewHolder.mPosition >= i) {
                viewHolder.offsetPosition(i2, true);
            }
        }
        requestLayout();
    }

    /* access modifiers changed from: 0000 */
    public void offsetPositionRecordsForRemove(int i, int i2, boolean z) {
        int i3 = i + i2;
        int b = this.mChildHelper.b();
        for (int i4 = 0; i4 < b; i4++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.c(i4));
            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore()) {
                if (childViewHolderInt.mPosition >= i3) {
                    childViewHolderInt.offsetPosition(-i2, z);
                    this.mState.i = true;
                } else if (childViewHolderInt.mPosition >= i) {
                    childViewHolderInt.flagRemovedAndOffsetPosition(i - 1, -i2, z);
                    this.mState.i = true;
                }
            }
        }
        Recycler recycler = this.mRecycler;
        for (int size = recycler.c.size() - 1; size >= 0; size--) {
            ViewHolder viewHolder = recycler.c.get(size);
            if (viewHolder != null) {
                if (viewHolder.mPosition >= i3) {
                    viewHolder.offsetPosition(-i2, z);
                } else if (viewHolder.mPosition >= i) {
                    viewHolder.addFlags(8);
                    recycler.c(size);
                }
            }
        }
        requestLayout();
    }

    /* access modifiers changed from: 0000 */
    public void viewRangeUpdate(int i, int i2, Object obj) {
        int b = this.mChildHelper.b();
        int i3 = i2 + i;
        for (int i4 = 0; i4 < b; i4++) {
            View c = this.mChildHelper.c(i4);
            ViewHolder childViewHolderInt = getChildViewHolderInt(c);
            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore() && childViewHolderInt.mPosition >= i && childViewHolderInt.mPosition < i3) {
                childViewHolderInt.addFlags(2);
                childViewHolderInt.addChangePayload(obj);
                ((LayoutParams) c.getLayoutParams()).e = true;
            }
        }
        Recycler recycler = this.mRecycler;
        for (int size = recycler.c.size() - 1; size >= 0; size--) {
            ViewHolder viewHolder = recycler.c.get(size);
            if (viewHolder != null) {
                int layoutPosition = viewHolder.getLayoutPosition();
                if (layoutPosition >= i && layoutPosition < i3) {
                    viewHolder.addFlags(2);
                    recycler.c(size);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean canReuseUpdatedViewHolder(ViewHolder viewHolder) {
        return this.mItemAnimator == null || this.mItemAnimator.a(viewHolder, viewHolder.getUnmodifiedPayloads());
    }

    /* access modifiers changed from: private */
    public void setDataSetChangedAfterLayout() {
        if (!this.mDataSetHasChangedAfterLayout) {
            this.mDataSetHasChangedAfterLayout = true;
            int b = this.mChildHelper.b();
            for (int i = 0; i < b; i++) {
                ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.c(i));
                if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore()) {
                    childViewHolderInt.addFlags(512);
                }
            }
            Recycler recycler = this.mRecycler;
            int size = recycler.c.size();
            for (int i2 = 0; i2 < size; i2++) {
                ViewHolder viewHolder = recycler.c.get(i2);
                if (viewHolder != null) {
                    viewHolder.addFlags(512);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void markKnownViewsInvalid() {
        int b = this.mChildHelper.b();
        for (int i = 0; i < b; i++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.c(i));
            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore()) {
                childViewHolderInt.addFlags(6);
            }
        }
        markItemDecorInsetsDirty();
        Recycler recycler = this.mRecycler;
        if (RecyclerView.this.mAdapter == null || !RecyclerView.this.mAdapter.hasStableIds()) {
            recycler.b();
            return;
        }
        int size = recycler.c.size();
        for (int i2 = 0; i2 < size; i2++) {
            ViewHolder viewHolder = recycler.c.get(i2);
            if (viewHolder != null) {
                viewHolder.addFlags(6);
                viewHolder.addChangePayload(null);
            }
        }
    }

    public void invalidateItemDecorations() {
        if (this.mItemDecorations.size() != 0) {
            if (this.mLayout != null) {
                this.mLayout.assertNotInLayoutOrScroll("Cannot invalidate item decorations during a scroll or layout");
            }
            markItemDecorInsetsDirty();
            requestLayout();
        }
    }

    public ViewHolder getChildViewHolder(View view) {
        ViewParent parent = view.getParent();
        if (parent == null || parent == this) {
            return getChildViewHolderInt(view);
        }
        StringBuilder sb = new StringBuilder("View ");
        sb.append(view);
        sb.append(" is not a direct child of ");
        sb.append(this);
        throw new IllegalArgumentException(sb.toString());
    }

    @Nullable
    public View findContainingItemView(View view) {
        ViewParent parent = view.getParent();
        while (parent != null && parent != this && (parent instanceof View)) {
            view = (View) parent;
            parent = view.getParent();
        }
        if (parent == this) {
            return view;
        }
        return null;
    }

    @Nullable
    public ViewHolder findContainingViewHolder(View view) {
        View findContainingItemView = findContainingItemView(view);
        if (findContainingItemView == null) {
            return null;
        }
        return getChildViewHolder(findContainingItemView);
    }

    static ViewHolder getChildViewHolderInt(View view) {
        if (view == null) {
            return null;
        }
        return ((LayoutParams) view.getLayoutParams()).c;
    }

    @Deprecated
    public int getChildPosition(View view) {
        return getChildAdapterPosition(view);
    }

    public int getChildAdapterPosition(View view) {
        ViewHolder childViewHolderInt = getChildViewHolderInt(view);
        if (childViewHolderInt != null) {
            return childViewHolderInt.getAdapterPosition();
        }
        return -1;
    }

    public int getChildLayoutPosition(View view) {
        ViewHolder childViewHolderInt = getChildViewHolderInt(view);
        if (childViewHolderInt != null) {
            return childViewHolderInt.getLayoutPosition();
        }
        return -1;
    }

    public long getChildItemId(View view) {
        if (this.mAdapter == null || !this.mAdapter.hasStableIds()) {
            return -1;
        }
        ViewHolder childViewHolderInt = getChildViewHolderInt(view);
        if (childViewHolderInt != null) {
            return childViewHolderInt.getItemId();
        }
        return -1;
    }

    @Deprecated
    public ViewHolder findViewHolderForPosition(int i) {
        return findViewHolderForPosition(i, false);
    }

    public ViewHolder findViewHolderForLayoutPosition(int i) {
        return findViewHolderForPosition(i, false);
    }

    public ViewHolder findViewHolderForAdapterPosition(int i) {
        if (this.mDataSetHasChangedAfterLayout) {
            return null;
        }
        int b = this.mChildHelper.b();
        for (int i2 = 0; i2 < b; i2++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.c(i2));
            if (childViewHolderInt != null && !childViewHolderInt.isRemoved() && getAdapterPositionFor(childViewHolderInt) == i) {
                return childViewHolderInt;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public ViewHolder findViewHolderForPosition(int i, boolean z) {
        int b = this.mChildHelper.b();
        for (int i2 = 0; i2 < b; i2++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.c(i2));
            if (childViewHolderInt != null && !childViewHolderInt.isRemoved()) {
                if (z) {
                    if (childViewHolderInt.mPosition == i) {
                        return childViewHolderInt;
                    }
                } else if (childViewHolderInt.getLayoutPosition() == i) {
                    return childViewHolderInt;
                }
            }
        }
        return null;
    }

    public ViewHolder findViewHolderForItemId(long j) {
        int b = this.mChildHelper.b();
        for (int i = 0; i < b; i++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.c(i));
            if (childViewHolderInt != null && childViewHolderInt.getItemId() == j) {
                return childViewHolderInt;
            }
        }
        return null;
    }

    public View findChildViewUnder(float f, float f2) {
        for (int a = this.mChildHelper.a() - 1; a >= 0; a--) {
            View b = this.mChildHelper.b(a);
            float translationX = ViewCompat.getTranslationX(b);
            float translationY = ViewCompat.getTranslationY(b);
            if (f >= ((float) b.getLeft()) + translationX && f <= ((float) b.getRight()) + translationX && f2 >= ((float) b.getTop()) + translationY && f2 <= ((float) b.getBottom()) + translationY) {
                return b;
            }
        }
        return null;
    }

    public boolean drawChild(Canvas canvas, View view, long j) {
        return super.drawChild(canvas, view, j);
    }

    public void offsetChildrenVertical(int i) {
        int a = this.mChildHelper.a();
        for (int i2 = 0; i2 < a; i2++) {
            this.mChildHelper.b(i2).offsetTopAndBottom(i);
        }
    }

    public void offsetChildrenHorizontal(int i) {
        int a = this.mChildHelper.a();
        for (int i2 = 0; i2 < a; i2++) {
            this.mChildHelper.b(i2).offsetLeftAndRight(i);
        }
    }

    /* access modifiers changed from: 0000 */
    public Rect getItemDecorInsetsForChild(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (!layoutParams.e) {
            return layoutParams.d;
        }
        Rect rect = layoutParams.d;
        rect.set(0, 0, 0, 0);
        int size = this.mItemDecorations.size();
        for (int i = 0; i < size; i++) {
            this.mTempRect.set(0, 0, 0, 0);
            this.mItemDecorations.get(i).getItemOffsets(this.mTempRect, view, this, this.mState);
            rect.left += this.mTempRect.left;
            rect.top += this.mTempRect.top;
            rect.right += this.mTempRect.right;
            rect.bottom += this.mTempRect.bottom;
        }
        layoutParams.e = false;
        return rect;
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnScrolled(int i, int i2) {
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        onScrollChanged(scrollX, scrollY, scrollX, scrollY);
        onScrolled(i, i2);
        if (this.mScrollListener != null) {
            this.mScrollListener.onScrolled(this, i, i2);
        }
        if (this.mScrollListeners != null) {
            for (int size = this.mScrollListeners.size() - 1; size >= 0; size--) {
                this.mScrollListeners.get(size).onScrolled(this, i, i2);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnScrollStateChanged(int i) {
        if (this.mLayout != null) {
            this.mLayout.onScrollStateChanged(i);
        }
        onScrollStateChanged(i);
        if (this.mScrollListener != null) {
            this.mScrollListener.onScrollStateChanged(this, i);
        }
        if (this.mScrollListeners != null) {
            for (int size = this.mScrollListeners.size() - 1; size >= 0; size--) {
                this.mScrollListeners.get(size).onScrollStateChanged(this, i);
            }
        }
    }

    public boolean hasPendingAdapterUpdates() {
        return !this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout || this.mAdapterHelper.d();
    }

    /* access modifiers changed from: private */
    public void repositionShadowingViews() {
        int a = this.mChildHelper.a();
        for (int i = 0; i < a; i++) {
            View b = this.mChildHelper.b(i);
            ViewHolder childViewHolder = getChildViewHolder(b);
            if (!(childViewHolder == null || childViewHolder.mShadowingHolder == null)) {
                View view = childViewHolder.mShadowingHolder.itemView;
                int left = b.getLeft();
                int top = b.getTop();
                if (left != view.getLeft() || top != view.getTop()) {
                    view.layout(left, top, view.getWidth() + left, view.getHeight() + top);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void dispatchChildDetached(View view) {
        ViewHolder childViewHolderInt = getChildViewHolderInt(view);
        onChildDetachedFromWindow(view);
        if (!(this.mAdapter == null || childViewHolderInt == null)) {
            this.mAdapter.onViewDetachedFromWindow(childViewHolderInt);
        }
        if (this.mOnChildAttachStateListeners != null) {
            for (int size = this.mOnChildAttachStateListeners.size() - 1; size >= 0; size--) {
                this.mOnChildAttachStateListeners.get(size).a(view);
            }
        }
    }

    /* access modifiers changed from: private */
    public void dispatchChildAttached(View view) {
        ViewHolder childViewHolderInt = getChildViewHolderInt(view);
        onChildAttachedToWindow(view);
        if (!(this.mAdapter == null || childViewHolderInt == null)) {
            this.mAdapter.onViewAttachedToWindow(childViewHolderInt);
        }
        if (this.mOnChildAttachStateListeners != null) {
            for (int size = this.mOnChildAttachStateListeners.size() - 1; size >= 0; size--) {
                this.mOnChildAttachStateListeners.get(size);
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x005a, code lost:
        continue;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getAdapterPositionFor(android.support.v7.widget.RecyclerView.ViewHolder r8) {
        /*
            r7 = this;
            r0 = 524(0x20c, float:7.34E-43)
            boolean r0 = r8.hasAnyOfTheFlags(r0)
            r1 = -1
            if (r0 != 0) goto L_0x005e
            boolean r0 = r8.isBound()
            if (r0 != 0) goto L_0x0010
            goto L_0x005e
        L_0x0010:
            android.support.v7.widget.AdapterHelper r0 = r7.mAdapterHelper
            int r8 = r8.mPosition
            java.util.ArrayList<android.support.v7.widget.AdapterHelper$UpdateOp> r2 = r0.a
            int r2 = r2.size()
            r3 = 0
        L_0x001b:
            if (r3 >= r2) goto L_0x005d
            java.util.ArrayList<android.support.v7.widget.AdapterHelper$UpdateOp> r4 = r0.a
            java.lang.Object r4 = r4.get(r3)
            android.support.v7.widget.AdapterHelper$UpdateOp r4 = (android.support.v7.widget.AdapterHelper.UpdateOp) r4
            int r5 = r4.a
            r6 = 8
            if (r5 == r6) goto L_0x0047
            switch(r5) {
                case 1: goto L_0x003f;
                case 2: goto L_0x002f;
                default: goto L_0x002e;
            }
        L_0x002e:
            goto L_0x005a
        L_0x002f:
            int r5 = r4.b
            if (r5 > r8) goto L_0x005a
            int r5 = r4.b
            int r6 = r4.d
            int r5 = r5 + r6
            if (r5 <= r8) goto L_0x003b
            return r1
        L_0x003b:
            int r4 = r4.d
            int r8 = r8 - r4
            goto L_0x005a
        L_0x003f:
            int r5 = r4.b
            if (r5 > r8) goto L_0x005a
            int r4 = r4.d
            int r8 = r8 + r4
            goto L_0x005a
        L_0x0047:
            int r5 = r4.b
            if (r5 != r8) goto L_0x004e
            int r8 = r4.d
            goto L_0x005a
        L_0x004e:
            int r5 = r4.b
            if (r5 >= r8) goto L_0x0054
            int r8 = r8 + -1
        L_0x0054:
            int r4 = r4.d
            if (r4 > r8) goto L_0x005a
            int r8 = r8 + 1
        L_0x005a:
            int r3 = r3 + 1
            goto L_0x001b
        L_0x005d:
            return r8
        L_0x005e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.getAdapterPositionFor(android.support.v7.widget.RecyclerView$ViewHolder):int");
    }

    public void setNestedScrollingEnabled(boolean z) {
        this.mScrollingChildHelper.setNestedScrollingEnabled(z);
    }

    public boolean isNestedScrollingEnabled() {
        return this.mScrollingChildHelper.isNestedScrollingEnabled();
    }

    public boolean startNestedScroll(int i) {
        return this.mScrollingChildHelper.startNestedScroll(i);
    }

    public void stopNestedScroll() {
        this.mScrollingChildHelper.stopNestedScroll();
    }

    public boolean hasNestedScrollingParent() {
        return this.mScrollingChildHelper.hasNestedScrollingParent();
    }

    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        return this.mScrollingChildHelper.dispatchNestedScroll(i, i2, i3, i4, iArr);
    }

    public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        return this.mScrollingChildHelper.dispatchNestedPreScroll(i, i2, iArr, iArr2);
    }

    public boolean dispatchNestedFling(float f, float f2, boolean z) {
        return this.mScrollingChildHelper.dispatchNestedFling(f, f2, z);
    }

    public boolean dispatchNestedPreFling(float f, float f2) {
        return this.mScrollingChildHelper.dispatchNestedPreFling(f, f2);
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int i, int i2) {
        if (this.mChildDrawingOrderCallback == null) {
            return super.getChildDrawingOrder(i, i2);
        }
        return this.mChildDrawingOrderCallback.a(i, i2);
    }
}
