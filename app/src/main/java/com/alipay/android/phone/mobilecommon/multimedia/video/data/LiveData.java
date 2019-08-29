package com.alipay.android.phone.mobilecommon.multimedia.video.data;

public class LiveData {
    public long mFramesInterval = 0;
    public long mSizeInterval = 0;
    public long mTimeInterval = 0;
    public long mTotalFrames = 0;
    public long mTotalSize = 0;
    public long mTotalTime = 0;

    public float getUploadRealTimeSpeed() {
        if (this.mTimeInterval == 0 || this.mSizeInterval == 0) {
            return 0.0f;
        }
        return ((((float) this.mSizeInterval) / 1024.0f) / ((float) this.mTimeInterval)) * 1000000.0f;
    }

    public String toString() {
        return "LiveData{mTotalSize=" + this.mTotalSize + ", mTotalTime=" + this.mTotalTime + ", mTotalFrames=" + this.mTotalFrames + ", mTimeInterval=" + this.mTimeInterval + ", mSizeInterval=" + this.mSizeInterval + ", mFramesInterval=" + this.mFramesInterval + '}';
    }
}
