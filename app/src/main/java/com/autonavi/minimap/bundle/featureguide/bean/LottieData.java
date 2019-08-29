package com.autonavi.minimap.bundle.featureguide.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class LottieData implements Parcelable {
    public static final Creator<LottieData> CREATOR = new Creator<LottieData>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new LottieData[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            LottieData lottieData = new LottieData();
            lottieData.a = parcel.readString();
            lottieData.b = parcel.readString();
            lottieData.c = parcel.readString();
            lottieData.d = parcel.readString();
            lottieData.e = parcel.readString();
            lottieData.f = parcel.readDouble();
            lottieData.g = parcel.readDouble();
            lottieData.h = parcel.readDouble();
            lottieData.i = parcel.readDouble();
            lottieData.j = parcel.readByte() != 0;
            return lottieData;
        }
    };
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public double f;
    public double g;
    public double h;
    public double i;
    public boolean j;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeString(this.e);
        parcel.writeDouble(this.f);
        parcel.writeDouble(this.g);
        parcel.writeDouble(this.h);
        parcel.writeDouble(this.i);
        parcel.writeByte(this.j ? (byte) 1 : 0);
    }
}
