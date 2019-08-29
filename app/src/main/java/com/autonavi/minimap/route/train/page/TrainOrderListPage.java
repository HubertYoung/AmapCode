package com.autonavi.minimap.route.train.page;

import com.autonavi.annotation.PageAction;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.train.presenter.TrainOrderPresenter;

@PageAction("amap.extra.route.train_order_list")
public class TrainOrderListPage extends BaseOrderPagetWithTitle<TrainOrderPresenter> {
    /* access modifiers changed from: protected */
    public final int a() {
        return R.string.life_order_train_detail_title;
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new TrainOrderPresenter(this);
    }
}
