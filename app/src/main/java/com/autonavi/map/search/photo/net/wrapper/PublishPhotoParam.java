package com.autonavi.map.search.photo.net.wrapper;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import java.io.File;
import org.json.JSONArray;

@Path(builder = AosURLBuilder.class, host = "aos_ugc_comment_url", sign = {"poi_id", "tid"}, url = "/ws/ugc/comment/img_upload_new?")
public class PublishPhotoParam implements ParamEntity {
    private JSONArray checksum;
    private File picture;
    private String poi_id;
    private String poi_x;
    private String poi_y;

    public static class a {
        private PublishPhotoParam a = new PublishPhotoParam(0);
    }

    /* synthetic */ PublishPhotoParam(byte b) {
        this();
    }

    private PublishPhotoParam() {
    }
}
