package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.bundle.buscard.page.BusCardPayPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.extra.route.buscard_hor"}, module = "buscard", pages = {"com.autonavi.bundle.buscard.page.BusCardPayPage"})
@KeepName
public final class BUSCARD_PageAction_DATA extends HashMap<String, Class<?>> {
    public BUSCARD_PageAction_DATA() {
        put("amap.extra.route.buscard_hor", BusCardPayPage.class);
    }
}
