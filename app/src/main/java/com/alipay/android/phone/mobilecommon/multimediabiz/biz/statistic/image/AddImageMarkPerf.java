package com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.BaseStatistics;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SP1;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SP2;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SP3;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SPExt;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.ST;
import com.alipay.multimedia.js.image.H5ImageBuildUrlPlugin.Params;
import java.util.Map;

@ST(caseId = "UC-MM-C41", seedId = "AddImageMarkPerf")
public class AddImageMarkPerf extends BaseStatistics {
    private long a = System.currentTimeMillis();
    @SPExt(name = "bz")
    public String bizId;
    @SPExt(name = "mh")
    public int markHeight = 100;
    @SPExt(name = "mid")
    public String markId;
    @SPExt(name = "mw")
    public int markWidth = 100;
    @SPExt(name = "nt")
    public long netTime;
    @SPExt(name = "px")
    public Integer paddingX;
    @SPExt(name = "py")
    public Integer paddingY;
    @SPExt(name = "per")
    public Integer percent;
    @SPExt(name = "pos")
    public int position = 5;
    @SP1
    public int retCode = 0;
    @SP2
    public long size = 0;
    @SP3
    public long totalTime;
    @SPExt(name = "ti")
    public String traceId;
    @SPExt(name = "tr")
    public int transparency = 80;

    public void submitRemoteAsync() {
        this.totalTime = System.currentTimeMillis() - this.a;
        super.submitRemoteAsync();
    }

    public String getCaseId() {
        return "UC-MM-C41";
    }

    public String getSeedId() {
        return "AddImageMarkPerf";
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
        ext.put("mid", String.valueOf(this.markId));
        ext.put("pos", String.valueOf(this.position));
        ext.put("tr", String.valueOf(this.transparency));
        ext.put("mw", String.valueOf(this.markWidth));
        ext.put("mh", String.valueOf(this.markHeight));
        ext.put(Params.UNIT_PX, String.valueOf(this.paddingX));
        ext.put("py", String.valueOf(this.paddingY));
        ext.put("per", String.valueOf(this.percent));
        ext.put("nt", String.valueOf(this.netTime));
        ext.put("bz", String.valueOf(this.bizId));
        ext.put("ti", String.valueOf(this.traceId));
    }
}
