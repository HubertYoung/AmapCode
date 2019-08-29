package com.autonavi.jni.bedstone.model;

import com.autonavi.common.utils.Logs;
import com.autonavi.minimap.bedstone.model.FrequentLocationInfo;
import java.io.Serializable;

public class FrequentLocationDBInfo implements Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    public FrequentLocationInfo FrequentLocation = new FrequentLocationInfo();
    public String infoJsonString = "";
    public String name;
    public String poiid;
    public int trafficType;
    public int x;
    public int y;

    public FrequentLocationDBInfo clone() {
        FrequentLocationDBInfo frequentLocationDBInfo;
        Throwable e;
        try {
            frequentLocationDBInfo = (FrequentLocationDBInfo) super.clone();
            try {
                frequentLocationDBInfo.FrequentLocation = this.FrequentLocation.clone();
            } catch (CloneNotSupportedException e2) {
                e = e2;
            }
        } catch (CloneNotSupportedException e3) {
            Throwable th = e3;
            frequentLocationDBInfo = null;
            e = th;
            Logs.e("FrequentLocationDBInfo", "clone", e);
            return frequentLocationDBInfo;
        }
        return frequentLocationDBInfo;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("FrequentLocationDBInfo{trafficType='");
        sb.append(this.trafficType);
        sb.append('\'');
        sb.append(", poiid='");
        sb.append(this.poiid);
        sb.append('\'');
        sb.append(", name='");
        sb.append(this.name);
        sb.append('\'');
        sb.append(", x='");
        sb.append(this.x);
        sb.append('\'');
        sb.append(", y=");
        sb.append(this.y);
        sb.append(", FrequentLocationInfo=");
        sb.append(this.FrequentLocation);
        sb.append('}');
        return sb.toString();
    }
}
