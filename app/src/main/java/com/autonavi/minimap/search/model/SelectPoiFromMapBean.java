package com.autonavi.minimap.search.model;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import java.io.Serializable;
import java.util.ArrayList;

public class SelectPoiFromMapBean implements Serializable {
    public static final int LINE_OVERLAY_STYLE_DEFAULT = 1;
    public static final int LINE_OVERLAY_STYLE_ROUTE_FOOT = 2;
    private static final long serialVersionUID = 2;
    private boolean isHideOldPoi;
    private int level = -1;
    private int lineOverlayItemStyle = 1;
    private POI mFromPOI;
    private ArrayList<POI> mMidPOIs;
    private GeoPoint mapCenter;
    private POI oldPOI;
    private GeoPoint[] points;
    private POI toPOI;

    public SelectPoiFromMapBean() {
    }

    public SelectPoiFromMapBean(POI poi, POI poi2, ArrayList<POI> arrayList) {
        this.mFromPOI = poi;
        this.toPOI = poi2;
        this.mMidPOIs = arrayList;
    }

    public POI getFromPOI() {
        return this.mFromPOI;
    }

    public void setFromPOI(POI poi) {
        this.mFromPOI = poi;
    }

    public ArrayList<POI> getMidPOIs() {
        return this.mMidPOIs;
    }

    public void setMidPOIs(ArrayList<POI> arrayList) {
        this.mMidPOIs = arrayList;
    }

    public POI getToPOI() {
        return this.toPOI;
    }

    public void setToPOI(POI poi) {
        this.toPOI = poi;
    }

    public GeoPoint getMapCenter() {
        return this.mapCenter;
    }

    public void setMapCenter(GeoPoint geoPoint) {
        this.mapCenter = geoPoint;
    }

    public POI getOldPOI() {
        return this.oldPOI;
    }

    public void setOldPOI(POI poi) {
        this.oldPOI = poi;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int i) {
        this.level = i;
    }

    public GeoPoint[] getPoints() {
        return this.points;
    }

    public void setPoints(GeoPoint[] geoPointArr) {
        this.points = geoPointArr;
    }

    public int getLineOverlayItemStyle() {
        return this.lineOverlayItemStyle;
    }

    public void setLineOverlayItemStyle(int i) {
        this.lineOverlayItemStyle = i;
    }

    public boolean isHideOldPoi() {
        return this.isHideOldPoi;
    }

    public void setHideOldPoi(boolean z) {
        this.isHideOldPoi = z;
    }
}
