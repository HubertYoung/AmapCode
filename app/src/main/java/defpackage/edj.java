package defpackage;

import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.minimap.route.net.base.req.BusBaseReq;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;

/* renamed from: edj reason: default package */
/* compiled from: RTBusRequestFactory */
public final class edj {
    public static ede a() {
        return new ede(ConfigerHelper.AOS_URL_KEY, "ws/mapapi/realtimebus/linestation/");
    }

    public static BusBaseReq a(Bus bus, String str, String str2, boolean z) {
        ede a = a();
        a.b(AutoJsonUtils.JSON_ADCODE, bus.areacode);
        a.b("lines", bus.id);
        a.b("stations", str);
        a.a("is_refresh", z ? "1" : "0");
        a.a("from_page", str2);
        a.a("need_bus_status", "1");
        a.a("need_not_depart", "true");
        a.a(NewHtcHomeBadger.COUNT, "3");
        return new BusBaseReq(a);
    }
}
