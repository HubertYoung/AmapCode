package defpackage;

import android.content.SharedPreferences.Editor;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.amaphome.api.MapHomeIntentAction;
import com.autonavi.bundle.amaphome.page.MapHomePage;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.gdtaojin.basemap.UiExecutor;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.core.Real3DManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.sdk.location.LocationInstrument;
import com.tencent.connect.common.Constants;

/* renamed from: arq reason: default package */
/* compiled from: MapHomeSchemeHandler */
public final class arq {
    final arn a;

    public arq(arn arn) {
        this.a = arn;
    }

    public final void a(PageBundle pageBundle) {
        if (pageBundle != null && pageBundle.containsKey(Constants.KEY_ACTION)) {
            String string = pageBundle.getString(Constants.KEY_ACTION);
            char c = 65535;
            switch (string.hashCode()) {
                case -2091501811:
                    if (string.equals("action_switch_city")) {
                        c = 1;
                        break;
                    }
                    break;
                case -1608218076:
                    if (string.equals("action_show_traffic")) {
                        c = 7;
                        break;
                    }
                    break;
                case -1128136817:
                    if (string.equals("action_traffic_event")) {
                        c = 8;
                        break;
                    }
                    break;
                case -133649190:
                    if (string.equals("action_move_to_current")) {
                        c = 3;
                        break;
                    }
                    break;
                case 809977317:
                    if (string.equals("action_show_realtime_bus")) {
                        c = 5;
                        break;
                    }
                    break;
                case 910458410:
                    if (string.equals("action_move_to_current_no_3d")) {
                        c = 4;
                        break;
                    }
                    break;
                case 977946637:
                    if (string.equals("action_base_map_scheme")) {
                        c = 0;
                        break;
                    }
                    break;
                case 1456493644:
                    if (string.equals("action_show_single_poi")) {
                        c = 2;
                        break;
                    }
                    break;
                case 1497081560:
                    if (string.equals("action_real3d")) {
                        c = 6;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    e(pageBundle);
                    return;
                case 1:
                    g(pageBundle);
                    return;
                case 2:
                    h(pageBundle);
                    return;
                case 3:
                    c();
                    return;
                case 4:
                    d();
                    return;
                case 5:
                    return;
                case 6:
                    Real3DManager.a().a(pageBundle, this.a.getMapManager(), this.a.getSuspendManager());
                    return;
                case 7:
                case 8:
                    if (this.a instanceof MapHomePage) {
                        b(pageBundle);
                        break;
                    }
                    break;
            }
        }
    }

    private void b(PageBundle pageBundle) {
        if (pageBundle != null && pageBundle.containsKey(Constants.KEY_ACTION)) {
            String string = pageBundle.getString(Constants.KEY_ACTION);
            if ("action_show_traffic".equalsIgnoreCase(string)) {
                c(pageBundle);
                return;
            }
            if ("action_traffic_event".equalsIgnoreCase(string)) {
                d(pageBundle);
            }
        }
    }

    private static void d(PageBundle pageBundle) {
        int i = pageBundle.getInt(IOverlayManager.EVENT_ID_KEY);
        double d = pageBundle.getDouble("lat", -1.0d);
        double d2 = pageBundle.getDouble(LocationParams.PARA_FLP_AUTONAVI_LON, -1.0d);
        int i2 = pageBundle.getInt("zoom", 16);
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null && iMainMapService.a()) {
            cde b = iMainMapService.b();
            MapManager d3 = iMainMapService.d();
            if (b != null && d3 != null) {
                cdz d4 = b.d();
                if (d4 != null && d3.getOverlayManager() != null) {
                    bty mapView = d3.getMapView();
                    if (mapView != null) {
                        new MapSharePreference(SharePreferenceName.SharedPreferences).edit().putInt("X".toString(), 0).putInt("Y".toString(), 0).putFloat("PRESISE_ZOOM_LEVEL", -1.0f).commit();
                        d4.f();
                        d4.a(false);
                        GeoPoint geoPoint = new GeoPoint(d2, d);
                        mapView.a(geoPoint.x, geoPoint.y);
                        if (i2 >= 0) {
                            mapView.f((float) i2);
                        }
                        bdl bdl = (bdl) a.a.a(bdl.class);
                        if (bdl != null) {
                            bid pageContext = AMapPageUtil.getPageContext();
                            a aVar = new a();
                            als als = new als();
                            als.e = geoPoint.x;
                            als.f = geoPoint.y;
                            als.g = -1;
                            aVar.a = als;
                            aVar.b = i;
                            bdl.a(pageContext, aVar);
                        }
                    }
                }
            }
        }
    }

    private void e(PageBundle pageBundle) {
        switch ((MapHomeIntentAction) pageBundle.getObject("key_scheme_feature")) {
            case MY_LOCATION:
                bci bci = (bci) a.a.a(bci.class);
                this.a.getMapManager().getOverlayManager().getGpsLayer();
                cdy b = cdx.b();
                if (bci != null) {
                    bci.a((bid) this.a, b);
                    return;
                }
                break;
            case OPEN_TRAFFIC_HELP:
                b();
                return;
            case OPEN_TRAFFIC_HELP_DIALOG:
                art.a(this.a);
                return;
            case SHORT_URL:
                new ars(this.a).a(pageBundle);
                return;
            case SWITCH_CITY:
                arr.a(this.a);
                return;
            case OPEN_LAYER_PANEL:
                aqw e = this.a.e();
                if (e != null && !e.c()) {
                    e.a();
                    return;
                }
            case CLOSE_FEED_LAYER:
                return;
            case CLOSED_TIPS_POINT:
                return;
            case OPEN_TRAFFIC_CONDITION:
                if (this.a instanceof MapHomePage) {
                    f(pageBundle);
                    break;
                }
                break;
        }
    }

    private void f(PageBundle pageBundle) {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        cde b = iMainMapService.b();
        MapManager d = iMainMapService.d();
        if (b != null && d != null) {
            pageBundle.getObject("POI");
            int i = pageBundle.getInt(H5PermissionManager.level);
            b.d().a(false);
            a();
            iMainMapService.g().f((float) i);
            iMainMapService.a(czl.class);
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
            if (latestPosition != null) {
                iMainMapService.g().a(latestPosition.x, latestPosition.y);
            }
        }
    }

    private void a() {
        bqx bqx = (bqx) ank.a(bqx.class);
        if (bqx != null) {
            bqx.a(true, true, DoNotUseTool.getMapManager(), this.a.getContext());
        }
    }

    private void g(PageBundle pageBundle) {
        this.a.getMapManager().getOverlayManager().clearAllFocus();
        this.a.getSuspendManager().d().f();
        Editor edit = new MapSharePreference(SharePreferenceName.SharedPreferences).edit();
        if (pageBundle.containsKey("key_map_center")) {
            GeoPoint geoPoint = (GeoPoint) pageBundle.get("key_map_center");
            this.a.getMapView().a(geoPoint.x, geoPoint.y);
            edit.putInt("X".toString(), geoPoint.x);
            edit.putInt("Y".toString(), geoPoint.y);
        }
        if (pageBundle.containsKey("key_map_level")) {
            this.a.getMapView().e(pageBundle.getInt("key_map_level"));
            edit.putFloat("PRESISE_ZOOM_LEVEL".toString(), (float) pageBundle.getInt("key_map_level"));
        }
        edit.apply();
        if (pageBundle.containsKey("key_area_name")) {
            StringBuilder sb = new StringBuilder();
            sb.append(AMapAppGlobal.getApplication().getString(R.string.switch_to));
            sb.append(pageBundle.getString("key_area_name"));
            ToastHelper.showLongToast(sb.toString());
        }
    }

    private void h(PageBundle pageBundle) {
        if (pageBundle.containsKey("key_single_poi")) {
            this.a.getMapManager().getOverlayManager().clearAllFocus();
            this.a.getSuspendManager().d().f();
            POI poi = (POI) pageBundle.getObject("key_single_poi");
            bci bci = (bci) a.a.a(bci.class);
            if (bci != null) {
                bci.a(this.a, poi, null, false);
            }
        }
    }

    private void d() {
        UiExecutor.post(new Runnable() {
            public final void run() {
                arq.this.a.getSuspendManager().d().i();
            }
        });
    }

    private void c(PageBundle pageBundle) {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        double d = pageBundle.getDouble("lat", latestPosition.getLatitude());
        double d2 = pageBundle.getDouble(LocationParams.PARA_FLP_AUTONAVI_LON, latestPosition.getLongitude());
        if (d < 0.0d) {
            d = latestPosition.getLatitude();
        }
        if (d2 < 0.0d) {
            d2 = latestPosition.getLongitude();
        }
        int i = pageBundle.getInt("zoom", -1);
        if (d >= 0.0d || d2 >= 0.0d) {
            IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
            if (iMainMapService != null && iMainMapService.a()) {
                cde b = iMainMapService.b();
                MapManager d3 = iMainMapService.d();
                if (b != null && d3 != null) {
                    cdz d4 = b.d();
                    bty mapView = d3.getMapView();
                    if (mapView != null && d4 != null) {
                        new MapSharePreference(SharePreferenceName.SharedPreferences).edit().putInt("X".toString(), 0).putInt("Y".toString(), 0).putFloat("PRESISE_ZOOM_LEVEL", -1.0f).commit();
                        d4.f();
                        d4.a(false);
                        GeoPoint geoPoint = new GeoPoint(d2, d);
                        mapView.a(geoPoint.x, geoPoint.y);
                        if (i > 0) {
                            mapView.f((float) i);
                        }
                        a();
                    }
                }
            }
        }
    }

    private static void b() {
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null && !awo.d()) {
            awo.b(true);
        }
    }

    private void c() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
        if (latestPosition != null) {
            bci bci = (bci) a.a.a(bci.class);
            DoNotUseTool.getMapManager().getOverlayManager().getGpsLayer();
            cdy b = cdx.b();
            if (bci != null) {
                bci.a(AMapPageUtil.getPageContext(), b);
            }
            this.a.getMapView().a(latestPosition.x, latestPosition.y);
            return;
        }
        ToastHelper.showToast(this.a.getContext().getResources().getString(R.string.has_no_location));
    }
}
