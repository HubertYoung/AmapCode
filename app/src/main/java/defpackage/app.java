package defpackage;

import com.autonavi.common.model.POI;

/* renamed from: app reason: default package */
/* compiled from: AirTicketUtil */
public final class app {
    public static String a(POI poi) {
        lj b = li.a().b(poi.getPoint().x, poi.getPoint().y);
        if (b == null) {
            return "";
        }
        String str = b.a;
        if (str.endsWith("市")) {
            return str.substring(0, str.length() - 1);
        }
        return str.endsWith("地区") ? str.substring(0, str.length() - 2) : str;
    }
}
