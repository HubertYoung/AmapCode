package defpackage;

import com.autonavi.ae.search.model.GPoiResult;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser;
import com.autonavi.common.Callback;
import com.autonavi.common.Callback.a;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import java.util.ArrayList;

/* renamed from: awe reason: default package */
/* compiled from: IReverseGeocodeManager */
public interface awe {
    a a(GeoPoint geoPoint, int i, Callback<ReverseGeocodeResponser> callback);

    ArrayList<POI> a(GPoiResult gPoiResult);
}
