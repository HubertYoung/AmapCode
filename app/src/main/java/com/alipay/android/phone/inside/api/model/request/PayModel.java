package com.alipay.android.phone.inside.api.model.request;

import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.model.operation.OnlinePayOp;
import com.alipay.android.phone.inside.api.result.code.OnlinePayCode;

public class PayModel extends BaseOpenAuthModel<OnlinePayCode> {
    private String payStr;

    public void setPayStr(String str) {
        this.payStr = str;
    }

    public String getPayStr() {
        return this.payStr;
    }

    public IBizOperation<OnlinePayCode> getOperaion() {
        return new OnlinePayOp();
    }
}
