package com.alipay.android.phone.inside.api.result.account;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.adapter.alipay.AlibcAlipay;
import com.alipay.android.phone.inside.api.result.ResultCode;
import com.sina.weibo.sdk.utils.WbAuthConstants;
import java.util.ArrayList;
import java.util.List;

public class AccountLogoutCode extends ResultCode {
    public static final AccountLogoutCode FAILED = new AccountLogoutCode(WbAuthConstants.AUTH_FAILED_NOT_INSTALL_CODE, "失败");
    public static final AccountLogoutCode SUCCESS = new AccountLogoutCode(AlibcAlipay.PAY_SUCCESS_CODE, "成功");
    public static final AccountLogoutCode TIMEOUT = new AccountLogoutCode("5000", "支付宝处理超时");
    private static final List<AccountLogoutCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(TIMEOUT);
    }

    protected AccountLogoutCode(String str, String str2) {
        super(str, str2);
    }

    public static AccountLogoutCode parse(String str) {
        for (AccountLogoutCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
