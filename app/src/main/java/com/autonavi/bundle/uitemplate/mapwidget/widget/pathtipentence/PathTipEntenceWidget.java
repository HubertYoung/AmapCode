package com.autonavi.bundle.uitemplate.mapwidget.widget.pathtipentence;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import com.autonavi.minimap.R;

public class PathTipEntenceWidget extends AbstractMapWidget<PathTipEntencePresenter> {
    public PathTipEntenceWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.layout_path_tipenter, null);
    }
}
