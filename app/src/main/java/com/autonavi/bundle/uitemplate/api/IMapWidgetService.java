package com.autonavi.bundle.uitemplate.api;

public interface IMapWidgetService extends IAMapActivityHost, IAmapPageHost {
    Object getCurrentPage();

    void onBindMapWidgets(IWidgetProperty... iWidgetPropertyArr);

    void onBindPage(Object obj);

    void releaseContainerConfig();

    void restoreContainerConfig();

    void saveContainerConfig();

    void unBindMapWidgets();
}
