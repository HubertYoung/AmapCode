package com.autonavi.bundle.uitemplate.mapwidget.impl;

import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidgetPageHost;

public class MapWidgetHost extends AMapActivityHost implements IMapWidgetPageHost {
    protected String mCurPageClassName;

    public void pageCreated(bid bid) {
    }

    public void pageDestroy(bid bid) {
    }

    public void pagePause(bid bid) {
    }

    public void pageResume(bid bid) {
    }

    public void pageStart(bid bid) {
    }

    public void pageStop(bid bid) {
    }

    public final void onPageCreated(bid bid) {
        pageCreated(bid);
    }

    public final void onPageStart(bid bid) {
        pageStart(bid);
    }

    public final void onPageResume(bid bid) {
        this.mCurPageClassName = bid == null ? "" : bid.getClass().getSimpleName();
        pageResume(bid);
    }

    public final void onPagePause(bid bid) {
        pagePause(bid);
    }

    public final void onPageStop(bid bid) {
        this.mCurPageClassName = "";
        pageStop(bid);
    }

    public void onPageDestroy(bid bid) {
        pageDestroy(bid);
    }
}
