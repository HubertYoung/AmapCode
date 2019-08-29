package com.autonavi.bundle.routecommute.common.bean;

import java.io.Serializable;

public class NaviAddress implements Serializable {
    public String busCarPref;
    @Deprecated
    public String busPref;
    @Deprecated
    public String carPref;
    public NaviAddressCompany company;
    public NaviAddressHome home;

    public String toString() {
        StringBuilder sb = new StringBuilder("NaviAddress{home=");
        sb.append(this.home);
        sb.append(", company=");
        sb.append(this.company);
        sb.append(", busCarPref='");
        sb.append(this.busCarPref);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
