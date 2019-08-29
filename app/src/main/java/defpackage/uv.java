package defpackage;

import com.amap.bundle.environmentmap.page.SearchEnvironmentMapPage;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: uv reason: default package */
/* compiled from: SearchEnvironmentMapPresenter */
public final class uv extends Ajx3PagePresenter {
    private SearchEnvironmentMapPage a;

    public uv(SearchEnvironmentMapPage searchEnvironmentMapPage) {
        super(searchEnvironmentMapPage);
        this.a = searchEnvironmentMapPage;
    }

    public final boolean onLabelClick(List<als> list) {
        if (list == null || list.isEmpty()) {
            return super.onLabelClick(list);
        }
        als als = list.get(0);
        if (als == null) {
            return super.onLabelClick(list);
        }
        if (new uw().a(als)) {
            SearchEnvironmentMapPage searchEnvironmentMapPage = this.a;
            searchEnvironmentMapPage.a.addEnvironmentOverlayItem(new GeoPoint(als.e, als.f), als.b, searchEnvironmentMapPage.getMapView());
            if (searchEnvironmentMapPage.b != null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, als.b);
                    jSONObject.put("batchNo", als.k);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                searchEnvironmentMapPage.b.callback(jSONObject.toString());
            }
        } else {
            this.a.a();
            this.a.b();
        }
        return super.onLabelClick(list);
    }

    public final boolean onBlankClick() {
        this.a.b();
        this.a.a();
        return super.onBlankClick();
    }
}
