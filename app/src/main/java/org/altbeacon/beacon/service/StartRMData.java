package org.altbeacon.beacon.service;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import java.io.Serializable;
import org.altbeacon.beacon.Region;

public class StartRMData implements Parcelable, Serializable {
    public static final Creator<StartRMData> CREATOR = new t();
    private Region a;
    private long b;
    private long c;
    private boolean d;
    private String e;

    /* synthetic */ StartRMData(Parcel x0, byte b2) {
        this(x0);
    }

    private StartRMData() {
    }

    public StartRMData(long scanPeriod, long betweenScanPeriod, boolean backgroundFlag) {
        this.b = scanPeriod;
        this.c = betweenScanPeriod;
        this.d = backgroundFlag;
    }

    public StartRMData(@NonNull Region region, @NonNull String callbackPackageName, long scanPeriod, long betweenScanPeriod, boolean backgroundFlag) {
        this.b = scanPeriod;
        this.c = betweenScanPeriod;
        this.a = region;
        this.e = callbackPackageName;
        this.d = backgroundFlag;
    }

    public final long a() {
        return this.b;
    }

    public final long b() {
        return this.c;
    }

    public final Region c() {
        return this.a;
    }

    public final String d() {
        return this.e;
    }

    public final boolean e() {
        return this.d;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(this.a, flags);
        out.writeString(this.e);
        out.writeLong(this.b);
        out.writeLong(this.c);
        out.writeByte((byte) (this.d ? 1 : 0));
    }

    private StartRMData(Parcel in) {
        this.a = (Region) in.readParcelable(StartRMData.class.getClassLoader());
        this.e = in.readString();
        this.b = in.readLong();
        this.c = in.readLong();
        this.d = in.readByte() != 0;
    }

    public final Bundle f() {
        Bundle bundle = new Bundle();
        bundle.putLong("scanPeriod", this.b);
        bundle.putLong("betweenScanPeriod", this.c);
        bundle.putBoolean("backgroundFlag", this.d);
        bundle.putString("callbackPackageName", this.e);
        if (this.a != null) {
            bundle.putSerializable("region", this.a);
        }
        return bundle;
    }

    public static StartRMData a(@NonNull Bundle bundle) {
        bundle.setClassLoader(Region.class.getClassLoader());
        boolean valid = false;
        StartRMData data = new StartRMData();
        if (bundle.containsKey("region")) {
            data.a = (Region) bundle.getSerializable("region");
            valid = true;
        }
        if (bundle.containsKey("scanPeriod")) {
            data.b = ((Long) bundle.get("scanPeriod")).longValue();
            valid = true;
        }
        if (bundle.containsKey("betweenScanPeriod")) {
            data.c = ((Long) bundle.get("betweenScanPeriod")).longValue();
        }
        if (bundle.containsKey("backgroundFlag")) {
            data.d = ((Boolean) bundle.get("backgroundFlag")).booleanValue();
        }
        if (bundle.containsKey("callbackPackageName")) {
            data.e = (String) bundle.get("callbackPackageName");
        }
        if (valid) {
            return data;
        }
        return null;
    }
}
