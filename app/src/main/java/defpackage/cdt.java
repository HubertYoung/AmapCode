package defpackage;

import java.util.Collections;
import java.util.List;

/* renamed from: cdt reason: default package */
/* compiled from: MapIndoorFloorAdapter */
public final class cdt implements cdk {
    private List<cds> a;

    public cdt(List<cds> list) {
        this.a = list;
        Collections.sort(this.a, new cdu());
    }

    public final int a() {
        if (this.a == null) {
            return 0;
        }
        return this.a.size();
    }

    public final Object a(int i) {
        if (this.a == null || i < 0 || i >= this.a.size()) {
            return null;
        }
        return this.a.get(i);
    }

    public final int b() {
        int i = 0;
        for (cds next : this.a) {
            if (next.toString().length() > i) {
                i = next.toString().length();
            }
        }
        return i;
    }
}
