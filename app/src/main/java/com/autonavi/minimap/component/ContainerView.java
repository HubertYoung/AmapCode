package com.autonavi.minimap.component;

import android.content.Context;
import android.widget.FrameLayout;

public class ContainerView extends FrameLayout {
    public ContainerView(Context context, drr drr) {
        super(context);
        init(drr);
    }

    private void init(drr drr) {
        setFocusableInTouchMode(true);
        setFocusable(true);
        requestFocus();
        setBackgroundColor(0);
        setOnClickListener(drr);
        setOnKeyListener(drr);
    }

    public void destroy() {
        setOnClickListener(null);
        setOnKeyListener(null);
    }
}
