package com.alipay.android.phone.inside.api.result.iotpay;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotPayUnbindCode extends ResultCode {
    public static final IotPayUnbindCode FAIL = new IotPayUnbindCode("iot_pay_unbind_code_9001", "失败，请重试");
    public static final IotPayUnbindCode SUCCESS = new IotPayUnbindCode("iot_pay_unbind_code_9000", "获取成功");
    public static final IotPayUnbindCode UNBIND = new IotPayUnbindCode("iot_pay_unbind_code_9002", "未绑定，请先绑定");
    private static final List<IotPayUnbindCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAIL);
        mCodeList.add(UNBIND);
    }

    protected IotPayUnbindCode(String str, String str2) {
        super(str, str2);
    }

    public static IotPayUnbindCode parse(String str) {
        for (IotPayUnbindCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
