package com.autonavi.minimap.life.db.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Shortcut implements Parcelable {
    public static final Creator<Shortcut> CREATOR = new Creator<Shortcut>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new Shortcut[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new Shortcut(parcel, 0);
        }
    };
    public Long a;
    public String b;
    public String c;
    public Integer d;
    public String e;
    public String f;
    public String g;
    public Boolean h;

    public int describeContents() {
        return 0;
    }

    /* synthetic */ Shortcut(Parcel parcel, byte b2) {
        this(parcel);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.a.longValue());
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeInt(this.d.intValue());
        parcel.writeString(this.e);
        parcel.writeString(this.f);
        parcel.writeString(this.g);
        parcel.writeInt(this.h.booleanValue() ? 1 : 0);
    }

    public Shortcut() {
    }

    private Shortcut(Parcel parcel) {
        this.a = Long.valueOf(parcel.readLong());
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = Integer.valueOf(parcel.readInt());
        this.e = parcel.readString();
        this.f = parcel.readString();
        this.g = parcel.readString();
        boolean z = true;
        this.h = Boolean.valueOf(parcel.readInt() != 1 ? false : z);
    }
}
