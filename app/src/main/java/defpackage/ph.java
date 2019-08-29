package defpackage;

import com.autonavi.common.model.POI;
import java.util.ArrayList;
import java.util.List;

/* renamed from: ph reason: default package */
/* compiled from: CarRouteResult */
public final class ph implements Cloneable {
    public long[] a;
    public int b;
    public long c;
    public int d;
    public int e = 0;
    public List<pg> f;
    public POI g;
    public POI h;
    public List<POI> i;
    public te j;

    public final pg a(int i2) {
        return this.f.get(i2);
    }

    public final pg a() {
        return this.f.get(this.e);
    }

    public final boolean b() {
        if (this.h == null || this.h.getPoiExtra().get("key_end_poi_source_type") == null || ((Integer) this.h.getPoiExtra().get("key_end_poi_source_type")).intValue() != 1) {
            return true;
        }
        return false;
    }

    /* renamed from: c */
    public final ph clone() throws CloneNotSupportedException {
        ph phVar = (ph) super.clone();
        phVar.g = this.g.clone();
        phVar.h = this.h.clone();
        if (this.i != null && this.i.size() > 0) {
            int size = this.i.size();
            phVar.i = new ArrayList();
            for (int i2 = 0; i2 < size; i2++) {
                phVar.i.add(this.i.get(i2).clone());
            }
        }
        return phVar;
    }
}
