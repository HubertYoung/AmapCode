package com.autonavi.minimap.life.order.viewpoint.fragment;

import com.autonavi.minimap.R;
import com.autonavi.minimap.life.order.base.page.BaseOrderTabPage;

public class ViewPointTabPage extends BaseOrderTabPage {
    public final void a() {
        this.c.add(new ViewPointListFragment(this));
        this.c.add(new ViewPointPhoneFragment(this));
        this.d = Boolean.TRUE;
        this.a.setTitle(getString(R.string.viewpoint_order_history_title));
    }
}
