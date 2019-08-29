package defpackage;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import com.autonavi.jni.ae.gmap.GLMapEngine;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.jni.ae.gmap.gesture.EAMapPlatformGestureInfo;

/* renamed from: aku reason: default package */
/* compiled from: GLMapGestureDetector */
public final class aku {
    akq a;
    akv b;
    Context c;
    GestureDetector d;
    GestureDetector e;
    ali f;
    public int g;
    public float h;
    public amj i = null;
    boolean j = false;
    /* access modifiers changed from: 0000 */
    public int k = 0;
    int l = 0;
    /* access modifiers changed from: 0000 */
    public int m = 0;
    int n = 0;
    int o = 0;
    boolean p = false;
    boolean q = true;
    private alk r;
    private alj s;
    private alh t;
    private alm u;

    /* renamed from: aku$a */
    /* compiled from: GLMapGestureDetector */
    class a implements OnDoubleTapListener, OnGestureListener {
        private EAMapPlatformGestureInfo b;

        public final boolean onDoubleTap(MotionEvent motionEvent) {
            return true;
        }

        public final boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return true;
        }

        public final boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return true;
        }

        public final void onLongPress(MotionEvent motionEvent) {
        }

        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return true;
        }

        public final void onShowPress(MotionEvent motionEvent) {
        }

        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            return true;
        }

        private a() {
            this.b = new EAMapPlatformGestureInfo();
        }

        /* synthetic */ a(aku aku, byte b2) {
            this();
        }

        public final boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            this.b.mDeviceId = aku.this.g;
            this.b.mGestureState = 3;
            this.b.mGestureType = 8;
            float[] fArr = {motionEvent.getX(), motionEvent.getY()};
            this.b.mLocation = fArr;
            int a2 = aku.this.a.a(this.b);
            if (aku.this.i != null) {
                aku.this.i.onClick(a2, motionEvent.getX(), motionEvent.getY());
            }
            return aku.this.a.a(a2, motionEvent);
        }
    }

    /* renamed from: aku$b */
    /* compiled from: GLMapGestureDetector */
    class b implements OnDoubleTapListener, OnGestureListener {
        float a;
        float b;
        float c;
        float d;
        float e;
        long f;
        private int h;
        private EAMapPlatformGestureInfo i;

        private static float a(float f2) {
            if (f2 > 0.0f) {
                return 1.0f;
            }
            if (f2 < 0.0f) {
                return -1.0f;
            }
            return f2;
        }

        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
            return false;
        }

        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        private b() {
            this.h = 0;
            this.a = 0.0f;
            this.i = new EAMapPlatformGestureInfo();
            this.b = (float) (Math.log(0.78d) / Math.log(0.9d));
            this.c = 0.35f;
            this.d = 0.015f;
            this.e = 9.80665f;
            this.f = 0;
        }

        /* synthetic */ b(aku aku, byte b2) {
            this();
        }

        public final boolean onDown(MotionEvent motionEvent) {
            aku.this.p = false;
            return true;
        }

        private double a(int i2) {
            float f2 = aku.this.h;
            return Math.log((double) ((this.c * ((float) Math.abs(i2))) / (this.d * (((this.e * 39.37f) * f2) * 0.84f))));
        }

        private double b(int i2) {
            double d2 = (double) this.d;
            return d2 * ((double) (this.e * 39.37f)) * ((double) aku.this.h) * 0.8399999737739563d * Math.exp((((double) this.b) / (((double) this.b) - 1.0d)) * a(i2));
        }

        public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
            this.i.mDeviceId = aku.this.g;
            this.i.mGestureState = 3;
            this.i.mGestureType = 3;
            float[] fArr = {motionEvent2.getX(), motionEvent2.getY()};
            this.i.mLocation = fArr;
            int a2 = aku.a(aku.this, aku.this.a.a(this.i));
            if (aku.this.n <= 0 && aku.this.l <= 0 && aku.this.m == 0 && !aku.this.q) {
                if (-1 == a2) {
                    return false;
                }
                if (aku.this.a.q != null) {
                    aku.this.a.q.onFling(a2, motionEvent, motionEvent2, f2, f3);
                }
            }
            if (aku.this.n <= 0 && aku.this.l <= 0 && aku.this.m == 0 && !aku.this.q) {
                PointF pointF = new PointF();
                pointF.x = f2;
                pointF.y = f3;
                int exp = (int) (Math.exp(a((int) pointF.length()) / (((double) this.b) - 1.0d)) * 1000.0d);
                float b2 = ((float) b((int) pointF.x)) * a(pointF.x);
                float b3 = ((float) b((int) pointF.y)) * a(pointF.y);
                pointF.x = f2 / 1000.0f;
                pointF.y = f3 / 1000.0f;
                int i2 = 800;
                if (exp <= 800) {
                    i2 = exp;
                }
                GLMapEngine gLMapEngine = aku.this.a.d;
                GLMapState newMapState = gLMapEngine.getNewMapState(a2);
                Rect mapViewBound = newMapState.getMapViewBound();
                newMapState.setProjectionCenter(((float) mapViewBound.width()) * 0.5f, ((float) mapViewBound.height()) * 0.5f);
                newMapState.setCameraDegree(0.0f);
                newMapState.recalculate();
                Point point = new Point();
                newMapState.screenToP20Point((((float) mapViewBound.width()) * 0.5f) - b2, (((float) mapViewBound.height()) * 0.5f) - b3, point);
                aku.this.b.a();
                gLMapEngine.startMapSlidAnim(aku.this.a, a2, i2, point);
            }
            return true;
        }

        public final void onLongPress(MotionEvent motionEvent) {
            if (aku.this.o == 1) {
                this.i.mDeviceId = aku.this.g;
                this.i.mGestureState = 3;
                this.i.mGestureType = 7;
                float[] fArr = {motionEvent.getX(), motionEvent.getY()};
                this.i.mLocation = fArr;
                int a2 = aku.a(aku.this, aku.this.a.a(this.i));
                if (-1 != a2) {
                    if (aku.this.i != null) {
                        aku.this.i.onLongPress(a2, motionEvent.getX(), motionEvent.getY());
                    }
                    akq akq = aku.this.a;
                    StringBuilder sb = new StringBuilder("onLongPress: ");
                    sb.append(a2);
                    sb.append(", ");
                    sb.append(motionEvent);
                    akq.b(sb.toString());
                    if (akq.q != null) {
                        akq.v(a2);
                        akq.q.onLongPress(a2, motionEvent);
                        akq.e(akq.d.getBelongToRenderDeviceId(a2), 30);
                    }
                }
            }
        }

        public final void onShowPress(MotionEvent motionEvent) {
            this.i.mDeviceId = aku.this.g;
            this.i.mGestureState = 3;
            this.i.mGestureType = 7;
            float[] fArr = {motionEvent.getX(), motionEvent.getY()};
            this.i.mLocation = fArr;
            if (-1 != aku.a(aku.this, aku.this.a.a(this.i))) {
            }
        }

        public final boolean onDoubleTap(MotionEvent motionEvent) {
            aku.this.d.setIsLongpressEnabled(false);
            this.h = motionEvent.getPointerCount();
            return false;
        }

        public final boolean onDoubleTapEvent(MotionEvent motionEvent) {
            if (this.h < motionEvent.getPointerCount()) {
                this.h = motionEvent.getPointerCount();
            }
            int action = motionEvent.getAction() & 255;
            boolean z = true;
            if (this.h != 1) {
                z = false;
            } else if (action == 0) {
                this.i.mDeviceId = aku.this.g;
                this.i.mGestureState = 1;
                this.i.mGestureType = 1;
                float[] fArr = {motionEvent.getX(), motionEvent.getY()};
                this.i.mLocation = fArr;
                int a2 = aku.a(aku.this, aku.this.a.a(this.i));
                if (-1 == a2) {
                    return false;
                }
                this.a = motionEvent.getY();
                aku.this.a.a(a2, (akw) new alc(100, 1.0f, 0.0f, 0.0f));
                this.f = SystemClock.uptimeMillis();
            } else if (action == 2) {
                aku.this.p = true;
                float y = this.a - motionEvent.getY();
                if (Math.abs(y) >= 20.0f) {
                    this.i.mDeviceId = aku.this.g;
                    this.i.mGestureState = 2;
                    this.i.mGestureType = 9;
                    float[] fArr2 = {motionEvent.getX(), motionEvent.getY()};
                    this.i.mLocation = fArr2;
                    int a3 = aku.a(aku.this, aku.this.a.a(this.i));
                    if (-1 == a3) {
                        return false;
                    }
                    float f2 = (4.0f * y) / ((float) aku.this.a.i);
                    if (y > 0.0f) {
                        aku.this.a.a(a3, (akw) new alc(101, f2, 0.0f, 0.0f));
                    } else {
                        aku.this.a.a(a3, (akw) new alc(101, f2, 0.0f, 0.0f));
                    }
                    this.a = motionEvent.getY();
                }
            } else {
                this.i.mDeviceId = aku.this.g;
                this.i.mGestureState = 3;
                this.i.mGestureType = 1;
                float[] fArr3 = {motionEvent.getX(), motionEvent.getY()};
                this.i.mLocation = fArr3;
                int a4 = aku.a(aku.this, aku.this.a.a(this.i));
                if (-1 == a4) {
                    return false;
                }
                aku.this.d.setIsLongpressEnabled(true);
                aku.this.a.a(a4, (akw) new alc(102, 1.0f, 0.0f, 0.0f));
                if (action == 1) {
                    aku.this.b.a(a4, 3);
                    long uptimeMillis = SystemClock.uptimeMillis() - this.f;
                    if (!aku.this.p || uptimeMillis < 200) {
                        if (aku.this.i != null) {
                            aku.this.i.onDoubleClick(a4, motionEvent.getX(), motionEvent.getY());
                        }
                        aku.this.b.a();
                        aku.this.a.d.clearAnimations(a4, false);
                        akq akq = aku.this.a;
                        StringBuilder sb = new StringBuilder("onDoubleTap: ");
                        sb.append(a4);
                        sb.append(", ");
                        sb.append(motionEvent);
                        akq.b(sb.toString());
                        int x = (int) motionEvent.getX();
                        int y2 = (int) motionEvent.getY();
                        if (akq.q != null) {
                            akq.q.onDoubleTap(a4, motionEvent);
                        }
                        if (akq.z(a4).e) {
                            akq.a(a4, (Point) null);
                        } else {
                            akq.a(a4, akq.d.getGestureCenter(a4, x, y2));
                        }
                        return false;
                    }
                    aku.this.p = false;
                } else {
                    aku.this.p = false;
                }
            }
            return z;
        }

        public final boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            boolean z = false;
            if (aku.this.o == 1) {
                this.i.mDeviceId = aku.this.g;
                this.i.mGestureState = 3;
                this.i.mGestureType = 8;
                float[] fArr = {motionEvent.getX(), motionEvent.getY()};
                this.i.mLocation = fArr;
                int a2 = aku.this.a.a(this.i);
                if (-1 == a2) {
                    return false;
                }
                if (aku.this.i != null) {
                    z = aku.this.i.onClick(a2, motionEvent.getX(), motionEvent.getY());
                }
                if (!z) {
                    aku.this.a.a(a2, motionEvent);
                }
            }
            return z;
        }
    }

    /* renamed from: aku$c */
    /* compiled from: GLMapGestureDetector */
    class c implements defpackage.alh.a {
        private EAMapPlatformGestureInfo b;

        private c() {
            this.b = new EAMapPlatformGestureInfo();
        }

        /* synthetic */ c(aku aku, byte b2) {
            this();
        }

        public final boolean a(alh alh) {
            this.b.mDeviceId = aku.this.g;
            this.b.mGestureState = 2;
            this.b.mGestureType = 6;
            boolean z = false;
            this.b.mLocation = new float[]{alh.c().getX(), alh.c().getY()};
            int a2 = aku.a(aku.this, aku.this.a.a(this.b));
            if (-1 == a2 || aku.this.a.h(a2) || aku.this.m > 3) {
                return false;
            }
            float f = alh.h.x;
            float f2 = alh.h.y;
            if (!aku.this.j) {
                PointF a3 = alh.a(0);
                PointF a4 = alh.a(1);
                boolean z2 = (a3.y > 5.0f && a4.y > 5.0f) || (a3.y < -5.0f && a4.y < -5.0f);
                if (Math.abs(a3.y) / Math.abs(a3.x) >= 3.732f && Math.abs(a4.y) / Math.abs(a4.x) >= 3.732f) {
                    z = z2;
                }
                if (z && Math.abs(f2) > 5.0f && Math.abs(f) < 10.0f) {
                    aku.this.j = true;
                }
            }
            if (aku.this.j) {
                float f3 = f2 / 6.0f;
                if (Math.abs(f3) > 0.0f) {
                    aku.this.a.a(a2, (akw) new akx(101, f3));
                    aku.this.n++;
                    return true;
                }
            }
            return true;
        }

        public final boolean b(alh alh) {
            this.b.mDeviceId = aku.this.g;
            this.b.mGestureState = 1;
            this.b.mGestureType = 6;
            float[] fArr = {alh.c().getX(), alh.c().getY()};
            this.b.mLocation = fArr;
            int a2 = aku.a(aku.this, aku.this.a.a(this.b));
            if (-1 == a2 || aku.this.a.h(a2)) {
                return false;
            }
            if (aku.this.a.q != null) {
                aku.this.a.q.onHoveBegin(a2, alh.c());
            }
            aku.this.a.a(a2, (akw) new akx(100, aku.this.a.l(a2)));
            return true;
        }

        public final void c(alh alh) {
            this.b.mDeviceId = aku.this.g;
            aku.this.j = false;
            this.b.mGestureState = 3;
            this.b.mGestureType = 6;
            float[] fArr = {alh.c().getX(), alh.c().getY()};
            this.b.mLocation = fArr;
            int a2 = aku.a(aku.this, aku.this.a.a(this.b));
            if (-1 != a2 && !aku.this.a.h(a2)) {
                if (aku.this.a.l(a2) >= 0.0f && aku.this.n > 0) {
                    aku.this.b.a(a2, 7);
                }
                aku.this.f.d(alh.c());
                aku.this.a.a(a2, (akw) new akx(102, aku.this.a.l(a2)));
            }
        }
    }

    /* renamed from: aku$d */
    /* compiled from: GLMapGestureDetector */
    class d implements defpackage.ali.a {
        private final float b;
        private PointF[] c;
        private long[] d;
        private EAMapPlatformGestureInfo e;

        private d() {
            this.b = 1.0f;
            this.c = new PointF[5];
            this.d = new long[5];
            this.e = new EAMapPlatformGestureInfo();
        }

        /* synthetic */ d(aku aku, byte b2) {
            this();
        }

        public final boolean a(ali ali) {
            if (aku.this.j) {
                return true;
            }
            this.e.mDeviceId = aku.this.g;
            this.e.mGestureState = 2;
            this.e.mGestureType = 3;
            float[] fArr = {ali.c().getX(), ali.c().getY()};
            this.e.mLocation = fArr;
            int a2 = aku.a(aku.this, aku.this.a.a(this.e));
            if (-1 == a2) {
                return false;
            }
            PointF pointF = ali.h;
            if (Math.abs(pointF.x) <= 1.0f && Math.abs(pointF.y) <= 1.0f) {
                return false;
            }
            if (aku.this.k == 0) {
                aku.this.a.d.clearAnimations(a2, false);
            }
            aku.this.a.a(a2, (akw) new ala(101, pointF.x, pointF.y));
            this.c[aku.this.k % 5] = pointF;
            this.d[aku.this.k % 5] = ali.b();
            aku.this.k = aku.this.k + 1;
            return true;
        }

        public final boolean b(ali ali) {
            this.e.mDeviceId = aku.this.g;
            this.e.mGestureState = 1;
            this.e.mGestureType = 3;
            float[] fArr = {ali.c().getX(), ali.c().getY()};
            this.e.mLocation = fArr;
            int a2 = aku.a(aku.this, aku.this.a.a(this.e));
            if (-1 == a2) {
                return false;
            }
            if (aku.this.a != null) {
                if (aku.this.i != null) {
                    aku.this.i.onMontionStart(a2, ali.c().getX(), ali.c().getY());
                }
                akq akq = aku.this.a;
                MotionEvent c2 = ali.c();
                if (akq.q != null) {
                    akq.q.onMoveBegin(a2, c2);
                }
            }
            aku.this.a.a(a2, (akw) new ala(100, 0.0f, 0.0f));
            return true;
        }

        public final void c(ali ali) {
            this.e.mDeviceId = aku.this.g;
            this.e.mGestureState = 3;
            this.e.mGestureType = 3;
            float[] fArr = {ali.c().getX(), ali.c().getY()};
            this.e.mLocation = fArr;
            int a2 = aku.a(aku.this, aku.this.a.a(this.e));
            if (-1 != a2) {
                if (aku.this.k > 0) {
                    aku.this.b.a(a2, 5);
                }
                aku.this.a.a(a2, (akw) new ala(102, 0.0f, 0.0f));
                this.c[aku.this.k % 5] = ali.h;
                this.d[aku.this.k % 5] = ali.b();
                aku.this.k = aku.this.k + 1;
            }
        }
    }

    /* renamed from: aku$e */
    /* compiled from: GLMapGestureDetector */
    class e extends defpackage.alj.b {
        private boolean b;
        private Point c;
        private float[] d;
        private float e;
        private EAMapPlatformGestureInfo f;

        private e() {
            this.b = false;
            this.c = new Point();
            this.d = new float[2];
            this.e = 0.0f;
            this.f = new EAMapPlatformGestureInfo();
        }

        /* synthetic */ e(aku aku, byte b2) {
            this();
        }

        public final boolean a(alj alj) {
            int i;
            this.f.mDeviceId = aku.this.g;
            this.f.mGestureState = 2;
            this.f.mGestureType = 5;
            boolean z = false;
            float[] fArr = {alj.c().getX(), alj.c().getY()};
            this.f.mLocation = fArr;
            int a2 = aku.a(aku.this, aku.this.a.a(this.f));
            int i2 = -1;
            if (-1 == a2) {
                return false;
            }
            if (!aku.this.a.z(a2).e) {
                i2 = ((int) (alj.c().getX(0) + alj.c().getX(1))) / 2;
                i = ((int) (alj.c().getY(0) + alj.c().getY(1))) / 2;
            } else {
                i = -1;
            }
            float abs = (float) Math.abs(i2 - this.c.x);
            float abs2 = (float) Math.abs(i - this.c.y);
            if (!aku.this.a.j(a2)) {
                float d2 = alj.d();
                if (Math.abs(d2) >= 358.0f) {
                    return false;
                }
                if (!this.b && Math.abs(d2) >= 6.0f) {
                    this.b = true;
                }
                if (this.b && 0.0f < Math.abs(d2) && ((abs <= 4.0f && abs2 <= 4.0f) || Math.abs(d2) >= 2.0f)) {
                    if (aku.this.m != 0 || Math.abs(d2) < 6.0f) {
                        this.e = d2 / ((float) alj.b());
                        this.d[aku.this.m % 2] = Math.abs(this.e);
                        aku.this.m = aku.this.m + 1;
                        aku.this.a.a(a2, (akw) new alb(101, d2, i2, i));
                        aku.this.b.a(a2, 6);
                        aku.this.b.a(64);
                        z = true;
                    } else {
                        aku.this.m = aku.this.m + 1;
                        return true;
                    }
                }
            }
            return z;
        }

        public final boolean b(alj alj) {
            this.f.mDeviceId = aku.this.g;
            this.f.mGestureState = 1;
            this.f.mGestureType = 5;
            float[] fArr = {alj.c().getX(), alj.c().getY()};
            this.f.mLocation = fArr;
            int a2 = aku.a(aku.this, aku.this.a.a(this.f));
            if (-1 == a2) {
                return false;
            }
            this.c.x = ((int) (alj.c().getX(0) + alj.c().getX(1))) / 2;
            this.c.y = ((int) (alj.c().getY(0) + alj.c().getY(1))) / 2;
            this.b = false;
            if (aku.this.a.q != null) {
                aku.this.a.q.onScaleRotateBegin(a2, alj.c());
            }
            if (!aku.this.a.j(a2)) {
                aku.this.a.a(a2, (akw) new alb(100, aku.this.a.k(a2), this.c.x, this.c.y));
            }
            return true;
        }

        /* JADX WARNING: Removed duplicated region for block: B:26:0x00d3  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void c(defpackage.alj r13) {
            /*
                r12 = this;
                com.autonavi.jni.ae.gmap.gesture.EAMapPlatformGestureInfo r0 = r12.f
                aku r1 = defpackage.aku.this
                int r1 = r1.g
                r0.mDeviceId = r1
                com.autonavi.jni.ae.gmap.gesture.EAMapPlatformGestureInfo r0 = r12.f
                r1 = 3
                r0.mGestureState = r1
                com.autonavi.jni.ae.gmap.gesture.EAMapPlatformGestureInfo r0 = r12.f
                r1 = 5
                r0.mGestureType = r1
                com.autonavi.jni.ae.gmap.gesture.EAMapPlatformGestureInfo r0 = r12.f
                r1 = 2
                float[] r2 = new float[r1]
                android.view.MotionEvent r3 = r13.c()
                float r3 = r3.getX()
                r4 = 0
                r2[r4] = r3
                android.view.MotionEvent r13 = r13.c()
                float r13 = r13.getY()
                r3 = 1
                r2[r3] = r13
                r0.mLocation = r2
                aku r13 = defpackage.aku.this
                akq r13 = r13.a
                com.autonavi.jni.ae.gmap.gesture.EAMapPlatformGestureInfo r0 = r12.f
                int r13 = r13.a(r0)
                aku r0 = defpackage.aku.this
                int r7 = defpackage.aku.a(r0, r13)
                r13 = -1
                if (r13 != r7) goto L_0x0043
                return
            L_0x0043:
                aku r13 = defpackage.aku.this
                akq r13 = r13.a
                alc r0 = new alc
                r2 = 1065353216(0x3f800000, float:1.0)
                r3 = 102(0x66, float:1.43E-43)
                r5 = 0
                r0.<init>(r3, r2, r5, r5)
                r13.a(r7, r0)
                aku r13 = defpackage.aku.this
                akq r13 = r13.a
                boolean r13 = r13.j(r7)
                r0 = -971228160(0xffffffffc61c3c00, float:-9999.0)
                if (r13 != 0) goto L_0x00cc
                aku r13 = defpackage.aku.this
                akq r13 = r13.a
                alb r2 = new alb
                aku r6 = defpackage.aku.this
                akq r6 = r6.a
                float r6 = r6.k(r7)
                r2.<init>(r3, r6, r4, r4)
                r13.a(r7, r2)
                aku r13 = defpackage.aku.this
                int r13 = r13.m
                if (r13 <= 0) goto L_0x00cc
                aku r13 = defpackage.aku.this
                akv r13 = r13.b
                r2 = 6
                r13.a(r7, r2)
                aku r13 = defpackage.aku.this
                int r13 = r13.m
                if (r13 <= r1) goto L_0x008b
                r13 = 2
                goto L_0x008f
            L_0x008b:
                aku r13 = defpackage.aku.this
                int r13 = r13.m
            L_0x008f:
                r2 = 0
            L_0x0090:
                if (r4 >= r1) goto L_0x009e
                float[] r3 = r12.d
                r3 = r3[r4]
                float r2 = r2 + r3
                float[] r3 = r12.d
                r3[r4] = r5
                int r4 = r4 + 1
                goto L_0x0090
            L_0x009e:
                float r13 = (float) r13
                float r2 = r2 / r13
                r13 = 1036831949(0x3dcccccd, float:0.1)
                int r13 = (r13 > r2 ? 1 : (r13 == r2 ? 0 : -1))
                if (r13 > 0) goto L_0x00cc
                r13 = 1128792064(0x43480000, float:200.0)
                float r2 = r2 * r13
                aku r13 = defpackage.aku.this
                akq r13 = r13.a
                float r13 = r13.k(r7)
                int r13 = (int) r13
                int r13 = r13 % 360
                r1 = 1114636288(0x42700000, float:60.0)
                int r3 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
                if (r3 < 0) goto L_0x00bd
                goto L_0x00be
            L_0x00bd:
                r1 = r2
            L_0x00be:
                float r2 = r12.e
                int r2 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
                if (r2 >= 0) goto L_0x00c5
                float r1 = -r1
            L_0x00c5:
                float r13 = (float) r13
                float r13 = r13 + r1
                int r13 = (int) r13
                int r13 = r13 % 360
                float r13 = (float) r13
                goto L_0x00cf
            L_0x00cc:
                r13 = -971228160(0xffffffffc61c3c00, float:-9999.0)
            L_0x00cf:
                int r0 = (r13 > r0 ? 1 : (r13 == r0 ? 0 : -1))
                if (r0 == 0) goto L_0x0100
                aku r0 = defpackage.aku.this
                akv r0 = r0.b
                r0.a()
                aku r0 = defpackage.aku.this
                akq r0 = r0.a
                com.autonavi.jni.ae.gmap.GLMapEngine r5 = r0.d
                aku r0 = defpackage.aku.this
                akq r6 = r0.a
                aku r0 = defpackage.aku.this
                akq r0 = r0.a
                com.autonavi.jni.ae.gmap.GLMapEngine r0 = r0.d
                android.graphics.Point r1 = r12.c
                int r1 = r1.x
                android.graphics.Point r2 = r12.c
                int r2 = r2.y
                android.graphics.Point r8 = r0.getGestureCenter(r7, r1, r2)
                r9 = -971228160(0xffffffffc61c3c00, float:-9999.0)
                int r13 = (int) r13
                float r10 = (float) r13
                r11 = 500(0x1f4, float:7.0E-43)
                r5.startPivotZoomRotateAnim(r6, r7, r8, r9, r10, r11)
            L_0x0100:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.aku.e.c(alj):void");
        }
    }

    /* renamed from: aku$f */
    /* compiled from: GLMapGestureDetector */
    class f extends defpackage.alk.b {
        private boolean b;
        private Point c;
        private float[] d;
        private long[] e;
        private float f;
        private long g;
        private EAMapPlatformGestureInfo h;

        private f() {
            this.b = false;
            this.c = new Point();
            this.d = new float[2];
            this.e = new long[2];
            this.f = 0.0f;
            this.g = 0;
            this.h = new EAMapPlatformGestureInfo();
        }

        /* synthetic */ f(aku aku, byte b2) {
            this();
        }

        public final boolean a(alk alk) {
            int i;
            this.h.mDeviceId = aku.this.g;
            this.h.mGestureState = 2;
            this.h.mGestureType = 4;
            boolean z = false;
            float[] fArr = {alk.e.getX(), alk.e.getY()};
            this.h.mLocation = fArr;
            int a2 = aku.a(aku.this, aku.this.a.a(this.h));
            int i2 = -1;
            if (-1 == a2) {
                return false;
            }
            if (!aku.this.a.z(a2).e) {
                i2 = (int) alk.f;
                i = (int) alk.g;
            } else {
                i = -1;
            }
            float abs = (float) Math.abs(i2 - this.c.x);
            float abs2 = (float) Math.abs(i - this.c.y);
            this.c.x = i2;
            this.c.y = i;
            float log = ((float) Math.log((double) alk.b())) - ((float) Math.log((double) this.f));
            if (!this.b && 0.02f < Math.abs(log)) {
                this.b = true;
            }
            if (this.b && 0.01f < Math.abs(log)) {
                if ((abs <= 2.0f && abs2 <= 2.0f) || Math.abs(log) >= 0.02f) {
                    Point gestureCenter = aku.this.a.d.getGestureCenter(a2, i2, i);
                    aku.this.a.a(a2, (akw) new alc(101, log, (float) gestureCenter.x, (float) gestureCenter.y));
                    this.f = alk.b();
                    this.d[aku.this.l % 2] = log;
                    long eventTime = alk.e.getEventTime();
                    this.e[aku.this.l % 2] = eventTime - this.g;
                    this.g = eventTime;
                    aku.this.l++;
                    if (log > 0.0f) {
                        aku.this.b.a(a2, 1);
                        aku.this.b.a(2);
                    } else {
                        aku.this.b.a(a2, 2);
                        aku.this.b.a(4);
                    }
                }
                z = true;
            }
            return z;
        }

        public final boolean b(alk alk) {
            this.h.mDeviceId = aku.this.g;
            this.h.mGestureState = 1;
            this.h.mGestureType = 4;
            float[] fArr = {alk.e.getX(), alk.e.getY()};
            this.h.mLocation = fArr;
            int a2 = aku.a(aku.this, aku.this.a.a(this.h));
            if (-1 == a2) {
                return false;
            }
            int i = (int) alk.f;
            int i2 = (int) alk.g;
            this.c.x = i;
            this.c.y = i2;
            this.b = false;
            this.f = alk.b();
            this.g = alk.e.getEventTime();
            if (aku.this.a.q != null) {
                aku.this.a.q.onScaleRotateBegin(a2, alk.e);
            }
            aku.this.a.a(a2, (akw) new alc(100, 1.0f, (float) i, (float) i2));
            return true;
        }

        /* JADX WARNING: Removed duplicated region for block: B:19:0x00ee  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void c(defpackage.alk r15) {
            /*
                r14 = this;
                com.autonavi.jni.ae.gmap.gesture.EAMapPlatformGestureInfo r0 = r14.h
                aku r1 = defpackage.aku.this
                int r1 = r1.g
                r0.mDeviceId = r1
                com.autonavi.jni.ae.gmap.gesture.EAMapPlatformGestureInfo r0 = r14.h
                r1 = 3
                r0.mGestureState = r1
                com.autonavi.jni.ae.gmap.gesture.EAMapPlatformGestureInfo r0 = r14.h
                r1 = 4
                r0.mGestureType = r1
                com.autonavi.jni.ae.gmap.gesture.EAMapPlatformGestureInfo r0 = r14.h
                r1 = 2
                float[] r2 = new float[r1]
                android.view.MotionEvent r3 = r15.e
                float r3 = r3.getX()
                r4 = 0
                r2[r4] = r3
                android.view.MotionEvent r3 = r15.e
                float r3 = r3.getY()
                r5 = 1
                r2[r5] = r3
                r0.mLocation = r2
                aku r0 = defpackage.aku.this
                akq r0 = r0.a
                com.autonavi.jni.ae.gmap.gesture.EAMapPlatformGestureInfo r2 = r14.h
                int r0 = r0.a(r2)
                aku r2 = defpackage.aku.this
                int r7 = defpackage.aku.a(r2, r0)
                r0 = -1
                if (r0 != r7) goto L_0x003f
                return
            L_0x003f:
                aku r0 = defpackage.aku.this
                akq r0 = r0.a
                alc r2 = new alc
                r3 = 102(0x66, float:1.43E-43)
                r5 = 1065353216(0x3f800000, float:1.0)
                r6 = 0
                r2.<init>(r3, r5, r6, r6)
                r0.a(r7, r2)
                aku r0 = defpackage.aku.this
                int r0 = r0.l
                r2 = -971228160(0xffffffffc61c3c00, float:-9999.0)
                if (r0 <= 0) goto L_0x00e7
                float r0 = r15.b()
                double r8 = (double) r0
                double r8 = java.lang.Math.log(r8)
                float r0 = (float) r8
                float r3 = r14.f
                double r8 = (double) r3
                double r8 = java.lang.Math.log(r8)
                float r3 = (float) r8
                float r0 = r0 - r3
                float[] r3 = r14.d
                aku r5 = defpackage.aku.this
                int r5 = r5.l
                int r5 = r5 % r1
                r3[r5] = r0
                long[] r0 = r14.e
                aku r3 = defpackage.aku.this
                int r3 = r3.l
                int r3 = r3 % r1
                android.view.MotionEvent r15 = r15.e
                long r8 = r15.getEventTime()
                long r10 = r14.g
                long r8 = r8 - r10
                r0[r3] = r8
                r8 = 0
                r10 = r8
                r15 = 0
            L_0x008b:
                if (r4 >= r1) goto L_0x00a2
                float[] r0 = r14.d
                r0 = r0[r4]
                float r15 = r15 + r0
                long[] r0 = r14.e
                r12 = r0[r4]
                long r10 = r10 + r12
                float[] r0 = r14.d
                r0[r4] = r6
                long[] r0 = r14.e
                r0[r4] = r8
                int r4 = r4 + 1
                goto L_0x008b
            L_0x00a2:
                float r0 = (float) r10
                float r15 = r15 / r0
                r0 = 990057071(0x3b03126f, float:0.002)
                float r1 = java.lang.Math.abs(r15)
                int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
                if (r0 > 0) goto L_0x00e7
                aku r0 = defpackage.aku.this
                akq r0 = r0.a
                int r0 = r0.c(r7)
                aku r1 = defpackage.aku.this
                akq r1 = r1.a
                int r1 = r1.b(r7)
                r3 = 1133903872(0x43960000, float:300.0)
                float r15 = r15 * r3
                r3 = 1075838976(0x40200000, float:2.5)
                float r15 = java.lang.Math.min(r15, r3)
                r3 = -1071644672(0xffffffffc0200000, float:-2.5)
                float r15 = java.lang.Math.max(r15, r3)
                aku r3 = defpackage.aku.this
                akq r3 = r3.a
                float r3 = r3.e(r7)
                float r3 = r3 + r15
                float r15 = (float) r1
                int r1 = (r3 > r15 ? 1 : (r3 == r15 ? 0 : -1))
                if (r1 <= 0) goto L_0x00df
            L_0x00dd:
                r9 = r15
                goto L_0x00ea
            L_0x00df:
                float r15 = (float) r0
                int r0 = (r3 > r15 ? 1 : (r3 == r15 ? 0 : -1))
                if (r0 >= 0) goto L_0x00e5
                goto L_0x00dd
            L_0x00e5:
                r9 = r3
                goto L_0x00ea
            L_0x00e7:
                r9 = -971228160(0xffffffffc61c3c00, float:-9999.0)
            L_0x00ea:
                int r15 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
                if (r15 == 0) goto L_0x0119
                aku r15 = defpackage.aku.this
                akv r15 = r15.b
                r15.a()
                aku r15 = defpackage.aku.this
                akq r15 = r15.a
                com.autonavi.jni.ae.gmap.GLMapEngine r5 = r15.d
                aku r15 = defpackage.aku.this
                akq r6 = r15.a
                aku r15 = defpackage.aku.this
                akq r15 = r15.a
                com.autonavi.jni.ae.gmap.GLMapEngine r15 = r15.d
                android.graphics.Point r0 = r14.c
                int r0 = r0.x
                android.graphics.Point r1 = r14.c
                int r1 = r1.y
                android.graphics.Point r8 = r15.getGestureCenter(r7, r0, r1)
                r10 = -971228160(0xffffffffc61c3c00, float:-9999.0)
                r11 = 500(0x1f4, float:7.0E-43)
                r5.startPivotZoomRotateAnim(r6, r7, r8, r9, r10, r11)
            L_0x0119:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.aku.f.c(alk):void");
        }
    }

    /* renamed from: aku$g */
    /* compiled from: GLMapGestureDetector */
    class g extends defpackage.alm.b {
        EAMapPlatformGestureInfo a;

        private g() {
            this.a = new EAMapPlatformGestureInfo();
        }

        /* synthetic */ g(aku aku, byte b2) {
            this();
        }

        public final void a(alm alm) {
            if (Math.abs(alm.h.x) <= 3.0f && Math.abs(alm.h.y) <= 3.0f) {
                aku.this.q = true;
                this.a.mDeviceId = aku.this.g;
                this.a.mGestureState = 2;
                this.a.mGestureType = 2;
                float[] fArr = {alm.c().getX(), alm.c().getY()};
                this.a.mLocation = fArr;
                int a2 = aku.a(aku.this, aku.this.a.a(this.a));
                if (-1 != a2) {
                    if (aku.this.a.q != null) {
                        aku.this.a.q.onZoomOutTap(a2, alm.c());
                    }
                    aku.this.b.a(a2, 4);
                    aku.this.b.a();
                    aku.this.a.d.clearAnimations(a2, false);
                    aku.this.a.f(a2);
                }
            }
        }
    }

    public aku(akq akq, akv akv) {
        this.c = akq.c;
        this.a = akq;
        this.b = akv;
        b bVar = new b(this, 0);
        a aVar = new a(this, 0);
        this.d = new GestureDetector(this.c, bVar);
        this.d.setOnDoubleTapListener(bVar);
        this.e = new GestureDetector(this.c, aVar);
        this.e.setOnDoubleTapListener(aVar);
        this.f = new ali(this.c, new d(this, 0));
        this.r = new alk(this.c, new f(this, 0));
        this.s = new alj(this.c, new e(this, 0));
        this.t = new alh(this.c, new c(this, 0));
        this.u = new alm(this.c, new g(this, 0));
    }

    /* JADX WARNING: Removed duplicated region for block: B:123:0x023a A[Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x023b A[Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x0240 A[Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x0259 A[Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x0272 A[ADDED_TO_REGION, Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x027c A[Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:141:0x028a A[Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x01b0 A[Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x01b1 A[Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x01c2 A[Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x01d2 A[Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(android.view.MotionEvent r15) {
        /*
            r14 = this;
            int r0 = r14.o
            int r1 = r15.getPointerCount()
            if (r0 >= r1) goto L_0x000e
            int r0 = r15.getPointerCount()
            r14.o = r0
        L_0x000e:
            int r0 = r15.getAction()
            r0 = r0 & 255(0xff, float:3.57E-43)
            r1 = 0
            if (r0 != 0) goto L_0x0019
            r14.q = r1
        L_0x0019:
            boolean r0 = r14.p
            r2 = 2
            if (r0 == 0) goto L_0x0024
            int r0 = r14.o
            if (r0 < r2) goto L_0x0024
            r14.p = r1
        L_0x0024:
            android.view.GestureDetector r0 = r14.d     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.onTouchEvent(r15)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            alh r0 = r14.t     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.a(r15)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            boolean r0 = r14.j     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r3 = 1
            if (r0 == 0) goto L_0x0037
            int r0 = r14.n     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r0 > 0) goto L_0x03f4
        L_0x0037:
            alm r0 = r14.u     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.a(r15)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            boolean r0 = r14.p     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r0 != 0) goto L_0x03f4
            ali r0 = r14.f     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.a(r15)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            alk r0 = r14.r     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r4 = r15.getAction()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r4 = r4 & 255(0xff, float:3.57E-43)
            if (r4 != 0) goto L_0x0052
            r0.a()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
        L_0x0052:
            boolean r5 = r0.r     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r5 != 0) goto L_0x03ef
            boolean r5 = r0.c     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r6 = -1
            r7 = 8
            if (r5 != 0) goto L_0x02b5
            r5 = -1082130432(0xffffffffbf800000, float:-1.0)
            switch(r4) {
                case 0: goto L_0x02ab;
                case 1: goto L_0x02a6;
                case 2: goto L_0x01f0;
                case 3: goto L_0x0062;
                case 4: goto L_0x0062;
                case 5: goto L_0x00d8;
                case 6: goto L_0x0064;
                default: goto L_0x0062;
            }     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
        L_0x0062:
            goto L_0x03ef
        L_0x0064:
            boolean r4 = r0.q     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r4 == 0) goto L_0x03ef
            int r4 = r15.getPointerCount()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r5 = android.os.Build.VERSION.SDK_INT     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r5 < r7) goto L_0x0075
            int r5 = r15.getActionIndex()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x0076
        L_0x0075:
            r5 = 0
        L_0x0076:
            int r7 = r15.getPointerId(r5)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r4 <= r2) goto L_0x00a4
            int r2 = r0.s     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r7 != r2) goto L_0x0090
            int r2 = r0.t     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r2 = r0.a(r15, r2, r5)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r2 < 0) goto L_0x03ef
            int r2 = r15.getPointerId(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.s = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x03ef
        L_0x0090:
            int r2 = r0.t     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r7 != r2) goto L_0x03ef
            int r2 = r0.s     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r2 = r0.a(r15, r2, r5)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r2 < 0) goto L_0x03ef
            int r2 = r15.getPointerId(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.t = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x03ef
        L_0x00a4:
            int r2 = r0.s     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r7 != r2) goto L_0x00ab
            int r2 = r0.t     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x00ad
        L_0x00ab:
            int r2 = r0.s     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
        L_0x00ad:
            int r2 = r15.findPointerIndex(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r2 >= 0) goto L_0x00c0
            r0.r = r3     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            boolean r2 = r0.c     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r2 == 0) goto L_0x03ef
            alk$a r2 = r0.b     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r2.c(r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x03ef
        L_0x00c0:
            int r4 = r15.getPointerId(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.s = r4     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.u = r3     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.t = r6     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r4 = r15.getX(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.f = r4     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r2 = r15.getY(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.g = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x03ef
        L_0x00d8:
            android.content.Context r2 = r0.a     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            android.content.res.Resources r2 = r2.getResources()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            android.util.DisplayMetrics r2 = r2.getDisplayMetrics()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r4 = r2.widthPixels     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r4 = (float) r4     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r8 = r0.n     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r4 = r4 - r8
            r0.o = r4     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r2 = r2.heightPixels     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r2 = (float) r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r4 = r0.n     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r2 = r2 - r4
            r0.p = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            android.view.MotionEvent r2 = r0.d     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r2 == 0) goto L_0x00fb
            android.view.MotionEvent r2 = r0.d     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r2.recycle()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
        L_0x00fb:
            android.view.MotionEvent r2 = android.view.MotionEvent.obtain(r15)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.d = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r8 = 0
            r0.m = r8     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r2 < r7) goto L_0x012d
            int r2 = r15.getActionIndex()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r4 = r0.s     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r4 = r15.findPointerIndex(r4)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r7 = r15.getPointerId(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.t = r7     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r4 < 0) goto L_0x011d
            if (r4 != r2) goto L_0x0146
        L_0x011d:
            if (r4 != r2) goto L_0x0120
            goto L_0x0122
        L_0x0120:
            int r6 = r0.t     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
        L_0x0122:
            int r4 = r0.a(r15, r6, r4)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r6 = r15.getPointerId(r4)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.s = r6     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x0146
        L_0x012d:
            int r2 = r15.getPointerCount()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r2 <= 0) goto L_0x0144
            int r2 = r15.findPointerIndex(r3)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r4 = r0.s     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r4 = r15.findPointerIndex(r4)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r6 = r15.getPointerId(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.t = r6     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x0146
        L_0x0144:
            r2 = 0
            r4 = 0
        L_0x0146:
            r0.u = r1     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r6 = r15.getX(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r7 = r15.getX(r4)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r6 = r6 - r7
            r0.h = r6     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r6 = r15.getY(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r7 = r15.getY(r4)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r6 = r6 - r7
            r0.i = r6     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r6 = r0.h     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r7 = r0.h     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r6 = r6 * r7
            float r7 = r0.i     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r8 = r0.i     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r7 = r7 * r8
            float r6 = r6 + r7
            double r6 = (double) r6     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            double r6 = java.lang.Math.sqrt(r6)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r6 = (float) r6     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.j = r6     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.a(r15)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r6 = r0.n     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r7 = r0.o     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r8 = r0.p     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r9 = defpackage.alk.a(r15, r4)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r10 = defpackage.alk.b(r15, r4)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r11 = defpackage.alk.a(r15, r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r12 = defpackage.alk.b(r15, r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r13 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
            if (r13 < 0) goto L_0x019f
            int r13 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r13 < 0) goto L_0x019f
            int r9 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r9 > 0) goto L_0x019f
            int r9 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r9 <= 0) goto L_0x019d
            goto L_0x019f
        L_0x019d:
            r9 = 0
            goto L_0x01a0
        L_0x019f:
            r9 = 1
        L_0x01a0:
            int r10 = (r11 > r6 ? 1 : (r11 == r6 ? 0 : -1))
            if (r10 < 0) goto L_0x01b3
            int r6 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1))
            if (r6 < 0) goto L_0x01b3
            int r6 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
            if (r6 > 0) goto L_0x01b3
            int r6 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1))
            if (r6 <= 0) goto L_0x01b1
            goto L_0x01b3
        L_0x01b1:
            r6 = 0
            goto L_0x01b4
        L_0x01b3:
            r6 = 1
        L_0x01b4:
            if (r9 == 0) goto L_0x01c0
            if (r6 == 0) goto L_0x01c0
            r0.f = r5     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.g = r5     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.q = r3     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x03ef
        L_0x01c0:
            if (r9 == 0) goto L_0x01d2
            float r4 = r15.getX(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.f = r4     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r2 = r15.getY(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.g = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.q = r3     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x03ef
        L_0x01d2:
            if (r6 == 0) goto L_0x01e4
            float r2 = r15.getX(r4)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.f = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r2 = r15.getY(r4)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.g = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.q = r3     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x03ef
        L_0x01e4:
            r0.q = r1     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            alk$a r2 = r0.b     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            boolean r2 = r2.b(r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.c = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x03ef
        L_0x01f0:
            boolean r2 = r0.q     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r2 == 0) goto L_0x03ef
            float r2 = r0.n     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r4 = r0.o     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r6 = r0.p     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r7 = r0.s     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r7 = r15.findPointerIndex(r7)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r8 = r0.t     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r8 = r15.findPointerIndex(r8)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r9 = defpackage.alk.a(r15, r7)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r10 = defpackage.alk.b(r15, r7)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r11 = defpackage.alk.a(r15, r8)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r12 = defpackage.alk.b(r15, r8)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r13 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r13 < 0) goto L_0x0229
            int r13 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r13 < 0) goto L_0x0229
            int r9 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r9 > 0) goto L_0x0229
            int r9 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r9 <= 0) goto L_0x0227
            goto L_0x0229
        L_0x0227:
            r9 = 0
            goto L_0x022a
        L_0x0229:
            r9 = 1
        L_0x022a:
            int r10 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            if (r10 < 0) goto L_0x023d
            int r2 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r2 < 0) goto L_0x023d
            int r2 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r2 > 0) goto L_0x023d
            int r2 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1))
            if (r2 <= 0) goto L_0x023b
            goto L_0x023d
        L_0x023b:
            r2 = 0
            goto L_0x023e
        L_0x023d:
            r2 = 1
        L_0x023e:
            if (r9 == 0) goto L_0x0256
            int r4 = r0.t     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r4 = r0.a(r15, r4, r7)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r4 < 0) goto L_0x0256
            int r6 = r15.getPointerId(r4)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.s = r6     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            defpackage.alk.a(r15, r4)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            defpackage.alk.b(r15, r4)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r9 = 0
            goto L_0x0257
        L_0x0256:
            r4 = r7
        L_0x0257:
            if (r2 == 0) goto L_0x026f
            int r6 = r0.s     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r6 = r0.a(r15, r6, r8)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r6 < 0) goto L_0x026f
            int r2 = r15.getPointerId(r6)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.t = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            defpackage.alk.a(r15, r6)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            defpackage.alk.b(r15, r6)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r2 = 0
            goto L_0x0270
        L_0x026f:
            r6 = r8
        L_0x0270:
            if (r9 == 0) goto L_0x027a
            if (r2 == 0) goto L_0x027a
            r0.f = r5     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.g = r5     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x03ef
        L_0x027a:
            if (r9 == 0) goto L_0x028a
            float r2 = r15.getX(r6)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.f = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r2 = r15.getY(r6)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.g = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x03ef
        L_0x028a:
            if (r2 == 0) goto L_0x029a
            float r2 = r15.getX(r4)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.f = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r2 = r15.getY(r4)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.g = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x03ef
        L_0x029a:
            r0.q = r1     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            alk$a r2 = r0.b     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            boolean r2 = r2.b(r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.c = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x03ef
        L_0x02a6:
            r0.a()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x03ef
        L_0x02ab:
            int r2 = r15.getPointerId(r1)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.s = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.u = r3     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x03ef
        L_0x02b5:
            switch(r4) {
                case 1: goto L_0x03ec;
                case 2: goto L_0x03c9;
                case 3: goto L_0x03c0;
                case 4: goto L_0x02b8;
                case 5: goto L_0x0362;
                case 6: goto L_0x02ba;
                default: goto L_0x02b8;
            }     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
        L_0x02b8:
            goto L_0x03ef
        L_0x02ba:
            int r4 = r15.getPointerCount()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r5 = android.os.Build.VERSION.SDK_INT     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r5 < r7) goto L_0x02c7
            int r5 = r15.getActionIndex()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x02c8
        L_0x02c7:
            r5 = 0
        L_0x02c8:
            int r6 = r15.getPointerId(r5)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r4 <= r2) goto L_0x0335
            int r2 = r0.s     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r6 != r2) goto L_0x02fb
            int r2 = r0.t     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r2 = r0.a(r15, r2, r5)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r2 < 0) goto L_0x02f9
            alk$a r4 = r0.b     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r4.c(r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r2 = r15.getPointerId(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.s = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.u = r3     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            android.view.MotionEvent r2 = android.view.MotionEvent.obtain(r15)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.d = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.a(r15)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            alk$a r2 = r0.b     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            boolean r2 = r2.b(r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.c = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x0325
        L_0x02f9:
            r2 = 1
            goto L_0x0326
        L_0x02fb:
            int r2 = r0.t     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r6 != r2) goto L_0x0325
            int r2 = r0.s     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r2 = r0.a(r15, r2, r5)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r2 < 0) goto L_0x02f9
            alk$a r4 = r0.b     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r4.c(r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r2 = r15.getPointerId(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.t = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.u = r1     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            android.view.MotionEvent r2 = android.view.MotionEvent.obtain(r15)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.d = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.a(r15)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            alk$a r2 = r0.b     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            boolean r2 = r2.b(r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.c = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
        L_0x0325:
            r2 = 0
        L_0x0326:
            android.view.MotionEvent r4 = r0.d     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r4.recycle()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            android.view.MotionEvent r4 = android.view.MotionEvent.obtain(r15)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.d = r4     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.a(r15)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x0336
        L_0x0335:
            r2 = 1
        L_0x0336:
            if (r2 == 0) goto L_0x03ef
            r0.a(r15)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r2 = r0.s     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r6 != r2) goto L_0x0342
            int r2 = r0.t     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x0344
        L_0x0342:
            int r2 = r0.s     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
        L_0x0344:
            int r4 = r15.findPointerIndex(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r5 = r15.getX(r4)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.f = r5     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r4 = r15.getY(r4)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.g = r4     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            alk$a r4 = r0.b     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r4.c(r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.a()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.s = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.u = r3     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x03ef
        L_0x0362:
            alk$a r2 = r0.b     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r2.c(r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r2 = r0.s     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r4 = r0.t     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.a()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            android.view.MotionEvent r5 = android.view.MotionEvent.obtain(r15)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.d = r5     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            boolean r5 = r0.u     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r5 == 0) goto L_0x0379
            goto L_0x037a
        L_0x0379:
            r2 = r4
        L_0x037a:
            r0.s = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r2 < r7) goto L_0x038b
            int r2 = r15.getActionIndex()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r2 = r15.getPointerId(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.t = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x0391
        L_0x038b:
            int r2 = r15.getPointerId(r3)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.t = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
        L_0x0391:
            r0.u = r1     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r2 = r0.s     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r2 = r15.findPointerIndex(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r2 < 0) goto L_0x03a1
            int r4 = r0.s     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r5 = r0.t     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r4 != r5) goto L_0x03b4
        L_0x03a1:
            int r4 = r0.s     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r5 = r0.t     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r4 != r5) goto L_0x03a8
            goto L_0x03aa
        L_0x03a8:
            int r6 = r0.t     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
        L_0x03aa:
            int r2 = r0.a(r15, r6, r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            int r2 = r15.getPointerId(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.s = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
        L_0x03b4:
            r0.a(r15)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            alk$a r2 = r0.b     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            boolean r2 = r2.b(r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.c = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x03ef
        L_0x03c0:
            alk$a r2 = r0.b     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r2.c(r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.a()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x03ef
        L_0x03c9:
            r0.a(r15)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r2 = r0.k     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r4 = r0.l     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            float r2 = r2 / r4
            r4 = 1059816735(0x3f2b851f, float:0.67)
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 <= 0) goto L_0x03ef
            alk$a r2 = r0.b     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            boolean r2 = r2.a(r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            if (r2 == 0) goto L_0x03ef
            android.view.MotionEvent r2 = r0.d     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r2.recycle()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            android.view.MotionEvent r2 = android.view.MotionEvent.obtain(r15)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.d = r2     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            goto L_0x03ef
        L_0x03ec:
            r0.a()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
        L_0x03ef:
            alj r0 = r14.s     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
            r0.a(r15)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03fa, NullPointerException -> 0x03f5 }
        L_0x03f4:
            return r3
        L_0x03f5:
            r15 = move-exception
            r15.printStackTrace()
            return r1
        L_0x03fa:
            r15 = move-exception
            r15.printStackTrace()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aku.a(android.view.MotionEvent):boolean");
    }

    static /* synthetic */ int a(aku aku, int i2) {
        GLMapEngine gLMapEngine = aku.a.d;
        if (!gLMapEngine.getSrvViewStateBoolValue(i2, 11) || !aku.b.b()) {
            return i2;
        }
        int[] engineIDs = gLMapEngine.getEngineIDs(aku.g);
        if (engineIDs == null) {
            return i2;
        }
        for (int i3 : engineIDs) {
            if (i3 != i2 && !gLMapEngine.getSrvViewStateBoolValue(i3, 11)) {
                return i3;
            }
        }
        return i2;
    }
}
