package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.unilink;

import android.net.Uri;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.ImageDjangoOriginalTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.ImageLocalTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.ImageNetTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.ImageUrlTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;

public class ImageUniLinkLocalTask extends ImageLocalTask {
    public ImageUniLinkLocalTask(ImageLoadReq loadReq, ViewWrapper wrapper) {
        super(loadReq, wrapper);
    }

    /* access modifiers changed from: protected */
    public boolean startNetTask() {
        if (this.loadReq.isEncryptRequest() || !ConfigManager.getInstance().getAftsLinkConf().isAftsImageSwitchOn(this.loadReq.options.getBizType())) {
            return super.startNetTask();
        }
        return a();
    }

    private boolean a() {
        ImageNetTask netTask;
        if (PathUtils.isHttp(Uri.parse(this.loadReq.source))) {
            String domain = PathUtils.extractDomain(this.loadReq.path);
            if (TextUtils.isEmpty(domain) || !ConfigManager.getInstance().isConvergenceDomain(domain)) {
                netTask = new ImageUrlTask(this.loadReq, this.viewWrapper);
            } else {
                netTask = new ImageAftsUrlTask(this.loadReq, this.viewWrapper);
            }
        } else if (checkDjgIdAndCurrentLimit()) {
            return true;
        } else {
            if (CutScaleType.NONE.equals(this.options.getCutScaleType())) {
                netTask = new ImageDjangoOriginalTask(this.loadReq, this.viewWrapper);
            } else if (b()) {
                netTask = new ImageNbNetTask(this.loadReq, this.viewWrapper);
            } else {
                netTask = new ImageAftsUrlTask(this.loadReq, this.viewWrapper);
            }
        }
        addNetTask(netTask);
        Logger.D("ImageUniLinkLocalTask", "startAftsLinkNetTask cachekey=" + this.loadReq.cacheKey, new Object[0]);
        return false;
    }

    private boolean b() {
        return this.loadReq.isEncryptRequest() || this.loadReq.getTransportWay() == 2 || (PathUtils.isAftsId(this.loadReq.path) && !PathUtils.isFilePublic(this.loadReq.path));
    }
}
