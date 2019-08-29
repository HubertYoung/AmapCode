package com.alipay.android.phone.inside.api.model.buscode;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.buscode.BusUnauthCode;

public class BusUnauthModel extends BaseOpenAuthModel<BusUnauthCode> {
    public IBizOperation<BusUnauthCode> getOperaion() {
        return new IBizOperation<BusUnauthCode>() {
            public BusUnauthCode parseResultCode(String str, String str2) {
                return BusUnauthCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.BUS_UNAUTH_ACTION;
            }
        };
    }
}
