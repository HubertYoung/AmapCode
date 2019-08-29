package defpackage;

import com.autonavi.bundle.vui.business.helpercenter.VUIHelpCenterPage;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

/* renamed from: pm reason: default package */
/* compiled from: MitVuiGotoGuideModel */
public final class pm extends bgd {
    public final String a() {
        return "gotoGuide";
    }

    public final boolean a(bgb bgb, bfb bfb) {
        StringBuilder sb = new StringBuilder("handleVUICmd ");
        sb.append(bgb.b);
        tq.b("NaviMonitor", "MitVuiGotoGuideModel", sb.toString());
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("bundle_key_token", bgb.a);
        bid pageContext = AMapPageUtil.getPageContext();
        if (VUIHelpCenterPage.class.equals(AMapPageUtil.getTopPageClass())) {
            d.a.a(bgb.a, 10000, (String) null);
            return true;
        }
        if (pageContext != null) {
            pageContext.startPage(VUIHelpCenterPage.class, pageBundle);
        }
        return true;
    }
}
