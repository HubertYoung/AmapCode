package com.autonavi.bundle.healthyrun.api;

import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;

public interface IHRunPage {

    public @interface PageType {
    }

    Class<? extends AbstractBasePage> a(@PageType int i);

    void a(@PageType int i, PageBundle pageBundle);
}
