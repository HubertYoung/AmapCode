package com.alipay.android.phone.inside.api.model.request;

import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.model.operation.QueryCodeOp;
import com.alipay.android.phone.inside.api.result.code.QueryPayCode;

public class QueryPayModel extends BaseOpenAuthModel<QueryPayCode> {
    private String appName;
    private String payCode;

    public void setAppName(String str) {
        this.appName = str;
    }

    public String getAppName() {
        return this.appName;
    }

    public String getPayCode() {
        return this.payCode;
    }

    public void setPayCode(String str) {
        this.payCode = str;
    }

    public IBizOperation<QueryPayCode> getOperaion() {
        return new QueryCodeOp();
    }
}
