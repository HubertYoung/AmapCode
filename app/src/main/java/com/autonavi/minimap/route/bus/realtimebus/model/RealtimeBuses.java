package com.autonavi.minimap.route.bus.realtimebus.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
public class RealtimeBuses implements Serializable {
    public List<RealtimeBus> buses;
    public int code;
    public String message;
    public String result;
    public String timestamp;
    public String version;

    @KeepClassMembers
    @KeepName
    public static class RealtimeBus {
        public String line;
        public String station;
        public int status;
        public List<RealtimeBusTrip> trip;

        public RealtimeBusTrip getNearestBusTrip() {
            if (dxx.a((Collection<T>) this.trip)) {
                return null;
            }
            return this.trip.get(0);
        }
    }

    @KeepClassMembers
    @KeepName
    public static class RealtimeBusTrip {
        public int arrival;
        public int arrival_old;
        public int dis;
        public int speed;
        public int speed_avg;
        public int station_left;
        public double x;
        public double y;

        public boolean isArriving() {
            return this.arrival <= 60 || this.station_left <= 1;
        }

        public boolean isArrived() {
            return this.arrival == 0 && this.station_left == 0;
        }
    }

    public boolean isContainArrivingBus() {
        if (!dxx.a((Collection<T>) this.buses)) {
            for (RealtimeBus nearestBusTrip : this.buses) {
                RealtimeBusTrip nearestBusTrip2 = nearestBusTrip.getNearestBusTrip();
                if (nearestBusTrip2 != null && nearestBusTrip2.isArriving()) {
                    return true;
                }
            }
        }
        return false;
    }
}
