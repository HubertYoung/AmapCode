package defpackage;

/* renamed from: ayx reason: default package */
/* compiled from: LngLat */
public final class ayx {
    public double a;
    public double b;

    public ayx(double d, double d2) {
        this.a = d;
        this.b = d2;
    }

    public final int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(this.b);
        long doubleToLongBits2 = Double.doubleToLongBits(this.a);
        return (((int) (doubleToLongBits ^ (doubleToLongBits >>> 32))) * 31) + ((int) ((doubleToLongBits2 >>> 32) ^ doubleToLongBits2));
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ayx)) {
            return false;
        }
        ayx ayx = (ayx) obj;
        if (this.a == ayx.a && this.b == ayx.b) {
            return true;
        }
        return false;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(",");
        sb.append(this.b);
        return sb.toString();
    }
}
