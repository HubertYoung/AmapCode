package defpackage;

import com.autonavi.common.model.POI;
import java.util.List;

/* renamed from: dhx reason: default package */
/* compiled from: DriveRequestParam */
public final class dhx {
    public POI a;
    public List<POI> b;
    public POI c;
    public boolean d = true;
    public boolean e = false;
    public int f;
    public String g;
    public String h;

    public dhx(POI poi, POI poi2) {
        this.a = poi;
        this.c = poi2;
    }

    public dhx(POI poi, POI poi2, List<POI> list) {
        this.a = poi;
        this.c = poi2;
        this.b = list;
    }
}
