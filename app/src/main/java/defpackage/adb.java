package defpackage;

import com.autonavi.common.model.POI;
import java.util.ArrayList;
import java.util.List;

/* renamed from: adb reason: default package */
/* compiled from: PlanData */
public final class adb implements Cloneable {
    public POI a;
    public POI b = null;
    public List<POI> c = null;

    public final List<POI> a() {
        if (this.c == null || this.c.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList(this.c.size());
        for (POI clone : this.c) {
            arrayList.add(clone.clone());
        }
        return arrayList;
    }

    public final Object clone() {
        try {
            adb adb = (adb) super.clone();
            if (this.a != null) {
                adb.a = this.a.clone();
            }
            if (this.b != null) {
                adb.b = this.b.clone();
            }
            if (this.c != null) {
                adb.c = new ArrayList(this.c.size());
                for (POI clone : this.c) {
                    adb.c.add(clone.clone());
                }
            }
            return adb;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    public static POI a(POI poi) {
        if (poi != null) {
            return poi.clone();
        }
        return null;
    }
}
