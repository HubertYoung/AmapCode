package org.altbeacon.beacon.service;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: StartRMData */
final class t implements Creator<StartRMData> {
    t() {
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return a(parcel);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return a(i);
    }

    private static StartRMData a(Parcel in) {
        return new StartRMData(in, 0);
    }

    private static StartRMData[] a(int size) {
        return new StartRMData[size];
    }
}
