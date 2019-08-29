package com.autonavi.bundle.routecommute.common;

import android.location.Location;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import com.amap.bundle.webview.page.WebViewPage;
import com.autonavi.bundle.routecommute.bus.details.BusCommuteDetailsPage;
import com.autonavi.bundle.routecommute.bus.details.BusCommuteListPage;
import com.autonavi.bundle.routecommute.common.bean.CommuteControlBean;
import com.autonavi.bundle.routecommute.common.bean.NaviAddress;
import com.autonavi.bundle.routecommute.drive.page.AjxDriveCommuteEndPage;
import com.autonavi.bundle.routecommute.drive.page.AjxDriveCommutePage;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.impl.Locator.Status;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.sdk.location.LocationInstrument;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public final class CommuteHelper implements axv, lp {
    public static String a = "RouteCommute";
    public bao b;
    public AbstractBaseMapPage c;
    public String d = "0";
    private azh e;
    private azm f;
    private LocationChangeListener g;
    private ban h;
    private boolean i;
    private String j;
    private boolean k;
    private boolean l;
    private String m;
    private boolean n;

    static class LocationChangeListener implements Callback<Status> {
        private WeakReference<CommuteHelper> mCommuteHelper;
        private long mLastCallbackTimeStamp = 0;
        private GeoPoint mLastGeoPoint;
        private float mMaxLength;
        private long mMaxTimeMs;

        public void error(Throwable th, boolean z) {
        }

        public LocationChangeListener(float f, long j, CommuteHelper commuteHelper) {
            this.mCommuteHelper = new WeakReference<>(commuteHelper);
            this.mMaxLength = f;
            this.mMaxTimeMs = j;
        }

        public void callback(Status status) {
            if (status == Status.ON_LOCATION_OK && SystemClock.elapsedRealtime() - this.mLastCallbackTimeStamp >= this.mMaxTimeMs) {
                CommuteHelper commuteHelper = (CommuteHelper) this.mCommuteHelper.get();
                if (commuteHelper != null && (commuteHelper.u() || (azk.a() && azk.b()))) {
                    Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
                    if (this.mLastGeoPoint == null) {
                        this.mLastGeoPoint = new GeoPoint(latestLocation.getLongitude(), latestLocation.getLatitude());
                        this.mLastCallbackTimeStamp = SystemClock.elapsedRealtime();
                        commuteHelper.a(this.mLastGeoPoint);
                        return;
                    }
                    if (bno.a) {
                        String str = CommuteHelper.a;
                        StringBuilder sb = new StringBuilder("handlerCommute lastPoint Longitude = ");
                        sb.append(this.mLastGeoPoint.getLongitude());
                        sb.append(", Latitude = ");
                        sb.append(this.mLastGeoPoint.getLatitude());
                        azb.a(str, sb.toString());
                        String str2 = CommuteHelper.a;
                        StringBuilder sb2 = new StringBuilder("handlerCommute current Longitude = ");
                        sb2.append(latestLocation.getLongitude());
                        sb2.append(", Latitude = ");
                        sb2.append(latestLocation.getLatitude());
                        azb.a(str2, sb2.toString());
                    }
                    GeoPoint geoPoint = new GeoPoint(this.mLastGeoPoint.getLongitude(), this.mLastGeoPoint.getLatitude());
                    GeoPoint geoPoint2 = new GeoPoint(latestLocation.getLongitude(), latestLocation.getLatitude());
                    if (cfe.a(geoPoint, geoPoint2) >= this.mMaxLength) {
                        this.mLastGeoPoint = geoPoint2;
                        this.mLastCallbackTimeStamp = SystemClock.elapsedRealtime();
                        CommuteHelper.b(commuteHelper, geoPoint2);
                    }
                    if (commuteHelper.p()) {
                        commuteHelper.o().a(geoPoint2);
                    }
                    if (commuteHelper.s()) {
                        commuteHelper.r().a(geoPoint2);
                    }
                }
            }
        }
    }

    public final void a(int i2) {
    }

    public final int m() {
        return 0;
    }

    public final void onConfigCallBack(int i2) {
    }

    /* access modifiers changed from: private */
    public void a(GeoPoint geoPoint) {
        if (!azi.p() && this.n && geoPoint != null) {
            if (this.e == null) {
                this.e = new azh(this);
            }
            azh azh = this.e;
            String str = this.j;
            azb.a(a, "NormalRulesHandler handle newPoint = ".concat(String.valueOf(geoPoint)));
            if (!azh.a.j() || a.a.a == null) {
                azh.a.t();
                azh.a.q();
                return;
            }
            azh.a.v();
            azf.a((a) new a(geoPoint, str) {
                final /* synthetic */ GeoPoint a;
                final /* synthetic */ String b;

                {
                    this.a = r2;
                    this.b = r3;
                }

                public final void a(final NaviAddress naviAddress) {
                    if (Looper.myLooper() == Looper.getMainLooper()) {
                        azh.a(azh.this, this.a, naviAddress, this.b);
                    } else {
                        aho.a(new Runnable() {
                            public final void run() {
                                azh.a(azh.this, AnonymousClass1.this.a, naviAddress, AnonymousClass1.this.b);
                            }
                        });
                    }
                }
            });
        }
    }

    public final void k() {
        this.j = "";
        if (u() && this.n) {
            a(LocationInstrument.getInstance().getLatestPosition(5));
        }
    }

    public final void a(String str, String str2, String str3) {
        this.d = str3;
        this.j = str;
        this.m = str2;
        if (this.n) {
            if (this.f == null) {
                this.f = new azm(this);
            }
            String str4 = a;
            StringBuilder sb = new StringBuilder("CommuteHelper handlerSchemeCommute type = ");
            sb.append(str);
            sb.append(", distance = ");
            sb.append(str2);
            azb.a(str4, sb.toString());
            this.f.a(str, str2, str3);
        }
    }

    public final void a(boolean z) {
        if (s()) {
            r().a(z);
        }
    }

    public final void l() {
        if (s()) {
            r().i();
        }
        if (p()) {
            o().i();
        }
    }

    public final void b(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                azb.a(a, "clearupViewStackForCommute paramString".concat(String.valueOf(str)));
                return;
            }
            String optString = new JSONObject(str).optString("from");
            if ("3".equals(optString)) {
                ArrayList<bid> pageContextStacks = AMapPageUtil.getPageContextStacks();
                if (!(pageContextStacks == null || pageContextStacks.size() == 0)) {
                    boolean z = false;
                    for (int size = pageContextStacks.size() - 1; size >= 0; size--) {
                        bid bid = pageContextStacks.get(size);
                        if (bid != null) {
                            if (!(bid instanceof AjxDriveCommutePage) && !(bid instanceof AjxDriveCommuteEndPage) && !(bid instanceof BusCommuteListPage)) {
                                if (!(bid instanceof BusCommuteDetailsPage)) {
                                    if (bid instanceof WebViewPage) {
                                        if (!z) {
                                            z = true;
                                        } else {
                                            bid.finish();
                                        }
                                    }
                                }
                            }
                            bid.finish();
                        }
                    }
                }
                return;
            }
            if (!"4".equals(optString) && !"5".equals(optString)) {
                AMapPageUtil.getPageContext().startPage((String) "amap.basemap.action.default_page", new PageBundle());
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final void a(AbstractBaseMapPage abstractBaseMapPage) {
        if (abstractBaseMapPage != null) {
            this.c = abstractBaseMapPage;
            this.d = "0";
            String a2 = lo.a().a((String) "commute_config");
            if (TextUtils.isEmpty(a2)) {
                a2 = azi.m();
            }
            azb.a(a, "handlerCommute updateConfig from init  ");
            d(a2);
            x();
        }
    }

    public final void a() {
        azb.a(a, "onDefaultPageCreate >>> ");
    }

    public final void b() {
        azb.a(a, "onDefaultPageResume >>> ");
        w();
        if (this.i) {
            this.i = false;
        } else {
            this.d = "0";
            a(LocationInstrument.getInstance().getLatestPosition(5));
        }
        if (p()) {
            azb.a(a, "onDefaultPageResume DriveCommute ");
            o().b();
        }
        if (s()) {
            azb.a(a, "onDefaultPageResume BusCommute ");
            r().b();
        }
    }

    public final void c() {
        azb.a(a, "onDefaultPagePause >>> ");
        if (p()) {
            azb.a(a, "onDefaultPagePause DriveCommute");
            o().c();
        }
        if (s()) {
            azb.a(a, "onDefaultPagePause BusCommute");
            r().c();
        }
        y();
    }

    public final void d() {
        azb.a(a, "onDefaultPageDestroy >>>  ");
        if (p()) {
            azb.a(a, "onDefaultPageDestroy DriveCommute  ");
            o().d();
        }
        if (s()) {
            azb.a(a, "onDefaultPageDestroy BusCommute  ");
            r().d();
        }
        azb.a(a, "destroyCommute >>> ");
        q();
        t();
        z();
        a.a.a = null;
        this.g = null;
        this.e = null;
        this.f = null;
        this.c = null;
        this.j = null;
        this.i = true;
    }

    public final void e() {
        if (p()) {
            azb.a(a, "onTipOrCQShow DriveCommute  ");
            o().a.f();
        }
        if (s()) {
            azb.a(a, "onTipOrCQShow BusCommute  ");
            r().e();
        }
    }

    public final void f() {
        if (p()) {
            azb.a(a, "onTipOrCQShow DriveCommute  ");
            o();
        }
        if (s()) {
            azb.a(a, "onTipOrCQShow BusCommute  ");
            r().f();
        }
    }

    public final void g() {
        if (p()) {
            azb.a(a, "onTrafficViewShow DriveCommute  ");
            o().g();
        }
        if (s()) {
            azb.a(a, "onTrafficViewShow BusCommute  ");
            r().g();
        }
    }

    public final void h() {
        if (p()) {
            azb.a(a, "onTrafficViewHide DriveCommute  ");
            o();
        }
        if (s()) {
            azb.a(a, "onTrafficViewHide BusCommute  ");
            r().h();
        }
    }

    private void v() {
        if (j()) {
            brm brm = (brm) ank.a(brm.class);
            if (brm != null) {
                brm.j();
            }
            azb.a(a, "closeOldCommute ");
        }
    }

    private void d(String str) {
        azb.a(a, "updateConfig config result = ".concat(String.valueOf(str)));
        if (!TextUtils.isEmpty(str)) {
            azi.b(str);
            if (a.a.a(str)) {
                if (j()) {
                    w();
                    x();
                } else {
                    y();
                    z();
                }
                v();
            }
        }
    }

    public final bao o() {
        if (this.b == null) {
            this.b = new bao(a.a.a != null ? a.a.a.carBubbleRule : null);
            this.b.a(this.c);
        }
        return this.b;
    }

    public final boolean p() {
        return this.b != null;
    }

    public final void q() {
        if (this.b != null) {
            this.b.a();
            this.b = null;
        }
    }

    public final baq r() {
        if (this.h == null) {
            this.h = new ban();
            this.h.a(this.c);
        }
        return this.h;
    }

    public final boolean s() {
        return this.h != null;
    }

    public final void t() {
        if (this.h != null) {
            this.h.a();
            this.h = null;
        }
    }

    private void w() {
        if (!this.k) {
            this.k = true;
            if (this.g == null) {
                this.g = new LocationChangeListener(30.0f, 1000, this);
            }
            LocationInstrument.getInstance().addStatusCallback(this.g, null);
        }
    }

    private void x() {
        if (!this.l) {
            this.l = true;
            lo.a().a((String) "commute_config", (lp) this);
        }
    }

    public final boolean u() {
        return this.c != null && this.c.isResumed();
    }

    private void y() {
        this.k = false;
        if (this.g != null) {
            LocationInstrument.getInstance().removeStatusCallback(this.g);
        }
    }

    private void z() {
        this.l = false;
        lo.a().b("commute_config", this);
    }

    public final void onConfigResultCallBack(int i2, String str) {
        azb.a(a, "onConfigResultCallBack config result = ".concat(String.valueOf(str)));
        boolean equals = TextUtils.equals(azi.m(), str);
        azb.a(a, "onConfigResultCallBack config equals = ".concat(String.valueOf(equals)));
        if (!equals) {
            azb.a(a, "handlerCommute updateConfig from onConfigResultCallBack  ");
            d(str);
            a(LocationInstrument.getInstance().getLatestPosition(5));
        }
    }

    public final void i() {
        this.n = true;
        if (TextUtils.equals(this.d, "0")) {
            k();
        } else {
            a(this.j, this.m, this.d);
        }
    }

    public final boolean j() {
        return a.a.a != null && a.a.a.isCommuteSwitch();
    }

    public final boolean a(String str) {
        return a.a.a != null && a.a.a.isOperateEventEnable(str);
    }

    public static CommuteControlBean n() {
        return a.a.a;
    }

    public static void c(String str) {
        axw axw = (axw) a.a.a(axw.class);
        if (axw != null) {
            axw.a(AMapPageUtil.getPageContext(), str);
        }
    }

    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v1, types: [com.autonavi.common.model.POI] */
    /* JADX WARNING: type inference failed for: r1v4, types: [com.autonavi.common.model.POI] */
    /* JADX WARNING: type inference failed for: r1v5, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r1v6, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], java.lang.String, com.autonavi.common.model.POI]
      uses: [com.autonavi.common.model.POI, java.lang.String]
      mth insns count: 61
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void b(com.autonavi.bundle.routecommute.common.CommuteHelper r4, com.autonavi.common.model.GeoPoint r5) {
        /*
            azh r0 = r4.e
            if (r0 == 0) goto L_0x008f
            azh r4 = r4.e
            com.autonavi.bundle.routecommute.common.CommuteHelper r0 = r4.a
            boolean r0 = r0.p()
            r1 = 0
            if (r0 == 0) goto L_0x0038
            azn r0 = defpackage.azn.a.a
            com.autonavi.bundle.routecommute.common.bean.CommuteControlBean r0 = r0.a
            if (r0 == 0) goto L_0x001b
            azn r0 = defpackage.azn.a.a
            com.autonavi.bundle.routecommute.common.bean.CommuteControlBean r0 = r0.a
            java.lang.String r1 = r0.carBubbleRule
        L_0x001b:
            com.autonavi.bundle.routecommute.common.bean.NaviAddress r0 = r4.b
            boolean r5 = defpackage.azk.a(r1, r0, r5)
            if (r5 != 0) goto L_0x0028
            com.autonavi.bundle.routecommute.common.CommuteHelper r4 = r4.a
            r4.q()
        L_0x0028:
            java.lang.String r4 = a
            java.lang.String r0 = "NormalRulesHandler checkDistanceRules isCarRule "
            java.lang.String r5 = java.lang.String.valueOf(r5)
            java.lang.String r5 = r0.concat(r5)
            defpackage.azb.a(r4, r5)
            return
        L_0x0038:
            com.autonavi.bundle.routecommute.common.CommuteHelper r0 = r4.a
            boolean r0 = r0.s()
            if (r0 == 0) goto L_0x008f
            azn r0 = defpackage.azn.a.a
            com.autonavi.bundle.routecommute.common.bean.CommuteControlBean r0 = r0.a
            if (r0 == 0) goto L_0x004d
            azn r0 = defpackage.azn.a.a
            com.autonavi.bundle.routecommute.common.bean.CommuteControlBean r0 = r0.a
            java.lang.String r0 = r0.busBubbleRule
            goto L_0x004e
        L_0x004d:
            r0 = r1
        L_0x004e:
            com.autonavi.bundle.routecommute.common.bean.NaviAddress r2 = r4.b
            if (r2 == 0) goto L_0x0061
            com.autonavi.bundle.routecommute.common.bean.NaviAddress r2 = r4.b
            com.autonavi.bundle.routecommute.common.bean.NaviAddressHome r2 = r2.home
            if (r2 == 0) goto L_0x0061
            com.autonavi.bundle.routecommute.common.bean.NaviAddress r2 = r4.b
            com.autonavi.bundle.routecommute.common.bean.NaviAddressHome r2 = r2.home
            com.autonavi.common.model.POI r2 = r2.getHome()
            goto L_0x0062
        L_0x0061:
            r2 = r1
        L_0x0062:
            com.autonavi.bundle.routecommute.common.bean.NaviAddress r3 = r4.b
            if (r3 == 0) goto L_0x0074
            com.autonavi.bundle.routecommute.common.bean.NaviAddress r3 = r4.b
            com.autonavi.bundle.routecommute.common.bean.NaviAddressCompany r3 = r3.company
            if (r3 == 0) goto L_0x0074
            com.autonavi.bundle.routecommute.common.bean.NaviAddress r1 = r4.b
            com.autonavi.bundle.routecommute.common.bean.NaviAddressCompany r1 = r1.company
            com.autonavi.common.model.POI r1 = r1.getCompany()
        L_0x0074:
            r3 = 0
            boolean r5 = defpackage.ayq.a(r0, r2, r1, r5, r3)
            if (r5 != 0) goto L_0x0080
            com.autonavi.bundle.routecommute.common.CommuteHelper r4 = r4.a
            r4.t()
        L_0x0080:
            java.lang.String r4 = a
            java.lang.String r0 = "NormalRulesHandler checkDistanceRules isBusDistanceRule "
            java.lang.String r5 = java.lang.String.valueOf(r5)
            java.lang.String r5 = r0.concat(r5)
            defpackage.azb.a(r4, r5)
        L_0x008f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.routecommute.common.CommuteHelper.b(com.autonavi.bundle.routecommute.common.CommuteHelper, com.autonavi.common.model.GeoPoint):void");
    }
}
