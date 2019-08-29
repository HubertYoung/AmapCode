package com.alipay.android.phone.inside.api.result.iotcashier;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotCashierCheckBindStatusCode extends ResultCode {
    public static final IotCashierCheckBindStatusCode BIND = new IotCashierCheckBindStatusCode("iot_cashier_bind_status_9000", "已绑定");
    public static final IotCashierCheckBindStatusCode FAILED = new IotCashierCheckBindStatusCode("iot_cashier_bind_status_9002", "失败，请重试");
    public static final IotCashierCheckBindStatusCode UNBIND = new IotCashierCheckBindStatusCode("iot_cashier_bind_status_9001", "未绑定");
    private static final List<IotCashierCheckBindStatusCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(BIND);
        mCodeList.add(UNBIND);
        mCodeList.add(FAILED);
    }

    public static IotCashierCheckBindStatusCode parse(String str) {
        for (IotCashierCheckBindStatusCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }

    protected IotCashierCheckBindStatusCode(String str, String str2) {
        super(str, str2);
    }
}
