package com.autonavi.bundle.airticket.api;

import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;

public interface IAirTicketPage {

    public @interface PageType {
    }

    Class<? extends AbstractBasePage> a(@PageType int i);

    void a(PageBundle pageBundle, int i);
}
