package com.alipay.android.phone.mobilecommon.multimedia.graphics;

import android.graphics.Bitmap;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;

public interface ImageWorkerPlugin {
    String getPluginKey();

    Bitmap process(APMultimediaTaskModel aPMultimediaTaskModel, Bitmap bitmap);
}
