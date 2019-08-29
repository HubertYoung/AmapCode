package defpackage;

import android.view.MotionEvent;
import com.autonavi.bundle.mapevent.listener.MainMapEventListener;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle.GLAmapFocusHits;
import com.autonavi.map.suspend.refactor.gps.GPSButton;

/* renamed from: daf reason: default package */
/* compiled from: GpsManagerImpl */
public class daf implements cef {
    cea a;
    private cdv b;
    private GPSButton c;
    private ceb d;

    public final void a(cdc cdc) {
        this.c = new GPSButton(cdc.a(), null);
        this.b = new cdv(cdc);
        this.b.a((ced) this.c);
        this.a = new cea(cdc, this.b);
        this.d = new ceb(cdc.a(), this.b, this.a, cdc.b());
        awb awb = (awb) a.a.a(awb.class);
        if (awb != null) {
            awb.a((MainMapEventListener) new awc() {
                public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                    daf.this.a.d();
                    return false;
                }

                public final void onLineOverlayClick(GLAmapFocusHits gLAmapFocusHits) {
                    daf.this.a.d();
                }

                public final void onPointOverlayClick(GLAmapFocusHits gLAmapFocusHits) {
                    daf.this.a.d();
                }

                public final void onUserMapTouchEvent(MotionEvent motionEvent) {
                    daf.this.a.d();
                }
            });
        }
    }

    public final ced a() {
        return this.c;
    }

    public final void a(ced ced) {
        a(ced, null);
    }

    public final void a(ced ced, cee cee) {
        if (this.d != null) {
            this.d.d = ced.getLogVersionState();
            if (cee != null) {
                this.d.c = cee;
            }
        }
        this.b.a(ced);
    }

    public final void b(ced ced) {
        this.b.b(ced);
    }

    public final int b() {
        return this.b.e();
    }

    public final void a(int i) {
        this.b.b(i);
    }

    public final boolean c() {
        return this.b.f();
    }

    public final void d() {
        this.a.b();
    }

    public final void e() {
        this.a.c();
    }

    public final void f() {
        this.a.d();
    }

    public final void a(boolean z) {
        this.a.b(z);
    }

    public final boolean g() {
        return this.a.g();
    }

    public final void b(boolean z) {
        this.a.c(z);
    }

    public final void a(a aVar) {
        this.a.a(aVar);
    }

    public final void b(int i) {
        this.a.a(i);
    }

    public final void a(a aVar) {
        this.d.e = aVar;
    }

    public final void i() {
        this.b.g();
    }

    public final /* bridge */ /* synthetic */ ceg h() {
        return this.a;
    }
}
