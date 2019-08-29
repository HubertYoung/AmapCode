package com.alipay.android.phone.mobilecommon.multimedia.video.data;

public class APAlbumVideoInfo {
    public int mDuration;
    public String mId;
    public String mPath;
    public long mSize;
    public boolean mSuccess;

    public String toString() {
        return "APAlbumVideoInfo{mSize=" + this.mSize + ", mDuration=" + this.mDuration + ", mId='" + this.mId + '\'' + ", mPath='" + this.mPath + '\'' + ", mSuccess=" + this.mSuccess + '}';
    }
}
