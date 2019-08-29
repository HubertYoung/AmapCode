package com.autonavi.miniapp.plugin.map.indoor.widget;

import android.content.Context;
import android.view.View;
import com.autonavi.map.core.MapManager;
import com.autonavi.miniapp.plugin.map.indoor.widget.MiniAppFloorWidgetView.IContainer;

public class MiniAppDefaultFloorWidgetOwner implements MiniAppIFloorWidgetOwner {
    private MiniAppFloorWidgetLayoutWithGuildTip mFloorWidgetLayoutWithGuildTip;
    private cdc mHost;

    public boolean isShowGuildTip() {
        return true;
    }

    public void onScrollingFinished() {
    }

    public MiniAppDefaultFloorWidgetOwner(cdc cdc, MiniAppFloorWidgetLayoutWithGuildTip miniAppFloorWidgetLayoutWithGuildTip) {
        this.mHost = cdc;
        this.mFloorWidgetLayoutWithGuildTip = miniAppFloorWidgetLayoutWithGuildTip;
    }

    public Context getContext() {
        return this.mHost.a();
    }

    public bty getMapView() {
        return this.mHost.b().getMapView();
    }

    public MapManager getMapManager() {
        return this.mHost.b();
    }

    public boolean isGpsFollowed() {
        return this.mHost.d().c();
    }

    public boolean isShowWidget() {
        return this.mHost.e() != null && this.mHost.e().isViewEnable(32768);
    }

    public boolean isHideWidget() {
        return this.mHost.b().getMapView().J() > 0.0f;
    }

    public MiniAppFloorWidgetLayoutWithLocationTip getFloorWidgetViewLayout() {
        if (this.mFloorWidgetLayoutWithGuildTip != null) {
            return this.mFloorWidgetLayoutWithGuildTip.getFloorWidgetViewLayout();
        }
        return null;
    }

    public View getGuideTipView() {
        if (this.mFloorWidgetLayoutWithGuildTip != null) {
            return this.mFloorWidgetLayoutWithGuildTip.getGuideTipView();
        }
        return null;
    }

    public IContainer getContainer() {
        return this.mFloorWidgetLayoutWithGuildTip;
    }

    public void onScrollingStarted() {
        if (this.mHost.d() != null) {
            this.mHost.d().f();
        }
    }

    public MiniAppFloorWidgetLayoutWithGuildTip getFloorWidgetWithGuideTip() {
        return this.mFloorWidgetLayoutWithGuildTip;
    }
}
