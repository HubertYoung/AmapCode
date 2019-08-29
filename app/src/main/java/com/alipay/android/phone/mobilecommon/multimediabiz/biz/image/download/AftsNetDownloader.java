package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.AftsLinkHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ZoomHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.common.transport.TransportCallback;

public class AftsNetDownloader extends NetDownloader {
    private static final Logger logger = Logger.getLogger((String) "AftsNetDownloader");

    public AftsNetDownloader(ImageLoadReq req, String savePath, TransportCallback transportCallback) {
        super(req, savePath, transportCallback);
    }

    /* access modifiers changed from: protected */
    public String getImageMdnUrl(ImageLoadReq req) {
        if (TextUtils.isEmpty(req.zoom)) {
            req.zoom = ZoomHelper.getZoom(req);
        }
        String zoom2 = ZoomHelper.getSecondaryZoom(req);
        if (!TextUtils.isEmpty(zoom2)) {
            req.zoom += "&zoom2=" + zoom2;
        }
        if (TextUtils.isEmpty(req.fileId)) {
            req.fileId = req.path;
        }
        String url = AftsLinkHelper.genDlImageAftsUrl(req.fileId, req.zoom, req.options.getBizType(), req.options.getImageMarkRequest());
        req.netPerf.zoom = req.zoom;
        req.netPerf.netMethod = 3;
        logger.d("getImageMdnUrl url=" + url, new Object[0]);
        return url;
    }

    /* access modifiers changed from: protected */
    public boolean isMdnWay() {
        return true;
    }
}
