package defpackage;

import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx3Page;

/* renamed from: cgi reason: default package */
/* compiled from: FeedbackNetworkManager */
public final class cgi {
    public static /* synthetic */ void a(String str) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", "amap_bundle_basemap_feedback/src/user_center_old/detail.jsx.js");
            pageBundle.putString("jsData", str);
            pageContext.startPage(Ajx3Page.class, pageBundle);
        }
    }
}
