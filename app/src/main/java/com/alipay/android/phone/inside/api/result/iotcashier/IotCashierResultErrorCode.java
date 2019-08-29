package com.alipay.android.phone.inside.api.result.iotcashier;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotCashierResultErrorCode extends ResultCode {
    public static final IotCashierResultErrorCode FAIL = new IotCashierResultErrorCode("iot_cashier_result_error_9001", "失败");
    public static final IotCashierResultErrorCode SUCCESS = new IotCashierResultErrorCode("iot_cashier_result_error_9000", "成功");
    private static final List<IotCashierResultErrorCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAIL);
    }

    public static IotCashierResultErrorCode parse(String str) {
        for (IotCashierResultErrorCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }

    protected IotCashierResultErrorCode(String str, String str2) {
        super(str, str2);
    }
}
