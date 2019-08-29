package com.alipay.android.phone.inside.api.result.iotpay;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotPayQueryUnbindResultCode extends ResultCode {
    public static final IotPayQueryUnbindResultCode FAIL = new IotPayQueryUnbindResultCode("iot_pay_unbind_result_9003", "失败，请重试");
    public static final IotPayQueryUnbindResultCode NOT_FINISH = new IotPayQueryUnbindResultCode("iot_pay_unbind_result_9001", "继续轮训");
    public static final IotPayQueryUnbindResultCode PARAMS_ILLEGAL = new IotPayQueryUnbindResultCode("iot_pay_unbind_result_9002", "参数异常，请先绑定");
    public static final IotPayQueryUnbindResultCode SUCCESS = new IotPayQueryUnbindResultCode("iot_pay_unbind_result_9000", "解绑成功，轮训结束");
    private static final List<IotPayQueryUnbindResultCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(NOT_FINISH);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(FAIL);
    }

    protected IotPayQueryUnbindResultCode(String str, String str2) {
        super(str, str2);
    }

    public static IotPayQueryUnbindResultCode parse(String str) {
        for (IotPayQueryUnbindResultCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
