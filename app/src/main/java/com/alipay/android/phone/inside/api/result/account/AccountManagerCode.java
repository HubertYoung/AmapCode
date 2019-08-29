package com.alipay.android.phone.inside.api.result.account;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class AccountManagerCode extends ResultCode {
    public static final AccountManagerCode FAILED = new AccountManagerCode("account_manager_8000", "失败");
    public static final AccountManagerCode PARAMS_ILLEGAL = new AccountManagerCode("account_manager_8001", "参数非法");
    public static final AccountManagerCode SUCCESS = new AccountManagerCode("account_manager_9000", "成功");
    private static final List<AccountManagerCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(PARAMS_ILLEGAL);
    }

    protected AccountManagerCode(String str, String str2) {
        super(str, str2);
    }

    public static AccountManagerCode parse(String str) {
        for (AccountManagerCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
