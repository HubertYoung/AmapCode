package defpackage;

import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.h5container.api.H5PageData;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drive.navi.drivenavi.normal.page.AjxRouteCarNaviPage;
import com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.IRouteHeaderEvent;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.bundle.vui.ajx.ModuleVUI;
import com.autonavi.bundle.vui.business.poiselector.PoiSelectPage;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.drive.widget.CarPlateInputView;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import com.tencent.connect.common.Constants;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.setup.UCMPackageInfo;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ow reason: default package */
/* compiled from: AjxRouteCarResultMapPresenter */
public final class ow extends Ajx3PagePresenter implements bgo {
    public qb a;
    public AjxRouteCarResultPage b;
    public POI c;
    public POI d;
    public List<POI> e = new ArrayList();
    public String f;
    public boolean g;
    public int h = -1;
    public acg i;
    private IRouteUI j;
    private boolean k = true;
    private a l;
    private int m = -1;
    private axe n = new axe() {
        public final boolean a(IRouteHeaderEvent iRouteHeaderEvent, PageBundle pageBundle) {
            if (iRouteHeaderEvent == null) {
                return false;
            }
            StringBuilder sb = new StringBuilder("onInputEventClick--iRouteHeaderEvent=");
            sb.append(iRouteHeaderEvent.name());
            AMapLog.d("AjxRouteCarResultMapPresenter", sb.toString());
            Class<?> topPageClass = AMapPageUtil.getTopPageClass();
            if (topPageClass != null && (topPageClass.equals(AjxRouteCarNaviPage.class) || topPageClass.equals(PoiSelectPage.class))) {
                return false;
            }
            switch (AnonymousClass5.a[iRouteHeaderEvent.ordinal()]) {
                case 1:
                    ow.k();
                    break;
                case 2:
                    ow.a("B092", null);
                    break;
                case 3:
                    ow.l();
                    break;
                case 4:
                    ow.a("B091", null);
                    break;
                case 5:
                    ow owVar = ow.this;
                    if (owVar.a.d()) {
                        boolean l = owVar.a.l();
                        owVar.a.h();
                        if (!l && owVar.a.a(true)) {
                            owVar.b();
                            break;
                        } else {
                            owVar.a.r();
                            break;
                        }
                    }
                    break;
                case 7:
                    ow.this.b.j = true;
                    AjxRouteCarResultPage.i();
                    break;
                case 9:
                    if (pageBundle != null) {
                        boolean z = pageBundle.getBoolean(IRouteHeaderEvent.HEAD_SMART_HEAD_TIPS_CHANGE.name(), true);
                        int i = pageBundle.getInt("tipsHeight", 0);
                        AMapLog.d("AjxRouteCarResultMapPresenter", "onTipSizeChanged--topHeight=".concat(String.valueOf(i)));
                        ow.this.b.a(z ? SecExceptionCode.SEC_ERROE_OPENSDK_UNSUPPORTED_VERSION : 1105, i);
                        AjxRouteCarResultPage f = ow.this.b;
                        if (f.s != null && !f.e) {
                            IRouteUI b = f.m.b();
                            if (b != null) {
                                f.s.a(b.m());
                                break;
                            }
                        }
                    }
                    break;
            }
            return false;
        }
    };

    /* renamed from: ow$5 reason: invalid class name */
    /* compiled from: AjxRouteCarResultMapPresenter */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] a = new int[IRouteHeaderEvent.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(20:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|20) */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0056 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0062 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent[] r0 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.START_CLICK     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.EXCHANGE_CLICK     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.END_CLICK     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.ADD_CLICK     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.COMPLETE_CLICK     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x004b }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.HEAD_ANIMATION_DONE     // Catch:{ NoSuchFieldError -> 0x004b }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x004b }
            L_0x004b:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0056 }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.PREPARE_SWITCH_TAB     // Catch:{ NoSuchFieldError -> 0x0056 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0056 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0056 }
            L_0x0056:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0062 }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.PAGE_ANIMATION_DONE     // Catch:{ NoSuchFieldError -> 0x0062 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0062 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0062 }
            L_0x0062:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x006e }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.HEAD_SMART_HEAD_TIPS_CHANGE     // Catch:{ NoSuchFieldError -> 0x006e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006e }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006e }
            L_0x006e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.ow.AnonymousClass5.<clinit>():void");
        }
    }

    /* renamed from: ow$a */
    /* compiled from: AjxRouteCarResultMapPresenter */
    class a implements ada {
        private a() {
        }

        /* synthetic */ a(ow owVar, byte b) {
            this();
        }

        public final void onTypeChange(RouteType routeType, RouteType routeType2) {
            if (RouteType.CAR == routeType2) {
                ow.this.i();
            }
        }
    }

    public ow(AjxRouteCarResultPage ajxRouteCarResultPage) {
        super(ajxRouteCarResultPage);
        this.b = ajxRouteCarResultPage;
    }

    public final void onPageCreated() {
        super.onPageCreated();
        j();
        this.a = new qb((AbstractBasePage) this.mPage);
        this.i = (acg) defpackage.esb.a.a.a(acg.class);
        if (this.i != null) {
            this.l = new a(this, 0);
            this.i.a((ada) this.l);
            i();
        }
    }

    /* access modifiers changed from: private */
    public void i() {
        if (this.i != null) {
            this.c = this.i.f();
            this.d = this.i.h();
            this.e = this.i.g();
            if (this.e == null) {
                this.e = new ArrayList();
            }
            this.i.a(this.c != null ? this.c.getName() : "");
            this.i.b(this.d != null ? this.d.getName() : "");
            this.i.a(this.e);
        }
    }

    public final void onResume() {
        super.onResume();
        this.j = ((axd) ((Ajx3Page) this.mPage).getContentView().getParent()).getRouteInputUI();
        this.a.a();
        this.a.a(this.n);
        this.i.a((acy) new acy() {
            public final void onDataChange(POI poi, List<POI> list, POI poi2) {
                if (poi != null) {
                    new StringBuilder("onDataChange/start: ").append(poi.getName());
                }
                if (list != null) {
                    new StringBuilder("onDataChange/mid: ").append(list.size());
                }
                boolean z = !ow.this.i.a(poi, ow.this.c);
                boolean z2 = !ow.this.i.a(poi2, ow.this.d);
                boolean a2 = ow.this.i.a(ow.this.e, list);
                if (z || z2 || a2) {
                    ow.this.c = z ? ow.this.i.f() : ow.this.c;
                    ow.this.d = z2 ? ow.this.i.h() : ow.this.d;
                    ow.this.e = a2 ? ow.this.i.g() : ow.this.e;
                    if (ow.this.e == null) {
                        ow.this.e = new ArrayList();
                    }
                    if (z) {
                        ow.this.i.a(ow.this.c != null ? ow.this.c.getName() : "");
                    }
                    if (z2) {
                        ow.this.i.b(ow.this.d != null ? ow.this.d.getName() : "");
                    }
                    if (a2) {
                        ow.this.i.a(ow.this.e);
                    }
                    ow.this.b();
                    return;
                }
                ToastHelper.showToast(((Ajx3Page) ow.this.mPage).getString(R.string.route_same_from_to));
            }
        });
        if (this.j.o()) {
            b();
            PageBundle arguments = ((Ajx3Page) this.mPage).getArguments();
            if (arguments != null) {
                int a2 = eqg.a(arguments.getString("bundleKeyVoiceCmd"));
                if (a2 != -1) {
                    d.a.a(a2, 10000, (String) null);
                }
            }
        }
    }

    private void j() {
        PageBundle arguments = ((Ajx3Page) this.mPage).getArguments();
        if (arguments != null) {
            this.h = arguments.getInt("bundle_key_token", this.h);
            if (bno.a && bno.a) {
                StringBuilder sb = new StringBuilder("initData mitVuiToken=");
                sb.append(this.h);
                tq.b("NaviMonitor", "AjxRouteCarResultMapPresenter", sb.toString());
            }
        }
    }

    public final void onStart() {
        super.onStart();
        if (this.k) {
            anf.a(1, 0);
            this.k = false;
        }
    }

    public final void onPause() {
        super.onPause();
        IRouteUI b2 = this.a.b();
        if (b2 != null && b2.o()) {
            this.a.c();
        }
    }

    public final void onStop() {
        super.onStop();
        if (drl.a().b()) {
            anf.a(0, -1);
            this.k = true;
        }
    }

    public final void onDestroy() {
        if (this.a.b() != null) {
            this.a.c();
        }
        this.i.a((ada) null);
        super.onDestroy();
    }

    public final void onResult(int i2, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i2, resultType, pageBundle);
        if (i2 == 110 && pageBundle != null && pageBundle.getBoolean("bundle_key_click_confirm_or_cancle")) {
            if (!TextUtils.isEmpty(pageBundle.getString(CarPlateInputView.BUNDLE_KEY_CAR_PLATE_NUMBER))) {
                DriveUtil.setAvoidLimitedPath(true);
            }
            if (this.a.a(false)) {
                this.a.h();
                if (!TextUtils.isEmpty(pageBundle.getString(CarPlateInputView.BUNDLE_KEY_CAR_PLATE_NUMBER))) {
                    b();
                }
            }
            this.b.i = false;
        }
        if (i2 == 120 || i2 == 200) {
            AjxRouteCarResultPage ajxRouteCarResultPage = this.b;
            if (ajxRouteCarResultPage.g != null && !ajxRouteCarResultPage.f) {
                String lastRoutingChoice = DriveUtil.getLastRoutingChoice();
                boolean isAvoidLimitedPath = DriveUtil.isAvoidLimitedPath();
                String carPlateNumber = DriveUtil.getCarPlateNumber();
                if ((!TextUtils.equals(lastRoutingChoice, ajxRouteCarResultPage.n) || !TextUtils.equals(Boolean.toString(ajxRouteCarResultPage.o), Boolean.toString(isAvoidLimitedPath)) || !TextUtils.equals(ajxRouteCarResultPage.p, carPlateNumber)) && ajxRouteCarResultPage.m.a(false)) {
                    ajxRouteCarResultPage.m.h();
                    ajxRouteCarResultPage.b.b();
                }
            }
        }
        if (i2 == 96 && resultType == ResultType.OK && pageBundle != null && pageBundle.containsKey("result_poi")) {
            POI poi = (POI) pageBundle.get("result_poi");
            if (poi != null) {
                com com2 = (com) ank.a(com.class);
                if (com2 != null) {
                    cop b2 = com2.b(com2.a());
                    if (b2 != null) {
                        b2.f(poi);
                    }
                }
            }
        }
        if (i2 == 97 && resultType == ResultType.OK && pageBundle != null && pageBundle.containsKey("result_poi")) {
            POI poi2 = (POI) pageBundle.get("result_poi");
            if (poi2 != null) {
                com com3 = (com) ank.a(com.class);
                if (com3 != null) {
                    cop b3 = com3.b(com3.a());
                    if (b3 != null) {
                        b3.e(poi2);
                    }
                }
            }
        }
        if (i2 == 1000 && resultType == ResultType.OK && pageBundle != null && pageBundle.containsKey("setting_selected_has_Changed") && pageBundle.getBoolean("setting_selected_has_Changed", false)) {
            new pf(this.a).i = "planresult_preference";
            a(null, null, null, "planresult_preference", false, false);
            this.b.b();
        }
        if ((i2 == 150 || i2 == 99) && (i2 == 150 || i2 == 99)) {
            this.b.a(false, (AnimationListener) new AnimationListener() {
                public final void onAnimationRepeat(Animation animation) {
                }

                public final void onAnimationStart(Animation animation) {
                }

                public final void onAnimationEnd(Animation animation) {
                    if (((Ajx3Page) ow.this.mPage).isAlive()) {
                        ((Ajx3Page) ow.this.mPage).getContentView().findViewById(R.id.mapTopInteractiveView).setVisibility(0);
                    }
                }
            });
        }
        if (i2 == 140) {
            this.b.a(false);
        }
    }

    public final void a(POI poi) {
        if (this.a != null && ((Ajx3Page) this.mPage).isResumed()) {
            this.i.b(RouteType.CAR, poi);
        }
    }

    public final int a() {
        if (this.m == -1) {
            PageBundle arguments = ((Ajx3Page) this.mPage).getArguments();
            if (arguments != null) {
                this.m = arguments.getInt("key_source", 100);
            } else {
                this.m = 100;
            }
        }
        return this.m;
    }

    public final void a(List<POI> list) {
        if (this.a != null && ((Ajx3Page) this.mPage).isResumed()) {
            this.i.a(RouteType.CAR, list);
            this.i.a(list);
        }
    }

    private void a(POI poi, POI poi2, List<POI> list) {
        if (poi != null && poi2 != null) {
            this.c = poi.clone();
            this.d = poi2.clone();
            this.e.clear();
            if (list != null && list.size() > 0) {
                for (int i2 = 0; i2 < list.size(); i2++) {
                    this.e.add(list.get(i2).clone());
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static boolean k() {
        a((String) "B089", (String) "type", (String) H5PageData.KEY_UC_START);
        return false;
    }

    /* access modifiers changed from: private */
    public static boolean l() {
        a((String) "B089", (String) "type", (String) "end");
        return false;
    }

    public final void b() {
        a(null, null, null, null, false, false);
    }

    public final void a(JSONObject jSONObject) {
        a(null, null, null, null, false, false, jSONObject);
    }

    public final void a(POI poi, POI poi2, ArrayList<ISearchPoiData> arrayList, String str, boolean z, boolean z2) {
        a(poi, poi2, arrayList, str, z, z2, null);
    }

    private void a(POI poi, POI poi2, ArrayList<ISearchPoiData> arrayList, String str, boolean z, boolean z2, JSONObject jSONObject) {
        this.b.a(b(poi, poi2, arrayList, str, z, z2, jSONObject), str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x00d2 A[Catch:{ JSONException -> 0x01b3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00f2 A[Catch:{ JSONException -> 0x01b3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00fa A[Catch:{ JSONException -> 0x01b3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0148 A[Catch:{ JSONException -> 0x01b3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x014f A[Catch:{ JSONException -> 0x01b3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x016a A[Catch:{ JSONException -> 0x01b3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x017e A[Catch:{ JSONException -> 0x01b3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0185 A[Catch:{ JSONException -> 0x01b3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x018a A[Catch:{ JSONException -> 0x01b3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0198 A[Catch:{ JSONException -> 0x01b3 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String b(com.autonavi.common.model.POI r15, com.autonavi.common.model.POI r16, java.util.ArrayList<com.autonavi.minimap.search.model.searchpoi.ISearchPoiData> r17, java.lang.String r18, boolean r19, boolean r20, org.json.JSONObject r21) {
        /*
            r14 = this;
            r1 = r14
            r2 = r21
            acg r3 = r1.i
            r4 = 0
            if (r3 != 0) goto L_0x0009
            return r4
        L_0x0009:
            r3 = r19
            r1.g = r3
            org.json.JSONObject r5 = new org.json.JSONObject
            r5.<init>()
            acg r6 = r1.i
            com.autonavi.common.model.POI r6 = r6.f()
            acg r7 = r1.i
            java.util.List r7 = r7.g()
            java.lang.String r8 = "is_need_request"
            r9 = 1
            r5.put(r8, r9)     // Catch:{ JSONException -> 0x01b3 }
            java.lang.String r8 = "start_poi"
            org.json.JSONObject r10 = defpackage.bnx.b(r6)     // Catch:{ JSONException -> 0x01b3 }
            r5.put(r8, r10)     // Catch:{ JSONException -> 0x01b3 }
            if (r15 != 0) goto L_0x0037
            acg r8 = r1.i     // Catch:{ JSONException -> 0x01b3 }
            com.autonavi.common.model.POI r8 = r8.h()     // Catch:{ JSONException -> 0x01b3 }
            goto L_0x0039
        L_0x0037:
            r8 = r15
            r3 = 1
        L_0x0039:
            if (r8 == 0) goto L_0x0096
            java.util.HashMap r10 = r8.getPoiExtra()     // Catch:{ JSONException -> 0x01b3 }
            if (r10 == 0) goto L_0x0096
            java.util.HashMap r10 = r8.getPoiExtra()     // Catch:{ JSONException -> 0x01b3 }
            java.lang.String r11 = "build_type"
            java.lang.Object r11 = r10.get(r11)     // Catch:{ JSONException -> 0x01b3 }
            java.io.Serializable r11 = (java.io.Serializable) r11     // Catch:{ JSONException -> 0x01b3 }
            if (r11 == 0) goto L_0x0096
            boolean r12 = r11 instanceof java.lang.Integer     // Catch:{ JSONException -> 0x01b3 }
            if (r12 == 0) goto L_0x0096
            java.lang.Integer r11 = (java.lang.Integer) r11     // Catch:{ JSONException -> 0x01b3 }
            int r11 = r11.intValue()     // Catch:{ JSONException -> 0x01b3 }
            if (r11 != 0) goto L_0x0096
            java.lang.String r11 = "main_poi"
            java.lang.Object r11 = r10.get(r11)     // Catch:{ JSONException -> 0x01b3 }
            java.io.Serializable r11 = (java.io.Serializable) r11     // Catch:{ JSONException -> 0x01b3 }
            java.lang.String r12 = "build_type_train_station_entrance_exit_poies"
            java.lang.Object r12 = r10.get(r12)     // Catch:{ JSONException -> 0x01b3 }
            java.io.Serializable r12 = (java.io.Serializable) r12     // Catch:{ JSONException -> 0x01b3 }
            java.lang.String r13 = "scene_poi"
            java.lang.Object r10 = r10.get(r13)     // Catch:{ JSONException -> 0x01b3 }
            java.io.Serializable r10 = (java.io.Serializable) r10     // Catch:{ JSONException -> 0x01b3 }
            if (r11 == 0) goto L_0x007c
            boolean r13 = r11 instanceof com.autonavi.common.model.POI     // Catch:{ JSONException -> 0x01b3 }
            if (r13 == 0) goto L_0x007c
            com.autonavi.common.model.POI r11 = (com.autonavi.common.model.POI) r11     // Catch:{ JSONException -> 0x01b3 }
            goto L_0x007e
        L_0x007c:
            r11 = r16
        L_0x007e:
            if (r12 == 0) goto L_0x0087
            boolean r13 = r12 instanceof java.util.ArrayList     // Catch:{ JSONException -> 0x01b3 }
            if (r13 == 0) goto L_0x0087
            java.util.ArrayList r12 = (java.util.ArrayList) r12     // Catch:{ JSONException -> 0x01b3 }
            goto L_0x0089
        L_0x0087:
            r12 = r17
        L_0x0089:
            if (r10 == 0) goto L_0x009a
            boolean r13 = r10 instanceof java.lang.Boolean     // Catch:{ JSONException -> 0x01b3 }
            if (r13 == 0) goto L_0x009a
            java.lang.Boolean r10 = (java.lang.Boolean) r10     // Catch:{ JSONException -> 0x01b3 }
            boolean r3 = r10.booleanValue()     // Catch:{ JSONException -> 0x01b3 }
            goto L_0x009a
        L_0x0096:
            r11 = r16
            r12 = r17
        L_0x009a:
            java.lang.String r10 = "end_poi"
            org.json.JSONObject r8 = defpackage.bnx.b(r8)     // Catch:{ JSONException -> 0x01b3 }
            r5.put(r10, r8)     // Catch:{ JSONException -> 0x01b3 }
            java.lang.String r8 = "mid_poi"
            org.json.JSONArray r10 = defpackage.rt.a(r7)     // Catch:{ JSONException -> 0x01b3 }
            r5.put(r8, r10)     // Catch:{ JSONException -> 0x01b3 }
            java.lang.String r8 = "aos_url"
            com.amap.bundle.blutils.app.ConfigerHelper r10 = com.amap.bundle.blutils.app.ConfigerHelper.getInstance()     // Catch:{ JSONException -> 0x01b3 }
            java.lang.String r13 = "drive_aos_url"
            java.lang.String r10 = r10.getKeyValue(r13)     // Catch:{ JSONException -> 0x01b3 }
            r5.put(r8, r10)     // Catch:{ JSONException -> 0x01b3 }
            java.lang.String r8 = "favId"
            com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage r10 = r1.b     // Catch:{ JSONException -> 0x01b3 }
            java.lang.String r10 = r10.h     // Catch:{ JSONException -> 0x01b3 }
            r5.put(r8, r10)     // Catch:{ JSONException -> 0x01b3 }
            java.lang.String r8 = "isScene"
            r5.put(r8, r3)     // Catch:{ JSONException -> 0x01b3 }
            java.lang.String r3 = "isForceOnline"
            r8 = r20
            r5.put(r3, r8)     // Catch:{ JSONException -> 0x01b3 }
            if (r11 == 0) goto L_0x00db
            java.lang.String r3 = "main_poi"
            org.json.JSONObject r8 = defpackage.bnx.b(r11)     // Catch:{ JSONException -> 0x01b3 }
            r5.put(r3, r8)     // Catch:{ JSONException -> 0x01b3 }
        L_0x00db:
            if (r12 == 0) goto L_0x00ec
            int r3 = r12.size()     // Catch:{ JSONException -> 0x01b3 }
            if (r3 <= 0) goto L_0x00ec
            java.lang.String r3 = "poi_list"
            org.json.JSONArray r8 = defpackage.rt.a(r12)     // Catch:{ JSONException -> 0x01b3 }
            r5.put(r3, r8)     // Catch:{ JSONException -> 0x01b3 }
        L_0x00ec:
            com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage r3 = r1.b     // Catch:{ JSONException -> 0x01b3 }
            boolean r3 = r3.e     // Catch:{ JSONException -> 0x01b3 }
            if (r3 == 0) goto L_0x00fa
            java.lang.String r3 = "source_type"
            java.lang.String r8 = "source_save"
            r5.put(r3, r8)     // Catch:{ JSONException -> 0x01b3 }
            goto L_0x0111
        L_0x00fa:
            java.lang.String r3 = "source_type"
            int r8 = r1.a()     // Catch:{ JSONException -> 0x01b3 }
            switch(r8) {
                case 100: goto L_0x010c;
                case 101: goto L_0x0109;
                case 102: goto L_0x0106;
                default: goto L_0x0103;
            }     // Catch:{ JSONException -> 0x01b3 }
        L_0x0103:
            java.lang.String r8 = ""
            goto L_0x010e
        L_0x0106:
            java.lang.String r8 = "source_etrip"
            goto L_0x010e
        L_0x0109:
            java.lang.String r8 = "source_save"
            goto L_0x010e
        L_0x010c:
            java.lang.String r8 = "source_common"
        L_0x010e:
            r5.put(r3, r8)     // Catch:{ JSONException -> 0x01b3 }
        L_0x0111:
            java.lang.String r3 = "routeType"
            com.autonavi.bundle.routecommon.model.RouteType r8 = com.autonavi.bundle.routecommon.model.RouteType.CAR     // Catch:{ JSONException -> 0x01b3 }
            int r8 = r8.getValue()     // Catch:{ JSONException -> 0x01b3 }
            r5.put(r3, r8)     // Catch:{ JSONException -> 0x01b3 }
            com.autonavi.map.fragmentcontainer.page.IPage r3 = r1.mPage     // Catch:{ JSONException -> 0x01b3 }
            com.autonavi.minimap.ajx3.Ajx3Page r3 = (com.autonavi.minimap.ajx3.Ajx3Page) r3     // Catch:{ JSONException -> 0x01b3 }
            com.autonavi.common.PageBundle r3 = r3.getArguments()     // Catch:{ JSONException -> 0x01b3 }
            boolean r8 = android.text.TextUtils.isEmpty(r18)     // Catch:{ JSONException -> 0x01b3 }
            if (r8 == 0) goto L_0x0140
            if (r3 == 0) goto L_0x0140
            java.lang.String r8 = "bundle_key_from_page"
            boolean r8 = r3.containsKey(r8)     // Catch:{ JSONException -> 0x01b3 }
            if (r8 == 0) goto L_0x0140
            java.lang.String r8 = "bundle_key_from_page"
            java.lang.String r8 = r3.getString(r8)     // Catch:{ JSONException -> 0x01b3 }
            java.lang.String r10 = "bundle_key_from_page"
            r3.remove(r10)     // Catch:{ JSONException -> 0x01b3 }
            goto L_0x0142
        L_0x0140:
            r8 = r18
        L_0x0142:
            boolean r10 = android.text.TextUtils.isEmpty(r8)     // Catch:{ JSONException -> 0x01b3 }
            if (r10 != 0) goto L_0x014d
            java.lang.String r10 = "fromPage"
            r5.put(r10, r8)     // Catch:{ JSONException -> 0x01b3 }
        L_0x014d:
            if (r2 == 0) goto L_0x0168
            java.lang.String r8 = "jsonParam"
            org.json.JSONObject r8 = r2.getJSONObject(r8)     // Catch:{ JSONException -> 0x01b3 }
            java.lang.String r10 = "from"
            int r8 = defpackage.agd.a(r8, r10)     // Catch:{ JSONException -> 0x01b3 }
            if (r8 != r9) goto L_0x0163
            java.lang.String r8 = "calcRouteMitVoiceToken"
            r5.put(r8, r2)     // Catch:{ JSONException -> 0x01b3 }
            goto L_0x0168
        L_0x0163:
            java.lang.String r8 = "calcRouteVoiceToken"
            r5.put(r8, r2)     // Catch:{ JSONException -> 0x01b3 }
        L_0x0168:
            if (r3 == 0) goto L_0x017b
            java.lang.String r8 = "sourceApplication"
            java.lang.String r8 = r3.getString(r8)     // Catch:{ JSONException -> 0x01b3 }
            boolean r9 = android.text.TextUtils.isEmpty(r8)     // Catch:{ JSONException -> 0x01b3 }
            if (r9 != 0) goto L_0x017b
            java.lang.String r9 = "sourceApplication"
            r5.put(r9, r8)     // Catch:{ JSONException -> 0x01b3 }
        L_0x017b:
            r8 = -1
            if (r3 == 0) goto L_0x0185
            java.lang.String r9 = "bundle_key_voice_tokenId"
            int r9 = r3.getInt(r9, r8)     // Catch:{ JSONException -> 0x01b3 }
            goto L_0x0186
        L_0x0185:
            r9 = -1
        L_0x0186:
            int r10 = r1.h     // Catch:{ JSONException -> 0x01b3 }
            if (r10 < 0) goto L_0x0198
            java.lang.String r2 = "requestRoute"
            int r3 = r1.h     // Catch:{ JSONException -> 0x01b3 }
            org.json.JSONObject r2 = defpackage.sb.a(r2, r3, r4)     // Catch:{ JSONException -> 0x01b3 }
            java.lang.String r3 = "calcRouteMitVoiceToken"
            r5.put(r3, r2)     // Catch:{ JSONException -> 0x01b3 }
            goto L_0x01b8
        L_0x0198:
            if (r9 < 0) goto L_0x01ab
            java.lang.String r2 = "requestRoute"
            org.json.JSONObject r2 = defpackage.sb.b(r2, r9, r4)     // Catch:{ JSONException -> 0x01b3 }
            java.lang.String r4 = "calcRouteVoiceToken"
            r5.put(r4, r2)     // Catch:{ JSONException -> 0x01b3 }
            java.lang.String r2 = "bundle_key_voice_tokenId"
            r3.putInt(r2, r8)     // Catch:{ JSONException -> 0x01b3 }
            goto L_0x01b8
        L_0x01ab:
            if (r2 == 0) goto L_0x01b8
            java.lang.String r3 = "calcRouteVoiceToken"
            r5.put(r3, r2)     // Catch:{ JSONException -> 0x01b3 }
            goto L_0x01b8
        L_0x01b3:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x01b8:
            acg r2 = r1.i
            com.autonavi.common.model.POI r2 = r2.h()
            r1.a(r6, r2, r7)
            java.lang.String r2 = r5.toString()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ow.b(com.autonavi.common.model.POI, com.autonavi.common.model.POI, java.util.ArrayList, java.lang.String, boolean, boolean, org.json.JSONObject):java.lang.String");
    }

    /* access modifiers changed from: private */
    public static void a(String str, JSONObject jSONObject) {
        if (str != null && (str.equals("B026") || str.endsWith("B030"))) {
            return;
        }
        if (jSONObject == null) {
            LogManager.actionLogV2(LogConstant.PAGE_ID_CAR_RESULT_MAP, str);
        } else {
            LogManager.actionLogV2(LogConstant.PAGE_ID_CAR_RESULT_MAP, str, jSONObject);
        }
    }

    private static void a(String str, String str2, String str3) {
        JSONObject jSONObject;
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            jSONObject = null;
        } else {
            jSONObject = new JSONObject();
            try {
                jSONObject.put(str2, str3);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        a(str, jSONObject);
    }

    public final POI d() {
        if (this.i != null) {
            return this.i.h();
        }
        return null;
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        j();
        this.b.a();
        if ("voice".equals(pageBundle.getString("bundle_key_from_page"))) {
            b();
        }
    }

    public final boolean handleVUICmd(bgb bgb, bfb bfb) {
        ModuleVUI moduleVUI = ((Ajx3Page) this.mPage).getAjxView() != null ? (ModuleVUI) ((Ajx3Page) this.mPage).getAjxView().getJsModule(ModuleVUI.MODULE_NAME) : null;
        if (bgb == null) {
            return false;
        }
        StringBuilder sb = new StringBuilder("handleVUICmd ");
        sb.append(bgb.b);
        tq.b("NaviMonitor", "AjxRouteCarResultMapPresenter", sb.toString());
        if (bgb.d.equals("refreshRoute")) {
            AjxRouteCarResultPage ajxRouteCarResultPage = this.b;
            int i2 = bgb.a;
            tq.b("NaviMonitor", "mitVuiRefreshRoute", "token =================> ".concat(String.valueOf(i2)));
            ajxRouteCarResultPage.l();
            if (i2 >= 0) {
                if (ajxRouteCarResultPage.h()) {
                    d.a.a(i2, (int) Constants.REQUEST_SEND_TO_MY_COMPUTER, (String) null);
                    tq.b("NaviMonitor", "mitVuiRefreshRoute", " CODE_ERROR_OFFLINE_NOT_SUPPORT_REFRESH ");
                } else if (!ajxRouteCarResultPage.u.hasMessages(1008) || ajxRouteCarResultPage.v == 1) {
                    DriveUtil.refreshTraffic(ajxRouteCarResultPage.getMapView());
                    ajxRouteCarResultPage.a(i2, sb.a("refreshRoute", i2, null), (a) new a(i2) {
                        final /* synthetic */ int a;

                        {
                            this.a = r2;
                        }

                        public final void a() {
                            AjxRouteCarResultPage.this.b(this.a);
                        }

                        public final void a(JSONObject jSONObject) {
                            AjxRouteCarResultPage.this.a(this.a, jSONObject, true);
                        }
                    });
                    ajxRouteCarResultPage.d();
                } else {
                    d.a.a(i2, (int) UCMPackageInfo.hadInstallUCMobile, (String) null);
                    tq.b("NaviMonitor", "mitVuiRefreshRoute", " CODE_ERROR_IS_LATEST_ROUTE ");
                }
            }
            return true;
        } else if (bgb.d.equals("addViaPoi")) {
            AjxRouteCarResultPage ajxRouteCarResultPage2 = this.b;
            int i3 = bgb.a;
            String str = bgb.b;
            ajxRouteCarResultPage2.l();
            tq.b("NaviMonitor", "mitVuiVuiAddViaPoi", "token  =================> ".concat(String.valueOf(i3)));
            JSONObject a2 = rg.a(str);
            if (a2 == null) {
                d.a.a(i3, (int) SDKFactory.getCoreType, (String) null);
                tq.b("NaviMonitor", "mitVuiVuiAddViaPoi", "CODE_ERROR_COMMON_UNKNOWN ");
            } else {
                ArrayList arrayList = new ArrayList();
                ArrayList<POI> b2 = rg.b(a2, "via_pois");
                if (ajxRouteCarResultPage2.getMidPOIs() != null && ajxRouteCarResultPage2.getMidPOIs().size() > 0) {
                    arrayList.addAll(ajxRouteCarResultPage2.getMidPOIs());
                }
                arrayList.addAll(b2);
                int a3 = rh.a(ajxRouteCarResultPage2.getStartPOI(), ajxRouteCarResultPage2.getEndPOI(), arrayList, agd.a(a2, "param_type1"), 1001, false, RouteType.CAR.getValue());
                if (a3 > 0) {
                    d.a.a(i3, a3, (String) null);
                } else {
                    if (ajxRouteCarResultPage2.b != null) {
                        ajxRouteCarResultPage2.b.a((List<POI>) arrayList);
                        ajxRouteCarResultPage2.a(i3, sb.a("addViaPoi", i3, null), (a) new a(i3) {
                            final /* synthetic */ int a;

                            {
                                this.a = r2;
                            }

                            public final void a() {
                                AjxRouteCarResultPage.this.b(this.a);
                            }

                            public final void a(JSONObject jSONObject) {
                                AjxRouteCarResultPage.this.a(this.a, jSONObject, false);
                            }
                        });
                    }
                    ajxRouteCarResultPage2.b();
                    ajxRouteCarResultPage2.c();
                }
            }
            return true;
        } else if (bgb.d.equals("requestRoute")) {
            JSONObject a4 = rg.a(bgb.b);
            int a5 = agd.a(a4, "param_type");
            int a6 = agd.a(a4, "param_type2");
            if (a6 != 0) {
                if (a6 == RouteType.TAXI.getValue()) {
                    tq.b("NaviMonitor", "AjxRouteCarResultMapPresenter handleVUICmd ", "打车状态返回 state:".concat(String.valueOf((String) Ajx.getInstance().getMemoryStorage("taxi_business_state").getItem("state"))));
                }
                return false;
            } else if (a5 == 1) {
                return false;
            } else {
                AjxRouteCarResultPage ajxRouteCarResultPage3 = this.b;
                int i4 = bgb.a;
                String str2 = bgb.b;
                ajxRouteCarResultPage3.l();
                tq.b("NaviMonitor", "token", " token =================> ".concat(String.valueOf(i4)));
                JSONObject a7 = rg.a(str2);
                if (a7 == null) {
                    d.a.a(i4, (int) SDKFactory.getCoreType, (String) null);
                } else {
                    POI a8 = rg.a(a7, "start_pois");
                    if (a8 == null) {
                        a8 = POIFactory.createPOI();
                        if (LocationInstrument.getInstance().getLatestPosition(5) == null) {
                            a8 = null;
                        } else {
                            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                            a8.setName("我的位置");
                            a8.setPoint(latestPosition);
                        }
                    }
                    POI a9 = rg.a(a7, "end_pois");
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.addAll(rg.b(a7, "via_pois"));
                    int a10 = rh.a(a8, a9, arrayList2, agd.a(a7, "param_type1"), 1002, false, RouteType.CAR.getValue());
                    if (a10 > 0) {
                        d.a.a(i4, a10, (String) null);
                    } else {
                        if (ajxRouteCarResultPage3.b != null) {
                            if (a9 != null) {
                                ajxRouteCarResultPage3.b.a(a9);
                            }
                            if (a8 != null) {
                                ow owVar = ajxRouteCarResultPage3.b;
                                if (owVar.a != null && ((Ajx3Page) owVar.mPage).isResumed()) {
                                    owVar.i.a(RouteType.CAR, a8);
                                }
                            }
                            ajxRouteCarResultPage3.b.a((List<POI>) arrayList2);
                            JSONObject a11 = sb.a("requestRoute", i4, null);
                            StringBuilder sb2 = new StringBuilder("refreshRouteJSON ");
                            sb2.append(a11.toString());
                            tq.b("NaviMonitor", "mitVuiRequestRoute", sb2.toString());
                            ajxRouteCarResultPage3.a(i4, a11, (a) new a(i4) {
                                final /* synthetic */ int a;

                                {
                                    this.a = r2;
                                }

                                public final void a() {
                                    AjxRouteCarResultPage.this.b(this.a);
                                }

                                public final void a(JSONObject jSONObject) {
                                    AjxRouteCarResultPage.this.a(this.a, jSONObject, false);
                                }
                            });
                        }
                        ajxRouteCarResultPage3.b();
                        ajxRouteCarResultPage3.c();
                    }
                }
                return true;
            }
        } else {
            int i5 = 10034;
            if (bgb.d.equals("setRouteParams")) {
                AjxRouteCarResultPage ajxRouteCarResultPage4 = this.b;
                int i6 = bgb.a;
                String str3 = bgb.b;
                ajxRouteCarResultPage4.l();
                tq.b("NaviMonitor", "mitVuiSetRouteParams", "token  =================> ".concat(String.valueOf(i6)));
                JSONObject a12 = rg.a(str3);
                if (a12 == null) {
                    d.a.a(i6, 10007, (String) null);
                    tq.b("NaviMonitor", "mitVuiSetRouteParams", "voiceResultObj == null CODE_ERROR_UNKNOWN_ERROR ");
                } else {
                    int a13 = agd.a(a12, "param_type");
                    if (!sa.a(a13)) {
                        tq.b("NaviMonitor", "verifySetRouteParams", " !VoiceDriveUtils.isSupportParamMethod(params) CODE_ERROR_UNSUPPORT_PREFERENCE_SETTING ");
                    } else {
                        String c2 = sa.c(a13);
                        if ((!NetworkReachability.b() || DriveSpUtil.shouldRouteOffline()) && c2.contains("2")) {
                            tq.b("NaviMonitor", "verifySetRouteParams", " CODE_ERROR_OFFLINE_UNSUPPORT_AVOID_JAM ");
                            i5 = UCMPackageInfo.expectDirFile1S;
                        } else if (!TextUtils.isEmpty(c2)) {
                            DriveUtil.putLastRoutingChoice(c2);
                            i5 = -1;
                        }
                    }
                    if (i5 > 0) {
                        d.a.a(i6, i5, (String) null);
                    } else {
                        ajxRouteCarResultPage4.a(i6, sa.c(a13));
                    }
                }
                return true;
            } else if (bgb.d.equals("cancelRouteParams")) {
                AjxRouteCarResultPage ajxRouteCarResultPage5 = this.b;
                int i7 = bgb.a;
                String str4 = bgb.b;
                ajxRouteCarResultPage5.l();
                tq.b("NaviMonitor", "mitVuiCancelRouteParams", "token  =================> ".concat(String.valueOf(i7)));
                JSONObject a14 = rg.a(str4);
                if (a14 == null) {
                    d.a.a(i7, 10007, (String) null);
                    tq.b("NaviMonitor", "mitVuiCancelRouteParams", " voiceResultObj == null CODE_ERROR_UNKNOWN_ERROR ");
                } else {
                    int a15 = agd.a(a14, "param_type");
                    if (rh.a(a15) > 0) {
                        d.a.a(i7, 10034, (String) null);
                    } else {
                        ajxRouteCarResultPage5.a(i7, DriveUtil.getLastRoutingChoice().replace(sa.c(a15), ""));
                    }
                }
                return true;
            } else if (bgb.d.equals("startNavi")) {
                AjxRouteCarResultPage ajxRouteCarResultPage6 = this.b;
                int i8 = bgb.a;
                if (ajxRouteCarResultPage6.getEndPOI() == null || ("我的位置".equals(ajxRouteCarResultPage6.getEndPOI().getName()) && (ajxRouteCarResultPage6.getMidPOIs() == null || ajxRouteCarResultPage6.getMidPOIs().size() == 0))) {
                    d.a.a(i8, (int) Constants.REQUEST_QQ_FAVORITES, (String) null);
                } else if (ajxRouteCarResultPage6.g == null) {
                    StringBuilder sb3 = new StringBuilder(" token =================> ");
                    sb3.append(i8);
                    sb3.append("mCarRouteResult == null");
                    tq.b("NaviMonitor", "mitVuiStartNavi", sb3.toString());
                    d.a.a(i8, (int) SDKFactory.getCoreType, (String) null);
                } else {
                    tq.b("NaviMonitor", "mitVuiStartNavi", " token =================> ".concat(String.valueOf(i8)));
                    JSONObject a16 = sb.a("startNavi", i8, null);
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("startNaviMitVoiceToken", a16);
                        d.a.a(i8, 10000, (String) null);
                        String jSONObject2 = jSONObject.toString();
                        if (ajxRouteCarResultPage6.d != null) {
                            ajxRouteCarResultPage6.d.requestMitVuiRouteResult(jSONObject2);
                        }
                    } catch (JSONException e2) {
                        tq.b("NaviMonitor", "mitVuiStartNavi", " JSONException ");
                        e2.printStackTrace();
                    }
                }
                return true;
            } else if (moduleVUI == null || moduleVUI.mSupportCmdList == null || !moduleVUI.mSupportCmdList.contains(bgb.d)) {
                return false;
            } else {
                return moduleVUI.handleVUICmd(bgb, bfb);
            }
        }
    }

    public final String c() {
        return b(null, null, null, null, false, false, null);
    }
}
