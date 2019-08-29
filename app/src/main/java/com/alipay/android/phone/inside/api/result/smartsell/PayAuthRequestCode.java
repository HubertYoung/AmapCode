package com.alipay.android.phone.inside.api.result.smartsell;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class PayAuthRequestCode extends ResultCode {
    public static final PayAuthRequestCode CANCEL = new PayAuthRequestCode("pay_auth_request_8002", "取消");
    public static final PayAuthRequestCode FAILED = new PayAuthRequestCode("pay_auth_request_8000", "失败");
    public static final PayAuthRequestCode PARAMS_ILLEGAL = new PayAuthRequestCode("pay_auth_request_8001", "参数非法");
    public static final PayAuthRequestCode SUCCESS = new PayAuthRequestCode("pay_auth_request_9000", "成功");
    public static final PayAuthRequestCode TIMEOUT = new PayAuthRequestCode("pay_auth_request_5000", "超时返回。");
    private static final List<PayAuthRequestCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(CANCEL);
        mCodeList.add(TIMEOUT);
    }

    protected PayAuthRequestCode(String str, String str2) {
        super(str, str2);
    }

    public static PayAuthRequestCode parse(String str) {
        for (PayAuthRequestCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
