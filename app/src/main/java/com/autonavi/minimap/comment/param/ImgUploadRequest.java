package com.autonavi.minimap.comment.param;

import com.amap.bundle.aosservice.request.AosMultipartRequest;
import java.io.File;

public class ImgUploadRequest extends AosMultipartRequest {
    public static final String f;
    public String g = null;
    public File h = null;
    public String i = null;
    public String j = null;
    public String k = null;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a("aos_ugc_comment_url"));
        sb.append("ws/ugc/comment/img_upload_new");
        f = sb.toString();
    }
}
