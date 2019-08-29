package com.alipay.android.phone.inside.api.model.wallet;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.wallet.SafeJumpAlipaySchemeCode;

public class SafeJumpAlipaySchemeModel extends BaseModel<SafeJumpAlipaySchemeCode> {
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

    public IBizOperation<SafeJumpAlipaySchemeCode> getOperaion() {
        return new IBizOperation<SafeJumpAlipaySchemeCode>() {
            public SafeJumpAlipaySchemeCode parseResultCode(String str, String str2) {
                return SafeJumpAlipaySchemeCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.SAFE_JUMP_ALIPAY_SCHEME;
            }
        };
    }
}
