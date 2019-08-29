package com.autonavi.bundle.ugc.api;

import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;

public interface IUGCPage {

    public @interface PageType {
    }

    Class<? extends AbstractBasePage> a();
}
