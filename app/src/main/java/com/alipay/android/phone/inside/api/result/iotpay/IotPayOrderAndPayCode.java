package com.alipay.android.phone.inside.api.result.iotpay;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotPayOrderAndPayCode extends ResultCode {
    public static final IotPayOrderAndPayCode BIND_ERROR = new IotPayOrderAndPayCode("iot_pay_order_and_pay_7000", "绑定关系不匹配，请重新绑定");
    public static final IotPayOrderAndPayCode FAIL = new IotPayOrderAndPayCode("iot_pay_order_and_pay_8000", UserTrackerConstants.EM_PAY_FAILURE);
    public static final IotPayOrderAndPayCode PARAMS_ILLEGAL = new IotPayOrderAndPayCode("iot_pay_order_and_pay_8002", "参数异常");
    public static final IotPayOrderAndPayCode SUCCESS = new IotPayOrderAndPayCode("iot_pay_order_and_pay_9000", "支付成功");
    public static final IotPayOrderAndPayCode UNKNOWN = new IotPayOrderAndPayCode("iot_pay_order_and_pay_8001", "支付结果未知");
    private static final List<IotPayOrderAndPayCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAIL);
        mCodeList.add(UNKNOWN);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(BIND_ERROR);
    }

    protected IotPayOrderAndPayCode(String str, String str2) {
        super(str, str2);
    }

    public static IotPayOrderAndPayCode parse(String str) {
        for (IotPayOrderAndPayCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
