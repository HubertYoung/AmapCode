package defpackage;

import android.graphics.Point;
import android.graphics.Rect;
import android.location.Location;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor;
import com.autonavi.jni.eyrie.amap.tracker.TrackInfo;
import com.autonavi.jni.eyrie.amap.tracker.TrackInfoKeyType;
import com.autonavi.jni.eyrie.amap.tracker.TrackType;
import com.autonavi.jni.route.health.HealthParamKey;
import com.autonavi.jni.route.health.HealthPoint;
import com.autonavi.jni.route.health.HealthPointStatus;
import com.autonavi.jni.route.health.IHealthBike;
import com.autonavi.jni.route.health.IHealthBikeSharing;
import com.autonavi.jni.route.health.PathLineParser;
import com.autonavi.jni.route.health.TraceStatistics;
import com.autonavi.jni.route.health.TraceStatus;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.IPageHost;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IActvitiyStateListener;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.route.foot.footnavi.FootNaviLocation;
import com.autonavi.minimap.route.logs.track.TrackPostUtils;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory;
import com.autonavi.minimap.route.sharebike.model.RideState;
import com.autonavi.minimap.route.sharebike.order.OrderInfo;
import com.autonavi.minimap.route.sharebike.order.OrderState;
import com.autonavi.minimap.route.sharebike.page.ShareBikePage;
import com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage;
import com.autonavi.minimap.route.sharebike.page.ShareRidingMapPage;
import com.autonavi.minimap.route.sharebike.page.ShareRidingMapPage.TipStyle;
import com.autonavi.minimap.widget.ConfirmDlg;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ehg reason: default package */
/* compiled from: ShareRidingMapPresenter */
public final class ehg extends eae<ShareRidingMapPage> implements com.autonavi.minimap.route.sharebike.overlay.ShareBikeAddressTipOverlay.a, defpackage.ecc.a, egd {
    /* access modifiers changed from: private */
    public ConfirmDlg A;
    /* access modifiers changed from: private */
    public int B;
    /* access modifiers changed from: private */
    public boolean C;
    /* access modifiers changed from: private */
    public boolean D;
    private boolean E;
    private dzv F;
    private dzv G;
    private int H;
    private int I;
    private Point J;
    private String K;
    /* access modifiers changed from: private */
    public String L;
    private boolean M;
    /* access modifiers changed from: private */
    public boolean N;
    /* access modifiers changed from: private */
    public boolean O;
    /* access modifiers changed from: private */
    public boolean P;
    /* access modifiers changed from: private */
    public boolean Q;
    private boolean R;
    public boolean a;
    public boolean b;
    public boolean c;
    public boolean d;
    public boolean e;
    /* access modifiers changed from: private */
    public ege f;
    /* access modifiers changed from: private */
    public ehb g;
    /* access modifiers changed from: private */
    public egw h;
    /* access modifiers changed from: private */
    public ehp i;
    /* access modifiers changed from: private */
    public boolean j;
    /* access modifiers changed from: private */
    public boolean k;
    private boolean l;
    /* access modifiers changed from: private */
    public HealthPointStatus m;
    /* access modifiers changed from: private */
    public ArrayList<HealthPoint> n;
    /* access modifiers changed from: private */
    public ArrayList<HealthPoint> o;
    private ArrayList<egs> p;
    private String q;
    private TraceStatistics r;
    /* access modifiers changed from: private */
    public long s;
    private int t;
    private int u;
    private IPageHost v;
    private Runnable w;
    private HealthPoint x;
    private Runnable y;
    /* access modifiers changed from: private */
    public GeoPoint z;

    /* renamed from: ehg$a */
    /* compiled from: ShareRidingMapPresenter */
    static class a extends ect<ehg> {
        a(ehg ehg) {
            super(ehg);
        }

        public final void run() {
            ehg ehg = (ehg) a();
            ehg.j = false;
            if (ehg != null) {
                ehg.k();
            }
        }
    }

    public final void a(int i2, int i3) {
    }

    public ehg(ShareRidingMapPage shareRidingMapPage) {
        super(shareRidingMapPage);
        this.a = true;
        this.n = new ArrayList<>();
        this.o = new ArrayList<>();
        this.p = new ArrayList<>();
        this.q = getClass().getSimpleName();
        this.s = 0;
        this.y = new a(this);
        this.C = false;
        this.D = false;
        this.H = 0;
        this.I = 0;
        this.c = true;
        this.P = true;
        this.e = false;
        this.H = ags.a(((ShareRidingMapPage) this.mPage).getContext()).width();
        this.I = ags.a(((ShareRidingMapPage) this.mPage).getContext()).height();
        this.J = new Point(this.H / 2, (int) (((float) this.I) * 0.667f));
        this.f = new ege();
        ege ege = this.f;
        if (ege.b == null) {
            defpackage.eao.a.b(TrackType.SHAREBIKE);
            boolean a2 = defpackage.eao.a.a();
            String a3 = TrackPostUtils.a(TrackType.SHAREBIKE, a2);
            ege.b = IHealthBikeSharing.CreateHealthBikeSharing(ege);
            if (ege.b != null) {
                ege.b.SetParam(HealthParamKey.HPK_WORKSPACE, a3);
                ege.b.SetParam(HealthParamKey.HPK_ENABLE_LOG, a2 ? "1" : "0");
                TrackPostUtils.a("init sharebike engine");
                ege.h = TrackInfo.createTrackInfo(TrackType.SHAREBIKE);
                if (ege.h != null) {
                    ege.h.set(TrackInfoKeyType.SdkVersion, IHealthBike.GetVersion());
                    ege.b.SetTrackInfo(ege.h);
                }
                defpackage.eao.a.a("ENGINE_OUT", "Riding Init success", "version = ", IHealthBike.GetVersion(), "log folder = ", a3, "debug = ", Boolean.valueOf(a2));
            }
        }
        if (ege.d == null) {
            ege.d = new FootNaviLocation();
        }
        ege.d.startLocation(ege);
        this.f.c = this;
        String b2 = ehs.b("share_bike_order_id");
        if (!TextUtils.isEmpty(b2)) {
            String b3 = ehl.a(AMapPageUtil.getAppContext()).b();
            if (!TextUtils.equals(b2, b3) && !TextUtils.isEmpty(b3)) {
                ehl.a(AMapPageUtil.getAppContext()).b(b3);
            }
        }
        if (egj.a().a >= 61) {
            this.N = true;
        } else if (!ehl.a(AMapPageUtil.getAppContext()).a) {
            ehl.a(AMapPageUtil.getAppContext()).a(ehs.b("share_bike_order_id"));
        }
        PageBundle arguments = ((ShareRidingMapPage) this.mPage).getArguments();
        if (arguments != null) {
            this.K = arguments.getString("CpSource");
            this.L = arguments.getString("OrderId");
            this.b = TextUtils.equals("page_go_to_default_page_to_routepage", arguments.getString("bundle_key_back_page"));
            if (arguments.containsKey("gjxq")) {
                this.d = true;
            }
            if (arguments.containsKey("bundle_key_page_from") && arguments.getString("bundle_key_page_from") != null && "5".equals(arguments.getString("bundle_key_page_from").trim())) {
                this.e = true;
            }
        }
        if (TextUtils.isEmpty(this.K)) {
            this.K = ehs.b("share_bike_cp_source");
        }
        if (TextUtils.isEmpty(this.L)) {
            this.L = ehs.b("share_bike_order_id");
        }
        AMapPageUtil.setActivityStateListener((bid) this.mPage, new IActvitiyStateListener() {
            public final void onActivityResume() {
            }

            public final void onActivityStop() {
            }

            public final void onActivityStart() {
                if (ShareRidingMapPage.class.equals(AMapPageUtil.getTopPageClass())) {
                    ehg.this.k = true;
                }
            }

            public final void onActivityPause() {
                if (!ShareRidingMapPage.class.equals(AMapPageUtil.getTopPageClass())) {
                    ehg.this.a = true;
                }
            }
        });
    }

    private void a(POI poi, boolean z2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("from", "1");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00300", "B003", jSONObject);
        String str = "";
        if (poi != null) {
            String name = poi.getName();
            if (!TextUtils.isEmpty(name) && !"我的位置".equals(name)) {
                str = name;
            }
        }
        c();
        if (!z2 || poi == null) {
            String str2 = TextUtils.isEmpty(str) ? "请输入终点" : str;
            SelectFor selectFor = SelectFor.TO_POI;
            PageBundle pageBundle = new PageBundle();
            pageBundle.putInt("search_for", 1);
            pageBundle.putString("hint", str2);
            if (TextUtils.isEmpty(str) || !str.equalsIgnoreCase("我的位置")) {
                pageBundle.putString(TrafficUtil.KEYWORD, str);
            } else {
                pageBundle.putString(TrafficUtil.KEYWORD, "");
            }
            pageBundle.putBoolean("isHideMyPosition", false);
            pageBundle.putObject("selectedfor", selectFor);
            pageBundle.putInt("from_page", 12400);
            pageBundle.putString("SUPER_ID", UploadQueueMgr.MSGTYPE_REALTIME);
            pageBundle.putBoolean("auto_search", false);
            this.a = true;
            ((ShareRidingMapPage) this.mPage).startPageForResult((String) "search.fragment.SearchCallbackFragment", pageBundle, 1002);
            return;
        }
        a(poi);
    }

    public final void c() {
        this.c = false;
        if (this.h != null) {
            egx.a().b(this.h);
        }
    }

    public final void onPageCreated() {
        super.onPageCreated();
        AMapPageUtil.setPageStateListener((bid) this.mPage, new IPageStateListener() {
            public final void onCover() {
            }

            public final void onAppear() {
                if (ehg.this.g != null) {
                    ehg.this.g.b();
                }
            }
        });
        this.g = new ehb((AbstractBaseMapPage) this.mPage);
        ehb ehb = this.g;
        if (ehb.d != null) {
            ehb.d.setOnAddressTipClickListener(this);
        }
        ehs.a((String) "share_bike_start_time_id", String.valueOf(System.currentTimeMillis()));
        this.F = h();
        this.s = ehs.c("share_bike_riding_start_timestamp");
        this.E = new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("traffic", false);
        this.w = new Runnable() {
            public final void run() {
                ehg.this.g();
                GeoPoint c = ehg.this.e();
                if (c != null) {
                    ehg.this.g.a(c.x, c.y, ehg.this.d());
                    ehg.this.g.a(ehg.this.f(), c.x, c.y, ((ShareRidingMapPage) ehg.this.mPage).i);
                    ((ShareRidingMapPage) ehg.this.mPage).getMapView().a(c.x, c.y);
                    ((ShareRidingMapPage) ehg.this.mPage).getMapView().e(16);
                    ((ShareRidingMapPage) ehg.this.mPage).b();
                }
            }
        };
        ebr.a(true).post(this.w);
        ((ShareRidingMapPage) this.mPage).a((RideState) null);
        this.v = (IPageHost) ((ShareRidingMapPage) this.mPage).getActivity();
        RideState c2 = egj.a().c();
        ShareRidingMapPage shareRidingMapPage = (ShareRidingMapPage) this.mPage;
        if (Boolean.parseBoolean(ehs.b("share_bike_unlocking_status_id"))) {
            eht.a((long) ehs.a());
            shareRidingMapPage.a(TipStyle.UNLOCKING);
        } else {
            shareRidingMapPage.a(TipStyle.RIDING);
            shareRidingMapPage.b.updateViewType(c2);
        }
        if (!this.l) {
            ShareRidingMapPage.c(((ShareRidingMapPage) this.mPage).i);
            ((ShareRidingMapPage) this.mPage).b(((ShareRidingMapPage) this.mPage).i);
        }
        egj.a().d();
        if (this.i == null) {
            this.i = new ehp() {
                public final void a(String str) {
                    if (ehg.this.mPage != null && ((ShareRidingMapPage) ehg.this.mPage).isAlive()) {
                        ((ShareRidingMapPage) ehg.this.mPage).a(Long.parseLong(str));
                        eao.e("tylorvan", "CountdownCallback  ---> onTick ".concat(String.valueOf(str)));
                    }
                }

                public final void a() {
                    eao.e("tylorvan", "CountdownCallback  ---> onStop ");
                    ((ShareRidingMapPage) ehg.this.mPage).a(TipStyle.RIDING);
                }
            };
        }
        eht.a(this.i);
        this.h = new egw() {
            public final boolean a(OrderState orderState, OrderInfo orderInfo, int i) {
                RideState rideState = null;
                if (!orderInfo.hasNetFailed) {
                    if (orderInfo.extraData instanceof RideState) {
                        rideState = (RideState) orderInfo.extraData;
                    }
                    if (!ehg.this.c || rideState == null || !rideState.result) {
                        return false;
                    }
                    ehg.this.B = 0;
                    if (ehg.this.N) {
                        String b = ehl.a(AMapPageUtil.getAppContext()).b();
                        if (!TextUtils.isEmpty(b)) {
                            ehl.a(AMapPageUtil.getAppContext()).a(b);
                            ehg.this.N = false;
                        }
                    }
                    if (ehg.this.O && ehg.this.f != null) {
                        ege j = ehg.this.f;
                        if (j.b != null) {
                            defpackage.eao.a.a("CALL_ENGINE", "ResumeTrace");
                            j.b.ResumeTrace();
                            j.e = false;
                        }
                        ehg.b(ehg.this.n);
                        ehg.b(ehg.this.o);
                        if (ehg.this.m != null && HealthPointStatus.HPS_PAUSE.equals(ehg.this.m)) {
                            ehg.this.m = HealthPointStatus.HPS_VALID;
                        }
                        ehg.this.k();
                        ehg.this.j();
                        ehg.this.l();
                    }
                    if (ehg.this.mPage != null && ((ShareRidingMapPage) ehg.this.mPage).isAlive()) {
                        ShareRidingMapPage shareRidingMapPage = (ShareRidingMapPage) ehg.this.mPage;
                        if (rideState != null && rideState.result) {
                            if (rideState.status == 2) {
                                shareRidingMapPage.a(TipStyle.UNLOCKING);
                            } else {
                                shareRidingMapPage.a(TipStyle.RIDING);
                            }
                        }
                        ((ShareRidingMapPage) ehg.this.mPage).b(rideState);
                        new MapSharePreference(SharePreferenceName.SharedPreferences).getStringValue(ehs.b("share_bike_cp_source"), "");
                        ((ShareRidingMapPage) ehg.this.mPage).c(rideState);
                        ((ShareRidingMapPage) ehg.this.mPage).a(rideState);
                    }
                    if (OrderState.IDLE.equals(orderState)) {
                        eao.f("tylorvan", "ShareRidingMapPresenter requestSuccess");
                        egx.a().b(ehg.this.h);
                        eht.b(ehg.this.i);
                        eht.e();
                        egj.a().a(false);
                        ehg.this.a(1);
                    }
                    if (OrderState.ORDER_ERROR_REPORT.equals(orderState)) {
                        eao.f("tylorvan", "ShareRidingMapPresenter requestSuccess");
                        egx.a().b(ehg.this.h);
                        eht.b(ehg.this.i);
                        eht.e();
                        egj.a().a(false);
                        egj.a().d();
                        ehg.this.a(2);
                    }
                } else if (!ehg.this.c) {
                    return false;
                } else {
                    eao.f("tylorvan", "ShareRidingMapPresenter requestFailed");
                    if (ehg.this.B <= 0 && !ehg.this.C) {
                        ehg.this.C = true;
                        ToastHelper.showToast(((ShareRidingMapPage) ehg.this.mPage).getString(R.string.text_share_finish_toast_error_network_order));
                    }
                    if (ehg.this.B == 60 && !ehg.this.D) {
                        ehg.this.D = true;
                        ToastHelper.showToast(((ShareRidingMapPage) ehg.this.mPage).getString(R.string.text_share_riding_toast_error_order));
                    }
                    ehg.this.B = ehg.this.B + 1;
                    if (ehg.this.mPage != null && ((ShareRidingMapPage) ehg.this.mPage).isAlive()) {
                        ((ShareRidingMapPage) ehg.this.mPage).b((RideState) null);
                        ((ShareRidingMapPage) ehg.this.mPage).c((RideState) null);
                        ((ShareRidingMapPage) ehg.this.mPage).a((RideState) null);
                    }
                    if (ehg.this.B == 61) {
                        if (ehg.this.f != null) {
                            ehg.this.f.a();
                            ehg.this.O = true;
                            ((ShareRidingMapPage) ehg.this.mPage).isAlive();
                        }
                        if (!(ehg.this.mPage == null || !((ShareRidingMapPage) ehg.this.mPage).isAlive() || ehg.this.f == null)) {
                            ehg.this.a(ehg.this.f.c(), ehg.this.s, false, false, false);
                        }
                    }
                }
                return false;
            }
        };
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
        double d2 = 0.0d;
        double longitude = latestPosition == null ? 0.0d : latestPosition.getLongitude();
        if (latestPosition != null) {
            d2 = latestPosition.getLatitude();
        }
        egj.a().a(this.K, (float) longitude, (float) d2, eht.a(latestPosition), ehs.b("share_bike_order_id"));
        this.c = true;
        egx.a().a(this.h);
        egx.a().a((egw) ehj.a());
        egx.a().a((egw) egb.a());
        ehl.a(AMapPageUtil.getAppContext()).a((defpackage.ehn.a) new defpackage.ehn.a() {
            public final void a(HealthPoint[] healthPointArr) {
                if (ehg.this.f != null) {
                    if (healthPointArr.length > 0) {
                        HealthPoint healthPoint = healthPointArr[0];
                        ehg.this.z = new GeoPoint(healthPoint.longitude, healthPoint.latitude);
                        ehg.this.a(ehg.this.z);
                    }
                    ehg.this.P = false;
                    ege j = ehg.this.f;
                    if (j.b != null) {
                        j.b.SetGPSArray(healthPointArr);
                    }
                    ege j2 = ehg.this.f;
                    if (j2.b != null) {
                        defpackage.eao.a.a("CALL_ENGINE", "StartTrace");
                        j2.b.StartTrace();
                        j2.g = true;
                        j2.e = false;
                        j2.f = false;
                    }
                    ehg.this.Q = true;
                    if (ehg.this.N) {
                        ehg.this.O = true;
                        ehg.this.f.a();
                    }
                }
            }
        });
        k();
    }

    /* access modifiers changed from: private */
    public void g() {
        if (this.mPage != null && ((ShareRidingMapPage) this.mPage).isAlive()) {
            if (this.t == 0 || this.u == 0) {
                Rect a2 = ags.a(((ShareRidingMapPage) this.mPage).getContext());
                int height = a2.height() - ags.d(((ShareRidingMapPage) this.mPage).getContext());
                this.t = a2.width() / 2;
                float height2 = (float) ((ShareRidingMapPage) this.mPage).g.getHeight();
                if (Float.compare(height2, 0.0f) != 0) {
                    this.u = (int) (((((float) height) - height2) / 2.0f) - 1.0f);
                }
            }
            ((ShareRidingMapPage) this.mPage).getMapManager().getMapView().b(this.t, this.u);
        }
    }

    public final boolean onMapTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                this.j = true;
                break;
            case 1:
                ebr.a(true).removeCallbacks(this.y);
                ebr.a(true).postDelayed(this.y, 10000);
                break;
        }
        return super.onMapTouchEvent(motionEvent);
    }

    public final void a(int i2) {
        if (n()) {
            switch (i2) {
                case 1:
                    if (this.f != null) {
                        a(this.f.c(), this.s, true, true, true);
                        this.f.a(true);
                        this.f.b();
                        this.Q = false;
                        TrackPostUtils.a("go to ride finish page");
                        aho.a(new Runnable() {
                            public final void run() {
                                if (ehg.this.mPage != null && ((ShareRidingMapPage) ehg.this.mPage).isAlive()) {
                                    ((ShareRidingMapPage) ehg.this.mPage).finish();
                                }
                            }
                        });
                        return;
                    }
                    break;
                case 2:
                    if (this.f != null) {
                        this.f.a(true);
                        this.f.b();
                        this.Q = false;
                    }
                    aho.a(new Runnable() {
                        public final void run() {
                            if (ehg.this.mPage != null && ((ShareRidingMapPage) ehg.this.mPage).isAlive()) {
                                ((ShareRidingMapPage) ehg.this.mPage).finish();
                                ((ShareRidingMapPage) ehg.this.mPage).startPage(ShareBikePage.class, (PageBundle) null);
                            }
                        }
                    });
                    break;
            }
        }
    }

    /* access modifiers changed from: private */
    public static void b(ArrayList<HealthPoint> arrayList) {
        if (arrayList != null && arrayList.size() > 0) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                HealthPoint healthPoint = arrayList.get(i2);
                if (healthPoint != null && HealthPointStatus.HPS_PAUSE.equals(healthPoint.status)) {
                    healthPoint.status = HealthPointStatus.HPS_VALID;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(TraceStatistics traceStatistics, long j2, boolean z2, boolean z3, boolean z4) {
        final long currentTimeMillis = System.currentTimeMillis();
        ehs.a((String) "share_bike_end_time_id", String.valueOf(currentTimeMillis));
        if (z2 || z4) {
            final TraceStatistics traceStatistics2 = traceStatistics;
            final long j3 = j2;
            final boolean z5 = z2;
            final boolean z6 = z3;
            final boolean z7 = z4;
            AnonymousClass13 r0 = new Runnable() {
                public final void run() {
                    TraceStatistics traceStatistics = traceStatistics2;
                    RideTraceHistory rideTraceHistory = null;
                    if (traceStatistics2 != null) {
                        eab b2 = eaa.a().b(ehg.this.L);
                        if (b2 != null) {
                            AMapPageUtil.getAppContext();
                            rideTraceHistory = ees.a(bsp.a().a(b2.c));
                        }
                        if (rideTraceHistory == null) {
                            rideTraceHistory = ees.a(traceStatistics, j3, currentTimeMillis);
                        }
                        if (z5) {
                            ehs.a((String) "share_bike_riding_status_id", (String) "false");
                            egj.a().a(false);
                            ees.a(rideTraceHistory);
                            egx.a().a(ehs.b("share_bike_order_id"));
                        }
                        if (z6) {
                            ehl.a(AMapPageUtil.getAppContext()).b(ehs.b("share_bike_order_id"));
                        } else {
                            ehg.this.N = true;
                            ehl.a(AMapPageUtil.getAppContext()).a();
                        }
                    }
                    if (z7) {
                        final bid pageContext = AMapPageUtil.getPageContext();
                        if (pageContext != null && pageContext.isAlive()) {
                            final PageBundle pageBundle = new PageBundle();
                            pageBundle.putObject("data", rideTraceHistory);
                            pageBundle.putBoolean("isfromRidePage", true);
                            pageBundle.putInt("where", 2);
                            eao.f("finishpage", "ShareRidingMap dealTraceStatistics callback");
                            pageBundle.putBoolean("bundle_is_request_coin", true);
                            aho.a(new Runnable() {
                                public final void run() {
                                    if (pageContext != null) {
                                        pageContext.startPage(ShareRidingFinishPage.class, pageBundle);
                                    }
                                }
                            });
                        }
                    }
                }
            };
            aho.a(r0);
        }
    }

    public final void onMapSurfaceChanged(int i2, int i3) {
        super.onMapSurfaceChanged(i2, i3);
        if (this.mPage != null && ((ShareRidingMapPage) this.mPage).isAlive()) {
            g();
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        ((ShareRidingMapPage) this.mPage).a();
        defpackage.eao.a.b();
        m();
        this.f.c = null;
        ((ShareRidingMapPage) this.mPage).c();
        this.f.a(false);
        this.f.b();
        this.Q = false;
        TrackPostUtils.a("riding page onDestroy");
        this.g.a();
        c();
        Rect a2 = ags.a(((ShareRidingMapPage) this.mPage).getContext());
        ((ShareRidingMapPage) this.mPage).getMapView().b(a2.width() / 2, a2.height() / 2);
        bty mapView = ((ShareRidingMapPage) this.mPage).getMapView();
        if (!(mapView == null || this.F == null)) {
            this.J = new Point(this.H / 2, this.I / 2);
            mapView.b(this.J.x, this.J.y);
            ebf.a(mapView, this.F.c, mapView.l(false), this.F.d);
            mapView.g(this.F.b);
            mapView.e(this.F.a);
            mapView.a(this.F.f.x, this.F.f.y);
            if (mapView.w() != ((int) this.F.e)) {
                mapView.f(this.F.e);
            }
        }
        ShareRidingMapPage shareRidingMapPage = (ShareRidingMapPage) this.mPage;
        if (shareRidingMapPage.b != null) {
            shareRidingMapPage.b.releaseTimer();
        }
    }

    public final int d() {
        if (this.x != null) {
            return this.x.angle;
        }
        Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
        if (latestLocation != null) {
            return (int) latestLocation.getBearing();
        }
        return 0;
    }

    public final void onResume() {
        super.onResume();
        this.M = false;
        bqx bqx = (bqx) ank.a(bqx.class);
        if (bqx != null) {
            bqx.a(false, false, false, ((ShareRidingMapPage) this.mPage).getMapManager(), ((ShareRidingMapPage) this.mPage).getContext());
        }
        if (!this.k && this.l) {
            ShareRidingMapPage.c(((ShareRidingMapPage) this.mPage).i);
            ((ShareRidingMapPage) this.mPage).b(((ShareRidingMapPage) this.mPage).i);
        }
        if (this.a) {
            ShareRidingMapPage shareRidingMapPage = (ShareRidingMapPage) this.mPage;
            if (shareRidingMapPage.b != null) {
                shareRidingMapPage.b.resetTimer();
            }
            this.a = false;
        }
        this.l = true;
        egj.a().d();
        if (!this.c && this.h != null) {
            egx.a().a(this.h);
            this.c = true;
        }
        egj.a().a(true);
        ((ShareRidingMapPage) this.mPage).b();
        ((ShareRidingMapPage) this.mPage).c();
        dzv dzv = this.G;
        if (!(this.mPage == null || dzv == null)) {
            bty mapView = ((ShareRidingMapPage) this.mPage).getMapView();
            if (mapView != null) {
                mapView.g(dzv.b);
                mapView.e(dzv.a);
                mapView.a(dzv.f.x, dzv.f.y);
                if (mapView.w() != ((int) dzv.e)) {
                    mapView.f(dzv.e);
                }
            }
        }
        ebr.a(true).post(new Runnable() {
            public final void run() {
                ehg.this.g();
                GeoPoint c = ehg.this.e();
                if (c != null) {
                    ehg.this.g.a(c.x, c.y, ehg.this.d());
                    ehg.this.g.a(ehg.this.f(), c.x, c.y, ((ShareRidingMapPage) ehg.this.mPage).i);
                    ((ShareRidingMapPage) ehg.this.mPage).getMapView().a(c.x, c.y);
                }
            }
        });
        this.g.a.resumeMarker();
        k();
        j();
        l();
        a(this.z);
        if (this.mPage != null && ((ShareRidingMapPage) this.mPage).isAlive()) {
            ShareRidingMapPage shareRidingMapPage2 = (ShareRidingMapPage) this.mPage;
            if (shareRidingMapPage2.c != null && shareRidingMapPage2.i == TipStyle.UNLOCKING) {
                int hours = new Date(System.currentTimeMillis()).getHours();
                if ((hours >= 0 && hours <= 5) || (19 <= hours && hours <= 23)) {
                    shareRidingMapPage2.c.setTorch(true);
                }
            }
        }
    }

    public final void onPause() {
        super.onPause();
        ((ShareRidingMapPage) this.mPage).a();
        this.G = h();
        bqx bqx = (bqx) ank.a(bqx.class);
        if (bqx != null) {
            bqx.a(false, this.E, false, ((ShareRidingMapPage) this.mPage).getMapManager(), ((ShareRidingMapPage) this.mPage).getContext());
        }
        this.k = false;
        if (this.v == null || !this.v.isHostPaused()) {
            c();
        }
        this.M = true;
        ebr.a(true).removeCallbacks(this.w);
        this.g.a();
        this.R = false;
    }

    private dzv h() {
        if (this.mPage != null) {
            bty mapView = ((ShareRidingMapPage) this.mPage).getMapView();
            if (mapView != null) {
                dzv dzv = new dzv(mapView.I(), mapView.v(), mapView.J(), mapView.o(), mapView.j(false), mapView.k(false));
                return dzv;
            }
        }
        return null;
    }

    public final void onStop() {
        super.onStop();
    }

    public final void onResult(int i2, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i2, resultType, pageBundle);
        if (i2 == 1002 && resultType == ResultType.OK && pageBundle != null && pageBundle.containsKey("result_poi")) {
            POI poi = (POI) pageBundle.get("result_poi");
            if (poi != null) {
                a(poi);
            }
        }
        this.a = true;
    }

    public final GeoPoint e() {
        return i() ? new GeoPoint(this.x.longitude, this.x.latitude) : LocationInstrument.getInstance().getLatestPosition();
    }

    private boolean i() {
        return (this.x == null || this.x.longitude == 0.0d || this.x.latitude == 0.0d) ? false : true;
    }

    /* access modifiers changed from: private */
    public void j() {
        if (!this.f.e) {
            int size = this.o.size();
            if (size > 0) {
                AMapLog.e(this.q, "mHelRuns : ".concat(String.valueOf(size)));
                PathLineParser pathLineParser = new PathLineParser(1);
                for (int i2 = 0; i2 < size; i2++) {
                    HealthPoint healthPoint = this.o.get(i2);
                    pathLineParser.addPoint(new GeoPoint(healthPoint.longitude, healthPoint.latitude), false, (int) healthPoint.speed);
                }
                this.g.a(pathLineParser);
            }
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (this.mPage == null || !((ShareRidingMapPage) this.mPage).isAlive()) {
            return super.onBackPressed();
        }
        ShareRidingMapPage shareRidingMapPage = (ShareRidingMapPage) this.mPage;
        if (shareRidingMapPage.d != null && shareRidingMapPage.d.isShown()) {
            ShareRidingMapPage shareRidingMapPage2 = (ShareRidingMapPage) this.mPage;
            if (shareRidingMapPage2.isAlive() && shareRidingMapPage2.d != null) {
                shareRidingMapPage2.d.dismissSelectView();
            }
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        ((ShareRidingMapPage) this.mPage).d();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    /* access modifiers changed from: private */
    public void k() {
        GeoPoint e2 = e();
        if (e2 == null) {
            AMapLog.w(this.q, "map center null!!");
            return;
        }
        defpackage.eao.a.a("drawMRunPosition", e2.getLongitude(), e2.getLatitude());
        int d2 = d();
        if (this.j) {
            g();
            this.g.b(e2.x, e2.y, d2);
        } else {
            g();
            a(e2, d2);
        }
        final int i2 = e2.x;
        final int i3 = e2.y;
        ebr.a(true).post(new Runnable() {
            public final void run() {
                if (((ShareRidingMapPage) ehg.this.mPage).isAlive()) {
                    ehg.this.g.a(ehg.this.f(), i2, i3, ((ShareRidingMapPage) ehg.this.mPage).i);
                }
            }
        });
    }

    private void a(GeoPoint geoPoint, int i2) {
        if (((ShareRidingMapPage) this.mPage).a.b.b) {
            this.g.a(geoPoint, i2);
        } else {
            this.g.a(geoPoint, i2, i2);
        }
    }

    public final void a(GeoPoint geoPoint) {
        if (this.g != null && !this.R && geoPoint != null) {
            this.g.a(geoPoint);
            this.z = geoPoint;
            this.R = true;
        }
    }

    /* access modifiers changed from: private */
    public void l() {
        int size = this.n.size();
        if (size > 0) {
            PathLineParser pathLineParser = new PathLineParser(1);
            for (int i2 = 0; i2 < size; i2++) {
                HealthPoint healthPoint = this.n.get(i2);
                pathLineParser.addPoint(new GeoPoint(healthPoint.longitude, healthPoint.latitude), healthPoint.status == HealthPointStatus.HPS_PAUSE, (int) healthPoint.speed);
            }
            this.g.b(pathLineParser);
        }
    }

    private void a(final POI poi) {
        if (poi != null && this.mPage != null && ((ShareRidingMapPage) this.mPage).isAlive()) {
            final MapSharePreference mapSharePreference = new MapSharePreference((String) "SharedPreferences");
            boolean booleanValue = mapSharePreference.getBooleanValue("agree_ondest_declare", false);
            if (edq.a((bid) this.mPage, LocationInstrument.getInstance().getLatestPosition(), poi.getPoint(), ((ShareRidingMapPage) this.mPage).getString(R.string.sharebike_navi_warning_text))) {
                if (booleanValue) {
                    b(poi);
                    c();
                    return;
                }
                this.A = new ConfirmDlg(((ShareRidingMapPage) this.mPage).getActivity(), new OnClickListener() {
                    public final void onClick(View view) {
                        ehx.b(ShareRidingMapPage.class.getSimpleName());
                        eao.d("wbsww", "Changed status of blue bar when dialog has been dismiss");
                        if (view.getId() == R.id.cancel) {
                            if (ehg.this.A != null && ehg.this.A.isShowing()) {
                                ehg.this.A.dismiss();
                                ehg.this.A = null;
                            }
                        } else if (view.getId() == R.id.confirm) {
                            if (mapSharePreference != null) {
                                mapSharePreference.putBooleanValue("agree_ondest_declare", true);
                            }
                            ehg.this.b(poi);
                            ehg.this.c();
                        }
                    }
                }, R.layout.ondest_declare);
                this.A.setCanceledOnTouchOutside(false);
                ehx.b(ConfirmDlg.class.getSimpleName());
                eao.d("wbsww", "Changed status of blue bar when app show dialog");
                this.A.show();
            }
        }
    }

    /* access modifiers changed from: private */
    public void b(POI poi) {
        PageBundle pageBundle = new PageBundle();
        m();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("endPoi", bnx.b(poi));
            if (((ShareRidingMapPage) this.mPage).i == TipStyle.UNLOCKING) {
                jSONObject.put("fromWhere", "dcjsm");
            } else {
                jSONObject.put("fromWhere", "dcycz");
            }
            pageBundle.putString("bundle_key_obj_result", jSONObject.toString());
            edr.a(0);
            aww aww = (aww) defpackage.esb.a.a.a(aww.class);
            if (aww != null) {
                ((ShareRidingMapPage) this.mPage).startPageForResult(aww.a().a(1), pageBundle, 0);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    private void m() {
        ebr.a(true).removeCallbacks(this.y);
    }

    public final void b() {
        a((String) "navigation");
        a(f(), true);
    }

    public final void a(boolean z2) {
        a(z2 ? "revise" : "blank");
        a(f(), false);
    }

    private void a(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", str);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (((ShareRidingMapPage) this.mPage).i == TipStyle.RIDING) {
            LogManager.actionLogV2("P00300", "B009", jSONObject);
        } else {
            LogManager.actionLogV2("P00310", "B006", jSONObject);
        }
    }

    public final void a(HealthPoint healthPoint) {
        eao.e("tylorvan", "shareRidingLocationChange");
        if (n()) {
            if (System.currentTimeMillis() - this.s > 43200000 && this.s != 0) {
                aho.a(new Runnable() {
                    public final void run() {
                        if (ehg.this.mPage != null && ((ShareRidingMapPage) ehg.this.mPage).isAlive() && ehg.this.f != null) {
                            ehg.this.f.b();
                            ehg.this.Q = false;
                        }
                    }
                });
            }
            if (this.x == null) {
                this.x = new HealthPoint();
            }
            this.x = healthPoint.clone();
            if (healthPoint.status == HealthPointStatus.HPS_VALID || (this.m == HealthPointStatus.HPS_AUTO_PAUSE && healthPoint.status == HealthPointStatus.HPS_VALID)) {
                this.o.add(healthPoint);
                if (!this.M) {
                    j();
                }
                if (this.o.size() == 1) {
                    a(new GeoPoint(healthPoint.longitude, healthPoint.latitude));
                }
            }
            this.m = healthPoint.status;
            if (!this.M) {
                k();
            }
        }
    }

    public final void a(HealthPoint healthPoint, int i2) {
        eao.e("tylorvan", "shareRidingMileStone");
        if (n()) {
            int i3 = i2 / 1000;
            if (i3 <= 100 && healthPoint != null) {
                GeoPoint geoPoint = new GeoPoint(healthPoint.longitude, healthPoint.latitude);
                this.p.add(new egs(geoPoint, i2));
                ehb ehb = this.g;
                PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
                new MapViewLayoutParams(-2, -2, geoPoint, 3).mode = 0;
                View inflate = ((LayoutInflater) ehb.e.getSystemService("layout_inflater")).inflate(R.layout.route_run_milestone_view, null);
                StringBuilder sb = new StringBuilder();
                sb.append(i3);
                ((TextView) inflate.findViewById(R.id.stone_miles)).setText(sb.toString());
                pointOverlayItem.mDefaultMarker = ehb.c.createMarker(ehb.f, inflate, 4, 0.0f, 0.0f, false);
                ehb.c.addItem(pointOverlayItem);
                ehb.f++;
            }
        }
    }

    public final void a(int i2, double d2, long j2) {
        StringBuilder sb = new StringBuilder("shareRidingLengthSpeedTimeint nLength ");
        sb.append(i2);
        sb.append(", double nSpeed ");
        sb.append(d2);
        sb.append(", long lTime");
        sb.append(j2);
        eao.e("tylorvan", sb.toString());
        if (n()) {
            ((ShareRidingMapPage) this.mPage).a(i2);
        }
    }

    public final void a(TraceStatus traceStatus) {
        eao.e("tylorvan", "shareRidingTraceStatus");
        if (this.mPage != null && ((ShareRidingMapPage) this.mPage).isAlive() && traceStatus != null) {
            traceStatus.getValue();
        }
    }

    public final void a(TraceStatistics traceStatistics) {
        eao.e("tylorvan", "shareRidingAllMembersUpdate");
        if (n()) {
            if (this.r == null) {
                this.r = new TraceStatistics();
            }
            if (traceStatistics != null) {
                this.r.gps_array = traceStatistics.gps_array;
                this.r.average_speed = traceStatistics.average_speed;
                this.r.calorie = traceStatistics.calorie;
                this.r.steps = traceStatistics.steps;
                this.r.trace_length = traceStatistics.trace_length;
                this.r.trace_time = traceStatistics.trace_time;
            }
            if (!(this.r == null || this.r.trace_length == 0 || this.P)) {
                ((ShareRidingMapPage) this.mPage).a(this.r.trace_length);
                this.P = true;
            }
            this.o.clear();
            this.g.b.clear();
            if (this.r != null) {
                HealthPoint[] healthPointArr = this.r.gps_array;
                if (healthPointArr != null) {
                    ArrayList<HealthPoint> arrayList = new ArrayList<>();
                    Collections.addAll(arrayList, healthPointArr);
                    this.n = arrayList;
                    if (!this.O) {
                        b(this.n);
                    }
                    l();
                    HealthPoint healthPoint = this.n.get(this.n.size() - 1);
                    if (healthPoint.status == HealthPointStatus.HPS_VALID) {
                        this.o.add(healthPoint);
                    }
                }
            }
        }
    }

    private boolean n() {
        return this.mPage != null && ((ShareRidingMapPage) this.mPage).isAlive();
    }

    public final void a() {
        eao.e("tylorvan", "onSatNumberChanged");
    }

    public final POI f() {
        if (!this.e) {
            return ehu.b();
        }
        return null;
    }
}
