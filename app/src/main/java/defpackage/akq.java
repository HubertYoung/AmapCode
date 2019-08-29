package defpackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.opengl.GLException;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.ae.gmap.utils.GLMapStaticValue;
import com.autonavi.jni.ae.gmap.GLMapEngine;
import com.autonavi.jni.ae.gmap.gesture.EAMapPlatformGestureInfo;
import com.autonavi.jni.ae.gmap.glinterface.GLDeviceAttribute;
import com.autonavi.jni.ae.gmap.glinterface.GLSurfaceAttribute;
import com.autonavi.jni.ae.gmap.glinterface.MapEngineInitParam;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/* renamed from: akq reason: default package */
/* compiled from: AMapController */
public class akq implements defpackage.amt.a {
    public static boolean A = false;
    public static akq B = null;
    public static int E = 1;
    public static int F = 2;
    public static int G = 3;
    public static int H = 4;
    private static alq P = null;
    public static String v = "style_1";
    public static boolean w = false;
    public static boolean x = false;
    public static boolean y = false;
    public static String z = "MapFuncCall";
    public ArrayList<als> C = null;
    protected String D = null;
    public boolean I = false;
    protected long J = 0;
    public Object K = null;
    /* access modifiers changed from: private */
    public String L = "AMapController";
    /* access modifiers changed from: private */
    public d M = null;
    private a N = new a();
    private long[] O = new long[16];
    private final int Q = 3;
    private long R = 0;
    private long S = 0;
    private int T = 0;
    private int U = 0;
    private float[] V = new float[3];
    private float[] W = new float[3];
    private int X = 0;
    private int Y = 0;
    private int Z = 0;
    public String a = "";
    private int aa = 0;
    private int ab = GLMapStaticValue.o;
    private ArrayList<Bitmap> ac = new ArrayList<>();
    private ArrayList<akt> ad = new ArrayList<>();
    private ArrayList<aks> ae = new ArrayList<>();
    public amt b = null;
    public Context c;
    public GLMapEngine d = null;
    public Integer e = Integer.valueOf(0);
    public final Object f = new Object();
    public alr g = new alr();
    public int h = 0;
    public int i = 0;
    public b j = null;
    public Rect k;
    public Integer l = Integer.valueOf(0);
    public HashMap<Integer, Bitmap> m = new HashMap<>();
    public HashMap<Integer, Bitmap> n = new HashMap<>();
    protected String o = "http://mps.amap.com";
    protected boolean p = false;
    amp q = new amp();
    public amq r = new amq();
    /* access modifiers changed from: 0000 */
    public amr s = new amr();
    public ams t = new ams();
    public Handler u = new Handler(Looper.getMainLooper());

    /* renamed from: akq$a */
    /* compiled from: AMapController */
    class a implements ale {
        public a() {
        }

        public final void a(int i) {
            akq.b("onClearException: ".concat(String.valueOf(i)));
            if (akq.this.q != null) {
                akq.this.q.onMapTipClear(i);
            }
        }

        public final void a(final int i, int i2) {
            StringBuilder sb = new StringBuilder("onException: ");
            sb.append(i);
            sb.append(", ");
            sb.append(i2);
            akq.b(sb.toString());
            if (i2 != 1004) {
                Point mapCenter = akq.this.d.getMapCenter(i);
                int i3 = mapCenter.x;
                int i4 = mapCenter.y;
                float mapZoomer = akq.this.d.getMapZoomer(i);
                akt z = akq.this.z(i);
                if (z.h != i3 || z.i != i4 || z.j != mapZoomer) {
                    z.getClass();
                    if (System.currentTimeMillis() - z.k >= 5000) {
                        z.k = System.currentTimeMillis();
                        z.h = i3;
                        z.i = i4;
                        z.j = mapZoomer;
                        final String str = null;
                        if (i2 != 1001) {
                            switch (i2) {
                                case 1006:
                                    str = "请检查网络后重试";
                                    break;
                                case 1007:
                                    str = "室内地图加载失败";
                                    break;
                            }
                        } else {
                            str = "请检查网络后重试";
                        }
                        StringBuilder sb2 = new StringBuilder("getErrorInfo: ");
                        sb2.append(i2);
                        sb2.append(", ");
                        sb2.append(str);
                        akq.b(sb2.toString());
                        if (akq.this.q != null && !TextUtils.isEmpty(str)) {
                            akq.this.u.post(new Runnable() {
                                public final void run() {
                                    akq.this.q.onMapTipInfo(i, str);
                                }
                            });
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            akq.this.p(akq.this.d.getBelongToRenderDeviceId(i));
        }

        public final void a(Runnable runnable) {
            akq.b("postOnUIThread: ".concat(String.valueOf(runnable)));
            akq.this.u.post(runnable);
        }

        public final void a(int i, boolean z) {
            StringBuilder sb = new StringBuilder("onChangeMapLogo: ");
            sb.append(i);
            sb.append(", ");
            sb.append(z);
            akq.b(sb.toString());
            if (akq.this.t != null) {
                if (z) {
                    akq.this.t.setScaleColor(i, -1, -16777216);
                    return;
                }
                akq.this.t.setScaleColor(i, -16777216, -1);
            }
        }

        public final Context a() {
            return akq.this.c.getApplicationContext();
        }

        public final String b() {
            StringBuilder sb = new StringBuilder("getUserAgent: ");
            sb.append(akq.this.a);
            akq.b(sb.toString());
            return akq.this.a;
        }

        public final void a(int i, int i2, int i3, int i4) {
            StringBuilder sb = new StringBuilder("onEGLSurfaceChanged: ");
            sb.append(i);
            sb.append(", ");
            sb.append(i2);
            sb.append(", ");
            sb.append(i3);
            akq.b(sb.toString());
            akq.this.h = i2;
            akq.this.i = i3;
            akq.this.s.onSurfaceChanged(i, i2, i3, i4);
        }

        public final void b(int i, int i2) {
            akq.this.s.onSurfaceRenderFrame(i, i2);
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(8:1|2|3|4|5|6|7|8) */
        /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x0028 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final synchronized void b(int r4) {
            /*
                r3 = this;
                monitor-enter(r3)
                java.lang.String r0 = "onRenderDeviceCreated: "
                java.lang.String r1 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x0033 }
                java.lang.String r0 = r0.concat(r1)     // Catch:{ all -> 0x0033 }
                defpackage.akq.b(r0)     // Catch:{ all -> 0x0033 }
                javax.microedition.khronos.egl.EGL r0 = javax.microedition.khronos.egl.EGLContext.getEGL()     // Catch:{ Exception -> 0x0028 }
                javax.microedition.khronos.egl.EGL10 r0 = (javax.microedition.khronos.egl.EGL10) r0     // Catch:{ Exception -> 0x0028 }
                javax.microedition.khronos.egl.EGLContext r0 = r0.eglGetCurrentContext()     // Catch:{ Exception -> 0x0028 }
                javax.microedition.khronos.opengles.GL r0 = r0.getGL()     // Catch:{ Exception -> 0x0028 }
                javax.microedition.khronos.opengles.GL10 r0 = (javax.microedition.khronos.opengles.GL10) r0     // Catch:{ Exception -> 0x0028 }
                akq r1 = defpackage.akq.this     // Catch:{ Exception -> 0x0028 }
                r2 = 7937(0x1f01, float:1.1122E-41)
                java.lang.String r0 = r0.glGetString(r2)     // Catch:{ Exception -> 0x0028 }
                r1.D = r0     // Catch:{ Exception -> 0x0028 }
            L_0x0028:
                akq r0 = defpackage.akq.this     // Catch:{ all -> 0x0033 }
                amr r0 = r0.s     // Catch:{ all -> 0x0033 }
                r0.onRenderDeviceCreated(r4)     // Catch:{ all -> 0x0033 }
                monitor-exit(r3)
                return
            L_0x0033:
                r4 = move-exception
                monitor-exit(r3)
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.akq.a.b(int):void");
        }

        public final void c(int i) {
            akq.b("onRenderDeviceDestroyed: ".concat(String.valueOf(i)));
            if (i == 0) {
                try {
                    akq.this.u.removeCallbacksAndMessages(null);
                } catch (Exception unused) {
                } catch (Throwable th) {
                    throw th;
                }
            }
            int[] engineIDs = akq.this.d.getEngineIDs(i);
            if (engineIDs != null) {
                for (int i2 = 0; i2 < engineIDs.length; i2++) {
                    akq.this.L;
                    new StringBuilder("onRenderDeviceDestroyed engine id: ").append(engineIDs[i2]);
                    ana.a();
                    akq akq = akq.this;
                    int i3 = engineIDs[i2];
                    akq.b("destroyMapEngine: ".concat(String.valueOf(i3)));
                    akq.d.removeEngine(i3);
                    akq.A(i3);
                    akq.p(akq.d.getBelongToRenderDeviceId(i3));
                }
            }
            akq.this.E(i);
            akq.this.s.onRenderDeviceDestroyed(i);
        }

        public final void d(int i) throws OutOfMemoryError {
            Integer valueOf = Integer.valueOf(i);
            Bitmap bitmap = (Bitmap) akq.this.m.get(valueOf);
            Bitmap bitmap2 = (Bitmap) akq.this.n.get(valueOf);
            akq.this.m.remove(valueOf);
            akq.this.n.remove(valueOf);
            akq.b((String) "OnCreateBitmapFromGLSurface: ");
            int i2 = akq.this.k.left;
            int i3 = akq.this.k.bottom;
            int i4 = akq.this.k.right;
            int i5 = (((int) (((float) akq.this.k.top) * 1.0f)) / 4) * 4;
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setFilterBitmap(true);
            Rect rect = new Rect(0, 0, (int) (((float) i4) * 1.0f), i5);
            Matrix matrix = new Matrix();
            matrix.postScale(1.0f, -1.0f);
            matrix.postTranslate(0.0f, (float) i5);
            canvas.setMatrix(matrix);
            canvas.drawBitmap(bitmap2, null, rect, paint);
            canvas.setMatrix(null);
            if (bitmap2 != null && !bitmap2.isRecycled()) {
                bitmap2.recycle();
            }
            try {
                if (akq.this.j != null) {
                    akq.this.j.a(bitmap);
                }
            } catch (OutOfMemoryError unused) {
                if (akq.this.j != null) {
                    akq.this.j.a(null);
                }
            } catch (GLException unused2) {
                if (akq.this.j != null) {
                    akq.this.j.a(null);
                }
            } catch (Exception unused3) {
                if (akq.this.j != null) {
                    akq.this.j.a(null);
                }
            }
        }

        public final void a(byte[] bArr) {
            akq.this.C = akq.a(akq.this, bArr);
            if (akq.this.M != null) {
                akq.this.M;
            }
        }

        public final void a(int i, int i2, long[] jArr, Bitmap bitmap) {
            akq.b("OnScreenShot: ".concat(String.valueOf(i)));
            if (akq.this.I && akq.this.J == 0) {
                akq.this.J = SystemClock.elapsedRealtime();
            }
            if (jArr != null && jArr.length == 8) {
                long j = jArr[3];
                if (j != 0) {
                    if (i2 == GLMapStaticValue.o) {
                        if (akq.this.I) {
                            long elapsedRealtime = SystemClock.elapsedRealtime();
                            new StringBuilder("onScreenShotFinished buffer time: ").append(elapsedRealtime - akq.this.J);
                            ana.a();
                            akq.this.J = elapsedRealtime;
                        }
                        akq.a(akq.this, i, j);
                        return;
                    }
                    int i3 = (int) jArr[6];
                    int i4 = (int) jArr[7];
                    int i5 = (int) jArr[5];
                    if (i3 > 0 && i4 > 0 && (i5 == 2 || i5 == 4)) {
                        if (i2 == GLMapStaticValue.p) {
                            if (akq.this.I) {
                                long elapsedRealtime2 = SystemClock.elapsedRealtime();
                                new StringBuilder("onScreenShotFinished bitmap time: ").append(elapsedRealtime2 - akq.this.J);
                                ana.a();
                                akq.this.J = elapsedRealtime2;
                            }
                            akq.a(akq.this, i, bitmap);
                            return;
                        }
                        File a2 = amz.a();
                        if (a2 != null) {
                            String absolutePath = a2.getAbsolutePath();
                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream(a2);
                                if (!bitmap.isRecycled()) {
                                    bitmap.compress(CompressFormat.JPEG, GLMapStaticValue.n, fileOutputStream);
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                    if (akq.this.I) {
                                        long elapsedRealtime3 = SystemClock.elapsedRealtime();
                                        new StringBuilder("onScreenShotFinished file time: ").append(elapsedRealtime3 - akq.this.J);
                                        ana.a();
                                        akq.this.J = elapsedRealtime3;
                                    }
                                    "onScreenShotFinished mBitmapPath = ".concat(String.valueOf(absolutePath));
                                    ana.a();
                                    akq.a(akq.this, i, absolutePath);
                                }
                            } catch (Exception e) {
                                new StringBuilder(" file error IOException: ").append(e.toString());
                                ana.a();
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    /* renamed from: akq$b */
    /* compiled from: AMapController */
    public interface b {
        void a(Bitmap bitmap);
    }

    /* renamed from: akq$c */
    /* compiled from: AMapController */
    public interface c {
    }

    /* renamed from: akq$d */
    /* compiled from: AMapController */
    public interface d {
    }

    public static alq a() {
        return P;
    }

    public static void a(alq alq) {
        P = alq;
    }

    public final void a(String str) {
        if (A) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(", this = ");
            sb.append(this);
            sb.append(", glMapEngine = ");
            sb.append(this.d);
            String sb2 = sb.toString();
            if (x) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sb2);
                sb3.append("\r\n\t");
                sb3.append(Log.getStackTraceString(new Throwable()));
                sb2 = sb3.toString();
            }
            ana.a(z, sb2);
        }
    }

    public static void b(String str) {
        if (y) {
            if (x) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("\r\n\t");
                sb.append(Log.getStackTraceString(new Throwable()));
            }
            ana.a();
        }
    }

    public final void a(amk amk) {
        b("addMapListener = ".concat(String.valueOf(amk)));
        this.q.addMapListener(amk);
        this.d.setMapListener(this.q);
    }

    public final void b(amk amk) {
        b("removeMapListener".concat(String.valueOf(amk)));
        this.q.removeMapListener(amk);
        this.d.setMapListener(this.q);
    }

    public final void a(aml aml) {
        b("addMapOverlayListener:".concat(String.valueOf(aml)));
        this.r.a(aml);
    }

    public final void b(aml aml) {
        b("removeMapOverlayListener:".concat(String.valueOf(aml)));
        this.r.b(aml);
    }

    /* access modifiers changed from: 0000 */
    public final void a(amm amm) {
        b("addMapSurfaceListener:".concat(String.valueOf(amm)));
        this.s.addMapSurfaceListener(amm);
    }

    /* access modifiers changed from: 0000 */
    public final void a(amn amn) {
        b("addMapWidgetListener:".concat(String.valueOf(amn)));
        this.t.addMapWidgetListener(amn);
    }

    private akq() {
        b((String) "GLMapView");
        new StringBuilder("GLMapView GLMapView: ,").append(hashCode());
        ana.a();
        this.O[9] = SystemClock.elapsedRealtime();
        this.O[10] = SystemClock.elapsedRealtime();
    }

    public static akq b() {
        if (B == null) {
            synchronized (akq.class) {
                if (B == null) {
                    B = new akq();
                    new StringBuilder("mInstance: ").append(B);
                    ana.a();
                }
            }
        }
        return B;
    }

    public final void c(String str) {
        if (str == null) {
            str = "";
        }
        synchronized (this.f) {
            if (this.e.intValue() == 0) {
                if (this.d == null) {
                    this.d = new GLMapEngine(d(), e(), str, this.N);
                }
                this.d.InitGLThread();
                b((String) "initNetworkState");
                this.b = new amt();
                this.b.a = this;
                this.b.a(this.c.getApplicationContext(), true);
                this.d.setNetStatus(amt.a(this.c.getApplicationContext()));
                this.e = Integer.valueOf(1);
            }
        }
        this.g.a();
    }

    public final void a(Context context) {
        b((String) "networkStateChanged");
        if (this.d.getNativeInstance() != 0) {
            this.d.setNetStatus(amt.a(context));
            this.d.GetValidDevices();
            int[] GetValidDevices = this.d.GetValidDevices();
            if (GetValidDevices != null) {
                for (int i2 = 0; i2 < GetValidDevices.length; i2++) {
                    new StringBuilder("networkStateChanged device id: ").append(GetValidDevices[i2]);
                    ana.a();
                    o(GetValidDevices[i2]);
                }
            }
        }
    }

    public final boolean a(int i2) {
        b("isMapInited: ".concat(String.valueOf(i2)));
        int[] engineIDs = this.d.getEngineIDs(-1);
        if (engineIDs == null) {
            return false;
        }
        int i3 = 0;
        while (i3 < engineIDs.length && engineIDs[i3] != i2) {
            i3++;
        }
        if (i3 == engineIDs.length) {
            return false;
        }
        return true;
    }

    public final int b(int i2) {
        akr akr = z(i2).d;
        if (-1 == akr.a || akr.b == null || akr.c == null) {
            return 20;
        }
        int maxZoomLevel = akr.b.getMaxZoomLevel(akr.a);
        StringBuilder sb = new StringBuilder("getMaxZoomLevel: ");
        sb.append(akr.a);
        sb.append(", ");
        sb.append(maxZoomLevel);
        b(sb.toString());
        return maxZoomLevel;
    }

    public final int c(int i2) {
        akr akr = z(i2).d;
        if (-1 == akr.a || akr.b == null || akr.c == null) {
            return 3;
        }
        int minZoomLevel = akr.b.getMinZoomLevel(akr.a);
        StringBuilder sb = new StringBuilder("getMinZoomLevel: ");
        sb.append(akr.a);
        sb.append(", ");
        sb.append(minZoomLevel);
        b(sb.toString());
        return minZoomLevel;
    }

    public final GLGeoPoint d(int i2) {
        akr akr = z(i2).d;
        if (-1 == akr.a || akr.b == null || akr.c == null) {
            return null;
        }
        Point mapCenter = akr.b.getMapCenter(akr.a);
        GLGeoPoint gLGeoPoint = new GLGeoPoint(mapCenter.x, mapCenter.y);
        StringBuilder sb = new StringBuilder("getMapCenter: ");
        sb.append(akr.a);
        sb.append(" (");
        sb.append(gLGeoPoint.x);
        sb.append(", ");
        sb.append(gLGeoPoint.y);
        sb.append(")");
        b(sb.toString());
        return gLGeoPoint;
    }

    public final boolean a(int i2, int i3, int i4) {
        akr akr = z(i2).d;
        if (-1 == akr.a || akr.b == null || akr.c == null) {
            return false;
        }
        StringBuilder sb = new StringBuilder("setMapCenter: ");
        sb.append(akr.a);
        sb.append(", ");
        sb.append(i3);
        sb.append(", ");
        sb.append(i4);
        b(sb.toString());
        if (i3 < 0 || i3 > 268435455 || i4 < 20 || i4 > 268435431) {
            return false;
        }
        akr.b.clearAnimationsByContent(akr.a, 1, true);
        akr.b.setMapCenter(akr.a, i3, i4);
        akr.c.p(akr.b.getBelongToRenderDeviceId(akr.a));
        return true;
    }

    public final void a(int i2, float f2) {
        StringBuilder sb = new StringBuilder("animateZoomTo: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(f2);
        b(sb.toString());
        a(i2, 500, f2, -9999.0f, -9999.0f, -9999, -9999);
    }

    public final boolean a(Runnable runnable, long j2) {
        return this.u.postDelayed(runnable, j2);
    }

    public final float e(int i2) {
        akr akr = z(i2).d;
        if (-1 == akr.a || akr.b == null || akr.c == null) {
            return 3.0f;
        }
        float mapZoomer = akr.b.getMapZoomer(akr.a);
        StringBuilder sb = new StringBuilder("getPreciseLevel: ");
        sb.append(akr.a);
        sb.append(", ");
        sb.append(mapZoomer);
        b(sb.toString());
        return mapZoomer;
    }

    public final boolean b(int i2, float f2) {
        akr akr = z(i2).d;
        if (-1 == akr.a || akr.b == null || akr.c == null) {
            return false;
        }
        StringBuilder sb = new StringBuilder("setMapLevel: ");
        sb.append(akr.a);
        sb.append(", ");
        sb.append(f2);
        b(sb.toString());
        akr.b.FinishAnimations(akr.a);
        akr.b.setMapZoom(akr.a, f2);
        akr.c.p(akr.b.getBelongToRenderDeviceId(akr.a));
        return true;
    }

    public final boolean a(int i2, Point point) {
        String str;
        float f2;
        String concat = "zoomIn: ".concat(String.valueOf(i2));
        if (point != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(concat);
            sb.append(", (");
            sb.append(point.x);
            sb.append(", ");
            sb.append(point.y);
            sb.append(")");
            str = sb.toString();
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(concat);
            sb2.append(", null");
            str = sb2.toString();
        }
        b(str);
        float uptimeMillis = 500.0f / ((float) (SystemClock.uptimeMillis() - this.R));
        int i3 = 3;
        if (uptimeMillis < 1.0f) {
            this.T = 0;
            this.V[this.T % 3] = 1.0f;
            this.T++;
            f2 = 1.0f;
        } else {
            this.V[this.T % 3] = uptimeMillis;
            this.T++;
            if (this.T <= 3) {
                i3 = this.T;
            }
            float f3 = 0.0f;
            for (int i4 = 0; i4 < i3; i4++) {
                f3 += this.V[i4];
            }
            f2 = f3 > 2.0f ? 2.0f : f3;
        }
        this.d.startPivotZoomAnim(this, i2, point, f2, Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        p(this.d.getBelongToRenderDeviceId(i2));
        this.R = SystemClock.uptimeMillis();
        return true;
    }

    public final void f(int i2) {
        b("zoomOut: ".concat(String.valueOf(i2)));
        float uptimeMillis = 500.0f / ((float) (SystemClock.uptimeMillis() - this.S));
        float f2 = 1.0f;
        int i3 = 3;
        if (uptimeMillis < 1.0f) {
            this.U = 0;
            this.W[this.U % 3] = 1.0f;
            this.U++;
        } else {
            this.W[this.U % 3] = uptimeMillis;
            this.U++;
            f2 = 0.0f;
            if (this.U <= 3) {
                i3 = this.U;
            }
            for (int i4 = 0; i4 < i3; i4++) {
                f2 += this.W[i4];
            }
            if (f2 > 2.0f) {
                f2 = 2.0f;
            }
        }
        this.d.startPivotZoomAnim(this, i2, null, -f2, Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        p(this.d.getBelongToRenderDeviceId(i2));
        this.S = SystemClock.uptimeMillis();
    }

    public final void a(int i2, boolean z2) {
        StringBuilder sb = new StringBuilder("lockMapCameraDegree: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(z2);
        b(sb.toString());
        b(i2, 7, true);
        if (z2) {
            a(i2, 500, -9999.0f, -9999.0f, 0.0f, -9999, -9999);
        }
    }

    public final void g(int i2) {
        b("lockMapCameraDegree: ".concat(String.valueOf(i2)));
        b(i2, 7, false);
    }

    public final boolean h(int i2) {
        boolean b2 = b(i2, 7);
        StringBuilder sb = new StringBuilder("isLockMapCameraDegree: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(b2);
        b(sb.toString());
        return b2;
    }

    public final void b(int i2, boolean z2) {
        StringBuilder sb = new StringBuilder("lockMapAngle: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(z2);
        b(sb.toString());
        b(i2, 5, true);
        if (z2) {
            a(i2, 500, -9999.0f, 0.0f, -9999.0f, -9999, -9999);
        }
    }

    public final void i(int i2) {
        b("unlockMapAngle: ".concat(String.valueOf(i2)));
        b(i2, 5, false);
    }

    public final boolean j(int i2) {
        boolean b2 = b(i2, 5);
        StringBuilder sb = new StringBuilder("isLockMapAngle: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(b2);
        b(sb.toString());
        return b2;
    }

    public final void c(int i2, boolean z2) {
        StringBuilder sb = new StringBuilder("setTouchEnable: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(z2);
        b(sb.toString());
        B(i2).d = z2;
    }

    public final boolean a(final int i2, MotionEvent motionEvent) {
        boolean z2;
        StringBuilder sb = new StringBuilder("onSingleTapConfirmed: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(motionEvent);
        b(sb.toString());
        GLOverlayBundle<BaseMapOverlay<?, ?>> s2 = s(i2);
        if (s2 == null) {
            return false;
        }
        if (s2.onSingleTap(i2, (int) motionEvent.getX(), (int) motionEvent.getY(), 1)) {
            D(i2);
            return true;
        }
        boolean b2 = b(i2, 6);
        if (b2) {
            s2.clearFocus();
        }
        if (this.q == null || this.d.isInMapAction(i2) || !this.q.onSingleTapUp(i2, motionEvent)) {
            if (s2.onSingleTap(i2, (int) motionEvent.getX(), (int) motionEvent.getY(), 2)) {
                z2 = true;
            } else {
                if (b2) {
                    s2.clearFocus();
                }
                z2 = false;
            }
            if (z2) {
                D(i2);
                return true;
            }
            b("onBlankClick: ".concat(String.valueOf(i2)));
            if (this.r != null) {
                this.u.post(new Runnable() {
                    public final void run() {
                        akq.this.r.onBlankClick(i2);
                    }
                });
            }
            if (b2) {
                b("clearFocus: ".concat(String.valueOf(i2)));
                if (this.r != null) {
                    this.u.post(new Runnable() {
                        public final void run() {
                            akq.this.r.onNoFeatureClick(i2);
                        }
                    });
                }
            }
            return false;
        }
        D(i2);
        return true;
    }

    private void D(final int i2) {
        b("onNoBlankClick: ".concat(String.valueOf(i2)));
        if (this.r != null) {
            this.u.post(new Runnable() {
                public final void run() {
                    akq.this.r.onNoBlankClick(i2);
                }
            });
        }
    }

    public static void a(boolean z2) {
        anb.a = z2;
    }

    private String d() {
        b((String) "getMapSvrAddress: ");
        if (this.p) {
            return this.o;
        }
        return anb.a ? "https://maps.testing.amap.com/" : "https://mps.amap.com/";
    }

    private static String e() {
        b((String) "getMapIndoorAddress: ");
        return anb.a ? "https://maps.testing.amap.com/" : "https://m5.amap.com/";
    }

    public final synchronized void b(int i2, int i3, int i4) {
        z(i2).d.a(i3, i4);
    }

    public final synchronized void a(int i2, float f2, float f3) {
        z(i2).d.a(f2, f3);
    }

    public final void c(int i2, float f2) {
        akr akr = z(i2).d;
        if (-1 != akr.a && akr.b != null && akr.c != null) {
            StringBuilder sb = new StringBuilder("setMapAngle: ");
            sb.append(akr.a);
            sb.append(", ");
            sb.append(f2);
            b(sb.toString());
            akr.b.clearAnimations(akr.a, true);
            akr.b.setMapAngle(akr.a, f2);
            akr.c.p(akr.b.getBelongToRenderDeviceId(akr.a));
        }
    }

    public final float k(int i2) {
        akr akr = z(i2).d;
        if (-1 == akr.a || akr.b == null || akr.c == null) {
            return 0.0f;
        }
        StringBuilder sb = new StringBuilder("getMapAngle: ");
        sb.append(akr.a);
        b(sb.toString());
        return akr.b.getMapAngle(akr.a);
    }

    public final float l(int i2) {
        akr akr = z(i2).d;
        if (-1 == akr.a || akr.b == null || akr.c == null) {
            return 0.0f;
        }
        StringBuilder sb = new StringBuilder("getCameraDegree: ");
        sb.append(akr.a);
        b(sb.toString());
        return akr.b.getCameraDegree(akr.a);
    }

    public final void d(int i2, float f2) {
        akr akr = z(i2).d;
        if (-1 != akr.a && akr.b != null && akr.c != null) {
            StringBuilder sb = new StringBuilder("setCameraDegree: ");
            sb.append(akr.a);
            b(sb.toString());
            akr.b.clearAnimations(akr.a, true);
            akr.b.setCameraDegree(akr.a, f2);
            akr.c.p(akr.b.getBelongToRenderDeviceId(akr.a));
        }
    }

    public final void a(int i2, akw akw) {
        b("addGestureMapMessage: ".concat(String.valueOf(i2)));
        this.d.addGestureMessage(i2, akw);
    }

    public final void a(int i2, int i3, float f2, float f3, float f4, int i4, int i5) {
        StringBuilder sb = new StringBuilder("addMapAnimation: ");
        int i6 = i2;
        sb.append(i6);
        sb.append(", ");
        int i7 = i3;
        sb.append(i7);
        sb.append(", ");
        float f5 = f2;
        sb.append(f5);
        sb.append(", ");
        float f6 = f3;
        sb.append(f6);
        sb.append(", ");
        float f7 = f4;
        sb.append(f7);
        sb.append(", ");
        int i8 = i4;
        sb.append(i8);
        sb.append(", ");
        int i9 = i5;
        sb.append(i9);
        b(sb.toString());
        a(i6, i7, f5, f6, f7, i8, i9, false);
    }

    public final void a(int i2, int i3, float f2, float f3, float f4, int i4, int i5, boolean z2) {
        int i6 = i2;
        float f5 = f2;
        float f6 = f3;
        float f7 = f4;
        int i7 = i4;
        int i8 = i5;
        StringBuilder sb = new StringBuilder("addMapAnimation 2: ");
        sb.append(i6);
        sb.append(", ");
        int i9 = i3;
        sb.append(i9);
        sb.append(", ");
        sb.append(f5);
        sb.append(", ");
        sb.append(f6);
        sb.append(", ");
        sb.append(f7);
        sb.append(", ");
        sb.append(i7);
        sb.append(", ");
        sb.append(i8);
        sb.append(", ");
        boolean z3 = z2;
        sb.append(z3);
        b(sb.toString());
        if (i7 != -9999 || i8 != -9999 || f5 != -9999.0f || f6 != -9999.0f || f7 != -9999.0f) {
            this.d.AddGroupAnimation(i6, -1, i9, f5, f6, f7, i7, i8, z3);
            e(this.d.getBelongToRenderDeviceId(i6), 6);
        }
    }

    public final void m(int i2) {
        b("renderResume: ".concat(String.valueOf(i2)));
        int[] engineIDs = this.d.getEngineIDs(i2);
        if (engineIDs != null && engineIDs.length > 0) {
            for (int t2 : engineIDs) {
                t(t2);
            }
        }
        this.d.renderResumeIn(i2);
    }

    public final boolean n(int i2) {
        b("isRenderPaused: ".concat(String.valueOf(i2)));
        return this.d.isRenderPauseIn(i2);
    }

    public final void o(int i2) {
        b((String) "resetRenderTimeLong: ");
        e(i2, 6);
    }

    public final void p(int i2) {
        b((String) "resetRenderTime: ");
        e(i2, 2);
    }

    public final void q(int i2) {
        b((String) "resetRenderTime: false");
        e(i2, 2);
    }

    public final void r(int i2) {
        b((String) "refreshRender: ");
        if (!n(i2)) {
            p(i2);
        }
    }

    public final GLOverlayBundle<BaseMapOverlay<?, ?>> s(int i2) {
        b("getOverlayBundle: ".concat(String.valueOf(i2)));
        return this.d.getOvelayBundle(i2);
    }

    public final void a(int i2, amh amh) {
        b("addOverlayTexture: ".concat(String.valueOf(i2)));
        GLOverlayBundle<BaseMapOverlay<?, ?>> s2 = s(i2);
        if (s2 != null) {
            if (amh == null || amh.b == null || amh.b.isRecycled()) {
                if (!(amh == null || amh.c == null)) {
                    int[] iArr = new int[2];
                    this.d.addOverlayTexture(i2, amh, iArr);
                    s2.addOverlayTextureItem(amh.a, amh.d, amh.e, amh.f, iArr[0], iArr[1]);
                }
                return;
            }
            this.d.addOverlayTexture(i2, amh, null);
            s2.addOverlayTextureItem(amh.a, amh.d, amh.e, amh.f, amh.b.getWidth(), amh.b.getHeight());
        }
    }

    public final void a(int i2, int i3) {
        b("cleanOverlayTexture: ".concat(String.valueOf(i2)));
        GLOverlayBundle<BaseMapOverlay<?, ?>> s2 = s(i2);
        if (s2 != null) {
            if (this.d.cleanOverlayTexture(i2, i3)) {
                amc overlayTextureItem = s2.getOverlayTextureItem(i3);
                if (overlayTextureItem != null) {
                    overlayTextureItem.h = 9;
                    overlayTextureItem.b = 1;
                    overlayTextureItem.c = 1;
                    overlayTextureItem.d = 1;
                    overlayTextureItem.e = 1;
                    overlayTextureItem.f = 1.0f;
                    overlayTextureItem.g = 1.0f;
                }
            }
            p(this.d.getBelongToRenderDeviceId(i2));
        }
    }

    public final void a(int i2, int i3, int i4, int i5, float f2, float f3, float f4, float f5, String str, int i6) {
        b("addPoiFilter 3: ".concat(String.valueOf(i2)));
        this.d.addPoiFilter(i2, i3, i4, i5, f2, f3, f4, f5, str, i6);
        q(this.d.getBelongToRenderDeviceId(i2));
    }

    public final synchronized void a(int i2, int i3, int i4, int i5) {
        a(i2, i3, i4, i5, -1);
    }

    public final synchronized void a(int i2, int i3, int i4, int i5, int i6) {
        z(i2).a(i3, i4, i5, i6);
    }

    public final void t(int i2) {
        b("clearAllMessageAndAnimationAsync: ".concat(String.valueOf(i2)));
        this.d.clearAllMessages(i2);
        this.d.clearAnimations(i2, true);
    }

    public final void u(int i2) {
        b("clearAllAnimation: ".concat(String.valueOf(i2)));
        this.d.clearAnimations(i2, true);
    }

    public final PointF c(int i2, int i3, int i4) {
        b("getP20ToScreenPoint: ".concat(String.valueOf(i2)));
        PointF pointF = new PointF();
        this.d.p20ToScreenPoint(i2, i3, i4, pointF);
        return pointF;
    }

    public final void v(int i2) {
        b("clearHightSubway: ".concat(String.valueOf(i2)));
        this.d.setBusinessDataParamater(i2, 63, 0, 0, 0, 0);
        p(this.d.getBelongToRenderDeviceId(i2));
    }

    public final String c() {
        b((String) "getGLRenderString: ");
        return this.D;
    }

    private static String f() {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getDeclaredMethod("get", new Class[]{String.class}).invoke(cls, new Object[]{"ro.build.version.emui"});
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x007d A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x008a  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0091  */
    /* JADX WARNING: Removed duplicated region for block: B:48:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private float e(int r9, int r10, int r11) {
        /*
            r8 = this;
            android.content.Context r0 = r8.c
            android.content.res.Resources r0 = r0.getResources()
            android.util.DisplayMetrics r0 = r0.getDisplayMetrics()
            java.lang.String r1 = f()
            r2 = 1065353216(0x3f800000, float:1.0)
            if (r1 == 0) goto L_0x0092
            boolean r3 = r1.isEmpty()
            if (r3 != 0) goto L_0x0092
            java.lang.String r3 = "EmotionUI_8"
            int r3 = r1.indexOf(r3)
            r4 = -1
            if (r3 != r4) goto L_0x0029
            java.lang.String r3 = "EmotionUI_9"
            int r1 = r1.indexOf(r3)
            if (r1 == r4) goto L_0x0092
        L_0x0029:
            if (r11 <= 0) goto L_0x0092
            r1 = 1
            r3 = 0
            java.lang.Class<android.util.DisplayMetrics> r4 = android.util.DisplayMetrics.class
            java.lang.String r5 = "noncompatWidthPixels"
            java.lang.reflect.Field r4 = r4.getDeclaredField(r5)     // Catch:{ NoSuchFieldException -> 0x0042, IllegalAccessException -> 0x003d }
            r4.setAccessible(r1)     // Catch:{ NoSuchFieldException -> 0x0042, IllegalAccessException -> 0x003d }
            int r4 = r4.getInt(r0)     // Catch:{ NoSuchFieldException -> 0x0042, IllegalAccessException -> 0x003d }
            goto L_0x0047
        L_0x003d:
            r4 = move-exception
            r4.printStackTrace()
            goto L_0x0046
        L_0x0042:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0046:
            r4 = 0
        L_0x0047:
            java.lang.Class<android.util.DisplayMetrics> r5 = android.util.DisplayMetrics.class
            java.lang.String r6 = "noncompatHeightPixels"
            java.lang.reflect.Field r5 = r5.getDeclaredField(r6)     // Catch:{ NoSuchFieldException -> 0x005c, IllegalAccessException -> 0x0057 }
            r5.setAccessible(r1)     // Catch:{ NoSuchFieldException -> 0x005c, IllegalAccessException -> 0x0057 }
            int r5 = r5.getInt(r0)     // Catch:{ NoSuchFieldException -> 0x005c, IllegalAccessException -> 0x0057 }
            goto L_0x0061
        L_0x0057:
            r5 = move-exception
            r5.printStackTrace()
            goto L_0x0060
        L_0x005c:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0060:
            r5 = 0
        L_0x0061:
            java.lang.Class<android.util.DisplayMetrics> r6 = android.util.DisplayMetrics.class
            java.lang.String r7 = "noncompatDensityDpi"
            java.lang.reflect.Field r6 = r6.getDeclaredField(r7)     // Catch:{ NoSuchFieldException -> 0x0076, IllegalAccessException -> 0x0071 }
            r6.setAccessible(r1)     // Catch:{ NoSuchFieldException -> 0x0076, IllegalAccessException -> 0x0071 }
            int r0 = r6.getInt(r0)     // Catch:{ NoSuchFieldException -> 0x0076, IllegalAccessException -> 0x0071 }
            goto L_0x007b
        L_0x0071:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x007a
        L_0x0076:
            r0 = move-exception
            r0.printStackTrace()
        L_0x007a:
            r0 = 0
        L_0x007b:
            if (r0 > r11) goto L_0x0081
            if (r4 > r9) goto L_0x0081
            if (r5 <= r10) goto L_0x0092
        L_0x0081:
            float r9 = (float) r0
            float r10 = (float) r11
            float r9 = r9 / r10
            r10 = 1073741824(0x40000000, float:2.0)
            int r11 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r11 <= 0) goto L_0x008c
            r9 = 1073741824(0x40000000, float:2.0)
        L_0x008c:
            int r10 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r10 >= 0) goto L_0x0091
            goto L_0x0092
        L_0x0091:
            r2 = r9
        L_0x0092:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.akq.e(int, int, int):float");
    }

    public final void d(int i2, boolean z2) {
        b("showIndoorBuilding: ".concat(String.valueOf(i2)));
        b(i2, 27, z2);
        p(this.d.getBelongToRenderDeviceId(i2));
    }

    public final int a(int i2, Rect rect, int i3, int i4, MapEngineInitParam mapEngineInitParam) {
        int i5;
        StringBuilder sb = new StringBuilder("createMapEngine: ");
        sb.append(i2);
        sb.append(", ");
        b(sb.toString());
        if (w) {
            this.O[0] = SystemClock.elapsedRealtime();
        }
        if (this.d == null || rect == null) {
            i5 = -1;
        } else {
            this.h = i3;
            this.i = i4;
            DisplayMetrics displayMetrics = this.c.getResources().getDisplayMetrics();
            float calMapZoomScalefactor = this.d.calMapZoomScalefactor(this.h, this.i, (float) displayMetrics.densityDpi, e(displayMetrics.widthPixels, displayMetrics.heightPixels, displayMetrics.densityDpi));
            i5 = this.d.createEngineWithFrame(i2, rect, i3, i4, mapEngineInitParam);
            this.d.setOvelayBundle(i5, new GLOverlayBundle(i5, this));
            this.d.setMapAngle(i5, (float) this.g.a);
            this.d.setMapZoom(i5, (float) this.g.c);
            this.d.setMapCenter(i5, this.g.d, this.g.e);
            this.d.setCameraDegree(i5, (float) this.g.b);
            this.d.setServerAddress(d());
            this.d.setIndoorServerAddress(e());
            b("setTextGLUnit: ".concat(String.valueOf(i5)));
            this.d.setBusinessDataParamater(i5, 69, 1000, 500, 200, 800);
            akt akt = new akt();
            akt.a(i5, this.d, this);
            akt.f = calMapZoomScalefactor;
            this.ad.add(akt);
        }
        if (w) {
            this.O[1] = SystemClock.elapsedRealtime();
        }
        return i5;
    }

    public final int a(EAMapPlatformGestureInfo eAMapPlatformGestureInfo) {
        b("getEngineIDWithGestureInfo: ".concat(String.valueOf(eAMapPlatformGestureInfo)));
        if (this.d != null) {
            return this.d.getEngineIDWithGestureInfo(eAMapPlatformGestureInfo);
        }
        return -1;
    }

    public final void b(int i2, int i3, boolean z2) {
        if (this.d != null) {
            this.d.setSrvViewStateBoolValue(i2, i3, z2);
        }
    }

    public final boolean b(int i2, int i3) {
        if (this.d != null) {
            return this.d.getSrvViewStateBoolValue(i2, i3);
        }
        return false;
    }

    public final void d(int i2, int i3, int i4) {
        StringBuilder sb = new StringBuilder("setRenderFps: deviceId =");
        sb.append(i2);
        sb.append(", fpsType = ");
        sb.append(i3);
        sb.append(", fps = ");
        sb.append(i4);
        b(sb.toString());
        if (i4 > 0 && i4 <= 60) {
            if (E == i3) {
                GLMapStaticValue.a = i4;
            } else if (F == i3) {
                GLMapStaticValue.b = i4;
            } else if (G == i3) {
                GLMapStaticValue.c = i4;
            } else if (H == i3) {
                GLMapStaticValue.e = i4;
            }
            this.d.setMaxFps(i2, i4);
            this.d.setRenderFpsEx(i2, i3, i4);
        }
    }

    public final void c(int i2, int i3) {
        b("setRenderFps: ".concat(String.valueOf(i3)));
        this.d.setRenderFps(i2, i3);
    }

    public final void e(int i2, int i3) {
        b("resetTickCount: ".concat(String.valueOf(i3)));
        if (this.d != null) {
            d(i2, Math.max(this.d.getAdapterRenderFps(i2, true), GLMapStaticValue.b));
        }
    }

    public final void a(int i2, Runnable runnable) {
        this.d.sendToRenderEvent(i2, runnable);
    }

    public final void w(int i2) {
        b((String) "requestRender: ");
        if (n(i2)) {
            m(i2);
            e(i2, 2);
        }
    }

    public final int a(GLDeviceAttribute gLDeviceAttribute) {
        DisplayMetrics displayMetrics = this.c.getResources().getDisplayMetrics();
        gLDeviceAttribute.mScreenScale = displayMetrics.density;
        gLDeviceAttribute.mDpi = (float) displayMetrics.densityDpi;
        gLDeviceAttribute.mDpiScale = e(displayMetrics.widthPixels, displayMetrics.heightPixels, displayMetrics.densityDpi);
        int createRenderDevice = this.d.createRenderDevice(gLDeviceAttribute);
        if (createRenderDevice >= 0) {
            aks aks = new aks();
            aks.c = this;
            aks.a = createRenderDevice;
            aks.b = this.d;
            this.ae.add(aks);
        }
        b("createRenderDevice: ".concat(String.valueOf(createRenderDevice)));
        return createRenderDevice;
    }

    public final void x(int i2) {
        aks B2 = B(i2);
        if (!(-1 == B2.a || B2.b == null || B2.c == null)) {
            StringBuilder sb = new StringBuilder("destroyRenderDevice: ");
            sb.append(B2.a);
            b(sb.toString());
            B2.b.destroyRenderDevice(B2.a);
        }
        E(i2);
    }

    public final void a(int i2, Surface surface, GLSurfaceAttribute gLSurfaceAttribute) {
        aks B2 = B(i2);
        if (-1 != B2.a && B2.b != null && B2.c != null) {
            StringBuilder sb = new StringBuilder("attachSurfaceToRenderDevice 1: ");
            sb.append(B2.a);
            b(sb.toString());
            B2.b.attachSurfaceToRenderDevice(B2.a, surface, gLSurfaceAttribute);
        }
    }

    public final void y(int i2) {
        aks B2 = B(i2);
        if (-1 != B2.a && B2.b != null && B2.c != null) {
            StringBuilder sb = new StringBuilder("detachSurfaceFromRenderDevice: ");
            sb.append(B2.a);
            b(sb.toString());
            B2.b.detachSurfaceFromRenderDevice(B2.a);
        }
    }

    public final void b(int i2, Surface surface, GLSurfaceAttribute gLSurfaceAttribute) {
        aks B2 = B(i2);
        if (-1 != B2.a && B2.b != null && B2.c != null) {
            StringBuilder sb = new StringBuilder("renderDeviceChanged 1: ");
            sb.append(B2.a);
            b(sb.toString());
            B2.b.renderDeviceChanged(B2.a, surface, gLSurfaceAttribute);
        }
    }

    public final akt z(int i2) {
        int size = this.ad.size();
        boolean z2 = false;
        for (int i3 = 0; i3 < size; i3++) {
            akt akt = this.ad.get(i3);
            if (akt.a == i2) {
                if (akt.c != this) {
                    b((String) "getAMapView ERROR about controller");
                    z2 = true;
                }
                if (akt.b != this.d) {
                    b((String) "getAMapView ERROR about engine");
                    z2 = true;
                }
                if (z2) {
                    akt.a(i2, this.d, this);
                }
                return akt;
            }
        }
        akt akt2 = new akt();
        akt2.a(i2, this.d, this);
        return akt2;
    }

    /* access modifiers changed from: 0000 */
    public final void A(int i2) {
        int size = this.ad.size();
        for (int i3 = 0; i3 < size; i3++) {
            akt akt = this.ad.get(i3);
            if (akt.a == i2) {
                if (akt.c != this) {
                    b((String) "removeAMapView ERROR about controller");
                    akt.c = this;
                }
                if (akt.b != this.d) {
                    b((String) "removeAMapView ERROR about engine");
                    akt.b = this.d;
                }
                this.ad.remove(i3);
                return;
            }
        }
    }

    public final aks B(int i2) {
        int size = this.ae.size();
        for (int i3 = 0; i3 < size; i3++) {
            aks aks = this.ae.get(i3);
            if (aks.a == i2) {
                if (aks.c != this) {
                    b((String) "getAMapRenderDevice ERROR about controller");
                    aks.c = this;
                }
                if (aks.b != this.d) {
                    b((String) "getAMapRenderDevice ERROR about engine");
                    aks.b = this.d;
                }
                return aks;
            }
        }
        aks aks2 = new aks();
        aks2.c = this;
        aks2.b = this.d;
        return aks2;
    }

    /* access modifiers changed from: private */
    public void E(int i2) {
        int size = this.ae.size();
        for (int i3 = 0; i3 < size; i3++) {
            aks aks = this.ae.get(i3);
            if (aks.a == i2) {
                if (aks.c != this) {
                    b((String) "removeAMapRenderDevice ERROR about controller");
                    aks.c = this;
                }
                if (aks.b != this.d) {
                    b((String) "removeAMapRenderDevice ERROR about engine");
                    aks.b = this.d;
                }
                this.ae.remove(i3);
                return;
            }
        }
    }

    public final int C(int i2) {
        return this.d.getBelongToRenderDeviceId(i2);
    }

    public final void a(int i2, int i3, boolean z2) {
        if (!z2) {
            i3 |= Integer.MIN_VALUE;
        }
        if (this.d != null) {
            this.d.setSrvViewStateIntValue(i2, 60, i3);
        }
    }

    public final void d(int i2, int i3) {
        StringBuilder sb = new StringBuilder("setRenderFpsWithTimer: ");
        sb.append(i3);
        sb.append(true);
        b(sb.toString());
        if (this.d != null) {
            this.d.setRenderFpsWithTimer(i2, i3, true);
        }
    }

    static /* synthetic */ ArrayList a(akq akq, byte[] bArr) {
        b((String) "labelBufferToItems: ");
        if (bArr == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int i2 = amy.a(bArr, 0) > 0 ? 1 : 0;
        int i3 = 4;
        for (int i4 = 0; i4 < i2; i4++) {
            als als = new als();
            int a2 = amy.a(bArr, i3);
            int i5 = i3 + 4;
            int a3 = amy.a(bArr, i5);
            int i6 = i5 + 4;
            als.c = a2;
            als.d = akq.i - a3;
            als.e = amy.a(bArr, i6);
            int i7 = i6 + 4;
            als.f = amy.a(bArr, i7);
            int i8 = i7 + 4;
            als.g = amy.a(bArr, i8);
            int i9 = i8 + 4;
            als.h = amy.a(bArr, i9);
            int i10 = i9 + 4;
            als.i = amy.a(bArr, i10);
            int i11 = i10 + 4;
            als.k = amy.a(bArr, i11);
            int i12 = i11 + 4;
            als.j = bArr[i12] != 0;
            int i13 = i12 + 1;
            if (bArr[i13] == 0) {
                als.b = null;
            } else {
                String str = "";
                for (int i14 = 0; i14 < 20; i14++) {
                    int i15 = i14 + i13;
                    if (bArr[i15] == 0) {
                        break;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append((char) bArr[i15]);
                    str = sb.toString();
                }
                als.b = str;
            }
            int i16 = i13 + 20;
            int i17 = i16 + 1;
            byte b2 = bArr[i16];
            StringBuffer stringBuffer = new StringBuffer();
            int i18 = i17;
            for (int i19 = 0; i19 < b2; i19++) {
                stringBuffer.append((char) amy.c(bArr, i18));
                i18 += 2;
            }
            als.a = stringBuffer.toString();
            int a4 = amy.a(bArr, i18);
            int i20 = i18 + 4;
            if (a4 > 0) {
                als.l = amy.a(bArr, i20, a4 - 1);
                i20 += als.l.length();
            }
            i3 = i20;
            arrayList.add(als);
        }
        return arrayList;
    }

    static /* synthetic */ void a(akq akq, int i2, long j2) {
        StringBuilder sb = new StringBuilder("onScreenShotFinished: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(j2);
        b(sb.toString());
        if (akq.q != null) {
            akq.q.onScreenShotFinished(i2, j2);
        }
    }

    static /* synthetic */ void a(akq akq, int i2, Bitmap bitmap) {
        StringBuilder sb = new StringBuilder("onScreenShotFinished: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(bitmap);
        b(sb.toString());
        if (akq.q != null && bitmap != null) {
            akq.q.onScreenShotFinished(i2, bitmap);
        }
    }

    static /* synthetic */ void a(akq akq, int i2, String str) {
        StringBuilder sb = new StringBuilder("onScreenShotFinished: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(str);
        b(sb.toString());
        if (akq.q != null) {
            akq.q.onScreenShotFinished(i2, str);
        }
    }
}
