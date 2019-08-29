package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.unilink;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.ImageLocalSmartCutTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.ImageMMTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.ImageUrlTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;

public class ImageUniLinkLocalSmartCutTask extends ImageLocalSmartCutTask {
    public ImageUniLinkLocalSmartCutTask(ImageLoadReq loadReq, ViewWrapper wrapper) {
        super(loadReq, wrapper);
    }

    /* access modifiers changed from: protected */
    public ImageMMTask getImageMMTask() {
        if (this.loadReq.isEncryptRequest() || !ConfigManager.getInstance().getAftsLinkConf().isAftsImageSwitchOn(this.loadReq.options.getBizType())) {
            return super.getImageMMTask();
        }
        return a();
    }

    private ImageMMTask a() {
        if (PathUtils.isHttp(this.loadReq.path)) {
            String domain = PathUtils.extractDomain(this.loadReq.path);
            if (TextUtils.isEmpty(domain) || !ConfigManager.getInstance().isConvergenceDomain(domain)) {
                return new ImageUrlTask(this.loadReq, this.viewWrapper);
            }
            return new ImageAftsUrlTask(this.loadReq, this.viewWrapper);
        } else if (b()) {
            return new ImageNbNetTask(this.loadReq, this.viewWrapper);
        } else {
            return new ImageAftsUrlTask(this.loadReq, this.viewWrapper);
        }
    }

    private boolean b() {
        return this.loadReq.isEncryptRequest() || this.loadReq.getTransportWay() == 2 || (PathUtils.isAftsId(this.loadReq.path) && !PathUtils.isFilePublic(this.loadReq.path));
    }
}
