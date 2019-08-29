package com.autonavi.bundle.banner.net;

import com.amap.bundle.network.request.param.builder.URLBuilder.ResultProperty;
import com.autonavi.bundle.banner.data.BannerItem;
import java.util.LinkedList;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
@ResultProperty(parser = asi.class)
public class BannerResult {
    public int interval;
    public LinkedList<BannerItem> items;
    public String responseTimestamp;
    public String token;
}
