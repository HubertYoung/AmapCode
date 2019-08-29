package com.autonavi.indoor.entity;

import com.autonavi.indoor.util.BLEUtils;

public class Beacon {
    protected Double c = null;
    protected Double mDistance;
    private final String mMacAddress;
    private final int mMajor;
    private final int mMeasuredPower;
    private final int mMinor;
    private final String mName;
    protected Integer mProximity;
    private int mRSSI;
    private final long mTimestamp;
    private final String mUUID;

    public int describeContents() {
        return 0;
    }

    public Beacon(String str, String str2, String str3, int i, int i2, int i3, int i4, long j) {
        this.mUUID = BLEUtils.normalizeProximityUUID(str);
        this.mName = str2;
        this.mMacAddress = str3;
        this.mMajor = i;
        this.mMinor = i2;
        this.mMeasuredPower = i3;
        this.mRSSI = i4;
        this.mTimestamp = j;
    }

    public String getMacAddress() {
        return this.mMacAddress;
    }

    public int getMajor() {
        return this.mMajor;
    }

    public int getMeasuredPower() {
        return this.mMeasuredPower;
    }

    public int getMinor() {
        return this.mMinor;
    }

    public String getName() {
        return this.mName;
    }

    public String getUUID() {
        return this.mUUID;
    }

    public int getRSSI() {
        return this.mRSSI;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public void setRSSI(int i) {
        this.mRSSI = i;
        this.mDistance = null;
    }
}
