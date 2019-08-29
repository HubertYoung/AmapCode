package com.alipay.android.phone.inside.api.model.iotcashier;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotcashier.IotCashierUnbindCode;

public class IotCashierUnbindModel extends IotCashierBaseModel<IotCashierUnbindCode> {
    private int countDownTime;

    public int getCountDownTime() {
        return this.countDownTime;
    }

    public void setCountDownTime(int i) {
        this.countDownTime = i;
    }

    public IBizOperation<IotCashierUnbindCode> getOperaion() {
        return new IBizOperation<IotCashierUnbindCode>() {
            public IotCashierUnbindCode parseResultCode(String str, String str2) {
                return IotCashierUnbindCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_CASHIER_UNBIND;
            }
        };
    }
}
