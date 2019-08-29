package com.autonavi.minimap.component;

import android.content.Context;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;

public class BottomView extends LinearLayout {
    public void destroy() {
    }

    public BottomView(Context context, drr drr) {
        super(context);
        init(drr);
    }

    private void init(drr drr) {
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.gravity = 80;
        setLayoutParams(layoutParams);
        setOrientation(1);
    }
}
