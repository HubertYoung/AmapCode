package com.alipay.android.phone.inside.api.model.aliautologin;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.aliautologin.AliAutoLoginCode;

public class AliAutoLoginModel extends BaseModel {
    private String bizScene;
    private boolean forceAuth;
    private String saveAliLoginCookie;
    private boolean showUi;
    private String source;
    private String sourceType;
    private String targetUrl;

    public String getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(SourceTypeEnum sourceTypeEnum) {
        this.sourceType = sourceTypeEnum == null ? "unknown" : sourceTypeEnum.getSourceTypeName();
    }

    public String getTargetUrl() {
        return this.targetUrl;
    }

    public void setTargetUrl(String str) {
        this.targetUrl = str;
    }

    public boolean isForceAuth() {
        return this.forceAuth;
    }

    public void setForceAuth(boolean z) {
        this.forceAuth = z;
    }

    public boolean isShowUi() {
        return this.showUi;
    }

    public void setShowUi(boolean z) {
        this.showUi = z;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String str) {
        this.source = str;
    }

    public String getBizScene() {
        return this.bizScene;
    }

    public void setBizScene(String str) {
        this.bizScene = str;
    }

    public String getSaveAliLoginCookie() {
        return this.saveAliLoginCookie;
    }

    public void setSaveAliLoginCookie(String str) {
        this.saveAliLoginCookie = str;
    }

    public IBizOperation getOperaion() {
        return new IBizOperation<AliAutoLoginCode>() {
            public AliAutoLoginCode parseResultCode(String str, String str2) {
                return AliAutoLoginCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.ALI_AUTO_LOGIN_DO_LOGIN;
            }
        };
    }
}
