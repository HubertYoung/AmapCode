package com.autonavi.minimap.address.param;

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
    public File o = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.OPERATIONAL_URL_KEY));
        sb.append("ws/address/upload");
        f = sb.toString();
    }
}
