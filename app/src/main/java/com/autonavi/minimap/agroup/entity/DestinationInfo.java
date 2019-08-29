package com.autonavi.minimap.agroup.entity;

import com.amap.bundle.blutils.log.DebugLog;
import java.io.Serializable;

public class DestinationInfo implements Serializable, Cloneable {
    private static final long serialVersionUID = 6196881642809510606L;
    public String address;
    public String childType;
    public String cityCode;
    public String endPoiExtension;
    public String entranceList;
    public String exitList;
    public String floor;
    public String industry;
    public String name;
    public String newType;
    public String parent;
    public String phoneNumbers;
    public String poiType;
    public String poiid;
    public String towardsAngle;
    public String transparent;
    public int x;
    public int y;

    public DestinationInfo clone() {
        try {
            return (DestinationInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            DebugLog.e("DestinationInfo", "clone", e);
            return null;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("DestinationInfo{poiid='");
        sb.append(this.poiid);
        sb.append('\'');
        sb.append(", name='");
        sb.append(this.name);
        sb.append('\'');
        sb.append(", address='");
        sb.append(this.address);
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
        sb.append(", phoneNumbers='");
        sb.append(this.phoneNumbers);
        sb.append('\'');
        sb.append(", newType='");
        sb.append(this.newType);
        sb.append('\'');
        sb.append(", industry='");
        sb.append(this.industry);
        sb.append('\'');
        sb.append(", towardsAngle='");
        sb.append(this.towardsAngle);
        sb.append('\'');
        sb.append(", endPoiExtension='");
        sb.append(this.endPoiExtension);
        sb.append('\'');
        sb.append(", transparent='");
        sb.append(this.transparent);
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
        sb.append(", entranceList='");
        sb.append(this.entranceList);
        sb.append('\'');
        sb.append(", exitList='");
        sb.append(this.exitList);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
