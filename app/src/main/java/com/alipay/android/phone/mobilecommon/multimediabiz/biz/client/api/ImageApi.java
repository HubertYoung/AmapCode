package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ThumbnailMarkAddReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ThumbnailsDownReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.AddMarkResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ThumbnailsDownResp;

public interface ImageApi {
    AddMarkResp addWaterMark(ThumbnailMarkAddReq thumbnailMarkAddReq);

    ThumbnailsDownResp downloadThumbnails(ThumbnailsDownReq thumbnailsDownReq);
}
