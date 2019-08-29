package com.alipay.android.phone.mobilecommon.multimedia.video.data;

import com.alipay.android.phone.mobilecommon.multimedia.api.data.video.CompressLevel;

public class APVideoCutReq {
    public long endPosition;
    public CompressLevel quality;
    public long startPositon;
    public int targetHeight;
    public int targetWidth;

    public String toString() {
        return "APVideoCutReq{startPositon=" + this.startPositon + ", endPosition=" + this.endPosition + ", targetWidth=" + this.targetWidth + ", targetHeight=" + this.targetHeight + ", quality=" + this.quality + '}';
    }
}
