package com.alipay.android.phone.inside.api.model.generalh5biz;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.generalh5biz.LaunchCode;

public class LaunchModel extends BaseOpenAuthModel<LaunchCode> {
    private String authURL;
    private String bizURL;

    public String getAuthURL() {
        return this.authURL;
    }

    public void setAuthURL(String str) {
        this.authURL = str;
    }

    public String getBizURL() {
        return this.bizURL;
    }

    public void setBizURL(String str) {
        this.bizURL = str;
    }

    public IBizOperation<LaunchCode> getOperaion() {
        return new IBizOperation<LaunchCode>() {
            public LaunchCode parseResultCode(String str, String str2) {
                return LaunchCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.LAUNCH_ACTION;
            }
        };
    }
}
