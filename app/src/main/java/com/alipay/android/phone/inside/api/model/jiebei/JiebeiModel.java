package com.alipay.android.phone.inside.api.model.jiebei;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.jiebei.JiebeiOperationCode;

public class JiebeiModel extends BaseOpenAuthModel<JiebeiOperationCode> {
    private String appId;
    private String authURL;
    private String bizURL;
    private String scopes;

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

    public void setAppId(String str) {
        this.appId = str;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setScopes(String str) {
        this.scopes = str;
    }

    public String getScopes() {
        return this.scopes;
    }

    public IBizOperation<JiebeiOperationCode> getOperaion() {
        return new IBizOperation<JiebeiOperationCode>() {
            public JiebeiOperationCode parseResultCode(String str, String str2) {
                return JiebeiOperationCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.JIEBEI_START_ACTION;
            }
        };
    }
}
