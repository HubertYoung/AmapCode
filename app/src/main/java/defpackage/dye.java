package defpackage;

import com.autonavi.bundle.realtimebus.api.IRTPage;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.route.bus.realtimebus.page.RTBusPositionPage;

/* renamed from: dye reason: default package */
/* compiled from: IRTPageImpl */
final class dye implements IRTPage {

    /* renamed from: dye$a */
    /* compiled from: IRTPageImpl */
    static class a {
        static dye a = new dye();
    }

    dye() {
    }

    public final void a() {
        Class<RTBusPositionPage> cls = RTBusPositionPage.class;
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_realbus/src/pages/realtime_index/realtime_index.page.js");
        AMapPageUtil.getPageContext().startPage(Ajx3Page.class, pageBundle);
    }
}
