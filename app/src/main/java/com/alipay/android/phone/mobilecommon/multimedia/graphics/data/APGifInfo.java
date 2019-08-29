package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

public class APGifInfo {
    public String mId;
    public String mPath;
    public long mSize;
    public boolean mSuccess;

    public String toString() {
        return "APGifInfo{mSuccess=" + this.mSuccess + ", mSize=" + this.mSize + ", mId='" + this.mId + '\'' + ", mPath='" + this.mPath + '\'' + '}';
    }
}
