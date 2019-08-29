package com.alipay.mobile.nebula.provider;

import android.content.Context;
import android.view.ViewGroup;
import com.alipay.mobile.nebula.view.H5NavMenuView;
import com.alipay.mobile.nebula.view.H5PullHeaderView;
import com.alipay.mobile.nebula.view.H5TitleView;
import com.alipay.mobile.nebula.view.H5WebContentView;

public interface H5ViewProvider {
    H5NavMenuView createNavMenu();

    H5PullHeaderView createPullHeaderView(Context context, ViewGroup viewGroup);

    H5TitleView createTitleView(Context context);

    H5WebContentView createWebContentView(Context context);
}
