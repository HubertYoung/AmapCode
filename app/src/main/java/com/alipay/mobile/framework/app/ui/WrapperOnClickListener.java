package com.alipay.mobile.framework.app.ui;

import android.view.View;
import android.view.View.OnClickListener;

public class WrapperOnClickListener implements OnClickListener {
    private OnClickListener a;

    public WrapperOnClickListener(OnClickListener onClickListener) {
        this.a = onClickListener;
    }

    public void onClick(View view) {
        view.getContext();
        this.a.onClick(view);
    }
}
