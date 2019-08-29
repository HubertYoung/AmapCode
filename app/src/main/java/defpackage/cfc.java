package defpackage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.view.View;
import com.amap.bundle.blutils.FileUtil;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;

/* renamed from: cfc reason: default package */
/* compiled from: GLMapViewScreenshot */
public class cfc {
    private static volatile cfc a;

    /* renamed from: cfc$a */
    /* compiled from: GLMapViewScreenshot */
    public interface a {
        void onFailure();

        void onPrepare();

        void onScreenShotFinish(String str);
    }

    /* renamed from: cfc$b */
    /* compiled from: GLMapViewScreenshot */
    static class b implements defpackage.bty.a {
        a a;
        boolean b;
        MapManager c;
        boolean d;
        cfd e;

        b(MapManager mapManager, a aVar, boolean z) {
            this(mapManager, aVar, z, null);
        }

        b(MapManager mapManager, a aVar, boolean z, cfd cfd) {
            this.a = null;
            this.b = true;
            this.d = false;
            this.a = aVar;
            this.b = z;
            this.c = mapManager;
            if (cfd != null) {
                this.e = cfd;
                this.d = false;
                return;
            }
            this.e = cfd.a();
            this.d = true;
        }

        public final void onCallBack(final Bitmap bitmap) {
            if (bitmap == null) {
                if (this.a != null) {
                    aho.a(new Runnable() {
                        public final void run() {
                            b.this.a.onFailure();
                        }
                    });
                }
                return;
            }
            aho.a(new Runnable() {
                public final void run() {
                    final Bitmap bitmap;
                    if (b.this.b) {
                        Bitmap bitmap2 = bitmap;
                        if (bitmap2 != null) {
                            if (VERSION.SDK_INT >= 19) {
                                bitmap2.setConfig(Config.RGB_565);
                            }
                            bitmap2.setDensity(240);
                        }
                        b.this.c.resetMapView(bitmap);
                        Activity activity = DoNotUseTool.getActivity();
                        if (activity != null) {
                            View findViewById = activity.getWindow().findViewById(16908290);
                            int bottom = findViewById.getBottom() - findViewById.getTop();
                            int top = b.this.e.g - findViewById.getTop();
                            try {
                                int i = b.this.e.f;
                                if (b.this.d) {
                                    top = Math.max(top, bottom);
                                }
                                cfd cfd = b.this.e;
                                bitmap = Bitmap.createBitmap(i, top, cfd.h != null ? cfd.h : Config.RGB_565);
                                try {
                                    findViewById.draw(new Canvas(bitmap));
                                } catch (Throwable unused) {
                                }
                            } catch (Throwable unused2) {
                                bitmap = null;
                            }
                            b.this.c.resetMapView(null);
                        } else {
                            return;
                        }
                    } else {
                        bitmap = Bitmap.createBitmap(bitmap);
                    }
                    if (!bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                    ahm.a(new Runnable() {
                        public final void run() {
                            Bitmap bitmap;
                            String saveBitmap;
                            if (bitmap != null && !bitmap.isRecycled()) {
                                final String str = null;
                                try {
                                    bitmap = b.this.e.a ? cfc.a(bitmap, b.this.e) : null;
                                    if (bitmap != null) {
                                        try {
                                            saveBitmap = FileUtil.saveBitmap(bitmap);
                                        } catch (Throwable unused) {
                                        }
                                    } else {
                                        saveBitmap = FileUtil.saveBitmap(bitmap);
                                    }
                                    str = saveBitmap;
                                } catch (Throwable unused2) {
                                    bitmap = null;
                                }
                                if (!bitmap.isRecycled()) {
                                    bitmap.recycle();
                                }
                                if (bitmap != null && !bitmap.isRecycled()) {
                                    bitmap.recycle();
                                }
                                aho.a(new Runnable() {
                                    public final void run() {
                                        b.this.a.onScreenShotFinish(str);
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }
    }

    /* renamed from: cfc$c */
    /* compiled from: GLMapViewScreenshot */
    public interface c {
        void a(String str);
    }

    public static cfc a() {
        if (a == null) {
            synchronized (cfc.class) {
                try {
                    if (a == null) {
                        a = new cfc();
                    }
                }
            }
        }
        return a;
    }

    private cfc() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0020, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(com.autonavi.map.core.MapManager r6, defpackage.cfc.a r7) {
        /*
            r5 = this;
            monitor-enter(r5)
            if (r6 != 0) goto L_0x0005
            monitor-exit(r5)
            return
        L_0x0005:
            r7.onPrepare()     // Catch:{ all -> 0x0021 }
            bty r0 = r6.getMapView()     // Catch:{ all -> 0x0021 }
            if (r0 == 0) goto L_0x001f
            int r1 = r0.al()     // Catch:{ all -> 0x0021 }
            int r2 = r0.am()     // Catch:{ all -> 0x0021 }
            cfc$b r3 = new cfc$b     // Catch:{ all -> 0x0021 }
            r4 = 0
            r3.<init>(r6, r7, r4)     // Catch:{ all -> 0x0021 }
            r0.a(r1, r2, r3)     // Catch:{ all -> 0x0021 }
        L_0x001f:
            monitor-exit(r5)
            return
        L_0x0021:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cfc.a(com.autonavi.map.core.MapManager, cfc$a):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0026, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0021, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void b(com.autonavi.map.core.MapManager r6, defpackage.cfc.a r7) {
        /*
            r5 = this;
            monitor-enter(r5)
            if (r7 == 0) goto L_0x0025
            if (r6 != 0) goto L_0x0006
            goto L_0x0025
        L_0x0006:
            r7.onPrepare()     // Catch:{ all -> 0x0022 }
            bty r0 = r6.getMapView()     // Catch:{ all -> 0x0022 }
            if (r0 == 0) goto L_0x0020
            int r1 = r0.al()     // Catch:{ all -> 0x0022 }
            int r2 = r0.am()     // Catch:{ all -> 0x0022 }
            cfc$b r3 = new cfc$b     // Catch:{ all -> 0x0022 }
            r4 = 1
            r3.<init>(r6, r7, r4)     // Catch:{ all -> 0x0022 }
            r0.a(r1, r2, r3)     // Catch:{ all -> 0x0022 }
        L_0x0020:
            monitor-exit(r5)
            return
        L_0x0022:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        L_0x0025:
            monitor-exit(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cfc.b(com.autonavi.map.core.MapManager, cfc$a):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(com.autonavi.map.core.MapManager r6, defpackage.cfc.a r7, defpackage.cfd r8) {
        /*
            r5 = this;
            monitor-enter(r5)
            if (r6 != 0) goto L_0x0005
            monitor-exit(r5)
            return
        L_0x0005:
            if (r8 != 0) goto L_0x000e
            r5.b(r6, r7)     // Catch:{ all -> 0x000c }
            monitor-exit(r5)
            return
        L_0x000c:
            r6 = move-exception
            goto L_0x002b
        L_0x000e:
            r7.onPrepare()     // Catch:{ all -> 0x000c }
            bty r0 = r6.getMapView()     // Catch:{ all -> 0x000c }
            if (r0 == 0) goto L_0x0029
            int r1 = r0.al()     // Catch:{ all -> 0x000c }
            int r2 = r0.am()     // Catch:{ all -> 0x000c }
            cfc$b r3 = new cfc$b     // Catch:{ all -> 0x000c }
            boolean r4 = r8.i     // Catch:{ all -> 0x000c }
            r3.<init>(r6, r7, r4, r8)     // Catch:{ all -> 0x000c }
            r0.a(r1, r2, r3)     // Catch:{ all -> 0x000c }
        L_0x0029:
            monitor-exit(r5)
            return
        L_0x002b:
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cfc.a(com.autonavi.map.core.MapManager, cfc$a, cfd):void");
    }

    public final synchronized void a(MapManager mapManager, final c cVar) {
        a().b(mapManager, new a() {
            public final void onPrepare() {
            }

            public final void onFailure() {
                cVar.a(null);
            }

            public final void onScreenShotFinish(String str) {
                cVar.a(str);
            }
        });
    }

    static /* synthetic */ Bitmap a(Bitmap bitmap, cfd cfd) {
        int i = cfd.b;
        int i2 = cfd.c;
        int i3 = cfd.d;
        int i4 = cfd.e;
        int width = (i + i3) - bitmap.getWidth();
        int height = (i2 + i4) - bitmap.getHeight();
        if (width > 0) {
            i3 -= width;
        }
        int max = Math.max(1, i3);
        if (height > 0) {
            i4 -= height;
        }
        return Bitmap.createBitmap(bitmap, i, i2, max, Math.max(1, i4));
    }
}
