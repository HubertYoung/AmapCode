package com.alipay.android.phone.inside.api.result.iotpay;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotPayInitCode extends ResultCode {
    public static final IotPayInitCode FAIL = new IotPayInitCode("iot_pay_init_9001", "失败，请重试");
    public static final IotPayInitCode SUCCESS = new IotPayInitCode("iot_pay_init_9000", "成功");
    private static final List<IotPayInitCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAIL);
    }

    protected IotPayInitCode(String str, String str2) {
        super(str, str2);
    }

    public static IotPayInitCode parse(String str) {
        for (IotPayInitCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
