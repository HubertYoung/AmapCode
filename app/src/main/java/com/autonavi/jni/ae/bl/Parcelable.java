package com.autonavi.jni.ae.bl;

public interface Parcelable {
    boolean readFromParcel(Parcel parcel);

    boolean writeToParcel(Parcel parcel);
}
