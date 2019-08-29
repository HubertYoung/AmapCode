package com.alipay.android.phone.inside.api.result.code;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;

public class OnlinePayCode extends ResultCode {
    public static final OnlinePayCode CANCEL = new OnlinePayCode("pay_9002", "支付取消，请重试。");
    public static final OnlinePayCode FAILED = new OnlinePayCode("pay_9001", "支付失败，请重试。");
    public static final OnlinePayCode INNER_EX = new OnlinePayCode("pay_9005", "内部异常，请重试。");
    public static final OnlinePayCode PARAMS_ILLEGAL = new OnlinePayCode("pay_9004", "参数非法，请重试。");
    public static final OnlinePayCode PAY_UNKNOWN = new OnlinePayCode("pay_9003", "支付结果未知，请去订单列表查询。");
    public static final OnlinePayCode SUCCESS = new OnlinePayCode("pay_9000", "支付成功。");

    protected OnlinePayCode(String str, String str2) {
        super(str, str2);
    }

    public static OnlinePayCode parse(String str) {
        if (TextUtils.equals(str, SUCCESS.getValue())) {
            return SUCCESS;
        }
        if (TextUtils.equals(str, FAILED.getValue())) {
            return FAILED;
        }
        if (TextUtils.equals(str, CANCEL.getValue())) {
            return CANCEL;
        }
        if (TextUtils.equals(str, PAY_UNKNOWN.getValue())) {
            return PAY_UNKNOWN;
        }
        if (TextUtils.equals(str, PARAMS_ILLEGAL.getValue())) {
            return PARAMS_ILLEGAL;
        }
        if (TextUtils.equals(str, INNER_EX.getValue())) {
            return INNER_EX;
        }
        return null;
    }
}
