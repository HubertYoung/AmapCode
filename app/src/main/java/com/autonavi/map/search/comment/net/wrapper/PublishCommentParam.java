package com.autonavi.map.search.comment.net.wrapper;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import java.io.File;
import java.util.List;

@Path(builder = AosURLBuilder.class, host = "aos_ugc_comment_url", sign = {"poi_id", "star", "tid"}, url = "/ws/ugc/comment/create")
public class PublishCommentParam implements ParamEntity {
    private List<String> checksum;
    private String content;
    private File picture;
    private String poi_id;
    private int star;

    public static class a {
        private PublishCommentParam a = new PublishCommentParam(0);
    }

    /* synthetic */ PublishCommentParam(byte b) {
        this();
    }

    private PublishCommentParam() {
    }
}
