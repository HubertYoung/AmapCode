package com.yunos.carkitsdk;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class TransferInfo implements Parcelable {
    public static final Creator<TransferInfo> CREATOR = new Creator() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new TransferInfo(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new TransferInfo[i];
        }
    };
    public String a;
    public String b;
    public long c;
    public boolean d;
    public long e;
    public long f;
    public long g;
    public int h;
    public int i;
    public int j;
    public int k;
    public int l;
    private String m;

    public int describeContents() {
        return 0;
    }

    public TransferInfo(String str, String str2, boolean z, long j2, long j3, long j4, int i2, String str3) {
        this.a = str;
        this.b = str2;
        this.d = z;
        this.e = j2;
        this.f = j3;
        this.g = j4;
        this.i = i2;
        this.m = str3;
    }

    public TransferInfo(String str, String str2, long j2, long j3, long j4, int i2, int i3, String str3) {
        this.a = str;
        this.b = str2;
        this.d = false;
        this.e = j2;
        this.f = j3;
        this.g = j4;
        this.i = i2;
        this.j = i3;
        this.m = str3;
    }

    public final boolean a() {
        return this.h == 5;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeLong(this.c);
        parcel.writeInt(this.d ? 1 : 0);
        parcel.writeLong(this.e);
        parcel.writeLong(this.f);
        parcel.writeLong(this.g);
        parcel.writeInt(this.h);
        parcel.writeInt(this.i);
        parcel.writeInt(this.j);
        parcel.writeString(this.m);
        parcel.writeInt(this.l);
    }

    public TransferInfo(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readLong();
        this.d = parcel.readInt() > 0;
        this.e = parcel.readLong();
        this.f = parcel.readLong();
        this.g = parcel.readLong();
        this.h = parcel.readInt();
        this.i = parcel.readInt();
        this.j = parcel.readInt();
        this.m = parcel.readString();
        this.l = parcel.readInt();
    }
}
