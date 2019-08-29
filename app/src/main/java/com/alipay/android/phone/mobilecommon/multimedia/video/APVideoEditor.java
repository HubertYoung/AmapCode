package com.alipay.android.phone.mobilecommon.multimedia.video;

import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoCutReq;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoInfo;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoThumbnailReq;

public abstract class APVideoEditor {
    public abstract void cutVideo(APVideoCutReq aPVideoCutReq, APVideoCutCallback aPVideoCutCallback);

    public abstract APVideoInfo getVideoInfo();

    public abstract void getVideoThumbnail(APVideoThumbnailReq aPVideoThumbnailReq);

    public abstract void release();

    public abstract void setVideoThumbnalListener(APVideoThumbnailListener aPVideoThumbnailListener);
}
