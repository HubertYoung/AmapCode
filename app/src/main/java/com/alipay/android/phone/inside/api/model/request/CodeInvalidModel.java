package com.alipay.android.phone.inside.api.model.request;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.code.CodeInvalidCode;

public class CodeInvalidModel extends BaseModel<CodeInvalidCode> {
    private String alipayUserId;
    private String shakeCode;

    public String getAlipayUserId() {
        return this.alipayUserId;
    }

    public void setAlipayUserId(String str) {
        this.alipayUserId = str;
    }

    public String getShakeCode() {
        return this.shakeCode;
    }

    public void setShakeCode(String str) {
        this.shakeCode = str;
    }

    public IBizOperation<CodeInvalidCode> getOperaion() {
        return new IBizOperation<CodeInvalidCode>() {
            public CodeInvalidCode parseResultCode(String str, String str2) {
                return CodeInvalidCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.CODE_INVALID;
            }
        };
    }
}
