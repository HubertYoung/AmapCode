package com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.BaseStatistics;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SP1;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SP2;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SP3;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SPExt;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.ST;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import java.util.Map;

@ST(caseId = "UC-MM-C35", seedId = "UploadImagePerf")
public class UploadImagePerf extends BaseStatistics implements Cloneable {
    @SPExt(name = "bz")
    public String biz;
    @SPExt(name = "co")
    public int compressLevel;
    @SPExt(name = "et")
    public long encodeTime;
    @SPExt(name = "ent")
    public long encryptTime;
    @SPExt(name = "exp")
    public String exception;
    @SPExt(name = "id")
    public String id;
    @SPExt(name = "it")
    public int imageType;
    @SPExt(name = "md5")
    public String md5;
    @SPExt(name = "unm")
    public int netMethod;
    @SPExt(name = "nt")
    public long netTime;
    @SPExt(name = "os")
    public long originalSize;
    @SPExt(name = "ra")
    public int rapid;
    @SP1
    public int retCode = 0;
    @SPExt(name = "ct")
    public long searchCacheTime;
    @SP2
    public long size = 0;
    public long startTime = System.currentTimeMillis();
    @SP3
    public long totalTime;
    @SPExt(name = "ti")
    public String traceId;

    public void submitRemoteAsync() {
        this.totalTime = System.currentTimeMillis() - this.startTime;
        super.submitRemoteAsync();
    }

    public String getCaseId() {
        return "UC-MM-C35";
    }

    public String getSeedId() {
        return "UploadImagePerf";
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
        ext.put("et", String.valueOf(this.encodeTime));
        ext.put(LogItem.MM_C43_K4_CAMERA_TIME, String.valueOf(this.searchCacheTime));
        ext.put("nt", String.valueOf(this.netTime));
        ext.put("unm", String.valueOf(this.netMethod));
        ext.put("ra", String.valueOf(this.rapid));
        ext.put(LogItem.MM_C01_K4_CO, String.valueOf(this.compressLevel));
        ext.put("it", String.valueOf(this.imageType));
        ext.put("bz", String.valueOf(this.biz));
        ext.put("os", String.valueOf(this.originalSize));
        ext.put("md5", String.valueOf(this.md5));
        ext.put("ti", String.valueOf(this.traceId));
        ext.put("exp", String.valueOf(this.exception));
        ext.put("id", this.id == null ? "" : this.id);
        ext.put("ent", String.valueOf(this.encryptTime));
    }
}
