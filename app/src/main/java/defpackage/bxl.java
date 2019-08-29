package defpackage;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.mobile.beehive.audio.Constants;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.GpsPOI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.ICQLayerController;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.search.fragment.SearchResultBasePage;
import com.autonavi.map.search.manager.SearchResultTipDetailViewManager;
import com.autonavi.map.search.manager.SearchResultTipDetailViewManager.DetailLayerState;
import com.autonavi.map.search.overlay.NormalSearchPoiOverlay;
import com.autonavi.map.search.overlay.SearchPoiOverlay;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.MapPointOverlayItem;
import com.autonavi.minimap.base.overlay.PointOverlay.OnItemClickListener;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData;
import com.autonavi.sdk.log.util.LogConstant;
import de.greenrobot.event.EventBus;
import java.lang.ref.WeakReference;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bxl reason: default package */
/* compiled from: PoiDetailController */
public final class bxl extends bxo {
    /* access modifiers changed from: private */
    public int P;
    private boolean Q = true;
    private boolean R = false;
    private boolean S = false;
    /* access modifiers changed from: private */
    public bxm T;
    private bxt U = new bxt();
    /* access modifiers changed from: private */
    public View V;
    private defpackage.bro.a W = new defpackage.bro.a() {
        public final void a() {
            bxl.this.A.b = bim.aa().l((String) "101");
        }
    };
    private defpackage.brq.a X = new defpackage.brq.a() {
        public final void a(boolean z) {
            if (bxl.this.V != null) {
                bxl.this.V.setVisibility(z ? 8 : 0);
            }
        }
    };
    public bzd a;
    public Set<String> d = new HashSet();

    /* renamed from: bxl$a */
    /* compiled from: PoiDetailController */
    static class a implements OnItemClickListener<MapPointOverlayItem> {
        private WeakReference<bzs> a;

        public a(@NonNull WeakReference<bzs> weakReference) {
            this.a = weakReference;
        }

        public final /* synthetic */ void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
            bzs bzs = (bzs) this.a.get();
            if (bzs != null) {
                bzs.b(3);
            }
        }
    }

    /* renamed from: bxl$b */
    /* compiled from: PoiDetailController */
    class b implements cbr {
        private b() {
        }

        /* synthetic */ b(bxl bxl, byte b) {
            this();
        }

        public final void a(POI poi, int i) {
            if (bxl.this.p == null || bxl.this.p.c == -1 || bxl.this.p.a == 4) {
                if (bxl.this.p == null || bxl.this.p.b == -1) {
                    if (bxl.this.b(poi, i) && bxl.this.D != null) {
                        if (bxl.this.D.e != null) {
                            bxl.this.D.b(true);
                            bxl.this.D.e.clear();
                        }
                        if (bxl.this.p != null) {
                            bxl.this.p.c();
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                } else if (bxl.this.E != null && !bxl.this.E.isEmpty()) {
                    SuperId.getInstance().setBit3("19");
                    bxl.this.D.a(bxl.this.l, i, bxl.this.p.d, bxl.this.C.b);
                    POI poi2 = (POI) bxl.this.E.get(i);
                    poi2.getPoiExtra().put("requestPoiData", Boolean.TRUE);
                    bxl.this.T();
                    bxl.this.T.b();
                    if (bxl.this.p != null) {
                        bxl.this.p.a(1);
                        bxl.this.p.a((POI) bxl.this.E.get(i));
                        bxl.this.p.c(i);
                        bxl.this.F.a(poi2);
                        bxl.this.a((SearchPoi) poi2);
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } else if (bxl.this.b(poi, i)) {
                if (!(bxl.this.l == null || bxl.this.D.f == null)) {
                    bxl.this.D.f.clearFocus();
                    bxl.this.D.j.a();
                    bxl.this.l.b(2);
                }
                if (bxl.this.p != null) {
                    bxl.this.p.c();
                } else {
                    return;
                }
            } else {
                return;
            }
            bxl.this.h();
        }
    }

    public static boolean h(int i) {
        return i == 4 || i == 1;
    }

    static /* synthetic */ boolean i(int i) {
        return i == 6 || i == 2 || i == 7;
    }

    /* access modifiers changed from: protected */
    public final void I() {
    }

    public bxl(SearchResultBasePage searchResultBasePage) {
        super(searchResultBasePage);
    }

    public final void a(Context context) {
        if (this.P == 3) {
            this.j = Long.valueOf(System.currentTimeMillis());
        }
        EventBus.getDefault().register(this);
        this.T = new bxm(this.f);
        bty mapView = this.f.getMapView();
        if (mapView != null) {
            mapView.N();
        }
        if (!al()) {
            am();
        }
        this.P = this.f.getArguments().getInt("poi_detail_page_type", -1);
        if (this.f.getArguments().containsKey("key_superid")) {
            SuperId.getInstance().reset();
            String[] split = this.f.getArguments().getString("key_superid").split("_");
            if (split != null && split.length >= 2) {
                SuperId.getInstance().setBit1(split[0]);
                SuperId.getInstance().setBit2(split[1]);
            }
        }
        super.a(context);
        an();
        this.I.post(new Runnable() {
            public final void run() {
                if (bxl.i(bxl.this.P)) {
                    bxl.this.f.setResult(ResultType.CANCEL, (PageBundle) null);
                }
            }
        });
        brq.a().a(this.X);
    }

    public final void a(PageBundle pageBundle) {
        this.P = this.f.getArguments().getInt("poi_detail_page_type", -1);
        if (f()) {
            this.D = new bzi(this.f.getMapView());
        }
        if (f()) {
            this.j = Long.valueOf(System.currentTimeMillis());
        }
        if (this.P == 1 || this.P == 4) {
            this.D.m();
            ao();
        }
        if (!al()) {
            am();
        }
        if (this.P == 3) {
            S();
        } else if (this.P == 1 || this.P == 4) {
            this.f.getCQLayerController().setLayerVisibility(false);
            this.f.getMapManager().getOverlayManager().clearAllFocus();
            this.f.deleteCurrentFocusKey();
        }
        if (this.P == 4) {
            this.f.getCQLayerController().resetHeaderTranslation();
        }
        super.a(pageBundle);
        an();
        if (this.P == 0) {
            this.o = false;
        }
    }

    /* access modifiers changed from: protected */
    public final void x() {
        boolean z = this.f.getArguments().getBoolean("is_go_detail", false);
        if (this.R || ((!this.Q && !z) || f())) {
            O();
        }
        this.R = false;
        this.Q = false;
        if (this.p != null) {
            b(this.p);
        }
    }

    /* access modifiers changed from: protected */
    public final void a(PageBundle pageBundle, boolean z) {
        String str;
        if (this.A == null) {
            this.A = new e(this.f.getGLMapView());
        }
        if (h(this.P)) {
            if (pageBundle != null && this.f.getSuspendManager().c().e()) {
                this.f.getSuspendManager().c().b(pageBundle.getInt("floor"));
            }
            return;
        }
        super.a(pageBundle, z);
        if (f()) {
            this.f.getArguments().putLong("key_search_process_key", this.j.longValue());
            this.p = new bxf();
            this.C.a((InfoliteResult) pageBundle.getObject("poi_search_result"));
            this.p.a(5);
            this.p.b(0);
            this.p.a(this.C.b(0));
            this.e = bxd.a(this.j, Integer.valueOf(hashCode()));
            this.e.b = this.p;
            this.e.g = this.h;
            this.e.f = this.C;
            this.e.d = this.D;
            this.e.a = this.A;
            this.e.c = this.k;
            this.e.e = this.l;
            M();
            af();
            if (this.C != null && this.C.f()) {
                InfoliteParam c = this.C.c();
                SearchPoi searchPoi = (SearchPoi) this.C.b(0).as(SearchPoi.class);
                if (c == null) {
                    str = "";
                } else {
                    str = c.query_type;
                }
                String str2 = str;
                AnonymousClass7 r6 = new defpackage.bxm.a() {
                    public final boolean a() {
                        return bxl.this.F.z() == DetailLayerState.COLLAPSED && bxl.this.p != null && (bxl.this.p.a == 0 || bxl.this.p.a == 5);
                    }
                };
                bxm bxm = this.T;
                defpackage.bxm.b bVar = new defpackage.bxm.b(this.i, searchPoi, str2, this.C.h(), r6);
                bxm.c = bVar;
                bxm.e();
                if (bxm.d()) {
                    bxm.a((POI) bxm.c.c, (String) "正在加载...");
                    LogManager.actionLogV25(LogConstant.SEARCH_RESULT_MAP, "B086", new SimpleEntry(TrafficUtil.POIID, bxm.c.c.getId()), new SimpleEntry("text", bxm.c.d));
                    GeoPoint g = bxm.g();
                    if (g != null) {
                        dhx dhx = new dhx(POIFactory.createPOI(lh.a(AMapAppGlobal.getApplication(), R.string.LocationMe), g), bxm.c.c);
                        dhx.d = false;
                        dhx.e = false;
                        bxm.d = bxm.f().a(dhx, CalcRouteScene.SCENE_SEARCH, bxm.g);
                    }
                }
            }
        }
    }

    private boolean al() {
        return this.f.getArguments().getObject("POI") instanceof GpsPOI;
    }

    private void am() {
        final cde suspendManager = this.f.getSuspendManager();
        if (suspendManager != null) {
            this.I.postDelayed(new Runnable() {
                public final void run() {
                    cdz d = suspendManager.d();
                    if (d != null) {
                        d.f();
                        d.a(false);
                    }
                }
            }, 100);
        }
    }

    public final void k() {
        super.k();
    }

    public final void l() {
        bzd bzd = this.a;
        if (!(bzd.a == null || bzd.b == null)) {
            bzd.a.setOverlayOnTop(true);
            bzd.a.addItem(bzd.b);
        }
        if (bzd.d != null) {
            bzd.c.a(bzd.d, false);
        }
        super.l();
        this.f.getMapView().S();
        this.S = false;
        this.f.requestScreenOrientation(1);
        if (this.a.b != null) {
            a((GLGeoPoint) this.a.b.getGeoPoint());
        }
        this.f.getMapManager().addMapModeChangeListener(this.W);
        ai();
    }

    /* access modifiers changed from: protected */
    public final void y() {
        super.y();
        bzd bzd = this.a;
        if (bzd.a != null && !bzd.a.getItems().isEmpty()) {
            bzd.b = (MapPointOverlayItem) bzd.a.getItem(0);
        }
        if (this.p != null) {
            this.p.d();
        }
    }

    /* access modifiers changed from: protected */
    public final void a(View view) {
        super.a(view);
        this.f.getMapManager().getOverlayManager().getMapPointOverlay().setOnItemClickListener(new a(new WeakReference(this.F)));
        this.a = new bzd(new bql() {
            public final MapBasePage a() {
                return bxl.this.f;
            }

            public final void a(GeoPoint geoPoint) {
                bxl.this.a((GLGeoPoint) geoPoint);
            }
        });
        this.a.a();
        this.F.a((cbr) new b(this, 0));
    }

    /* access modifiers changed from: protected */
    public final void z() {
        if (f()) {
            super.z();
        }
    }

    /* access modifiers changed from: protected */
    public final void A() {
        this.H.a(2);
    }

    /* access modifiers changed from: protected */
    public final bzh a(bty bty) {
        long j = this.f.getArguments().getLong("key_search_process_key", -1);
        if (j != -1) {
            this.D = bxd.a(Long.valueOf(j), Integer.valueOf(hashCode())).d;
        }
        if (this.D == null) {
            return new bzi(bty);
        }
        return this.D;
    }

    /* access modifiers changed from: protected */
    public final bzg B() {
        return new SearchResultTipDetailViewManager(this, this.H, this.U);
    }

    public final void C() {
        if (!b()) {
            super.C();
            return;
        }
        PageBundle pageBundle = new PageBundle();
        String v = this.G.v();
        if (!TextUtils.isEmpty(v)) {
            pageBundle.putString(TrafficUtil.KEYWORD, v);
        }
        if (this.P == 0) {
            pageBundle.putInt("poi_detail_page_type", this.i);
            this.f.startPageForResult((String) "amap.search.action.searchfragment", pageBundle, 1);
            return;
        }
        this.f.startPage((String) "amap.search.action.searchfragment", pageBundle);
    }

    public final ON_BACK_TYPE q() {
        if (this.F.q()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        this.f.finish();
        return super.q();
    }

    public final void a(int i, ResultType resultType, PageBundle pageBundle) {
        if (ResultType.OK == resultType && i == 1 && pageBundle.getInt("poi_detail_page_type", -1) == 0) {
            this.f.finish();
        }
        this.F.a((AbstractBasePage) this.f, i, resultType, pageBundle);
    }

    public final boolean s() {
        return b();
    }

    public final void d(final int i) {
        if ((this.p.a == 0 || this.p.a == 5) && this.p.b == i) {
            b(this.C.b(i));
        } else if (!b()) {
            this.F.b(1);
            this.f.getMapManager().getOverlayManager().clearAllFocus();
            this.f.getMapManager().getOverlayManager().clearScenicSelectMapPois();
            boolean z = i == this.p.b;
            if (z) {
                this.l.a(2, true);
                if (elc.b) {
                    this.l.a(2, false);
                    this.l.a = true;
                }
                this.T.a();
            } else {
                this.l.b(2);
            }
            this.D.a(z);
            this.a.b();
            if (this.H.b() != null && this.H.b().a == 3) {
                this.H.a();
            }
            this.H.b().f = this.C.b(i);
            this.I.post(new Runnable() {
                public final void run() {
                    bxl.this.g(i);
                }
            });
            T();
            this.f.getCQLayerController().dismissCQLayer(false);
            if (this.p != null) {
                this.p.a(0);
                this.p.a(this.C.b(i));
                this.p.b(i);
                this.p.c(-1);
                this.F.a(this.H.b().f);
            }
        }
    }

    public final void e(int i) {
        if (this.p != null && i != this.p.c) {
            SuperId.getInstance().setBit3("20");
            if (this.E != null && !this.E.isEmpty() && i >= 0 && i < this.E.size()) {
                POI poi = (POI) this.E.get(i);
                if (this.F.d_() == 8 && this.C != null) {
                    POI b2 = this.C.b(0);
                    String a2 = this.F.a();
                    if (!(poi == null || b2 == null)) {
                        try {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("page", "idq_plus");
                            jSONObject.put("click", "sub_poi_click");
                            jSONObject.put("status", bxt.a(a2));
                            jSONObject.put("new_type", ((SearchPoi) poi.as(SearchPoi.class)).getSubType());
                            jSONObject.put(Constants.KEY_AUDIO_BUSINESS_ID, b2.getIndustry());
                            jSONObject.put(TrafficUtil.POIID, poi.getId());
                            jSONObject.put("source", String.format("%s;%s", new Object[]{b2.getId(), b2.getType()}));
                            LogManager.actionLogV2("1000", "1", jSONObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                this.D.c(i);
                poi.getPoiExtra().put("requestPoiData", Boolean.TRUE);
                this.F.b(1);
                this.p.a(1);
                this.p.a(poi);
                this.p.c(i);
                this.F.a(poi);
                a((SearchPoi) poi);
                T();
                this.T.b();
            }
        }
    }

    public final void f(int i) {
        if (this.p != null && i != this.p.c) {
            POI poi = (POI) this.E.get(i);
            poi.setType("150700");
            this.p.a(2);
            this.p.a(poi);
            this.p.c(i);
            a((SearchPoi) poi.as(SearchPoi.class));
            if (this.H.b() != null && this.H.b().a == 3) {
                this.H.a();
            }
            this.F.b(3);
            this.F.a(poi, 7);
            this.a.b();
        }
    }

    public final void a(bzx bzx) {
        if (this.p != null) {
            ao();
            this.f.getCQLayerController().dismissCQLayer(false);
            this.a.b();
            if (this.H.b() != null && this.H.b().a == 3) {
                this.H.a();
            }
            bzx.getPOI().getPoiExtra().put("requestPoiData", Boolean.TRUE);
            this.p.a(3);
            this.p.g = bzx;
            this.p.a(bzx.getPOI());
            this.F.a(bzx.getPOI());
            this.F.b(2);
        }
    }

    public final void D() {
        this.F.b(3);
    }

    public final void a(Object obj) {
        if (this.p != null) {
            this.p.a(4);
            this.p.b(-1);
            if (obj != null && (obj instanceof bzv)) {
                this.p.a(((bzv) obj).a);
            }
        }
        this.f.getMapManager().getOverlayManager().clearAllFocus();
        this.a.b();
        if (this.H.b() != null && this.H.b().a == 3) {
            this.H.a();
        }
        this.f.getCQLayerController().dismissCQLayer(false);
        if (obj != null && (obj instanceof bzv)) {
            this.F.a(((bzv) obj).a);
        }
    }

    private void an() {
        if (f()) {
            this.I.post(new Runnable() {
                public final void run() {
                    bxl.this.ac();
                }
            });
        } else if (this.p != null) {
            Rect rect = this.p.e;
            if (rect != null) {
                final float a2 = this.f.getMapView().a(rect.left, rect.top, rect.right, rect.bottom, ags.a(this.f.getContext()).width(), ags.a(this.f.getContext()).height() - this.F.u()) - 0.9f;
                final GeoPoint geoPoint = new GeoPoint(rect.centerX(), rect.centerY());
                if (this.p.d != null && this.p.d.getType() != null && this.p.d.getType().startsWith("15090")) {
                    return;
                }
                if (this.p.a == 0) {
                    this.I.post(new Runnable() {
                        public final void run() {
                            bxl.this.a((GLGeoPoint) geoPoint);
                        }
                    });
                    this.I.postDelayed(new Runnable() {
                        public final void run() {
                            bxl.this.f.getMapView().d(a2);
                        }
                    }, 200);
                    return;
                }
                this.f.getMapView().d(a2);
                c((GLGeoPoint) geoPoint);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void b(PageBundle pageBundle) {
        long j = pageBundle.getLong("key_search_process_key", -1);
        if (j != -1) {
            this.j = Long.valueOf(j);
            this.e = bxd.a(Long.valueOf(j), Integer.valueOf(hashCode()));
            this.p = this.e.b;
            if (this.p == null) {
                this.p = new bxf();
                this.e.b = this.p;
            }
            this.h = this.e.g;
            this.C = this.e.f;
            this.D = this.e.d;
            this.A = this.e.a;
            this.k = this.e.c;
            this.l = this.e.e;
            this.e.h = this.d;
        }
    }

    public final void a(bxf bxf) {
        this.a.b();
        this.f.getMapManager().getOverlayManager().clearAllFocus();
        this.l.a(2, true);
        if (bxf != null) {
            bxf.d();
            this.p = bxf.a();
            b(bxf);
        }
    }

    public final void a(com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b bVar) {
        if (bVar.a != null) {
            this.a.b();
            this.f.getMapManager().getOverlayManager().clearAllFocus();
            this.a.a(bVar.a);
            if (h(bVar.o)) {
                this.f.getSuspendManager().c().b(bVar.m);
            }
            this.a.a(bVar.a);
            if (bVar.p != null) {
                this.a.a(bVar.p);
            }
        }
    }

    public final void E() {
        ICQLayerController cQLayerController = this.f.getCQLayerController();
        if (cQLayerController != null) {
            boolean z = false;
            b(0);
            a_(0);
            cQLayerController.setLayerVisibility(true);
            cQLayerController.expendCQLayer();
            POI curPoi = this.f.getCQLayerController().getCurPoi();
            if (curPoi != null) {
                MapPointOverlayItem mapPointOverlayItem = new MapPointOverlayItem(curPoi.getPoint(), R.drawable.b_poi_hl);
                if (!(this.f.getMapManager() == null || this.f.getMapManager().getOverlayManager() == null || this.f.getMapManager().getOverlayManager().getMapPointOverlay() == null)) {
                    z = true;
                }
                if (z) {
                    this.f.getMapManager().getOverlayManager().getMapPointOverlay().setItem(mapPointOverlayItem);
                    this.f.getMapManager().getOverlayManager().getDeepInfoOverlayManager().a(curPoi);
                }
            }
            cQLayerController.onResume();
            cQLayerController.recoverFocusCenter();
            if (cQLayerController.getDetailLayerState() == ICQLayerController.DetailLayerState.EXPAND) {
                this.f.getMapSuspendBtnView2().setVisibility(8);
            }
            this.f.getMapManager().getOverlayManager().getMapPointOverlay().setOverlayOnTop(true);
            this.a.b();
            P();
        }
    }

    /* access modifiers changed from: protected */
    public final void F() {
        if (this.P == 0 && this.p != null) {
            this.p.c();
        }
    }

    /* access modifiers changed from: protected */
    public final void G() {
        if (f()) {
            this.C = new bxh();
        }
    }

    /* access modifiers changed from: protected */
    public final void H() {
        super.H();
        if (this.p != null) {
            bxf bxf = this.p;
            bxf.b = bxf.f.b;
            bxf.c = bxf.f.c;
            if (bxf.f.d != null) {
                bxf.d = bxf.f.d.clone();
            }
            bxf.a = bxf.f.a;
            bxf.g = bxf.f.e;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x0088  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onLabelClick(java.util.List<defpackage.als> r6) {
        /*
            r5 = this;
            bxf r0 = r5.p
            if (r0 == 0) goto L_0x0024
            bzh r0 = r5.D
            if (r0 == 0) goto L_0x0024
            bxf r0 = r5.p
            int r0 = r0.a
            r1 = 4
            if (r0 != r1) goto L_0x0024
            bzh r0 = r5.D
            com.autonavi.map.search.overlay.SearchGeoOverlay r1 = r0.e
            if (r1 == 0) goto L_0x001a
            com.autonavi.map.search.overlay.SearchGeoOverlay r1 = r0.e
            r1.clear()
        L_0x001a:
            com.autonavi.map.search.overlay.SearchPoiOverlay r1 = r0.f
            if (r1 == 0) goto L_0x0024
            com.autonavi.map.search.overlay.SearchPoiOverlay r0 = r0.f
            r1 = 1
            r0.setVisible(r1)
        L_0x0024:
            r0 = 0
            java.lang.Object r1 = r6.get(r0)
            als r1 = (defpackage.als) r1
            if (r6 == 0) goto L_0x004e
            int r2 = r6.size()
            if (r2 <= 0) goto L_0x004e
            java.lang.Object r2 = r6.get(r0)
            als r2 = (defpackage.als) r2
            esb r3 = defpackage.esb.a.a
            java.lang.Class<awo> r4 = defpackage.awo.class
            esc r3 = r3.a(r4)
            awo r3 = (defpackage.awo) r3
            if (r3 == 0) goto L_0x004e
            int r2 = r2.i
            boolean r2 = r3.d(r2)
            goto L_0x004f
        L_0x004e:
            r2 = 0
        L_0x004f:
            r3 = 90000(0x15f90, float:1.26117E-40)
            r4 = 2
            if (r2 != 0) goto L_0x0060
            int r2 = r1.i
            if (r4 == r2) goto L_0x0060
            int r2 = r1.i
            if (r3 == r2) goto L_0x0060
            r5.ao()
        L_0x0060:
            int r2 = r1.i
            if (r4 == r2) goto L_0x008f
            int r1 = r1.i
            if (r3 == r1) goto L_0x008f
            bzs r1 = r5.F
            int r1 = r1.g()
            r2 = 8
            if (r1 != r2) goto L_0x008f
            bxh r1 = r5.C
            if (r1 == 0) goto L_0x008f
            bxt r1 = r5.U
            bxh r2 = r5.C
            com.autonavi.common.model.POI r2 = r2.b(r0)
            bzs r3 = r5.F
            java.lang.String r3 = r3.a()
            boolean r4 = r1.a
            if (r4 == 0) goto L_0x008f
            r1.a = r0
            java.lang.String r0 = "2"
            defpackage.bxt.a(r2, r3, r0)
        L_0x008f:
            boolean r6 = super.onLabelClick(r6)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bxl.onLabelClick(java.util.List):boolean");
    }

    public final boolean onMapLongPress(MotionEvent motionEvent, GeoPoint geoPoint) {
        ao();
        if (this.F.g() == 8 && this.C != null) {
            bxt bxt = this.U;
            POI b2 = this.C.b(0);
            String a2 = this.F.a();
            if (bxt.a) {
                bxt.a = false;
                LogManager.actionLogV25("1000", "1", new SimpleEntry("page", "idq_plus"), new SimpleEntry("click", "long_click"), new SimpleEntry("status", bxt.a(a2)), new SimpleEntry("new_type", b2.getType()), new SimpleEntry(Constants.KEY_AUDIO_BUSINESS_ID, b2.getIndustry()), new SimpleEntry(TrafficUtil.POIID, b2.getId()));
            }
        }
        return super.onMapLongPress(motionEvent, geoPoint);
    }

    /* access modifiers changed from: protected */
    public final void c(boolean z) {
        if (this.P != 4 || z) {
            super.c(true);
            if (this.S) {
                Y();
                if (this.l != null) {
                    this.l.b();
                }
                if (this.D != null) {
                    this.D.d();
                }
            }
            bzd bzd = this.a;
            bzd.a.clear();
            bzd.a.setOverlayOnTop(false);
            bzd.c.a();
        }
    }

    public final void v() {
        if (this.F instanceof SearchResultTipDetailViewManager) {
            ((SearchResultTipDetailViewManager) this.F).w();
        }
    }

    public final void w() {
        if (this.F instanceof SearchResultTipDetailViewManager) {
            this.F.o();
        }
    }

    public final void p() {
        super.p();
    }

    public final void m() {
        if (this.f.getArguments().getBoolean("is_go_detail", false)) {
            this.S = true;
        }
        super.m();
        aj();
        this.T.b();
        this.R = true;
        this.f.getMapManager().removeMapModeChangeListener(this.W);
    }

    private void ao() {
        if (this.D != null) {
            this.D.f();
        }
        if (this.a != null) {
            this.a.b();
        }
        if (this.T != null) {
            this.T.b();
        }
        if (this.p != null) {
            this.p.c();
        }
        a(this.D);
        b(this.D);
    }

    private void b(bzh bzh) {
        if (bzh != null && this.C != null && bcy.d(this.C.b) && this.l != null) {
            this.l.a(this.C.b.searchInfo.a.o);
        }
    }

    public final void onEventMainThread(cam cam) {
        if (cam != null && this.f.isStarted()) {
            this.F.a((AbstractBasePage) this.f, cam.a, cam.b, cam.c);
        }
    }

    public final void a(POI poi, int i) {
        if (this.p == null || this.p.c == -1 || this.p.a == 4) {
            if (this.p == null || this.p.b == -1) {
                if (b(poi, i) && this.D != null) {
                    if (this.D.e != null) {
                        this.D.b(true);
                        this.D.e.clear();
                    }
                    if (this.p != null) {
                        this.p.c();
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } else if (this.E != null && !this.E.isEmpty()) {
                SuperId.getInstance().setBit3("19");
                this.D.a(this.l, i, this.p.d, this.C.b);
                POI poi2 = (POI) this.E.get(i);
                T();
                this.T.b();
                if (this.p != null) {
                    this.p.a(1);
                    this.p.a((POI) this.E.get(i));
                    this.p.c(i);
                    a((SearchPoi) poi2);
                } else {
                    return;
                }
            } else {
                return;
            }
        } else if (b(poi, i)) {
            if (!(this.l == null || this.D.f == null)) {
                this.D.f.clearFocus();
                this.D.j.a();
                this.l.b(2);
            }
            if (this.p != null) {
                this.p.c();
            } else {
                return;
            }
        } else {
            return;
        }
        h();
    }

    public final void a(String str) {
        int i;
        ArrayList arrayList = this.E;
        if (arrayList != null && !arrayList.isEmpty() && !TextUtils.isEmpty(str)) {
            int size = arrayList.size();
            i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                POI poi = (POI) arrayList.get(i);
                if (poi != null && TextUtils.equals(poi.getId(), str)) {
                    break;
                }
                i++;
            }
            if (!(this.C == null || i == -1)) {
                new b(this, 0).a(ad(), i);
            }
        }
        i = -1;
        new b(this, 0).a(ad(), i);
    }

    /* access modifiers changed from: private */
    public boolean b(POI poi, int i) {
        if (poi == null) {
            return false;
        }
        ChildrenPoiData poiChildrenInfo = ((SearchPoi) poi.as(SearchPoi.class)).getPoiChildrenInfo();
        POI poi2 = null;
        if (!(poiChildrenInfo == null || poiChildrenInfo.poiList == null || poiChildrenInfo.poiList.size() <= i)) {
            poi2 = (POI) ((List) poiChildrenInfo.poiList).get(i);
        }
        if (poi2 == null) {
            return false;
        }
        poi2.getPoiExtra().put("requestPoiData", Boolean.TRUE);
        this.F.a(poi2, 11);
        return true;
    }

    public final void r() {
        super.r();
        EventBus.getDefault().unregister(this);
        bxm bxm = this.T;
        bxm.e();
        bxm.b.F().c(bxm.e);
        bxm.b.F().c(bxm.f);
        bxm.a.getMapManager().removeMapModeChangeListener(bxm.h);
        this.S = false;
        if (this.D != null) {
            this.D.c();
        }
        if ((this.F instanceof SearchResultTipDetailViewManager) && ((SearchResultTipDetailViewManager) this.F).z() == DetailLayerState.EXPAND) {
            cfe.b(this.f);
            cfe.a((IMapPage) this.f);
        }
        if (this.p != null) {
            this.p.e = null;
        }
        bbx.b(this.f);
        if (this.D != null) {
            this.D.f();
        }
        brq.a().b(this.X);
    }

    public final boolean onMapTouchEvent(MotionEvent motionEvent) {
        if (this.e == null) {
            return super.onMapTouchEvent(motionEvent);
        }
        if (!this.e.i && motionEvent.getAction() == 2 && this.F.z() == DetailLayerState.COLLAPSED) {
            this.e.i = true;
        }
        return super.onMapTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public final void J() {
        this.P = 3;
        this.F.y();
    }

    /* access modifiers changed from: protected */
    public final void K() {
        this.f.finish();
    }

    public final boolean b() {
        if (this.C != null && this.C.b == null) {
            return true;
        }
        return false;
    }

    public final bzd d() {
        return this.a;
    }

    public final bzh e() {
        return this.D;
    }

    public final boolean onMapLevelChange(boolean z) {
        if (this.T != null) {
            this.T.c();
        }
        return super.onMapLevelChange(z);
    }

    public final void g(int i) {
        super.g(i);
        this.T.a();
    }

    public final boolean onGpsBtnClick() {
        if (this.F.d_() == 8 && this.C != null) {
            POI b2 = this.C.b(0);
            String a2 = this.F.a();
            if (b2 != null) {
                LogManager.actionLogV25("1000", "1", new SimpleEntry("page", "idq_plus"), new SimpleEntry("click", "location"), new SimpleEntry("status", bxt.a(a2)), new SimpleEntry("new_type", b2.getType()), new SimpleEntry(Constants.KEY_AUDIO_BUSINESS_ID, b2.getIndustry()), new SimpleEntry(TrafficUtil.POIID, b2.getId()));
            }
        }
        return super.onGpsBtnClick();
    }

    public final boolean f() {
        return this.P == 3 || this.P == 8 || this.P == 10;
    }

    public final void a(ccv ccv, ccy ccy) {
        if (this.V == null) {
            final vr vrVar = (vr) defpackage.esb.a.a.a(vr.class);
            if (vrVar != null) {
                this.V = (View) vrVar.b();
                this.V.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        bxl.c(bxl.this);
                        vrVar.a();
                    }
                });
            }
        }
        vp vpVar = (vp) defpackage.esb.a.a.a(vp.class);
        if (vpVar != null && vpVar.b()) {
            ccv.addWidget(this.V, ccy.q(), 3);
            vpVar.a(new vq() {
                public final void onHeadunitLoginStateChanged(int i) {
                }

                public final void onHeadunitWifiConnectStateChanged(boolean z) {
                    if (z) {
                        bxl.this.V.setVisibility(0);
                    } else {
                        bxl.this.V.setVisibility(8);
                    }
                }
            });
        }
    }

    private void b(bxf bxf) {
        switch (bxf.a) {
            case 0:
                g(bxf.b);
                this.F.b(1);
                break;
            case 1:
                g(bxf.b);
                this.D.a(this.l, bxf.c, this.C.b(bxf.b), this.C.b);
                this.F.b(5);
                break;
            case 2:
                g(bxf.b);
                this.D.a(bxf.c, bxf.b);
                break;
            case 3:
                POI poi = bxf.d;
                if (poi != null) {
                    a((GLGeoPoint) poi.getPoint());
                    this.F.b(2);
                }
                this.D.a(bxf.g);
                break;
            case 4:
                if (bxf.d != null) {
                    b((SearchPoi) bxf.d.as(SearchPoi.class));
                    this.D.b(bxf.d);
                    MapManager mapManager = this.f.getMapManager();
                    if (mapManager.getOverlayManager().getMapPointOverlay().getSize() == 0 && mapManager.getOverlayManager().getGeoCodeOverlay().getItems().isEmpty() && this.a.a.getSize() == 0) {
                        this.D.b();
                        a((GLGeoPoint) bxf.d.getPoint());
                    }
                    this.F.b(3);
                    break;
                }
                break;
            case 5:
                g(bxf.b);
                break;
        }
        this.F.b(3);
        T();
    }

    private static void a(bzh bzh) {
        if (bzh != null) {
            SearchPoiOverlay searchPoiOverlay = bzh.f;
            if (searchPoiOverlay != null && (searchPoiOverlay instanceof NormalSearchPoiOverlay)) {
                ((NormalSearchPoiOverlay) searchPoiOverlay).clearLastFocusIndex();
            }
        }
    }

    static /* synthetic */ void c(bxl bxl) {
        vp vpVar = (vp) defpackage.esb.a.a.a(vp.class);
        if (vpVar != null) {
            ICQLayerController cQLayerController = bxl.f.getCQLayerController();
            if (cQLayerController != null && bxl.p != null) {
                POI poi = bxl.p.d;
                if (poi == null) {
                    poi = cQLayerController.getCurPoi();
                }
                if (poi != null && vpVar.b()) {
                    vpVar.a(poi, (vo) new vo() {
                        public final void onSuccess(int i) {
                            switch (i) {
                                case 0:
                                    ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.send_headunit_successed_by_linksdk));
                                    return;
                                case 1:
                                    ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.send_headunit_successed_by_aos));
                                    break;
                            }
                        }

                        public final void onError(String str, String str2) {
                            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.send_headunit_failed));
                        }
                    });
                }
            }
        }
    }
}
