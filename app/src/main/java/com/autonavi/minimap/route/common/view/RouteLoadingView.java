package com.autonavi.minimap.route.common.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import com.autonavi.minimap.R;

public class RouteLoadingView extends RelativeLayout {
    public RouteLoadingView(Context context) {
        super(context);
    }

    public RouteLoadingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public RouteLoadingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    @TargetApi(21)
    public RouteLoadingView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.route_loading_view, this, true);
    }
}
