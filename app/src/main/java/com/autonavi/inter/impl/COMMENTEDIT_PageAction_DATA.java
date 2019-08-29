package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.map.search.page.EditCommentPage;
import com.autonavi.map.search.page.MyCommentListPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.search.action.comment", "comment_list_page"}, module = "commentedit", pages = {"com.autonavi.map.search.page.EditCommentPage", "com.autonavi.map.search.page.MyCommentListPage"})
@KeepName
public final class COMMENTEDIT_PageAction_DATA extends HashMap<String, Class<?>> {
    public COMMENTEDIT_PageAction_DATA() {
        put("amap.search.action.comment", EditCommentPage.class);
        put("comment_list_page", MyCommentListPage.class);
    }
}
