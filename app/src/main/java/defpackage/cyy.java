package defpackage;

import com.autonavi.ae.search.model.GPoiBase;
import com.autonavi.ae.search.model.GPoiResult;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser;
import com.autonavi.common.Callback;
import com.autonavi.common.Callback.a;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.bundle.maphome.impl.ReverseGeocodeManagerImpl$1;
import com.autonavi.minimap.geo.GeoRequestHolder;
import com.autonavi.minimap.geo.param.ReverseCodeRequest;
import com.autonavi.minimap.map.DPoint;
import java.util.ArrayList;
import java.util.List;

/* renamed from: cyy reason: default package */
/* compiled from: ReverseGeocodeManagerImpl */
public class cyy implements awe {
    public final a a(GeoPoint geoPoint, int i, Callback<ReverseGeocodeResponser> callback) {
        ReverseCodeRequest reverseCodeRequest = new ReverseCodeRequest();
        reverseCodeRequest.g = 0;
        reverseCodeRequest.h = 1;
        reverseCodeRequest.d = true;
        reverseCodeRequest.f = 1;
        reverseCodeRequest.i = 1;
        if (geoPoint != null) {
            DPoint a = cfg.a((long) geoPoint.x, (long) geoPoint.y);
            reverseCodeRequest.c = String.valueOf(a.y);
            reverseCodeRequest.b = String.valueOf(a.x);
            reverseCodeRequest.g = i;
        }
        aas aas = new aas(reverseCodeRequest);
        GeoRequestHolder.getInstance().sendReverseCode(reverseCodeRequest, new ReverseGeocodeManagerImpl$1(this, callback, reverseCodeRequest));
        return aas;
    }

    public final ArrayList<POI> a(GPoiResult gPoiResult) {
        List<GPoiBase> poiList = gPoiResult.getPoiList();
        ArrayList<POI> arrayList = new ArrayList<>();
        adz adz = (adz) a.a.a(adz.class);
        if (adz != null) {
            ady b = adz.b();
            if (b != null) {
                for (GPoiBase a : poiList) {
                    arrayList.add(b.a(a));
                }
            }
        }
        return arrayList;
    }
}
