package com.autonavi.bundle.routecommute.common.bean;

import com.autonavi.common.model.POI;
import java.io.Serializable;

public class NaviAddressCompany implements Serializable {
    private POI company;
    public String pCompany;
    public int source;

    public POI getCompany() {
        return this.company;
    }

    public void setCompany(POI poi) {
        this.company = poi;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("NaviAddressCompany{company=");
        sb.append(this.company);
        sb.append(", pCompany='");
        sb.append(this.pCompany);
        sb.append('\'');
        sb.append(", source=");
        sb.append(this.source);
        sb.append('}');
        return sb.toString();
    }
}
