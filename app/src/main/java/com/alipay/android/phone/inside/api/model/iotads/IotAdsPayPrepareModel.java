package com.alipay.android.phone.inside.api.model.iotads;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotads.IotAdsPayPrepareCode;

public class IotAdsPayPrepareModel extends IotAdsBaseModel<IotAdsPayPrepareCode> {
    private String barToken;
    private boolean isFace;

    public String getBarToken() {
        return this.barToken;
    }

    public void setBarToken(String str) {
        this.barToken = str;
    }

    public boolean isFace() {
        return this.isFace;
    }

    public void setFace(boolean z) {
        this.isFace = z;
    }

    public IBizOperation<IotAdsPayPrepareCode> getOperaion() {
        return new IBizOperation<IotAdsPayPrepareCode>() {
            public IotAdsPayPrepareCode parseResultCode(String str, String str2) {
                return IotAdsPayPrepareCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_ADS_PAY_PREPARE;
            }
        };
    }
}
