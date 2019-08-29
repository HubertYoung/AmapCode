package com.alipay.android.phone.inside.api.model.sharetoken;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.sharetoken.JumpShareTokenCode;

public class JumpShareTokenModel extends BaseModel<JumpShareTokenCode> {
    private String token;

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public IBizOperation<JumpShareTokenCode> getOperaion() {
        return new IBizOperation<JumpShareTokenCode>() {
            public JumpShareTokenCode parseResultCode(String str, String str2) {
                return JumpShareTokenCode.SUCCESS;
            }

            public ActionEnum getAction() {
                return ActionEnum.JUMP_TOKEN;
            }
        };
    }
}
