package com.alipay.android.phone.inside.api.model.request;

import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.model.operation.SwitchUserOp;
import com.alipay.android.phone.inside.api.result.code.SwitchUserCode;

public class SwithUserRequestModel extends BaseModel<SwitchUserCode> {
    public IBizOperation<SwitchUserCode> getOperaion() {
        return new SwitchUserOp();
    }
}
