package com.alipay.android.phone.inside.api.result.iotcashier;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotCashierInitCode extends ResultCode {
    public static final IotCashierInitCode FAIL = new IotCashierInitCode("iot_cashier_init_9001", "失败");
    public static final IotCashierInitCode SUCCESS = new IotCashierInitCode("iot_cashier_init_9000", "成功");
    private static final List<IotCashierInitCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAIL);
    }

    protected IotCashierInitCode(String str, String str2) {
        super(str, str2);
    }

    public static IotCashierInitCode parse(String str) {
        for (IotCashierInitCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
