package com.autonavi.ae.bl.search;

import com.autonavi.jni.ae.bl.Parcel;

public interface BLSearchObserver {
    void onSearchStatusChanged(BLSearchRequest bLSearchRequest, Parcel parcel);
}
