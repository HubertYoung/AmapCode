package com.autonavi.minimap.bundle.feed.presenter;

import com.autonavi.common.json.JsonUtil;
import java.util.ArrayList;
import java.util.List;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public class FeedLayerState {
    public static final int STATE_TYPE_ANCHOR = 1;
    public static final int STATE_TYPE_COLLAPSE = 0;
    public static final int STATE_TYPE_EXPAND = 3;
    public static final int STATE_TYPE_INVALID = -1;
    public static final int STATE_TYPE_TITLE_BAR = 2;
    public String name;
    public int offset;

    public static class FeedLayerStateList extends ArrayList<FeedLayerState> {
    }

    public FeedLayerState() {
    }

    public FeedLayerState(String str) {
        this.name = str;
    }

    public int toType() {
        return toType(this.name);
    }

    public static int toType(String str) {
        if (str == null) {
            return -1;
        }
        if ("bottomHeight".equals(str)) {
            return 0;
        }
        if ("middleHeight".equals(str)) {
            return 1;
        }
        if ("deltaHeight".equals(str)) {
            return 2;
        }
        if ("screenHeight".equals(str)) {
            return 3;
        }
        return -1;
    }

    public static List<FeedLayerState> parse(String str) {
        try {
            return (List) JsonUtil.fromString(str, FeedLayerStateList.class);
        } catch (Exception unused) {
            return null;
        }
    }
}
