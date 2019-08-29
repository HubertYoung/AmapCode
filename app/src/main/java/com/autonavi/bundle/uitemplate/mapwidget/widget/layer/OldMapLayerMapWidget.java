package com.autonavi.bundle.uitemplate.mapwidget.widget.layer;

import android.content.Context;
import com.autonavi.minimap.R;

public class OldMapLayerMapWidget extends MapLayerMapWidget {
    public OldMapLayerMapWidget(Context context) {
        super(context);
    }

    public int getLayoutId() {
        return R.layout.old_map_widget_layer_layout;
    }

    public Class<LayerWidgetPresenter> getPresenterClass() {
        return LayerWidgetPresenter.class;
    }
}
