package com.alipay.android.phone.inside.api.model.jiebei;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.jiebei.JiebeiCheckAuthRelationCode;

public class JiebeiCheckAuthRelationModel extends BaseOpenAuthModel<JiebeiCheckAuthRelationCode> {
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

    public IBizOperation<JiebeiCheckAuthRelationCode> getOperaion() {
        return new IBizOperation<JiebeiCheckAuthRelationCode>() {
            public JiebeiCheckAuthRelationCode parseResultCode(String str, String str2) {
                return JiebeiCheckAuthRelationCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.JIEBEI_CHECK_AUTH_RELATION_ACTION;
            }
        };
    }
}
