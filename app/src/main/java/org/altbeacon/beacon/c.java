package org.altbeacon.beacon;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: Beacon */
final class c implements Creator<Beacon> {
    c() {
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return a(parcel);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return a(i);
    }

    private static Beacon a(Parcel in) {
        return new Beacon(in);
    }

    private static Beacon[] a(int size) {
        return new Beacon[size];
    }
}
