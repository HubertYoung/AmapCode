package com.alipay.android.phone.inside.api.model.iotcashier;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotcashier.IotCashierCheckBindStatusCode;

public class IotCashierCheckBindStatusModel extends IotCashierBaseModel<IotCashierCheckBindStatusCode> {
    public IBizOperation<IotCashierCheckBindStatusCode> getOperaion() {
        return new IBizOperation<IotCashierCheckBindStatusCode>() {
            public IotCashierCheckBindStatusCode parseResultCode(String str, String str2) {
                return IotCashierCheckBindStatusCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_CASHIER_CHECK_BIND_STATUS;
            }
        };
    }
}
