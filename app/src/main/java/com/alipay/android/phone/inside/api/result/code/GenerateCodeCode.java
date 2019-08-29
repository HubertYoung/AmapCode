package com.alipay.android.phone.inside.api.result.code;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;

public class GenerateCodeCode extends ResultCode {
    public static final GenerateCodeCode AUTH_INVALID = new GenerateCodeCode("gen_code_9002", "生成付款码，授权过期，请重新授权。");
    public static final GenerateCodeCode FAILED = new GenerateCodeCode("gen_code_9001", "生成付款码，失败，请重试。");
    public static final GenerateCodeCode PARAMS_ILLEGAL = new GenerateCodeCode("gen_code_9003", "生成付款码，参数非法，请重试。");
    public static final GenerateCodeCode SUCCESS = new GenerateCodeCode("gen_code_9000", "生成付款码，成功。");

    protected GenerateCodeCode(String str, String str2) {
        super(str, str2);
    }

    public static GenerateCodeCode parse(String str) {
        if (TextUtils.equals(str, SUCCESS.getValue())) {
            return SUCCESS;
        }
        if (TextUtils.equals(str, FAILED.getValue())) {
            return FAILED;
        }
        if (TextUtils.equals(str, AUTH_INVALID.getValue())) {
            return AUTH_INVALID;
        }
        if (TextUtils.equals(str, PARAMS_ILLEGAL.getValue())) {
            return PARAMS_ILLEGAL;
        }
        return null;
    }
}
