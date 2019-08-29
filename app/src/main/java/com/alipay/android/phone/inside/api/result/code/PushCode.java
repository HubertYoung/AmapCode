package com.alipay.android.phone.inside.api.result.code;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;

public class PushCode extends ResultCode {
    public static final PushCode FAILED = new PushCode("push_9001", "数据格式异常。");
    public static final PushCode PARAMS_ILLEGAL = new PushCode("push_9002", "参数非法。");

    protected PushCode(String str, String str2) {
        super(str, str2);
    }

    public static PushCode parse(String str) {
        if (TextUtils.equals(str, FAILED.getValue())) {
            return FAILED;
        }
        if (TextUtils.equals(str, PARAMS_ILLEGAL.getValue())) {
            return PARAMS_ILLEGAL;
        }
        return null;
    }
}
