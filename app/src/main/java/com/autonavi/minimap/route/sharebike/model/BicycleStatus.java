package com.autonavi.minimap.route.sharebike.model;

public class BicycleStatus extends BaseNetResult {
    private ego bicycle;

    public ego getBicycle() {
        return this.bicycle;
    }

    public void setBicycle(ego ego) {
        this.bicycle = ego;
    }
}
