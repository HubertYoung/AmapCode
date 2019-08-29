package com.autonavi.jni.ae.data;

import com.autonavi.jni.ae.bl.Parcel;
import com.autonavi.jni.ae.bl.Parcelable;

public class AreaExtraInfo implements Parcelable {
    public int mAdcode;
    public double mCenterLat;
    public double mCenterLon;
    public int mCityAdcode;
    public String mCityName;
    public String mPronvinceName;
    public String mTownName;

    public AreaExtraInfo() {
    }

    public AreaExtraInfo(int i, int i2, double d, double d2, String str, String str2, String str3) {
        this.mAdcode = i;
        this.mCityAdcode = i2;
        this.mCenterLon = d;
        this.mCenterLat = d2;
        this.mPronvinceName = str;
        this.mCityName = str2;
        this.mTownName = str3;
    }

    public boolean writeToParcel(Parcel parcel) {
        parcel.reset();
        parcel.writeInt(this.mAdcode);
        parcel.writeInt(this.mCityAdcode);
        parcel.writeInt((int) (this.mCenterLon * 1000000.0d));
        parcel.writeInt((int) (this.mCenterLat * 1000000.0d));
        parcel.writeInt(this.mPronvinceName.length());
        if (this.mPronvinceName.length() > 0) {
            parcel.writeString(this.mPronvinceName);
        }
        parcel.writeInt(this.mCityName.length());
        if (this.mCityName.length() > 0) {
            parcel.writeString(this.mCityName);
        }
        parcel.writeInt(this.mTownName.length());
        if (this.mTownName.length() > 0) {
            parcel.writeString(this.mTownName);
        }
        return true;
    }

    public boolean readFromParcel(Parcel parcel) {
        parcel.reset();
        this.mAdcode = parcel.readInt();
        this.mCityAdcode = parcel.readInt();
        this.mCenterLon = (double) (((float) parcel.readInt()) / 1000000.0f);
        this.mCenterLat = (double) (((float) parcel.readInt()) / 1000000.0f);
        if (parcel.readInt() > 0) {
            this.mPronvinceName = parcel.readString();
        }
        if (parcel.readInt() > 0) {
            this.mCityName = parcel.readString();
        }
        if (parcel.readInt() > 0) {
            this.mTownName = parcel.readString();
        }
        return true;
    }
}
