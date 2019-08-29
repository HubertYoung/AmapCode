package com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh;

import android.view.View;
import com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.OnLoadMoreListener;
import com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.OnPullEventListener;
import com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.OnPullListener;
import com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.State;

public interface IPullToRefresh<T extends View> {
    Mode getCurrentMode();

    Mode getMode();

    T getRefreshableView();

    State getState();

    boolean isPullToRefreshEnabled();

    boolean isRefreshing();

    void onRefreshComplete();

    void setMode(Mode mode);

    void setOnLoadMoreListener(OnLoadMoreListener<T> onLoadMoreListener);

    void setOnPullEventListener(OnPullEventListener<T> onPullEventListener);

    void setOnPullListener(OnPullListener onPullListener);

    void setOnRefreshListener(OnRefreshListener<T> onRefreshListener);

    void setRefreshing();

    void setRefreshing(boolean z);
}
