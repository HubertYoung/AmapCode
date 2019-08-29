package com.alipay.android.phone.inside.api.result.iotpay;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotPayCheckBindStatusCode extends ResultCode {
    public static final IotPayCheckBindStatusCode BIND = new IotPayCheckBindStatusCode("iot_pay_bind_status_9000", "已绑定");
    public static final IotPayCheckBindStatusCode FAILED = new IotPayCheckBindStatusCode("iot_pay_bind_status_9002", "失败，请重试");
    public static final IotPayCheckBindStatusCode UNBIND = new IotPayCheckBindStatusCode("iot_pay_bind_status_9001", "未绑定");
    private static final List<IotPayCheckBindStatusCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(BIND);
        mCodeList.add(UNBIND);
        mCodeList.add(FAILED);
    }

    protected IotPayCheckBindStatusCode(String str, String str2) {
        super(str, str2);
    }

    public static IotPayCheckBindStatusCode parse(String str) {
        for (IotPayCheckBindStatusCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
