package defpackage;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.searchresult.ajx.ModuleSearch;
import com.autonavi.bundle.searchresult.ajx.ModuleSearch.b;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.search.manager.SearchResultAjxViewManager$3;
import com.autonavi.map.search.view.SlidingUpPanelLayout.SlideState;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.modules.ModuleHistory;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.ajx3.views.AmapAjxView.AttributeListener;
import com.autonavi.minimap.ajx3.views.AmapAjxView.BackCallback;
import java.util.List;
import org.json.JSONException;

/* renamed from: bzf reason: default package */
/* compiled from: SearchResultAjxViewManager */
public class bzf extends bzk<bxp> implements bzl {
    static final /* synthetic */ boolean a = true;
    /* access modifiers changed from: private */
    public Context k;
    /* access modifiers changed from: private */
    public AmapAjxView l;
    /* access modifiers changed from: private */
    public a m;
    private String n;
    /* access modifiers changed from: private */
    public bze o;
    private String p;
    /* access modifiers changed from: private */
    public boolean q;
    /* access modifiers changed from: private */
    public boolean r = true;
    /* access modifiers changed from: private */
    public boolean s = false;
    /* access modifiers changed from: private */
    public long t;
    private BackCallback u = new BackCallback() {
        public final void back(Object obj, String str) {
            ((bxp) bzf.this.c).f.finish();
        }
    };
    private AttributeListener v = new AttributeListener() {
        public final boolean handleAttr(String str, Object obj) {
            boolean z = false;
            if (!"SEARCHRESULT_TOP".equalsIgnoreCase(str)) {
                return false;
            }
            int standardUnitToPixel = DimensionUtils.standardUnitToPixel(bzf.this.k, (float) (obj instanceof Float ? Math.round(((Float) obj).floatValue()) : 0));
            bzf bzf = bzf.this;
            if (standardUnitToPixel >= (bzf.m.f - bzf.this.m.d) - 5) {
                z = true;
            }
            bzf.a(z);
            return true;
        }
    };
    private Callback<AmapAjxView> w = new SearchResultAjxViewManager$3(this);
    /* access modifiers changed from: private */
    public b x = new b() {
        public final void a() {
            ((bxp) bzf.this.c).C();
        }

        public final void a(int i) {
            ((bxp) bzf.this.c).k(i);
        }

        public final void a(int i, int i2) {
            ((bxp) bzf.this.c).a(i, i2);
        }

        public final void b() {
            ((bxp) bzf.this.c).al();
        }

        public final void b(int i) {
            ((bxp) bzf.this.c).i(i);
        }

        public final void c() {
            ((bxp) bzf.this.c).h(0);
        }

        public final void a(String str, String str2) {
            ((bxp) bzf.this.c).d("true".equals(str));
            if ("true".equals(str2) || bzf.this.m.b == "1") {
                if (!"true".equals(str2) && bzf.this.m.b == "1") {
                    bzf.this.s = true;
                }
                return;
            }
            ((bxp) bzf.this.c).E();
        }
    };
    /* access modifiers changed from: private */
    public com.autonavi.bundle.searchresult.ajx.ModuleSearch.a y = new com.autonavi.bundle.searchresult.ajx.ModuleSearch.a() {
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void a(java.lang.String r9) {
            /*
                r8 = this;
                bzf r0 = defpackage.bzf.this
                bzf$a r0 = r0.m
                r0.a(r9)
                bzf r0 = defpackage.bzf.this
                bxo r0 = r0.c
                bxp r0 = (defpackage.bxp) r0
                com.autonavi.map.search.fragment.SearchResultBasePage r0 = r0.f
                bty r0 = r0.getMapView()
                int r1 = r9.hashCode()
                r2 = 2
                r3 = 1
                r4 = 0
                switch(r1) {
                    case 49: goto L_0x003e;
                    case 50: goto L_0x0034;
                    case 51: goto L_0x002a;
                    case 52: goto L_0x0020;
                    default: goto L_0x001f;
                }
            L_0x001f:
                goto L_0x0048
            L_0x0020:
                java.lang.String r1 = "4"
                boolean r9 = r9.equals(r1)
                if (r9 == 0) goto L_0x0048
                r9 = 3
                goto L_0x0049
            L_0x002a:
                java.lang.String r1 = "3"
                boolean r9 = r9.equals(r1)
                if (r9 == 0) goto L_0x0048
                r9 = 2
                goto L_0x0049
            L_0x0034:
                java.lang.String r1 = "2"
                boolean r9 = r9.equals(r1)
                if (r9 == 0) goto L_0x0048
                r9 = 1
                goto L_0x0049
            L_0x003e:
                java.lang.String r1 = "1"
                boolean r9 = r9.equals(r1)
                if (r9 == 0) goto L_0x0048
                r9 = 0
                goto L_0x0049
            L_0x0048:
                r9 = -1
            L_0x0049:
                r5 = 33554432(0x2000000, double:1.6578092E-316)
                r1 = 8
                r7 = 30
                switch(r9) {
                    case 0: goto L_0x00ef;
                    case 1: goto L_0x00aa;
                    case 2: goto L_0x006e;
                    case 3: goto L_0x0055;
                    default: goto L_0x0053;
                }
            L_0x0053:
                goto L_0x0110
            L_0x0055:
                bzf r9 = defpackage.bzf.this
                bxo r9 = r9.c
                bxp r9 = (defpackage.bxp) r9
                r9.h()
                bzf r9 = defpackage.bzf.this
                bxo r9 = r9.c
                bxp r9 = (defpackage.bxp) r9
                r9.b(r1)
                if (r0 == 0) goto L_0x0110
                r0.b(r7)
                goto L_0x0110
            L_0x006e:
                bzf r9 = defpackage.bzf.this
                bzf r1 = defpackage.bzf.this
                bzf$a r1 = r1.m
                int r1 = r1.g
                r9.a(r1)
                bzf r9 = defpackage.bzf.this
                r9.a(r3)
                bzf r9 = defpackage.bzf.this
                bxo r9 = r9.c
                bxp r9 = (defpackage.bxp) r9
                r9.b(r4)
                bzf r9 = defpackage.bzf.this
                r9.q = false
                if (r0 == 0) goto L_0x0093
                r0.b(r7)
            L_0x0093:
                bzf r9 = defpackage.bzf.this
                boolean r9 = r9.s
                if (r9 == 0) goto L_0x00a4
                bzf r9 = defpackage.bzf.this
                bxo r9 = r9.c
                bxp r9 = (defpackage.bxp) r9
                r9.E()
            L_0x00a4:
                bzf r9 = defpackage.bzf.this
                r9.t = r5
                return
            L_0x00aa:
                bzf r9 = defpackage.bzf.this
                r9.d(r4)
                bzf r9 = defpackage.bzf.this
                bzf r2 = defpackage.bzf.this
                bzf$a r2 = r2.m
                int r2 = r2.g
                r9.a(r2)
                bzf r9 = defpackage.bzf.this
                r9.a(r3)
                bzf r9 = defpackage.bzf.this
                bxo r9 = r9.c
                bxp r9 = (defpackage.bxp) r9
                r9.a(r1, r4)
                bzf r9 = defpackage.bzf.this
                bxo r9 = r9.c
                bxp r9 = (defpackage.bxp) r9
                r9.a(r3)
                if (r0 == 0) goto L_0x00d8
                r0.b(r7)
            L_0x00d8:
                bzf r9 = defpackage.bzf.this
                boolean r9 = r9.s
                if (r9 == 0) goto L_0x00e9
                bzf r9 = defpackage.bzf.this
                bxo r9 = r9.c
                bxp r9 = (defpackage.bxp) r9
                r9.E()
            L_0x00e9:
                bzf r9 = defpackage.bzf.this
                r9.t = r5
                return
            L_0x00ef:
                bzf r9 = defpackage.bzf.this
                r9.d(r4)
                bzf r9 = defpackage.bzf.this
                r9.a(r4)
                bzf r9 = defpackage.bzf.this
                bxo r9 = r9.c
                bxp r9 = (defpackage.bxp) r9
                r9.a(r4)
                if (r0 == 0) goto L_0x0107
                r0.b(r2)
            L_0x0107:
                bzf r9 = defpackage.bzf.this
                r0 = 67108864(0x4000000, double:3.31561842E-316)
                r9.t = r0
                return
            L_0x0110:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.bzf.AnonymousClass4.a(java.lang.String):void");
        }

        public final void b(String str) {
            bzf.this.m.a(str);
            ((bxp) bzf.this.c).a(8, !"2".equals(str));
            bzf.this.w();
        }

        public final void a(String str, String str2, String str3, String str4, String str5, String str6) {
            a b = bzf.this.m;
            b.c = a.a(b.a, str2);
            b.d = a.a(b.a, str3);
            b.e = a.a(b.a, str4);
            b.f = a.a(b.a, str5);
            b.b = str;
            b.b();
            if (!"1".equals(str)) {
                bzf.this.a(bzf.this.m.g);
            }
            bzf.this.j.a(1).b = bzf.this.m.a();
            if ("true".equals(str6)) {
                if (bzf.this.r) {
                    bzf.this.b.postDelayed(new Runnable() {
                        public final void run() {
                            bzf.this.r = false;
                            ((bxp) bzf.this.c).O();
                            ((bxp) bzf.this.c).am();
                        }
                    }, 100);
                } else {
                    ((bxp) bzf.this.c).n = true;
                    ((bxp) bzf.this.c).am();
                }
            }
        }
    };

    /* renamed from: bzf$a */
    /* compiled from: SearchResultAjxViewManager */
    static class a {
        Context a;
        String b = "invalid";
        int c = -1;
        int d = -1;
        int e = -1;
        int f = -1;
        int g = -1;

        a(Context context) {
            this.a = context;
        }

        /* access modifiers changed from: 0000 */
        public final void a(String str) {
            this.b = str;
            b();
        }

        /* access modifiers changed from: 0000 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final com.autonavi.map.search.view.SlidingUpPanelLayout.SlideState a() {
            /*
                r2 = this;
                java.lang.String r0 = r2.b
                int r1 = r0.hashCode()
                switch(r1) {
                    case 49: goto L_0x001e;
                    case 50: goto L_0x0014;
                    case 51: goto L_0x000a;
                    default: goto L_0x0009;
                }
            L_0x0009:
                goto L_0x0028
            L_0x000a:
                java.lang.String r1 = "3"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0028
                r0 = 2
                goto L_0x0029
            L_0x0014:
                java.lang.String r1 = "2"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0028
                r0 = 1
                goto L_0x0029
            L_0x001e:
                java.lang.String r1 = "1"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0028
                r0 = 0
                goto L_0x0029
            L_0x0028:
                r0 = -1
            L_0x0029:
                switch(r0) {
                    case 0: goto L_0x0035;
                    case 1: goto L_0x0032;
                    case 2: goto L_0x002f;
                    default: goto L_0x002c;
                }
            L_0x002c:
                com.autonavi.map.search.view.SlidingUpPanelLayout$SlideState r0 = com.autonavi.map.search.view.SlidingUpPanelLayout.SlideState.ANCHORED
                return r0
            L_0x002f:
                com.autonavi.map.search.view.SlidingUpPanelLayout$SlideState r0 = com.autonavi.map.search.view.SlidingUpPanelLayout.SlideState.COLLAPSED
                return r0
            L_0x0032:
                com.autonavi.map.search.view.SlidingUpPanelLayout$SlideState r0 = com.autonavi.map.search.view.SlidingUpPanelLayout.SlideState.ANCHORED
                return r0
            L_0x0035:
                com.autonavi.map.search.view.SlidingUpPanelLayout$SlideState r0 = com.autonavi.map.search.view.SlidingUpPanelLayout.SlideState.EXPANDED
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.bzf.a.a():com.autonavi.map.search.view.SlidingUpPanelLayout$SlideState");
        }

        /* access modifiers changed from: 0000 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void b() {
            /*
                r2 = this;
                java.lang.String r0 = r2.b
                int r1 = r0.hashCode()
                switch(r1) {
                    case 49: goto L_0x001e;
                    case 50: goto L_0x0014;
                    case 51: goto L_0x000a;
                    default: goto L_0x0009;
                }
            L_0x0009:
                goto L_0x0028
            L_0x000a:
                java.lang.String r1 = "3"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0028
                r0 = 2
                goto L_0x0029
            L_0x0014:
                java.lang.String r1 = "2"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0028
                r0 = 1
                goto L_0x0029
            L_0x001e:
                java.lang.String r1 = "1"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0028
                r0 = 0
                goto L_0x0029
            L_0x0028:
                r0 = -1
            L_0x0029:
                switch(r0) {
                    case 0: goto L_0x0037;
                    case 1: goto L_0x0032;
                    case 2: goto L_0x002d;
                    default: goto L_0x002c;
                }
            L_0x002c:
                goto L_0x003c
            L_0x002d:
                int r0 = r2.e
                r2.g = r0
                goto L_0x003c
            L_0x0032:
                int r0 = r2.d
                r2.g = r0
                return
            L_0x0037:
                int r0 = r2.c
                r2.g = r0
                return
            L_0x003c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.bzf.a.b():void");
        }

        static int a(Context context, String str) {
            int i = 0;
            if (TextUtils.isEmpty(str)) {
                return 0;
            }
            try {
                i = (int) Float.parseFloat(str);
            } catch (NumberFormatException unused) {
            }
            return DimensionUtils.standardUnitToPixel(context, (float) i);
        }
    }

    public final int d_() {
        return 9;
    }

    public final int g() {
        return 9;
    }

    public final int h() {
        return 0;
    }

    public bzf(bxp bxp, bzj bzj) {
        super(bxp, bzj);
        this.k = bxp.f.getContext();
        this.m = new a(this.k);
        this.o = new bze(bxp.f, A());
    }

    public final void a(View view) {
        super.a(view);
        if (a || this.g != null) {
            this.l = new AmapAjxView(this.k);
            this.l.onAjxContextCreated(this.w);
            this.l.setAttributeListener(this.v);
            this.l.setLoadingCallback(null);
            this.l.setBackCallBack(this.u);
            this.g.addView(this.l, new LayoutParams(-1, -1));
            return;
        }
        throw new AssertionError();
    }

    @Nullable
    public final ModuleSearch c_() {
        if (this.l == null) {
            return null;
        }
        return (ModuleSearch) this.l.getJsModule(ModuleSearch.MODULE_NAME);
    }

    public final void a(PageBundle pageBundle, InfoliteResult infoliteResult, List<POI> list, boolean z) {
        String str;
        super.a(pageBundle, infoliteResult, list, z);
        bzj bzj = this.j;
        if (!bzj.a.isEmpty()) {
            bzj.a.clear();
        }
        switch (pageBundle.getInt("list_anchored_key", 1)) {
            case 0:
                str = "3";
                break;
            case 1:
                str = "2";
                break;
            case 2:
                str = "1";
                break;
            default:
                str = "2";
                break;
        }
        try {
            this.p = bvd.a(infoliteResult, str).toString();
            DisplayMetrics displayMetrics = this.k.getResources().getDisplayMetrics();
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            Context context = this.k;
            int identifier = context.getResources().getIdentifier("status_bar_height", ResUtils.DIMEN, "android");
            int dimensionPixelSize = identifier > 0 ? context.getResources().getDimensionPixelSize(identifier) : 0;
            if (!z && infoliteResult.mWrapper != null) {
                "RQBXY".equals(infoliteResult.mWrapper.query_type);
            }
            bze bze = this.o;
            bxd ag = ((bxp) this.c).ag();
            int A = A();
            bze.d = ag;
            bze.b = A;
            int height = this.l.getHeight();
            if (height <= 0) {
                height = i2 - dimensionPixelSize;
            }
            this.l.load("path://amap_bundle_search/src/search_result/pages/SearchResultPage.tsx.js", this.p, null, "InfoliteResult", TextUtils.equals(Build.MODEL, "MI PLAY") ? 0 : i, height, "path://amap_bundle_search/src/search_result/pages/search_result_preload.js");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public final void a(bxh bxh, bxi bxi) {
        bze bze = this.o;
        bxd ag = ((bxp) this.c).ag();
        bze.c = bxi;
        bze.d = ag;
        bze.a(bxh);
    }

    public final void a(bxh bxh) {
        this.o.a(bxh);
    }

    public final boolean b() {
        return this.r;
    }

    public final long c() {
        return this.t;
    }

    public final void c(boolean z) {
        this.l.setVisibility(z ? 0 : 8);
    }

    public final void d() {
        c(true);
        if ("2".equals(this.m.b)) {
            d(false);
            w();
            a(this.m.g);
            ((bxp) this.c).b(8);
            ((bxp) this.c).an();
            return;
        }
        if ("3".equals(this.m.b)) {
            a(this.m.g);
            ((bxp) this.c).b(0);
            w();
        }
    }

    public final void d(boolean z) {
        bxd ag = ((bxp) this.c).ag();
        if (ag != null) {
            if (!z) {
                this.i.setVisibility(8);
                ag.i = false;
            } else if (ag.i && !t() && this.j != null) {
                defpackage.bzj.a b = this.j.b();
                boolean z2 = this.l.getVisibility() == 0 ? "3".equals(this.m.b) : b != null && b.a == 3;
                if (!((bxp) this.c).R() && z2) {
                    this.i.setVisibility(0);
                    ag.i = true;
                }
            }
        }
    }

    public final void e() {
        if (c_() != null && !"3".equals(this.m.b)) {
            c_().setViewState("3", true);
        }
    }

    public final SlideState j() {
        return this.m.a();
    }

    public final SlideState k() {
        return this.m.a();
    }

    public final void a(AbstractBasePage abstractBasePage, int i, ResultType resultType, PageBundle pageBundle) {
        super.a(abstractBasePage, i, resultType, pageBundle);
        if (pageBundle != null) {
            this.n = (String) pageBundle.get(ModuleHistory.AJX_BACK_RETURN_DATA_KEY);
        } else {
            this.n = null;
        }
    }

    public final Point l() {
        int dimensionPixelOffset = this.k.getResources().getDimensionPixelOffset(R.dimen.v3_bottom_bar_height);
        Point point = new Point();
        point.x = ags.a(this.k).width() / 2;
        if (this.l == null || this.l.getVisibility() != 8) {
            point.y = (((((bxp) this.c).f.getContentView().getHeight() - dimensionPixelOffset) - this.m.g) / 2) + dimensionPixelOffset;
        } else {
            point.y = (((((bxp) this.c).f.getContentView().getHeight() - dimensionPixelOffset) - 200) / 2) + dimensionPixelOffset;
        }
        return point;
    }

    public final int m() {
        return ags.a(((bxp) this.c).f.getContext()).height() - this.m.d;
    }

    public final void n() {
        super.n();
        this.l.onResume(false, this.n);
        this.n = null;
    }

    public final void o() {
        super.o();
        this.l.onPause(false);
    }

    public final void p() {
        super.p();
        this.g.removeView(this.l);
        bze bze = this.o;
        if (bze.f != null) {
            bze.f.b(bze.h);
        }
        bze.g.clear();
        this.l.onDestroy();
    }

    public final boolean q() {
        if (this.j.a.size() > 1) {
            if (this.j.a().a == 3) {
                ((bxp) this.c).f.getCQLayerController().dismissCQLayer(false);
                ((bxp) this.c).Q();
                return true;
            }
        } else if (this.l.backPressed()) {
            return true;
        }
        return super.q();
    }

    public final void r() {
        w();
    }

    /* access modifiers changed from: private */
    public void w() {
        defpackage.bzj.a b = this.j.b();
        if ((b == null || b.a != 3) && !this.q) {
            defpackage.bzj.a c = this.j.c();
            String str = this.m.b;
            char c2 = 65535;
            switch (str.hashCode()) {
                case 50:
                    if (str.equals("2")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case 51:
                    if (str.equals("3")) {
                        c2 = 1;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    ((bxp) this.c).h();
                    if (c.c > 0.0f) {
                        ((bxp) this.c).f.getMapView().d(c.c);
                    }
                    if (!(c.e.x == 0 || c.e.y == 0)) {
                        ((bxp) this.c).a(c.e);
                        return;
                    }
                case 1:
                    ((bxp) this.c).h();
                    ((bxp) this.c).f.getMapView().d(c.d);
                    ((bxp) this.c).a(c.e);
                    break;
            }
        }
    }

    public final void e(boolean z) {
        if (this.l.getVisibility() != 8) {
            this.q = z;
        }
    }

    private int A() {
        PageBundle arguments = ((bxp) this.c).f.getArguments();
        if (arguments != null) {
            return arguments.getInt("search_page_type", 0);
        }
        return 0;
    }
}
