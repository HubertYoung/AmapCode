package defpackage;

import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.uc.webview.export.internal.SDKFactory;

/* renamed from: bgf reason: default package */
/* compiled from: MitVuiGotoFavoritesPageModel */
public final class bgf extends bgd {
    public final String a() {
        return "gotoFavorite";
    }

    public final boolean a(bgb bgb, bfb bfb) {
        StringBuilder sb = new StringBuilder("handleVUICmd ");
        sb.append(bgb.b);
        bfh.a("#MitVuiGotoFavoritesPageModel", sb.toString());
        try {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putInt("bundle_key_token", bgb.a);
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                pageContext.startPage((String) "amap.basemap.action.favorite_page", pageBundle);
                d.a.a(bgb.a, 10000, (String) null);
            }
        } catch (Throwable th) {
            d.a.a(bgb.a, (int) SDKFactory.getCoreType, (String) null);
            String str = bgb == null ? "" : bgb.f;
            bfq bfq = b.a;
            StringBuilder sb2 = new StringBuilder("MitVuiGotoFavoritesPageModel error");
            sb2.append(th.getMessage());
            sb2.append(" taskId=");
            sb2.append(str);
            bfp.a(bfq, 2, sb2.toString());
        }
        return true;
    }
}
