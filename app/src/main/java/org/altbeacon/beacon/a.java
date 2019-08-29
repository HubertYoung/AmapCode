package org.altbeacon.beacon;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: AltBeacon */
final class a implements Creator<AltBeacon> {
    a() {
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return a(parcel);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return a(i);
    }

    private static AltBeacon a(Parcel in) {
        return new AltBeacon(in);
    }

    private static AltBeacon[] a(int size) {
        return new AltBeacon[size];
    }
}
