package com.autonavi.bundle.uitemplate.mapwidget.widget.search;

import com.autonavi.bundle.uitemplate.mapwidget.inter.IEventDelegate;
import com.autonavi.common.PageBundle;

public interface INearbyEventDelegate extends IEventDelegate {
    String getFeedGrayAjxPath();

    void startFeedAjx3Page(bid bid, PageBundle pageBundle);
}
