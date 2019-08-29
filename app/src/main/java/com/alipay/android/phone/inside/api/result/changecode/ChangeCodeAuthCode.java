package com.alipay.android.phone.inside.api.result.changecode;

import com.alipay.android.phone.inside.api.result.ResultCode;

public class ChangeCodeAuthCode extends ResultCode {
    protected ChangeCodeAuthCode(String str) {
        super(str);
    }

    public static ChangeCodeAuthCode parse(String str) {
        return new ChangeCodeAuthCode(str);
    }
}
