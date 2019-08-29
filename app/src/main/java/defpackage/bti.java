package defpackage;

import android.text.TextUtils;
import com.autonavi.common.model.POI;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bti reason: default package */
/* compiled from: SaveRoute */
public class bti implements cor, Comparable<bti> {
    public String a;
    public String b;
    public int c = 0;
    public int d = 0;
    public int e = 0;
    public int f = 0;
    public int g = 0;
    public String h;
    public String i = "1.0.0";
    public String j;
    public int k;
    public int l = 0;
    public String m;
    public String n;
    public String o;
    public boolean p = false;
    public String q;
    public String r;
    public Long s;
    public Integer t;
    public int u;
    public Object v;
    public POI w;
    public POI x;
    public ArrayList<POI> y = null;

    public static String a(int i2) {
        switch (i2) {
            case 0:
                return "105";
            case 1:
                return "102";
            case 2:
                return "103";
            case 3:
                return "104";
            default:
                return "";
        }
    }

    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return this.s.compareTo(((bti) obj).s);
    }

    public final void a(POI poi) {
        this.w = poi;
        this.m = poi != null ? btf.a(poi) : null;
    }

    public final POI a() {
        if (this.w == null && !TextUtils.isEmpty(this.m)) {
            this.w = btf.a(this.m);
        }
        return this.w;
    }

    public final void b(POI poi) {
        this.x = poi;
        this.n = poi != null ? btf.a(poi) : null;
    }

    public final POI b() {
        if (this.x == null && !TextUtils.isEmpty(this.n)) {
            this.x = btf.a(this.n);
        }
        return this.x;
    }

    public final void a(ArrayList<POI> arrayList) {
        this.y = arrayList;
        this.o = arrayList != null ? btf.a((List<POI>) arrayList) : null;
    }

    public final ArrayList<POI> c() {
        if (this.y == null && !TextUtils.isEmpty(this.o)) {
            this.y = btf.b(this.o);
        }
        return this.y;
    }

    public final void b(ArrayList<POI> arrayList) {
        this.y = arrayList;
        this.o = btf.a((List<POI>) arrayList);
    }

    public final void a(int i2, Object obj) {
        this.c = i2;
        this.v = obj;
        if (obj != null) {
            switch (i2) {
                case 0:
                case 2:
                case 3:
                    btr btr = (btr) ank.a(btr.class);
                    if (btr != null) {
                        this.r = btr.a(obj, i2);
                        break;
                    }
                    break;
                case 1:
                    dhz dhz = (dhz) ank.a(dhz.class);
                    if (dhz != null) {
                        this.r = dhz.a(obj);
                        return;
                    }
                    break;
            }
        }
    }

    public final Object d() {
        if (this.v == null && !TextUtils.isEmpty(this.r)) {
            try {
                switch (this.c) {
                    case 0:
                    case 2:
                    case 3:
                        btr btr = (btr) ank.a(btr.class);
                        if (btr != null) {
                            this.v = btr.a(this.r, this.c);
                            break;
                        }
                        break;
                    case 1:
                        dhz dhz = (dhz) ank.a(dhz.class);
                        if (dhz != null) {
                            this.v = dhz.a(new JSONObject(this.r), false);
                            break;
                        }
                        break;
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return this.v;
    }

    public final boolean e() {
        this.v = d();
        if (this.v == null) {
            return false;
        }
        switch (this.c) {
            case 0:
            case 2:
            case 3:
                btr btr = (btr) ank.a(btr.class);
                if (btr != null) {
                    return btr.b(this.v, this.c);
                }
                break;
            case 1:
                return ((dia) this.v).hasLinePoints();
        }
        return false;
    }

    public final String f() {
        return this.a;
    }

    public final int g() {
        return this.c;
    }

    public final String h() {
        return this.h;
    }
}
