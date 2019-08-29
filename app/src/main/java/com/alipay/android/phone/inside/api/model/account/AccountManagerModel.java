package com.alipay.android.phone.inside.api.model.account;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.account.AccountManagerCode;

public class AccountManagerModel extends BaseOpenAuthModel<AccountManagerCode> {
    public IBizOperation<AccountManagerCode> getOperaion() {
        return new IBizOperation<AccountManagerCode>() {
            public AccountManagerCode parseResultCode(String str, String str2) {
                return AccountManagerCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.ACCOUNT_MANAGER_ACTION;
            }
        };
    }
}
