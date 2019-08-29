package com.alipay.android.phone.inside.api.model.iotads;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotads.IotAdsExitCode;

public class IotAdsExitModel extends IotAdsBaseModel<IotAdsExitCode> {
    public IBizOperation<IotAdsExitCode> getOperaion() {
        return new IBizOperation<IotAdsExitCode>() {
            public IotAdsExitCode parseResultCode(String str, String str2) {
                return IotAdsExitCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_ADS_EXIT;
            }
        };
    }
}
