package com.autonavi.map.search.comment.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.autonavi.widget.pulltorefresh.PullToRefreshAdapterViewBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.AnimationStyle;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Orientation;

public class PullToRefreshFloatingGroupListView extends PullToRefreshAdapterViewBase<FloatingGroupExpandableListView> {

    class InternalExpandableListView extends FloatingGroupExpandableListView implements erf {
        public InternalExpandableListView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public void setEmptyView(View view) {
            PullToRefreshFloatingGroupListView.this.setEmptyView(view);
        }

        public void setEmptyViewInternal(View view) {
            super.setEmptyView(view);
        }
    }

    public PullToRefreshFloatingGroupListView(Context context) {
        super(context);
    }

    public PullToRefreshFloatingGroupListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PullToRefreshFloatingGroupListView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshFloatingGroupListView(Context context, Mode mode, AnimationStyle animationStyle) {
        super(context, mode, animationStyle);
    }

    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    /* access modifiers changed from: protected */
    public FloatingGroupExpandableListView createRefreshableView(Context context, AttributeSet attributeSet) {
        InternalExpandableListView internalExpandableListView = new InternalExpandableListView(context, attributeSet);
        internalExpandableListView.setId(16908298);
        return internalExpandableListView;
    }
}
