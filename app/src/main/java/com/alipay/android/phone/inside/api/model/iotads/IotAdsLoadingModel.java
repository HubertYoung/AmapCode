package com.alipay.android.phone.inside.api.model.iotads;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotads.IotAdsLoadingCode;

public class IotAdsLoadingModel extends IotAdsBaseModel<IotAdsLoadingCode> {
    private int delay;
    private boolean isvisible;
    private int timeout;

    public boolean isIsvisible() {
        return this.isvisible;
    }

    public void setIsvisible(boolean z) {
        this.isvisible = z;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int i) {
        this.timeout = i;
    }

    public int getDelay() {
        return this.delay;
    }

    public void setDelay(int i) {
        this.delay = i;
    }

    public IBizOperation<IotAdsLoadingCode> getOperaion() {
        return new IBizOperation<IotAdsLoadingCode>() {
            public IotAdsLoadingCode parseResultCode(String str, String str2) {
                return IotAdsLoadingCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_ADS_LOADING;
            }
        };
    }
}
