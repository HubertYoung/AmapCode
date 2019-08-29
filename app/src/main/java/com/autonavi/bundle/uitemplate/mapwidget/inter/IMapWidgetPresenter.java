package com.autonavi.bundle.uitemplate.mapwidget.inter;

import com.autonavi.bundle.uitemplate.api.IAMapActivityHost;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget;
import com.autonavi.map.fragmentcontainer.page.IPage;

public interface IMapWidgetPresenter<Widget extends IMapWidget> extends IAMapActivityHost, IMapWidgetPageHost {
    <T extends IPage> void addListener(WidgetListener<T> widgetListener);

    <Delegate extends IEventDelegate> Delegate getEventDelegate();

    Widget getWidget();

    void initialize(Widget widget);

    void isNeedDispatchEvent(boolean z);

    void removeAllListener();

    <T extends IPage> void removeListener(WidgetListener<T> widgetListener);

    <Delegate extends IEventDelegate> void setEventDelegate(Delegate delegate);
}
