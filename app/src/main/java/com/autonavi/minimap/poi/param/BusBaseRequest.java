package com.autonavi.minimap.poi.param;

import com.amap.bundle.aosservice.request.AosPostRequest;

public class BusBaseRequest extends AosPostRequest {
    public int a = 1;
    public boolean b = true;
    public String c = "";

    public void a() {
        addReqParam("keywords", this.c);
        addReqParam("isShowNextPage", Boolean.toString(this.b));
        addReqParam("pagenum", Integer.toString(this.a));
    }
}
