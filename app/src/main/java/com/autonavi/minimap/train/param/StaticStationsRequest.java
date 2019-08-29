package com.autonavi.minimap.train.param;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class StaticStationsRequest extends AosGetRequest {
    public static final String a;
    public String b = "train";
    public String c = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_URL_KEY));
        sb.append("ws/valueadded/train/static/stations");
        a = sb.toString();
    }
}
