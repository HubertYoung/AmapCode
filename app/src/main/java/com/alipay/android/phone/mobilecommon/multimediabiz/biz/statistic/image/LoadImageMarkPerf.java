package com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SPExt;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.ST;
import com.alipay.multimedia.js.image.H5ImageBuildUrlPlugin.Params;
import java.util.Map;

@ST(caseId = "UC-MM-C42", seedId = "LoadImageMarkPerf")
public class LoadImageMarkPerf extends LoadImageFromNetworkPerf {
    @SPExt(name = "mh")
    public int markHeight = 100;
    @SPExt(name = "mid")
    public String markId;
    @SPExt(name = "mw")
    public int markWidth = 100;
    @SPExt(name = "px")
    public Integer paddingX;
    @SPExt(name = "py")
    public Integer paddingY;
    @SPExt(name = "per")
    public Integer percent;
    @SPExt(name = "pos")
    public int position = 5;
    @SPExt(name = "tr")
    public int transparency = 80;

    public LoadImageMarkPerf(ImageLoadReq loadReq) {
        super(loadReq);
    }

    public String getCaseId() {
        return "UC-MM-C42";
    }

    public String getSeedId() {
        return "LoadImageMarkPerf";
    }

    /* access modifiers changed from: protected */
    public void fillExtParams(Map<String, String> ext) {
        super.fillExtParams(ext);
        ext.put("mid", String.valueOf(this.markId));
        ext.put("pos", String.valueOf(this.position));
        ext.put("tr", String.valueOf(this.transparency));
        ext.put("mw", String.valueOf(this.markWidth));
        ext.put("mh", String.valueOf(this.markHeight));
        ext.put(Params.UNIT_PX, String.valueOf(this.paddingX));
        ext.put("py", String.valueOf(this.paddingY));
        ext.put("per", String.valueOf(this.percent));
        ext.put("nt", String.valueOf(this.netTime));
    }
}
