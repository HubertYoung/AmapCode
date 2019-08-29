package com.alipay.android.phone.inside.api.model.account;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.account.AccountInfoCode;

public class AccountInfoModel extends BaseOpenAuthModel<AccountInfoCode> {
    public IBizOperation<AccountInfoCode> getOperaion() {
        return new IBizOperation<AccountInfoCode>() {
            public AccountInfoCode parseResultCode(String str, String str2) {
                return AccountInfoCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.ACCOUNT_INFO_ACTION;
            }
        };
    }
}
