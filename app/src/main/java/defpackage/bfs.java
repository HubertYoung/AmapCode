package defpackage;

import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.vui.business.poiselector.PoiSelectPage;
import com.autonavi.common.PageBundle;

@BundleInterface(asu.class)
/* renamed from: bfs reason: default package */
/* compiled from: PoiSelectorInvokerImpl */
public class bfs extends esi implements asu {
    public final void a(asx asx) {
        bid bid = asx.c;
        asv asv = asx.b;
        String str = asx.a;
        if (bid != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("onPoiSelectorResult", asv);
            pageBundle.putString("requestData", str);
            bid.startPage(PoiSelectPage.class, pageBundle);
        }
    }
}
