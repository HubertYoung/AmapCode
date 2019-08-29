package defpackage;

import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout.LayoutParams;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.nebula.search.H5SearchType;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.drive.result.motorresult.result.AjxRouteMotorResultPage;
import com.amap.bundle.drive.result.motorresult.result.tip.AjxMotorTipsManager;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.IRouteHeaderEvent;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.common.model.RouteHeaderModel;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ps reason: default package */
/* compiled from: AjxRouteMotorResultMapPresenter */
public final class ps extends Ajx3PagePresenter implements bgo {
    public qb a;
    public AjxRouteMotorResultPage b;
    public POI c;
    public POI d;
    public acg e;
    public List<POI> f = new ArrayList();
    public String g;
    public boolean h;
    private boolean i = true;
    private String j;
    private String k;
    private int l = -1;
    private axe m = new axe() {
        public final boolean a(IRouteHeaderEvent iRouteHeaderEvent, PageBundle pageBundle) {
            boolean z = false;
            if (iRouteHeaderEvent == null) {
                return false;
            }
            StringBuilder sb = new StringBuilder("onInputEventClick--iRouteHeaderEvent=");
            sb.append(iRouteHeaderEvent.name());
            AMapLog.d("AjxRouteMotorResultMapPresenter", sb.toString());
            switch (AnonymousClass5.a[iRouteHeaderEvent.ordinal()]) {
                case 1:
                    z = ps.d();
                    break;
                case 2:
                    z = ps.j();
                    break;
                case 3:
                    z = ps.a("B092", null);
                    break;
                case 4:
                    z = ps.k();
                    break;
                case 5:
                    z = ps.h();
                    break;
                case 6:
                    z = ps.d(ps.this);
                    break;
                case 7:
                    z = ps.i();
                    break;
                case 8:
                    ps psVar = ps.this;
                    if (psVar.a.d()) {
                        boolean l = psVar.a.l();
                        psVar.a.h();
                        if (!l && psVar.a.a(true)) {
                            psVar.b();
                            break;
                        } else {
                            psVar.a.r();
                            break;
                        }
                    }
                    break;
                case 10:
                    ps.this.b.g = true;
                    AjxRouteMotorResultPage.d();
                    break;
                case 12:
                    if (pageBundle != null) {
                        boolean z2 = pageBundle.getBoolean(IRouteHeaderEvent.HEAD_SMART_HEAD_TIPS_CHANGE.name(), true);
                        int i = pageBundle.getInt("tipsHeight", 0);
                        AMapLog.d("AjxRouteMotorResultMapPresenter", "onTipSizeChanged--topHeight=".concat(String.valueOf(i)));
                        ps.this.b.a(z2 ? SecExceptionCode.SEC_ERROE_OPENSDK_UNSUPPORTED_VERSION : 1105, i);
                        AjxRouteMotorResultPage ajxRouteMotorResultPage = ps.this.b;
                        if (ajxRouteMotorResultPage.q != null && !ajxRouteMotorResultPage.c) {
                            IRouteUI b = ajxRouteMotorResultPage.j.b();
                            if (b != null) {
                                AjxMotorTipsManager ajxMotorTipsManager = ajxRouteMotorResultPage.q;
                                LayoutParams layoutParams = (LayoutParams) ajxMotorTipsManager.d.getLayoutParams();
                                layoutParams.topMargin = b.m();
                                ajxMotorTipsManager.d.setLayoutParams(layoutParams);
                                break;
                            }
                        }
                    }
                    break;
            }
            return z;
        }
    };

    /* renamed from: ps$5 reason: invalid class name */
    /* compiled from: AjxRouteMotorResultMapPresenter */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] a = new int[IRouteHeaderEvent.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(24:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|(3:23|24|26)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(26:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|26) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0056 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0062 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x006e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x007a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0086 */
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
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.BACK_CLICK     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.START_CLICK     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.EXCHANGE_CLICK     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.END_CLICK     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.PASS_POI_CLICK     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x004b }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.ADD_CLICK     // Catch:{ NoSuchFieldError -> 0x004b }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x004b }
            L_0x004b:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0056 }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.SUMMARY_CLICK     // Catch:{ NoSuchFieldError -> 0x0056 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0056 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0056 }
            L_0x0056:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0062 }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.COMPLETE_CLICK     // Catch:{ NoSuchFieldError -> 0x0062 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0062 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0062 }
            L_0x0062:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x006e }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.HEAD_ANIMATION_DONE     // Catch:{ NoSuchFieldError -> 0x006e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006e }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006e }
            L_0x006e:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x007a }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.PREPARE_SWITCH_TAB     // Catch:{ NoSuchFieldError -> 0x007a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x007a }
                r2 = 10
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x007a }
            L_0x007a:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0086 }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.PAGE_ANIMATION_DONE     // Catch:{ NoSuchFieldError -> 0x0086 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0086 }
                r2 = 11
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0086 }
            L_0x0086:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0092 }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.HEAD_SMART_HEAD_TIPS_CHANGE     // Catch:{ NoSuchFieldError -> 0x0092 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0092 }
                r2 = 12
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0092 }
            L_0x0092:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.ps.AnonymousClass5.<clinit>():void");
        }
    }

    static /* synthetic */ boolean d() {
        return false;
    }

    static /* synthetic */ boolean h() {
        return false;
    }

    static /* synthetic */ boolean i() {
        return false;
    }

    public final boolean handleVUICmd(bgb bgb, bfb bfb) {
        return false;
    }

    public ps(AjxRouteMotorResultPage ajxRouteMotorResultPage) {
        super(ajxRouteMotorResultPage);
        this.b = ajxRouteMotorResultPage;
    }

    public final void onPageCreated() {
        super.onPageCreated();
        this.a = new qb((AbstractBasePage) this.mPage);
        this.e = (acg) a.a.a(acg.class);
        if (this.e != null) {
            this.c = this.e.f();
            this.d = this.e.h();
            this.e.a(this.c);
            this.e.b(this.d);
            this.e.a(new String[]{"输入起点", "输入终点"});
        }
    }

    public final void onResume() {
        super.onResume();
        IRouteUI routeInputUI = ((axd) ((Ajx3Page) this.mPage).getContentView().getParent()).getRouteInputUI();
        if (this.e != null) {
            this.c = this.e.f();
            this.d = this.e.h();
            this.e.a((acy) new acy() {
                public final void onDataChange(POI poi, List<POI> list, POI poi2) {
                    boolean z = !ps.this.e.a(poi, ps.this.c);
                    boolean z2 = !ps.this.e.a(poi2, ps.this.d);
                    ps.this.c = poi;
                    ps.this.d = poi2;
                    if (z || z2) {
                        if (z && ps.this.e != null) {
                            ps.this.e.a(ps.this.c);
                        }
                        if (z2 && ps.this.e != null) {
                            ps.this.e.b(ps.this.d);
                        }
                        ps.this.b();
                    }
                }
            });
            this.e.a(this.c);
            this.e.b(this.d);
        }
        this.a.a();
        this.a.a(this.m);
        if (routeInputUI != null && routeInputUI.o()) {
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

    public final void onStart() {
        super.onStart();
        if (this.i) {
            anf.a(1, 0);
            this.i = false;
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
            this.i = true;
        }
    }

    public final void onDestroy() {
        if (this.a.b() != null) {
            this.a.c();
        }
        super.onDestroy();
    }

    public final void onResult(int i2, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i2, resultType, pageBundle);
        if (pageBundle != null && pageBundle.containsKey(RouteHeaderModel.ROUTE_HEADER_MODEL_KEY)) {
            RouteHeaderModel routeHeaderModel = (RouteHeaderModel) pageBundle.getObject(RouteHeaderModel.ROUTE_HEADER_MODEL_KEY);
            if (routeHeaderModel != null && routeHeaderModel.mWidgetId == 3) {
                a("B092", null);
            }
        }
        if (i2 == 65537) {
            AjxRouteMotorResultPage ajxRouteMotorResultPage = this.b;
            String motorInfo = DriveUtil.getMotorInfo();
            if (!TextUtils.isEmpty(motorInfo) && !bny.c.equals(motorInfo)) {
                ro.a(1);
                StringBuilder sb = new StringBuilder("onCheckedChange onMoterPlateSetted isAvoid: ");
                sb.append(DriveUtil.isMotorAvoidLimitedPath());
                AMapLog.d("Daniel-27", sb.toString());
            }
            if (AjxRouteMotorResultPage.a(ajxRouteMotorResultPage.n, motorInfo) && ajxRouteMotorResultPage.j.a(false)) {
                ajxRouteMotorResultPage.j.h();
                ajxRouteMotorResultPage.b.b();
            }
        }
        if (i2 == 120 || i2 == 200) {
            AjxRouteMotorResultPage ajxRouteMotorResultPage2 = this.b;
            if (ajxRouteMotorResultPage2.e != null && !ajxRouteMotorResultPage2.d) {
                String motorRoutingChoice = DriveUtil.getMotorRoutingChoice();
                boolean isMotorAvoidLimitedPath = DriveUtil.isMotorAvoidLimitedPath();
                String motorPlateNum = DriveUtil.getMotorPlateNum();
                if ((!TextUtils.equals(motorRoutingChoice, ajxRouteMotorResultPage2.k) || !TextUtils.equals(Boolean.toString(ajxRouteMotorResultPage2.l), Boolean.toString(isMotorAvoidLimitedPath)) || !TextUtils.equals(ajxRouteMotorResultPage2.m, motorPlateNum)) && ajxRouteMotorResultPage2.j.a(false)) {
                    ajxRouteMotorResultPage2.j.h();
                    ajxRouteMotorResultPage2.b.b();
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
            this.b.a();
        }
        if (i2 != 150 && i2 != 99) {
            return;
        }
        if (i2 == 150 || i2 == 99) {
            this.b.a(false, (AnimationListener) new AnimationListener() {
                public final void onAnimationRepeat(Animation animation) {
                }

                public final void onAnimationStart(Animation animation) {
                }

                public final void onAnimationEnd(Animation animation) {
                    if (((Ajx3Page) ps.this.mPage).isAlive()) {
                        ((Ajx3Page) ps.this.mPage).getContentView().findViewById(R.id.mapTopInteractiveView).setVisibility(0);
                    }
                }
            });
        }
    }

    public final void a(POI poi) {
        if (this.e != null && ((Ajx3Page) this.mPage).isResumed() && this.e.i() == RouteType.MOTOR) {
            this.e.b(RouteType.MOTOR, poi);
        }
    }

    public final int a() {
        if (this.l == -1) {
            PageBundle arguments = ((Ajx3Page) this.mPage).getArguments();
            if (arguments != null) {
                this.l = arguments.getInt("key_source", 100);
            } else {
                this.l = 100;
            }
        }
        return this.l;
    }

    private void a(POI poi, POI poi2, List<POI> list) {
        if (poi != null && poi2 != null) {
            this.c = poi.clone();
            this.d = poi2.clone();
            this.f.clear();
            if (list != null && list.size() > 0) {
                for (int i2 = 0; i2 < list.size(); i2++) {
                    this.f.add(list.get(i2).clone());
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static boolean j() {
        a((String) "B089", (String) "type", (String) H5PageData.KEY_UC_START);
        return false;
    }

    /* access modifiers changed from: private */
    public static boolean k() {
        a((String) "B089", (String) "type", (String) "end");
        return false;
    }

    public final void b() {
        a(null, null, null, null, false, false);
    }

    public final void a(POI poi, POI poi2, ArrayList<ISearchPoiData> arrayList, String str, boolean z, boolean z2) {
        c(poi, poi2, arrayList, str, z, z2);
    }

    private void c(POI poi, POI poi2, ArrayList<ISearchPoiData> arrayList, String str, boolean z, boolean z2) {
        this.b.a(b(poi, poi2, arrayList, str, z, z2));
    }

    public final String b(POI poi, POI poi2, ArrayList<ISearchPoiData> arrayList, String str, boolean z, boolean z2) {
        String str2;
        if (this.e == null) {
            return null;
        }
        this.h = z;
        JSONObject jSONObject = new JSONObject();
        POI f2 = this.e.f();
        List<POI> g2 = this.e.g();
        try {
            jSONObject.put("is_need_request", true);
            jSONObject.put("start_poi", bnx.b(f2));
            if (poi == null) {
                poi = this.e.h();
            } else {
                z = true;
            }
            if (bnx.a(f2, poi)) {
                ToastHelper.showLongToast(AMapAppGlobal.getApplication().getResources().getString(R.string.drive_search_same_from_to));
            }
            jSONObject.put("end_poi", bnx.b(poi));
            jSONObject.put(ConfigerHelper.AOS_URL_KEY, ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.DRIVE_AOS_URL_KEY));
            jSONObject.put("favId", this.b.f);
            jSONObject.put("isScene", z);
            jSONObject.put("isForceOnline", z2);
            if (poi2 != null) {
                jSONObject.put("main_poi", bnx.b(poi2));
            }
            if (arrayList != null && arrayList.size() > 0) {
                jSONObject.put("poi_list", rt.a(arrayList));
            }
            if (this.b.c) {
                jSONObject.put("source_type", "source_save");
            } else {
                String str3 = "";
                switch (a()) {
                    case 100:
                        str3 = "source_common";
                        break;
                    case 101:
                        str3 = "source_save";
                        break;
                    case 102:
                        str3 = "source_etrip";
                        break;
                }
                jSONObject.put("source_type", str3);
            }
            PageBundle arguments = ((Ajx3Page) this.mPage).getArguments();
            if (arguments != null) {
                this.j = arguments.getString("bundle_key_from_page");
                this.k = arguments.getString("bundle_key_source");
                String str4 = this.k;
                String str5 = this.j;
                boolean z3 = false;
                IRouteUI b2 = this.a.b();
                if (b2 != null) {
                    z3 = b2.o();
                }
                if (z3) {
                    str2 = "tabswitch";
                } else if (TextUtils.isEmpty(str4) || !str4.equals("scheme")) {
                    if (!TextUtils.isEmpty(str5)) {
                        if (str5.equals("tips_go")) {
                            str2 = H5SearchType.SEARCH;
                        } else if (str5.equals("planend_record")) {
                            str2 = "planhome";
                        }
                    }
                    str2 = "";
                } else {
                    str2 = "scheme";
                }
                jSONObject.put("fromSource", str2);
            }
            if (TextUtils.isEmpty(str) && arguments != null && arguments.containsKey("bundle_key_from_page")) {
                str = arguments.getString("bundle_key_from_page");
                arguments.remove("bundle_key_from_page");
            }
            if (!TextUtils.isEmpty(str)) {
                jSONObject.put("fromPage", str);
            }
            if (arguments != null) {
                String string = arguments.getString(DriveUtil.SOURCE_APPLICATION);
                if (!TextUtils.isEmpty(string)) {
                    jSONObject.put(DriveUtil.SOURCE_APPLICATION, string);
                }
            }
            int i2 = arguments != null ? arguments.getInt("bundle_key_voice_tokenId", -1) : -1;
            if (i2 >= 0) {
                jSONObject.put("calcRouteVoiceToken", sb.b("requestRoute", i2, null));
                arguments.putInt("bundle_key_voice_tokenId", -1);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        a(f2, this.e.h(), g2);
        return jSONObject.toString();
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

    public final POI c() {
        if (this.e != null) {
            return this.e.h();
        }
        return null;
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        if ("voice".equals(pageBundle.getString("bundle_key_from_page"))) {
            b();
        }
    }

    static /* synthetic */ boolean d(ps psVar) {
        a("B091", null);
        psVar.a.i();
        return false;
    }
}
