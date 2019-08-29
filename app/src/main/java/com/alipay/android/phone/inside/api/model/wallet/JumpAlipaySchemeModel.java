package com.alipay.android.phone.inside.api.model.wallet;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.wallet.JumpAlipaySchemeCode;

public class JumpAlipaySchemeModel extends BaseModel<JumpAlipaySchemeCode> {
    private String alipayScheme;
    private int minVersionCode;

    public String getAlipayScheme() {
        return this.alipayScheme;
    }

    public void setAlipayScheme(String str) {
        this.alipayScheme = str;
    }

    public int getMinVersionCode() {
        return this.minVersionCode;
    }

    public void setMinVersionCode(int i) {
        this.minVersionCode = i;
    }

    public IBizOperation<JumpAlipaySchemeCode> getOperaion() {
        return new IBizOperation<JumpAlipaySchemeCode>() {
            public JumpAlipaySchemeCode parseResultCode(String str, String str2) {
                return JumpAlipaySchemeCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.JUMP_ALIPAY_SCHEME;
            }
        };
    }
}
