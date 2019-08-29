package org.altbeacon.beacon;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Region implements Parcelable, Serializable {
    public static final Creator<Region> CREATOR = new p();
    private static final Pattern d = Pattern.compile("^[0-9A-Fa-f]{2}\\:[0-9A-Fa-f]{2}\\:[0-9A-Fa-f]{2}\\:[0-9A-Fa-f]{2}\\:[0-9A-Fa-f]{2}\\:[0-9A-Fa-f]{2}$");
    protected final List<l> a;
    protected final String b;
    protected final String c;

    public Region(String uniqueId) {
        this.a = new ArrayList(3);
        this.a.add(null);
        this.a.add(null);
        this.a.add(null);
        this.c = uniqueId;
        this.b = null;
        if (uniqueId == null) {
            throw new NullPointerException("uniqueId may not be null");
        }
    }

    private Region(String uniqueId, List<l> identifiers, String bluetoothAddress) {
        a(bluetoothAddress);
        this.a = new ArrayList(identifiers);
        this.c = uniqueId;
        this.b = bluetoothAddress;
        if (uniqueId == null) {
            throw new NullPointerException("uniqueId may not be null");
        }
    }

    private l a(int i) {
        if (this.a.size() > i) {
            return this.a.get(i);
        }
        return null;
    }

    public final String a() {
        return this.c;
    }

    public final boolean a(Beacon beacon) {
        int i = this.a.size();
        while (true) {
            i--;
            if (i >= 0) {
                l identifier = this.a.get(i);
                l beaconIdentifier = null;
                if (i < beacon.c.size()) {
                    beaconIdentifier = beacon.b(i);
                }
                if (beaconIdentifier == null && identifier != null) {
                    return false;
                }
                if (beaconIdentifier != null && identifier != null && !identifier.equals(beaconIdentifier)) {
                    return false;
                }
            } else if (this.b == null || this.b.equalsIgnoreCase(beacon.i)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public int hashCode() {
        return this.c.hashCode();
    }

    public boolean equals(Object other) {
        if (other instanceof Region) {
            return ((Region) other).c.equals(this.c);
        }
        return false;
    }

    public final boolean a(Region region) {
        if (region.a.size() != this.a.size()) {
            return false;
        }
        for (int i = 0; i < region.a.size(); i++) {
            if (region.a(i) == null && a(i) != null) {
                return false;
            }
            if (region.a(i) != null && a(i) == null) {
                return false;
            }
            if ((region.a(i) != null || a(i) != null) && !region.a(i).equals(a(i))) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (l identifier : this.a) {
            if (i > 1) {
                sb.append(Token.SEPARATOR);
            }
            sb.append("id");
            sb.append(i);
            sb.append(": ");
            sb.append(identifier == null ? "null" : identifier.toString());
            i++;
        }
        return sb.toString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.c);
        out.writeString(this.b);
        out.writeInt(this.a.size());
        for (l identifier : this.a) {
            if (identifier != null) {
                out.writeString(identifier.toString());
            } else {
                out.writeString(null);
            }
        }
    }

    protected Region(Parcel in) {
        this.c = in.readString();
        this.b = in.readString();
        int size = in.readInt();
        this.a = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            String identifierString = in.readString();
            if (identifierString == null) {
                this.a.add(null);
            } else {
                this.a.add(l.a(identifierString));
            }
        }
    }

    private static void a(String mac) {
        if (mac != null && !d.matcher(mac).matches()) {
            throw new IllegalArgumentException("Invalid mac address: '" + mac + "' Must be 6 hex bytes separated by colons.");
        }
    }

    /* access modifiers changed from: private */
    @Deprecated
    /* renamed from: b */
    public Region clone() {
        return new Region(this.c, this.a, this.b);
    }
}
