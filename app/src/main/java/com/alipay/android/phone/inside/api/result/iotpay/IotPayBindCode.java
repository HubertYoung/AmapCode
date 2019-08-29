package com.alipay.android.phone.inside.api.result.iotpay;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotPayBindCode extends ResultCode {
    public static final IotPayBindCode FAIL = new IotPayBindCode("iot_pay_bind_code_9001", "失败，请重试");
    public static final IotPayBindCode SUCCESS = new IotPayBindCode("iot_pay_bind_code_9000", "获取成功");
    private static final List<IotPayBindCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAIL);
    }

    protected IotPayBindCode(String str, String str2) {
        super(str, str2);
    }

    public static IotPayBindCode parse(String str) {
        for (IotPayBindCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
