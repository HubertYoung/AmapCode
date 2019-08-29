package com.autonavi.bundle.uitemplate.mapwidget.widget.gps;

import android.content.Context;
import android.view.View;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import com.autonavi.minimap.R;

public class GPSMapWidget extends AbstractMapWidget<GpsWidgetPresenter> {
    public GPSMapWidget(Context context, IWidgetProperty iWidgetProperty) {
        super(context, iWidgetProperty);
    }

    public View createContentView(Context context) {
        GpsMapView gpsMapView = new GpsMapView(context, getGpsLayout(), getLogVersionState());
        gpsMapView.setContentDescription(context.getResources().getString(R.string.ctdes_text_gps));
        return gpsMapView;
    }

    public int getGpsLayout() {
        return R.layout.map_widget_gps_layout;
    }
}
