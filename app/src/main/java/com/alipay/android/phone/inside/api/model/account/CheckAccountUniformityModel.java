package com.alipay.android.phone.inside.api.model.account;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.account.CheckAccountUniformityCode;

public class CheckAccountUniformityModel extends BaseOpenAuthModel<CheckAccountUniformityCode> {
    public IBizOperation<CheckAccountUniformityCode> getOperaion() {
        return new IBizOperation<CheckAccountUniformityCode>() {
            public CheckAccountUniformityCode parseResultCode(String str, String str2) {
                return CheckAccountUniformityCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.CHECK_ACCOUNT_UNIFORMITY;
            }
        };
    }
}
