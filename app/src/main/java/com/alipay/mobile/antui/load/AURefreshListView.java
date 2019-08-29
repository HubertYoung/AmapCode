package com.alipay.mobile.antui.load;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout.LayoutParams;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUDragLoadingView;
import com.alipay.mobile.antui.basic.AUFrameLayout;
import com.alipay.mobile.antui.basic.AUListView;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.autonavi.widget.ui.BalloonLayout;
import java.util.ArrayList;
import java.util.List;

public class AURefreshListView extends AUListView implements OnScrollListener {
    private static final String TAG = "AURefreshListView";
    private String currentStyle = "_BLUE";
    private d delayRunnable = null;
    private float firstMotionY = -1.0f;
    private int fixedHeaderHeight = 0;
    private View fixedHeaderView;
    private boolean hasMore = true;
    private AUFrameLayout headerContainer;
    private int heightSum = 0;
    private boolean isRefreshing = false;
    private boolean isShowLoad = false;
    private boolean isTouching;
    /* access modifiers changed from: private */
    public AbsLoadingView loadingView;
    /* access modifiers changed from: private */
    public int loadingViewTopMargin;
    private AbsLoadMoreView mLoadMoreView;
    private OnLoadMoreListener mOnLoadMoreListener;
    /* access modifiers changed from: private */
    public OnPullRefreshListener mOnPullRefreshListener;
    private List<OnScrollListener> mOnScrollListeners;
    private int maxPullDistance;
    /* access modifiers changed from: private */
    public int refreshDistance;
    private e refreshFinishAnimationListener;
    /* access modifiers changed from: private */
    public boolean refreshFinished;
    private AnimationListener releaseToRefreshAnimListener = new b(this);
    private float secondMotionY = -1.0f;

    public AURefreshListView(Context paramContext) {
        super(paramContext);
        init(paramContext);
    }

    public AURefreshListView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init(paramContext);
    }

    public AURefreshListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init(paramContext);
    }

    public void setLoadingView(@ColorInt int backgroundColor, String style) {
        if (!TextUtils.equals(style, this.currentStyle) && !TextUtils.isEmpty(style)) {
            this.currentStyle = style;
        }
        if (backgroundColor != 0) {
            this.headerContainer.setBackgroundColor(backgroundColor);
        }
    }

    public void setLoadingView(@ColorInt int backgroundColor, AbsLoadingView loadingView2) {
        if (loadingView2 != null) {
            this.headerContainer.removeView(this.loadingView);
            LayoutParams progressLayoutParams = new LayoutParams(-2, -2);
            progressLayoutParams.gravity = 1;
            this.loadingView = loadingView2;
            initLoadingListener();
            this.headerContainer.addView((View) this.loadingView, (ViewGroup.LayoutParams) progressLayoutParams);
        }
        if (backgroundColor != 0) {
            this.headerContainer.setBackgroundColor(backgroundColor);
        }
    }

    public void setLoadMore(boolean loadMore) {
        if (loadMore) {
            setLoadMore((AbsLoadMoreView) new AUDragLoadingView(getContext()));
        }
    }

    public void setLoadMore(AbsLoadMoreView loadMoreView) {
        if (loadMoreView != null) {
            this.mLoadMoreView = loadMoreView;
            this.isShowLoad = true;
            addFooterView(this.mLoadMoreView);
        }
    }

    public void addOnScrollListener(OnScrollListener onScrollListener) {
        if (this.mOnScrollListeners == null) {
            this.mOnScrollListeners = new ArrayList();
        }
        this.mOnScrollListeners.add(onScrollListener);
    }

    public void setOnPullRefreshListener(OnPullRefreshListener onPullRefreshListener) {
        this.mOnPullRefreshListener = onPullRefreshListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    public void startRefresh() {
        startRefresh(true);
    }

    public void startRefresh(boolean callListener) {
        AuiLogger.debug(TAG, "startRefresh, isRefreshing : " + this.isRefreshing);
        if (!this.isRefreshing && this.loadingView != null) {
            this.loadingView.initAnimation(this.currentStyle);
            this.loadingView.onPullOver(0, 1);
            this.refreshFinished = false;
            this.isRefreshing = true;
            ValueAnimator valueAnimatorSmall = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            valueAnimatorSmall.setDuration(400);
            valueAnimatorSmall.addUpdateListener(new g(this, this.loadingView, this.refreshDistance + this.loadingViewTopMargin, callListener));
            valueAnimatorSmall.start();
        }
    }

    public void finishRefresh() {
        if (this.delayRunnable == null) {
            this.delayRunnable = new d(this, 0);
        }
        postDelayed(this.delayRunnable, BalloonLayout.DEFAULT_DISPLAY_DURATION);
        finishRefresh(true);
    }

    public void finishRefresh(boolean afterLoadingAppeared) {
        AuiLogger.debug(TAG, "refreshFinished, afterLoadingAppeared:" + afterLoadingAppeared + ",firstLoadingAppeared:" + this.loadingView.isFirstLoadingAppeared());
        if (!afterLoadingAppeared || this.loadingView.isFirstLoadingAppeared()) {
            invokeReleaseAnimation();
        }
        this.refreshFinished = true;
    }

    public void updateLoadMore(boolean isShowLoad2, boolean hasMore2) {
        this.isShowLoad = isShowLoad2;
        this.hasMore = hasMore2;
    }

    public void setLoadingText(String loadingText) {
        this.loadingView.setLoadingText(loadingText);
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (this.mLoadMoreView != null && view.getLastVisiblePosition() == view.getCount() - 1 && !this.isRefreshing) {
            if (!this.isShowLoad) {
                this.mLoadMoreView.loadingFinished(false);
                removeFooterView(this.mLoadMoreView);
            } else if (!this.hasMore) {
                this.mLoadMoreView.loadingFinished(this.hasMore);
            } else {
                this.mLoadMoreView.startLoading();
                if (this.mOnLoadMoreListener != null) {
                    this.mOnLoadMoreListener.onLoadMore();
                }
            }
        }
        if (this.mOnScrollListeners != null && this.mOnScrollListeners.size() > 0) {
            for (OnScrollListener onScrollStateChanged : this.mOnScrollListeners) {
                onScrollStateChanged.onScrollStateChanged(view, scrollState);
            }
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (this.mOnScrollListeners != null && this.mOnScrollListeners.size() > 0) {
            for (OnScrollListener onScroll : this.mOnScrollListeners) {
                onScroll.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        }
    }

    public void setFixedHeaderView(View headerView) {
        this.fixedHeaderView = headerView;
        if (headerView != null) {
            this.headerContainer.addView(headerView, new ViewGroup.LayoutParams(-1, -2));
        }
    }

    public AbsLoadingView getLoadingView() {
        return this.loadingView;
    }

    private void invokeReleaseAnimation() {
        AuiLogger.debug(TAG, "invokeReleaseAnimation");
        f animation = new f(this, this.loadingView, this.loadingViewTopMargin);
        animation.setDuration(300);
        if (this.refreshFinishAnimationListener == null) {
            AuiLogger.debug(TAG, "refreshFinishAnimationListener is null ");
            this.refreshFinishAnimationListener = new e(this, 0);
        }
        animation.setAnimationListener(this.refreshFinishAnimationListener);
        animation.setInterpolator(new DecelerateInterpolator());
        this.loadingView.startAnimation(animation);
    }

    private void releaseToRefreshPosition() {
        f animation = new f(this, this.loadingView, this.loadingViewTopMargin + this.refreshDistance);
        animation.setDuration(100);
        animation.setAnimationListener(this.releaseToRefreshAnimListener);
        this.loadingView.startAnimation(animation);
        this.loadingView.setFirstLoadingAppeared(false);
    }

    private void init(Context paramContext) {
        this.headerContainer = new AUFrameLayout(paramContext);
        this.loadingView = new AntLoadingView(getContext());
        initLoadingListener();
        LayoutParams progressLayoutParams = new LayoutParams(-2, -2);
        progressLayoutParams.gravity = 1;
        this.headerContainer.addView((View) this.loadingView, (ViewGroup.LayoutParams) progressLayoutParams);
        this.headerContainer.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
        setOnScrollListener(this);
        addHeaderView(this.headerContainer);
        this.maxPullDistance = getResources().getDimensionPixelSize(R.dimen.pull_refresh_max_distance);
        this.refreshDistance = getResources().getDimensionPixelSize(R.dimen.pull_refresh_distance);
    }

    private void reset() {
        this.heightSum = 0;
        this.firstMotionY = -1.0f;
        this.secondMotionY = -1.0f;
    }

    private void initLoadingListener() {
        this.loadingView.setLoadingListener(new c(this));
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            AuiLogger.error(TAG, ex.toString());
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
        if (this.refreshDistance == 0) {
            this.refreshDistance = this.loadingView.getMeasuredHeight();
        }
        int previousFixedHeaderHeight = this.fixedHeaderHeight;
        if (this.fixedHeaderView != null) {
            this.fixedHeaderHeight = this.fixedHeaderView.getHeight();
        }
        if (this.loadingView != null && this.loadingView.getHeight() > 0) {
            this.loadingViewTopMargin = this.fixedHeaderHeight - this.loadingView.getHeight();
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.loadingView.getLayoutParams();
            if (marginLayoutParams != null && !this.isTouching && !this.isRefreshing && marginLayoutParams.topMargin != this.loadingViewTopMargin) {
                marginLayoutParams.topMargin = this.loadingViewTopMargin;
                AuiLogger.error(TAG, "loadingViewTopMargin 1 :" + marginLayoutParams.topMargin);
                this.loadingView.setLayoutParams(marginLayoutParams);
            } else if (marginLayoutParams != null && previousFixedHeaderHeight != 0 && previousFixedHeaderHeight != this.fixedHeaderHeight) {
                marginLayoutParams.topMargin += this.fixedHeaderHeight - previousFixedHeaderHeight;
                AuiLogger.error(TAG, "loadingViewTopMargin 2 :" + marginLayoutParams.topMargin);
                this.loadingView.setLayoutParams(marginLayoutParams);
            } else if (marginLayoutParams != null && this.isRefreshing && previousFixedHeaderHeight != this.fixedHeaderHeight) {
                marginLayoutParams.topMargin = this.loadingViewTopMargin + this.refreshDistance;
                AuiLogger.error(TAG, "loadingViewTopMargin 3 :" + marginLayoutParams.topMargin);
                this.loadingView.setLayoutParams(marginLayoutParams);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent) {
        if (getFirstVisiblePosition() > 0) {
            return super.onTouchEvent(paramMotionEvent);
        }
        if (paramMotionEvent.getAction() == 1) {
            this.isTouching = false;
            reset();
        } else if (paramMotionEvent.getAction() == 0 || paramMotionEvent.getAction() == 2) {
            this.isTouching = true;
        }
        if (this.isRefreshing) {
            AuiLogger.debug(TAG, "onTouch, isRefreshing = true");
            return super.onTouchEvent(paramMotionEvent);
        }
        switch (paramMotionEvent.getAction() & 255) {
            case 0:
                if (this.headerContainer.getBottom() >= this.fixedHeaderHeight) {
                    this.firstMotionY = paramMotionEvent.getY();
                    break;
                }
                break;
            case 1:
                reset();
                upToRefresh();
                break;
            case 2:
                if (this.headerContainer.getBottom() >= this.fixedHeaderHeight) {
                    if (!onTouchMoveEvent(paramMotionEvent)) {
                        return super.onTouchEvent(paramMotionEvent);
                    }
                    return true;
                }
                break;
            case 5:
                onSecondaryPointerDown(paramMotionEvent, paramMotionEvent.getActionIndex());
                break;
            case 6:
                onSecondaryPointerUp(paramMotionEvent);
                break;
        }
        return super.onTouchEvent(paramMotionEvent);
    }

    private boolean onTouchMoveEvent(MotionEvent paramMotionEvent) {
        this.loadingView.initAnimation(this.currentStyle);
        int height = this.heightSum;
        int pointCount = paramMotionEvent.getPointerCount();
        if (pointCount == 1) {
            if (this.firstMotionY == -1.0f) {
                this.firstMotionY = paramMotionEvent.getY(0);
            }
            height += (int) (((paramMotionEvent.getY(0) - this.firstMotionY) / 2.0f) + ((float) this.fixedHeaderHeight));
        } else if (pointCount >= 2) {
            if (this.firstMotionY == -1.0f) {
                this.firstMotionY = paramMotionEvent.getY(0);
            }
            if (this.secondMotionY == -1.0f) {
                this.secondMotionY = paramMotionEvent.getY(1);
            }
            height += (int) (((paramMotionEvent.getY(0) - this.firstMotionY) / 2.0f) + ((paramMotionEvent.getY(1) - this.secondMotionY) / 2.0f) + ((float) this.fixedHeaderHeight));
        }
        if (height <= this.fixedHeaderHeight) {
            if (((MarginLayoutParams) this.loadingView.getLayoutParams()).topMargin <= this.loadingViewTopMargin) {
                return false;
            }
        } else if (height >= this.fixedHeaderHeight + this.maxPullDistance) {
            height = this.fixedHeaderHeight + this.maxPullDistance;
        }
        setLoadingViewLoc(height);
        return true;
    }

    private void upToRefresh() {
        if (this.headerContainer.getBottom() - this.fixedHeaderHeight >= this.refreshDistance) {
            releaseToRefreshPosition();
            this.refreshFinished = false;
            this.isRefreshing = true;
            if (this.mOnPullRefreshListener != null) {
                this.mOnPullRefreshListener.onRefresh();
            }
            AuiLogger.debug(TAG, "onRefresh, thread : " + Thread.currentThread().getId());
        } else if (this.headerContainer.getBottom() > this.fixedHeaderHeight) {
            finishRefresh(false);
        }
    }

    private void onSecondaryPointerUp(MotionEvent paramMotionEvent) {
        float motionY;
        int i = paramMotionEvent.getActionIndex();
        if (i == 0 || i == 1) {
            if (i == 0) {
                motionY = this.firstMotionY;
                this.firstMotionY = this.secondMotionY;
            } else {
                motionY = this.secondMotionY;
                this.secondMotionY = -1.0f;
            }
            this.heightSum += ((int) (paramMotionEvent.getY(i) - motionY)) / 2;
        }
    }

    private void onSecondaryPointerDown(MotionEvent paramMotionEvent, int index) {
        if (index == 1) {
            this.secondMotionY = paramMotionEvent.getY(1);
        } else if (index == 0) {
            this.secondMotionY = this.firstMotionY;
            this.firstMotionY = paramMotionEvent.getY(0);
        }
    }

    /* access modifiers changed from: private */
    public void refreshFinishLayoutAction() {
        AuiLogger.debug(TAG, "refreshFinishLayoutAction");
        removeDelayInvoke();
        this.isRefreshing = false;
        if (this.loadingView != null) {
            this.loadingView.finishLoading();
            MarginLayoutParams marginParams = (MarginLayoutParams) this.loadingView.getLayoutParams();
            if (marginParams != null) {
                marginParams.topMargin = this.loadingViewTopMargin;
                this.loadingView.setLayoutParams(marginParams);
            }
        }
    }

    private void setLoadingViewLoc(int height) {
        if (this.loadingView != null) {
            this.loadingView.onPullOver(height - this.fixedHeaderHeight, this.refreshDistance);
            MarginLayoutParams loadingLayoutParams = (MarginLayoutParams) this.loadingView.getLayoutParams();
            loadingLayoutParams.topMargin = height - this.loadingView.getHeight();
            this.loadingView.setLayoutParams(loadingLayoutParams);
        }
    }

    private void removeDelayInvoke() {
        if (this.delayRunnable != null) {
            AuiLogger.debug(TAG, "removeDelayInvoke");
            removeCallbacks(this.delayRunnable);
        }
    }
}
