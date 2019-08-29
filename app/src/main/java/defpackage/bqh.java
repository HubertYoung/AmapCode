package defpackage;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus;
import com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus.IdqMaxCMD;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.POI;
import com.autonavi.idqmax.page.SearchIdqMaxPage;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.search.manager.PoiDetailAjxLayerHandler;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import java.util.HashMap;
import java.util.Map;

/* renamed from: bqh reason: default package */
/* compiled from: SearchIdqMaxController */
public final class bqh implements bzo {
    private static int m = -1;
    public SearchIdqMaxPage a;
    public bzn b;
    public PoiDetailAjxLayerHandler c;
    public bqg d;
    b e;
    cbl f;
    bzi g;
    public bqi h;
    public final Map<Integer, com.autonavi.map.search.manager.SearchResultTipDetailViewManager.b> i = new HashMap();
    public int j = -1;
    /* access modifiers changed from: private */
    public final int k;
    private bzj l;

    /* renamed from: bqh$a */
    /* compiled from: SearchIdqMaxController */
    class a implements com.autonavi.map.search.manager.SearchResultTipDetailViewManager.b {
        private a() {
        }

        /* synthetic */ a(bqh bqh, byte b) {
            this();
        }

        public final void a(InfoliteResult infoliteResult, PageBundle pageBundle, POI poi) {
            bqh.this.a.g.b();
            View c = bqh.this.a.c();
            if (c != null) {
                c.getLayoutParams().height = agn.a(bqh.this.a.getContext(), 48.0f);
                c.requestLayout();
            }
            bqj l = bqh.this.a.l();
            if (l != null) {
                l.setMarginTop((int) l.j, 0);
                l.setMarginBottom(DimensionUtils.standardUnitToPixel((float) bqh.this.d.n()), 0);
            }
            defpackage.bqg.a a2 = bqh.this.d.a(infoliteResult, bqh.this.h.d, pageBundle);
            a2.n = 1;
            bqh.this.b = bqh.this.d;
            bqh.this.f.a((defpackage.cbl.a) a2, (ViewGroup) bqh.this.a.d());
            bqh.this.j = 11;
        }
    }

    /* renamed from: bqh$b */
    /* compiled from: SearchIdqMaxController */
    class b implements bqk {
        public final void a(GLGeoPoint gLGeoPoint) {
        }

        public final void a(SearchPoi searchPoi) {
        }

        public final void a(POI poi, int i) {
        }

        public final void b(GLGeoPoint gLGeoPoint) {
        }

        public final bzh e() {
            return null;
        }

        public final boolean f() {
            return true;
        }

        public final void g() {
        }

        public final void h() {
        }

        public final int i() {
            return 11;
        }

        private b() {
        }

        /* synthetic */ b(bqh bqh, byte b) {
            this();
        }

        public final MapBasePage a() {
            return bqh.this.a;
        }

        public final void a_(int i) {
            if (bqh.this.a.k() != null) {
                ViewParent parent = bqh.this.a.k().getParent();
                if (parent != null) {
                    ((View) parent).setVisibility(i);
                }
            }
        }

        public final void b(int i) {
            if (bqh.this.a != null && bqh.this.a.isAlive()) {
                if (i == 8) {
                    i = 4;
                }
                bqj l = bqh.this.a.l();
                if (bqh.this.b instanceof bqg) {
                    View c = bqh.this.a.c();
                    if (c != null) {
                        c.getLayoutParams().height = agn.a(bqh.this.a.getContext(), 48.0f);
                        c.requestLayout();
                    }
                    if (l != null) {
                        l.setMarginTop((int) l.j, 0);
                        l.setMarginBottom(DimensionUtils.standardUnitToPixel((float) bqh.this.d.n()), 0);
                    }
                }
                if (l != null) {
                    l.b();
                }
                bqh.this.a.g().setVisibility(i);
                bqh.this.a.h().setVisibility(i);
                bqh.this.a.i().setVisibility(i);
                bqh.this.a.getMapSuspendBtnView2().setVisibility(i);
                bqh.this.a.e().setVisibility(i);
                bqh.this.a.f().setVisibility(i);
                if (i == 0) {
                    bqh.this.a.b().a();
                } else {
                    bqh.this.a.b().b();
                }
            }
        }

        public final void a(int i, boolean z) {
            b(i);
        }

        public final void c(int i) {
            if (bqh.this.a.j() != null) {
                ViewParent parent = bqh.this.a.j().getParent();
                if (parent != null) {
                    ((View) parent).setVisibility(i);
                }
            }
        }

        public final boolean b() {
            return bqh.this.b instanceof PoiDetailAjxLayerHandler;
        }

        public final POI c() {
            return bqh.this.h.d;
        }

        public final bzd d() {
            return bqh.this.a.g;
        }

        public final void a(bxf bxf) {
            bqh.this.b.i();
        }

        public final void a(com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b bVar) {
            if (bVar.a != null && d() != null) {
                d().b();
                bqh.this.a.getMapManager().getOverlayManager().clearAllFocus();
                d().a(bVar.a);
                if (bVar.o == 4 || bVar.o == 1 || bVar.o == 11) {
                    bqh.this.a.getSuspendManager().c().b(bVar.m);
                }
                d().a(bVar.a);
                if (bVar.p != null) {
                    d().a(bVar.p);
                }
            }
        }

        public final void a(boolean z) {
            View i = bqh.this.a.i();
            int i2 = 4;
            if (i != null) {
                i.setVisibility(z ? 0 : 4);
            }
            View h = bqh.this.a.h();
            if (h != null) {
                if (z) {
                    i2 = 0;
                }
                h.setVisibility(i2);
            }
        }

        public final void a(String str) {
            POI poi = bqh.this.h.f.get(str);
            if (poi != null) {
                bqh.this.c(false);
                bqh.this.a.getMapManager().getOverlayManager().clearAllFocus();
                bqh.this.a.getCQLayerController().setLayerVisibility(false);
                SuperId.getInstance().setBit3("19");
                poi.getPoiExtra().put("requestPoiData", Boolean.TRUE);
                bqh.this.a(poi, 4);
                ModuleIdqPlus l = bqh.this.d.l();
                if (l != null) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("param", str);
                    l.sendIdqMaxCmd(IdqMaxCMD.CMD_OVERLAY_CLICK_SUB, hashMap);
                }
            }
        }
    }

    /* renamed from: bqh$c */
    /* compiled from: SearchIdqMaxController */
    class c implements com.autonavi.map.search.manager.SearchResultTipDetailViewManager.b {
        private c() {
        }

        /* synthetic */ c(bqh bqh, byte b) {
            this();
        }

        public final void a(InfoliteResult infoliteResult, PageBundle pageBundle, POI poi) {
            bqh.this.a.g.b();
            bqh.this.a.l().setMarginTop(bqh.this.k, 0);
            bqh.this.a.l().setMarginBottom(bqh.this.k, 0);
            bqh.this.c.a(pageBundle, infoliteResult, poi);
            com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b a2 = bqh.this.c.a(pageBundle, poi);
            a2.n = 4;
            a2.o = 0;
            bqh.this.b = bqh.this.c;
            if (a2.w) {
                bqh.this.f.a((defpackage.cbl.a) a2, (ViewGroup) bqh.this.a.d());
            }
            bqh.this.j = 0;
        }
    }

    /* renamed from: bqh$d */
    /* compiled from: SearchIdqMaxController */
    class d implements com.autonavi.map.search.manager.SearchResultTipDetailViewManager.b {
        private d() {
        }

        /* synthetic */ d(bqh bqh, byte b) {
            this();
        }

        public final void a(InfoliteResult infoliteResult, PageBundle pageBundle, POI poi) {
            bqh.this.a.g.b();
            bqh.this.a.l().setMarginTop(bqh.this.k, 0);
            bqh.this.a.l().setMarginBottom(bqh.this.k, 0);
            bqh.this.c.a(pageBundle, infoliteResult, poi);
            com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b a2 = bqh.this.c.a(infoliteResult, pageBundle, poi);
            a2.n = 6;
            bqh.this.b = bqh.this.c;
            bqh.this.f.a((defpackage.cbl.a) a2, (ViewGroup) bqh.this.a.d());
            bqh.this.j = 4;
        }
    }

    public final void b(boolean z) {
    }

    public final void h_() {
    }

    public bqh(SearchIdqMaxPage searchIdqMaxPage, bqi bqi) {
        this.h = bqi;
        this.a = searchIdqMaxPage;
        this.k = agn.a(this.a.getContext(), 48.0f);
        this.e = new b(this, 0);
        this.l = new bzj();
        this.c = new PoiDetailAjxLayerHandler(this);
        this.d = new bqg(this);
        this.f = new cbl();
        if (m == -1) {
            m = agn.a(this.a.getContext(), 39.0f);
        }
        this.g = new bzi(this.a.getMapView());
        this.i.put(Integer.valueOf(4), new d(this, 0));
        this.i.put(Integer.valueOf(11), new a(this, 0));
        this.i.put(Integer.valueOf(0), new c(this, 0));
    }

    public final ON_BACK_TYPE a() {
        if (this.b == null) {
            return ON_BACK_TYPE.TYPE_NORMAL;
        }
        ModuleIdqPlus l2 = this.d.l();
        if ((this.b instanceof PoiDetailAjxLayerHandler) && this.j == 4 && l2 != null) {
            l2.sendIdqMaxCmd(IdqMaxCMD.CMD_SUB_BACK, null);
        }
        if ((this.b instanceof bqg) && l2 != null) {
            l2.sendIdqMaxCmd(IdqMaxCMD.CMD_IDQ_MAX_BACK, null);
        }
        return this.b.a() ? ON_BACK_TYPE.TYPE_IGNORE : ON_BACK_TYPE.TYPE_NORMAL;
    }

    public final void a(POI poi, PageBundle pageBundle) {
        this.i.get(Integer.valueOf(0)).a(this.h.c, pageBundle, poi);
        c(true);
        ModuleIdqPlus l2 = this.d.l();
        if (l2 != null) {
            l2.sendIdqMaxCmd(IdqMaxCMD.CMD_OVERLAY_CLEAR, null);
        }
    }

    public final void a(POI poi, int i2) {
        bxz.a(this.a.getContext(), poi);
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("POI", poi);
        this.i.get(Integer.valueOf(i2)).a(this.h.c, pageBundle, poi);
        c(true);
    }

    public final FrameLayout b() {
        return this.a.d();
    }

    public final bqk c() {
        return this.e;
    }

    public final cbl e_() {
        return this.f;
    }

    public final void a(boolean z) {
        if (z) {
            this.a.b().a();
        } else {
            this.a.b().b();
        }
    }

    public final void a(String str) {
        this.a.a().setKeyword(str);
    }

    public final void a(int i2) {
        if (!(this.b instanceof bqg)) {
            View c2 = this.a.c();
            c2.getLayoutParams().height = i2;
            c2.requestLayout();
        }
    }

    public final View f_() {
        return this.a.a();
    }

    public final View f() {
        return this.a.c();
    }

    public final bzj g_() {
        return this.l;
    }

    public final void a(bzn bzn) {
        this.b = bzn;
    }

    public final float i() {
        return this.a.getResources().getDimension(R.dimen.top_searchbar_height) + this.a.getResources().getDimension(R.dimen.map_container_btn_margin);
    }

    public final void i_() {
        c(false);
        this.a.getCQLayerController().setLayerVisibility(false);
        this.a.getMapManager().getOverlayManager().clearAllFocus();
        View c2 = this.a.c();
        if (c2 != null) {
            c2.getLayoutParams().height = agn.a(this.a.getContext(), 48.0f);
            c2.requestLayout();
        }
        bqj l2 = this.a.l();
        if (l2 != null) {
            l2.setMarginTop((int) l2.j, 0);
            l2.setMarginBottom(DimensionUtils.standardUnitToPixel((float) this.d.n()), 0);
        }
        a(this.h.e, 11);
    }

    public final void c(boolean z) {
        if (this.b != null) {
            this.b.a(z);
        }
    }
}
