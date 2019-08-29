package defpackage;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus;
import com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus.c;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b;
import com.autonavi.map.search.manager.SearchResultTipDetailViewManager.DetailLayerState;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.views.AmapAjxView.AttributeListener;
import com.autonavi.minimap.ajx3.views.AmapAjxView.BackCallback;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.json.JSONException;

/* renamed from: byw reason: default package */
/* compiled from: IdqPlusAjxLayerHandler */
public final class byw implements bzn, bzp {
    byx a;
    bzo b;
    bqk c;
    public a d;
    Context e;
    Activity f;
    int g = -1;
    int h = -1;
    int i = -1;
    String j;
    String k = "2";
    com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus.a l = new com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus.a() {
        private int b = -1;

        public final void a(String str, String str2, boolean z) {
            if (byw.this.d.c()) {
                byw.this.k = str;
                char c = 65535;
                switch (str.hashCode()) {
                    case 49:
                        if (str.equals("1")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 50:
                        if (str.equals("2")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 51:
                        if (str.equals("3")) {
                            c = 2;
                            break;
                        }
                        break;
                    case 52:
                        if (str.equals("4")) {
                            c = 3;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        if (!TextUtils.isEmpty(str2)) {
                            byw.this.g = a(str2);
                            byw.this.g = DimensionUtils.standardUnitToPixel(byw.this.e, (float) byw.this.g);
                        }
                        byw.this.b.a(false);
                        byw.this.c.a(false);
                        break;
                    case 1:
                        if (!TextUtils.isEmpty(str2)) {
                            byw.this.h = a(str2);
                            if (z) {
                                byw.this.h = DimensionUtils.standardUnitToPixel(byw.this.e, (float) byw.this.h);
                            }
                        }
                        byw.this.b.a(byw.this.d.q.getHeight() - byw.this.h);
                        byw.this.b.a(true);
                        byw.this.c.a(8, false);
                        byw.this.c.a(true);
                        break;
                    case 2:
                        if (!TextUtils.isEmpty(str2)) {
                            byw.this.i = a(str2);
                            byw.this.i = DimensionUtils.standardUnitToPixel(byw.this.e, (float) byw.this.i);
                        }
                        byw.this.b.a(byw.this.d.q.getHeight() - byw.this.i);
                        byw.this.b.a(true);
                        byw.this.c.b(0);
                        break;
                    case 3:
                        byw.this.c.h();
                        byw.this.c.b(8);
                        break;
                }
                byw.this.j = str;
            }
        }

        public final void a(String str, String str2) {
            if (byw.this.d.c()) {
                char c = 65535;
                switch (str.hashCode()) {
                    case 49:
                        if (str.equals("1")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 50:
                        if (str.equals("2")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 51:
                        if (str.equals("3")) {
                            c = 2;
                            break;
                        }
                        break;
                    case 52:
                        if (str.equals("4")) {
                            c = 3;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        if (!TextUtils.isEmpty(str2)) {
                            byw.this.g = a(str2);
                            byw.this.g = DimensionUtils.standardUnitToPixel(byw.this.e, (float) byw.this.g);
                            break;
                        }
                        break;
                    case 1:
                        if (!TextUtils.isEmpty(str2)) {
                            byw.this.h = a(str2);
                            byw.this.h = DimensionUtils.standardUnitToPixel(byw.this.e, (float) byw.this.h);
                            break;
                        }
                        break;
                    case 2:
                        if (!TextUtils.isEmpty(str2)) {
                            byw.this.i = a(str2);
                            byw.this.i = DimensionUtils.standardUnitToPixel(byw.this.e, (float) byw.this.i);
                            break;
                        }
                        break;
                }
                byw.this.k = str;
                byw.this.c.b(8);
                byw.this.a(str);
            }
        }

        public final void a(int i) {
            if (byw.this.d.c()) {
                this.b = DimensionUtils.standardUnitToPixel(byw.this.e, (float) i);
                byw.this.b.a(this.b > byw.this.h + -5);
                StringBuilder sb = new StringBuilder("topH = ");
                sb.append(this.b);
                AMapLog.d("IdqPlus", sb.toString());
            }
        }

        private static int a(String str) {
            try {
                return (int) Float.parseFloat(str);
            } catch (NumberFormatException unused) {
                return 0;
            }
        }
    };
    private c m = new c() {
        public final void a() {
        }

        public final void a(String str) {
            if (!TextUtils.isEmpty(str) && byw.this.c.a().isAlive()) {
                byw.this.c.a(str);
            }
        }
    };
    private BackCallback n = new BackCallback() {
        public final void back(Object obj, String str) {
            byw.this.c.a().finish();
        }
    };

    /* renamed from: byw$a */
    /* compiled from: IdqPlusAjxLayerHandler */
    public class a extends defpackage.cbl.a {
        public a() {
        }

        @SuppressFBWarnings({"UW_UNCOND_WAIT", "WA_NOT_IN_LOOP"})
        public final void a(boolean z) {
            super.a(z);
            byw.this.b.a((bzn) byw.this);
            byw.this.c.a(this.s);
            byw.this.c.c(0);
            byw.this.c.a_(0);
            if (!z) {
                byw.this.l.a(byw.this.j, "");
                byw.this.l.a(byw.this.j, "", false);
            } else if (TextUtils.equals("2", byw.this.j)) {
                byw.this.l.a("2", "");
                byw.this.l.a("2", "", false);
            } else {
                if (TextUtils.equals("3", byw.this.j) && byw.this.i != -1) {
                    byw.this.b.a(this.q.getHeight() - byw.this.i);
                }
                byw.this.a.a((String) "2");
                byw.this.j = "2";
                synchronized (this) {
                    try {
                        wait(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public final void a(cbr cbr) {
    }

    public final DetailLayerState c() {
        return null;
    }

    public final boolean e() {
        return false;
    }

    public byw(@NonNull bzo bzo) {
        this.b = bzo;
        if (this.b != null) {
            this.c = this.b.c();
            if (this.c != null) {
                this.e = this.c.a().getContext();
                this.f = this.c.a().getActivity();
            }
        }
    }

    public final a a(InfoliteResult infoliteResult, PageBundle pageBundle) {
        try {
            if (this.a != null) {
                return this.d;
            }
            this.a = new byx(this.e);
            String jSONObject = bvd.a(infoliteResult, (AbstractBaseMapPage) this.b.c().a()).toString();
            this.d = new a();
            this.d.o = 8;
            POI poi = infoliteResult.searchInfo.l.get(0);
            pageBundle.putObject("POI", poi);
            this.d.t = new bxw(poi);
            a aVar = this.d;
            byx byx = this.a;
            FrameLayout b2 = this.b.b();
            bxw bxw = this.d.t;
            byx.h = b2;
            LayoutParams layoutParams = new LayoutParams(-1, -1);
            if (byx.g.getParent() == null) {
                b2.addView(byx.g);
            }
            if (byx.b.getParent() == null) {
                b2.addView(byx.b, layoutParams);
            }
            byx.b.setAttributeListener(new AttributeListener() {
                public final boolean handleAttr(String str, Object obj) {
                    int i = 0;
                    if (!"IDQPLUS_TOP".equalsIgnoreCase(str)) {
                        return false;
                    }
                    ModuleIdqPlus moduleIdqPlus = (ModuleIdqPlus) byx.this.b.getJsModule(ModuleIdqPlus.MODULE_NAME);
                    if (moduleIdqPlus != null) {
                        if (obj instanceof Float) {
                            i = Math.round(((Float) obj).floatValue());
                        }
                        moduleIdqPlus.onTopHeightChange(i);
                    }
                    return true;
                }
            });
            byx.b.onAjxContextCreated(byx.i);
            byx.b.setLoadingCallback(byx.k);
            byx.b.setAjxFragmentResultExecutor(bxw);
            byx.b.load("path://amap_bundle_idqplus/src/idqframework/IDQPlusPage.tsx.js", jSONObject, "Idqplus");
            byx.b.setVisibility(0);
            aVar.q = byx.b;
            this.d.t.a((AbstractBaseMapPage) this.c.a(), pageBundle);
            this.a.a(pageBundle);
            byx byx2 = this.a;
            com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus.a aVar2 = this.l;
            if (byx2.a() == null) {
                byx2.d = aVar2;
            } else {
                byx2.a().setOnStateChangeListener(aVar2);
            }
            byx byx3 = this.a;
            c cVar = this.m;
            if (byx3.a() == null) {
                byx3.e = cVar;
            } else {
                byx3.a().setOverlayEventListener(cVar);
            }
            byx byx4 = this.a;
            BackCallback backCallback = this.n;
            if (byx4.b != null) {
                byx4.b.setBackCallBack(backCallback);
            }
            byx byx5 = this.a;
            int a2 = ccc.a(this.f);
            if (byx5.g != null) {
                byx5.g.setLoadingAnimViewH(a2);
            }
            aho.a(new Runnable() {
                public final void run() {
                    byw.this.l.a("2", String.valueOf(ccc.a(byw.this.f) - byw.this.a.g.getLoadingAnimViewH()), false);
                }
            });
            return this.d;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public final void i() {
        if (this.d == null || this.d.t == null) {
            this.a.b(null);
        } else {
            this.a.b(this.d.t.a);
            this.d.t.a = null;
        }
        defpackage.bzj.a c2 = this.b.g_().c();
        if (c2 != null && c2.e != null) {
            this.b.c().a(c2.e);
        }
    }

    public final void j() {
        byx byx = this.a;
        if (byx.b != null) {
            byx.b.onPause(false);
        }
    }

    public final void k() {
        byx byx = this.a;
        if (byx.b != null) {
            byx.b.onDestroy();
        }
        byx.a = false;
    }

    public final boolean a() {
        byx byx = this.a;
        if (byx.b != null) {
            byx.b.backPressed();
        }
        return true;
    }

    public final void a(AbstractBasePage abstractBasePage, int i2, ResultType resultType, PageBundle pageBundle) {
        JsAdapter b2 = this.a.b();
        if (b2 != null) {
            this.d.t.onFragmentResult(abstractBasePage, i2, resultType, pageBundle, b2);
        }
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.graphics.Point b() {
        /*
            r5 = this;
            android.graphics.Point r0 = new android.graphics.Point
            r0.<init>()
            android.content.Context r1 = r5.e
            android.graphics.Rect r1 = defpackage.ags.a(r1)
            int r1 = r1.width()
            r2 = 2
            int r1 = r1 / r2
            r0.x = r1
            android.content.Context r1 = r5.e
            android.content.res.Resources r1 = r1.getResources()
            int r3 = com.autonavi.minimap.R.dimen.v3_bottom_bar_height
            int r1 = r1.getDimensionPixelOffset(r3)
            java.lang.String r3 = r5.k
            int r4 = r3.hashCode()
            switch(r4) {
                case 49: goto L_0x0047;
                case 50: goto L_0x003d;
                case 51: goto L_0x0033;
                case 52: goto L_0x0029;
                default: goto L_0x0028;
            }
        L_0x0028:
            goto L_0x0051
        L_0x0029:
            java.lang.String r4 = "4"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0051
            r3 = 3
            goto L_0x0052
        L_0x0033:
            java.lang.String r4 = "3"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0051
            r3 = 2
            goto L_0x0052
        L_0x003d:
            java.lang.String r4 = "2"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0051
            r3 = 1
            goto L_0x0052
        L_0x0047:
            java.lang.String r4 = "1"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0051
            r3 = 0
            goto L_0x0052
        L_0x0051:
            r3 = -1
        L_0x0052:
            switch(r3) {
                case 0: goto L_0x0065;
                case 1: goto L_0x005e;
                case 2: goto L_0x0057;
                default: goto L_0x0055;
            }
        L_0x0055:
            r0 = 0
            return r0
        L_0x0057:
            int r3 = r5.i
            int r3 = r3 / r2
            int r3 = r3 + r1
            r0.y = r3
            goto L_0x006b
        L_0x005e:
            int r3 = r5.h
            int r3 = r3 / r2
            int r3 = r3 + r1
            r0.y = r3
            goto L_0x006b
        L_0x0065:
            int r3 = r5.g
            int r3 = r3 / r2
            int r3 = r3 + r1
            r0.y = r3
        L_0x006b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.byw.b():android.graphics.Point");
    }

    public final String d() {
        return this.k;
    }

    public final void a(boolean z) {
        this.d.r = z;
        byx byx = this.a;
        int i2 = z ? 0 : 8;
        if (byx.b != null) {
            byx.b.setVisibility(i2);
        }
    }

    public final void f() {
        a(this.k);
    }

    public final void g() {
        if (this.a != null && !"3".equals(this.k)) {
            this.a.a((String) "3");
        }
    }

    public final void a(ViewGroup viewGroup) {
        cbl e_ = this.b.e_();
        if (e_ != null && viewGroup != null) {
            e_.a((ViewGroup) null, e_.b());
            b bVar = new b(this);
            bVar.o = 0;
            e_.a((defpackage.cbl.a) bVar, viewGroup);
        }
    }

    public final int h() {
        return this.h;
    }

    /* access modifiers changed from: 0000 */
    public final void a(String str) {
        defpackage.bzj.a c2 = this.b.g_().c();
        if (c2 != null) {
            if ("2".equals(str)) {
                this.c.h();
                this.b.c().a().getMapView().d(c2.c);
                this.b.c().a(c2.e);
                return;
            }
            if ("3".equals(str)) {
                this.c.h();
                this.b.c().a().getMapView().d(c2.d);
                this.b.c().a(c2.e);
            }
        }
    }

    public final void a(b bVar) {
        if (bVar.o == 0) {
            this.b.h_();
        }
    }

    public final void m() {
        this.c.a().finish();
    }
}
