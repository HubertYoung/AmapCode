package com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ZoomHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.BaseStatistics;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SP1;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SP2;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SP3;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SPExt;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.ST;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CompareUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.framework.app.ui.ActivityHelper;
import com.uc.webview.export.internal.interfaces.IWaStat;
import java.util.Map;
import java.util.Map.Entry;

@ST(caseId = "UC-MM-C36", seedId = "LoadImageFromNetworkPerf")
public class LoadImageFromNetworkPerf extends BaseStatistics implements Cloneable {
    private static final String[] a = {IWaStat.KEY_CHECK_COMPRESS};
    private long b;
    @SPExt(name = "bz")
    public String biz;
    @SPExt(name = "ccode")
    public int convergeResultCode;
    public long decodeTime = 0;
    @SPExt(name = "det")
    public long decryptTime = 0;
    @SPExt(name = "dt")
    public int downloadType = 0;
    @SPExt(name = "exp")
    public String exception;
    @SPExt(name = "ft")
    public int fileType;
    public boolean hasNetwork = true;
    @SPExt(name = "id")
    public String id;
    @SPExt(name = "tp")
    public int imageType;
    @SPExt(name = "isb")
    public int isBack = 0;
    public ImageLoadReq loadReq;
    @SPExt(name = "unm")
    public int netMethod = 1;
    @SPExt(name = "nt")
    public long netTime = -1;
    @SPExt(name = "nnt")
    public int noNetwork = 0;
    @SPExt(name = "ol")
    public String originalUrl;
    @SPExt(name = "pt")
    public long pjpgTime = -1;
    @SPExt(name = "st")
    public long prepareTime;
    @SPExt(name = "gl")
    public int progressive;
    @SPExt(name = "q")
    public int quality;
    @SP1
    public int retCode = 0;
    @SP2
    public long size = 0;
    public long startTime = System.currentTimeMillis();
    @SP3
    public long totalTime;
    @SPExt(name = "ti")
    public String traceId;
    @SPExt(name = "wt")
    public long waitTime;
    @SPExt(name = "wp")
    public int webp;
    @SPExt(name = "zo")
    public String zoom;

    public LoadImageFromNetworkPerf(ImageLoadReq loadReq2) {
        this.loadReq = loadReq2;
    }

    public void submitRemoteAsync() {
        int i;
        int i2;
        int i3 = 1;
        this.totalTime = System.currentTimeMillis() - this.startTime;
        if (this.loadReq.options.getCutScaleType() == CutScaleType.NONE) {
            this.zoom = "0x0";
        } else if (TextUtils.isEmpty(this.zoom)) {
            this.zoom = ZoomHelper.getZoom(this.loadReq);
        }
        if (this.zoom.contains(".webp")) {
            i = 1;
        } else {
            i = 0;
        }
        this.webp = i;
        if (this.zoom.contains(DjangoConstant.PROGRESSIVE_KEY)) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        this.progressive = i2;
        this.quality = this.loadReq.options.getQuality();
        this.imageType = ImageStType.getType(this.loadReq.options.getCutScaleType());
        this.biz = this.loadReq.options.getBizType();
        if (this.hasNetwork || this.retCode == 0) {
            i3 = 0;
        }
        this.noNetwork = i3;
        super.submitRemoteAsync();
    }

    public String getCaseId() {
        return "UC-MM-C36";
    }

    public String getSeedId() {
        return "LoadImageFromNetworkPerf";
    }

    public String getParam1() {
        return String.valueOf(this.retCode);
    }

    public String getParam2() {
        return String.valueOf(this.size);
    }

    public String getParam3() {
        return String.valueOf(this.totalTime);
    }

    /* access modifiers changed from: protected */
    public void fillExtParams(Map<String, String> ext) {
        ext.put("st", String.valueOf(this.prepareTime));
        ext.put(wt.a, String.valueOf(this.waitTime));
        ext.put("nt", String.valueOf(this.netTime));
        ext.put("unm", String.valueOf(this.netMethod));
        ext.put("wp", String.valueOf(this.webp));
        ext.put("q", String.valueOf(this.quality));
        ext.put("gl", String.valueOf(this.progressive));
        ext.put(LogItem.MM_C04_K4_ZO, String.valueOf(this.zoom));
        ext.put("tp", String.valueOf(this.imageType));
        ext.put("bz", String.valueOf(this.biz));
        ext.put("id", String.valueOf(this.id));
        ext.put("ti", String.valueOf(this.traceId));
        ext.put("exp", String.valueOf(this.exception));
        ext.put("pt", String.valueOf(this.pjpgTime));
        ext.put("dc", String.valueOf(this.decodeTime));
        ext.put("dt", String.valueOf(this.downloadType));
        ext.put("det", String.valueOf(this.decryptTime));
        if (!TextUtils.isEmpty(this.originalUrl)) {
            ext.put("ol", this.originalUrl);
        }
        ext.put("ccode", String.valueOf(this.convergeResultCode));
        ext.put(LogItem.MM_C43_K4_FINISH_TIME, String.valueOf(this.fileType));
        ext.put(LogItem.MM_K4_NO_NETWORK, String.valueOf(this.noNetwork));
        if (ActivityHelper.isBackgroundRunning()) {
            ext.put("isb", "1");
        } else {
            ext.put("isb", "0");
        }
        if (this.loadReq.options.extInfo != null && !this.loadReq.options.extInfo.isEmpty()) {
            for (Entry entry : this.loadReq.options.extInfo.entrySet()) {
                if (CompareUtils.arrayContains(entry.getKey(), a)) {
                    ext.put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    public void beginWait() {
        this.b = System.currentTimeMillis();
    }

    public void endWait() {
        this.waitTime = System.currentTimeMillis() - this.b;
    }
}
