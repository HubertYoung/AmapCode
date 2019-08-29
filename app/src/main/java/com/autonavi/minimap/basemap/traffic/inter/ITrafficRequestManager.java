package com.autonavi.minimap.basemap.traffic.inter;

import com.amap.bundle.aosservice.request.AosRequest;
import com.autonavi.server.TrafficAosUICallback;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public interface ITrafficRequestManager {
    AosRequest a(TrafficAosUICallback trafficAosUICallback);

    AosRequest a(cso cso, TrafficAosUICallback trafficAosUICallback);

    AosRequest b(cso cso, TrafficAosUICallback trafficAosUICallback);
}
