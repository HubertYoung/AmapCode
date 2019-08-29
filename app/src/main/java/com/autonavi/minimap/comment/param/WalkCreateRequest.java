package com.autonavi.minimap.comment.param;

import com.amap.bundle.aosservice.request.AosPostRequest;

public class WalkCreateRequest extends AosPostRequest {
    public static final String a;
    public String b = null;
    public String c = null;
    public String d = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a("aos_ugc_comment_url"));
        sb.append("ws/ugc/comment/walk/create/");
        a = sb.toString();
    }
}
