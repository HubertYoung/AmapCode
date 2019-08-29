package defpackage;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Handler;
import android.view.SurfaceHolder;
import com.amap.bundle.blutils.log.DebugLog;
import com.google.zxing.PlanarYUVLuminanceSource;
import java.io.IOException;

/* renamed from: dtt reason: default package */
/* compiled from: CameraManager */
public class dtt {
    protected final Context a;
    protected final dtr b;
    protected Camera c;
    protected dtq d;
    protected Rect e;
    protected Rect f;
    protected boolean g;
    protected boolean h;
    protected int i = -1;
    protected int j;
    protected int k;
    protected final dub l;
    private final dtp m;

    public dtt(Context context) {
        this.a = context;
        this.b = new dtr(context);
        this.l = new dub(this.b);
        this.m = new dtp();
    }

    public final synchronized void a(SurfaceHolder surfaceHolder) throws IOException {
        String str;
        Camera camera = this.c;
        if (camera == null) {
            camera = dua.a(this.i);
            if (camera == null) {
                throw new IOException();
            }
            this.c = camera;
        }
        camera.setPreviewDisplay(surfaceHolder);
        camera.setDisplayOrientation(90);
        if (!this.g) {
            this.g = true;
            this.b.a(camera);
            if (this.j > 0 && this.k > 0) {
                a(this.j, this.k);
                this.j = 0;
                this.k = 0;
            }
        }
        Parameters parameters = camera.getParameters();
        if (parameters == null) {
            str = null;
        } else {
            str = parameters.flatten();
        }
        try {
            this.b.a(camera, false);
        } catch (RuntimeException unused) {
            if (str != null) {
                Parameters parameters2 = camera.getParameters();
                parameters2.unflatten(str);
                try {
                    camera.setParameters(parameters2);
                    this.b.a(camera, true);
                } catch (RuntimeException unused2) {
                }
            }
        }
    }

    public final synchronized boolean a() {
        return this.c != null;
    }

    public synchronized void b() {
        if (this.c != null) {
            this.c.release();
            this.c = null;
            this.e = null;
            this.f = null;
        }
    }

    public final synchronized void c() {
        Camera camera = this.c;
        if (camera != null && !this.h) {
            try {
                camera.startPreview();
                this.h = true;
                this.d = new dtq(this.a, this.c);
            } catch (Exception unused) {
            }
        }
    }

    public final synchronized void d() {
        if (this.d != null) {
            this.d.b();
            this.d = null;
        }
        if (this.c != null && this.h) {
            this.c.stopPreview();
            this.l.a(null, 0);
            this.m.a(null, 0);
            this.h = false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0052, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(boolean r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            android.hardware.Camera r0 = r4.c     // Catch:{ all -> 0x0055 }
            boolean r0 = defpackage.dtr.b(r0)     // Catch:{ all -> 0x0055 }
            if (r5 == r0) goto L_0x0053
            android.hardware.Camera r0 = r4.c     // Catch:{ all -> 0x0055 }
            if (r0 == 0) goto L_0x0053
            dtq r0 = r4.d     // Catch:{ all -> 0x0055 }
            if (r0 == 0) goto L_0x0013
            r0 = 1
            goto L_0x0014
        L_0x0013:
            r0 = 0
        L_0x0014:
            if (r0 == 0) goto L_0x001e
            dtq r1 = r4.d     // Catch:{ all -> 0x0055 }
            r1.b()     // Catch:{ all -> 0x0055 }
            r1 = 0
            r4.d = r1     // Catch:{ all -> 0x0055 }
        L_0x001e:
            dtr r1 = r4.b     // Catch:{ Exception -> 0x002d }
            android.hardware.Camera r2 = r4.c     // Catch:{ Exception -> 0x002d }
            android.hardware.Camera$Parameters r3 = r2.getParameters()     // Catch:{ Exception -> 0x002d }
            r1.a(r3, r5)     // Catch:{ Exception -> 0x002d }
            r2.setParameters(r3)     // Catch:{ Exception -> 0x002d }
            goto L_0x0035
        L_0x002d:
            r5 = move-exception
            java.lang.String r1 = "qrcode"
            java.lang.String r2 = "setTorch"
            com.amap.bundle.blutils.log.DebugLog.e(r1, r2, r5)     // Catch:{ all -> 0x0055 }
        L_0x0035:
            if (r0 == 0) goto L_0x0051
            dtq r5 = new dtq     // Catch:{ Exception -> 0x0048 }
            android.content.Context r0 = r4.a     // Catch:{ Exception -> 0x0048 }
            android.hardware.Camera r1 = r4.c     // Catch:{ Exception -> 0x0048 }
            r5.<init>(r0, r1)     // Catch:{ Exception -> 0x0048 }
            r4.d = r5     // Catch:{ Exception -> 0x0048 }
            dtq r5 = r4.d     // Catch:{ Exception -> 0x0048 }
            r5.a()     // Catch:{ Exception -> 0x0048 }
            goto L_0x0051
        L_0x0048:
            r5 = move-exception
            java.lang.String r0 = "qrcode"
            java.lang.String r1 = "init AutoFocusManager"
            com.amap.bundle.blutils.log.DebugLog.e(r0, r1, r5)     // Catch:{ all -> 0x0055 }
            goto L_0x0053
        L_0x0051:
            monitor-exit(r4)
            return
        L_0x0053:
            monitor-exit(r4)
            return
        L_0x0055:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dtt.a(boolean):void");
    }

    public final synchronized void a(Handler handler, int i2) {
        Camera camera = this.c;
        if (camera != null && this.h) {
            this.l.a(handler, i2);
            camera.setOneShotPreviewCallback(this.l);
        }
    }

    public final void b(Handler handler, int i2) {
        if (this.c != null && this.h) {
            try {
                this.m.a(handler, i2);
                this.c.autoFocus(this.m);
            } catch (Exception e2) {
                DebugLog.e("qrcode", "requestAutoFocus", e2);
            }
        }
    }

    public synchronized Rect e() {
        if (this.e == null) {
            if (this.c == null) {
                return null;
            }
            Point point = this.b.a;
            if (point == null) {
                return null;
            }
            int i2 = (point.x * 5) / 8;
            if (i2 < 240) {
                i2 = 240;
            } else if (i2 > 675) {
                i2 = 675;
            }
            int i3 = (point.x - i2) / 2;
            int i4 = (point.y - i2) / 2;
            this.e = new Rect(i3, i4, i3 + i2, i2 + i4);
        }
        return this.e;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0050, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized android.graphics.Rect f() {
        /*
            r5 = this;
            monitor-enter(r5)
            android.graphics.Rect r0 = r5.f     // Catch:{ all -> 0x0055 }
            if (r0 != 0) goto L_0x0051
            android.graphics.Rect r0 = r5.e()     // Catch:{ all -> 0x0055 }
            r1 = 0
            if (r0 != 0) goto L_0x000e
            monitor-exit(r5)
            return r1
        L_0x000e:
            dtr r2 = r5.b     // Catch:{ all -> 0x0055 }
            android.graphics.Point r2 = r2.b     // Catch:{ all -> 0x0055 }
            dtr r3 = r5.b     // Catch:{ all -> 0x0055 }
            android.graphics.Point r3 = r3.a     // Catch:{ all -> 0x0055 }
            if (r2 == 0) goto L_0x004f
            if (r3 != 0) goto L_0x001b
            goto L_0x004f
        L_0x001b:
            android.graphics.Rect r1 = new android.graphics.Rect     // Catch:{ all -> 0x0055 }
            r1.<init>(r0)     // Catch:{ all -> 0x0055 }
            int r0 = r1.left     // Catch:{ all -> 0x0055 }
            int r4 = r2.y     // Catch:{ all -> 0x0055 }
            int r0 = r0 * r4
            int r4 = r3.x     // Catch:{ all -> 0x0055 }
            int r0 = r0 / r4
            r1.left = r0     // Catch:{ all -> 0x0055 }
            int r0 = r1.right     // Catch:{ all -> 0x0055 }
            int r4 = r2.y     // Catch:{ all -> 0x0055 }
            int r0 = r0 * r4
            int r4 = r3.x     // Catch:{ all -> 0x0055 }
            int r0 = r0 / r4
            r1.right = r0     // Catch:{ all -> 0x0055 }
            int r0 = r1.top     // Catch:{ all -> 0x0055 }
            int r4 = r2.x     // Catch:{ all -> 0x0055 }
            int r0 = r0 * r4
            int r4 = r3.y     // Catch:{ all -> 0x0055 }
            int r0 = r0 / r4
            r1.top = r0     // Catch:{ all -> 0x0055 }
            int r0 = r1.bottom     // Catch:{ all -> 0x0055 }
            int r2 = r2.x     // Catch:{ all -> 0x0055 }
            int r0 = r0 * r2
            int r2 = r3.y     // Catch:{ all -> 0x0055 }
            int r0 = r0 / r2
            r1.bottom = r0     // Catch:{ all -> 0x0055 }
            r5.f = r1     // Catch:{ all -> 0x0055 }
            goto L_0x0051
        L_0x004f:
            monitor-exit(r5)
            return r1
        L_0x0051:
            android.graphics.Rect r0 = r5.f     // Catch:{ all -> 0x0055 }
            monitor-exit(r5)
            return r0
        L_0x0055:
            r0 = move-exception
            monitor-exit(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dtt.f():android.graphics.Rect");
    }

    public synchronized void a(int i2, int i3) {
        if (this.g) {
            Point point = this.b.a;
            if (i2 > point.x) {
                i2 = point.x;
            }
            if (i3 > point.y) {
                i3 = point.y;
            }
            int i4 = (point.x - i2) / 2;
            int i5 = (point.y - i3) / 2;
            this.e = new Rect(i4, i5, i2 + i4, i3 + i5);
            this.f = null;
            return;
        }
        this.j = i2;
        this.k = i3;
    }

    public final PlanarYUVLuminanceSource a(byte[] bArr, int i2, int i3) {
        Rect f2 = f();
        if (f2 == null) {
            return null;
        }
        try {
            PlanarYUVLuminanceSource planarYUVLuminanceSource = new PlanarYUVLuminanceSource(bArr, i2, i3, f2.left, f2.top, f2.width(), f2.height(), false);
            return planarYUVLuminanceSource;
        } catch (Exception unused) {
            return null;
        }
    }
}
