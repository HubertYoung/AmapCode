package defpackage;

import com.autonavi.jni.ae.bl.map.IMapPage;

/* renamed from: bub reason: default package */
/* compiled from: IMapVirtualizationPageImpl */
public final class bub implements bua {
    final IMapPage a;

    public bub(IMapPage iMapPage) {
        this.a = iMapPage;
    }

    public final void a() {
        if (this.a != null) {
            this.a.onCreate();
        }
    }

    public final void b() {
        if (this.a != null) {
            this.a.onStart();
        }
    }

    public final void c() {
        if (this.a != null) {
            this.a.onStop();
        }
    }

    public final void d() {
        if (this.a != null) {
            this.a.onDestroy();
        }
    }

    public final void a(int i, long j, long j2) {
        if (this.a != null) {
            this.a.addOverlay((long) i, j, j2);
        }
    }

    public final void a(int i, long j) {
        if (this.a != null) {
            this.a.removeOverlay((long) i, j);
        }
    }

    public final void a(long j) {
        if (this.a != null) {
            this.a.setMapStateVirtualFlags(j);
        }
    }

    public final long e() {
        if (this.a == null) {
            return 0;
        }
        return this.a.getMapStateVirtualFlags();
    }
}
