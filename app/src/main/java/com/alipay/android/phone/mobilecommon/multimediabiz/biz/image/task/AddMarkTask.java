package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task;

import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageMarkRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.mark.AddMarkRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.DjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.impl.HttpConnectionManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.impl.HttpDjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ThumbnailMarkAddReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ThumbnailMarkDownReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.AddMarkResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ZoomHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.AddImageMarkPerf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.MarkUtil;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.concurrent.Callable;

public class AddMarkTask implements Callable<AddMarkRsp> {
    private HttpDjangoClient a;
    private AddImageMarkPerf b;
    public ImageLoadReq loadReq;
    protected DisplayImageOptions options;

    public AddMarkTask(ImageLoadReq loadReq2) {
        this.loadReq = loadReq2;
        this.options = loadReq2.options;
    }

    public AddMarkRsp call() {
        this.b = new AddImageMarkPerf();
        AddMarkRsp addMarkRsp = new AddMarkRsp();
        DjangoClient djangoClient = getDjangoClient();
        ThumbnailMarkAddReq req = new ThumbnailMarkAddReq(this.loadReq.path, ZoomHelper.getZoom(this.loadReq));
        APImageMarkRequest markRequest = this.loadReq.options.getImageMarkRequest();
        MarkUtil.fillMarkParams((ThumbnailMarkDownReq) req, markRequest);
        long start = System.currentTimeMillis();
        AddMarkResp rsp = djangoClient.getImageApi().addWaterMark(req);
        this.b.netTime = System.currentTimeMillis() - start;
        APImageRetMsg retMsg = new APImageRetMsg();
        if (rsp == null || !rsp.isSuccess() || rsp.getSuccFileIds() == null || rsp.getSuccFileIds().length <= 0) {
            Logger.D("AddMarkTask", new StringBuilder(Token.SEPARATOR).append(rsp).toString(), new Object[0]);
            retMsg.setCode(RETCODE.UNKNOWN_ERROR);
        } else {
            retMsg.setCode(RETCODE.SUC);
        }
        this.b.bizId = this.options.getBizType();
        this.b.traceId = rsp != null ? rsp.getTraceId() : "";
        addMarkRsp.setRetmsg(retMsg);
        a(markRequest, this.b);
        this.b.submitRemoteAsync();
        return addMarkRsp;
    }

    private static void a(APImageMarkRequest markRequest, AddImageMarkPerf markPerf) {
        markPerf.markId = markRequest.getMarkId();
        markPerf.markWidth = markRequest.getMarkWidth().intValue();
        markPerf.markHeight = markRequest.getMarkHeight().intValue();
        markPerf.paddingX = markRequest.getPaddingX();
        markPerf.paddingY = markRequest.getPaddingY();
        markPerf.position = markRequest.getPosition().intValue();
        markPerf.transparency = markRequest.getTransparency().intValue();
        markPerf.percent = markRequest.getPercent();
    }

    /* access modifiers changed from: protected */
    public synchronized DjangoClient getDjangoClient() {
        if (this.a == null) {
            this.a = new HttpDjangoClient(AppUtils.getApplicationContext(), new HttpConnectionManager());
        }
        return this.a;
    }
}
