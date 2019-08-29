package com.autonavi.bundle.uitemplate.mapwidget.widget.routeline;

import android.content.Context;
import android.view.View;
import com.autonavi.minimap.R;

public class OldRouteLineMapWidget extends RouteLineMapWidget {
    public OldRouteLineMapWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        return loadLayoutRes(context, R.layout.old_map_widget_route_line);
    }

    public Class<RouteLineWidgetPresenter> getPresenterClass() {
        return RouteLineWidgetPresenter.class;
    }
}
