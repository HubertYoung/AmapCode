package com.alipay.android.phone.inside.api.result.code;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class AuthCode extends ResultCode {
    public static final AuthCode CANCEL = new AuthCode("auth_9002", "授权取消，请重试。");
    public static final AuthCode FAILED = new AuthCode("auth_9001", "授权失败，请重试。");
    public static final AuthCode INNER_EX = new AuthCode("auth_9003", "授权失败，内部异常。");
    public static final AuthCode PARAMS_ILLEGAL = new AuthCode("auth_9004", "授权失败，参数非法。");
    public static final AuthCode SUCCESS = new AuthCode("auth_9000", "授权成功。");
    private static final List<AuthCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(CANCEL);
        mCodeList.add(INNER_EX);
        mCodeList.add(PARAMS_ILLEGAL);
    }

    protected AuthCode(String str, String str2) {
        super(str, str2);
    }

    public static AuthCode parse(String str) {
        for (AuthCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
