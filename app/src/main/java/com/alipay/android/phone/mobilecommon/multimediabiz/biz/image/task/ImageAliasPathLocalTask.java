package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;

public class ImageAliasPathLocalTask extends ImageLocalTask {
    public ImageAliasPathLocalTask(ImageLoadReq loadReq, ViewWrapper wrapper) {
        super(loadReq, wrapper);
    }

    public Object call() {
        checkAndStartNetTask();
        return null;
    }
}
