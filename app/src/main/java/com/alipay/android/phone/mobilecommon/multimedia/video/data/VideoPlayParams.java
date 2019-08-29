package com.alipay.android.phone.mobilecommon.multimedia.video.data;

public class VideoPlayParams {
    public static int EFFECT_DEFAULT = 0;
    public static int EFFECT_TRANSPARENT = 1;
    public static int TYPE_GENERAL = 2;
    public static int TYPE_LAZY = 4;
    public static int TYPE_LIVE = 0;
    public static int TYPE_SOCIAL = 1;
    public static int TYPE_URL = 3;
    public String alg = "";
    public String key = "";
    public String mBizId;
    public int mEffect = EFFECT_DEFAULT;
    public boolean mEnableAudio;
    public boolean mEnableCache = true;
    public int mProgressInterval = 500;
    public int mType = TYPE_GENERAL;
    public String mVideoId;

    public String toString() {
        return "VideoPlayParams {mType=" + this.mType + ", mEffect=" + this.mEffect + ", mVideoId=" + this.mVideoId + ", mBizId=" + this.mBizId + ", mEnableAudio=" + this.mEnableAudio + ", alg=" + this.alg + ", key=" + this.key + ", mProgressInterval=" + this.mProgressInterval + '}';
    }
}
