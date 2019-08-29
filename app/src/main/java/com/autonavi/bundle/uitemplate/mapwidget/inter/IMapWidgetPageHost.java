package com.autonavi.bundle.uitemplate.mapwidget.inter;

public interface IMapWidgetPageHost {
    void onPageCreated(bid bid);

    void onPageDestroy(bid bid);

    void onPagePause(bid bid);

    void onPageResume(bid bid);

    void onPageStart(bid bid);

    void onPageStop(bid bid);
}
