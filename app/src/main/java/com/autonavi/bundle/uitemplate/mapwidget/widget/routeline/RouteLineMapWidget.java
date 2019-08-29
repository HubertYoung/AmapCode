package com.autonavi.bundle.uitemplate.mapwidget.widget.routeline;

import android.content.Context;
import android.view.View;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import com.autonavi.minimap.R;

public class RouteLineMapWidget extends AbstractMapWidget<RouteLineWidgetPresenter> {
    public RouteLineMapWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        return loadLayoutRes(context, R.layout.map_widget_route_line);
    }
}
