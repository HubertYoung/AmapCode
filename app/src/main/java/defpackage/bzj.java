package defpackage;

import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.search.view.SlidingUpPanelLayout.SlideState;
import java.util.ArrayList;
import java.util.List;

/* renamed from: bzj reason: default package */
/* compiled from: SearchResultStateManager */
public final class bzj {
    List<a> a = new ArrayList();

    /* renamed from: bzj$a */
    /* compiled from: SearchResultStateManager */
    public static class a {
        public int a;
        public SlideState b;
        public float c;
        public float d;
        public GLGeoPoint e = new GeoPoint();
        public POI f;
    }

    public final a a(int i) {
        a aVar = new a();
        aVar.a = i;
        this.a.add(aVar);
        return aVar;
    }

    public final a a() {
        if (this.a.size() > 0) {
            return this.a.remove(this.a.size() - 1);
        }
        return null;
    }

    public final a b() {
        if (this.a.size() > 0) {
            return this.a.get(this.a.size() - 1);
        }
        return null;
    }

    public final void a(float f, float f2, GLGeoPoint gLGeoPoint) {
        if (!this.a.isEmpty()) {
            a aVar = this.a.get(0);
            aVar.c = f;
            aVar.d = f2;
            aVar.e = gLGeoPoint;
        }
    }

    public final a c() {
        if (this.a.isEmpty()) {
            return null;
        }
        return this.a.get(0);
    }
}
