package com.alipay.android.phone.inside.api.result.account;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class OAuthAccountUniformityCode extends ResultCode {
    public static final OAuthAccountUniformityCode INNER_EX = new OAuthAccountUniformityCode("oauth_account_uniformity_9002", "检查账户内部异常。");
    public static final OAuthAccountUniformityCode SUCCESS = new OAuthAccountUniformityCode("oauth_account_uniformity_9000", "账号一致。");
    public static final OAuthAccountUniformityCode UNCONFORMITY = new OAuthAccountUniformityCode("oauth_account_uniformity_9001", "账号不一致。");
    private static final List<OAuthAccountUniformityCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(UNCONFORMITY);
        mCodeList.add(INNER_EX);
    }

    protected OAuthAccountUniformityCode(String str, String str2) {
        super(str, str2);
    }

    public static OAuthAccountUniformityCode parse(String str) {
        for (OAuthAccountUniformityCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
