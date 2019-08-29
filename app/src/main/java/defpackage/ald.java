package defpackage;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.ae.gmap.utils.GLMapStaticValue;
import com.autonavi.jni.ae.gmap.GLMapEngine;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.jni.ae.gmap.gesture.EAMapPlatformGestureInfo;
import com.autonavi.jni.ae.gmap.glinterface.GLDeviceAttribute;
import com.autonavi.jni.ae.gmap.glinterface.GLSurfaceAttribute;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle;

/* renamed from: ald reason: default package */
/* compiled from: SurfaceLogicImpl */
public final class ald {
    private boolean A = false;
    private int B = GLMapStaticValue.b;
    private Handler C = new Handler(Looper.getMainLooper());
    private boolean D = false;
    private long E = 0;
    private float F = 0.0f;
    private float G = 0.0f;
    private float H = 0.0f;
    private float I = 0.0f;
    private boolean J = true;
    akq a = null;
    Context b;
    aku c = null;
    Integer d = Integer.valueOf(-1);
    boolean e = false;
    boolean f = false;
    int g = 0;
    int h = 0;
    int i = 0;
    int j = 0;
    b k = new b(this, 0);
    c l = new c(this, 0);
    boolean m = true;
    int n = 0;
    int o = 0;
    int p = 0;
    boolean q;
    final Object r = new Object();
    volatile boolean s = false;
    boolean t = false;
    int u;
    public a v = new a(this, 0);
    private String w = "SurfaceLogicImpl";
    private EAMapPlatformGestureInfo x = new EAMapPlatformGestureInfo();
    private final Object y = new Object();
    private boolean z = false;

    /* renamed from: ald$a */
    /* compiled from: SurfaceLogicImpl */
    public class a implements akv {
        private a() {
        }

        /* synthetic */ a(ald ald, byte b) {
            this();
        }

        public final void a(int i, int i2) {
            StringBuilder sb = new StringBuilder("setGestureStatus: ");
            sb.append(i);
            sb.append(", ");
            sb.append(i2);
            akq.b(sb.toString());
            if (ald.this.o == 0) {
                ald.this.o = i2;
            }
            if (ald.this.n == 0 || i2 != 5) {
                ald.this.n = i2;
            }
        }

        public final void a() {
            akq.b((String) "setGestureHasInertia: true");
            if (ald.this.o != 0) {
                ald.this.q = true;
            }
        }

        public final boolean b() {
            akq.b((String) "isGestureInMain");
            return ald.this.m;
        }

        public final void b(int i, int i2) {
            akq.b("setMotionState: ".concat(String.valueOf(i2)));
            synchronized (ald.this.r) {
                ald.this.a.d.setPostureState(i, i2);
            }
        }

        public final void a(int i) {
            ald.this.p = i | ald.this.p;
        }
    }

    /* renamed from: ald$b */
    /* compiled from: SurfaceLogicImpl */
    class b implements amm {
        public amm a;
        private int c;
        private float d;
        private boolean e;

        private b() {
            this.a = null;
            this.c = GLMapStaticValue.b;
            this.d = 0.0f;
            this.e = false;
        }

        /* synthetic */ b(ald ald, byte b2) {
            this();
        }

        public final void onSurfaceCreated(int i) {
            if (ald.this.a(i) && this.a != null) {
                this.a.onSurfaceCreated(i);
            }
        }

        public final void onSurfaceChanged(int i, int i2, int i3, int i4) {
            if (ald.this.a(i)) {
                ald.this.u = i4;
                ald.this.t = false;
                DisplayMetrics displayMetrics = ald.this.b.getResources().getDisplayMetrics();
                int i5 = displayMetrics.widthPixels;
                int i6 = displayMetrics.heightPixels;
                if (!(ald.this.g == 0 || ald.this.h == 0 || ald.this.i == 0 || ald.this.j == 0 || ((ald.this.g == i2 && ald.this.h == i3) || ald.this.i != i6 || ald.this.j != i5))) {
                    ald.this.f = true;
                }
                ald.this.g = i2;
                ald.this.h = i3;
                ald.this.i = i5;
                ald.this.j = i6;
                if (!ald.this.s) {
                    onRenderDeviceCreated(i);
                }
                aks B = ald.this.a.B(i);
                switch (i4) {
                    case 1:
                        B.e = Config.ALPHA_8;
                        return;
                    case 2:
                        B.e = Config.ARGB_8888;
                        return;
                    default:
                        B.e = Config.RGB_565;
                        return;
                }
            }
        }

        public final void onSurfaceDestroy(int i) {
            if (ald.this.a(i) && this.a != null) {
                this.a.onSurfaceDestroy(i);
            }
        }

        public final void onRenderDeviceCreated(int i) {
            StringBuilder sb = new StringBuilder("onRenderDeviceCreated: ");
            sb.append(i);
            sb.append(", ");
            sb.append(ald.this.d);
            StringBuilder sb2 = new StringBuilder("onRenderDeviceCreated: deviceId: ");
            sb2.append(i);
            sb2.append(", mDeviceID: ");
            sb2.append(ald.this.d);
            ald.this.a.a(sb2.toString());
            if (ald.this.a(i)) {
                ald.this.t = false;
                ald.this.s = true;
            }
        }

        public final void onRenderDeviceDestroyed(int i) {
            StringBuilder sb = new StringBuilder("onRenderDeviceDestroyed: ");
            sb.append(i);
            sb.append(", ");
            sb.append(ald.this.d);
            if (ald.this.a(i)) {
                try {
                    ald.this.d = Integer.valueOf(-1);
                    for (int s : ald.this.a.d.getEngineIDs(i)) {
                        GLOverlayBundle<BaseMapOverlay<?, ?>> s2 = ald.this.a.s(s);
                        if (s2 != null) {
                            s2.clearOverlayTexture();
                            s2.removeAll(true);
                        }
                    }
                    if (ald.this.k != null) {
                        ald.this.k.onSurfaceDestroy(i);
                    }
                    akq akq = ald.this.a;
                    b bVar = ald.this.k;
                    akq.b("removeMapSurfaceListener:".concat(String.valueOf(bVar)));
                    akq.s.removeMapSurfaceListener(bVar);
                    akq akq2 = ald.this.a;
                    c cVar = ald.this.l;
                    akq.b("removeMapWidgetListener:".concat(String.valueOf(cVar)));
                    akq2.t.removeMapWidgetListener(cVar);
                    ald.this.t = false;
                } catch (Exception unused) {
                }
            }
        }

        public final void onDrawFrameFirst(int i) {
            if (ald.this.a(i) && this.a != null) {
                this.a.onDrawFrameFirst(i);
            }
        }

        public final void onDrawFrameFirstOnGLThread(int i) {
            if (ald.this.a(i) && this.a != null) {
                this.a.onDrawFrameFirstOnGLThread(i);
            }
        }

        public final void onSurfaceRenderFrame(int i, int i2) {
            if (ald.this.a(i)) {
                if (i2 == 0) {
                    int[] engineIDs = ald.this.a.d.getEngineIDs(ald.this.d.intValue());
                    if (engineIDs.length <= 0) {
                        akq.b((String) "AMapSurface onPreDrawFrame error with no MapEngine IDS: ");
                    }
                    int i3 = engineIDs[0];
                    StringBuilder sb = new StringBuilder("onPreDrawFrame: ");
                    sb.append(i);
                    sb.append("(");
                    sb.append(i3);
                    sb.append(", -1)");
                    akq.b(sb.toString());
                    GLOverlayBundle<BaseMapOverlay<?, ?>> s = ald.this.a.s(i3);
                    if (s != null) {
                        s.reculateRouteBoard(ald.this.a);
                    }
                    return;
                }
                if (1 == i2) {
                    GLMapEngine gLMapEngine = ald.this.a.d;
                    int[] engineIDs2 = gLMapEngine.getEngineIDs(ald.this.d.intValue());
                    if (engineIDs2.length <= 0) {
                        akq.b((String) "AMapSurface onAfterDrawFrame error with no MapEngine IDS: ");
                    }
                    final int i4 = engineIDs2[0];
                    StringBuilder sb2 = new StringBuilder("onAfterDrawFrame: ");
                    sb2.append(i);
                    sb2.append("(");
                    sb2.append(i4);
                    sb2.append(")");
                    akq.b(sb2.toString());
                    GLMapState mapState = gLMapEngine.getMapState(i4);
                    float f = 0.0f;
                    if (mapState != null) {
                        f = mapState.getMapZoomer();
                    }
                    if (ald.this.l != null) {
                        ald.this.l.paintCompass(i4);
                        ald.this.l.refreshScaleLineView(i4);
                    }
                    if (!gLMapEngine.isInMapAction(i4) && !gLMapEngine.isInMapAnimation(i4)) {
                        if (gLMapEngine.getPostureState(i4) == 1) {
                            ald.this.v.b(i4, 0);
                            if (ald.this.a.q != null) {
                                ald.this.a.a(gLMapEngine.getBelongToRenderDeviceId(i4), (Runnable) new Runnable() {
                                    public final void run() {
                                        if (ald.this.c.i != null) {
                                            ald.this.c.i.onMontionFinish(i4);
                                        }
                                        ald.this.a.q.onMotionFinished(i4, 1);
                                    }
                                });
                            }
                            if (ald.this.n != 0) {
                                ald ald = ald.this;
                                akq.b("doMapCenterReport: ".concat(String.valueOf(i4)));
                                GLGeoPoint d2 = ald.a.d(i4);
                                if (d2 != null) {
                                    int l = (int) ald.a.l(i4);
                                    int i5 = ald.a.d != null ? ald.a.d.getControllerStateBoolValue(i4, 1) : false ? 1 : 0;
                                    int[] mapModeState = ald.a.d.getMapModeState(i4, false);
                                    if (mapModeState != null) {
                                        ana.a(d2, (int) f, mapModeState[0], mapModeState[2], ald.n, l, i5);
                                        ald.n = 0;
                                    }
                                }
                            }
                            if (ald.this.o != 0) {
                                ald.this.o = 0;
                                ald.this.p = 0;
                                ald.this.q = false;
                            }
                        }
                        if (this.d != f) {
                            this.d = f;
                            if (ald.this.a.q != null) {
                                ald.this.a((Runnable) new Runnable() {
                                    public final void run() {
                                        ald.this.a.q.onMapLevelChange(i4, true);
                                    }
                                });
                            }
                        }
                    }
                    if (!ald.this.t) {
                        ald.this.t = true;
                        ald.this.a((Runnable) new Runnable() {
                            public final void run() {
                                if (b.this.a != null) {
                                    b.this.a.onSurfaceChanged(i4, ald.this.g, ald.this.h, ald.this.u);
                                }
                            }
                        });
                    }
                    if (!this.e) {
                        if (this.a != null) {
                            this.a.onDrawFrameFirstOnGLThread(i4);
                        }
                        this.e = true;
                        ald.this.a((Runnable) new Runnable() {
                            public final void run() {
                                if (b.this.a != null) {
                                    b.this.a.onDrawFrameFirst(i4);
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    /* renamed from: ald$c */
    /* compiled from: SurfaceLogicImpl */
    class c implements amn {
        public amn a;

        private c() {
            this.a = null;
        }

        /* synthetic */ c(ald ald, byte b2) {
            this();
        }

        public final void refreshScaleLineView(int i) {
            if (ald.this.a.d.getBelongToRenderDeviceId(i) == ald.this.d.intValue() && this.a != null) {
                this.a.refreshScaleLineView(i);
            }
        }

        public final void setScaleColor(int i, int i2, int i3) {
            if (ald.this.a.d.getBelongToRenderDeviceId(i) == ald.this.d.intValue() && this.a != null) {
                this.a.setScaleColor(i, i2, i3);
            }
        }

        public final void paintCompass(int i) {
            if (ald.this.a.d.getBelongToRenderDeviceId(i) == ald.this.d.intValue() && this.a != null) {
                this.a.paintCompass(i);
            }
        }

        public final void fadeCompassWidget(int i) {
            if (ald.this.a.d.getBelongToRenderDeviceId(i) == ald.this.d.intValue() && this.a != null && !ald.this.e) {
                this.a.fadeCompassWidget(i);
            }
        }

        public final void setFrontViewVisibility(int i, boolean z) {
            if (ald.this.a.d.getBelongToRenderDeviceId(i) == ald.this.d.intValue() && this.a != null) {
                this.a.setFrontViewVisibility(i, z);
            }
        }
    }

    public ald(Context context) {
        this.b = context;
    }

    public final boolean a(Runnable runnable) {
        return this.C.post(runnable);
    }

    public final int a() {
        if (this.d.intValue() == -1) {
            synchronized (this.y) {
                try {
                    GLDeviceAttribute gLDeviceAttribute = new GLDeviceAttribute();
                    gLDeviceAttribute.mIsNeedAntialias = true;
                    gLDeviceAttribute.mSamples = 2;
                    gLDeviceAttribute.mIsRecordeable = false;
                    new StringBuilder("[testperformance] set antialias = ").append(gLDeviceAttribute.mIsNeedAntialias);
                    this.d = Integer.valueOf(this.a.a(gLDeviceAttribute));
                    this.z = true;
                    this.x.mDeviceId = this.d.intValue();
                    this.c.g = this.d.intValue();
                }
            }
        }
        return this.d.intValue();
    }

    public final void a(akq akq) {
        this.a = akq;
        this.c = new aku(akq, this.v);
        this.a.a((amm) this.k);
        this.a.a((amn) this.l);
    }

    public final void b() {
        long j2;
        long j3;
        long j4;
        StringBuilder sb = new StringBuilder("SurfaceLogicImpl uninit: ");
        sb.append(this.d);
        sb.append(", ");
        sb.append(this);
        sb.append(", ");
        sb.append(Process.myTid());
        sb.append(", ");
        sb.append(Log.getStackTraceString(new Throwable()));
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (this.a == null || true != this.z) {
            j4 = elapsedRealtime;
            j3 = j4;
            j2 = j3;
        } else {
            this.z = false;
            int intValue = this.d.intValue();
            j4 = SystemClock.elapsedRealtime();
            this.a.y(intValue);
            j3 = SystemClock.elapsedRealtime();
            this.a.x(intValue);
            j2 = SystemClock.elapsedRealtime();
        }
        StringBuilder sb2 = new StringBuilder("SurfaceLogicImpl destroy time: ");
        sb2.append(j4 - elapsedRealtime);
        sb2.append(", ");
        sb2.append(j3 - j4);
        sb2.append(", ");
        sb2.append(j2 - j3);
        sb2.append(", ");
        sb2.append(SystemClock.elapsedRealtime() - j2);
    }

    public final void a(Surface surface, int i2, int i3) {
        StringBuilder sb = new StringBuilder("SurfaceLogicImpl surfaceCreated：mDeviceID = ");
        sb.append(this.d);
        sb.append(", ");
        sb.append(surface);
        sb.append(", ");
        sb.append(i2);
        sb.append(",");
        sb.append(i3);
        sb.append(",");
        sb.append(this);
        long elapsedRealtime = SystemClock.elapsedRealtime();
        this.c.h = (float) this.b.getResources().getDisplayMetrics().densityDpi;
        if (this.a != null) {
            synchronized (this.y) {
                if (this.d.intValue() == -1) {
                    GLDeviceAttribute gLDeviceAttribute = new GLDeviceAttribute();
                    gLDeviceAttribute.mIsNeedAntialias = true;
                    gLDeviceAttribute.mSamples = 2;
                    gLDeviceAttribute.mIsRecordeable = false;
                    new StringBuilder("[testperformance] set antialias = ").append(gLDeviceAttribute.mIsNeedAntialias);
                    this.d = Integer.valueOf(this.a.a(gLDeviceAttribute));
                    this.z = true;
                    this.x.mDeviceId = this.d.intValue();
                    this.c.g = this.d.intValue();
                }
            }
            GLSurfaceAttribute gLSurfaceAttribute = new GLSurfaceAttribute();
            gLSurfaceAttribute.mSurfaceWidth = i2;
            gLSurfaceAttribute.mSurfaceHeight = i3;
            this.a.a(this.d.intValue(), surface, gLSurfaceAttribute);
            if (!this.A && this.k != null) {
                this.k.onSurfaceCreated(this.d.intValue());
                this.A = true;
            }
            this.a.c(this.d.intValue(), GLMapStaticValue.b);
        }
        StringBuilder sb2 = new StringBuilder("SurfaceLogicImpl surfaceCreated time: ");
        sb2.append(SystemClock.elapsedRealtime() - elapsedRealtime);
        sb2.append(", ");
        sb2.append(this);
    }

    public final void b(Surface surface, int i2, int i3) {
        StringBuilder sb = new StringBuilder("SurfaceLogicImpl surfaceChanged: mDeviceID = ");
        sb.append(this.d);
        sb.append(", ");
        sb.append(surface);
        sb.append(", ");
        sb.append(i2);
        sb.append(",");
        sb.append(i3);
        sb.append(",");
        sb.append(this);
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (this.a != null) {
            GLSurfaceAttribute gLSurfaceAttribute = new GLSurfaceAttribute();
            gLSurfaceAttribute.mSurfaceWidth = i2;
            gLSurfaceAttribute.mSurfaceHeight = i3;
            this.a.b(this.d.intValue(), surface, gLSurfaceAttribute);
            b(this.d.intValue());
        }
        StringBuilder sb2 = new StringBuilder("SurfaceLogicImpl surfaceChanged time: ");
        sb2.append(SystemClock.elapsedRealtime() - elapsedRealtime);
        sb2.append(", ");
        sb2.append(this);
    }

    private void b(int i2) {
        int[] engineIDs = this.a.d.getEngineIDs(i2);
        if (engineIDs != null && engineIDs.length > 0) {
            for (int clearAllMessages : engineIDs) {
                this.a.d.clearAllMessages(clearAllMessages);
            }
        }
        this.a.d.renderResumeIn(i2);
    }

    public final void a(Surface surface) {
        StringBuilder sb = new StringBuilder("SurfaceLogicImpl surfaceDestroyed: mDeviceID = ");
        sb.append(this.d);
        sb.append(", ");
        sb.append(surface);
        sb.append(", , ");
        sb.append(this);
        sb.append(", ");
        sb.append(Thread.currentThread().getId());
        sb.append(", ");
        sb.append(Process.myTid());
        sb.append(", ");
        sb.append(Log.getStackTraceString(new Throwable()));
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (this.a != null) {
            this.a.y(this.d.intValue());
        }
        StringBuilder sb2 = new StringBuilder("SurfaceLogicImpl surfaceDestroyed time: ");
        sb2.append(SystemClock.elapsedRealtime() - elapsedRealtime);
        sb2.append(", ");
        sb2.append(this);
    }

    public final void c() {
        new StringBuilder("SurfaceLogicImpl onDetachedFromWindow, ").append(this);
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (this.a != null) {
            this.a.y(this.d.intValue());
        }
        long elapsedRealtime2 = SystemClock.elapsedRealtime();
        long j2 = elapsedRealtime2 - elapsedRealtime;
        long elapsedRealtime3 = SystemClock.elapsedRealtime() - elapsedRealtime2;
        StringBuilder sb = new StringBuilder("SurfaceLogicImpl onDetachedFromWindow time: ");
        sb.append(j2);
        sb.append(", ");
        sb.append(elapsedRealtime3);
        sb.append(", ");
        sb.append(this);
    }

    public final void a(amm amm) {
        akq.b((String) "setMapSurfaceListener");
        this.k.a = amm;
    }

    public final void a(amn amn) {
        this.l.a = amn;
    }

    public final void a(int i2, boolean z2) {
        StringBuilder sb = new StringBuilder("setNaviMode: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(z2);
        akq.b(sb.toString());
        if (this.e != z2) {
            this.e = z2;
            GLMapEngine gLMapEngine = this.a.d;
            int C2 = this.a.C(i2);
            if (this.e) {
                gLMapEngine.setBusinessDataParamater(i2, 66, 1, 15, 0, 0);
                this.a.d(C2, akq.F, GLMapStaticValue.a);
            } else {
                gLMapEngine.setBusinessDataParamater(i2, 66, 0, 0, 0, 0);
                this.a.d(C2, akq.F, this.B);
            }
            this.a.p(C2);
        }
    }

    public final void a(amj amj) {
        this.c.i = amj;
    }

    public final boolean a(MotionEvent motionEvent) {
        boolean z2 = false;
        if (this.a == null) {
            return false;
        }
        boolean z3 = this.s;
        boolean z4 = this.a.B(this.d.intValue()).d;
        StringBuilder sb = new StringBuilder("onTouchEvent: mHasEglContextCreated: ");
        sb.append(this.s);
        sb.append(", mDeviceID: ");
        sb.append(this.d);
        sb.append(", isTouchEnable: ");
        sb.append(z4);
        String sb2 = sb.toString();
        if (!z3 || !z4) {
            this.a.a(sb2);
            return false;
        }
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        this.x.mDeviceId = this.d.intValue();
        this.x.mGestureState = 3;
        this.x.mGestureType = 8;
        float[] fArr = {motionEvent.getX(), motionEvent.getY()};
        this.x.mLocation = fArr;
        final int a2 = this.a.a(this.x);
        if (this.c.i != null) {
            this.c.i.onTouchEvent(a2, motionEvent);
        }
        amp amp = this.a.q;
        this.a.d(this.a.C(a2), GLMapStaticValue.e);
        int action = motionEvent.getAction() & 255;
        if (action != 5) {
            switch (action) {
                case 0:
                    akq akq = this.a;
                    int intValue = this.d.intValue();
                    akq.b((String) "requestMapRender: ");
                    if (akq.n(intValue)) {
                        akq.w(intValue);
                    }
                    if (this.a != null) {
                        akq.b("gestureBegin: ".concat(String.valueOf(a2)));
                        if (GLMapStaticValue.g) {
                            StringBuilder sb3 = new StringBuilder("gestureBegin: ");
                            sb3.append(a2);
                            sb3.append(Log.getStackTraceString(new Throwable()));
                            ana.b("navioverlay", sb3.toString());
                        }
                        if (this.s) {
                            aku aku = this.c;
                            aku.k = 0;
                            aku.m = 0;
                            aku.l = 0;
                            aku.n = 0;
                            aku.o = 0;
                            if (motionEvent.getEventTime() - this.E > 200) {
                                this.a.d.clearAnimationsByContent(a2, 29, true);
                            }
                            this.E = motionEvent.getEventTime();
                            this.J = true;
                            this.D = true;
                            this.m = !this.a.d.getSrvViewStateBoolValue(a2, 11);
                            final boolean z5 = this.m;
                            this.a.a(this.a.C(a2), (Runnable) new Runnable() {
                                public final void run() {
                                    akq akq = ald.this.a;
                                    akq.d.clearAllMessages(a2);
                                }
                            });
                        }
                    }
                    this.a.d.setInUserAction(true);
                    this.F = x2;
                    this.G = y2;
                    break;
                case 1:
                    if (this.a != null) {
                        akq.b("gestureEnd: ".concat(String.valueOf(a2)));
                        this.J = true;
                        this.D = false;
                        if (this.m) {
                            this.C.postDelayed(new Runnable() {
                                public final void run() {
                                    ald.this.v.b(a2, 1);
                                }
                            }, 300);
                        }
                    }
                    this.a.d.setInUserAction(false);
                    break;
                case 2:
                    if (motionEvent.getPointerCount() > 1 && this.F == x2 && this.G == y2 && motionEvent.getX(1) == this.H && motionEvent.getY(1) == this.I) {
                        return true;
                    }
                    if (motionEvent.getPointerCount() == 1 && this.F == x2 && this.G == y2) {
                        return true;
                    }
                    break;
            }
        } else {
            int actionIndex = motionEvent.getActionIndex();
            this.H = motionEvent.getX(actionIndex);
            this.I = motionEvent.getY(actionIndex);
        }
        if (amp != null) {
            amp.onUserMapTouchEvent(a2, motionEvent);
        }
        if (this.J) {
            if (this.m) {
                try {
                    z2 = this.c.a(motionEvent);
                } catch (IllegalArgumentException e2) {
                    e2.printStackTrace();
                }
            } else {
                z2 = this.c.e.onTouchEvent(motionEvent);
            }
        }
        if (action == 1) {
            if (this.a != null) {
                amp amp2 = this.a.q;
                if (amp2 != null) {
                    alg alg = new alg();
                    alg.a = this.o;
                    alg.d = this.p;
                    alg.b = 1;
                    alg.c = this.q;
                    amp2.onEngineActionGesture(a2, alg);
                }
            }
            this.m = true;
        }
        if (!z2) {
            StringBuilder sb4 = new StringBuilder("onTouchEvent: mIsUseMapGesture: ");
            sb4.append(this.J);
            sb4.append(", mTouchInMain: ");
            sb4.append(this.m);
            this.a.a(sb4.toString());
        }
        return z2;
    }

    /* access modifiers changed from: 0000 */
    public final boolean a(int i2) {
        boolean z2;
        synchronized (this.y) {
            z2 = i2 == this.d.intValue();
        }
        return z2;
    }
}
