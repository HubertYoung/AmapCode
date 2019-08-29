package com.autonavi.minimap.ajx3.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.location.sdk.fusion.LocationParams;

public class UpdatableSchemeRequest extends AosPostRequest {
    private static final String URL;
    public String package_info;
    public String scheme_key;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_URL_KEY));
        sb.append("ws/shield/dyui/ajx3/updatable/v3/scheme");
        URL = sb.toString();
    }

    public UpdatableSchemeRequest() {
        this.package_info = null;
        this.scheme_key = null;
    }

    public UpdatableSchemeRequest(String str, String str2) {
        this.package_info = str;
        this.scheme_key = str2;
    }

    public final void build() {
        setUrl(URL);
        addSignParam("channel");
        addSignParam(LocationParams.PARA_COMMON_DIU);
        addSignParam(LocationParams.PARA_COMMON_DIV);
        addReqParam("package_info", this.package_info);
        addReqParam("scheme_key", this.scheme_key);
    }
}
