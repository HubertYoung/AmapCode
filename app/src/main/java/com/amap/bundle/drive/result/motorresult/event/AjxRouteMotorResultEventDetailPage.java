package com.amap.bundle.drive.result.motorresult.event;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.drive.ajx.inter.IEventDetailDialog;
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
public class AjxRouteMotorResultEventDetailPage extends MapBasePage<pr> implements OnClickListener, launchModeSingleTop, d, f {
    public double A;
    public double B;
    public int C;
    public String D;
    public String E = "";
    protected AjxPageStateInvoker F;
    public OnLayoutChangeListener G = new OnLayoutChangeListener() {
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            int i9 = i7 - i5;
            int i10 = i8 - i6;
            int width = view.getWidth();
            int height = view.getHeight();
            if (i9 != width || i10 != height) {
                AjxRouteMotorResultEventDetailPage.this.m.removeOnLayoutChangeListener(AjxRouteMotorResultEventDetailPage.this.G);
                AjxRouteMotorResultEventDetailPage.f(AjxRouteMotorResultEventDetailPage.this);
            }
        }
    };
    public RouteEventActionInterface H = new RouteEventActionInterface() {
        public final void showIncidentDetail(String str) {
            if (AjxRouteMotorResultEventDetailPage.this.isResumed()) {
                try {
                    AMapLog.d("sudaxia", "showIncidentDetail--json=".concat(String.valueOf(str)));
                    JSONObject jSONObject = new JSONObject(str);
                    int optInt = jSONObject.optInt("incidentId");
                    int optInt2 = jSONObject.optInt("focusIndex");
                    int optInt3 = jSONObject.optInt("type");
                    AjxRouteMotorResultEventDetailPage.this.g = optInt3;
                    if (optInt3 == 4) {
                        AjxRouteMotorResultEventDetailPage.this.t = new ForbiddenLineInfo();
                        AjxRouteMotorResultEventDetailPage.this.u = jSONObject.optInt("forbiddenId");
                        AjxRouteMotorResultEventDetailPage.this.v = jSONObject.optInt("forbiddenType");
                        AjxRouteMotorResultEventDetailPage.this.w = jSONObject.optInt("vehicleType");
                        AjxRouteMotorResultEventDetailPage.this.x = jSONObject.optString("timeDescription");
                        AjxRouteMotorResultEventDetailPage.this.y = jSONObject.optString("roadNameString");
                        AjxRouteMotorResultEventDetailPage.this.z = jSONObject.optString("nextRoadNameString");
                        AjxRouteMotorResultEventDetailPage.this.t.pathId = (long) AjxRouteMotorResultEventDetailPage.this.u;
                        AjxRouteMotorResultEventDetailPage.this.t.forbiddenType = (byte) AjxRouteMotorResultEventDetailPage.this.v;
                        AjxRouteMotorResultEventDetailPage.this.t.forbiddenTime = AjxRouteMotorResultEventDetailPage.this.x;
                        AjxRouteMotorResultEventDetailPage.this.t.carType = (byte) AjxRouteMotorResultEventDetailPage.this.w;
                        AjxRouteMotorResultEventDetailPage.this.t.roadName = AjxRouteMotorResultEventDetailPage.this.y;
                        AjxRouteMotorResultEventDetailPage.this.t.nextRoadName = AjxRouteMotorResultEventDetailPage.this.z;
                    } else if (optInt3 == 3) {
                        optInt = jSONObject.optInt("poiID");
                    } else if (optInt3 == 8 || optInt3 == 10 || optInt3 == 9) {
                        AjxRouteMotorResultEventDetailPage.this.d();
                    }
                    if (optInt3 != 1) {
                        AjxRouteMotorResultEventDetailPage.s(AjxRouteMotorResultEventDetailPage.this);
                    }
                    AjxRouteMotorResultEventDetailPage.this.a(optInt3, optInt, optInt2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private IOverlayManager I;
    private defpackage.csb.a J;
    private boolean K = true;
    private boolean L = true;
    private View M;
    /* access modifiers changed from: private */
    public int N = -1;
    /* access modifiers changed from: private */
    public int O = -1;
    private AjxLifeCircleListener P = new AjxLifeCircleListener() {
        public final void onAjxContxtCreated(IAjxContext iAjxContext) {
            AjxRouteMotorResultEventDetailPage.this.s = (ModuleRouteDriveResult) AjxRouteMotorResultEventDetailPage.this.l.getJsModule(ModuleRouteDriveResult.MODULE_NAME);
            AMapLog.d("sudaxia", "onAjxContxtCreated");
            if (AjxRouteMotorResultEventDetailPage.this.s != null) {
                AjxRouteMotorResultEventDetailPage.this.s.addRouteEventActionInterface(AjxRouteMotorResultEventDetailPage.this.H);
                AjxRouteMotorResultEventDetailPage.this.s.setIEventDetailDialog(AjxRouteMotorResultEventDetailPage.this.Q);
            }
        }

        public final void onJsBack(Object obj, String str) {
            AjxRouteMotorResultEventDetailPage.this.finish();
        }
    };
    /* access modifiers changed from: private */
    public IEventDetailDialog Q = new IEventDetailDialog() {
        public final void setEventDetailDialogHeight(String str) {
            if (!TextUtils.isEmpty(str)) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    AjxRouteMotorResultEventDetailPage.this.N = jSONObject.getInt("type");
                    AjxRouteMotorResultEventDetailPage.this.O = jSONObject.getInt("height");
                    if (AjxRouteMotorResultEventDetailPage.this.g == AjxRouteMotorResultEventDetailPage.this.N) {
                        AjxRouteMotorResultEventDetailPage.a(AjxRouteMotorResultEventDetailPage.this, AjxRouteMotorResultEventDetailPage.this.O);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
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
    public AmapAjxView l;
    public ViewGroup m;
    public int n = 0;
    public int o = 1;
    protected cry p;
    public boolean q;
    public alt r;
    public ModuleRouteDriveResult s;
    public ForbiddenLineInfo t;
    public int u;
    public int v;
    public int w;
    public String x;
    public String y;
    public String z;

    class a extends defpackage.csb.a {
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
            AjxRouteMotorResultEventDetailPage.this.p = this.d;
        }

        public final void b() {
            if (AjxRouteMotorResultEventDetailPage.this.g == 2 || AjxRouteMotorResultEventDetailPage.this.g == 3) {
                AjxRouteMotorResultEventDetailPage.this.finish();
                return;
            }
            if (AjxRouteMotorResultEventDetailPage.this.g == 8 || AjxRouteMotorResultEventDetailPage.this.g == 9 || AjxRouteMotorResultEventDetailPage.this.g == 10) {
                if (AjxRouteMotorResultEventDetailPage.this.g == AjxRouteMotorResultEventDetailPage.this.N) {
                    AjxRouteMotorResultEventDetailPage.a(AjxRouteMotorResultEventDetailPage.this, AjxRouteMotorResultEventDetailPage.this.O);
                }
            } else if (AjxRouteMotorResultEventDetailPage.this.m != null) {
                AjxRouteMotorResultEventDetailPage.a(AjxRouteMotorResultEventDetailPage.this, AjxRouteMotorResultEventDetailPage.this.m.getHeight());
            }
        }

        public final void a(int i, int i2, boolean z) {
            this.d.a(i, i2, z);
            AjxRouteMotorResultEventDetailPage.a(AjxRouteMotorResultEventDetailPage.this, i);
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
        if (this.J == null) {
            this.J = new a(this);
        }
        return this.J;
    }

    private String b() {
        if (!TextUtils.isEmpty(this.E)) {
            return this.E;
        }
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
            jSONObject.put("forbiddenId", this.u);
            jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, this.A);
            jSONObject.put("lat", this.B);
            jSONObject.put("z", this.C);
            jSONObject.put("poiID", this.D);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public pr createPresenter() {
        return new pr(this);
    }

    public View getMapSuspendView() {
        if (this.M == null) {
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
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            layoutParams.leftMargin = agn.a(getContext(), 13.0f);
            layoutParams.topMargin = agn.a(getContext(), 13.0f);
            ccv.addWidget(a2, layoutParams, 1);
            this.M = ccv.getSuspendView();
            if (euk.a()) {
                int a3 = euk.a(getContext());
                View view = this.M;
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + a3, view.getPaddingRight(), view.getPaddingBottom());
            }
        }
        return this.M;
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.ajx_event_detail_layout);
        MapManager mapManager = getMapManager();
        if (mapManager != null) {
            this.I = mapManager.getOverlayManager();
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
            gLMapView.a(alv);
        }
        a(this.g, this.i, this.j);
        if (this.L) {
            this.L = false;
            this.l = new AmapAjxView(getContext());
            this.l.setAjxLifeCircleListener(this.P);
            ((ViewGroup) getContentView()).addView(this.l, new ViewGroup.LayoutParams(-1, -1));
            String b2 = b();
            this.F = new AjxPageStateInvoker(this, this.l);
            this.l.load(ModuleRouteDriveResult.MOTOR_EVENT_DETAIL, b2, "CAR_EVENT_DETAIL");
            this.l.onResume(false, b2);
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
                a(i3, this.L);
                break;
            case 2:
                a(i3, i2);
                break;
            case 3:
                a(i3, i2);
                break;
            case 4:
                d();
                a(this.t);
                break;
        }
        this.g = i2;
        this.i = i3;
        this.j = i4;
    }

    private void a(int i2, int i3) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt(IOverlayManager.EVENT_ID_KEY, i2);
        pageBundle.putBoolean(IOverlayManager.EVENT_IS_FROM_ROUTE_RESULT, true);
        pageBundle.putInt(IOverlayManager.EVENT_LAYERTAG_FROM_ROUTE_RESULT, i3);
        this.I.handleTrafficItemClick(pageBundle);
    }

    private void a(int i2, boolean z2) {
        a(a(i2), z2);
    }

    /* access modifiers changed from: private */
    public void d() {
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

    private sn a(int i2) {
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
        Context appContext = AMapPageUtil.getAppContext();
        if (appContext != null) {
            this.d.setText(appContext.getText(R.string.motor_bike));
        }
    }

    public void onPageResume() {
        super.onPageResume();
        if (this.l != null) {
            this.l.setVisibility(0);
        }
        if (this.F != null) {
            this.F.onResume();
            this.F.resumeActivityState();
        }
    }

    public void onPageStop() {
        super.onPageStop();
        if (this.F != null) {
            this.F.onStop();
        }
    }

    public void onPageDestroy() {
        super.onPageDestroy();
        if (this.l != null) {
            this.l.destroy();
            this.l.onAjxContextCreated(null);
            this.l = null;
        }
        if (this.F != null) {
            this.F.onDestroy();
        }
    }

    static /* synthetic */ void a(AjxRouteMotorResultEventDetailPage ajxRouteMotorResultEventDetailPage, int i2) {
        if (i2 > 0 && ajxRouteMotorResultEventDetailPage.M != null) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            layoutParams.bottomMargin = i2;
            ajxRouteMotorResultEventDetailPage.M.setLayoutParams(layoutParams);
        }
    }

    static /* synthetic */ void f(AjxRouteMotorResultEventDetailPage ajxRouteMotorResultEventDetailPage) {
        if (ajxRouteMotorResultEventDetailPage.getMapView() != null) {
            bty mapView = ajxRouteMotorResultEventDetailPage.getMapView();
            mapView.b(tt.a((Context) ajxRouteMotorResultEventDetailPage.getActivity()) / 2, ((tt.b((Context) ajxRouteMotorResultEventDetailPage.getActivity()) - ags.d(ajxRouteMotorResultEventDetailPage.getActivity())) - ajxRouteMotorResultEventDetailPage.m.getHeight()) / 2);
        }
    }

    static /* synthetic */ void s(AjxRouteMotorResultEventDetailPage ajxRouteMotorResultEventDetailPage) {
        if (ajxRouteMotorResultEventDetailPage.m != null) {
            ajxRouteMotorResultEventDetailPage.m.setVisibility(8);
        }
    }
}
