package defpackage;

import android.view.MotionEvent;
import com.autonavi.common.model.GeoPoint;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* renamed from: bdw reason: default package */
/* compiled from: MainMapEventListenerExt */
public class bdw extends awc {
    public static final String a = "bdw";
    private WeakReference<b> b;

    /* renamed from: bdw$a */
    /* compiled from: MainMapEventListenerExt */
    public static class a implements b {
        public bty a() {
            return null;
        }

        public void a(GeoPoint geoPoint) {
        }

        public boolean a(List<als> list) {
            return false;
        }

        public boolean b() {
            return false;
        }

        public boolean c() {
            return false;
        }
    }

    /* renamed from: bdw$b */
    /* compiled from: MainMapEventListenerExt */
    public interface b {
        bty a();

        void a(GeoPoint geoPoint);

        boolean a(List<als> list);

        boolean b();

        boolean c();
    }

    public bdw(b bVar) {
        this.b = new WeakReference<>(bVar);
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        b bVar = (b) this.b.get();
        boolean z = false;
        if (bVar == null) {
            return false;
        }
        bty a2 = bVar.a();
        if (a2 == null) {
            return false;
        }
        if (bVar.b()) {
            ArrayList<als> d = a2.d((int) motionEvent.getX(), (int) motionEvent.getY());
            if (d != null && d.size() > 0) {
                z = bVar.a((List<als>) d);
            }
        }
        return z;
    }

    public void onLongPress(MotionEvent motionEvent) {
        b bVar = (b) this.b.get();
        if (bVar != null) {
            bty a2 = bVar.a();
            if (a2 != null && bVar.c()) {
                GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(a2.c((int) motionEvent.getX(), (int) motionEvent.getY()));
                if (glGeoPoint2GeoPoint != null) {
                    bVar.a(glGeoPoint2GeoPoint);
                }
            }
        }
    }
}
