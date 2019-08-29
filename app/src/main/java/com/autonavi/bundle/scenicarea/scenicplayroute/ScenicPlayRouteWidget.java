package com.autonavi.bundle.scenicarea.scenicplayroute;

import android.content.Context;
import android.view.View;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;

public class ScenicPlayRouteWidget extends AbstractMapWidget<ScenicPlayRouteWidgetPresenter> {
    public ScenicPlayRouteWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        if (this.mContentView != null) {
            return this.mContentView;
        }
        this.mContentView = new ScenicPlayRouteView(context);
        this.mContentView.setVisibility(0);
        return this.mContentView;
    }
}
