package com.alipay.android.phone.inside.api.result.accountopenauth;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class AOAuthCode extends ResultCode {
    public static final AOAuthCode ALIPAY_VERSION_UNMATCH = new AOAuthCode("account_open_auth_4002", "支付宝当前版本不支持，请升级后再试。");
    public static final AOAuthCode CANCELLED = new AOAuthCode("account_open_auth_6000", "用户取消授权。");
    public static final AOAuthCode FAILED = new AOAuthCode("account_open_auth_8000", "授权失败。");
    public static final AOAuthCode INTERRUPTED = new AOAuthCode("account_open_auth_5000", "超时，授权流程中断。");
    public static final AOAuthCode SUCCESS = new AOAuthCode("account_open_auth_9000", "授权成功。");
    private static final List<AOAuthCode> mCodeList;

    protected AOAuthCode(String str, String str2) {
        super(str, str2);
    }

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(CANCELLED);
        mCodeList.add(INTERRUPTED);
        mCodeList.add(ALIPAY_VERSION_UNMATCH);
    }

    public static AOAuthCode parse(String str) {
        for (AOAuthCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
