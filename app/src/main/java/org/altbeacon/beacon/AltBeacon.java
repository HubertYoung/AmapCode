package org.altbeacon.beacon;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class AltBeacon extends Beacon {
    public static final Creator<AltBeacon> CREATOR = new a();

    protected AltBeacon() {
    }

    protected AltBeacon(Parcel in) {
        super(in);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
    }
}
