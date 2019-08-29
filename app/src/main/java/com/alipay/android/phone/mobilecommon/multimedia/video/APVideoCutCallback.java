package com.alipay.android.phone.mobilecommon.multimedia.video;

import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoCutRsp;

public interface APVideoCutCallback {
    void onProgress(APVideoCutRsp aPVideoCutRsp);

    void onVideoCutFinished(APVideoCutRsp aPVideoCutRsp);
}
