package defpackage;

import android.text.TextUtils;
import com.autonavi.common.model.POI;
import java.util.List;

/* renamed from: acj reason: default package */
/* compiled from: PlanCommonUtil */
public final class acj {
    public static boolean a() {
        POI b = ade.a().b(true);
        if (b == null || TextUtils.isEmpty(b.getName())) {
            return false;
        }
        POI d = ade.a().d(true);
        if (d == null || TextUtils.isEmpty(d.getName())) {
            return false;
        }
        return true;
    }

    public static boolean a(POI poi) {
        if (poi == null || poi.getPoint() == null || poi.getPoint().x == 0 || poi.getPoint().y == 0) {
            return false;
        }
        return true;
    }

    public static boolean a(POI poi, POI poi2, boolean z) {
        if (poi == null && poi2 == null) {
            return true;
        }
        if (poi == null || poi2 == null) {
            return false;
        }
        String id = poi.getId();
        String id2 = poi2.getId();
        if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(id2) && id.equalsIgnoreCase(id2)) {
            return true;
        }
        if (poi.getPoint().x != poi2.getPoint().x || poi.getPoint().y != poi2.getPoint().y) {
            return false;
        }
        if (z) {
            return TextUtils.equals(poi.getName(), poi2.getName());
        }
        return true;
    }

    public static boolean a(List<POI> list, List<POI> list2) {
        if ((list == null || list.isEmpty()) && (list2 == null || list2.isEmpty())) {
            return false;
        }
        if (list == null || list.isEmpty() || list2 == null || list2.isEmpty() || list.size() != list2.size()) {
            return true;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!a(list.get(i), list2.get(i), true)) {
                return true;
            }
        }
        return false;
    }
}
