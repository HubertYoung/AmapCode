package com.autonavi.map.db.model;

import java.util.List;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class VehiclesGroup {
    private List<VehiclesToJson> vehicles;

    public List<VehiclesToJson> getVehicles() {
        return this.vehicles;
    }

    public void setVehicles(List<VehiclesToJson> list) {
        this.vehicles = list;
    }
}
