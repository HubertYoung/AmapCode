package com.autonavi.minimap.ajx3.widget.view.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.autonavi.minimap.ajx3.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.util.ViewUtils;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.property.TouchHelper;
import com.autonavi.minimap.ajx3.widget.view.HorizontalScroller;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import com.autonavi.minimap.ajx3.widget.view.list.sticky.StickySectionFooterDecoration;
import com.autonavi.minimap.ajx3.widget.view.list.sticky.StickySectionHeaderDecoration;
import com.autonavi.minimap.ajx3.widget.view.listener.ScrollListener.ScrollBeginListener;
import com.autonavi.minimap.ajx3.widget.view.listener.ScrollListener.ScrollEndListener;
import com.autonavi.minimap.ajx3.widget.view.listener.ScrollListener.ScrollInertiaEnd;

@SuppressLint({"ViewConstructor"})
public class AjxList extends RecyclerView implements ViewExtension {
    public static final int EDGE_EFFECT_MODE_BOTH = 0;
    public static final int EDGE_EFFECT_MODE_BOTTOM = 2;
    public static final int EDGE_EFFECT_MODE_NONE = 3;
    public static final int EDGE_EFFECT_MODE_TOP = 1;
    public static final int MASK_FOR_ANIMATE = 16;
    public static final int MASK_FOR_TOUCH = 1;
    private static final int TOUCH_ENABLED = 0;
    /* access modifiers changed from: private */
    public boolean isForceEnable = false;
    /* access modifiers changed from: private */
    public boolean isSmoothToPositionTopEnable = false;
    protected final IAjxContext mAjxContext;
    /* access modifiers changed from: private */
    public int mEdgeEffectMode = 3;
    private int mIgnoreTouchFlag = 0;
    private InnerScrollListener mInnerScrollListener;
    private boolean mIsScrollByInertia = false;
    public boolean mIsWaterFall = false;
    private PreloadInvoker mPreloadInvoker = new PreloadInvoker();
    private final BaseProperty mProperty;
    /* access modifiers changed from: private */
    public PullToRefreshList mPullToRefreshList;
    /* access modifiers changed from: private */
    public ScrollBeginListener mScrollBeginListener;
    /* access modifiers changed from: private */
    public ScrollBoundListener mScrollBoundListener;
    /* access modifiers changed from: private */
    public ScrollEndListener mScrollEndListener;
    /* access modifiers changed from: private */
    public ScrollInertiaEnd mScrollInertiaEnd;
    /* access modifiers changed from: private */
    public ScrollTopChange mScrollTopChange;
    private StickySectionFooterDecoration mStickySectionFooterDecoration;
    private StickySectionHeaderDecoration mStickySectionHeaderDecoration;
    private View mTouchedHeader = null;
    private int mVerticalOffset = 0;

    class AjxStaggeredGridLayoutManager extends StaggeredGridLayoutManager {
        AjxStaggeredGridLayoutManager(int i, int i2) {
            super(i, i2);
        }

        public void layoutDecorated(View view, int i, int i2, int i3, int i4) {
            super.layoutDecorated(view, i, i2, i3, i4);
        }

        public void onLayoutChildren(Recycler recycler, State state) {
            super.onLayoutChildren(recycler, state);
            if (AjxList.this.mPullToRefreshList.isUpdatingUI()) {
                AjxList.this.mPullToRefreshList.updateFinished();
                AjxList.this.mScrollTopChange.onScrollTopChange(true);
            }
        }

        public int scrollVerticallyBy(int i, Recycler recycler, State state) {
            return super.scrollVerticallyBy(i, recycler, state);
        }

        public void removeAndRecycleAllViews(Recycler recycler) {
            super.removeAndRecycleAllViews(recycler);
        }

        public void removeAndRecycleView(View view, Recycler recycler) {
            super.removeAndRecycleView(view, recycler);
        }

        public void removeAndRecycleViewAt(int i, Recycler recycler) {
            super.removeAndRecycleViewAt(i, recycler);
        }

        public void scrollToPosition(int i) {
            super.scrollToPosition(i);
        }

        public void scrollToPositionWithOffset(int i, int i2) {
            super.scrollToPositionWithOffset(i, i2);
        }

        public void smoothScrollToPosition(RecyclerView recyclerView, State state, int i) {
            super.smoothScrollToPosition(recyclerView, state, i);
        }
    }

    class InnerScrollListener extends OnScrollListener {
        private static final int POSITION_BOTTOM = 2;
        private static final int POSITION_MIDDLE = 1;
        private static final int POSITION_TOP = 0;
        private boolean isDecelerate;
        private boolean isFingerDrag;
        private int mPositionType;

        private InnerScrollListener() {
            this.isFingerDrag = false;
            this.isDecelerate = false;
            this.mPositionType = 0;
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            super.onScrolled(recyclerView, i, i2);
            if (AjxList.this.mScrollTopChange != null) {
                AjxList.this.mScrollTopChange.onScrollTopChange(false);
            }
            if (AjxList.this.mEdgeEffectMode == 0) {
                recyclerView.setOverScrollMode(0);
            } else {
                if (AjxList.this.mEdgeEffectMode == 1) {
                    if (i2 <= 0) {
                        recyclerView.setOverScrollMode(0);
                    }
                } else if (AjxList.this.mEdgeEffectMode == 2 && i2 > 0) {
                    recyclerView.setOverScrollMode(0);
                }
                recyclerView.setOverScrollMode(2);
            }
            if (AjxList.this.mScrollBoundListener != null) {
                if (AjxList.this.isFirstItemVisible()) {
                    if (this.mPositionType != 0) {
                        this.mPositionType = 0;
                        AjxList.this.mScrollBoundListener.onScrollBound();
                    }
                } else if (AjxList.this.isLastItemVisible()) {
                    if (this.mPositionType != 2) {
                        this.mPositionType = 2;
                        AjxList.this.mScrollBoundListener.onScrollBound();
                    }
                } else if (this.mPositionType != 1) {
                    this.mPositionType = 1;
                    AjxList.this.mScrollBoundListener.onScrollChange();
                }
            }
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            super.onScrollStateChanged(recyclerView, i);
            switch (i) {
                case 0:
                    AjxList.this.mScrollTopChange.onScrollTopChange(true);
                    if (this.isDecelerate) {
                        if (AjxList.this.isForceEnable && AjxList.this.isSmoothToPositionTopEnable && AjxList.this.getAccurateScrollOffsetY() > 0) {
                            AjxList.this.smoothScrollToPosition(0);
                        }
                        if (AjxList.this.mScrollInertiaEnd != null) {
                            AjxList.this.mScrollInertiaEnd.onScrollInertiaEnd();
                        }
                    } else if (AjxList.this.mScrollEndListener != null) {
                        AjxList.this.mScrollEndListener.onScrollEnd();
                    }
                    AjxList.this.isForceEnable = false;
                    AjxList.this.isSmoothToPositionTopEnable = false;
                    this.isDecelerate = false;
                    this.isFingerDrag = false;
                    break;
                case 1:
                    this.isFingerDrag = true;
                    if (AjxList.this.mScrollBeginListener != null) {
                        AjxList.this.mScrollBeginListener.onDragBegin();
                        return;
                    }
                    break;
                case 2:
                    this.isDecelerate = true;
                    if (this.isFingerDrag && AjxList.this.mScrollEndListener != null) {
                        AjxList.this.mScrollEndListener.onScrollEnd();
                        return;
                    }
            }
        }
    }

    class MyLinearLayoutManager extends LinearLayoutManager {
        MyLinearLayoutManager(Context context) {
            super(context);
        }

        public void onLayoutChildren(Recycler recycler, State state) {
            super.onLayoutChildren(recycler, state);
            if (AjxList.this.mPullToRefreshList.isUpdatingUI()) {
                AjxList.this.mPullToRefreshList.updateFinished();
                AjxList.this.mScrollTopChange.onScrollTopChange(true);
            }
        }
    }

    class PreloadInvoker implements ListItemAttachChangeListener {
        private static final String EVENT_JS_PRELOAD = "preload";
        private int mLastAttached;
        private int mPreTrigger;
        /* access modifiers changed from: private */
        public boolean needPreload;

        private PreloadInvoker() {
            this.mPreTrigger = 1;
            this.needPreload = false;
            this.mLastAttached = -1;
        }

        private boolean checkIfNeedPreload() {
            if (!this.needPreload) {
                return false;
            }
            int itemCount = AjxList.this.getAdapter() == null ? 0 : AjxList.this.getAdapter().getItemCount();
            if (itemCount != 0 && itemCount >= this.mPreTrigger) {
                return true;
            }
            return false;
        }

        private void invokePreload() {
            AjxList.this.mAjxContext.invokeJsEvent(new Builder().setEventName(EVENT_JS_PRELOAD).setNodeId(AjxList.this.mAjxContext.getDomTree().getNodeId(AjxList.this.mPullToRefreshList)).build());
        }

        /* access modifiers changed from: private */
        public void setPreTrigger(int i) {
            this.mPreTrigger = i;
            if (checkIfNeedPreload()) {
                int itemCount = AjxList.this.getAdapter().getItemCount();
                int attached = AjxList.this.getAdapter().getAttached();
                int detached = AjxList.this.getAdapter().getDetached();
                if (attached > detached) {
                    detached = attached;
                }
                this.mLastAttached = attached;
                int access$1300 = AjxList.this.findFirstVisibleItemPosition();
                if (access$1300 >= 0) {
                    if (access$1300 + this.mPreTrigger <= itemCount && detached + this.mPreTrigger >= itemCount) {
                        invokePreload();
                    }
                } else if (detached >= itemCount - this.mPreTrigger) {
                    invokePreload();
                }
            }
        }

        public void onItemAttachChange(int i, int i2) {
            if (this.mLastAttached != i) {
                this.mLastAttached = i;
                if (checkIfNeedPreload() && i > i2 && i == AjxList.this.getAdapter().getItemCount() - this.mPreTrigger) {
                    invokePreload();
                }
            }
        }
    }

    public interface ScrollBoundListener {
        void onScrollBound();

        void onScrollChange();
    }

    public interface ScrollTopChange {
        void onScrollTopChange(boolean z);
    }

    public void bind(AjxDomNode ajxDomNode) {
    }

    public void bindStrictly(long j) {
    }

    public Object getAttribute(String str) {
        return null;
    }

    public float getSize(String str) {
        return 0.0f;
    }

    public Object getStyle(int i) {
        return null;
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
    }

    public void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
    }

    public void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
    }

    public void updateDiffProperty() {
    }

    public AjxList(@NonNull PullToRefreshList pullToRefreshList, @NonNull IAjxContext iAjxContext, int i) {
        super(new ContextThemeWrapper(iAjxContext.getNativeContext(), R.style.scrollbar_recyclerview));
        if (i > 1) {
            this.mIsWaterFall = true;
        }
        this.mAjxContext = iAjxContext;
        this.mProperty = new BaseProperty(this, iAjxContext);
        this.mPullToRefreshList = pullToRefreshList;
        setOverScrollMode(2);
        setItemAnimator(null);
        if (this.mIsWaterFall) {
            setLayoutManager(new AjxStaggeredGridLayoutManager(i, 1));
        } else {
            setLayoutManager(new MyLinearLayoutManager(iAjxContext.getNativeContext()));
        }
    }

    public BaseProperty getProperty() {
        return this.mProperty;
    }

    public void setPreTrigger(int i) {
        this.mPreloadInvoker.setPreTrigger(i);
    }

    public void trackScroll(boolean z) {
        this.mPreloadInvoker.needPreload = z;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        requestDisallowInterceptTouchEvent();
    }

    public void setAdapter(BaseListAdapter baseListAdapter) {
        super.setAdapter(baseListAdapter);
        baseListAdapter.setListItemAttachChangeListener(this.mPreloadInvoker);
        removeItemDecoration(this.mStickySectionHeaderDecoration);
        this.mStickySectionHeaderDecoration = new StickySectionHeaderDecoration(baseListAdapter);
        this.mStickySectionHeaderDecoration.setPullOffset(this.mVerticalOffset, 0);
        addItemDecoration(this.mStickySectionHeaderDecoration);
        removeItemDecoration(this.mStickySectionFooterDecoration);
        this.mStickySectionFooterDecoration = new StickySectionFooterDecoration(baseListAdapter);
        addItemDecoration(this.mStickySectionFooterDecoration);
        initScrollListener();
    }

    public void setEdgeEffectMode(String str) {
        if ("both".equals(str)) {
            this.mEdgeEffectMode = 0;
        } else if ("top".equals(str)) {
            this.mEdgeEffectMode = 1;
        } else if ("bottom".equals(str)) {
            this.mEdgeEffectMode = 2;
        } else {
            this.mEdgeEffectMode = 3;
        }
    }

    public int getEdgeEffectMode() {
        return this.mEdgeEffectMode;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 && this.mIsScrollByInertia) {
            if (getScrollState() == 2) {
                try {
                    this.mAjxContext.getDomTree().getRootView().getHelper().setIgnoreClickFlag();
                } catch (Exception unused) {
                }
            }
            stopScroll();
        }
        if (this.mIgnoreTouchFlag != 0) {
            return false;
        }
        if (this.mTouchedHeader != null) {
            return true;
        }
        if ((getProperty() == null || getProperty().couldHandleTouch()) && !this.mIsScrollByInertia && super.onInterceptTouchEvent(motionEvent)) {
            return true;
        }
        return false;
    }

    public void enableTouch(int i) {
        this.mIgnoreTouchFlag = (~i) & this.mIgnoreTouchFlag;
    }

    public void disableTouch(int i) {
        this.mIgnoreTouchFlag = i | this.mIgnoreTouchFlag;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.isSmoothToPositionTopEnable = false;
        if (this.mTouchedHeader != null) {
            MotionEvent obtain = MotionEvent.obtain(motionEvent);
            if (this.mTouchedHeader instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) this.mTouchedHeader;
                if (viewGroup.getChildCount() > 0) {
                    TouchHelper.transformIfNeeded(viewGroup.getChildAt(0), obtain);
                }
            }
            HorizontalScroller findHorizontalScroller = findHorizontalScroller(this.mTouchedHeader);
            if (obtain.getAction() == 1 || obtain.getAction() == 3) {
                this.mTouchedHeader = null;
            }
            if (findHorizontalScroller != null) {
                TouchHelper.transformIfNeeded(findHorizontalScroller, obtain);
                findHorizontalScroller.dispatchTouchEvent(obtain);
                invalidate();
            }
            return true;
        } else if ((getProperty() != null && !getProperty().couldHandleTouch()) || this.mIgnoreTouchFlag != 0) {
            return false;
        } else {
            if (motionEvent.getAction() == 0) {
                if (!this.mIsScrollByInertia) {
                    return true;
                }
            } else if (motionEvent.getAction() == 2 && this.mIsScrollByInertia) {
                return false;
            }
            if ((motionEvent.getAction() != 0 || !this.mIsScrollByInertia) && super.onTouchEvent(motionEvent)) {
                return true;
            }
            return false;
        }
    }

    public void doTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
    }

    public View findTouchChild(float f, float f2) {
        return findChildViewUnder(f, f2);
    }

    /* JADX WARNING: type inference failed for: r0v1 */
    /* JADX WARNING: type inference failed for: r2v2, types: [android.view.View] */
    /* JADX WARNING: type inference failed for: r0v2, types: [com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList] */
    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View findTouchView(float r2, float r3) {
        /*
            r1 = this;
            com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList r0 = r1.mPullToRefreshList
            if (r0 != 0) goto L_0x0006
            r0 = r1
            goto L_0x0008
        L_0x0006:
            com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList r0 = r1.mPullToRefreshList
        L_0x0008:
            android.view.View r2 = com.autonavi.minimap.ajx3.util.ViewUtils.getClickView(r2, r3, r1)
            if (r2 == 0) goto L_0x000f
            goto L_0x0010
        L_0x000f:
            r2 = r0
        L_0x0010:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.view.list.AjxList.findTouchView(float, float):android.view.View");
    }

    public BaseListAdapter getAdapter() {
        return (BaseListAdapter) super.getAdapter();
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mProperty.onLayout(z, i, i2, i3, i4);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mProperty.onDraw(canvas);
    }

    public void draw(Canvas canvas) {
        this.mProperty.beforeDraw(canvas);
        super.draw(canvas);
        this.mProperty.afterDraw(canvas);
    }

    public int getScrollToPosition(long j) {
        BaseListAdapter adapter = getAdapter();
        if (adapter == null) {
            return -1;
        }
        return adapter.getScrollToPosition(j);
    }

    public int getScrollToPosition(float f, boolean z) {
        BaseListAdapter adapter = getAdapter();
        if (adapter == null) {
            return -1;
        }
        return adapter.getScrollToPosition(f, z);
    }

    public int getTargetScrollOffsetY(long j) {
        BaseListAdapter adapter = getAdapter();
        if (adapter == null) {
            return 0;
        }
        return adapter.getTargetScrollOffsetY(j);
    }

    public int getAccurateScrollOffsetY() {
        int findFirstVisibleItemPosition = findFirstVisibleItemPosition();
        if (findFirstVisibleItemPosition == -1) {
            return 0;
        }
        BaseListAdapter adapter = getAdapter();
        if (adapter == null) {
            return 0;
        }
        int cellTotalHeight = adapter.getCellTotalHeight(findFirstVisibleItemPosition);
        ViewHolder findViewHolderForAdapterPosition = findViewHolderForAdapterPosition(findFirstVisibleItemPosition);
        if (!(findViewHolderForAdapterPosition == null || findViewHolderForAdapterPosition.itemView == null)) {
            cellTotalHeight -= findViewHolderForAdapterPosition.itemView.getTop();
        }
        return cellTotalHeight;
    }

    public boolean checkViewHolder() {
        int findFirstVisibleItemPosition = findFirstVisibleItemPosition();
        if (findFirstVisibleItemPosition == -1 || getAdapter() == null) {
            return false;
        }
        ViewHolder findViewHolderForAdapterPosition = findViewHolderForAdapterPosition(findFirstVisibleItemPosition);
        if (findViewHolderForAdapterPosition == null || findViewHolderForAdapterPosition.itemView == null) {
            return false;
        }
        return true;
    }

    public void scrollByInertia(boolean z) {
        this.mIsScrollByInertia = z;
        requestDisallowInterceptTouchEvent();
    }

    private void requestDisallowInterceptTouchEvent() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(!this.mIsScrollByInertia);
        }
    }

    public void setScrollBeginListener(ScrollBeginListener scrollBeginListener) {
        this.mScrollBeginListener = scrollBeginListener;
    }

    public void setScrollEndListener(ScrollEndListener scrollEndListener) {
        this.mScrollEndListener = scrollEndListener;
    }

    public void setScrollInertiaEnd(ScrollInertiaEnd scrollInertiaEnd) {
        this.mScrollInertiaEnd = scrollInertiaEnd;
    }

    public void setScrollBoundListener(ScrollBoundListener scrollBoundListener) {
        this.mScrollBoundListener = scrollBoundListener;
    }

    public void setScrollTopChange(ScrollTopChange scrollTopChange) {
        this.mScrollTopChange = scrollTopChange;
    }

    private void initScrollListener() {
        if (this.mInnerScrollListener == null) {
            this.mInnerScrollListener = new InnerScrollListener();
            addOnScrollListener(this.mInnerScrollListener);
        }
    }

    /* access modifiers changed from: private */
    public int findFirstVisibleItemPosition() {
        int i = -1;
        if (this.mIsWaterFall) {
            LayoutManager layoutManager = getLayoutManager();
            if (layoutManager instanceof AjxStaggeredGridLayoutManager) {
                return ((AjxStaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(null)[0];
            }
            return -1;
        }
        LayoutManager layoutManager2 = getLayoutManager();
        if (layoutManager2 instanceof LinearLayoutManager) {
            i = ((LinearLayoutManager) layoutManager2).findFirstVisibleItemPosition();
        }
        return i;
    }

    public boolean isFirstItemVisible() {
        BaseListAdapter adapter = getAdapter();
        if (adapter == null || adapter.getItemCount() == 0) {
            return true;
        }
        View childAt = getChildAt(0);
        if (childAt == null || getChildAdapterPosition(childAt) != 0 || getChildAt(0).getTop() < getTop()) {
            return false;
        }
        return true;
    }

    public boolean isLastItemVisible() {
        BaseListAdapter adapter = getAdapter();
        if (adapter == null || adapter.getItemCount() == 0) {
            return false;
        }
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof AjxStaggeredGridLayoutManager) {
            if ((adapter.getCellTotalHeight() - getHeight()) - getAccurateScrollOffsetY() <= 1) {
                return true;
            }
        } else if (!(layoutManager instanceof LinearLayoutManager) || findLastCompletelyVisibleItemPosition() != adapter.getItemCount() - 1) {
            return false;
        } else {
            return true;
        }
        return false;
    }

    public int findLastCompletelyVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(getChildCount() - 1, -1, true, false);
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
        if (findOneVisibleChild == null) {
            return -1;
        }
        return linearLayoutManager.getPosition(findOneVisibleChild);
    }

    private View findOneVisibleChild(int i, int i2, boolean z, boolean z2) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
        OrientationHelper a = OrientationHelper.a(linearLayoutManager, linearLayoutManager.getOrientation());
        int b = a.b();
        int c = a.c();
        int i3 = i2 > i ? 1 : -1;
        View view = null;
        while (i != i2) {
            View childAt = getChildAt(i);
            int a2 = a.a(childAt);
            int b2 = a.b(childAt);
            if (a2 < c && b2 > b) {
                if (!z) {
                    return childAt;
                }
                if (b2 - a2 > c - b && b2 <= c) {
                    return childAt;
                }
                if (a2 >= b && b2 <= c) {
                    return childAt;
                }
                if (z2 && view == null) {
                    view = childAt;
                }
            }
            i += i3;
        }
        return view;
    }

    public void setPullOffset(int i, int i2) {
        this.mVerticalOffset = i;
        if (this.mStickySectionHeaderDecoration != null) {
            this.mStickySectionHeaderDecoration.setPullOffset(i, i2);
            invalidate();
        }
    }

    public View findTouchHeaderTarget(int i, int i2, View view) {
        if (this.mStickySectionHeaderDecoration == null || view == null) {
            return null;
        }
        if (!(view instanceof ViewGroup)) {
            return view;
        }
        Rect rect = this.mStickySectionHeaderDecoration.mSectionOffset;
        return ViewUtils.getClickView((float) ((i + (view.getLeft() - rect.left)) - view.getLeft()), (float) ((i2 + (view.getTop() - rect.top)) - view.getTop()), (ViewGroup) view);
    }

    public View getTouchedHeader(int i, int i2) {
        if (this.mStickySectionHeaderDecoration != null) {
            View sectionView = getSectionView(this.mStickySectionHeaderDecoration.findSectionPositionUnder(i, i2));
            if (sectionView != null) {
                return sectionView;
            }
        }
        return null;
    }

    private View getSectionView(int i) {
        if (i == -1) {
            return null;
        }
        BaseListAdapter adapter = getAdapter();
        if (adapter != null) {
            return adapter.getSectionHeaderView(this, i);
        }
        return null;
    }

    public void setTouchInHeader(View view) {
        this.mTouchedHeader = view;
    }

    private HorizontalScroller findHorizontalScroller(View view) {
        if (view instanceof HorizontalScroller) {
            return (HorizontalScroller) view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                HorizontalScroller findHorizontalScroller = findHorizontalScroller(viewGroup.getChildAt(i));
                if (findHorizontalScroller != null) {
                    return findHorizontalScroller;
                }
            }
        }
        return null;
    }

    public void smoothScrollToPosition(int i) {
        super.smoothScrollToPosition(i);
        this.isSmoothToPositionTopEnable = i == 0;
    }

    public void smoothScrollBy(int i, int i2) {
        super.smoothScrollBy(i, i2);
        this.isSmoothToPositionTopEnable = false;
    }

    public void scrollBy(int i, int i2) {
        super.scrollBy(i, i2);
        this.isSmoothToPositionTopEnable = false;
    }

    public void scrollToPosition(int i) {
        super.scrollToPosition(i);
        this.isSmoothToPositionTopEnable = false;
    }

    public void scrollTo(int i, int i2) {
        super.scrollTo(i, i2);
        this.isSmoothToPositionTopEnable = false;
    }

    public void stopScroll() {
        super.stopScroll();
        this.isSmoothToPositionTopEnable = false;
    }

    public void setForceFlag(boolean z) {
        this.isForceEnable = z;
    }
}
