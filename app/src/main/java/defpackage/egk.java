package defpackage;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.io.IOException;

/* renamed from: egk reason: default package */
/* compiled from: ShareBikeCameraManager */
public final class egk extends dtt {
    private static egk r = new egk();
    private int m;
    private int n;
    private int o;
    private int p;
    private SurfaceTexture q;

    public static egk g() {
        return r;
    }

    private egk() {
        super(AMapPageUtil.getAppContext());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0014, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean h() {
        /*
            r4 = this;
            monitor-enter(r4)
            r0 = 0
            boolean r1 = r4.a()     // Catch:{ Exception -> 0x0017 }
            if (r1 == 0) goto L_0x0013
            android.hardware.Camera r1 = r4.c     // Catch:{ Exception -> 0x0017 }
            boolean r1 = defpackage.dtr.b(r1)     // Catch:{ Exception -> 0x0017 }
            if (r1 == 0) goto L_0x0013
            r0 = 1
            monitor-exit(r4)
            return r0
        L_0x0013:
            monitor-exit(r4)
            return r0
        L_0x0015:
            r0 = move-exception
            goto L_0x0021
        L_0x0017:
            r1 = move-exception
            java.lang.String r2 = "sharebike"
            java.lang.String r3 = "isTorchOpen"
            defpackage.eao.a(r2, r3, r1)     // Catch:{ all -> 0x0015 }
            monitor-exit(r4)
            return r0
        L_0x0021:
            monitor-exit(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.egk.h():boolean");
    }

    public final synchronized Rect e() {
        int i;
        int i2;
        if (this.e == null) {
            if (this.c == null) {
                return null;
            }
            Point point = this.b.a;
            if (point == null) {
                return null;
            }
            int i3 = (point.x * 65) / 100;
            if (this.m != 0) {
                if (this.n != 0) {
                    i2 = (this.m - i3) / 2;
                    i = (((this.n - i3) - this.o) - this.p) / 2;
                    this.e = new Rect(i2, i, i2 + i3, i3 + i);
                }
            }
            i = (point.y - i3) / 2;
            i2 = (point.x - i3) / 2;
            this.e = new Rect(i2, i, i2 + i3, i3 + i);
        }
        return this.e;
    }

    public final synchronized void a(int i, int i2) {
        int i3;
        int i4;
        if (this.g) {
            Point point = this.b.a;
            if (i > point.x) {
                i = point.x;
            }
            if (i2 > point.y) {
                i2 = point.y;
            }
            if (this.m != 0) {
                if (this.n != 0) {
                    i4 = (this.m - i) / 2;
                    i3 = (((this.n - i2) - this.o) - this.p) / 2;
                    this.e = new Rect(i4, i3, i + i4, i2 + i3);
                    this.f = null;
                    return;
                }
            }
            i3 = (point.y - i2) / 2;
            i4 = (point.x - i) / 2;
            this.e = new Rect(i4, i3, i + i4, i2 + i3);
            this.f = null;
            return;
        }
        this.j = i;
        this.k = i2;
    }

    public final synchronized void i() throws IOException {
        String str;
        Camera camera = this.c;
        if (camera == null) {
            camera = dua.a(this.i);
            if (camera == null) {
                throw new IOException();
            }
            this.c = camera;
        }
        if (this.q == null) {
            this.q = new SurfaceTexture(0);
        }
        camera.setPreviewTexture(this.q);
        camera.startPreview();
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

    public final synchronized void b() {
        if (this.q != null) {
            this.q.release();
            this.q = null;
        }
        super.b();
    }
}
