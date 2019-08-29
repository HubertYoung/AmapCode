package com.yunos.carkitsdk;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ConnectionStatusInfo implements Parcelable {
    public static final Creator<ConnectionStatusInfo> CREATOR = new Creator() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new ConnectionStatusInfo(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new ConnectionStatusInfo[i];
        }
    };
    public String a;
    public int b;
    public boolean c;
    public boolean d;
    private long e;

    public int describeContents() {
        return 0;
    }

    public ConnectionStatusInfo(String str, int i, long j, boolean z, boolean z2) {
        this.a = str;
        this.b = i;
        this.e = j;
        this.c = z;
        this.d = z2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeInt(this.b);
        parcel.writeLong(this.e);
        parcel.writeInt(this.c ? 1 : 0);
        parcel.writeInt(this.d ? 1 : 0);
    }

    public ConnectionStatusInfo(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readInt();
        this.e = parcel.readLong();
        boolean z = false;
        this.c = parcel.readInt() > 0;
        this.d = parcel.readInt() > 0 ? true : z;
    }
}
