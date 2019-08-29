package com.autonavi.minimap.ajx3.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.location.sdk.fusion.LocationParams;

public class UpdatableInitRequest extends AosPostRequest {
    private static final String URL;
    public String package_info;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_URL_KEY));
        sb.append("ws/shield/dyui/ajx3/updatable/v3/init");
        URL = sb.toString();
    }

    public UpdatableInitRequest() {
        this.package_info = null;
    }

    public UpdatableInitRequest(String str) {
        this.package_info = str;
    }

    public final void build() {
        setUrl(URL);
        addSignParam("channel");
        addSignParam(LocationParams.PARA_COMMON_DIU);
        addSignParam(LocationParams.PARA_COMMON_DIV);
        addReqParam("package_info", this.package_info);
    }
}
