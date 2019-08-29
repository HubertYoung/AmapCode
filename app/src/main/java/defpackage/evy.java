package defpackage;

import android.content.Context;

/* renamed from: evy reason: default package */
/* compiled from: PostObjAction */
public class evy {
    static final String a = "evy";
    String b;
    String c;
    String d;
    private String e;
    private String f;
    private String g;

    public evy(String str, String str2, Context context) {
        this.b = str;
        this.c = str2;
        this.e = euw.a();
        this.d = euw.a(context, 1);
        this.g = euw.d(context);
        this.f = euw.n(context);
    }

    public evy(String str, String str2, String str3, String str4, String str5, String str6) {
        this.b = str;
        this.c = str2;
        this.e = str3;
        this.d = str4;
        this.f = str5;
        this.g = str6;
    }

    public int hashCode() {
        int i;
        int i2;
        int i3;
        int i4 = 0;
        if (this.c == null) {
            i = 0;
        } else {
            i = this.c.hashCode();
        }
        int i5 = (i + 31) * 31;
        if (this.d == null) {
            i2 = 0;
        } else {
            i2 = this.d.hashCode();
        }
        int hashCode = (((i5 + i2) * 31) + (this.g == null ? 0 : this.g.hashCode())) * 31;
        if (this.b == null) {
            i3 = 0;
        } else {
            i3 = this.b.hashCode();
        }
        int hashCode2 = (((hashCode + i3) * 31) + (this.e == null ? 0 : this.e.hashCode())) * 31;
        if (this.f != null) {
            i4 = this.f.hashCode();
        }
        return hashCode2 + i4;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        evy evy = (evy) obj;
        if (this.c == null) {
            if (evy.c != null) {
                return false;
            }
        } else if (!this.c.equals(evy.c)) {
            return false;
        }
        if (this.d == null) {
            if (evy.d != null) {
                return false;
            }
        } else if (!this.d.equals(evy.d)) {
            return false;
        }
        if (this.g == null) {
            if (evy.g != null) {
                return false;
            }
        } else if (!this.g.equals(evy.g)) {
            return false;
        }
        if (this.b == null) {
            if (evy.b != null) {
                return false;
            }
        } else if (!this.b.equals(evy.b)) {
            return false;
        }
        if (this.e == null) {
            if (evy.e != null) {
                return false;
            }
        } else if (!this.e.equals(evy.e)) {
            return false;
        }
        if (this.f == null) {
            if (evy.f != null) {
                return false;
            }
        } else if (!this.f.equals(evy.f)) {
            return false;
        }
        return true;
    }
}
