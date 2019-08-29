package com.autonavi.minimap.route.subway.inter.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class SubWayStation implements Parcelable {
    public static final Creator<SubWayStation> CREATOR = new Creator<SubWayStation>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new SubWayStation[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            SubWayStation subWayStation = new SubWayStation();
            subWayStation.a = parcel.readString();
            subWayStation.b = parcel.readString();
            subWayStation.c = parcel.readString();
            subWayStation.d = parcel.readString();
            subWayStation.e = parcel.readInt();
            return subWayStation;
        }
    };
    public String a;
    public String b;
    public String c;
    public String d;
    public int e;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeInt(this.e);
    }
}
