package com.autonavi.minimap.comment.param;

import com.amap.bundle.aosservice.request.AosMultipartRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommentCreateRequest extends AosMultipartRequest {
    public static final String f;
    public String g = null;
    public String h = null;
    public String i = null;
    public String j = null;
    public File k = null;
    public String l = null;
    public List<String> m = new ArrayList();

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a("aos_ugc_comment_url"));
        sb.append("ws/ugc/comment/create");
        f = sb.toString();
    }
}
