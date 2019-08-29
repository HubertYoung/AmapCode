package com.autonavi.miniapp.plugin.map.indoor;

import android.content.Context;
import com.autonavi.miniapp.plugin.map.indoor.widget.MiniAppFloorWidgetLayoutWithGuildTip;
import com.autonavi.miniapp.plugin.map.indoor.widget.MiniAppFloorWidgetLayoutWithLocationTip;
import com.autonavi.miniapp.plugin.map.indoor.widget.MiniAppFloorWidgetView.IContainer;

public class MiniAppFloorWidgetOwner {
    private boolean enableFloorWidget;
    private Context mContext;
    private MiniAppFloorWidgetLayoutWithGuildTip mFloorWidgetLayoutWithGuildTip;
    private bty mMapView;

    public boolean isGpsFollowed() {
        return false;
    }

    public MiniAppFloorWidgetOwner(Context context, bty bty, MiniAppFloorWidgetLayoutWithGuildTip miniAppFloorWidgetLayoutWithGuildTip) {
        this.mContext = context;
        this.mMapView = bty;
        this.mFloorWidgetLayoutWithGuildTip = miniAppFloorWidgetLayoutWithGuildTip;
    }

    public Context getContext() {
        return this.mContext;
    }

    public bty getMapView() {
        return this.mMapView;
    }

    public boolean isHideWidget() {
        return !this.enableFloorWidget;
    }

    public MiniAppFloorWidgetLayoutWithLocationTip getFloorWidgetViewLayout() {
        if (this.mFloorWidgetLayoutWithGuildTip != null) {
            return this.mFloorWidgetLayoutWithGuildTip.getFloorWidgetViewLayout();
        }
        return null;
    }

    public IContainer getContainer() {
        return this.mFloorWidgetLayoutWithGuildTip;
    }

    public MiniAppFloorWidgetLayoutWithGuildTip getFloorWidgetWithGuideTip() {
        return this.mFloorWidgetLayoutWithGuildTip;
    }

    public void setEnable(boolean z) {
        this.enableFloorWidget = z;
    }
}
