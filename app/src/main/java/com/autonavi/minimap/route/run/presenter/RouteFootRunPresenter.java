package com.autonavi.minimap.route.run.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.route.health.HealthPoint;
import com.autonavi.jni.route.health.HealthPointStatus;
import com.autonavi.jni.route.health.PathLineParser;
import com.autonavi.jni.route.health.TraceStatistics;
import com.autonavi.jni.route.health.TraceStatus;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.LineOverlayItem;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.route.foot.util.AmapBroadcastReceiver;
import com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil;
import com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil.OperationType;
import com.autonavi.minimap.route.run.page.RouteFootRunMapPage;
import com.autonavi.sdk.location.LocationInstrument;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.iflytek.tts.TtsService.Tts;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class RouteFootRunPresenter extends efm {
    /* access modifiers changed from: private */
    public static int O = 0;
    private static final String r = "RouteFootRunPresenter";
    private Runnable A = new d(this);
    private Runnable B;
    private GeoPoint C;
    /* access modifiers changed from: private */
    public boolean D = false;
    /* access modifiers changed from: private */
    public boolean E = false;
    private boolean F = false;
    private RunNaviSystemKeyCode G;
    /* access modifiers changed from: private */
    public ctl H;
    /* access modifiers changed from: private */
    public boolean I = true;
    private boolean J = true;
    /* access modifiers changed from: private */
    public boolean K;
    private boolean L;
    private int M;
    private int N;
    private boolean P;
    private boolean Q = true;
    private Runnable R = new Runnable() {
        public final void run() {
            if (RouteFootRunPresenter.this.mPage != null && ((RouteFootRunMapPage) RouteFootRunPresenter.this.mPage).isAlive()) {
                RouteFootRunPresenter.this.K = true;
                RouteFootRunPresenter.this.b(ecv.a(ecv.a()));
            }
        }
    };
    private defpackage.drn.c S = new defpackage.drn.c() {
        public final void a() {
        }

        public final void c() {
        }

        public final void b() {
            RouteFootRunPresenter.y(RouteFootRunPresenter.this);
        }
    };
    public float o = -1.0f;
    public Handler p = new c(this);
    public boolean q = true;
    private String[] s;
    private String[] t;
    private String[] u;
    private float v = -1.0f;
    /* access modifiers changed from: private */
    public HealthPointStatus w;
    /* access modifiers changed from: private */
    public TraceStatistics x;
    /* access modifiers changed from: private */
    public HealthPoint y;
    private Runnable z = new a(this);

    static class RunNaviSystemKeyCode extends AmapBroadcastReceiver<RouteFootRunPresenter> {
        public RunNaviSystemKeyCode(RouteFootRunPresenter routeFootRunPresenter) {
            super(routeFootRunPresenter);
        }

        public void onReceive(Context context, Intent intent) {
            RouteFootRunPresenter routeFootRunPresenter = (RouteFootRunPresenter) a();
            if (routeFootRunPresenter != null) {
                String action = intent.getAction();
                if (action != null && "android.intent.action.SCREEN_OFF".equals(action)) {
                    RouteFootRunPresenter.y(routeFootRunPresenter);
                }
            }
        }
    }

    static class a extends ect<RouteFootRunPresenter> {
        a(RouteFootRunPresenter routeFootRunPresenter) {
            super(routeFootRunPresenter);
        }

        public final void run() {
            RouteFootRunPresenter routeFootRunPresenter = (RouteFootRunPresenter) a();
            if (routeFootRunPresenter != null) {
                routeFootRunPresenter.D = false;
                routeFootRunPresenter.r();
            }
        }
    }

    static class b extends ect<RouteFootRunPresenter> {
        b(RouteFootRunPresenter routeFootRunPresenter) {
            super(routeFootRunPresenter);
        }

        public final void run() {
            RouteFootRunPresenter.h((RouteFootRunPresenter) a());
        }
    }

    static class c extends Handler {
        private WeakReference<RouteFootRunPresenter> a;

        c(RouteFootRunPresenter routeFootRunPresenter) {
            this.a = new WeakReference<>(routeFootRunPresenter);
        }

        public final void handleMessage(Message message) {
            super.handleMessage(message);
            RouteFootRunPresenter routeFootRunPresenter = (RouteFootRunPresenter) this.a.get();
            if (routeFootRunPresenter != null) {
                int i = message.what;
                if (((RouteFootRunMapPage) routeFootRunPresenter.mPage).isAlive() || i == 5) {
                    if (i != 7) {
                        switch (i) {
                            case 0:
                                if (message.obj != null) {
                                    HealthPoint healthPoint = (HealthPoint) message.obj;
                                    if (routeFootRunPresenter.y == null) {
                                        routeFootRunPresenter.y = new HealthPoint();
                                    }
                                    routeFootRunPresenter.y = healthPoint.clone();
                                    if (healthPoint.status == HealthPointStatus.HPS_VALID || (routeFootRunPresenter.w == HealthPointStatus.HPS_AUTO_PAUSE && healthPoint.status == HealthPointStatus.HPS_VALID)) {
                                        routeFootRunPresenter.i.add(healthPoint);
                                        routeFootRunPresenter.s();
                                        if (routeFootRunPresenter.i.size() == 1) {
                                            routeFootRunPresenter.a(new GeoPoint(healthPoint.longitude, healthPoint.latitude));
                                        }
                                    }
                                    routeFootRunPresenter.w = healthPoint.status;
                                    routeFootRunPresenter.r();
                                    return;
                                }
                                return;
                            case 1:
                                if (message.obj != null) {
                                    HealthPoint healthPoint2 = (HealthPoint) message.obj;
                                    GeoPoint geoPoint = new GeoPoint(healthPoint2.longitude, healthPoint2.latitude);
                                    routeFootRunPresenter.j.add(new efi(geoPoint, message.arg1));
                                    routeFootRunPresenter.k.a(geoPoint, message.arg1);
                                    if (routeFootRunPresenter.I && routeFootRunPresenter.H != null) {
                                        routeFootRunPresenter.H.a("9", new RouteFootRunPresenter$InnerHandler$1(this, routeFootRunPresenter));
                                    }
                                    return;
                                }
                                break;
                            case 2:
                                efh efh = (efh) message.obj;
                                if (efh != null) {
                                    RouteFootRunPresenter.O = efh.a;
                                    RouteFootRunMapPage routeFootRunMapPage = (RouteFootRunMapPage) routeFootRunPresenter.mPage;
                                    routeFootRunMapPage.g.setIleage(efv.a(efh.a)[0]);
                                    if (efh.a >= 1000) {
                                        routeFootRunMapPage.g.setIleageUnit(routeFootRunMapPage.getString(R.string.running_map_distance_km));
                                    }
                                    routeFootRunMapPage.g.setSpeed(efv.a(efh.b));
                                    return;
                                }
                                break;
                            case 3:
                                int i2 = message.arg1;
                                RouteFootRunMapPage routeFootRunMapPage2 = (RouteFootRunMapPage) routeFootRunPresenter.mPage;
                                if (i2 == TraceStatus.TS_AUTO_PAUSE.getValue()) {
                                    routeFootRunMapPage2.c.setBackgroundResource(R.drawable.route_run_btn_start_selector);
                                    routeFootRunMapPage2.c.setTag(routeFootRunMapPage2.i);
                                    routeFootRunMapPage2.g();
                                    routeFootRunMapPage2.a(AMapAppGlobal.getApplication().getString(R.string.auto_pause_record));
                                    return;
                                }
                                if (i2 == TraceStatus.TS_AUTO_RESUME.getValue()) {
                                    routeFootRunMapPage2.c.setBackgroundResource(R.drawable.route_run_btn_pause_selector);
                                    routeFootRunMapPage2.f();
                                    routeFootRunMapPage2.c.setTag(routeFootRunMapPage2.j);
                                    routeFootRunMapPage2.a(routeFootRunMapPage2.getString(R.string.recovery_record));
                                    aho.a(new Runnable() {
                                        public final void run() {
                                            RouteFootRunMapPage.this.b();
                                        }
                                    }, 4000);
                                }
                                return;
                            case 4:
                                TraceStatistics traceStatistics = (TraceStatistics) message.obj;
                                if (routeFootRunPresenter.x == null) {
                                    routeFootRunPresenter.x = new TraceStatistics();
                                }
                                routeFootRunPresenter.x.gps_array = traceStatistics.gps_array;
                                routeFootRunPresenter.x.average_speed = traceStatistics.average_speed;
                                routeFootRunPresenter.x.calorie = traceStatistics.calorie;
                                routeFootRunPresenter.x.steps = traceStatistics.steps;
                                routeFootRunPresenter.x.trace_length = traceStatistics.trace_length;
                                routeFootRunPresenter.x.trace_time = traceStatistics.trace_time;
                                routeFootRunPresenter.i.clear();
                                routeFootRunPresenter.k.b.clear();
                                RouteFootRunPresenter.o(routeFootRunPresenter);
                                return;
                            case 5:
                                routeFootRunPresenter.b((String) message.obj);
                                return;
                        }
                    } else {
                        ((RouteFootRunMapPage) routeFootRunPresenter.mPage).g.setUseTime((String) message.obj);
                    }
                }
            }
        }
    }

    static class d extends ect<RouteFootRunPresenter> {
        d(RouteFootRunPresenter routeFootRunPresenter) {
            super(routeFootRunPresenter);
        }

        public final void run() {
            RouteFootRunPresenter routeFootRunPresenter = (RouteFootRunPresenter) a();
            if (routeFootRunPresenter != null && ((RouteFootRunMapPage) routeFootRunPresenter.mPage).isStarted()) {
                routeFootRunPresenter.E = false;
            }
        }
    }

    public RouteFootRunPresenter(RouteFootRunMapPage routeFootRunMapPage) {
        super(routeFootRunMapPage);
    }

    public void onMapSurfaceChanged(int i, int i2) {
        super.onMapSurfaceChanged(i, i2);
        StringBuilder sb = new StringBuilder("LifeCycle: onMapSurfaceChanged() page resumed: ");
        sb.append(((RouteFootRunMapPage) this.mPage).isStarted());
        sb.append(", ");
        sb.append(this.l.x);
        sb.append(", ");
        sb.append(this.l.y);
        eft.a(sb.toString());
        if (((RouteFootRunMapPage) this.mPage).isStarted() && !((RouteFootRunMapPage) this.mPage).h()) {
            ((RouteFootRunMapPage) this.mPage).getMapView().b(this.l.x, this.l.y);
        }
    }

    public void onPageCreated() {
        eft.a("runpage", "RouteFootRunPresenter super onPageCreated");
        super.onPageCreated();
        eft.a("runpage", "RouteFootRunPresenter onPageCreated");
        this.s = new String[]{((RouteFootRunMapPage) this.mPage).getString(R.string.running_map_voice_gps_weak_default1), ((RouteFootRunMapPage) this.mPage).getString(R.string.running_map_voice_gps_weak_default2)};
        this.t = new String[]{((RouteFootRunMapPage) this.mPage).getString(R.string.running_map_voice_gps_weak_gdg1), ((RouteFootRunMapPage) this.mPage).getString(R.string.running_map_voice_gps_weak_gdg2)};
        this.u = new String[]{((RouteFootRunMapPage) this.mPage).getString(R.string.running_map_voice_gps_weak_hxm), ((RouteFootRunMapPage) this.mPage).getString(R.string.running_map_voice_gps_weak_hxm2)};
        GeoPoint m = m();
        if (m != null) {
            efk efk = this.k;
            efk.a.firstSetCarPosition(m.x, m.y, l());
            ((RouteFootRunMapPage) this.mPage).getMapView().a(m.x, m.y);
        }
        this.H = (ctl) defpackage.esb.a.a.a(ctl.class);
        AMapPageUtil.setPageStateListener((bid) this.mPage, new IPageStateListener() {
            public final void onAppear() {
            }

            public final void onCover() {
                if (RouteFootRunPresenter.this.H != null) {
                    ctl a2 = RouteFootRunPresenter.this.H;
                    RouteFootRunPresenter.this.mPage;
                    a2.a("7");
                }
            }
        });
        if (o()) {
            this.B = new b(this);
        }
        drm.a((defpackage.drn.a) this.S);
    }

    public void onStart() {
        eft.a("runpage", "RouteFootRunPresenter super onStart");
        super.onStart();
        eft.a("runpage", "RouteFootRunPresenter onStart");
        RouteFootRunMapPage routeFootRunMapPage = (RouteFootRunMapPage) this.mPage;
        if (!(routeFootRunMapPage.f == null || !routeFootRunMapPage.f.isShown() || routeFootRunMapPage.d == null)) {
            routeFootRunMapPage.d.a();
        }
        r();
        if (!(this.j == null || this.j.size() == 0)) {
            Iterator it = this.j.iterator();
            while (it.hasNext()) {
                efi efi = (efi) it.next();
                this.k.a(efi.a, efi.b);
            }
        }
        s();
        t();
        if (this.G == null) {
            this.G = new RunNaviSystemKeyCode(this);
        }
        if (!this.F) {
            this.F = true;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
            ((RouteFootRunMapPage) this.mPage).getActivity().registerReceiver(this.G, intentFilter);
        }
        a(this.C);
        RouteFootRunMapPage routeFootRunMapPage2 = (RouteFootRunMapPage) this.mPage;
        if (!TextUtils.equals((CharSequence) routeFootRunMapPage2.c.getTag(), routeFootRunMapPage2.k) && System.currentTimeMillis() < routeFootRunMapPage2.h) {
            routeFootRunMapPage2.finish();
        }
    }

    public void onResume() {
        super.onResume();
        this.d.a();
        RouteFootRunMapPage routeFootRunMapPage = (RouteFootRunMapPage) this.mPage;
        if (!routeFootRunMapPage.l) {
            routeFootRunMapPage.i();
            routeFootRunMapPage.l = true;
        }
        if (euk.a()) {
            euk.a(AMapAppGlobal.getTopActivity(), routeFootRunMapPage.getResources().getColor(R.color.c_27));
        }
        bqx bqx = (bqx) ank.a(bqx.class);
        if (bqx != null) {
            bqx.a(false, false, false, ((RouteFootRunMapPage) this.mPage).getMapManager(), ((RouteFootRunMapPage) this.mPage).getContext());
        }
        if (this.n) {
            GeoPoint[] geoPointArr = this.m;
            if (geoPointArr != null && geoPointArr.length > 0) {
                efk efk = this.k;
                PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPointArr[0]);
                pointOverlayItem.mDefaultMarker = efk.d.createMarker(R.drawable.bubble_start, 5);
                efk.d.addItem(pointOverlayItem);
                efk efk2 = this.k;
                LineOverlayItem lineOverlayItem = new LineOverlayItem(1, geoPointArr, agn.a(efk2.e, 4.0f));
                lineOverlayItem.setFillLineColor(-5977601);
                lineOverlayItem.setFillLineId(R.drawable.map_frontlr);
                efk2.c.addItem(lineOverlayItem);
            }
            if (this.J) {
                this.k.a();
                this.J = false;
            }
        }
    }

    public final void b() {
        this.o = ((RouteFootRunMapPage) this.mPage).getMapView().v();
        this.k.a();
        p();
    }

    private void p() {
        if (this.B != null && ((RouteFootRunMapPage) this.mPage).h()) {
            this.p.removeCallbacks(this.B);
            this.p.postDelayed(this.B, 10000);
        }
    }

    public final void c() {
        bty mapView = ((RouteFootRunMapPage) this.mPage).getMapView();
        if (mapView != null) {
            mapView.b(this.l.x, this.l.y);
            float f = 0.0f;
            mapView.d(this.o > 0.0f ? this.o : 18.0f);
            boolean a2 = edr.a((String) "runnavimodewithangle", true);
            mapView.g(a2 ? 0.0f : 39.0f);
            if (!a2) {
                f = (float) l();
            }
            mapView.e(f);
            GeoPoint m = m();
            mapView.a(m.x, m.y);
        }
        r();
    }

    public final void d() {
        if (o()) {
            AMapLog.d("qiujunhui", "doRecEnterAnimation");
            GeoPoint m = m();
            boolean a2 = edr.a((String) "runnavimodewithangle", true);
            ((RouteFootRunMapPage) this.mPage).getMapView().b(this.l.x, this.l.y);
            ((RouteFootRunMapPage) this.mPage).getMapView().a(500, 18.0f, a2 ? 0 : l(), a2 ? 0 : 39, m.x, m.y, false);
        }
    }

    public void onStop() {
        eft.a("runpage", "RouteFootRunPresenter super onStop");
        super.onStop();
        eft.a("runpage", "RouteFootRunPresenter onStop");
        ((RouteFootRunMapPage) this.mPage).c();
        this.L = false;
    }

    public void onDestroy() {
        eft.a("runpage", "RouteFootRunPresenter super onDestroy");
        super.onDestroy();
        eft.a("runpage", "RouteFootRunPresenter onDestroy");
        u();
        if (this.p != null) {
            this.p.removeMessages(6);
            this.p.removeMessages(4);
            this.p.removeMessages(2);
            this.p.removeMessages(1);
            this.p.removeMessages(0);
            this.p.removeMessages(7);
            this.p.removeMessages(3);
            this.p.removeCallbacks(this.z);
            this.p.removeCallbacks(this.R);
            this.p.removeCallbacks(this.B);
        }
        this.d.c = null;
        ((RouteFootRunMapPage) this.mPage).g();
        if (this.S != null) {
            drm.b((defpackage.drn.a) this.S);
        }
    }

    public boolean onMapTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                this.D = true;
                e();
                break;
            case 1:
                p();
                if (this.p != null) {
                    this.p.removeCallbacks(this.z);
                    this.p.postDelayed(this.z, 10000);
                    break;
                }
                break;
        }
        return super.onMapTouchEvent(motionEvent);
    }

    public final void e() {
        if (o() && this.B != null) {
            this.p.removeCallbacks(this.B);
        }
    }

    public final void f() {
        this.M = (int) (System.currentTimeMillis() / 1000);
        O = 0;
        efd efd = this.d;
        if (efd.b != null) {
            eft.a("CALL_ENGINE", "StartTrace");
            efd.b.StartTrace();
            efd.g = true;
            efd.e = false;
            defpackage.eao.a.a(efd.b);
        }
        this.P = true;
        this.p.postDelayed(this.R, 8000);
    }

    public final void g() {
        efd efd = this.d;
        if (efd.b != null) {
            eft.a("CALL_ENGINE", "PauseTrace");
            efd.b.PauseTrace();
            efd.e = true;
        }
    }

    public final void h() {
        efd efd = this.d;
        if (efd.b != null) {
            eft.a("CALL_ENGINE", "ResumeTrace");
            efd.b.ResumeTrace();
            efd.e = false;
        }
    }

    public final void i() {
        this.N = (int) (System.currentTimeMillis() / 1000);
        UpLoadOperationDataUtil.a(this.n ? OperationType.TYPE_HEALTH_RUN_RECOMMEND : OperationType.TYPE_HEALTH_RUN, this.M, this.N, O);
        efd efd = this.d;
        if (efd.b != null) {
            eft.a("CALL_ENGINE", "StopTrace");
            efd.b.StopTrace();
            efd.e = true;
        }
        this.P = false;
    }

    public final Handler a() {
        return this.p;
    }

    public ON_BACK_TYPE onBackPressed() {
        RouteFootRunMapPage routeFootRunMapPage = (RouteFootRunMapPage) this.mPage;
        if (routeFootRunMapPage.d != null && routeFootRunMapPage.f != null && routeFootRunMapPage.f.isShown()) {
            routeFootRunMapPage.f.setVisibility(8);
            if (routeFootRunMapPage.e != null) {
                routeFootRunMapPage.e.setVisibility(0);
            }
            return ON_BACK_TYPE.TYPE_IGNORE;
        } else if (TextUtils.equals((CharSequence) routeFootRunMapPage.c.getTag(), routeFootRunMapPage.k)) {
            return ON_BACK_TYPE.TYPE_NORMAL;
        } else {
            if (routeFootRunMapPage.isViewLayerShowing(routeFootRunMapPage.b)) {
                routeFootRunMapPage.f();
                routeFootRunMapPage.c.setTag(routeFootRunMapPage.j);
                routeFootRunMapPage.c.setBackgroundResource(R.drawable.route_run_btn_pause_selector);
                ((RouteFootRunPresenter) routeFootRunMapPage.mPresenter).h();
                routeFootRunMapPage.b();
                routeFootRunMapPage.dismissViewLayer(routeFootRunMapPage.b);
            } else if (!routeFootRunMapPage.g.isLockPulled()) {
                routeFootRunMapPage.a();
            }
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
    }

    public final void j() {
        if (o()) {
            ((RouteFootRunMapPage) this.mPage).getMapView().b(this.l.x, this.l.y);
        }
    }

    private boolean q() {
        return (this.y == null || this.y.longitude == 0.0d || this.y.latitude == 0.0d) ? false : true;
    }

    public final boolean k() {
        efd efd = this.d;
        boolean z2 = efd.b != null && efd.b.IsTraceTooShort();
        eft.a("CALL_ENGINE", "isTracTooShort return = ".concat(String.valueOf(z2)));
        return z2;
    }

    /* access modifiers changed from: private */
    public void b(String str) {
        if (!this.g && this.q) {
            PlaySoundUtils.getInstance().playSound(str);
        }
    }

    public final void b(int i) {
        if (this.K && i > 1 && !this.E && this.d.g) {
            this.E = true;
            this.d.b.PlayGPSWeakVoice();
            this.p.postDelayed(this.A, 120000);
        }
    }

    /* access modifiers changed from: private */
    public void r() {
        GeoPoint m = m();
        if (m == null) {
            AMapLog.w(r, "map center null!!");
            return;
        }
        int l = l();
        if (this.D || ((RouteFootRunMapPage) this.mPage).h()) {
            this.k.a(m.x, m.y, l);
        } else {
            a(m, l);
        }
    }

    public final int l() {
        if (this.y != null) {
            int i = this.y.angle;
            StringBuilder sb = new StringBuilder("runlocation :");
            sb.append(String.valueOf(i));
            eft.a("run dir : ", sb.toString());
            return i;
        }
        Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
        int bearing = latestLocation != null ? (int) latestLocation.getBearing() : 0;
        StringBuilder sb2 = new StringBuilder("runlocation is empty :");
        sb2.append(String.valueOf(bearing));
        eft.a("run dir : ", sb2.toString());
        return bearing;
    }

    public final GeoPoint m() {
        return q() ? new GeoPoint(this.y.longitude, this.y.latitude) : LocationInstrument.getInstance().getLatestPosition();
    }

    private void a(GeoPoint geoPoint, int i) {
        j();
        if (((RouteFootRunMapPage) this.mPage).a.b.b) {
            this.k.b(geoPoint, i);
        } else {
            this.k.a(geoPoint, i, i);
        }
    }

    /* access modifiers changed from: private */
    public void s() {
        if (!this.d.e) {
            int size = this.i.size();
            if (size > 0) {
                AMapLog.e("qiujunhui", "mHelRuns : ".concat(String.valueOf(size)));
                PathLineParser pathLineParser = new PathLineParser(0);
                for (int i = 0; i < size; i++) {
                    HealthPoint healthPoint = (HealthPoint) this.i.get(i);
                    pathLineParser.addPoint(new GeoPoint(healthPoint.longitude, healthPoint.latitude), false, (int) healthPoint.speed);
                }
                this.k.a(pathLineParser);
            }
        }
    }

    private void t() {
        int size = this.h.size();
        if (size > 0) {
            PathLineParser pathLineParser = new PathLineParser(0);
            for (int i = 0; i < size; i++) {
                HealthPoint healthPoint = (HealthPoint) this.h.get(i);
                pathLineParser.addPoint(new GeoPoint(healthPoint.longitude, healthPoint.latitude), healthPoint.status == HealthPointStatus.HPS_PAUSE, (int) healthPoint.speed);
            }
            this.k.b(pathLineParser);
        }
    }

    public final void a(GeoPoint geoPoint) {
        if (!(this.n && this.m != null && this.m.length > 0) && this.k != null && !this.L && geoPoint != null) {
            this.k.a(geoPoint);
            this.C = geoPoint;
            this.L = true;
        }
    }

    private void u() {
        if (this.F) {
            this.F = false;
            try {
                ((RouteFootRunMapPage) this.mPage).getActivity().unregisterReceiver(this.G);
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }

    public final void a(int i, int i2) {
        super.a(i, i2);
    }

    public final TraceStatistics n() {
        HealthPoint[] healthPointArr;
        efd efd = this.d;
        if (efd.b == null) {
            return null;
        }
        TraceStatistics GetTraceStatistics = efd.b.GetTraceStatistics();
        eft.a("CALL_ENGINE", "HelRunGetAllMembers".concat(String.valueOf(GetTraceStatistics)));
        if (GetTraceStatistics == null) {
            return GetTraceStatistics;
        }
        StringBuilder sb = new StringBuilder("HelRunGetAllMembersnTraceLength=");
        sb.append(GetTraceStatistics.trace_length);
        sb.append("nTraceTime=");
        sb.append(GetTraceStatistics.trace_time);
        sb.append("nCalorie=");
        sb.append(GetTraceStatistics.calorie);
        sb.append("nStep=");
        sb.append(GetTraceStatistics.steps);
        sb.append("nAverageSpeed=");
        sb.append(GetTraceStatistics.average_speed);
        eft.a("CALL_ENGINE", sb.toString());
        if (GetTraceStatistics.gps_array == null) {
            return GetTraceStatistics;
        }
        StringBuilder sb2 = new StringBuilder();
        for (HealthPoint healthPoint : GetTraceStatistics.gps_array) {
            sb2.append(healthPoint.longitude);
            sb2.append(", ");
            sb2.append(healthPoint.latitude);
            sb2.append(", ");
            sb2.append(healthPoint.speed);
            sb2.append("\n");
        }
        StringBuilder sb3 = new StringBuilder("getAllMembers");
        sb3.append(sb2.toString());
        eft.a("ENGINE_OUT", sb3.toString());
        return GetTraceStatistics;
    }

    public final boolean o() {
        return this.m != null;
    }

    public final void a(int i) {
        if (!this.a) {
            this.f = i;
            this.e = this.f == 0;
            ((RouteFootRunMapPage) this.mPage).a(ecv.a(this.f));
            b(ecv.a(this.f));
        }
    }

    static /* synthetic */ void h(RouteFootRunPresenter routeFootRunPresenter) {
        RouteFootRunMapPage routeFootRunMapPage = (RouteFootRunMapPage) routeFootRunPresenter.mPage;
        if (routeFootRunMapPage.a != null) {
            routeFootRunMapPage.a.c();
        }
        routeFootRunPresenter.c();
    }

    static /* synthetic */ void o(RouteFootRunPresenter routeFootRunPresenter) {
        if (routeFootRunPresenter.x != null) {
            HealthPoint[] healthPointArr = routeFootRunPresenter.x.gps_array;
            if (healthPointArr != null) {
                ArrayList arrayList = new ArrayList();
                Collections.addAll(arrayList, healthPointArr);
                routeFootRunPresenter.h = arrayList;
                routeFootRunPresenter.t();
                int size = routeFootRunPresenter.h.size();
                if (size > 0) {
                    HealthPoint healthPoint = (HealthPoint) routeFootRunPresenter.h.get(size - 1);
                    if (healthPoint.status == HealthPointStatus.HPS_VALID) {
                        routeFootRunPresenter.i.add(healthPoint);
                    }
                }
            }
        }
    }

    static /* synthetic */ void y(RouteFootRunPresenter routeFootRunPresenter) {
        if (routeFootRunPresenter.Q) {
            String string = ((RouteFootRunMapPage) routeFootRunPresenter.mPage).getString(R.string.route_run_continue_record_text);
            routeFootRunPresenter.u();
            if (Tts.getInstance().JniIsPlaying() != 1) {
                routeFootRunPresenter.b(string);
            }
            routeFootRunPresenter.Q = false;
        }
        efd.a(((RouteFootRunMapPage) routeFootRunPresenter.mPage).getString(R.string.notification_title_keep_record), ((RouteFootRunMapPage) routeFootRunPresenter.mPage).getString(R.string.notification_content_running_record));
    }
}
