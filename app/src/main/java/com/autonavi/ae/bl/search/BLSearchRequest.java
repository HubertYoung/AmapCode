package com.autonavi.ae.bl.search;

import com.autonavi.jni.ae.bl.Parcel;

public class BLSearchRequest {
    private BLSearchCondition mSearchCondition;
    private long mShadow;

    private native long nativeCreate(Parcel parcel, BLSearchObserver bLSearchObserver);

    private BLSearchRequest(BLSearchCondition bLSearchCondition, BLSearchObserver bLSearchObserver) {
        Parcel parcel = new Parcel();
        bLSearchCondition.writeToParcel(parcel);
        this.mShadow = nativeCreate(parcel, bLSearchObserver);
        this.mSearchCondition = bLSearchCondition;
    }

    public static BLSearchRequest create(BLSearchCondition bLSearchCondition, BLSearchObserver bLSearchObserver) {
        return new BLSearchRequest(bLSearchCondition, bLSearchObserver);
    }

    public BLSearchCondition getSearchCondition() {
        return this.mSearchCondition;
    }

    /* access modifiers changed from: 0000 */
    public long shadow() {
        return this.mShadow;
    }
}
