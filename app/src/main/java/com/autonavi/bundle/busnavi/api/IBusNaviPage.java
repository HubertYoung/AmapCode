package com.autonavi.bundle.busnavi.api;

import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;

public interface IBusNaviPage {

    public @interface PageType {
    }

    Class<? extends AbstractBasePage> a(@PageType int i);

    void a(@PageType int i, PageBundle pageBundle);

    void a(String str);

    boolean a(bid bid);
}
