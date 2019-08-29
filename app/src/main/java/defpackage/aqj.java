package defpackage;

import android.view.MotionEvent;
import com.autonavi.common.model.GeoPoint;

/* renamed from: aqj reason: default package */
/* compiled from: MsgBoxService */
public final class aqj implements czj {
    private czj a;

    public final void a(czj czj) {
        this.a = czj;
    }

    public final boolean a() {
        if (this.a == null) {
            return false;
        }
        return this.a.a();
    }

    public final void b() {
        if (this.a != null) {
            this.a.b();
        }
    }

    public final boolean a(MotionEvent motionEvent) {
        if (this.a == null) {
            return false;
        }
        return this.a.a(motionEvent);
    }

    public final boolean a(MotionEvent motionEvent, GeoPoint geoPoint) {
        if (this.a == null) {
            return false;
        }
        return this.a.a(motionEvent, geoPoint);
    }

    public final void c() {
        if (this.a != null) {
            this.a.c();
        }
    }
}
