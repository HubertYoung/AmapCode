package com.autonavi.map.search.manager;

import android.graphics.Point;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.search.tip.SearchPoiTipWrapper;
import com.autonavi.map.search.view.PoiDetailViewForCQ;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.MapPointOverlayItem;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SearchResultTipDetailViewManager extends bzk<bxl> implements bzo {
    public static int a = -1;
    public cbl k;
    private int l;
    private View m;
    private InfoliteResult n;
    private Map<Integer, b> o = new HashMap();
    private PoiDetailAjxLayerHandler p;
    /* access modifiers changed from: private */
    public byw q;
    private bzn r;
    private cbr s;
    /* access modifiers changed from: private */
    public bxt t;

    public enum DetailLayerState {
        EXPAND,
        COLLAPSED,
        OTHERS
    }

    class a implements b {
        private a() {
        }

        /* synthetic */ a(SearchResultTipDetailViewManager searchResultTipDetailViewManager, byte b) {
            this();
        }

        public final void a(InfoliteResult infoliteResult, PageBundle pageBundle, POI poi) {
            com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b a2 = SearchResultTipDetailViewManager.a(SearchResultTipDetailViewManager.this, infoliteResult, pageBundle, poi).a(infoliteResult, pageBundle);
            a2.n = 3;
            a2.s = ((bxl) SearchResultTipDetailViewManager.this.c).V().a();
            SearchResultTipDetailViewManager.this.k.a((defpackage.cbl.a) a2, (ViewGroup) SearchResultTipDetailViewManager.this.g);
        }
    }

    public interface b {
        void a(InfoliteResult infoliteResult, PageBundle pageBundle, POI poi);
    }

    class c implements b {
        private c() {
        }

        /* synthetic */ c(SearchResultTipDetailViewManager searchResultTipDetailViewManager, byte b) {
            this();
        }

        public final void a(InfoliteResult infoliteResult, PageBundle pageBundle, POI poi) {
            defpackage.byw.a a2 = SearchResultTipDetailViewManager.e(SearchResultTipDetailViewManager.this).a(infoliteResult, pageBundle);
            a2.n = 3;
            a2.s = ((bxl) SearchResultTipDetailViewManager.this.c).V().a();
            SearchResultTipDetailViewManager.this.k.a((defpackage.cbl.a) a2, (ViewGroup) SearchResultTipDetailViewManager.this.g);
            bbx.a((bid) ((bxl) SearchResultTipDetailViewManager.this.c).f);
        }
    }

    class d implements b {
        private d() {
        }

        /* synthetic */ d(SearchResultTipDetailViewManager searchResultTipDetailViewManager, byte b) {
            this();
        }

        public final void a(InfoliteResult infoliteResult, PageBundle pageBundle, POI poi) {
            com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b a2 = SearchResultTipDetailViewManager.a(SearchResultTipDetailViewManager.this, infoliteResult, pageBundle, poi).a(pageBundle, poi);
            if (a2.w) {
                a2.n = 5;
                SearchResultTipDetailViewManager.this.k.a((defpackage.cbl.a) a2, (ViewGroup) SearchResultTipDetailViewManager.this.g);
            }
        }
    }

    class e implements b {
        private e() {
        }

        /* synthetic */ e(SearchResultTipDetailViewManager searchResultTipDetailViewManager, byte b) {
            this();
        }

        public final void a(InfoliteResult infoliteResult, PageBundle pageBundle, POI poi) {
            PoiDetailAjxLayerHandler a2 = SearchResultTipDetailViewManager.a(SearchResultTipDetailViewManager.this, infoliteResult, pageBundle, poi);
            pageBundle.putBoolean("key_forbidden_dragdown", true);
            a2.k.a(false);
            pageBundle.putObject("key_tip_poi", PoiDetailAjxLayerHandler.a(poi));
            pageBundle.putInt("key_tip_request_type", 1);
            pageBundle.putInt("key_tip_type", 0);
            pageBundle.putString("key_centerpoint_superid", "h");
            a2.a(pageBundle, poi, (com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b) null, true, (String) "type_default");
            a2.d.b = new SearchPoiTipWrapper();
            a2.d.b.initData(infoliteResult, a2.d.p, 0);
            a2.d.c.updatePoiData(infoliteResult, (SearchPoi) poi.as(SearchPoi.class), -1);
            a2.a(pageBundle, a2.d.b.getView(), (View) a2.d.c, true, false);
            a2.k.a(a2.d.p.getName());
            a2.d.a = new MapPointOverlayItem(a2.d.p.getPoint(), R.drawable.b_poi_hl);
            a2.k.c().d().a(a2.d.a);
            a2.d.o = 5;
            a2.d.d.g();
            SearchResultTipDetailViewManager.this.k.a((defpackage.cbl.a) a2.d, (ViewGroup) SearchResultTipDetailViewManager.this.g);
        }
    }

    class f implements b {
        private f() {
        }

        /* synthetic */ f(SearchResultTipDetailViewManager searchResultTipDetailViewManager, byte b) {
            this();
        }

        public final void a(InfoliteResult infoliteResult, PageBundle pageBundle, POI poi) {
            SuperId superId;
            String str;
            PoiDetailAjxLayerHandler a2 = SearchResultTipDetailViewManager.a(SearchResultTipDetailViewManager.this, infoliteResult, pageBundle, poi);
            a2.k.g_().a(2);
            GeoPoint geoPoint = (GeoPoint) pageBundle.getObject("key_tip_schema_center_point");
            pageBundle.putObject("key_tip_poi", PoiDetailAjxLayerHandler.a(poi));
            int i = 0;
            pageBundle.putInt("key_tip_type", 0);
            pageBundle.putInt("key_tip_request_type", 1);
            if (geoPoint != null) {
                pageBundle.putString("key_centerpoint_longitude", String.valueOf(geoPoint.getLongitude()));
                pageBundle.putString("key_centerpoint_latitude", String.valueOf(geoPoint.getLatitude()));
            }
            if (geoPoint == null) {
                superId = null;
            } else {
                superId = SuperId.getInstance();
            }
            if (superId == null) {
                str = "h";
            } else {
                str = superId.getScenceId();
            }
            pageBundle.putString("key_centerpoint_superid", str);
            a2.a(pageBundle, poi, (com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b) null, true, (String) "type_inner_detail_page");
            bxb.a(a2.d);
            a2.k.a(false);
            a2.d.o = 4;
            a2.d.b = new SearchPoiTipWrapper();
            if (pageBundle.getInt("key_from_page", 0) == 1) {
                i = 3;
            }
            a2.d.b.initData(infoliteResult, a2.d.p, i);
            a2.d.m = pageBundle.getInt("floor");
            a2.d.c.updatePoiData(infoliteResult, (SearchPoi) poi.as(SearchPoi.class), -1);
            a2.i = pageBundle.getString("key_floor");
            a2.j = pageBundle.getInt("floor");
            a2.n();
            a2.a(pageBundle, a2.d.b.getView(), (View) a2.d.c, true, false);
            if (a2.e == null) {
                a2.k.a(a2.d.p.getName());
            }
            a2.d.a = new MapPointOverlayItem(a2.d.p.getPoint(), R.drawable.b_poi_hl);
            a2.k.c().d().a(a2.d.a);
            bqk.b.add(poi.getId());
            SearchResultTipDetailViewManager.this.k.a((defpackage.cbl.a) a2.d, (ViewGroup) SearchResultTipDetailViewManager.this.g);
        }
    }

    class g implements b {
        private g() {
        }

        /* synthetic */ g(SearchResultTipDetailViewManager searchResultTipDetailViewManager, byte b) {
            this();
        }

        public final void a(InfoliteResult infoliteResult, PageBundle pageBundle, POI poi) {
            SearchResultTipDetailViewManager.this.k.a((defpackage.cbl.a) SearchResultTipDetailViewManager.a(SearchResultTipDetailViewManager.this, infoliteResult, pageBundle, poi).b(infoliteResult, pageBundle, poi), (ViewGroup) SearchResultTipDetailViewManager.this.g);
        }
    }

    class h implements b {
        private h() {
        }

        /* synthetic */ h(SearchResultTipDetailViewManager searchResultTipDetailViewManager, byte b) {
            this();
        }

        public final void a(InfoliteResult infoliteResult, PageBundle pageBundle, POI poi) {
            PoiDetailAjxLayerHandler a2 = SearchResultTipDetailViewManager.a(SearchResultTipDetailViewManager.this, infoliteResult, pageBundle, poi);
            pageBundle.putInt("key_tip_request_type", 0);
            pageBundle.putObject("key_tip_poi", PoiDetailAjxLayerHandler.a(poi));
            pageBundle.putInt("key_tip_type", 0);
            pageBundle.putString("is_whole", "1");
            if (bcy.b(infoliteResult) && infoliteResult.searchInfo.a != null && !TextUtils.isEmpty(infoliteResult.searchInfo.a.R)) {
                pageBundle.putString("key_gsid", infoliteResult.searchInfo.a.R);
            }
            if (bcy.b(infoliteResult) && infoliteResult.searchInfo.a != null && !TextUtils.isEmpty(infoliteResult.searchInfo.a.O)) {
                pageBundle.putString("key_queryIntentCate", infoliteResult.searchInfo.a.O);
            }
            pageBundle.putInt("key_tip_data_source", 1);
            a2.a(pageBundle, poi, (com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b) null, true, (String) "type_search_result");
            a2.d.o = 2;
            a2.d.b = new SearchPoiTipWrapper();
            ((SearchPoiTipWrapper) a2.d.b).a();
            a2.d.b.initData(infoliteResult, a2.d.p, 3);
            a2.d.c.updatePoiData(infoliteResult, (SearchPoi) poi.as(SearchPoi.class), -1);
            a2.a(pageBundle, a2.d.b.getView(), (View) a2.d.c, true, false);
            a2.l();
            a2.d.d.a((defpackage.cap.a) new defpackage.cap.a() {
                public final void a() {
                    if (bcy.d(PoiDetailAjxLayerHandler.this.e) && PoiDetailAjxLayerHandler.this.d.p != null) {
                        PoiDetailAjxLayerHandler.this.h.a(PoiDetailAjxLayerHandler.this.e.searchInfo.a.O, bcy.l(PoiDetailAjxLayerHandler.this.e), PoiDetailAjxLayerHandler.this.d.p.getId());
                    }
                }
            });
            com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b bVar = a2.d;
            bVar.n = 5;
            bVar.s = ((bxl) SearchResultTipDetailViewManager.this.c).V().a();
            SearchResultTipDetailViewManager.this.k.a((defpackage.cbl.a) bVar, (ViewGroup) SearchResultTipDetailViewManager.this.g);
        }
    }

    class i implements b {
        private i() {
        }

        /* synthetic */ i(SearchResultTipDetailViewManager searchResultTipDetailViewManager, byte b) {
            this();
        }

        public final void a(InfoliteResult infoliteResult, PageBundle pageBundle, POI poi) {
            PoiDetailAjxLayerHandler a2 = SearchResultTipDetailViewManager.a(SearchResultTipDetailViewManager.this, infoliteResult, pageBundle, poi);
            pageBundle.putInt("key_tip_request_type", 0);
            pageBundle.putObject("key_tip_poi", PoiDetailAjxLayerHandler.a(poi));
            pageBundle.putInt("key_tip_type", 0);
            pageBundle.putString("is_whole", "1");
            if (bcy.b(infoliteResult) && infoliteResult.searchInfo.a != null && !TextUtils.isEmpty(infoliteResult.searchInfo.a.R)) {
                pageBundle.putString("key_gsid", infoliteResult.searchInfo.a.R);
            }
            if (bcy.b(infoliteResult) && infoliteResult.searchInfo.a != null && !TextUtils.isEmpty(infoliteResult.searchInfo.a.O)) {
                pageBundle.putString("key_queryIntentCate", infoliteResult.searchInfo.a.O);
            }
            pageBundle.putInt("key_tip_data_source", 1);
            a2.a(pageBundle, poi, (com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b) null, false, (String) "type_default");
            a2.k.a(true);
            a2.d.o = 6;
            com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b bVar = a2.d;
            PoiDetailViewForCQ poiDetailViewForCQ = new PoiDetailViewForCQ(DoNotUseTool.getActivity());
            bVar.e = poiDetailViewForCQ;
            poiDetailViewForCQ.setIsPoiChildMark(true);
            a2.d.e.setTipItemEvent(new ccj(false));
            a2.d.e.setPoi(a2.d.p);
            if (bcy.d(a2.e)) {
                a2.d.e.setCate(a2.e.searchInfo.a.O);
            }
            byc byc = new byc(a2.k.c().a(), a2.e);
            POI poi2 = a2.d.p;
            defpackage.byc.b bVar2 = new defpackage.byc.b(byc, 0);
            bVar2.b = byc.b;
            poiDetailViewForCQ.bindEvent(0, bVar2);
            poiDetailViewForCQ.setEventListener(new defpackage.byc.a(byc, 0));
            poiDetailViewForCQ.setPoi(poi2);
            poiDetailViewForCQ.setMainTitle(poi2.getName());
            poiDetailViewForCQ.refreshByScreenState(ags.b(byc.a.getActivity()));
            a2.d.c.updatePoiData(infoliteResult, (SearchPoi) poi.as(SearchPoi.class), -1);
            a2.a(pageBundle, (View) poiDetailViewForCQ, (View) a2.d.c, false, false);
            a2.l();
            a2.d.d.a((defpackage.cap.a) new defpackage.cap.a() {
                public final void a() {
                    if (bcy.d(PoiDetailAjxLayerHandler.this.e) && PoiDetailAjxLayerHandler.this.d.p != null) {
                        PoiDetailAjxLayerHandler.this.h.a(PoiDetailAjxLayerHandler.this.e.searchInfo.a.O, bcy.l(PoiDetailAjxLayerHandler.this.e), PoiDetailAjxLayerHandler.this.d.p.getId());
                    }
                }
            });
            com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b bVar3 = a2.d;
            bVar3.n = 5;
            SearchResultTipDetailViewManager.this.k.a((defpackage.cbl.a) bVar3, (ViewGroup) SearchResultTipDetailViewManager.this.g);
        }
    }

    class j implements b {
        private j() {
        }

        /* synthetic */ j(SearchResultTipDetailViewManager searchResultTipDetailViewManager, byte b) {
            this();
        }

        public final void a(InfoliteResult infoliteResult, PageBundle pageBundle, POI poi) {
            com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b bVar;
            bxf a2 = ((bxl) SearchResultTipDetailViewManager.this.c).V().a();
            if (SearchResultTipDetailViewManager.this.q == null || a2.a != 0) {
                SearchPoi searchPoi = (SearchPoi) poi.as(SearchPoi.class);
                PoiDetailAjxLayerHandler a3 = SearchResultTipDetailViewManager.a(SearchResultTipDetailViewManager.this, infoliteResult, pageBundle, poi);
                if (searchPoi == null || !"citycard".equals(searchPoi.getIndustry())) {
                    bVar = a3.a(infoliteResult, pageBundle, poi);
                } else {
                    bVar = a3.a(infoliteResult, pageBundle);
                }
                bVar.s = a2;
                if (bVar.w) {
                    defpackage.cbl.a a4 = SearchResultTipDetailViewManager.this.k.a();
                    char c = a4 instanceof defpackage.byw.a ? 1 : a4 instanceof com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b ? (char) 0 : 2;
                    if (!(c == 1 && a2.a == 1)) {
                        bVar.n = 3;
                    }
                    SearchResultTipDetailViewManager.this.k.a((defpackage.cbl.a) bVar, (ViewGroup) SearchResultTipDetailViewManager.this.g);
                    return;
                }
                return;
            }
            defpackage.byw.a aVar = SearchResultTipDetailViewManager.this.q.d;
            if (aVar != null) {
                aVar.n = 7;
                if (aVar.q.getParent() != null) {
                    ((ViewGroup) aVar.q.getParent()).removeView(aVar.q);
                }
                SearchResultTipDetailViewManager.this.k.a((defpackage.cbl.a) aVar, (ViewGroup) SearchResultTipDetailViewManager.this.g);
                SearchResultTipDetailViewManager.this.g.addView(aVar.q);
            }
            SearchResultTipDetailViewManager.this.t;
            bxt.a(poi, SearchResultTipDetailViewManager.this.a(), "1");
        }
    }

    class k implements b {
        private k() {
        }

        /* synthetic */ k(SearchResultTipDetailViewManager searchResultTipDetailViewManager, byte b) {
            this();
        }

        public final void a(InfoliteResult infoliteResult, PageBundle pageBundle, POI poi) {
            if (SearchResultTipDetailViewManager.this.q != null) {
                SearchResultTipDetailViewManager.this.q = null;
            }
            PoiDetailAjxLayerHandler a2 = SearchResultTipDetailViewManager.a(SearchResultTipDetailViewManager.this, infoliteResult, pageBundle, poi);
            com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b bVar = null;
            if (bcy.b(infoliteResult)) {
                POI poi2 = !infoliteResult.searchInfo.l.isEmpty() ? infoliteResult.searchInfo.l.get(0) : null;
                if (poi2 != null) {
                    pageBundle.putObject("POI", poi2);
                    pageBundle.putString("is_whole", "1");
                    pageBundle.putInt("key_tip_request_type", 0);
                    pageBundle.putObject("key_tip_poi", PoiDetailAjxLayerHandler.a(poi2));
                    pageBundle.putInt("key_tip_type", 0);
                    if (infoliteResult.searchInfo.a != null && !TextUtils.isEmpty(infoliteResult.searchInfo.a.R)) {
                        pageBundle.putString("key_gsid", infoliteResult.searchInfo.a.R);
                    }
                    if (infoliteResult.searchInfo.a != null && !TextUtils.isEmpty(infoliteResult.searchInfo.a.O)) {
                        pageBundle.putString("key_queryIntentCate", infoliteResult.searchInfo.a.O);
                    }
                    pageBundle.putInt("is_search_idq", 1);
                    pageBundle.putInt("key_tip_data_source", 1);
                    a2.a(pageBundle, poi2, (com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b) null, false, (String) "type_search_idq");
                    a2.d.o = 3;
                    poi2.getPoiExtra().put("key_result_page_type", Integer.valueOf(a2.d.o));
                    if (((SearchPoi) a2.d.p.as(SearchPoi.class)).getTemplateData() == null && ((SearchPoi) a2.d.p.as(SearchPoi.class)).getTemplateDataMap() == null) {
                        bxz.a(a2.k.c().a().getContext(), a2.d.p);
                    }
                    a2.d.b = new SearchPoiTipWrapper();
                    ((SearchPoiTipWrapper) a2.d.b).a();
                    a2.d.b.initData(infoliteResult, a2.d.p, 1);
                    View view = a2.d.b.getView();
                    a2.d.c.updatePoiData(infoliteResult, (SearchPoi) poi2.as(SearchPoi.class), -1);
                    a2.k.a(true);
                    a2.a(pageBundle, view, (View) a2.d.c, false, false);
                    a2.l();
                    a2.d.d.a((defpackage.cap.a) new defpackage.cap.a() {
                        public final void a() {
                            if (bcy.d(PoiDetailAjxLayerHandler.this.e)) {
                                PoiDetailAjxLayerHandler.this.h.a(bcy.k(PoiDetailAjxLayerHandler.this.e), bcy.l(PoiDetailAjxLayerHandler.this.e), PoiDetailAjxLayerHandler.this.d.p.getId());
                            }
                        }
                    });
                    bVar = a2.d;
                }
            }
            bVar.n = 3;
            bVar.s = ((bxl) SearchResultTipDetailViewManager.this.c).V().a();
            SearchResultTipDetailViewManager.this.k.a((defpackage.cbl.a) bVar, (ViewGroup) SearchResultTipDetailViewManager.this.g);
        }
    }

    class l implements b {
        private l() {
        }

        /* synthetic */ l(SearchResultTipDetailViewManager searchResultTipDetailViewManager, byte b) {
            this();
        }

        public final void a(InfoliteResult infoliteResult, PageBundle pageBundle, POI poi) {
            com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b b = SearchResultTipDetailViewManager.a(SearchResultTipDetailViewManager.this, infoliteResult, pageBundle, poi).b(infoliteResult, pageBundle, poi);
            if (b.w) {
                b.n = 5;
                SearchResultTipDetailViewManager.this.k.a((defpackage.cbl.a) b, (ViewGroup) SearchResultTipDetailViewManager.this.g);
            }
        }
    }

    public final void d() {
    }

    public final void i_() {
    }

    public final void s() {
    }

    public SearchResultTipDetailViewManager(bxl bxl, bzj bzj, bxt bxt) {
        super(bxl, bzj);
        if (a == -1) {
            a = agn.a(bxl.f.getContext(), 39.0f);
        }
        this.t = bxt;
    }

    public final String a() {
        return this.r instanceof byw ? this.r.d() : "invalid";
    }

    public final int m() {
        return this.r.h();
    }

    public final void e() {
        if (this.r instanceof byw) {
            this.r.g();
        }
    }

    public final int d_() {
        if (this.k == null || this.k.a() == null) {
            return -1;
        }
        return this.k.a().o;
    }

    public final int g() {
        if (this.k == null) {
            return -1;
        }
        cbl cbl = this.k;
        int c2 = this.k.c() - 2;
        defpackage.cbl.a aVar = null;
        if (!cbl.a.isEmpty() && c2 >= 0 && cbl.a.size() > c2) {
            aVar = cbl.a.get(c2);
        }
        if (aVar == null) {
            return -1;
        }
        return aVar.o;
    }

    public final int h() {
        defpackage.cbl.a a2 = this.k.a();
        if (a2 == null || !bxl.h(a2.o) || !(a2 instanceof com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b)) {
            return 0;
        }
        return ((com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b) a2).m;
    }

    public final FrameLayout b() {
        return this.g;
    }

    public final bqk c() {
        return this.c;
    }

    public final cbl e_() {
        return this.k;
    }

    public final void a(String str) {
        b(str);
    }

    public final View f_() {
        return this.d;
    }

    public final View f() {
        return this.h;
    }

    public final bzj g_() {
        return this.j;
    }

    public final void a(View view) {
        super.a(view);
        this.k = new cbl();
        this.o.put(Integer.valueOf(4), new g(this, 0));
        this.o.put(Integer.valueOf(2), new h(this, 0));
        this.o.put(Integer.valueOf(6), new j(this, 0));
        this.o.put(Integer.valueOf(0), new d(this, 0));
        this.o.put(Integer.valueOf(3), new k(this, 0));
        this.o.put(Integer.valueOf(1), new f(this, 0));
        this.o.put(Integer.valueOf(5), new e(this, 0));
        this.o.put(Integer.valueOf(7), new i(this, 0));
        this.o.put(Integer.valueOf(8), new c(this, 0));
        this.o.put(Integer.valueOf(10), new a(this, 0));
        this.o.put(Integer.valueOf(11), new l(this, 0));
    }

    public final void a(PageBundle pageBundle, InfoliteResult infoliteResult, List<POI> list, boolean z) {
        if (this.m != null) {
            this.m.setVisibility(8);
        }
        A();
        POI poi = (POI) pageBundle.getObject("POI");
        Object object = pageBundle.getObject("tip_view");
        if (object != null) {
            if (object instanceof ely) {
                this.m = ((ely) object).getView();
            } else {
                this.m = (View) object;
            }
        }
        int i2 = 0;
        if (this.m != null) {
            this.m.setVisibility(0);
        }
        if (this.m != null && (this.m instanceof AbstractPoiDetailView)) {
            ((AbstractPoiDetailView) this.m).setOnSetPoiListener(null);
        }
        this.n = infoliteResult;
        this.l = pageBundle.getInt("poi_detail_page_type", -1);
        if (this.l == -1) {
            if (this.m == null) {
                i2 = poi != null ? 1 : -1;
            }
            this.l = i2;
        }
        if (this.o.containsKey(Integer.valueOf(this.l))) {
            this.o.get(Integer.valueOf(this.l)).a(infoliteResult, pageBundle, poi);
        }
        c(true);
    }

    public final void a(POI poi) {
        a(poi, 6);
    }

    public final void a(POI poi, int i2) {
        if (((SearchPoi) poi.as(SearchPoi.class)).getTemplateDataMap() == null && i2 == 6) {
            bxz.a(((bxl) this.c).f.getContext(), poi);
        }
        A();
        c(true);
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("POI", poi);
        this.o.get(Integer.valueOf(i2)).a(this.n, pageBundle, poi);
    }

    public final void n() {
        super.n();
        if (this.r != null) {
            this.r.i();
        }
    }

    public final void w() {
        super.n();
        if (this.r instanceof PoiDetailAjxLayerHandler) {
            com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b p2 = ((PoiDetailAjxLayerHandler) this.r).p();
            if (p2 != null && p2.d != null && p2.t != null) {
                p2.d.a(true, p2.t.a);
                p2.t.a = null;
            }
        }
    }

    public final void o() {
        super.o();
        if (this.r != null) {
            this.r.j();
        }
    }

    public final void p() {
        super.p();
        if (this.q != null) {
            this.q.k();
        }
        if (this.p != null) {
            this.p.k();
        }
    }

    public final boolean q() {
        if (this.r != null) {
            return this.r.a();
        }
        return false;
    }

    public final Point l() {
        if (this.r != null) {
            return this.r.b();
        }
        return null;
    }

    public final void a(AbstractBasePage abstractBasePage, int i2, ResultType resultType, PageBundle pageBundle) {
        if (this.r != null) {
            this.r.a(abstractBasePage, i2, resultType, pageBundle);
        }
    }

    public final void d(boolean z) {
        bxd ag = ((bxl) this.c).ag();
        if (ag != null) {
            if (!z) {
                this.i.setVisibility(8);
                ag.i = false;
            } else if (ag.i && !t() && !((bxl) this.c).R() && this.r.c() != DetailLayerState.EXPAND) {
                this.i.setVisibility(0);
                ag.i = true;
            }
        }
    }

    public final void a(cbr cbr) {
        if (this.r != null) {
            this.r.a(cbr);
        }
        this.s = cbr;
    }

    public final int u() {
        return this.h.getLayoutParams().height + a;
    }

    public final void c(boolean z) {
        if (this.r != null) {
            this.r.a(z);
        }
    }

    public final boolean x() {
        if (this.r != null) {
            return this.r.e();
        }
        return false;
    }

    private void A() {
        if (this.m != null && (this.m instanceof AbstractPoiDetailView)) {
            ((AbstractPoiDetailView) this.m).setOnSetPoiListener(null);
        }
    }

    public final void r() {
        if (this.r != null) {
            this.r.f();
        }
    }

    public final void y() {
        super.y();
        if (this.r != null) {
            this.r.a((ViewGroup) this.g);
        }
    }

    public final void a(bzn bzn) {
        this.r = bzn;
        if (bzn instanceof byw) {
            this.t.a = true;
        }
    }

    public final void h_() {
        a(false);
        d(false);
        ((bxl) this.c).E();
    }

    public final DetailLayerState z() {
        if (this.p != null) {
            return this.p.g;
        }
        return DetailLayerState.OTHERS;
    }

    static /* synthetic */ PoiDetailAjxLayerHandler a(SearchResultTipDetailViewManager searchResultTipDetailViewManager, InfoliteResult infoliteResult, PageBundle pageBundle, POI poi) {
        if (searchResultTipDetailViewManager.p == null) {
            searchResultTipDetailViewManager.p = new PoiDetailAjxLayerHandler(searchResultTipDetailViewManager);
            searchResultTipDetailViewManager.p.f = searchResultTipDetailViewManager.s;
        }
        searchResultTipDetailViewManager.p.a(pageBundle, infoliteResult, poi);
        searchResultTipDetailViewManager.t.a = false;
        searchResultTipDetailViewManager.r = searchResultTipDetailViewManager.p;
        return searchResultTipDetailViewManager.p;
    }

    static /* synthetic */ byw e(SearchResultTipDetailViewManager searchResultTipDetailViewManager) {
        if (searchResultTipDetailViewManager.q == null) {
            searchResultTipDetailViewManager.q = new byw(searchResultTipDetailViewManager);
        }
        searchResultTipDetailViewManager.t.a = true;
        searchResultTipDetailViewManager.r = searchResultTipDetailViewManager.q;
        return searchResultTipDetailViewManager.q;
    }
}
