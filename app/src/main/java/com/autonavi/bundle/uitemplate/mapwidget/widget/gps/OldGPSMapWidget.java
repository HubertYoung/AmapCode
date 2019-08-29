package com.autonavi.bundle.uitemplate.mapwidget.widget.gps;

import android.content.Context;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.minimap.R;

public class OldGPSMapWidget extends GPSMapWidget {
    public OldGPSMapWidget(Context context, IWidgetProperty iWidgetProperty) {
        super(context, iWidgetProperty);
    }

    public int getGpsLayout() {
        return R.layout.old_map_widget_gps_layout;
    }

    public Class<GpsWidgetPresenter> getPresenterClass() {
        return GpsWidgetPresenter.class;
    }
}
