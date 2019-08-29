package com.alipay.android.phone.inside.api.model.changecode;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.changecode.ChangeCodeAuthCode;

public class ChangeCodeAuthModel extends BaseModel<ChangeCodeAuthCode> {
    public IBizOperation<ChangeCodeAuthCode> getOperaion() {
        return new IBizOperation<ChangeCodeAuthCode>() {
            public ChangeCodeAuthCode parseResultCode(String str, String str2) {
                return ChangeCodeAuthCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.CHANGE_CODE_AUTH_ACTION;
            }
        };
    }
}
