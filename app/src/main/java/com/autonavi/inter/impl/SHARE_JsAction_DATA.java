package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.JsActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@JsActionLogger(actions = {"share", "shareToFriends"}, jsActions = {"com.autonavi.minimap.bundle.share.jsaction.ShareAction", "com.autonavi.minimap.bundle.share.jsaction.ShareToFriendAction"}, module = "share")
@KeepName
public final class SHARE_JsAction_DATA extends HashMap<String, Class<?>> {
    public SHARE_JsAction_DATA() {
        put("share", dcx.class);
        put("shareToFriends", dcy.class);
    }
}
