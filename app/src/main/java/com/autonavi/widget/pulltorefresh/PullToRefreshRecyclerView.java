package com.autonavi.widget.pulltorefresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.AttributeSet;
import android.view.View;
import com.autonavi.minimap.R;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.AnimationStyle;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Orientation;

public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {
    public PullToRefreshRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle animationStyle) {
        super(context, mode, animationStyle);
    }

    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    /* access modifiers changed from: protected */
    public RecyclerView createRefreshableView(Context context, AttributeSet attributeSet) {
        RecyclerView recyclerView = new RecyclerView(context, attributeSet);
        recyclerView.setId(R.id.recyclerview);
        return recyclerView;
    }

    /* access modifiers changed from: protected */
    public boolean isReadyForPullStart() {
        return isFirstItemVisible();
    }

    /* access modifiers changed from: protected */
    public boolean isReadyForPullEnd() {
        return isLastItemVisible();
    }

    private boolean isFirstItemVisible() {
        Adapter adapter = ((RecyclerView) getRefreshableView()).getAdapter();
        if (adapter == null || adapter.getItemCount() == 0) {
            return true;
        }
        if (getFirstVisiblePosition() != 0 || ((RecyclerView) this.mRefreshableView).getChildAt(0).getTop() < ((RecyclerView) this.mRefreshableView).getTop()) {
            return false;
        }
        return true;
    }

    private int getFirstVisiblePosition() {
        View childAt = ((RecyclerView) this.mRefreshableView).getChildAt(0);
        if (childAt != null) {
            return ((RecyclerView) this.mRefreshableView).getChildAdapterPosition(childAt);
        }
        return -1;
    }

    private boolean isLastItemVisible() {
        Adapter adapter = ((RecyclerView) getRefreshableView()).getAdapter();
        if (adapter == null || adapter.getItemCount() == 0) {
            return true;
        }
        if (getLastVisiblePosition() < ((RecyclerView) this.mRefreshableView).getAdapter().getItemCount() - 1 || ((RecyclerView) this.mRefreshableView).getChildAt(((RecyclerView) this.mRefreshableView).getChildCount() - 1).getBottom() > ((RecyclerView) this.mRefreshableView).getBottom()) {
            return false;
        }
        return true;
    }

    private int getLastVisiblePosition() {
        View childAt = ((RecyclerView) this.mRefreshableView).getChildAt(((RecyclerView) this.mRefreshableView).getChildCount() - 1);
        if (childAt != null) {
            return ((RecyclerView) this.mRefreshableView).getChildAdapterPosition(childAt);
        }
        return -1;
    }
}
