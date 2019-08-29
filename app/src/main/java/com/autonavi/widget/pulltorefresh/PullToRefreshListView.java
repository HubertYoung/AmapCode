package com.autonavi.widget.pulltorefresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.autonavi.minimap.R;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.AnimationStyle;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Orientation;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.State;
import com.autonavi.widget.pulltorefresh.internal.LoadingLayout;

public class PullToRefreshListView extends PullToRefreshAdapterViewBase<ListView> {
    public LoadingLayout mFooterLoadingView;
    public LoadingLayout mHeaderLoadingView;
    private boolean mListViewExtrasEnabled;
    public FrameLayout mLvFooterLoadingFrame;
    private boolean mNeedRefreshing = true;
    /* access modifiers changed from: private */
    public String mParentWindowClass = "";

    public class InternalListView extends ListView implements erf {
        private boolean mAddedLvFooter = false;

        public InternalListView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            setScrollingCacheEnabled(false);
        }

        /* access modifiers changed from: protected */
        public void dispatchDraw(Canvas canvas) {
            try {
                super.dispatchDraw(canvas);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }

        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            try {
                return super.dispatchTouchEvent(motionEvent);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                return false;
            } catch (IllegalStateException unused) {
                return false;
            }
        }

        public void setAdapter(ListAdapter listAdapter) {
            if (PullToRefreshListView.this.mLvFooterLoadingFrame != null && !this.mAddedLvFooter) {
                addFooterView(PullToRefreshListView.this.mLvFooterLoadingFrame, null, false);
                this.mAddedLvFooter = true;
            }
            super.setAdapter(listAdapter);
        }

        public void setEmptyView(View view) {
            PullToRefreshListView.this.setEmptyView(view);
        }

        public void setEmptyViewInternal(View view) {
            super.setEmptyView(view);
        }

        /* access modifiers changed from: protected */
        public void layoutChildren() {
            try {
                super.layoutChildren();
            } catch (Exception e) {
                e.printStackTrace();
                if (!TextUtils.isEmpty(PullToRefreshListView.this.mParentWindowClass)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(e.getMessage());
                    sb.append("/windowName=");
                    sb.append(PullToRefreshListView.this.mParentWindowClass);
                    sb.append("/ParentClass=");
                    sb.append(PullToRefreshListView.this.getListView().getParent().getParent().getParent().getClass());
                    throw new IllegalStateException(sb.toString());
                }
            }
        }
    }

    @TargetApi(9)
    final class InternalListViewSDK9 extends InternalListView {
        public InternalListViewSDK9(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        /* access modifiers changed from: protected */
        public final boolean overScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
            boolean overScrollBy = super.overScrollBy(i, i2, i3, i4, i5, i6, i7, i8, z);
            eri.a(PullToRefreshListView.this, i, i3, i2, i4, z);
            return overScrollBy;
        }
    }

    public PullToRefreshListView(Context context) {
        super(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PullToRefreshListView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshListView(Context context, Mode mode, AnimationStyle animationStyle) {
        super(context, mode, animationStyle);
    }

    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    public void setNeedRefreshing(boolean z) {
        this.mNeedRefreshing = z;
    }

    /* access modifiers changed from: protected */
    public void onRefreshing(boolean z) {
        int i;
        int i2;
        LoadingLayout loadingLayout;
        LoadingLayout loadingLayout2;
        LoadingLayout loadingLayout3;
        ListAdapter adapter = ((ListView) this.mRefreshableView).getAdapter();
        if (!this.mListViewExtrasEnabled || !getShowViewWhileRefreshing() || adapter == null || adapter.isEmpty()) {
            super.onRefreshing(z);
            return;
        }
        super.onRefreshing(false);
        switch (getCurrentMode()) {
            case MANUAL_REFRESH_ONLY:
            case PULL_FROM_END:
                loadingLayout3 = getFooterLayout();
                loadingLayout2 = isFootershowflag() ? this.mFooterLoadingView : null;
                loadingLayout = this.mHeaderLoadingView;
                i2 = ((ListView) this.mRefreshableView).getCount() - 1;
                i = getScrollY() - getFooterSize();
                break;
            default:
                loadingLayout3 = getHeaderLayout();
                loadingLayout2 = this.mHeaderLoadingView;
                loadingLayout = this.mFooterLoadingView;
                i = getHeaderSize() + getScrollY();
                i2 = 0;
                break;
        }
        loadingLayout3.reset();
        loadingLayout3.hideAllViews();
        loadingLayout.setVisibility(8);
        if (this.mNeedRefreshing && loadingLayout2 != null) {
            loadingLayout2.setVisibility(0);
            loadingLayout2.refreshing();
        }
        if (z) {
            disableLoadingLayoutVisibilityChanges();
            setHeaderScroll(i);
            ((ListView) this.mRefreshableView).setSelection(i2);
            smoothScrollTo(0);
        }
    }

    /* access modifiers changed from: protected */
    public void onReset() {
        int i;
        int i2;
        LoadingLayout loadingLayout;
        LoadingLayout loadingLayout2;
        if (!this.mListViewExtrasEnabled) {
            super.onReset();
            return;
        }
        boolean z = false;
        boolean z2 = true;
        switch (getCurrentMode()) {
            case MANUAL_REFRESH_ONLY:
            case PULL_FROM_END:
                loadingLayout2 = getFooterLayout();
                loadingLayout = this.mFooterLoadingView;
                i2 = ((ListView) this.mRefreshableView).getCount() - 1;
                i = getFooterSize();
                if (Math.abs(((ListView) this.mRefreshableView).getLastVisiblePosition() - i2) <= 1) {
                    z = true;
                    break;
                }
                break;
            default:
                loadingLayout2 = getHeaderLayout();
                loadingLayout = this.mHeaderLoadingView;
                i = -getHeaderSize();
                if (Math.abs(((ListView) this.mRefreshableView).getFirstVisiblePosition() - 0) > 1) {
                    z2 = false;
                }
                z = z2;
                i2 = 0;
                break;
        }
        if (loadingLayout.getVisibility() == 0) {
            loadingLayout2.showInvisibleViews();
            loadingLayout.setVisibility(8);
            if (z && getState() != State.MANUAL_REFRESHING) {
                ((ListView) this.mRefreshableView).setSelection(i2);
                setHeaderScroll(i);
            }
        }
        super.onReset();
    }

    /* access modifiers changed from: protected */
    public erh createLoadingLayoutProxy(boolean z, boolean z2) {
        erh createLoadingLayoutProxy = super.createLoadingLayoutProxy(z, z2);
        if (this.mListViewExtrasEnabled) {
            Mode mode = getMode();
            if (z && mode.showHeaderLoadingLayout()) {
                createLoadingLayoutProxy.a(this.mHeaderLoadingView);
            }
            if (z2 && mode.showFooterLoadingLayout()) {
                createLoadingLayoutProxy.a(this.mFooterLoadingView);
            }
        }
        return createLoadingLayoutProxy;
    }

    /* access modifiers changed from: protected */
    public ListView createListView(Context context, AttributeSet attributeSet) {
        if (VERSION.SDK_INT >= 9) {
            return new InternalListViewSDK9(context, attributeSet);
        }
        return new InternalListView(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public ListView createRefreshableView(Context context, AttributeSet attributeSet) {
        ListView createListView = createListView(context, attributeSet);
        createListView.setMotionEventSplittingEnabled(false);
        createListView.setId(16908298);
        return createListView;
    }

    /* access modifiers changed from: protected */
    public void handleStyledAttributes(TypedArray typedArray) {
        super.handleStyledAttributes(typedArray);
        this.mListViewExtrasEnabled = typedArray.getBoolean(R.styleable.PullToRefreshAttrs_mPtrListViewExtrasEnabled, true);
        if (this.mListViewExtrasEnabled) {
            LayoutParams layoutParams = new LayoutParams(-1, -2, 1);
            FrameLayout frameLayout = new FrameLayout(getContext());
            this.mHeaderLoadingView = createLoadingLayout(getContext(), Mode.PULL_FROM_START, typedArray);
            this.mHeaderLoadingView.setVisibility(8);
            frameLayout.addView(this.mHeaderLoadingView, layoutParams);
            ((ListView) this.mRefreshableView).addHeaderView(frameLayout, null, false);
            this.mLvFooterLoadingFrame = new FrameLayout(getContext());
            this.mFooterLoadingView = createLoadingLayout(getContext(), Mode.PULL_FROM_END, typedArray);
            this.mFooterLoadingView.setVisibility(8);
            this.mLvFooterLoadingFrame.addView(this.mFooterLoadingView, layoutParams);
            if (!typedArray.hasValue(R.styleable.PullToRefreshAttrs_mPtrScrollingWhileRefreshingEnabled)) {
                setScrollingWhileRefreshingEnabled(true);
            }
        }
    }

    public void setParentWindowClass(String str) {
        this.mParentWindowClass = str;
    }

    public ListView getListView() {
        return (ListView) this.mRefreshableView;
    }

    public LoadingLayout changeFooter() {
        return super.changeFooter();
    }

    public void hideLoadingProgress() {
        if (this.mFooterLoadingView != null) {
            this.mFooterLoadingView.setVisibility(8);
        }
    }

    public void showLoadingProgress() {
        if (this.mFooterLoadingView != null) {
            this.mFooterLoadingView.setVisibility(0);
        }
    }
}
