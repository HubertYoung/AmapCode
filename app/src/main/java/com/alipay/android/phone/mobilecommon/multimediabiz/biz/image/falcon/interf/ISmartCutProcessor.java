package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.interf;

import android.graphics.Bitmap;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;

public interface ISmartCutProcessor {
    Bitmap process(String str, ImageLoadReq imageLoadReq);

    Bitmap process(byte[] bArr, ImageLoadReq imageLoadReq);
}
