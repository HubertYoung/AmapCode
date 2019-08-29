package com.amap.bundle.tripgroup.api;

import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
@KeepImplementations
public interface IVehicleInfoEvent {
    public static final int VEHICLE_INFO_ADDED = 1;
    public static final int VEHICLE_INFO_DELETED = 2;
    public static final int VEHICLE_INFO_LOCAL_DATA_MERGED = 5;
    public static final int VEHICLE_INFO_SYNC_ENED = 6;
    public static final int VEHICLE_INFO_USER_LOGIN = 3;
    public static final int VEHICLE_INFO_USER_LOGOUT = 4;

    void onVehicleInfoChanged(int i);
}
