package com.alipay.android.phone.inside.api.result.smartsellv2;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class PayAuthV2ResultCode extends ResultCode {
    public static final PayAuthV2ResultCode CANCEL = new PayAuthV2ResultCode("pay_auth_v2_resultcode_8002", "取消");
    public static final PayAuthV2ResultCode FAILED = new PayAuthV2ResultCode("pay_auth_v2_resultcode_8000", "失败");
    public static final PayAuthV2ResultCode PARAMS_ILLEGAL = new PayAuthV2ResultCode("pay_auth_v2_resultcode_8001", "参数非法");
    public static final PayAuthV2ResultCode SUCCESS = new PayAuthV2ResultCode("pay_auth_v2_resultcode_9000", "成功");
    public static final PayAuthV2ResultCode TIMEOUT = new PayAuthV2ResultCode("pay_auth_v2_resultcode_5000", "超时返回。");
    private static final List<PayAuthV2ResultCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(CANCEL);
        mCodeList.add(TIMEOUT);
    }

    protected PayAuthV2ResultCode(String str, String str2) {
        super(str, str2);
    }

    public static PayAuthV2ResultCode parse(String str) {
        for (PayAuthV2ResultCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
