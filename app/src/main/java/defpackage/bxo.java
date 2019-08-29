package defpackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import com.autonavi.bundle.searchresult.util.TipLogHelper;
import com.autonavi.common.KeyValueStorage.WebStorage;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.MapCustomizeManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.GeocodePOI;
import com.autonavi.map.fragmentcontainer.GpsPOI;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.BaseCQLayerOwner;
import com.autonavi.map.fragmentcontainer.page.ICQLayerController;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.fragmentcontainer.page.IPoiTipViewService;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.search.fragment.PoiDetailPageNew;
import com.autonavi.map.search.fragment.SearchResultBasePage;
import com.autonavi.map.search.fragment.SearchResultMapBaseController$2;
import com.autonavi.map.search.manager.SearchResultTipDetailViewManager.DetailLayerState;
import com.autonavi.map.search.overlay.MarkFocusOverlay;
import com.autonavi.map.search.overlay.NormalSearchPoiOverlay;
import com.autonavi.map.search.overlay.SearchChildOverlay;
import com.autonavi.map.search.overlay.SearchChildStationOverlay;
import com.autonavi.map.search.overlay.SearchGeoOverlay;
import com.autonavi.map.search.overlay.SearchParkPoiOverlay;
import com.autonavi.map.search.overlay.SearchPoiMarkOverlay;
import com.autonavi.map.search.overlay.SearchPoiOverlay;
import com.autonavi.map.search.overlay.SearchUGCTipOverlay;
import com.autonavi.map.search.view.SlidingUpPanelLayout.SlideState;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import de.greenrobot.event.EventBus;
import java.lang.ref.WeakReference;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bxo reason: default package */
/* compiled from: SearchResultMapBaseController */
public abstract class bxo extends bxn implements OnClickListener, bqk, bxk {
    protected e A;
    protected bxi B;
    public bxh C;
    public bzh D;
    public ArrayList<POI> E = new ArrayList<>();
    public bzs F;
    public bzm G;
    public bzj H;
    public c I = new c(this);
    protected boolean J = false;
    protected int K;
    protected int L;
    protected Rect M;
    defpackage.bzh.a N = new defpackage.bzh.a() {
        public final void a(BaseMapOverlay baseMapOverlay, PointOverlayItem pointOverlayItem) {
            if (bxo.this.f.isAlive()) {
                if (baseMapOverlay instanceof SearchPoiMarkOverlay) {
                    bxo.this.b((Object) pointOverlayItem);
                } else if (baseMapOverlay instanceof SearchPoiOverlay) {
                    int itemIndex = ((SearchPoiOverlay) baseMapOverlay).getItemIndex(pointOverlayItem);
                    bxo.b(bxo.this, itemIndex);
                    bxo.this.j(itemIndex);
                    bxo.this.d(itemIndex);
                    bxo.a(bxo.this, itemIndex, baseMapOverlay);
                } else if (baseMapOverlay instanceof SearchChildOverlay) {
                    bxo.this.e(((SearchChildOverlay) baseMapOverlay).getItemIndex(pointOverlayItem));
                } else if (baseMapOverlay instanceof SearchChildStationOverlay) {
                    int itemIndex2 = ((SearchChildStationOverlay) baseMapOverlay).getItemIndex(pointOverlayItem);
                    bxo.c(bxo.this, itemIndex2);
                    bxo.this.f(itemIndex2);
                } else if (baseMapOverlay instanceof SearchGeoOverlay) {
                    bxo.this.a((Object) pointOverlayItem);
                } else if (baseMapOverlay instanceof SearchParkPoiOverlay) {
                    bxo.this.D();
                } else {
                    if (baseMapOverlay instanceof MarkFocusOverlay) {
                        bxo.this.F.b(2);
                    }
                }
            }
        }
    };
    protected SuperId O;
    private int P;
    @Deprecated
    private long Q = 0;
    private bya R;
    private cbo S;
    private boolean T = false;
    private boolean U = false;
    private SearchUGCTipOverlay V;
    private bxa W;
    /* access modifiers changed from: private */
    public int X;
    /* access modifiers changed from: private */
    public int Y;
    /* access modifiers changed from: private */
    public int Z;
    private boolean a = false;
    /* access modifiers changed from: private */
    public byy aa;
    /* access modifiers changed from: private */
    public ctl ab;
    private AbstractPoiDetailView ac;
    private ely ad;
    private String ae;
    private cdp af = new cdp() {
        public final void onFloorWidgetVisibilityChanged(ami ami, boolean z, int i) {
            int i2;
            if (bxo.this.f.isAlive()) {
                if (!bxo.this.F.x()) {
                    bxo.this.h(bxo.this.X);
                }
                byy b = bxo.this.aa;
                bxo.this.F.k();
                if (b.a != null) {
                    elc.b = z;
                    elc.a = z;
                    cde suspendManager = b.a.f.getSuspendManager();
                    ami a2 = suspendManager != null ? suspendManager.c().a() : null;
                    bxc X = b.a.X();
                    bzh bzh = b.a.D;
                    if (bzh != null) {
                        boolean z2 = !z;
                        if (bzh.j != null) {
                            bzh.j.a(z2);
                        }
                        if (b.a instanceof bxl) {
                            ((bxl) b.a).a.a(!z);
                        }
                        if (X != null) {
                            String str = a2 != null ? a2.e : null;
                            InfoliteResult U = b.a.U();
                            if (U != null) {
                                if (U.searchInfo.a.J == null || !U.searchInfo.a.J.equals("interior")) {
                                    if (ami != null) {
                                        elc.d = ami.a;
                                        elc.f = ami.f;
                                        elc.g = ami.e;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(ami.d);
                                        elc.h = sb.toString();
                                    } else {
                                        elc.d = null;
                                        elc.f = null;
                                        elc.g = null;
                                        elc.h = null;
                                    }
                                } else if (!(elc.g == null || ami == null || elc.g.equals(ami.f))) {
                                    elc.d = ami.a;
                                    elc.f = ami.f;
                                    elc.g = ami.e;
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append(ami.d);
                                    elc.h = sb2.toString();
                                }
                                cce W = b.a.W();
                                if (z) {
                                    if ((b.a.V() == null || b.a.V().c == -1) && W != null) {
                                        W.a = true;
                                        W.b(2);
                                    }
                                    bty mapView = b.a.f.getMapManager().getMapView();
                                    if (mapView != null) {
                                        StringBuilder sb3 = new StringBuilder();
                                        sb3.append(GeoPoint.glGeoPoint2GeoPoint(mapView.n()).getLatitude());
                                        elc.j = sb3.toString();
                                        StringBuilder sb4 = new StringBuilder();
                                        sb4.append(GeoPoint.glGeoPoint2GeoPoint(mapView.n()).getLongitude());
                                        elc.i = sb4.toString();
                                        elc.l = mapView.H();
                                        elc.k = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
                                    }
                                } else {
                                    if (W != null && W.a) {
                                        W.a = false;
                                        W.b(2);
                                        bzh.a(W, b.a.ad(), U);
                                    }
                                    elc.l = null;
                                    elc.k = null;
                                }
                                POI ad = b.a.ad();
                                if (ad != null) {
                                    SearchPoi searchPoi = (SearchPoi) ad.as(SearchPoi.class);
                                    bxo bxo = b.a;
                                    if (bxo.p == null) {
                                        i2 = -1;
                                    } else {
                                        i2 = bxo.p.c;
                                    }
                                    if (i2 == -1) {
                                        boolean z3 = !z;
                                        if (bzh.c != null) {
                                            bzh.c.setVisible(z3);
                                        }
                                    }
                                    if (searchPoi.getIndoorPoiInfo() != null && z && !TextUtils.isEmpty(str) && str.equals(searchPoi.getIndoorPoiInfo().parentId) && searchPoi.getIndoorPoiInfo().floorNo != 0) {
                                        suspendManager.c().b(searchPoi.getIndoorPoiInfo().floorNo);
                                    }
                                    c cVar = b.a.I;
                                    if (cVar != null) {
                                        cVar.removeMessages(2);
                                        Message obtainMessage = cVar.obtainMessage();
                                        obtainMessage.what = 2;
                                        cVar.sendMessage(obtainMessage);
                                    }
                                    return;
                                }
                                int h = b.a.F.h();
                                if (h != 0) {
                                    suspendManager.c().b(h);
                                }
                            }
                        }
                    }
                }
            }
        }

        public final void onFloorChanged(int i, int i2) {
            if (bxo.this.f.isAlive()) {
                bxo.this.aa.onFloorChanged(i, i2);
            }
        }
    };
    private boolean ag = true;
    private int ah = -1;
    private boolean ai = true;
    private POI d;
    protected bxd e;
    public SearchResultBasePage f;
    protected int g;
    protected int h = 0;
    protected int i = -1;
    protected Long j = Long.valueOf(-1);
    protected bxc k;
    protected cce l;
    protected Point m;
    public boolean n = true;
    protected boolean o = false;
    public bxf p;
    protected ccy q;
    protected ccv r;
    protected View s;
    protected View t;
    protected View u;
    protected View v;
    protected View w;
    protected View x;
    protected View y;
    protected View z;

    /* renamed from: bxo$a */
    /* compiled from: SearchResultMapBaseController */
    public class a extends BaseCQLayerOwner {
        boolean a;

        public final boolean disableCollapseWhenBack() {
            return true;
        }

        public final boolean enableResetBG() {
            return false;
        }

        public a(MapBasePage mapBasePage) {
            super(mapBasePage);
        }

        public final void onShowCQLayer() {
            super.onShowCQLayer();
            bxo.this.F.c(false);
            bxo.this.b(0);
            defpackage.bzj.a b2 = bxo.this.H.b();
            if (!(b2 == null || b2.a == 3)) {
                bxo.this.H.a(3);
            }
            bxo.this.f.getCQLayerController().setLayerVisibility(true);
            bxo.this.J();
        }

        public final void onDismissCQLayer(int i) {
            super.onDismissCQLayer(i);
            bxo.this.I();
            bxo.this.f.getMapManager().getOverlayManager().clearAllMapPointRequest();
        }

        public final void onSliding() {
            super.onSliding();
            if (bxo.this.F.t()) {
                this.a = true;
                bxo.this.F.d(false);
            }
        }

        public final void onSlideEnd(boolean z) {
            super.onSlideEnd(z);
            if (z) {
                if (bxo.this.F.t()) {
                    bxo.this.F.d(false);
                }
                this.a = true;
                return;
            }
            if (this.a) {
                if (bxo.this.ag() != null) {
                    bxo.this.ag().i = true;
                }
                bxo.this.F.d(true);
            }
        }

        public final boolean isCancelHandleSuspendSliding() {
            return bxo.this.F != null && bxo.this.F.z() == DetailLayerState.EXPAND;
        }

        public final void onAjxBack() {
            bxo.this.K();
        }
    }

    /* renamed from: bxo$b */
    /* compiled from: SearchResultMapBaseController */
    static final class b implements defpackage.ceb.a {
        private WeakReference<bxo> a;

        b(@NonNull WeakReference<bxo> weakReference) {
            this.a = weakReference;
        }

        public final void a() {
            bxo bxo = (bxo) this.a.get();
            if (bxo != null) {
                bxo.onGpsBtnClick();
            }
        }
    }

    /* renamed from: bxo$c */
    /* compiled from: SearchResultMapBaseController */
    public static class c extends Handler {
        WeakReference<bxo> a;

        c(bxo bxo) {
            this.a = new WeakReference<>(bxo);
        }

        public final void handleMessage(Message message) {
            bxo bxo = (bxo) this.a.get();
            if (bxo != null) {
                switch (message.what) {
                    case 1:
                        return;
                    case 2:
                        bxo.c(bxo);
                        return;
                    case 5:
                        bxo.am();
                        return;
                    case 6:
                        aja aja = new aja((String) message.obj);
                        aja.b = new ajf() {
                            public final boolean d() {
                                return false;
                            }

                            public final boolean g() {
                                return true;
                            }

                            public final defpackage.ajh.a l_() {
                                return new defpackage.ajh.a() {
                                    public final String a() {
                                        return "";
                                    }

                                    public final boolean b() {
                                        return false;
                                    }
                                };
                            }

                            public final defpackage.ajh.b c() {
                                return new defpackage.ajh.b() {
                                    public final boolean a() {
                                        return false;
                                    }

                                    public final String b() {
                                        return null;
                                    }

                                    public final long c() {
                                        return 0;
                                    }
                                };
                            }

                            public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
                                super.onResult(i, resultType, pageBundle);
                            }
                        };
                        aix aix = (aix) defpackage.esb.a.a.a(aix.class);
                        if (aix != null) {
                            aix.a((bid) bxo.f, aja);
                            break;
                        }
                        break;
                }
            }
        }
    }

    /* renamed from: bxo$d */
    /* compiled from: SearchResultMapBaseController */
    public static class d implements defpackage.ccu.a {
        public final boolean a() {
            return true;
        }

        public final boolean b() {
            return false;
        }

        public final boolean c() {
            return false;
        }
    }

    /* renamed from: bxo$e */
    /* compiled from: SearchResultMapBaseController */
    public static class e {
        bty a;
        int b = -1;
        int c = -1;
        int d = -1;

        public e(bty bty) {
            this.a = bty;
        }

        public final void a() {
            if (this.a != null) {
                this.a.a(this.a.j(), 0, 0, 10, -1);
            }
        }

        public final void b() {
            if (this.a != null && c()) {
                this.a.a(this.a.j(), this.b, this.c, this.d, -1);
            }
        }

        private boolean c() {
            return (this.b == -1 || this.c == -1 || this.d == -1) ? false : true;
        }
    }

    /* access modifiers changed from: protected */
    public abstract void A();

    /* access modifiers changed from: 0000 */
    public abstract bzg B();

    public abstract void D();

    /* access modifiers changed from: protected */
    public abstract void F();

    /* access modifiers changed from: protected */
    public abstract void G();

    /* access modifiers changed from: protected */
    public abstract void I();

    /* access modifiers changed from: protected */
    public abstract void J();

    /* access modifiers changed from: protected */
    public abstract void K();

    public void Q() {
    }

    public void a(bxf bxf) {
    }

    public abstract void a(bzx bzx);

    /* access modifiers changed from: protected */
    public abstract void a(ccv ccv, ccy ccy);

    public void a(com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b bVar) {
    }

    public abstract void a(Object obj);

    /* access modifiers changed from: protected */
    public void b(PageBundle pageBundle) {
    }

    public boolean b() {
        return false;
    }

    public bzd d() {
        return null;
    }

    public abstract void d(int i2);

    public abstract void e(int i2);

    public abstract void f(int i2);

    public boolean f() {
        return false;
    }

    public void v() {
    }

    public void w() {
    }

    /* access modifiers changed from: protected */
    public abstract void x();

    /* access modifiers changed from: protected */
    public void y() {
    }

    public bxo(SearchResultBasePage searchResultBasePage) {
        this.f = searchResultBasePage;
        this.f.setIsHideAllOpenLayerCustomize(true);
        this.aa = new byy();
        this.aa.a = this;
    }

    public final void d(boolean z2) {
        this.J = z2;
    }

    public boolean onGpsBtnClick() {
        bty mapView = this.f.getMapManager().getMapView();
        this.m = this.F.l();
        if (!(mapView == null || this.m == null)) {
            mapView.b(this.m.x, this.m.y);
        }
        return super.onGpsBtnClick();
    }

    public void a(Context context) {
        if (this.f != null) {
            PageBundle arguments = this.f.getArguments();
            if (arguments != null) {
                this.f.a(R.layout.search_result_map_fragment);
                a(this.f.getContentView());
                b(arguments);
                z();
                a(arguments, true);
                A();
                N();
                if (this.i == 0) {
                    InfoliteParam c2 = this.C.c();
                    if ((c2 == null || !"RQBXY".equals(c2.query_type)) && this.P != 2 && aaw.c(this.f.getContext())) {
                        this.ab = (ctl) defpackage.esb.a.a.a(ctl.class);
                        if (this.ab != null) {
                            this.ab.a("17", new SearchResultMapBaseController$2(this));
                        }
                    }
                }
                this.F.a(this.f.getArguments(), this.C.b, this.C.c, true);
            }
        }
    }

    public final void g() {
        if (this.ab != null) {
            this.ab.a("17");
        }
    }

    public final bxh L() {
        return this.C;
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        this.H = new bzj();
        bzg B2 = B();
        this.G = B2;
        this.F = B2;
        this.D = a(this.f.getMapManager().getMapView());
        this.F.a(view);
        this.C = new bxh();
        this.B = new bxi(this.f.getContext());
        this.S = new cbo(this.f.getSuspendManager(), this.f.getMapManager(), this.C);
        this.q = this.f.getSuspendWidgetHelper();
        this.R = new bya(this.D);
    }

    /* access modifiers changed from: protected */
    public bzh a(bty bty) {
        return new bzi(bty);
    }

    /* access modifiers changed from: protected */
    public void z() {
        if (this.f != null) {
            bty mapView = this.f.getMapManager().getMapView();
            if (mapView != null) {
                this.l = new cce(mapView);
                this.k = new bxc(this.l);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void M() {
        bxh bxh = this.C;
        if (bxh.c != null && bcy.d(bxh.b) && "rqbxy".equals(bxh.b.searchInfo.a.J)) {
            elc.b = false;
            elc.f = null;
            elc.g = null;
            elc.h = null;
        }
    }

    /* access modifiers changed from: protected */
    public void N() {
        this.G.b(this.f.getContentView());
        if (this.C != null && this.C.b != null) {
            if (bcy.f(this.C.b) && !TextUtils.isEmpty(this.C.b())) {
                this.G.b(this.C.b());
            }
            if (elc.b) {
                this.D.a(this.p);
            }
        }
    }

    public final void j() {
        if (this.A != null) {
            e eVar = this.A;
            if (eVar.a != null) {
                int l2 = bim.aa().l((String) "101");
                int o2 = eVar.a.o(false);
                if (l2 == 0) {
                    eVar.b = l2;
                    eVar.c = eVar.a.ae();
                    if (o2 == 0 || o2 > 1000) {
                        eVar.d = o2;
                    }
                    eVar.a();
                    return;
                }
                eVar.a.a(eVar.a.j(), l2, eVar.a.ae(), eVar.a.o(false), -1);
            }
        }
    }

    @CallSuper
    public void k() {
        this.f.getMapView().N();
        cbo cbo = this.S;
        EventBus.getDefault().register(cbo);
        cbo.b = false;
    }

    @CallSuper
    public void l() {
        if (this.f != null) {
            String str = this.C.n;
            String k2 = bcy.k(this.C.b);
            TipLogHelper.a = str;
            TipLogHelper.b = k2;
            cbn cbn = this.S.a;
            if (cbn != null) {
                defpackage.bzj.a b2 = this.H.b();
                if (b2 != null) {
                    if (b2.a != 3 || this.f.getCQLayerController().getDetailLayerState() == ICQLayerController.DetailLayerState.COLLAPSED) {
                        a(cbn);
                    }
                    if (this.f != null) {
                        bqx bqx = (bqx) ank.a(bqx.class);
                        this.q.d(bqx != null ? bqx.a() : false);
                    }
                }
            }
            this.F.n();
            if (this.f != null) {
                cde suspendManager = this.f.getSuspendManager();
                MapManager mapManager = this.f.getMapManager();
                if (!(suspendManager == null || mapManager == null)) {
                    bty mapView = mapManager.getMapView();
                    if (mapView != null) {
                        mapView.g(false);
                        E();
                        mapView.h(mapView.ad());
                    }
                    cdd.n().e();
                    suspendManager.c().a(this.af);
                    h(this.X);
                }
            }
            if (this.q != null) {
                this.q.a((defpackage.ceb.a) new b(new WeakReference(this)));
            }
            if (this.C.b != null) {
                aey.a(this.U);
                this.D.a(this.N);
                H();
                x();
                if (this.f != null) {
                    this.F.a(this.C.b);
                    bty mapView2 = this.f.getMapManager().getMapView();
                    if (!(mapView2 == null || this.g == 0)) {
                        mapView2.e(this.g);
                    }
                }
                bty mapView3 = this.f.getMapManager().getMapView();
                if (!(mapView3 == null || this.g == 0)) {
                    mapView3.e(this.g - 3);
                    this.f.getMapView().a((float) this.g, 1000);
                    this.g = 0;
                }
            }
            h();
            if (this.q != null) {
                this.q.b();
            }
        }
    }

    public final void b(boolean z2) {
        if (z2) {
            this.S.b();
        }
    }

    /* access modifiers changed from: protected */
    public void H() {
        if (bcy.a(this.C.b)) {
            cbn cbn = this.S.a;
            if (cbn != null) {
                this.C.b.searchInfo.o.a = cbn.b.a;
                this.C.b.searchInfo.o.b = cbn.b.b;
                this.C.b.searchInfo.o.c = cbn.b.c;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(cbn cbn) {
        if (this.f != null && cbn != null) {
            bty mapView = this.f.getMapManager().getMapView();
            if (mapView != null) {
                mapView.d(cbn.a.a);
                GLGeoPoint gLGeoPoint = cbn.a.d;
                if (this.f.isAlive()) {
                    bty mapView2 = this.f.getMapManager().getMapView();
                    if (mapView2 != null) {
                        if (this.m != null) {
                            mapView2.b(this.m.x, this.m.y);
                            mapView2.S();
                        }
                        mapView2.a(gLGeoPoint.x, gLGeoPoint.y);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void c(boolean z2) {
        if (z2 && !this.o) {
            Y();
            if (this.l != null) {
                this.l.b();
            }
            if (this.D != null) {
                this.D.d();
            }
        }
    }

    public final void O() {
        bzh bzh = this.D;
        if (bzh.d != null) {
            bzh.d.clear();
        }
        this.D.a(this.C.b, this.S.a, this.C.k);
        Q();
        ArrayList arrayList = null;
        if (this.C.b != null) {
            cde suspendManager = this.f != null ? this.f.getSuspendManager() : null;
            ami a2 = suspendManager != null ? suspendManager.c().a() : null;
            InfoliteResult infoliteResult = this.C.b;
            int i2 = -1000;
            if (bcy.d(infoliteResult) && bcy.b(infoliteResult) && "interior".equals(infoliteResult.searchInfo.a.J)) {
                ArrayList<POI> arrayList2 = infoliteResult.searchInfo.l;
                if (!arrayList2.isEmpty()) {
                    POI poi = arrayList2.get(0);
                    if (poi != null) {
                        SearchPoi searchPoi = (SearchPoi) poi.as(SearchPoi.class);
                        if (searchPoi != null) {
                            IndoorPoiData indoorPoiInfo = searchPoi.getIndoorPoiInfo();
                            if (indoorPoiInfo != null) {
                                i2 = indoorPoiInfo.floorNo;
                            }
                        }
                    }
                }
            }
            if (suspendManager != null && a2 != null && elc.b && elc.a) {
                suspendManager.c().b(i2);
            }
            this.k.a = this.C.b;
            this.D.f.setVisible(true);
            this.k.a(this.C.c, this.D.f, this.p, this.J);
        }
        POI ad2 = ad();
        if (ad2 != null) {
            SearchPoi searchPoi2 = (SearchPoi) ad2.as(SearchPoi.class);
            if (searchPoi2 != null) {
                ChildrenPoiData poiChildrenInfo = searchPoi2.getPoiChildrenInfo();
                if (poiChildrenInfo != null) {
                    switch (poiChildrenInfo.childType) {
                        case 1:
                            if (searchPoi2 != null) {
                                if (searchPoi2 != null) {
                                    ChildrenPoiData poiChildrenInfo2 = searchPoi2.getPoiChildrenInfo();
                                    if (poiChildrenInfo2 != null && poiChildrenInfo2.childType == 1) {
                                        Collection<? extends POI> collection = poiChildrenInfo2.stationList;
                                        if (collection != null) {
                                            arrayList = new ArrayList();
                                            for (POI add : collection) {
                                                arrayList.add(add);
                                            }
                                        }
                                    }
                                }
                                if (arrayList != null) {
                                    this.D.a((List<POI>) arrayList);
                                    break;
                                }
                            }
                            break;
                        case 2:
                            if (searchPoi2 != null) {
                                List<POI> a3 = bxv.a(searchPoi2);
                                if (a3 != null) {
                                    this.E.clear();
                                    this.E.addAll(a3);
                                    this.D.b(a3);
                                    break;
                                }
                            }
                            break;
                    }
                }
            }
        }
        if (!(this.C.b == null || this.p == null)) {
            this.l.a(this.C.b.searchInfo, this.p.b, this.p.c);
        }
        d(ad());
    }

    /* access modifiers changed from: protected */
    public final void P() {
        this.m = null;
    }

    public final boolean R() {
        return !(this.C != null && this.C.c() != null && !TextUtils.isEmpty(this.C.c().search_sceneid) && "103500".equals(this.C.c().search_sceneid)) && this.C != null && this.C.f == 0;
    }

    public final void h() {
        cdz d2 = this.f.getSuspendManager().d();
        if (d2 != null) {
            d2.f();
            d2.a(false);
        }
    }

    public final void n() {
        MapManager mapManager = this.f.getMapManager();
        if (!mapManager.isMapSurfaceCreated()) {
            mapManager.getMapView();
            E();
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                public final void run() {
                    if (bxo.this.l != null && bcy.a(bxo.this.C.b) && bxo.this.p != null) {
                        cce cce = bxo.this.l;
                        aus aus = bxo.this.C.b.searchInfo;
                        int i = bxo.this.p.b;
                        int i2 = bxo.this.p.c;
                        if (cce.c != null && aus != null && aus.l != null && aus.l.size() != 0) {
                            cce.a((List<POI>) aus.l, 90000, aus.a.o);
                            ArrayList<POI> arrayList = aus.l;
                            if (i >= 0 && i < arrayList.size()) {
                                cce.a(arrayList.get(i), 90000, aus.a.o);
                            }
                            cce.a(aus, i, i2);
                        }
                    }
                }
            }, 100);
        }
    }

    public final void o() {
        bty mapView = this.f.getMapManager().getMapView();
        Point point = this.m;
        defpackage.bzj.a b2 = this.H.b();
        if (!(b2 == null || mapView == null || point == null || b2.a == 3)) {
            mapView.b(point.x, point.y);
        }
    }

    public final void j(int i2) {
        POI b2 = this.C.b(i2);
        if (b2 != null) {
            SearchPoi searchPoi = (SearchPoi) b2.as(SearchPoi.class);
            Map templateDataMap = searchPoi.getTemplateDataMap();
            String id = searchPoi.getId();
            if (!TextUtils.isEmpty(id) && this.F != null) {
                SlideState j2 = this.F.j();
                String str = "";
                if (j2 != null) {
                    switch (j2) {
                        case ANCHORED:
                            str = "图表";
                            break;
                        case COLLAPSED:
                            str = "全图";
                            break;
                    }
                } else {
                    str = ModulePoi.TIPS;
                }
                if (!TextUtils.isEmpty(str)) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("text", bcy.k(this.C.b));
                        jSONObject.put(TrafficUtil.POIID, id);
                        jSONObject.put("status", str);
                        if (!(templateDataMap == null || templateDataMap.get(Integer.valueOf(3005)) == null)) {
                            jSONObject.put("deep", 3005);
                            LogManager.actionLogV2(LogConstant.SEARCH_RESULT_MAP, LogConstant.MAIN_MAP_GUIDE_BTN_CLICK, jSONObject);
                        }
                        LogManager.actionLogV2(LogConstant.SEARCH_RESULT_MAP, LogConstant.HONGBAO_SHOWN, jSONObject);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    @CallSuper
    public void p() {
        boolean z2 = this.f.getArguments().getBoolean("is_go_detail", false);
        if (!this.o && !z2 && this.A != null) {
            this.A.b();
        }
        cbo cbo = this.S;
        EventBus.getDefault().unregister(cbo);
        cbo.a();
    }

    public ON_BACK_TYPE q() {
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public void r() {
        cde suspendManager = this.f.getSuspendManager();
        h();
        g();
        if (suspendManager != null) {
            suspendManager.c().b(this.af);
        }
        if (this.F != null) {
            this.F.p();
        }
        if (this.I != null) {
            this.I.removeCallbacksAndMessages(null);
        }
        if (this.aa != null) {
            this.aa.a = null;
        }
        if (this.E != null) {
            this.E.clear();
            this.E = null;
        }
        aey.a(false);
        bxd.b(this.j, Integer.valueOf(hashCode()));
        defpackage.ekw.a.a.a = 0;
    }

    public boolean onNonFeatureClick() {
        this.D.q();
        if (this.l != null) {
            this.l.a();
        }
        this.f.getBottomTipsContainer().getContainer().removeAllViews();
        return super.onNonFeatureClick();
    }

    public void a(PageBundle pageBundle) {
        if (pageBundle != null) {
            this.T = true;
            this.F.e(false);
            this.n = true;
            G();
            b(pageBundle);
            a(pageBundle, false);
            c(false);
            this.D.a(this.N);
            this.F.a(pageBundle, this.C.b, this.C.c, false);
            N();
            F();
            this.m = null;
            this.S.b();
            if (this.P == 2) {
                this.F.s();
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void S() {
        this.f.getMapManager().getOverlayManager().clearAllFocus();
        this.f.getCQLayerController().dismissCQLayer(false);
    }

    /* access modifiers changed from: protected */
    public final void T() {
        this.I.removeMessages(2);
        Message obtainMessage = this.I.obtainMessage();
        obtainMessage.what = 2;
        this.I.sendMessageDelayed(obtainMessage, 100);
    }

    public boolean onMapLevelChange(boolean z2) {
        T();
        if (!(this.D == null || this.C == null || this.C.b == null)) {
            this.D.a(this.l, ad(), this.C.b, (this.p == null || this.p.c == -1) ? false : true);
        }
        return super.onMapLevelChange(z2);
    }

    public boolean onMapLongPress(MotionEvent motionEvent, GeoPoint geoPoint) {
        Y();
        this.m = ao();
        this.F.b(4);
        LogManager.actionLogV2(LogConstant.SEARCH_RESULT_MAP, LogConstant.MAIN_MAP_GUIDE_MAP_CLICK, al());
        GeocodePOI geocodePOI = (GeocodePOI) POIFactory.createPOI("", geoPoint).as(GeocodePOI.class);
        PageBundle pageBundle = new PageBundle();
        String string = AMapAppGlobal.getApplication().getString(R.string.select_point_from_map);
        String string2 = AMapAppGlobal.getApplication().getString(R.string.getting_address);
        geocodePOI.setName(string);
        if (TextUtils.isEmpty(string2)) {
            string2 = geocodePOI.getName();
        }
        pageBundle.putString("mainTitle", string2);
        pageBundle.putString("viceTitle", null);
        pageBundle.putObject("POI", geocodePOI);
        IPoiTipViewService iPoiTipViewService = (IPoiTipViewService) ank.a(IPoiTipViewService.class);
        if (iPoiTipViewService != null) {
            this.ac = iPoiTipViewService.createPoiDetailViewForCQ();
            this.ac.adjustMargin();
        }
        this.ac.refreshByScreenState(ags.b(this.f.getActivity()));
        AbstractPoiDetailView abstractPoiDetailView = this.ac;
        if (abstractPoiDetailView != null) {
            String string3 = pageBundle.getString("mainTitle");
            String string4 = pageBundle.getString("viceTitle");
            POI poi = (POI) pageBundle.getObject("POI");
            if (poi != null) {
                if (string3 == null && string4 == null) {
                    if (!((FavoritePOI) poi.as(FavoritePOI.class)).isSaved()) {
                        string3 = poi.getName();
                        string4 = poi.getAddr();
                    } else {
                        string3 = ((FavoritePOI) poi.as(FavoritePOI.class)).getCustomName();
                        if (TextUtils.isEmpty(string3)) {
                            string3 = ((FavoritePOI) poi.as(FavoritePOI.class)).getName();
                        }
                        string4 = ((FavoritePOI) poi.as(FavoritePOI.class)).getAddr();
                    }
                }
                boolean isInstance = GpsPOI.class.isInstance(poi);
                View findViewById = abstractPoiDetailView.findViewById(R.id.child_station_ll);
                TextView textView = (TextView) abstractPoiDetailView.findViewById(R.id.station_num);
                if (pageBundle.getBoolean("isChildStation")) {
                    findViewById.setVisibility(0);
                    textView.setText(pageBundle.getString("childAlia"));
                } else {
                    findViewById.setVisibility(8);
                }
                abstractPoiDetailView.setMainTitle(string3);
                abstractPoiDetailView.setViceTitle(string4);
                ISearchPoiData iSearchPoiData = (ISearchPoiData) poi.as(ISearchPoiData.class);
                if (iSearchPoiData.getPoiChildrenInfo() != null) {
                    Collection<? extends POI> collection = iSearchPoiData.getPoiChildrenInfo().stationList;
                    if (collection != null && collection.size() > 0) {
                        abstractPoiDetailView.setPoi(((ChildStationPoiData[]) collection.toArray(new ChildStationPoiData[collection.size()]))[0]);
                    }
                }
                if (isInstance && TextUtils.isEmpty(poi.getName())) {
                    poi.setName(AMapAppGlobal.getApplication().getString(R.string.my_location));
                }
                abstractPoiDetailView.setPoi(poi);
            }
        }
        PageBundle a2 = a((POI) geocodePOI, (Object) this.ac);
        a2.putInt("key_tip_request_type", 2);
        a2.putInt("key_tip_type", 1);
        a2.putString("point_type", "2");
        a(new bxf(), a2);
        return super.onMapLongPress(motionEvent, geoPoint);
    }

    private void a(bxf bxf, PageBundle pageBundle) {
        this.o = true;
        pageBundle.putInt("poi_detail_page_type", 0);
        pageBundle.putLong("key_search_process_key", this.j.longValue());
        if (this.F.k() == SlideState.ANCHORED) {
            this.K = -1;
            this.L = -1;
        }
        pageBundle.putInt("originalX", this.K);
        pageBundle.putInt("originalY", this.L);
        bxd.a(this.j, Integer.valueOf(hashCode())).b = bxf;
        this.f.startPage(PoiDetailPageNew.class, pageBundle);
    }

    private static PageBundle a(POI poi, Object obj) {
        PageBundle pageBundle;
        if (obj instanceof ely) {
            ely ely = (ely) obj;
            ely.getView();
            pageBundle = eks.a(poi, ely);
        } else if (obj instanceof View) {
            pageBundle = eks.a(poi, (View) obj);
        } else {
            MapBasePage.LogCQ(String.format("CQLayerController showCQLayer. type of poiTipView is wrong", new Object[0]));
            return null;
        }
        return pageBundle;
    }

    public final InfoliteResult U() {
        return this.C.b;
    }

    public final int i() {
        return this.i;
    }

    public final bxf V() {
        return this.p == null ? new bxf() : this.p;
    }

    public final cce W() {
        return this.l;
    }

    public final bxc X() {
        return this.k;
    }

    /* access modifiers changed from: protected */
    public final void a(POI poi) {
        if (this.E != null) {
            this.E.clear();
        }
        SearchPoi searchPoi = (SearchPoi) poi.as(SearchPoi.class);
        if (searchPoi != null) {
            Collection<? extends POI> collection = null;
            if (searchPoi.getPoiChildrenInfo() != null && searchPoi.getPoiChildrenInfo().childType == 2) {
                collection = searchPoi.getPoiChildrenInfo().poiList;
            } else if (searchPoi.getPoiChildrenInfo() != null) {
                collection = searchPoi.getPoiChildrenInfo().stationList;
            }
            if (collection != null && collection.size() > 0) {
                Map templateDataMap = ((SearchPoi) searchPoi.as(SearchPoi.class)).getTemplateDataMap();
                if (templateDataMap != null && templateDataMap.size() > 0) {
                    PoiLayoutTemplate poiLayoutTemplate = (PoiLayoutTemplate) templateDataMap.get(Integer.valueOf(1014));
                    if (poiLayoutTemplate != null && poiLayoutTemplate.isShown() == 0) {
                        for (POI distance : collection) {
                            distance.setDistance(searchPoi.getDistance());
                        }
                    }
                }
                this.E.addAll(collection);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x010f A[Catch:{ Exception -> 0x01bc }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(int r9, com.autonavi.bundle.entity.common.searchpoi.SearchPoi r10) {
        /*
            r8 = this;
            com.autonavi.map.search.fragment.SearchResultBasePage r0 = r8.f
            com.autonavi.map.core.MapManager r0 = r0.getMapManager()
            bty r0 = r0.getMapView()
            if (r0 != 0) goto L_0x000d
            return
        L_0x000d:
            com.autonavi.map.search.fragment.SearchResultBasePage r1 = r8.f
            cde r1 = r1.getSuspendManager()
            if (r1 != 0) goto L_0x0016
            return
        L_0x0016:
            bzh r2 = r8.D
            if (r2 == 0) goto L_0x01c2
            bxc r2 = r8.k
            if (r2 == 0) goto L_0x01c2
            bxc r2 = r8.k
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r2.a
            r3 = -1
            if (r2 == 0) goto L_0x0027
            r2 = r9
            goto L_0x0028
        L_0x0027:
            r2 = -1
        L_0x0028:
            if (r2 != r3) goto L_0x002b
            return
        L_0x002b:
            bzh r4 = r8.D
            int r4 = r4.i()
            if (r4 == r2) goto L_0x007b
            bzh r4 = r8.D
            r4.a(r2)
            boolean r2 = defpackage.elc.b
            if (r2 == 0) goto L_0x007b
            cdn r2 = r1.c()
            ami r2 = r2.a()
            if (r10 == 0) goto L_0x007b
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r4 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r4 = r10.as(r4)
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r4 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r4
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r4 = r4.getIndoorPoiInfo()
            if (r4 == 0) goto L_0x007b
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r4 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r4 = r10.as(r4)
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r4 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r4
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r4 = r4.getIndoorPoiInfo()
            int r4 = r4.floorNo
            if (r4 == 0) goto L_0x007b
            if (r2 == 0) goto L_0x007b
            cdn r1 = r1.c()
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r2 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r2 = r10.as(r2)
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r2 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r2
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r2 = r2.getIndoorPoiInfo()
            int r2 = r2.floorNo
            r1.b(r2)
        L_0x007b:
            r0.P()
            cce r0 = r8.l
            bxh r1 = r8.C
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r1 = r1.b
            aus r1 = r1.searchInfo
            bxf r2 = r8.p
            int r2 = r2.b
            bxf r4 = r8.p
            int r4 = r4.c
            r0.a(r1, r2, r4)
            bxh r0 = r8.C
            java.util.List<com.autonavi.common.model.POI> r0 = r0.c
            if (r0 == 0) goto L_0x01c1
            bxh r0 = r8.C
            java.util.List<com.autonavi.common.model.POI> r0 = r0.c
            int r0 = r0.size()
            if (r0 > 0) goto L_0x00a3
            goto L_0x01c1
        L_0x00a3:
            bxh r0 = r8.C     // Catch:{ Exception -> 0x01bc }
            auk r0 = r0.e()     // Catch:{ Exception -> 0x01bc }
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x011d
            boolean r4 = defpackage.cch.a(r10)     // Catch:{ Exception -> 0x01bc }
            r5 = 90000(0x15f90, float:1.26117E-40)
            if (r4 == 0) goto L_0x00e0
            cce r4 = r8.l     // Catch:{ Exception -> 0x01bc }
            int r0 = r0.o     // Catch:{ Exception -> 0x01bc }
            bty r6 = r4.c     // Catch:{ Exception -> 0x01bc }
            java.util.List<com.autonavi.common.model.POI> r7 = r4.b     // Catch:{ Exception -> 0x01bc }
            boolean r6 = defpackage.cce.a(r6, r7)     // Catch:{ Exception -> 0x01bc }
            if (r6 == 0) goto L_0x011d
            bty r6 = r4.c     // Catch:{ Exception -> 0x01bc }
            r6.b(r5, r2)     // Catch:{ Exception -> 0x01bc }
            bty r6 = r4.c     // Catch:{ Exception -> 0x01bc }
            r6.b(r5, r1)     // Catch:{ Exception -> 0x01bc }
            java.util.List<com.autonavi.common.model.POI> r6 = r4.b     // Catch:{ Exception -> 0x01bc }
            com.autonavi.jni.ae.gmap.scenic.Label3rd[] r0 = defpackage.cce.b(r6, r5, r0)     // Catch:{ Exception -> 0x01bc }
            r6 = r0[r9]     // Catch:{ Exception -> 0x01bc }
            java.lang.String r7 = ""
            r6.mLabelName = r7     // Catch:{ Exception -> 0x01bc }
            bty r4 = r4.c     // Catch:{ Exception -> 0x01bc }
            defpackage.cce.a(r4, r0, r5)     // Catch:{ Exception -> 0x01bc }
            goto L_0x011d
        L_0x00e0:
            bxh r4 = r8.C     // Catch:{ Exception -> 0x01bc }
            bzh r6 = r8.D     // Catch:{ Exception -> 0x01bc }
            if (r4 != 0) goto L_0x00e8
        L_0x00e6:
            r4 = 0
            goto L_0x010d
        L_0x00e8:
            if (r6 != 0) goto L_0x00eb
            goto L_0x00e6
        L_0x00eb:
            com.autonavi.map.search.overlay.SearchPoiOverlay r6 = r6.f     // Catch:{ Exception -> 0x01bc }
            boolean r7 = r6 instanceof com.autonavi.map.search.overlay.NormalSearchPoiOverlay     // Catch:{ Exception -> 0x01bc }
            if (r7 == 0) goto L_0x00e6
            com.autonavi.map.search.overlay.NormalSearchPoiOverlay r6 = (com.autonavi.map.search.overlay.NormalSearchPoiOverlay) r6     // Catch:{ Exception -> 0x01bc }
            int r6 = r6.getLastFocusIndex()     // Catch:{ Exception -> 0x01bc }
            if (r3 != r6) goto L_0x00fa
            goto L_0x00e6
        L_0x00fa:
            com.autonavi.common.model.POI r4 = r4.b(r6)     // Catch:{ Exception -> 0x01bc }
            if (r4 != 0) goto L_0x0101
            goto L_0x00e6
        L_0x0101:
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r6 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r4 = r4.as(r6)     // Catch:{ Exception -> 0x01bc }
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r4 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r4     // Catch:{ Exception -> 0x01bc }
            boolean r4 = defpackage.cch.a(r4)     // Catch:{ Exception -> 0x01bc }
        L_0x010d:
            if (r4 == 0) goto L_0x0116
            cce r4 = r8.l     // Catch:{ Exception -> 0x01bc }
            int r6 = r0.o     // Catch:{ Exception -> 0x01bc }
            r4.a(r6)     // Catch:{ Exception -> 0x01bc }
        L_0x0116:
            cce r4 = r8.l     // Catch:{ Exception -> 0x01bc }
            int r0 = r0.o     // Catch:{ Exception -> 0x01bc }
            r4.a(r10, r5, r0)     // Catch:{ Exception -> 0x01bc }
        L_0x011d:
            if (r9 == r3) goto L_0x0123
            if (r10 != 0) goto L_0x0122
            goto L_0x0123
        L_0x0122:
            r1 = 0
        L_0x0123:
            bzh r0 = r8.D     // Catch:{ Exception -> 0x01bc }
            r0.a(r1, r9)     // Catch:{ Exception -> 0x01bc }
            bxf r0 = r8.p     // Catch:{ Exception -> 0x01bc }
            int r0 = r0.c     // Catch:{ Exception -> 0x01bc }
            if (r0 != r3) goto L_0x01bb
            if (r9 == r3) goto L_0x01bb
            boolean r0 = defpackage.elc.b     // Catch:{ Exception -> 0x01bc }
            if (r0 == 0) goto L_0x01bb
            com.autonavi.map.search.fragment.SearchResultBasePage r0 = r8.f     // Catch:{ Exception -> 0x01bc }
            cde r0 = r0.getSuspendManager()     // Catch:{ Exception -> 0x01bc }
            if (r0 == 0) goto L_0x0145
            cdn r1 = r0.c()     // Catch:{ Exception -> 0x01bc }
            ami r1 = r1.a()     // Catch:{ Exception -> 0x01bc }
            goto L_0x0146
        L_0x0145:
            r1 = 0
        L_0x0146:
            if (r10 == 0) goto L_0x018c
            if (r1 == 0) goto L_0x018c
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r2 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r2 = r10.as(r2)     // Catch:{ Exception -> 0x01bc }
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r2 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r2     // Catch:{ Exception -> 0x01bc }
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r2 = r2.getIndoorPoiInfo()     // Catch:{ Exception -> 0x01bc }
            if (r2 == 0) goto L_0x018c
            java.lang.String r2 = r1.e     // Catch:{ Exception -> 0x01bc }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x01bc }
            if (r2 != 0) goto L_0x018c
            java.lang.String r2 = r1.e     // Catch:{ Exception -> 0x01bc }
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r3 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r3 = r10.as(r3)     // Catch:{ Exception -> 0x01bc }
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r3 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r3     // Catch:{ Exception -> 0x01bc }
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r3 = r3.getIndoorPoiInfo()     // Catch:{ Exception -> 0x01bc }
            java.lang.String r3 = r3.parentId     // Catch:{ Exception -> 0x01bc }
            boolean r2 = r2.equals(r3)     // Catch:{ Exception -> 0x01bc }
            if (r2 != 0) goto L_0x018c
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r2 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r2 = r10.as(r2)     // Catch:{ Exception -> 0x01bc }
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r2 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r2     // Catch:{ Exception -> 0x01bc }
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r2 = r2.getIndoorPoiInfo()     // Catch:{ Exception -> 0x01bc }
            int r2 = r2.floorNo     // Catch:{ Exception -> 0x01bc }
            if (r2 == 0) goto L_0x018c
            bzh r10 = r8.D     // Catch:{ Exception -> 0x01bc }
            r10.a(r9)     // Catch:{ Exception -> 0x01bc }
            return
        L_0x018c:
            if (r10 == 0) goto L_0x01bb
            if (r1 == 0) goto L_0x01bb
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r9 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r9 = r10.as(r9)     // Catch:{ Exception -> 0x01bc }
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r9 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r9     // Catch:{ Exception -> 0x01bc }
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r9 = r9.getIndoorPoiInfo()     // Catch:{ Exception -> 0x01bc }
            if (r9 == 0) goto L_0x01bb
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r9 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r9 = r10.as(r9)     // Catch:{ Exception -> 0x01bc }
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r9 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r9     // Catch:{ Exception -> 0x01bc }
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r9 = r9.getIndoorPoiInfo()     // Catch:{ Exception -> 0x01bc }
            int r9 = r9.floorNo     // Catch:{ Exception -> 0x01bc }
            if (r9 == 0) goto L_0x01bb
            cdn r9 = r0.c()     // Catch:{ Exception -> 0x01bc }
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r10 = r10.getIndoorPoiInfo()     // Catch:{ Exception -> 0x01bc }
            int r10 = r10.floorNo     // Catch:{ Exception -> 0x01bc }
            r9.b(r10)     // Catch:{ Exception -> 0x01bc }
        L_0x01bb:
            return
        L_0x01bc:
            r9 = move-exception
            defpackage.kf.a(r9)
            goto L_0x01c2
        L_0x01c1:
            return
        L_0x01c2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bxo.a(int, com.autonavi.bundle.entity.common.searchpoi.SearchPoi):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:115:0x0187 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0171  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x021f  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x0235 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0236  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onLabelClick(java.util.List<defpackage.als> r10) {
        /*
            r9 = this;
            if (r10 == 0) goto L_0x0292
            boolean r0 = r10.isEmpty()
            if (r0 == 0) goto L_0x000a
            goto L_0x0292
        L_0x000a:
            r0 = 0
            java.lang.Object r1 = r10.get(r0)
            als r1 = (defpackage.als) r1
            if (r1 != 0) goto L_0x0018
            boolean r10 = super.onLabelClick(r10)
            return r10
        L_0x0018:
            int r2 = r1.i
            r3 = -1
            switch(r2) {
                case 2: goto L_0x00ef;
                case 3: goto L_0x0047;
                case 90000: goto L_0x0042;
                case 9000003: goto L_0x002b;
                case 9000004: goto L_0x002b;
                default: goto L_0x001e;
            }
        L_0x001e:
            com.autonavi.common.model.POI r2 = r9.ad()
            bxf r4 = r9.p
            r5 = 1
            if (r1 == 0) goto L_0x0151
            if (r2 != 0) goto L_0x00fb
            goto L_0x0151
        L_0x002b:
            com.autonavi.common.model.GeoPoint r2 = new com.autonavi.common.model.GeoPoint
            int r3 = r1.e
            int r1 = r1.f
            r2.<init>(r3, r1)
            r9.a(r2, r0)
            bzs r0 = r9.F
            r1 = 6
            r0.b(r1)
            boolean r10 = super.onLabelClick(r10)
            return r10
        L_0x0042:
            r9.a(r10)
            goto L_0x028b
        L_0x0047:
            bxh r0 = r9.C
            if (r0 == 0) goto L_0x00ea
            java.lang.String r0 = r1.b
            bxh r2 = r9.C
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r2.b
            boolean r2 = defpackage.bcy.b(r2)
            if (r2 == 0) goto L_0x00c6
            bxh r2 = r9.C
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r2.b
            aus r2 = r2.searchInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r2 = r2.l
            java.util.Iterator r2 = r2.iterator()
        L_0x0063:
            boolean r4 = r2.hasNext()
            if (r4 == 0) goto L_0x00c6
            java.lang.Object r4 = r2.next()
            com.autonavi.common.model.POI r4 = (com.autonavi.common.model.POI) r4
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r5 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r4 = r4.as(r5)
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r4 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r4
            if (r4 == 0) goto L_0x0063
            aum r4 = r4.getRecommendMode()
            if (r4 == 0) goto L_0x0063
            java.util.List<aut> r5 = r4.b
            if (r5 == 0) goto L_0x0063
            java.util.List<aut> r5 = r4.b
            java.util.Iterator r5 = r5.iterator()
        L_0x0089:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x0063
            java.lang.Object r6 = r5.next()
            aut r6 = (defpackage.aut) r6
            java.lang.String r6 = r6.d
            boolean r6 = r0.equals(r6)
            if (r6 == 0) goto L_0x0089
            java.lang.String r1 = r4.a
            org.json.JSONObject r2 = new org.json.JSONObject
            r2.<init>()
            java.lang.String r4 = "poiId"
            r2.put(r4, r1)     // Catch:{ JSONException -> 0x00ba }
            java.lang.String r1 = "from"
            r2.put(r1, r0)     // Catch:{ JSONException -> 0x00ba }
            java.lang.String r0 = "text"
            bxh r1 = r9.C     // Catch:{ JSONException -> 0x00ba }
            java.lang.String r1 = r1.h()     // Catch:{ JSONException -> 0x00ba }
            r2.put(r0, r1)     // Catch:{ JSONException -> 0x00ba }
            goto L_0x00be
        L_0x00ba:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00be:
            java.lang.String r0 = "P00007"
            java.lang.String r1 = "B030"
            com.amap.bundle.statistics.LogManager.actionLogV2(r0, r1, r2)
            goto L_0x00ea
        L_0x00c6:
            org.json.JSONObject r2 = new org.json.JSONObject
            r2.<init>()
            java.lang.String r4 = "poiId"
            java.lang.String r5 = r1.b     // Catch:{ JSONException -> 0x00e6 }
            r2.put(r4, r5)     // Catch:{ JSONException -> 0x00e6 }
            java.lang.String r4 = "poiName"
            java.lang.String r1 = r1.a     // Catch:{ JSONException -> 0x00e6 }
            r2.put(r4, r1)     // Catch:{ JSONException -> 0x00e6 }
            java.lang.String r1 = "from"
            r2.put(r1, r0)     // Catch:{ JSONException -> 0x00e6 }
            java.lang.String r0 = "P00007"
            java.lang.String r1 = "B067"
            com.amap.bundle.statistics.LogManager.actionLogV2(r0, r1, r2)     // Catch:{ JSONException -> 0x00e6 }
            goto L_0x00ea
        L_0x00e6:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00ea:
            r9.Y()
            goto L_0x028b
        L_0x00ef:
            r9.a(r1)
            com.autonavi.common.model.POI r0 = r9.ad()
            r9.a(r10, r0)
            goto L_0x028b
        L_0x00fb:
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r6 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r2 = r2.as(r6)
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r2 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r2
            if (r2 == 0) goto L_0x0151
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r6 = r2.getPoiChildrenInfo()
            if (r6 == 0) goto L_0x0151
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r6 = r2.getPoiChildrenInfo()
            java.util.Collection<? extends com.autonavi.common.model.POI> r6 = r6.poiList
            if (r6 == 0) goto L_0x0151
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r6 = r2.getPoiChildrenInfo()
            java.util.Collection<? extends com.autonavi.common.model.POI> r6 = r6.poiList
            int r6 = r6.size()
            if (r6 <= 0) goto L_0x0151
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r2 = r2.getPoiChildrenInfo()
            java.util.Collection<? extends com.autonavi.common.model.POI> r2 = r2.poiList
            r6.addAll(r2)
            if (r4 != 0) goto L_0x0131
            r2 = -1
            goto L_0x0133
        L_0x0131:
            int r2 = r4.c
        L_0x0133:
            if (r2 < 0) goto L_0x0151
            int r4 = r6.size()
            if (r2 >= r4) goto L_0x0151
            java.lang.Object r2 = r6.get(r2)
            com.autonavi.common.model.POI r2 = (com.autonavi.common.model.POI) r2
            if (r2 == 0) goto L_0x0151
            java.lang.String r2 = r2.getId()
            java.lang.String r4 = r1.b
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x0151
            r2 = 1
            goto L_0x0152
        L_0x0151:
            r2 = 0
        L_0x0152:
            com.autonavi.common.model.POI r4 = r9.ad()
            if (r1 == 0) goto L_0x0166
            if (r4 != 0) goto L_0x015b
            goto L_0x0166
        L_0x015b:
            java.lang.String r4 = r4.getId()
            java.lang.String r6 = r1.b
            boolean r4 = r4.equals(r6)
            goto L_0x0167
        L_0x0166:
            r4 = 0
        L_0x0167:
            java.util.Iterator r6 = r10.iterator()
        L_0x016b:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x0187
            java.lang.Object r7 = r6.next()
            als r7 = (defpackage.als) r7
            if (r7 == 0) goto L_0x016b
            java.lang.String r8 = r7.a
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            if (r8 == 0) goto L_0x016b
            boolean r7 = r7.j
            if (r7 == 0) goto L_0x016b
            r6 = 0
            goto L_0x0188
        L_0x0187:
            r6 = 1
        L_0x0188:
            if (r6 == 0) goto L_0x01a8
            if (r2 != 0) goto L_0x01a8
            if (r4 != 0) goto L_0x01a8
            r9.Y()
            android.graphics.Point r2 = r9.ao()
            r9.m = r2
            bzs r2 = r9.F
            r4 = 3
            r2.b(r4)
            java.lang.String r2 = "P00007"
            java.lang.String r4 = "B072"
            org.json.JSONObject r6 = r9.al()
            com.amap.bundle.statistics.LogManager.actionLogV2(r2, r4, r6)
        L_0x01a8:
            java.lang.String r2 = r1.a
            com.autonavi.common.model.GeoPoint r4 = new com.autonavi.common.model.GeoPoint
            int r6 = r1.e
            int r7 = r1.f
            r4.<init>(r6, r7)
            com.autonavi.common.model.POI r2 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r2, r4)
            java.lang.String r4 = r1.b
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x01ce
            java.lang.String r4 = "0"
            java.lang.String r6 = r1.b
            boolean r4 = r4.equals(r6)
            if (r4 != 0) goto L_0x01ce
            java.lang.String r1 = r1.b
            r2.setId(r1)
        L_0x01ce:
            java.lang.String r1 = r2.getName()
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.util.HashMap r6 = new java.util.HashMap
            r6.<init>()
            com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate r7 = new com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate
            r7.<init>()
            r8 = r7
            com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate r8 = (com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate) r8
            r8.setValue(r1)
            r1 = 2001(0x7d1, float:2.804E-42)
            r7.setId(r1)
            java.lang.String r1 = "text"
            r7.setType(r1)
            int r1 = r7.getId()
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r6.put(r1, r7)
            r4.add(r7)
            java.lang.Class<com.autonavi.minimap.search.model.searchpoi.ISearchPoiData> r1 = com.autonavi.minimap.search.model.searchpoi.ISearchPoiData.class
            com.autonavi.common.model.POI r1 = r2.as(r1)
            com.autonavi.minimap.search.model.searchpoi.ISearchPoiData r1 = (com.autonavi.minimap.search.model.searchpoi.ISearchPoiData) r1
            r1.setTemplateDataMap(r6)
            java.lang.Class<com.autonavi.minimap.search.model.searchpoi.ISearchPoiData> r1 = com.autonavi.minimap.search.model.searchpoi.ISearchPoiData.class
            com.autonavi.common.model.POI r1 = r2.as(r1)
            com.autonavi.minimap.search.model.searchpoi.ISearchPoiData r1 = (com.autonavi.minimap.search.model.searchpoi.ISearchPoiData) r1
            r1.setTemplateData(r4)
            java.lang.Class<com.autonavi.map.fragmentcontainer.page.IPoiTipViewService> r1 = com.autonavi.map.fragmentcontainer.page.IPoiTipViewService.class
            java.lang.Object r1 = defpackage.ank.a(r1)
            com.autonavi.map.fragmentcontainer.page.IPoiTipViewService r1 = (com.autonavi.map.fragmentcontainer.page.IPoiTipViewService) r1
            if (r1 == 0) goto L_0x0231
            com.autonavi.map.search.fragment.SearchResultBasePage r4 = r9.f
            com.autonavi.map.fragmentcontainer.page.dialog.TipContainer r4 = r4.getBottomTipsContainer()
            android.view.ViewGroup r4 = r4.getContainer()
            com.autonavi.map.search.fragment.SearchResultBasePage r6 = r9.f
            ely r4 = r1.createPoiTipView(r4, r6, r2)
            r9.ad = r4
        L_0x0231:
            ely r4 = r9.ad
            if (r4 != 0) goto L_0x0236
            return r0
        L_0x0236:
            ely r4 = r9.ad
            r4.setSingle(r5)
            ely r4 = r9.ad
            java.lang.String r6 = "mainmap"
            r4.setFromSource(r6)
            ely r4 = r9.ad
            r4.adjustMargin()
            ely r4 = r9.ad
            boolean r4 = r4 instanceof defpackage.ely
            if (r4 == 0) goto L_0x0256
            ely r4 = r9.ad
            ely$a r1 = r1.createPoiTipEvent(r0)
            r4.setTipItemEvent(r1)
        L_0x0256:
            ely r1 = r9.ad
            com.autonavi.map.search.fragment.SearchResultBasePage r4 = r9.f
            android.app.Activity r4 = r4.getActivity()
            boolean r4 = defpackage.ags.b(r4)
            r1.refreshByScreenState(r4)
            ely r1 = r9.ad
            r4 = 0
            r6 = 2
            r1.initData(r4, r2, r6)
            ely r1 = r9.ad
            com.autonavi.common.PageBundle r1 = a(r2, r1)
            java.lang.String r2 = "key_tip_request_type"
            r1.putInt(r2, r5)
            java.lang.String r2 = "key_tip_type"
            r1.putInt(r2, r0)
            java.lang.String r0 = "key_subway_activeid"
            java.lang.String r2 = r9.ae
            r1.putString(r0, r2)
            bxf r0 = new bxf
            r0.<init>()
            r9.a(r0, r1)
        L_0x028b:
            r9.ah = r3
            boolean r10 = super.onLabelClick(r10)
            return r10
        L_0x0292:
            boolean r10 = super.onLabelClick(r10)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bxo.onLabelClick(java.util.List):boolean");
    }

    public void onSelectSubWayActive(List<Long> list) {
        super.onSelectSubWayActive(list);
        StringBuilder sb = new StringBuilder();
        if (list != null && !list.isEmpty()) {
            for (Long l2 : list) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(l2.toString());
                sb2.append(",");
                sb.append(sb2.toString());
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        this.ae = sb.toString();
    }

    private void a(als als) {
        POI ad2 = ad();
        if (ad2 != null && ad2.getId() != null) {
            SearchPoi searchPoi = (SearchPoi) ad2.as(SearchPoi.class);
            Map templateDataMap = searchPoi.getTemplateDataMap();
            SearchPoi searchPoi2 = null;
            ArrayList arrayList = (ArrayList) searchPoi.getPoiChildrenInfo().poiList;
            if (searchPoi.getPoiChildrenInfo() != null && arrayList != null) {
                Iterator it = arrayList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    SearchPoi searchPoi3 = (SearchPoi) it.next();
                    if (searchPoi3.getId().equals(als.b)) {
                        searchPoi2 = searchPoi3;
                        break;
                    }
                }
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("text", bcy.k(this.C.b));
                jSONObject.put(TrafficUtil.POIID, als.b);
                jSONObject.put("from", ad2.getId());
                if (!(templateDataMap == null || templateDataMap.get(Integer.valueOf(2015)) == null || searchPoi2 == null || searchPoi2.getPoiExtra() == null || searchPoi2.getPoiExtra().get("poi_deep_info") == null || ((ArrayList) searchPoi2.getPoiExtra().get("poi_deep_info")).size() <= 0)) {
                    jSONObject.put("deep", 2015);
                    LogManager.actionLogV2(LogConstant.SEARCH_RESULT_MAP, LogConstant.MAIN_MAP_TIP, jSONObject);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            LogManager.actionLogV2(LogConstant.SEARCH_RESULT_MAP, "B031", jSONObject);
        }
    }

    private JSONObject al() {
        SlideState j2 = this.F.j();
        String str = "";
        if (j2 == null) {
            return null;
        }
        switch (j2) {
            case ANCHORED:
                str = this.f.getResources().getString(R.string.anchored);
                break;
            case COLLAPSED:
                str = this.f.getResources().getString(R.string.collapsed);
                break;
            case LOWERANCHORED:
                str = this.f.getResources().getString(R.string.loweranchored);
                break;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status", str);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    private void a(List<als> list, POI poi) {
        if (list != null && list.size() != 0) {
            int i2 = 0;
            als als = list.get(0);
            if (als != null) {
                if ((2 == als.i || als.j) && poi != null) {
                    SearchPoi searchPoi = (SearchPoi) poi.as(SearchPoi.class);
                    if (!(searchPoi == null || searchPoi.getPoiChildrenInfo() == null || searchPoi.getPoiChildrenInfo().poiList == null)) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.addAll(searchPoi.getPoiChildrenInfo().poiList);
                        int size = arrayList.size();
                        POI poi2 = null;
                        int i3 = 0;
                        while (true) {
                            if (i3 >= size) {
                                break;
                            }
                            POI poi3 = (POI) arrayList.get(i3);
                            if (poi3 != null && poi3.getId().equals(als.b)) {
                                i2 = i3;
                                poi2 = poi3;
                                break;
                            }
                            i3++;
                        }
                        if (i2 != this.p.c) {
                            e(i2);
                            if (this.l != null) {
                                this.l.a(poi2, 2, this.C.e().o);
                            }
                        }
                    }
                }
            }
        }
    }

    private void a(List<als> list) {
        if (list != null && list.size() != 0) {
            int i2 = 0;
            als als = list.get(0);
            if (als != null) {
                if ((90000 == als.i || als.j) && this.C.c != null) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.addAll(this.C.c);
                    int size = arrayList.size();
                    int i3 = 0;
                    while (true) {
                        if (i3 >= size) {
                            break;
                        }
                        POI poi = (POI) arrayList.get(i3);
                        if (poi != null && poi.getId().equals(als.b)) {
                            i2 = i3;
                            break;
                        }
                        i3++;
                    }
                    d(i2);
                    j(i2);
                }
            }
        }
    }

    public void C() {
        if (this.C != null) {
            aey.a(false);
            LogManager.actionLogV2(LogConstant.SEARCH_RESULT_MAP, "B004");
            new PageBundle();
            if (!elc.c && this.i == 2) {
                bbi bbi = (bbi) defpackage.esb.a.a.a(bbi.class);
                if (bbi != null) {
                    bbi.a().a(this.C.b()).b((String) "220000").a(this.C.k).a().b().a(this.d).a(this.f);
                    this.f.finish();
                }
            } else if (this.i == 1) {
                bbi bbi2 = (bbi) defpackage.esb.a.a.a(bbi.class);
                if (bbi2 != null) {
                    boolean z2 = this.f.getArguments().getBoolean("is_cache", false);
                    boolean z3 = this.f.getArguments().getBoolean("key_can_search_with_hint", false);
                    boolean z4 = this.f.getArguments().getBoolean("is_from_new_nearby", false);
                    bbi2.a().a(this.C.b()).b((String) "620000").c(z4).b(z2).a(z3).c(this.f.getArguments().getString("hint_content", "")).a(this.f);
                    this.f.finish();
                }
            } else {
                elc.c = false;
                bcb bcb = (bcb) defpackage.esb.a.a.a(bcb.class);
                if (bcb != null) {
                    bcb.a().c(this.C.b()).a(this.f);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void Y() {
        if (this.D != null) {
            this.D.e();
        }
        if (this.l != null) {
            this.l.a();
            this.l.b(2);
        }
        if (this.p != null) {
            this.p.b();
        }
        this.I.removeMessages(5);
        am();
    }

    public boolean onMapMotionStop() {
        Z();
        if (!byz.e) {
            this.D.a(this.C.b);
        }
        T();
        return super.onMapMotionStop();
    }

    public boolean onMapTouchEvent(MotionEvent motionEvent) {
        return super.onMapTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public final void Z() {
        if (this.F != null && !this.F.t()) {
            this.I.postDelayed(new Runnable() {
                public final void run() {
                    bty mapView = bxo.this.f.getMapManager().getMapView();
                    if (mapView != null) {
                        if (mapView.p() != bxo.this.Y || mapView.q() != bxo.this.Z) {
                            bxo.this.Y = mapView.p();
                            bxo.this.Z = mapView.q();
                            if (!(bxo.this.Y == bxo.this.K && bxo.this.Z == bxo.this.L)) {
                                bxo.this.F.d(true);
                                bxo.e(false);
                            }
                        }
                    }
                }
            }, 50);
        }
    }

    public void g(int i2) {
        if (!this.T) {
            bxv.a((AbstractBaseMapPage) this.f);
        }
        if (this.C.b != null && this.p != null) {
            POI b2 = this.C.b(i2);
            if (b2 != null) {
                SearchPoi searchPoi = (SearchPoi) b2.as(SearchPoi.class);
                if (searchPoi != null) {
                    if (cch.a(searchPoi)) {
                        if (this.a) {
                            this.a = false;
                        } else {
                            LogManager.actionLogV25("P00360", "B009", new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, searchPoi.getId()));
                        }
                    }
                    a((POI) searchPoi);
                    a(i2, searchPoi);
                    this.D.b(i2);
                    this.D.a(this.l, i2, this.C);
                    d((POI) searchPoi);
                    if (searchPoi != null) {
                        if (!cch.a(this.C.e())) {
                            if ("citycard".equals(searchPoi.getIndustry())) {
                                this.D.a((POI) searchPoi, true);
                            } else {
                                this.D.a((POI) searchPoi, false);
                            }
                        }
                        b((POI) searchPoi);
                    }
                    a(searchPoi);
                    this.T = false;
                    T();
                    SearchPoiOverlay searchPoiOverlay = this.D.f;
                    if (searchPoiOverlay instanceof NormalSearchPoiOverlay) {
                        NormalSearchPoiOverlay normalSearchPoiOverlay = (NormalSearchPoiOverlay) searchPoiOverlay;
                        if (i2 != normalSearchPoiOverlay.getLastFocusIndex()) {
                            normalSearchPoiOverlay.setLastFocusIndex(i2);
                        }
                    }
                }
            }
        }
    }

    public final void a(SearchPoi searchPoi) {
        if (this.p == null || this.o) {
            return;
        }
        if (this.p.c == -1) {
            b(searchPoi);
        } else if (!this.E.isEmpty() && this.E.size() >= this.p.c) {
            int i2 = this.p.c;
            if (i2 >= 0 && i2 < this.E.size()) {
                c(this.E.get(i2));
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0063  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00e1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b(com.autonavi.bundle.entity.common.searchpoi.SearchPoi r10) {
        /*
            r9 = this;
            com.autonavi.map.search.fragment.SearchResultBasePage r0 = r9.f
            com.autonavi.map.core.MapManager r0 = r0.getMapManager()
            bty r0 = r0.getMapView()
            if (r10 == 0) goto L_0x00ea
            if (r0 != 0) goto L_0x0010
            goto L_0x00ea
        L_0x0010:
            com.autonavi.common.model.GeoPoint r0 = r10.getPoint()
            bzh r1 = r9.D
            boolean r1 = r1.a(r10)
            r7 = 0
            r8 = 1
            if (r1 != 0) goto L_0x005a
            com.autonavi.map.search.fragment.SearchResultBasePage r1 = r9.f
            bid r1 = r1.getPageContext()
            com.autonavi.map.search.fragment.SearchResultBasePage r2 = r9.f
            android.content.Context r2 = r2.getContext()
            android.content.Context r2 = r2.getApplicationContext()
            r3 = 1116471296(0x428c0000, float:70.0)
            int r2 = defpackage.agn.a(r2, r3)
            int r2 = r2 + 20
            com.autonavi.map.search.fragment.SearchResultBasePage r3 = r9.f
            android.content.Context r3 = r3.getContext()
            android.content.Context r3 = r3.getApplicationContext()
            r4 = 1101004800(0x41a00000, float:20.0)
            int r3 = defpackage.agn.a(r3, r4)
            int r3 = r3 + r2
            bzs r2 = r9.F
            int r4 = r2.u()
            r5 = 120(0x78, float:1.68E-43)
            r6 = 100
            r2 = r0
            boolean r1 = defpackage.cfe.a(r1, r2, r3, r4, r5, r6)
            if (r1 != 0) goto L_0x005a
            r1 = 1
            goto L_0x005b
        L_0x005a:
            r1 = 0
        L_0x005b:
            com.autonavi.map.search.fragment.SearchResultBasePage r2 = r9.f
            cde r2 = r2.getSuspendManager()
            if (r2 == 0) goto L_0x006c
            cdn r2 = r2.c()
            ami r2 = r2.a()
            goto L_0x006d
        L_0x006c:
            r2 = 0
        L_0x006d:
            if (r2 == 0) goto L_0x00b0
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r3 = r10.getIndoorPoiInfo()
            if (r3 == 0) goto L_0x00b0
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r3 = r10.getIndoorPoiInfo()
            if (r3 == 0) goto L_0x00b0
            java.lang.String r3 = r2.e
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x00b0
            java.lang.String r2 = r2.e
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r3 = r10.getIndoorPoiInfo()
            java.lang.String r3 = r3.parentId
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x00b0
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r2 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r2 = r10.as(r2)
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r2 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r2
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r2 = r2.getIndoorPoiInfo()
            if (r2 == 0) goto L_0x00b0
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r2 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r2 = r10.as(r2)
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r2 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r2
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r2 = r2.getIndoorPoiInfo()
            int r2 = r2.floorNo
            if (r2 == 0) goto L_0x00b0
            r1 = 1
        L_0x00b0:
            java.lang.String r2 = r10.getType()
            if (r2 == 0) goto L_0x00ce
            java.lang.String r2 = r10.getType()
            java.lang.String r3 = "15090"
            boolean r2 = r2.startsWith(r3)
            if (r2 == 0) goto L_0x00ce
            bzh r1 = r9.D
            r10.getPoint()
            r1.g()
            r9.a(r0, r7)
            return
        L_0x00ce:
            bzs r2 = r9.F
            boolean r2 = r2.x()
            if (r2 == 0) goto L_0x00d7
            r1 = 1
        L_0x00d7:
            java.lang.String r10 = r10.getId()
            boolean r10 = android.text.TextUtils.isEmpty(r10)
            if (r10 == 0) goto L_0x00e2
            r1 = 1
        L_0x00e2:
            if (r1 == 0) goto L_0x00e9
            if (r0 == 0) goto L_0x00e9
            r9.a(r0, r8)
        L_0x00e9:
            return
        L_0x00ea:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bxo.b(com.autonavi.bundle.entity.common.searchpoi.SearchPoi):void");
    }

    private void c(POI poi) {
        if (this.f.getMapManager().getMapView() != null && poi != null) {
            if (!cfe.a(this.f.getPageContext(), poi.getPoint(), agn.a(this.f.getContext().getApplicationContext(), 20.0f) + agn.a(this.f.getContext().getApplicationContext(), 70.0f) + 20, this.F.u(), MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_, 100)) {
                a((GLGeoPoint) poi.getPoint(), true);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void b(POI poi) {
        SearchPoi searchPoi = poi != null ? (SearchPoi) poi.as(SearchPoi.class) : null;
        bty mapView = this.f.getMapManager().getMapView();
        if (mapView != null) {
            if (searchPoi == null || !searchPoi.getType().equals("150500")) {
                mapView.E();
            } else {
                String str = (String) searchPoi.getPoiExtra().get("businfo_lineids");
                if (str != null) {
                    String[] split = str.split("\\|");
                    if (split.length > 0) {
                        mapView.a(split);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void b(Object obj) {
        if (this.f.getMapManager().getMapView() != null) {
            MapManager mapManager = this.f.getMapManager();
            if (mapManager != null) {
                mapManager.getOverlayManager().clearAllFocus();
            }
            bzx bzx = (bzx) obj;
            if (bzx != null && bzx.getPOI() != null) {
                a(bzx);
                if (this.f.getMapManager().getMapView() != null) {
                    this.f.getMapView().Y();
                }
                bty mapView = this.f.getMapManager().getMapView();
                if (mapView != null) {
                    Point ao = ao();
                    mapView.a(300, (GLGeoPoint) bzx.getGeoPoint(), ao, true);
                    this.f.getMapView().Y();
                    this.m = ao;
                }
                this.D.a((PointOverlayItem) bzx, bzx.b);
                if (this.l != null) {
                    this.l.b(2);
                }
                SuperId.getInstance().setBit4("05");
                this.F.b(2);
                SlideState j2 = this.F.j();
                String str = "";
                if (j2 != null) {
                    switch (j2) {
                        case ANCHORED:
                            str = this.f.getResources().getString(R.string.anchored);
                            break;
                        case COLLAPSED:
                            str = this.f.getResources().getString(R.string.collapsed);
                            break;
                        case LOWERANCHORED:
                            str = this.f.getResources().getString(R.string.loweranchored);
                            break;
                    }
                } else {
                    str = ModulePoi.TIPS;
                }
                if (!TextUtils.isEmpty(str)) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("type", bzx.getPOI().getType());
                        jSONObject.put(TrafficUtil.POIID, bzx.getPOI().getId());
                        jSONObject.put("status", str);
                        jSONObject.put("text", bcy.k(this.C.b));
                        LogManager.actionLogV2(LogConstant.SEARCH_RESULT_MAP, LogConstant.MAIN_MAP_SHORTCUT_CREATE, jSONObject);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void aa() {
        this.n = true;
        if (this.C.d()) {
            this.D.o();
        }
    }

    /* access modifiers changed from: protected */
    public final void ab() {
        c(true);
        MapManager mapManager = this.f.getMapManager();
        if (mapManager != null) {
            IOverlayManager overlayManager = mapManager.getOverlayManager();
            if (overlayManager != null) {
                overlayManager.clearAllFocus();
            }
        }
    }

    private void d(POI poi) {
        bty mapView = this.f.getMapManager().getMapView();
        if (mapView != null) {
            int i2 = this.p == null ? -1 : this.p.b;
            if (!(this.p == null || i2 == this.ah)) {
                this.ag = true;
                this.ai = true;
            }
            if (poi == null) {
                if (!(this.W == null || this.W.a == null)) {
                    this.W.a();
                }
            } else if (bxa.a(poi) != 1) {
                if (!(this.W == null || this.W.a == null)) {
                    this.W.a();
                }
            } else {
                final int a2 = bxa.a(poi);
                if (this.ag) {
                    if (this.ai) {
                        this.I.removeMessages(5);
                        if (a2 == 1) {
                            Message obtain = Message.obtain();
                            obtain.what = 5;
                            this.I.sendMessageDelayed(obtain, 10000);
                            this.ai = false;
                            LogManager.actionLogV2(LogConstant.SEARCH_RESULT_MAP, "B045");
                        }
                    }
                    this.ah = i2;
                    if (this.V == null) {
                        this.V = new SearchUGCTipOverlay(mapView);
                        this.f.addOverlay(this.V);
                    }
                    if (this.W == null) {
                        this.W = new bxa(this.f.getContext(), this.V, new defpackage.bxa.a() {
                            public final void a(POI poi) {
                                if (a2 == 1) {
                                    Message obtain = Message.obtain();
                                    obtain.what = 6;
                                    obtain.obj = (String) poi.getPoiExtra().get("tra_action_param");
                                    obtain.arg1 = 1;
                                    bxo.this.I.sendMessage(obtain);
                                    LogManager.actionLogV2(LogConstant.SEARCH_RESULT_MAP, LogConstant.MAIN_MAP_INVIEW_SEARCH);
                                }
                            }
                        }, this.f.getMapManager().getMapView());
                    }
                    this.W.a(poi, a2);
                    if (this.W.c != null && !cfe.a(this.f.getPageContext(), poi.getPoint(), this.f.getResources().getDimensionPixelOffset(R.dimen.v3_bottom_bar_height), this.f.getResources().getDimensionPixelOffset(R.dimen.search_result_map_bottom), this.W.c.getMeasuredWidth(), this.W.c.getMeasuredHeight())) {
                        a((GLGeoPoint) poi.getPoint(), true);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void am() {
        if (this.W != null) {
            this.ag = false;
            this.W.a();
        }
    }

    public final void a(int i2, boolean z2) {
        if (i2 == 8) {
            i2 = 4;
        }
        if (this.x != null) {
            this.x.setVisibility(i2);
        }
        if (this.u != null) {
            this.u.setVisibility(i2);
        }
        if (this.v != null) {
            this.v.setVisibility(i2);
        }
        if (this.z != null) {
            this.z.setVisibility(i2);
        }
        if (z2 && this.w != null) {
            this.w.setVisibility(i2);
        }
        this.f.getMapSuspendBtnView2().setVisibility(0);
        h(i2);
    }

    public final void b(int i2) {
        a(i2, true);
    }

    public final void a(boolean z2) {
        int i2 = 4;
        if (this.w != null) {
            this.w.setVisibility(z2 ? 0 : 4);
        }
        if (this.v != null) {
            View view = this.v;
            if (z2) {
                i2 = 0;
            }
            view.setVisibility(i2);
        }
    }

    /* access modifiers changed from: private */
    public void h(int i2) {
        if (this.s != null && this.t != null) {
            this.X = i2;
            if (an()) {
                i2 = 4;
            }
            this.s.setVisibility(i2);
            this.t.setVisibility(i2);
            if (i2 != 0) {
                this.q.u();
            }
        }
    }

    public final void a_(int i2) {
        ViewParent parent = this.y.getParent();
        if (parent != null) {
            ((View) parent).setVisibility(i2);
        }
    }

    public final void c(int i2) {
        if (this.x != null) {
            ViewParent parent = this.x.getParent();
            if (parent != null) {
                ((View) parent).setVisibility(i2);
            }
        }
    }

    private boolean an() {
        cde suspendManager = this.f.getSuspendManager();
        if (suspendManager == null) {
            return false;
        }
        cdn c2 = suspendManager.c();
        if (c2 == null) {
            return false;
        }
        return c2.c();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x02c3  */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x0324  */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x032e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void ac() {
        /*
            r27 = this;
            r0 = r27
            bxh r1 = r0.C
            auk r1 = r1.e()
            if (r1 != 0) goto L_0x000b
            return
        L_0x000b:
            bxh r1 = r0.C
            auk r1 = r1.e()
            auv r1 = r1.M
            r3 = 0
            if (r1 != 0) goto L_0x0360
            bxh r1 = r0.C
            java.util.List<com.autonavi.common.model.POI> r1 = r1.c
            bxh r4 = r0.C
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r4.b
            int r4 = defpackage.bcy.g(r4)
            if (r1 == 0) goto L_0x035f
            int r5 = r1.size()
            bzh r6 = r0.D
            bbr r6 = r6.j()
            bxh r7 = r0.C
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r7 = r7.b
            if (r7 == 0) goto L_0x035f
            com.autonavi.map.search.fragment.SearchResultBasePage r8 = r0.f
            bty r8 = r8.getMapView()
            if (r8 == 0) goto L_0x035f
            r8 = 0
            if (r6 == 0) goto L_0x0044
            com.autonavi.common.model.GeoPoint r9 = r6.getGeoPoint()
            goto L_0x0066
        L_0x0044:
            bzh r9 = r0.D
            java.util.List r9 = r9.k()
            if (r9 == 0) goto L_0x0065
            bzh r9 = r0.D
            java.util.List r9 = r9.k()
            int r9 = r9.size()
            if (r9 <= 0) goto L_0x0065
            bzh r9 = r0.D
            bbr r9 = r9.l()
            if (r9 == 0) goto L_0x0065
            com.autonavi.common.model.GeoPoint r9 = r9.getGeoPoint()
            goto L_0x0066
        L_0x0065:
            r9 = r8
        L_0x0066:
            com.autonavi.map.search.fragment.SearchResultBasePage r10 = r0.f
            bty r10 = r10.getMapView()
            int r10 = r10.p()
            com.autonavi.map.search.fragment.SearchResultBasePage r11 = r0.f
            bty r11 = r11.getMapView()
            int r11 = r11.q()
            com.autonavi.map.search.fragment.SearchResultBasePage r12 = r0.f
            bty r12 = r12.getMapView()
            float r12 = r12.v()
            com.autonavi.map.search.fragment.SearchResultBasePage r13 = r0.f
            bty r13 = r13.getMapView()
            float r13 = r13.v()
            if (r9 == 0) goto L_0x0094
            int r10 = r9.x
            int r11 = r9.y
        L_0x0094:
            boolean r9 = r0.n
            r14 = 1
            if (r9 == 0) goto L_0x034f
            r0.M = r8
            bxo$6 r9 = new bxo$6
            r9.<init>()
            defpackage.aho.a(r9)
            aus r9 = r7.searchInfo
            int r9 = r9.w
            r16 = 1061158912(0x3f400000, float:0.75)
            r17 = -1082130432(0xffffffffbf800000, float:-1.0)
            if (r9 == r14) goto L_0x015b
            aus r9 = r7.searchInfo
            auk r9 = r9.a
            java.lang.Double[] r9 = r9.u
            if (r9 == 0) goto L_0x015b
            int r9 = r0.g
            if (r9 > 0) goto L_0x015b
            aus r4 = r7.searchInfo
            auk r4 = r4.a
            java.lang.Double[] r4 = r4.u
            com.autonavi.common.model.GeoPoint r5 = new com.autonavi.common.model.GeoPoint
            r9 = r4[r3]
            double r8 = r9.doubleValue()
            r2 = r4[r14]
            double r14 = r2.doubleValue()
            r5.<init>(r8, r14)
            com.autonavi.common.model.GeoPoint r2 = new com.autonavi.common.model.GeoPoint
            r8 = 2
            r8 = r4[r8]
            double r8 = r8.doubleValue()
            r14 = 3
            r4 = r4[r14]
            double r14 = r4.doubleValue()
            r2.<init>(r8, r14)
            com.autonavi.map.search.fragment.SearchResultBasePage r4 = r0.f
            android.content.Context r4 = r4.getContext()
            android.graphics.Rect r4 = defpackage.ags.a(r4)
            int r24 = r4.width()
            bzs r4 = r0.F
            int r25 = r4.m()
            com.autonavi.map.search.fragment.SearchResultBasePage r4 = r0.f
            bty r19 = r4.getMapView()
            int r4 = r5.x
            int r8 = r5.y
            int r9 = r2.x
            int r14 = r2.y
            r20 = r4
            r21 = r8
            r22 = r9
            r23 = r14
            float r4 = r19.a(r20, r21, r22, r23, r24, r25)
            float r4 = r4 - r16
            com.autonavi.map.search.fragment.SearchResultBasePage r8 = r0.f
            bty r8 = r8.getMapView()
            int r9 = r5.x
            int r14 = r5.y
            int r15 = r2.x
            int r3 = r2.y
            float r3 = r8.a(r9, r14, r15, r3)
            r8 = 1058642330(0x3f19999a, float:0.6)
            float r3 = r3 - r8
            bxh r8 = r0.C
            auu r8 = r8.e
            int r8 = r8.a
            r9 = 1
            if (r8 != r9) goto L_0x0147
            r8 = 0
            java.lang.Object r2 = r1.get(r8)
            com.autonavi.common.model.POI r2 = (com.autonavi.common.model.POI) r2
            com.autonavi.common.model.GeoPoint r2 = r2.getPoint()
            int r10 = r2.x
            int r11 = r2.y
            r2 = r4
            r26 = r10
            r8 = 0
            goto L_0x0222
        L_0x0147:
            android.graphics.Rect r8 = new android.graphics.Rect
            int r9 = r5.x
            int r5 = r5.y
            int r14 = r2.x
            int r2 = r2.y
            r8.<init>(r9, r5, r14, r2)
            r0.M = r8
            r2 = r4
            r26 = r10
            goto L_0x0222
        L_0x015b:
            r2 = 1
            if (r5 <= r2) goto L_0x01b4
            r3 = r10
            r9 = r3
            r8 = r11
            r14 = r8
            r2 = 0
        L_0x0163:
            if (r2 >= r5) goto L_0x01a8
            java.lang.Object r15 = r1.get(r2)
            com.autonavi.common.model.POI r15 = (com.autonavi.common.model.POI) r15
            if (r15 == 0) goto L_0x01a1
            if (r4 != r2) goto L_0x0176
            r26 = r10
            boolean r10 = r0.J
            if (r10 != 0) goto L_0x01aa
            goto L_0x0178
        L_0x0176:
            r26 = r10
        L_0x0178:
            com.autonavi.common.model.GeoPoint r10 = r15.getPoint()
            int r10 = r10.x
            int r3 = java.lang.Math.min(r3, r10)
            com.autonavi.common.model.GeoPoint r10 = r15.getPoint()
            int r10 = r10.y
            int r8 = java.lang.Math.min(r8, r10)
            com.autonavi.common.model.GeoPoint r10 = r15.getPoint()
            int r10 = r10.x
            int r9 = java.lang.Math.max(r9, r10)
            com.autonavi.common.model.GeoPoint r10 = r15.getPoint()
            int r10 = r10.y
            int r14 = java.lang.Math.max(r14, r10)
            goto L_0x01a3
        L_0x01a1:
            r26 = r10
        L_0x01a3:
            int r2 = r2 + 1
            r10 = r26
            goto L_0x0163
        L_0x01a8:
            r26 = r10
        L_0x01aa:
            android.graphics.Rect r2 = new android.graphics.Rect
            r2.<init>(r3, r8, r9, r14)
            r0.M = r2
            r18 = r2
            goto L_0x01bd
        L_0x01b4:
            r26 = r10
            r8 = r11
            r14 = r8
            r3 = r26
            r9 = r3
            r18 = 0
        L_0x01bd:
            int r2 = r0.g
            if (r2 <= 0) goto L_0x01c9
            int r2 = r0.g
            float r2 = (float) r2
            r8 = r18
        L_0x01c6:
            r3 = -1082130432(0xffffffffbf800000, float:-1.0)
            goto L_0x0222
        L_0x01c9:
            r2 = 1
            if (r5 <= r2) goto L_0x020e
            if (r4 != 0) goto L_0x01d5
            boolean r2 = r0.J
            if (r2 != 0) goto L_0x01d3
            goto L_0x01d5
        L_0x01d3:
            r2 = 1
            goto L_0x020e
        L_0x01d5:
            com.autonavi.map.search.fragment.SearchResultBasePage r2 = r0.f
            android.content.Context r2 = r2.getContext()
            android.graphics.Rect r2 = defpackage.ags.a(r2)
            int r24 = r2.width()
            bzs r2 = r0.F
            int r25 = r2.m()
            com.autonavi.map.search.fragment.SearchResultBasePage r2 = r0.f
            bty r19 = r2.getMapView()
            r20 = r3
            r21 = r8
            r22 = r9
            r23 = r14
            float r2 = r19.a(r20, r21, r22, r23, r24, r25)
            float r2 = r2 - r16
            com.autonavi.map.search.fragment.SearchResultBasePage r4 = r0.f
            bty r4 = r4.getMapView()
            float r3 = r4.a(r3, r8, r9, r14)
            r4 = 1058642330(0x3f19999a, float:0.6)
            float r3 = r3 - r4
        L_0x020b:
            r8 = r18
            goto L_0x0222
        L_0x020e:
            if (r5 == r2) goto L_0x021c
            if (r4 != 0) goto L_0x0217
            boolean r2 = r0.J
            if (r2 == 0) goto L_0x0217
            goto L_0x021c
        L_0x0217:
            r8 = r18
            r2 = -1082130432(0xffffffffbf800000, float:-1.0)
            goto L_0x01c6
        L_0x021c:
            r2 = 1099431936(0x41880000, float:17.0)
            r3 = 1099798938(0x418d999a, float:17.7)
            goto L_0x020b
        L_0x0222:
            r4 = 0
            int r5 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r5 <= 0) goto L_0x0228
            goto L_0x0229
        L_0x0228:
            r2 = r12
        L_0x0229:
            int r4 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r4 <= 0) goto L_0x022e
            r13 = r3
        L_0x022e:
            if (r8 == 0) goto L_0x0239
            int r26 = r8.centerX()
            int r11 = r8.centerY()
            goto L_0x025d
        L_0x0239:
            int r3 = r1.size()
            r4 = 1
            if (r3 != r4) goto L_0x025d
            r3 = 0
            java.lang.Object r4 = r1.get(r3)
            com.autonavi.common.model.POI r4 = (com.autonavi.common.model.POI) r4
            if (r4 == 0) goto L_0x025d
            com.autonavi.common.model.GeoPoint r3 = r4.getPoint()
            if (r3 == 0) goto L_0x025d
            com.autonavi.common.model.GeoPoint r3 = r4.getPoint()
            int r3 = r3.x
            com.autonavi.common.model.GeoPoint r4 = r4.getPoint()
            int r11 = r4.y
            r26 = r3
        L_0x025d:
            if (r7 == 0) goto L_0x0306
            aus r3 = r7.searchInfo
            auk r3 = r3.a
            java.lang.String r3 = r3.J
            if (r3 == 0) goto L_0x0306
            aus r3 = r7.searchInfo
            auk r3 = r3.a
            java.lang.String r3 = r3.J
            java.lang.String r4 = "interior"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0306
            bxh r3 = r0.C
            java.util.List<com.autonavi.common.model.POI> r3 = r3.c
            if (r3 == 0) goto L_0x02c0
            if (r7 == 0) goto L_0x02c0
            aus r4 = r7.searchInfo
            if (r4 == 0) goto L_0x02c0
            aus r4 = r7.searchInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r4 = r4.l
            if (r4 == 0) goto L_0x02c0
            int r4 = r3.size()
            aus r5 = r7.searchInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r5 = r5.l
            int r5 = r5.size()
            if (r4 <= r5) goto L_0x02c0
            int r4 = r3.size()
            aus r5 = r7.searchInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r5 = r5.l
            int r5 = r5.size()
            int r4 = r4 - r5
            r5 = 1
            int r4 = r4 - r5
            java.lang.Object r3 = r3.get(r4)
            com.autonavi.common.model.POI r3 = (com.autonavi.common.model.POI) r3
            if (r3 == 0) goto L_0x02c0
            java.lang.String r4 = r3.getId()
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 == 0) goto L_0x02c0
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r3 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r3
            boolean r3 = r3.getIsTopList()
            if (r3 != 0) goto L_0x02c0
            r3 = 1
            goto L_0x02c1
        L_0x02c0:
            r3 = 0
        L_0x02c1:
            if (r3 != 0) goto L_0x0306
            r3 = 1
            defpackage.elc.b = r3
            boolean r3 = r1.isEmpty()
            if (r3 != 0) goto L_0x0306
            r3 = 0
        L_0x02cd:
            int r2 = r1.size()
            if (r3 >= r2) goto L_0x02e2
            java.lang.Object r2 = r1.get(r3)
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r2 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r2
            boolean r2 = r2.getIsTopList()
            if (r2 == 0) goto L_0x02e2
            int r3 = r3 + 1
            goto L_0x02cd
        L_0x02e2:
            boolean r2 = defpackage.bcy.b(r7)
            if (r2 == 0) goto L_0x02f2
            aus r2 = r7.searchInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r2 = r2.l
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x02f3
        L_0x02f2:
            r3 = 0
        L_0x02f3:
            java.lang.Object r1 = r1.get(r3)
            com.autonavi.common.model.POI r1 = (com.autonavi.common.model.POI) r1
            com.autonavi.common.model.GeoPoint r1 = r1.getPoint()
            int r2 = r1.x
            int r11 = r1.y
            r1 = 1099956224(0x41900000, float:18.0)
            r13 = 1099956224(0x41900000, float:18.0)
            goto L_0x0309
        L_0x0306:
            r1 = r2
            r2 = r26
        L_0x0309:
            r0.K = r2
            r0.L = r11
            com.autonavi.ae.gmap.glinterface.GLGeoPoint r3 = new com.autonavi.ae.gmap.glinterface.GLGeoPoint
            r3.<init>(r2, r11)
            r2 = 0
            r0.n = r2
            bzj r2 = r0.H
            r2.a(r1, r13, r3)
            bzs r2 = r0.F
            com.autonavi.map.search.view.SlidingUpPanelLayout$SlideState r2 = r2.j()
            com.autonavi.map.search.view.SlidingUpPanelLayout$SlideState r4 = com.autonavi.map.search.view.SlidingUpPanelLayout.SlideState.COLLAPSED
            if (r2 != r4) goto L_0x032e
            com.autonavi.map.search.fragment.SearchResultBasePage r1 = r0.f
            bty r1 = r1.getMapView()
            r1.d(r13)
            goto L_0x0337
        L_0x032e:
            com.autonavi.map.search.fragment.SearchResultBasePage r2 = r0.f
            bty r2 = r2.getMapView()
            r2.d(r1)
        L_0x0337:
            bzs r1 = r0.F
            android.graphics.Point r1 = r1.l()
            r0.m = r1
            com.autonavi.map.search.fragment.SearchResultBasePage r2 = r0.f
            com.autonavi.map.core.MapManager r2 = r2.getMapManager()
            bty r2 = r2.getMapView()
            r4 = 0
            r5 = 100
            r2.a(r5, r3, r1, r4)
        L_0x034f:
            int r1 = r0.h
            r2 = 11100(0x2b5c, float:1.5554E-41)
            if (r1 != r2) goto L_0x035f
            if (r6 == 0) goto L_0x035f
            com.autonavi.common.model.GeoPoint r1 = r6.getGeoPoint()
            r2 = 1
            r0.a(r1, r2)
        L_0x035f:
            return
        L_0x0360:
            bxh r1 = r0.C
            auk r1 = r1.e()
            auv r1 = r1.M
            com.autonavi.map.search.fragment.SearchResultBasePage r2 = r0.f
            com.autonavi.map.core.MapManager r2 = r2.getMapManager()
            bty r2 = r2.getMapView()
            if (r2 == 0) goto L_0x03a4
            boolean r3 = r0.n
            if (r3 != 0) goto L_0x0379
            goto L_0x03a4
        L_0x0379:
            r3 = 0
            r0.n = r3
            bzs r4 = r0.F
            android.graphics.Point r4 = r4.l()
            r0.m = r4
            int r4 = r1.b
            r2.e(r4)
            com.autonavi.common.model.GeoPoint r4 = r1.a
            android.graphics.Point r5 = r0.m
            r6 = 100
            r2.a(r6, r4, r5, r3)
            bzj r2 = r0.H
            int r3 = r1.b
            float r3 = (float) r3
            int r4 = r1.b
            float r4 = (float) r4
            r5 = 1060320051(0x3f333333, float:0.7)
            float r4 = r4 + r5
            com.autonavi.common.model.GeoPoint r1 = r1.a
            r2.a(r3, r4, r1)
            return
        L_0x03a4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bxo.ac():void");
    }

    public final void a(GLGeoPoint gLGeoPoint) {
        a(gLGeoPoint, true);
    }

    private void a(GLGeoPoint gLGeoPoint, boolean z2) {
        if (this.f.isAlive()) {
            bty mapView = this.f.getMapManager().getMapView();
            Point l2 = this.F.l();
            if (!(mapView == null || l2 == null)) {
                mapView.a(200, gLGeoPoint, l2, z2);
                mapView.S();
                this.m = l2;
                if ((!(this.K == 0 || this.L == 0 || gLGeoPoint.x == this.K || gLGeoPoint.y == this.L) || (this.e != null && this.e.i)) && !this.F.x()) {
                    this.F.d(true);
                    e(false);
                }
            }
        }
    }

    public final void b(GLGeoPoint gLGeoPoint) {
        if (this.f.isAlive()) {
            bty mapView = this.f.getMapManager().getMapView();
            Point l2 = this.F.l();
            if (!(mapView == null || l2 == null)) {
                this.m = l2;
                if ((!(this.K == 0 || this.L == 0 || gLGeoPoint.x == this.K || gLGeoPoint.y == this.L) || (this.e != null && this.e.i)) && !this.F.x()) {
                    this.F.d(true);
                    e(false);
                }
            }
        }
    }

    public final void c(GLGeoPoint gLGeoPoint) {
        if (this.f.isAlive()) {
            bty mapView = this.f.getMapManager().getMapView();
            Point l2 = this.F.l();
            if (!(mapView == null || l2 == null)) {
                mapView.b(l2.x, l2.y);
                mapView.a(gLGeoPoint.x, gLGeoPoint.y);
                mapView.S();
                this.m = l2;
                if ((!(this.K == 0 || this.L == 0 || gLGeoPoint.x == this.K || gLGeoPoint.y == this.L) || (this.e != null && this.e.i)) && !this.F.x()) {
                    this.F.d(true);
                    e(false);
                }
            }
        }
    }

    private Point ao() {
        return new Point(ags.a(this.f.getContext()).width() / 2, ags.a(this.f.getContext()).height() / 2);
    }

    public final POI c() {
        return this.d;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x00ca  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onClick(android.view.View r11) {
        /*
            r10 = this;
            int r0 = r11.getId()
            int r1 = com.autonavi.minimap.R.id.top_find_here_layout
            if (r0 != r1) goto L_0x00e8
            int r11 = r11.getId()
            int r0 = com.autonavi.minimap.R.id.top_find_here_layout
            r1 = 1
            if (r11 != r0) goto L_0x0014
            e(r1)
        L_0x0014:
            long r2 = java.lang.System.currentTimeMillis()
            long r4 = r10.Q
            long r4 = r2 - r4
            r6 = 1000(0x3e8, double:4.94E-321)
            int r11 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r11 <= 0) goto L_0x00e8
            bxi r11 = r10.B
            bzs r0 = r10.F
            com.autonavi.map.search.view.SlidingUpPanelLayout$SlideState r0 = r0.j()
            r4 = 0
            if (r0 != 0) goto L_0x002f
        L_0x002d:
            r0 = 0
            goto L_0x0056
        L_0x002f:
            bzj r0 = r10.H
            bzj$a r0 = r0.b()
            if (r0 == 0) goto L_0x003d
            int r0 = r0.a
            r5 = 3
            if (r0 != r5) goto L_0x003d
            goto L_0x002d
        L_0x003d:
            int[] r0 = defpackage.bxo.AnonymousClass7.a
            bzs r5 = r10.F
            com.autonavi.map.search.view.SlidingUpPanelLayout$SlideState r5 = r5.j()
            int r5 = r5.ordinal()
            r0 = r0[r5]
            r5 = 4
            if (r0 == r5) goto L_0x0055
            switch(r0) {
                case 1: goto L_0x0053;
                case 2: goto L_0x002d;
                default: goto L_0x0051;
            }
        L_0x0051:
            r0 = -1
            goto L_0x0056
        L_0x0053:
            r0 = 1
            goto L_0x0056
        L_0x0055:
            r0 = 2
        L_0x0056:
            bxh r5 = r10.C
            com.autonavi.map.search.fragment.SearchResultBasePage r6 = r10.f
            com.autonavi.map.core.MapManager r7 = r6.getMapManager()
            bty r7 = r7.getMapView()
            if (r7 == 0) goto L_0x00e3
            if (r5 != 0) goto L_0x0068
            goto L_0x00e3
        L_0x0068:
            java.lang.String r7 = r5.b()
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 != 0) goto L_0x00e3
            com.autonavi.minimap.controller.AppManager r7 = com.autonavi.minimap.controller.AppManager.getInstance()
            java.lang.String r7 = r7.getUserLocInfo()
            java.lang.String r5 = r5.b()
            bty r8 = r6.getMapView()
            com.autonavi.ae.gmap.glinterface.GLGeoPoint r8 = r8.n()
            com.autonavi.common.model.GeoPoint r8 = com.autonavi.common.model.GeoPoint.glGeoPoint2GeoPoint(r8)
            r9 = 0
            com.autonavi.bl.search.InfoliteParam r5 = defpackage.bbv.a(r7, r5, r8, r9)
            java.lang.String r7 = "5000"
            r5.range = r7
            java.lang.String r7 = "103500"
            r5.search_sceneid = r7
            com.autonavi.common.SuperId r7 = com.autonavi.common.SuperId.getInstance()
            java.lang.String r8 = "03"
            r7.setBit4(r8)
            java.lang.String r7 = r7.getScenceId()
            r5.superid = r7
            bwx r7 = new bwx
            java.lang.String r8 = r5.keywords
            r7.<init>(r8, r4, r1)
            r7.p = r0
            bty r0 = r6.getMapView()
            android.graphics.Rect r0 = r0.H()
            r7.o = r0
            ekv r0 = new ekv
            r0.<init>()
            java.lang.String r8 = r5.keywords
            defpackage.bct.a(r8)
            defpackage.aey.a(r4)
            bbq r4 = r11.b
            if (r4 == 0) goto L_0x00cf
            bbq r4 = r11.b
            r4.a()
        L_0x00cf:
            bvo r4 = new bvo
            android.content.Context r6 = r6.getContext()
            r4.<init>(r6, r5, r7)
            bbq r0 = r0.a(r5, r1, r4)
            r11.b = r0
            java.lang.String r11 = ""
            defpackage.bct.a(r11)
        L_0x00e3:
            r10.ah()
            r10.Q = r2
        L_0x00e8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bxo.onClick(android.view.View):void");
    }

    /* access modifiers changed from: private */
    public static void e(boolean z2) {
        String str = z2 ? "B069" : LogConstant.MAIN_MAP_GUIDE_MAP_SHOW;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("from", 1);
            LogManager.actionLogV2(LogConstant.SEARCH_RESULT_MAP, str, jSONObject);
        } catch (JSONException unused) {
        }
    }

    public void onMapTouch() {
        if (this.F.k() != SlideState.COLLAPSED) {
            this.F.e(true);
            if (this.e != null) {
                this.e.i = true;
            }
            defpackage.bzj.a b2 = this.H.b();
            if (b2 != null && b2.a != 3) {
                this.F.e();
            }
        }
    }

    public boolean onBlankClick() {
        boolean onBlankClick = super.onBlankClick();
        defpackage.bzj.a b2 = this.H.b();
        if (!(b2 == null || b2.a == 3 || this.H.b().a == 2) || TextUtils.equals("2", this.F.a())) {
            this.F.e();
        }
        return onBlankClick;
    }

    public final POI ad() {
        if (this.p == null || this.p.b == -1) {
            return null;
        }
        return this.C.b(this.p.b);
    }

    public final View b(Context context) {
        if (this.r == null) {
            this.s = this.q.a(false, new d());
            this.t = this.q.c(false);
            this.u = this.q.l();
            this.v = this.q.f();
            this.w = this.q.d();
            this.x = this.q.n();
            this.y = this.q.a();
            this.z = this.q.o();
            this.r = new ccv(context);
            this.r.addWidget(this.s, this.q.i(), 4);
            this.r.addWidget(this.t, this.q.k(), 4);
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            layoutParams.rightMargin = this.f.getContext().getResources().getDimensionPixelSize(R.dimen.map_container_btn_margin);
            layoutParams.topMargin = agn.a(this.f.getContext(), 4.0f);
            layoutParams.bottomMargin = this.f.getContext().getResources().getDimensionPixelSize(R.dimen.zoom_view_bottom_margin) + agn.a(this.f.getContext(), 39.0f);
            this.r.addWidget(this.u, layoutParams, 6);
            this.q.b((View) this.q.o());
            this.r.addWidget(this.z, this.q.p(), 2);
            this.r.addWidget(this.v, this.q.g(), 7);
            this.r.addWidget(this.y, this.q.c(), 1);
            this.q.a(this.q.n());
            this.r.addWidget(this.x, this.q.q(), 2);
            a(this.r, this.q);
            this.q.a(this.q.d());
            this.q.a(this.r.getSuspendView(), this.w, this.q.e(), 3);
            if (this.F != null && this.F.x()) {
                if (this.s != null) {
                    this.s.setVisibility(4);
                }
                if (this.t != null) {
                    this.t.setVisibility(4);
                }
            }
        }
        return this.r.getSuspendView();
    }

    public final boolean ae() {
        if (this.A == null) {
            return false;
        }
        this.A.a();
        return true;
    }

    public final void af() {
        if (this.C.b != null && bcy.d(this.C.b) && this.C.e().N != 2000) {
            String str = "tip";
            if (this.F.j() != null) {
                str = ccd.a(this.f.getArguments().getInt("list_anchored_key"));
            }
            for (POI next : this.C.c) {
                if (next != null) {
                    ChildrenPoiData poiChildrenInfo = ((SearchPoi) next.as(SearchPoi.class)).getPoiChildrenInfo();
                    if (poiChildrenInfo != null) {
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put(TrafficUtil.POIID, next.getId());
                            jSONObject.put("status", str);
                            jSONObject.put("text", bcy.k(this.C.b));
                            Map templateDataMap = ((SearchPoi) next.as(SearchPoi.class)).getTemplateDataMap();
                            if (templateDataMap != null) {
                                if (!templateDataMap.containsKey(Integer.valueOf(2027))) {
                                    if (templateDataMap.containsKey(Integer.valueOf(2023))) {
                                    }
                                }
                            }
                            if (poiChildrenInfo.childType == 1) {
                                LogManager.actionLogV2(LogConstant.SEARCH_RESULT_MAP, LogConstant.HONGBAO_CLICK, jSONObject);
                            } else if (poiChildrenInfo.childType == 2) {
                                LogManager.actionLogV2(LogConstant.SEARCH_RESULT_LIST, "B068", jSONObject);
                            }
                        } catch (JSONException unused) {
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }

    public final bxd ag() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    public final void ah() {
        if (this.e != null) {
            this.F.d(false);
            this.e.i = false;
        }
    }

    public final BaseCQLayerOwner t() {
        return new a(this.f);
    }

    public final long u() {
        return this.j.longValue();
    }

    public final MapBasePage a() {
        return this.f;
    }

    /* access modifiers changed from: protected */
    public final void aj() {
        this.f.getMapView().r(false);
    }

    /* access modifiers changed from: protected */
    public void a(PageBundle pageBundle, boolean z2) {
        WebStorage a2 = bic.a((String) "poi_info");
        a2.beginTransaction();
        a2.set("CURRENT_BUS_ALIAS", "");
        a2.commit();
        if (pageBundle != null && this.C != null) {
            this.h = pageBundle.getInt("from_page", 0);
            this.P = this.f.getArguments().getInt("list_anchored_key", 0);
            int i2 = pageBundle.getInt("search_page_type", -2);
            if (i2 != -2) {
                this.i = i2;
            }
            this.C.k = (Rect) pageBundle.getObject("map_center_rect");
            this.g = pageBundle.getInt("zoomlevel");
            if (pageBundle.containsKey("SUPER_ID")) {
                this.O = (SuperId) pageBundle.get("SUPER_ID");
            }
            this.d = (POI) pageBundle.get("center_poi");
            this.C.l = this.d;
            if (pageBundle.containsKey("key_is_switch_online")) {
                this.U = pageBundle.getBoolean("key_is_switch_online");
            }
            this.K = pageBundle.getInt("originalX");
            this.L = pageBundle.getInt("originalY");
        }
    }

    private static void E() {
        awo awo = (awo) defpackage.esb.a.a.a(awo.class);
        if (awo != null) {
            awo.b(9001);
            if (awo.d()) {
                awo.e();
            }
        }
    }

    @CallSuper
    public void m() {
        TipLogHelper.a = "";
        TipLogHelper.b = "";
        this.I.removeCallbacksAndMessages(null);
        this.U = aey.a();
        aey.a(false);
        y();
        this.D.m();
        if (this.f != null) {
            boolean z2 = this.f.getArguments().getBoolean("is_go_detail", false);
            if (!this.o && !z2 && this.A != null) {
                cfe.b(this.f);
                cfe.a((IMapPage) this.f);
            }
            bty mapView = this.f.getMapManager().getMapView();
            if (mapView != null) {
                mapView.g(true);
                mapView.E();
                mapView.Y();
            }
            cde suspendManager = this.f.getSuspendManager();
            if (suspendManager != null) {
                cdn c2 = suspendManager.c();
                if (c2 != null) {
                    c2.b(this.af);
                }
            }
            MapCustomizeManager b2 = this.f.getSuspendManager().b();
            if (b2 != null) {
                b2.getMapLayerDialogCustomActions().a = 1;
            }
            if (this.f.getIsHideAllOpenLayerCustomize()) {
                awo awo = (awo) defpackage.esb.a.a.a(awo.class);
                if (awo != null) {
                    awo.c();
                    awo.f();
                }
            }
        }
        c(true);
        this.T = true;
        this.F.o();
        if (this.q != null) {
            this.q.a((defpackage.ceb.a) null);
        }
        h();
        g();
        this.a = true;
    }

    /* access modifiers changed from: protected */
    public final void ai() {
        if (cch.a(this.C.e())) {
            this.f.getMapView().r(true);
            awo awo = (awo) defpackage.esb.a.a.a(awo.class);
            if (awo != null) {
                Iterator<LayerItem> it = awo.i().iterator();
                while (it.hasNext()) {
                    LayerItem next = it.next();
                    if (next.getLayer_id() == 610000) {
                        awo.a(next.getData());
                        awo.b(610000);
                        return;
                    }
                }
            }
        }
    }

    static /* synthetic */ void c(bxo bxo) {
        if (bxo.f != null && bxo.D != null) {
            bty mapView = bxo.f.getMapManager().getMapView();
            if (mapView != null) {
                ArrayList arrayList = new ArrayList();
                bbr bbr = null;
                bbr a2 = bya.a((PointOverlay) bxo.D.f, (List<bzu>) arrayList, 1);
                if (a2 != null) {
                    bbr = a2;
                }
                bbr a3 = bya.a((PointOverlay) bxo.D.c, (List<bzu>) arrayList, 2);
                if (a3 != null) {
                    bbr = a3;
                }
                bbr a4 = bya.a((PointOverlay) bxo.D.b, (List<bzu>) arrayList, 3);
                if (a4 != null) {
                    bbr = a4;
                }
                if (!arrayList.isEmpty()) {
                    bxo.R.a((List<bzu>) arrayList, bbr, mapView, bxo.p);
                }
            }
        }
    }

    static /* synthetic */ void b(bxo bxo, int i2) {
        if (bxo.C != null && bcy.b(bxo.C.b)) {
            POI b2 = bxo.C.b(i2);
            if (b2 != null) {
                SearchPoi searchPoi = (SearchPoi) b2.as(SearchPoi.class);
                if (searchPoi.getRecommendMode() != null && searchPoi.getRecommendMode().b.size() > 0) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("text", bxo.C.h());
                        jSONObject.put(TrafficUtil.POIID, searchPoi.getId());
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    LogManager.actionLogV2(LogConstant.SEARCH_RESULT_MAP, LogConstant.MAIN_LOCATION_DETAIL, jSONObject);
                }
            }
        }
    }

    static /* synthetic */ void a(bxo bxo, int i2, BaseMapOverlay baseMapOverlay) {
        if (baseMapOverlay instanceof NormalSearchPoiOverlay) {
            NormalSearchPoiOverlay normalSearchPoiOverlay = (NormalSearchPoiOverlay) baseMapOverlay;
            if (bxo.C != null && i2 == normalSearchPoiOverlay.getLastFocusIndex() && bcy.b(bxo.C.b)) {
                POI b2 = bxo.C.b(i2);
                if (b2 != null) {
                    SearchPoi searchPoi = (SearchPoi) b2.as(SearchPoi.class);
                    if (cch.a(searchPoi)) {
                        String sketchUrl = searchPoi.getSketchUrl();
                        if (!TextUtils.isEmpty(sketchUrl)) {
                            bxo.f.startScheme(new Intent("android.intent.action.VIEW", Uri.parse(sketchUrl)));
                            LogManager.actionLogV25("P00360", "B010", new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, searchPoi.getId()));
                        }
                    }
                }
            }
        }
    }

    static /* synthetic */ void c(bxo bxo, int i2) {
        if (bxo.D.i() >= 0) {
            POI ad2 = bxo.ad();
            if (ad2 != null) {
                SearchPoi searchPoi = (SearchPoi) ad2.as(SearchPoi.class);
                POI poi = (POI) ((List) searchPoi.getPoiChildrenInfo().stationList).get(i2);
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(TrafficUtil.POIID, poi.getId());
                    jSONObject.put("from", searchPoi.getId());
                    jSONObject.put("text", bcy.k(bxo.C.b));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                LogManager.actionLogV2(LogConstant.SEARCH_RESULT_MAP, "B032", jSONObject);
            }
        }
    }
}
