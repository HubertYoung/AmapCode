package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.minimap.route.train.page.TrainOrderListPage;
import com.autonavi.minimap.route.train.page.TrainSearchPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.life.action.TrainSearchFragment", "amap.extra.route.train_order_list"}, module = "train", pages = {"com.autonavi.minimap.route.train.page.TrainSearchPage", "com.autonavi.minimap.route.train.page.TrainOrderListPage"})
@KeepName
public final class TRAIN_PageAction_DATA extends HashMap<String, Class<?>> {
    public TRAIN_PageAction_DATA() {
        put("amap.life.action.TrainSearchFragment", TrainSearchPage.class);
        put("amap.extra.route.train_order_list", TrainOrderListPage.class);
    }
}
