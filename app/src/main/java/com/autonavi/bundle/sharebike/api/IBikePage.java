package com.autonavi.bundle.sharebike.api;

import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;

public interface IBikePage {

    public @interface PageType {
    }

    Class<? extends AbstractBasePage> a(@PageType int i);

    void a(@PageType int i, PageBundle pageBundle);
}
