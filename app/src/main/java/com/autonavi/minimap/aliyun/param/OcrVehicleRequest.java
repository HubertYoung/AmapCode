package com.autonavi.minimap.aliyun.param;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;

public class OcrVehicleRequest extends AosPostRequest {
    public static final String a;
    public String b = null;
    public String c = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.DRIVE_AOS_URL_KEY));
        sb.append("ws/valueadded/aliyun/ocr_vehicle/");
        a = sb.toString();
    }
}
