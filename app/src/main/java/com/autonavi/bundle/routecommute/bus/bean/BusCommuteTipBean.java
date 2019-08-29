package com.autonavi.bundle.routecommute.bus.bean;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath.Emergency;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRouteResponse.StopEvent;
import com.autonavi.minimap.R;
import java.io.Serializable;
import java.util.ArrayList;

public class BusCommuteTipBean implements Serializable {
    private static final long serialVersionUID = 1;
    public String busStatus;
    public GeoPoint currentLocPoint;
    public String customLineColor;
    public String destinationStr = "";
    public Emergency emergency;
    public String etaStr = "";
    public int firstSegmentBusType;
    public int foucsIndex;
    public boolean hasIcon;
    public int iconId;
    public String iconUrl;
    public boolean isGoHome;
    public boolean isRealtime;
    public boolean isSettingUser;
    public boolean isSingleLine;
    public String lineName = "";
    public int realtimeColorId = R.color.f_c_16;
    public String realtimeStr = "";
    public int simIconId;
    public ArrayList<StopEvent> stopEventList;
    public int time_tag;
    public String transferInfo = "";

    public String toString() {
        StringBuilder sb = new StringBuilder("BusCommuteTipBean{destinationStr='");
        sb.append(this.destinationStr);
        sb.append('\'');
        sb.append(", etaStr='");
        sb.append(this.etaStr);
        sb.append('\'');
        sb.append(", transferInfo='");
        sb.append(this.transferInfo);
        sb.append('\'');
        sb.append(", lineName='");
        sb.append(this.lineName);
        sb.append('\'');
        sb.append(", emergency=");
        sb.append(this.emergency);
        sb.append(", isRealtime=");
        sb.append(this.isRealtime);
        sb.append(", time_tag=");
        sb.append(this.time_tag);
        sb.append(", realtimeStr='");
        sb.append(this.realtimeStr);
        sb.append('\'');
        sb.append(", isSingleLine=");
        sb.append(this.isSingleLine);
        sb.append(", customLineColor=");
        sb.append(this.customLineColor);
        sb.append('}');
        return sb.toString();
    }
}
