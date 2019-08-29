package com.alipay.android.phone.inside.api.result.code;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;

public class SwitchUserCode extends ResultCode {
    public static final SwitchUserCode CANCEL = new SwitchUserCode("switch_9002", "");
    public static final SwitchUserCode FAILED = new SwitchUserCode("switch_9001", "");
    public static final SwitchUserCode INNER_EX = new SwitchUserCode("switch_9003", "");
    public static final SwitchUserCode SUCCESS = new SwitchUserCode("switch_9000", "");

    protected SwitchUserCode(String str, String str2) {
        super(str, str2);
    }

    public static SwitchUserCode parse(String str) {
        if (TextUtils.equals(str, SUCCESS.getValue())) {
            return SUCCESS;
        }
        if (TextUtils.equals(str, FAILED.getValue())) {
            return FAILED;
        }
        if (TextUtils.equals(str, CANCEL.getValue())) {
            return CANCEL;
        }
        if (TextUtils.equals(str, INNER_EX.getValue())) {
            return INNER_EX;
        }
        return null;
    }
}
