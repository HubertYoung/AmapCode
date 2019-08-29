package com.autonavi.minimap.route.bus.realtimebus.data;

import com.autonavi.minimap.route.bus.realtimebus.model.RTBusData;

public abstract class BusStationDataCallback {

    public static class BusStationException extends Exception {
        private int stateCode;

        public BusStationException(int i, String str) {
            super(str);
            this.stateCode = i;
        }

        public int getStateCode() {
            return this.stateCode;
        }
    }

    public abstract void a(BusStationException busStationException);

    public abstract void a(RTBusData rTBusData);
}
