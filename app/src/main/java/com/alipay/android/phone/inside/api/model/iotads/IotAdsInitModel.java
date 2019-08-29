package com.alipay.android.phone.inside.api.model.iotads;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotads.IotAdsInitCode;

public class IotAdsInitModel extends IotAdsBaseModel<IotAdsInitCode> {
    private boolean initRpc = true;

    public boolean isInitRpc() {
        return this.initRpc;
    }

    public void setInitRpc(boolean z) {
        this.initRpc = z;
    }

    public IBizOperation<IotAdsInitCode> getOperaion() {
        return new IBizOperation<IotAdsInitCode>() {
            public IotAdsInitCode parseResultCode(String str, String str2) {
                return IotAdsInitCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_ADS_INIT;
            }
        };
    }
}
