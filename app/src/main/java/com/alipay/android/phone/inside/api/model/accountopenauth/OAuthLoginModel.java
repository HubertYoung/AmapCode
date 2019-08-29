package com.alipay.android.phone.inside.api.model.accountopenauth;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.accountopenauth.OAuthLoginCode;

public class OAuthLoginModel extends BaseOpenAuthModel<OAuthLoginCode> {
    private String accessToken;
    private String alipayUid;
    private String bizSource;
    private String mcUid;

    public String getAlipayUid() {
        return this.alipayUid;
    }

    public void setAlipayUid(String str) {
        this.alipayUid = str;
    }

    public String getMcUid() {
        return this.mcUid;
    }

    public void setMcUid(String str) {
        this.mcUid = str;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String str) {
        this.accessToken = str;
    }

    public String getBizSource() {
        return this.bizSource;
    }

    public void setBizSource(String str) {
        this.bizSource = str;
    }

    public IBizOperation<OAuthLoginCode> getOperaion() {
        return new IBizOperation<OAuthLoginCode>() {
            public OAuthLoginCode parseResultCode(String str, String str2) {
                return OAuthLoginCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.ALIPAY_OPEN_AUTH_LOGIN;
            }
        };
    }
}
