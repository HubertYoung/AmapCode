package com.alipay.android.phone.mobilecommon.multimedia.video.data;

public class APVideoFilterParams {
    public String mBizType;
    public String mEffect;
    public String mName;
    public int mRenderTime;
    public int mResult;
    public String mVideoPath;

    public String toString() {
        return "APVideoFilterParams{mRenderTime=" + this.mRenderTime + ", mEffect='" + this.mEffect + '\'' + ", mName='" + this.mName + '\'' + ", mResult=" + this.mResult + ", mVideoPath='" + this.mVideoPath + '\'' + ", mBizType='" + this.mBizType + '\'' + '}';
    }
}
