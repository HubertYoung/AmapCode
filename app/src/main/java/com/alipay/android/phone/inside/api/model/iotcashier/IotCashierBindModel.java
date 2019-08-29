package com.alipay.android.phone.inside.api.model.iotcashier;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotcashier.IotCashierBindCode;

public class IotCashierBindModel extends IotCashierBaseModel<IotCashierBindCode> {
    private int countDownTime;

    public int getCountDownTime() {
        return this.countDownTime;
    }

    public void setCountDownTime(int i) {
        this.countDownTime = i;
    }

    public IBizOperation<IotCashierBindCode> getOperaion() {
        return new IBizOperation<IotCashierBindCode>() {
            public IotCashierBindCode parseResultCode(String str, String str2) {
                return IotCashierBindCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_CASHIER_BIND;
            }
        };
    }
}
