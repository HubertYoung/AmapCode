package com.amap.bundle.drivecommon.model;

import com.autonavi.common.model.GeoPoint;
import java.io.Serializable;

public class JamInfo implements Serializable {
    private static final long serialVersionUID = 7804841960512744397L;
    public GeoPoint gPoint;
    public double lat;
    public double lon;
    public int speed;
}
