package com.autonavi.minimap.bundle.feed;

import com.autonavi.bundle.feed.ajx.ModuleFeed;
import com.autonavi.minimap.ajx3.Ajx;

public class FeedVApp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleFeed.class);
    }
}
