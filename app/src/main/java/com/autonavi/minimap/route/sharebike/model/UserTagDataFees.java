package com.autonavi.minimap.route.sharebike.model;

import java.io.Serializable;

public class UserTagDataFees implements Serializable {
    private a mobike;
    private a ofo;

    public static class a {
        public String a = null;
        public String b = null;
    }

    public a getMobike() {
        return this.mobike;
    }

    public void setMobike(a aVar) {
        this.mobike = aVar;
    }

    public a getOfo() {
        return this.ofo;
    }

    public void setOfo(a aVar) {
        this.ofo = aVar;
    }
}
