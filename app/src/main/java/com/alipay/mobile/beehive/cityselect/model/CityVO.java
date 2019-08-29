package com.alipay.mobile.beehive.cityselect.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.alipay.sdk.util.h;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class CityVO implements Parcelable, Serializable, Cloneable {
    public static final Creator<CityVO> CREATOR = new Creator<CityVO>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }

        private static CityVO a(Parcel in) {
            return new CityVO(in);
        }

        private static CityVO[] a(int size) {
            return new CityVO[size];
        }
    };
    private static final long serialVersionUID = -1624470121497837719L;
    public List<String> abbreviation;
    public String adCode;
    public int areaLevel;
    public HashMap bizmap;
    public String city;
    public List<CityFragmentModel> cityFragmentModels;
    public String enName;
    public boolean isMainLand = true;
    public double latitude;
    public double longitude;
    public String pinyin;
    public String province;
    public String url;

    public CityVO() {
    }

    public CityVO(String city2, boolean isMainLand2) {
        this.city = city2;
        this.isMainLand = isMainLand2;
    }

    public String toString() {
        return new StringBuffer("CityVO{province='").append(this.province).append("', city='").append(this.city).append("', pinyin='").append(this.pinyin).append("', adCode='").append(this.adCode).append("', isMainLand=").append(this.isMainLand).append(", latitude=").append(this.latitude).append(", longitude=").append(this.longitude).append(h.d).toString();
    }

    public CityVO(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        boolean z = true;
        this.province = in.readString();
        this.city = in.readString();
        this.pinyin = in.readString();
        this.adCode = in.readString();
        if (in.readInt() != 1) {
            z = false;
        }
        this.isMainLand = z;
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.bizmap = in.readHashMap(HashMap.class.getClassLoader());
        in.readList(this.abbreviation, List.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.pinyin);
        dest.writeString(this.adCode);
        dest.writeInt(this.isMainLand ? 1 : 0);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeMap(this.bizmap);
        dest.writeList(this.abbreviation);
    }

    public Object clone() {
        return super.clone();
    }
}
