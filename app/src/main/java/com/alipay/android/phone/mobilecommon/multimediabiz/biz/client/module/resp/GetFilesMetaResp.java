package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.BaseResp;
import java.util.ArrayList;
import java.util.List;

public class GetFilesMetaResp extends BaseResp {
    @JSONField(name = "data")
    private List<DjangoFileInfoResp> filesMeta;

    public List<DjangoFileInfoResp> getFilesMeta() {
        if (this.filesMeta == null) {
            this.filesMeta = new ArrayList();
        }
        return this.filesMeta;
    }

    public void setFilesMeta(List<DjangoFileInfoResp> filesMeta2) {
        this.filesMeta = filesMeta2;
    }
}
