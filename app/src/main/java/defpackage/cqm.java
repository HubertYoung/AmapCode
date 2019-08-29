package defpackage;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build.VERSION;
import android.os.Handler;
import android.view.Display;
import android.view.WindowManager;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;

/* renamed from: cqm reason: default package */
/* compiled from: CameraManager */
public final class cqm {
    static final int a;
    private static final String d = "cqm";
    private static volatile cqm e;
    private static final Object f = new Object();
    public boolean b;
    public int c = 0;
    private final Context g;
    private final cql h;
    private Camera i;
    private Rect j;
    private Handler k;
    private boolean l;
    private final boolean m;
    private final cqn n;
    private final cqk o;
    private Runnable p = new Runnable() {
        public final void run() {
            cqm.this.e();
        }
    };

    static {
        int i2;
        try {
            i2 = Integer.parseInt(VERSION.SDK);
        } catch (NumberFormatException unused) {
            i2 = 10000;
        }
        a = i2;
    }

    public static void a(Context context) {
        if (e == null) {
            synchronized (f) {
                if (e == null) {
                    e = new cqm(context);
                }
            }
        }
    }

    public static cqm a() {
        return e;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001f, code lost:
        r0 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private cqm(android.content.Context r3) {
        /*
            r2 = this;
            r2.<init>()
            r0 = 0
            r2.c = r0
            cqm$1 r1 = new cqm$1
            r1.<init>()
            r2.p = r1
            r2.g = r3
            cql r1 = new cql
            r1.<init>(r3)
            r2.h = r1
            java.lang.String r3 = android.os.Build.VERSION.SDK     // Catch:{ NumberFormatException -> 0x0021 }
            int r3 = java.lang.Integer.parseInt(r3)     // Catch:{ NumberFormatException -> 0x0021 }
            r1 = 3
            if (r3 <= r1) goto L_0x0021
            r3 = 1
            r0 = 1
        L_0x0021:
            r2.m = r0
            cqn r3 = new cqn
            cql r0 = r2.h
            boolean r1 = r2.m
            r3.<init>(r0, r1)
            r2.n = r3
            cqk r3 = new cqk
            r3.<init>()
            r2.o = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cqm.<init>(android.content.Context):void");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(11:8|(6:10|(1:12)|13|(1:15)|(1:17)|18)|19|(1:24)(1:23)|25|(15:29|30|(4:32|33|34|(1:36))|37|39|40|(3:42|43|(1:45))|46|48|49|(1:51)|52|(3:54|55|(2:57|58))|(3:62|63|64)|(1:66))|67|68|(1:70)|71|72) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:71:0x013e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(android.view.SurfaceHolder r11) throws java.io.IOException {
        /*
            r10 = this;
            monitor-enter(r10)
            android.hardware.Camera r0 = r10.i     // Catch:{ all -> 0x0143 }
            if (r0 != 0) goto L_0x0141
            android.hardware.Camera r0 = android.hardware.Camera.open()     // Catch:{ all -> 0x0143 }
            r10.i = r0     // Catch:{ all -> 0x0143 }
            android.hardware.Camera r0 = r10.i     // Catch:{ all -> 0x0143 }
            if (r0 != 0) goto L_0x0015
            java.io.IOException r11 = new java.io.IOException     // Catch:{ all -> 0x0143 }
            r11.<init>()     // Catch:{ all -> 0x0143 }
            throw r11     // Catch:{ all -> 0x0143 }
        L_0x0015:
            android.hardware.Camera r0 = r10.i     // Catch:{ all -> 0x0143 }
            r0.setPreviewDisplay(r11)     // Catch:{ all -> 0x0143 }
            boolean r11 = r10.b     // Catch:{ all -> 0x0143 }
            r0 = 3
            r1 = 1
            if (r11 != 0) goto L_0x007d
            r10.b = r1     // Catch:{ all -> 0x0143 }
            cql r11 = r10.h     // Catch:{ all -> 0x0143 }
            android.hardware.Camera r2 = r10.i     // Catch:{ all -> 0x0143 }
            android.hardware.Camera$Parameters r2 = r2.getParameters()     // Catch:{ all -> 0x0143 }
            int r3 = r2.getPreviewFormat()     // Catch:{ all -> 0x0143 }
            r11.d = r3     // Catch:{ all -> 0x0143 }
            java.lang.String r3 = "preview-format"
            java.lang.String r3 = r2.get(r3)     // Catch:{ all -> 0x0143 }
            r11.e = r3     // Catch:{ all -> 0x0143 }
            android.content.Context r3 = r11.a     // Catch:{ all -> 0x0143 }
            java.lang.String r4 = "window"
            java.lang.Object r3 = r3.getSystemService(r4)     // Catch:{ all -> 0x0143 }
            android.view.WindowManager r3 = (android.view.WindowManager) r3     // Catch:{ all -> 0x0143 }
            android.view.Display r3 = r3.getDefaultDisplay()     // Catch:{ all -> 0x0143 }
            android.graphics.Point r4 = new android.graphics.Point     // Catch:{ all -> 0x0143 }
            int r5 = r3.getWidth()     // Catch:{ all -> 0x0143 }
            int r3 = r3.getHeight()     // Catch:{ all -> 0x0143 }
            r4.<init>(r5, r3)     // Catch:{ all -> 0x0143 }
            r11.b = r4     // Catch:{ all -> 0x0143 }
            android.graphics.Point r3 = r11.b     // Catch:{ all -> 0x0143 }
            java.lang.String r4 = "preview-size-values"
            java.lang.String r4 = r2.get(r4)     // Catch:{ all -> 0x0143 }
            if (r4 != 0) goto L_0x0065
            java.lang.String r4 = "preview-size-value"
            java.lang.String r4 = r2.get(r4)     // Catch:{ all -> 0x0143 }
        L_0x0065:
            r2 = 0
            if (r4 == 0) goto L_0x006c
            android.graphics.Point r2 = defpackage.cql.a(r4, r3)     // Catch:{ all -> 0x0143 }
        L_0x006c:
            if (r2 != 0) goto L_0x007b
            android.graphics.Point r2 = new android.graphics.Point     // Catch:{ all -> 0x0143 }
            int r4 = r3.x     // Catch:{ all -> 0x0143 }
            int r4 = r4 >> r0
            int r4 = r4 << r0
            int r3 = r3.y     // Catch:{ all -> 0x0143 }
            int r3 = r3 >> r0
            int r3 = r3 << r0
            r2.<init>(r4, r3)     // Catch:{ all -> 0x0143 }
        L_0x007b:
            r11.c = r2     // Catch:{ all -> 0x0143 }
        L_0x007d:
            cql r11 = r10.h     // Catch:{ all -> 0x0143 }
            android.hardware.Camera r2 = r10.i     // Catch:{ all -> 0x0143 }
            android.hardware.Camera$Parameters r3 = r2.getParameters()     // Catch:{ all -> 0x0143 }
            android.graphics.Point r4 = r11.c     // Catch:{ all -> 0x0143 }
            int r4 = r4.x     // Catch:{ all -> 0x0143 }
            android.graphics.Point r11 = r11.c     // Catch:{ all -> 0x0143 }
            int r11 = r11.y     // Catch:{ all -> 0x0143 }
            r3.setPreviewSize(r4, r11)     // Catch:{ all -> 0x0143 }
            java.lang.String r11 = android.os.Build.MODEL     // Catch:{ all -> 0x0143 }
            java.lang.String r4 = "Behold II"
            boolean r11 = r11.contains(r4)     // Catch:{ all -> 0x0143 }
            if (r11 == 0) goto L_0x00a4
            int r11 = a     // Catch:{ all -> 0x0143 }
            if (r11 != r0) goto L_0x00a4
            java.lang.String r11 = "flash-value"
            r3.set(r11, r1)     // Catch:{ all -> 0x0143 }
            goto L_0x00aa
        L_0x00a4:
            java.lang.String r11 = "flash-value"
            r0 = 2
            r3.set(r11, r0)     // Catch:{ all -> 0x0143 }
        L_0x00aa:
            java.lang.String r11 = "flash-mode"
            java.lang.String r0 = "off"
            r3.set(r11, r0)     // Catch:{ all -> 0x0143 }
            java.lang.String r11 = "zoom-supported"
            java.lang.String r11 = r3.get(r11)     // Catch:{ all -> 0x0143 }
            if (r11 == 0) goto L_0x00bf
            boolean r11 = java.lang.Boolean.parseBoolean(r11)     // Catch:{ all -> 0x0143 }
            if (r11 == 0) goto L_0x011e
        L_0x00bf:
            java.lang.String r11 = "max-zoom"
            java.lang.String r11 = r3.get(r11)     // Catch:{ all -> 0x0143 }
            r0 = 27
            r4 = 4621819117588971520(0x4024000000000000, double:10.0)
            if (r11 == 0) goto L_0x00d5
            double r6 = java.lang.Double.parseDouble(r11)     // Catch:{ NumberFormatException -> 0x00d5 }
            double r6 = r6 * r4
            int r6 = (int) r6
            if (r0 <= r6) goto L_0x00d5
            r0 = r6
        L_0x00d5:
            java.lang.String r6 = "taking-picture-zoom-max"
            java.lang.String r6 = r3.get(r6)     // Catch:{ all -> 0x0143 }
            if (r6 == 0) goto L_0x00e4
            int r7 = java.lang.Integer.parseInt(r6)     // Catch:{ NumberFormatException -> 0x00e4 }
            if (r0 <= r7) goto L_0x00e4
            r0 = r7
        L_0x00e4:
            java.lang.String r7 = "mot-zoom-values"
            java.lang.String r7 = r3.get(r7)     // Catch:{ all -> 0x0143 }
            if (r7 == 0) goto L_0x00f0
            int r0 = defpackage.cql.a(r7, r0)     // Catch:{ all -> 0x0143 }
        L_0x00f0:
            java.lang.String r8 = "mot-zoom-step"
            java.lang.String r8 = r3.get(r8)     // Catch:{ all -> 0x0143 }
            if (r8 == 0) goto L_0x0108
            java.lang.String r8 = r8.trim()     // Catch:{ NumberFormatException -> 0x0108 }
            double r8 = java.lang.Double.parseDouble(r8)     // Catch:{ NumberFormatException -> 0x0108 }
            double r8 = r8 * r4
            int r8 = (int) r8     // Catch:{ NumberFormatException -> 0x0108 }
            if (r8 <= r1) goto L_0x0108
            int r8 = r0 % r8
            int r0 = r0 - r8
        L_0x0108:
            if (r11 != 0) goto L_0x010c
            if (r7 == 0) goto L_0x0117
        L_0x010c:
            java.lang.String r11 = "zoom"
            double r7 = (double) r0     // Catch:{ all -> 0x0143 }
            double r7 = r7 / r4
            java.lang.String r4 = java.lang.String.valueOf(r7)     // Catch:{ all -> 0x0143 }
            r3.set(r11, r4)     // Catch:{ all -> 0x0143 }
        L_0x0117:
            if (r6 == 0) goto L_0x011e
            java.lang.String r11 = "taking-picture-zoom"
            r3.set(r11, r0)     // Catch:{ all -> 0x0143 }
        L_0x011e:
            java.lang.Class r11 = r2.getClass()     // Catch:{ Exception -> 0x013e }
            java.lang.String r0 = "setDisplayOrientation"
            java.lang.Class[] r4 = new java.lang.Class[r1]     // Catch:{ Exception -> 0x013e }
            java.lang.Class r5 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x013e }
            r6 = 0
            r4[r6] = r5     // Catch:{ Exception -> 0x013e }
            java.lang.reflect.Method r11 = r11.getMethod(r0, r4)     // Catch:{ Exception -> 0x013e }
            if (r11 == 0) goto L_0x013e
            java.lang.Object[] r0 = new java.lang.Object[r1]     // Catch:{ Exception -> 0x013e }
            r1 = 90
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x013e }
            r0[r6] = r1     // Catch:{ Exception -> 0x013e }
            r11.invoke(r2, r0)     // Catch:{ Exception -> 0x013e }
        L_0x013e:
            r2.setParameters(r3)     // Catch:{ all -> 0x0143 }
        L_0x0141:
            monitor-exit(r10)
            return
        L_0x0143:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cqm.a(android.view.SurfaceHolder):void");
    }

    public final synchronized void b() {
        if (this.i != null) {
            this.i.release();
            this.i = null;
        }
    }

    public final synchronized void c() {
        if (this.i != null && !this.l) {
            this.i.startPreview();
            this.l = true;
        }
    }

    public final synchronized void d() {
        if (this.i != null && this.l) {
            if (!this.m) {
                this.i.setPreviewCallback(null);
            }
            this.i.stopPreview();
            this.n.a(null, 0);
            this.o.a(null, 0);
            this.l = false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0025, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(android.os.Handler r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            android.hardware.Camera r0 = r2.i     // Catch:{ all -> 0x0026 }
            if (r0 == 0) goto L_0x0024
            boolean r0 = r2.l     // Catch:{ all -> 0x0026 }
            if (r0 == 0) goto L_0x0024
            cqn r0 = r2.n     // Catch:{ all -> 0x0026 }
            r1 = 256(0x100, float:3.59E-43)
            r0.a(r3, r1)     // Catch:{ all -> 0x0026 }
            boolean r3 = r2.m     // Catch:{ all -> 0x0026 }
            if (r3 == 0) goto L_0x001d
            android.hardware.Camera r3 = r2.i     // Catch:{ all -> 0x0026 }
            cqn r0 = r2.n     // Catch:{ all -> 0x0026 }
            r3.setOneShotPreviewCallback(r0)     // Catch:{ all -> 0x0026 }
            monitor-exit(r2)
            return
        L_0x001d:
            android.hardware.Camera r3 = r2.i     // Catch:{ all -> 0x0026 }
            cqn r0 = r2.n     // Catch:{ all -> 0x0026 }
            r3.setPreviewCallback(r0)     // Catch:{ all -> 0x0026 }
        L_0x0024:
            monitor-exit(r2)
            return
        L_0x0026:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cqm.a(android.os.Handler):void");
    }

    public final synchronized void b(Handler handler) {
        if (this.i != null && this.l) {
            this.o.a(handler, 257);
            e();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0016, code lost:
        if (r4.k == null) goto L_0x0018;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0018, code lost:
        r4.k = new android.os.Handler();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001f, code lost:
        r4.k.postDelayed(r4.p, 1000);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0029, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0011, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x0014 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void e() {
        /*
            r4 = this;
            monitor-enter(r4)
            android.hardware.Camera r0 = r4.i     // Catch:{ RuntimeException -> 0x0014 }
            if (r0 == 0) goto L_0x0010
            boolean r0 = r4.l     // Catch:{ RuntimeException -> 0x0014 }
            if (r0 == 0) goto L_0x0010
            android.hardware.Camera r0 = r4.i     // Catch:{ RuntimeException -> 0x0014 }
            cqk r1 = r4.o     // Catch:{ RuntimeException -> 0x0014 }
            r0.autoFocus(r1)     // Catch:{ RuntimeException -> 0x0014 }
        L_0x0010:
            monitor-exit(r4)
            return
        L_0x0012:
            r0 = move-exception
            goto L_0x002a
        L_0x0014:
            android.os.Handler r0 = r4.k     // Catch:{ all -> 0x0012 }
            if (r0 != 0) goto L_0x001f
            android.os.Handler r0 = new android.os.Handler     // Catch:{ all -> 0x0012 }
            r0.<init>()     // Catch:{ all -> 0x0012 }
            r4.k = r0     // Catch:{ all -> 0x0012 }
        L_0x001f:
            android.os.Handler r0 = r4.k     // Catch:{ all -> 0x0012 }
            java.lang.Runnable r1 = r4.p     // Catch:{ all -> 0x0012 }
            r2 = 1000(0x3e8, double:4.94E-321)
            r0.postDelayed(r1, r2)     // Catch:{ all -> 0x0012 }
            monitor-exit(r4)
            return
        L_0x002a:
            monitor-exit(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cqm.e():void");
    }

    public final synchronized Rect a(int i2, boolean z) {
        try {
            if (this.j == null || z) {
                cql cql = this.h;
                if (z) {
                    Display defaultDisplay = ((WindowManager) cql.a.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay();
                    cql.b = new Point(defaultDisplay.getWidth(), defaultDisplay.getHeight());
                }
                Point point = cql.b;
                if (this.i == null) {
                    return null;
                }
                int i3 = point.x - 30;
                int i4 = (i2 * 5) / 8;
                int i5 = (point.x - i3) / 2;
                int i6 = i2 - i4;
                int i7 = i6 / 2 > 0 ? i6 / 2 : 0;
                this.j = new Rect(i5, i7, i3 + i5, i4 + i7);
            }
            return this.j;
        }
    }
}
