package com.alipay.android.phone.inside.api.model.accountopenauth;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.accountopenauth.AOAuthCode;

public class AOAuthModel extends BaseOpenAuthModel<AOAuthCode> {
    private String authUrl;
    private String phoneNumber;

    public String getAuthUrl() {
        return this.authUrl;
    }

    public void setAuthUrl(String str) {
        this.authUrl = str;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String str) {
        this.phoneNumber = str;
    }

    public IBizOperation<AOAuthCode> getOperaion() {
        return new IBizOperation<AOAuthCode>() {
            public AOAuthCode parseResultCode(String str, String str2) {
                return AOAuthCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.ALIPAY_OAUTH;
            }
        };
    }
}
