package com.alipay.android.phone.inside.api.result.account;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class AccountInfoCode extends ResultCode {
    public static final AccountInfoCode FAILED = new AccountInfoCode("user_info_8000", "失败");
    public static final AccountInfoCode SUCCESS = new AccountInfoCode("user_info_9000", "成功");
    private static final List<AccountInfoCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
    }

    protected AccountInfoCode(String str, String str2) {
        super(str, str2);
    }

    public static AccountInfoCode parse(String str) {
        for (AccountInfoCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
