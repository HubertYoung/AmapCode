package defpackage;

import android.text.TextUtils;
import com.amap.bundle.datamodel.FavoritePOI;
import com.autonavi.common.model.POI;
import java.util.Comparator;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bth reason: default package */
/* compiled from: SavePoint */
public class bth implements Comparable<bth> {
    public static Comparator<bth> h = new Comparator<bth>() {
        public final /* synthetic */ int compare(Object obj, Object obj2) {
            return ((bth) obj).f.compareTo(((bth) obj2).f);
        }
    };
    public static Comparator<bth> i = new Comparator<bth>() {
        public final /* synthetic */ int compare(Object obj, Object obj2) {
            return ((bth) obj2).f.compareTo(((bth) obj).f);
        }
    };
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public Long f;
    public Object g;
    private POI j;

    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return this.f.compareTo(((bth) obj).f);
    }

    public final void a(POI poi) {
        this.j = poi;
        if (poi != null) {
            this.e = poi.getId();
            coo coo = (coo) ank.a(coo.class);
            if (coo != null) {
                this.c = coo.a(poi).toString();
            }
            this.d = ((FavoritePOI) poi.as(FavoritePOI.class)).getCommonName();
        }
    }

    public final POI a() {
        if (this.j == null && !TextUtils.isEmpty(this.c)) {
            try {
                coo coo = (coo) ank.a(coo.class);
                if (coo != null) {
                    this.j = coo.a(new JSONObject(this.c));
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return this.j;
    }
}
