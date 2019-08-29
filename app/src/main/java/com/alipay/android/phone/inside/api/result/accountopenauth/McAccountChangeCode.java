package com.alipay.android.phone.inside.api.result.accountopenauth;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class McAccountChangeCode extends ResultCode {
    public static final McAccountChangeCode FAILED = new McAccountChangeCode("mc_account_change_8000", "通知失败。");
    public static final McAccountChangeCode SUCCESS = new McAccountChangeCode("mc_account_change_9000", "通知成功。");
    private static final List<McAccountChangeCode> mCodeList;

    protected McAccountChangeCode(String str, String str2) {
        super(str, str2);
    }

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
    }

    public static McAccountChangeCode parse(String str) {
        for (McAccountChangeCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
