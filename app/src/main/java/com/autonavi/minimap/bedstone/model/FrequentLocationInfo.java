package com.autonavi.minimap.bedstone.model;

import com.amap.bundle.datamodel.FavoritePOI;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Logs;
import java.io.Serializable;
import java.util.ArrayList;

public class FrequentLocationInfo implements Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    public String childType;
    public String cityCode;
    public String endPoiExtension;
    public ArrayList<GeoPoint> entranceList;
    public String floor;
    public String fnona;
    public String name;
    public String parent;
    public String poiType;
    public String poiid;
    public String towardsAngle;
    public String transparent;
    public int x;
    public int y;

    public FrequentLocationInfo clone() {
        try {
            return (FrequentLocationInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            Logs.e("FrequentLocationInfo", "clone", e);
            return null;
        }
    }

    public FrequentLocationInfo copyFromPoi(POI poi) {
        if (poi != null) {
            try {
                this.poiid = poi.getId();
                this.name = poi.getName();
                this.x = poi.getPoint().x;
                this.y = poi.getPoint().y;
                this.cityCode = poi.getCityCode();
                this.poiType = poi.getType();
                this.endPoiExtension = poi.getEndPoiExtension();
                this.transparent = poi.getTransparent();
                FavoritePOI favoritePOI = (FavoritePOI) poi.as(FavoritePOI.class);
                if (favoritePOI != null) {
                    this.childType = favoritePOI.getChildType();
                    this.fnona = favoritePOI.getFnona();
                    this.parent = favoritePOI.getParent();
                    this.towardsAngle = favoritePOI.getTowardsAngle();
                }
                this.entranceList = poi.getEntranceList();
            } catch (Exception unused) {
            }
        }
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("FrequentLocationInfo{poiid='");
        sb.append(this.poiid);
        sb.append('\'');
        sb.append(", name='");
        sb.append(this.name);
        sb.append('\'');
        sb.append(", x=");
        sb.append(this.x);
        sb.append(", y=");
        sb.append(this.y);
        sb.append(", cityCode='");
        sb.append(this.cityCode);
        sb.append('\'');
        sb.append(", poiType='");
        sb.append(this.poiType);
        sb.append('\'');
        sb.append(", towardsAngle='");
        sb.append(this.towardsAngle);
        sb.append('\'');
        sb.append(", parent='");
        sb.append(this.parent);
        sb.append('\'');
        sb.append(", floor='");
        sb.append(this.floor);
        sb.append('\'');
        sb.append(", childType='");
        sb.append(this.childType);
        sb.append('\'');
        sb.append(", fnona='");
        sb.append(this.fnona);
        sb.append('\'');
        sb.append(", endPoiExtension='");
        sb.append(this.endPoiExtension);
        sb.append('\'');
        sb.append(", transparent='");
        sb.append(this.transparent);
        sb.append('\'');
        sb.append(", entranceList='");
        sb.append(this.entranceList);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
