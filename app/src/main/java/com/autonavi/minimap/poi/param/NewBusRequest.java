package com.autonavi.minimap.poi.param;

import com.amap.bundle.blutils.app.ConfigerHelper;

public class NewBusRequest extends BusBaseRequest {
    private static final String i;
    public String d = null;
    public String e = "2.14";
    public int f = 10;
    public String g = null;
    public String h = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.SEARCH_AOS_URL_KEY));
        sb.append("ws/mapapi/poi/newbus/");
        i = sb.toString();
    }

    public final void a() {
        setUrl(i);
        super.a();
        addSignParam("channel");
        addSignParam("id");
        addSignParam("city");
        addReqParam("id", this.d);
        addReqParam("version", this.e);
        addReqParam("pagesize", Integer.toString(this.f));
        addReqParam("city", this.g);
        addReqParam("search_sceneid", this.h);
    }
}
