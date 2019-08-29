package com.autonavi.bundle.uitemplate.mapwidget.widget.zoom;

import com.autonavi.bundle.uitemplate.mapwidget.inter.IEventDelegate;

public interface IZoomWidgetEventDelegate extends IEventDelegate {
    void onClickToZoomIn();

    void onClickToZoomOut();
}
