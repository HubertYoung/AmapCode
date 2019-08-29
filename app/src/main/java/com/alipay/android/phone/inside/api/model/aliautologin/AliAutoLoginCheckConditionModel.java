package com.alipay.android.phone.inside.api.model.aliautologin;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.aliautologin.AliAutoLoginCheckConditionCode;

public class AliAutoLoginCheckConditionModel extends BaseModel {
    private String targetUrl;

    public String getTargetUrl() {
        return this.targetUrl;
    }

    public void setTargetUrl(String str) {
        this.targetUrl = str;
    }

    public IBizOperation getOperaion() {
        return new IBizOperation<AliAutoLoginCheckConditionCode>() {
            public AliAutoLoginCheckConditionCode parseResultCode(String str, String str2) {
                return AliAutoLoginCheckConditionCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.ALI_AUTO_LOGIN_CHECK_CONDITION;
            }
        };
    }
}
