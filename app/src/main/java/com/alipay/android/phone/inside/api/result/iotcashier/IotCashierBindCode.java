package com.alipay.android.phone.inside.api.result.iotcashier;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotCashierBindCode extends ResultCode {
    public static final IotCashierBindCode FAIL = new IotCashierBindCode("iot_cashier_bind_9001", "失败，请重试");
    public static final IotCashierBindCode SUCCESS = new IotCashierBindCode("iot_cashier_bind_9000", "成功");
    private static final List<IotCashierBindCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAIL);
    }

    protected IotCashierBindCode(String str, String str2) {
        super(str, str2);
    }

    public static IotCashierBindCode parse(String str) {
        for (IotCashierBindCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
