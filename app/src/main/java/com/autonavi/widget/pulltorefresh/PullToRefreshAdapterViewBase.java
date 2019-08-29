package com.autonavi.widget.pulltorefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import com.autonavi.minimap.R;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.AnimationStyle;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.a;
import com.autonavi.widget.pulltorefresh.internal.IndicatorLayout;

public abstract class PullToRefreshAdapterViewBase<T extends AbsListView> extends PullToRefreshBase<T> implements OnScrollListener {
    private View mEmptyView;
    private IndicatorLayout mIndicatorIvBottom;
    private IndicatorLayout mIndicatorIvTop;
    private boolean mLastItemVisible;
    private a mOnLastItemVisibleListener;
    private OnScrollListener mOnScrollListener;
    private boolean mScrollEmptyView = true;
    private boolean mShowIndicator;

    private static LayoutParams convertEmptyViewLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams == null) {
            return null;
        }
        LayoutParams layoutParams2 = new LayoutParams(layoutParams);
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            layoutParams2.gravity = ((LinearLayout.LayoutParams) layoutParams).gravity;
            return layoutParams2;
        }
        layoutParams2.gravity = 17;
        return layoutParams2;
    }

    public PullToRefreshAdapterViewBase(Context context) {
        super(context);
        ((AbsListView) this.mRefreshableView).setOnScrollListener(this);
    }

    public PullToRefreshAdapterViewBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        ((AbsListView) this.mRefreshableView).setOnScrollListener(this);
    }

    public PullToRefreshAdapterViewBase(Context context, Mode mode) {
        super(context, mode);
        ((AbsListView) this.mRefreshableView).setOnScrollListener(this);
    }

    public PullToRefreshAdapterViewBase(Context context, Mode mode, AnimationStyle animationStyle) {
        super(context, mode, animationStyle);
        ((AbsListView) this.mRefreshableView).setOnScrollListener(this);
    }

    public void unRegisterAllListener() {
        super.unRegistAllListener();
        this.mOnScrollListener = null;
    }

    public boolean getShowIndicator() {
        return this.mShowIndicator;
    }

    public final void onScroll(AbsListView absListView, int i, int i2, int i3) {
        if (this.mOnLastItemVisibleListener != null) {
            this.mLastItemVisible = i3 > 0 && i + i2 >= i3 + -1;
        }
        if (getShowIndicatorInternal()) {
            updateIndicatorViewsVisibility();
        }
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScroll(absListView, i, i2, i3);
        }
    }

    public final void onScrollStateChanged(AbsListView absListView, int i) {
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScrollStateChanged(absListView, i);
        }
    }

    public void setAdapter(ListAdapter listAdapter) {
        ((AdapterView) this.mRefreshableView).setAdapter(listAdapter);
    }

    public final void setEmptyView(View view) {
        FrameLayout refreshableViewWrapper = getRefreshableViewWrapper();
        if (view != null) {
            view.setClickable(true);
            ViewParent parent = view.getParent();
            if (parent != null && (parent instanceof ViewGroup)) {
                ((ViewGroup) parent).removeView(view);
            }
            LayoutParams convertEmptyViewLayoutParams = convertEmptyViewLayoutParams(view.getLayoutParams());
            if (convertEmptyViewLayoutParams != null) {
                refreshableViewWrapper.addView(view, convertEmptyViewLayoutParams);
            } else {
                refreshableViewWrapper.addView(view);
            }
        }
        if (this.mRefreshableView instanceof erf) {
            ((erf) this.mRefreshableView).setEmptyViewInternal(view);
        } else {
            ((AbsListView) this.mRefreshableView).setEmptyView(view);
        }
        this.mEmptyView = view;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        ((AbsListView) this.mRefreshableView).setOnItemClickListener(onItemClickListener);
    }

    public final void setOnLastItemVisibleListener(a aVar) {
        this.mOnLastItemVisibleListener = aVar;
    }

    public final void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public final void setScrollEmptyView(boolean z) {
        this.mScrollEmptyView = z;
    }

    public void setShowIndicator(boolean z) {
        this.mShowIndicator = z;
        if (getShowIndicatorInternal()) {
            addIndicatorViews();
        } else {
            removeIndicatorViews();
        }
    }

    /* access modifiers changed from: protected */
    public void onPullToRefresh() {
        super.onPullToRefresh();
        if (getShowIndicatorInternal()) {
            switch (getCurrentMode()) {
                case PULL_FROM_END:
                    this.mIndicatorIvBottom.pullToRefresh();
                    return;
                case PULL_FROM_START:
                    this.mIndicatorIvTop.pullToRefresh();
                    break;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onRefreshing(boolean z) {
        super.onRefreshing(z);
        if (getShowIndicatorInternal()) {
            updateIndicatorViewsVisibility();
        }
    }

    /* access modifiers changed from: protected */
    public void onReleaseToRefresh() {
        super.onReleaseToRefresh();
        if (getShowIndicatorInternal()) {
            switch (getCurrentMode()) {
                case PULL_FROM_END:
                    this.mIndicatorIvBottom.releaseToRefresh();
                    return;
                case PULL_FROM_START:
                    this.mIndicatorIvTop.releaseToRefresh();
                    break;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onReset() {
        super.onReset();
        if (getShowIndicatorInternal()) {
            updateIndicatorViewsVisibility();
        }
    }

    /* access modifiers changed from: protected */
    public void handleStyledAttributes(TypedArray typedArray) {
        this.mShowIndicator = typedArray.getBoolean(R.styleable.PullToRefreshAttrs_mPtrShowIndicator, !isPullToRefreshOverScrollEnabled());
    }

    /* access modifiers changed from: protected */
    public boolean isReadyForPullStart() {
        return isFirstItemVisible();
    }

    /* access modifiers changed from: protected */
    public boolean isReadyForPullEnd() {
        return isLastItemVisible();
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        if (this.mEmptyView != null && !this.mScrollEmptyView) {
            this.mEmptyView.scrollTo(-i, -i2);
        }
    }

    /* access modifiers changed from: protected */
    public void updateUIForMode() {
        super.updateUIForMode();
        if (getShowIndicatorInternal()) {
            addIndicatorViews();
        } else {
            removeIndicatorViews();
        }
    }

    private void addIndicatorViews() {
        Mode mode = getMode();
        FrameLayout refreshableViewWrapper = getRefreshableViewWrapper();
        if (mode.showHeaderLoadingLayout() && this.mIndicatorIvTop == null) {
            this.mIndicatorIvTop = new IndicatorLayout(getContext(), Mode.PULL_FROM_START);
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            layoutParams.rightMargin = getResources().getDimensionPixelSize(R.dimen.pulltorefresh_indicator_right_padding);
            layoutParams.gravity = 53;
            refreshableViewWrapper.addView(this.mIndicatorIvTop, layoutParams);
        } else if (!mode.showHeaderLoadingLayout() && this.mIndicatorIvTop != null) {
            refreshableViewWrapper.removeView(this.mIndicatorIvTop);
            this.mIndicatorIvTop = null;
        }
        if (!mode.showFooterLoadingLayout() || this.mIndicatorIvBottom != null) {
            if (!mode.showFooterLoadingLayout() && this.mIndicatorIvBottom != null) {
                refreshableViewWrapper.removeView(this.mIndicatorIvBottom);
                this.mIndicatorIvBottom = null;
            }
            return;
        }
        this.mIndicatorIvBottom = new IndicatorLayout(getContext(), Mode.PULL_FROM_END);
        LayoutParams layoutParams2 = new LayoutParams(-2, -2);
        layoutParams2.rightMargin = getResources().getDimensionPixelSize(R.dimen.pulltorefresh_indicator_right_padding);
        layoutParams2.gravity = 85;
        refreshableViewWrapper.addView(this.mIndicatorIvBottom, layoutParams2);
    }

    private boolean getShowIndicatorInternal() {
        return this.mShowIndicator && isPullToRefreshEnabled();
    }

    /* access modifiers changed from: protected */
    public boolean isFirstItemVisible() {
        Adapter adapter = ((AbsListView) this.mRefreshableView).getAdapter();
        if (adapter == null || adapter.isEmpty()) {
            return true;
        }
        if (((AbsListView) this.mRefreshableView).getFirstVisiblePosition() <= 1) {
            View childAt = ((AbsListView) this.mRefreshableView).getChildAt(0);
            if (childAt == null || childAt.getTop() < ((AbsListView) this.mRefreshableView).getTop()) {
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean isLastItemVisible() {
        Adapter adapter = ((AbsListView) this.mRefreshableView).getAdapter();
        if (adapter == null || adapter.isEmpty()) {
            return true;
        }
        int lastVisiblePosition = ((AbsListView) this.mRefreshableView).getLastVisiblePosition();
        if (lastVisiblePosition >= (((AbsListView) this.mRefreshableView).getCount() - 1) - 1) {
            View childAt = ((AbsListView) this.mRefreshableView).getChildAt(lastVisiblePosition - ((AbsListView) this.mRefreshableView).getFirstVisiblePosition());
            if (childAt == null || childAt.getBottom() > ((AbsListView) this.mRefreshableView).getBottom()) {
                return false;
            }
            return true;
        }
        return false;
    }

    private void removeIndicatorViews() {
        if (this.mIndicatorIvTop != null) {
            getRefreshableViewWrapper().removeView(this.mIndicatorIvTop);
            this.mIndicatorIvTop = null;
        }
        if (this.mIndicatorIvBottom != null) {
            getRefreshableViewWrapper().removeView(this.mIndicatorIvBottom);
            this.mIndicatorIvBottom = null;
        }
    }

    private void updateIndicatorViewsVisibility() {
        if (this.mIndicatorIvTop != null) {
            if (isRefreshing() || !isReadyForPullStart()) {
                if (this.mIndicatorIvTop.isVisible()) {
                    this.mIndicatorIvTop.hide();
                }
            } else if (!this.mIndicatorIvTop.isVisible()) {
                this.mIndicatorIvTop.show();
            }
        }
        if (this.mIndicatorIvBottom != null) {
            if (isRefreshing() || !isReadyForPullEnd()) {
                if (this.mIndicatorIvBottom.isVisible()) {
                    this.mIndicatorIvBottom.hide();
                }
            } else if (!this.mIndicatorIvBottom.isVisible()) {
                this.mIndicatorIvBottom.show();
            }
        }
    }
}
