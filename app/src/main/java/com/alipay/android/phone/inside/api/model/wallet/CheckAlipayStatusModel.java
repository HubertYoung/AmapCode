package com.alipay.android.phone.inside.api.model.wallet;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.wallet.CheckAlipayStatusCode;

public class CheckAlipayStatusModel extends BaseModel<CheckAlipayStatusCode> {
    private int minVersionCode;

    public int getMinVersionCode() {
        return this.minVersionCode;
    }

    public void setMinVersionCode(int i) {
        this.minVersionCode = i;
    }

    public IBizOperation<CheckAlipayStatusCode> getOperaion() {
        return new IBizOperation<CheckAlipayStatusCode>() {
            public CheckAlipayStatusCode parseResultCode(String str, String str2) {
                return CheckAlipayStatusCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.CHECK_ALIPAY_STATUS;
            }
        };
    }
}
