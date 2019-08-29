package defpackage;

import android.view.View;
import com.amap.bundle.statistics.LogManager;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.mappage.TipsView.PoiDetailView;
import com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b;
import com.autonavi.map.search.view.PoiDetailViewForCQ;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bxx reason: default package */
/* compiled from: SearchPoiDetailLogHelper */
public final class bxx {
    public boolean a;
    public boolean b;
    private bty c;

    public static boolean a(int i) {
        return i == 3 || i == 6 || i == 2 || i == 7 || i == 8;
    }

    public bxx(bty bty) {
        this.c = bty;
    }

    public final void a(View view, final b bVar, final boolean z) {
        if (bVar != null) {
            final boolean z2 = false;
            if (bVar.b != null) {
                bVar.b.setTipItemEvent(new ccj(false, z));
            }
            if (view instanceof PoiDetailView) {
                ((PoiDetailView) view).setTipItemEvent(new ccj(true, z));
                z2 = true;
            }
            if (view instanceof PoiDetailViewForCQ) {
                ((PoiDetailViewForCQ) view).setTipItemEvent(new ccj(true, z));
                z2 = true;
            }
            bVar.d.a((a) new a() {
                public final void a() {
                    new ccj(z2, z).b(bVar.p);
                }
            });
            POI poi = bVar.p;
            if (poi != null) {
                int i = 2;
                int i2 = z ? 1 : 2;
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("from", this.c.w());
                    jSONObject.put("lat", poi.getPoint().getLatitude());
                    jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, poi.getPoint().getLongitude());
                    if (this.c.s()) {
                        i = 1;
                    }
                    jSONObject.put("status", i);
                    jSONObject.put("itemId", i2);
                    jSONObject.put(TrafficUtil.POIID, poi.getId());
                    if (z2) {
                        LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_LONGPRESS, jSONObject);
                    } else {
                        LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_POITIP, jSONObject);
                    }
                } catch (JSONException unused) {
                }
            }
        }
    }

    public final void a(String str, String str2, String str3) {
        this.a = true;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("text", str);
            jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, str3);
            jSONObject.put("gsid", str2);
            LogManager.actionLogV2(LogConstant.SEARCH_RESULT_MAP, "B079", jSONObject);
        } catch (JSONException unused) {
        }
    }
}
