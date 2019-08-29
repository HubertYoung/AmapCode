package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.minimap.basemap.favorites.page.FavoritesPage;
import com.autonavi.minimap.basemap.save.page.SaveSearchPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.basemap.action.save_search_page", "amap.basemap.action.favorite_page"}, module = "favorites", pages = {"com.autonavi.minimap.basemap.save.page.SaveSearchPage", "com.autonavi.minimap.basemap.favorites.page.FavoritesPage"})
@KeepName
public final class FAVORITES_PageAction_DATA extends HashMap<String, Class<?>> {
    public FAVORITES_PageAction_DATA() {
        put("amap.basemap.action.save_search_page", SaveSearchPage.class);
        put("amap.basemap.action.favorite_page", FavoritesPage.class);
    }
}
