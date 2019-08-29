package defpackage;

import java.util.Arrays;

/* renamed from: ayy reason: default package */
/* compiled from: LngLatBound */
public final class ayy {
    ayx a;
    ayx b;

    /* renamed from: ayy$a */
    /* compiled from: LngLatBound */
    public static class a {
        double a = 2.147483647E9d;
        double b = 2.147483647E9d;
        double c = -2.147483648E9d;
        double d = -2.147483648E9d;
    }

    /* synthetic */ ayy(ayx ayx, ayx ayx2, byte b2) {
        this(ayx, ayx2);
    }

    ayy(ayx ayx, ayx ayx2) {
        this.a = ayx;
        this.b = ayx2;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ayy)) {
            return false;
        }
        ayy ayy = (ayy) obj;
        if (!this.a.equals(ayy.a) || !this.b.equals(ayy.b)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.a, this.b});
    }

    public final String toString() {
        Object obj;
        Object obj2;
        Object obj3;
        StringBuilder sb = new StringBuilder("southwest:");
        sb.append(this.a == null ? "null" : Double.valueOf(this.a.a));
        sb.append(",");
        if (this.a == null) {
            obj = "null";
        } else {
            obj = Double.valueOf(this.a.b);
        }
        sb.append(obj);
        sb.append("\nnortheast:");
        if (this.b == null) {
            obj2 = "null";
        } else {
            obj2 = Double.valueOf(this.b.a);
        }
        sb.append(obj2);
        sb.append(",");
        if (this.b == null) {
            obj3 = "null";
        } else {
            obj3 = Double.valueOf(this.b.b);
        }
        sb.append(obj3);
        return sb.toString();
    }
}
