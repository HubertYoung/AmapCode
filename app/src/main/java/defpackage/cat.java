package defpackage;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.searchresult.ajx.AjxModuleTipDetailPage;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import com.autonavi.bundle.searchresult.ajx.ModulePoi.b;
import com.autonavi.bundle.searchresult.ajx.ModulePoi.c;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.search.poi.detail.PoiDetailLayer;
import com.autonavi.map.search.poi.detail.PoiDetailNativeView;
import com.autonavi.map.search.tip.indicator.SearchPoiIndicatorView;
import com.autonavi.map.search.tip.indicator.SearchPoiIndicatorView.a;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.ModuleJsBridge;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleBridge;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.ajx3.views.AmapAjxView.AttributeListener;
import com.autonavi.minimap.ajx3.views.AmapAjxView.BackCallback;
import com.autonavi.minimap.search.PoiDetailSlidingView;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cat reason: default package */
/* compiled from: PoiDetailLayerManagerImpl */
public final class cat implements cap {
    /* access modifiers changed from: private */
    public static int b;
    /* access modifiers changed from: private */
    public static int c;
    /* access modifiers changed from: private */
    public static int d;
    private static int e;
    private a A = new a() {
        public final void a() {
            if (cat.this.s) {
                String str = cat.this.g.c.a;
                if (TextUtils.equals(str, ModulePoi.TIPS)) {
                    cat.this.g.c.a = "full";
                    cat.this.a.a((String) "full", false);
                    cat.this.f.a((String) "full", true);
                } else if (TextUtils.equals(str, "full")) {
                    cat.this.g.c.a = ModulePoi.TIPS;
                    cat.this.a.a((String) ModulePoi.TIPS, false);
                    cat.this.f.a((String) ModulePoi.TIPS, true);
                }
                if (cat.this.r != null) {
                    cat.this.r.a();
                }
            }
        }
    };
    private OnTouchListener B = new OnTouchListener() {
        private int b;
        private boolean c;
        private boolean d;

        public final boolean onTouch(View view, MotionEvent motionEvent) {
            if (!cat.this.s) {
                return true;
            }
            boolean z = false;
            switch (motionEvent.getAction()) {
                case 0:
                    this.d = true;
                    this.b = (int) motionEvent.getY();
                    this.c = true;
                    break;
                case 1:
                    if (this.c && this.d) {
                        this.d = false;
                        if (motionEvent.getAction() == 1) {
                            cat.this.g.c.a = "full";
                            cat.this.a.a((String) "full", false);
                            cat.this.f.a((String) "full", true);
                            if (cat.this.r != null) {
                                cat.this.r.a();
                            }
                            if (cat.this.q != null) {
                                cat.this.q;
                                break;
                            }
                        }
                    }
                    break;
                case 2:
                    if (Math.abs(this.b - ((int) motionEvent.getY())) < cat.d) {
                        z = true;
                    }
                    this.c = z;
                    break;
            }
            return true;
        }
    };
    c a = new c() {
        public final void a(String str, boolean z) {
            if (!TextUtils.equals(str, "full")) {
                cat.this.s();
            }
            if (!TextUtils.equals(str, ModulePoi.TIPS) || z) {
                cat.this.r();
            }
            if (cat.this.i != null) {
                cat.this.i.a(str, z);
            }
        }

        public final void b(String str, boolean z) {
            if (cat.this.i != null) {
                cat.this.i.b(str, z);
            }
        }

        public final void a(int i, boolean z) {
            cat.this.s();
            if (cat.this.a(i)) {
                cat.this.f;
                AMapLog.e("info", "switchViewVisibility");
            } else {
                cat.this.f;
                AMapLog.e("info", "switchViewVisibility");
            }
            if (cat.this.i != null) {
                cat.this.i.a(i, z);
            }
        }
    };
    /* access modifiers changed from: private */
    public PoiDetailLayer f = new PoiDetailLayer();
    /* access modifiers changed from: private */
    public car g = new car();
    private boolean h;
    /* access modifiers changed from: private */
    public c i;
    private View j;
    private View k;
    private String l;
    private PageBundle m;
    /* access modifiers changed from: private */
    public Handler n = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public b o;
    /* access modifiers changed from: private */
    public AjxModuleTipDetailPage.a p;
    /* access modifiers changed from: private */
    public b q;
    /* access modifiers changed from: private */
    public a r;
    /* access modifiers changed from: private */
    public boolean s;
    private PoiDetailNativeView t;
    private ViewGroup u;
    /* access modifiers changed from: private */
    public int v = -1;
    /* access modifiers changed from: private */
    public int w;
    /* access modifiers changed from: private */
    public String x = "";
    private AjxModuleTipDetailPage.a y = new AjxModuleTipDetailPage.a() {
        public final void a(final String str, final JsFunctionCallback jsFunctionCallback) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.optString("action").equals("tipsDidMount")) {
                    cat.this.n.postDelayed(new Runnable() {
                        public final void run() {
                            PoiDetailLayer d = cat.this.f;
                            if (d.d != null && d.e != null && d.d.getChildCount() > 0 && d.e.getChildCount() > 0) {
                                d.d.setVisibility(8);
                                d.e.setVisibility(8);
                                d.b.setVisibility(0);
                                d.d.removeAllViews();
                                d.e.removeAllViews();
                            }
                        }
                    }, 1000);
                }
                if (jSONObject.optString("action").equals("detailDidMount")) {
                    cat.this.n.postDelayed(new Runnable() {
                        public final void run() {
                            cat.this.s();
                        }
                    }, 1000);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            cat.this.n.post(new Runnable() {
                public final void run() {
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        if (jSONObject.has("param")) {
                            String optString = new JSONObject(jSONObject.optString("param", "")).optString("state");
                            String optString2 = jSONObject.optString("action", "");
                            if (!TextUtils.isEmpty(optString) && "detailStateDidChange".equals(optString2)) {
                                cat.this.x = optString;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (cat.this.p != null) {
                        cat.this.p.a(str, jsFunctionCallback);
                    }
                }
            });
        }

        public final void a(final int i) {
            cat.this.s();
            if (cat.this.v == -1) {
                cat.this.v = i;
            } else if (cat.this.v != i) {
                cat.this.v = i;
                cat.this.n.post(new Runnable() {
                    public final void run() {
                        if (euk.a()) {
                            cat.c = 0;
                        }
                        cat.this.g.c.b = (cat.b - i) - cat.c;
                        if (cat.this.g.c.b < cat.this.w) {
                            cat.this.g.c.b = cat.this.w;
                            cat.this.p.a(cat.this.g.c.b);
                            return;
                        }
                        if (cat.this.p != null) {
                            cat.this.p.a(cat.this.g.c.b);
                        }
                    }
                });
            }
        }
    };
    private b z = new b() {
        public final void a(final float f, final float f2) {
            cat.this.n.post(new Runnable() {
                public final void run() {
                    cat.this.s = true;
                    cat.this.f.a(true);
                    if (cat.this.o != null) {
                        cat.this.o.a(f, f2);
                    }
                }
            });
        }
    };

    /* access modifiers changed from: private */
    public void r() {
        if (!TextUtils.equals(this.g.c.a, "invalid")) {
            TextUtils.equals(this.g.c.a, ModulePoi.TIPS);
            AMapLog.e("info", "switchViewVisibility");
        }
    }

    public final boolean a(int i2) {
        int i3 = this.f.b().d;
        return i2 == i3 || i2 + -1 == i3 || i2 + 1 == i3;
    }

    public final View a(AbstractBasePage abstractBasePage, ViewGroup viewGroup, boolean z2, PageBundle pageBundle) {
        this.m = pageBundle;
        this.s = false;
        this.u = (ViewGroup) abstractBasePage.getContentView();
        Context context = DoNotUseTool.getContext();
        if (context != null) {
            if (e == 0) {
                e = agn.a(context, 39.0f);
            }
            if (d == 0) {
                d = ViewConfiguration.get(context).getScaledTouchSlop();
            }
            if (b == 0) {
                b = ags.a(context).height();
            }
            if (c == 0) {
                c = ags.d(context);
            }
        }
        car car = this.g;
        int i2 = b;
        int i3 = c;
        car.b = i2;
        car.a = i3;
        View findViewWithTag = this.u.findViewWithTag("poi_detail_ajx_native_view_tag");
        if (findViewWithTag != null) {
            this.u.removeView(findViewWithTag);
        }
        POI poi = (POI) pageBundle.getObject("POI");
        if (z2) {
            ViewGroup viewGroup2 = this.u;
            if (this.t == null) {
                this.t = new PoiDetailNativeView(DoNotUseTool.getContext());
                this.t.setTag("poi_detail_ajx_native_view_tag");
            }
            if (poi != null) {
                this.t.setPoiName(poi.getName());
                this.t.setHeaderH(ekt.a(DoNotUseTool.getContext(), poi.getType()));
                viewGroup2.addView(this.t);
            }
        }
        PoiDetailLayer poiDetailLayer = this.f;
        poiDetailLayer.j = new caq();
        poiDetailLayer.g = abstractBasePage;
        poiDetailLayer.f = abstractBasePage.getContext();
        caq caq = poiDetailLayer.j;
        caq.a = false;
        caq.b = "invalid";
        caq.d = 0;
        caq.c = false;
        caq.f = null;
        caq.g = null;
        caq.h = null;
        poiDetailLayer.a = new PoiDetailSlidingView(poiDetailLayer.f);
        poiDetailLayer.b = (AmapAjxView) poiDetailLayer.a.findViewById(R.id.ajx_view);
        poiDetailLayer.b.attachPage(abstractBasePage);
        poiDetailLayer.b.onAjxContextCreated(poiDetailLayer.k);
        poiDetailLayer.b.setOnUiLoadCallback(poiDetailLayer.l);
        poiDetailLayer.b.setAjxFragmentResultExecutor(new bxw(poi));
        poiDetailLayer.b.setAttributeListener(new AttributeListener() {
            public final boolean handleAttr(String str, Object obj) {
                int i = 0;
                if (!"POI_TOP".equalsIgnoreCase(str)) {
                    return false;
                }
                AjxModuleTipDetailPage ajxModuleTipDetailPage = (AjxModuleTipDetailPage) PoiDetailLayer.this.b.getJsModule("tipDetailPage");
                if (ajxModuleTipDetailPage != null) {
                    if (obj instanceof Float) {
                        i = Math.round(((Float) obj).floatValue());
                    }
                    ajxModuleTipDetailPage.topHeightChange(i);
                }
                return true;
            }
        });
        poiDetailLayer.b.setLoadingCallback(new Callback<AmapAjxView>() {
            public void callback(AmapAjxView amapAjxView) {
            }

            public void error(Throwable th, boolean z) {
            }
        });
        viewGroup.addView(poiDetailLayer.a);
        poiDetailLayer.d = (ViewGroup) poiDetailLayer.a.findViewById(R.id.tips_container);
        poiDetailLayer.e = (ViewGroup) poiDetailLayer.a.findViewById(R.id.bottom_banner);
        PoiDetailSlidingView poiDetailSlidingView = poiDetailLayer.a;
        this.f.a(false);
        String str = z2 ? "full" : ModulePoi.TIPS;
        this.g.c.a = str;
        r();
        this.g.h.a(str, false);
        String str2 = z2 ? "full" : ModulePoi.TIPS;
        final JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("state", str2);
            jSONObject.put("from", pageBundle.getInt("key_tip_from"));
            jSONObject.put("action", "detailStateDidChange");
            jSONObject.put("param", jSONObject2);
            if (pageBundle.getInt("key_tip_from") != 1) {
                this.n.post(new Runnable() {
                    public final void run() {
                        if (cat.this.p != null) {
                            cat.this.p.a(jSONObject.toString(), null);
                        }
                    }
                });
            } else if (this.p != null) {
                this.p.a(jSONObject.toString(), null);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        this.g.c.a = z2 ? "full" : ModulePoi.TIPS;
        PoiDetailLayer poiDetailLayer2 = this.f;
        c cVar = this.g.h;
        if (poiDetailLayer2.c == null) {
            poiDetailLayer2.j.g = cVar;
        } else {
            poiDetailLayer2.c.setOnStateChangeListener(cVar);
        }
        this.g.e = this.a;
        PoiDetailLayer poiDetailLayer3 = this.f;
        b bVar = this.z;
        if (poiDetailLayer3.c == null) {
            poiDetailLayer3.j.f = bVar;
        } else {
            poiDetailLayer3.c.setOnRegisterInfoListener(bVar);
        }
        PoiDetailLayer poiDetailLayer4 = this.f;
        AjxModuleTipDetailPage.a aVar = this.y;
        if (poiDetailLayer4.i == null) {
            poiDetailLayer4.j.h = aVar;
        } else {
            poiDetailLayer4.i.setListener(aVar);
        }
        if (!this.h) {
            PoiDetailLayer poiDetailLayer5 = this.f;
            String str3 = this.l;
            if (poiDetailLayer5.b != null) {
                poiDetailLayer5.h = str3;
                poiDetailLayer5.b.load("path://amap_bundle_poi/src/poi.jsx.js", str3, "Poi");
            }
            PoiDetailLayer poiDetailLayer6 = this.f;
            PageBundle pageBundle2 = this.m;
            if (poiDetailLayer6.b != null) {
                ModuleJsBridge moduleJsBridge = (ModuleJsBridge) poiDetailLayer6.b.getJsModule("js");
                if (moduleJsBridge != null) {
                    moduleJsBridge.getJsMethod().setBundle(pageBundle2);
                }
            }
            this.h = true;
        }
        return poiDetailSlidingView;
    }

    public final void a(String str) {
        PoiDetailLayer poiDetailLayer = this.f;
        if (poiDetailLayer.b != null) {
            poiDetailLayer.b.onResume(false, str);
        }
    }

    public final void a(boolean z2, String str) {
        PoiDetailLayer poiDetailLayer = this.f;
        if (poiDetailLayer.b != null) {
            poiDetailLayer.b.onResume(z2, str);
        }
    }

    public final void a(c cVar) {
        this.i = cVar;
    }

    public final void a() {
        PoiDetailLayer poiDetailLayer = this.f;
        if (poiDetailLayer.b != null) {
            poiDetailLayer.b.onPause(false);
        }
    }

    public final void b() {
        PoiDetailLayer poiDetailLayer = this.f;
        if (poiDetailLayer.b != null) {
            poiDetailLayer.b.onAjxContextCreated(null);
            poiDetailLayer.b.setAjxFragmentResultExecutor(null);
            poiDetailLayer.b.setBackCallBack(null);
            poiDetailLayer.b.setLoadingCallback(null);
            poiDetailLayer.d.setOnClickListener(null);
            if (poiDetailLayer.c != null) {
                poiDetailLayer.c.setOnRegisterInfoListener(null);
                poiDetailLayer.c.setOnStateChangeListener(null);
                poiDetailLayer.c.setOnPoiCallbackInitListener(null);
            }
            poiDetailLayer.b.onDestroy();
            poiDetailLayer.c = null;
            poiDetailLayer.h = null;
        }
        this.h = false;
        s();
        this.u = null;
    }

    public final ON_BACK_TYPE c() {
        PoiDetailLayer poiDetailLayer = this.f;
        if (poiDetailLayer.b != null && poiDetailLayer.b.backPressed()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        return ON_BACK_TYPE.TYPE_NORMAL;
    }

    public final void a(b bVar) {
        this.o = bVar;
    }

    public final void a(AjxModuleTipDetailPage.a aVar) {
        this.p = aVar;
    }

    public final void i() {
        if (this.j != null && this.k != null) {
            int a2 = (ccd.a(this.j) - e) + ccd.a(this.k);
            this.g.a(a2);
            this.w = a2;
            PoiDetailLayer poiDetailLayer = this.f;
            String str = this.l;
            if (poiDetailLayer.b != null && !TextUtils.isEmpty(str)) {
                poiDetailLayer.j.d = a2;
                poiDetailLayer.h = poiDetailLayer.a(str, a2);
                AjxModuleBridge ajxModuleBridge = (AjxModuleBridge) poiDetailLayer.b.getJsModule(AjxModuleBridge.MODULE_NAME);
                if (ajxModuleBridge != null) {
                    ajxModuleBridge.callJsFunction("onChange", poiDetailLayer.h);
                }
            }
        }
    }

    public final void a(View view, View view2) {
        if (view != null && view2 != null) {
            this.j = view;
            this.k = view2;
            View findViewById = view.findViewById(R.id.view_shadow);
            if (findViewById != null) {
                findViewById.setOnTouchListener(this.B);
            }
            View findViewById2 = view.findViewById(R.id.main_layout);
            if (findViewById2 == null) {
                findViewById2 = view.findViewById(R.id.basemap_gpstip);
            }
            if (findViewById2 == null) {
                findViewById2 = view.findViewById(R.id.poi_detail);
            }
            if (findViewById2 == null) {
                findViewById2 = view;
            }
            if (findViewById2 != null) {
                findViewById2.setOnTouchListener(this.B);
            }
            if (view2 instanceof SearchPoiIndicatorView) {
                ((SearchPoiIndicatorView) view2).setOnDetailsClickListener(this.A);
            }
            int a2 = (ccd.a(view) - e) + ccd.a(view2);
            this.g.a(a2);
            this.w = a2;
            PoiDetailLayer poiDetailLayer = this.f;
            if (poiDetailLayer.d.getChildCount() > 0) {
                poiDetailLayer.d.removeAllViews();
            }
            if (poiDetailLayer.e.getChildCount() > 0) {
                poiDetailLayer.e.removeAllViews();
            }
            poiDetailLayer.d.addView(view);
            poiDetailLayer.e.addView(view2);
            poiDetailLayer.j.d = a2;
        }
    }

    public final void e() {
        if (this.j != null) {
            this.j.setOnTouchListener(null);
        }
        this.j = null;
        PoiDetailLayer poiDetailLayer = this.f;
        if (poiDetailLayer.d.getChildCount() > 0) {
            poiDetailLayer.d.removeAllViews();
        }
    }

    public final void b(String str) {
        this.l = str;
    }

    public final void a(boolean z2) {
        this.f.a((String) ModulePoi.TIPS, z2);
    }

    public final void f() {
        this.f.a((String) "full", false);
    }

    public final void g() {
        this.f.j.a = true;
    }

    public final JsAdapter h() {
        AmapAjxView amapAjxView = this.f.b;
        if (amapAjxView == null) {
            return null;
        }
        ModuleJsBridge moduleJsBridge = (ModuleJsBridge) amapAjxView.getJsModule("js");
        if (moduleJsBridge != null) {
            return moduleJsBridge.getJsMethod();
        }
        return null;
    }

    public final void b(PageBundle pageBundle) {
        PoiDetailLayer poiDetailLayer = this.f;
        if (poiDetailLayer.j != null) {
            poiDetailLayer.j.e = pageBundle;
        }
    }

    public final caq d() {
        return this.f.b();
    }

    public final void a(BackCallback backCallback) {
        PoiDetailLayer poiDetailLayer = this.f;
        if (poiDetailLayer.b != null) {
            poiDetailLayer.b.setBackCallBack(backCallback);
        }
    }

    public final void a(PageBundle pageBundle) {
        this.m = pageBundle;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0041  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout.PanelState j() {
        /*
            r3 = this;
            java.lang.String r0 = r3.x
            int r1 = r0.hashCode()
            r2 = -1068259250(0xffffffffc053a84e, float:-3.3071475)
            if (r1 == r2) goto L_0x002a
            r2 = 3154575(0x30228f, float:4.420501E-39)
            if (r1 == r2) goto L_0x0020
            r2 = 3560248(0x365338, float:4.98897E-39)
            if (r1 == r2) goto L_0x0016
            goto L_0x0034
        L_0x0016:
            java.lang.String r1 = "tips"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0034
            r0 = 1
            goto L_0x0035
        L_0x0020:
            java.lang.String r1 = "full"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0034
            r0 = 0
            goto L_0x0035
        L_0x002a:
            java.lang.String r1 = "moving"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0034
            r0 = 2
            goto L_0x0035
        L_0x0034:
            r0 = -1
        L_0x0035:
            switch(r0) {
                case 0: goto L_0x0041;
                case 1: goto L_0x003e;
                case 2: goto L_0x003b;
                default: goto L_0x0038;
            }
        L_0x0038:
            com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout$PanelState r0 = com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout.PanelState.HIDDEN
            goto L_0x0043
        L_0x003b:
            com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout$PanelState r0 = com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout.PanelState.DRAGGING
            goto L_0x0043
        L_0x003e:
            com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout$PanelState r0 = com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout.PanelState.COLLAPSED
            goto L_0x0043
        L_0x0041:
            com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout$PanelState r0 = com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout.PanelState.EXPANDED
        L_0x0043:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cat.j():com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout$PanelState");
    }

    public final void a(a aVar) {
        this.r = aVar;
    }

    public final void k() {
        if (!this.f.a()) {
            PoiDetailLayer poiDetailLayer = this.f;
            if (poiDetailLayer.a != null) {
                poiDetailLayer.a.setVisibility(0);
            }
        }
    }

    public final void l() {
        if (this.f.a()) {
            PoiDetailLayer poiDetailLayer = this.f;
            if (poiDetailLayer.a != null) {
                poiDetailLayer.a.setVisibility(8);
            }
            this.a.a(0, false);
            this.a.a((String) ModulePoi.TIPS, false);
        }
    }

    /* access modifiers changed from: private */
    public void s() {
        if (this.u != null && this.t != null) {
            this.u.removeView(this.t);
            this.t = null;
        }
    }

    public final boolean m() {
        return this.f.a();
    }
}
