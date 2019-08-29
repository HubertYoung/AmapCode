package com.autonavi.bundle.uitemplate.mapwidget.widget.zoom;

import android.content.Context;
import com.autonavi.minimap.R;

public class OldZoomInOutMapWidget extends ZoomInOutMapWidget {
    public OldZoomInOutMapWidget(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public int getLayoutId() {
        return R.layout.old_map_widget_zoom_layout;
    }

    public Class<ZoomWidgetPresenter> getPresenterClass() {
        return ZoomWidgetPresenter.class;
    }
}
