package com.autonavi.map.search.comment.net;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import proguard.annotation.KeepName;

public final class CommentParam {

    @Path(builder = AosURLBuilder.class, host = "aos_ugc_comment_url", sign = {"tid"}, url = "/ws/ugc/comment/commented_list?")
    @KeepName
    public static class CommentedListParam implements ParamEntity {
        private int page_num;
        private int page_size = 15;

        public CommentedListParam(int i) {
            this.page_num = i;
        }
    }

    @Path(builder = AosURLBuilder.class, host = "aos_ugc_comment_url", sign = {"diu"}, url = "/ws/ugc/comment/waiting_comment?")
    @KeepName
    public static class CommentingListParam implements ParamEntity {
    }

    @Path(builder = AosURLBuilder.class, host = "aos_ugc_comment_url", sign = {"comment_id", "tid"}, url = "/ws/ugc/comment/delete?")
    @KeepName
    public static class DeleteCommentedParam implements ParamEntity {
        private String comment_id;

        public DeleteCommentedParam(String str) {
            this.comment_id = str;
        }
    }
}
