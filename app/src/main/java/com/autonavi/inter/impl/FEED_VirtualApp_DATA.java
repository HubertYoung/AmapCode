package com.autonavi.inter.impl;

import com.autonavi.minimap.bundle.feed.FeedVApp;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class FEED_VirtualApp_DATA extends ArrayList<Class<?>> {
    public FEED_VirtualApp_DATA() {
        add(FeedVApp.class);
    }
}
