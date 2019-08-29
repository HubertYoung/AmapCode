package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download;

import android.os.Bundle;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;

public interface ImageDownloader<Resp> {
    void cancel();

    Resp download(ImageLoadReq imageLoadReq, Bundle bundle);
}
