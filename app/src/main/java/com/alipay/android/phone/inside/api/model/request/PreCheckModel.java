package com.alipay.android.phone.inside.api.model.request;

import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.model.operation.PreCheckOp;
import com.alipay.android.phone.inside.api.result.code.PreCheckCode;

public class PreCheckModel extends BaseModel<PreCheckCode> {
    private String alipayUserId;
    private String pushDeviceId;

    public String getAlipayUserId() {
        return this.alipayUserId;
    }

    public void setAlipayUserId(String str) {
        this.alipayUserId = str;
    }

    public String getPushDeviceId() {
        return this.pushDeviceId;
    }

    public void setPushDeviceId(String str) {
        this.pushDeviceId = str;
    }

    public IBizOperation<PreCheckCode> getOperaion() {
        return new PreCheckOp();
    }
}
