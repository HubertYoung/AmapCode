package com.amap.location.common.model;

import android.location.Location;
import android.text.TextUtils;
import java.util.Objects;

public class HisLocation {
    private static final Double INT_LATLNG = Double.valueOf(1.0E7d);
    public int lat;
    public int locType;
    public int lon;
    public int radius;
    public int retype;
    public int subretype;
    public long time;

    public double distanceTo(HisLocation hisLocation) {
        if (hisLocation == null) {
            return 0.0d;
        }
        float[] fArr = new float[1];
        Location.distanceBetween(((double) this.lat) / INT_LATLNG.doubleValue(), ((double) this.lon) / INT_LATLNG.doubleValue(), ((double) hisLocation.lat) / INT_LATLNG.doubleValue(), ((double) hisLocation.lon) / INT_LATLNG.doubleValue(), fArr);
        return (double) fArr[0];
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.time);
        sb.append(",");
        sb.append(this.lon);
        sb.append(",");
        sb.append(this.lat);
        sb.append(",");
        sb.append(this.radius);
        sb.append(",");
        sb.append(this.locType);
        sb.append(",");
        sb.append(this.retype);
        sb.append(",");
        sb.append(this.subretype);
        return sb.toString();
    }

    public static HisLocation makeLocationByGpsLocation(Location location) {
        if (location == null) {
            return null;
        }
        HisLocation hisLocation = new HisLocation();
        hisLocation.time = System.currentTimeMillis();
        hisLocation.lon = (int) Math.round(location.getLongitude() * INT_LATLNG.doubleValue());
        hisLocation.lat = (int) Math.round(location.getLatitude() * INT_LATLNG.doubleValue());
        hisLocation.radius = Math.round(location.getAccuracy());
        hisLocation.locType = 1;
        hisLocation.retype = 63;
        hisLocation.subretype = 0;
        return hisLocation;
    }

    public static HisLocation makeLocationByNetworkLocation(AmapLoc amapLoc) {
        if (amapLoc == null) {
            return null;
        }
        HisLocation hisLocation = new HisLocation();
        hisLocation.time = System.currentTimeMillis();
        hisLocation.lon = (int) Math.round(amapLoc.getLon() * INT_LATLNG.doubleValue());
        hisLocation.lat = (int) Math.round(amapLoc.getLat() * INT_LATLNG.doubleValue());
        hisLocation.radius = Math.round(amapLoc.getAccuracy());
        hisLocation.locType = AmapLoc.getLocType(amapLoc) + 1;
        try {
            hisLocation.retype = Integer.parseInt(amapLoc.getRetype());
        } catch (Exception unused) {
            hisLocation.retype = 63;
        }
        try {
            hisLocation.subretype = Integer.parseInt(amapLoc.getSubType());
        } catch (Exception unused2) {
            hisLocation.subretype = 0;
        }
        return hisLocation;
    }

    public static HisLocation makeLocationByText(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                String[] split = str.split(",");
                HisLocation hisLocation = new HisLocation();
                hisLocation.time = Long.parseLong(split[0]);
                hisLocation.lon = Integer.parseInt(split[1]);
                hisLocation.lat = Integer.parseInt(split[2]);
                hisLocation.radius = Integer.parseInt(split[3]);
                hisLocation.locType = Integer.parseInt(split[4]);
                hisLocation.retype = Integer.parseInt(split[5]);
                hisLocation.subretype = Integer.parseInt(split[6]);
                return hisLocation;
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HisLocation hisLocation = (HisLocation) obj;
        return this.lon == hisLocation.lon && this.lat == hisLocation.lat && this.radius == hisLocation.radius;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(this.lon), Integer.valueOf(this.lat), Integer.valueOf(this.radius)});
    }
}
