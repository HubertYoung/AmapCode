package com.autonavi.bundle.uitemplate.mapwidget.inter;

import android.content.Context;
import android.graphics.Rect;
import java.util.List;

public interface IMapWidgetContainer extends IMapWidgetManagerExtend {
    int addWidget(IMapWidget iMapWidget);

    void enterImmersiveMode(Rect rect, IMapWidget... iMapWidgetArr);

    void enterImmersiveMode(IMapWidget... iMapWidgetArr);

    Context getContainerContext();

    int getWidgetVisible(IMapWidget iMapWidget);

    void setWidgetVisible(IMapWidget iMapWidget, int i);

    void setWidgets(List<IMapWidget> list);
}
