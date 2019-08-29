package com.autonavi.minimap.route.sharebike.model;

import java.io.Serializable;

public class MixBicycle implements Serializable {
    public String citycode;
    public BicycleBasicItem item;
    public String scope;

    public MixBicycle(BicycleBasicItem bicycleBasicItem, String str, String str2) {
        this.item = bicycleBasicItem;
        this.scope = str;
        this.citycode = str2;
    }
}
