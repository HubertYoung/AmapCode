package com.alibaba.mtl.appmonitor.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Dimension implements Parcelable {
    public static final Creator<Dimension> CREATOR = new Creator<Dimension>() {
        public final Dimension createFromParcel(Parcel parcel) {
            return Dimension.readFromParcel(parcel);
        }

        public final Dimension[] newArray(int i) {
            return new Dimension[i];
        }
    };
    static final String DEFAULT_NULL_VALUE = "null";
    protected String constantValue;
    protected String name;

    public int describeContents() {
        return 0;
    }

    public Dimension() {
        this.constantValue = "null";
    }

    public Dimension(String str) {
        this(str, null);
    }

    public Dimension(String str, String str2) {
        this.constantValue = "null";
        this.name = str;
        this.constantValue = str2 == null ? "null" : str2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getConstantValue() {
        return this.constantValue;
    }

    public void setConstantValue(String str) {
        this.constantValue = str;
    }

    public int hashCode() {
        return (this.name == null ? 0 : this.name.hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Dimension dimension = (Dimension) obj;
        if (this.name == null) {
            if (dimension.name != null) {
                return false;
            }
        } else if (!this.name.equals(dimension.name)) {
            return false;
        }
        return true;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.constantValue);
        parcel.writeString(this.name);
    }

    static Dimension readFromParcel(Parcel parcel) {
        try {
            return new Dimension(parcel.readString(), parcel.readString());
        } catch (Throwable unused) {
            return null;
        }
    }
}
