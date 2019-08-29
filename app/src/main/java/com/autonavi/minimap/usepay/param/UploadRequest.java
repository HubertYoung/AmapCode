package com.autonavi.minimap.usepay.param;

import com.amap.bundle.aosservice.request.AosMultipartRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;
import java.io.File;

public class UploadRequest extends AosMultipartRequest {
    public static final String f;
    public String g = null;
    public String h = null;
    public String i = null;
    public String j = null;
    public String k = null;
    public String l = null;
    public String m = null;
    public String n = null;
    public String o = null;
    public String p = null;
    public String q = null;
    public String r = null;
    public String s = null;
    public String t = null;
    public String u = null;
    public String v = null;
    public String w = null;
    public String x = null;
    public File y = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.OPERATIONAL_URL_KEY));
        sb.append("ws/use_pay/upload");
        f = sb.toString();
    }
}
