package com.alipay.mobile.nebula.refresh;

import android.view.View;

public interface H5PullAdapter {
    boolean canPull();

    boolean canRefresh();

    View getHeaderView();

    void onFinish();

    void onLoading();

    void onOpen();

    void onOver();

    void onProgressUpdate(int i);

    void onRefreshFinish();
}
