package com.alipay.android.phone.inside.api.model.account;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.account.OAuthAccountUniformityCode;

public class OAuthAccountUniformityModel extends BaseOpenAuthModel<OAuthAccountUniformityCode> {
    private String mcBindAlipayUid;

    public String getMcBindAlipayUid() {
        return this.mcBindAlipayUid;
    }

    public void setMcBindAlipayUid(String str) {
        this.mcBindAlipayUid = str;
    }

    public IBizOperation<OAuthAccountUniformityCode> getOperaion() {
        return new IBizOperation<OAuthAccountUniformityCode>() {
            public OAuthAccountUniformityCode parseResultCode(String str, String str2) {
                return OAuthAccountUniformityCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.OAUTH_ACCOUNT_UNIFORMITY;
            }
        };
    }
}
