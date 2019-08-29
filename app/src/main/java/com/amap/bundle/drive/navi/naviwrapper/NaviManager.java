package com.amap.bundle.drive.navi.naviwrapper;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.jni.ae.guide.GuideService;
import com.autonavi.jni.ae.guide.callback.OnVoiceConfigVersionCallback;
import com.autonavi.jni.ae.guide.model.SoundInfo;
import com.autonavi.jni.ae.guide.observer.GSoundPlayObserver;
import com.autonavi.jni.ae.pos.LocListener;
import com.autonavi.jni.ae.pos.LocNGMInfo;
import com.autonavi.jni.ae.pos.LocNGMListener;
import com.autonavi.jni.ae.route.RouteService;
import com.autonavi.jni.ae.route.model.ForbiddenInfo;
import com.autonavi.jni.ae.route.observer.RouteObserver;
import com.autonavi.jni.ae.route.route.CalcRouteResult;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressFBWarnings({"BIT_SIGNED_CHECK"})
public class NaviManager implements GSoundPlayObserver, LocNGMListener, RouteObserver {
    public static final /* synthetic */ boolean n = true;
    public RouteService a;
    public GuideService b;
    public AtomicInteger c;
    public AtomicInteger d;
    public final nz e;
    public List<LocListener> f;
    public List<RouteObserver> g;
    public NaviType h;
    public boolean i;
    public boolean j;
    public OnVoiceConfigVersionCallback k;
    public OnVoiceConfigVersionCallback l;
    public afx m;
    private final List<Object> o;

    public enum NaviType {
        CAR_NAVI,
        CAR_SIMULATE,
        TRUCK_NAVI,
        TRUCK_SIMULATE,
        MOTOR,
        CRUISE
    }

    static class a {
        /* access modifiers changed from: private */
        public static NaviManager a = new NaviManager(0);
    }

    /* synthetic */ NaviManager(byte b2) {
        this();
    }

    private NaviManager() {
        this.c = new AtomicInteger(0);
        this.d = new AtomicInteger(0);
        this.o = new CopyOnWriteArrayList();
        this.j = false;
        this.l = new OnVoiceConfigVersionCallback() {
            public final void onVoiceVersion(int i) {
                NaviManager.a("version onVoiceVersion version=".concat(String.valueOf(i)));
                if (NaviManager.this.k != null) {
                    NaviManager.this.k.onVoiceVersion(i);
                }
            }
        };
        this.m = (afx) defpackage.esb.a.a.a(afx.class);
        this.e = new nz();
    }

    public static NaviManager a() {
        return a.a;
    }

    public static void a(String str) {
        AMapLog.i("NaviManager", str);
        ku.a().c("NaviMonitor", "[NaviManager]".concat(String.valueOf(str)));
    }

    public final void a(RouteObserver routeObserver) {
        if (this.g != null && this.g.contains(routeObserver)) {
            this.g.remove(routeObserver);
        }
    }

    public final boolean b() {
        if (this.b == null) {
            return false;
        }
        int i2 = this.d.get();
        if (bno.a) {
            if (i2 > 1 || i2 <= 0) {
                tq.a("NaviManager", "", "GuideService ref error by ".concat(String.valueOf(i2)));
            }
            if (Looper.myLooper() != Looper.getMainLooper()) {
                StringBuilder sb = new StringBuilder("Call GuideService in child thread ");
                sb.append(Thread.currentThread().getName());
                String sb2 = sb.toString();
                ToastHelper.showToast(sb2);
                tq.a("NaviManager", "", sb2);
            }
        }
        if (i2 > 0) {
            return true;
        }
        return false;
    }

    public final boolean c() {
        if (this.a == null) {
            return false;
        }
        int i2 = this.c.get();
        if (bno.a) {
            if (i2 > 1 || i2 <= 0) {
                tq.a("NaviManager", "", "RouteService ref error by ".concat(String.valueOf(i2)));
            }
            if (Looper.myLooper() != Looper.getMainLooper()) {
                StringBuilder sb = new StringBuilder("Call RouteService in child thread ");
                sb.append(Thread.currentThread().getName());
                tq.a("NaviManager", "", sb.toString());
            }
        }
        if (i2 > 0) {
            return true;
        }
        return false;
    }

    public final void d() {
        b(this.m != null ? this.m.a() : false);
    }

    public final boolean e() {
        return this.h != null && (this.h == NaviType.CAR_NAVI || this.h == NaviType.CAR_SIMULATE || this.h == NaviType.TRUCK_NAVI || this.h == NaviType.TRUCK_SIMULATE || this.h == NaviType.MOTOR);
    }

    public final boolean f() {
        return e() || (this.h != null && this.h == NaviType.CRUISE) || this.i;
    }

    public final void a(int i2, String str) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            if (bno.a) {
                StringBuilder sb = new StringBuilder("control ");
                sb.append(i2);
                sb.append(MergeUtil.SEPARATOR_KV);
                sb.append(str);
                sb.append(" in ");
                sb.append(Thread.currentThread().getName());
                a(sb.toString());
            }
            Message a2 = nz.a(1105);
            Bundle bundle = new Bundle();
            bundle.putInt("key", i2);
            bundle.putString("value", str);
            a2.setData(bundle);
            this.e.a(a2);
            return;
        }
        b(i2, str);
    }

    public final void c(int i2, String str) {
        if (c()) {
            StringBuilder sb = new StringBuilder("routeControl ");
            sb.append(i2);
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(str);
            a(sb.toString());
            this.a.control(i2, str);
        }
    }

    public static String g() {
        return GuideService.getEngineVersion();
    }

    public static String h() {
        return RouteService.getSdkVersion();
    }

    public static String i() {
        return RouteService.getRouteVersion();
    }

    public void updateNGMInfo(LocNGMInfo locNGMInfo) {
        Message a2 = nz.a(9999);
        Bundle bundle = new Bundle();
        bundle.putSerializable("LocNGMInfo", locNGMInfo);
        a2.setData(bundle);
        this.e.a(a2);
    }

    public void onNewRoute(int i2, CalcRouteResult calcRouteResult, Object obj, boolean z) {
        String str;
        if (!f()) {
            Message a2 = nz.a(100);
            a2.obj = new Object[]{calcRouteResult, obj};
            Bundle bundle = new Bundle();
            bundle.putInt("type", i2);
            bundle.putBoolean("isLocal", z);
            a2.setData(bundle);
            this.e.a(a2);
            if (bno.a) {
                StringBuilder sb = new StringBuilder("onNewRoute : type= ");
                sb.append(i2);
                sb.append(" isLocal = ");
                sb.append(z);
                sb.append(",route length=");
                if (calcRouteResult.getRoute(0) != null) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(calcRouteResult.getRoute(0).getRouteLength());
                    sb2.append(" ,pathId ==> ");
                    str = sb2.toString();
                } else {
                    str = "-1, pathId ==> ";
                }
                sb.append(str);
                String sb3 = sb.toString();
                int pathCount = calcRouteResult.getPathCount();
                StringBuilder sb4 = new StringBuilder(sb3);
                for (int i3 = 0; i3 < pathCount; i3++) {
                    sb4.append(calcRouteResult.getRoute(i3).getPathId());
                    sb4.append(", ");
                }
                a(sb4.toString());
                if (i2 == 11 || i2 == 13) {
                    if (obj != null) {
                        a("onNewRoute : obj != null, roadName = ".concat(String.valueOf(((ForbiddenInfo) obj).roadName)));
                        return;
                    }
                    a((String) "onNewRoute : obj == null, no roadName");
                }
            }
        }
    }

    public void onNewRouteError(int i2, int i3, Object obj, boolean z) {
        if (bno.a) {
            StringBuilder sb = new StringBuilder("onNewRouteError : type = ");
            sb.append(i2);
            sb.append(" errorCode = ");
            sb.append(i3);
            sb.append(" isLocal = ");
            sb.append(z);
            a(sb.toString());
        }
        if (!f()) {
            Message a2 = nz.a(101);
            a2.obj = obj;
            Bundle bundle = new Bundle();
            bundle.putInt("type", i2);
            bundle.putInt("errorCode", i3);
            bundle.putBoolean("isLocal", z);
            a2.setData(bundle);
            this.e.a(a2);
        }
    }

    public boolean isPlaying() {
        return PlaySoundUtils.getInstance().isPlaying();
    }

    private void b(boolean z) {
        if (b()) {
            this.b.control(46, z ? "1" : "0");
        }
    }

    public final void b(int i2, String str) {
        if (b()) {
            StringBuilder sb = new StringBuilder("control ");
            sb.append(i2);
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(str);
            a(sb.toString());
            this.b.control(i2, str);
        }
    }

    public final void a(boolean z) {
        if (b()) {
            a("openTrafficBroadcast".concat(String.valueOf(z)));
            this.b.control(5, z ? "1" : "0");
        }
    }

    public void onPlayTTS(SoundInfo soundInfo) {
        if (!a.a.f() && !this.j) {
            if (soundInfo == null) {
                a((String) "onPlayTTS,info == null");
                return;
            }
            if (bno.a) {
                StringBuilder sb = new StringBuilder("onPlayTTS, type = ");
                sb.append(soundInfo.sceneType);
                sb.append(", str = ");
                sb.append(soundInfo.text);
                a(sb.toString());
            }
            Message a2 = nz.a(300);
            Bundle bundle = new Bundle();
            bundle.putString("str", soundInfo.text);
            bundle.putInt("type", soundInfo.sceneType);
            a2.setData(bundle);
            this.e.a(a2);
        }
    }

    public void onPlayRing(int i2) {
        if (!a.a.f() && !this.j) {
            if (bno.a) {
                a("onPlayRing, type = ".concat(String.valueOf(i2)));
            }
            Message a2 = nz.a(301);
            Bundle bundle = new Bundle();
            bundle.putInt("type", i2);
            a2.setData(bundle);
            this.e.a(a2);
        }
    }
}
