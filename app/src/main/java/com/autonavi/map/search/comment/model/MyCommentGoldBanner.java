package com.autonavi.map.search.comment.model;

import com.autonavi.common.annotation.Name;
import java.io.Serializable;
import java.util.List;
import proguard.annotation.Keep;

@Keep
public class MyCommentGoldBanner implements Serializable {
    private static final long serialVersionUID = -1975133377262548168L;
    @Name("action_uri")
    public String actionUri;
    @Name("pic_uri")
    public List<String> picUrls;
    @Name("today_gold")
    public int todayGold;
    @Name("total_gold")
    public int totalGold;
}
