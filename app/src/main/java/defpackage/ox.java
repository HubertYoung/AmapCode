package defpackage;

import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.h5container.api.H5PageData;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.drive.ajx.inter.TruckTopHeightCallBack;
import com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.IRouteHeaderEvent;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.db.model.Car;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.drive.widget.CarPlateInputView;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.sdk.log.util.LogConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ox reason: default package */
/* compiled from: AjxRouteTruckResultPresenter */
public final class ox extends Ajx3PagePresenter implements bgo, TruckTopHeightCallBack {
    public qb a;
    public AjxRouteTruckResultPage b;
    public POI c;
    public POI d;
    public String e;
    public boolean f;
    public acg g;
    /* access modifiers changed from: private */
    public List<POI> h = new ArrayList();
    private IRouteUI i;
    private boolean j = true;
    private pj k = null;
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
            switch (AnonymousClass5.a[iRouteHeaderEvent.ordinal()]) {
                case 1:
                    return ox.i();
                case 2:
                    return ox.j();
                case 3:
                    return ox.a((String) "B091", (JSONObject) null);
                case 4:
                    ox oxVar = ox.this;
                    if (oxVar.a.d()) {
                        boolean l = oxVar.a.l();
                        oxVar.a.h();
                        if (l || !oxVar.a.a(true)) {
                            oxVar.a.r();
                        } else {
                            oxVar.b();
                        }
                    }
                    return false;
                case 6:
                    ox.this.b.h = true;
                    break;
                case 8:
                    if (pageBundle != null) {
                        boolean z = pageBundle.getBoolean(IRouteHeaderEvent.HEAD_SMART_HEAD_TIPS_CHANGE.name(), true);
                        int i = pageBundle.getInt("tipsHeight", 0);
                        AMapLog.d("AjxRouteCarResultMapPresenter", "onTipSizeChanged--topHeight=".concat(String.valueOf(i)));
                        ox.this.b.a(z ? SecExceptionCode.SEC_ERROE_OPENSDK_UNSUPPORTED_VERSION : 1105, i);
                        AjxRouteTruckResultPage g = ox.this.b;
                        if (g.p != null && !g.d) {
                            IRouteUI b = g.j.b();
                            if (b != null) {
                                g.p.a(b.m());
                                break;
                            }
                        }
                    }
                    break;
            }
            return false;
        }
    };

    /* renamed from: ox$5 reason: invalid class name */
    /* compiled from: AjxRouteTruckResultPresenter */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] a = new int[IRouteHeaderEvent.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|(3:15|16|18)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(18:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|18) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0056 */
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
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.END_CLICK     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.ADD_CLICK     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.COMPLETE_CLICK     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.HEAD_ANIMATION_DONE     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x004b }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.PREPARE_SWITCH_TAB     // Catch:{ NoSuchFieldError -> 0x004b }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x004b }
            L_0x004b:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0056 }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.PAGE_ANIMATION_DONE     // Catch:{ NoSuchFieldError -> 0x0056 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0056 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0056 }
            L_0x0056:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0062 }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.HEAD_SMART_HEAD_TIPS_CHANGE     // Catch:{ NoSuchFieldError -> 0x0062 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0062 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0062 }
            L_0x0062:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.ox.AnonymousClass5.<clinit>():void");
        }
    }

    /* renamed from: ox$a */
    /* compiled from: AjxRouteTruckResultPresenter */
    class a implements ada {
        private a() {
        }

        /* synthetic */ a(ox oxVar, byte b) {
            this();
        }

        public final void onTypeChange(RouteType routeType, RouteType routeType2) {
            if (RouteType.TRUCK == routeType2) {
                ox.this.g();
            }
        }
    }

    public final boolean handleVUICmd(bgb bgb, bfb bfb) {
        return false;
    }

    public ox(AjxRouteTruckResultPage ajxRouteTruckResultPage) {
        super(ajxRouteTruckResultPage);
        this.b = ajxRouteTruckResultPage;
    }

    public final void onPageCreated() {
        super.onPageCreated();
        this.a = new qb((AbstractBasePage) this.mPage);
        this.k = h();
        this.g = (acg) defpackage.esb.a.a.a(acg.class);
        if (this.g != null) {
            this.l = new a(this, 0);
            this.g.a((ada) this.l);
            g();
        }
    }

    /* access modifiers changed from: private */
    public void g() {
        if (this.g != null) {
            this.c = this.g.f();
            this.d = this.g.h();
            this.h = this.g.g();
            if (this.h == null) {
                this.h = new ArrayList();
            }
            this.g.a(this.c != null ? this.c.getName() : "");
            this.g.b(this.d != null ? this.d.getName() : "");
            this.g.a(this.h);
        }
    }

    public final void onResume() {
        super.onResume();
        this.i = ((axd) ((Ajx3Page) this.mPage).getContentView().getParent()).getRouteInputUI();
        this.a.a();
        this.a.a(this.n);
        this.g.a((acy) new acy() {
            public final void onDataChange(POI poi, List<POI> list, POI poi2) {
                if (poi != null) {
                    new StringBuilder("onDataChange/start: ").append(poi.getName());
                }
                if (list != null) {
                    new StringBuilder("onDataChange/mid: ").append(list.size());
                }
                boolean z = !ox.this.g.a(poi, ox.this.c);
                boolean z2 = !ox.this.g.a(poi2, ox.this.d);
                boolean a2 = ox.this.g.a(ox.this.h, list);
                if (z || z2 || a2) {
                    ox.this.c = z ? ox.this.g.f() : ox.this.c;
                    ox.this.d = z2 ? ox.this.g.h() : ox.this.d;
                    ox.this.h = a2 ? ox.this.g.g() : ox.this.h;
                    if (ox.this.h == null) {
                        ox.this.h = new ArrayList();
                    }
                    if (z) {
                        ox.this.g.a(ox.this.c != null ? ox.this.c.getName() : "");
                    }
                    if (z2) {
                        ox.this.g.b(ox.this.d != null ? ox.this.d.getName() : "");
                    }
                    if (a2) {
                        ox.this.g.a(ox.this.h);
                    }
                    ox.this.b();
                    return;
                }
                ToastHelper.showToast(((Ajx3Page) ox.this.mPage).getString(R.string.route_same_from_to));
            }
        });
        if (this.i.o()) {
            b();
            PageBundle arguments = ((Ajx3Page) this.mPage).getArguments();
            if (arguments != null) {
                int a2 = eqg.a(arguments.getString("bundleKeyVoiceCmd"));
                if (a2 != -1) {
                    if (!DriveUtil.isCarTruckInfoEmpty()) {
                        d.a.a(a2, 10000, (String) null);
                        return;
                    }
                    d.a.a(a2, 10127, (String) null);
                }
            }
        }
    }

    public final void onStart() {
        super.onStart();
        if (this.i != null) {
            this.i.s();
        }
        if (this.j) {
            anf.a(1, 0);
            this.j = false;
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
            this.j = true;
        }
    }

    public final void onDestroy() {
        if (this.a.b() != null) {
            this.a.c();
        }
        this.g.a((ada) null);
        super.onDestroy();
    }

    public final void onResult(int i2, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i2, resultType, pageBundle);
        if (i2 == 65538 && pageBundle != null && pageBundle.getBoolean("bundle_key_click_confirm_or_cancle")) {
            if (!TextUtils.isEmpty(pageBundle.getString(CarPlateInputView.BUNDLE_KEY_CAR_PLATE_NUMBER))) {
                DriveUtil.setAvoidLimitedPath(true);
            }
            if (this.a.a(false)) {
                this.a.h();
                b();
            }
        }
        if (i2 == 120 || i2 == 200) {
            AjxRouteTruckResultPage ajxRouteTruckResultPage = this.b;
            if (ajxRouteTruckResultPage.f != null && !ajxRouteTruckResultPage.e) {
                String truckRoutingChoice = DriveUtil.getTruckRoutingChoice();
                boolean truckAvoidSwitch = DriveUtil.getTruckAvoidSwitch();
                String truckCarPlateNumber = DriveUtil.getTruckCarPlateNumber();
                if ((!TextUtils.equals(truckRoutingChoice, ajxRouteTruckResultPage.k) || !TextUtils.equals(Boolean.toString(ajxRouteTruckResultPage.l), Boolean.toString(truckAvoidSwitch)) || !TextUtils.equals(ajxRouteTruckResultPage.m, truckCarPlateNumber)) && ajxRouteTruckResultPage.j.a(false)) {
                    ajxRouteTruckResultPage.j.h();
                    ajxRouteTruckResultPage.c.b();
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
            a((String) "planresult_preference");
            this.b.a();
        }
        if (i2 == 1002) {
            pj h2 = h();
            if (h2 != null && (this.k == null || !h2.equals(this.k))) {
                this.k = h2;
                a((String) "planresult_preference");
                this.b.a();
            }
        }
        if ((i2 == 150 || i2 == 99) && (i2 == 150 || i2 == 99)) {
            this.b.a(false, (AnimationListener) new AnimationListener() {
                public final void onAnimationRepeat(Animation animation) {
                }

                public final void onAnimationStart(Animation animation) {
                }

                public final void onAnimationEnd(Animation animation) {
                    if (((Ajx3Page) ox.this.mPage).isAlive()) {
                        ((Ajx3Page) ox.this.mPage).getContentView().findViewById(R.id.mapTopInteractiveView).setVisibility(0);
                    }
                }
            });
        }
        if (i2 == 140) {
            this.b.a(false);
        }
    }

    private static pj h() {
        Car carTruckInfo = DriveUtil.getCarTruckInfo();
        if (carTruckInfo == null) {
            return null;
        }
        pj pjVar = new pj();
        pjVar.a = carTruckInfo;
        pjVar.b = DriveUtil.getTruckRoutingChoice();
        pjVar.c = DriveUtil.getTruckAvoidSwitch();
        pjVar.d = DriveUtil.getTruckAvoidLimitedLoad();
        return pjVar;
    }

    public final void a(POI poi) {
        if (this.a != null && ((Ajx3Page) this.mPage).isResumed()) {
            this.g.b(RouteType.TRUCK, poi);
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

    private void a(POI poi, POI poi2, List<POI> list) {
        if (poi != null && poi2 != null) {
            this.c = poi.clone();
            this.d = poi2.clone();
            this.h.clear();
            if (list != null && list.size() > 0) {
                for (int i2 = 0; i2 < list.size(); i2++) {
                    this.h.add(list.get(i2).clone());
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static boolean i() {
        a((String) "B089", (String) "type", (String) H5PageData.KEY_UC_START);
        return false;
    }

    /* access modifiers changed from: private */
    public static boolean j() {
        a((String) "B089", (String) "type", (String) "end");
        return false;
    }

    public final void b() {
        a(null, null, null, null, false, false);
    }

    public final void a(JSONObject jSONObject) {
        a(null, null, null, null, false, false, jSONObject);
    }

    private void a(String str) {
        a(null, null, null, str, false, false);
    }

    public final void a(POI poi, POI poi2, ArrayList<ISearchPoiData> arrayList, String str, boolean z, boolean z2) {
        a(poi, poi2, arrayList, str, z, z2, null);
    }

    private void a(POI poi, POI poi2, ArrayList<ISearchPoiData> arrayList, String str, boolean z, boolean z2, JSONObject jSONObject) {
        this.b.a(b(poi, poi2, arrayList, str, z, z2, jSONObject));
    }

    public final String c() {
        return b(null, null, null, null, false, false, null);
    }

    private String b(POI poi, POI poi2, ArrayList<ISearchPoiData> arrayList, String str, boolean z, boolean z2, JSONObject jSONObject) {
        if (this.a.b() == null) {
            return null;
        }
        this.f = z;
        JSONObject jSONObject2 = new JSONObject();
        POI n2 = this.a.n();
        List<POI> p = this.a.p();
        try {
            jSONObject2.put("is_need_request", true);
            jSONObject2.put("start_poi", bnx.b(n2));
            if (poi == null) {
                poi = this.a.o();
            } else {
                z = true;
            }
            if (!(poi == null || poi.getPoiExtra() == null)) {
                HashMap<String, Serializable> poiExtra = poi.getPoiExtra();
                Serializable serializable = poiExtra.get("build_type");
                if (serializable != null && (serializable instanceof Integer) && ((Integer) serializable).intValue() == 0) {
                    Serializable serializable2 = poiExtra.get("main_poi");
                    Serializable serializable3 = poiExtra.get("build_type_train_station_entrance_exit_poies");
                    Serializable serializable4 = poiExtra.get("scene_poi");
                    if (serializable2 != null && (serializable2 instanceof POI)) {
                        poi2 = (POI) serializable2;
                    }
                    if (serializable3 != null && (serializable3 instanceof ArrayList)) {
                        arrayList = (ArrayList) serializable3;
                    }
                    if (serializable4 != null && (serializable4 instanceof Boolean)) {
                        z = ((Boolean) serializable4).booleanValue();
                    }
                }
            }
            jSONObject2.put("end_poi", bnx.b(poi));
            jSONObject2.put("mid_poi", rt.a(p));
            jSONObject2.put(ConfigerHelper.AOS_URL_KEY, ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.DRIVE_AOS_URL_KEY));
            jSONObject2.put("favId", this.b.g);
            jSONObject2.put("isScene", z);
            jSONObject2.put("isForceOnline", z2);
            if (poi2 != null) {
                jSONObject2.put("main_poi", bnx.b(poi2));
            }
            if (arrayList != null && arrayList.size() > 0) {
                jSONObject2.put("poi_list", rt.a(arrayList));
            }
            if (this.b.d) {
                jSONObject2.put("source_type", "source_save");
            } else {
                String str2 = "";
                switch (a()) {
                    case 100:
                        str2 = "source_common";
                        break;
                    case 101:
                        str2 = "source_save";
                        break;
                    case 102:
                        str2 = "source_etrip";
                        break;
                }
                jSONObject2.put("source_type", str2);
            }
            jSONObject2.put("routeType", RouteType.TRUCK.getValue());
            PageBundle arguments = ((Ajx3Page) this.mPage).getArguments();
            if (TextUtils.isEmpty(str) && arguments != null && arguments.containsKey("bundle_key_from_page")) {
                str = arguments.getString("bundle_key_from_page");
                arguments.remove("bundle_key_from_page");
            }
            if (!TextUtils.isEmpty(str)) {
                jSONObject2.put("fromPage", str);
            }
            if (jSONObject != null) {
                jSONObject2.put("calcRouteVoiceToken'", jSONObject);
            }
            if (arguments != null) {
                String string = arguments.getString(DriveUtil.SOURCE_APPLICATION);
                if (!TextUtils.isEmpty(string)) {
                    jSONObject2.put(DriveUtil.SOURCE_APPLICATION, string);
                }
            }
            int i2 = arguments != null ? arguments.getInt("bundle_key_voice_tokenId", -1) : -1;
            if (i2 >= 0) {
                jSONObject2.put("calcRouteVoiceToken", sb.b("requestRoute", i2, null));
                arguments.putInt("bundle_key_voice_tokenId", -1);
            } else if (jSONObject != null) {
                jSONObject2.put("calcRouteVoiceToken", jSONObject);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        a(n2, this.a.o(), p);
        return jSONObject2.toString();
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

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        if ("voice".equals(pageBundle.getString("bundle_key_from_page"))) {
            b();
        }
    }

    public final int getTopHeight() {
        AjxRouteTruckResultPage ajxRouteTruckResultPage = this.b;
        if (ajxRouteTruckResultPage.c.a.b() != null) {
            return ajxRouteTruckResultPage.c.a.b().m();
        }
        return 0;
    }
}
