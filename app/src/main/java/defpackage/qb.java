package defpackage;

import android.annotation.TargetApi;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.List;

/* renamed from: qb reason: default package */
/* compiled from: DriveRouteInputController */
public final class qb {
    protected AbstractBasePage a;
    protected IRouteUI b;
    protected View c;
    protected Handler d = new Handler(Looper.getMainLooper());
    protected boolean e = false;
    protected POI f;
    protected POI g;
    protected List<POI> h;
    protected boolean i = false;
    protected String j = "";

    /* renamed from: qb$5 reason: invalid class name */
    /* compiled from: DriveRouteInputController */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] b = new int[RouteType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(26:0|(2:1|2)|3|(2:5|6)|7|(2:9|10)|11|(2:13|14)|15|(2:17|18)|19|21|22|(2:23|24)|25|27|28|29|30|31|32|33|34|35|36|(3:37|38|40)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(29:0|1|2|3|(2:5|6)|7|(2:9|10)|11|13|14|15|(2:17|18)|19|21|22|23|24|25|27|28|29|30|31|32|33|34|35|36|(3:37|38|40)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(31:0|1|2|3|5|6|7|(2:9|10)|11|13|14|15|17|18|19|21|22|23|24|25|27|28|29|30|31|32|33|34|35|36|(3:37|38|40)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x0069 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x0073 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x007d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:35:0x0087 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:37:0x0091 */
        static {
            /*
                com.autonavi.bundle.routecommon.model.RouteType[] r0 = com.autonavi.bundle.routecommon.model.RouteType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                b = r0
                r0 = 1
                int[] r1 = b     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.BUS     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = b     // Catch:{ NoSuchFieldError -> 0x001f }
                com.autonavi.bundle.routecommon.model.RouteType r3 = com.autonavi.bundle.routecommon.model.RouteType.ONFOOT     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                r2 = 3
                int[] r3 = b     // Catch:{ NoSuchFieldError -> 0x002a }
                com.autonavi.bundle.routecommon.model.RouteType r4 = com.autonavi.bundle.routecommon.model.RouteType.RIDE     // Catch:{ NoSuchFieldError -> 0x002a }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r3[r4] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                r3 = 4
                int[] r4 = b     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.autonavi.bundle.routecommon.model.RouteType r5 = com.autonavi.bundle.routecommon.model.RouteType.TRAIN     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r4[r5] = r3     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                r4 = 5
                int[] r5 = b     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.autonavi.bundle.routecommon.model.RouteType r6 = com.autonavi.bundle.routecommon.model.RouteType.TRUCK     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r6 = r6.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r5[r6] = r4     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                r5 = 6
                int[] r6 = b     // Catch:{ NoSuchFieldError -> 0x004b }
                com.autonavi.bundle.routecommon.model.RouteType r7 = com.autonavi.bundle.routecommon.model.RouteType.CAR     // Catch:{ NoSuchFieldError -> 0x004b }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
                r6[r7] = r5     // Catch:{ NoSuchFieldError -> 0x004b }
            L_0x004b:
                int[] r6 = b     // Catch:{ NoSuchFieldError -> 0x0056 }
                com.autonavi.bundle.routecommon.model.RouteType r7 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP     // Catch:{ NoSuchFieldError -> 0x0056 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x0056 }
                r8 = 7
                r6[r7] = r8     // Catch:{ NoSuchFieldError -> 0x0056 }
            L_0x0056:
                com.autonavi.common.utils.Constant$SelectPoiFromMapFragment$SelectFor[] r6 = com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor.values()
                int r6 = r6.length
                int[] r6 = new int[r6]
                a = r6
                int[] r6 = a     // Catch:{ NoSuchFieldError -> 0x0069 }
                com.autonavi.common.utils.Constant$SelectPoiFromMapFragment$SelectFor r7 = com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor.FROM_POI     // Catch:{ NoSuchFieldError -> 0x0069 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x0069 }
                r6[r7] = r0     // Catch:{ NoSuchFieldError -> 0x0069 }
            L_0x0069:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0073 }
                com.autonavi.common.utils.Constant$SelectPoiFromMapFragment$SelectFor r6 = com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor.TO_POI     // Catch:{ NoSuchFieldError -> 0x0073 }
                int r6 = r6.ordinal()     // Catch:{ NoSuchFieldError -> 0x0073 }
                r0[r6] = r1     // Catch:{ NoSuchFieldError -> 0x0073 }
            L_0x0073:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x007d }
                com.autonavi.common.utils.Constant$SelectPoiFromMapFragment$SelectFor r1 = com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor.MID_POI     // Catch:{ NoSuchFieldError -> 0x007d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x007d }
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x007d }
            L_0x007d:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0087 }
                com.autonavi.common.utils.Constant$SelectPoiFromMapFragment$SelectFor r1 = com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor.MID_POI_1     // Catch:{ NoSuchFieldError -> 0x0087 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0087 }
                r0[r1] = r3     // Catch:{ NoSuchFieldError -> 0x0087 }
            L_0x0087:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0091 }
                com.autonavi.common.utils.Constant$SelectPoiFromMapFragment$SelectFor r1 = com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor.MID_POI_2     // Catch:{ NoSuchFieldError -> 0x0091 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0091 }
                r0[r1] = r4     // Catch:{ NoSuchFieldError -> 0x0091 }
            L_0x0091:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x009b }
                com.autonavi.common.utils.Constant$SelectPoiFromMapFragment$SelectFor r1 = com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor.MID_POI_3     // Catch:{ NoSuchFieldError -> 0x009b }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x009b }
                r0[r1] = r5     // Catch:{ NoSuchFieldError -> 0x009b }
            L_0x009b:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.qb.AnonymousClass5.<clinit>():void");
        }
    }

    public qb(@NonNull AbstractBasePage abstractBasePage) {
        this.a = abstractBasePage;
    }

    public final void a() {
        if (this.b == null) {
            this.b = b();
        }
    }

    public final IRouteUI b() {
        if (this.b == null && this.a != null) {
            axd axd = (axd) this.a.getContentView().getParent();
            if (axd == null) {
                this.d.post(new Runnable() {
                    public final void run() {
                        axd axd = (axd) qb.this.a.getContentView().getParent();
                        if (axd != null) {
                            qb.this.b = axd.getRouteInputUI();
                        }
                    }
                });
                return null;
            }
            this.b = axd.getRouteInputUI();
        }
        return this.b;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0064, code lost:
        r1 = r7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor r7, com.autonavi.common.model.POI r8) {
        /*
            r6 = this;
            r0 = 0
            if (r8 == 0) goto L_0x0077
            if (r7 != 0) goto L_0x0007
            goto L_0x0077
        L_0x0007:
            com.autonavi.bundle.routecommon.inter.IRouteUI r1 = r6.b()
            if (r1 != 0) goto L_0x000e
            return r0
        L_0x000e:
            com.autonavi.common.model.POI r2 = r1.d()
            com.autonavi.common.model.POI r3 = r1.f()
            java.util.List r1 = r1.e()
            int[] r4 = defpackage.qb.AnonymousClass5.a
            int r7 = r7.ordinal()
            r7 = r4[r7]
            r4 = 1
            r5 = 2
            switch(r7) {
                case 1: goto L_0x0071;
                case 2: goto L_0x006f;
                case 3: goto L_0x0066;
                case 4: goto L_0x0051;
                case 5: goto L_0x003d;
                case 6: goto L_0x0028;
                default: goto L_0x0027;
            }
        L_0x0027:
            goto L_0x0072
        L_0x0028:
            if (r1 == 0) goto L_0x0072
            int r7 = r1.size()
            r0 = 3
            if (r7 < r0) goto L_0x0072
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>(r1)
            r7.remove(r5)
            r7.add(r5, r8)
            goto L_0x0064
        L_0x003d:
            if (r1 == 0) goto L_0x0072
            int r7 = r1.size()
            if (r7 < r5) goto L_0x0072
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>(r1)
            r7.remove(r4)
            r7.add(r4, r8)
            goto L_0x0064
        L_0x0051:
            if (r1 == 0) goto L_0x0072
            int r7 = r1.size()
            if (r7 <= 0) goto L_0x0072
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>(r1)
            r7.remove(r0)
            r7.add(r0, r8)
        L_0x0064:
            r1 = r7
            goto L_0x0072
        L_0x0066:
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r1.add(r8)
            goto L_0x0072
        L_0x006f:
            r3 = r8
            goto L_0x0072
        L_0x0071:
            r2 = r8
        L_0x0072:
            boolean r7 = r6.a(r2, r3, r1, r4)
            return r7
        L_0x0077:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.qb.a(com.autonavi.common.utils.Constant$SelectPoiFromMapFragment$SelectFor, com.autonavi.common.model.POI):boolean");
    }

    private boolean a(POI poi, POI poi2, List<POI> list, boolean z) {
        if (list != null && !list.isEmpty()) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                POI poi3 = list.get(i2);
                if (c(poi3)) {
                    if (poi != null && bnx.a(poi, poi3)) {
                        this.j = this.a.getString(R.string.route_same_mid_to);
                        if (z) {
                            ToastHelper.showLongToast(this.j);
                        }
                        return false;
                    } else if (poi2 == null || !bnx.a(poi2, poi3)) {
                        int i3 = i2 + 1;
                        while (i3 < list.size()) {
                            POI poi4 = list.get(i3);
                            if (!c(poi4) || !bnx.a(poi3, poi4)) {
                                i3++;
                            } else {
                                this.j = this.a.getString(R.string.drive_route_same_mids);
                                if (z) {
                                    ToastHelper.showLongToast(this.j);
                                }
                                return false;
                            }
                        }
                        continue;
                    } else {
                        this.j = this.a.getString(R.string.route_same_mid_to);
                        if (z) {
                            ToastHelper.showLongToast(this.j);
                        }
                        return false;
                    }
                }
            }
            return true;
        } else if (poi == null || poi2 == null) {
            return true;
        } else {
            boolean z2 = !bnx.a(poi, poi2);
            if (!z2) {
                this.j = this.a.getString(R.string.route_same_from_to);
                if (z) {
                    ToastHelper.showLongToast(this.j);
                }
            }
            return z2;
        }
    }

    public final boolean a(boolean z) {
        IRouteUI b2 = b();
        if (b2 == null) {
            return false;
        }
        POI d2 = b2.d();
        POI f2 = b2.f();
        List<POI> e2 = b2.e();
        if (!c(d2)) {
            this.j = this.a.getString(R.string.drive_route_start_empty);
            if (z) {
                ToastHelper.showLongToast(this.j);
            }
            return false;
        } else if (!c(f2)) {
            this.j = this.a.getString(R.string.drive_route_end_empty);
            if (z) {
                ToastHelper.showLongToast(this.j);
            }
            return false;
        } else {
            if (e2 != null) {
                for (int i2 = 0; i2 < e2.size(); i2++) {
                    if (!c(e2.get(i2))) {
                        return false;
                    }
                }
            }
            return a(d2, f2, e2, z);
        }
    }

    public final void a(final axe axe) {
        IRouteUI b2 = b();
        if (b2 != null) {
            b2.a(axe);
        } else {
            this.d.post(new Runnable() {
                public final void run() {
                    IRouteUI b2 = qb.this.b();
                    if (b2 != null) {
                        b2.a(axe);
                    }
                }
            });
        }
    }

    public final void c() {
        IRouteUI b2 = b();
        if (b2 != null) {
            b2.a((axe) null);
        }
    }

    private void b(SelectFor selectFor, POI poi) {
        IRouteUI b2 = b();
        if (b2 != null) {
            if (selectFor == SelectFor.FROM_POI) {
                b2.a(poi);
            } else if (selectFor == SelectFor.TO_POI) {
                b2.b(poi);
            } else if (selectFor == SelectFor.MID_POI) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(poi);
                b2.a((List<POI>) arrayList);
            } else if (selectFor == SelectFor.MID_POI_1) {
                ArrayList arrayList2 = new ArrayList(b2.e());
                arrayList2.remove(0);
                arrayList2.add(0, poi);
                b2.a((List<POI>) arrayList2);
            } else if (selectFor == SelectFor.MID_POI_2) {
                ArrayList arrayList3 = new ArrayList(b2.e());
                arrayList3.remove(1);
                arrayList3.add(1, poi);
                b2.a((List<POI>) arrayList3);
            } else {
                if (selectFor == SelectFor.MID_POI_3) {
                    ArrayList arrayList4 = new ArrayList(b2.e());
                    arrayList4.remove(2);
                    arrayList4.add(2, poi);
                    b2.a((List<POI>) arrayList4);
                }
            }
        }
    }

    public final boolean d() {
        POI n = n();
        POI o = o();
        List<POI> p = p();
        if (!c(n) || !c(o)) {
            return true;
        }
        if (p != null && !p.isEmpty()) {
            for (POI c2 : p) {
                if (c(c2)) {
                    return true;
                }
            }
            if (!a(n, o)) {
                return true;
            }
            ToastHelper.showLongToast(this.a.getString(R.string.drive_route_mid_empty));
            return false;
        } else if (!a(n, o)) {
            return true;
        } else {
            ToastHelper.showLongToast(this.a.getString(R.string.route_same_from_to));
            return false;
        }
    }

    public final boolean a(SelectFor selectFor, ResultType resultType, PageBundle pageBundle) {
        if (resultType == ResultType.OK) {
            if (pageBundle != null && pageBundle.containsKey("result_poi")) {
                POI poi = (POI) pageBundle.getObject("result_poi");
                if (!c(selectFor, poi) && a(selectFor, poi)) {
                    b(selectFor, poi);
                    return true;
                }
            }
        } else if (resultType == ResultType.CANCEL) {
            return false;
        }
        return false;
    }

    public final void e() {
        if (b() != null && this.c != null && this.c.getParent() != null) {
            qd.c(this.a.getContext(), this.c);
        }
    }

    public final void f() {
        IRouteUI b2 = b();
        if (b2 != null) {
            View c2 = b2.c();
            if (c2 != null) {
                qd.a(this.a.getContext(), c2);
            }
        }
    }

    @TargetApi(19)
    public final void g() {
        IRouteUI b2 = b();
        if (b2 != null) {
            View c2 = b2.c();
            if (c2 != null) {
                qd.b(this.a.getContext(), c2);
            }
        }
    }

    public final void h() {
        IRouteUI b2 = b();
        if (b2 != null) {
            b2.a(n());
            b2.b(o());
            this.e = false;
            this.f = null;
            this.g = null;
            this.h = null;
        }
    }

    public final void i() {
        if (!this.e) {
            if (b() != null) {
                this.f = b().d();
                this.g = b().f();
                this.h = t();
            } else {
                return;
            }
        }
        this.e = true;
    }

    @Nullable
    public final View j() {
        if (b() != null) {
            return b().c();
        }
        return null;
    }

    public final void k() {
        this.d.post(new Runnable() {
            final /* synthetic */ int a = 8;

            public final void run() {
                View j = qb.this.j();
                if (qb.this.b() != null) {
                    qb.this.b().a(false);
                }
                if (j != null && (j instanceof ViewGroup)) {
                    ViewGroup viewGroup = (ViewGroup) j;
                    for (int i = 0; i < viewGroup.getChildCount(); i++) {
                        viewGroup.getChildAt(i).setVisibility(this.a);
                    }
                }
            }
        });
    }

    public final void a(final View view) {
        this.d.post(new Runnable() {
            public final void run() {
                View j = qb.this.j();
                if (j != null && (j instanceof ViewGroup)) {
                    ((ViewGroup) j).addView(view);
                }
            }
        });
    }

    public final boolean l() {
        if (b() == null) {
            return false;
        }
        POI d2 = b().d();
        POI f2 = b().f();
        List<POI> t = t();
        if (!a(d2, this.f) || !a(f2, this.g)) {
            return false;
        }
        List<POI> list = this.h;
        if (list == null || t.size() != list.size()) {
            return false;
        }
        for (int i2 = 0; i2 < t.size(); i2++) {
            if (!a(t.get(i2), list.get(i2))) {
                return false;
            }
        }
        return true;
    }

    private static boolean a(POI poi, POI poi2) {
        if ((poi == null && poi2 == null) || poi == poi2) {
            return true;
        }
        if (poi == null || poi2 == null || (poi.getPoiExtra().containsKey("main_poi") ^ poi2.getPoiExtra().containsKey("main_poi"))) {
            return false;
        }
        return bnx.a(poi, poi2);
    }

    private static boolean c(POI poi) {
        if (poi == null) {
            return false;
        }
        GeoPoint point = poi.getPoint();
        return (point != null && point.x != 0 && point.y != 0) && (TextUtils.isEmpty(poi.getName()) ^ true);
    }

    public final boolean m() {
        return this.e;
    }

    public final POI n() {
        if (b() != null) {
            return b().d();
        }
        return null;
    }

    public final POI o() {
        if (b() != null) {
            return b().f();
        }
        return null;
    }

    public final List<POI> p() {
        if (b() != null) {
            return b().e();
        }
        return null;
    }

    public final void a(POI poi) {
        IRouteUI b2 = b();
        if (b2 != null) {
            b2.a(poi);
        }
    }

    public final void b(POI poi) {
        IRouteUI b2 = b();
        if (b2 != null) {
            b2.b(poi);
        }
    }

    public static POI a(ResultType resultType, PageBundle pageBundle) {
        if (ResultType.OK != resultType || pageBundle == null || !pageBundle.containsKey("result_poi")) {
            return null;
        }
        return (POI) pageBundle.getObject("result_poi");
    }

    public final void a(String str) {
        if (b() != null) {
            b().a(str);
        }
    }

    public final void q() {
        this.i = true;
    }

    public final void r() {
        if (!c(n())) {
            ToastHelper.showLongToast(this.a.getString(R.string.drive_route_start_empty));
            return;
        }
        if (!c(o())) {
            ToastHelper.showLongToast(this.a.getString(R.string.drive_route_end_empty));
        }
    }

    public final ViewGroup s() {
        View j2 = j();
        if (j2 == null || j2.getParent() == null) {
            return null;
        }
        return (ViewGroup) j2.getParent().getParent();
    }

    public final List<POI> t() {
        List<POI> p = p();
        ArrayList arrayList = new ArrayList();
        if (p != null) {
            for (int i2 = 0; i2 < p.size(); i2++) {
                if (c(p.get(i2))) {
                    arrayList.add(p.get(i2));
                }
            }
        }
        return arrayList;
    }

    private boolean c(SelectFor selectFor, POI poi) {
        POI poi2;
        List<POI> p = p();
        if (selectFor == SelectFor.FROM_POI) {
            poi2 = n();
        } else if (selectFor == SelectFor.TO_POI) {
            poi2 = o();
        } else {
            if (p != null && p.size() > 0) {
                if (selectFor == SelectFor.MID_POI || selectFor == SelectFor.MID_POI_1) {
                    poi2 = p.get(0);
                } else if (selectFor == SelectFor.MID_POI_2 && p.size() >= 2) {
                    poi2 = p.get(1);
                } else if (selectFor == SelectFor.MID_POI_3 && p.size() >= 3) {
                    poi2 = p.get(2);
                }
            }
            poi2 = null;
        }
        return poi2 != null && a(poi2, poi);
    }

    public final void a(RouteType routeType) {
        if (this.b != null) {
            this.b.a(routeType);
        }
    }
}
