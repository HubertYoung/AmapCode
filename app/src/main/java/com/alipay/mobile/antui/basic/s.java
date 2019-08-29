package com.alipay.mobile.antui.basic;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: AUHorizontalListView */
final class s implements Creator<SavedState> {
    s() {
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return a(parcel);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return a(i);
    }

    private static SavedState a(Parcel in) {
        return new SavedState(in, 0);
    }

    private static SavedState[] a(int size) {
        return new SavedState[size];
    }
}
