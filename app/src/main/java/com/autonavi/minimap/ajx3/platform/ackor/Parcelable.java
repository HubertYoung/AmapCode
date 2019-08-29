package com.autonavi.minimap.ajx3.platform.ackor;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface Parcelable {
    boolean readFromParcel(Parcel parcel);

    boolean writeToParcel(Parcel parcel);
}
