package com.autonavi.minimap.poi.param;

import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.minimap.route.bus.realtimebus.net.param.RealTimeBusSearchKeywordsUrlWrapper;

public class BusRequest extends BusBaseRequest {
    private static final String o;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public int i;
    public int j;
    public String k;
    public String l;
    public String m;
    public String n;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.SEARCH_AOS_URL_KEY));
        sb.append("ws/mapapi/poi/bus/");
        o = sb.toString();
    }

    public BusRequest() {
        this.d = null;
        this.e = null;
        this.c = null;
        this.f = "BUSLINE";
        this.g = null;
        this.h = null;
        this.i = 3000;
        this.j = 10;
        this.a = 1;
        this.b = true;
        this.k = "2.14";
        this.l = null;
        this.m = "1";
        this.n = RealTimeBusSearchKeywordsUrlWrapper.SUPERID_REAL_TIME_HISTORY_SEARCH;
    }

    public final void a() {
        setUrl(o);
        addSignParam("channel");
        addSignParam("keywords");
        addSignParam("city");
        addSignParam("longitude");
        addSignParam("latitude");
        addReqParam("id", this.d);
        addReqParam("city", this.e);
        addReqParam("keywords", this.c);
        addReqParam("data_type", this.f);
        addReqParam("longitude", this.g);
        addReqParam("latitude", this.h);
        addReqParam("range", Integer.toString(this.i));
        addReqParam("pagesize", Integer.toString(this.j));
        addReqParam("pagenum", Integer.toString(this.a));
        addReqParam("isShowNextPage", this.b ? "true" : "false");
        addReqParam("version", this.k);
        addReqParam("search_sceneid", this.l);
        addReqParam("transfer_realtimebus", this.m);
        addReqParam("superid", this.n);
    }
}
