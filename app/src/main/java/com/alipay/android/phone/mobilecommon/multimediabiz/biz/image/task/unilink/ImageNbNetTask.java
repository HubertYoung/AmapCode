package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.unilink;

import android.graphics.Bitmap;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.ImageDjangoTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils.DecryptException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;

public class ImageNbNetTask extends ImageDjangoTask {
    private static final Logger a = Logger.getLogger((String) "ImageNbNetTask");

    public ImageNbNetTask(ImageLoadReq loadReq, ViewWrapper wrapper) {
        super(loadReq, wrapper);
        setTag("ImageNbNetTask");
    }

    public Bitmap executeTask() {
        a.d("executeTask req: " + this.loadReq, new Object[0]);
        try {
            downloadFromNBNet(exeuteInit());
        } catch (DecryptException e) {
            notifyError(RETCODE.DECRYPT_FAILED, "decrypt file fail", e);
        }
        return null;
    }
}
