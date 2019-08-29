package com.autonavi.minimap.poi.param;

import com.amap.bundle.blutils.app.ConfigerHelper;

public class BusLiteRequest extends BusBaseRequest {
    private static final String m;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public String k;
    public int l;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.SEARCH_AOS_URL_KEY));
        sb.append("ws/mapapi/poi/buslite/");
        m = sb.toString();
    }

    public BusLiteRequest() {
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = "50000";
        this.j = null;
        this.k = null;
        this.c = null;
        this.l = 10;
        this.a = 1;
    }

    public final void a() {
        setUrl(m);
        super.a();
        addSignParam("channel");
        addSignParam("keywords");
        addSignParam("city");
        addSignParam("longitude");
        addSignParam("latitude");
        addReqParam("id", this.d);
        addReqParam("city", this.e);
        addReqParam("search_sceneid", this.f);
        addReqParam("longitude", this.g);
        addReqParam("latitude", this.h);
        addReqParam("range", this.i);
        addReqParam("transfer_realtimebus", this.j);
        addReqParam("superid", this.k);
        addReqParam("pagesize", Integer.toString(this.l));
    }
}
