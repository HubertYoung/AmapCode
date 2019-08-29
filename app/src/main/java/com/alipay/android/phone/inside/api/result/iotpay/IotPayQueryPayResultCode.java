package com.alipay.android.phone.inside.api.result.iotpay;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotPayQueryPayResultCode extends ResultCode {
    public static final IotPayQueryPayResultCode BIND_ERROR = new IotPayQueryPayResultCode("iot_pay_pay_result_7000", "绑定关系不匹配，请重新绑定");
    public static final IotPayQueryPayResultCode FAIL = new IotPayQueryPayResultCode("iot_pay_pay_result_8000", UserTrackerConstants.EM_PAY_FAILURE);
    public static final IotPayQueryPayResultCode PARAMS_ILLEGAL = new IotPayQueryPayResultCode("iot_pay_pay_result_8002", "参数异常");
    public static final IotPayQueryPayResultCode SUCCESS = new IotPayQueryPayResultCode("iot_pay_pay_result_9000", "支付成功");
    public static final IotPayQueryPayResultCode UNKNOWN = new IotPayQueryPayResultCode("iot_pay_pay_result_8001", "支付结果未知");
    private static final List<IotPayQueryPayResultCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAIL);
        mCodeList.add(UNKNOWN);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(BIND_ERROR);
    }

    protected IotPayQueryPayResultCode(String str, String str2) {
        super(str, str2);
    }

    public static IotPayQueryPayResultCode parse(String str) {
        for (IotPayQueryPayResultCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
