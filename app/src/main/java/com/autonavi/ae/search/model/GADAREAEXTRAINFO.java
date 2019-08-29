package com.autonavi.ae.search.model;

import android.graphics.PointF;
import com.autonavi.jni.ae.bl.Parcel;
import com.autonavi.jni.ae.bl.Parcelable;

public class GADAREAEXTRAINFO implements Parcelable {
    private int nTelAreaCode;
    private int nTelRegionCode;
    private String pzCityName;
    private String pzProvName;
    private String pzTownName;
    private GADMINCODE stAdCode;
    private PointF stCenterPoint;

    public boolean writeToParcel(Parcel parcel) {
        return false;
    }

    public GADAREAEXTRAINFO(Parcel parcel) {
        if (parcel != null) {
            readFromParcel(parcel);
        }
    }

    private GADAREAEXTRAINFO(GADMINCODE gadmincode, PointF pointF, int i, int i2, String str, String str2, String str3) {
        this.stAdCode = gadmincode;
        this.stCenterPoint = pointF;
        this.nTelRegionCode = i;
        this.nTelAreaCode = i2;
        this.pzProvName = str;
        this.pzCityName = str2;
        this.pzTownName = str3;
    }

    public GADMINCODE getAdCode() {
        return this.stAdCode;
    }

    public PointF getCenterPoint() {
        return this.stCenterPoint;
    }

    public int getTelRegionCode() {
        return this.nTelRegionCode;
    }

    public int getTelAreaCode() {
        return this.nTelAreaCode;
    }

    public String getProvName() {
        return this.pzProvName;
    }

    public String getCityName() {
        return this.pzCityName;
    }

    public String getTownName() {
        return this.pzTownName;
    }

    public boolean readFromParcel(Parcel parcel) {
        parcel.reset();
        this.stAdCode = new GADMINCODE(parcel.readInt(), parcel.readInt(), parcel.readInt());
        this.stCenterPoint = new PointF();
        this.stCenterPoint.x = ((float) parcel.readInt()) / 1000000.0f;
        this.stCenterPoint.y = ((float) parcel.readInt()) / 1000000.0f;
        this.nTelRegionCode = parcel.readInt();
        this.nTelAreaCode = parcel.readInt();
        if (parcel.readInt() > 0) {
            this.pzProvName = parcel.readString();
        }
        if (parcel.readInt() > 0) {
            this.pzCityName = parcel.readString();
        }
        if (parcel.readInt() > 0) {
            this.pzTownName = parcel.readString();
        }
        return true;
    }
}
