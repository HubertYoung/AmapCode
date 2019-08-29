package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.BaseResp;

public class DjangoFileInfoRespWrapper extends BaseResp {
    @JSONField(name = "data")
    private DjangoFileInfoResp fileInfo;

    public DjangoFileInfoResp getFileInfo() {
        return this.fileInfo;
    }

    public void setFileInfo(DjangoFileInfoResp fileInfo2) {
        this.fileInfo = fileInfo2;
    }

    public String toString() {
        return "DjangoFileInfoRespWrapper{fileInfo=" + this.fileInfo + "}\n" + super.toString();
    }
}
