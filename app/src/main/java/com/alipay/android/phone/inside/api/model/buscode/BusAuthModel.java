package com.alipay.android.phone.inside.api.model.buscode;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.buscode.BusAuthCode;

public class BusAuthModel extends BaseOpenAuthModel<BusAuthCode> {
    private String authBizData;
    private String pushDeviceId;

    public String getAuthBizData() {
        return this.authBizData;
    }

    public void setAuthBizData(String str) {
        this.authBizData = str;
    }

    public String getPushDeviceId() {
        return this.pushDeviceId;
    }

    public void setPushDeviceId(String str) {
        this.pushDeviceId = str;
    }

    public IBizOperation<BusAuthCode> getOperaion() {
        return new IBizOperation<BusAuthCode>() {
            public BusAuthCode parseResultCode(String str, String str2) {
                return BusAuthCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.BUS_AUTH_ACTION;
            }
        };
    }
}
