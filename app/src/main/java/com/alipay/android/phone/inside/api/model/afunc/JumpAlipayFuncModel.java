package com.alipay.android.phone.inside.api.model.afunc;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.afunc.JumpAlipayFuncCode;

public class JumpAlipayFuncModel extends BaseModel<JumpAlipayFuncCode> {
    private String alipayFunc;
    private String jumpScheme;
    private int minVersionCode;
    private String source;

    public String getSource() {
        return this.source;
    }

    public void setSource(String str) {
        this.source = str;
    }

    public String getJumpScheme() {
        return this.jumpScheme;
    }

    public void setJumpScheme(String str) {
        this.jumpScheme = str;
    }

    public String getAlipayFunc() {
        return this.alipayFunc;
    }

    public void setAlipayFunc(String str) {
        this.alipayFunc = str;
    }

    public int getMinVersionCode() {
        return this.minVersionCode;
    }

    public void setMinVersionCode(int i) {
        this.minVersionCode = i;
    }

    public IBizOperation<JumpAlipayFuncCode> getOperaion() {
        return new IBizOperation<JumpAlipayFuncCode>() {
            public JumpAlipayFuncCode parseResultCode(String str, String str2) {
                return JumpAlipayFuncCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.JUMP_ALIPAY_FUNC;
            }
        };
    }
}
