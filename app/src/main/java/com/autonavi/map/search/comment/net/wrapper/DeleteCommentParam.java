package com.autonavi.map.search.comment.net.wrapper;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_ugc_comment_url", sign = {"comment_id", "tid"}, url = "/ws/ugc/comment/delete")
public class DeleteCommentParam implements ParamEntity {
    private long comment_id;

    private DeleteCommentParam() {
    }
}
