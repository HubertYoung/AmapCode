package com.alipay.android.phone.inside.api.model.account;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.account.AccountLogoutCode;

public class AccountLogoutModel extends BaseOpenAuthModel<AccountLogoutCode> {
    public IBizOperation<AccountLogoutCode> getOperaion() {
        return new IBizOperation<AccountLogoutCode>() {
            public AccountLogoutCode parseResultCode(String str, String str2) {
                return AccountLogoutCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.ACCOUNT_LOGOUT_ACTION;
            }
        };
    }
}
