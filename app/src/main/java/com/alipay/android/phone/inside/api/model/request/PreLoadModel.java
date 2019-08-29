package com.alipay.android.phone.inside.api.model.request;

import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.model.operation.PreLoadOp;
import com.alipay.android.phone.inside.api.result.code.PreLoadCode;

public class PreLoadModel extends BaseModel<PreLoadCode> {
    public IBizOperation<PreLoadCode> getOperaion() {
        return new PreLoadOp();
    }
}
