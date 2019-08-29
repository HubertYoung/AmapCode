package com.autonavi.minimap.route.bus.realtimebus.model;

import com.autonavi.common.model.GeoPoint;
import java.io.Serializable;

public class TripInfo implements Serializable {
    private static final long serialVersionUID = -8197725956873830705L;
    public String arrstid;
    public GeoPoint gpoi;
    public String sitedistance;
    public String sitetime;
    public int state;
    public String stationleft;
    public int tripid;
}
