package defpackage;

import com.autonavi.bundle.entity.infolite.external.CitySuggestion;
import com.autonavi.common.model.POI;
import java.util.ArrayList;

/* renamed from: auc reason: default package */
/* compiled from: PoiSearchInfo */
public final class auc implements Cloneable {
    public aub a;
    public ArrayList<String> b;
    public ArrayList<CitySuggestion> c;
    public ArrayList<POI> d;
    public int e = 0;
    public POI f;
    public boolean g;

    public final Object clone() throws CloneNotSupportedException {
        auc auc = (auc) super.clone();
        if (this.d != null) {
            auc.d = (ArrayList) this.d.clone();
        }
        return auc;
    }
}
