package com.autonavi.bundle.uitemplate.mapwidget.widget.compass;

import android.content.Context;
import android.view.View;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import com.autonavi.minimap.R;

public class CompassMapWidget extends AbstractMapWidget<CompassWidgetPresenter> {
    private UICompassView mCompassView;

    public CompassMapWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        View loadLayoutRes = loadLayoutRes(context, R.layout.map_widget_compass_layout);
        this.mCompassView = (UICompassView) loadLayoutRes.findViewById(R.id.map_widget_compass);
        this.mCompassView.setCompassRes(R.drawable.map_widget_compass_icon);
        loadLayoutRes.setVisibility(8);
        return loadLayoutRes;
    }

    public UICompassView getCompassView() {
        return this.mCompassView;
    }
}
