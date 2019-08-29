package com.autonavi.minimap.route.bus.inter;

import com.autonavi.minimap.poi.param.BusBaseRequest;

public interface IBusLineSearchResult extends IBusLineResult {
    BusBaseRequest getBusReqest();

    void setBusRequest(BusBaseRequest busBaseRequest);
}
