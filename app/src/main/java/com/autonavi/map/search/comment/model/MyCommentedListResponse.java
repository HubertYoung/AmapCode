package com.autonavi.map.search.comment.model;

import com.autonavi.common.annotation.Ignore;
import com.autonavi.common.annotation.Name;
import com.autonavi.map.search.model.BaseResponse;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import proguard.annotation.Keep;

@Keep
public class MyCommentedListResponse extends BaseResponse {
    private static final long serialVersionUID = 3076568014059744650L;
    @Name("list")
    public List<Item> commentedList = new ArrayList();
    public int count;
    @Name("gold_banner")
    public MyCommentGoldBanner goldBanner = new MyCommentGoldBanner();
    @Name("banner_flag")
    public int showBanner;
    @Name("total_page")
    public int totalPage;

    @Keep
    @SuppressFBWarnings({"UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
    public static class Item implements Serializable {
        private static final long serialVersionUID = 6890115781301480277L;
        public String content;
        @Name("created_time")
        public long createdTime;
        @Name("gold_num")
        public int goldNumber;
        @Name("gold_type")
        public int goldType;
        public String id;
        @Name("pic")
        public List<String> picUrls;
        @Name("poi_id")
        public String poiId;
        @Name("poi_name")
        public String poiName;
        @Name("pw_num")
        public int pwNumber;
        @Ignore
        public boolean showAllComment = false;
        public int star;
        public int status;
        public int type;
        public String uid;
    }
}
