package com.autonavi.minimap.drive.inter.impl;

import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.tripgroup.api.IVehicleInfoEvent;
import com.autonavi.map.db.model.Car;
import java.util.List;

public class VehicleInfoEventImpl implements IVehicleInfoEvent {
    public void onVehicleInfoChanged(int i) {
        int i2;
        int lastCarsCount = DriveUtil.getLastCarsCount();
        ato ato = (ato) a.a.a(ato.class);
        List<Car> list = null;
        List a = ato != null ? ato.a().a(1) : null;
        int i3 = 0;
        if (a == null) {
            i2 = 0;
        } else {
            i2 = a.size();
        }
        if (lastCarsCount == 0 && i2 != 0) {
            DriveUtil.setAvoidLimitedPath(true);
        } else if (lastCarsCount != 0 && i2 == 0) {
            DriveUtil.setAvoidLimitedPath(false);
        }
        DriveUtil.setLastCarsCount(i2);
        int lastTrucksCount = DriveUtil.getLastTrucksCount();
        ato ato2 = (ato) a.a.a(ato.class);
        if (ato2 != null) {
            list = ato2.a().a(2);
        }
        if (list != null) {
            i3 = list.size();
        }
        if (lastTrucksCount == 0 && i3 != 0) {
            DriveUtil.setTruckAvoidSwitch(true);
            DriveUtil.setTruckAvoidLimitedLoad(true);
        } else if (lastTrucksCount != 0 && i3 == 0) {
            DriveUtil.setTruckAvoidSwitch(true);
        }
        DriveUtil.setLastTrucksCount(i3);
    }
}
