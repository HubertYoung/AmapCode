package com.autonavi.minimap.route.bus.realtimebus.view.fliprecyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

public class RealtimeInfoLayoutManager extends LinearLayoutManager {
    boolean a = true;

    public RealtimeInfoLayoutManager(Context context) {
        super(context);
    }

    public RealtimeInfoLayoutManager(Context context, int i, boolean z) {
        super(context, i, z);
    }

    public RealtimeInfoLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public boolean canScrollVertically() {
        return super.canScrollVertically() && this.a;
    }
}
