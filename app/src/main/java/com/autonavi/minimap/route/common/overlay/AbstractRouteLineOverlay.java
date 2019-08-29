package com.autonavi.minimap.route.common.overlay;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.LineOverlay;
import com.autonavi.minimap.base.overlay.LineOverlayItem;

public abstract class AbstractRouteLineOverlay extends LineOverlay {
    private eap mConfig;

    public int getArrowLineItemType() {
        return 6;
    }

    public AbstractRouteLineOverlay(bty bty, int i) {
        super(bty);
        initLineConfig(i);
    }

    public AbstractRouteLineOverlay(int i, bty bty, int i2) {
        super(i, bty);
        initLineConfig(i2);
    }

    private void initLineConfig(int i) {
        this.mConfig = eap.a(i);
    }

    public void createAndAddBackgroundLineItem(GeoPoint[] geoPointArr) {
        LineOverlayItem lineOverlayItem = new LineOverlayItem(1, geoPointArr, agn.a(this.mContext, (float) this.mConfig.f()));
        lineOverlayItem.setFillLineColor(this.mConfig.a());
        lineOverlayItem.setFillLineId(R.drawable.route_map_frontlr);
        lineOverlayItem.setBackgroundColor(this.mConfig.b());
        lineOverlayItem.setBackgrondId(R.drawable.route_map_lr);
        addItem(lineOverlayItem);
    }

    public void createAndAddAlterBackgroundLineItem(GeoPoint[] geoPointArr) {
        LineOverlayItem lineOverlayItem = new LineOverlayItem(1, geoPointArr, agn.a(this.mContext, (float) this.mConfig.f()));
        lineOverlayItem.setFillLineColor(-6438919);
        lineOverlayItem.setFillLineId(R.drawable.route_map_frontlr);
        lineOverlayItem.setBackgroundColor(-8476174);
        lineOverlayItem.setBackgrondId(R.drawable.route_map_lr);
        addItem(lineOverlayItem);
    }

    public void createAndAddAlphaBackgroundLineItem(GeoPoint[] geoPointArr) {
        LineOverlayItem lineOverlayItem = new LineOverlayItem(1, geoPointArr, agn.a(this.mContext, (float) this.mConfig.f()));
        lineOverlayItem.setFillLineColor(-4204801);
        lineOverlayItem.setFillLineId(R.drawable.route_map_frontlr);
        lineOverlayItem.setBackgroundColor(-6110987);
        lineOverlayItem.setBackgrondId(R.drawable.route_map_lr);
        addItem(lineOverlayItem);
    }

    public int createAndAddLinkPathItem(GeoPoint[] geoPointArr) {
        LineOverlayItem lineOverlayItem = new LineOverlayItem(5, geoPointArr, agn.a(this.mContext, 3.0f));
        lineOverlayItem.setFillLineId(R.drawable.route_map_gray);
        addItem(lineOverlayItem);
        return getSize() - 1;
    }

    public int createAndAddArrowLineItem(GeoPoint[] geoPointArr) {
        LineOverlayItem lineOverlayItem = new LineOverlayItem(getArrowLineItemType(), geoPointArr, agn.a(this.mContext, (float) this.mConfig.f()));
        lineOverlayItem.setFillLineId(this.mConfig.c());
        addItem(lineOverlayItem);
        return getSize() - 1;
    }

    public LineOverlayItem createAndAddArrowLineItem2(GeoPoint[] geoPointArr) {
        LineOverlayItem lineOverlayItem = new LineOverlayItem(getArrowLineItemType(), geoPointArr, agn.a(this.mContext, (float) this.mConfig.f()));
        lineOverlayItem.setFillLineId(this.mConfig.c());
        addItem(lineOverlayItem);
        return lineOverlayItem;
    }

    public void createHighlightLineItem(GeoPoint[] geoPointArr) {
        LineOverlayItem lineOverlayItem = new LineOverlayItem(1, geoPointArr, agn.a(this.mContext, (float) this.mConfig.f()));
        lineOverlayItem.setFillLineColor(-1436881970);
        lineOverlayItem.setFillLineId(R.drawable.route_map_lr);
        addItem(lineOverlayItem);
    }

    public LineOverlayItem createPassedLineItem(GeoPoint[] geoPointArr) {
        LineOverlayItem lineOverlayItem = new LineOverlayItem(1, geoPointArr, agn.a(this.mContext, (float) this.mConfig.f()));
        lineOverlayItem.setFillLineColor(this.mConfig.e());
        lineOverlayItem.setFillLineId(R.drawable.route_map_frontlr);
        lineOverlayItem.setBackgroundColor(this.mConfig.d());
        lineOverlayItem.setBackgrondId(R.drawable.route_map_lr);
        addItem(lineOverlayItem);
        return lineOverlayItem;
    }

    public void createEagleEyeLine(GeoPoint[] geoPointArr, int i, int i2) {
        LineOverlayItem lineOverlayItem = new LineOverlayItem(1, geoPointArr, agn.a(this.mContext, (float) i2));
        lineOverlayItem.setFillLineColor(i);
        lineOverlayItem.setFillLineId(R.drawable.route_map_frontlr);
        addItem(lineOverlayItem);
    }
}
