package defpackage;

import android.content.SharedPreferences.Editor;
import android.graphics.Rect;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.tencent.connect.common.Constants;
import java.util.ArrayList;
import java.util.List;

/* renamed from: bwv reason: default package */
/* compiled from: PoiMapLocator */
public final class bwv {
    private Boolean a;
    private bid b;

    public bwv(bid bid, Boolean bool) {
        this.a = bool;
        this.b = bid;
    }

    public final void a(InfoliteResult infoliteResult, POI poi, boolean z) {
        Editor edit = new MapSharePreference((String) "SharedPreferences").edit();
        edit.putInt("X", poi.getPoint().x);
        edit.putInt("Y", poi.getPoint().y);
        edit.putInt("Z", 11);
        edit.apply();
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("key_map_center", poi.getPoint());
        pageBundle.putInt("key_map_level", 11);
        pageBundle.putString(Constants.KEY_ACTION, "action_switch_city");
        SearchPoi searchPoi = (SearchPoi) poi.as(SearchPoi.class);
        if (searchPoi.getRegions() != null && searchPoi.getRegions().size() > 0) {
            pageBundle.putObject("key_coords", searchPoi.getRegions());
        }
        if (infoliteResult.searchInfo.a.u != null && !z) {
            Double[] dArr = infoliteResult.searchInfo.a.u;
            GeoPoint geoPoint = new GeoPoint(dArr[0].doubleValue(), dArr[1].doubleValue());
            GeoPoint geoPoint2 = new GeoPoint(dArr[2].doubleValue(), dArr[3].doubleValue());
            Rect rect = new Rect(geoPoint.x, geoPoint.y, geoPoint2.x, geoPoint2.y);
            pageBundle.putObject("key_map_center", new GeoPoint(rect.centerX(), rect.centerY()));
            pageBundle.putInt("key_map_level", (int) (DoNotUseTool.getMapView().a(geoPoint.x, geoPoint.y, geoPoint2.x, geoPoint2.y) - 0.5f));
        } else if (searchPoi.getRegions() != null && searchPoi.getRegions().size() > 0) {
            ArrayList<ArrayList<GeoPoint>> regions = searchPoi.getRegions();
            if (regions != null && regions.size() > 0) {
                ArrayList arrayList = new ArrayList();
                for (ArrayList<GeoPoint> addAll : regions) {
                    arrayList.addAll(addAll);
                }
                if (arrayList.size() > 0) {
                    Rect rect2 = new Rect();
                    MapManager mapManager = DoNotUseTool.getMapManager();
                    pageBundle.putInt("key_map_level", (int) (cfe.a((List<GeoPoint>) arrayList, mapManager, rect2, (float) mapManager.getMapView().w()) - 1.0f));
                    pageBundle.putObject("key_map_center", new GeoPoint(rect2.centerX(), rect2.centerY()));
                }
            }
        }
        if (this.a.booleanValue()) {
            this.b.finish();
            apr apr = (apr) a.a.a(apr.class);
            if (apr != null) {
                apr.a(this.b, pageBundle);
            }
            return;
        }
        apr apr2 = (apr) a.a.a(apr.class);
        if (apr2 != null) {
            apr2.a(this.b, pageBundle);
        }
    }
}
