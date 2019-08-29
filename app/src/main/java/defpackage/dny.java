package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.common.PageBundle;
import com.autonavi.map.search.data.DateEntity;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.life.order.hotel.page.OrderHotelListPage;
import com.autonavi.minimap.life.order.viewpoint.fragment.ViewPointTabPage;
import org.json.JSONObject;

/* renamed from: dny reason: default package */
/* compiled from: OrderFeatureAction */
public class dny extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            dno dno = new dno(a.mPageContext, a);
            String optString = jSONObject.optString("feature");
            if (DateEntity.DATETYPE_HOTEL.equals(optString)) {
                int i = jSONObject.optString("tab") == "1" ? 2 : 1;
                PageBundle pageBundle = new PageBundle();
                pageBundle.putInt("category", i);
                dno.a.startPage(OrderHotelListPage.class, pageBundle);
            } else if (AmapMessage.TOKEN_TRAVEL.equals(optString)) {
                dno.a.startPage(ViewPointTabPage.class, (PageBundle) null);
            } else {
                if (!"airticket".equals(optString) && "".equals(optString)) {
                    PageBundle pageBundle2 = new PageBundle();
                    pageBundle2.putString("url", "path://amap_bundle_order_home/src/pages/OrderHome.page.js");
                    pageBundle2.putString("pageId", "OrderHome");
                    dno.a.startPage(Ajx3Page.class, pageBundle2);
                }
            }
        }
    }
}
