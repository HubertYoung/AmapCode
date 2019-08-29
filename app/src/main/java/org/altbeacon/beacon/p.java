package org.altbeacon.beacon;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: Region */
final class p implements Creator<Region> {
    p() {
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return a(parcel);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return a(i);
    }

    private static Region a(Parcel in) {
        return new Region(in);
    }

    private static Region[] a(int size) {
        return new Region[size];
    }
}
