package com.autonavi.minimap.route.ride.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
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
import com.autonavi.minimap.route.foot.util.AmapBroadcastReceiver;
import com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil;
import com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil.OperationType;
import com.autonavi.minimap.route.ride.page.RouteFootRideMapPage;
import com.autonavi.sdk.location.LocationInstrument;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.iflytek.tts.TtsService.Tts;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class RouteFootRidePresenter extends eel {
    /* access modifiers changed from: private */
    public static int H = 0;
    private static final String o = "RouteFootRidePresenter";
    private static final String[] p = {"信号不太好，换个方位试试。"};
    private static final String[] q = {"GPS信号弱，到空旷地带重新起航吧。", "信号不好，请到空旷地方让高德地图恢复一下。"};
    /* access modifiers changed from: private */
    public boolean A = false;
    private boolean B = false;
    private RunNaviSystemKeyCode C;
    /* access modifiers changed from: private */
    public ctl D;
    /* access modifiers changed from: private */
    public boolean E = true;
    private int F;
    private int G;
    private boolean I;
    private boolean J;
    private boolean K = true;
    private Runnable L = new Runnable() {
        public final void run() {
            if (RouteFootRidePresenter.this.mPage != null && ((RouteFootRideMapPage) RouteFootRidePresenter.this.mPage).isAlive()) {
                ((RouteFootRideMapPage) RouteFootRidePresenter.this.mPage).a(ecv.a(ecv.a()));
            }
        }
    };
    private defpackage.drn.c M = new defpackage.drn.c() {
        public final void a() {
        }

        public final void c() {
        }

        public final void b() {
            RouteFootRidePresenter.x(RouteFootRidePresenter.this);
        }
    };
    public Handler m = new b(this);
    public boolean n = true;
    private String[] r;
    private float s = -1.0f;
    /* access modifiers changed from: private */
    public HealthPointStatus t;
    /* access modifiers changed from: private */
    public TraceStatistics u;
    /* access modifiers changed from: private */
    public HealthPoint v;
    private Runnable w = new a(this);
    private Runnable x = new c(this);
    private GeoPoint y;
    /* access modifiers changed from: private */
    public boolean z = false;

    static class RunNaviSystemKeyCode extends AmapBroadcastReceiver<RouteFootRidePresenter> {
        public RunNaviSystemKeyCode(RouteFootRidePresenter routeFootRidePresenter) {
            super(routeFootRidePresenter);
        }

        public void onReceive(Context context, Intent intent) {
            RouteFootRidePresenter routeFootRidePresenter = (RouteFootRidePresenter) a();
            if (routeFootRidePresenter != null) {
                String action = intent.getAction();
                if (action != null && "android.intent.action.SCREEN_OFF".equals(action)) {
                    RouteFootRidePresenter.x(routeFootRidePresenter);
                }
            }
        }
    }

    static class a extends ect<RouteFootRidePresenter> {
        a(RouteFootRidePresenter routeFootRidePresenter) {
            super(routeFootRidePresenter);
        }

        public final void run() {
            RouteFootRidePresenter routeFootRidePresenter = (RouteFootRidePresenter) a();
            if (routeFootRidePresenter != null) {
                routeFootRidePresenter.z = false;
            }
        }
    }

    static class b extends Handler {
        private WeakReference<RouteFootRidePresenter> a;

        b(RouteFootRidePresenter routeFootRidePresenter) {
            this.a = new WeakReference<>(routeFootRidePresenter);
        }

        public final void handleMessage(Message message) {
            super.handleMessage(message);
            RouteFootRidePresenter routeFootRidePresenter = (RouteFootRidePresenter) this.a.get();
            if (routeFootRidePresenter != null) {
                int i = message.what;
                if (((RouteFootRideMapPage) routeFootRidePresenter.mPage).isAlive() || i == 5) {
                    if (i != 7) {
                        switch (i) {
                            case 0:
                                if (message.obj != null) {
                                    HealthPoint healthPoint = (HealthPoint) message.obj;
                                    if (routeFootRidePresenter.v == null) {
                                        routeFootRidePresenter.v = new HealthPoint();
                                    }
                                    routeFootRidePresenter.v = healthPoint.clone();
                                    if (healthPoint.status == HealthPointStatus.HPS_VALID || (routeFootRidePresenter.t == HealthPointStatus.HPS_AUTO_PAUSE && healthPoint.status == HealthPointStatus.HPS_VALID)) {
                                        routeFootRidePresenter.i.add(healthPoint);
                                        routeFootRidePresenter.m();
                                        if (routeFootRidePresenter.i.size() == 1) {
                                            routeFootRidePresenter.a(new GeoPoint(healthPoint.longitude, healthPoint.latitude));
                                        }
                                    }
                                    routeFootRidePresenter.t = healthPoint.status;
                                    routeFootRidePresenter.l();
                                    return;
                                }
                                return;
                            case 1:
                                if (message.obj != null) {
                                    HealthPoint healthPoint2 = (HealthPoint) message.obj;
                                    GeoPoint geoPoint = new GeoPoint(healthPoint2.longitude, healthPoint2.latitude);
                                    routeFootRidePresenter.j.add(new eei(geoPoint, message.arg1));
                                    routeFootRidePresenter.k.a(geoPoint, message.arg1);
                                    if (routeFootRidePresenter.E && routeFootRidePresenter.D != null) {
                                        routeFootRidePresenter.D.a("9", new RouteFootRidePresenter$InnerHandler$1(this, routeFootRidePresenter));
                                    }
                                    return;
                                }
                                break;
                            case 2:
                                eeh eeh = (eeh) message.obj;
                                if (eeh != null) {
                                    RouteFootRidePresenter.H = eeh.a;
                                    RouteFootRideMapPage routeFootRideMapPage = (RouteFootRideMapPage) routeFootRidePresenter.mPage;
                                    routeFootRideMapPage.g.setIleage(efv.a(eeh.a)[0]);
                                    if (eeh.a >= 1000) {
                                        routeFootRideMapPage.g.setIleageUnit(routeFootRideMapPage.getString(R.string.ride_pull_section_custom_distance_km));
                                    }
                                    routeFootRideMapPage.a(efv.b(eeh.b));
                                    return;
                                }
                                break;
                            case 3:
                                int i2 = message.arg1;
                                RouteFootRideMapPage routeFootRideMapPage2 = (RouteFootRideMapPage) routeFootRidePresenter.mPage;
                                if (i2 == TraceStatus.TS_AUTO_PAUSE.getValue()) {
                                    routeFootRideMapPage2.c.setBackgroundResource(R.drawable.route_run_btn_start_selector);
                                    routeFootRideMapPage2.c.setTag(routeFootRideMapPage2.j);
                                    routeFootRideMapPage2.h();
                                    routeFootRideMapPage2.b(AMapPageUtil.getAppContext().getString(R.string.auto_pause_record));
                                    routeFootRideMapPage2.m = true;
                                    routeFootRideMapPage2.a(efv.b(0.0d));
                                    return;
                                }
                                if (i2 == TraceStatus.TS_AUTO_RESUME.getValue()) {
                                    routeFootRideMapPage2.c.setBackgroundResource(R.drawable.route_run_btn_pause_selector);
                                    routeFootRideMapPage2.g();
                                    routeFootRideMapPage2.c.setTag(routeFootRideMapPage2.k);
                                    routeFootRideMapPage2.c();
                                    routeFootRideMapPage2.b(AMapPageUtil.getAppContext().getString(R.string.recovery_record));
                                    aho.a(new Runnable() {
                                        public final void run() {
                                            RouteFootRideMapPage.this.c();
                                        }
                                    }, 4000);
                                    routeFootRideMapPage2.m = false;
                                }
                                return;
                            case 4:
                                TraceStatistics traceStatistics = (TraceStatistics) message.obj;
                                if (routeFootRidePresenter.u == null) {
                                    routeFootRidePresenter.u = new TraceStatistics();
                                }
                                routeFootRidePresenter.u.gps_array = traceStatistics.gps_array;
                                routeFootRidePresenter.u.average_speed = traceStatistics.average_speed;
                                routeFootRidePresenter.u.calorie = traceStatistics.calorie;
                                routeFootRidePresenter.u.steps = traceStatistics.steps;
                                routeFootRidePresenter.u.trace_length = traceStatistics.trace_length;
                                routeFootRidePresenter.u.trace_time = traceStatistics.trace_time;
                                routeFootRidePresenter.i.clear();
                                routeFootRidePresenter.k.b.clear();
                                RouteFootRidePresenter.n(routeFootRidePresenter);
                                return;
                            case 5:
                                routeFootRidePresenter.b((String) message.obj);
                                return;
                        }
                    } else {
                        RouteFootRideMapPage routeFootRideMapPage3 = (RouteFootRideMapPage) routeFootRidePresenter.mPage;
                        routeFootRideMapPage3.g.setUseTime((String) message.obj);
                        if (routeFootRideMapPage3.i > 43200) {
                            ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.ride_long_time_tip));
                            routeFootRideMapPage3.b();
                        }
                    }
                }
            }
        }
    }

    static class c extends ect<RouteFootRidePresenter> {
        c(RouteFootRidePresenter routeFootRidePresenter) {
            super(routeFootRidePresenter);
        }

        public final void run() {
            RouteFootRidePresenter routeFootRidePresenter = (RouteFootRidePresenter) a();
            if (routeFootRidePresenter != null && ((RouteFootRideMapPage) routeFootRidePresenter.mPage).isStarted()) {
                routeFootRidePresenter.A = false;
            }
        }
    }

    public RouteFootRidePresenter(RouteFootRideMapPage routeFootRideMapPage) {
        super(routeFootRideMapPage);
    }

    public void onResume() {
        super.onResume();
        this.d.a();
        RouteFootRideMapPage routeFootRideMapPage = (RouteFootRideMapPage) this.mPage;
        if (!routeFootRideMapPage.n) {
            routeFootRideMapPage.i();
            routeFootRideMapPage.n = true;
        }
        if (euk.a()) {
            euk.a(AMapAppGlobal.getTopActivity(), routeFootRideMapPage.getResources().getColor(R.color.c_27));
        }
    }

    public void onMapSurfaceChanged(int i, int i2) {
        super.onMapSurfaceChanged(i, i2);
        AMapLog.i(o, "onMapSurfaceChanged");
        StringBuilder sb = new StringBuilder("LifeCycle: onMapSurfaceChanged() page resumed: ");
        sb.append(((RouteFootRideMapPage) this.mPage).isStarted());
        sb.append(", ");
        sb.append(this.l.x);
        sb.append(", ");
        sb.append(this.l.y);
        eer.a(sb.toString());
        if (((RouteFootRideMapPage) this.mPage).isStarted()) {
            ((RouteFootRideMapPage) this.mPage).getMapView().b(this.l.x, this.l.y);
        }
    }

    public void onPageCreated() {
        super.onPageCreated();
        GeoPoint i = i();
        if (i != null) {
            eek eek = this.k;
            eek.a.firstSetCarPosition(i.x, i.y, h());
            ((RouteFootRideMapPage) this.mPage).getMapView().a(i.x, i.y);
        }
        this.D = (ctl) defpackage.esb.a.a.a(ctl.class);
        AMapPageUtil.setPageStateListener((bid) this.mPage, new IPageStateListener() {
            public final void onAppear() {
            }

            public final void onCover() {
                if (RouteFootRidePresenter.this.D != null) {
                    ctl a2 = RouteFootRidePresenter.this.D;
                    RouteFootRidePresenter.this.mPage;
                    a2.a("9");
                }
            }
        });
        drm.a((defpackage.drn.a) this.M);
        this.r = new String[]{((RouteFootRideMapPage) this.mPage).getString(R.string.bike_navi_gps_week_voice3), ((RouteFootRideMapPage) this.mPage).getString(R.string.bike_navi_gps_week_voice4), ((RouteFootRideMapPage) this.mPage).getString(R.string.bike_navi_gps_week_voice5)};
    }

    public void onStart() {
        super.onStart();
        AMapLog.i(o, "onResume()");
        eer.a("LifeCycle: onResume()");
        RouteFootRideMapPage routeFootRideMapPage = (RouteFootRideMapPage) this.mPage;
        if (!(routeFootRideMapPage.f == null || routeFootRideMapPage.f.getVisibility() == 8 || routeFootRideMapPage.d == null)) {
            routeFootRideMapPage.d.a();
        }
        l();
        if (!(this.j == null || this.j.size() == 0)) {
            Iterator it = this.j.iterator();
            while (it.hasNext()) {
                eei eei = (eei) it.next();
                this.k.a(eei.a, eei.b);
            }
        }
        m();
        n();
        a(this.y);
        if (this.C == null) {
            this.C = new RunNaviSystemKeyCode(this);
        }
        if (!this.B) {
            this.B = true;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
            ((RouteFootRideMapPage) this.mPage).getActivity().registerReceiver(this.C, intentFilter);
        }
        RouteFootRideMapPage routeFootRideMapPage2 = (RouteFootRideMapPage) this.mPage;
        if (!TextUtils.equals((CharSequence) routeFootRideMapPage2.c.getTag(), routeFootRideMapPage2.l) && System.currentTimeMillis() < routeFootRideMapPage2.h) {
            routeFootRideMapPage2.finish();
        }
    }

    public void onStop() {
        super.onStop();
        eer.a("LifeCycle: onPause()");
        ((RouteFootRideMapPage) this.mPage).d();
        this.J = false;
    }

    public void onDestroy() {
        super.onDestroy();
        eer.a("LifeCycle: onDestroy()");
        o();
        if (this.m != null) {
            this.m.removeMessages(6);
            this.m.removeMessages(4);
            this.m.removeMessages(2);
            this.m.removeMessages(1);
            this.m.removeMessages(0);
            this.m.removeMessages(7);
            this.m.removeMessages(3);
            this.m.removeCallbacks(this.w);
            this.m.removeCallbacks(this.L);
        }
        this.d.c = null;
        ((RouteFootRideMapPage) this.mPage).h();
        if (this.M != null) {
            drm.b((defpackage.drn.a) this.M);
        }
    }

    public boolean onMapTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                this.z = true;
                break;
            case 1:
                if (this.m != null) {
                    this.m.removeCallbacks(this.w);
                    this.m.postDelayed(this.w, 10000);
                    break;
                }
                break;
        }
        return super.onMapTouchEvent(motionEvent);
    }

    public final void b() {
        this.F = (int) (System.currentTimeMillis() / 1000);
        H = 0;
        edx edx = this.d;
        if (edx.b != null) {
            eer.a("CALL_ENGINE", "StartTrace");
            edx.b.StartTrace();
            edx.g = true;
            edx.e = false;
            defpackage.eao.a.a(edx.b);
        }
        this.I = true;
        this.m.postDelayed(this.L, 8000);
    }

    public final void c() {
        edx edx = this.d;
        if (edx.b != null) {
            eer.a("CALL_ENGINE", "PauseTrace");
            edx.b.PauseTrace();
            edx.e = true;
        }
    }

    public final void d() {
        edx edx = this.d;
        if (edx.b != null) {
            eer.a("CALL_ENGINE", "ResumeTrace");
            edx.b.ResumeTrace();
            edx.e = false;
        }
    }

    public final void e() {
        this.G = (int) (System.currentTimeMillis() / 1000);
        UpLoadOperationDataUtil.a(OperationType.TYPE_HEALTH_RIDE, this.F, this.G, H);
        edx edx = this.d;
        if (edx.b != null) {
            eer.a("CALL_ENGINE", "StopTrace");
            edx.b.StopTrace();
            edx.e = true;
        }
        this.I = false;
    }

    public final Handler a() {
        return this.m;
    }

    public ON_BACK_TYPE onBackPressed() {
        RouteFootRideMapPage routeFootRideMapPage = (RouteFootRideMapPage) this.mPage;
        if (routeFootRideMapPage.d != null && routeFootRideMapPage.f != null && routeFootRideMapPage.f.getVisibility() != 8) {
            routeFootRideMapPage.f.setVisibility(8);
            if (routeFootRideMapPage.e != null) {
                routeFootRideMapPage.e.setVisibility(0);
            }
            return ON_BACK_TYPE.TYPE_IGNORE;
        } else if (TextUtils.equals((CharSequence) routeFootRideMapPage.c.getTag(), routeFootRideMapPage.l)) {
            return ON_BACK_TYPE.TYPE_NORMAL;
        } else {
            if (routeFootRideMapPage.isViewLayerShowing(routeFootRideMapPage.b)) {
                routeFootRideMapPage.g();
                routeFootRideMapPage.c.setTag(routeFootRideMapPage.k);
                routeFootRideMapPage.c.setBackgroundResource(R.drawable.route_run_btn_pause_selector);
                ((RouteFootRidePresenter) routeFootRideMapPage.mPresenter).d();
                routeFootRideMapPage.c();
                routeFootRideMapPage.dismissViewLayer(routeFootRideMapPage.b);
            } else if (!routeFootRideMapPage.g.isLockPulled()) {
                routeFootRideMapPage.a();
            }
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
    }

    private boolean k() {
        return (this.v == null || this.v.longitude == 0.0d || this.v.latitude == 0.0d) ? false : true;
    }

    public final boolean f() {
        edx edx = this.d;
        boolean z2 = edx.b != null && edx.b.IsTraceTooShort();
        eer.a("CALL_ENGINE", "IsTraceTooShort return = ".concat(String.valueOf(z2)));
        return z2;
    }

    /* access modifiers changed from: private */
    public void b(String str) {
        if (!this.g && this.n) {
            PlaySoundUtils.getInstance().playSound(str);
        }
    }

    public final void g() {
        if (!this.A && this.d.g) {
            this.A = true;
            this.d.b.PlayGPSWeakVoice();
            this.m.postDelayed(this.x, 120000);
        }
    }

    /* access modifiers changed from: private */
    public void l() {
        GeoPoint i = i();
        if (i == null) {
            AMapLog.w(o, "map center null!!");
            return;
        }
        eer.a("drawMRunPosition", i.getLongitude(), i.getLatitude());
        int h = h();
        if (this.z) {
            this.k.a(i.x, i.y, h);
        } else {
            a(i, h);
        }
    }

    public final int h() {
        int i;
        if (this.v != null) {
            i = this.v.angle;
        } else {
            Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
            i = latestLocation != null ? (int) latestLocation.getBearing() : 0;
        }
        AMapLog.e(o, "run dir:　".concat(String.valueOf(i)));
        return i;
    }

    public final GeoPoint i() {
        return k() ? new GeoPoint(this.v.longitude, this.v.latitude) : LocationInstrument.getInstance().getLatestPosition();
    }

    private void a(GeoPoint geoPoint, int i) {
        if (((RouteFootRideMapPage) this.mPage).a.b.b) {
            this.k.b(geoPoint, i);
        } else {
            this.k.a(geoPoint, i, i);
        }
    }

    /* access modifiers changed from: private */
    public void m() {
        if (!this.d.e) {
            int size = this.i.size();
            if (size > 0) {
                AMapLog.e(o, "mHelRuns : ".concat(String.valueOf(size)));
                PathLineParser pathLineParser = new PathLineParser(1);
                for (int i = 0; i < size; i++) {
                    HealthPoint healthPoint = (HealthPoint) this.i.get(i);
                    pathLineParser.addPoint(new GeoPoint(healthPoint.longitude, healthPoint.latitude), false, (int) healthPoint.speed);
                }
                this.k.a(pathLineParser);
            }
        }
    }

    private void n() {
        int size = this.h.size();
        if (size > 0) {
            PathLineParser pathLineParser = new PathLineParser(1);
            for (int i = 0; i < size; i++) {
                HealthPoint healthPoint = (HealthPoint) this.h.get(i);
                pathLineParser.addPoint(new GeoPoint(healthPoint.longitude, healthPoint.latitude), healthPoint.status == HealthPointStatus.HPS_PAUSE, (int) healthPoint.speed);
            }
            this.k.b(pathLineParser);
        }
    }

    private void o() {
        if (this.B) {
            this.B = false;
            try {
                ((RouteFootRideMapPage) this.mPage).getActivity().unregisterReceiver(this.C);
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }

    public final void a(int i, int i2) {
        super.a(i, i2);
    }

    public final TraceStatistics j() {
        HealthPoint[] healthPointArr;
        edx edx = this.d;
        if (edx.b == null) {
            return null;
        }
        TraceStatistics GetTraceStatistics = edx.b.GetTraceStatistics();
        eer.a("CALL_ENGINE", "HelRideGetAllMembers".concat(String.valueOf(GetTraceStatistics)));
        if (GetTraceStatistics == null) {
            return GetTraceStatistics;
        }
        StringBuilder sb = new StringBuilder("HelRideAllMembersnTraceLength=");
        sb.append(GetTraceStatistics.trace_length);
        sb.append("nTraceTime=");
        sb.append(GetTraceStatistics.trace_time);
        sb.append("nCalorie=");
        sb.append(GetTraceStatistics.calorie);
        sb.append("nStep=");
        sb.append(GetTraceStatistics.steps);
        sb.append("nAverageSpeed=");
        sb.append(GetTraceStatistics.average_speed);
        sb.append("nMaxSpeed=");
        sb.append(GetTraceStatistics.max_speed);
        eer.a("CALL_ENGINE", sb.toString());
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
        eer.a("ENGINE_OUT", sb3.toString());
        return GetTraceStatistics;
    }

    public final void a(GeoPoint geoPoint) {
        if (this.k != null && !this.J && geoPoint != null) {
            this.k.a(geoPoint);
            this.y = geoPoint;
            this.J = true;
        }
    }

    static /* synthetic */ void n(RouteFootRidePresenter routeFootRidePresenter) {
        if (routeFootRidePresenter.u != null) {
            HealthPoint[] healthPointArr = routeFootRidePresenter.u.gps_array;
            if (healthPointArr != null) {
                ArrayList arrayList = new ArrayList();
                Collections.addAll(arrayList, healthPointArr);
                routeFootRidePresenter.h = arrayList;
                routeFootRidePresenter.n();
                HealthPoint healthPoint = (HealthPoint) routeFootRidePresenter.h.get(routeFootRidePresenter.h.size() - 1);
                if (healthPoint.status == HealthPointStatus.HPS_VALID) {
                    routeFootRidePresenter.i.add(healthPoint);
                }
            }
        }
    }

    static /* synthetic */ void x(RouteFootRidePresenter routeFootRidePresenter) {
        if (routeFootRidePresenter.K) {
            String string = ((RouteFootRideMapPage) routeFootRidePresenter.mPage).getString(R.string.route_run_continue_record_text);
            routeFootRidePresenter.o();
            if (Tts.getInstance().JniIsPlaying() != 1) {
                routeFootRidePresenter.b(string);
            }
            routeFootRidePresenter.K = false;
        }
        edx.a(((RouteFootRideMapPage) routeFootRidePresenter.mPage).getString(R.string.notification_title_keep_record), ((RouteFootRideMapPage) routeFootRidePresenter.mPage).getString(R.string.notification_content_riding_record));
    }
}
