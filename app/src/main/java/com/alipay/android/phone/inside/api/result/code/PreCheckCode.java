package com.alipay.android.phone.inside.api.result.code;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;

public class PreCheckCode extends ResultCode {
    public static final PreCheckCode AUTH_INVALID = new PreCheckCode("precheck_9001", "授权过期，请重新授权。");
    public static final PreCheckCode FAILED = new PreCheckCode("precheck_9002", "预校验失败。");
    public static final PreCheckCode PARAMS_ILLEGAL = new PreCheckCode("precheck_9003", "预校验失败，参数非法。");
    public static final PreCheckCode SUCCESS = new PreCheckCode("precheck_9000", "预校验成功。");

    protected PreCheckCode(String str, String str2) {
        super(str, str2);
    }

    public static PreCheckCode parse(String str) {
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
