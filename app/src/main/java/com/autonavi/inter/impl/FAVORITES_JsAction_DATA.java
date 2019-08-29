package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.JsActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@JsActionLogger(actions = {"setFavoriteMark", "editFavoriteInfo"}, jsActions = {"com.autonavi.minimap.basemap.jsaction.SetFavoriteMarkAction", "com.autonavi.minimap.basemap.jsaction.EditFavoriteInfoAction"}, module = "favorites")
@KeepName
public final class FAVORITES_JsAction_DATA extends HashMap<String, Class<?>> {
    public FAVORITES_JsAction_DATA() {
        put("setFavoriteMark", cpz.class);
        put("editFavoriteInfo", cpx.class);
    }
}
