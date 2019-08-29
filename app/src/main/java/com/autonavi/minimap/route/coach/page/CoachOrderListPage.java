package com.autonavi.minimap.route.coach.page;

import com.autonavi.annotation.PageAction;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.coach.presenter.CoachOrderListPresenter;
import com.autonavi.minimap.route.train.page.BaseOrderPagetWithTitle;

@PageAction("amap.extra.route.coach_order_list")
public class CoachOrderListPage extends BaseOrderPagetWithTitle<CoachOrderListPresenter> {
    public final int a() {
        return R.string.coach_order_title;
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new CoachOrderListPresenter(this);
    }
}
