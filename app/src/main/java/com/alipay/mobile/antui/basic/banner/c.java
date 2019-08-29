package com.alipay.mobile.antui.basic.banner;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: CirclePageIndicator */
final class c implements Creator<SavedState> {
    c() {
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
