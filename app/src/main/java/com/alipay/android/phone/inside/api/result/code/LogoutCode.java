package com.alipay.android.phone.inside.api.result.code;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;

public class LogoutCode extends ResultCode {
    public static final LogoutCode FAILED = new LogoutCode("logout_9001", "解除授权，失败，请重试。");
    public static final LogoutCode PARAMS_ILLEGAL = new LogoutCode("logout_9002", "解除授权，参数非法，请重试。");
    public static final LogoutCode SUCCESS = new LogoutCode("logout_9000", "解除授权，成功。");

    protected LogoutCode(String str, String str2) {
        super(str, str2);
    }

    public static LogoutCode parse(String str) {
        if (TextUtils.equals(str, SUCCESS.getValue())) {
            return SUCCESS;
        }
        if (TextUtils.equals(str, FAILED.getValue())) {
            return FAILED;
        }
        if (TextUtils.equals(str, PARAMS_ILLEGAL.getValue())) {
            return PARAMS_ILLEGAL;
        }
        return null;
    }
}
