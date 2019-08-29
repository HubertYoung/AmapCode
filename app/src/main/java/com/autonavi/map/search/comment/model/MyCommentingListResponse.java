package com.autonavi.map.search.comment.model;

import com.autonavi.common.annotation.Name;
import com.autonavi.map.search.model.BaseResponse;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;
import java.util.ArrayList;
import proguard.annotation.Keep;

@Keep
public class MyCommentingListResponse extends BaseResponse {
    private static final long serialVersionUID = -936079266511713257L;
    @Name("first_comment")
    public List firstComment = new List();
    @Name("general_comment")
    public List generalComment = new List();
    @Name("gold_banner")
    public MyCommentGoldBanner goldBanner = new MyCommentGoldBanner();
    @Name("banner_flag")
    public int showBanner;

    @Keep
    @SuppressFBWarnings({"UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
    public static class Item implements Serializable {
        private static final long serialVersionUID = 1667243612208979442L;
        @Name("action_uri")
        public String actionUri;
        public String cover;
        @Name("data_source")
        public String dataSource;
        @Name("gold_num")
        public int goldNumber;
        @Name("gold_type")
        public int goldType;
        @Name("poi_id")
        public String poiId;
        @Name("name")
        public String poiName;
        @Name("pw_num")
        public int pwNumber;
        @Name("visit_time")
        public String visitTime;
    }

    public static class List implements Serializable {
        private static final long serialVersionUID = 5557428849488960203L;
        @Name("list")
        public java.util.List<Item> commentingList = new ArrayList();
        @Name("pw_tag")
        public String title;
    }
}
