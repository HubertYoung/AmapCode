package com.alipay.android.phone.inside.api.result.accountopenauth;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class AFastOAuthCode extends ResultCode {
    public static final AFastOAuthCode ALIPAY_VERSION_UNMATCH = new AFastOAuthCode("afast_open_auth_4002", "支付宝当前版本不支持，请升级后再试。");
    public static final AFastOAuthCode CANCELLED = new AFastOAuthCode("afast_open_auth_6000", "用户取消授权。");
    public static final AFastOAuthCode FAILED = new AFastOAuthCode("afast_open_auth_8000", "授权失败。");
    public static final AFastOAuthCode INTERRUPTED = new AFastOAuthCode("afast_open_auth_5000", "超时，授权流程中断。");
    public static final AFastOAuthCode MC_CANCELLED = new AFastOAuthCode("afast_open_auth_6001", "商户主动取消授权");
    public static final AFastOAuthCode ST_INVALID_FAILED = new AFastOAuthCode("afast_open_auth_8001", "不具备快捷授权页面开启条件。");
    public static final AFastOAuthCode SUCCESS = new AFastOAuthCode("afast_open_auth_9000", "授权成功。");
    private static final List<AFastOAuthCode> mCodeList;

    protected AFastOAuthCode(String str, String str2) {
        super(str, str2);
    }

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(CANCELLED);
        mCodeList.add(INTERRUPTED);
        mCodeList.add(ST_INVALID_FAILED);
        mCodeList.add(ALIPAY_VERSION_UNMATCH);
        mCodeList.add(MC_CANCELLED);
    }

    public static AFastOAuthCode parse(String str) {
        for (AFastOAuthCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
