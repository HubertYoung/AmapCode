package com.alipay.mobile.nebula.view;

import android.view.View;

public interface H5PullHeaderView {
    public static final int DEFAULT_STYLE = 0;
    public static final int TRANSPARENT_STYLE = 1;

    View getContentView();

    void onProgressUpdate(int i);

    void onRefreshFinish();

    void setLastRefresh();

    void showFinish();

    void showLoading();

    void showOpen(int i);

    void showOver();
}
