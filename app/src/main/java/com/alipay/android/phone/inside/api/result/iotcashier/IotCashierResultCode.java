package com.alipay.android.phone.inside.api.result.iotcashier;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotCashierResultCode extends ResultCode {
    public static final IotCashierResultCode FAIL = new IotCashierResultCode("iot_cashier_result_9001", "失败");
    public static final IotCashierResultCode SUCCESS = new IotCashierResultCode("iot_cashier_result_9000", "成功");
    private static final List<IotCashierResultCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAIL);
    }

    public static IotCashierResultCode parse(String str) {
        for (IotCashierResultCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }

    protected IotCashierResultCode(String str, String str2) {
        super(str, str2);
    }
}
