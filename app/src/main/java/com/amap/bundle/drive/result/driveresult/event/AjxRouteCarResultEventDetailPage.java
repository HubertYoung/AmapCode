package com.amap.bundle.drive.result.driveresult.event;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.drive.ajx.inter.RouteEventActionInterface;
import com.amap.bundle.drive.ajx.module.ModuleRouteDriveResult;
import com.amap.bundle.logs.AMapLog;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.PageBundle;
import com.autonavi.jni.ae.route.model.ForbiddenLineInfo;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTop;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.map.suspend.refactor.scale.ScaleView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.AjxPageStateInvoker;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.ajx3.views.AmapAjxView.AjxLifeCircleListener;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
@SuppressFBWarnings({"BIT_SIGNED_CHECK"})
public class AjxRouteCarResultEventDetailPage extends MapBasePage<ot> implements OnClickListener, launchModeSingleTop, d, f {
    public String A;
    public String B;
    public double C;
    public double D;
    public int E;
    public String F;
    protected AjxPageStateInvoker G;
    public OnLayoutChangeListener H = new OnLayoutChangeListener() {
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            int i9 = i7 - i5;
            int i10 = i8 - i6;
            int width = view.getWidth();
            int height = view.getHeight();
            if (i9 != width || i10 != height) {
                AjxRouteCarResultEventDetailPage.this.m.removeOnLayoutChangeListener(AjxRouteCarResultEventDetailPage.this.H);
                AjxRouteCarResultEventDetailPage.e(AjxRouteCarResultEventDetailPage.this);
            }
        }
    };
    public RouteEventActionInterface I = new RouteEventActionInterface() {
        public final void showIncidentDetail(String str) {
            if (AjxRouteCarResultEventDetailPage.this.isResumed()) {
                try {
                    AMapLog.d("sudaxia", "showIncidentDetail--json=".concat(String.valueOf(str)));
                    JSONObject jSONObject = new JSONObject(str);
                    int optInt = jSONObject.optInt("incidentId");
                    int optInt2 = jSONObject.optInt("focusIndex");
                    int optInt3 = jSONObject.optInt("type");
                    AjxRouteCarResultEventDetailPage.this.g = optInt3;
                    if (optInt3 == 4) {
                        AjxRouteCarResultEventDetailPage.this.u = new ForbiddenLineInfo();
                        AjxRouteCarResultEventDetailPage.this.v = jSONObject.optInt("forbiddenId");
                        AjxRouteCarResultEventDetailPage.this.w = jSONObject.optInt("forbiddenType");
                        AjxRouteCarResultEventDetailPage.this.x = jSONObject.optInt("vehicleType");
                        AjxRouteCarResultEventDetailPage.this.z = jSONObject.optString("timeDescription");
                        AjxRouteCarResultEventDetailPage.this.A = jSONObject.optString("roadNameString");
                        AjxRouteCarResultEventDetailPage.this.B = jSONObject.optString("nextRoadNameString");
                        AjxRouteCarResultEventDetailPage.this.u.pathId = (long) AjxRouteCarResultEventDetailPage.this.v;
                        AjxRouteCarResultEventDetailPage.this.u.forbiddenType = (byte) AjxRouteCarResultEventDetailPage.this.w;
                        AjxRouteCarResultEventDetailPage.this.u.forbiddenTime = AjxRouteCarResultEventDetailPage.this.z;
                        AjxRouteCarResultEventDetailPage.this.u.carType = (byte) AjxRouteCarResultEventDetailPage.this.x;
                        AjxRouteCarResultEventDetailPage.this.u.roadName = AjxRouteCarResultEventDetailPage.this.A;
                        AjxRouteCarResultEventDetailPage.this.u.nextRoadName = AjxRouteCarResultEventDetailPage.this.B;
                    } else if (optInt3 == 3) {
                        optInt = jSONObject.optInt("poiID");
                    }
                    if (optInt3 != 1) {
                        AjxRouteCarResultEventDetailPage.this.m.setVisibility(8);
                    }
                    AjxRouteCarResultEventDetailPage.this.a(optInt3, optInt, optInt2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public AmapAjxView J;
    private IOverlayManager K;
    /* access modifiers changed from: private */
    public View L;
    private defpackage.csb.a M;
    private boolean N = true;
    private boolean O = true;
    private IPageStateListener P = new IPageStateListener() {
        public final void onAppear() {
        }

        public final void onCover() {
            AjxRouteCarResultEventDetailPage.this.s = true;
        }
    };
    private AjxLifeCircleListener Q = new AjxLifeCircleListener() {
        public final void onAjxContxtCreated(IAjxContext iAjxContext) {
            AjxRouteCarResultEventDetailPage.this.t = (ModuleRouteDriveResult) AjxRouteCarResultEventDetailPage.this.J.getJsModule(ModuleRouteDriveResult.MODULE_NAME);
            AMapLog.d("sudaxia", "onAjxContxtCreated");
            if (AjxRouteCarResultEventDetailPage.this.t != null) {
                AjxRouteCarResultEventDetailPage.this.t.addRouteEventActionInterface(AjxRouteCarResultEventDetailPage.this.I);
            }
        }

        public final void onJsBack(Object obj, String str) {
            AjxRouteCarResultEventDetailPage.this.finish();
        }
    };
    public ViewGroup a;
    public TextView b;
    public TextView c;
    public TextView d;
    public TextView e;
    public ImageView f;
    public int g;
    public ph h;
    public int i;
    public int j;
    public long[] k;
    public int l;
    public ViewGroup m;
    public int n = 0;
    public int o = 1;
    protected cry p;
    public boolean q;
    public alt r;
    public boolean s = false;
    public ModuleRouteDriveResult t = null;
    public ForbiddenLineInfo u;
    public int v;
    public int w;
    public int x;
    public int y;
    public String z;

    class a extends defpackage.csb.a {
        private LayoutParams e;

        public final int c() {
            return 0;
        }

        public final void d() {
        }

        public a(MapBasePage mapBasePage) {
            super(mapBasePage);
        }

        public final void a() {
            if (f() != null) {
                f().setVisibility(4);
            }
            AjxRouteCarResultEventDetailPage.this.p = this.d;
        }

        public final void b() {
            if (AjxRouteCarResultEventDetailPage.this.g == 2 || AjxRouteCarResultEventDetailPage.this.g == 3) {
                AjxRouteCarResultEventDetailPage.this.finish();
                return;
            }
            if (!(AjxRouteCarResultEventDetailPage.this.L == null || this.e == null)) {
                AjxRouteCarResultEventDetailPage.this.L.setLayoutParams(this.e);
            }
        }

        public final void a(int i, int i2, boolean z) {
            this.d.a(i, i2, z);
            if (i > 0 && AjxRouteCarResultEventDetailPage.this.L != null) {
                if (this.e == null) {
                    this.e = AjxRouteCarResultEventDetailPage.this.L.getLayoutParams();
                }
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
                layoutParams.bottomMargin = i;
                AjxRouteCarResultEventDetailPage.this.L.setLayoutParams(layoutParams);
            }
        }
    }

    public int getTrafficEventSource() {
        return 2;
    }

    public void onEventMapEngineActionGesture(alg alg) {
    }

    public void onEventPageMapAnimationFinished(aln aln) {
    }

    public defpackage.csb.a getReleatedTrafficEventHandler() {
        if (this.M == null) {
            this.M = new a(this);
        }
        return this.M;
    }

    private String b() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            if (this.k != null) {
                for (long put : this.k) {
                    jSONArray.put(put);
                }
            }
            jSONObject.put("routeSetId", jSONArray);
            jSONObject.put("incidentId", this.i);
            jSONObject.put("focusIndex", this.j);
            jSONObject.put("type", this.g);
            jSONObject.put("routeType", this.y);
            jSONObject.put("forbiddenId", this.v);
            jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, this.C);
            jSONObject.put("lat", this.D);
            jSONObject.put("z", this.E);
            jSONObject.put("poiID", this.F);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public ot createPresenter() {
        return new ot(this);
    }

    public View getMapSuspendView() {
        if (this.L == null) {
            Context context = getContext();
            ccy suspendWidgetHelper = getSuspendWidgetHelper();
            ccv ccv = new ccv(context);
            ScaleView f2 = suspendWidgetHelper.f();
            if (!(f2 == null || f2.getParent() == null || !(f2.getParent() instanceof ViewGroup))) {
                ((ViewGroup) f2.getParent()).removeView(f2);
            }
            ccv.addWidget(f2, suspendWidgetHelper.g(), 3);
            View a2 = suspendWidgetHelper.a();
            if (!(a2 == null || a2.getParent() == null || !(a2.getParent() instanceof ViewGroup))) {
                ((ViewGroup) a2.getParent()).removeView(a2);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.leftMargin = agn.a(getContext(), 13.0f);
            layoutParams.topMargin = agn.a(getContext(), 13.0f);
            ccv.addWidget(a2, layoutParams, 1);
            this.L = ccv.getSuspendView();
            if (euk.a()) {
                this.L.setPadding(this.L.getPaddingLeft(), this.L.getPaddingTop() + euk.a(getContext()), this.L.getPaddingRight(), this.L.getPaddingBottom());
            }
        }
        return this.L;
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.ajx_event_detail_layout);
        MapManager mapManager = getMapManager();
        if (mapManager != null) {
            this.K = mapManager.getOverlayManager();
        }
    }

    public final void a() {
        cde suspendManager = getSuspendManager();
        if (suspendManager != null) {
            cdz d2 = suspendManager.d();
            if (d2 != null) {
                d2.a(false);
                d2.f();
            }
        }
        alu alu = new alu();
        alu.a = 9001;
        bty gLMapView = getGLMapView();
        if (gLMapView != null) {
            this.r = gLMapView.a(alu);
            alv alv = new alv();
            alv.a = 9001;
            alv.b = 20;
            alv.c = 16;
            alv.d = 1;
            getGLMapView().a(alv);
        }
        a(this.g, this.i, this.j);
        this.N = false;
        if (this.O) {
            this.O = false;
            this.J = new AmapAjxView(getContext());
            this.J.setAjxLifeCircleListener(this.Q);
            ((ViewGroup) getContentView()).addView(this.J, new LayoutParams(-1, -1));
            String b2 = b();
            this.G = new AjxPageStateInvoker(this, this.J);
            this.J.load(ModuleRouteDriveResult.CAR_EVENT_DETAIL, b2, "CAR_EVENT_DETAIL");
            this.J.onResume(false, b2);
        }
    }

    /* access modifiers changed from: private */
    public void a(int i2, int i3, int i4) {
        ku a2 = ku.a();
        StringBuilder sb = new StringBuilder("showDetailTips   eventType:");
        sb.append(i2);
        sb.append("   eventId");
        sb.append(i3);
        a2.c("15198671", sb.toString());
        switch (i2) {
            case 1:
                d();
                a(i3, this.O);
                break;
            case 2:
                b(i3, i2);
                break;
            case 3:
                b(i3, i2);
                break;
            case 4:
                d();
                a(this.u);
                break;
            default:
                switch (i2) {
                    case 15:
                        d();
                        a(a(i3, i4));
                        break;
                    case 16:
                        d();
                        a(a(i3));
                        break;
                }
        }
        this.g = i2;
        this.i = i3;
        this.j = i4;
    }

    private void a(pp ppVar) {
        int i2;
        String str;
        if (ppVar != null) {
            e();
            this.a.setVisibility(8);
            int i3 = ppVar.a;
            if (i3 != 2) {
                switch (i3) {
                    case 81:
                        str = "限高";
                        i2 = R.drawable.tips_event_limit_height;
                        break;
                    case 82:
                        str = "限宽";
                        i2 = R.drawable.tips_event_limit_width;
                        break;
                    default:
                        str = "限高";
                        i2 = R.drawable.tips_event_limit_height;
                        break;
                }
            } else {
                str = "限重";
                i2 = R.drawable.tips_event_limit_weight;
            }
            String str2 = ppVar.c;
            if (TextUtils.isEmpty(str2)) {
                str2 = "当前道路";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("有");
            sb.append(str);
            sb.append(",无法避开");
            String sb2 = sb.toString();
            this.b.setText(str);
            this.e.setText(sb2);
            this.f.setImageResource(i2);
        }
    }

    private void a(defpackage.pe.a aVar) {
        int i2;
        String str;
        if (aVar != null) {
            e();
            this.a.setVisibility(8);
            switch (aVar.a) {
                case 0:
                    str = "限高";
                    i2 = R.drawable.tips_event_limit_height;
                    break;
                case 1:
                    str = "限宽";
                    i2 = R.drawable.tips_event_limit_width;
                    break;
                case 2:
                    str = "限重";
                    i2 = R.drawable.tips_event_limit_weight;
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    b(aVar);
                    return;
                default:
                    str = "限高";
                    i2 = R.drawable.tips_event_limit_height;
                    break;
            }
            String str2 = aVar.c;
            if (TextUtils.isEmpty(str2)) {
                str2 = "当前道路";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("有");
            sb.append(str);
            sb.append(",已为您避开");
            String sb2 = sb.toString();
            this.b.setText(str);
            this.e.setText(sb2);
            this.e.setVisibility(0);
            this.f.setImageResource(i2);
        }
    }

    private defpackage.pe.a a(int i2) {
        List<pe> list = this.h.a(0).i;
        if (!(list == null || list.isEmpty() || list.get(0).b == null)) {
            List<defpackage.pe.a> list2 = list.get(0).b;
            for (int i3 = 0; i3 < list2.size(); i3++) {
                defpackage.pe.a aVar = list2.get(i3);
                if (aVar != null && aVar.b == i2) {
                    return aVar;
                }
            }
        }
        return null;
    }

    private pp a(int i2, int i3) {
        if (i3 >= this.h.b) {
            return null;
        }
        List<pp> list = this.h.a(i3).n;
        if (list == null) {
            return null;
        }
        for (int i4 = 0; i4 < list.size(); i4++) {
            pp ppVar = list.get(i4);
            if (ppVar != null && ppVar.b == i2) {
                return ppVar;
            }
        }
        return null;
    }

    private void b(int i2, int i3) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt(IOverlayManager.EVENT_ID_KEY, i2);
        pageBundle.putBoolean(IOverlayManager.EVENT_IS_FROM_ROUTE_RESULT, true);
        pageBundle.putInt(IOverlayManager.EVENT_LAYERTAG_FROM_ROUTE_RESULT, i3);
        this.K.handleTrafficItemClick(pageBundle);
    }

    private void a(int i2, boolean z2) {
        a(b(i2), z2);
    }

    private void d() {
        if (this.p != null) {
            this.p.b();
        }
    }

    private void e() {
        this.m.setVisibility(0);
        for (int i2 = 0; i2 < this.m.getChildCount(); i2++) {
            this.m.getChildAt(i2).setVisibility(0);
        }
    }

    private sn b(int i2) {
        List<sn> list = this.h.a(0).j;
        if (list == null) {
            return null;
        }
        for (int i3 = 0; i3 < list.size(); i3++) {
            sn snVar = list.get(i3);
            if (snVar != null && snVar.a == i2) {
                return snVar;
            }
        }
        return null;
    }

    private void a(sn snVar, boolean z2) {
        int i2;
        String str;
        e();
        if (snVar != null) {
            switch (snVar.f) {
                case 2:
                    str = getString(R.string.autonavi_avoid_jam_slow_suff);
                    i2 = R.drawable.traffic_report_congestion2;
                    break;
                case 3:
                    str = getString(R.string.autonavi_avoid_jam_bad_suff);
                    i2 = R.drawable.traffic_report_congestion2;
                    break;
                case 4:
                    str = getString(R.string.autonavi_avoid_jam_very_bad_suff);
                    i2 = R.drawable.traffic_report_congestion2;
                    break;
                default:
                    str = getString(R.string.autonavi_avoid_jam_slow_suff);
                    i2 = R.drawable.traffic_report_congestion2;
                    break;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(snVar.d);
            sb.append(str);
            String sb2 = sb.toString();
            String str2 = snVar.p;
            this.b.setText(sb2);
            this.e.setText(str2);
            this.f.setImageResource(i2);
            if (z2) {
                qd.a(getContext(), this.m, null);
            }
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.route_car_result_event_detail_close) {
            finish();
        }
    }

    private void a(ForbiddenLineInfo forbiddenLineInfo) {
        String str;
        int i2;
        String str2;
        e();
        this.a.setVisibility(0);
        switch (forbiddenLineInfo.forbiddenType) {
            case 0:
                i2 = R.drawable.tips_event_forbid_turn_left;
                str = "禁止左转";
                break;
            case 1:
                i2 = R.drawable.tips_event_forbid_turn_right;
                str = "禁止右转";
                break;
            case 2:
                i2 = R.drawable.tips_event_forbid_turn_hard_left;
                str = "禁止左掉头";
                break;
            case 3:
                i2 = R.drawable.tips_event_forbid_turn_hard_right;
                str = "禁止右掉头";
                break;
            case 4:
                i2 = R.drawable.tips_event_forbid_go_straight;
                str = "禁止直行";
                break;
            default:
                i2 = R.drawable.tips_event_forbid_go_straight;
                str = "禁止直行";
                break;
        }
        this.b.setText(str);
        this.f.setImageResource(i2);
        if (!TextUtils.isEmpty(forbiddenLineInfo.roadName) || !TextUtils.isEmpty(forbiddenLineInfo.nextRoadName)) {
            this.e.setVisibility(0);
            if (TextUtils.isEmpty(forbiddenLineInfo.roadName)) {
                StringBuilder sb = new StringBuilder("从 无名道路 到 ");
                sb.append(forbiddenLineInfo.nextRoadName);
                str2 = sb.toString();
            } else if (TextUtils.isEmpty(forbiddenLineInfo.nextRoadName)) {
                StringBuilder sb2 = new StringBuilder("从 ");
                sb2.append(forbiddenLineInfo.roadName);
                sb2.append(" 到 无名道路");
                str2 = sb2.toString();
            } else if (forbiddenLineInfo.roadName.equals(forbiddenLineInfo.nextRoadName)) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(forbiddenLineInfo.roadName);
                sb3.append("交叉路口");
                sb3.append(str);
                str2 = sb3.toString();
            } else {
                StringBuilder sb4 = new StringBuilder("从 ");
                sb4.append(forbiddenLineInfo.roadName);
                sb4.append(" 到 ");
                sb4.append(forbiddenLineInfo.nextRoadName);
                str2 = sb4.toString();
            }
            this.e.setText(str2);
        } else {
            this.e.setVisibility(8);
        }
        if (!TextUtils.isEmpty(forbiddenLineInfo.forbiddenTime)) {
            this.c.setText(Html.fromHtml(forbiddenLineInfo.forbiddenTime));
        }
        this.d.setText(a(forbiddenLineInfo.carType));
    }

    private void b(defpackage.pe.a aVar) {
        String str;
        int i2;
        String str2;
        if (aVar.a != 0 && aVar.a != 1 && aVar.a != 2) {
            this.m.setVisibility(0);
            this.a.setVisibility(0);
            switch (aVar.a) {
                case 3:
                    i2 = R.drawable.tips_event_forbid_turn_left;
                    str = "禁止左转";
                    break;
                case 4:
                    i2 = R.drawable.tips_event_forbid_turn_right;
                    str = "禁止右转";
                    break;
                case 5:
                    i2 = R.drawable.tips_event_forbid_turn_hard_left;
                    str = "禁止左掉头";
                    break;
                case 6:
                    i2 = R.drawable.tips_event_forbid_turn_hard_right;
                    str = "禁止右掉头";
                    break;
                case 7:
                    i2 = R.drawable.tips_event_forbid_go_straight;
                    str = "禁止直行";
                    break;
                default:
                    i2 = R.drawable.tips_event_forbid_go_straight;
                    str = "禁止直行";
                    break;
            }
            this.b.setText(str);
            this.f.setImageResource(i2);
            if (!TextUtils.isEmpty(aVar.d) || !TextUtils.isEmpty(aVar.e)) {
                this.e.setVisibility(0);
                if (TextUtils.isEmpty(aVar.d)) {
                    StringBuilder sb = new StringBuilder("从 无名道路 到 ");
                    sb.append(aVar.e);
                    str2 = sb.toString();
                } else if (TextUtils.isEmpty(aVar.e)) {
                    StringBuilder sb2 = new StringBuilder("从 ");
                    sb2.append(aVar.d);
                    sb2.append(" 到 无名道路");
                    str2 = sb2.toString();
                } else if (aVar.d.equals(aVar.e)) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(aVar.d);
                    sb3.append("交叉路口");
                    sb3.append(str);
                    str2 = sb3.toString();
                } else {
                    StringBuilder sb4 = new StringBuilder("从 ");
                    sb4.append(aVar.d);
                    sb4.append(" 到 ");
                    sb4.append(aVar.e);
                    str2 = sb4.toString();
                }
                this.e.setText(str2);
            } else {
                this.e.setVisibility(8);
            }
            if (!TextUtils.isEmpty(aVar.f)) {
                this.c.setText(Html.fromHtml(aVar.f));
            }
            this.d.setText(a((byte) aVar.g));
        }
    }

    private static String a(byte b2) {
        StringBuilder sb = new StringBuilder();
        Context appContext = AMapPageUtil.getAppContext();
        if ((b2 & 1) == 1) {
            if (sb.length() > 0) {
                sb.append("、");
            }
            sb.append(appContext.getString(R.string.all_car_truck));
        }
        if ((b2 & 2) == 2) {
            if (sb.length() > 0) {
                sb.append("、");
            }
            sb.append(appContext.getString(R.string.all_car));
        }
        if ((b2 & 4) == 4) {
            if (sb.length() > 0) {
                sb.append("、");
            }
            sb.append(appContext.getString(R.string.mini_truck));
        }
        if ((b2 & 8) == 8) {
            if (sb.length() > 0) {
                sb.append("、");
            }
            sb.append(appContext.getString(R.string.light_truck));
        }
        if ((b2 & 16) == 16) {
            if (sb.length() > 0) {
                sb.append("、");
            }
            sb.append(appContext.getString(R.string.medium_truck));
        }
        if ((b2 & 32) == 32) {
            if (sb.length() > 0) {
                sb.append("、");
            }
            sb.append(appContext.getString(R.string.heavy_truck));
        }
        if ((b2 & 64) == 64) {
            if (sb.length() > 0) {
                sb.append("、");
            }
            sb.append(appContext.getString(R.string.heavy_trailer));
        }
        return sb.toString();
    }

    public void onPageResume() {
        super.onPageResume();
        if (this.J != null) {
            this.J.setVisibility(0);
        }
        if (this.G != null) {
            this.G.onResume();
            this.G.resumeActivityState();
        }
    }

    public void onPageStop() {
        super.onPageStop();
        if (this.G != null) {
            this.G.onStop();
        }
    }

    public void onPageDestroy() {
        super.onPageDestroy();
        if (this.J != null) {
            this.J.destroy();
            this.J.onAjxContextCreated(null);
            this.J = null;
        }
        if (this.G != null) {
            this.G.onDestroy();
        }
    }

    static /* synthetic */ void e(AjxRouteCarResultEventDetailPage ajxRouteCarResultEventDetailPage) {
        if (ajxRouteCarResultEventDetailPage.getMapView() != null) {
            bty mapView = ajxRouteCarResultEventDetailPage.getMapView();
            mapView.b(tt.a((Context) ajxRouteCarResultEventDetailPage.getActivity()) / 2, ((tt.b((Context) ajxRouteCarResultEventDetailPage.getActivity()) - ags.d(ajxRouteCarResultEventDetailPage.getActivity())) - ajxRouteCarResultEventDetailPage.m.getHeight()) / 2);
        }
    }
}
