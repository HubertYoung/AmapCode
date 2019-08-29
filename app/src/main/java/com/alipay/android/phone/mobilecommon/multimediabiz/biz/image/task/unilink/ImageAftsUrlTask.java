package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.unilink;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download.AftsNetDownloader;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download.HttpTransListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download.NetDownloader;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.ImageUrlTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;

public class ImageAftsUrlTask extends ImageUrlTask {
    private static final Logger a = Logger.getLogger((String) "ImageAftsUrlTask");

    public ImageAftsUrlTask(ImageLoadReq loadReq, ViewWrapper wrapper) {
        super(loadReq, wrapper);
        setTag("ImageAftsUrlTask");
        this.loadReq.adjustToDjangoReq();
    }

    /* access modifiers changed from: protected */
    public NetDownloader createNetDownloader(String savePath) {
        a.d("createNetDownloader savepath=" + savePath, new Object[0]);
        return new AftsNetDownloader(this.loadReq, savePath, new HttpTransListener(this.loadReqSet));
    }

    /* access modifiers changed from: protected */
    public String genSavePath() {
        return ImageDiskCache.get().genPathByKey(this.loadReq.cacheKey.complexCacheKey());
    }
}
