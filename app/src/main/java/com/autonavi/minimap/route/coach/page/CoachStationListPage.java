package com.autonavi.minimap.route.coach.page;

import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;

public class CoachStationListPage extends BaseStationListPage {
    public final int a() {
        return 1;
    }

    public static void a(AbstractBasePage abstractBasePage, String str) {
        a(abstractBasePage, 1011, true, str);
    }

    public static void a(AbstractBasePage abstractBasePage) {
        a(abstractBasePage, 1010, false, null);
    }

    private static void a(AbstractBasePage abstractBasePage, int i, boolean z, String str) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("SWITCH_CITY_FOR", 0);
        pageBundle.putBoolean("COACH_ARRIVAL_STATION", z);
        pageBundle.putString("STRAT_STATION_ADCODE", str);
        abstractBasePage.startPageForResult(CoachStationListPage.class, pageBundle, i);
    }
}
