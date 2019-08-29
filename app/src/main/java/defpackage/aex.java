package defpackage;

import com.autonavi.ae.search.model.GPoiBase;
import com.autonavi.ae.search.model.GPoiResult;
import com.autonavi.common.model.POI;
import java.util.ArrayList;
import java.util.List;

/* renamed from: aex reason: default package */
/* compiled from: OfflineDataUtils */
public final class aex {
    public static ArrayList<POI> a(GPoiResult gPoiResult) {
        adz adz = (adz) a.a.a(adz.class);
        if (adz == null) {
            return null;
        }
        List<GPoiBase> poiList = gPoiResult.getPoiList();
        ArrayList<POI> arrayList = new ArrayList<>();
        for (GPoiBase a : poiList) {
            arrayList.add(adz.a(a));
        }
        return arrayList;
    }
}
