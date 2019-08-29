package defpackage;

import android.text.TextUtils;
import com.autonavi.minimap.R;
import com.autonavi.sync.beans.GirfFavoritePoint;

/* renamed from: cpb reason: default package */
/* compiled from: SimplePoint */
public final class cpb {
    public String a;
    public String b;
    public String c;
    public int d;
    public int e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public String k;
    public String l;
    public String m;
    private String n;
    private boolean o;

    public cpb(GirfFavoritePoint girfFavoritePoint) {
        if (girfFavoritePoint != null) {
            this.a = String.valueOf(girfFavoritePoint.id);
            this.b = girfFavoritePoint.item_id;
            this.c = girfFavoritePoint.name;
            try {
                if (!TextUtils.isEmpty(girfFavoritePoint.px)) {
                    this.d = Integer.parseInt(girfFavoritePoint.px);
                }
                if (!TextUtils.isEmpty(girfFavoritePoint.py)) {
                    this.e = Integer.parseInt(girfFavoritePoint.py);
                }
            } catch (NumberFormatException unused) {
            }
            this.f = girfFavoritePoint.cityName;
            this.h = girfFavoritePoint.tag;
            this.g = girfFavoritePoint.cityName;
            this.i = girfFavoritePoint.newType;
            this.k = girfFavoritePoint.commonName;
            StringBuilder sb = new StringBuilder();
            sb.append(girfFavoritePoint.topTime);
            this.n = sb.toString();
            this.l = girfFavoritePoint.address;
            this.m = girfFavoritePoint.customName;
            this.j = girfFavoritePoint.classification;
        }
    }

    public cpb(String str) {
        this(bim.aa().f(str));
    }

    public final int c() {
        if (e()) {
            return R.drawable.save_gongsi;
        }
        if (d()) {
            return R.drawable.save_jia;
        }
        return cru.b(this.j);
    }

    public final boolean f() {
        return d() || e();
    }

    public final boolean a() {
        if ("0".equals(this.n) || f()) {
            this.o = false;
            return false;
        }
        this.o = true;
        return true;
    }

    public final boolean b() {
        return (this.d == 0 && this.e == 0) ? false : true;
    }

    public final boolean d() {
        if (!TextUtils.isEmpty(this.k)) {
            return this.k.equals(crt.b);
        }
        return false;
    }

    public final boolean e() {
        if (!TextUtils.isEmpty(this.k)) {
            return this.k.equals(crt.c);
        }
        return false;
    }
}
