package defpackage;

import com.autonavi.common.model.POI;

/* renamed from: acu reason: default package */
/* compiled from: UpdateRuleUtil */
public final class acu {
    public static String a(POI poi) {
        String str = "";
        if (poi == null) {
            return str;
        }
        lj b = li.a().b(poi.getPoint().x, poi.getPoint().y);
        if (b != null) {
            str = b.a;
            if (str.endsWith("市")) {
                str = str.substring(0, str.length() - 1);
            } else if (str.endsWith("地区")) {
                str = str.substring(0, str.length() - 2);
            }
        }
        return str;
    }
}
