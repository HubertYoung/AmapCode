package com.alipay.android.phone.inside.api.result.iotcashier;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotCashierUnbindCode extends ResultCode {
    public static final IotCashierUnbindCode FAIL = new IotCashierUnbindCode("iot_cashier_unbind_9001", "失败，请重试");
    public static final IotCashierUnbindCode SUCCESS = new IotCashierUnbindCode("iot_cashier_unbind_9000", "成功");
    private static final List<IotCashierUnbindCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAIL);
    }

    public static IotCashierUnbindCode parse(String str) {
        for (IotCashierUnbindCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }

    protected IotCashierUnbindCode(String str, String str2) {
        super(str, str2);
    }
}
