package com.autonavi.bundle.ridenavi.api;

import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;

public interface IRideNaviPage {

    public @interface PageType {
    }

    Class<? extends AbstractBasePage> a(@PageType int i);

    void a(@PageType int i, PageBundle pageBundle);

    boolean a(bid bid);

    boolean a(String str);
}
