package org.altbeacon.beacon.service;

import java.io.Serializable;
import java.util.HashMap;
import org.altbeacon.beacon.Beacon;

/* compiled from: ExtraDataBeaconTracker */
public final class d implements Serializable {
    private HashMap<String, HashMap<Integer, Beacon>> a = new HashMap<>();
    private boolean b = true;

    public d() {
    }

    public d(boolean matchBeaconsByServiceUUID) {
        this.b = matchBeaconsByServiceUUID;
    }

    public final synchronized Beacon a(Beacon beacon) {
        Beacon trackedBeacon;
        if (beacon.i() || beacon.b() != -1) {
            trackedBeacon = b(beacon);
        } else {
            trackedBeacon = beacon;
        }
        return trackedBeacon;
    }

    private Beacon b(Beacon beacon) {
        Beacon trackedBeacon = null;
        HashMap matchingTrackedBeacons = this.a.get(c(beacon));
        if (matchingTrackedBeacons != null) {
            for (Beacon matchingTrackedBeacon : matchingTrackedBeacons.values()) {
                if (beacon.j()) {
                    matchingTrackedBeacon.a(beacon.f());
                    matchingTrackedBeacon.a(beacon.c());
                } else {
                    beacon.a(matchingTrackedBeacon.d());
                    trackedBeacon = beacon;
                }
            }
        }
        if (!beacon.j()) {
            a(beacon, matchingTrackedBeacons);
        }
        if (trackedBeacon != null || beacon.j()) {
            return trackedBeacon;
        }
        return beacon;
    }

    private void a(Beacon trackedBeacon, HashMap<Integer, Beacon> matchingTrackedBeacons) {
        if (matchingTrackedBeacons == null) {
            matchingTrackedBeacons = new HashMap<>();
        }
        matchingTrackedBeacons.put(Integer.valueOf(trackedBeacon.hashCode()), trackedBeacon);
        this.a.put(c(trackedBeacon), matchingTrackedBeacons);
    }

    private String c(Beacon beacon) {
        if (this.b) {
            return beacon.h() + beacon.b();
        }
        return beacon.h();
    }
}
