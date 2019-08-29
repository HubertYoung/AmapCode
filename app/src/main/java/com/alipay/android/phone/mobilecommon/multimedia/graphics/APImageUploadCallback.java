package com.alipay.android.phone.mobilecommon.multimedia.graphics;

import android.graphics.drawable.Drawable;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageUploadRsp;

public interface APImageUploadCallback {
    void onCompressSucc(Drawable drawable);

    void onError(APImageUploadRsp aPImageUploadRsp, Exception exc);

    void onProcess(APMultimediaTaskModel aPMultimediaTaskModel, int i);

    void onStartUpload(APMultimediaTaskModel aPMultimediaTaskModel);

    void onSuccess(APImageUploadRsp aPImageUploadRsp);
}
