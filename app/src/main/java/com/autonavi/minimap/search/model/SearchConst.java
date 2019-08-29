package com.autonavi.minimap.search.model;

import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;

public final class SearchConst {
    public static final String a = AMapAppGlobal.getApplication().getString(R.string.my_location);
    public static final String b = AMapAppGlobal.getApplication().getString(R.string.map_selected_location);

    public enum SearchFor {
        DEFAULT(0),
        ROUTE(1),
        SHORTCUT_NAVI(2),
        INTENT(3),
        GOTO_NAVI(4),
        GOTO_NAVI_HOME(5),
        GOTO_NAVI_COMPANY(6),
        SCHEME_POI(7),
        QUICK_NAVI(8),
        QUICK_NAVI_HOME_COMPANY_SET(9),
        ROUTE_HOME_COMPANY_SET(10),
        MY(11),
        SEM_ANA(12),
        SUPER_FROMTO_HOME_COMPANY_SET(13),
        TAXI_HISTORY_ORDER_HOME_COMPANY_SET(14);
        
        private int searchFor;

        private SearchFor(int i) {
            this.searchFor = i;
        }
    }
}
