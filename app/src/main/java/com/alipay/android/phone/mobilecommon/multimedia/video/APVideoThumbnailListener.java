package com.alipay.android.phone.mobilecommon.multimedia.video;

import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoThumbnailReq;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoThumbnailRsp;

public interface APVideoThumbnailListener {
    void onGetThumbnail(APVideoThumbnailReq aPVideoThumbnailReq, APVideoThumbnailRsp aPVideoThumbnailRsp);
}
