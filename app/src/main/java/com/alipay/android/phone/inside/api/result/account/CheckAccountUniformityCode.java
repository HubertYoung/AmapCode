package com.alipay.android.phone.inside.api.result.account;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class CheckAccountUniformityCode extends ResultCode {
    public static final CheckAccountUniformityCode INCONFORMITY = new CheckAccountUniformityCode("account_uniformity_9001", "账号不一致。");
    public static final CheckAccountUniformityCode INNER_EX = new CheckAccountUniformityCode("account_uniformity_9002", "校验账号一致性，内部异常。");
    public static final CheckAccountUniformityCode SUCCESS = new CheckAccountUniformityCode("account_uniformity_9000", "账号一致。");
    private static final List<CheckAccountUniformityCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(INCONFORMITY);
        mCodeList.add(INNER_EX);
    }

    protected CheckAccountUniformityCode(String str, String str2) {
        super(str, str2);
    }

    public static CheckAccountUniformityCode parse(String str) {
        for (CheckAccountUniformityCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
