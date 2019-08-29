package org.altbeacon.beacon;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.altbeacon.beacon.a.a;
import org.altbeacon.beacon.a.b;
import org.altbeacon.beacon.b.d;
import org.altbeacon.beacon.distance.c;

public class Beacon implements Parcelable, Serializable {
    @Deprecated
    public static final Creator<Beacon> CREATOR = new c();
    protected static boolean a = false;
    protected static c b = null;
    protected static a j = new b();
    private static final List<Long> q = Collections.unmodifiableList(new ArrayList());
    private static final List<l> r = Collections.unmodifiableList(new ArrayList());
    protected List<l> c;
    protected List<Long> d;
    protected List<Long> e;
    protected Double f;
    protected int g;
    protected int h;
    protected String i;
    protected int k;
    protected int l;
    protected int m;
    protected String n;
    protected String o;
    protected boolean p;
    private Double s;

    public static void a(c dc) {
        b = dc;
    }

    private static c k() {
        return b;
    }

    public static void a(boolean e2) {
        a = e2;
    }

    public static boolean a() {
        return a;
    }

    @Deprecated
    protected Beacon(Parcel in) {
        boolean z = false;
        this.s = null;
        this.m = -1;
        this.p = false;
        int size = in.readInt();
        this.c = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            this.c.add(l.a(in.readString()));
        }
        this.f = Double.valueOf(in.readDouble());
        this.g = in.readInt();
        this.h = in.readInt();
        this.i = in.readString();
        this.k = in.readInt();
        this.m = in.readInt();
        int dataSize = in.readInt();
        this.d = new ArrayList(dataSize);
        for (int i3 = 0; i3 < dataSize; i3++) {
            this.d.add(Long.valueOf(in.readLong()));
        }
        int extraDataSize = in.readInt();
        this.e = new ArrayList(extraDataSize);
        for (int i4 = 0; i4 < extraDataSize; i4++) {
            this.e.add(Long.valueOf(in.readLong()));
        }
        this.l = in.readInt();
        this.n = in.readString();
        this.o = in.readString();
        this.p = in.readByte() != 0 ? true : z;
    }

    protected Beacon() {
        this.s = null;
        this.m = -1;
        this.p = false;
        this.c = new ArrayList(1);
        this.d = new ArrayList(1);
        this.e = new ArrayList(1);
    }

    public final void a(double rssi) {
        this.s = Double.valueOf(rssi);
        this.f = null;
    }

    public final void a(int rssi) {
        this.g = rssi;
    }

    public final int b() {
        return this.m;
    }

    public final l b(int i2) {
        return this.c.get(i2);
    }

    public final List<Long> c() {
        if (this.d.getClass().isInstance(q)) {
            return this.d;
        }
        return Collections.unmodifiableList(this.d);
    }

    public final List<Long> d() {
        if (this.e.getClass().isInstance(q)) {
            return this.e;
        }
        return Collections.unmodifiableList(this.e);
    }

    public final void a(List<Long> fields) {
        this.e = fields;
    }

    public final double e() {
        if (this.f == null) {
            double bestRssiAvailable = (double) this.g;
            if (this.s != null) {
                bestRssiAvailable = this.s.doubleValue();
            } else {
                d.a("Beacon", "Not using running average RSSI because it is null", new Object[0]);
            }
            this.f = a(this.h, bestRssiAvailable);
        }
        return this.f.doubleValue();
    }

    public final int f() {
        return this.g;
    }

    public final int g() {
        return this.h;
    }

    public final String h() {
        return this.i;
    }

    public final boolean i() {
        return this.p;
    }

    public int hashCode() {
        StringBuilder sb = l();
        if (a) {
            sb.append(this.i);
        }
        return sb.toString().hashCode();
    }

    public boolean equals(Object that) {
        if (!(that instanceof Beacon)) {
            return false;
        }
        Beacon thatBeacon = (Beacon) that;
        if (!this.c.equals(thatBeacon.c)) {
            return false;
        }
        if (a) {
            return h().equals(thatBeacon.h());
        }
        return true;
    }

    public String toString() {
        return l().toString();
    }

    private StringBuilder l() {
        StringBuilder sb = new StringBuilder();
        int i2 = 1;
        for (l identifier : this.c) {
            if (i2 > 1) {
                sb.append(Token.SEPARATOR);
            }
            sb.append("id");
            sb.append(i2);
            sb.append(": ");
            sb.append(identifier == null ? "null" : identifier.toString());
            i2++;
        }
        if (this.o != null) {
            sb.append(" type " + this.o);
        }
        return sb;
    }

    @Deprecated
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.c.size());
        for (l identifier : this.c) {
            out.writeString(identifier == null ? null : identifier.toString());
        }
        out.writeDouble(e());
        out.writeInt(this.g);
        out.writeInt(this.h);
        out.writeString(this.i);
        out.writeInt(this.k);
        out.writeInt(this.m);
        out.writeInt(this.d.size());
        for (Long dataField : this.d) {
            out.writeLong(dataField.longValue());
        }
        out.writeInt(this.e.size());
        for (Long dataField2 : this.e) {
            out.writeLong(dataField2.longValue());
        }
        out.writeInt(this.l);
        out.writeString(this.n);
        out.writeString(this.o);
        out.writeByte((byte) (this.p ? 1 : 0));
    }

    public final boolean j() {
        return this.c.size() == 0 && this.d.size() != 0;
    }

    private static Double a(int txPower, double bestRssiAvailable) {
        if (k() != null) {
            return Double.valueOf(k().a(txPower, bestRssiAvailable));
        }
        d.d("Beacon", "Distance calculator not set.  Distance will bet set to -1", new Object[0]);
        return Double.valueOf(-1.0d);
    }
}
