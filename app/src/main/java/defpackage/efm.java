package defpackage;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.graphics.Point;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.eyrie.amap.tracker.TrackInfo;
import com.autonavi.jni.eyrie.amap.tracker.TrackInfoKeyType;
import com.autonavi.jni.eyrie.amap.tracker.TrackType;
import com.autonavi.jni.route.health.HealthParamKey;
import com.autonavi.jni.route.health.HealthPoint;
import com.autonavi.jni.route.health.IHealth;
import com.autonavi.jni.route.health.IHealthRun;
import com.autonavi.jni.route.health.TraceStatistics;
import com.autonavi.jni.route.health.TraceStatus;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.foot.footnavi.FootNaviLocation;
import com.autonavi.minimap.route.logs.track.TrackPostUtils;
import com.autonavi.minimap.route.run.page.RouteFootRunMapPage;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.iflytek.tts.TtsService.PlaySoundUtils.HandleInterruptEvent;
import java.util.ArrayList;

/* renamed from: efm reason: default package */
/* compiled from: BaseRouteRunPresenter */
public abstract class efm extends eae<RouteFootRunMapPage> implements HandleInterruptEvent, defpackage.ecc.a, efc {
    public boolean a;
    protected int b;
    protected int c;
    protected efd d;
    protected boolean e;
    protected int f;
    protected boolean g;
    public ArrayList<HealthPoint> h;
    public ArrayList<HealthPoint> i;
    public ArrayList<efi> j;
    public efk k;
    protected Point l;
    protected GeoPoint[] m;
    public boolean n;
    private final a o;
    private ecc p;
    private boolean q;
    private dzv r;

    /* renamed from: efm$a */
    /* compiled from: BaseRouteRunPresenter */
    static class a implements ServiceConnection {
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        }

        public final void onServiceDisconnected(ComponentName componentName) {
        }

        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }
    }

    public abstract Handler a();

    public void a(int i2, int i3) {
    }

    public efm(RouteFootRunMapPage routeFootRunMapPage) {
        super(routeFootRunMapPage);
        this.o = new a(0);
        this.a = false;
        this.b = 0;
        this.c = 0;
        this.d = null;
        this.e = false;
        this.f = 0;
        this.g = false;
        this.h = new ArrayList<>();
        this.i = new ArrayList<>();
        this.j = new ArrayList<>();
        this.p = null;
        this.b = ags.a(((RouteFootRunMapPage) this.mPage).getContext()).width();
        this.c = ags.a(((RouteFootRunMapPage) this.mPage).getContext()).height();
        this.l = new Point(this.b / 2, (int) (((float) this.c) * 0.667f));
        this.d = new efd(((RouteFootRunMapPage) this.mPage).getContext());
        efd efd = this.d;
        if (efd.b == null) {
            defpackage.eao.a.b(TrackType.HEALTH_RUN);
            boolean a2 = defpackage.eao.a.a();
            String a3 = TrackPostUtils.a(TrackType.HEALTH_RUN, a2);
            efd.b = IHealthRun.CreateHealthRun(efd);
            efd.b.SetParam(HealthParamKey.HPK_WORKSPACE, a3);
            efd.b.SetParam(HealthParamKey.HPK_ENABLE_LOG, a2 ? "1" : "0");
            StringBuilder sb = new StringBuilder("voice health run path = ");
            sb.append(ebt.b());
            eft.a("song---", sb.toString());
            efd.b.SetParam(HealthParamKey.HPK_RES_PATH, ebt.b());
            efd.a();
            efd.h = TrackInfo.createTrackInfo(TrackType.HEALTH_RUN);
            eft.a("Tracker", "init health_run engine");
            if (efd.h != null) {
                efd.h.set(TrackInfoKeyType.SdkVersion, IHealthRun.GetVersion());
                efd.b.SetTrackInfo(efd.h);
            }
            StringBuilder sb2 = new StringBuilder("RunEngine version: ");
            sb2.append(IHealthRun.GetVersion());
            sb2.append("Build Date: ");
            sb2.append(IHealth.GetBuildDate());
            sb2.append("log folder = ");
            sb2.append(a3);
            sb2.append("debug = ");
            sb2.append(a2);
            eft.a("ENGINE_OUT", sb2.toString());
        }
        if (efd.d == null) {
            efd.d = new FootNaviLocation();
        }
        efd.d.startLocation(efd);
        this.p = new ecc(((RouteFootRunMapPage) this.mPage).getContext());
    }

    public void onPageCreated() {
        PageBundle arguments = ((RouteFootRunMapPage) this.mPage).getArguments();
        if (arguments != null) {
            this.m = (GeoPoint[]) arguments.getObject("key_recommend_line_points");
            this.n = arguments.getBoolean("key_is_from_recommend");
        }
        super.onPageCreated();
        eac a2 = eac.a();
        ead a3 = ead.a(4);
        a3.c = eay.a(R.string.notification_title_keep_record);
        a3.d = eay.a(R.string.notification_content_running_record);
        a2.a(a3);
        this.k = new efk((AbstractBaseMapPage) this.mPage);
        this.d.c = this;
        bty mapView = ((RouteFootRunMapPage) this.mPage).getMapView();
        if (mapView != null) {
            dzv dzv = new dzv(mapView.I(), mapView.v(), mapView.J(), mapView.o(), mapView.j(false), mapView.k(false));
            this.r = dzv;
            mapView.e(18);
            mapView.b(this.l.x, this.l.y);
            ebf.a(mapView, mapView.j(false), 0, 6);
            mapView.g(edr.a((String) "runnavimodewithangle", true) ? 0.0f : 39.0f);
        }
        ((RouteFootRunMapPage) this.mPage).a(ecv.a(ecv.a()));
        this.q = new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("traffic", false);
        ((RouteFootRunMapPage) this.mPage).getSuspendManager().d().f();
    }

    public void onStart() {
        super.onStart();
        ebo.a((AbstractBaseMapPage) this.mPage);
        this.p.a = this;
        this.p.a();
        PlaySoundUtils.getInstance().setHandleInterruptEventObj(this);
    }

    public void onStop() {
        super.onStop();
        this.p.b();
        this.p.a = null;
        this.k.a(false);
    }

    public void onDestroy() {
        super.onDestroy();
        eac.a().a(4);
        bqx bqx = (bqx) ank.a(bqx.class);
        if (bqx != null) {
            bqx.a(false, this.q, false, ((RouteFootRunMapPage) this.mPage).getMapManager(), ((RouteFootRunMapPage) this.mPage).getContext());
        }
        efd efd = this.d;
        efd.f = true;
        if (efd.d != null) {
            efd.d.stopLocation();
        }
        if (efd.b != null) {
            eft.a("CALL_ENGINE", "destroyHelEngine Release");
            IHealthRun.Release(efd.b);
            efd.b = null;
        }
        this.k.a(true);
        bty mapView = ((RouteFootRunMapPage) this.mPage).getMapView();
        if (mapView != null) {
            this.l = new Point(this.b / 2, this.c / 2);
            mapView.b(this.l.x, this.l.y);
            ebf.a(mapView, this.r.c, mapView.l(false), this.r.d);
            mapView.g(this.r.b);
            mapView.e(this.r.a);
            mapView.a(this.r.f.x, this.r.f.y);
            if (mapView.w() != ((int) this.r.e)) {
                mapView.f(this.r.e);
            }
        }
    }

    public void setMakeReceiveCallEvent(int i2) {
        this.g = i2 > 0;
        if (this.g) {
            PlaySoundUtils.getInstance().clear();
        }
    }

    public final void a(HealthPoint healthPoint) {
        Message obtainMessage = a().obtainMessage(0);
        obtainMessage.obj = healthPoint;
        a().sendMessage(obtainMessage);
    }

    public final void a(HealthPoint healthPoint, int i2) {
        Message obtainMessage = a().obtainMessage(1);
        obtainMessage.obj = healthPoint;
        obtainMessage.arg1 = i2;
        a().sendMessage(obtainMessage);
    }

    public final void a(int i2, double d2, long j2) {
        Message obtainMessage = a().obtainMessage(2);
        obtainMessage.obj = new efh(i2, d2, (int) j2);
        a().sendMessage(obtainMessage);
    }

    public final void a(TraceStatus traceStatus) {
        Message obtainMessage = a().obtainMessage(3);
        obtainMessage.arg1 = traceStatus.getValue();
        a().sendMessage(obtainMessage);
    }

    public final void a(TraceStatistics traceStatistics) {
        Message obtainMessage = a().obtainMessage(4);
        obtainMessage.obj = traceStatistics;
        a().sendMessage(obtainMessage);
    }

    public final void a(String str) {
        if (!this.g) {
            Message obtainMessage = a().obtainMessage(5);
            obtainMessage.obj = str;
            a().sendMessage(obtainMessage);
        }
    }
}
