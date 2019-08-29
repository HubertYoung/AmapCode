package com.autonavi.minimap.route.voice.model;

import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
public class PoiModel {
    public double lat = -1000.0d;
    public double lon = -1000.0d;
    public String poiId;
    public String poiName;
    public int poiType = -1;
}
