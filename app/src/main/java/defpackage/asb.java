package defpackage;

import com.autonavi.bundle.uitemplate.mapwidget.widget.search.INearbyEventDelegate;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.Ajx3Path;
import com.autonavi.minimap.bundle.feed.page.FeedAjx3Page;

/* renamed from: asb reason: default package */
/* compiled from: NearbyEventDelegate */
public final class asb implements INearbyEventDelegate {
    public final String getFeedGrayAjxPath() {
        return Ajx3Path.FEED_PATH;
    }

    public final void startFeedAjx3Page(bid bid, PageBundle pageBundle) {
        if (bid != null && pageBundle != null) {
            bid.startPage(FeedAjx3Page.class, pageBundle);
        }
    }
}
