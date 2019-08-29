package defpackage;

import android.support.annotation.ColorInt;

/* renamed from: gp reason: default package */
/* compiled from: DocumentData */
public final class gp {
    public String a;
    public String b;
    public int c;
    int d;
    public int e;
    double f;
    public double g;
    @ColorInt
    public int h;
    @ColorInt
    public int i;
    public int j;
    public boolean k;

    public gp(String str, String str2, int i2, int i3, int i4, double d2, double d3, @ColorInt int i5, @ColorInt int i6, int i7, boolean z) {
        this.a = str;
        this.b = str2;
        this.c = i2;
        this.d = i3;
        this.e = i4;
        this.f = d2;
        this.g = d3;
        this.h = i5;
        this.i = i6;
        this.j = i7;
        this.k = z;
    }

    public final int hashCode() {
        int hashCode = (((((((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c) * 31) + this.d) * 31) + this.e;
        long doubleToLongBits = Double.doubleToLongBits(this.f);
        return (((hashCode * 31) + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)))) * 31) + this.h;
    }
}
