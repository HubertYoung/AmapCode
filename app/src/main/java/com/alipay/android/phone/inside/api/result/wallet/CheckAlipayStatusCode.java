package com.alipay.android.phone.inside.api.result.wallet;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class CheckAlipayStatusCode extends ResultCode {
    public static final CheckAlipayStatusCode ALIPAY_NOT_INSTALL = new CheckAlipayStatusCode("alipay_status_9004", "支付宝未安装");
    public static final CheckAlipayStatusCode ALIPAY_SIGN_ERROR = new CheckAlipayStatusCode("alipay_status_9005", "支付宝签名异常");
    public static final CheckAlipayStatusCode ALIPAY_VERSION_UNMATCH = new CheckAlipayStatusCode("alipay_status_9006", "支付宝版本太低");
    public static final CheckAlipayStatusCode INNER_EX = new CheckAlipayStatusCode("alipay_status_9002", "内部异常");
    public static final CheckAlipayStatusCode LOGIN = new CheckAlipayStatusCode("alipay_status_9000", "登录状态");
    public static final CheckAlipayStatusCode PARAMS_ILLEGAL = new CheckAlipayStatusCode("alipay_status_9003", "参数异常");
    public static final CheckAlipayStatusCode UNLOGIN = new CheckAlipayStatusCode("alipay_status_9001", "非登录状态");
    private static final List<CheckAlipayStatusCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(LOGIN);
        mCodeList.add(UNLOGIN);
        mCodeList.add(INNER_EX);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(ALIPAY_NOT_INSTALL);
        mCodeList.add(ALIPAY_SIGN_ERROR);
        mCodeList.add(ALIPAY_VERSION_UNMATCH);
    }

    protected CheckAlipayStatusCode(String str, String str2) {
        super(str, str2);
    }

    public static CheckAlipayStatusCode parse(String str) {
        for (CheckAlipayStatusCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
