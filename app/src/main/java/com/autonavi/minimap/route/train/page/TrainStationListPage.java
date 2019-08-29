package com.autonavi.minimap.route.train.page;

import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.route.coach.page.BaseStationListPage;

public class TrainStationListPage extends BaseStationListPage {
    public final int a() {
        return 0;
    }

    public static void a(AbstractBasePage abstractBasePage, int i) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("SWITCH_CITY_FOR", 0);
        abstractBasePage.startPageForResult(TrainStationListPage.class, pageBundle, i);
    }
}
