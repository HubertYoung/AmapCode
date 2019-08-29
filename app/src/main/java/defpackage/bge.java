package defpackage;

import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.uc.webview.export.internal.SDKFactory;

/* renamed from: bge reason: default package */
/* compiled from: MitVuiBackMainPageModel */
public final class bge extends bgd {
    public final String a() {
        return "backMainPage";
    }

    public final boolean a(bgb bgb, bfb bfb) {
        StringBuilder sb = new StringBuilder("handleVUICmd ");
        sb.append(bgb.b);
        bfh.a("MitVuiBackMainPageModel", sb.toString());
        try {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putInt("bundle_key_token", bgb.a);
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                if (AMapPageUtil.isHomePage()) {
                    pageBundle.putInt("bundle_key_code", 10121);
                } else {
                    pageBundle.putInt("bundle_key_code", 10000);
                }
                pageContext.startPage((String) "amap.basemap.action.default_page", pageBundle);
            }
        } catch (Throwable th) {
            d.a.a(bgb.a, (int) SDKFactory.getCoreType, (String) null);
            String str = bgb == null ? "" : bgb.f;
            bfq bfq = b.a;
            StringBuilder sb2 = new StringBuilder("handleVUICmd backMainPage fail error=");
            sb2.append(th.getMessage());
            sb2.append(" taskId=");
            sb2.append(str);
            bfp.a(bfq, 3, sb2.toString());
        }
        return true;
    }
}
