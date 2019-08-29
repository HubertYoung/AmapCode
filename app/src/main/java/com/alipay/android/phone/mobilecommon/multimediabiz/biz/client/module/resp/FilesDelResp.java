package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.BaseResp;

public class FilesDelResp extends BaseResp {
    @JSONField(name = "data")
    private String[] failedFileIds;

    public String[] getFailedFileIds() {
        return this.failedFileIds;
    }

    public void setFailedFileIds(String[] failedFileIds2) {
        this.failedFileIds = failedFileIds2;
    }
}
