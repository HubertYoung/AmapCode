package com.ali.user.mobile.register;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class RegistParam implements Parcelable {
    public static final Creator<RegistParam> CREATOR = new Creator<RegistParam>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new RegistParam[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new RegistParam(parcel);
        }
    };
    public String country;
    public String countryName;
    public String registAccount;

    public int describeContents() {
        return 0;
    }

    public RegistParam() {
    }

    public RegistParam(Parcel parcel) {
        this.registAccount = parcel.readString();
        this.country = parcel.readString();
        this.countryName = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.registAccount);
        parcel.writeString(this.country);
        parcel.writeString(this.countryName);
    }
}
