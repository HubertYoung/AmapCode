package com.alipay.android.phone.inside.api.model.iotadx;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotadx.IotAdxResultCode;

public class AdxActionModel extends AdxBaseModel {
    public IBizOperation<IotAdxResultCode> getOperaion() {
        return new IBizOperation<IotAdxResultCode>() {
            public IotAdxResultCode parseResultCode(String str, String str2) {
                return IotAdxResultCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_ADX_ACTION;
            }
        };
    }
}
