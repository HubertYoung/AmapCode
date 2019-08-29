package com.alipay.mobile.nebula.provider;

import android.content.Context;
import android.view.ViewGroup;
import com.alipay.mobile.nebula.view.H5PullHeaderView;

public interface H5PullHeaderViewProvider {
    H5PullHeaderView createPullHeaderView(Context context, ViewGroup viewGroup);

    boolean enableUsePullHeader();
}
