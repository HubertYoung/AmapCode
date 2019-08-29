package defpackage;

import com.autonavi.common.Callback;
import com.autonavi.minimap.life.marketdetail.MarketDetailManager$1;
import com.autonavi.minimap.life.marketdetail.MarketDetailManager$2;
import com.autonavi.minimap.shoping.ShopingRequestHolder;
import com.autonavi.minimap.shoping.param.PoiListRequest;

/* renamed from: dpe reason: default package */
/* compiled from: MarketDetailManager */
public final class dpe {
    public static void a(bid bid, String str, String str2, String str3, boolean z, String str4, String str5) {
        PoiListRequest poiListRequest = new PoiListRequest();
        poiListRequest.d = str;
        poiListRequest.b = 1;
        poiListRequest.e = str3;
        poiListRequest.f = str4;
        poiListRequest.g = str5;
        a(poiListRequest, new MarketDetailManager$1(str, str2, z, bid));
    }

    public static void a(PoiListRequest poiListRequest, Callback<dpi> callback) {
        ShopingRequestHolder.getInstance().sendPoiList(poiListRequest, new MarketDetailManager$2(callback));
    }
}
