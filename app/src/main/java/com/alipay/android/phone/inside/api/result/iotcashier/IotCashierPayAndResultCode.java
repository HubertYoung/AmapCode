package com.alipay.android.phone.inside.api.result.iotcashier;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotCashierPayAndResultCode extends ResultCode {
    public static final IotCashierPayAndResultCode FAIL = new IotCashierPayAndResultCode("iot_cashier_pay_and_result_9001", "失败");
    public static final IotCashierPayAndResultCode SUCCESS = new IotCashierPayAndResultCode("iot_cashier_pay_and_result_9000", "成功");
    private static final List<IotCashierPayAndResultCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAIL);
    }

    public static IotCashierPayAndResultCode parse(String str) {
        for (IotCashierPayAndResultCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }

    protected IotCashierPayAndResultCode(String str, String str2) {
        super(str, str2);
    }
}
