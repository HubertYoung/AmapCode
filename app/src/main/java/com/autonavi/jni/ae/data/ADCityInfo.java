package com.autonavi.jni.ae.data;

import com.autonavi.jni.ae.bl.Parcel;
import com.autonavi.jni.ae.bl.Parcelable;

public class ADCityInfo implements Parcelable {
    public int mAdcode;
    public double mCenterLat;
    public double mCenterLon;
    public int mCenterPointX;
    public int mCenterPointY;
    public String mCityName;
    public String mCitycode;
    public String mJianpin;
    public int mLevel;
    public String mPronvinceName;
    public String mQuanpin;

    public ADCityInfo() {
    }

    public ADCityInfo(int i, double d, double d2, int i2, int i3, int i4, String str, String str2, String str3, String str4, String str5) {
        this.mAdcode = i;
        this.mCenterLon = d;
        this.mCenterLat = d2;
        this.mCenterPointX = i2;
        this.mCenterPointY = i3;
        this.mLevel = i4;
        this.mCityName = str;
        this.mQuanpin = str2;
        this.mJianpin = str3;
        this.mCitycode = str4;
        this.mPronvinceName = str5;
    }

    public boolean writeToParcel(Parcel parcel) {
        parcel.reset();
        parcel.writeInt(this.mAdcode);
        parcel.writeDouble(this.mCenterLon);
        parcel.writeDouble(this.mCenterLat);
        parcel.writeInt(this.mCenterPointX);
        parcel.writeInt(this.mCenterPointY);
        parcel.writeInt(this.mLevel);
        parcel.writeString(this.mCityName);
        parcel.writeString(this.mQuanpin);
        parcel.writeString(this.mJianpin);
        parcel.writeString(this.mCitycode);
        parcel.writeString(this.mPronvinceName);
        return true;
    }

    public boolean readFromParcel(Parcel parcel) {
        parcel.reset();
        this.mAdcode = parcel.readInt();
        this.mCenterLon = parcel.readDouble();
        this.mCenterLat = parcel.readDouble();
        this.mCenterPointX = parcel.readInt();
        this.mCenterPointY = parcel.readInt();
        this.mLevel = parcel.readInt();
        this.mCityName = parcel.readString();
        this.mQuanpin = parcel.readString();
        this.mJianpin = parcel.readString();
        this.mCitycode = parcel.readString();
        this.mPronvinceName = parcel.readString();
        return true;
    }
}
