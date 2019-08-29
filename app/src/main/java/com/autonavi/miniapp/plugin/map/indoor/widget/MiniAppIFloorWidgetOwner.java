package com.autonavi.miniapp.plugin.map.indoor.widget;

import android.content.Context;
import android.view.View;
import com.autonavi.map.core.MapManager;
import com.autonavi.miniapp.plugin.map.indoor.widget.MiniAppFloorWidgetView.IContainer;

public interface MiniAppIFloorWidgetOwner {
    IContainer getContainer();

    Context getContext();

    MiniAppFloorWidgetLayoutWithLocationTip getFloorWidgetViewLayout();

    MiniAppFloorWidgetLayoutWithGuildTip getFloorWidgetWithGuideTip();

    View getGuideTipView();

    MapManager getMapManager();

    bty getMapView();

    boolean isGpsFollowed();

    boolean isHideWidget();

    boolean isShowGuildTip();

    boolean isShowWidget();

    void onScrollingFinished();

    void onScrollingStarted();
}
