package com.alipay.android.phone.inside.api.result.code;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;

public class PreLoadCode extends ResultCode {
    public static final PreLoadCode FAILED = new PreLoadCode("pre_load_9001", "预加载失败。");
    public static final PreLoadCode PARAMS_ILLEGAL = new PreLoadCode("pre_load_9002", "预加载失败，参数非法。");
    public static final PreLoadCode SUCCESS = new PreLoadCode("pre_load_9000", "预加载成功。");

    protected PreLoadCode(String str, String str2) {
        super(str, str2);
    }

    public static PreLoadCode parse(String str) {
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
