package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.BaseResp;
import java.util.Arrays;

public class AddMarkResp extends BaseResp {
    @JSONField(name = "data")
    private String[] succFileIds;

    public String[] getSuccFileIds() {
        return this.succFileIds;
    }

    public void setSuccFileIds(String[] succFileIds2) {
        this.succFileIds = succFileIds2;
    }

    public String toString() {
        return "AddMarkResp{" + super.toString() + "succFileIds=" + Arrays.toString(this.succFileIds) + '}';
    }
}
