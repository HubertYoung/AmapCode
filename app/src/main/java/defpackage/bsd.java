package defpackage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.MapCustomizeManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.core.view.MapLayerDrawerView;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.map.FavoriteLayer;
import com.autonavi.minimap.map.FavoriteOverlayItem;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.log.util.LogConstant;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bsd reason: default package */
/* compiled from: MapLayerDrawerPresenter */
public final class bsd implements e, f {
    g a;
    boolean b;
    MapManager c;
    protected ArrayList<LayerItem> d = new ArrayList<>();
    final MapSharePreference e = new MapSharePreference(SharePreferenceName.SharedPreferences);
    Context f;
    Handler g;
    private final String h = getClass().getSimpleName();
    private c i;
    private d j;
    private boolean k;
    private boolean l;
    private boolean m;
    private boolean n = false;
    private boolean o = false;
    private int p = -1;
    private cde q;
    private MapCustomizeManager r;
    private bsg s;
    private bru t = null;
    private biy u = new a(this);
    private defpackage.awo.a v = new defpackage.awo.a() {
        public final void a() {
            bsd.this.h();
        }
    };

    /* renamed from: bsd$a */
    /* compiled from: MapLayerDrawerPresenter */
    static class a implements biy {
        private WeakReference<bsd> a;

        public a(bsd bsd) {
            this.a = new WeakReference<>(bsd);
        }

        public final void updateSuccess() {
            bsd bsd = this.a != null ? (bsd) this.a.get() : null;
            if (bsd != null && bsd.g != null) {
                bsd.g.post(new Runnable() {
                    public final void run() {
                        boolean o = bin.a.o("103");
                        if (o != bsd.this.b) {
                            bsd.this.a.c(o);
                            awo awo = (awo) defpackage.esb.a.a.a(awo.class);
                            if (awo != null) {
                                if (o) {
                                    awo.e();
                                } else {
                                    awo.f();
                                }
                            }
                            bsd.this.b = o;
                        }
                    }
                });
            }
        }
    }

    public bsd(@NonNull cde cde, @NonNull MapManager mapManager, com.autonavi.map.core.view.MapLayerDrawerView.a aVar) {
        this.q = cde;
        this.c = mapManager;
        this.r = cde.b();
        this.f = this.q.a();
        this.a = new MapLayerDrawerView(aVar);
        this.a.a(this);
        this.s = new bsg(this, this.q, this.c);
        this.a.a((e) this);
        this.g = new Handler(Looper.getMainLooper());
        this.t = new bru(new defpackage.bru.a() {
            public final Context a() {
                return bsd.this.f;
            }

            public final bty b() {
                return bsd.this.c.getMapView();
            }

            public final MapManager c() {
                return bsd.this.c;
            }
        });
    }

    public final void b(int i2) {
        this.p = i2;
        d();
    }

    public final void a(boolean z) {
        this.t.a(z, true, true);
        this.a.b(z);
    }

    public final void d(boolean z) {
        bmn.b();
        if (!brj.b(this.c.getMapView()) || !brj.a(this.c.getMapView())) {
            ToastHelper.showToast(this.q.a().getResources().getString(R.string.tip_realtimebus_unsupport));
            return;
        }
        this.a.a(true);
        bmn.b(true);
        if (cdd.n().b() != null) {
            cdd.n().b().a(true, true);
        }
        brn brn = (brn) ank.a(brn.class);
        if (brn != null) {
            this.c.getMapView().w();
            brn.c();
        }
        JSONObject jSONObject = new JSONObject();
        try {
            GLGeoPoint n2 = this.c.getMapView().n();
            jSONObject.put(AutoJsonUtils.JSON_ADCODE, String.valueOf(li.a().a(n2.x, n2.y)));
            jSONObject.put("status", 1);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_REALTIME_BUS, jSONObject);
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("status", "open");
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        LogManager.actionLogV2("P00367", "B005", jSONObject2);
        this.l = z;
    }

    public final void e(boolean z) {
        this.m = z;
        this.a.e(z);
        if (!(!(AMapPageUtil.getPageContext() instanceof AbstractBasePage) || DoNotUseTool.getSuspendManager() == null || DoNotUseTool.getSuspendManager().i() == null)) {
            DoNotUseTool.getSuspendManager().i().a(z);
            if (z) {
                DoNotUseTool.getSuspendManager().i().c();
                return;
            }
            DoNotUseTool.getSuspendManager().i().d();
        }
    }

    public final void c() {
        b bVar = new b();
        defpackage.bsc.a aVar = new defpackage.bsc.a();
        if (this.o) {
            this.i = bVar;
            this.j = aVar;
            this.a.a();
            bin.a.d(this.u);
            bin.a.R();
            this.a.a(bin.a.p("101"));
            this.a.a((d) aVar);
            this.l = bmn.b();
            this.a.a(this.l);
            if (this.l && !bmn.c() && brj.b(this.c.getMapView()) && brj.a(this.c.getMapView())) {
                if (cdd.n().b() != null) {
                    cdd.n().b().a(true, false);
                }
                brn brn = (brn) ank.a(brn.class);
                if (brn != null) {
                    this.c.getMapView().w();
                    brn.c();
                }
                bmn.d();
            }
            Boolean a2 = bru.a();
            this.a.b(a2 != null ? a2.booleanValue() : true);
            g gVar = this.a;
            this.b = bin.a.o("103");
            gVar.c(this.b);
            this.k = bin.a.o("104");
            this.a.d(this.k);
            if (!(!(AMapPageUtil.getPageContext() instanceof AbstractBasePage) || DoNotUseTool.getSuspendManager() == null || DoNotUseTool.getSuspendManager().i() == null)) {
                this.m = DoNotUseTool.getSuspendManager().i().a();
            }
            this.a.e(this.m);
            this.a.f(new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("map_skin_indicator_2", true));
        }
    }

    public final void d() {
        this.a.b();
    }

    public final void e() {
        this.p = 10000;
        d();
    }

    public final boolean f() {
        return this.n;
    }

    public final void g() {
        this.o = true;
        c();
    }

    public final void a() {
        this.n = true;
        defpackage.csb.a.k();
        if (this.q != null) {
            MapCustomizeManager b2 = this.q.b();
            if (!(b2 == null || b2.getMapLayerDialogCustomActions() == null)) {
                if (this.g != null) {
                    this.g.post(new Runnable() {
                        public final void run() {
                            bsd.this.i();
                        }
                    });
                }
                awo awo = (awo) defpackage.esb.a.a.a(awo.class);
                if (awo != null) {
                    awo.a(this.v);
                    awo.g();
                }
            }
        }
    }

    public final void b() {
        this.n = false;
        if (this.p != -1) {
            if (this.p != 10000) {
                int i2 = this.p;
                if (this.d != null && i2 >= 0 && i2 <= this.d.size() - 1) {
                    try {
                        LayerItem layerItem = this.d.get(i2);
                        if (layerItem != null && layerItem.isDynamic()) {
                            String action_url = layerItem.getAction_url();
                            if (!TextUtils.isEmpty(action_url)) {
                                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(action_url));
                                intent.putExtra("owner", "banner");
                                if (this.c != null) {
                                    String str = "";
                                    bty mapView = this.c.getMapView();
                                    if (!(mapView == null || mapView.n() == null)) {
                                        str = String.valueOf(GeoPoint.glGeoPoint2GeoPoint(mapView.n()).getAdCode());
                                    }
                                    if (TextUtils.isEmpty(str)) {
                                        str = "";
                                    }
                                    JSONObject jSONObject = new JSONObject();
                                    try {
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(layerItem.getLayer_id());
                                        jSONObject.put(TrafficUtil.KEYWORD, sb.toString());
                                        jSONObject.put(AutoJsonUtils.JSON_ADCODE, str);
                                    } catch (JSONException e2) {
                                        kf.a((Throwable) e2);
                                    }
                                    LogManager.actionLogV2("P00188", "B007", jSONObject);
                                    DoNotUseTool.startScheme(intent);
                                }
                            }
                        }
                    } catch (IndexOutOfBoundsException unused) {
                    }
                }
            } else {
                LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_SETTING);
                String str2 = "";
                if (this.c != null) {
                    bty mapView2 = this.c.getMapView();
                    if (!(mapView2 == null || mapView2.n() == null)) {
                        str2 = String.valueOf(GeoPoint.glGeoPoint2GeoPoint(mapView2.n()).getAdCode());
                    }
                }
                if (TextUtils.isEmpty(str2)) {
                    str2 = "";
                }
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put(AutoJsonUtils.JSON_ADCODE, str2);
                } catch (JSONException e3) {
                    kf.a((Throwable) e3);
                }
                LogManager.actionLogV2("P00001", LogConstant.MAP_SKIN_FEED_ENTRY, jSONObject2);
                this.e.putBooleanValue("map_skin_indicator_2", false);
                AMapPageUtil.getPageContext().startPage((String) "amap.basemap.action.sys_map_setting_page", (PageBundle) null);
            }
            this.p = -1;
            if (this.i != null) {
                bin.a.d(null);
            }
        }
    }

    public final void h() {
        i();
    }

    private void j() {
        LayerItem layerItem = new LayerItem();
        layerItem.setName(this.f.getString(R.string.real_scene_title));
        layerItem.setIcon_id(R.drawable.shijin_ic);
        LayerItem layerItem2 = new LayerItem();
        layerItem2.setName(this.f.getString(R.string.layer_commuting));
        layerItem2.setIcon_id(R.drawable.shangxiaban_ic);
    }

    public final void a(int i2) {
        int i3;
        int i4;
        boolean z = true;
        switch (i2) {
            case 0:
                this.s.a();
                bin.a.d((String) "101", 0);
                ceo mapLayerDialogCustomActions = this.r.getMapLayerDialogCustomActions();
                if (mapLayerDialogCustomActions != null && mapLayerDialogCustomActions.a == 4) {
                    if (!(bin.a.p("101") == 0)) {
                        bin.a.d((String) "101", 0);
                    }
                    this.c.getMapView().a(0, 0, 1);
                    ku a2 = ku.a();
                    StringBuilder sb = new StringBuilder("[");
                    sb.append(this.h);
                    sb.append("]setCarMode#setMapModeAndStyle");
                    a2.c("NaviMonitor", sb.toString());
                }
                bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext == null || !(pageContext instanceof btz)) {
                    bty mapView = this.c.getMapView();
                    if (mapView != null) {
                        mapView.a(0, 0, 0);
                    }
                } else {
                    this.c.getMapView().a(0, 0, 0);
                }
                if (this.c.getMapView().p(false) == 0) {
                    this.c.getMapView().q(true);
                }
                i4 = R.string.map_layer_standard_map;
                break;
            case 1:
                this.s.b();
                bin.a.d((String) "101", 1);
                bty mapView2 = this.c.getMapView();
                if (mapView2 != null) {
                    mapView2.a(1, 0, 0);
                }
                this.c.getMapView().q(false);
                i3 = R.string.map_layer_satellite_map;
                break;
            case 2:
                this.s.b();
                bin.a.d((String) "101", 2);
                if (bin.a.p("101") != 2) {
                    bin.a.d((String) "101", 2);
                }
                bty mapView3 = this.c.getMapView();
                if (mapView3 != null) {
                    mapView3.a(2, 0, 0);
                }
                this.c.getMapView().q(false);
                i4 = R.string.map_layer_bus_map;
                break;
            default:
                z = false;
                i3 = -1;
                break;
        }
        i3 = i4;
        z = false;
        this.a.a(i2);
        this.e.putBooleanValue("satellite", z);
        if (i3 != -1) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(this.f.getString(R.string.map_layer_mode), this.f.getString(i3));
            } catch (JSONException e2) {
                kf.a((Throwable) e2);
            }
            LogManager.actionLogV2("P00001", "B009", jSONObject);
        }
        this.c.getOverlayManager().recoverSubwayHighlight();
        this.c.notifyMapModeChange(i2);
        bty mapView4 = this.c.getMapView();
        if (mapView4 != null && i2 != 2) {
            mapView4.a(false);
        }
    }

    public final void b(final boolean z) {
        this.a.c(z);
        boolean booleanValue = this.e.getBooleanValue("confirmTrafficReport", false);
        if (!z || booleanValue) {
            f(z);
        } else {
            cse.a(this.q.a(), new defpackage.cse.a() {
                public final void a() {
                    bsd.f(true);
                    bsd.this.e.putBooleanValue("confirmTrafficReport", true);
                }

                public final void b() {
                    if (z) {
                        bsd.this.b = false;
                        bsd.this.a.c(false);
                        bsd.f(false);
                    }
                }
            });
        }
        this.b = z;
    }

    public final void c(boolean z) {
        bin.a.d((String) "104", z ? 1 : 0);
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            iMainMapService.a(czf.class);
        }
        brn brn = (brn) ank.a(brn.class);
        FavoritePOI favoritePOI = null;
        FavoriteLayer g2 = brn != null ? brn.g() : null;
        if (g2 != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", z ? "1" : "0");
            } catch (JSONException e2) {
                kf.a((Throwable) e2);
            }
            LogManager.actionLogV2("P00001", "B013", jSONObject);
            g2.refreshFavorite();
            boolean z2 = false;
            if (z) {
                if (bin.a.S()) {
                    z2 = true;
                }
                if (z2 || brn.h() <= 0) {
                    bin.a.T();
                    brn.f();
                    AMapLog.i("zyl", "isChecked---reloadAllFavorites");
                }
                g2.setVisible(true);
                g2.setClickable(true);
                g2.setMoveToFocus(true);
                FavoriteOverlayItem focusItem = g2.getFocusItem();
                if (focusItem != null) {
                    g2.clearFocus();
                    g2.onFavoriteLayerClick(focusItem);
                }
                GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(this.c.getMapView().n());
                com com2 = (com) ank.a(com.class);
                if (com2 != null) {
                    cop b2 = com2.b(com2.a());
                    if (b2 != null) {
                        favoritePOI = b2.b();
                    }
                }
                Resources resources = AMapAppGlobal.getApplication().getResources();
                if (favoritePOI == null || favoritePOI.getPoint() == null || glGeoPoint2GeoPoint == null) {
                    ToastHelper.showToast(resources.getString(R.string.map_layer_no_saved_point));
                } else {
                    ToastHelper.showToast(resources.getString(R.string.map_layer_saved_layer_is_opened));
                }
            } else {
                g2.setFavoriteItemVisible(false);
                if (!z && g2.getFocusItem() != null) {
                    this.c.getOverlayManager().dimissTips();
                }
                ToastHelper.showToast(AMapAppGlobal.getApplication().getResources().getString(R.string.map_layer_saved_layer_is_closed));
                if (this.c != null) {
                    IOverlayManager overlayManager = this.c.getOverlayManager();
                    if (!(overlayManager == null || overlayManager.getDeepInfoOverlayManager() == null || g2.getFocusItem() == null)) {
                        overlayManager.getDeepInfoOverlayManager().a();
                    }
                }
            }
        }
        this.k = z;
        this.a.d(z);
    }

    /* access modifiers changed from: protected */
    public final void i() {
        awo awo = (awo) defpackage.esb.a.a.a(awo.class);
        if (awo != null) {
            ArrayList<LayerItem> i2 = awo.i();
            ArrayList<LayerItem> arrayList = new ArrayList<>();
            Iterator<LayerItem> it = i2.iterator();
            while (it.hasNext()) {
                LayerItem next = it.next();
                if (next.getSwitch_status() != 2 && next.isDynamic()) {
                    arrayList.add(next);
                }
            }
            this.d = arrayList;
            if (this.d != null && this.d.size() > 0) {
                Collections.sort(this.d, new Comparator<LayerItem>() {
                    public final /* synthetic */ int compare(Object obj, Object obj2) {
                        return a((LayerItem) obj, (LayerItem) obj2);
                    }

                    private static int a(LayerItem layerItem, LayerItem layerItem2) {
                        try {
                            int order = layerItem.getOrder();
                            int order2 = layerItem2.getOrder();
                            if (order < order2) {
                                return -1;
                            }
                            return order > order2 ? 1 : 0;
                        } catch (NumberFormatException unused) {
                            return -1;
                        }
                    }
                });
            }
            j();
            this.a.a((List<LayerItem>) this.d);
        }
    }

    public static void f(boolean z) {
        awo awo = (awo) defpackage.esb.a.a.a(awo.class);
        if (awo != null && z != awo.d()) {
            awo.b(z);
            AMapAppGlobal.getApplication().getResources();
            boolean d2 = awo.d();
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", d2 ? "1" : "0");
                jSONObject.put("status", 1);
            } catch (JSONException e2) {
                kf.a((Throwable) e2);
            }
            LogManager.actionLogV2("P00001", "B047", jSONObject);
        }
    }
}
