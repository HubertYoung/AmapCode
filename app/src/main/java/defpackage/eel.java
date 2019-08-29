package defpackage;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.graphics.Point;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.jni.eyrie.amap.tracker.TrackInfo;
import com.autonavi.jni.eyrie.amap.tracker.TrackInfoKeyType;
import com.autonavi.jni.eyrie.amap.tracker.TrackType;
import com.autonavi.jni.route.health.HealthParamKey;
import com.autonavi.jni.route.health.HealthPoint;
import com.autonavi.jni.route.health.IHealthBike;
import com.autonavi.jni.route.health.TraceStatistics;
import com.autonavi.jni.route.health.TraceStatus;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.foot.footnavi.FootNaviLocation;
import com.autonavi.minimap.route.logs.track.TrackPostUtils;
import com.autonavi.minimap.route.ride.page.RouteFootRideMapPage;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.iflytek.tts.TtsService.PlaySoundUtils.HandleInterruptEvent;
import java.util.ArrayList;

/* renamed from: eel reason: default package */
/* compiled from: BaseRouteRidePresenter */
public abstract class eel extends eae<RouteFootRideMapPage> implements HandleInterruptEvent, defpackage.ecc.a, edw {
    public boolean a;
    protected int b;
    protected int c;
    protected edx d;
    protected boolean e;
    protected int f;
    protected boolean g;
    public ArrayList<HealthPoint> h;
    public ArrayList<HealthPoint> i;
    public ArrayList<eei> j;
    public eek k;
    protected Point l;
    private final a m;
    private ecc n;
    private boolean o;
    private dzv p;

    /* renamed from: eel$a */
    /* compiled from: BaseRouteRidePresenter */
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

    public eel(RouteFootRideMapPage routeFootRideMapPage) {
        super(routeFootRideMapPage);
        this.m = new a(0);
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
        this.n = null;
        this.b = ags.a(((RouteFootRideMapPage) this.mPage).getContext()).width();
        this.c = ags.a(((RouteFootRideMapPage) this.mPage).getContext()).height();
        this.l = new Point(this.b / 2, (int) (((float) this.c) * 0.667f));
        this.d = new edx(((RouteFootRideMapPage) this.mPage).getContext());
        edx edx = this.d;
        if (edx.b == null) {
            defpackage.eao.a.b(TrackType.HEALTH_RIDE);
            boolean a2 = defpackage.eao.a.a();
            String a3 = TrackPostUtils.a(TrackType.HEALTH_RIDE, a2);
            edx.b = IHealthBike.CreateHealthBike(edx);
            edx.b.SetParam(HealthParamKey.HPK_WORKSPACE, a3);
            edx.b.SetParam(HealthParamKey.HPK_ENABLE_LOG, a2 ? "1" : "0");
            StringBuilder sb = new StringBuilder("voice health ride path = ");
            sb.append(ebt.c());
            eer.a("song---", sb.toString());
            edx.b.SetParam(HealthParamKey.HPK_RES_PATH, ebt.c());
            edx.a();
            edx.h = TrackInfo.createTrackInfo(TrackType.HEALTH_RIDE);
            eer.a("Tracker", "init bike engine trackPath");
            if (edx.h != null) {
                edx.h.set(TrackInfoKeyType.SdkVersion, IHealthBike.GetVersion());
                edx.b.SetTrackInfo(edx.h);
            }
            if (edx.b != null) {
                StringBuilder sb2 = new StringBuilder("Init successversion = ");
                sb2.append(IHealthBike.GetVersion());
                sb2.append("log folder = ");
                sb2.append(a3);
                sb2.append("debug = ");
                sb2.append(a2);
                eer.a("ENGINE_OUT", sb2.toString());
            }
        }
        if (edx.d == null) {
            edx.d = new FootNaviLocation();
        }
        edx.d.startLocation(edx);
        this.n = new ecc(((RouteFootRideMapPage) this.mPage).getContext());
    }

    public void onPageCreated() {
        super.onPageCreated();
        eac a2 = eac.a();
        ead a3 = ead.a(5);
        a3.c = eay.a(R.string.notification_title_keep_record);
        a3.d = eay.a(R.string.notification_content_riding_record);
        a2.a(a3);
        this.k = new eek((AbstractBaseMapPage) this.mPage);
        this.d.c = this;
        bty mapView = ((RouteFootRideMapPage) this.mPage).getMapView();
        if (mapView != null) {
            dzv dzv = new dzv(mapView.I(), mapView.v(), mapView.J(), mapView.o(), mapView.j(false), mapView.k(false));
            this.p = dzv;
            mapView.e(18);
            mapView.b(this.l.x, this.l.y);
            ebf.a(mapView, mapView.j(false), 0, 12);
            mapView.g(edr.a((String) "ridenavimodewithangle", true) ? 0.0f : 39.0f);
        }
        ((RouteFootRideMapPage) this.mPage).a(ecv.a(ecv.a()));
        this.o = new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("traffic", false);
        ((RouteFootRideMapPage) this.mPage).getSuspendManager().d().f();
        bqx bqx = (bqx) ank.a(bqx.class);
        if (bqx != null) {
            bqx.a(false, false, false, mapView, ((RouteFootRideMapPage) this.mPage).getContext());
        }
    }

    public void onStart() {
        super.onStart();
        ebo.a((AbstractBaseMapPage) this.mPage);
        this.n.a = this;
        this.n.a();
        PlaySoundUtils.getInstance().setHandleInterruptEventObj(this);
    }

    public void onStop() {
        super.onStop();
        this.n.b();
        this.n.a = null;
        this.k.a(false);
    }

    public void onDestroy() {
        super.onDestroy();
        eac.a().a(5);
        bqx bqx = (bqx) ank.a(bqx.class);
        if (bqx != null) {
            bqx.a(false, this.o, false, ((RouteFootRideMapPage) this.mPage).getMapManager(), ((RouteFootRideMapPage) this.mPage).getContext());
        }
        edx edx = this.d;
        edx.f = true;
        if (edx.d != null) {
            edx.d.stopLocation();
        }
        if (edx.b != null) {
            eer.a("CALL_ENGINE", "destroyHelEngine Release");
            IHealthBike.Release(edx.b);
            edx.b = null;
        }
        this.k.a(true);
        bty mapView = ((RouteFootRideMapPage) this.mPage).getMapView();
        if (mapView != null) {
            this.l = new Point(this.b / 2, this.c / 2);
            mapView.b(this.l.x, this.l.y);
            ebf.a(mapView, this.p.c, mapView.l(false), this.p.d);
            mapView.g(this.p.b);
            mapView.e(this.p.a);
            mapView.a(this.p.f.x, this.p.f.y);
            if (mapView.w() != ((int) this.p.e)) {
                mapView.f(this.p.e);
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
        if (i2 / 1000 <= 100) {
            Message obtainMessage = a().obtainMessage(1);
            obtainMessage.obj = healthPoint;
            obtainMessage.arg1 = i2;
            a().sendMessage(obtainMessage);
        }
    }

    public final void a(int i2, double d2, long j2) {
        Message obtainMessage = a().obtainMessage(2);
        eeh eeh = new eeh(i2, d2, j2);
        obtainMessage.obj = eeh;
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

    public final void a(int i2) {
        if (!this.a) {
            this.f = i2;
            this.e = this.f == 0;
            ((RouteFootRideMapPage) this.mPage).a(ecv.a(this.f));
        }
    }
}
