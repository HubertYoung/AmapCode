package com.alipay.android.phone.inside.api.result.smartsell;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class PayAuthPreloadCode extends ResultCode {
    public static final PayAuthPreloadCode CANCEL = new PayAuthPreloadCode("pay_auth_preload_8002", "取消");
    public static final PayAuthPreloadCode FAILED = new PayAuthPreloadCode("pay_auth_preload_8000", "失败");
    public static final PayAuthPreloadCode PARAMS_ILLEGAL = new PayAuthPreloadCode("pay_auth_preload_8001", "参数非法");
    public static final PayAuthPreloadCode SUCCESS = new PayAuthPreloadCode("pay_auth_preload_9000", "成功");
    public static final PayAuthPreloadCode TIMEOUT = new PayAuthPreloadCode("pay_auth_preload_5000", "超时返回。");
    private static final List<PayAuthPreloadCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(CANCEL);
        mCodeList.add(TIMEOUT);
    }

    protected PayAuthPreloadCode(String str, String str2) {
        super(str, str2);
    }

    public static PayAuthPreloadCode parse(String str) {
        for (PayAuthPreloadCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
