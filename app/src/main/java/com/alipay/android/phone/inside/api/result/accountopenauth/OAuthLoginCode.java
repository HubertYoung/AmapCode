package com.alipay.android.phone.inside.api.result.accountopenauth;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class OAuthLoginCode extends ResultCode {
    public static final OAuthLoginCode FAILED = new OAuthLoginCode("account_3rdauthlogin_8000", "登录失败。");
    public static final OAuthLoginCode INTERRUPTED = new OAuthLoginCode("account_3rdauthlogin_5000", "超时，授权流程中断。");
    public static final OAuthLoginCode PARAMS_ILLEGAL = new OAuthLoginCode("account_3rdauthlogin_8001", "参数非法。");
    public static final OAuthLoginCode SUCCESS = new OAuthLoginCode("account_3rdauthlogin_9000", "登录成功。");
    public static final OAuthLoginCode TOKEN_INVALID = new OAuthLoginCode("account_3rdauthlogin_8002", "token失效。");
    private static final List<OAuthLoginCode> mCodeList;

    protected OAuthLoginCode(String str, String str2) {
        super(str, str2);
    }

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(TOKEN_INVALID);
        mCodeList.add(INTERRUPTED);
    }

    public static OAuthLoginCode parse(String str) {
        for (OAuthLoginCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
