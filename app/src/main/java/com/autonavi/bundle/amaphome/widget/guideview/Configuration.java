package com.autonavi.bundle.amaphome.widget.guideview;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.view.View;

public class Configuration implements Parcelable {
    public static final Creator<Configuration> CREATOR = new Creator<Configuration>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new Configuration[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            Configuration configuration = new Configuration();
            configuration.h = parcel.readInt();
            configuration.i = parcel.readInt();
            configuration.j = parcel.readInt();
            configuration.m = parcel.readInt();
            configuration.k = parcel.readInt();
            configuration.b = parcel.readInt();
            configuration.c = parcel.readInt();
            configuration.d = parcel.readInt();
            configuration.e = parcel.readInt();
            configuration.f = parcel.readInt();
            configuration.l = parcel.readInt();
            boolean z = false;
            configuration.n = parcel.readByte() == 1;
            if (parcel.readByte() == 1) {
                z = true;
            }
            configuration.o = z;
            return configuration;
        }
    };
    public View a = null;
    public int b = 0;
    public int c = 0;
    public int d = 0;
    public int e = 0;
    public int f = 0;
    public boolean g = false;
    public int h = 255;
    public int i = -1;
    public int j = -1;
    public int k = 0;
    public int l = 0;
    public int m = 17170444;
    public boolean n = true;
    public boolean o = false;
    boolean p = false;
    public int q = -1;
    public int r = -1;

    public int describeContents() {
        return 0;
    }

    Configuration() {
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.h);
        parcel.writeInt(this.i);
        parcel.writeInt(this.j);
        parcel.writeInt(this.m);
        parcel.writeInt(this.k);
        parcel.writeInt(this.b);
        parcel.writeInt(this.c);
        parcel.writeInt(this.d);
        parcel.writeInt(this.e);
        parcel.writeInt(this.f);
        parcel.writeInt(this.l);
        parcel.writeByte(this.n ? (byte) 1 : 0);
        parcel.writeByte(this.o ? (byte) 1 : 0);
    }
}
