package com.autonavi.bundle.routecommute.common.bean;

import com.autonavi.common.model.POI;
import java.io.Serializable;

public class NaviAddressHome implements Serializable {
    private POI home;
    public String pHome;
    public int source;

    public POI getHome() {
        return this.home;
    }

    public void setHome(POI poi) {
        this.home = poi;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("NaviAddressHome{home=");
        sb.append(this.home);
        sb.append(", pHome='");
        sb.append(this.pHome);
        sb.append('\'');
        sb.append(", source=");
        sb.append(this.source);
        sb.append('}');
        return sb.toString();
    }
}
